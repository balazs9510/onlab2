using BLL.Services;
using DAL.Model;
using log4net;
using PhysicExperiment.Models;
using System;
using System.Diagnostics;
using System.IO;
using System.Text;
using System.Threading.Tasks;
using System.Web.Mvc;


namespace PhysicExperiment.Controllers
{
    public class ExperimentsController : Controller
    {
        private readonly ExperimentService experimentService;
        private static readonly ILog log = LogManager.GetLogger(typeof(ExperimentsController));
        public ExperimentsController()
        {
            var dbContext = new ApplicationDbContext();
            experimentService = new ExperimentService(dbContext);
        }
        // GET: Experiments
        public async Task<ActionResult> Index()
        {
            ViewBag.Experiments = (await experimentService.GetExperimentsAsync()).ConvertAll(x => new ExperimentViewModel(x));
            return View();
        }

        [HttpGet]
        public async Task<ActionResult> GetExperiment(Guid id)
        {
            var experiment = await experimentService.GetExperiment(id);
            var vm = new ExperimentViewModel(experiment);
            if (experiment.Images != null)
            {
                var expImage = await experimentService.GetExperimentLastImage(id);
                byte[] imageArray = System.IO.File.ReadAllBytes(expImage.Path);
                vm.LastImageBase64 = "data:image/png;base64," + Convert.ToBase64String(imageArray);
            }
            return View(vm);
        }
        [HttpGet]
        public async Task<ActionResult> GetExperimentImage(Guid id)
        {
            var expImage = await experimentService.GetExperimentLastImage(id);
            return File(System.IO.File.ReadAllBytes(expImage.Path), "image");
        }
        [HttpGet]
        public async Task<ActionResult> GetVideo(Guid id)
        {
            var images = await experimentService.GetExperimentImages(id);
            var path = Path.Combine(Server.MapPath("~/App_Data"),"test.txt");
            var builder = new StringBuilder();
            for (int i = 0; i < images.Count; i++)
            {
                builder.AppendLine($"file \"{images[i].Path.Replace(@"\", @"\\")}\"");
            }
            System.IO.File.AppendAllText(path, builder.ToString());
            Process ffmpeg = new Process();
            ffmpeg.StartInfo.FileName = "C:\\Users\\Balazs\\Desktop\\ffmpeg.exe";
            ffmpeg.StartInfo.Arguments = $"-y -r 1/5 -f concat -safe 0 -i \"{path}\" -c:v libx264 -vf \"fps = 25,format = yuv420p\" \"c:\\out.mp4\"";
            ffmpeg.StartInfo.RedirectStandardError = true;
            ffmpeg.StartInfo.RedirectStandardOutput = true;
            ffmpeg.StartInfo.UseShellExecute = false;
            ffmpeg.StartInfo.CreateNoWindow = true;
            ffmpeg.OutputDataReceived += new DataReceivedEventHandler((s, e) =>
            {
                var esd = e.Data;
                log.Debug(e.Data);
                //logMessage("Info:" + e.Data);
            });
            ffmpeg.ErrorDataReceived += new DataReceivedEventHandler((s, e) =>
            {
                if (e.Data != null)
                {
                    log.Debug(e.Data);
                }
                //logMessage("Error:" + e.Data);
            });
            ffmpeg.Start();
            ffmpeg.BeginErrorReadLine();
            ffmpeg.BeginOutputReadLine();
            ffmpeg.WaitForExit();
            return Json("asd", JsonRequestBehavior.AllowGet);
        }
    }
}
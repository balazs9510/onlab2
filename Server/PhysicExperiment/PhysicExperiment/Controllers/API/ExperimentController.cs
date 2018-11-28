using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web;
using System.Web.Hosting;
using System.Web.Http;
using System.Web.Http.Results;
using BLL.Services;
using DAL.Model;
using log4net;
using PhysicExperiment.Helper;
using PhysicExperiment.Models;
using PhysicExperiment.Models.DTOs;

namespace PhysicExperiment.Controllers.API
{
    [Authorize]
    public class ExperimentController : ApiController
    {
        private static readonly ILog log = LogManager.GetLogger(typeof(ExperimentController));
        private ExperimentService experimentService;
        private UserService userService;
        public ExperimentController()
        {
            var appContext = ApplicationDbContext.Create();
            experimentService = new ExperimentService(appContext);
            userService = new UserService(appContext);
        }
        [HttpPost]
        public async Task<IHttpActionResult> CreateExperiment(ExperimentViewModel vm)
        {
            var result = new JsonResultBase();
            if (ModelState.IsValid)
            {
                var exp = vm.ToEntity();
                exp.Id = Guid.NewGuid();

                var user = await userService.SingleOrDefaultAsync(u => u.Email == User.Identity.Name);
                if (user == null)
                    return Json("Nincs ilyen felhasználó");
                exp.CreatorUser = user;
                try
                {
                    await experimentService.InsertExperiment(exp);
                }
                catch(Exception e)
                {
                    log.Error(nameof(CreateExperiment), e);
                }
            }
            return Json(result);
        }
        [HttpGet]
        public async Task<IHttpActionResult> GetExperiments()
        {
            var experiments = new List<Experiment>();
            try
            {
                    experiments = await experimentService.GetExperimentsAsync();
            }
            catch(Exception e)
            {
                log.Error(nameof(GetExperiments), e);
            }
            return Json(experiments);
        }
        [HttpGet]
        [Route("api/experiment/{id}")]
        public async Task<IHttpActionResult> GetExperiment(Guid id)
        {
            try
            {
                var experiment = await experimentService.GetExperiment(id);
                return Json(experiment);
            }
            catch (Exception e)
            {
                log.Error(nameof(GetExperiments), e);
            }
            return Json("Hiba történt.");
        }
        [HttpGet]
        [Route("api/experiment/stopexperiment/{id}")]
        public async Task<IHttpActionResult> StopExperiment(Guid id)
        {
            try
            {
                var experiment = await experimentService.GetExperiment(id);
                experiment.EndDate = DateTime.Now;
                experiment.State = Experiment.ExperimentState.End;
                await experimentService.UpdateExperiment(experiment);
            }
            catch (Exception e)
            {
                log.Error(nameof(StopExperiment), e);
            }
            return Json("Hiba történt.");
        }
    }
}

using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;
using BLL.Services;
using DAL.Model;

namespace PhysicExperiment.Controllers.API
{
    [Authorize]
    public class ExperimentImageController : ApiController
    {
        private ExperimentService experimentService;
        private UserService userService;
        private ApplicationDbContext dbContext;
        public ExperimentImageController()
        {
            dbContext = new ApplicationDbContext();
            experimentService = new ExperimentService(dbContext);
            userService = new UserService(dbContext);
        }
        [HttpPost]
        [Route("api/ExperimentImage/{experimentId}")]
        public async Task<HttpResponseMessage> UploadImageAsync(string experimentId)
        {
            var experiment = await experimentService.GetExperiment(Guid.Parse(experimentId));
            if (experiment == null)
                Request.CreateErrorResponse(HttpStatusCode.BadRequest, "Not found experiment");
            var user = await userService.SingleOrDefaultAsync(x => x.UserName == HttpContext.Current.User.Identity.Name);
            if (experiment.CreatorUser != user)
                Request.CreateErrorResponse(HttpStatusCode.BadRequest, "Wrong user.");

            Dictionary<string, object> dict = new Dictionary<string, object>();

            try
            {
                HttpRequest httpRequest = HttpContext.Current.Request;
                foreach (string file in httpRequest.Files)
                {
                    HttpResponseMessage response = Request.CreateResponse(HttpStatusCode.Created);

                    var postedFile = httpRequest.Files[file];
                    if (postedFile != null && postedFile.ContentLength > 0)
                    {

                        // int MaxContentLength = 1024 * 1024 * 1; //Size = 1 MB

                        IList<string> AllowedFileExtensions = new List<string> { ".jpg", ".png" };
                        var ext = postedFile.FileName.Substring(postedFile.FileName.LastIndexOf('.'));
                        var extension = ext.ToLower();
                        if (!AllowedFileExtensions.Contains(extension))
                        {

                            var message = string.Format("Please Upload image of type .jpg, .png.");

                            dict.Add("error", message);
                            response.StatusCode = HttpStatusCode.BadRequest;
                            return response;
                        }
                        else
                        {
                            var serverPath = HttpContext.Current.Server.MapPath("~/App_Data");
                            var currentUserFolder = Path.Combine(serverPath, user.UserName);
                            Directory.CreateDirectory(currentUserFolder);
                            var experimentFolder = Path.Combine(currentUserFolder, experiment.Id.ToString());
                            Directory.CreateDirectory(experimentFolder);
                            var id = Guid.NewGuid();
                            var filePath = HttpContext.Current.Server.MapPath($"~/App_Data/{user.UserName}/{experiment.Id}/" + id + extension);
                            var expImage = new ExperimentImage
                            {
                                Id = id,
                                ExperimentId = experiment.Id,
                                Path = filePath,
                                CreationTime = DateTime.Now
                                
                            };

                            postedFile.SaveAs(filePath);
                            dbContext.ExperimentImages.Add(expImage);
                            await dbContext.SaveChangesAsync();
                            
                        }
                    }

                    var message1 = string.Format("Image Updated Successfully.");
                    return Request.CreateErrorResponse(HttpStatusCode.Created, message1); ;
                }
            }
            catch (Exception e)
            {
                var res = string.Format(e.ToString());
                dict.Add("error", res);
                return Request.CreateResponse(HttpStatusCode.NotFound, dict);
            }
            return Request.CreateErrorResponse(HttpStatusCode.BadRequest, "Nagy error");
        }
    }
}

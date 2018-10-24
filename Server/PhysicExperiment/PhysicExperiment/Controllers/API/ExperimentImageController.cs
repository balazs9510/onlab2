using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;
using BLL.Services;

namespace PhysicExperiment.Controllers.API
{
    public class ExperimentImageController : ApiController
    {
        private ExperimentService experimentService;
        public ExperimentImageController()
        {
            experimentService = new ExperimentService(new DAL.Model.ApplicationDbContext());
        }
        [HttpPost]
        public async Task<HttpResponseMessage> UploadImageAsync(Guid experimentId)
        {
            var experiment = experimentService.SingleOrDefaultAsync(e => e.Id == experimentId);
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

                        IList<string> AllowedFileExtensions = new List<string> { ".jpg", ".gif", ".png" };
                        var ext = postedFile.FileName.Substring(postedFile.FileName.LastIndexOf('.'));
                        var extension = ext.ToLower();
                        if (!AllowedFileExtensions.Contains(extension))
                        {

                            var message = string.Format("Please Upload image of type .jpg,.gif,.png.");

                            dict.Add("error", message);
                            response.StatusCode = HttpStatusCode.BadRequest;
                            return response;
                        }
                        else
                        {
                            var filePath = HttpContext.Current.Server.MapPath("~/App_Data/" + Guid.NewGuid() + extension);
                            //Userimage myfolder name where i want to save my image
                            postedFile.SaveAs(filePath);

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

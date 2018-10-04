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
using PhysicExperiment.Helper;
using PhysicExperiment.Models.DTOs;

namespace PhysicExperiment.Controllers.API
{
    public class ExperimentController : ApiController
    {
        private ExperimentService experimentService;
        public ExperimentController()
        {
            experimentService = new ExperimentService(ApplicationDbContext.Create());
        }
        [HttpGet]

        public JsonResult<string> Stg()
        {
            return Json("ok");
        }
        [HttpPost]
        public async Task<IHttpActionResult> CreateExperiment(ExperimentDTO dto)
        {
            if (ModelState.IsValid)
            {
                try
                {
                    Experiment experiment = new Experiment();
                    experiment.Id = Guid.NewGuid();
                    dto.ToEntity(experiment);
                    experimentService.Insert(experiment);
                    return Ok(experiment);
                }
                catch(Exception e)
                {
                    e.ToString();
                }
                return InternalServerError();
                
            }
            else
            {
                return BadRequest(ModelState);
            }
        }
    }
}

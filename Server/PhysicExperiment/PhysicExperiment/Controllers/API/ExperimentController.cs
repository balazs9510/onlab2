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
                var user = HttpContext.Current.User.Identity.Name;
                try
                {
                    experimentService.Insert(exp, true);
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
            //    if (onlyMine)
            //    {
            //        var user = await userService.SingleOrDefaultAsync(u => u.Email == HttpContext.Current.User.Identity.Name);
            //        experiments = experimentService.GetList(x => x.CreatorUser == user);
            //    }
            //    else
            //    {
                    experiments = experimentService.GetList();
                //}
            }
            catch(Exception e)
            {
                log.Error(nameof(GetExperiments), e);
            }
            return Json(experiments);
        }
    }
}

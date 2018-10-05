using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Threading.Tasks;
using System.Net;
using System.Web;
using System.Web.Mvc;
using DAL.Model;
using BLL.Services;
using PhysicExperiment.Models;

namespace PhysicExperiment.Controllers
{
    public class ExperimentsController : Controller
    {
        private ApplicationDbContext db = new ApplicationDbContext();
        private ExperimentService _experimentService;
        public ExperimentsController()
        {
            _experimentService = new ExperimentService(db);
        }
        [HttpPost]
        public async Task<ActionResult> CreateExperiment(ExperimentCreateViewModel vm)
        {
            if (ModelState.IsValid)
            {
                var experiment = vm.ToEntity();
                experiment.Id = Guid.NewGuid();
                experiment.Author = 
                    db.Users.SingleOrDefault(u => u.UserName == HttpContext.User.Identity.Name);
                try
                {
                    _experimentService.Insert(experiment, true);
                    return new HttpStatusCodeResult(201);
                }
                catch(Exception e)
                {
                    return new HttpStatusCodeResult(500);
                }
                 
            }
            return new HttpStatusCodeResult(404);
        }
    }    
}

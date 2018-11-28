using BLL.Services;
using DAL.Model;
using PhysicExperiment.Models.DTOs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Web.Http;

namespace PhysicExperiment.Controllers.API
{
    [Authorize]
    public class AccountController : ApiController
    {
        private UserService userService;
        private ExperimentService experimentService;
        public AccountController()
        {
            var dbContext = ApplicationDbContext.Create();
            userService = new UserService(dbContext);
            experimentService = new ExperimentService(dbContext);
        }
        [Route("account/myid")]
        public async Task<IHttpActionResult> GetLoggedUserId()
        {
            var user = await userService.SingleOrDefaultAsync(u => u.Email == User.Identity.Name);
            if (user == null)
                return Json("Nincs ilyen felhasználó");
            return Json(user.Id);
        }
        [Route("account/{userId}/experiments")]
        public async Task<IHttpActionResult> GetLoggedUserId(string userId)
        {
            var user = await userService.SingleOrDefaultAsync(u => u.Id == userId);
            if (user == null)
                return Json("Nincs ilyen felhasználó");
            try
            {
                var experiments = (await experimentService.GetUserExperiment(userId)).Select(e => new ExperimentDTO(e));
                return Json(experiments);
            }
            catch (Exception e)
            {
                return Json(new List<Experiment>());
            }

        }

    }
}

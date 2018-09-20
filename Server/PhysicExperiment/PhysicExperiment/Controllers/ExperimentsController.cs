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

        // GET: Experiments
        public ActionResult Index()
        {
            return View(_experimentService.GetList());
        }

        // GET: Experiments/Details/5
        public async Task<ActionResult> Details(Guid? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Experiment experiment = await _experimentService.SingleOrDefaultAsync(e => e.Id == id);
            if (experiment == null)
            {
                return HttpNotFound();
            }
            return View(experiment);
        }
        public ActionResult Create()
        {
            return View();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Create([Bind(Include = "Id,StartDate,EndDate,Name")] Experiment experiment)
        {
            if (ModelState.IsValid)
            {
                experiment.Id = Guid.NewGuid();
                _experimentService.Insert(experiment, true);
                return RedirectToAction("Index");
            }

            return View(experiment);
        }

        // GET: Experiments/Edit/5
        public async Task<ActionResult> Edit(Guid? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Experiment experiment = await db.Experiments.FindAsync(id);
            if (experiment == null)
            {
                return HttpNotFound();
            }
            return View(experiment);
        }

        // POST: Experiments/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Edit([Bind(Include = "Id,StartDate,EndDate,Name")] Experiment experiment)
        {
            if (ModelState.IsValid)
            {
                db.Entry(experiment).State = EntityState.Modified;
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }
            return View(experiment);
        }

        // GET: Experiments/Delete/5
        public async Task<ActionResult> Delete(Guid? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Experiment experiment = await db.Experiments.FindAsync(id);
            if (experiment == null)
            {
                return HttpNotFound();
            }
            return View(experiment);
        }

        // POST: Experiments/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> DeleteConfirmed(Guid id)
        {
            Experiment experiment = await db.Experiments.FindAsync(id);
            db.Experiments.Remove(experiment);
            await db.SaveChangesAsync();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
    }
}

using DAL.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.Entity;

namespace BLL.Services
{
    public class ExperimentService : ServiceBase<ApplicationDbContext, Experiment>
    {
        public ExperimentService(ApplicationDbContext dbContext) :
            base(dbContext)
        {

        }
        //public async Task<ExperimentImage> AddImageAsync(Guid experimentId, string filePath)
        //{
        //    var experiment = await DbContext.Experiments.SingleOrDefaultAsync(e => e.Id == experimentId);
        //    ExperimentImage newImage = new ExperimentImage
        //    {
        //        Id = Guid.NewGuid(),
        //        ExperimentId = experiment.Id,
        //        Path = filePath
        //    };
        //    experiment.Images.Add(newImage);
        //    await DbContext.SaveChangesAsync();
        //    return newImage;
        //}
    }
}

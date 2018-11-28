using DAL.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.Entity;

namespace BLL.Services
{
    public class ExperimentService 
    {
        private readonly ApplicationDbContext _dbContext;
        public ExperimentService(ApplicationDbContext dbContext) 
        {
            _dbContext = dbContext;
        }
        public async Task<List<Experiment>> GetExperimentsAsync()
        {
            return await _dbContext.Experiments.ToListAsync();
        }
        public async Task<Experiment> GetExperiment(Guid id)
        {
            return await _dbContext.Experiments.FirstOrDefaultAsync(x => x.Id == id);
        }
        public async Task<List<Experiment>> GetUserExperiment(string userId)
        {
            return await _dbContext.Experiments.Where(x => x.CreatorUserId == userId).ToListAsync();
        }
        public async Task InsertExperiment(Experiment experiment)
        {
            _dbContext.Experiments.Add(experiment);
            await _dbContext.SaveChangesAsync();
        }
        public async Task UpdateExperiment(Experiment experiment)
        {
            var entity = _dbContext.Experiments.Find(experiment.Id);
            if (entity == null)
            {
                return;
            }
            _dbContext.Entry(entity).CurrentValues.SetValues(experiment);
            await _dbContext.SaveChangesAsync();
        }
        public async Task<ExperimentImage> GetExperimentLastImage(Guid experimentId)
        {
            var lastImageDate = _dbContext.ExperimentImages.Where(x => x.ExperimentId == experimentId).Max(x => (DateTime?)x.CreationTime);
            return await _dbContext.ExperimentImages.FirstOrDefaultAsync(y => y.CreationTime == lastImageDate && y.ExperimentId == experimentId);
        }
        public async Task<List<ExperimentImage>> GetExperimentImages(Guid experimentId)
        {
            return await _dbContext.ExperimentImages.Where(x => x.ExperimentId == experimentId).OrderBy(x => x.CreationTime).ToListAsync();
        }
    }
}

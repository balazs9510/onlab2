using DAL.Enumerations;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DAL.Model
{
    public class Experiment
    {
        public Guid Id { get; set; }
        public ApplicationUser Author { get; set; }
        public DateTime StartDate { get; set; }
        public DateTime? EndDate { get; set; }
        public DateTime? ExpectedEndDate { get; set; }
        public string Description { get; set; }
        public string Name { get; set; }
        public ExperimentState State { get; set; }
        public List<ExperimentImage> Images { get; set; }
    }
}

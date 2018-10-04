using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DAL.Model
{
    public class Experiment
    {
        public Guid Id { get; set; }
        public string Author { get; set; }
        // public Guid AuthorId { get; set; }
        public DateTime StartDate { get; set; }
        public DateTime? EndDate { get; set; }
        public string Name { get; set; }
        public List<ExperimentImage> Images { get; set; }
    }
}

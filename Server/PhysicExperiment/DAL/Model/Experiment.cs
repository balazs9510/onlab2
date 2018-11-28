using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DAL.Model
{
    public class Experiment
    {
        public Guid Id { get; set; }
        [ForeignKey("CreatorUserId")]
        public ApplicationUser CreatorUser { get; set; }
        public string CreatorUserId { get; set; }
        public string Author { get; set; }
        public DateTime StartDate { get; set; }
        public DateTime? EndDate { get; set; }
        public DateTime? ExpectedEndDate { get; set; }
        public string Description { get; set; }
        public string Name { get; set; }
        public ExperimentState State { get; set; }
        public List<ExperimentImage> Images { get; set; }
        public enum ExperimentState
        {
            Unknown = 0,
            Running = 1,
            Paused = 2,
            End = 3,
            Cancelled = 4
        }
    }
}

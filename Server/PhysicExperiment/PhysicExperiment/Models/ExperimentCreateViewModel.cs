using DAL.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace PhysicExperiment.Models
{
    public class ExperimentCreateViewModel
    {
        public string Name { get; set; }
        public string Author { get; set; }
        public DateTime StartDate { get; set; }
        public string Description { get; set; }
        public DateTime? ExpectedEndDate { get; set; }
        public Experiment ToEntity()
        {
            Experiment experiment = new Experiment();
            experiment.StartDate = StartDate;
            experiment.Name = Name;
            experiment.Description = Description;
            experiment.ExpectedEndDate = ExpectedEndDate;
            return experiment;
        }
    }
}
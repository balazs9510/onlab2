using DAL.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace PhysicExperiment.Models
{
    public class ExperimentViewModel
    {
        public string Name { get; set; }
        public string Author { get; set; }

        public string Description { get; set; }
        public DateTime StartDate { get; set; }
        public DateTime ExpectedEndDate { get; set; }
        public Experiment ToEntity()
        {
            var experiment = new Experiment
            {
                Name = Name,
                Author = Author,
                Description = Description,
                ExpectedEndDate = ExpectedEndDate,
                StartDate = StartDate,
                State = Experiment.ExperimentState.Running
            };
            return experiment;
        }
    }
}
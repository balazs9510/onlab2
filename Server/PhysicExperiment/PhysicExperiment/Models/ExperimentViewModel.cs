using DAL.Model;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace PhysicExperiment.Models
{
    public class ExperimentViewModel
    {
        public Guid? Id { get; set; }
        [Display(Name = "Név")]
        public string Name { get; set; }
        [Display(Name = "Végrehajtó")]
        public string Author { get; set; }
        [Display(Name = "Leírás")]
        public string Description { get; set; }
        [Display(Name = "Kezdet")]
        public DateTime StartDate { get; set; }
        public DateTime ExpectedEndDate { get; set; }
        public string LastImageBase64 { get; set; }
        public ExperimentViewModel()
        {

        }
        public ExperimentViewModel(Experiment entity)
        {
            Id = entity.Id;
            Name = entity.Name;
            Author = entity.Author;
            Description = entity.Description;
            StartDate = entity.StartDate;
            ExpectedEndDate = entity.ExpectedEndDate.HasValue ? entity.ExpectedEndDate.Value : DateTime.Now;
            
        }
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
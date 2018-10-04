using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using DAL.Model;

namespace PhysicExperiment.Models.DTOs
{
    public class ExperimentDTO
    {
        public string Author { get; set; }
        public DateTime StartDate { get; set; }
        public DateTime EndDate { get; set; }
        public string Name { get; set; }
        public Experiment ToEntity(Experiment entity)
        {
            if (entity == null) entity = new Experiment();
            entity.Author = Author;
            entity.EndDate = EndDate;
            entity.Name = Name;
            entity.StartDate = StartDate;
            return entity;
        }
    }
}
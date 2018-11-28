using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using DAL.Model;
using static DAL.Model.Experiment;

namespace PhysicExperiment.Models.DTOs
{
    public class ExperimentDTO
    {
        public Guid Id { get; set; }
        public string Author { get; set; }
        public DateTime StartDate { get; set; }
        public DateTime? EndDate { get; set; }
        public string Name { get; set; }
        public ExperimentState State { get; set; }
        public ExperimentDTO()
        {

        }
        public ExperimentDTO(Experiment entity)
        {
            Id = entity.Id;
            Author = entity.Author;
            EndDate = entity.EndDate;
            Name = entity.Name;
            StartDate = entity.StartDate;
            State = entity.State;
        }
        public Experiment ToEntity(Experiment entity)
        {
            if (entity == null) entity = new Experiment();
            entity.Author = Author;
            entity.EndDate = EndDate;
            entity.Name = Name;
            entity.StartDate = StartDate;
            entity.State = State;
            return entity;
        }
    }
}
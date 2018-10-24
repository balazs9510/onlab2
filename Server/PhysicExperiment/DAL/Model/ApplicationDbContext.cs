using Microsoft.AspNet.Identity.EntityFramework;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Data.Common;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DAL.Model
{
    [DbConfigurationType(typeof(MySqlConfiguration))]
    public class ApplicationDbContext : IdentityDbContext<ApplicationUser>
    {
        public ApplicationDbContext()
            : base("DefaultConnection")
        {
        }
        public static ApplicationDbContext Create()
        {
            return new ApplicationDbContext();
        }
        // Constructor to use on a DbConnection that is already opened
        public ApplicationDbContext(DbConnection existingConnection, bool contextOwnsConnection)
          : base(existingConnection, contextOwnsConnection)
        {

        }


        public System.Data.Entity.DbSet<DAL.Model.Experiment> Experiments { get; set; }
        public System.Data.Entity.DbSet<DAL.Model.ExperimentImage> ExperimentImages { get; set; }
    }
}

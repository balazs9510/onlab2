using DAL.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BLL.Services
{
    public class UserService : ServiceBase<ApplicationDbContext, ApplicationUser>
    {
        public UserService(ApplicationDbContext dbContextType) : base(dbContextType)
        {
        }
    }
}

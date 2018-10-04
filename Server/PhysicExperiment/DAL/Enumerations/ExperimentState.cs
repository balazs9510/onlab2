using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DAL.Enumerations
{
    public enum ExperimentState
    {
        Unknown = 0,
        Running = 1,
        Paused = 2,
        End = 3,
        Cancelled = 4
    }
}

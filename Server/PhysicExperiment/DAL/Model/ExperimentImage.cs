using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DAL.Model
{
    public class ExperimentImage
    {
        public Guid Id { get; set; }
        public Guid ExperimentId { get; set; }
        public byte[] Content { get; set; }
    }
}

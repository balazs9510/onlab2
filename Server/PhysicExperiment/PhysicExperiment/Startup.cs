using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(PhysicExperiment.Startup))]
namespace PhysicExperiment
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}

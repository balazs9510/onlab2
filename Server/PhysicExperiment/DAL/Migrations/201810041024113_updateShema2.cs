namespace DAL.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class updateShema2 : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.AspNetUsers", "Name", c => c.String(unicode: false));
            AddColumn("dbo.AspNetUsers", "Age", c => c.Int());
            AddColumn("dbo.AspNetUsers", "Job", c => c.String(unicode: false));
        }
        
        public override void Down()
        {
            DropColumn("dbo.AspNetUsers", "Job");
            DropColumn("dbo.AspNetUsers", "Age");
            DropColumn("dbo.AspNetUsers", "Name");
        }
    }
}

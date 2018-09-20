namespace DAL.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class First : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Experiments",
                c => new
                    {
                        Id = c.Guid(nullable: false),
                        StartDate = c.DateTime(nullable: false),
                        EndDate = c.DateTime(),
                        Name = c.String(),
                        Author_Id = c.String(maxLength: 128),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.AspNetUsers", t => t.Author_Id)
                .Index(t => t.Author_Id);
            
            CreateTable(
                "dbo.ExperimentImages",
                c => new
                    {
                        Id = c.Guid(nullable: false),
                        ExperimentId = c.Guid(nullable: false),
                        Content = c.Binary(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Experiments", t => t.ExperimentId, cascadeDelete: true)
                .Index(t => t.ExperimentId);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.ExperimentImages", "ExperimentId", "dbo.Experiments");
            DropForeignKey("dbo.Experiments", "Author_Id", "dbo.AspNetUsers");
            DropIndex("dbo.ExperimentImages", new[] { "ExperimentId" });
            DropIndex("dbo.Experiments", new[] { "Author_Id" });
            DropTable("dbo.ExperimentImages");
            DropTable("dbo.Experiments");
        }
    }
}

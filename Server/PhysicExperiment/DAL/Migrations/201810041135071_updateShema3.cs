namespace DAL.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class updateShema3 : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Experiments", "ExpectedEndDate", c => c.DateTime(precision: 0));
            AddColumn("dbo.Experiments", "Description", c => c.String(unicode: false));
            AddColumn("dbo.Experiments", "State", c => c.Int(nullable: false));
        }
        
        public override void Down()
        {
            DropColumn("dbo.Experiments", "State");
            DropColumn("dbo.Experiments", "Description");
            DropColumn("dbo.Experiments", "ExpectedEndDate");
        }
    }
}

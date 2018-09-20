using DAL.Model;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Linq.Expressions;
using System.Text;
using System.Threading.Tasks;

namespace BLL.Services
{
    public class ServiceBase<DbContextType, EntityType>
        where DbContextType : ApplicationDbContext
        where EntityType : class, new()
    {
        public DbContextType DbContext { get; set; }
        public ServiceBase(DbContextType dbContextType)
        {
            DbContext = dbContextType;
        }
        protected virtual DbSet<EntityType> Set()
        {
            return DbContext.Set<EntityType>();
        }
        public Task<EntityType> SingleOrDefaultAsync(Expression<Func<EntityType, bool>> predicate = null)
        {
            var s = Set();
            if (predicate == null) return s.SingleOrDefaultAsync();
            return s.SingleOrDefaultAsync(predicate);
        }
        public List<EntityType> GetList(Expression<Func<EntityType, bool>> predicate = null)
        {
            var s = Set();
            if (predicate == null) return s.ToList();
            return s.Where(predicate).ToList();
        }
        public virtual EntityType Insert(EntityType entity, bool save = false)
        {
            var objectSet = Set();
            entity = objectSet.Add(entity);
            if (save) DbContext.SaveChanges();
            return entity;
        }

        public virtual void Insert(IEnumerable<EntityType> entities, bool save = false)
        {
            var objectSet = Set();

            foreach (EntityType entity in entities)
            {
                objectSet.Add(entity);
            }

            if (save) DbContext.SaveChanges();
        }
        public virtual void Update(EntityType entity, EntityType original_entity, bool save = false)
        {
            var objectSet = Set();

            if (original_entity != null)
                DbContext.Entry(objectSet.Attach(original_entity)).CurrentValues.SetValues(entity);
            else
                DbContext.Entry(objectSet.Attach(entity)).State = EntityState.Modified;

            if (save) DbContext.SaveChanges();
        }

        public virtual void Update(IEnumerable<EntityType> entities, bool save = false)
        {
            var objectSet = Set();

            foreach (EntityType entity in entities)
            {
                DbContext.Entry(objectSet.Attach(entity)).State = EntityState.Modified;
            }

            if (save) DbContext.SaveChanges();
        }
        public virtual void Delete(EntityType entity, bool save = false)
        {
            var objectSet = Set();

            objectSet.Attach(entity);
            objectSet.Remove(entity);

            if (save) DbContext.SaveChanges();
        }
        public virtual void Delete(IEnumerable<EntityType> entities, bool save = false)
        {
            var objectSet = Set();

            foreach (EntityType entity in entities)
            {
                objectSet.Attach(entity);
                objectSet.Remove(entity);
            }

            if (save) DbContext.SaveChanges();
        }
    }
}

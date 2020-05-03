package gg.bayes.challenge.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

public abstract class BaseDao< T extends Serializable> {

   private Class< T > clazz;

   @PersistenceContext
   protected EntityManager entityManager;

   public void setClazz( final Class< T > clazzToSet ){
      this.clazz = clazzToSet;
   }

   public T getById( final Long id ){
      return this.entityManager.find( this.clazz, id );
   }
   public List< T > getAll(){
      return this.entityManager
            .createQuery( "from " + this.clazz.getName() ).getResultList();
   }

   public void create( final T entity ){
      this.entityManager.persist( entity );
   }


}
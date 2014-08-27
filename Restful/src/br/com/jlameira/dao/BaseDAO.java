package br.com.jlameira.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.postgresql.util.PSQLException;

import br.com.jlameira.exception.ForeignKeyViolationException;
import br.com.jlameira.model.EntidadeBase;


public class BaseDAO<T extends EntidadeBase> {
	
	 private static final String FOREIGN_KEY_VIOLATION_SQLSTATE = "23503";

	    @PersistenceContext
	    protected EntityManager em;

	    public T findByPrimaryKey(Long key) {
	        return (T) em.find(getEntityClass(), key);
	    }

	    public T load(Long key, String... init) {

	        CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery cq = cb.createQuery(getEntityClass());
	        Root<T> emp = cq.from(getEntityClass());

	        for (String i : init) {
	            emp.fetch(i);
	        }

	        cq.where(cb.equal(emp.get("id"), key));

	        cq.select(emp);

	        TypedQuery<T> query = em.createQuery(cq);

	        return (T) query.getSingleResult();

	    }

	    public List<T> findAll() {

	        CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery cq = cb.createQuery(getEntityClass());

	        Root<T> emp = cq.from(getEntityClass());
	        cq.select(emp);
	        

	        TypedQuery<T> query = em.createQuery(cq);
	        
	        List<T> rows = query.getResultList();

	        return rows;

	    }



	    public T saveOrUpdate(T object) {

	        try {
	            if (object.getId() != null) {
	                object = em.merge(object);
	            } else {
	                em.persist(object);
	            }
	        } catch (Exception ex) {
	             Throwable t=ex;
	            while( t!=null && ! (t instanceof ConstraintViolationException))
	                t=t.getCause();

	            if(t==null)
	                throw (RuntimeException)ex;
	            ConstraintViolationException e=(ConstraintViolationException) t;

	            System.err.println("CONSTRAINT VIOLATIONS SAVING : " + object.getClass().getName());

	            for(ConstraintViolation v: e.getConstraintViolations()){
	                StringBuilder str = new StringBuilder();
	                str.append("CONSTRAINT: Classe= ").
	                        append(v.getRootBeanClass().getName().toUpperCase())
	                        .append(" - ")
	                        .append(v.getMessage().toUpperCase())
	                        .append(" / FIELD = ")
	                        .append(v.getPropertyPath().toString().toUpperCase());
	             System.err.println(str.toString());
	                System.err.println(v.getLeafBean().getClass()+"."+v.getPropertyPath()+":"+ v.getConstraintDescriptor().toString());
	            }

	            throw (RuntimeException)ex;
	        }


	        return object;

	    }

	    public void delete(T object) {
	        try {
	            em.remove(em.merge(object));
	            em.flush();
	        } catch (RuntimeException ex) {
	            Throwable root = ExceptionUtils.getRootCause(ex);
	            if(root == null)
	                root = ex;

	            if(root instanceof PSQLException) {
	                String SQLState = ((PSQLException) root).getSQLState();

	                if(SQLState != null && SQLState.equals(FOREIGN_KEY_VIOLATION_SQLSTATE)) {
	                    System.err.println("CONSTRAINT VIOLATIONS DELETING : " + object.getClass().getName());
	                    throw new ForeignKeyViolationException("exception.foreign.key.violation", object.getClass());
	                }
	            }

	            throw ex;

	        }
	    }
	    
	    
	    private Class getEntityClass() {
	        ParameterizedType t = (ParameterizedType) getClass().getGenericSuperclass();
	        return (Class) t.getActualTypeArguments()[0];
	    }

	    public T merge(T entity) {
	        try {
	            return em.merge(entity);
	        } catch (OptimisticLockException ole) {
	            throw ole;
	        }
	    }

	    public void setQueryParameters(Query query, Map<String,Object> parameters) {

	        /** seta os parâmetros na query */
	        for (String key : parameters.keySet()) {
	            query.setParameter(key, parameters.get(key));
	        }

	    }

	    public FlushModeType getFlushMode() {
	        return em.getFlushMode();
	    }

	    public void setFlushMode(FlushModeType flushType) {
	        em.setFlushMode(flushType);
	    }

	    public void flush() {
	        try {
	            em.flush();
	        } catch (OptimisticLockException ole) {
	            throw ole ;
	        }
	    }

}

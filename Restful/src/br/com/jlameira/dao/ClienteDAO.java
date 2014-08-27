package br.com.jlameira.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.jlameira.model.Cliente;
@Stateless
public class ClienteDAO extends BaseDAO<Cliente> {

	public List<Cliente> findAllClientes(){
		 StringBuilder queryBuilder = new StringBuilder(" SELECT c from Cliente c ");
		 queryBuilder.append(" order by c.nome");
		 
		  Query query = em.createQuery(queryBuilder.toString());
		  
		  List<Cliente> lista = query.getResultList();
		  
          return lista;
	}
}

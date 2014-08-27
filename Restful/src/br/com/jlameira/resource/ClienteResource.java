package br.com.jlameira.resource;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.com.jlameira.controller.ClienteFacade;
import br.com.jlameira.model.Cliente;
import br.com.jlameira.model.ClienteWs;
@Path("/cliente")
@Stateless
@Singleton
public class ClienteResource {
	
	@EJB
	private ClienteFacade clienteFacade;
	
	@GET
	@Path("/listarTodos")
	@Produces("application/json")
	public List<Cliente> listarTodosClientes(){
		 
			List<Cliente> lista = clienteFacade.buscarTodosClientes();
		
		return lista;
	}
	
	@GET
	@Path("/listarTodosWs")
	@Produces("application/json")
	public List<ClienteWs> listarTodosClientesWs(){
		 
			List<ClienteWs> lista = clienteFacade.buscarTodosClientesWs();
		
		return lista;
	}

}

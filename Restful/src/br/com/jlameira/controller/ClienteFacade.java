package br.com.jlameira.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.jlameira.dao.ClienteDAO;
import br.com.jlameira.model.Cliente;
import br.com.jlameira.model.ClienteWs;

@Stateless
@LocalBean
public class ClienteFacade {
	
	@EJB
	private ClienteDAO clienteDAO;
	
	public List<Cliente> buscarTodosClientes(){
		return clienteDAO.findAllClientes();
	}
	
	public List<ClienteWs> buscarTodosClientesWs(){
		List<Cliente> ListaCli = clienteDAO.findAllClientes();
		
		Long i = 0l;
		List<ClienteWs> listaClienteWs = new ArrayList<ClienteWs>();
		for (Cliente cliente : ListaCli) {
			ClienteWs cli = new ClienteWs();
			cli.setId(cliente.getId());
			cli.setNome(cliente.getNome());
			cli.setCpf(cliente.getCpf());
			cli.setEndereco(cliente.getEndereco());
			
			listaClienteWs.add(cli);
			i++;
		}
		
		return listaClienteWs;
		
	}

}

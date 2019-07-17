package com.mendieta.springboot.backend.apirest.models.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mendieta.springboot.backend.apirest.models.dao.IClienteDAO;
import com.mendieta.springboot.backend.apirest.models.entity.Cliente;

@Service
public class ClienteServicesImpl implements IClienteServices{

	
	@Autowired
	private IClienteDAO clienteDAO;
	@Override
	@Transactional(readOnly=true)
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return (List<Cliente>) clienteDAO.findAll();
	}

	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		// TODO Auto-generated method stub
		return clienteDAO.save(cliente);
	}
	@Override
	@Transactional(readOnly=true)
	public Cliente findById(Long id) {
		// TODO Auto-generated method stub
		return clienteDAO.findById(id).orElse(null);
	}
	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		clienteDAO.deleteById(id);
		
		
	}


	
	
}

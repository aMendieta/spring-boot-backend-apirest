package com.mendieta.springboot.backend.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mendieta.springboot.backend.apirest.models.entity.Cliente;
import com.mendieta.springboot.backend.apirest.models.services.IClienteServices;

@CrossOrigin(origins= {"http://localhost:4200"})
                       
@RestController
@RequestMapping("/api")
public class ClienteRestController {
	
	@Autowired
	private IClienteServices clienteService;

	@GetMapping("/clientes")
	public List<Cliente> index()
	{
				return clienteService.findAll();
	}
	
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		Cliente cliente = null;
		try {
			cliente = clienteService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al acceder a base de datos");
		}
		
		
		if(cliente == null) {
			response.put("mensaje", "El cliente ID:".concat(id.toString().concat(" no existe en la vase de datos")));
			return new ResponseEntity<Map>(response, HttpStatus.NOT_FOUND);
		}else {
		    return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
		}

	}
	//@RequestBody porque es un json que viene en el cuerpo con todos los datos Cliente
	@PostMapping("/clientes")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@RequestBody Cliente cliente) {
		Map<String, Object> response = new HashMap<String, Object>();
		Cliente clienteNew = null;
		try {
			clienteNew = clienteService.save(cliente);
		} catch (DataAccessException e) {
			response.put("mensaje", "Errorako " );
		}
		response.put("mensaje", "El cliente ha sido creado con éxito");
		response.put("cliente", clienteNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/clientes/{id}")
//	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@RequestBody Cliente cliente, @PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		Cliente clienteActual = clienteService.findById(id);
		
		if(clienteActual == null) {
			response.put("mensaje", "ERROR: no se pudo editar, el cliente ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map>(response, HttpStatus.NOT_FOUND);
		}
		
		clienteActual.setApellido(cliente.getApellido());
		clienteActual.setNombre(cliente.getNombre());
		clienteActual.setEmail(cliente.getEmail());
		try {
			Cliente clientaActualizado = clienteService.save(clienteActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar la base de datos");
			response.put("mensaje", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
		}
		response.put("mensaje", "El cliente ha sido actualizado con éxito");
		response.put("cliente", clienteActual);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		clienteService.delete(id);
	}
	
}

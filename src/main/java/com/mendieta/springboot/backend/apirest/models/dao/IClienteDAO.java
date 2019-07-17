package com.mendieta.springboot.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.mendieta.springboot.backend.apirest.models.entity.Cliente;

public interface IClienteDAO extends CrudRepository<Cliente, Long>{

}

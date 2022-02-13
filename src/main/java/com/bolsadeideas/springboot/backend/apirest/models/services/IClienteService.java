package com.bolsadeideas.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;

public interface IClienteService {
	//Método para obtener listado de todos los clientes
	public List<Cliente> findAll();
	//Método para obtener listado de todos los clientes usando paginación
	public Page<Cliente> findAll(Pageable pageable);
	//Método para obtener información de un cliente según Id
	public Cliente findById(Long id);
	//Método para crear nuevo cliente
	public Cliente save(Cliente cliente);
	//Método para eliminar un cliente según Id
	public void delete(Long id);
}

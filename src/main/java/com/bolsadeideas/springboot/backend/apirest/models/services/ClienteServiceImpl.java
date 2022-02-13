package com.bolsadeideas.springboot.backend.apirest.models.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.backend.apirest.models.dao.IClienteDao;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;

//Con la anotación Service se indica a Spring que es una clase de servicio
@Service
public class ClienteServiceImpl implements IClienteService {

	//Se usa Autowired para inyección de dependencias
	@Autowired
	private IClienteDao clienteDao;
	
	@Override
	//Con la anotación se indica transaccionalidad y con readOnly = true se indica que es una consulta
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		//Se llama al dao para obtener el listado de clientes
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	//Con la anotación se indica transaccionalidad y con readOnly = true se indica que es una consulta
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		//Al ser tipo Optional se usa orElse para en caso de no encontrar el registro devolver un null
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	//Con la anotación se indica transaccionalidad
	@Transactional
	public Cliente save(Cliente cliente) {
		//Se llama al Dao para guardar la entidad
		return clienteDao.save(cliente);
	}

	@Override
	//Con la anotación se indica transaccionalidad
	@Transactional
	public void delete(Long id) {
		//Se llama al Dao para eliminar el cliente según el Id
		clienteDao.deleteById(id);
	}

	@Override
	//Con la anotación se indica transaccionalidad y con readOnly = true se indica que es una consulta
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {
		//Se llama al dao para obtener el listado de clientes usando paginación
		return clienteDao.findAll(pageable);
	}

}

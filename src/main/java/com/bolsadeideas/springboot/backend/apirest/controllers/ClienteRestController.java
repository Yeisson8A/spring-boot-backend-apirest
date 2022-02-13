package com.bolsadeideas.springboot.backend.apirest.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;
import com.bolsadeideas.springboot.backend.apirest.models.services.IClienteService;

//Anotación para habilitar CORS y que la aplicación sea consumida por un cliente externo
//con origins se indican las IPs permitidas
@CrossOrigin(origins = {"http://localhost:4200"})
//Anotación para indicar que es un controlador de una Api REST
@RestController
//Anotación para indicar el endpoint de la Api
@RequestMapping("/api")
public class ClienteRestController {
	
	//Con esta anotación se usa inyección de dependencias
	@Autowired
	private IClienteService clienteService;
	
	//Se mapea el método a la Api como tipo GET
	@GetMapping("/clientes")
	public List<Cliente> index(){
		//Llamar al service para obtener listado de todos los clientes
		return clienteService.findAll();
	}
	
	//Se mapea el método a la Api como tipo GET
	//Se usa la anotación PathVariable para indicar que es una variable que viene en la Url
	@GetMapping("/clientes/page/{page}")
	public Page<Cliente> index(@PathVariable Integer page){
		//Se crea objeto Pageable indicando el número de la página a consultar y la cantidad de registros por página
		Pageable pageable = PageRequest.of(page, 4);
		//Llamar al service para obtener listado de todos los clientes usando paginación
		return clienteService.findAll(pageable);
	}
	
	//Se mapea el método a la Api como tipo GET
	//Se usa la anotación PathVariable para indicar que es una variable que viene en la Url
	//Se usa el tipo de dato de respuesta ResponseEntity para devolver errores o los datos
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Cliente cliente = null;
		//Declarar mapa para devolver el mensaje de error
		Map<String, Object> response = new HashMap<String, Object>(); 
		
		try {
			//Llamar al service para obtener un cliente según Id
			cliente = clienteService.findById(id);
		} catch (DataAccessException e) {
			//Adicionar mensaje de error
			response.put("mensaje", "Error al realizar la consulta");
			//Adicionar mensaje de la excepción
			response.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			//Se añade el mensaje de error al response a devolver
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//Validar si no se encontró el cliente especificado
		if (cliente == null) {
			//Adicionar mensaje de error
			response.put("mensaje", "El cliente ID: ".concat(id.toString()).concat(" no existe"));
			//Se añade el mensaje de error al response a devolver
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		//En caso de que el cliente exista se devuelve la información con estado OK
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}
	
	//Se mapea el método a la Api como tipo POST
	//Se usa la anotación RequestBody para indicar que el contenido viene en el body de la petición
	//Se usa el tipo de dato de respuesta ResponseEntity para devolver errores o los datos
	//Se usa la anotación Valid para indicar que antes de entrar al método se debe aplicar las validaciones
	//definidas en la entidad
	//Se inyecta el objeto BindingResult que contiene todos los mensajes de error
	@PostMapping("/clientes")
	public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) {
		Cliente clienteNew = null;
		//Declarar mapa para devolver el mensaje de error
		Map<String, Object> response = new HashMap<String, Object>();
		
		//Validar si se obtuvieron errores de las validaciones
		if (result.hasErrors()) {
			List<String> errors = new ArrayList<String>();
			
			//Recorrer listado de errores obtenidos
			for (FieldError err : result.getFieldErrors()) {
				errors.add("El campo '" + err.getField() + "' " + err.getDefaultMessage());
			}
			
			//Adicionar listado de errores
			response.put("errors", errors);
			//Se añade el mensaje de error al response a devolver
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			//Llamar al service para guardar datos del nuevo cliente
			clienteNew = clienteService.save(cliente);
		} catch (DataAccessException e) {
			//Adicionar mensaje de error
			response.put("mensaje", "Error al realizar la creación");
			//Adicionar mensaje de la excepción
			response.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			//Se añade el mensaje de error al response a devolver
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//Adicionar mensaje de éxito
		response.put("mensaje", "El cliente ha sido creado con éxito");
		//Adicionar datos del cliente creado como objeto
		response.put("cliente", clienteNew);
		//En caso de que no se presenten errores se devuelve la información con estado OK
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	//Se mapea el método a la Api como tipo PUT
	//Se usa la anotación RequestBody para indicar que el contenido viene en el body de la petición
	//Se usa la anotación PathVariable para indicar que es una variable que viene en la Url
	//Se usa el tipo de dato de respuesta ResponseEntity para devolver errores o los datos
	//Se usa la anotación Valid para indicar que antes de entrar al método se debe aplicar las validaciones
	//definidas en la entidad
	//Se inyecta el objeto BindingResult que contiene todos los mensajes de error
	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id) {
		//Obtener información del cliente actual
		Cliente clienteActual = clienteService.findById(id);
		Cliente clienteUpdated = null;
		//Declarar mapa para devolver el mensaje de error
		Map<String, Object> response = new HashMap<String, Object>();
		
		//Validar si se obtuvieron errores de las validaciones
		if (result.hasErrors()) {
			//Recorrer listado de mensajes obtenido y guardar en lista tipo texto
			List<String> errors = result.getFieldErrors()
									.stream()
									.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
									.collect(Collectors.toList());
			//Adicionar listado de errores
			response.put("errors", errors);
			//Se añade el mensaje de error al response a devolver
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		//Validar si no se encontró el cliente especificado
		if (cliente == null) {
			//Adicionar mensaje de error
			response.put("mensaje", "El cliente ID: ".concat(id.toString()).concat(" no existe"));
			//Se añade el mensaje de error al response a devolver
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			//Asignar datos a actualizar para el cliente
			clienteActual.setApellido(cliente.getApellido());
			clienteActual.setEmail(cliente.getEmail());
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setCreatedAt(cliente.getCreatedAt());
			
			//Llamar al service para guardar datos del cliente actualizado
			clienteUpdated = clienteService.save(clienteActual);
		} catch (DataAccessException e) {
			//Adicionar mensaje de error
			response.put("mensaje", "Error al realizar la actualización");
			//Adicionar mensaje de la excepción
			response.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			//Se añade el mensaje de error al response a devolver
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//Adicionar mensaje de éxito
		response.put("mensaje", "El cliente ha sido actualizado con éxito");
		//Adicionar datos del cliente creado como objeto
		response.put("cliente", clienteUpdated);
		//En caso de que no se presenten errores se devuelve la información con estado OK
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	//se mapea el método a la Api como tipo DELETE
	//Se usa la anotación PathVariable para indicar que es una variable que viene en la Url
	//Se usa el tipo de dato de respuesta ResponseEntity para devolver errores o los datos
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		//Declarar mapa para devolver el mensaje de error
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			//Llamar al service para eliminar el cliente según Id
			clienteService.delete(id);
		} catch (DataAccessException e) {
			//Adicionar mensaje de error
			response.put("mensaje", "Error al realizar la eliminación");
			//Adicionar mensaje de la excepción
			response.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			//Se añade el mensaje de error al response a devolver
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//Adicionar mensaje de éxito
		response.put("mensaje", "El cliente ha sido eliminado con éxito");
		//En caso de que no se presenten errores se devuelve la información con estado OK
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}

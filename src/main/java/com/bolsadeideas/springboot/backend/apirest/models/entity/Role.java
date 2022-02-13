package com.bolsadeideas.springboot.backend.apirest.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//Anotación para indicar que la clase es una entidad de base de datos y se debe persistir
@Entity
//Anotación para indicar el nombre de la tabla en la base de datos
@Table(name = "roles")
public class Role implements Serializable {
	
	//Anotación para indicar que dicho campo es la clave primaria de la tabla
	@Id
	//Decorador para indicar como se va a generar dicho Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//Decorador para indicar que el valor del campo Nombre es único y tiene una longitud de 20 caracteres
	@Column(unique = true, length = 20)
	private String nombre;
	
	private static final long serialVersionUID = 1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}

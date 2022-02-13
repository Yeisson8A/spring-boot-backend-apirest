package com.bolsadeideas.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

//Decorador para indicar que es una entidad
@Entity
//Decorador para indicar el nombre de la tabla
@Table(name = "clientes")
public class Cliente implements Serializable {

	//Decorador para indicar dicho campo es la clave primaria de la tabla
	@Id
	//Decorador para indicar como se va a generar dicho Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//Decorador para indicar que el campo no puede estar vacío
	@NotEmpty(message = "no puede estar vacío")
	//Decorador para indicar la longitud minima y máxima del contenido del campo
	@Size(min = 4, max = 12, message = "el tamaño tiene que estar entre 4 y 12")
	//Decorador para indicar que dicho campo no acepta nulos
	@Column(nullable = false)
	private String nombre;
	
	//Decorador para indicar que el campo no puede estar vacío
	@NotEmpty(message = "no puede estar vacío")
	private String apellido;
	
	//Decorador para indicar que el campo no puede estar vacío
	@NotEmpty(message = "no puede estar vacío")
	//Decorador para indicar que el contenido del campo debe tener formato valido de correo
	@Email(message = "no es una dirección de correo válida")
	//Decorador para indicar que dicho campo no acepta nulos y su valor debe ser único
	@Column(nullable = false, unique = true)
	private String email;
	
	//Decorador para indicar el nombre de la columna de la tabla
	//Nota: cuando el nombre del atributo se llama igual al campo de la tabla se puede obviar la anotación
	@Column(name = "create_at")
	//Decorador para indicar que se va a convertir del tipo Date Java a Date de SQL
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	
	//Anotación para ejecutar operaciones antes de persistir la entidad en la base de datos
	@PrePersist
	public void prePersist() {
		//Asignar como fecha de creación la fecha actual
		createdAt = new Date();
	}
	
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
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	private static final long serialVersionUID = 1L;
}

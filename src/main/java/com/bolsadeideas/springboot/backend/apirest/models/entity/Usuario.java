package com.bolsadeideas.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

//Anotación para indicar que la clase es una entidad de base de datos y se debe persistir
@Entity
//Anotación para indicar el nombre de la tabla en la base de datos
@Table(name = "usuarios")
public class Usuario implements Serializable {

	//Anotación para indicar que dicho campo es la clave primaria de la tabla
	@Id
	//Decorador para indicar como se va a generar dicho Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//Decorador para indicar que el valor del campo Username es único y tiene una longitud de 20 caracteres
	@Column(unique = true, length = 20)
	private String username;
	
	//Decorador para indicar que el valor del campo Password tiene una longitud de 60 caracteres
	@Column(length = 60)
	private String password;
	private Boolean enabled;
	
	//Decorador para indicar que la relación entre las tablas Usuario y Role es de muchos a muchos
	//Se usa Cascade All para que en caso de que se elimine el usuario se eliminen igualmente todos los roles asociados
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//Decorador para indicar el nombre de la tabla intermedia que relaciona Usuario y Role,
	//así como los nombres de las llaves foraneas
	//Se usa UniqueConstraints para indicar que la llave Usuario_Id y Role_Id son únicas
	@JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "usuario_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"),
				uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id", "role_id"})})
	private List<Role> roles;
	
	private static final long serialVersionUID = 1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}

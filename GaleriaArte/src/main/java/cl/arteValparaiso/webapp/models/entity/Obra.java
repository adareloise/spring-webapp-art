package cl.arteValparaiso.webapp.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "obras")
public class Obra implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String dimension;
	private String tecnica;
	private String disponibilidad;
	private String posicion;
	private String foto;

	public Obra() {
	}

	public Obra(Long id, String titulo, String dimension, String tecnica, String disponibilidad,String posicion, String foto) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.dimension = dimension;
		this.tecnica = tecnica;
		this.disponibilidad = disponibilidad;
		this.setPosicion(posicion);
		this.foto = foto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getTecnica() {
		return tecnica;
	}

	public void setTecnica(String tecnica) {
		this.tecnica = tecnica;
	}

	public String getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(String disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto (String foto) {
		this.foto = foto;
	}

	public String getPosicion() {
		return posicion;
	}

	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}
}
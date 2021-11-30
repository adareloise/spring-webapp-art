package cl.arteValparaiso.webapp.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "news")
public class Noticia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String fecha;
	private String introduccion;
	@Lob
	private String redaccion;
	private String conclusion;
	private String link_video;
	private String foto;
	
	public Noticia () {
	}

	public Noticia(Long id, String titulo, String fecha, String introduccion, String redaccion, String conclusion,
			String link_video, String foto) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.fecha = fecha;
		this.introduccion = introduccion;
		this.redaccion = redaccion;
		this.conclusion = conclusion;
		this.link_video = link_video;
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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getIntroduccion() {
		return introduccion;
	}

	public void setIntroduccion(String introduccion) {
		this.introduccion = introduccion;
	}

	public String getRedaccion() {
		return redaccion;
	}

	public void setRedaccion(String redaccion) {
		this.redaccion = redaccion;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public String getLink_video() {
		return link_video;
	}

	public void setLink_video(String link_video) {
		this.link_video = link_video;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
}
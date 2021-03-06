package org.ucla.sigma.modelo;

// Generated 04/06/2012 02:14:15 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Publicacion generated by hbm2java
 */
@Entity
@Table(name = "publicacion", schema = "public",uniqueConstraints = @UniqueConstraint(columnNames = "uri"))
public class Publicacion implements java.io.Serializable {

	private int id;
	private Imagen imagen;
	private Usuario usuario;
	private TipoPublicacion tipoPublicacion;
	private GeneroPublicacion generoPublicacion;
	private String titulo;
	private String uri;
	private String texto;
	private Date creacion;
	private char estatus = 'A';
	private Set<BoletinInformativo> boletinInformativos = new HashSet<BoletinInformativo>(
			0);
	private Set<Adjunto> adjuntos = new HashSet<Adjunto>(0);

	public Publicacion() {
	}

	public Publicacion(int id, Usuario usuario,
			TipoPublicacion tipoPublicacion,
			GeneroPublicacion generoPublicacion, String titulo, String uri,
			String texto, Date creacion, char estatus) {
		this.id = id;
		this.usuario = usuario;
		this.tipoPublicacion = tipoPublicacion;
		this.generoPublicacion = generoPublicacion;
		this.titulo = titulo;
		this.uri = uri;
		this.texto = texto;
		this.creacion = creacion;
		this.estatus = estatus;
	}

	public Publicacion(int id, Imagen imagen, Usuario usuario,
			TipoPublicacion tipoPublicacion,
			GeneroPublicacion generoPublicacion, String titulo, String uri,
			String texto, Date creacion, char estatus,
			Set<BoletinInformativo> boletinInformativos, Set<Adjunto> adjuntos) {
		this.id = id;
		this.imagen = imagen;
		this.usuario = usuario;
		this.tipoPublicacion = tipoPublicacion;
		this.generoPublicacion = generoPublicacion;
		this.titulo = titulo;
		this.uri = uri;
		this.texto = texto;
		this.creacion = creacion;
		this.estatus = estatus;
		this.boletinInformativos = boletinInformativos;
		this.adjuntos = adjuntos;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PUBLICACION", sequenceName = "seq_publicacion", allocationSize = 1, schema = "public")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PUBLICACION")
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "imagenid")
	public Imagen getImagen() {
		return this.imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuariocedula", nullable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tipo_publicacionid", nullable = false)
	public TipoPublicacion getTipoPublicacion() {
		return this.tipoPublicacion;
	}

	public void setTipoPublicacion(TipoPublicacion tipoPublicacion) {
		this.tipoPublicacion = tipoPublicacion;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "genero_publicacionid", nullable = false)
	public GeneroPublicacion getGeneroPublicacion() {
		return this.generoPublicacion;
	}

	public void setGeneroPublicacion(GeneroPublicacion generoPublicacion) {
		this.generoPublicacion = generoPublicacion;
	}

	@Column(name = "titulo", nullable = false)
	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	@Column(name = "uri", unique = true, nullable = false)
	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Column(name = "texto", nullable = false, columnDefinition = "text")
	public String getTexto() {
		return this.texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "creacion", nullable = false, length = 13)
	public Date getCreacion() {
		return this.creacion;
	}

	public void setCreacion(Date creacion) {
		this.creacion = creacion;
	}

	@Column(name = "estatus", nullable = false, length = 1)
	public char getEstatus() {
		return this.estatus;
	}

	public void setEstatus(char estatus) {
		this.estatus = estatus;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "boletin_informativo_publicacion", schema = "public", joinColumns = { @JoinColumn(name = "publicacionid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "boletin_informativoid", nullable = false, updatable = false) })
	public Set<BoletinInformativo> getBoletinInformativos() {
		return this.boletinInformativos;
	}

	public void setBoletinInformativos(
			Set<BoletinInformativo> boletinInformativos) {
		this.boletinInformativos = boletinInformativos;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "publicacion_adjunto", schema = "public", joinColumns = { @JoinColumn(name = "publicacionid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "adjuntoid", nullable = false, updatable = false) })
	@Fetch(FetchMode.SUBSELECT)
	public Set<Adjunto> getAdjuntos() {
		return this.adjuntos;
	}

	public void setAdjuntos(Set<Adjunto> adjuntos) {
		this.adjuntos = adjuntos;
	}

	@Override
	public boolean equals(Object obj) {
		Publicacion comparacion = (Publicacion) obj;
		return comparacion.getId() == id;
	}

	@Override
	public String toString() {
		return "Modelo Publicacion, id: " + id;
	}

}

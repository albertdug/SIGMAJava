package org.ucla.sigma.modelo;

// Generated 15/07/2012 02:32:55 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Historial generated by hbm2java
 */
@Entity
@Table(name = "historial", schema = "public")
public class Historial implements java.io.Serializable {

	private int id;
	private Paciente paciente;
	private Veterinario veterinario;
	private TipoServicio tipoServicio;
	private Date fecha;
	private Date hora;
	private char estatus;
	private Dermatologia dermatologia;
	private Oftalmologia oftalmologia;
	private Cirugia cirugia;
	private ConsultaGeneral consultaGeneral;
	private Set<Sintoma> sintomas = new HashSet<Sintoma>(0);
	private Neurologia neurologia;
	private Defuncion defuncion;
	private Set<Patologia> patologias = new HashSet<Patologia>(0);
	private Set<Tratamiento> tratamientos = new HashSet<Tratamiento>(0);
	private Set<Servicio> servicios = new HashSet<Servicio>(0);
	private PostOperatorio postOperatorio;
	private Laboratorio laboratorio;
	private Traumatologia traumatologia;
	private Cardiologia cardiologia;
	private FichaMedica fichaMedica;
	private Imagenologia imagenologia;
	private PreOperatorio preOperatorio;
	private Set<Referencia> referencias = new HashSet<Referencia>(0);

	public Historial() {
	}

	public Historial(int id, Paciente paciente, Veterinario veterinario,
			TipoServicio tipoServicio, Date fecha, Date hora, char estatus) {
		this.id = id;
		this.paciente = paciente;
		this.veterinario = veterinario;
		this.tipoServicio = tipoServicio;
		this.fecha = fecha;
		this.hora = hora;
		this.estatus = estatus;
	}

	public Historial(int id, Paciente paciente, Veterinario veterinario,
			TipoServicio tipoServicio, Date fecha, Date hora, char estatus,
			Dermatologia dermatologia, Oftalmologia oftalmologia,
			Cirugia cirugia, ConsultaGeneral consultaGeneral,
			Set<Sintoma> sintomas, Neurologia neurologia, Defuncion defuncion,
			Set<Patologia> patologias, Set<Tratamiento> tratamientos,
			Set<Servicio> servicios, PostOperatorio postOperatorio,
			Laboratorio laboratorio, Traumatologia traumatologia,
			Cardiologia cardiologia, FichaMedica fichaMedica,
			Imagenologia imagenologia, PreOperatorio preOperatorio,
			Set<Referencia> referencias) {
		this.id = id;
		this.paciente = paciente;
		this.veterinario = veterinario;
		this.tipoServicio = tipoServicio;
		this.fecha = fecha;
		this.hora = hora;
		this.estatus = estatus;
		this.dermatologia = dermatologia;
		this.oftalmologia = oftalmologia;
		this.cirugia = cirugia;
		this.consultaGeneral = consultaGeneral;
		this.sintomas = sintomas;
		this.neurologia = neurologia;
		this.defuncion = defuncion;
		this.patologias = patologias;
		this.tratamientos = tratamientos;
		this.servicios = servicios;
		this.postOperatorio = postOperatorio;
		this.laboratorio = laboratorio;
		this.traumatologia = traumatologia;
		this.cardiologia = cardiologia;
		this.fichaMedica = fichaMedica;
		this.imagenologia = imagenologia;
		this.preOperatorio = preOperatorio;
		this.referencias = referencias;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pacientehistoria_medica", nullable = false)
	public Paciente getPaciente() {
		return this.paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "veterinariocedula", nullable = false)
	public Veterinario getVeterinario() {
		return this.veterinario;
	}

	public void setVeterinario(Veterinario veterinario) {
		this.veterinario = veterinario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_servicioid", nullable = false)
	public TipoServicio getTipoServicio() {
		return this.tipoServicio;
	}

	public void setTipoServicio(TipoServicio tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha", nullable = false, length = 13)
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "hora", nullable = false, length = 15)
	public Date getHora() {
		return this.hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	@Column(name = "estatus", nullable = false, length = 1)
	public char getEstatus() {
		return this.estatus;
	}

	public void setEstatus(char estatus) {
		this.estatus = estatus;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "historial")
	public Dermatologia getDermatologia() {
		return this.dermatologia;
	}

	public void setDermatologia(Dermatologia dermatologia) {
		this.dermatologia = dermatologia;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "historial")
	public Oftalmologia getOftalmologia() {
		return this.oftalmologia;
	}

	public void setOftalmologia(Oftalmologia oftalmologia) {
		this.oftalmologia = oftalmologia;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "historial")
	public Cirugia getCirugia() {
		return this.cirugia;
	}

	public void setCirugia(Cirugia cirugia) {
		this.cirugia = cirugia;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "historial")
	public ConsultaGeneral getConsultaGeneral() {
		return this.consultaGeneral;
	}

	public void setConsultaGeneral(ConsultaGeneral consultaGeneral) {
		this.consultaGeneral = consultaGeneral;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "historial_sintoma", schema = "public", joinColumns = { @JoinColumn(name = "historialid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "sintomaid", nullable = false, updatable = false) })
	public Set<Sintoma> getSintomas() {
		return this.sintomas;
	}

	public void setSintomas(Set<Sintoma> sintomas) {
		this.sintomas = sintomas;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "historial")
	public Neurologia getNeurologia() {
		return this.neurologia;
	}

	public void setNeurologia(Neurologia neurologia) {
		this.neurologia = neurologia;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "historial")
	public Defuncion getDefuncion() {
		return this.defuncion;
	}

	public void setDefuncion(Defuncion defuncion) {
		this.defuncion = defuncion;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "historial_patologia", schema = "public", joinColumns = { @JoinColumn(name = "historialid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "patologiaid", nullable = false, updatable = false) })
	public Set<Patologia> getPatologias() {
		return this.patologias;
	}

	public void setPatologias(Set<Patologia> patologias) {
		this.patologias = patologias;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "historial_tratamiento", schema = "public", joinColumns = { @JoinColumn(name = "historialid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "tratamientoid", nullable = false, updatable = false) })
	public Set<Tratamiento> getTratamientos() {
		return this.tratamientos;
	}

	public void setTratamientos(Set<Tratamiento> tratamientos) {
		this.tratamientos = tratamientos;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "historial_servicio", schema = "public", joinColumns = { @JoinColumn(name = "historialid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "servicioid", nullable = false, updatable = false) })
	public Set<Servicio> getServicios() {
		return this.servicios;
	}

	public void setServicios(Set<Servicio> servicios) {
		this.servicios = servicios;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "historial")
	public PostOperatorio getPostOperatorio() {
		return this.postOperatorio;
	}

	public void setPostOperatorio(PostOperatorio postOperatorio) {
		this.postOperatorio = postOperatorio;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "historial")
	public Laboratorio getLaboratorio() {
		return this.laboratorio;
	}

	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "historial")
	public Traumatologia getTraumatologia() {
		return this.traumatologia;
	}

	public void setTraumatologia(Traumatologia traumatologia) {
		this.traumatologia = traumatologia;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "historial")
	public Cardiologia getCardiologia() {
		return this.cardiologia;
	}

	public void setCardiologia(Cardiologia cardiologia) {
		this.cardiologia = cardiologia;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "historial")
	public FichaMedica getFichaMedica() {
		return this.fichaMedica;
	}

	public void setFichaMedica(FichaMedica fichaMedica) {
		this.fichaMedica = fichaMedica;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "historial")
	public Imagenologia getImagenologia() {
		return this.imagenologia;
	}

	public void setImagenologia(Imagenologia imagenologia) {
		this.imagenologia = imagenologia;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "historial")
	public PreOperatorio getPreOperatorio() {
		return this.preOperatorio;
	}

	public void setPreOperatorio(PreOperatorio preOperatorio) {
		this.preOperatorio = preOperatorio;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historial")
	public Set<Referencia> getReferencias() {
		return this.referencias;
	}

	public void setReferencias(Set<Referencia> referencias) {
		this.referencias = referencias;
	}

}
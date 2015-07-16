package org.ucla.sigma.modelo;

// Generated 15/07/2012 02:32:55 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * ConsultaGeneral generated by hbm2java
 */
@Entity
@Table(name = "consulta_general", schema = "public")
public class ConsultaGeneral implements java.io.Serializable {

	private int id;
	private Historial historial;

	public ConsultaGeneral() {
	}

	public ConsultaGeneral(Historial historial) {
		this.historial = historial;
	}

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "historial"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public Historial getHistorial() {
		return this.historial;
	}

	public void setHistorial(Historial historial) {
		this.historial = historial;
	}

}

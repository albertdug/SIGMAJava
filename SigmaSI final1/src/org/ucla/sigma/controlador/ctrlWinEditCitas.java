/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.soap.Text;

import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.components.HelperDateAge;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.ConsultaGeneral;
import org.ucla.sigma.modelo.DiaDeAtencion;
import org.ucla.sigma.modelo.EspImagenologia;
import org.ucla.sigma.modelo.Historial;
import org.ucla.sigma.modelo.Paciente;
import org.ucla.sigma.modelo.Referencia;
import org.ucla.sigma.modelo.ReferenciaConsultaEspecializada;
import org.ucla.sigma.modelo.ReferenciaImagenologia;
import org.ucla.sigma.modelo.TipoImagenologia;
import org.ucla.sigma.modelo.TipoServicio;
import org.ucla.sigma.servicio.ServicioCitas;
import org.ucla.sigma.servicio.ServicioDiaDeAtencion;
import org.ucla.sigma.servicio.ServicioEspImagenologia;
import org.ucla.sigma.servicio.ServicioReferencia;
import org.ucla.sigma.servicio.ServicioReferenciaConsultaEspecializada;
import org.ucla.sigma.servicio.ServicioTipoImagenologia;
import org.ucla.sigma.servicio.ServicioTipoServicio;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author lis
 * 
 */
public class ctrlWinEditCitas extends GenericForwardComposer {

	private Window winEditCitas;
	private Button btnCancelar;
	private Button btnGuardar;
	private Listbox listServicios;
	private Textbox txtEdad;
	private Datebox dbFechaCita;

	// ----------------------------------------------------------------------------------------------------

	private ServicioReferencia servicioReferencia;
	private ctrlWinReferencia ctrlWinReferencia;
	private ctrlWinCitasSecretaria ctrlWinCitasSecretaria;
	private ctrlWinEditCitas ctrlWinEditCitas;
	private Paciente paciente;
	private Historial historial;
	private int conteo;
	private DiaDeAtencion diaAtencion;
	private ServicioDiaDeAtencion servicioDiaDeAtencion;
	// ----------------------------------------------------------------------------------------------------

	private Referencia referencia;

	public ctrlWinCitasSecretaria getCtrlWinCitasSecretaria() {
		return ctrlWinCitasSecretaria;
	}

	public void setCtrlWinCitasSecretaria(
			ctrlWinCitasSecretaria ctrlWinCitasSecretaria) {
		this.ctrlWinCitasSecretaria = ctrlWinCitasSecretaria;
	}

	public DiaDeAtencion getDiaAtencion() {
		return diaAtencion;
	}

	public void setDiaAtencion(DiaDeAtencion diaAtencion) {
		this.diaAtencion = diaAtencion;
	}

	public int getConteo() {
		return conteo;
	}

	public void setConteo(int conteo) {
		this.conteo = conteo;
	}

	public ServicioDiaDeAtencion getServicioDiaDeAtencion() {
		return servicioDiaDeAtencion;
	}

	public void setServicioDiaDeAtencion(
			ServicioDiaDeAtencion servicioDiaDeAtencion) {
		this.servicioDiaDeAtencion = servicioDiaDeAtencion;
	}

	public ctrlWinReferencia getCtrlWinReferencia() {
		return ctrlWinReferencia;
	}

	public void setCtrlWinReferencia(ctrlWinReferencia ctrlWinReferencia) {
		this.ctrlWinReferencia = ctrlWinReferencia;
	}

	public Window getWinEditCitas() {
		return winEditCitas;
	}

	public void setWinEditCitas(Window winEditCitas) {
		this.winEditCitas = winEditCitas;
	}

	public Datebox getDbFechaCita() {
		return dbFechaCita;
	}

	public void setDbFechaCita(Datebox dbFechaCita) {
		this.dbFechaCita = dbFechaCita;
	}

	public ServicioReferencia getServicioReferencia() {
		return servicioReferencia;
	}

	public void setServicioReferencia(ServicioReferencia servicioReferencia) {
		this.servicioReferencia = servicioReferencia;
	}

	public ctrlWinEditCitas getCtrlWinEditCitas() {
		return ctrlWinEditCitas;
	}

	public void setCtrlWinEditCitas(ctrlWinEditCitas ctrlWinEditCitas) {
		this.ctrlWinEditCitas = ctrlWinEditCitas;
	}

	public Historial getHistorial() {
		return historial;
	}

	public void setHistorial(Historial historial) {
		this.historial = historial;
	}

	public Referencia getReferencia() {
		return referencia;
	}

	public void setReferencia(Referencia referencia) {
		this.referencia = referencia;
	}

	public Textbox getTxtEdad() {
		return txtEdad;
	}

	public void setTxtEdad(Textbox txtEdad) {
		this.txtEdad = txtEdad;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Button getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(Button btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public Button getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(Button btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public Listbox getListServicios() {
		return listServicios;
	}

	public void setListServicios(Listbox listServicios) {
		this.listServicios = listServicios;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditCitas.setAttribute(comp.getId() + "ctrl", this);
		referencia = (Referencia) arg.get("objeto");
		if (arg.get("ctrlWinReferencia") != null) {
			ctrlWinReferencia = (ctrlWinReferencia) arg
					.get("ctrlWinReferencia");

		} else if( arg.get("ctrlWinCitasSecretaria") != null) {
			ctrlWinCitasSecretaria = (ctrlWinCitasSecretaria) arg
					.get("ctrlWinCitasSecretaria");

		}
		servicioReferencia = (ServicioReferencia) SpringUtil
				.getBean("beanServicioReferencia");
		servicioDiaDeAtencion = (ServicioDiaDeAtencion) SpringUtil
				.getBean("beanServicioDiaDeAtencion");
		txtEdad.setValue(HelperDateAge.age(referencia.getHistorial()
				.getPaciente().getFechaNac(), ' '));
	}

	public void onClick$btnCancelar() {
		if (arg.get("ctrlWinReferencia") != null) {
			ctrlWinReferencia.recargar();

		} else if(arg.get("ctrlWinCitasSecretaria") != null) {
			ctrlWinCitasSecretaria.recargar();

		}
		this.winEditCitas.detach();
	}

	public void onChange$dbFechaCita() {
		conteo = servicioReferencia.countDisponibilidad(dbFechaCita.getValue()
				.toString(), referencia.getTipoServicio().getId());
		System.out.println(conteo);
		diaAtencion = servicioDiaDeAtencion.buscarUno(referencia
				.getTipoServicio().getId(), 'A');
		System.out.println(diaAtencion.getMaxCitas());
		if (conteo >= diaAtencion.getMaxCitas()) {
			MensajeEmergente.mostrar("MAXDATES");
			dbFechaCita.setValue(null);
		}
	}

	public void onClick$btnGuardar() {
		if (dbFechaCita.getValue() == null) {
			MensajeEmergente.mostrar("NOEMPTY", "\nFecha de la cita",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							dbFechaCita.setFocus(true);
						}
					});
		} else {
			referencia.setFechaCita(dbFechaCita.getValue());
			referencia.setHoraCita(dbFechaCita.getValue());
			servicioReferencia.guardarReferencia(referencia);
			MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
				@Override
				public void alAceptar() {
					if (arg.get("ctrlWinReferencia") != null) {
						ctrlWinReferencia.recargar();

					} else if(arg.get("ctrlWinCitasSecretaria") != null) {
						ctrlWinCitasSecretaria.recargar();

					}
					winEditCitas.detach();
				}
			});

		}
	}
	public void onClose$winEditCitas() {
		if (arg.get("ctrlWinReferencia") != null) {
			ctrlWinReferencia.recargar();

		} else if(arg.get("ctrlWinCitasSecretaria") != null) {
			ctrlWinCitasSecretaria.recargar();

		}
	}
}

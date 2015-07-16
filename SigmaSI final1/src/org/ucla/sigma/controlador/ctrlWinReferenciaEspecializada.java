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
import org.ucla.sigma.modelo.EspImagenologia;
import org.ucla.sigma.modelo.Historial;
import org.ucla.sigma.modelo.Paciente;
import org.ucla.sigma.modelo.ReferenciaConsultaEspecializada;
import org.ucla.sigma.modelo.ReferenciaImagenologia;
import org.ucla.sigma.modelo.TipoImagenologia;
import org.ucla.sigma.modelo.TipoReferencia;
import org.ucla.sigma.modelo.TipoServicio;
import org.ucla.sigma.servicio.ServicioEspImagenologia;
import org.ucla.sigma.servicio.ServicioReferenciaConsultaEspecializada;
import org.ucla.sigma.servicio.ServicioTipoImagenologia;
import org.ucla.sigma.servicio.ServicioTipoReferencia;
import org.ucla.sigma.servicio.ServicioTipoServicio;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author lis
 *
 */
public class ctrlWinReferenciaEspecializada extends GenericForwardComposer {

	private Window winReferenciaEspecializada;
	private Button btnCancelar;
	private Button btnGuardar;
	private Listbox listServicios;
	private Textbox txtEdad;
	private Datebox dbFechaActual;

	// ----------------------------------------------------------------------------------------------------
	
	private ServicioTipoServicio servicioTipoServicio;
	private ServicioTipoReferencia servicioTipoReferencia;
	private TipoReferencia tipoReferencia;
	private ServicioReferenciaConsultaEspecializada servicioReferenciaConsultaEspecializada;
	private ctrlWinReferenciaEspecializada ctrlWinReferenciaEspecializada;
	private Paciente paciente;
	private Historial historial;

	// ----------------------------------------------------------------------------------------------------
	
	private List<TipoServicio> tipoServicios = new ArrayList<TipoServicio>();
	private ReferenciaConsultaEspecializada referenciaConsultaEspecializada;
	
	public Datebox getDbFechaActual() {
		return dbFechaActual;
	}
	public void setDbFechaActual(Datebox dbFechaActual) {
		this.dbFechaActual = dbFechaActual;
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
	public Window getWinReferenciaEspecializada() {
		return winReferenciaEspecializada;
	}
	public void setWinReferenciaEspecializada(Window winReferenciaEspecializada) {
		this.winReferenciaEspecializada = winReferenciaEspecializada;
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
	public ServicioTipoServicio getServicioTipoServicio() {
		return servicioTipoServicio;
	}
	public void setServicioTipoServicio(ServicioTipoServicio servicioTipoServicio) {
		this.servicioTipoServicio = servicioTipoServicio;
	}
	public ctrlWinReferenciaEspecializada getCtrlWinReferenciaEspecializada() {
		return ctrlWinReferenciaEspecializada;
	}
	public void setCtrlWinReferenciaEspecializada(
			ctrlWinReferenciaEspecializada ctrlWinReferenciaEspecializada) {
		this.ctrlWinReferenciaEspecializada = ctrlWinReferenciaEspecializada;
	}
	public List<TipoServicio> getTipoServicios() {
		return tipoServicios;
	}
	public void setTipoServicios(List<TipoServicio> tipoServicios) {
		this.tipoServicios = tipoServicios;
	}
	public ReferenciaConsultaEspecializada getReferenciaConsultaEspecializada() {
		return referenciaConsultaEspecializada;
	}
	public void setReferenciaConsultaEspecializada(
			ReferenciaConsultaEspecializada referenciaConsultaEspecializada) {
		this.referenciaConsultaEspecializada = referenciaConsultaEspecializada;
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		paciente = (Paciente) arg.get("paciente");
		historial = (Historial) arg.get("historial");
		winReferenciaEspecializada.setAttribute(comp.getId() + "ctrl", this);
		servicioTipoServicio = (ServicioTipoServicio) SpringUtil
				.getBean("beanServicioTipoServicio");
		servicioTipoReferencia = (ServicioTipoReferencia) SpringUtil
				.getBean("beanServicioTipoReferencia");
		servicioReferenciaConsultaEspecializada = (ServicioReferenciaConsultaEspecializada) SpringUtil.getBean("beanServicioReferenciaConsultaEspecializada");
		referenciaConsultaEspecializada = new ReferenciaConsultaEspecializada();
		tipoReferencia = servicioTipoReferencia.buscarUno("RCE");
		tipoServicios = new ArrayList<TipoServicio>(tipoReferencia.getTipoServicios());
		
		txtEdad.setValue(HelperDateAge.age(paciente.getFechaNac(), ' '));
		dbFechaActual.setValue(HelperDate.now());
	}
	
	public void onClick$btnCancelar() {
		this.winReferenciaEspecializada.detach();
	}
	
	public void onClick$btnGuardar(){
		if (listServicios.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\nServicio",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listServicios.setFocus(true);
						}
					});
		} else {
			referenciaConsultaEspecializada.setFechaExpedicion(dbFechaActual.getValue());
			referenciaConsultaEspecializada.setHistorial(historial);
			referenciaConsultaEspecializada.setTipoServicio((TipoServicio) listServicios.getSelectedItem().getValue());
			servicioReferenciaConsultaEspecializada.guardarReferenciaConsultaEspecializada(referenciaConsultaEspecializada);
			MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
				@Override
				public void alAceptar() {
					winReferenciaEspecializada.detach();
				}
			});

		}
	}
}

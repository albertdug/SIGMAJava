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
import org.ucla.sigma.modelo.ReferenciaCirugia;
import org.ucla.sigma.modelo.ReferenciaConsultaEspecializada;
import org.ucla.sigma.modelo.ReferenciaImagenologia;
import org.ucla.sigma.modelo.TipoImagenologia;
import org.ucla.sigma.modelo.TipoReferencia;
import org.ucla.sigma.modelo.TipoServicio;
import org.ucla.sigma.servicio.ServicioEspImagenologia;
import org.ucla.sigma.servicio.ServicioReferenciaCirugia;
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
public class ctrlWinReferenciaCirugia extends GenericForwardComposer {

	private Window winReferenciaCirugia;
	private Button btnCancelar;
	private Button btnGuardar;
	private Listbox listServicios;
	private Textbox txtEdad;
	private Textbox txtComentarios;
	private Datebox dbFechaActual;

	// ----------------------------------------------------------------------------------------------------
	
	private ServicioTipoServicio servicioTipoServicio;
	private ServicioTipoReferencia servicioTipoReferencia;
	private TipoReferencia tipoReferencia;
	private TipoServicio tipoServicio;
	
	public Textbox getTxtComentarios() {
		return txtComentarios;
	}
	public void setTxtComentarios(Textbox txtComentarios) {
		this.txtComentarios = txtComentarios;
	}

	private ServicioReferenciaCirugia servicioReferenciaCirugia;
	private ctrlWinReferenciaCirugia ctrlWinReferenciaCirugia;
	private Paciente paciente;
	private Historial historial;

	// ----------------------------------------------------------------------------------------------------
	
	private List<TipoServicio> tipoServicios = new ArrayList<TipoServicio>();
	private ReferenciaCirugia referenciaCirugia;
	
	public Window getWinReferenciaCirugia() {
		return winReferenciaCirugia;
	}
	public void setWinReferenciaCirugia(Window winReferenciaCirugia) {
		this.winReferenciaCirugia = winReferenciaCirugia;
	}
	public ServicioTipoReferencia getServicioTipoReferencia() {
		return servicioTipoReferencia;
	}
	public void setServicioTipoReferencia(
			ServicioTipoReferencia servicioTipoReferencia) {
		this.servicioTipoReferencia = servicioTipoReferencia;
	}
	public TipoReferencia getTipoReferencia() {
		return tipoReferencia;
	}
	public void setTipoReferencia(TipoReferencia tipoReferencia) {
		this.tipoReferencia = tipoReferencia;
	}
	public ServicioReferenciaCirugia getServicioReferenciaCirugia() {
		return servicioReferenciaCirugia;
	}
	public void setServicioReferenciaCirugia(
			ServicioReferenciaCirugia servicioReferenciaCirugia) {
		this.servicioReferenciaCirugia = servicioReferenciaCirugia;
	}
	public ctrlWinReferenciaCirugia getCtrlWinReferenciaCirugia() {
		return ctrlWinReferenciaCirugia;
	}
	public void setCtrlWinReferenciaCirugia(
			ctrlWinReferenciaCirugia ctrlWinReferenciaCirugia) {
		this.ctrlWinReferenciaCirugia = ctrlWinReferenciaCirugia;
	}
	public Historial getHistorial() {
		return historial;
	}
	public void setHistorial(Historial historial) {
		this.historial = historial;
	}
	public ReferenciaCirugia getReferenciaCirugia() {
		return referenciaCirugia;
	}
	public void setReferenciaCirugia(ReferenciaCirugia referenciaCirugia) {
		this.referenciaCirugia = referenciaCirugia;
	}
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
	public TipoServicio getTipoServicio() {
		return tipoServicio;
	}
	public void setTipoServicio(TipoServicio tipoServicio) {
		this.tipoServicio = tipoServicio;
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
	public ServicioTipoServicio getServicioTipoServicio() {
		return servicioTipoServicio;
	}
	public void setServicioTipoServicio(ServicioTipoServicio servicioTipoServicio) {
		this.servicioTipoServicio = servicioTipoServicio;
	}
	public List<TipoServicio> getTipoServicios() {
		return tipoServicios;
	}
	public void setTipoServicios(List<TipoServicio> tipoServicios) {
		this.tipoServicios = tipoServicios;
	}
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		paciente = (Paciente) arg.get("paciente");
		historial = (Historial) arg.get("historial");
		winReferenciaCirugia.setAttribute(comp.getId() + "ctrl", this);
		servicioTipoServicio = (ServicioTipoServicio) SpringUtil
				.getBean("beanServicioTipoServicio");
		servicioTipoReferencia = (ServicioTipoReferencia) SpringUtil
				.getBean("beanServicioTipoReferencia");
		servicioReferenciaCirugia = (ServicioReferenciaCirugia) SpringUtil.getBean("beanServicioReferenciaCirugia");
		referenciaCirugia = new ReferenciaCirugia();
		tipoServicio = servicioTipoServicio.buscarTipoServicio("CIR",'A');
		tipoReferencia = servicioTipoReferencia.buscarUno("RCI");
		tipoServicios = new ArrayList<TipoServicio>(tipoReferencia.getTipoServicios());
		txtEdad.setValue(HelperDateAge.age(paciente.getFechaNac(), ' '));
		dbFechaActual.setValue(HelperDate.now());
	}
	
	public void onClick$btnCancelar() {
		this.winReferenciaCirugia.detach();
	}
	
	public void onClick$btnGuardar(){
		if (txtComentarios.getValue().equalsIgnoreCase("")){
			MensajeEmergente.mostrar("NOEMPTY", "\nComentarios",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listServicios.setFocus(true);
						}
					});
		} else {
			referenciaCirugia.setFechaExpedicion(dbFechaActual.getValue());
			referenciaCirugia.setHistorial(historial);
			referenciaCirugia.setComentarios(txtComentarios.getValue());
			referenciaCirugia.setTipoServicio((TipoServicio) listServicios.getSelectedItem().getValue());
			servicioReferenciaCirugia.guardarReferenciaCirugia(referenciaCirugia);
			MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
				@Override
				public void alAceptar() {  
					winReferenciaCirugia.detach();
				}
			});

		}
	}
}

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
import org.ucla.sigma.componentszk.SessionAdministrator;
import org.ucla.sigma.modelo.ConsultaGeneral;
import org.ucla.sigma.modelo.Defuncion;
import org.ucla.sigma.modelo.EspImagenologia;
import org.ucla.sigma.modelo.Historial;
import org.ucla.sigma.modelo.Paciente;
import org.ucla.sigma.modelo.ReferenciaCirugia;
import org.ucla.sigma.modelo.ReferenciaConsultaEspecializada;
import org.ucla.sigma.modelo.ReferenciaImagenologia;
import org.ucla.sigma.modelo.TipoDefuncion;
import org.ucla.sigma.modelo.TipoImagenologia;
import org.ucla.sigma.modelo.TipoReferencia;
import org.ucla.sigma.modelo.TipoServicio;
import org.ucla.sigma.modelo.Usuario;
import org.ucla.sigma.modelo.Veterinario;
import org.ucla.sigma.servicio.ServicioDefuncion;
import org.ucla.sigma.servicio.ServicioEspImagenologia;
import org.ucla.sigma.servicio.ServicioReferenciaCirugia;
import org.ucla.sigma.servicio.ServicioReferenciaConsultaEspecializada;
import org.ucla.sigma.servicio.ServicioTipoDefuncion;
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
public class ctrlWinServicioDefuncion extends GenericForwardComposer {

	private Window winServicioDefuncion;
	private Button btnCancelar;
	private Button btnGuardar;
	private Listbox listTipoDefuncion;
	private Textbox txtEdad;
	private Textbox txtComentarios;
	private Datebox dbFechaActual;

	// ----------------------------------------------------------------------------------------------------
	
	private ServicioTipoDefuncion servicioTipoDefuncion;
	private ServicioDefuncion servicioDefuncion;
	private Defuncion defuncion;
	
	private Paciente paciente;
	private Historial historial;
	private Veterinario veterinario;

	// ----------------------------------------------------------------------------------------------------
	
	private List<TipoDefuncion> tipoDefuncions = new ArrayList<TipoDefuncion>();
	private Usuario usuario;
	
	public Textbox getTxtComentarios() {
		return txtComentarios;
	}
	public void setTxtComentarios(Textbox txtComentarios) {
		this.txtComentarios = txtComentarios;
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		paciente = (Paciente) arg.get("paciente");
		historial = (Historial) arg.get("historial");
		winServicioDefuncion.setAttribute(comp.getId() + "ctrl", this);
		servicioTipoDefuncion = (ServicioTipoDefuncion) SpringUtil
				.getBean("beanServicioTipoDefuncion");
		servicioDefuncion = (ServicioDefuncion) SpringUtil
				.getBean("beanServicioDefuncion");
		defuncion = new Defuncion();
		usuario = new Usuario();
		veterinario = new Veterinario();
		usuario = SessionAdministrator.getLoggedUsuario();
		veterinario = usuario.getPersona().getVeterinario();
		tipoDefuncions = servicioTipoDefuncion.buscarTodos('A');
		paciente = (Paciente) arg.get("paciente");
		txtEdad.setValue(HelperDateAge.age(paciente.getFechaNac(), ' '));
		dbFechaActual.setValue(HelperDate.now());
	}
	
	public void onClick$btnCancelar() {
		this.winServicioDefuncion.detach();
	}
	
	public void onClick$btnGuardar(){
		if (txtComentarios.getValue().equalsIgnoreCase("")){
			MensajeEmergente.mostrar("NOEMPTY", "\nComentarios",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listTipoDefuncion.setFocus(true);
						}
					});
		} else {
			paciente.setEstatus('E');
			defuncion.setPaciente(paciente);
			defuncion.setVeterinario(veterinario);
			defuncion.setHora(dbFechaActual.getValue());
			defuncion.setFecha(dbFechaActual.getValue());
			defuncion.setComentarios(txtComentarios.getValue());
			defuncion.setTipoDefuncion((TipoDefuncion) listTipoDefuncion.getSelectedItem().getValue());
			servicioDefuncion.guardarDefuncion(defuncion);
			MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
				@Override
				public void alAceptar() {  
					winServicioDefuncion.detach();
				}
			});

		}
	}
	public Window getWinServicioDefuncion() {
		return winServicioDefuncion;
	}
	public void setWinServicioDefuncion(Window winServicioDefuncion) {
		this.winServicioDefuncion = winServicioDefuncion;
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
	public Listbox getListTipoDefuncion() {
		return listTipoDefuncion;
	}
	public void setListTipoDefuncion(Listbox listTipoDefuncion) {
		this.listTipoDefuncion = listTipoDefuncion;
	}
	public Textbox getTxtEdad() {
		return txtEdad;
	}
	public void setTxtEdad(Textbox txtEdad) {
		this.txtEdad = txtEdad;
	}
	public Datebox getDbFechaActual() {
		return dbFechaActual;
	}
	public void setDbFechaActual(Datebox dbFechaActual) {
		this.dbFechaActual = dbFechaActual;
	}
	public ServicioTipoDefuncion getServicioTipoDefuncion() {
		return servicioTipoDefuncion;
	}
	public void setServicioTipoDefuncion(ServicioTipoDefuncion servicioTipoDefuncion) {
		this.servicioTipoDefuncion = servicioTipoDefuncion;
	}
	public ServicioDefuncion getServicioDefuncion() {
		return servicioDefuncion;
	}
	public void setServicioDefuncion(ServicioDefuncion servicioDefuncion) {
		this.servicioDefuncion = servicioDefuncion;
	}
	public Defuncion getDefuncion() {
		return defuncion;
	}
	public void setDefuncion(Defuncion defuncion) {
		this.defuncion = defuncion;
	}
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	public Historial getHistorial() {
		return historial;
	}
	public void setHistorial(Historial historial) {
		this.historial = historial;
	}
	public List<TipoDefuncion> getTipoDefuncions() {
		return tipoDefuncions;
	}
	public void setTipoDefuncions(List<TipoDefuncion> tipoDefuncions) {
		this.tipoDefuncions = tipoDefuncions;
	}
	
	
	
}

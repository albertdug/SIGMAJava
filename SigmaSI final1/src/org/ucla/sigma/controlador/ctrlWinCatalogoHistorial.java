/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.List;

import org.ucla.sigma.modelo.Historial;
import org.ucla.sigma.modelo.Paciente;
import org.ucla.sigma.servicio.ServicioHistorial;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author rafael
 *
 */
public class ctrlWinCatalogoHistorial extends GenericForwardComposer {

	private Window winCatalogoHistorial;
	private Button btnCancelar;
	private Button btnSeleccionar;
	private Listbox listHistorial;
	private Textbox txtNombrePaciente;
	private Textbox txtApellidoResponsable;
	private Textbox txtNombreResponsable;
	private Textbox txtCi;
	
	private List<Historial> historials = new ArrayList<Historial>();
	
	private ServicioHistorial servicioHistorial;
	
	private Paciente paciente;

	/**
	 *
	 *
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winCatalogoHistorial.setAttribute(comp.getId()+"ctrl",this);
		servicioHistorial = (ServicioHistorial) SpringUtil.getBean("beanServicioHistorial");
		paciente= (Paciente) arg.get("paciente");
		txtNombrePaciente.setValue(paciente.getNombre());
		txtNombreResponsable.setValue(paciente.getResponsable().getPersona().getNombre());
		txtApellidoResponsable.setValue(paciente.getResponsable().getPersona().getApellido());
		txtCi.setValue(""+paciente.getResponsable().getPersona().getCedula());
		historials = servicioHistorial.buscarServicioPacienteHistorial(paciente.getHistoriaMedica(), 'A');
	}

	public Window getWinCatalogoHistorial() {
		return winCatalogoHistorial;
	}

	public void setWinCatalogoHistorial(Window winCatalogoHistorial) {
		this.winCatalogoHistorial = winCatalogoHistorial;
	}

	public Button getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(Button btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public Button getBtnSeleccionar() {
		return btnSeleccionar;
	}

	public void setBtnSeleccionar(Button btnSeleccionar) {
		this.btnSeleccionar = btnSeleccionar;
	}

	public Listbox getListHistorial() {
		return listHistorial;
	}

	public void setListHistorial(Listbox listHistorial) {
		this.listHistorial = listHistorial;
	}

	public Textbox getTxtNombrePaciente() {
		return txtNombrePaciente;
	}

	public void setTxtNombrePaciente(Textbox txtNombrePaciente) {
		this.txtNombrePaciente = txtNombrePaciente;
	}

	public Textbox getTxtApellidoResponsable() {
		return txtApellidoResponsable;
	}

	public void setTxtApellidoResponsable(Textbox txtApellidoResponsable) {
		this.txtApellidoResponsable = txtApellidoResponsable;
	}

	public Textbox getTxtNombreResponsable() {
		return txtNombreResponsable;
	}

	public void setTxtNombreResponsable(Textbox txtNombreResponsable) {
		this.txtNombreResponsable = txtNombreResponsable;
	}

	public Textbox getTxtCi() {
		return txtCi;
	}

	public void setTxtCi(Textbox txtCi) {
		this.txtCi = txtCi;
	}

	public List<Historial> getHistorials() {
		return historials;
	}

	public void setHistorials(List<Historial> historials) {
		this.historials = historials;
	}

	public ServicioHistorial getServicioHistorial() {
		return servicioHistorial;
	}

	public void setServicioHistorial(ServicioHistorial servicioHistorial) {
		this.servicioHistorial = servicioHistorial;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	

}

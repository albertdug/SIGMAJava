/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.HashMap;
import java.util.Map;

import org.ucla.sigma.modelo.ConsultaGeneral;
import org.ucla.sigma.modelo.Historial;
import org.ucla.sigma.modelo.Paciente;
import org.ucla.sigma.modelo.ReferenciaConsultaEspecializada;
import org.ucla.sigma.modelo.Veterinario;
import org.ucla.sigma.servicio.ServicioTipoServicio;
import org.ucla.sigma.servicio.ServicioVeterinario;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;

/**
 * @author lis
 *
 */
public class ctrlWinSelecReferencia extends GenericForwardComposer {

	private Window winSelecReferencia;
	private Button btnCirugia;
	private Button btnImagenologia;
	private Button btnLaboratorio;
	private Button btnEspecializada;
	
	// ----------------------------------------------------------------------------------------------------

	private String refImagenologia = "/sigma/vistas/servicios/referencias/referenciaImagenologia.zul";	
	private String refCirugia = "/sigma/vistas/servicios/referencias/referenciaCirugia.zul";
	private String refLaboratorio = "/sigma/vistas/servicios/referencias/referenciaLaboratorio.zul";
	private String refEspecializada = "/sigma/vistas/servicios/referencias/referenciaEspecializada.zul";
	private Paciente paciente;
	private Historial historial;
	private Map<String, Object> parametros = new HashMap<String, Object>();
	
	// ----------------------------------------------------------------------------------------------------
	
	public Window getWinSelecReferencia() {
		return winSelecReferencia;
	}
	public String getRefImagenologia() {
		return refImagenologia;
	}
	public void setRefImagenologia(String refImagenologia) {
		this.refImagenologia = refImagenologia;
	}
	public String getRefCirugia() {
		return refCirugia;
	}
	public void setRefCirugia(String refCirugia) {
		this.refCirugia = refCirugia;
	}
	public String getRefLaboratorio() {
		return refLaboratorio;
	}
	public void setRefLaboratorio(String refLaboratorio) {
		this.refLaboratorio = refLaboratorio;
	}
	public String getRefEspecializada() {
		return refEspecializada;
	}
	public void setRefEspecializada(String refEspecializada) {
		this.refEspecializada = refEspecializada;
	}
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	public void setWinSelecReferencia(Window winSelecReferencia) {
		this.winSelecReferencia = winSelecReferencia;
	}
	public Button getBtnCirugia() {
		return btnCirugia;
	}
	public void setBtnCirugia(Button btnCirugia) {
		this.btnCirugia = btnCirugia;
	}
	public Button getBtnImagenologia() {
		return btnImagenologia;
	}
	public void setBtnImagenologia(Button btnImagenologia) {
		this.btnImagenologia = btnImagenologia;
	}
	public Button getBtnLaboratorio() {
		return btnLaboratorio;
	}
	public void setBtnLaboratorio(Button btnLaboratorio) {
		this.btnLaboratorio = btnLaboratorio;
	}
	public Button getBtnEspecializada() {
		return btnEspecializada;
	}
	public void setBtnEspecializada(Button btnEspecializada) {
		this.btnEspecializada = btnEspecializada;
	}


	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		paciente = (Paciente) arg.get("paciente");
		historial = (Historial) arg.get("historial");
		parametros.put("paciente", paciente);
		parametros.put("historial", historial);
		winSelecReferencia.setAttribute(comp.getId() + "ctrl", this);
	}
	
	public void onClick$btnImagenologia(){
		Window win = (Window) Executions.createComponents(refImagenologia,
				null, parametros);
		win.doHighlighted();
		this.winSelecReferencia.detach();
	}	
	
	public void onClick$btnCirugia(){
		Window win = (Window) Executions.createComponents(refCirugia,
				null, parametros);
		win.doHighlighted();
		this.winSelecReferencia.detach();
	}	
	
	public void onClick$btnLaboratorio(){
		Window win = (Window) Executions.createComponents(refLaboratorio,
				null, parametros);
		win.doHighlighted();
		this.winSelecReferencia.detach();
	}	

	public void onClick$btnEspecializada(){
		Window win = (Window) Executions.createComponents(refEspecializada,
				null, parametros);
		win.doHighlighted();
		this.winSelecReferencia.detach();
	}	
	
	
}

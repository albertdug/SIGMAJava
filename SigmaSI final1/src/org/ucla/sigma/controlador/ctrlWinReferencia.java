/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.modelo.Referencia;
import org.ucla.sigma.modelo.Responsable;
import org.ucla.sigma.servicio.ServicioReferencia;
import org.ucla.sigma.servicio.ServicioResponsable;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

/**
 * @author lis
 *
 */
public class ctrlWinReferencia extends GenericForwardComposer {

	// ----------------------------------------------------------------------------------------------------
	
	private Window winReferencia;
	private Listbox listReferencias;
	private Button btnAsignar;
	private Button btnBuscar;
	private Datebox dbInicio;
	private Datebox dbFin;
	private Button btnVer;
	

	// ----------------------------------------------------------------------------------------------------

	private Referencia seleccion;
	private List<Referencia> referencias = new ArrayList<Referencia>();
	private String editCitas = "/sigma/vistas/servicios/citas/editCitas.zul";
	private ServicioReferencia servicioReferencia;
	private boolean buscando = false;
	private boolean verTodos = false;
	// ----------------------------------------------------------------------------------------------------
	
	
	
	public Window getWinReferencia() {
		return winReferencia;
	}

	public Button getBtnVer() {
		return btnVer;
	}

	public void setBtnVer(Button btnVer) {
		this.btnVer = btnVer;
	}

	public Datebox getDbInicio() {
		return dbInicio;
	}

	public void setDbInicio(Datebox dbInicio) {
		this.dbInicio = dbInicio;
	}

	public Datebox getDbFin() {
		return dbFin;
	}

	public void setDbFin(Datebox dbFin) {
		this.dbFin = dbFin;
	}

	public boolean isBuscando() {
		return buscando;
	}

	public void setBuscando(boolean buscando) {
		this.buscando = buscando;
	}

	public boolean isVerTodos() {
		return verTodos;
	}

	public void setVerTodos(boolean verTodos) {
		this.verTodos = verTodos;
	}

	public Referencia getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Referencia seleccion) {
		this.seleccion = seleccion;
	}

	public List<Referencia> getReferencias() {
		return referencias;
	}

	public void setReferencias(List<Referencia> referencias) {
		this.referencias = referencias;
	}

	public String getEditCitas() {
		return editCitas;
	}

	public void setEditCitas(String editCitas) {
		this.editCitas = editCitas;
	}

	public ServicioReferencia getServicioReferencia() {
		return servicioReferencia;
	}

	public void setServicioReferencia(ServicioReferencia servicioReferencia) {
		this.servicioReferencia = servicioReferencia;
	}

	public void setWinReferencia(Window winReferencia) {
		this.winReferencia = winReferencia;
	}

	public Listbox getListReferencias() {
		return listReferencias;
	}

	public void setListReferencias(Listbox listReferencias) {
		this.listReferencias = listReferencias;
	}

	public Button getBtnAsignar() {
		return btnAsignar;
	}

	public void setBtnAsignar(Button btnAsignar) {
		this.btnAsignar = btnAsignar;
	}

	public Button getBtnBuscar() {
		return btnBuscar;
	}

	public void setBtnBuscar(Button btnBuscar) {
		this.btnBuscar = btnBuscar;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
			
		winReferencia.setAttribute(comp.getId() + "ctrl", this);
		seleccion = new Referencia();
		servicioReferencia = (ServicioReferencia) SpringUtil
				.getBean("beanServicioReferencia");
		apagarBotones();
		referencias = servicioReferencia.buscarUltimos('R');
		
		
	}

	public void onClick$btnAsignar(){
			if (listReferencias.getSelectedItems().isEmpty()) {
				MensajeEmergente.mostrar("SELECTREG");
			} else {
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("objeto", seleccion);
				parametros.put("ctrlWinReferencia", this);
				Window win = (Window) Executions.createComponents(editCitas, null,
						parametros);
				win.doHighlighted();
				apagarBotones();
			}

		}

	public void onSelect$listReferencias(){
		btnAsignar.setDisabled(false);		
	}
	
	
	public void apagarBotones() {
		btnAsignar.setDisabled(true);
		btnBuscar.setDisabled(true);
		dbFin.setDisabled(true);
	}
	
	public void onAfterRender$listReferencias(){
		if (listReferencias.getItems().isEmpty()){
			MensajeEmergente.mostrar("LISTEMPTY");
			dbFin.setText(null);
			dbInicio.setText(null);
			dbFin.setDisabled(true);
			btnBuscar.setDisabled(true);
		}
		
	}
	
	public void recargar() {
		seleccion = null;
		referencias = servicioReferencia.buscarUltimos('R');
		listReferencias.setModel(new BindingListModelList(referencias, false));
	}

	public void onClick$btnBuscar(){
		if (dbFin.getValue() == null || dbInicio.getValue() == null){
			MensajeEmergente.mostrar("DATESELECT");
		}else{
			referencias = servicioReferencia.buscarRango("fechaExpedicion","fechaExpedicion",dbInicio.getValue(), dbFin.getValue(), 'R');
			listReferencias.setModel(new BindingListModelList(referencias, false));
		}
	}
	
	public void onClick$btnVer() {
		referencias = servicioReferencia.buscarUltimos('R');
		listReferencias.setModel(new BindingListModelList(referencias, false));
		buscando = false;
		verTodos = true;
		dbFin.setText(null);
		dbInicio.setText(null);
		apagarBotones();
	}
	
	public void onChange$dbInicio() {
		dbFin.setValue(null);
		dbFin.setConstraint("between"
				+ HelperDate.format(dbInicio.getValue(), "yyyyMMdd")
				+ " and " + HelperDate.nowFormat("yyyyMMdd"));
		dbFin.setDisabled(false);
	}
	
	public void onChange$dbFin() {
		btnBuscar.setDisabled(false);
	}
		

	
}

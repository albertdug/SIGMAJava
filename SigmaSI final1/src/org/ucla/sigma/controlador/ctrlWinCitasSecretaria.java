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
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Referencia;
import org.ucla.sigma.servicio.ServicioReferencia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Window;

/**
 * @author lis
 * 
 */
public class ctrlWinCitasSecretaria extends GenericForwardComposer {

	private Window winCitasSecretaria;
	private Button btnEliminar;
	private Button btnModificar;
	private Button btnVer;
	private Button btnBuscar;
	private Radio rbCitasDia;
	private Listbox listReferencias;
	private Datebox dbInicio;
	private Datebox dbFin;

	// ----------------------------------------------------------------------------------------------------

	private Referencia seleccion;
	private List<Referencia> referencias = new ArrayList<Referencia>();
	private String editCitas = "/sigma/vistas/servicios/citas/editCitas.zul";
	private ServicioReferencia servicioReferencia;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			dbInicio.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	
	
	public Window getWinCitasSecretaria() {
		return winCitasSecretaria;
	}

	public Button getBtnVer() {
		return btnVer;
	}

	public void setBtnVer(Button btnVer) {
		this.btnVer = btnVer;
	}

	public MensajeListener getAsignarFocusBuscar() {
		return asignarFocusBuscar;
	}

	public void setAsignarFocusBuscar(MensajeListener asignarFocusBuscar) {
		this.asignarFocusBuscar = asignarFocusBuscar;
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

	public void setWinCitasSecretaria(Window winCitasSecretaria) {
		this.winCitasSecretaria = winCitasSecretaria;
	}

	public Button getBtnEliminar() {
		return btnEliminar;
	}

	public void setBtnEliminar(Button btnEliminar) {
		this.btnEliminar = btnEliminar;
	}

	public Button getBtnModificar() {
		return btnModificar;
	}

	public void setBtnModificar(Button btnModificar) {
		this.btnModificar = btnModificar;
	}

	public Button getBtnBuscar() {
		return btnBuscar;
	}

	public void setBtnBuscar(Button btnBuscar) {
		this.btnBuscar = btnBuscar;
	}

	public Radio getRbCitasDia() {
		return rbCitasDia;
	}

	public void setRbCitasDia(Radio rbCitasDia) {
		this.rbCitasDia = rbCitasDia;
	}

	public Listbox getListReferencias() {
		return listReferencias;
	}

	public void setListReferencias(Listbox listReferencias) {
		this.listReferencias = listReferencias;
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

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winCitasSecretaria.setAttribute(comp.getId() + "ctrl", this);
		seleccion = new Referencia();
		servicioReferencia = (ServicioReferencia) SpringUtil
				.getBean("beanServicioReferencia");
		apagarBotones();
		referencias = servicioReferencia.buscarTodos("fechaCita",'C');
		dbFin.setDisabled(true);
	}

	public void onClick$btnBuscar() {
		if (dbFin.getValue() == null || dbInicio.getValue() == null) {
			MensajeEmergente.mostrar("DATESELECT");
		} else {
			referencias = servicioReferencia.buscarRango("fechaCita","fechaCita",dbInicio.getValue(),
					dbFin.getValue(), 'C');
			listReferencias.setModel(new BindingListModelList(referencias,
					false));
			if (listReferencias.getItems().isEmpty()) {
				MensajeEmergente.mostrar("LISTEMPTY");
				dbFin.setText(null);
				dbInicio.setText(null);
				dbFin.setDisabled(true);
				btnBuscar.setDisabled(true);
			}
		}
	}

	public void onChange$dbInicio() {
		dbFin.setValue(null);
		dbFin.setConstraint("after"
				+ HelperDate.format(dbInicio.getValue(), "yyyyMMdd"));
		dbFin.setDisabled(false);
	}

	public void onSelect$listCitasSecretaria() {
		btnEliminar.setDisabled(false);
		btnModificar.setDisabled(false);
	}

	public void apagarBotones() {
		btnEliminar.setDisabled(true);
		btnModificar.setDisabled(true);
		btnBuscar.setDisabled(true);
		dbFin.setDisabled(true);
	}
	
	public void onSelect$listReferencias(){
		btnEliminar.setDisabled(false);
		btnModificar.setDisabled(false);
	}
	
	public void onClick$btnEliminar() {
		if (listReferencias.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioReferencia.borrarReferencia(seleccion);
					referencias.remove(seleccion);
					listReferencias.setModel(new BindingListModelList(referencias, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}
	
	public void recargar() {
		seleccion = null;
		referencias = servicioReferencia.buscarTodos("fechaCita", 'C');
		listReferencias.setModel(new BindingListModelList(referencias, false));
	}
	
	public void onClick$btnModificar() {
		if (listReferencias.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinCitasSecretaria", this);
			Window win = (Window) Executions.createComponents(editCitas, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}
	
	public void onClick$btnVer() {
		rbCitasDia.setChecked(false);
		referencias = servicioReferencia.buscarTodos("fechaCita",'C');
		listReferencias.setModel(new BindingListModelList(referencias, false));
		buscando = false;
		verTodos = true;
		dbFin.setText(null);
		dbInicio.setText(null);
		apagarBotones();
	}
	
	public void onCheck$rbCitasDia(){
		referencias = servicioReferencia.buscarHoy("fechaCita", 'C');
		if (referencias.isEmpty()) {
			MensajeEmergente.mostrar("NODATE");
		}
		listReferencias.setModel(new BindingListModelList(referencias, false));
		dbInicio.setValue(null);
		dbFin.setValue(null);
		dbFin.setDisabled(true);
		btnBuscar.setDisabled(true);
	}
	
	public void onFocus$dbInicio(){
		rbCitasDia.setChecked(false);
	}
	
	public void onFocus$dbFin(){
		rbCitasDia.setChecked(false);
	}
	
	public void onChange$dbFin() {
		btnBuscar.setDisabled(false);
	}
	
	// public void onAfterRender$listCitasSecretaria(){
	// if(listCitasSecretaria.getItems().isEmpty()){
	// MensajeEmergente.mostrar("LISTEMPTY");
	// }
	//
	// }
}
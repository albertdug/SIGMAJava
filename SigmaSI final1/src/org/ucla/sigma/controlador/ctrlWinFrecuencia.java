package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Frecuencia;
import org.ucla.sigma.servicio.ServicioFrecuencia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinFrecuencia extends GenericForwardComposer {

	private Window winFrecuencia;
	private Listbox listFrecuencia;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreFrecuencia;

	// ----------------------------------------------------------------------------------------------------

	private String editFrecuencia = "/sigma/vistas/maestros/frecuencia/editFrecuencia.zul";
	private ServicioFrecuencia servicioFrecuencia;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreFrecuencia.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Frecuencia seleccion;
	private List<Frecuencia> frecuencias = new ArrayList<Frecuencia>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinFrecuencia() {
		return winFrecuencia;
	}

	public void setWinFrecuencia(Window winFrecuencia) {
		this.winFrecuencia = winFrecuencia;
	}

	public Listbox getListFrecuencia() {
		return listFrecuencia;
	}

	public void setListFrecuencia(Listbox listFrecuencia) {
		this.listFrecuencia = listFrecuencia;
	}

	public Button getBtnBuscar() {
		return btnBuscar;
	}

	public void setBtnBuscar(Button btnBuscar) {
		this.btnBuscar = btnBuscar;
	}

	public Button getBtnNuevo() {
		return btnNuevo;
	}

	public void setBtnNuevo(Button btnNuevo) {
		this.btnNuevo = btnNuevo;
	}

	public Button getBtnEditar() {
		return btnEditar;
	}

	public void setBtnEditar(Button btnEditar) {
		this.btnEditar = btnEditar;
	}

	public Button getBtnEliminar() {
		return btnEliminar;
	}

	public void setBtnEliminar(Button btnEliminar) {
		this.btnEliminar = btnEliminar;
	}

	public Button getBtnVerTodos() {
		return btnVerTodos;
	}

	public void setBtnVerTodos(Button btnVerTodos) {
		this.btnVerTodos = btnVerTodos;
	}

	public ServicioFrecuencia getServicioFrecuencia() {
		return servicioFrecuencia;
	}

	public void setServicioFrecuencia(ServicioFrecuencia servicioFrecuencia) {
		this.servicioFrecuencia = servicioFrecuencia;
	}

	public Frecuencia getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Frecuencia seleccion) {
		this.seleccion = seleccion;
	}

	public Textbox getTxtNombreFrecuencia() {
		return txtNombreFrecuencia;
	}

	public String getEditFrecuencia() {
		return editFrecuencia;
	}

	public void setEditFrecuencia(String editFrecuencia) {
		this.editFrecuencia = editFrecuencia;
	}

	public boolean isVerTodos() {
		return verTodos;
	}

	public void setVerTodos(boolean verTodos) {
		this.verTodos = verTodos;
	}

	public List<Frecuencia> getFrecuencias() {
		return frecuencias;
	}

	public void setFrecuencias(List<Frecuencia> frecuencias) {
		this.frecuencias = frecuencias;
	}

	public void setTxtNombreFrecuencia(Textbox txtNombreFrecuencia) {
		this.txtNombreFrecuencia = txtNombreFrecuencia;
	}

	public void setFrecuencia(List<Frecuencia> frecuencia) {
		this.frecuencias = frecuencia;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winFrecuencia.setAttribute(comp.getId() + "ctrl", this);
		servicioFrecuencia = (ServicioFrecuencia) SpringUtil
				.getBean("beanServicioFrecuencia");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinFrecuencia", this);
		Window win = (Window) Executions.createComponents(editFrecuencia, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listFrecuencia.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinFrecuencia", this);
			Window win = (Window) Executions.createComponents(editFrecuencia,
					null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listFrecuencia.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioFrecuencia.borrarFrecuencia(seleccion);
					frecuencias.remove(seleccion);
					listFrecuencia.setModel(new BindingListModelList(
							frecuencias, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		frecuencias = servicioFrecuencia.buscarTodos('A');
		listFrecuencia.setModel(new BindingListModelList(frecuencias, false));
		buscando = false;
		verTodos = true;
		txtNombreFrecuencia.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreFrecuencia.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			frecuencias = servicioFrecuencia.buscarCoincidencias(
					txtNombreFrecuencia.getValue(), 'A');
			if (frecuencias.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listFrecuencia.setModel(new BindingListModelList(frecuencias,
						false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listFrecuencia() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreFrecuencia() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			frecuencias = servicioFrecuencia.buscarTodos('A');
		else if (buscando)
			frecuencias = servicioFrecuencia.buscarCoincidencias(
					txtNombreFrecuencia.getValue(), 'A');
		else
			frecuencias.clear();

		listFrecuencia.setModel(new BindingListModelList(frecuencias, false));
	}

	public void apagarBotones() {
		txtNombreFrecuencia.setFocus(true);
		listFrecuencia.clearSelection();
		listFrecuencia.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

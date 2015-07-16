/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Estado;
import org.ucla.sigma.modelo.Consistencia;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioConsistencia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author jhoan
 *
 */
public class ctrlWinConsistencia extends GenericForwardComposer {

	private Window winConsistencia;
	private Listbox listConsistencia;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreConsistencia;

	// ----------------------------------------------------------------------------------------------------

	private String editConsistencia = "/sigma/vistas/maestros/consistencia/editConsistencia.zul";
	private ServicioConsistencia servicioConsistencia;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreConsistencia.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Consistencia seleccion;
	private List<Consistencia> consistencias = new ArrayList<Consistencia>();
	public Window getWinConsistencia() {
		return winConsistencia;
	}
	public void setWinConsistencia(Window winConsistencia) {
		this.winConsistencia = winConsistencia;
	}

	public Listbox getListConsistencia() {
		return listConsistencia;
	}
	public void setListConsistencia(Listbox listConsistencia) {
		this.listConsistencia = listConsistencia;
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
	public Textbox getTxtNombreConsistencia() {
		return txtNombreConsistencia;
	}
	public void setTxtNombreConsistencia(Textbox txtNombreConsistencia) {
		this.txtNombreConsistencia = txtNombreConsistencia;
	}
	public String getEditConsistencia() {
		return editConsistencia;
	}
	public void setEditConsistencia(String editConsistencia) {
		this.editConsistencia = editConsistencia;
	}
	public ServicioConsistencia getServicioConsistencia() {
		return servicioConsistencia;
	}
	public void setServicioConsistencia(ServicioConsistencia servicioConsistencia) {
		this.servicioConsistencia = servicioConsistencia;
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
	public MensajeListener getAsignarFocusBuscar() {
		return asignarFocusBuscar;
	}
	public void setAsignarFocusBuscar(MensajeListener asignarFocusBuscar) {
		this.asignarFocusBuscar = asignarFocusBuscar;
	}
	public Consistencia getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(Consistencia seleccion) {
		this.seleccion = seleccion;
	}
	public List<Consistencia> getConsistencias() {
		return consistencias;
	}
	public void setConsistencias(List<Consistencia> consistencias) {
		this.consistencias = consistencias;
	}

	// ----------------------------------------------------------------------------------------------------
	

	
	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winConsistencia.setAttribute(comp.getId() + "ctrl", this);
		servicioConsistencia = (ServicioConsistencia) SpringUtil
				.getBean("beanServicioConsistencia");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinConsistencia", this);
		Window win = (Window) Executions.createComponents(editConsistencia, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listConsistencia.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinConsistencia", this);
			Window win = (Window) Executions.createComponents(editConsistencia, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listConsistencia.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioConsistencia.borrarConsistencia(seleccion);
					consistencias.remove(seleccion);
					listConsistencia
							.setModel(new BindingListModelList(consistencias, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		consistencias = servicioConsistencia.buscarTodos('A');
		listConsistencia.setModel(new BindingListModelList(consistencias, false));
		buscando = false;
		verTodos = true;
		txtNombreConsistencia.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreConsistencia.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			consistencias = servicioConsistencia.buscarCoincidencias(
					txtNombreConsistencia.getValue(), 'A');
			if (consistencias.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listConsistencia.setModel(new BindingListModelList(consistencias, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listConsistencia() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			consistencias = servicioConsistencia.buscarTodos('A');
		else if (buscando)
			consistencias = servicioConsistencia.buscarCoincidencias(
					txtNombreConsistencia.getValue(), 'A');
		else
			consistencias.clear();

		listConsistencia.setModel(new BindingListModelList(consistencias, false));
	}

	public void apagarBotones() {
		txtNombreConsistencia.setFocus(true);
		listConsistencia.clearSelection();
		listConsistencia.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}


}

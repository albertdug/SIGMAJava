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
import org.ucla.sigma.modelo.MembranaMucosa;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioMembranaMucosa;
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
public class ctrlWinMembranaMucosa extends GenericForwardComposer {

	private Window winMembranaMucosa;
	private Listbox listMembranaMucosa;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreMembranaMucosa;

	// ----------------------------------------------------------------------------------------------------

	private String editMembranaMucosa = "/sigma/vistas/maestros/membranamucosa/editMembranaMucosa.zul";
	private ServicioMembranaMucosa servicioMembranaMucosa;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreMembranaMucosa.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private MembranaMucosa seleccion;
	private List<MembranaMucosa> membranamucosas = new ArrayList<MembranaMucosa>();
	public Window getWinMembranaMucosa() {
		return winMembranaMucosa;
	}
	public void setWinMembranaMucosa(Window winMembranaMucosa) {
		this.winMembranaMucosa = winMembranaMucosa;
	}

	public Listbox getListMembranaMucosa() {
		return listMembranaMucosa;
	}
	public void setListMembranaMucosa(Listbox listMembranaMucosa) {
		this.listMembranaMucosa = listMembranaMucosa;
	}
	public List<MembranaMucosa> getMembranamucosas() {
		return membranamucosas;
	}
	public void setMembranamucosas(List<MembranaMucosa> membranamucosas) {
		this.membranamucosas = membranamucosas;
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
	public Textbox getTxtNombreMembranaMucosa() {
		return txtNombreMembranaMucosa;
	}
	public void setTxtNombreMembranaMucosa(Textbox txtNombreMembranaMucosa) {
		this.txtNombreMembranaMucosa = txtNombreMembranaMucosa;
	}
	public String getEditMembranaMucosa() {
		return editMembranaMucosa;
	}
	public void setEditMembranaMucosa(String editMembranaMucosa) {
		this.editMembranaMucosa = editMembranaMucosa;
	}
	public ServicioMembranaMucosa getServicioMembranaMucosa() {
		return servicioMembranaMucosa;
	}
	public void setServicioMembranaMucosa(ServicioMembranaMucosa servicioMembranaMucosa) {
		this.servicioMembranaMucosa = servicioMembranaMucosa;
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
	public MembranaMucosa getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(MembranaMucosa seleccion) {
		this.seleccion = seleccion;
	}
	public List<MembranaMucosa> getMembranaMucosas() {
		return membranamucosas;
	}
	public void setMembranaMucosas(List<MembranaMucosa> membranamucosas) {
		this.membranamucosas = membranamucosas;
	}

	// ----------------------------------------------------------------------------------------------------
	

	
	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winMembranaMucosa.setAttribute(comp.getId() + "ctrl", this);
		servicioMembranaMucosa = (ServicioMembranaMucosa) SpringUtil
				.getBean("beanServicioMembranaMucosa");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinMembranaMucosa", this);
		Window win = (Window) Executions.createComponents(editMembranaMucosa, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listMembranaMucosa.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinMembranaMucosa", this);
			Window win = (Window) Executions.createComponents(editMembranaMucosa, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listMembranaMucosa.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioMembranaMucosa.borrarMembranaMucosa(seleccion);
					membranamucosas.remove(seleccion);
					listMembranaMucosa
							.setModel(new BindingListModelList(membranamucosas, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		membranamucosas = servicioMembranaMucosa.buscarTodos('A');
		listMembranaMucosa.setModel(new BindingListModelList(membranamucosas, false));
		buscando = false;
		verTodos = true;
		txtNombreMembranaMucosa.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreMembranaMucosa.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			membranamucosas = servicioMembranaMucosa.buscarCoincidencias(
					txtNombreMembranaMucosa.getValue(), 'A');
			if (membranamucosas.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listMembranaMucosa.setModel(new BindingListModelList(membranamucosas, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listMembranaMucosa() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			membranamucosas = servicioMembranaMucosa.buscarTodos('A');
		else if (buscando)
			membranamucosas = servicioMembranaMucosa.buscarCoincidencias(
					txtNombreMembranaMucosa.getValue(), 'A');
		else
			membranamucosas.clear();

		listMembranaMucosa.setModel(new BindingListModelList(membranamucosas, false));
	}

	public void apagarBotones() {
		txtNombreMembranaMucosa.setFocus(true);
		listMembranaMucosa.clearSelection();
		listMembranaMucosa.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}


}

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
import org.ucla.sigma.modelo.AreaEnfermedad;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioAreaEnfermedad;
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
public class ctrlWinAreaEnfermedad extends GenericForwardComposer {

	private Window winAreaEnfermedad;
	private Listbox listAreaEnfermedad;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreAreaEnfermedad;

	// ----------------------------------------------------------------------------------------------------

	private String editAreaEnfermedad = "/sigma/vistas/maestros/areaenfermedad/editAreaEnfermedad.zul";
	private ServicioAreaEnfermedad servicioAreaEnfermedad;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreAreaEnfermedad.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private AreaEnfermedad seleccion;
	private List<AreaEnfermedad> areaenfermedads = new ArrayList<AreaEnfermedad>();
	public Window getWinAreaEnfermedad() {
		return winAreaEnfermedad;
	}
	public void setWinAreaEnfermedad(Window winAreaEnfermedad) {
		this.winAreaEnfermedad = winAreaEnfermedad;
	}

	public Listbox getListAreaEnfermedad() {
		return listAreaEnfermedad;
	}
	public void setListAreaEnfermedad(Listbox listAreaEnfermedad) {
		this.listAreaEnfermedad = listAreaEnfermedad;
	}
	public List<AreaEnfermedad> getAreaenfermedads() {
		return areaenfermedads;
	}
	public void setAreaenfermedads(List<AreaEnfermedad> areaenfermedads) {
		this.areaenfermedads = areaenfermedads;
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
	public Textbox getTxtNombreAreaEnfermedad() {
		return txtNombreAreaEnfermedad;
	}
	public void setTxtNombreAreaEnfermedad(Textbox txtNombreAreaEnfermedad) {
		this.txtNombreAreaEnfermedad = txtNombreAreaEnfermedad;
	}
	public String getEditAreaEnfermedad() {
		return editAreaEnfermedad;
	}
	public void setEditAreaEnfermedad(String editAreaEnfermedad) {
		this.editAreaEnfermedad = editAreaEnfermedad;
	}
	public ServicioAreaEnfermedad getServicioAreaEnfermedad() {
		return servicioAreaEnfermedad;
	}
	public void setServicioAreaEnfermedad(ServicioAreaEnfermedad servicioAreaEnfermedad) {
		this.servicioAreaEnfermedad = servicioAreaEnfermedad;
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
	public AreaEnfermedad getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(AreaEnfermedad seleccion) {
		this.seleccion = seleccion;
	}
	public List<AreaEnfermedad> getAreaEnfermedads() {
		return areaenfermedads;
	}
	public void setAreaEnfermedads(List<AreaEnfermedad> areaenfermedads) {
		this.areaenfermedads = areaenfermedads;
	}

	// ----------------------------------------------------------------------------------------------------
	

	
	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winAreaEnfermedad.setAttribute(comp.getId() + "ctrl", this);
		servicioAreaEnfermedad = (ServicioAreaEnfermedad) SpringUtil
				.getBean("beanServicioAreaEnfermedad");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinAreaEnfermedad", this);
		Window win = (Window) Executions.createComponents(editAreaEnfermedad, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listAreaEnfermedad.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinAreaEnfermedad", this);
			Window win = (Window) Executions.createComponents(editAreaEnfermedad, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listAreaEnfermedad.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioAreaEnfermedad.borrarAreaEnfermedad(seleccion);
					areaenfermedads.remove(seleccion);
					listAreaEnfermedad
							.setModel(new BindingListModelList(areaenfermedads, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		areaenfermedads = servicioAreaEnfermedad.buscarTodos('A');
		listAreaEnfermedad.setModel(new BindingListModelList(areaenfermedads, false));
		buscando = false;
		verTodos = true;
		txtNombreAreaEnfermedad.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreAreaEnfermedad.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			areaenfermedads = servicioAreaEnfermedad.buscarCoincidencias(
					txtNombreAreaEnfermedad.getValue(), 'A');
			if (areaenfermedads.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listAreaEnfermedad.setModel(new BindingListModelList(areaenfermedads, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listAreaEnfermedad() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			areaenfermedads = servicioAreaEnfermedad.buscarTodos('A');
		else if (buscando)
			areaenfermedads = servicioAreaEnfermedad.buscarCoincidencias(
					txtNombreAreaEnfermedad.getValue(), 'A');
		else
			areaenfermedads.clear();

		listAreaEnfermedad.setModel(new BindingListModelList(areaenfermedads, false));
	}

	public void apagarBotones() {
		txtNombreAreaEnfermedad.setFocus(true);
		listAreaEnfermedad.clearSelection();
		listAreaEnfermedad.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}


}

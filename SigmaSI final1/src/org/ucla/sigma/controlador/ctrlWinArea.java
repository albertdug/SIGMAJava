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
import org.ucla.sigma.modelo.Area;
import org.ucla.sigma.modelo.Usuario;
import org.ucla.sigma.servicio.ServicioArea;
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
 * @author rafael
 * 
 */
public class ctrlWinArea extends GenericForwardComposer {

	private Window winArea;
	private Listbox listArea;
	private Button btnVerTodo;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombreArea;
	private Usuario usuario;
	// ----------------------------------------------------------------------------------------------------

	private String editArea = "/sigma/vistas/maestros/area/editArea.zul";
	private ServicioArea servicioArea;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreArea.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Area seleccion;
	private List<Area> areas = new ArrayList<Area>();

	public Window getWinArea() {
		return winArea;
	}

	public void setWinArea(Window winArea) {
		this.winArea = winArea;
	}

	public Listbox getListArea() {
		return listArea;
	}

	public void setListArea(Listbox listArea) {
		this.listArea = listArea;
	}

	public Button getBtnVerTodo() {
		return btnVerTodo;
	}

	public void setBtnVerTodo(Button btnVerTodo) {
		this.btnVerTodo = btnVerTodo;
	}

	public Button getBtnEliminar() {
		return btnEliminar;
	}

	public void setBtnEliminar(Button btnEliminar) {
		this.btnEliminar = btnEliminar;
	}

	public Button getBtnEditar() {
		return btnEditar;
	}

	public void setBtnEditar(Button btnEditar) {
		this.btnEditar = btnEditar;
	}

	public Button getBtnNuevo() {
		return btnNuevo;
	}

	public void setBtnNuevo(Button btnNuevo) {
		this.btnNuevo = btnNuevo;
	}

	public Button getBtnBuscar() {
		return btnBuscar;
	}

	public void setBtnBuscar(Button btnBuscar) {
		this.btnBuscar = btnBuscar;
	}

	public Textbox getTxtNombreArea() {
		return txtNombreArea;
	}

	public void setTxtNombreArea(Textbox txtNombreArea) {
		this.txtNombreArea = txtNombreArea;
	}

	public String getEditArea() {
		return editArea;
	}

	public void setEditArea(String editArea) {
		this.editArea = editArea;
	}

	public ServicioArea getServicioArea() {
		return servicioArea;
	}

	public void setServicioArea(ServicioArea servicioArea) {
		this.servicioArea = servicioArea;
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

	public Area getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Area seleccion) {
		this.seleccion = seleccion;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	// ----------------------------------------------------------------------------------------------------

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winArea.setAttribute(comp.getId() + "ctrl", this);
		servicioArea = (ServicioArea) SpringUtil.getBean("beanServicioArea");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinArea", this);
		Window win = (Window) Executions.createComponents(editArea, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listArea.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinArea", this);
			Window win = (Window) Executions.createComponents(editArea, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listArea.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioArea.borrarArea(seleccion);
					areas.remove(seleccion);
					listArea.setModel(new BindingListModelList(areas, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		areas = servicioArea.buscarTodos('A');
		listArea.setModel(new BindingListModelList(areas, false));
		buscando = false;
		verTodos = true;
		txtNombreArea.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreArea.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			areas = servicioArea.buscarCoincidencias(txtNombreArea.getValue(),
					'A');
			if (areas.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listArea.setModel(new BindingListModelList(areas, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listArea() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreArea() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			areas = servicioArea.buscarTodos('A');
		else if (buscando)
			areas = servicioArea.buscarCoincidencias(txtNombreArea.getValue(),
					'A');
		else
			areas.clear();

		listArea.setModel(new BindingListModelList(areas, false));
	}

	public void apagarBotones() {
		txtNombreArea.setFocus(true);
		listArea.clearSelection();
		listArea.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

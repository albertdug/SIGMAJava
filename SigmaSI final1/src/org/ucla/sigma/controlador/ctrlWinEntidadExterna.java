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
import org.ucla.sigma.modelo.EntidadExterna;
import org.ucla.sigma.servicio.ServicioEntidadExterna;
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
public class ctrlWinEntidadExterna extends GenericForwardComposer {

	private Window winEntidadExterna;
	private Listbox listEntidadEx;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombre;

	private String editEntidadExterna = "/sigma/vistas/maestros/entidadExterna/editEntidadexterna.zul";
	private ServicioEntidadExterna servicioEntidadExterna;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombre.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private EntidadExterna seleccion;
	private List<EntidadExterna> entidadExs = new ArrayList<EntidadExterna>();

	public Window getWinEntidadExterna() {
		return winEntidadExterna;
	}

	public void setWinEntidadExterna(Window winEntidadExterna) {
		this.winEntidadExterna = winEntidadExterna;
	}

	public Listbox getListEntidadEx() {
		return listEntidadEx;
	}

	public void setListEntidadEx(Listbox listEntidadEx) {
		this.listEntidadEx = listEntidadEx;
	}

	public Button getBtnVerTodos() {
		return btnVerTodos;
	}

	public void setBtnVerTodos(Button btnVerTodos) {
		this.btnVerTodos = btnVerTodos;
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

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public String getEditEntidadExterna() {
		return editEntidadExterna;
	}

	public void setEditEntidadExterna(String editEntidadExterna) {
		this.editEntidadExterna = editEntidadExterna;
	}

	public ServicioEntidadExterna getServicioEntidadExterna() {
		return servicioEntidadExterna;
	}

	public void setServicioEntidadExterna(
			ServicioEntidadExterna servicioEntidadExterna) {
		this.servicioEntidadExterna = servicioEntidadExterna;
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

	public EntidadExterna getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(EntidadExterna seleccion) {
		this.seleccion = seleccion;
	}

	public List<EntidadExterna> getEntidadExs() {
		return entidadExs;
	}

	public void setEntidadExs(List<EntidadExterna> entidadExs) {
		this.entidadExs = entidadExs;
	}

	// ----------------------------------------------------------------------------------------------------

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEntidadExterna.setAttribute(comp.getId() + "ctrl", this);
		servicioEntidadExterna = (ServicioEntidadExterna) SpringUtil
				.getBean("beanServicioEntidadExterna");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinEntidadExterna", this);
		Window win = (Window) Executions.createComponents(editEntidadExterna,
				null, parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listEntidadEx.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinEntidadExterna", this);
			Window win = (Window) Executions.createComponents(
					editEntidadExterna, null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listEntidadEx.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioEntidadExterna.borrarEntidadExterna(seleccion);
					entidadExs.remove(seleccion);
					listEntidadEx.setModel(new BindingListModelList(entidadExs,
							false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		entidadExs = servicioEntidadExterna.buscarTodos('A');
		listEntidadEx.setModel(new BindingListModelList(entidadExs, false));
		buscando = false;
		verTodos = true;
		txtNombre.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombre.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			entidadExs = servicioEntidadExterna.buscarCoincidencias(
					txtNombre.getValue(), 'A');
			if (entidadExs.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listEntidadEx.setModel(new BindingListModelList(entidadExs,
						false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listEntidadEx() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombre() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			entidadExs = servicioEntidadExterna.buscarTodos('A');
		else if (buscando)
			entidadExs = servicioEntidadExterna.buscarCoincidencias(
					txtNombre.getValue(), 'A');
		else
			entidadExs.clear();

		listEntidadEx.setModel(new BindingListModelList(entidadExs, false));
	}

	public void apagarBotones() {
		txtNombre.setFocus(true);
		listEntidadEx.clearSelection();
		listEntidadEx.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

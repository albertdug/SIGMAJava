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
import org.ucla.sigma.modelo.TipoEntidadExterna;
import org.ucla.sigma.servicio.ServicioTipoEntidadExterna;

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
public class ctrlWinTipoEntidadExterna extends GenericForwardComposer {

	private Window winTipoEntidadExterna;
	private Listbox listTipoEntidadEx;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private String editTipoEntidadExsterna = "/sigma/vistas/maestros/tipoEntidadExterna/editTipoEntidadExterna.zul";
	private ServicioTipoEntidadExterna servicioTipoEntidadExterna;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombre.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private TipoEntidadExterna seleccion;
	private List<TipoEntidadExterna> tipoEntidadExs = new ArrayList<TipoEntidadExterna>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinTipoEntidadExterna() {
		return winTipoEntidadExterna;
	}

	public void setWinTipoEntidadExterna(Window winTipoEntidadExterna) {
		this.winTipoEntidadExterna = winTipoEntidadExterna;
	}

	public Listbox getListTipoEntidadEx() {
		return listTipoEntidadEx;
	}

	public void setListTipoEntidadEx(Listbox listTipoEntidadEx) {
		this.listTipoEntidadEx = listTipoEntidadEx;
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

	public String getEditTipoEntidadExsterna() {
		return editTipoEntidadExsterna;
	}

	public void setEditTipoEntidadExsterna(String editTipoEntidadExsterna) {
		this.editTipoEntidadExsterna = editTipoEntidadExsterna;
	}

	public ServicioTipoEntidadExterna getServicioTipoEntidadExterna() {
		return servicioTipoEntidadExterna;
	}

	public void setServicioTipoEntidadExterna(
			ServicioTipoEntidadExterna servicioTipoEntidadExterna) {
		this.servicioTipoEntidadExterna = servicioTipoEntidadExterna;
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

	public TipoEntidadExterna getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(TipoEntidadExterna seleccion) {
		this.seleccion = seleccion;
	}

	public List<TipoEntidadExterna> getTipoEntidadExs() {
		return tipoEntidadExs;
	}

	public void setTipoEntidadExs(List<TipoEntidadExterna> tipoEntidadExs) {
		this.tipoEntidadExs = tipoEntidadExs;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar *
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		// TODO Auto-generated method stub
		winTipoEntidadExterna.setAttribute(comp.getId() + "ctrl", this);
		servicioTipoEntidadExterna = (ServicioTipoEntidadExterna) SpringUtil
				.getBean("beanServicioTipoEntidadExterna");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinTipoEntidadExterna", this);
		Window win = (Window) Executions.createComponents(
				editTipoEntidadExsterna, null, parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listTipoEntidadEx.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinTipoEntidadExterna", this);
			Window win = (Window) Executions.createComponents(
					editTipoEntidadExsterna, null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listTipoEntidadEx.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioTipoEntidadExterna
							.borrarTipoEntidadExterna(seleccion);
					tipoEntidadExs.remove(seleccion);
					listTipoEntidadEx.setModel(new BindingListModelList(
							tipoEntidadExs, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		tipoEntidadExs = servicioTipoEntidadExterna.buscarTodos('A');
		listTipoEntidadEx.setModel(new BindingListModelList(tipoEntidadExs,
				false));
		buscando = false;
		verTodos = true;
		txtNombre.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombre.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			tipoEntidadExs = servicioTipoEntidadExterna.buscarCoincidencias(
					txtNombre.getValue(), 'A');
			if (tipoEntidadExs.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listTipoEntidadEx.setModel(new BindingListModelList(
						tipoEntidadExs, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listTipoEntidadEx() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombre() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			tipoEntidadExs = servicioTipoEntidadExterna.buscarTodos('A');
		else if (buscando)
			tipoEntidadExs = servicioTipoEntidadExterna.buscarCoincidencias(
					txtNombre.getValue(), 'A');
		else
			tipoEntidadExs.clear();

		listTipoEntidadEx.setModel(new BindingListModelList(tipoEntidadExs,
				false));
	}

	public void apagarBotones() {
		txtNombre.setFocus(true);
		listTipoEntidadEx.clearSelection();
		listTipoEntidadEx.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

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
import org.ucla.sigma.modelo.Tratamiento;
import org.ucla.sigma.servicio.ServicioTratamiento;
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
 * @author promo49
 * 
 */
public class ctrlWinTratamiento extends GenericForwardComposer {

	private Window winTratamiento;
	private Listbox listTratamiento;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private String editTratamiento = "/sigma/vistas/maestros/tratamiento/editTratamiento.zul";
	private ServicioTratamiento servicioTratamiento;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombre.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Tratamiento seleccion;
	private List<Tratamiento> tratamientos = new ArrayList<Tratamiento>();

	public Window getWinTratamiento() {
		return winTratamiento;
	}

	public void setWinTratamiento(Window winTratamiento) {
		this.winTratamiento = winTratamiento;
	}

	public Listbox getListTratamiento() {
		return listTratamiento;
	}

	public void setListTratamiento(Listbox listTratamiento) {
		this.listTratamiento = listTratamiento;
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

	public String getEditTratamiento() {
		return editTratamiento;
	}

	public void setEditTratamiento(String editTratamiento) {
		this.editTratamiento = editTratamiento;
	}

	public ServicioTratamiento getServicioTratamiento() {
		return servicioTratamiento;
	}

	public void setServicioTratamiento(ServicioTratamiento servicioTratamiento) {
		this.servicioTratamiento = servicioTratamiento;
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

	public Tratamiento getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Tratamiento seleccion) {
		this.seleccion = seleccion;
	}

	public List<Tratamiento> getTratamientos() {
		return tratamientos;
	}

	public void setTratamientos(List<Tratamiento> tratamientos) {
		this.tratamientos = tratamientos;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winTratamiento.setAttribute(comp.getId() + "ctrl", this);
		servicioTratamiento = (ServicioTratamiento) SpringUtil
				.getBean("beanServicioTratamiento");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinTratamiento", this);
		Window win = (Window) Executions.createComponents(editTratamiento,
				null, parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listTratamiento.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinTratamiento", this);
			Window win = (Window) Executions.createComponents(editTratamiento,
					null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listTratamiento.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioTratamiento.borrarTratamiento(seleccion);
					tratamientos.remove(seleccion);
					listTratamiento.setModel(new BindingListModelList(
							tratamientos, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		tratamientos = servicioTratamiento.buscarTodos('A');
		listTratamiento.setModel(new BindingListModelList(tratamientos, false));
		buscando = false;
		verTodos = true;
		txtNombre.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombre.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			tratamientos = servicioTratamiento.buscarCoincidencias(
					txtNombre.getValue(), 'A');
			if (tratamientos.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listTratamiento.setModel(new BindingListModelList(tratamientos,
						false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listTratamiento() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombre() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			tratamientos = servicioTratamiento.buscarTodos('A');
		else if (buscando)
			tratamientos = servicioTratamiento.buscarCoincidencias(
					txtNombre.getValue(), 'A');
		else
			tratamientos.clear();

		listTratamiento.setModel(new BindingListModelList(tratamientos, false));
	}

	public void apagarBotones() {
		txtNombre.setFocus(true);
		listTratamiento.clearSelection();
		listTratamiento.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

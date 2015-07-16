package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Especie;
import org.ucla.sigma.servicio.ServicioEspecie;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEspecie extends GenericForwardComposer {

	private Window winEspecie;
	private Listbox listEspecie;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreEspecie;

	// ----------------------------------------------------------------------------------------------------

	private String editEspecie = "/sigma/vistas/maestros/especie/editEspecie.zul";
	private ServicioEspecie servicioEspecie;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreEspecie.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Especie seleccion;
	private List<Especie> especies = new ArrayList<Especie>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinEspecie() {
		return winEspecie;
	}

	public void setWinEspecie(Window winEspecie) {
		this.winEspecie = winEspecie;
	}

	public Listbox getListEspecie() {
		return listEspecie;
	}

	public void setListEspecie(Listbox listEspecie) {
		this.listEspecie = listEspecie;
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

	public ServicioEspecie getServicioEspecie() {
		return servicioEspecie;
	}

	public void setServicioEspecie(ServicioEspecie servicioEspecie) {
		this.servicioEspecie = servicioEspecie;
	}

	public Especie getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Especie seleccion) {
		this.seleccion = seleccion;
	}

	public Textbox getTxtNombreEspecie() {
		return txtNombreEspecie;
	}

	public void setTxtNombreEspecie(Textbox txtNombreEspecie) {
		this.txtNombreEspecie = txtNombreEspecie;
	}

	public List<Especie> getEspecies() {
		return especies;
	}

	public void setEspecies(List<Especie> especies) {
		this.especies = especies;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEspecie.setAttribute(comp.getId() + "ctrl", this);
		servicioEspecie = (ServicioEspecie) SpringUtil
				.getBean("beanServicioEspecie");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinEspecie", this);
		Window win = (Window) Executions.createComponents(editEspecie, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listEspecie.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinEspecie", this);
			Window win = (Window) Executions.createComponents(editEspecie,
					null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listEspecie.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioEspecie.borrarEspecie(seleccion);
					especies.remove(seleccion);
					listEspecie.setModel(new BindingListModelList(especies,
							false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		especies = servicioEspecie.buscarTodos('A');
		listEspecie.setModel(new BindingListModelList(especies, false));
		buscando = false;
		verTodos = true;
		txtNombreEspecie.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreEspecie.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			especies = servicioEspecie.buscarCoincidencias(
					txtNombreEspecie.getValue(), 'A');
			if (especies.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listEspecie.setModel(new BindingListModelList(especies, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listEspecie() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEspecie() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			especies = servicioEspecie.buscarTodos('A');
		else if (buscando)
			especies = servicioEspecie.buscarCoincidencias(
					txtNombreEspecie.getValue(), 'A');
		else
			especies.clear();

		listEspecie.setModel(new BindingListModelList(especies, false));
	}

	public void apagarBotones() {
		txtNombreEspecie.setFocus(true);
		listEspecie.clearSelection();
		listEspecie.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

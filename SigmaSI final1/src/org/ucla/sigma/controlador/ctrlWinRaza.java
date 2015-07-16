package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Raza;
import org.ucla.sigma.servicio.ServicioRaza;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinRaza extends GenericForwardComposer {

	private Window winRaza;
	private Listbox listRaza;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreRaza;

	// ----------------------------------------------------------------------------------------------------

	private String editRaza = "/sigma/vistas/maestros/raza/editRaza.zul";
	private ServicioRaza servicioRaza;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreRaza.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Raza seleccion;
	private List<Raza> razas = new ArrayList<Raza>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinRaza() {
		return winRaza;
	}

	public void setWinRaza(Window winRaza) {
		this.winRaza = winRaza;
	}

	public Listbox getListRaza() {
		return listRaza;
	}

	public void setListRaza(Listbox listRaza) {
		this.listRaza = listRaza;
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

	public ServicioRaza getServicioRaza() {
		return servicioRaza;
	}

	public void setServicioRaza(ServicioRaza servicioRaza) {
		this.servicioRaza = servicioRaza;
	}

	public Raza getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Raza seleccion) {
		this.seleccion = seleccion;
	}

	public Textbox getTxtNombreRaza() {
		return txtNombreRaza;
	}

	public void setTxtNombreRaza(Textbox txtNombreRaza) {
		this.txtNombreRaza = txtNombreRaza;
	}

	public List<Raza> getRazas() {
		return razas;
	}

	public void setRazas(List<Raza> razas) {
		this.razas = razas;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winRaza.setAttribute(comp.getId() + "ctrl", this);
		servicioRaza = (ServicioRaza) SpringUtil.getBean("beanServicioRaza");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinRaza", this);
		Window win = (Window) Executions.createComponents(editRaza, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listRaza.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinRaza", this);
			Window win = (Window) Executions.createComponents(editRaza, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listRaza.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioRaza.borrarRaza(seleccion);
					razas.remove(seleccion);
					listRaza.setModel(new BindingListModelList(razas, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		razas = servicioRaza.buscarTodos('A');
		listRaza.setModel(new BindingListModelList(razas, false));
		buscando = false;
		verTodos = true;
		txtNombreRaza.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreRaza.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			razas = servicioRaza.buscarCoincidencias(txtNombreRaza.getValue(),
					'A');
			if (razas.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listRaza.setModel(new BindingListModelList(razas, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listRaza() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreRaza() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			razas = servicioRaza.buscarTodos('A');
		else if (buscando)
			razas = servicioRaza.buscarCoincidencias(txtNombreRaza.getValue(),
					'A');
		else
			razas.clear();

		listRaza.setModel(new BindingListModelList(razas, false));
	}

	public void apagarBotones() {
		txtNombreRaza.setFocus(true);
		listRaza.clearSelection();
		listRaza.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

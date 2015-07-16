package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Endoscopia;
import org.ucla.sigma.servicio.ServicioEndoscopia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEndoscopia extends GenericForwardComposer {

	private Window winEndoscopia;
	private Listbox listEndoscopia;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtEndoscopia;

	// ----------------------------------------------------------------------------------------------------

	private String editEndoscopia = "/sigma/vistas/maestros/endoscopia/editEndoscopia.zul";
	private ServicioEndoscopia servicioEndoscopia;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtEndoscopia.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Endoscopia seleccion;
	private List<Endoscopia> endoscopias = new ArrayList<Endoscopia>();

	// ----------------------------------------------------------------------------------------------------

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEndoscopia.setAttribute(comp.getId() + "ctrl", this);
		servicioEndoscopia = (ServicioEndoscopia) SpringUtil
				.getBean("beanServicioEndoscopia");
		apagarBotones();
	}

	public Window getWinEndoscopia() {
		return winEndoscopia;
	}

	public void setWinEndoscopia(Window winEndoscopia) {
		this.winEndoscopia = winEndoscopia;
	}

	public Listbox getListEndoscopia() {
		return listEndoscopia;
	}

	public void setListEndoscopia(Listbox listEndoscopia) {
		this.listEndoscopia = listEndoscopia;
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

	public Textbox getTxtEndoscopia() {
		return txtEndoscopia;
	}

	public void setTxtEndoscopia(Textbox txtEndoscopia) {
		this.txtEndoscopia = txtEndoscopia;
	}

	public String getEditEndoscopia() {
		return editEndoscopia;
	}

	public void setEditEndoscopia(String editEndoscopia) {
		this.editEndoscopia = editEndoscopia;
	}

	public ServicioEndoscopia getServicioEndoscopia() {
		return servicioEndoscopia;
	}

	public void setServicioEndoscopia(ServicioEndoscopia servicioEndoscopia) {
		this.servicioEndoscopia = servicioEndoscopia;
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

	public Endoscopia getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Endoscopia seleccion) {
		this.seleccion = seleccion;
	}

	public List<Endoscopia> getEndoscopias() {
		return endoscopias;
	}

	public void setEndoscopias(List<Endoscopia> endoscopias) {
		this.endoscopias = endoscopias;
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinEndoscopia", this);
		Window win = (Window) Executions.createComponents(editEndoscopia, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listEndoscopia.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinEndoscopia", this);
			Window win = (Window) Executions.createComponents(editEndoscopia,
					null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listEndoscopia.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioEndoscopia.borrarEndoscopia(seleccion);
					endoscopias.remove(seleccion);
					listEndoscopia.setModel(new BindingListModelList(
							endoscopias, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		endoscopias = servicioEndoscopia.buscarTodos('A');
		listEndoscopia.setModel(new BindingListModelList(endoscopias, false));
		buscando = false;
		verTodos = true;
		txtEndoscopia.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtEndoscopia.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			endoscopias = servicioEndoscopia.buscarCoincidencias(
					txtEndoscopia.getValue(), 'A');
			if (endoscopias.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listEndoscopia.setModel(new BindingListModelList(endoscopias,
						false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listEndoscopia() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEndoscopia() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			endoscopias = servicioEndoscopia.buscarTodos('A');
		else if (buscando)
			endoscopias = servicioEndoscopia.buscarCoincidencias(
					txtEndoscopia.getValue(), 'A');
		else
			endoscopias.clear();

		listEndoscopia.setModel(new BindingListModelList(endoscopias, false));
	}

	public void apagarBotones() {
		txtEndoscopia.setFocus(true);
		listEndoscopia.clearSelection();
		listEndoscopia.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Ecocardiograma;
import org.ucla.sigma.servicio.ServicioEcocardiograma;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEcocardiograma extends GenericForwardComposer {

	private Window winEcocardiograma;
	private Listbox listEcocardiograma;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtEcocardiograma;

	// ----------------------------------------------------------------------------------------------------

	private String editEcocardiograma = "/sigma/vistas/maestros/ecocardiograma/editEcocardiograma.zul";
	private ServicioEcocardiograma servicioEcocardiograma;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtEcocardiograma.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Ecocardiograma seleccion;
	private List<Ecocardiograma> ecocardiogramas = new ArrayList<Ecocardiograma>();

	// ----------------------------------------------------------------------------------------------------

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEcocardiograma.setAttribute(comp.getId() + "ctrl", this);
		servicioEcocardiograma = (ServicioEcocardiograma) SpringUtil
				.getBean("beanServicioEcocardiograma");
		apagarBotones();
	}

	public Window getWinEcocardiograma() {
		return winEcocardiograma;
	}

	public void setWinEcocardiograma(Window winEcocardiograma) {
		this.winEcocardiograma = winEcocardiograma;
	}

	public Listbox getListEcocardiograma() {
		return listEcocardiograma;
	}

	public void setListEcocardiograma(Listbox listEcocardiograma) {
		this.listEcocardiograma = listEcocardiograma;
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

	public Textbox getTxtEcocardiograma() {
		return txtEcocardiograma;
	}

	public void setTxtEcocardiograma(Textbox txtEcocardiograma) {
		this.txtEcocardiograma = txtEcocardiograma;
	}

	public String getEditEcocardiograma() {
		return editEcocardiograma;
	}

	public void setEditEcocardiograma(String editEcocardiograma) {
		this.editEcocardiograma = editEcocardiograma;
	}

	public ServicioEcocardiograma getServicioEcocardiograma() {
		return servicioEcocardiograma;
	}

	public void setServicioEcocardiograma(
			ServicioEcocardiograma servicioEcocardiograma) {
		this.servicioEcocardiograma = servicioEcocardiograma;
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

	public Ecocardiograma getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Ecocardiograma seleccion) {
		this.seleccion = seleccion;
	}

	public List<Ecocardiograma> getEcocardiogramas() {
		return ecocardiogramas;
	}

	public void setEcocardiogramas(List<Ecocardiograma> ecocardiogramas) {
		this.ecocardiogramas = ecocardiogramas;
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinEcocardiograma", this);
		Window win = (Window) Executions.createComponents(editEcocardiograma,
				null, parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listEcocardiograma.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinEcocardiograma", this);
			Window win = (Window) Executions.createComponents(
					editEcocardiograma, null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listEcocardiograma.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioEcocardiograma.borrarEcocardiograma(seleccion);
					ecocardiogramas.remove(seleccion);
					listEcocardiograma.setModel(new BindingListModelList(
							ecocardiogramas, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}
	}

	public void onClick$btnVerTodos() {
		ecocardiogramas = servicioEcocardiograma.buscarTodos('A');
		listEcocardiograma.setModel(new BindingListModelList(ecocardiogramas,
				false));
		buscando = false;
		verTodos = true;
		txtEcocardiograma.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtEcocardiograma.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			ecocardiogramas = servicioEcocardiograma.buscarCoincidencias(
					txtEcocardiograma.getValue(), 'A');
			if (ecocardiogramas.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listEcocardiograma.setModel(new BindingListModelList(
						ecocardiogramas, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listEcocardiograma() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEcocardiograma() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			ecocardiogramas = servicioEcocardiograma.buscarTodos('A');
		else if (buscando)
			ecocardiogramas = servicioEcocardiograma.buscarCoincidencias(
					txtEcocardiograma.getValue(), 'A');
		else
			ecocardiogramas.clear();

		listEcocardiograma.setModel(new BindingListModelList(ecocardiogramas,
				false));
	}

	public void apagarBotones() {
		txtEcocardiograma.setFocus(true);
		listEcocardiograma.clearSelection();
		listEcocardiograma.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}
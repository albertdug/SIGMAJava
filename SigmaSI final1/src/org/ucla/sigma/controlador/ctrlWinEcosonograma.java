package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Ecosonograma;
import org.ucla.sigma.servicio.ServicioEcosonograma;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEcosonograma extends GenericForwardComposer {

	private Window winEcosonograma;
	private Listbox listEcosonograma;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtEcosonograma;

	// ----------------------------------------------------------------------------------------------------

	private String editEcosonograma = "/sigma/vistas/maestros/ecosonograma/editEcosonograma.zul";
	private ServicioEcosonograma servicioEcosonograma;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtEcosonograma.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Ecosonograma seleccion;
	private List<Ecosonograma> ecosonogramas = new ArrayList<Ecosonograma>();

	// ----------------------------------------------------------------------------------------------------

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEcosonograma.setAttribute(comp.getId() + "ctrl", this);
		servicioEcosonograma = (ServicioEcosonograma) SpringUtil
				.getBean("beanServicioEcosonograma");
		apagarBotones();
	}

	public Window getWinEcosonograma() {
		return winEcosonograma;
	}

	public void setWinEcosonograma(Window winEcosonograma) {
		this.winEcosonograma = winEcosonograma;
	}

	public Listbox getListEcosonograma() {
		return listEcosonograma;
	}

	public void setListEcosonograma(Listbox listEcosonograma) {
		this.listEcosonograma = listEcosonograma;
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

	public Textbox getTxtEcosonograma() {
		return txtEcosonograma;
	}

	public void setTxtEcosonograma(Textbox txtEcosonograma) {
		this.txtEcosonograma = txtEcosonograma;
	}

	public String getEditEcosonograma() {
		return editEcosonograma;
	}

	public void setEditEcosonograma(String editEcosonograma) {
		this.editEcosonograma = editEcosonograma;
	}

	public ServicioEcosonograma getServicioEcosonograma() {
		return servicioEcosonograma;
	}

	public void setServicioEcosonograma(
			ServicioEcosonograma servicioEcosonograma) {
		this.servicioEcosonograma = servicioEcosonograma;
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

	public Ecosonograma getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Ecosonograma seleccion) {
		this.seleccion = seleccion;
	}

	public List<Ecosonograma> getEcosonogramas() {
		return ecosonogramas;
	}

	public void setEcosonogramas(List<Ecosonograma> ecosonogramas) {
		this.ecosonogramas = ecosonogramas;
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinEcosonograma", this);
		Window win = (Window) Executions.createComponents(editEcosonograma,
				null, parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listEcosonograma.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinEcosonograma", this);
			Window win = (Window) Executions.createComponents(editEcosonograma,
					null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listEcosonograma.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioEcosonograma.borrarEcosonograma(seleccion);
					ecosonogramas.remove(seleccion);
					listEcosonograma.setModel(new BindingListModelList(
							ecosonogramas, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}
	}

	public void onClick$btnVerTodos() {
		ecosonogramas = servicioEcosonograma.buscarTodos('A');
		listEcosonograma
				.setModel(new BindingListModelList(ecosonogramas, false));
		buscando = false;
		verTodos = true;
		txtEcosonograma.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtEcosonograma.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			ecosonogramas = servicioEcosonograma.buscarCoincidencias(
					txtEcosonograma.getValue(), 'A');
			if (ecosonogramas.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listEcosonograma.setModel(new BindingListModelList(
						ecosonogramas, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listEcosonograma() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEcosonograma() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			ecosonogramas = servicioEcosonograma.buscarTodos('A');
		else if (buscando)
			ecosonogramas = servicioEcosonograma.buscarCoincidencias(
					txtEcosonograma.getValue(), 'A');
		else
			ecosonogramas.clear();

		listEcosonograma
				.setModel(new BindingListModelList(ecosonogramas, false));
	}

	public void apagarBotones() {
		txtEcosonograma.setFocus(true);
		listEcosonograma.clearSelection();
		listEcosonograma.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

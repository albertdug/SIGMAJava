package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Ganglio;
import org.ucla.sigma.servicio.ServicioGanglio;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinGanglio extends GenericForwardComposer {

	private Window winGanglio;
	private Listbox listGanglio;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreGanglio;

	// ----------------------------------------------------------------------------------------------------

	private String editGanglio = "/sigma/vistas/maestros/ganglios/editGanglios.zul";
	private ServicioGanglio servicioGanglio;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreGanglio.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Ganglio seleccion;
	private List<Ganglio> ganglios = new ArrayList<Ganglio>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinGanglio() {
		return winGanglio;
	}

	public void setWinGanglio(Window winGanglio) {
		this.winGanglio = winGanglio;
	}

	public Listbox getListGanglio() {
		return listGanglio;
	}

	public void setListGanglio(Listbox listGanglio) {
		this.listGanglio = listGanglio;
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

	public ServicioGanglio getServicioGanglio() {
		return servicioGanglio;
	}

	public void setServicioGanglio(ServicioGanglio servicioGanglio) {
		this.servicioGanglio = servicioGanglio;
	}

	public Ganglio getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Ganglio seleccion) {
		this.seleccion = seleccion;
	}

	public Textbox getTxtNombreGanglio() {
		return txtNombreGanglio;
	}

	public void setTxtNombreGanglio(Textbox txtNombreGanglio) {
		this.txtNombreGanglio = txtNombreGanglio;
	}

	public List<Ganglio> getGanglios() {
		return ganglios;
	}

	public void setGanglios(List<Ganglio> ganglios) {
		this.ganglios = ganglios;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winGanglio.setAttribute(comp.getId() + "ctrl", this);
		servicioGanglio = (ServicioGanglio) SpringUtil
				.getBean("beanServicioGanglio");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinGanglio", this);
		Window win = (Window) Executions.createComponents(editGanglio, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listGanglio.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinGanglio", this);
			Window win = (Window) Executions.createComponents(editGanglio,
					null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listGanglio.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioGanglio.borrarGanglio(seleccion);
					ganglios.remove(seleccion);
					listGanglio.setModel(new BindingListModelList(ganglios,
							false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		ganglios = servicioGanglio.buscarTodos('A');
		listGanglio.setModel(new BindingListModelList(ganglios, false));
		buscando = false;
		verTodos = true;
		txtNombreGanglio.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreGanglio.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			ganglios = servicioGanglio.buscarCoincidencias(
					txtNombreGanglio.getValue(), 'A');
			if (ganglios.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listGanglio.setModel(new BindingListModelList(ganglios, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listGanglio() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreGanglio() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			ganglios = servicioGanglio.buscarTodos('A');
		else if (buscando)
			ganglios = servicioGanglio.buscarCoincidencias(
					txtNombreGanglio.getValue(), 'A');
		else
			ganglios.clear();

		listGanglio.setModel(new BindingListModelList(ganglios, false));
	}

	public void apagarBotones() {
		txtNombreGanglio.setFocus(true);
		listGanglio.clearSelection();
		listGanglio.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

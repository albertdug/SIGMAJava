package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.modelo.ParesiaPlejia;
import org.ucla.sigma.servicio.ServicioParesiaPlejia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinParesiaPlejia extends GenericForwardComposer {

	private Window winParesiaPlejia;
	private Listbox listParesiaPlejia;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreParesiaPlejia;

	// ----------------------------------------------------------------------------------------------------

	private String editParesiaPlejia = "/sigma/vistas/maestros/paresiaPlejia/editParesiaPlejia.zul";
	private ServicioParesiaPlejia servicioParesiaPlejia;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreParesiaPlejia.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private ParesiaPlejia seleccion;
	private List<ParesiaPlejia> paresiaPlejias = new ArrayList<ParesiaPlejia>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinParesiaPlejia() {
		return winParesiaPlejia;
	}

	public void setWinParesiaPlejia(Window winParesiaPlejia) {
		this.winParesiaPlejia = winParesiaPlejia;
	}

	public Listbox getListParesiaPlejia() {
		return listParesiaPlejia;
	}

	public void setListParesiaPlejia(Listbox listParesiaPlejia) {
		this.listParesiaPlejia = listParesiaPlejia;
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

	public ServicioParesiaPlejia getServicioParesiaPlejia() {
		return servicioParesiaPlejia;
	}

	public void setServicioParesiaPlejia(ServicioParesiaPlejia servicioParesiaPlejia) {
		this.servicioParesiaPlejia = servicioParesiaPlejia;
	}

	public ParesiaPlejia getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(ParesiaPlejia seleccion) {
		this.seleccion = seleccion;
	}

	public Textbox getTxtNombreParesiaPlejia() {
		return txtNombreParesiaPlejia;
	}

	public void setTxtNombreParesiaPlejia(Textbox txtNombreParesiaPlejia) {
		this.txtNombreParesiaPlejia = txtNombreParesiaPlejia;
	}

	public List<ParesiaPlejia> getParesiaPlejias() {
		return paresiaPlejias;
	}

	public void setParesiaPlejias(List<ParesiaPlejia> paresiaPlejias) {
		this.paresiaPlejias = paresiaPlejias;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winParesiaPlejia.setAttribute(comp.getId() + "ctrl", this);
		servicioParesiaPlejia = (ServicioParesiaPlejia) SpringUtil
				.getBean("beanServicioParesiaPlejia");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinParesiaPlejia", this);
		Window win = (Window) Executions.createComponents(editParesiaPlejia, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listParesiaPlejia.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinParesiaPlejia", this);
			Window win = (Window) Executions.createComponents(editParesiaPlejia, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listParesiaPlejia.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioParesiaPlejia.borrarParesiaPlejia(seleccion);
					paresiaPlejias.remove(seleccion);
					listParesiaPlejia
							.setModel(new BindingListModelList(paresiaPlejias, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		paresiaPlejias = servicioParesiaPlejia.buscarTodos('A');
		listParesiaPlejia.setModel(new BindingListModelList(paresiaPlejias, false));
		buscando = false;
		verTodos = true;
		txtNombreParesiaPlejia.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreParesiaPlejia.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			paresiaPlejias = servicioParesiaPlejia.buscarCoincidencias(
					txtNombreParesiaPlejia.getValue(), 'A');
			if (paresiaPlejias.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
				paresiaPlejias.clear();
				listParesiaPlejia.setModel(new BindingListModelList(paresiaPlejias, false));
			} else {
				listParesiaPlejia.setModel(new BindingListModelList(paresiaPlejias, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listParesiaPlejia() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreParesiaPlejia() {
		apagarBotones();
	}

	public void recargar() {
		seleccion= null;
		if (verTodos)
			paresiaPlejias = servicioParesiaPlejia.buscarTodos('A');
		else if (buscando)
			paresiaPlejias = servicioParesiaPlejia.buscarCoincidencias(
					txtNombreParesiaPlejia.getValue(), 'A');
		else
			paresiaPlejias.clear();

		listParesiaPlejia.setModel(new BindingListModelList(paresiaPlejias, false));
	}

	public void apagarBotones() {
		txtNombreParesiaPlejia.setFocus(true);
		listParesiaPlejia.clearSelection();
		listParesiaPlejia.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

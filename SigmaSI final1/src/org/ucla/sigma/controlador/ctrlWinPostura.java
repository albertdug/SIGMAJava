package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.modelo.Postura;
import org.ucla.sigma.servicio.ServicioPostura;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinPostura extends GenericForwardComposer {

	private Window winPostura;
	private Listbox listPostura;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombrePostura;

	// ----------------------------------------------------------------------------------------------------

	private String editPostura = "/sigma/vistas/maestros/postura/editPostura.zul";
	private ServicioPostura servicioPostura;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombrePostura.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Postura seleccion;
	private List<Postura> posturas = new ArrayList<Postura>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinPostura() {
		return winPostura;
	}

	public void setWinPostura(Window winPostura) {
		this.winPostura = winPostura;
	}

	public Listbox getListPostura() {
		return listPostura;
	}

	public void setListPostura(Listbox listPostura) {
		this.listPostura = listPostura;
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

	public ServicioPostura getServicioPostura() {
		return servicioPostura;
	}

	public void setServicioPostura(ServicioPostura servicioPostura) {
		this.servicioPostura = servicioPostura;
	}

	public Postura getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Postura seleccion) {
		this.seleccion = seleccion;
	}

	public Textbox getTxtNombrePostura() {
		return txtNombrePostura;
	}

	public void setTxtNombrePostura(Textbox txtNombrePostura) {
		this.txtNombrePostura = txtNombrePostura;
	}

	public List<Postura> getPosturas() {
		return posturas;
	}

	public void setPosturas(List<Postura> Posturas) {
		this.posturas = posturas;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winPostura.setAttribute(comp.getId() + "ctrl", this);
		servicioPostura = (ServicioPostura) SpringUtil
				.getBean("beanServicioPostura");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinPostura", this);
		Window win = (Window) Executions.createComponents(editPostura, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listPostura.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinPostura", this);
			Window win = (Window) Executions.createComponents(editPostura, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listPostura.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioPostura.borrarPostura(seleccion);
					posturas.remove(seleccion);
					listPostura
							.setModel(new BindingListModelList(posturas, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		posturas = servicioPostura.buscarTodos('A');
		listPostura.setModel(new BindingListModelList(posturas, false));
		buscando = false;
		verTodos = true;
		txtNombrePostura.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombrePostura.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			posturas = servicioPostura.buscarCoincidencias(
					txtNombrePostura.getValue(), 'A');
			if (posturas.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
				posturas.clear();
				listPostura.setModel(new BindingListModelList(posturas, false));
			} else {
				listPostura.setModel(new BindingListModelList(posturas, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listPostura() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombrePostura() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			posturas = servicioPostura.buscarTodos('A');
		else if (buscando)
			posturas = servicioPostura.buscarCoincidencias(
					txtNombrePostura.getValue(), 'A');
		else
			posturas.clear();

		listPostura.setModel(new BindingListModelList(posturas, false));
	}

	public void apagarBotones() {
		txtNombrePostura.setFocus(true);
		listPostura.clearSelection();
		listPostura.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}
}
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Reflejo;
import org.ucla.sigma.servicio.ServicioReflejo;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinReflejo extends GenericForwardComposer {

	private Window winReflejo;
	private Listbox listReflejo;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreReflejo;

	// ----------------------------------------------------------------------------------------------------

	private String editReflejo = "/sigma/vistas/maestros/reflejo/editReflejo.zul";
	private ServicioReflejo servicioReflejo;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreReflejo.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Reflejo seleccion;
	private List<Reflejo> reflejos = new ArrayList<Reflejo>();

	// ----------------------------------------------------------------------------------------------------

	
	
	public Window getWinReflejo() {
		return winReflejo;
	}

	public void setWinReflejo(Window winReflejo) {
		this.winReflejo = winReflejo;
	}

	

	public Listbox getListReflejo() {
		return listReflejo;
	}

	public void setListReflejo(Listbox listReflejo) {
		this.listReflejo = listReflejo;
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

	public ServicioReflejo getServicioReflejo() {
		return servicioReflejo;
	}

	public void setServicioReflejo(ServicioReflejo servicioReflejo) {
		this.servicioReflejo = servicioReflejo;
	}

	public Reflejo getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Reflejo seleccion) {
		this.seleccion = seleccion;
	}

	public Textbox getTxtNombreReflejo() {
		return txtNombreReflejo;
	}

	public void setTxtNombreReflejo(Textbox txtNombreReflejo) {
		this.txtNombreReflejo = txtNombreReflejo;
	}

	public List<Reflejo> getReflejos() {
		return reflejos;
	}

	public void setReflejos(List<Reflejo> reflejos) {
		this.reflejos = reflejos;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winReflejo.setAttribute(comp.getId() + "ctrl", this);
		servicioReflejo = (ServicioReflejo) SpringUtil
				.getBean("beanServicioReflejo");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinReflejo", this);
		Window win = (Window) Executions.createComponents(editReflejo, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listReflejo.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinReflejo", this);
			Window win = (Window) Executions.createComponents(editReflejo, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listReflejo.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioReflejo.borrarReflejo(seleccion);
					reflejos.remove(seleccion);
					listReflejo
							.setModel(new BindingListModelList(reflejos, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		reflejos = servicioReflejo.buscarTodos('A');
		listReflejo.setModel(new BindingListModelList(reflejos, false));
		buscando = false;
		verTodos = true;
		txtNombreReflejo.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreReflejo.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			reflejos = servicioReflejo.buscarCoincidencias(
					txtNombreReflejo.getValue(), 'A');
			if (reflejos.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listReflejo.setModel(new BindingListModelList(reflejos, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listReflejo() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreReflejo() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			reflejos = servicioReflejo.buscarTodos('A');
		else if (buscando)
			reflejos = servicioReflejo.buscarCoincidencias(
					txtNombreReflejo.getValue(), 'A');
		else
			reflejos.clear();

		listReflejo.setModel(new BindingListModelList(reflejos, false));
	}

	public void apagarBotones() {
		txtNombreReflejo.setFocus(true);
		listReflejo.clearSelection();
		listReflejo.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

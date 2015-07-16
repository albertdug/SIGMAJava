package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.modelo.Ataxia;
import org.ucla.sigma.modelo.Historial;
import org.ucla.sigma.modelo.Neurologia;
import org.ucla.sigma.modelo.Patologia;
import org.ucla.sigma.servicio.ServicioAtaxia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinAtaxia extends GenericForwardComposer {

	private Window winAtaxia;
	private Listbox listAtaxia;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreAtaxia;

	// ----------------------------------------------------------------------------------------------------

	private String editAtaxia = "/sigma/vistas/maestros/ataxia/editAtaxia.zul";
	private ServicioAtaxia servicioAtaxia;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreAtaxia.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Ataxia seleccion;
	private List<Ataxia> ataxias = new ArrayList<Ataxia>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinAtaxia() {
		return winAtaxia;
	}

	public void setWinAtaxia(Window winAtaxia) {
		this.winAtaxia = winAtaxia;
	}

	public Listbox getListAtaxia() {
		return listAtaxia;
	}

	public void setListAtaxia(Listbox listAtaxia) {
		this.listAtaxia = listAtaxia;
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

	public ServicioAtaxia getServicioAtaxia() {
		return servicioAtaxia;
	}

	public void setServicioAtaxia(ServicioAtaxia servicioAtaxia) {
		this.servicioAtaxia = servicioAtaxia;
	}

	public Ataxia getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Ataxia seleccion) {
		this.seleccion = seleccion;
	}

	public Textbox getTxtNombreAtaxia() {
		return txtNombreAtaxia;
	}

	public void setTxtNombreAtaxia(Textbox txtNombreAtaxia) {
		this.txtNombreAtaxia = txtNombreAtaxia;
	}

	public List<Ataxia> getAtaxias() {
		return ataxias;
	}

	public void setAtaxias(List<Ataxia> ataxias) {
		this.ataxias = ataxias;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winAtaxia.setAttribute(comp.getId() + "ctrl", this);
		servicioAtaxia = (ServicioAtaxia) SpringUtil
				.getBean("beanServicioAtaxia");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinAtaxia", this);
		Window win = (Window) Executions.createComponents(editAtaxia, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listAtaxia.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinAtaxia", this);
			Window win = (Window) Executions.createComponents(editAtaxia, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listAtaxia.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioAtaxia.borrarAtaxia(seleccion);
					ataxias.remove(seleccion);
					listAtaxia
							.setModel(new BindingListModelList(ataxias, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		ataxias = servicioAtaxia.buscarTodos('A');
		listAtaxia.setModel(new BindingListModelList(ataxias, false));
		buscando = false;
		verTodos = true;
		txtNombreAtaxia.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreAtaxia.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			ataxias = servicioAtaxia.buscarCoincidencias(
					txtNombreAtaxia.getValue(), 'A');
			if (ataxias.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
				ataxias.clear();
				listAtaxia.setModel(new BindingListModelList(ataxias, false));
			} else {
				listAtaxia.setModel(new BindingListModelList(ataxias, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listAtaxia() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreAtaxia() {
		apagarBotones();
	}

	public void recargar() {
		seleccion= null;
		if (verTodos)
			ataxias = servicioAtaxia.buscarTodos('A');
		else if (buscando)
			ataxias = servicioAtaxia.buscarCoincidencias(
					txtNombreAtaxia.getValue(), 'A');
		else
			ataxias.clear();

		listAtaxia.setModel(new BindingListModelList(ataxias, false));
	}

	public void apagarBotones() {
		txtNombreAtaxia.setFocus(true);
		listAtaxia.clearSelection();
		listAtaxia.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

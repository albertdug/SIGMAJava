package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Membrana;
import org.ucla.sigma.servicio.ServicioMembrana;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * 
 */

/**
 * @author Albert
 * 
 */
public class ctrlWinMembrana extends GenericForwardComposer {

	private Window winMembrana;
	private Listbox listMembrana;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombreMembrana;

	// ----------------------------------------------------------------------------------------------------

	private String editMembrana = "/sigma/vistas/maestros/membrana/editMembrana.zul";
	private ServicioMembrana servicioMembrana;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreMembrana.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Membrana seleccion;
	private List<Membrana> membranas = new ArrayList<Membrana>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinMembrana() {
		return winMembrana;
	}

	public void setWinMembrana(Window winMembrana) {
		this.winMembrana = winMembrana;
	}

	public Listbox getListMembrana() {
		return listMembrana;
	}

	public void setListMembrana(Listbox listMembrana) {
		this.listMembrana = listMembrana;
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

	public ServicioMembrana getServicioMembrana() {
		return servicioMembrana;
	}

	public void setServicioMembrana(ServicioMembrana servicioMembrana) {
		this.servicioMembrana = servicioMembrana;
	}

	public Membrana getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Membrana seleccion) {
		this.seleccion = seleccion;
	}

	public Textbox getTxtNombreMembrana() {
		return txtNombreMembrana;
	}

	public void setTxtNombreMembrana(Textbox txtNombreMembrana) {
		this.txtNombreMembrana = txtNombreMembrana;
	}

	public List<Membrana> getMembranas() {
		return membranas;
	}

	public void setMembranas(List<Membrana> membranas) {
		this.membranas = membranas;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winMembrana.setAttribute(comp.getId() + "ctrl", this);
		servicioMembrana = (ServicioMembrana) SpringUtil
				.getBean("beanServicioMembrana");
		apagarBotones();

	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinMembrana", this);
		Window win = (Window) Executions.createComponents(editMembrana, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listMembrana.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinMembrana", this);
			Window win = (Window) Executions.createComponents(editMembrana,
					null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listMembrana.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioMembrana.borrarMembrana(seleccion);
					membranas.remove(seleccion);
					listMembrana.setModel(new BindingListModelList(membranas,
							false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		membranas = servicioMembrana.buscarTodos('A');
		listMembrana.setModel(new BindingListModelList(membranas, false));
		buscando = false;
		verTodos = true;
		txtNombreMembrana.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreMembrana.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			membranas = servicioMembrana.buscarCoincidencias(
					txtNombreMembrana.getValue(), 'A');
			if (membranas.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listMembrana
						.setModel(new BindingListModelList(membranas, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();

	}

	public void onSelect$listMembrana() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreMembrana() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			membranas = servicioMembrana.buscarTodos('A');
		else if (buscando)
			membranas = servicioMembrana.buscarCoincidencias(
					txtNombreMembrana.getValue(), 'A');
		else
			membranas.clear();

		listMembrana.setModel(new BindingListModelList(membranas, false));
	}

	public void apagarBotones() {
		txtNombreMembrana.setFocus(true);
		listMembrana.clearSelection();
		listMembrana.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

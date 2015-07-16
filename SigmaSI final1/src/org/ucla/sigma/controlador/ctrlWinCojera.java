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
import javax.persistence.ManyToOne;

import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.modelo.Cojera;
import org.ucla.sigma.modelo.Historial;
import org.ucla.sigma.servicio.ServicioCojera;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinCojera extends GenericForwardComposer {

	private Window winCojera;
	private Listbox listCojera;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreCojera;

	// ----------------------------------------------------------------------------------------------------

	private String editCojera = "/sigma/vistas/maestros/cojera/editCojera.zul";
	private ServicioCojera servicioCojera;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreCojera.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Cojera seleccion;
	private List<Cojera> cojeras = new ArrayList<Cojera>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinCojera() {
		return winCojera;
	}

	public void setWinCojera(Window winCojera) {
		this.winCojera = winCojera;
	}

	public Listbox getListCojera() {
		return listCojera;
	}

	public void setListCojera(Listbox listCojera) {
		this.listCojera = listCojera;
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

	public ServicioCojera getServicioCojera() {
		return servicioCojera;
	}

	public void setServicioCojera(ServicioCojera servicioCojera) {
		this.servicioCojera = servicioCojera;
	}

	public Cojera getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Cojera seleccion) {
		this.seleccion = seleccion;
	}

	public Textbox getTxtNombreCojera() {
		return txtNombreCojera;
	}

	public void setTxtNombreCojera(Textbox txtNombreCojera) {
		this.txtNombreCojera = txtNombreCojera;
	}

	public List<Cojera> getCojeras() {
		return cojeras;
	}

	public void setCojeras(List<Cojera> cojeras) {
		this.cojeras = cojeras;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winCojera.setAttribute(comp.getId() + "ctrl", this);
		servicioCojera = (ServicioCojera) SpringUtil
				.getBean("beanServicioCojera");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinCojera", this);
		Window win = (Window) Executions.createComponents(editCojera, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listCojera.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinCojera", this);
			Window win = (Window) Executions.createComponents(editCojera, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listCojera.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioCojera.borrarCojera(seleccion);
					cojeras.remove(seleccion);
					listCojera
							.setModel(new BindingListModelList(cojeras, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		cojeras = servicioCojera.buscarTodos('A');
		listCojera.setModel(new BindingListModelList(cojeras, false));
		buscando = false;
		verTodos = true;
		txtNombreCojera.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreCojera.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			cojeras = servicioCojera.buscarCoincidencias(
					txtNombreCojera.getValue(), 'A');
			if (cojeras.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
				cojeras.clear();
				listCojera.setModel(new BindingListModelList(cojeras, false));
			} else {
				listCojera.setModel(new BindingListModelList(cojeras, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listCojera() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreCojera() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			cojeras = servicioCojera.buscarTodos('A');
		else if (buscando)
			cojeras = servicioCojera.buscarCoincidencias(
					txtNombreCojera.getValue(), 'A');
		else
			cojeras.clear();

		listCojera.setModel(new BindingListModelList(cojeras, false));
	}

	public void apagarBotones() {
		txtNombreCojera.setFocus(true);
		listCojera.clearSelection();
		listCojera.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

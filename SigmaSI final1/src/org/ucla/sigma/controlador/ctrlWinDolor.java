package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.modelo.Dolor;
import org.ucla.sigma.servicio.ServicioDolor;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinDolor extends GenericForwardComposer {

	private Window winDolor;
	private Listbox listDolor;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreDolor;

	// ----------------------------------------------------------------------------------------------------

	private String editDolor = "/sigma/vistas/maestros/dolor/editDolor.zul";
	private ServicioDolor servicioDolor;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreDolor.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Dolor seleccion;
	private List<Dolor> dolors = new ArrayList<Dolor>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinDolor() {
		return winDolor;
	}

	public void setWinDolor(Window winDolor) {
		this.winDolor = winDolor;
	}

	public Listbox getListDolor() {
		return listDolor;
	}

	public void setListDolor(Listbox listDolor) {
		this.listDolor = listDolor;
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

	public ServicioDolor getServicioDolor() {
		return servicioDolor;
	}

	public void setServicioDolor(ServicioDolor servicioDolor) {
		this.servicioDolor = servicioDolor;
	}

	public Dolor getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Dolor seleccion) {
		this.seleccion = seleccion;
	}

	public Textbox getTxtNombreDolor() {
		return txtNombreDolor;
	}

	public void setTxtNombreDolor(Textbox txtNombreDolor) {
		this.txtNombreDolor = txtNombreDolor;
	}

	public List<Dolor> getDolors() {
		return dolors;
	}

	public void setDolors(List<Dolor> dolors) {
		this.dolors = dolors;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winDolor.setAttribute(comp.getId() + "ctrl", this);
		servicioDolor = (ServicioDolor) SpringUtil
				.getBean("beanServicioDolor");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinDolor", this);
		Window win = (Window) Executions.createComponents(editDolor, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listDolor.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinDolor", this);
			Window win = (Window) Executions.createComponents(editDolor, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listDolor.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioDolor.borrarDolor(seleccion);
					dolors.remove(seleccion);
					listDolor
							.setModel(new BindingListModelList(dolors, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		dolors = servicioDolor.buscarTodos('A');
		listDolor.setModel(new BindingListModelList(dolors, false));
		buscando = false;
		verTodos = true;
		txtNombreDolor.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreDolor.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			dolors = servicioDolor.buscarCoincidencias(
					txtNombreDolor.getValue(), 'A');
			if (dolors.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
				dolors.clear();
				listDolor.setModel(new BindingListModelList(dolors, false));
			} else {
				listDolor.setModel(new BindingListModelList(dolors, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listDolor() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreDolor() {
		apagarBotones();
	}

	public void recargar() {
		seleccion= null;
		if (verTodos)
			dolors = servicioDolor.buscarTodos('A');
		else if (buscando)
			dolors = servicioDolor.buscarCoincidencias(
					txtNombreDolor.getValue(), 'A');
		else
			dolors.clear();

		listDolor.setModel(new BindingListModelList(dolors, false));
	}

	public void apagarBotones() {
		txtNombreDolor.setFocus(true);
		listDolor.clearSelection();
		listDolor.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

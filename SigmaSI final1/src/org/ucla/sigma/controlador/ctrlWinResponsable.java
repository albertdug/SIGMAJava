/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Responsable;
import org.ucla.sigma.modelo.Responsable;
import org.ucla.sigma.servicio.ServicioResponsable;
import org.ucla.sigma.servicio.ServicioResponsable;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author rafael
 * 
 */
public class ctrlWinResponsable extends GenericForwardComposer {

	private Window winResponsable;
	private Listbox listResponsable;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Intbox txtCedula;

	// ----------------------------------------------------------------------------------------------------

	private String editResponsable = "/sigma/vistas/maestros/responsable/editResponsable.zul";
	private ServicioResponsable servicioResponsable;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtCedula.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Responsable seleccion;
	private List<Responsable> responsables = new ArrayList<Responsable>();

	public Window getWinResponsable() {
		return winResponsable;
	}

	public void setWinResponsable(Window winResponsable) {
		this.winResponsable = winResponsable;
	}

	public Listbox getListResponsable() {
		return listResponsable;
	}

	public void setListResponsable(Listbox listResponsable) {
		this.listResponsable = listResponsable;
	}

	public Button getBtnVerTodos() {
		return btnVerTodos;
	}

	public void setBtnVerTodos(Button btnVerTodos) {
		this.btnVerTodos = btnVerTodos;
	}

	public Button getBtnEliminar() {
		return btnEliminar;
	}

	public void setBtnEliminar(Button btnEliminar) {
		this.btnEliminar = btnEliminar;
	}

	public Button getBtnEditar() {
		return btnEditar;
	}

	public void setBtnEditar(Button btnEditar) {
		this.btnEditar = btnEditar;
	}

	public Button getBtnNuevo() {
		return btnNuevo;
	}

	public void setBtnNuevo(Button btnNuevo) {
		this.btnNuevo = btnNuevo;
	}

	public Button getBtnBuscar() {
		return btnBuscar;
	}

	public void setBtnBuscar(Button btnBuscar) {
		this.btnBuscar = btnBuscar;
	}

	public Intbox getTxtCedula() {
		return txtCedula;
	}

	public void setTxtCedula(Intbox txtCedula) {
		this.txtCedula = txtCedula;
	}

	public String getEditResponsable() {
		return editResponsable;
	}

	public void setEditResponsable(String editResponsable) {
		this.editResponsable = editResponsable;
	}

	public ServicioResponsable getServicioResponsable() {
		return servicioResponsable;
	}

	public void setServicioResponsable(ServicioResponsable servicioResponsable) {
		this.servicioResponsable = servicioResponsable;
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

	public Responsable getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Responsable seleccion) {
		this.seleccion = seleccion;
	}

	public List<Responsable> getResponsables() {
		return responsables;
	}

	public void setResponsables(List<Responsable> responsables) {
		this.responsables = responsables;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winResponsable.setAttribute(comp.getId() + "ctrl", this);
		seleccion = new Responsable();
		servicioResponsable = (ServicioResponsable) SpringUtil
				.getBean("beanServicioResponsable");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinResponsable", this);
		Window win = (Window) Executions.createComponents(editResponsable,
				null, parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listResponsable.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinResponsable", this);
			Window win = (Window) Executions.createComponents(editResponsable,
					null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listResponsable.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioResponsable.borrarResponsable(seleccion);
					responsables.remove(seleccion);
					listResponsable.setModel(new BindingListModelList(
							responsables, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		responsables = servicioResponsable.buscarTodos('A');
		listResponsable.setModel(new BindingListModelList(responsables, false));
		buscando = false;
		verTodos = true;
		txtCedula.setValue(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtCedula.getValue() == null) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			responsables = servicioResponsable.buscarCoincidencias(
					txtCedula.getValue(), 'A');
			if (responsables.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listResponsable.setModel(new BindingListModelList(responsables,
						false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listResponsable() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombre() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			responsables = servicioResponsable.buscarTodos('A');
		else if (buscando)
			responsables = servicioResponsable.buscarCoincidencias(
					txtCedula.getValue(), 'A');
		else
			responsables.clear();

		listResponsable.setModel(new BindingListModelList(responsables, false));
	}

	public void apagarBotones() {
		txtCedula.setFocus(true);
		listResponsable.clearSelection();
		listResponsable.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

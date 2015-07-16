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
import org.ucla.sigma.modelo.Recepcionista;
import org.ucla.sigma.modelo.Recepcionista;
import org.ucla.sigma.servicio.ServicioRecepcionista;
import org.ucla.sigma.servicio.ServicioRecepcionista;
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
public class ctrlWinRecepcionista extends GenericForwardComposer {

	private Window winRecepcionista;
	private Listbox listRecepcionista;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Intbox txtCedula;

	// ----------------------------------------------------------------------------------------------------

	private String editRecepcionista = "/sigma/vistas/maestros/recepcionista/editRecepcionista.zul";
	private ServicioRecepcionista servicioRecepcionista;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtCedula.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Recepcionista seleccion;
	private List<Recepcionista> recepcionistas = new ArrayList<Recepcionista>();

	public Window getWinRecepcionista() {
		return winRecepcionista;
	}

	public void setWinRecepcionista(Window winRecepcionista) {
		this.winRecepcionista = winRecepcionista;
	}

	public Listbox getListRecepcionista() {
		return listRecepcionista;
	}

	public void setListRecepcionista(Listbox listRecepcionista) {
		this.listRecepcionista = listRecepcionista;
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

	public String getEditRecepcionista() {
		return editRecepcionista;
	}

	public void setEditRecepcionista(String editRecepcionista) {
		this.editRecepcionista = editRecepcionista;
	}

	public ServicioRecepcionista getServicioRecepcionista() {
		return servicioRecepcionista;
	}

	public void setServicioRecepcionista(
			ServicioRecepcionista servicioRecepcionista) {
		this.servicioRecepcionista = servicioRecepcionista;
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

	public Recepcionista getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Recepcionista seleccion) {
		this.seleccion = seleccion;
	}

	public List<Recepcionista> getRecepcionistas() {
		return recepcionistas;
	}

	public void setRecepcionistas(List<Recepcionista> recepcionistas) {
		this.recepcionistas = recepcionistas;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winRecepcionista.setAttribute(comp.getId() + "ctrl", this);
		seleccion = new Recepcionista();
		servicioRecepcionista = (ServicioRecepcionista) SpringUtil
				.getBean("beanServicioRecepcionista");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinRecepcionista", this);
		Window win = (Window) Executions.createComponents(editRecepcionista,
				null, parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listRecepcionista.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinRecepcionista", this);
			Window win = (Window) Executions.createComponents(
					editRecepcionista, null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listRecepcionista.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioRecepcionista.borrarRecepcionista(seleccion);
					recepcionistas.remove(seleccion);
					listRecepcionista.setModel(new BindingListModelList(
							recepcionistas, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		recepcionistas = servicioRecepcionista.buscarTodos('A');
		listRecepcionista.setModel(new BindingListModelList(recepcionistas,
				false));
		buscando = false;
		verTodos = true;
		txtCedula.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtCedula.getValue() == null) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			recepcionistas = servicioRecepcionista.buscarCoincidencias(
					txtCedula.getValue(), 'A');
			if (recepcionistas.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listRecepcionista.setModel(new BindingListModelList(
						recepcionistas, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listRecepcionista() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombre() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			recepcionistas = servicioRecepcionista.buscarTodos('A');
		else if (buscando)
			recepcionistas = servicioRecepcionista.buscarCoincidencias(
					txtCedula.getValue(), 'A');
		else
			recepcionistas.clear();

		listRecepcionista.setModel(new BindingListModelList(recepcionistas,
				false));
	}

	public void apagarBotones() {
		txtCedula.setFocus(true);
		listRecepcionista.clearSelection();
		listRecepcionista.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

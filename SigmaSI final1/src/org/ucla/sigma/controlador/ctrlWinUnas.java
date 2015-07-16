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
import org.ucla.sigma.modelo.Estado;
import org.ucla.sigma.modelo.Unas;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioUnas;
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
 * @author jhoan
 *
 */
public class ctrlWinUnas extends GenericForwardComposer {

	private Window winUnas;
	private Listbox listUnas;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreUnas;

	// ----------------------------------------------------------------------------------------------------

	private String editUnas = "/sigma/vistas/maestros/unas/editUnas.zul";
	private ServicioUnas servicioUnas;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreUnas.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Unas seleccion;
	private List<Unas> unass = new ArrayList<Unas>();
	public Window getWinUnas() {
		return winUnas;
	}
	public void setWinUnas(Window winUnas) {
		this.winUnas = winUnas;
	}

	public Listbox getListUnas() {
		return listUnas;
	}
	public void setListUnas(Listbox listUnas) {
		this.listUnas = listUnas;
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
	public Textbox getTxtNombreUnas() {
		return txtNombreUnas;
	}
	public void setTxtNombreUnas(Textbox txtNombreUnas) {
		this.txtNombreUnas = txtNombreUnas;
	}
	public String getEditUnas() {
		return editUnas;
	}
	public void setEditUnas(String editUnas) {
		this.editUnas = editUnas;
	}
	public ServicioUnas getServicioUnas() {
		return servicioUnas;
	}
	public void setServicioUnas(ServicioUnas servicioUnas) {
		this.servicioUnas = servicioUnas;
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
	public Unas getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(Unas seleccion) {
		this.seleccion = seleccion;
	}
	public List<Unas> getUnass() {
		return unass;
	}
	public void setUnass(List<Unas> unass) {
		this.unass = unass;
	}

	// ----------------------------------------------------------------------------------------------------
	

	
	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winUnas.setAttribute(comp.getId() + "ctrl", this);
		servicioUnas = (ServicioUnas) SpringUtil
				.getBean("beanServicioUnas");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinUnas", this);
		Window win = (Window) Executions.createComponents(editUnas, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listUnas.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinUnas", this);
			Window win = (Window) Executions.createComponents(editUnas, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listUnas.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioUnas.borrarUnas(seleccion);
					unass.remove(seleccion);
					listUnas
							.setModel(new BindingListModelList(unass, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		unass = servicioUnas.buscarTodos('A');
		listUnas.setModel(new BindingListModelList(unass, false));
		buscando = false;
		verTodos = true;
		txtNombreUnas.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreUnas.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			unass = servicioUnas.buscarCoincidencias(
					txtNombreUnas.getValue(), 'A');
			if (unass.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listUnas.setModel(new BindingListModelList(unass, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listUnas() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			unass = servicioUnas.buscarTodos('A');
		else if (buscando)
			unass = servicioUnas.buscarCoincidencias(
					txtNombreUnas.getValue(), 'A');
		else
			unass.clear();

		listUnas.setModel(new BindingListModelList(unass, false));
	}

	public void apagarBotones() {
		txtNombreUnas.setFocus(true);
		listUnas.clearSelection();
		listUnas.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}


}

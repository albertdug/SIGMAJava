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
import org.ucla.sigma.modelo.Configuracion;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioConfiguracion;
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
public class ctrlWinConfiguracion extends GenericForwardComposer {

	private Window winConfiguracion;
	private Listbox listConfiguracion;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreConfiguracion;

	// ----------------------------------------------------------------------------------------------------

	private String editConfiguracion = "/sigma/vistas/maestros/configuracion/editConfiguracion.zul";
	private ServicioConfiguracion servicioConfiguracion;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreConfiguracion.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Configuracion seleccion;
	private List<Configuracion> configuracions = new ArrayList<Configuracion>();
	public Window getWinConfiguracion() {
		return winConfiguracion;
	}
	public void setWinConfiguracion(Window winConfiguracion) {
		this.winConfiguracion = winConfiguracion;
	}

	public Listbox getListConfiguracion() {
		return listConfiguracion;
	}
	public void setListConfiguracion(Listbox listConfiguracion) {
		this.listConfiguracion = listConfiguracion;
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
	public Textbox getTxtNombreConfiguracion() {
		return txtNombreConfiguracion;
	}
	public void setTxtNombreConfiguracion(Textbox txtNombreConfiguracion) {
		this.txtNombreConfiguracion = txtNombreConfiguracion;
	}
	public String getEditConfiguracion() {
		return editConfiguracion;
	}
	public void setEditConfiguracion(String editConfiguracion) {
		this.editConfiguracion = editConfiguracion;
	}
	public ServicioConfiguracion getServicioConfiguracion() {
		return servicioConfiguracion;
	}
	public void setServicioConfiguracion(ServicioConfiguracion servicioConfiguracion) {
		this.servicioConfiguracion = servicioConfiguracion;
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
	public Configuracion getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(Configuracion seleccion) {
		this.seleccion = seleccion;
	}
	public List<Configuracion> getConfiguracions() {
		return configuracions;
	}
	public void setConfiguracions(List<Configuracion> configuracions) {
		this.configuracions = configuracions;
	}

	// ----------------------------------------------------------------------------------------------------
	

	
	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winConfiguracion.setAttribute(comp.getId() + "ctrl", this);
		servicioConfiguracion = (ServicioConfiguracion) SpringUtil
				.getBean("beanServicioConfiguracion");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinConfiguracion", this);
		Window win = (Window) Executions.createComponents(editConfiguracion, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listConfiguracion.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinConfiguracion", this);
			Window win = (Window) Executions.createComponents(editConfiguracion, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listConfiguracion.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioConfiguracion.borrarConfiguracion(seleccion);
					configuracions.remove(seleccion);
					listConfiguracion
							.setModel(new BindingListModelList(configuracions, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		configuracions = servicioConfiguracion.buscarTodos('A');
		listConfiguracion.setModel(new BindingListModelList(configuracions, false));
		buscando = false;
		verTodos = true;
		txtNombreConfiguracion.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreConfiguracion.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			configuracions = servicioConfiguracion.buscarCoincidencias(
					txtNombreConfiguracion.getValue(), 'A');
			if (configuracions.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listConfiguracion.setModel(new BindingListModelList(configuracions, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listConfiguracion() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			configuracions = servicioConfiguracion.buscarTodos('A');
		else if (buscando)
			configuracions = servicioConfiguracion.buscarCoincidencias(
					txtNombreConfiguracion.getValue(), 'A');
		else
			configuracions.clear();

		listConfiguracion.setModel(new BindingListModelList(configuracions, false));
	}

	public void apagarBotones() {
		txtNombreConfiguracion.setFocus(true);
		listConfiguracion.clearSelection();
		listConfiguracion.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}


}

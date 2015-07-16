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
import org.ucla.sigma.modelo.EstadoHidratacion;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioEstadoHidratacion;
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
public class ctrlWinEstadoHidratacion extends GenericForwardComposer {

	private Window winEstadoHidratacion;
	private Listbox listEstadoHidratacion;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreEstadoHidratacion;

	// ----------------------------------------------------------------------------------------------------

	private String editEstadoHidratacion = "/sigma/vistas/maestros/estadohidratacion/editEstadoHidratacion.zul";
	private ServicioEstadoHidratacion servicioEstadoHidratacion;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreEstadoHidratacion.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private EstadoHidratacion seleccion;
	private List<EstadoHidratacion> estadohidratacions = new ArrayList<EstadoHidratacion>();

	public Window getWinEstadoHidratacion() {
		return winEstadoHidratacion;
	}
	public void setWinEstadoHidratacion(Window winEstadoHidratacion) {
		this.winEstadoHidratacion = winEstadoHidratacion;
	}
	public List<EstadoHidratacion> getEstadohidratacions() {
		return estadohidratacions;
	}
	public void setEstadohidratacions(List<EstadoHidratacion> estadohidratacions) {
		this.estadohidratacions = estadohidratacions;
	}
	public Listbox getListEstadoHidratacion() {
		return listEstadoHidratacion;
	}
	public void setListEstadoHidratacion(Listbox listEstadoHidratacion) {
		this.listEstadoHidratacion = listEstadoHidratacion;
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
	public Textbox getTxtNombreEstadoHidratacion() {
		return txtNombreEstadoHidratacion;
	}
	public void setTxtNombreEstadoHidratacion(Textbox txtNombreEstadoHidratacion) {
		this.txtNombreEstadoHidratacion = txtNombreEstadoHidratacion;
	}
	public String getEditEstadoHidratacion() {
		return editEstadoHidratacion;
	}
	public void setEditEstadoHidratacion(String editEstadoHidratacion) {
		this.editEstadoHidratacion = editEstadoHidratacion;
	}
	public ServicioEstadoHidratacion getServicioEstadoHidratacion() {
		return servicioEstadoHidratacion;
	}
	public void setServicioEstadoHidratacion(ServicioEstadoHidratacion servicioEstadoHidratacion) {
		this.servicioEstadoHidratacion = servicioEstadoHidratacion;
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
	public EstadoHidratacion getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(EstadoHidratacion seleccion) {
		this.seleccion = seleccion;
	}
	public List<EstadoHidratacion> getEstadoHidratacions() {
		return estadohidratacions;
	}
	public void setEstadoHidratacions(List<EstadoHidratacion> estadohidratacions) {
		this.estadohidratacions = estadohidratacions;
	}

	// ----------------------------------------------------------------------------------------------------
	

	
	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEstadoHidratacion.setAttribute(comp.getId() + "ctrl", this);
		servicioEstadoHidratacion = (ServicioEstadoHidratacion) SpringUtil
				.getBean("beanServicioEstadoHidratacion");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinEstadoHidratacion", this);
		Window win = (Window) Executions.createComponents(editEstadoHidratacion, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listEstadoHidratacion.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinEstadoHidratacion", this);
			Window win = (Window) Executions.createComponents(editEstadoHidratacion, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listEstadoHidratacion.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioEstadoHidratacion.borrarEstadoHidratacion(seleccion);
					estadohidratacions.remove(seleccion);
					listEstadoHidratacion
							.setModel(new BindingListModelList(estadohidratacions, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		estadohidratacions = servicioEstadoHidratacion.buscarTodos('A');
		listEstadoHidratacion.setModel(new BindingListModelList(estadohidratacions, false));
		buscando = false;
		verTodos = true;
		txtNombreEstadoHidratacion.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreEstadoHidratacion.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			estadohidratacions = servicioEstadoHidratacion.buscarCoincidencias(
					txtNombreEstadoHidratacion.getValue(), 'A');
			if (estadohidratacions.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listEstadoHidratacion.setModel(new BindingListModelList(estadohidratacions, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listEstadoHidratacion() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			estadohidratacions = servicioEstadoHidratacion.buscarTodos('A');
		else if (buscando)
			estadohidratacions = servicioEstadoHidratacion.buscarCoincidencias(
					txtNombreEstadoHidratacion.getValue(), 'A');
		else
			estadohidratacions.clear();

		listEstadoHidratacion.setModel(new BindingListModelList(estadohidratacions, false));
	}

	public void apagarBotones() {
		txtNombreEstadoHidratacion.setFocus(true);
		listEstadoHidratacion.clearSelection();
		listEstadoHidratacion.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}


}

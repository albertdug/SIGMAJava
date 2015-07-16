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
import org.ucla.sigma.modelo.Servicio;
import org.ucla.sigma.servicio.ServicioServicio;
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
 * @author promo49
 * 
 */
public class ctrlWinServicio extends GenericForwardComposer {

	private Window winServicio;
	private Listbox listServicio;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtServicio;

	// ----------------------------------------------------------------------------------------------------

	private String editServicio = "/sigma/vistas/maestros/servicio/editServicio.zul";
	private ServicioServicio servicioServicio;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtServicio.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Servicio seleccion;
	private List<Servicio> servicios = new ArrayList<Servicio>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinServicio() {
		return winServicio;
	}

	public void setWinServicio(Window winServicio) {
		this.winServicio = winServicio;
	}

	public Listbox getListServicio() {
		return listServicio;
	}

	public void setListServicio(Listbox listServicio) {
		this.listServicio = listServicio;
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

	public Textbox getTxtServicio() {
		return txtServicio;
	}

	public void setTxtServicio(Textbox txtServicio) {
		this.txtServicio = txtServicio;
	}
	public String getEditServicio() {
		return editServicio;
	}

	public void setEditServicio(String editServicio) {
		this.editServicio = editServicio;
	}

	public ServicioServicio getServicioServicio() {
		return servicioServicio;
	}

	public void setServicioServicio(ServicioServicio servicioServicio) {
		this.servicioServicio = servicioServicio;
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

	public Servicio getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Servicio seleccion) {
		this.seleccion = seleccion;
	}

	public List<Servicio> getServicios() {
		return servicios;
	}

	public void setServicios(List<Servicio> servicios) {
		this.servicios = servicios;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winServicio.setAttribute(comp.getId() + "ctrl", this);
		servicioServicio = (ServicioServicio) SpringUtil
				.getBean("beanServicioServicio");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinServicio", this);
		Window win = (Window) Executions.createComponents(editServicio, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listServicio.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinServicio", this);
			Window win = (Window) Executions.createComponents(editServicio, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listServicio.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioServicio.borrarServicio(seleccion);
					servicios.remove(seleccion);
					listServicio
							.setModel(new BindingListModelList(servicios, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		servicios = servicioServicio.buscarTodos('A');
		listServicio.setModel(new BindingListModelList(servicios, false));
		buscando = false;
		verTodos = true;
		txtServicio.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtServicio.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			servicios = servicioServicio.buscarCoincidencias(
					txtServicio.getValue(), 'A');
			if (servicios.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listServicio.setModel(new BindingListModelList(servicios, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listServicio() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtServicio() {
		apagarBotones();
	}

	public void recargar() {
		if (verTodos)
			servicios = servicioServicio.buscarTodos('A');
		else if (buscando)
			servicios = servicioServicio.buscarCoincidencias(
					txtServicio.getValue(), 'A');
		else
			servicios.clear();

		listServicio.setModel(new BindingListModelList(servicios, false));
	}

	public void apagarBotones() {
		txtServicio.setFocus(true);
		listServicio.clearSelection();
		listServicio.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}
	
	

}

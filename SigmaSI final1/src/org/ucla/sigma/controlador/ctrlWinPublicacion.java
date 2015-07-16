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
import org.ucla.sigma.modelo.Publicacion;
import org.ucla.sigma.servicio.ServicioPublicacion;
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
 * @author Albert
 *
 */
public class ctrlWinPublicacion extends GenericForwardComposer {

	private Window winPublicacion;
	private Listbox listPublicacion;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtTitulo;

	
	private String editPublicacion = "/sigma/vistas/portal/publicacion/editPublicacion.zul";
	private ServicioPublicacion servicioPublicacion;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtTitulo.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Publicacion seleccion;
	private List<Publicacion> publicacion = new ArrayList<Publicacion>();	

	// ----------------------------------------------------------------------------------------------------

	
	
	
	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winPublicacion.setAttribute(comp.getId() + "ctrl", this);
		servicioPublicacion = (ServicioPublicacion) SpringUtil
				.getBean("beanServicioPublicacion");
		apagarBotones();
	}

	public Window getWinPublicacion() {
		return winPublicacion;
	}

	public void setWinPublicacion(Window winPublicacion) {
		this.winPublicacion = winPublicacion;
	}

	public Listbox getListPublicacion() {
		return listPublicacion;
	}

	public void setListPublicacion(Listbox listPublicacion) {
		this.listPublicacion = listPublicacion;
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

	public Textbox getTxtTitulo() {
		return txtTitulo;
	}

	public void setTxtTitulo(Textbox txtTitulo) {
		this.txtTitulo = txtTitulo;
	}

	public String getEditPublicacion() {
		return editPublicacion;
	}

	public void setEditPublicacion(String editPublicacion) {
		this.editPublicacion = editPublicacion;
	}

	public ServicioPublicacion getServicioPublicacion() {
		return servicioPublicacion;
	}

	public void setServicioPublicacion(ServicioPublicacion servicioPublicacion) {
		this.servicioPublicacion = servicioPublicacion;
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

	public Publicacion getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Publicacion seleccion) {
		this.seleccion = seleccion;
	}

	public List<Publicacion> getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(List<Publicacion> publicacion) {
		this.publicacion = publicacion;
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinPublicacion", this);
		Window win = (Window) Executions.createComponents(editPublicacion,
				null, parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listPublicacion.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinPublicacion", this);
			Window win = (Window) Executions.createComponents(
					editPublicacion, null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listPublicacion.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioPublicacion.borrarPublicacion(seleccion);
					publicacion.remove(seleccion);
					listPublicacion.setModel(new BindingListModelList(publicacion,
							false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		publicacion = servicioPublicacion.buscarTodos('A');
		listPublicacion.setModel(new BindingListModelList(publicacion, false));
		buscando = false;
		verTodos = true;
		txtTitulo.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtTitulo.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			publicacion = servicioPublicacion.buscarCoincidencias(
					txtTitulo.getValue(), 'A');
			if (publicacion.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listPublicacion.setModel(new BindingListModelList(publicacion,
						false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listPublicacion() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtTitulo() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			publicacion = servicioPublicacion.buscarTodos('A');
		else if (buscando)
			publicacion = servicioPublicacion.buscarCoincidencias(
					txtTitulo.getValue(), 'A');
		else
			publicacion.clear();

		listPublicacion.setModel(new BindingListModelList(publicacion, false));
	}

	public void apagarBotones() {
		txtTitulo.setFocus(true);
		listPublicacion.clearSelection();
		listPublicacion.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}


}

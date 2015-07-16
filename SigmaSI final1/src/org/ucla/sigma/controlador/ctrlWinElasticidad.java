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
import org.ucla.sigma.modelo.Elasticidad;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioElasticidad;
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
public class ctrlWinElasticidad extends GenericForwardComposer {

	private Window winElasticidad;
	private Listbox listElasticidad;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreElasticidad;

	// ----------------------------------------------------------------------------------------------------

	private String editElasticidad = "/sigma/vistas/maestros/elasticidad/editElasticidad.zul";
	private ServicioElasticidad servicioElasticidad;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreElasticidad.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Elasticidad seleccion;
	private List<Elasticidad> elasticidads = new ArrayList<Elasticidad>();
	public Window getWinElasticidad() {
		return winElasticidad;
	}
	public void setWinElasticidad(Window winElasticidad) {
		this.winElasticidad = winElasticidad;
	}

	public Listbox getListElasticidad() {
		return listElasticidad;
	}
	public void setListElasticidad(Listbox listElasticidad) {
		this.listElasticidad = listElasticidad;
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
	public Textbox getTxtNombreElasticidad() {
		return txtNombreElasticidad;
	}
	public void setTxtNombreElasticidad(Textbox txtNombreElasticidad) {
		this.txtNombreElasticidad = txtNombreElasticidad;
	}
	public String getEditElasticidad() {
		return editElasticidad;
	}
	public void setEditElasticidad(String editElasticidad) {
		this.editElasticidad = editElasticidad;
	}
	public ServicioElasticidad getServicioElasticidad() {
		return servicioElasticidad;
	}
	public void setServicioElasticidad(ServicioElasticidad servicioElasticidad) {
		this.servicioElasticidad = servicioElasticidad;
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
	public Elasticidad getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(Elasticidad seleccion) {
		this.seleccion = seleccion;
	}
	public List<Elasticidad> getElasticidads() {
		return elasticidads;
	}
	public void setElasticidads(List<Elasticidad> elasticidads) {
		this.elasticidads = elasticidads;
	}

	// ----------------------------------------------------------------------------------------------------
	

	
	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winElasticidad.setAttribute(comp.getId() + "ctrl", this);
		servicioElasticidad = (ServicioElasticidad) SpringUtil
				.getBean("beanServicioElasticidad");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinElasticidad", this);
		Window win = (Window) Executions.createComponents(editElasticidad, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listElasticidad.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinElasticidad", this);
			Window win = (Window) Executions.createComponents(editElasticidad, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listElasticidad.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioElasticidad.borrarElasticidad(seleccion);
					elasticidads.remove(seleccion);
					listElasticidad
							.setModel(new BindingListModelList(elasticidads, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		elasticidads = servicioElasticidad.buscarTodos('A');
		listElasticidad.setModel(new BindingListModelList(elasticidads, false));
		buscando = false;
		verTodos = true;
		txtNombreElasticidad.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreElasticidad.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			elasticidads = servicioElasticidad.buscarCoincidencias(
					txtNombreElasticidad.getValue(), 'A');
			if (elasticidads.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listElasticidad.setModel(new BindingListModelList(elasticidads, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listElasticidad() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			elasticidads = servicioElasticidad.buscarTodos('A');
		else if (buscando)
			elasticidads = servicioElasticidad.buscarCoincidencias(
					txtNombreElasticidad.getValue(), 'A');
		else
			elasticidads.clear();

		listElasticidad.setModel(new BindingListModelList(elasticidads, false));
	}

	public void apagarBotones() {
		txtNombreElasticidad.setFocus(true);
		listElasticidad.clearSelection();
		listElasticidad.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}


}



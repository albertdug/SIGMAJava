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
import org.ucla.sigma.modelo.Espesor;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioEspesor;
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
public class ctrlWinEspesor extends GenericForwardComposer {

	private Window winEspesor;
	private Listbox listEspesor;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreEspesor;

	// ----------------------------------------------------------------------------------------------------

	private String editEspesor = "/sigma/vistas/maestros/espesor/editEspesor.zul";
	private ServicioEspesor servicioEspesor;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreEspesor.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Espesor seleccion;
	private List<Espesor> espesors = new ArrayList<Espesor>();
	public Window getWinEspesor() {
		return winEspesor;
	}
	public void setWinEspesor(Window winEspesor) {
		this.winEspesor = winEspesor;
	}

	public Listbox getListEspesor() {
		return listEspesor;
	}
	public void setListEspesor(Listbox listEspesor) {
		this.listEspesor = listEspesor;
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
	public Textbox getTxtNombreEspesor() {
		return txtNombreEspesor;
	}
	public void setTxtNombreEspesor(Textbox txtNombreEspesor) {
		this.txtNombreEspesor = txtNombreEspesor;
	}
	public String getEditEspesor() {
		return editEspesor;
	}
	public void setEditEspesor(String editEspesor) {
		this.editEspesor = editEspesor;
	}
	public ServicioEspesor getServicioEspesor() {
		return servicioEspesor;
	}
	public void setServicioEspesor(ServicioEspesor servicioEspesor) {
		this.servicioEspesor = servicioEspesor;
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
	public Espesor getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(Espesor seleccion) {
		this.seleccion = seleccion;
	}
	public List<Espesor> getEspesors() {
		return espesors;
	}
	public void setEspesors(List<Espesor> espesors) {
		this.espesors = espesors;
	}

	// ----------------------------------------------------------------------------------------------------
	

	
	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEspesor.setAttribute(comp.getId() + "ctrl", this);
		servicioEspesor = (ServicioEspesor) SpringUtil
				.getBean("beanServicioEspesor");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinEspesor", this);
		Window win = (Window) Executions.createComponents(editEspesor, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listEspesor.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinEspesor", this);
			Window win = (Window) Executions.createComponents(editEspesor, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listEspesor.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioEspesor.borrarEspesor(seleccion);
					espesors.remove(seleccion);
					listEspesor
							.setModel(new BindingListModelList(espesors, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		espesors = servicioEspesor.buscarTodos('A');
		listEspesor.setModel(new BindingListModelList(espesors, false));
		buscando = false;
		verTodos = true;
		txtNombreEspesor.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreEspesor.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			espesors = servicioEspesor.buscarCoincidencias(
					txtNombreEspesor.getValue(), 'A');
			if (espesors.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listEspesor.setModel(new BindingListModelList(espesors, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listEspesor() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			espesors = servicioEspesor.buscarTodos('A');
		else if (buscando)
			espesors = servicioEspesor.buscarCoincidencias(
					txtNombreEspesor.getValue(), 'A');
		else
			espesors.clear();

		listEspesor.setModel(new BindingListModelList(espesors, false));
	}

	public void apagarBotones() {
		txtNombreEspesor.setFocus(true);
		listEspesor.clearSelection();
		listEspesor.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}


}



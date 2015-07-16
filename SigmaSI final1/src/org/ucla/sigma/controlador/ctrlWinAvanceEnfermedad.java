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
import org.ucla.sigma.modelo.AvanceEnfermedad;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioAvanceEnfermedad;
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
public class ctrlWinAvanceEnfermedad extends GenericForwardComposer {

	private Window winAvanceEnfermedad;
	private Listbox listAvanceEnfermedad;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreAvanceEnfermedad;

	// ----------------------------------------------------------------------------------------------------

	private String editAvanceEnfermedad = "/sigma/vistas/maestros/avanceenfermedad/editAvanceEnfermedad.zul";
	private ServicioAvanceEnfermedad servicioAvanceEnfermedad;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreAvanceEnfermedad.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private AvanceEnfermedad seleccion;
	private List<AvanceEnfermedad> avanceenfermedads = new ArrayList<AvanceEnfermedad>();
	public Window getWinAvanceEnfermedad() {
		return winAvanceEnfermedad;
	}
	public void setWinAvanceEnfermedad(Window winAvanceEnfermedad) {
		this.winAvanceEnfermedad = winAvanceEnfermedad;
	}

	public Listbox getListAvanceEnfermedad() {
		return listAvanceEnfermedad;
	}
	public void setListAvanceEnfermedad(Listbox listAvanceEnfermedad) {
		this.listAvanceEnfermedad = listAvanceEnfermedad;
	}
	public List<AvanceEnfermedad> getAvanceenfermedads() {
		return avanceenfermedads;
	}
	public void setAvanceenfermedads(List<AvanceEnfermedad> avanceenfermedads) {
		this.avanceenfermedads = avanceenfermedads;
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
	public Textbox getTxtNombreAvanceEnfermedad() {
		return txtNombreAvanceEnfermedad;
	}
	public void setTxtNombreAvanceEnfermedad(Textbox txtNombreAvanceEnfermedad) {
		this.txtNombreAvanceEnfermedad = txtNombreAvanceEnfermedad;
	}
	public String getEditAvanceEnfermedad() {
		return editAvanceEnfermedad;
	}
	public void setEditAvanceEnfermedad(String editAvanceEnfermedad) {
		this.editAvanceEnfermedad = editAvanceEnfermedad;
	}
	public ServicioAvanceEnfermedad getServicioAvanceEnfermedad() {
		return servicioAvanceEnfermedad;
	}
	public void setServicioAvanceEnfermedad(ServicioAvanceEnfermedad servicioAvanceEnfermedad) {
		this.servicioAvanceEnfermedad = servicioAvanceEnfermedad;
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
	public AvanceEnfermedad getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(AvanceEnfermedad seleccion) {
		this.seleccion = seleccion;
	}
	public List<AvanceEnfermedad> getAvanceEnfermedads() {
		return avanceenfermedads;
	}
	public void setAvanceEnfermedads(List<AvanceEnfermedad> avanceenfermedads) {
		this.avanceenfermedads = avanceenfermedads;
	}

	// ----------------------------------------------------------------------------------------------------
	

	
	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winAvanceEnfermedad.setAttribute(comp.getId() + "ctrl", this);
		servicioAvanceEnfermedad = (ServicioAvanceEnfermedad) SpringUtil
				.getBean("beanServicioAvanceEnfermedad");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinAvanceEnfermedad", this);
		Window win = (Window) Executions.createComponents(editAvanceEnfermedad, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listAvanceEnfermedad.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinAvanceEnfermedad", this);
			Window win = (Window) Executions.createComponents(editAvanceEnfermedad, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listAvanceEnfermedad.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioAvanceEnfermedad.borrarAvanceEnfermedad(seleccion);
					avanceenfermedads.remove(seleccion);
					listAvanceEnfermedad
							.setModel(new BindingListModelList(avanceenfermedads, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		avanceenfermedads = servicioAvanceEnfermedad.buscarTodos('A');
		listAvanceEnfermedad.setModel(new BindingListModelList(avanceenfermedads, false));
		buscando = false;
		verTodos = true;
		txtNombreAvanceEnfermedad.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreAvanceEnfermedad.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			avanceenfermedads = servicioAvanceEnfermedad.buscarCoincidencias(
					txtNombreAvanceEnfermedad.getValue(), 'A');
			if (avanceenfermedads.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listAvanceEnfermedad.setModel(new BindingListModelList(avanceenfermedads, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listAvanceEnfermedad() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			avanceenfermedads = servicioAvanceEnfermedad.buscarTodos('A');
		else if (buscando)
			avanceenfermedads = servicioAvanceEnfermedad.buscarCoincidencias(
					txtNombreAvanceEnfermedad.getValue(), 'A');
		else
			avanceenfermedads.clear();

		listAvanceEnfermedad.setModel(new BindingListModelList(avanceenfermedads, false));
	}

	public void apagarBotones() {
		txtNombreAvanceEnfermedad.setFocus(true);
		listAvanceEnfermedad.clearSelection();
		listAvanceEnfermedad.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}


}

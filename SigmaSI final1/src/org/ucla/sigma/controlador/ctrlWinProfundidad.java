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
import org.ucla.sigma.modelo.Profundidad;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioProfundidad;
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
public class ctrlWinProfundidad extends GenericForwardComposer {

	private Window winProfundidad;
	private Listbox listProfundidad;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreProfundidad;

	// ----------------------------------------------------------------------------------------------------

	private String editProfundidad = "/sigma/vistas/maestros/profundidad/editProfundidad.zul";
	private ServicioProfundidad servicioProfundidad;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreProfundidad.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Profundidad seleccion;
	private List<Profundidad> profundidads = new ArrayList<Profundidad>();
	public Window getWinProfundidad() {
		return winProfundidad;
	}
	public void setWinProfundidad(Window winProfundidad) {
		this.winProfundidad = winProfundidad;
	}
	
	public Listbox getListProfundidad() {
		return listProfundidad;
	}
	public void setListProfundidad(Listbox listProfundidad) {
		this.listProfundidad = listProfundidad;
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
	public Textbox getTxtNombreProfundidad() {
		return txtNombreProfundidad;
	}
	public void setTxtNombreProfundidad(Textbox txtNombreProfundidad) {
		this.txtNombreProfundidad = txtNombreProfundidad;
	}
	public String getEditProfundidad() {
		return editProfundidad;
	}
	public void setEditProfundidad(String editProfundidad) {
		this.editProfundidad = editProfundidad;
	}
	public ServicioProfundidad getServicioProfundidad() {
		return servicioProfundidad;
	}
	public void setServicioProfundidad(ServicioProfundidad servicioProfundidad) {
		this.servicioProfundidad = servicioProfundidad;
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
	public Profundidad getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(Profundidad seleccion) {
		this.seleccion = seleccion;
	}
	public List<Profundidad> getProfundidads() {
		return profundidads;
	}
	public void setProfundidads(List<Profundidad> profundidads) {
		this.profundidads = profundidads;
	}

	// ----------------------------------------------------------------------------------------------------
	

	
	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winProfundidad.setAttribute(comp.getId() + "ctrl", this);
		servicioProfundidad = (ServicioProfundidad) SpringUtil
				.getBean("beanServicioProfundidad");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinProfundidad", this);
		Window win = (Window) Executions.createComponents(editProfundidad, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listProfundidad.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinProfundidad", this);
			Window win = (Window) Executions.createComponents(editProfundidad, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listProfundidad.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioProfundidad.borrarProfundidad(seleccion);
					profundidads.remove(seleccion);
					listProfundidad
							.setModel(new BindingListModelList(profundidads, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		profundidads = servicioProfundidad.buscarTodos('A');
		listProfundidad.setModel(new BindingListModelList(profundidads, false));
		buscando = false;
		verTodos = true;
		txtNombreProfundidad.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreProfundidad.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			profundidads = servicioProfundidad.buscarCoincidencias(
					txtNombreProfundidad.getValue(), 'A');
			if (profundidads.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listProfundidad.setModel(new BindingListModelList(profundidads, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listProfundidad() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			profundidads = servicioProfundidad.buscarTodos('A');
		else if (buscando)
			profundidads = servicioProfundidad.buscarCoincidencias(
					txtNombreProfundidad.getValue(), 'A');
		else
			profundidads.clear();

		listProfundidad.setModel(new BindingListModelList(profundidads, false));
	}

	public void apagarBotones() {
		txtNombreProfundidad.setFocus(true);
		listProfundidad.clearSelection();
		listProfundidad.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}


}

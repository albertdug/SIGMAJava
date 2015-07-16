package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Radiografia;
import org.ucla.sigma.servicio.ServicioRadiografia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinRadiografia extends GenericForwardComposer {

	private Window winRadiografia;
	private Listbox listRadiografia;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtRadiografia;

	// ----------------------------------------------------------------------------------------------------

	private String editRadiografia = "/sigma/vistas/maestros/radiografia/editRadiografia.zul";
	private ServicioRadiografia servicioRadiografia;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtRadiografia.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Radiografia seleccion;
	private List<Radiografia> radiografias = new ArrayList<Radiografia>();

	// ----------------------------------------------------------------------------------------------------

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winRadiografia.setAttribute(comp.getId() + "ctrl", this);
		servicioRadiografia = (ServicioRadiografia) SpringUtil
				.getBean("beanServicioRadiografia");
		apagarBotones();
	}

	public Window getWinRadiografia() {
		return winRadiografia;
	}

	public void setWinRadiografia(Window winRadiografia) {
		this.winRadiografia = winRadiografia;
	}

	public Listbox getListRadiografia() {
		return listRadiografia;
	}

	public void setListRadiografia(Listbox listRadiografia) {
		this.listRadiografia = listRadiografia;
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

	public Textbox getTxtRadiografia() {
		return txtRadiografia;
	}

	public void setTxtRadiografia(Textbox txtRadiografia) {
		this.txtRadiografia = txtRadiografia;
	}

	public String getEditRadiografia() {
		return editRadiografia;
	}

	public void setEditRadiografia(String editRadiografia) {
		this.editRadiografia = editRadiografia;
	}

	public ServicioRadiografia getServicioRadiografia() {
		return servicioRadiografia;
	}

	public void setServicioRadiografia(ServicioRadiografia servicioRadiografia) {
		this.servicioRadiografia = servicioRadiografia;
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

	public Radiografia getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Radiografia seleccion) {
		this.seleccion = seleccion;
	}

	public List<Radiografia> getRadiografias() {
		return radiografias;
	}

	public void setRadiografias(List<Radiografia> radiografias) {
		this.radiografias = radiografias;
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinRadiografia", this);
		Window win = (Window) Executions.createComponents(editRadiografia,
				null, parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listRadiografia.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinRadiografia", this);
			Window win = (Window) Executions.createComponents(editRadiografia,
					null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listRadiografia.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioRadiografia.borrarRadiografia(seleccion);
					radiografias.remove(seleccion);
					listRadiografia.setModel(new BindingListModelList(
							radiografias, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		radiografias = servicioRadiografia.buscarTodos('A');
		listRadiografia.setModel(new BindingListModelList(radiografias, false));
		buscando = false;
		verTodos = true;
		txtRadiografia.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtRadiografia.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			radiografias = servicioRadiografia.buscarCoincidencias(
					txtRadiografia.getValue(), 'A');
			if (radiografias.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listRadiografia.setModel(new BindingListModelList(radiografias,
						false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listRadiografia() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			radiografias = servicioRadiografia.buscarTodos('A');
		else if (buscando)
			radiografias = servicioRadiografia.buscarCoincidencias(
					txtRadiografia.getValue(), 'A');
		else
			radiografias.clear();

		listRadiografia.setModel(new BindingListModelList(radiografias, false));
	}

	public void apagarBotones() {
		txtRadiografia.setFocus(true);
		listRadiografia.clearSelection();
		listRadiografia.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

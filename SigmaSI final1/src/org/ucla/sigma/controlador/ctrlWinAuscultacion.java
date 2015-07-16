package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Auscultacion;
import org.ucla.sigma.servicio.ServicioAuscultacion;
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
 * 
 */

/**
 * @author Albert
 * 
 */
public class ctrlWinAuscultacion extends GenericForwardComposer {

	private Window winAuscultacion;
	private Listbox listAuscultacion;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombreAuscultacion;

	// ----------------------------------------------------------------------------------------------------

	private String editAuscultacion = "/sigma/vistas/maestros/auscultacion/editAuscultacion.zul";
	private ServicioAuscultacion servicioAuscultacion;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreAuscultacion.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Auscultacion seleccion;
	private List<Auscultacion> auscultacions = new ArrayList<Auscultacion>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinAuscultacion() {
		return winAuscultacion;
	}

	public void setWinAuscultacion(Window winAuscultacion) {
		this.winAuscultacion = winAuscultacion;
	}

	public Listbox getListAuscultacion() {
		return listAuscultacion;
	}

	public void setListAuscultacion(Listbox listAuscultacion) {
		this.listAuscultacion = listAuscultacion;
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

	public ServicioAuscultacion getServicioAuscultacion() {
		return servicioAuscultacion;
	}

	public void setServicioAuscultacion(
			ServicioAuscultacion servicioAuscultacion) {
		this.servicioAuscultacion = servicioAuscultacion;
	}

	public Auscultacion getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Auscultacion seleccion) {
		this.seleccion = seleccion;
	}

	public Textbox getTxtNombreAuscultacion() {
		return txtNombreAuscultacion;
	}

	public void setTxtNombreAuscultacion(Textbox txtNombreAuscultacion) {
		this.txtNombreAuscultacion = txtNombreAuscultacion;
	}

	public List<Auscultacion> getAuscultacions() {
		return auscultacions;
	}

	public void setAuscultacions(List<Auscultacion> auscultacions) {
		this.auscultacions = auscultacions;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winAuscultacion.setAttribute(comp.getId() + "ctrl", this);
		servicioAuscultacion = (ServicioAuscultacion) SpringUtil
				.getBean("beanServicioAuscultacion");
		apagarBotones();

	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinAuscultacion", this);
		Window win = (Window) Executions.createComponents(editAuscultacion,
				null, parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listAuscultacion.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinAuscultacion", this);
			Window win = (Window) Executions.createComponents(editAuscultacion,
					null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listAuscultacion.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioAuscultacion.borrarAuscultacion(seleccion);
					auscultacions.remove(seleccion);
					listAuscultacion.setModel(new BindingListModelList(
							auscultacions, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		auscultacions = servicioAuscultacion.buscarTodos('A');
		listAuscultacion
				.setModel(new BindingListModelList(auscultacions, false));
		buscando = false;
		verTodos = true;
		txtNombreAuscultacion.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreAuscultacion.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			auscultacions = servicioAuscultacion.buscarCoincidencias(
					txtNombreAuscultacion.getValue(), 'A');
			if (auscultacions.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listAuscultacion.setModel(new BindingListModelList(
						auscultacions, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();

	}

	public void onSelect$listAuscultacion() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreAuscultacion() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			auscultacions = servicioAuscultacion.buscarTodos('A');
		else if (buscando)
			auscultacions = servicioAuscultacion.buscarCoincidencias(
					txtNombreAuscultacion.getValue(), 'A');
		else
			auscultacions.clear();

		listAuscultacion
				.setModel(new BindingListModelList(auscultacions, false));
	}

	public void apagarBotones() {
		txtNombreAuscultacion.setFocus(true);
		listAuscultacion.clearSelection();
		listAuscultacion.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

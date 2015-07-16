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
import org.ucla.sigma.modelo.FrecuenciaBano;
import org.ucla.sigma.servicio.ServicioFrecuenciaBano;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;



public class ctrlWinFrecuenciaBano extends GenericForwardComposer {

	private Window winFrecuenciaBano;
	private Listbox listFrecuenciaBano;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombreFrecuenciaBano;

	// ----------------------------------------------------------------------------------------------------

	private String editFrecuenciaBano = "/sigma/vistas/maestros/frecuenciaBano/editFrecuenciaBano.zul";
	private ServicioFrecuenciaBano servicioFrecuenciaBano;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreFrecuenciaBano.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private FrecuenciaBano seleccion;
	private List<FrecuenciaBano> frecuenciaBanos = new ArrayList<FrecuenciaBano>();

	public Window getWinFrecuenciaBano() {
		return winFrecuenciaBano;
	}

	public void setWinFrecuenciaBano(Window winFrecuenciaBano) {
		this.winFrecuenciaBano = winFrecuenciaBano;
	}

	public Listbox getListFrecuenciaBano() {
		return listFrecuenciaBano;
	}

	public void setListFrecuenciaBano(Listbox listFrecuenciaBano) {
		this.listFrecuenciaBano = listFrecuenciaBano;
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

	public Textbox getTxtNombreFrecuenciaBano() {
		return txtNombreFrecuenciaBano;
	}

	public void setTxtNombreFrecuenciaBano(Textbox txtNombreFrecuenciaBano) {
		this.txtNombreFrecuenciaBano = txtNombreFrecuenciaBano;
	}

	public String getEditFrecuenciaBano() {
		return editFrecuenciaBano;
	}

	public void setEditFrecuenciaBano(String editFrecuenciaBano) {
		this.editFrecuenciaBano = editFrecuenciaBano;
	}

	public ServicioFrecuenciaBano getServicioFrecuenciaBano() {
		return servicioFrecuenciaBano;
	}

	public void setServicioFrecuenciaBano(ServicioFrecuenciaBano servicioFrecuenciaBano) {
		this.servicioFrecuenciaBano = servicioFrecuenciaBano;
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

	public FrecuenciaBano getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(FrecuenciaBano seleccion) {
		this.seleccion = seleccion;
	}

	public List<FrecuenciaBano> getFrecuenciaBanos() {
		return frecuenciaBanos;
	}

	public void setFrecuenciaBanos(List<FrecuenciaBano> frecuenciaBanos) {
		this.frecuenciaBanos = frecuenciaBanos;
	}

	// ----------------------------------------------------------------------------------------------------

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winFrecuenciaBano.setAttribute(comp.getId() + "ctrl", this);
		servicioFrecuenciaBano = (ServicioFrecuenciaBano) SpringUtil
				.getBean("beanServicioFrecuenciaBano");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinFrecuenciaBano", this);
		Window win = (Window) Executions.createComponents(editFrecuenciaBano, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listFrecuenciaBano.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinFrecuenciaBano", this);
			Window win = (Window) Executions.createComponents(editFrecuenciaBano,
					null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listFrecuenciaBano.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioFrecuenciaBano.borrarFrecuenciaBano(seleccion);
					frecuenciaBanos.remove(seleccion);
					listFrecuenciaBano.setModel(new BindingListModelList(
							frecuenciaBanos, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		frecuenciaBanos = servicioFrecuenciaBano.buscarTodos('A');
		listFrecuenciaBano.setModel(new BindingListModelList(frecuenciaBanos, false));
		buscando = false;
		verTodos = true;
		txtNombreFrecuenciaBano.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreFrecuenciaBano.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			frecuenciaBanos = servicioFrecuenciaBano.buscarCoincidencias(
					txtNombreFrecuenciaBano.getValue(), 'A');
			if (frecuenciaBanos.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listFrecuenciaBano.setModel(new BindingListModelList(frecuenciaBanos,
						false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listFrecuenciaBano() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreFrecuenciaBano() {
		apagarBotones();
	}

	public void recargar() {

		seleccion = null;

		if (verTodos)
			frecuenciaBanos = servicioFrecuenciaBano.buscarTodos('A');
		else if (buscando)
			frecuenciaBanos = servicioFrecuenciaBano.buscarCoincidencias(
					txtNombreFrecuenciaBano.getValue(), 'A');
		else
			frecuenciaBanos.clear();

		listFrecuenciaBano.setModel(new BindingListModelList(frecuenciaBanos, false));
	}

	public void apagarBotones() {
		txtNombreFrecuenciaBano.setFocus(true);
		listFrecuenciaBano.clearSelection();
		listFrecuenciaBano.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

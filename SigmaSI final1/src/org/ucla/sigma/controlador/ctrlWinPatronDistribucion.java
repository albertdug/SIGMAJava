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
import org.ucla.sigma.modelo.PatronDistribucion;
import org.ucla.sigma.servicio.ServicioPatronDistribucion;
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
public class ctrlWinPatronDistribucion extends GenericForwardComposer {

	private Window winPatronDistribucion;
	private Listbox listPatronDistribucion;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombrePatronDistribucion;

	// ----------------------------------------------------------------------------------------------------

	private String editPatronDistribucion = "/sigma/vistas/maestros/patronDistribucion/editPatronDistribucion.zul";
	private ServicioPatronDistribucion servicioPatronDistribucion;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombrePatronDistribucion.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private PatronDistribucion seleccion;
	private List<PatronDistribucion> patrondistribucions = new ArrayList<PatronDistribucion>();

	public Window getWinPatronDistribucion() {
		return winPatronDistribucion;
	}
	public void setWinPatronDistribucion(Window winPatronDistribucion) {
		this.winPatronDistribucion = winPatronDistribucion;
	}
	public List<PatronDistribucion> getPatrondistribucions() {
		return patrondistribucions;
	}
	public void setPatrondistribucions(List<PatronDistribucion> patrondistribucions) {
		this.patrondistribucions = patrondistribucions;
	}
	public Listbox getListPatronDistribucion() {
		return listPatronDistribucion;
	}
	public void setListPatronDistribucion(Listbox listPatronDistribucion) {
		this.listPatronDistribucion = listPatronDistribucion;
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
	public Textbox getTxtNombrePatronDistribucion() {
		return txtNombrePatronDistribucion;
	}
	public void setTxtNombrePatronDistribucion(Textbox txtNombrePatronDistribucion) {
		this.txtNombrePatronDistribucion = txtNombrePatronDistribucion;
	}
	public String getEditPatronDistribucion() {
		return editPatronDistribucion;
	}
	public void setEditPatronDistribucion(String editPatronDistribucion) {
		this.editPatronDistribucion = editPatronDistribucion;
	}
	public ServicioPatronDistribucion getServicioPatronDistribucion() {
		return servicioPatronDistribucion;
	}
	public void setServicioPatronDistribucion(ServicioPatronDistribucion servicioPatronDistribucion) {
		this.servicioPatronDistribucion = servicioPatronDistribucion;
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
	public PatronDistribucion getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(PatronDistribucion seleccion) {
		this.seleccion = seleccion;
	}
	public List<PatronDistribucion> getPatronDistribucions() {
		return patrondistribucions;
	}
	public void setPatronDistribucions(List<PatronDistribucion> patrondistribucions) {
		this.patrondistribucions = patrondistribucions;
	}

	// ----------------------------------------------------------------------------------------------------
	

	
	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winPatronDistribucion.setAttribute(comp.getId() + "ctrl", this);
		servicioPatronDistribucion = (ServicioPatronDistribucion) SpringUtil
				.getBean("beanServicioPatronDistribucion");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinPatronDistribucion", this);
		Window win = (Window) Executions.createComponents(editPatronDistribucion, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listPatronDistribucion.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinPatronDistribucion", this);
			Window win = (Window) Executions.createComponents(editPatronDistribucion, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listPatronDistribucion.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioPatronDistribucion.borrarPatronDistribucion(seleccion);
					patrondistribucions.remove(seleccion);
					listPatronDistribucion
							.setModel(new BindingListModelList(patrondistribucions, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		patrondistribucions = servicioPatronDistribucion.buscarTodos('A');
		listPatronDistribucion.setModel(new BindingListModelList(patrondistribucions, false));
		buscando = false;
		verTodos = true;
		txtNombrePatronDistribucion.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombrePatronDistribucion.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			patrondistribucions = servicioPatronDistribucion.buscarCoincidencias(
					txtNombrePatronDistribucion.getValue(), 'A');
			if (patrondistribucions.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listPatronDistribucion.setModel(new BindingListModelList(patrondistribucions, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listPatronDistribucion() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			patrondistribucions = servicioPatronDistribucion.buscarTodos('A');
		else if (buscando)
			patrondistribucions = servicioPatronDistribucion.buscarCoincidencias(
					txtNombrePatronDistribucion.getValue(), 'A');
		else
			patrondistribucions.clear();

		listPatronDistribucion.setModel(new BindingListModelList(patrondistribucions, false));
	}

	public void apagarBotones() {
		txtNombrePatronDistribucion.setFocus(true);
		listPatronDistribucion.clearSelection();
		listPatronDistribucion.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}


}

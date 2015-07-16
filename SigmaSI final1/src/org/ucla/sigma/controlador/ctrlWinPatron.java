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
import org.ucla.sigma.modelo.Patron;
import org.ucla.sigma.servicio.ServicioPatron;
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
public class ctrlWinPatron extends GenericForwardComposer {

	private Window winPatron;
	private Listbox listPatron;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombrePatron;

	// ----------------------------------------------------------------------------------------------------

	private String editPatron = "/sigma/vistas/maestros/patron/editPatron.zul";
	private ServicioPatron servicioPatron;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombrePatron.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Patron seleccion;
	private List<Patron> patrons = new ArrayList<Patron>();

	public Window getWinPatron() {
		return winPatron;
	}

	public void setWinPatron(Window winPatron) {
		this.winPatron = winPatron;
	}

	public List<Patron> getPatron() {
		return patrons;
	}

	public void setPatron(List<Patron> patrons) {
		this.patrons = patrons;
	}

	public Listbox getListPatron() {
		return listPatron;
	}

	public void setListPatron(Listbox listPatron) {
		this.listPatron = listPatron;
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

	public Textbox getTxtNombrePatron() {
		return txtNombrePatron;
	}

	public void setTxtNombrePatron(Textbox txtNombrePatron) {
		this.txtNombrePatron = txtNombrePatron;
	}

	public String getEditPatron() {
		return editPatron;
	}

	public void setEditPatron(String editPatron) {
		this.editPatron = editPatron;
	}

	public ServicioPatron getServicioPatron() {
		return servicioPatron;
	}

	public void setServicioPatron(ServicioPatron servicioPatron) {
		this.servicioPatron = servicioPatron;
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

	public Patron getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Patron seleccion) {
		this.seleccion = seleccion;
	}

	public List<Patron> getPatrons() {
		return patrons;
	}

	public void setPatrons(List<Patron> patrons) {
		this.patrons = patrons;
	}

	// ----------------------------------------------------------------------------------------------------

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winPatron.setAttribute(comp.getId() + "ctrl", this);
		servicioPatron = (ServicioPatron) SpringUtil
				.getBean("beanServicioPatron");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinPatron", this);
		Window win = (Window) Executions.createComponents(editPatron, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listPatron.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinPatron", this);
			Window win = (Window) Executions.createComponents(editPatron, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listPatron.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioPatron.borrarPatron(seleccion);
					patrons.remove(seleccion);
					listPatron
							.setModel(new BindingListModelList(patrons, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		patrons = servicioPatron.buscarTodos('A');
		listPatron.setModel(new BindingListModelList(patrons, false));
		buscando = false;
		verTodos = true;
		txtNombrePatron.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombrePatron.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			patrons = servicioPatron.buscarCoincidencias(
					txtNombrePatron.getValue(), 'A');
			if (patrons.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listPatron.setModel(new BindingListModelList(patrons, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listPatron() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			patrons = servicioPatron.buscarTodos('A');
		else if (buscando)
			patrons = servicioPatron.buscarCoincidencias(
					txtNombrePatron.getValue(), 'A');
		else
			patrons.clear();

		listPatron.setModel(new BindingListModelList(patrons, false));
	}

	public void apagarBotones() {
		txtNombrePatron.setFocus(true);
		listPatron.clearSelection();
		listPatron.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

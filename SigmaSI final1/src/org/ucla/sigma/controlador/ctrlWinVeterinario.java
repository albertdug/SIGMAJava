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
import org.ucla.sigma.modelo.Veterinario;
import org.ucla.sigma.servicio.ServicioVeterinario;
import org.ucla.sigma.servicio.ServicioVeterinario;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author rafael
 * 
 */
public class ctrlWinVeterinario extends GenericForwardComposer {

	private Window winVeterinario;
	private Listbox listVeterinario;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Intbox txtCedula;

	// ----------------------------------------------------------------------------------------------------

	private String editVeterinario = "/sigma/vistas/maestros/veterinario/editVeterinario.zul";
	private ServicioVeterinario servicioVeterinario;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtCedula.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Veterinario seleccion;
	private List<Veterinario> veterinarios = new ArrayList<Veterinario>();

	public Window getWinVeterinario() {
		return winVeterinario;
	}

	public void setWinVeterinario(Window winVeterinario) {
		this.winVeterinario = winVeterinario;
	}

	public Listbox getListVeterinario() {
		return listVeterinario;
	}

	public void setListVeterinario(Listbox listVeterinario) {
		this.listVeterinario = listVeterinario;
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
	
	public Intbox getTxtCedula() {
		return txtCedula;
	}

	public void setTxtCedula(Intbox txtCedula) {
		this.txtCedula = txtCedula;
	}

	public String getEditVeterinario() {
		return editVeterinario;
	}

	public void setEditVeterinario(String editVeterinario) {
		this.editVeterinario = editVeterinario;
	}

	public ServicioVeterinario getServicioVeterinario() {
		return servicioVeterinario;
	}

	public void setServicioVeterinario(ServicioVeterinario servicioVeterinario) {
		this.servicioVeterinario = servicioVeterinario;
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

	public Veterinario getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Veterinario seleccion) {
		this.seleccion = seleccion;
	}

	public List<Veterinario> getVeterinarios() {
		return veterinarios;
	}

	public void setVeterinarios(List<Veterinario> veterinarios) {
		this.veterinarios = veterinarios;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winVeterinario.setAttribute(comp.getId() + "ctrl", this);
		seleccion = new Veterinario();
		servicioVeterinario = (ServicioVeterinario) SpringUtil
				.getBean("beanServicioVeterinario");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinVeterinario", this);
		Window win = (Window) Executions.createComponents(editVeterinario,
				null, parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listVeterinario.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinVeterinario", this);
			Window win = (Window) Executions.createComponents(editVeterinario,
					null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listVeterinario.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioVeterinario.borrarVeterinario(seleccion);
					veterinarios.remove(seleccion);
					listVeterinario.setModel(new BindingListModelList(
							veterinarios, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		veterinarios = servicioVeterinario.buscarTodos('A');
		listVeterinario.setModel(new BindingListModelList(veterinarios, false));
		buscando = false;
		verTodos = true;
		txtCedula.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtCedula.getValue() == null) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			veterinarios = servicioVeterinario.buscarCoincidencias(
					txtCedula.getValue(), 'A');
			if (veterinarios.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listVeterinario.setModel(new BindingListModelList(veterinarios,
						false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listVeterinario() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombre() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			veterinarios = servicioVeterinario.buscarTodos('A');
		else if (buscando)
			veterinarios = servicioVeterinario.buscarCoincidencias(
					txtCedula.getValue(), 'A');
		else
			veterinarios.clear();

		listVeterinario.setModel(new BindingListModelList(veterinarios, false));
	}

	public void apagarBotones() {
		txtCedula.setFocus(true);
		listVeterinario.clearSelection();
		listVeterinario.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

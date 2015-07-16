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
import org.ucla.sigma.modelo.OtrasCaracteristicas;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioOtrasCaracteristicas;
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
public class ctrlWinOtrasCaracteristicas extends GenericForwardComposer {

	private Window winOtrasCaracteristicas;
	private Listbox listOtrasCaracteristicas;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreOtrasCaracteristicas;

	// ----------------------------------------------------------------------------------------------------

	private String editOtrasCaracteristicas = "/sigma/vistas/maestros/otrascaracteristicas/editOtrasCaracteristicas.zul";
	private ServicioOtrasCaracteristicas servicioOtrasCaracteristicas;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreOtrasCaracteristicas.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private OtrasCaracteristicas seleccion;
	private List<OtrasCaracteristicas> otrascaracteristicass = new ArrayList<OtrasCaracteristicas>();
	
	public Window getWinOtrasCaracteristicas() {
		return winOtrasCaracteristicas;
	}
	public void setWinOtrasCaracteristicas(Window winOtrasCaracteristicas) {
		this.winOtrasCaracteristicas = winOtrasCaracteristicas;
	}
	public List<OtrasCaracteristicas> getOtrascaracteristicass() {
		return otrascaracteristicass;
	}
	public void setOtrascaracteristicass(
			List<OtrasCaracteristicas> otrascaracteristicass) {
		this.otrascaracteristicass = otrascaracteristicass;
	}
	public Listbox getListOtrasCaracteristicas() {
		return listOtrasCaracteristicas;
	}
	public void setListOtrasCaracteristicas(Listbox listOtrasCaracteristicas) {
		this.listOtrasCaracteristicas = listOtrasCaracteristicas;
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
	public Textbox getTxtNombreOtrasCaracteristicas() {
		return txtNombreOtrasCaracteristicas;
	}
	public void setTxtNombreOtrasCaracteristicas(Textbox txtNombreOtrasCaracteristicas) {
		this.txtNombreOtrasCaracteristicas = txtNombreOtrasCaracteristicas;
	}
	public String getEditOtrasCaracteristicas() {
		return editOtrasCaracteristicas;
	}
	public void setEditOtrasCaracteristicas(String editOtrasCaracteristicas) {
		this.editOtrasCaracteristicas = editOtrasCaracteristicas;
	}
	public ServicioOtrasCaracteristicas getServicioOtrasCaracteristicas() {
		return servicioOtrasCaracteristicas;
	}
	public void setServicioOtrasCaracteristicas(ServicioOtrasCaracteristicas servicioOtrasCaracteristicas) {
		this.servicioOtrasCaracteristicas = servicioOtrasCaracteristicas;
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
	public OtrasCaracteristicas getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(OtrasCaracteristicas seleccion) {
		this.seleccion = seleccion;
	}
	public List<OtrasCaracteristicas> getOtrasCaracteristicass() {
		return otrascaracteristicass;
	}
	public void setOtrasCaracteristicass(List<OtrasCaracteristicas> otrascaracteristicass) {
		this.otrascaracteristicass = otrascaracteristicass;
	}

	// ----------------------------------------------------------------------------------------------------
	

	
	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winOtrasCaracteristicas.setAttribute(comp.getId() + "ctrl", this);
		servicioOtrasCaracteristicas = (ServicioOtrasCaracteristicas) SpringUtil
				.getBean("beanServicioOtrasCaracteristicas");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinOtrasCaracteristicas", this);
		Window win = (Window) Executions.createComponents(editOtrasCaracteristicas, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listOtrasCaracteristicas.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinOtrasCaracteristicas", this);
			Window win = (Window) Executions.createComponents(editOtrasCaracteristicas, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listOtrasCaracteristicas.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioOtrasCaracteristicas.borrarOtrasCaracteristicas(seleccion);
					otrascaracteristicass.remove(seleccion);
					listOtrasCaracteristicas
							.setModel(new BindingListModelList(otrascaracteristicass, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		otrascaracteristicass = servicioOtrasCaracteristicas.buscarTodos('A');
		listOtrasCaracteristicas.setModel(new BindingListModelList(otrascaracteristicass, false));
		buscando = false;
		verTodos = true;
		txtNombreOtrasCaracteristicas.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreOtrasCaracteristicas.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			otrascaracteristicass = servicioOtrasCaracteristicas.buscarCoincidencias(
					txtNombreOtrasCaracteristicas.getValue(), 'A');
			if (otrascaracteristicass.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listOtrasCaracteristicas.setModel(new BindingListModelList(otrascaracteristicass, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listOtrasCaracteristicas() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			otrascaracteristicass = servicioOtrasCaracteristicas.buscarTodos('A');
		else if (buscando)
			otrascaracteristicass = servicioOtrasCaracteristicas.buscarCoincidencias(
					txtNombreOtrasCaracteristicas.getValue(), 'A');
		else
			otrascaracteristicass.clear();

		listOtrasCaracteristicas.setModel(new BindingListModelList(otrascaracteristicass, false));
	}

	public void apagarBotones() {
		txtNombreOtrasCaracteristicas.setFocus(true);
		listOtrasCaracteristicas.clearSelection();
		listOtrasCaracteristicas.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}


}

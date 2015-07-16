package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Ritmo;
import org.ucla.sigma.servicio.ServicioRitmo;
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
public class ctrlWinRitmo extends GenericForwardComposer {

	private Window winRitmo;
	private Listbox listRitmo;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombreRitmo;

	// ----------------------------------------------------------------------------------------------------

	private String editRitmo = "/sigma/vistas/maestros/ritmo/editRitmo.zul";
	private ServicioRitmo servicioRitmo;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreRitmo.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Ritmo seleccion;
	private List<Ritmo> ritmos = new ArrayList<Ritmo>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinRitmo() {
		return winRitmo;
	}

	public void setWinRitmo(Window winRitmo) {
		this.winRitmo = winRitmo;
	}

	public Listbox getListRitmo() {
		return listRitmo;
	}

	public void setListRitmo(Listbox listRitmo) {
		this.listRitmo = listRitmo;
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

	public ServicioRitmo getServicioRitmo() {
		return servicioRitmo;
	}

	public void setServicioRitmo(ServicioRitmo servicioRitmo) {
		this.servicioRitmo = servicioRitmo;
	}

	public Ritmo getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Ritmo seleccion) {
		this.seleccion = seleccion;
	}

	public Textbox getTxtNombreRitmo() {
		return txtNombreRitmo;
	}

	public void setTxtNombreRitmo(Textbox txtNombreRitmo) {
		this.txtNombreRitmo = txtNombreRitmo;
	}

	public List<Ritmo> getRitmos() {
		return ritmos;
	}

	public void setRitmos(List<Ritmo> ritmos) {
		this.ritmos = ritmos;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winRitmo.setAttribute(comp.getId() + "ctrl", this);
		servicioRitmo = (ServicioRitmo) SpringUtil.getBean("beanServicioRitmo");
		apagarBotones();

	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinRitmo", this);
		Window win = (Window) Executions.createComponents(editRitmo, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listRitmo.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinRitmo", this);
			Window win = (Window) Executions.createComponents(editRitmo, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listRitmo.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioRitmo.borrarRitmo(seleccion);
					ritmos.remove(seleccion);
					listRitmo.setModel(new BindingListModelList(ritmos, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		ritmos = servicioRitmo.buscarTodos('A');
		listRitmo.setModel(new BindingListModelList(ritmos, false));
		buscando = false;
		verTodos = true;
		txtNombreRitmo.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreRitmo.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			ritmos = servicioRitmo.buscarCoincidencias(
					txtNombreRitmo.getValue(), 'A');
			if (ritmos.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listRitmo.setModel(new BindingListModelList(ritmos, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();

	}

	public void onSelect$listRitmo() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreRitmo() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			ritmos = servicioRitmo.buscarTodos('A');
		else if (buscando)
			ritmos = servicioRitmo.buscarCoincidencias(
					txtNombreRitmo.getValue(), 'A');
		else
			ritmos.clear();

		listRitmo.setModel(new BindingListModelList(ritmos, false));
	}

	public void apagarBotones() {
		txtNombreRitmo.setFocus(true);
		listRitmo.clearSelection();
		listRitmo.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

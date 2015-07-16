package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Cardiaca;
import org.ucla.sigma.servicio.ServicioCardiaca;
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
public class ctrlWinCardiaca extends GenericForwardComposer {

	private Window winCardiaca;
	private Listbox listCardiaca;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombreCardiaca;

	// ----------------------------------------------------------------------------------------------------

	private String editCardiaca = "/sigma/vistas/maestros/cardiaca/editCardiaca.zul";
	private ServicioCardiaca servicioCardiaca;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreCardiaca.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Cardiaca seleccion;
	private List<Cardiaca> cardiacas = new ArrayList<Cardiaca>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinCardiaca() {
		return winCardiaca;
	}

	public void setWinCardiaca(Window winCardiaca) {
		this.winCardiaca = winCardiaca;
	}

	public Listbox getListCardiaca() {
		return listCardiaca;
	}

	public void setListCardiaca(Listbox listCardiaca) {
		this.listCardiaca = listCardiaca;
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

	public ServicioCardiaca getServicioCardiaca() {
		return servicioCardiaca;
	}

	public void setServicioCardiaca(ServicioCardiaca servicioCardiaca) {
		this.servicioCardiaca = servicioCardiaca;
	}

	public Cardiaca getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Cardiaca seleccion) {
		this.seleccion = seleccion;
	}

	public Textbox getTxtNombreCardiaca() {
		return txtNombreCardiaca;
	}

	public void setTxtNombreCardiaca(Textbox txtNombreCardiaca) {
		this.txtNombreCardiaca = txtNombreCardiaca;
	}

	public List<Cardiaca> getCardiacas() {
		return cardiacas;
	}

	public void setCardiacas(List<Cardiaca> cardiacas) {
		this.cardiacas = cardiacas;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winCardiaca.setAttribute(comp.getId() + "ctrl", this);
		servicioCardiaca = (ServicioCardiaca) SpringUtil
				.getBean("beanServicioCardiaca");
		apagarBotones();

	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinCardiaca", this);
		Window win = (Window) Executions.createComponents(editCardiaca, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listCardiaca.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinCardiaca", this);
			Window win = (Window) Executions.createComponents(editCardiaca,
					null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listCardiaca.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioCardiaca.borrarCardiaca(seleccion);
					cardiacas.remove(seleccion);
					listCardiaca.setModel(new BindingListModelList(cardiacas,
							false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		cardiacas = servicioCardiaca.buscarTodos('A');
		listCardiaca.setModel(new BindingListModelList(cardiacas, false));
		buscando = false;
		verTodos = true;
		txtNombreCardiaca.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreCardiaca.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			cardiacas = servicioCardiaca.buscarCoincidencias(
					txtNombreCardiaca.getValue(), 'A');
			if (cardiacas.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listCardiaca
						.setModel(new BindingListModelList(cardiacas, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();

	}

	public void onSelect$listCardiaca() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreCardiaca() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			cardiacas = servicioCardiaca.buscarTodos('A');
		else if (buscando)
			cardiacas = servicioCardiaca.buscarCoincidencias(
					txtNombreCardiaca.getValue(), 'A');
		else
			cardiacas.clear();

		listCardiaca.setModel(new BindingListModelList(cardiacas, false));
	}

	public void apagarBotones() {
		txtNombreCardiaca.setFocus(true);
		listCardiaca.clearSelection();
		listCardiaca.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

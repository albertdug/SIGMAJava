package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Estado;
import org.ucla.sigma.servicio.ServicioEstado;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEstado extends GenericForwardComposer {

	private Window winEstado;
	private Listbox listEstado;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreEstado;

	// ----------------------------------------------------------------------------------------------------

	private String editEstado = "/sigma/vistas/maestros/estado/editEstado.zul";
	private ServicioEstado servicioEstado;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreEstado.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Estado seleccion;
	private List<Estado> estados = new ArrayList<Estado>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinEstado() {
		return winEstado;
	}

	public void setWinEstado(Window winEstado) {
		this.winEstado = winEstado;
	}

	public Listbox getListEstado() {
		return listEstado;
	}

	public void setListEstado(Listbox listEstado) {
		this.listEstado = listEstado;
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

	public ServicioEstado getServicioEstado() {
		return servicioEstado;
	}

	public void setServicioEstado(ServicioEstado servicioEstado) {
		this.servicioEstado = servicioEstado;
	}

	public Estado getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Estado seleccion) {
		this.seleccion = seleccion;
	}

	public Textbox getTxtNombreEstado() {
		return txtNombreEstado;
	}

	public void setTxtNombreEstado(Textbox txtNombreEstado) {
		this.txtNombreEstado = txtNombreEstado;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEstado.setAttribute(comp.getId() + "ctrl", this);
		servicioEstado = (ServicioEstado) SpringUtil
				.getBean("beanServicioEstado");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinEstado", this);
		Window win = (Window) Executions.createComponents(editEstado, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listEstado.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinEstado", this);
			Window win = (Window) Executions.createComponents(editEstado, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listEstado.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioEstado.borrarEstado(seleccion);
					estados.remove(seleccion);
					listEstado
							.setModel(new BindingListModelList(estados, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		estados = servicioEstado.buscarTodos('A');
		listEstado.setModel(new BindingListModelList(estados, false));
		buscando = false;
		verTodos = true;
		txtNombreEstado.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreEstado.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			estados = servicioEstado.buscarCoincidencias(
					txtNombreEstado.getValue(), 'A');
			if (estados.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listEstado.setModel(new BindingListModelList(estados, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listEstado() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
		System.out.println("mas wuju y yeah!");
		Set collection = listEstado.getSelectedItems();
		for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
			Listitem valor = (Listitem) iterator.next();
			Estado est = (Estado) valor.getValue();
			System.out.println(est.getNombre());
		}
		System.out.println(collection.size());
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			estados = servicioEstado.buscarTodos('A');
		else if (buscando)
			estados = servicioEstado.buscarCoincidencias(
					txtNombreEstado.getValue(), 'A');
		else
			estados.clear();

		listEstado.setModel(new BindingListModelList(estados, false));
	}

	public void apagarBotones() {
		txtNombreEstado.setFocus(true);
		listEstado.clearSelection();
		listEstado.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}
	
}

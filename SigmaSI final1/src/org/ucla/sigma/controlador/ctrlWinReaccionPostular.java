package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.modelo.ReaccionPostular;
import org.ucla.sigma.servicio.ServicioReaccionPostular;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinReaccionPostular extends GenericForwardComposer {

	private Window winReaccionPostular;
	private Listbox listReaccionPostular;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreReaccionPostular;

	// ----------------------------------------------------------------------------------------------------

	private String editReaccionPostular = "/sigma/vistas/maestros/reaccionPostular/editReaccionPostular.zul";
	private ServicioReaccionPostular servicioReaccionPostular;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreReaccionPostular.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private ReaccionPostular seleccion;
	private List<ReaccionPostular> reaccionPostulares = new ArrayList<ReaccionPostular>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinReaccionPostular() {
		return winReaccionPostular;
	}

	public void setWinReaccionPostular(Window winReaccionPostular) {
		this.winReaccionPostular = winReaccionPostular;
	}

	public Listbox getListReaccionPostular() {
		return listReaccionPostular;
	}

	public void setListReaccionPostular(Listbox listReaccionPostular) {
		this.listReaccionPostular = listReaccionPostular;
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

	public ServicioReaccionPostular getServicioReaccionPostular() {
		return servicioReaccionPostular;
	}

	public void setServicioReaccionPostular(ServicioReaccionPostular servicioReaccionPostular) {
		this.servicioReaccionPostular = servicioReaccionPostular;
	}

	public ReaccionPostular getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(ReaccionPostular seleccion) {
		this.seleccion = seleccion;
	}

	public Textbox getTxtNombreReaccionPostular() {
		return txtNombreReaccionPostular;
	}

	public void setTxtNombreReaccionPostular(Textbox txtNombreReaccionPostular) {
		this.txtNombreReaccionPostular = txtNombreReaccionPostular;
	}

	public List<ReaccionPostular> getReaccionPostulares() {
		return reaccionPostulares;
	}

	public void setReaccionPostulares(List<ReaccionPostular> reaccionPostulares) {
		this.reaccionPostulares = reaccionPostulares;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winReaccionPostular.setAttribute(comp.getId() + "ctrl", this);
		servicioReaccionPostular = (ServicioReaccionPostular) SpringUtil
				.getBean("beanServicioReaccionPostular");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinReaccionPostular", this);
		Window win = (Window) Executions.createComponents(editReaccionPostular, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listReaccionPostular.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinReaccionPostular", this);
			Window win = (Window) Executions.createComponents(editReaccionPostular, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listReaccionPostular.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioReaccionPostular.borrarReaccionPostular(seleccion);
					reaccionPostulares.remove(seleccion);
					listReaccionPostular
							.setModel(new BindingListModelList(reaccionPostulares, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		reaccionPostulares = servicioReaccionPostular.buscarTodos('A');
		listReaccionPostular.setModel(new BindingListModelList(reaccionPostulares, false));
		buscando = false;
		verTodos = true;
		txtNombreReaccionPostular.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreReaccionPostular.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			reaccionPostulares = servicioReaccionPostular.buscarCoincidencias(
					txtNombreReaccionPostular.getValue(), 'A');
			if (reaccionPostulares.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
				reaccionPostulares.clear();
				listReaccionPostular.setModel(new BindingListModelList(reaccionPostulares, false));
			} else {
				listReaccionPostular.setModel(new BindingListModelList(reaccionPostulares, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listReaccionPostular() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreReaccionPostular() {
		apagarBotones();
	}

	public void recargar() {
		seleccion= null;
		if (verTodos)
			reaccionPostulares = servicioReaccionPostular.buscarTodos('A');
		else if (buscando)
			reaccionPostulares = servicioReaccionPostular.buscarCoincidencias(
					txtNombreReaccionPostular.getValue(), 'A');
		else
			reaccionPostulares.clear();

		listReaccionPostular.setModel(new BindingListModelList(reaccionPostulares, false));
	}

	public void apagarBotones() {
		txtNombreReaccionPostular.setFocus(true);
		listReaccionPostular.clearSelection();
		listReaccionPostular.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

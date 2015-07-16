package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.modelo.ValoracionSubjetiva;
import org.ucla.sigma.servicio.ServicioValoracionSubjetiva;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinValoracionSubjetiva extends GenericForwardComposer {

	private Window winValoracionSubjetiva;
	private Listbox listValoracionSubjetiva;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreValoracionSubjetiva;

	// ----------------------------------------------------------------------------------------------------

	private String editValoracionSubjetiva = "/sigma/vistas/maestros/valoracionSubjetiva/editValoracionSubjetiva.zul";
	private ServicioValoracionSubjetiva servicioValoracionSubjetiva;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreValoracionSubjetiva.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private ValoracionSubjetiva seleccion;
	private List<ValoracionSubjetiva> valoracionSubjetivas = new ArrayList<ValoracionSubjetiva>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinValoracionSubjetiva() {
		return winValoracionSubjetiva;
	}

	public void setWinValoracionSubjetiva(Window winValoracionSubjetiva) {
		this.winValoracionSubjetiva = winValoracionSubjetiva;
	}

	public Listbox getListValoracionSubjetiva() {
		return listValoracionSubjetiva;
	}

	public void setListValoracionSubjetiva(Listbox listValoracionSubjetiva) {
		this.listValoracionSubjetiva = listValoracionSubjetiva;
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

	public ServicioValoracionSubjetiva getServicioValoracionSubjetiva() {
		return servicioValoracionSubjetiva;
	}

	public void setServicioValoracionSubjetiva(ServicioValoracionSubjetiva servicioValoracionSubjetiva) {
		this.servicioValoracionSubjetiva = servicioValoracionSubjetiva;
	}

	public ValoracionSubjetiva getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(ValoracionSubjetiva seleccion) {
		this.seleccion = seleccion;
	}

	public Textbox getTxtNombreValoracionSubjetiva() {
		return txtNombreValoracionSubjetiva;
	}

	public void setTxtNombreValoracionSubjetiva(Textbox txtNombreValoracionSubjetiva) {
		this.txtNombreValoracionSubjetiva = txtNombreValoracionSubjetiva;
	}

	public List<ValoracionSubjetiva> getValoracionSubjetivas() {
		return valoracionSubjetivas;
	}

	public void setValoracionSubjetivas(List<ValoracionSubjetiva> valoracionSubjetivas) {
		this.valoracionSubjetivas = valoracionSubjetivas;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winValoracionSubjetiva.setAttribute(comp.getId() + "ctrl", this);
		servicioValoracionSubjetiva = (ServicioValoracionSubjetiva) SpringUtil
				.getBean("beanServicioValoracionSubjetiva");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinValoracionSubjetiva", this);
		Window win = (Window) Executions.createComponents(editValoracionSubjetiva, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listValoracionSubjetiva.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinValoracionSubjetiva", this);
			Window win = (Window) Executions.createComponents(editValoracionSubjetiva, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listValoracionSubjetiva.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioValoracionSubjetiva.borrarValoracionSubjetiva(seleccion);
					valoracionSubjetivas.remove(seleccion);
					listValoracionSubjetiva
							.setModel(new BindingListModelList(valoracionSubjetivas, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		valoracionSubjetivas = servicioValoracionSubjetiva.buscarTodos('A');
		listValoracionSubjetiva.setModel(new BindingListModelList(valoracionSubjetivas, false));
		buscando = false;
		verTodos = true;
		txtNombreValoracionSubjetiva.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreValoracionSubjetiva.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			valoracionSubjetivas = servicioValoracionSubjetiva.buscarCoincidencias(
					txtNombreValoracionSubjetiva.getValue(), 'A');
			if (valoracionSubjetivas.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
				valoracionSubjetivas.clear();
				listValoracionSubjetiva.setModel(new BindingListModelList(valoracionSubjetivas, false));
			} else {
				listValoracionSubjetiva.setModel(new BindingListModelList(valoracionSubjetivas, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listValoracionSubjetiva() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreValoracionSubjetiva() {
		apagarBotones();
	}

	public void recargar() {
		seleccion= null;
		if (verTodos)
			valoracionSubjetivas = servicioValoracionSubjetiva.buscarTodos('A');
		else if (buscando)
			valoracionSubjetivas = servicioValoracionSubjetiva.buscarCoincidencias(
					txtNombreValoracionSubjetiva.getValue(), 'A');
		else
			valoracionSubjetivas.clear();

		listValoracionSubjetiva.setModel(new BindingListModelList(valoracionSubjetivas, false));
	}

	public void apagarBotones() {
		txtNombreValoracionSubjetiva.setFocus(true);
		listValoracionSubjetiva.clearSelection();
		listValoracionSubjetiva.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

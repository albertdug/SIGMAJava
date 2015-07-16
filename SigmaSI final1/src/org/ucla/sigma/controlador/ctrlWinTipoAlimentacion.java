package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Estado;
import org.ucla.sigma.modelo.TipoAlimentacion;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioTipoAlimentacion;
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
 * @author promo49
 * 
 */
public class ctrlWinTipoAlimentacion extends GenericForwardComposer {

	private Window winTipoAlimentacion;
	private Listbox listTipoAlimentacion;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombreAlimento;

	/**
	 *
	 *
	 */

	// ----------------------------------------------------------------------------------------------------
	private String editTipoAlimentacion = "/sigma/vistas/maestros/tipoAlimentacion/editTipoAlimentacion.zul";
	private ServicioTipoAlimentacion servicioTipoAlimentacion;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreAlimento.setFocus(true);
		};
	};
	// ----------------------------------------------------------------------------------------------------

	private TipoAlimentacion seleccion;
	private List<TipoAlimentacion> tipoalimentacion = new ArrayList<TipoAlimentacion>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinTipoAlimentacion() {
		return winTipoAlimentacion;
	}

	public void setWinTipoAlimentacion(Window winTipoAlimentacion) {
		this.winTipoAlimentacion = winTipoAlimentacion;
	}

	public Listbox getListTipoAlimentacion() {
		return listTipoAlimentacion;
	}

	public void setListTipoAlimentacion(Listbox listTipoAlimentacion) {
		this.listTipoAlimentacion = listTipoAlimentacion;
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

	public Textbox getTxtNombreAlimento() {
		return txtNombreAlimento;
	}

	public void setTxtNombreAlimento(Textbox txtNombreAlimento) {
		this.txtNombreAlimento = txtNombreAlimento;
	}

	public String getEditTipoAlimentacion() {
		return editTipoAlimentacion;
	}

	public void setEditTipoAlimentacion(String editTipoAlimentacion) {
		this.editTipoAlimentacion = editTipoAlimentacion;
	}

	public ServicioTipoAlimentacion getServicioTipoAlimentacion() {
		return servicioTipoAlimentacion;
	}

	public void setServicioTipoAlimentacion(
			ServicioTipoAlimentacion servicioTipoAlimentacion) {
		this.servicioTipoAlimentacion = servicioTipoAlimentacion;
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

	public TipoAlimentacion getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(TipoAlimentacion seleccion) {
		this.seleccion = seleccion;
	}

	public List<TipoAlimentacion> getTipoalimentacion() {
		return tipoalimentacion;
	}

	public void setTipoalimentacion(List<TipoAlimentacion> tipoalimentacion) {
		this.tipoalimentacion = tipoalimentacion;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winTipoAlimentacion.setAttribute(comp.getId() + "ctrl", this);
		servicioTipoAlimentacion = (ServicioTipoAlimentacion) SpringUtil
				.getBean("beanServicioTipoAlimentacion");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinTipoAlimentacion", this);
		Window win = (Window) Executions.createComponents(editTipoAlimentacion,
				null, parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listTipoAlimentacion.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinTipoAlimentacion", this);
			Window win = (Window) Executions.createComponents(
					editTipoAlimentacion, null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listTipoAlimentacion.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioTipoAlimentacion.borrarTipoAlimentacion(seleccion);
					tipoalimentacion.remove(seleccion);
					listTipoAlimentacion.setModel(new BindingListModelList(
							tipoalimentacion, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		tipoalimentacion = servicioTipoAlimentacion.buscarTodos('A');
		listTipoAlimentacion.setModel(new BindingListModelList(
				tipoalimentacion, false));
		buscando = false;
		verTodos = true;
		txtNombreAlimento.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreAlimento.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			tipoalimentacion = servicioTipoAlimentacion.buscarCoincidencias(
					txtNombreAlimento.getValue(), 'A');
			if (tipoalimentacion.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listTipoAlimentacion.setModel(new BindingListModelList(
						tipoalimentacion, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listTipoAlimentacion() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreAlimento() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			tipoalimentacion = servicioTipoAlimentacion.buscarTodos('A');
		else if (buscando)
			tipoalimentacion = servicioTipoAlimentacion.buscarCoincidencias(
					txtNombreAlimento.getValue(), 'A');
		else
			tipoalimentacion.clear();

		listTipoAlimentacion.setModel(new BindingListModelList(
				tipoalimentacion, false));
	}

	public void apagarBotones() {
		txtNombreAlimento.setFocus(true);
		listTipoAlimentacion.clearSelection();
		listTipoAlimentacion.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}
}

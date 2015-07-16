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
import org.ucla.sigma.modelo.TipoDefuncion;
import org.ucla.sigma.servicio.ServicioTipoDefuncion;
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
 * @author lis
 * 
 */
public class ctrlWinTipoDefuncion extends GenericForwardComposer {

	private Window winTipoDefuncion;
	private Listbox listTipoDefuncion;
	private Button btnVer;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtTipoDefuncion;

	// ----------------------------------------------------------------------------------------------------

	private String editTipoDefuncion = "/sigma/vistas/maestros/tipoDefuncion/editTipoDefuncion.zul";
	private ServicioTipoDefuncion servicioTipoDefuncion;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtTipoDefuncion.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private TipoDefuncion seleccion;
	private List<TipoDefuncion> tipodefunciones = new ArrayList<TipoDefuncion>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinTipoDefuncion() {
		return winTipoDefuncion;
	}

	public void setWinTipoDefuncion(Window winTipoDefuncion) {
		this.winTipoDefuncion = winTipoDefuncion;
	}

	public Listbox getListTipoDefuncion() {
		return listTipoDefuncion;
	}

	public void setListTipoDefuncion(Listbox listTipoDefuncion) {
		this.listTipoDefuncion = listTipoDefuncion;
	}

	public Button getBtnVer() {
		return btnVer;
	}

	public void setBtnVer(Button btnVer) {
		this.btnVer = btnVer;
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

	public Textbox getTxtTipoDefuncion() {
		return txtTipoDefuncion;
	}

	public void setTxtTipoDefuncion(Textbox txtTipoDefuncion) {
		this.txtTipoDefuncion = txtTipoDefuncion;
	}

	public String getEditTipoDefuncion() {
		return editTipoDefuncion;
	}

	public void setEditTipoDefuncion(String editTipoDefuncion) {
		this.editTipoDefuncion = editTipoDefuncion;
	}

	public ServicioTipoDefuncion getServicioTipoDefuncion() {
		return servicioTipoDefuncion;
	}

	public void setServicioTipoDefuncion(
			ServicioTipoDefuncion servicioTipoDefuncion) {
		this.servicioTipoDefuncion = servicioTipoDefuncion;
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

	public TipoDefuncion getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(TipoDefuncion seleccion) {
		this.seleccion = seleccion;
	}

	public List<TipoDefuncion> getTipodefunciones() {
		return tipodefunciones;
	}

	public void setTipodefunciones(List<TipoDefuncion> tipodefunciones) {
		this.tipodefunciones = tipodefunciones;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winTipoDefuncion.setAttribute(comp.getId() + "ctrl", this);
		servicioTipoDefuncion = (ServicioTipoDefuncion) SpringUtil
				.getBean("beanServicioTipoDefuncion");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinTipoDefuncion", this);
		Window win = (Window) Executions.createComponents(editTipoDefuncion,
				null, parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listTipoDefuncion.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinTipoDefuncion", this);
			Window win = (Window) Executions.createComponents(
					editTipoDefuncion, null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listTipoDefuncion.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioTipoDefuncion.borrarTipoDefuncion(seleccion);
					tipodefunciones.remove(seleccion);
					listTipoDefuncion.setModel(new BindingListModelList(
							tipodefunciones, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVer() {
		tipodefunciones = servicioTipoDefuncion.buscarTodos('A');
		listTipoDefuncion.setModel(new BindingListModelList(tipodefunciones,
				false));
		buscando = false;
		verTodos = true;
		txtTipoDefuncion.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtTipoDefuncion.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			tipodefunciones = servicioTipoDefuncion.buscarCoincidencias(
					txtTipoDefuncion.getValue(), 'A');
			if (tipodefunciones.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listTipoDefuncion.setModel(new BindingListModelList(
						tipodefunciones, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listTipoDefuncion() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtTipoDefuncion() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			tipodefunciones = servicioTipoDefuncion.buscarTodos('A');
		else if (buscando)
			tipodefunciones = servicioTipoDefuncion.buscarCoincidencias(
					txtTipoDefuncion.getValue(), 'A');
		else
			tipodefunciones.clear();

		listTipoDefuncion.setModel(new BindingListModelList(tipodefunciones,
				false));
	}

	public void apagarBotones() {
		txtTipoDefuncion.setFocus(true);
		listTipoDefuncion.clearSelection();
		listTipoDefuncion.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

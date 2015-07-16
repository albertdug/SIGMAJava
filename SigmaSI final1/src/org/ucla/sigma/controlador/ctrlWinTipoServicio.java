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
import org.ucla.sigma.modelo.TipoServicio;
import org.ucla.sigma.modelo.TipoServicio;
import org.ucla.sigma.servicio.ServicioTipoServicio;
import org.ucla.sigma.servicio.ServicioTipoServicio;
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
 * @author promo49
 * 
 */
public class ctrlWinTipoServicio extends GenericForwardComposer {

	private Window winTipoServicio;
	private Listbox listTipoServicio;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private String editTipoServicio = "/sigma/vistas/maestros/tipoServicio/editTipoServicio.zul";
	private ServicioTipoServicio servicioTipoServicio;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombre.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private TipoServicio seleccion;
	private List<TipoServicio> TipoServicios = new ArrayList<TipoServicio>();
	public Window getWinTipoServicio() {
		return winTipoServicio;
	}
	public void setWinTipoServicio(Window winTipoServicio) {
		this.winTipoServicio = winTipoServicio;
	}
	public Listbox getListTipoServicio() {
		return listTipoServicio;
	}
	public void setListTipoServicio(Listbox listTipoServicio) {
		this.listTipoServicio = listTipoServicio;
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
	public Textbox getTxtNombre() {
		return txtNombre;
	}
	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}
	
	public String getEditTipoServicio() {
		return editTipoServicio;
	}
	public void setEditTipoServicio(String editTipoServicio) {
		this.editTipoServicio = editTipoServicio;
	}
	public ServicioTipoServicio getServicioTipoServicio() {
		return servicioTipoServicio;
	}
	public void setServicioTipoServicio(ServicioTipoServicio servicioTipoServicio) {
		this.servicioTipoServicio = servicioTipoServicio;
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
	public TipoServicio getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(TipoServicio seleccion) {
		this.seleccion = seleccion;
	}
	public List<TipoServicio> getTipoServicios() {
		return TipoServicios;
	}
	public void setTipoServicios(List<TipoServicio> tipoServicios) {
		TipoServicios = tipoServicios;
	}

	// ----------------------------------------------------------------------------------------------------

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winTipoServicio.setAttribute(comp.getId() + "ctrl", this);
		servicioTipoServicio = (ServicioTipoServicio) SpringUtil
				.getBean("beanServicioTipoServicio");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinTipoServicio", this);
		Window win = (Window) Executions.createComponents(editTipoServicio, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listTipoServicio.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinTipoServicio", this);
			Window win = (Window) Executions.createComponents(editTipoServicio, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listTipoServicio.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioTipoServicio.borrarTipoServicio(seleccion);
					TipoServicios.remove(seleccion);
					listTipoServicio
							.setModel(new BindingListModelList(TipoServicios, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		TipoServicios = servicioTipoServicio.buscarTodos('A');
		listTipoServicio.setModel(new BindingListModelList(TipoServicios, false));
		buscando = false;
		verTodos = true;
		txtNombre.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombre.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			TipoServicios = servicioTipoServicio.buscarCoincidencias(
					txtNombre.getValue(), 'A');
			if (TipoServicios.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listTipoServicio.setModel(new BindingListModelList(TipoServicios, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listTipoServicio() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreTipoServicio() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			TipoServicios = servicioTipoServicio.buscarTodos('A');
		else if (buscando)
			TipoServicios = servicioTipoServicio.buscarCoincidencias(
					txtNombre.getValue(), 'A');
		else
			TipoServicios.clear();

		listTipoServicio.setModel(new BindingListModelList(TipoServicios, false));
	}

	public void apagarBotones() {
		txtNombre.setFocus(true);
		listTipoServicio.clearSelection();
		listTipoServicio.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}
	
}

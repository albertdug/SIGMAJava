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
import org.ucla.sigma.modelo.TipoImagenologia;
import org.ucla.sigma.servicio.ServicioTipoImagenologia;
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
public class ctrlWinTipoImagenologia extends GenericForwardComposer {

	private Window winTipoImagenologia;
	private Listbox listTipo;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private String editTipoImagenologia = "/sigma/vistas/maestros/tipoImagenologia/editTipoImagenologia.zul";
	private ServicioTipoImagenologia servicioTipoImagenologia;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombre.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private TipoImagenologia seleccion;
	private List<TipoImagenologia> tipoImagenologias = new ArrayList<TipoImagenologia>();
	
	// ----------------------------------------------------------------------------------------------------
	
	public Window getWinTipoImagenologia() {
		return winTipoImagenologia;
	}
	public void setWinTipoImagenologia(Window winTipoImagenologia) {
		this.winTipoImagenologia = winTipoImagenologia;
	}
	public Listbox getListTipo() {
		return listTipo;
	}
	public void setListTipo(Listbox listTipo) {
		this.listTipo = listTipo;
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
	public String getEditTipoImagenologia() {
		return editTipoImagenologia;
	}
	public void setEditTipoImagenologia(String editTipoImagenologia) {
		this.editTipoImagenologia = editTipoImagenologia;
	}
	public ServicioTipoImagenologia getServicioTipoImagenologia() {
		return servicioTipoImagenologia;
	}
	public void setServicioTipoImagenologia(
			ServicioTipoImagenologia servicioTipoImagenologia) {
		this.servicioTipoImagenologia = servicioTipoImagenologia;
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
	public TipoImagenologia getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(TipoImagenologia seleccion) {
		this.seleccion = seleccion;
	}
	public List<TipoImagenologia> getTipoImagenologias() {
		return tipoImagenologias;
	}
	public void setTipoImagenologias(List<TipoImagenologia> tipoImagenologias) {
		this.tipoImagenologias = tipoImagenologias;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winTipoImagenologia.setAttribute(comp.getId() + "ctrl", this);
		servicioTipoImagenologia = (ServicioTipoImagenologia) SpringUtil
				.getBean("beanServicioTipoImagenologia");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinTipoImagenologia", this);
		Window win = (Window) Executions.createComponents(editTipoImagenologia, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listTipo.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinTipoImagenologia", this);
			Window win = (Window) Executions.createComponents(editTipoImagenologia, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listTipo.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioTipoImagenologia.borrarTipoImagenologia(seleccion);
					tipoImagenologias.remove(seleccion);
					listTipo
							.setModel(new BindingListModelList(tipoImagenologias, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		tipoImagenologias = servicioTipoImagenologia.buscarTodos('A');
		listTipo.setModel(new BindingListModelList(tipoImagenologias, false));
		buscando = false;
		verTodos = true;
		txtNombre.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombre.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			tipoImagenologias = servicioTipoImagenologia.buscarCoincidencias(
					txtNombre.getValue(), 'A');
			if (tipoImagenologias.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listTipo.setModel(new BindingListModelList(tipoImagenologias, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listTipo() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombre() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			tipoImagenologias = servicioTipoImagenologia.buscarTodos('A');
		else if (buscando)
			tipoImagenologias = servicioTipoImagenologia.buscarCoincidencias(
					txtNombre.getValue(), 'A');
		else
			tipoImagenologias.clear();

		listTipo.setModel(new BindingListModelList(tipoImagenologias, false));
	}

	public void apagarBotones() {
		txtNombre.setFocus(true);
		listTipo.clearSelection();
		listTipo.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

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
import org.ucla.sigma.modelo.TipoPatologia;
import org.ucla.sigma.modelo.TipoPatologia;
import org.ucla.sigma.servicio.ServicioTipoPatologia;
import org.ucla.sigma.servicio.ServicioTipoPatologia;
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
 * @author rafael
 * 
 */
public class ctrlWinTipoPatologia extends GenericForwardComposer {

	private Window winTipoPatologia;
	private Listbox listTipoPatologia;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombreTipoPatologia;

	// ----------------------------------------------------------------------------------------------------

	private String editTipoPatologia = "/sigma/vistas/maestros/tipoPatologia/editTipoPatologia.zul";
	private ServicioTipoPatologia servicioTipoPatologia;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreTipoPatologia.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private TipoPatologia seleccion;
	private List<TipoPatologia> tipoPatologias = new ArrayList<TipoPatologia>();

	public Window getWinTipoPatologia() {
		return winTipoPatologia;
	}

	public void setWinTipoPatologia(Window winTipoPatologia) {
		this.winTipoPatologia = winTipoPatologia;
	}

	public Listbox getListTipoPatologia() {
		return listTipoPatologia;
	}

	public void setListTipoPatologia(Listbox listTipoPatologia) {
		this.listTipoPatologia = listTipoPatologia;
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

	public Textbox getTxtNombreTipoPatologia() {
		return txtNombreTipoPatologia;
	}

	public void setTxtNombreTipoPatologia(Textbox txtNombreTipoPatologia) {
		this.txtNombreTipoPatologia = txtNombreTipoPatologia;
	}

	public String getEditTipoPatologia() {
		return editTipoPatologia;
	}

	public void setEditTipoPatologia(String editTipoPatologia) {
		this.editTipoPatologia = editTipoPatologia;
	}

	public ServicioTipoPatologia getServicioTipoPatologia() {
		return servicioTipoPatologia;
	}

	public void setServicioTipoPatologia(
			ServicioTipoPatologia servicioTipoPatologia) {
		this.servicioTipoPatologia = servicioTipoPatologia;
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

	public TipoPatologia getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(TipoPatologia seleccion) {
		this.seleccion = seleccion;
	}

	public List<TipoPatologia> getTipoPatologias() {
		return tipoPatologias;
	}

	public void setTipoPatologias(List<TipoPatologia> tipoPatologias) {
		this.tipoPatologias = tipoPatologias;
	}

	// ----------------------------------------------------------------------------------------------------

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winTipoPatologia.setAttribute(comp.getId() + "ctrl", this);
		servicioTipoPatologia = (ServicioTipoPatologia) SpringUtil
				.getBean("beanServicioTipoPatologia");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinTipoPatologia", this);
		Window win = (Window) Executions.createComponents(editTipoPatologia,
				null, parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listTipoPatologia.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinTipoPatologia", this);
			Window win = (Window) Executions.createComponents(
					editTipoPatologia, null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listTipoPatologia.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioTipoPatologia.borrarTipoPatologia(seleccion);
					tipoPatologias.remove(seleccion);
					listTipoPatologia.setModel(new BindingListModelList(
							tipoPatologias, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		tipoPatologias = servicioTipoPatologia.buscarTodos('A');
		listTipoPatologia.setModel(new BindingListModelList(tipoPatologias,
				false));
		buscando = false;
		verTodos = true;
		txtNombreTipoPatologia.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreTipoPatologia.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			tipoPatologias = servicioTipoPatologia.buscarCoincidencias(
					txtNombreTipoPatologia.getValue(), 'A');
			if (tipoPatologias.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listTipoPatologia.setModel(new BindingListModelList(
						tipoPatologias, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listTipoPatologia() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreTipoPatologia() {
		apagarBotones();
	}

	public void recargar() {

		seleccion = null;

		if (verTodos)
			tipoPatologias = servicioTipoPatologia.buscarTodos('A');
		else if (buscando)
			tipoPatologias = servicioTipoPatologia.buscarCoincidencias(
					txtNombreTipoPatologia.getValue(), 'A');
		else
			tipoPatologias.clear();

		listTipoPatologia.setModel(new BindingListModelList(tipoPatologias,
				false));
	}

	public void apagarBotones() {
		txtNombreTipoPatologia.setFocus(true);
		listTipoPatologia.clearSelection();
		listTipoPatologia.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

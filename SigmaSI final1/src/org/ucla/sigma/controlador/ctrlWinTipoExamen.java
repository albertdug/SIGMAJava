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
import org.ucla.sigma.modelo.TipoExamen;
import org.ucla.sigma.servicio.ServicioTipoExamen;
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
 * @author Albert
 * 
 */
public class ctrlWinTipoExamen extends GenericForwardComposer {

	private Window winTipoExamen;
	private Listbox listTipoExamen;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombreTipoExamen;

	// ----------------------------------------------------------------------------------------------------

	private String editTipoExamen = "/sigma/vistas/maestros/tipoExamen/editTipoExamen.zul";
	private ServicioTipoExamen servicioTipoExamen;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreTipoExamen.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private TipoExamen seleccion;
	private List<TipoExamen> tipoExamenes = new ArrayList<TipoExamen>();

	public Window getWinTipoExamen() {
		return winTipoExamen;
	}

	public void setWinTipoExamen(Window winTipoExamen) {
		this.winTipoExamen = winTipoExamen;
	}

	public Listbox getListTipoExamen() {
		return listTipoExamen;
	}

	public void setListTipoExamen(Listbox listTipoExamen) {
		this.listTipoExamen = listTipoExamen;
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

	public Textbox getTxtNombreTipoExamen() {
		return txtNombreTipoExamen;
	}

	public void setTxtNombreTipoExamen(Textbox txtNombreTipoExamen) {
		this.txtNombreTipoExamen = txtNombreTipoExamen;
	}

	public String getEditTipoExamen() {
		return editTipoExamen;
	}

	public void setEditTipoExamen(String editTipoExamen) {
		this.editTipoExamen = editTipoExamen;
	}

	public ServicioTipoExamen getServicioTipoExamen() {
		return servicioTipoExamen;
	}

	public void setServicioTipoExamen(ServicioTipoExamen servicioTipoExamen) {
		this.servicioTipoExamen = servicioTipoExamen;
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

	public TipoExamen getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(TipoExamen seleccion) {
		this.seleccion = seleccion;
	}

	public List<TipoExamen> getTipoExamenes() {
		return tipoExamenes;
	}

	public void setTipoExamenes(List<TipoExamen> tipoExamenes) {
		this.tipoExamenes = tipoExamenes;
	}

	// ----------------------------------------------------------------------------------------------------

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winTipoExamen.setAttribute(comp.getId() + "ctrl", this);
		servicioTipoExamen = (ServicioTipoExamen) SpringUtil
				.getBean("beanServicioTipoExamen");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinTipoExamen", this);
		Window win = (Window) Executions.createComponents(editTipoExamen, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listTipoExamen.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinTipoExamen", this);
			Window win = (Window) Executions.createComponents(editTipoExamen,
					null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listTipoExamen.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioTipoExamen.borrarTipoExamen(seleccion);
					tipoExamenes.remove(seleccion);
					listTipoExamen.setModel(new BindingListModelList(
							tipoExamenes, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		tipoExamenes = servicioTipoExamen.buscarTodos('A');
		listTipoExamen.setModel(new BindingListModelList(tipoExamenes, false));
		buscando = false;
		verTodos = true;
		txtNombreTipoExamen.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreTipoExamen.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			tipoExamenes = servicioTipoExamen.buscarCoincidencias(
					txtNombreTipoExamen.getValue(), 'A');
			if (tipoExamenes.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listTipoExamen.setModel(new BindingListModelList(tipoExamenes,
						false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listTipoExamen() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreTipoExamen() {
		apagarBotones();
	}

	public void recargar() {

		seleccion = null;

		if (verTodos)
			tipoExamenes = servicioTipoExamen.buscarTodos('A');
		else if (buscando)
			tipoExamenes = servicioTipoExamen.buscarCoincidencias(
					txtNombreTipoExamen.getValue(), 'A');
		else
			tipoExamenes.clear();

		listTipoExamen.setModel(new BindingListModelList(tipoExamenes, false));
	}

	public void apagarBotones() {
		txtNombreTipoExamen.setFocus(true);
		listTipoExamen.clearSelection();
		listTipoExamen.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

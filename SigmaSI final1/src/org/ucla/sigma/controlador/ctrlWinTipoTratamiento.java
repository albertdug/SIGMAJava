/**
 * 
 */
package org.ucla.sigma.controlador;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.TipoTratamiento;
import org.ucla.sigma.servicio.ServicioTipoTratamiento;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
/**
 * @author usuario
 *
 */
public class ctrlWinTipoTratamiento extends GenericForwardComposer {

	private Window winTipoTratamiento;
	private Listbox listTipoTratamiento;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombreTipoTratamiento;

	// ----------------------------------------------------------------------------------------------------

		private String editTipoTratamiento = "/sigma/vistas/maestros/tipoTratamiento/editTipoTratamiento.zul";
		private ServicioTipoTratamiento servicioTipoTratamiento;
		private boolean buscando = false;
		private boolean verTodos = false;
		private MensajeListener asignarFocusBuscar = new MensajeListener() {
			public void alDestruir() {
				txtNombreTipoTratamiento.setFocus(true);
			};
		};

		// ----------------------------------------------------------------------------------------------------

		private TipoTratamiento seleccion;
		private List<TipoTratamiento> tipoTratamientos = new ArrayList<TipoTratamiento>();

		// ----------------------------------------------------------------------------------------------------

		public Window getWinTipoTratamiento() {
			return winTipoTratamiento;
		}

		public void setWinTipoTratamiento(Window winTipoTratamiento) {
			this.winTipoTratamiento = winTipoTratamiento;
		}

		public Listbox getListTipoTratamiento() {
			return listTipoTratamiento;
		}

		public void setListTipoTratamiento(Listbox listTipoTratamiento) {
			this.listTipoTratamiento = listTipoTratamiento;
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

		public ServicioTipoTratamiento getServicioTipoTratamiento() {
			return servicioTipoTratamiento;
		}

		public void setServicioTipoTratamiento(ServicioTipoTratamiento servicioTipoTratamiento) {
			this.servicioTipoTratamiento = servicioTipoTratamiento;
		}

		public TipoTratamiento getSeleccion() {
			return seleccion;
		}

		public void setSeleccion(TipoTratamiento seleccion) {
			this.seleccion = seleccion;
		}

		public Textbox getTxtNombreTipoTratamiento() {
			return txtNombreTipoTratamiento;
		}

		public void setTxtNombreTipoTratamiento(Textbox txtNombreTipoTratamiento) {
			this.txtNombreTipoTratamiento = txtNombreTipoTratamiento;
		}

		public List<TipoTratamiento> getTipoTratamientos() {
			return tipoTratamientos;
		}

		public void setTipoTratamientos(List<TipoTratamiento> tipoTratamientos) {
			this.tipoTratamientos = tipoTratamientos;
		}

		/**
		 * Agrega el controlador a la vista, Asigna el servicio a usar
		 */
		@Override
		public void doAfterCompose(Component comp) throws Exception {
			super.doAfterCompose(comp);
			winTipoTratamiento.setAttribute(comp.getId() + "ctrl", this);
			servicioTipoTratamiento = (ServicioTipoTratamiento) SpringUtil
					.getBean("beanServicioTipoTratamiento");
			apagarBotones();
		}

		public void onClick$btnNuevo() {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("ctrlWinTipoTratamiento", this);
			Window win = (Window) Executions.createComponents(editTipoTratamiento, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

		public void onClick$btnEditar() {
			if (listTipoTratamiento.getSelectedItems().isEmpty()) {
				MensajeEmergente.mostrar("SELECTREG");
			} else {
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("objeto", seleccion);
				parametros.put("ctrlWinTipoTratamiento", this);
				Window win = (Window) Executions.createComponents(editTipoTratamiento, null,
						parametros);
				win.doHighlighted();
				apagarBotones();
			}

		}

		public void onClick$btnEliminar() {
			if (listTipoTratamiento.getSelectedItems().isEmpty()) {
				MensajeEmergente.mostrar("SELECTREG");
			} else {
				MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
					@Override
					public void alAceptar() {
						servicioTipoTratamiento.borrarTipoTratamiento(seleccion);
						tipoTratamientos.remove(seleccion);
						listTipoTratamiento
								.setModel(new BindingListModelList(tipoTratamientos, false));
						MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
					}
				});
				apagarBotones();
			}

		}

		public void onClick$btnVerTodos() {
			tipoTratamientos = servicioTipoTratamiento.buscarTodos('A');
			listTipoTratamiento.setModel(new BindingListModelList(tipoTratamientos, false));
			buscando = false;
			verTodos = true;
			txtNombreTipoTratamiento.setText(null);
			apagarBotones();
		}

		public void onClick$btnBuscar() {

			if (txtNombreTipoTratamiento.getValue().isEmpty()) {
				MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
			} else {
				tipoTratamientos = servicioTipoTratamiento.buscarCoincidencias(
						txtNombreTipoTratamiento.getValue(), 'A');
				if (tipoTratamientos.isEmpty()) {
					MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
					tipoTratamientos.clear();
					listTipoTratamiento.setModel(new BindingListModelList(tipoTratamientos, false));
				} else {
					listTipoTratamiento.setModel(new BindingListModelList(tipoTratamientos, false));
					buscando = true;
					verTodos = false;
				}
			}
			apagarBotones();
		}

		public void onSelect$listTipoTratamiento() {
			btnEditar.setDisabled(false);
			btnEliminar.setDisabled(false);
		}

		public void onFocus$txtNombreTipoTratamiento() {
			apagarBotones();
		}

		public void recargar() {
			seleccion = null;
			if (verTodos)
				tipoTratamientos = servicioTipoTratamiento.buscarTodos('A');
			else if (buscando)
				tipoTratamientos = servicioTipoTratamiento.buscarCoincidencias(
						txtNombreTipoTratamiento.getValue(), 'A');
			else
				tipoTratamientos.clear();

			listTipoTratamiento.setModel(new BindingListModelList(tipoTratamientos, false));
		}

		public void apagarBotones() {
			txtNombreTipoTratamiento.setFocus(true);
			listTipoTratamiento.clearSelection();
			listTipoTratamiento.selectItem(null);
			btnEditar.setDisabled(true);
			btnEliminar.setDisabled(true);
		}
}

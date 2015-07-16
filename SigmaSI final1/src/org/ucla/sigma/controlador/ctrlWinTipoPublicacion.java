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
import org.ucla.sigma.modelo.TipoPublicacion;
import org.ucla.sigma.servicio.ServicioTipoPublicacion;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
/**
 * @author usuario
 *
 */
public class ctrlWinTipoPublicacion extends GenericForwardComposer {

	private Window winTipoPublicacion;
	private Listbox listTipoPublicacion;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombreTipoPublicacion;

	// ----------------------------------------------------------------------------------------------------

		private String editTipoPublicacion = "/sigma/vistas/portal/tipoPublicacion/editTipoPublicacion.zul";
		private ServicioTipoPublicacion servicioTipoPublicacion;
		private boolean buscando = false;
		private boolean verTodos = false;
		private MensajeListener asignarFocusBuscar = new MensajeListener() {
			public void alDestruir() {
				txtNombreTipoPublicacion.setFocus(true);
			};
		};

		// ----------------------------------------------------------------------------------------------------

		private TipoPublicacion seleccion;
		private List<TipoPublicacion> tipoPublicaciones = new ArrayList<TipoPublicacion>();

		// ----------------------------------------------------------------------------------------------------

		public Window getWinTipoPublicacion() {
			return winTipoPublicacion;
		}

		public void setWinTipoPublicacion(Window winTipoPublicacion) {
			this.winTipoPublicacion = winTipoPublicacion;
		}

		public Listbox getListTipoPublicacion() {
			return listTipoPublicacion;
		}

		public void setListTipoPublicacion(Listbox listTipoPublicacion) {
			this.listTipoPublicacion = listTipoPublicacion;
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

		public ServicioTipoPublicacion getServicioTipoPublicacion() {
			return servicioTipoPublicacion;
		}

		public void setServicioTipoPublicacion(ServicioTipoPublicacion servicioTipoPublicacion) {
			this.servicioTipoPublicacion = servicioTipoPublicacion;
		}

		public TipoPublicacion getSeleccion() {
			return seleccion;
		}

		public void setSeleccion(TipoPublicacion seleccion) {
			this.seleccion = seleccion;
		}

		public Textbox getTxtNombreTipoPublicacion() {
			return txtNombreTipoPublicacion;
		}

		public void setTxtNombreTipoPublicacion(Textbox txtNombreTipoPublicacion) {
			this.txtNombreTipoPublicacion = txtNombreTipoPublicacion;
		}

		public List<TipoPublicacion> getTipoPublicaciones() {
			return tipoPublicaciones;
		}

		public void setTipoPublicaciones(List<TipoPublicacion> tipoPublicaciones) {
			this.tipoPublicaciones = tipoPublicaciones;
		}

		/**
		 * Agrega el controlador a la vista, Asigna el servicio a usar
		 */
		@Override
		public void doAfterCompose(Component comp) throws Exception {
			super.doAfterCompose(comp);
			winTipoPublicacion.setAttribute(comp.getId() + "ctrl", this);
			servicioTipoPublicacion = (ServicioTipoPublicacion) SpringUtil
					.getBean("beanServicioTipoPublicacion");
			apagarBotones();
		}

		public void onClick$btnNuevo() {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("ctrlWinTipoPublicacion", this);
			Window win = (Window) Executions.createComponents(editTipoPublicacion, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

		public void onClick$btnEditar() {
			if (listTipoPublicacion.getSelectedItems().isEmpty()) {
				MensajeEmergente.mostrar("SELECTREG");
			} else {
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("objeto", seleccion);
				parametros.put("ctrlWinTipoPublicacion", this);
				Window win = (Window) Executions.createComponents(editTipoPublicacion, null,
						parametros);
				win.doHighlighted();
				apagarBotones();
			}

		}

		public void onClick$btnEliminar() {
			if (listTipoPublicacion.getSelectedItems().isEmpty()) {
				MensajeEmergente.mostrar("SELECTREG");
			} else {
				MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
					@Override
					public void alAceptar() {
						servicioTipoPublicacion.borrarTipoPublicacion(seleccion);
						tipoPublicaciones.remove(seleccion);
						listTipoPublicacion
								.setModel(new BindingListModelList(tipoPublicaciones, false));
						MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
					}
				});
				apagarBotones();
			}

		}

		public void onClick$btnVerTodos() {
			tipoPublicaciones = servicioTipoPublicacion.buscarTodos('A');
			listTipoPublicacion.setModel(new BindingListModelList(tipoPublicaciones, false));
			buscando = false;
			verTodos = true;
			txtNombreTipoPublicacion.setText(null);
			apagarBotones();
		}

		public void onClick$btnBuscar() {

			if (txtNombreTipoPublicacion.getValue().isEmpty()) {
				MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
			} else {
				tipoPublicaciones = servicioTipoPublicacion.buscarCoincidencias(
						txtNombreTipoPublicacion.getValue(), 'A');
				if (tipoPublicaciones.isEmpty()) {
					MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
					tipoPublicaciones.clear();
					listTipoPublicacion.setModel(new BindingListModelList(tipoPublicaciones, false));
				} else {
					listTipoPublicacion.setModel(new BindingListModelList(tipoPublicaciones, false));
					buscando = true;
					verTodos = false;
				}
			}
			apagarBotones();
		}

		public void onSelect$listTipoPublicacion() {
			btnEditar.setDisabled(false);
			btnEliminar.setDisabled(false);
		}

		public void onFocus$txtNombreTipoPublicacion() {
			apagarBotones();
		}

		public void recargar() {
			seleccion = null;
			if (verTodos)
				tipoPublicaciones = servicioTipoPublicacion.buscarTodos('A');
			else if (buscando)
				tipoPublicaciones = servicioTipoPublicacion.buscarCoincidencias(
						txtNombreTipoPublicacion.getValue(), 'A');
			else
				tipoPublicaciones.clear();

			listTipoPublicacion.setModel(new BindingListModelList(tipoPublicaciones, false));
		}

		public void apagarBotones() {
			txtNombreTipoPublicacion.setFocus(true);
			listTipoPublicacion.clearSelection();
			listTipoPublicacion.selectItem(null);
			btnEditar.setDisabled(true);
			btnEliminar.setDisabled(true);
		}
}

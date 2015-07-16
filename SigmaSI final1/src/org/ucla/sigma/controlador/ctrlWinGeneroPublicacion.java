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
import org.ucla.sigma.modelo.GeneroPublicacion;
import org.ucla.sigma.servicio.ServicioGeneroPublicacion;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
/**
 * @author usuario
 *
 */
public class ctrlWinGeneroPublicacion extends GenericForwardComposer {

	private Window winGeneroPublicacion;
	private Listbox listGeneroPublicacion;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombreGeneroPublicacion;

	// ----------------------------------------------------------------------------------------------------

		private String editGeneroPublicacion = "/sigma/vistas/portal/generoPublicacion/editGeneroPublicacion.zul";
		private ServicioGeneroPublicacion servicioGeneroPublicacion;
		private boolean buscando = false;
		private boolean verTodos = false;
		private MensajeListener asignarFocusBuscar = new MensajeListener() {
			public void alDestruir() {
				txtNombreGeneroPublicacion.setFocus(true);
			};
		};

		// ----------------------------------------------------------------------------------------------------

		private GeneroPublicacion seleccion;
		private List<GeneroPublicacion> generoPublicaciones = new ArrayList<GeneroPublicacion>();

		// ----------------------------------------------------------------------------------------------------

		public Window getWinGeneroPublicacion() {
			return winGeneroPublicacion;
		}

		public void setWinGeneroPublicacion(Window winGeneroPublicacion) {
			this.winGeneroPublicacion = winGeneroPublicacion;
		}

		public Listbox getListGeneroPublicacion() {
			return listGeneroPublicacion;
		}

		public void setListGeneroPublicacion(Listbox listGeneroPublicacion) {
			this.listGeneroPublicacion = listGeneroPublicacion;
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

		public ServicioGeneroPublicacion getServicioGeneroPublicacion() {
			return servicioGeneroPublicacion;
		}

		public void setServicioGeneroPublicacion(ServicioGeneroPublicacion servicioGeneroPublicacion) {
			this.servicioGeneroPublicacion = servicioGeneroPublicacion;
		}

		public GeneroPublicacion getSeleccion() {
			return seleccion;
		}

		public void setSeleccion(GeneroPublicacion seleccion) {
			this.seleccion = seleccion;
		}

		public Textbox getTxtNombreGeneroPublicacion() {
			return txtNombreGeneroPublicacion;
		}

		public void setTxtNombreGeneroPublicacion(Textbox txtNombreGeneroPublicacion) {
			this.txtNombreGeneroPublicacion = txtNombreGeneroPublicacion;
		}

		public List<GeneroPublicacion> getGeneroPublicaciones() {
			return generoPublicaciones;
		}

		public void setGeneroPublicaciones(List<GeneroPublicacion> generoPublicaciones) {
			this.generoPublicaciones = generoPublicaciones;
		}

		/**
		 * Agrega el controlador a la vista, Asigna el servicio a usar
		 */
		@Override
		public void doAfterCompose(Component comp) throws Exception {
			super.doAfterCompose(comp);
			winGeneroPublicacion.setAttribute(comp.getId() + "ctrl", this);
			servicioGeneroPublicacion = (ServicioGeneroPublicacion) SpringUtil
					.getBean("beanServicioGeneroPublicacion");
			apagarBotones();
		}

		public void onClick$btnNuevo() {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("ctrlWinGeneroPublicacion", this);
			Window win = (Window) Executions.createComponents(editGeneroPublicacion, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

		public void onClick$btnEditar() {
			if (listGeneroPublicacion.getSelectedItems().isEmpty()) {
				MensajeEmergente.mostrar("SELECTREG");
			} else {
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("objeto", seleccion);
				parametros.put("ctrlWinGeneroPublicacion", this);
				Window win = (Window) Executions.createComponents(editGeneroPublicacion, null,
						parametros);
				win.doHighlighted();
				apagarBotones();
			}

		}

		public void onClick$btnEliminar() {
			if (listGeneroPublicacion.getSelectedItems().isEmpty()) {
				MensajeEmergente.mostrar("SELECTREG");
			} else {
				MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
					@Override
					public void alAceptar() {
						servicioGeneroPublicacion.borrarGeneroPublicacion(seleccion);
						generoPublicaciones.remove(seleccion);
						listGeneroPublicacion
								.setModel(new BindingListModelList(generoPublicaciones, false));
						MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
					}
				});
				apagarBotones();
			}

		}

		public void onClick$btnVerTodos() {
			generoPublicaciones = servicioGeneroPublicacion.buscarTodos('A');
			listGeneroPublicacion.setModel(new BindingListModelList(generoPublicaciones, false));
			buscando = false;
			verTodos = true;
			txtNombreGeneroPublicacion.setText(null);
			apagarBotones();
		}

		public void onClick$btnBuscar() {

			if (txtNombreGeneroPublicacion.getValue().isEmpty()) {
				MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
			} else {
				generoPublicaciones = servicioGeneroPublicacion.buscarCoincidencias(
						txtNombreGeneroPublicacion.getValue(), 'A');
				if (generoPublicaciones.isEmpty()) {
					MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
					generoPublicaciones.clear();
					listGeneroPublicacion.setModel(new BindingListModelList(generoPublicaciones, false));
				} else {
					listGeneroPublicacion.setModel(new BindingListModelList(generoPublicaciones, false));
					buscando = true;
					verTodos = false;
				}
			}
			apagarBotones();
		}

		public void onSelect$listGeneroPublicacion() {
			btnEditar.setDisabled(false);
			btnEliminar.setDisabled(false);
		}

		public void onFocus$txtNombreGeneroPublicacion() {
			apagarBotones();
		}

		public void recargar() {
			seleccion = null;
			if (verTodos)
				generoPublicaciones = servicioGeneroPublicacion.buscarTodos('A');
			else if (buscando)
				generoPublicaciones = servicioGeneroPublicacion.buscarCoincidencias(
						txtNombreGeneroPublicacion.getValue(), 'A');
			else
				generoPublicaciones.clear();

			listGeneroPublicacion.setModel(new BindingListModelList(generoPublicaciones, false));
		}

		public void apagarBotones() {
			txtNombreGeneroPublicacion.setFocus(true);
			listGeneroPublicacion.clearSelection();
			listGeneroPublicacion.selectItem(null);
			btnEditar.setDisabled(true);
			btnEliminar.setDisabled(true);
		}
}

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

import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.modelo.CursoClinico;
import org.ucla.sigma.servicio.ServicioCursoClinico;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
/**
 * @author usuario
 *
 */
public class ctrlWinCursoClinico extends GenericForwardComposer {

	private Window winCursoClinico;
	private Listbox listCursoClinico;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombreCursoClinico;

	// ----------------------------------------------------------------------------------------------------

		private String editCursoClinico = "/sigma/vistas/maestros/cursoClinico/editCursoClinico.zul";
		private ServicioCursoClinico servicioCursoClinico;
		private boolean buscando = false;
		private boolean verTodos = false;
		private MensajeListener asignarFocusBuscar = new MensajeListener() {
			public void alDestruir() {
				txtNombreCursoClinico.setFocus(true);
			};
		};

		// ----------------------------------------------------------------------------------------------------

		private CursoClinico seleccion;
		private List<CursoClinico> cursoClinicos = new ArrayList<CursoClinico>();

		// ----------------------------------------------------------------------------------------------------

		public Window getWinCursoClinico() {
			return winCursoClinico;
		}

		public void setWinCursoClinico(Window winCursoClinico) {
			this.winCursoClinico = winCursoClinico;
		}

		public Listbox getListCursoClinico() {
			return listCursoClinico;
		}

		public void setListCursoClinico(Listbox listCursoClinico) {
			this.listCursoClinico = listCursoClinico;
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

		public ServicioCursoClinico getServicioCursoClinico() {
			return servicioCursoClinico;
		}

		public void setServicioCursoClinico(ServicioCursoClinico servicioCursoClinico) {
			this.servicioCursoClinico = servicioCursoClinico;
		}

		public CursoClinico getSeleccion() {
			return seleccion;
		}

		public void setSeleccion(CursoClinico seleccion) {
			this.seleccion = seleccion;
		}

		public Textbox getTxtNombreCursoClinico() {
			return txtNombreCursoClinico;
		}

		public void setTxtNombreCursoClinico(Textbox txtNombreCursoClinico) {
			this.txtNombreCursoClinico = txtNombreCursoClinico;
		}

		public List<CursoClinico> getCursoClinicos() {
			return cursoClinicos;
		}

		public void setCursoClinicos(List<CursoClinico> cursoClinicos) {
			this.cursoClinicos = cursoClinicos;
		}

		/**
		 * Agrega el controlador a la vista, Asigna el servicio a usar
		 */
		@Override
		public void doAfterCompose(Component comp) throws Exception {
			super.doAfterCompose(comp);
			winCursoClinico.setAttribute(comp.getId() + "ctrl", this);
			servicioCursoClinico = (ServicioCursoClinico) SpringUtil
					.getBean("beanServicioCursoClinico");
			apagarBotones();
		}

		public void onClick$btnNuevo() {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("ctrlWinCursoClinico", this);
			Window win = (Window) Executions.createComponents(editCursoClinico, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

		public void onClick$btnEditar() {
			if (listCursoClinico.getSelectedItems().isEmpty()) {
				MensajeEmergente.mostrar("SELECTREG");
			} else {
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("objeto", seleccion);
				parametros.put("ctrlWinCursoClinico", this);
				Window win = (Window) Executions.createComponents(editCursoClinico, null,
						parametros);
				win.doHighlighted();
				apagarBotones();
			}

		}

		public void onClick$btnEliminar() {
			if (listCursoClinico.getSelectedItems().isEmpty()) {
				MensajeEmergente.mostrar("SELECTREG");
			} else {
				MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
					@Override
					public void alAceptar() {
						servicioCursoClinico.borrarCursoClinico(seleccion);
						cursoClinicos.remove(seleccion);
						listCursoClinico
								.setModel(new BindingListModelList(cursoClinicos, false));
						MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
					}
				});
				apagarBotones();
			}

		}

		public void onClick$btnVerTodos() {
			cursoClinicos = servicioCursoClinico.buscarTodos('A');
			listCursoClinico.setModel(new BindingListModelList(cursoClinicos, false));
			buscando = false;
			verTodos = true;
			txtNombreCursoClinico.setText(null);
			apagarBotones();
		}

		public void onClick$btnBuscar() {

			if (txtNombreCursoClinico.getValue().isEmpty()) {
				MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
			} else {
				cursoClinicos = servicioCursoClinico.buscarCoincidencias(
						txtNombreCursoClinico.getValue(), 'A');
				if (cursoClinicos.isEmpty()) {
					MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
					cursoClinicos.clear();
					listCursoClinico.setModel(new BindingListModelList(cursoClinicos, false));
				} else {
					listCursoClinico.setModel(new BindingListModelList(cursoClinicos, false));
					buscando = true;
					verTodos = false;
				}
			}
			apagarBotones();
		}

		public void onSelect$listCursoClinico() {
			btnEditar.setDisabled(false);
			btnEliminar.setDisabled(false);
		}

		public void onFocus$txtNombreCursoClinico() {
			apagarBotones();
		}

		public void recargar() {
			seleccion = null;
			if (verTodos)
				cursoClinicos = servicioCursoClinico.buscarTodos('A');
			else if (buscando)
				cursoClinicos = servicioCursoClinico.buscarCoincidencias(
						txtNombreCursoClinico.getValue(), 'A');
			else
				cursoClinicos.clear();

			listCursoClinico.setModel(new BindingListModelList(cursoClinicos, false));
		}

		public void apagarBotones() {
			txtNombreCursoClinico.setFocus(true);
			listCursoClinico.clearSelection();
			listCursoClinico.selectItem(null);
			btnEditar.setDisabled(true);
			btnEliminar.setDisabled(true);
		}
}

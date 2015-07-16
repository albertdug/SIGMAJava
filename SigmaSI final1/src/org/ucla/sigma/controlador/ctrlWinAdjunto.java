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
import org.ucla.sigma.modelo.Adjunto;
import org.ucla.sigma.servicio.ServicioAdjunto;
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
public class ctrlWinAdjunto extends GenericForwardComposer {

	private Window winAdjunto;
	private Listbox listAdjunto;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombreAdjunto;

	private String editAdjunto = "/sigma/vistas/portal/adjunto/editAdjunto.zul";
	private ServicioAdjunto servicioAdjunto;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreAdjunto.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Adjunto seleccion;
	private List<Adjunto> adjuntos = new ArrayList<Adjunto>();
	public Window getWinAdjunto() {
		return winAdjunto;
	}
	public void setWinAdjunto(Window winAdjunto) {
		this.winAdjunto = winAdjunto;
	}
	public Listbox getListAdjunto() {
		return listAdjunto;
	}
	public void setListAdjunto(Listbox listAdjunto) {
		this.listAdjunto = listAdjunto;
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
	public Textbox getTxtNombreAdjunto() {
		return txtNombreAdjunto;
	}
	public void setTxtNombreAdjunto(Textbox txtNombreAdjunto) {
		this.txtNombreAdjunto = txtNombreAdjunto;
	}
	public String getEditAdjunto() {
		return editAdjunto;
	}
	public void setEditAdjunto(String editAdjunto) {
		this.editAdjunto = editAdjunto;
	}
	public ServicioAdjunto getServicioAdjunto() {
		return servicioAdjunto;
	}
	public void setServicioAdjunto(ServicioAdjunto servicioAdjunto) {
		this.servicioAdjunto = servicioAdjunto;
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
	public Adjunto getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(Adjunto seleccion) {
		this.seleccion = seleccion;
	}
	public List<Adjunto> getAdjuntos() {
		return adjuntos;
	}
	public void setAdjuntos(List<Adjunto> adjuntos) {
		this.adjuntos = adjuntos;
	}

	// ----------------------------------------------------------------------------------------------------

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winAdjunto.setAttribute(comp.getId() + "ctrl", this);
		servicioAdjunto = (ServicioAdjunto) SpringUtil
				.getBean("beanServicioAdjunto");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinAdjunto", this);
		Window win = (Window) Executions.createComponents(editAdjunto, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listAdjunto.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinAdjunto", this);
			Window win = (Window) Executions.createComponents(editAdjunto, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listAdjunto.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioAdjunto.borrarAdjunto(seleccion);
					adjuntos.remove(seleccion);
					listAdjunto
							.setModel(new BindingListModelList(adjuntos, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		adjuntos = servicioAdjunto.buscarTodos('A');
		listAdjunto.setModel(new BindingListModelList(adjuntos, false));
		buscando = false;
		verTodos = true;
		txtNombreAdjunto.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreAdjunto.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			adjuntos = servicioAdjunto.buscarCoincidencias(
					txtNombreAdjunto.getValue(), 'A');
			if (adjuntos.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listAdjunto.setModel(new BindingListModelList(adjuntos, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listAdjunto() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreAdjunto() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			adjuntos = servicioAdjunto.buscarTodos('A');
		else if (buscando)
			adjuntos = servicioAdjunto.buscarCoincidencias(
					txtNombreAdjunto.getValue(), 'A');
		else
			adjuntos.clear();

		listAdjunto.setModel(new BindingListModelList(adjuntos, false));
	}

	public void apagarBotones() {
		txtNombreAdjunto.setFocus(true);
		listAdjunto.clearSelection();
		listAdjunto.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}
}

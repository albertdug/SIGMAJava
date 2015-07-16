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
import org.ucla.sigma.modelo.Estado;
import org.ucla.sigma.modelo.Aspecto;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioAspecto;
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
 * @author jhoan
 *
 */
public class ctrlWinAspecto extends GenericForwardComposer {

	private Window winAspecto;
	private Listbox listAspecto;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreAspecto;

	// ----------------------------------------------------------------------------------------------------

	private String editAspecto = "/sigma/vistas/maestros/aspecto/editAspecto.zul";
	private ServicioAspecto servicioAspecto;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreAspecto.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Aspecto seleccion;
	private List<Aspecto> aspectos = new ArrayList<Aspecto>();
	public Window getWinAspecto() {
		return winAspecto;
	}
	public void setWinAspecto(Window winAspecto) {
		this.winAspecto = winAspecto;
	}

	public Listbox getListAspecto() {
		return listAspecto;
	}
	public void setListAspecto(Listbox listAspecto) {
		this.listAspecto = listAspecto;
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
	public Textbox getTxtNombreAspecto() {
		return txtNombreAspecto;
	}
	public void setTxtNombreAspecto(Textbox txtNombreAspecto) {
		this.txtNombreAspecto = txtNombreAspecto;
	}
	public String getEditAspecto() {
		return editAspecto;
	}
	public void setEditAspecto(String editAspecto) {
		this.editAspecto = editAspecto;
	}
	public ServicioAspecto getServicioAspecto() {
		return servicioAspecto;
	}
	public void setServicioAspecto(ServicioAspecto servicioAspecto) {
		this.servicioAspecto = servicioAspecto;
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
	public Aspecto getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(Aspecto seleccion) {
		this.seleccion = seleccion;
	}
	public List<Aspecto> getAspectos() {
		return aspectos;
	}
	public void setAspectos(List<Aspecto> aspectos) {
		this.aspectos = aspectos;
	}

	// ----------------------------------------------------------------------------------------------------
	

	
	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winAspecto.setAttribute(comp.getId() + "ctrl", this);
		servicioAspecto = (ServicioAspecto) SpringUtil
				.getBean("beanServicioAspecto");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinAspecto", this);
		Window win = (Window) Executions.createComponents(editAspecto, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listAspecto.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinAspecto", this);
			Window win = (Window) Executions.createComponents(editAspecto, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listAspecto.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioAspecto.borrarAspecto(seleccion);
					aspectos.remove(seleccion);
					listAspecto
							.setModel(new BindingListModelList(aspectos, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		aspectos = servicioAspecto.buscarTodos('A');
		listAspecto.setModel(new BindingListModelList(aspectos, false));
		buscando = false;
		verTodos = true;
		txtNombreAspecto.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreAspecto.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			aspectos = servicioAspecto.buscarCoincidencias(
					txtNombreAspecto.getValue(), 'A');
			if (aspectos.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listAspecto.setModel(new BindingListModelList(aspectos, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listAspecto() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			aspectos = servicioAspecto.buscarTodos('A');
		else if (buscando)
			aspectos = servicioAspecto.buscarCoincidencias(
					txtNombreAspecto.getValue(), 'A');
		else
			aspectos.clear();

		listAspecto.setModel(new BindingListModelList(aspectos, false));
	}

	public void apagarBotones() {
		txtNombreAspecto.setFocus(true);
		listAspecto.clearSelection();
		listAspecto.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}


}

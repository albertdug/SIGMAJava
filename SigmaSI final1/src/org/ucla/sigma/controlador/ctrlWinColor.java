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
import org.ucla.sigma.modelo.Color;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioColor;
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
public class ctrlWinColor extends GenericForwardComposer {

	private Window winColor;
	private Listbox listColor;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreColor;

	// ----------------------------------------------------------------------------------------------------

	private String editColor = "/sigma/vistas/maestros/color/editColor.zul";
	private ServicioColor servicioColor;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreColor.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Color seleccion;
	private List<Color> colors = new ArrayList<Color>();
	public Window getWinColor() {
		return winColor;
	}
	public void setWinColor(Window winColor) {
		this.winColor = winColor;
	}

	public Listbox getListColor() {
		return listColor;
	}
	public void setListColor(Listbox listColor) {
		this.listColor = listColor;
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
	public Textbox getTxtNombreColor() {
		return txtNombreColor;
	}
	public void setTxtNombreColor(Textbox txtNombreColor) {
		this.txtNombreColor = txtNombreColor;
	}
	public String getEditColor() {
		return editColor;
	}
	public void setEditColor(String editColor) {
		this.editColor = editColor;
	}
	public ServicioColor getServicioColor() {
		return servicioColor;
	}
	public void setServicioColor(ServicioColor servicioColor) {
		this.servicioColor = servicioColor;
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
	public Color getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(Color seleccion) {
		this.seleccion = seleccion;
	}
	public List<Color> getColors() {
		return colors;
	}
	public void setColors(List<Color> colors) {
		this.colors = colors;
	}

	// ----------------------------------------------------------------------------------------------------
	

	
	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winColor.setAttribute(comp.getId() + "ctrl", this);
		servicioColor = (ServicioColor) SpringUtil
				.getBean("beanServicioColor");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinColor", this);
		Window win = (Window) Executions.createComponents(editColor, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listColor.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinColor", this);
			Window win = (Window) Executions.createComponents(editColor, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listColor.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioColor.borrarColor(seleccion);
					colors.remove(seleccion);
					listColor
							.setModel(new BindingListModelList(colors, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		colors = servicioColor.buscarTodos('A');
		listColor.setModel(new BindingListModelList(colors, false));
		buscando = false;
		verTodos = true;
		txtNombreColor.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreColor.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			colors = servicioColor.buscarCoincidencias(
					txtNombreColor.getValue(), 'A');
			if (colors.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listColor.setModel(new BindingListModelList(colors, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listColor() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			colors = servicioColor.buscarTodos('A');
		else if (buscando)
			colors = servicioColor.buscarCoincidencias(
					txtNombreColor.getValue(), 'A');
		else
			colors.clear();

		listColor.setModel(new BindingListModelList(colors, false));
	}

	public void apagarBotones() {
		txtNombreColor.setFocus(true);
		listColor.clearSelection();
		listColor.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}


}

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
import org.ucla.sigma.modelo.Difusion;
import org.ucla.sigma.servicio.ServicioDifusion;
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
 * @author promo49
 * 
 */
public class ctrlWinDifusion extends GenericForwardComposer {

	private Window winDifusion;
	private Listbox listDifusion;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private String editDifusion = "/sigma/vistas/portal/difusion/editDifusion.zul";
	private ServicioDifusion servicioDifusion;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombre.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Difusion seleccion;
	private List<Difusion> difusiones = new ArrayList<Difusion>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinDifusion() {
		return winDifusion;
	}

	public void setWinDifusion(Window winDifusion) {
		this.winDifusion = winDifusion;
	}

	public Listbox getListDifusion() {
		return listDifusion;
	}

	public void setListDifusion(Listbox listDifusion) {
		this.listDifusion = listDifusion;
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

	public String getEditDifusion() {
		return editDifusion;
	}

	public void setEditDifusion(String editDifusion) {
		this.editDifusion = editDifusion;
	}

	public ServicioDifusion getServicioDifusion() {
		return servicioDifusion;
	}

	public void setServicioDifusion(ServicioDifusion servicioDifusion) {
		this.servicioDifusion = servicioDifusion;
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

	public Difusion getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Difusion seleccion) {
		this.seleccion = seleccion;
	}

	public List<Difusion> getDifusiones() {
		return difusiones;
	}

	public void setDifusiones(List<Difusion> difusiones) {
		this.difusiones = difusiones;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winDifusion.setAttribute(comp.getId() + "ctrl", this);
		servicioDifusion = (ServicioDifusion) SpringUtil
				.getBean("beanServicioDifusion");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinDifusion", this);
		Window win = (Window) Executions.createComponents(editDifusion, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listDifusion.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinDifusion", this);
			Window win = (Window) Executions.createComponents(editDifusion,
					null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listDifusion.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioDifusion.borrarDifusion(seleccion);
					difusiones.remove(seleccion);
					listDifusion.setModel(new BindingListModelList(difusiones,
							false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		difusiones = servicioDifusion.buscarTodos('A');
		listDifusion.setModel(new BindingListModelList(difusiones, false));
		buscando = false;
		verTodos = true;
		txtNombre.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombre.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			difusiones = servicioDifusion.buscarCoincidencias(
					txtNombre.getValue(), 'A');
			if (difusiones.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listDifusion.setModel(new BindingListModelList(difusiones,
						false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listDifusion() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombre() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			difusiones = servicioDifusion.buscarTodos('A');
		else if (buscando)
			difusiones = servicioDifusion.buscarCoincidencias(
					txtNombre.getValue(), 'A');
		else
			difusiones.clear();

		listDifusion.setModel(new BindingListModelList(difusiones, false));
	}

	public void apagarBotones() {
		txtNombre.setFocus(true);
		listDifusion.clearSelection();
		listDifusion.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}
}

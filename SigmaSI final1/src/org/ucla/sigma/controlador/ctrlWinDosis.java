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
import org.ucla.sigma.modelo.Dosis;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioDosis;
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
 * @author mayer
 *
 */
public class ctrlWinDosis extends GenericForwardComposer {

	private Window winDosis;
	private Listbox listDosis;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreDosis;

	// ----------------------------------------------------------------------------------------------------

	private String editDosis = "/sigma/vistas/maestros/dosis/editDosis.zul";
	private ServicioDosis servicioDosis;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreDosis.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Dosis seleccion;
	private List<Dosis> dosiss = new ArrayList<Dosis>();
	public Window getWinDosis() {
		return winDosis;
	}
	public void setWinDosis(Window winDosis) {
		this.winDosis = winDosis;
	}

	public Listbox getListDosis() {
		return listDosis;
	}
	public void setListDosis(Listbox listDosis) {
		this.listDosis = listDosis;
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
	public Textbox getTxtNombreDosis() {
		return txtNombreDosis;
	}
	public void setTxtNombreDosis(Textbox txtNombreDosis) {
		this.txtNombreDosis = txtNombreDosis;
	}
	public String getEditDosis() {
		return editDosis;
	}
	public void setEditDosis(String editDosis) {
		this.editDosis = editDosis;
	}
	public ServicioDosis getServicioDosis() {
		return servicioDosis;
	}
	public void setServicioDosis(ServicioDosis servicioDosis) {
		this.servicioDosis = servicioDosis;
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
	public Dosis getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(Dosis seleccion) {
		this.seleccion = seleccion;
	}
	public List<Dosis> getDosiss() {
		return dosiss;
	}
	public void setDosiss(List<Dosis> dosiss) {
		this.dosiss = dosiss;
	}

	// ----------------------------------------------------------------------------------------------------
	

	
	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winDosis.setAttribute(comp.getId() + "ctrl", this);
		servicioDosis = (ServicioDosis) SpringUtil
				.getBean("beanServicioDosis");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinDosis", this);
		Window win = (Window) Executions.createComponents(editDosis, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listDosis.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinDosis", this);
			Window win = (Window) Executions.createComponents(editDosis, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listDosis.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioDosis.borrarDosis(seleccion);
					dosiss.remove(seleccion);
					listDosis
							.setModel(new BindingListModelList(dosiss, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		dosiss = servicioDosis.buscarTodos('A');
		listDosis.setModel(new BindingListModelList(dosiss, false));
		buscando = false;
		verTodos = true;
		txtNombreDosis.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreDosis.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			dosiss = servicioDosis.buscarCoincidencias(
					txtNombreDosis.getValue(), 'A');
			if (dosiss.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listDosis.setModel(new BindingListModelList(dosiss, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listDosis() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			dosiss = servicioDosis.buscarTodos('A');
		else if (buscando)
			dosiss = servicioDosis.buscarCoincidencias(
					txtNombreDosis.getValue(), 'A');
		else
			dosiss.clear();

		listDosis.setModel(new BindingListModelList(dosiss, false));
	}

	public void apagarBotones() {
		txtNombreDosis.setFocus(true);
		listDosis.clearSelection();
		listDosis.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}


}



/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Perfil;
import org.ucla.sigma.servicio.ServicioPerfil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author rafael
 *
 */
public class ctrlWinPerfil extends GenericForwardComposer {

	private Window winPerfil;
	private Listbox listPerfil;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombrePerfil;

	// ----------------------------------------------------------------------------------------------------

	private String editPerfil = "/sigma/vistas/administracion/perfil/editPerfil.zul";
	private ServicioPerfil servicioPerfil;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombrePerfil.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Perfil seleccion;
	private List<Perfil> perfils = new ArrayList<Perfil>();

	// ----------------------------------------------------------------------------------------------------
	
	public Window getWinPerfil() {
		return winPerfil;
	}

	public void setWinPerfil(Window winPerfil) {
		this.winPerfil = winPerfil;
	}

	public Listbox getListPerfil() {
		return listPerfil;
	}

	public void setListPerfil(Listbox listPerfil) {
		this.listPerfil = listPerfil;
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

	public Textbox getTxtNombrePerfil() {
		return txtNombrePerfil;
	}

	public void setTxtNombrePerfil(Textbox txtNombrePerfil) {
		this.txtNombrePerfil = txtNombrePerfil;
	}

	public String getEditPerfil() {
		return editPerfil;
	}

	public void setEditPerfil(String editPerfil) {
		this.editPerfil = editPerfil;
	}

	public ServicioPerfil getServicioPerfil() {
		return servicioPerfil;
	}

	public void setServicioPerfil(ServicioPerfil servicioPerfil) {
		this.servicioPerfil = servicioPerfil;
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

	public Perfil getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Perfil seleccion) {
		this.seleccion = seleccion;
	}

	public List<Perfil> getPerfils() {
		return perfils;
	}

	public void setPerfils(List<Perfil> perfils) {
		this.perfils = perfils;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winPerfil.setAttribute(comp.getId() + "ctrl", this);
		servicioPerfil = (ServicioPerfil) SpringUtil
				.getBean("beanServicioPerfil");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinPerfil", this);
		Window win = (Window) Executions.createComponents(editPerfil, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listPerfil.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinPerfil", this);
			Window win = (Window) Executions.createComponents(editPerfil, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listPerfil.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioPerfil.borrarPerfil(seleccion);
					perfils.remove(seleccion);
					listPerfil
							.setModel(new BindingListModelList(perfils, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		perfils = servicioPerfil.buscarTodos('A');
		listPerfil.setModel(new BindingListModelList(perfils, false));
		buscando = false;
		verTodos = true;
		txtNombrePerfil.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombrePerfil.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			perfils = servicioPerfil.buscarCoincidencias(
					txtNombrePerfil.getValue(), 'A');
			if (perfils.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listPerfil.setModel(new BindingListModelList(perfils, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listPerfil() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
		System.out.println("mas wuju y yeah!");
		Set collection = listPerfil.getSelectedItems();
		for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
			Listitem valor = (Listitem) iterator.next();
			Perfil est = (Perfil) valor.getValue();
			System.out.println(est.getNombre());
		}
		System.out.println(collection.size());
	}

	public void onFocus$txtNombrePerfil() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			perfils = servicioPerfil.buscarTodos('A');
		else if (buscando)
			perfils = servicioPerfil.buscarCoincidencias(
					txtNombrePerfil.getValue(), 'A');
		else
			perfils.clear();

		listPerfil.setModel(new BindingListModelList(perfils, false));
	}

	public void apagarBotones() {
		txtNombrePerfil.setFocus(true);
		listPerfil.clearSelection();
		listPerfil.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}
}

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
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.servicio.ServicioCiudad;
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
 * @author JP
 * 
 */
public class ctrlWinCiudad extends GenericForwardComposer {

	private Window winCiudad;
	private Listbox listCiudad;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombreCiudad;

	// ----------------------------------------------------------------------------------------------------

	private String editCiudad = "/sigma/vistas/maestros/ciudad/editCiudad.zul";
	private ServicioCiudad servicioCiudad;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreCiudad.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Ciudad seleccion;
	private List<Ciudad> ciudades = new ArrayList<Ciudad>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinCiudad() {
		return winCiudad;
	}

	public void setWinCiudad(Window winCiudad) {
		this.winCiudad = winCiudad;
	}

	public Listbox getListCiudad() {
		return listCiudad;
	}

	public void setListCiudad(Listbox listCiudad) {
		this.listCiudad = listCiudad;
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

	public Textbox getTxtNombreCiudad() {
		return txtNombreCiudad;
	}

	public void setTxtNombreCiudad(Textbox txtNombreCiudad) {
		this.txtNombreCiudad = txtNombreCiudad;
	}

	public List<Ciudad> getCiudades() {
		return ciudades;
	}

	public void setCiudades(List<Ciudad> ciudades) {
		this.ciudades = ciudades;
	}

	public Ciudad getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Ciudad seleccion) {
		this.seleccion = seleccion;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winCiudad.setAttribute(comp.getId() + "ctrl", this);
		servicioCiudad = (ServicioCiudad) SpringUtil
				.getBean("beanServicioCiudad");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinCiudad", this);
		Window win = (Window) Executions.createComponents(editCiudad, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listCiudad.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinCiudad", this);
			Window win = (Window) Executions.createComponents(editCiudad, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listCiudad.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioCiudad.borrarCiudad(seleccion);
					ciudades.remove(seleccion);
					listCiudad.setModel(new BindingListModelList(ciudades,
							false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		ciudades = servicioCiudad.buscarTodos('A');
		listCiudad.setModel(new BindingListModelList(ciudades, false));
		buscando = false;
		verTodos = true;
		txtNombreCiudad.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreCiudad.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			ciudades = servicioCiudad.buscarCoincidencias(
					txtNombreCiudad.getValue(), 'A');
			if (ciudades.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listCiudad.setModel(new BindingListModelList(ciudades, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listCiudad() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreCiudad() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			ciudades = servicioCiudad.buscarTodos('A');
		else if (buscando)
			ciudades = servicioCiudad.buscarCoincidencias(
					txtNombreCiudad.getValue(), 'A');
		else
			ciudades.clear();

		listCiudad.setModel(new BindingListModelList(ciudades, false));
	}

	public void apagarBotones() {
		listCiudad.clearSelection();
		listCiudad.selectItem(null);
		txtNombreCiudad.setFocus(true);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}
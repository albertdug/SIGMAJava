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
import org.ucla.sigma.modelo.TexturaPiel;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioTexturaPiel;
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
public class ctrlWinTexturaPiel extends GenericForwardComposer {

	private Window winTexturaPiel;
	private Listbox listTexturaPiel;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreTexturaPiel;

	// ----------------------------------------------------------------------------------------------------

	private String editTexturaPiel = "/sigma/vistas/maestros/texturapiel/editTexturaPiel.zul";
	private ServicioTexturaPiel servicioTexturaPiel;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreTexturaPiel.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private TexturaPiel seleccion;
	private List<TexturaPiel> texturapiels = new ArrayList<TexturaPiel>();
	public Window getWinTexturaPiel() {
		return winTexturaPiel;
	}
	public void setWinTexturaPiel(Window winTexturaPiel) {
		this.winTexturaPiel = winTexturaPiel;
	}

	public Listbox getListTexturaPiel() {
		return listTexturaPiel;
	}
	public void setListTexturaPiel(Listbox listTexturaPiel) {
		this.listTexturaPiel = listTexturaPiel;
	}
	public List<TexturaPiel> getTexturapiels() {
		return texturapiels;
	}
	public void setTexturapiels(List<TexturaPiel> texturapiels) {
		this.texturapiels = texturapiels;
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
	public Textbox getTxtNombreTexturaPiel() {
		return txtNombreTexturaPiel;
	}
	public void setTxtNombreTexturaPiel(Textbox txtNombreTexturaPiel) {
		this.txtNombreTexturaPiel = txtNombreTexturaPiel;
	}
	public String getEditTexturaPiel() {
		return editTexturaPiel;
	}
	public void setEditTexturaPiel(String editTexturaPiel) {
		this.editTexturaPiel = editTexturaPiel;
	}
	public ServicioTexturaPiel getServicioTexturaPiel() {
		return servicioTexturaPiel;
	}
	public void setServicioTexturaPiel(ServicioTexturaPiel servicioTexturaPiel) {
		this.servicioTexturaPiel = servicioTexturaPiel;
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
	public TexturaPiel getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(TexturaPiel seleccion) {
		this.seleccion = seleccion;
	}
	public List<TexturaPiel> getTexturaPiels() {
		return texturapiels;
	}
	public void setTexturaPiels(List<TexturaPiel> texturapiels) {
		this.texturapiels = texturapiels;
	}

	// ----------------------------------------------------------------------------------------------------
	

	
	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winTexturaPiel.setAttribute(comp.getId() + "ctrl", this);
		servicioTexturaPiel = (ServicioTexturaPiel) SpringUtil
				.getBean("beanServicioTexturaPiel");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinTexturaPiel", this);
		Window win = (Window) Executions.createComponents(editTexturaPiel, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listTexturaPiel.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinTexturaPiel", this);
			Window win = (Window) Executions.createComponents(editTexturaPiel, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listTexturaPiel.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioTexturaPiel.borrarTexturaPiel(seleccion);
					texturapiels.remove(seleccion);
					listTexturaPiel
							.setModel(new BindingListModelList(texturapiels, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		texturapiels = servicioTexturaPiel.buscarTodos('A');
		listTexturaPiel.setModel(new BindingListModelList(texturapiels, false));
		buscando = false;
		verTodos = true;
		txtNombreTexturaPiel.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreTexturaPiel.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			texturapiels = servicioTexturaPiel.buscarCoincidencias(
					txtNombreTexturaPiel.getValue(), 'A');
			if (texturapiels.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listTexturaPiel.setModel(new BindingListModelList(texturapiels, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listTexturaPiel() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			texturapiels = servicioTexturaPiel.buscarTodos('A');
		else if (buscando)
			texturapiels = servicioTexturaPiel.buscarCoincidencias(
					txtNombreTexturaPiel.getValue(), 'A');
		else
			texturapiels.clear();

		listTexturaPiel.setModel(new BindingListModelList(texturapiels, false));
	}

	public void apagarBotones() {
		txtNombreTexturaPiel.setFocus(true);
		listTexturaPiel.clearSelection();
		listTexturaPiel.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}


}

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
import org.ucla.sigma.modelo.TexturaPilosa;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioTexturaPilosa;
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
public class ctrlWinTexturaPilosa extends GenericForwardComposer {

	private Window winTexturaPilosa;
	private Listbox listTexturaPilosa;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreTexturaPilosa;

	// ----------------------------------------------------------------------------------------------------

	private String editTexturaPilosa = "/sigma/vistas/maestros/texturapilosa/editTexturaPilosa.zul";
	private ServicioTexturaPilosa servicioTexturaPilosa;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreTexturaPilosa.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private TexturaPilosa seleccion;
	private List<TexturaPilosa> texturapilosas = new ArrayList<TexturaPilosa>();
	public Window getWinTexturaPilosa() {
		return winTexturaPilosa;
	}
	public void setWinTexturaPilosa(Window winTexturaPilosa) {
		this.winTexturaPilosa = winTexturaPilosa;
	}

	public Listbox getListTexturaPilosa() {
		return listTexturaPilosa;
	}
	public void setListTexturaPilosa(Listbox listTexturaPilosa) {
		this.listTexturaPilosa = listTexturaPilosa;
	}
	public List<TexturaPilosa> getTexturapilosas() {
		return texturapilosas;
	}
	public void setTexturapilosas(List<TexturaPilosa> texturapilosas) {
		this.texturapilosas = texturapilosas;
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
	public Textbox getTxtNombreTexturaPilosa() {
		return txtNombreTexturaPilosa;
	}
	public void setTxtNombreTexturaPilosa(Textbox txtNombreTexturaPilosa) {
		this.txtNombreTexturaPilosa = txtNombreTexturaPilosa;
	}
	public String getEditTexturaPilosa() {
		return editTexturaPilosa;
	}
	public void setEditTexturaPilosa(String editTexturaPilosa) {
		this.editTexturaPilosa = editTexturaPilosa;
	}
	public ServicioTexturaPilosa getServicioTexturaPilosa() {
		return servicioTexturaPilosa;
	}
	public void setServicioTexturaPilosa(ServicioTexturaPilosa servicioTexturaPilosa) {
		this.servicioTexturaPilosa = servicioTexturaPilosa;
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
	public TexturaPilosa getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(TexturaPilosa seleccion) {
		this.seleccion = seleccion;
	}
	public List<TexturaPilosa> getTexturaPilosas() {
		return texturapilosas;
	}
	public void setTexturaPilosas(List<TexturaPilosa> texturapilosas) {
		this.texturapilosas = texturapilosas;
	}

	// ----------------------------------------------------------------------------------------------------
	

	
	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winTexturaPilosa.setAttribute(comp.getId() + "ctrl", this);
		servicioTexturaPilosa = (ServicioTexturaPilosa) SpringUtil
				.getBean("beanServicioTexturaPilosa");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinTexturaPilosa", this);
		Window win = (Window) Executions.createComponents(editTexturaPilosa, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listTexturaPilosa.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinTexturaPilosa", this);
			Window win = (Window) Executions.createComponents(editTexturaPilosa, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listTexturaPilosa.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioTexturaPilosa.borrarTexturaPilosa(seleccion);
					texturapilosas.remove(seleccion);
					listTexturaPilosa
							.setModel(new BindingListModelList(texturapilosas, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		texturapilosas = servicioTexturaPilosa.buscarTodos('A');
		listTexturaPilosa.setModel(new BindingListModelList(texturapilosas, false));
		buscando = false;
		verTodos = true;
		txtNombreTexturaPilosa.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreTexturaPilosa.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			texturapilosas = servicioTexturaPilosa.buscarCoincidencias(
					txtNombreTexturaPilosa.getValue(), 'A');
			if (texturapilosas.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listTexturaPilosa.setModel(new BindingListModelList(texturapilosas, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listTexturaPilosa() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			texturapilosas = servicioTexturaPilosa.buscarTodos('A');
		else if (buscando)
			texturapilosas = servicioTexturaPilosa.buscarCoincidencias(
					txtNombreTexturaPilosa.getValue(), 'A');
		else
			texturapilosas.clear();

		listTexturaPilosa.setModel(new BindingListModelList(texturapilosas, false));
	}

	public void apagarBotones() {
		txtNombreTexturaPilosa.setFocus(true);
		listTexturaPilosa.clearSelection();
		listTexturaPilosa.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}


}

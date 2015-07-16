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
import org.ucla.sigma.modelo.Imagen;
import org.ucla.sigma.servicio.ServicioImagen;
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
public class ctrlWinImagen extends GenericForwardComposer {

	private Window winImagen;
	private Listbox listImagen;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombreImagen;

	private String editImagen = "/sigma/vistas/portal/imagen/editImagen.zul";
	private ServicioImagen servicioImagen;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreImagen.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Imagen seleccion;
	private List<Imagen> imagens = new ArrayList<Imagen>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinImagen() {
		return winImagen;
	}

	public void setWinImagen(Window winImagen) {
		this.winImagen = winImagen;
	}

	public Listbox getListImagen() {
		return listImagen;
	}

	public void setListImagen(Listbox listImagen) {
		this.listImagen = listImagen;
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

	public Textbox getTxtNombreImagen() {
		return txtNombreImagen;
	}

	public void setTxtNombreImagen(Textbox txtNombreImagen) {
		this.txtNombreImagen = txtNombreImagen;
	}

	public String getEditImagen() {
		return editImagen;
	}

	public void setEditImagen(String editImagen) {
		this.editImagen = editImagen;
	}

	public ServicioImagen getServicioImagen() {
		return servicioImagen;
	}

	public void setServicioImagen(ServicioImagen servicioImagen) {
		this.servicioImagen = servicioImagen;
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

	public Imagen getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Imagen seleccion) {
		this.seleccion = seleccion;
	}

	public List<Imagen> getImagens() {
		return imagens;
	}

	public void setImagens(List<Imagen> imagens) {
		this.imagens = imagens;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winImagen.setAttribute(comp.getId() + "ctrl", this);
		servicioImagen = (ServicioImagen) SpringUtil
				.getBean("beanServicioImagen");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinImagen", this);
		Window win = (Window) Executions.createComponents(editImagen, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listImagen.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinImagen", this);
			Window win = (Window) Executions.createComponents(editImagen, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listImagen.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioImagen.borrarImagen(seleccion);
					imagens.remove(seleccion);
					listImagen
							.setModel(new BindingListModelList(imagens, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		imagens = servicioImagen.buscarTodos('A');
		listImagen.setModel(new BindingListModelList(imagens, false));
		buscando = false;
		verTodos = true;
		txtNombreImagen.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreImagen.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			imagens = servicioImagen.buscarCoincidencias(
					txtNombreImagen.getValue(), 'A');
			if (imagens.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listImagen.setModel(new BindingListModelList(imagens, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listImagen() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreImagen() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			imagens = servicioImagen.buscarTodos('A');
		else if (buscando)
			imagens = servicioImagen.buscarCoincidencias(
					txtNombreImagen.getValue(), 'A');
		else
			imagens.clear();

		listImagen.setModel(new BindingListModelList(imagens, false));
	}

	public void apagarBotones() {
		txtNombreImagen.setFocus(true);
		listImagen.clearSelection();
		listImagen.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

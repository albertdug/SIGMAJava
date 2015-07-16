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
import org.ucla.sigma.modelo.Usuario;
import org.ucla.sigma.modelo.Usuario;
import org.ucla.sigma.servicio.ServicioUsuario;
import org.ucla.sigma.servicio.ServicioUsuario;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author rafael
 *
 */
public class ctrlWinUsuario extends GenericForwardComposer {

	private Window winUsuario;
	private Listbox listUsuario;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Intbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private String editUsuario = "/sigma/vistas/administracion/usuario/editUsuario.zul";
	private ServicioUsuario servicioUsuario;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombre.setFocus(true);
		};
	};

	private Usuario seleccion;
	private List<Usuario> usuarios = new ArrayList<Usuario>();

	// ----------------------------------------------------------------------------------------------------
	
	public Window getWinUsuario() {
		return winUsuario;
	}

	public void setWinUsuario(Window winUsuario) {
		this.winUsuario = winUsuario;
	}

	public Listbox getListUsuario() {
		return listUsuario;
	}

	public void setListUsuario(Listbox listUsuario) {
		this.listUsuario = listUsuario;
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

	public Intbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Intbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public String getEditUsuario() {
		return editUsuario;
	}

	public void setEditUsuario(String editUsuario) {
		this.editUsuario = editUsuario;
	}

	public ServicioUsuario getServicioUsuario() {
		return servicioUsuario;
	}

	public void setServicioUsuario(ServicioUsuario servicioUsuario) {
		this.servicioUsuario = servicioUsuario;
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

	public Usuario getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Usuario seleccion) {
		this.seleccion = seleccion;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winUsuario.setAttribute(comp.getId() + "ctrl", this);
		seleccion = new Usuario();
		servicioUsuario = (ServicioUsuario) SpringUtil
				.getBean("beanServicioUsuario");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinUsuario", this);
		Window win = (Window) Executions.createComponents(editUsuario,
				null, parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listUsuario.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinUsuario", this);
			Window win = (Window) Executions.createComponents(editUsuario,
					null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listUsuario.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioUsuario.borrarUsuario(seleccion);
					usuarios.remove(seleccion);
					listUsuario.setModel(new BindingListModelList(
							usuarios, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		usuarios = servicioUsuario.buscarTodos('A');
		listUsuario.setModel(new BindingListModelList(usuarios, false));
		buscando = false;
		verTodos = true;
		txtNombre.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombre.getValue().equals(null)) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			usuarios = servicioUsuario.buscarCoincidencias(
					txtNombre.getValue(), 'A');
			if (usuarios.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listUsuario.setModel(new BindingListModelList(usuarios,
						false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listUsuario() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombre() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			usuarios = servicioUsuario.buscarTodos('A');
		else if (buscando)
			usuarios = servicioUsuario.buscarCoincidencias(
					txtNombre.getValue(), 'A');
		else
			usuarios.clear();

		listUsuario.setModel(new BindingListModelList(usuarios, false));
	}

	public void apagarBotones() {
		txtNombre.setFocus(true);
		listUsuario.clearSelection();
		listUsuario.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}
	
	
	
}

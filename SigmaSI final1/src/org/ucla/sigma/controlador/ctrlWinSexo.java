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
import org.ucla.sigma.modelo.Sexo;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioSexo;
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
public class ctrlWinSexo extends GenericForwardComposer {

	private Window winSexo;
	private Listbox listSexo;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreSexo;

	// ----------------------------------------------------------------------------------------------------

	private String editSexo = "/sigma/vistas/maestros/sexo/editSexo.zul";
	private ServicioSexo servicioSexo;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreSexo.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Sexo seleccion;
	private List<Sexo> sexos = new ArrayList<Sexo>();

	public Window getWinSexo() {
		return winSexo;
	}

	public void setWinSexo(Window winSexo) {
		this.winSexo = winSexo;
	}

	public Listbox getListSexo() {
		return listSexo;
	}

	public void setListSexo(Listbox listSexo) {
		this.listSexo = listSexo;
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

	public Textbox getTxtNombreSexo() {
		return txtNombreSexo;
	}

	public void setTxtNombreSexo(Textbox txtNombreSexo) {
		this.txtNombreSexo = txtNombreSexo;
	}

	public String getEditSexo() {
		return editSexo;
	}

	public void setEditSexo(String editSexo) {
		this.editSexo = editSexo;
	}

	public ServicioSexo getServicioSexo() {
		return servicioSexo;
	}

	public void setServicioSexo(ServicioSexo servicioSexo) {
		this.servicioSexo = servicioSexo;
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

	public Sexo getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Sexo seleccion) {
		this.seleccion = seleccion;
	}

	public List<Sexo> getSexos() {
		return sexos;
	}

	public void setSexos(List<Sexo> sexos) {
		this.sexos = sexos;
	}

	// ----------------------------------------------------------------------------------------------------

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winSexo.setAttribute(comp.getId() + "ctrl", this);
		servicioSexo = (ServicioSexo) SpringUtil.getBean("beanServicioSexo");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinSexo", this);
		Window win = (Window) Executions.createComponents(editSexo, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listSexo.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinSexo", this);
			Window win = (Window) Executions.createComponents(editSexo, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listSexo.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioSexo.borrarSexo(seleccion);
					sexos.remove(seleccion);
					listSexo.setModel(new BindingListModelList(sexos, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		sexos = servicioSexo.buscarTodos('A');
		listSexo.setModel(new BindingListModelList(sexos, false));
		buscando = false;
		verTodos = true;
		txtNombreSexo.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreSexo.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			sexos = servicioSexo.buscarCoincidencias(txtNombreSexo.getValue(),
					'A');
			if (sexos.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listSexo.setModel(new BindingListModelList(sexos, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listSexo() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			sexos = servicioSexo.buscarTodos('A');
		else if (buscando)
			sexos = servicioSexo.buscarCoincidencias(txtNombreSexo.getValue(),
					'A');
		else
			sexos.clear();

		listSexo.setModel(new BindingListModelList(sexos, false));
	}

	public void apagarBotones() {
		txtNombreSexo.setFocus(true);
		listSexo.clearSelection();
		listSexo.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

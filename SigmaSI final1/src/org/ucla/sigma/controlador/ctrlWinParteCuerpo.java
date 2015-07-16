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
import org.ucla.sigma.modelo.ParteCuerpo;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioParteCuerpo;
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
public class ctrlWinParteCuerpo extends GenericForwardComposer {

	private Window winParteCuerpo;
	private Listbox listParteCuerpo;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreParteCuerpo;

	// ----------------------------------------------------------------------------------------------------

	private String editParteCuerpo = "/sigma/vistas/maestros/partecuerpo/editParteCuerpo.zul";
	private ServicioParteCuerpo servicioParteCuerpo;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreParteCuerpo.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private ParteCuerpo seleccion;
	private List<ParteCuerpo> partecuerpos = new ArrayList<ParteCuerpo>();
	public Window getWinParteCuerpo() {
		return winParteCuerpo;
	}
	public void setWinParteCuerpo(Window winParteCuerpo) {
		this.winParteCuerpo = winParteCuerpo;
	}
	
	public Listbox getListParteCuerpo() {
		return listParteCuerpo;
	}
	public void setListParteCuerpo(Listbox listParteCuerpo) {
		this.listParteCuerpo = listParteCuerpo;
	}
	public List<ParteCuerpo> getPartecuerpos() {
		return partecuerpos;
	}
	public void setPartecuerpos(List<ParteCuerpo> partecuerpos) {
		this.partecuerpos = partecuerpos;
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
	public Textbox getTxtNombreParteCuerpo() {
		return txtNombreParteCuerpo;
	}
	public void setTxtNombreParteCuerpo(Textbox txtNombreParteCuerpo) {
		this.txtNombreParteCuerpo = txtNombreParteCuerpo;
	}
	public String getEditParteCuerpo() {
		return editParteCuerpo;
	}
	public void setEditParteCuerpo(String editParteCuerpo) {
		this.editParteCuerpo = editParteCuerpo;
	}
	public ServicioParteCuerpo getServicioParteCuerpo() {
		return servicioParteCuerpo;
	}
	public void setServicioParteCuerpo(ServicioParteCuerpo servicioParteCuerpo) {
		this.servicioParteCuerpo = servicioParteCuerpo;
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
	public ParteCuerpo getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(ParteCuerpo seleccion) {
		this.seleccion = seleccion;
	}
	public List<ParteCuerpo> getParteCuerpos() {
		return partecuerpos;
	}
	public void setParteCuerpos(List<ParteCuerpo> partecuerpos) {
		this.partecuerpos = partecuerpos;
	}

	// ----------------------------------------------------------------------------------------------------
	

	
	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winParteCuerpo.setAttribute(comp.getId() + "ctrl", this);
		servicioParteCuerpo = (ServicioParteCuerpo) SpringUtil
				.getBean("beanServicioParteCuerpo");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinParteCuerpo", this);
		Window win = (Window) Executions.createComponents(editParteCuerpo, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listParteCuerpo.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinParteCuerpo", this);
			Window win = (Window) Executions.createComponents(editParteCuerpo, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listParteCuerpo.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioParteCuerpo.borrarParteCuerpo(seleccion);
					partecuerpos.remove(seleccion);
					listParteCuerpo
							.setModel(new BindingListModelList(partecuerpos, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		partecuerpos = servicioParteCuerpo.buscarTodos('A');
		listParteCuerpo.setModel(new BindingListModelList(partecuerpos, false));
		buscando = false;
		verTodos = true;
		txtNombreParteCuerpo.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreParteCuerpo.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			partecuerpos = servicioParteCuerpo.buscarCoincidencias(
					txtNombreParteCuerpo.getValue(), 'A');
			if (partecuerpos.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listParteCuerpo.setModel(new BindingListModelList(partecuerpos, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listParteCuerpo() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			partecuerpos = servicioParteCuerpo.buscarTodos('A');
		else if (buscando)
			partecuerpos = servicioParteCuerpo.buscarCoincidencias(
					txtNombreParteCuerpo.getValue(), 'A');
		else
			partecuerpos.clear();

		listParteCuerpo.setModel(new BindingListModelList(partecuerpos, false));
	}

	public void apagarBotones() {
		txtNombreParteCuerpo.setFocus(true);
		listParteCuerpo.clearSelection();
		listParteCuerpo.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}


}

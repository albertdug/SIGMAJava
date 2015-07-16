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
import org.ucla.sigma.modelo.Pulpejos;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioPulpejos;
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
public class ctrlWinPulpejos extends GenericForwardComposer {

	private Window winPulpejos;
	private Listbox listPulpejos;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombrePulpejos;

	// ----------------------------------------------------------------------------------------------------

	private String editPulpejos = "/sigma/vistas/maestros/pulpejos/editPulpejos.zul";
	private ServicioPulpejos servicioPulpejos;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombrePulpejos.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Pulpejos seleccion;
	private List<Pulpejos> pulpejoss = new ArrayList<Pulpejos>();
	public Window getWinPulpejos() {
		return winPulpejos;
	}
	public void setWinPulpejos(Window winPulpejos) {
		this.winPulpejos = winPulpejos;
	}

	public Listbox getListPulpejos() {
		return listPulpejos;
	}
	public void setListPulpejos(Listbox listPulpejos) {
		this.listPulpejos = listPulpejos;
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
	public Textbox getTxtNombrePulpejos() {
		return txtNombrePulpejos;
	}
	public void setTxtNombrePulpejos(Textbox txtNombrePulpejos) {
		this.txtNombrePulpejos = txtNombrePulpejos;
	}
	public String getEditPulpejos() {
		return editPulpejos;
	}
	public void setEditPulpejos(String editPulpejos) {
		this.editPulpejos = editPulpejos;
	}
	public ServicioPulpejos getServicioPulpejos() {
		return servicioPulpejos;
	}
	public void setServicioPulpejos(ServicioPulpejos servicioPulpejos) {
		this.servicioPulpejos = servicioPulpejos;
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
	public Pulpejos getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(Pulpejos seleccion) {
		this.seleccion = seleccion;
	}
	public List<Pulpejos> getPulpejoss() {
		return pulpejoss;
	}
	public void setPulpejoss(List<Pulpejos> pulpejoss) {
		this.pulpejoss = pulpejoss;
	}

	// ----------------------------------------------------------------------------------------------------
	

	
	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winPulpejos.setAttribute(comp.getId() + "ctrl", this);
		servicioPulpejos = (ServicioPulpejos) SpringUtil
				.getBean("beanServicioPulpejos");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinPulpejos", this);
		Window win = (Window) Executions.createComponents(editPulpejos, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listPulpejos.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinPulpejos", this);
			Window win = (Window) Executions.createComponents(editPulpejos, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listPulpejos.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioPulpejos.borrarPulpejos(seleccion);
					pulpejoss.remove(seleccion);
					listPulpejos
							.setModel(new BindingListModelList(pulpejoss, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		pulpejoss = servicioPulpejos.buscarTodos('A');
		listPulpejos.setModel(new BindingListModelList(pulpejoss, false));
		buscando = false;
		verTodos = true;
		txtNombrePulpejos.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombrePulpejos.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			pulpejoss = servicioPulpejos.buscarCoincidencias(
					txtNombrePulpejos.getValue(), 'A');
			if (pulpejoss.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listPulpejos.setModel(new BindingListModelList(pulpejoss, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listPulpejos() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			pulpejoss = servicioPulpejos.buscarTodos('A');
		else if (buscando)
			pulpejoss = servicioPulpejos.buscarCoincidencias(
					txtNombrePulpejos.getValue(), 'A');
		else
			pulpejoss.clear();

		listPulpejos.setModel(new BindingListModelList(pulpejoss, false));
	}

	public void apagarBotones() {
		txtNombrePulpejos.setFocus(true);
		listPulpejos.clearSelection();
		listPulpejos.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}


}

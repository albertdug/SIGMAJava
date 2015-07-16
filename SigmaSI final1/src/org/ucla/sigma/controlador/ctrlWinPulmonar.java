package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Pulmonar;
import org.ucla.sigma.servicio.ServicioPulmonar;
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
 * 
 */

/**
 * @author Albert
 * 
 */
public class ctrlWinPulmonar extends GenericForwardComposer {

	private Window winPulmonar;
	private Listbox listPulmonar;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombrePulmonar;

	// ----------------------------------------------------------------------------------------------------

	private String editPulmonar = "/sigma/vistas/maestros/pulmonar/editPulmonar.zul";
	private ServicioPulmonar servicioPulmonar;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombrePulmonar.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Pulmonar seleccion;
	private List<Pulmonar> pulmonars = new ArrayList<Pulmonar>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinPulmonar() {
		return winPulmonar;
	}

	public void setWinPulmonar(Window winPulmonar) {
		this.winPulmonar = winPulmonar;
	}

	public Listbox getListPulmonar() {
		return listPulmonar;
	}

	public void setListPulmonar(Listbox listPulmonar) {
		this.listPulmonar = listPulmonar;
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

	public ServicioPulmonar getServicioPulmonar() {
		return servicioPulmonar;
	}

	public void setServicioPulmonar(ServicioPulmonar servicioPulmonar) {
		this.servicioPulmonar = servicioPulmonar;
	}

	public Pulmonar getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Pulmonar seleccion) {
		this.seleccion = seleccion;
	}

	public Textbox getTxtNombrePulmonar() {
		return txtNombrePulmonar;
	}

	public void setTxtNombrePulmonar(Textbox txtNombrePulmonar) {
		this.txtNombrePulmonar = txtNombrePulmonar;
	}

	public List<Pulmonar> getPulmonars() {
		return pulmonars;
	}

	public void setPulmonars(List<Pulmonar> pulmonars) {
		this.pulmonars = pulmonars;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winPulmonar.setAttribute(comp.getId() + "ctrl", this);
		servicioPulmonar = (ServicioPulmonar) SpringUtil
				.getBean("beanServicioPulmonar");
		apagarBotones();

	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinPulmonar", this);
		Window win = (Window) Executions.createComponents(editPulmonar, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listPulmonar.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinPulmonar", this);
			Window win = (Window) Executions.createComponents(editPulmonar,
					null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listPulmonar.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioPulmonar.borrarPulmonar(seleccion);
					pulmonars.remove(seleccion);
					listPulmonar.setModel(new BindingListModelList(pulmonars,
							false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		pulmonars = servicioPulmonar.buscarTodos('A');
		listPulmonar.setModel(new BindingListModelList(pulmonars, false));
		buscando = false;
		verTodos = true;
		txtNombrePulmonar.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombrePulmonar.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			pulmonars = servicioPulmonar.buscarCoincidencias(
					txtNombrePulmonar.getValue(), 'A');
			if (pulmonars.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listPulmonar
						.setModel(new BindingListModelList(pulmonars, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();

	}

	public void onSelect$listPulmonar() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombrePulmonar() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			pulmonars = servicioPulmonar.buscarTodos('A');
		else if (buscando)
			pulmonars = servicioPulmonar.buscarCoincidencias(
					txtNombrePulmonar.getValue(), 'A');
		else
			pulmonars.clear();

		listPulmonar.setModel(new BindingListModelList(pulmonars, false));
	}

	public void apagarBotones() {
		txtNombrePulmonar.setFocus(true);
		listPulmonar.clearSelection();
		listPulmonar.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

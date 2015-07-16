package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.modelo.ReflejoEspinal;
import org.ucla.sigma.servicio.ServicioReflejoEspinal;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinReflejoEspinal extends GenericForwardComposer {

	private Window winReflejoEspinal;
	private Listbox listReflejoEspinal;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreReflejoEspinal;

	// ----------------------------------------------------------------------------------------------------

	private String editReflejoEspinal = "/sigma/vistas/maestros/reflejoEspinal/editReflejoEspinal.zul";
	private ServicioReflejoEspinal servicioReflejoEspinal;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreReflejoEspinal.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private ReflejoEspinal seleccion;
	private List<ReflejoEspinal> reflejoEspinales = new ArrayList<ReflejoEspinal>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinReflejoEspinal() {
		return winReflejoEspinal;
	}

	public void setWinReflejoEspinal(Window winReflejoEspinal) {
		this.winReflejoEspinal = winReflejoEspinal;
	}

	public Listbox getListReflejoEspinal() {
		return listReflejoEspinal;
	}

	public void setListReflejoEspinal(Listbox listReflejoEspinal) {
		this.listReflejoEspinal = listReflejoEspinal;
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

	public ServicioReflejoEspinal getServicioReflejoEspinal() {
		return servicioReflejoEspinal;
	}

	public void setServicioReflejoEspinal(ServicioReflejoEspinal servicioReflejoEspinal) {
		this.servicioReflejoEspinal = servicioReflejoEspinal;
	}

	public ReflejoEspinal getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(ReflejoEspinal seleccion) {
		this.seleccion = seleccion;
	}

	public Textbox getTxtNombreReflejoEspinal() {
		return txtNombreReflejoEspinal;
	}

	public void setTxtNombreReflejoEspinal(Textbox txtNombreReflejoEspinal) {
		this.txtNombreReflejoEspinal = txtNombreReflejoEspinal;
	}

	public List<ReflejoEspinal> getReflejoEspinales() {
		return reflejoEspinales;
	}

	public void setReflejoEspinales(List<ReflejoEspinal> reflejoEspinales) {
		this.reflejoEspinales = reflejoEspinales;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winReflejoEspinal.setAttribute(comp.getId() + "ctrl", this);
		servicioReflejoEspinal = (ServicioReflejoEspinal) SpringUtil
				.getBean("beanServicioReflejoEspinal");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinReflejoEspinal", this);
		Window win = (Window) Executions.createComponents(editReflejoEspinal, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listReflejoEspinal.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinReflejoEspinal", this);
			Window win = (Window) Executions.createComponents(editReflejoEspinal, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listReflejoEspinal.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioReflejoEspinal.borrarReflejoEspinal(seleccion);
					reflejoEspinales.remove(seleccion);
					listReflejoEspinal
							.setModel(new BindingListModelList(reflejoEspinales, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		reflejoEspinales = servicioReflejoEspinal.buscarTodos('A');
		listReflejoEspinal.setModel(new BindingListModelList(reflejoEspinales, false));
		buscando = false;
		verTodos = true;
		txtNombreReflejoEspinal.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreReflejoEspinal.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			reflejoEspinales = servicioReflejoEspinal.buscarCoincidencias(
					txtNombreReflejoEspinal.getValue(), 'A');
			if (reflejoEspinales.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
				reflejoEspinales.clear();
				listReflejoEspinal.setModel(new BindingListModelList(reflejoEspinales, false));
			} else {
				listReflejoEspinal.setModel(new BindingListModelList(reflejoEspinales, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listReflejoEspinal() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreReflejoEspinal() {
		apagarBotones();
	}

	public void recargar() {
		seleccion= null;
		if (verTodos)
			reflejoEspinales = servicioReflejoEspinal.buscarTodos('A');
		else if (buscando)
			reflejoEspinales = servicioReflejoEspinal.buscarCoincidencias(
					txtNombreReflejoEspinal.getValue(), 'A');
		else
			reflejoEspinales.clear();

		listReflejoEspinal.setModel(new BindingListModelList(reflejoEspinales, false));
	}

	public void apagarBotones() {
		txtNombreReflejoEspinal.setFocus(true);
		listReflejoEspinal.clearSelection();
		listReflejoEspinal.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

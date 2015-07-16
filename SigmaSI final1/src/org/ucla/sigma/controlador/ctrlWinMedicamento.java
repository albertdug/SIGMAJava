package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Medicamento;
import org.ucla.sigma.servicio.ServicioMedicamento;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinMedicamento extends GenericForwardComposer {

	private Window winMedicamento;
	private Listbox listMedicamento;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreMedicamento;

	// ----------------------------------------------------------------------------------------------------

	private String editMedicamento = "/sigma/vistas/maestros/medicamento/editMedicamento.zul";
	private ServicioMedicamento servicioMedicamento;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreMedicamento.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Medicamento seleccion;
	private List<Medicamento> medicamentos = new ArrayList<Medicamento>();

	// ----------------------------------------------------------------------------------------------------

	
	
	public Window getWinMedicamento() {
		return winMedicamento;
	}

	public void setWinMedicamento(Window winMedicamento) {
		this.winMedicamento = winMedicamento;
	}

	

	public Listbox getListMedicamento() {
		return listMedicamento;
	}

	public void setListMedicamento(Listbox listMedicamento) {
		this.listMedicamento = listMedicamento;
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

	public ServicioMedicamento getServicioMedicamento() {
		return servicioMedicamento;
	}

	public void setServicioMedicamento(ServicioMedicamento servicioMedicamento) {
		this.servicioMedicamento = servicioMedicamento;
	}

	public Medicamento getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Medicamento seleccion) {
		this.seleccion = seleccion;
	}

	public Textbox getTxtNombreMedicamento() {
		return txtNombreMedicamento;
	}

	public void setTxtNombreMedicamento(Textbox txtNombreMedicamento) {
		this.txtNombreMedicamento = txtNombreMedicamento;
	}

	public List<Medicamento> getMedicamentos() {
		return medicamentos;
	}

	public void setMedicamentos(List<Medicamento> medicamentos) {
		this.medicamentos = medicamentos;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winMedicamento.setAttribute(comp.getId() + "ctrl", this);
		servicioMedicamento = (ServicioMedicamento) SpringUtil
				.getBean("beanServicioMedicamento");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinMedicamento", this);
		Window win = (Window) Executions.createComponents(editMedicamento, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listMedicamento.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinMedicamento", this);
			Window win = (Window) Executions.createComponents(editMedicamento, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listMedicamento.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioMedicamento.borrarMedicamento(seleccion);
					medicamentos.remove(seleccion);
					listMedicamento
							.setModel(new BindingListModelList(medicamentos, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		medicamentos = servicioMedicamento.buscarTodos('A');
		listMedicamento.setModel(new BindingListModelList(medicamentos, false));
		buscando = false;
		verTodos = true;
		txtNombreMedicamento.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreMedicamento.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			medicamentos = servicioMedicamento.buscarCoincidencias(
					txtNombreMedicamento.getValue(), 'A');
			if (medicamentos.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listMedicamento.setModel(new BindingListModelList(medicamentos, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listMedicamento() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreMedicamento() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			medicamentos = servicioMedicamento.buscarTodos('A');
		else if (buscando)
			medicamentos = servicioMedicamento.buscarCoincidencias(
					txtNombreMedicamento.getValue(), 'A');
		else
			medicamentos.clear();

		listMedicamento.setModel(new BindingListModelList(medicamentos, false));
	}

	public void apagarBotones() {
		txtNombreMedicamento.setFocus(true);
		listMedicamento.clearSelection();
		listMedicamento.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

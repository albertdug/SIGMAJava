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
import org.ucla.sigma.modelo.Examen;
import org.ucla.sigma.servicio.ServicioExamen;
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
 * @author Albert
 * 
 */
public class ctrlWinExamen extends GenericForwardComposer {

	private Window winExamen;
	private Listbox listExamen;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombreExamen;

	private String editExamen = "/sigma/vistas/maestros/examen/editExamen.zul";
	private ServicioExamen servicioExamen;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreExamen.setFocus(true);
		};
	};

	private Examen seleccion;
	private List<Examen> examenes = new ArrayList<Examen>();

	public Window getWinExamen() {
		return winExamen;
	}

	public void setWinExamen(Window winExamen) {
		this.winExamen = winExamen;
	}

	public Listbox getListExamen() {
		return listExamen;
	}

	public void setListExamen(Listbox listExamen) {
		this.listExamen = listExamen;
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

	public Textbox getTxtNombreExamen() {
		return txtNombreExamen;
	}

	public void setTxtNombreExamen(Textbox txtNombreExamen) {
		this.txtNombreExamen = txtNombreExamen;
	}

	public String getEditExamen() {
		return editExamen;
	}

	public void setEditExamen(String editExamen) {
		this.editExamen = editExamen;
	}

	public ServicioExamen getServicioExamen() {
		return servicioExamen;
	}

	public void setServicioExamen(ServicioExamen servicioExamen) {
		this.servicioExamen = servicioExamen;
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

	public Examen getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Examen seleccion) {
		this.seleccion = seleccion;
	}

	public List<Examen> getExamenes() {
		return examenes;
	}

	public void setExamenes(List<Examen> examenes) {
		this.examenes = examenes;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winExamen.setAttribute(comp.getId() + "ctrl", this);
		servicioExamen = (ServicioExamen) SpringUtil
				.getBean("beanServicioExamen");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinExamen", this);
		Window win = (Window) Executions.createComponents(editExamen, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listExamen.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinExamen", this);
			Window win = (Window) Executions.createComponents(editExamen, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listExamen.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioExamen.borrarExamen(seleccion);
					examenes.remove(seleccion);
					listExamen.setModel(new BindingListModelList(examenes,
							false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		examenes = servicioExamen.buscarTodos('A');
		listExamen.setModel(new BindingListModelList(examenes, false));
		buscando = false;
		verTodos = true;
		txtNombreExamen.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreExamen.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			examenes = servicioExamen.buscarCoincidencias(
					txtNombreExamen.getValue(), 'A');
			if (examenes.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listExamen.setModel(new BindingListModelList(examenes, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listExamen() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreExamen() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			examenes = servicioExamen.buscarTodos('A');
		else if (buscando)
			examenes = servicioExamen.buscarCoincidencias(
					txtNombreExamen.getValue(), 'A');
		else
			examenes.clear();

		listExamen.setModel(new BindingListModelList(examenes, false));
	}

	public void apagarBotones() {
		txtNombreExamen.setFocus(true);
		listExamen.clearSelection();
		listExamen.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

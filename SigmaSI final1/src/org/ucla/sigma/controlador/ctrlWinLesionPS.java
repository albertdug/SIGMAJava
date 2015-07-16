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
import org.ucla.sigma.modelo.LesionPS;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioLesionPS;
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
public class ctrlWinLesionPS extends GenericForwardComposer {

	private Window winLesionPS;
	private Listbox listLesionPS;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreLesionPS;

	// ----------------------------------------------------------------------------------------------------

	private String editLesionPS = "/sigma/vistas/maestros/lesionps/editLesionPS.zul";
	private ServicioLesionPS servicioLesionPS;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreLesionPS.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private LesionPS seleccion;
	private List<LesionPS> lesionpss = new ArrayList<LesionPS>();
	public Window getWinLesionPS() {
		return winLesionPS;
	}
	public void setWinLesionPS(Window winLesionPS) {
		this.winLesionPS = winLesionPS;
	}
	
	public Listbox getListLesionPS() {
		return listLesionPS;
	}
	public void setListLesionPS(Listbox listLesionPS) {
		this.listLesionPS = listLesionPS;
	}
	public List<LesionPS> getLesionpss() {
		return lesionpss;
	}
	public void setLesionpss(List<LesionPS> lesionpss) {
		this.lesionpss = lesionpss;
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
	public Textbox getTxtNombreLesionPS() {
		return txtNombreLesionPS;
	}
	public void setTxtNombreLesionPS(Textbox txtNombreLesionPS) {
		this.txtNombreLesionPS = txtNombreLesionPS;
	}
	public String getEditLesionPS() {
		return editLesionPS;
	}
	public void setEditLesionPS(String editLesionPS) {
		this.editLesionPS = editLesionPS;
	}
	public ServicioLesionPS getServicioLesionPS() {
		return servicioLesionPS;
	}
	public void setServicioLesionPS(ServicioLesionPS servicioLesionPS) {
		this.servicioLesionPS = servicioLesionPS;
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
	public LesionPS getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(LesionPS seleccion) {
		this.seleccion = seleccion;
	}
	public List<LesionPS> getLesionPSs() {
		return lesionpss;
	}
	public void setLesionPSs(List<LesionPS> lesionpss) {
		this.lesionpss = lesionpss;
	}

	// ----------------------------------------------------------------------------------------------------
	

	
	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winLesionPS.setAttribute(comp.getId() + "ctrl", this);
		servicioLesionPS = (ServicioLesionPS) SpringUtil
				.getBean("beanServicioLesionPS");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinLesionPS", this);
		Window win = (Window) Executions.createComponents(editLesionPS, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listLesionPS.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinLesionPS", this);
			Window win = (Window) Executions.createComponents(editLesionPS, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listLesionPS.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioLesionPS.borrarLesionPS(seleccion);
					lesionpss.remove(seleccion);
					listLesionPS
							.setModel(new BindingListModelList(lesionpss, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		lesionpss = servicioLesionPS.buscarTodos('A');
		listLesionPS.setModel(new BindingListModelList(lesionpss, false));
		buscando = false;
		verTodos = true;
		txtNombreLesionPS.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreLesionPS.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			lesionpss = servicioLesionPS.buscarCoincidencias(
					txtNombreLesionPS.getValue(), 'A');
			if (lesionpss.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listLesionPS.setModel(new BindingListModelList(lesionpss, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listLesionPS() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			lesionpss = servicioLesionPS.buscarTodos('A');
		else if (buscando)
			lesionpss = servicioLesionPS.buscarCoincidencias(
					txtNombreLesionPS.getValue(), 'A');
		else
			lesionpss.clear();

		listLesionPS.setModel(new BindingListModelList(lesionpss, false));
	}

	public void apagarBotones() {
		txtNombreLesionPS.setFocus(true);
		listLesionPS.clearSelection();
		listLesionPS.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}


}

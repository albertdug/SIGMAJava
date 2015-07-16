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
import org.ucla.sigma.modelo.LesionPrimaria;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioLesionPrimaria;
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
public class ctrlWinLesionPrimaria extends GenericForwardComposer {

	private Window winLesionPrimaria;
	private Listbox listLesionPrimaria;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreLesionPrimaria;

	// ----------------------------------------------------------------------------------------------------

	private String editLesionPrimaria = "/sigma/vistas/maestros/lesionprimaria/editLesionPrimaria.zul";
	private ServicioLesionPrimaria servicioLesionPrimaria;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreLesionPrimaria.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private LesionPrimaria seleccion;
	private List<LesionPrimaria> lesionprimarias = new ArrayList<LesionPrimaria>();
	public Window getWinLesionPrimaria() {
		return winLesionPrimaria;
	}
	public void setWinLesionPrimaria(Window winLesionPrimaria) {
		this.winLesionPrimaria = winLesionPrimaria;
	}

	public Listbox getListLesionPrimaria() {
		return listLesionPrimaria;
	}
	public void setListLesionPrimaria(Listbox listLesionPrimaria) {
		this.listLesionPrimaria = listLesionPrimaria;
	}
	public List<LesionPrimaria> getLesionprimarias() {
		return lesionprimarias;
	}
	public void setLesionprimarias(List<LesionPrimaria> lesionprimarias) {
		this.lesionprimarias = lesionprimarias;
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
	public Textbox getTxtNombreLesionPrimaria() {
		return txtNombreLesionPrimaria;
	}
	public void setTxtNombreLesionPrimaria(Textbox txtNombreLesionPrimaria) {
		this.txtNombreLesionPrimaria = txtNombreLesionPrimaria;
	}
	public String getEditLesionPrimaria() {
		return editLesionPrimaria;
	}
	public void setEditLesionPrimaria(String editLesionPrimaria) {
		this.editLesionPrimaria = editLesionPrimaria;
	}
	public ServicioLesionPrimaria getServicioLesionPrimaria() {
		return servicioLesionPrimaria;
	}
	public void setServicioLesionPrimaria(ServicioLesionPrimaria servicioLesionPrimaria) {
		this.servicioLesionPrimaria = servicioLesionPrimaria;
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
	public LesionPrimaria getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(LesionPrimaria seleccion) {
		this.seleccion = seleccion;
	}
	public List<LesionPrimaria> getLesionPrimarias() {
		return lesionprimarias;
	}
	public void setLesionPrimarias(List<LesionPrimaria> lesionprimarias) {
		this.lesionprimarias = lesionprimarias;
	}

	// ----------------------------------------------------------------------------------------------------
	

	
	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winLesionPrimaria.setAttribute(comp.getId() + "ctrl", this);
		servicioLesionPrimaria = (ServicioLesionPrimaria) SpringUtil
				.getBean("beanServicioLesionPrimaria");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinLesionPrimaria", this);
		Window win = (Window) Executions.createComponents(editLesionPrimaria, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listLesionPrimaria.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinLesionPrimaria", this);
			Window win = (Window) Executions.createComponents(editLesionPrimaria, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listLesionPrimaria.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioLesionPrimaria.borrarLesionPrimaria(seleccion);
					lesionprimarias.remove(seleccion);
					listLesionPrimaria
							.setModel(new BindingListModelList(lesionprimarias, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		lesionprimarias = servicioLesionPrimaria.buscarTodos('A');
		listLesionPrimaria.setModel(new BindingListModelList(lesionprimarias, false));
		buscando = false;
		verTodos = true;
		txtNombreLesionPrimaria.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreLesionPrimaria.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			lesionprimarias = servicioLesionPrimaria.buscarCoincidencias(
					txtNombreLesionPrimaria.getValue(), 'A');
			if (lesionprimarias.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listLesionPrimaria.setModel(new BindingListModelList(lesionprimarias, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listLesionPrimaria() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			lesionprimarias = servicioLesionPrimaria.buscarTodos('A');
		else if (buscando)
			lesionprimarias = servicioLesionPrimaria.buscarCoincidencias(
					txtNombreLesionPrimaria.getValue(), 'A');
		else
			lesionprimarias.clear();

		listLesionPrimaria.setModel(new BindingListModelList(lesionprimarias, false));
	}

	public void apagarBotones() {
		txtNombreLesionPrimaria.setFocus(true);
		listLesionPrimaria.clearSelection();
		listLesionPrimaria.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}


}
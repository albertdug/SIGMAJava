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
import org.ucla.sigma.modelo.LesionSecundaria;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioLesionSecundaria;
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
public class ctrlWinLesionSecundaria extends GenericForwardComposer {

	private Window winLesionSecundaria;
	private Listbox listLesionSecundaria;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreLesionSecundaria;

	// ----------------------------------------------------------------------------------------------------

	private String editLesionSecundaria = "/sigma/vistas/maestros/lesionsecundaria/editLesionSecundaria.zul";
	private ServicioLesionSecundaria servicioLesionSecundaria;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreLesionSecundaria.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private LesionSecundaria seleccion;
	private List<LesionSecundaria> lesionsecundarias = new ArrayList<LesionSecundaria>();
	public Window getWinLesionSecundaria() {
		return winLesionSecundaria;
	}
	public void setWinLesionSecundaria(Window winLesionSecundaria) {
		this.winLesionSecundaria = winLesionSecundaria;
	}

	public Listbox getListLesionSecundaria() {
		return listLesionSecundaria;
	}
	public void setListLesionSecundaria(Listbox listLesionSecundaria) {
		this.listLesionSecundaria = listLesionSecundaria;
	}
	public List<LesionSecundaria> getLesionsecundarias() {
		return lesionsecundarias;
	}
	public void setLesionsecundarias(List<LesionSecundaria> lesionsecundarias) {
		this.lesionsecundarias = lesionsecundarias;
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
	public Textbox getTxtNombreLesionSecundaria() {
		return txtNombreLesionSecundaria;
	}
	public void setTxtNombreLesionSecundaria(Textbox txtNombreLesionSecundaria) {
		this.txtNombreLesionSecundaria = txtNombreLesionSecundaria;
	}
	public String getEditLesionSecundaria() {
		return editLesionSecundaria;
	}
	public void setEditLesionSecundaria(String editLesionSecundaria) {
		this.editLesionSecundaria = editLesionSecundaria;
	}
	public ServicioLesionSecundaria getServicioLesionSecundaria() {
		return servicioLesionSecundaria;
	}
	public void setServicioLesionSecundaria(ServicioLesionSecundaria servicioLesionSecundaria) {
		this.servicioLesionSecundaria = servicioLesionSecundaria;
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
	public LesionSecundaria getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(LesionSecundaria seleccion) {
		this.seleccion = seleccion;
	}
	public List<LesionSecundaria> getLesionSecundarias() {
		return lesionsecundarias;
	}
	public void setLesionSecundarias(List<LesionSecundaria> lesionsecundarias) {
		this.lesionsecundarias = lesionsecundarias;
	}

	// ----------------------------------------------------------------------------------------------------
	

	
	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winLesionSecundaria.setAttribute(comp.getId() + "ctrl", this);
		servicioLesionSecundaria = (ServicioLesionSecundaria) SpringUtil
				.getBean("beanServicioLesionSecundaria");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinLesionSecundaria", this);
		Window win = (Window) Executions.createComponents(editLesionSecundaria, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listLesionSecundaria.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinLesionSecundaria", this);
			Window win = (Window) Executions.createComponents(editLesionSecundaria, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listLesionSecundaria.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioLesionSecundaria.borrarLesionSecundaria(seleccion);
					lesionsecundarias.remove(seleccion);
					listLesionSecundaria
							.setModel(new BindingListModelList(lesionsecundarias, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		lesionsecundarias = servicioLesionSecundaria.buscarTodos('A');
		listLesionSecundaria.setModel(new BindingListModelList(lesionsecundarias, false));
		buscando = false;
		verTodos = true;
		txtNombreLesionSecundaria.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreLesionSecundaria.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			lesionsecundarias = servicioLesionSecundaria.buscarCoincidencias(
					txtNombreLesionSecundaria.getValue(), 'A');
			if (lesionsecundarias.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listLesionSecundaria.setModel(new BindingListModelList(lesionsecundarias, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listLesionSecundaria() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			lesionsecundarias = servicioLesionSecundaria.buscarTodos('A');
		else if (buscando)
			lesionsecundarias = servicioLesionSecundaria.buscarCoincidencias(
					txtNombreLesionSecundaria.getValue(), 'A');
		else
			lesionsecundarias.clear();

		listLesionSecundaria.setModel(new BindingListModelList(lesionsecundarias, false));
	}

	public void apagarBotones() {
		txtNombreLesionSecundaria.setFocus(true);
		listLesionSecundaria.clearSelection();
		listLesionSecundaria.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}


}

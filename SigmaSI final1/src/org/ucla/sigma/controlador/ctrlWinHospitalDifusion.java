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
import org.ucla.sigma.modelo.HospitalDifusion;
import org.ucla.sigma.servicio.ServicioHospitalDifusion;
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
 * @author lis
 * 
 */
public class ctrlWinHospitalDifusion extends GenericForwardComposer {

	private Window winHospitalDifusion;
	private Listbox listHospitalDifusion;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtEnlace;

	// ----------------------------------------------------------------------------------------------------

	private String editHospitalDifusion = "/sigma/vistas/portal/hospitalDifusion/editHospitalDifusion.zul";
	private ServicioHospitalDifusion servicioHospitalDifusion;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtEnlace.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private HospitalDifusion seleccion;
	private List<HospitalDifusion> hospitalDifusiones = new ArrayList<HospitalDifusion>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinHospitalDifusion() {
		return winHospitalDifusion;
	}

	public void setWinHospitalDifusion(Window winHospitalDifusion) {
		this.winHospitalDifusion = winHospitalDifusion;
	}

	public Listbox getListHospitalDifusion() {
		return listHospitalDifusion;
	}

	public void setListHospitalDifusion(Listbox listHospitalDifusion) {
		this.listHospitalDifusion = listHospitalDifusion;
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

	public Textbox getTxtEnlace() {
		return txtEnlace;
	}

	public void setTxtEnlace(Textbox txtEnlace) {
		this.txtEnlace = txtEnlace;
	}

	public String getEditHospitalDifusion() {
		return editHospitalDifusion;
	}

	public void setEditHospitalDifusion(String editHospitalDifusion) {
		this.editHospitalDifusion = editHospitalDifusion;
	}

	public ServicioHospitalDifusion getServicioHospitalDifusion() {
		return servicioHospitalDifusion;
	}

	public void setServicioHospitalDifusion(
			ServicioHospitalDifusion servicioHospitalDifusion) {
		this.servicioHospitalDifusion = servicioHospitalDifusion;
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

	public HospitalDifusion getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(HospitalDifusion seleccion) {
		this.seleccion = seleccion;
	}

	public List<HospitalDifusion> getHospitalDifusiones() {
		return hospitalDifusiones;
	}

	public void setHospitalDifusiones(List<HospitalDifusion> hospitalDifusiones) {
		this.hospitalDifusiones = hospitalDifusiones;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winHospitalDifusion.setAttribute(comp.getId() + "ctrl", this);
		servicioHospitalDifusion = (ServicioHospitalDifusion) SpringUtil
				.getBean("beanServicioHospitalDifusion");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinHospitalDifusion", this);
		Window win = (Window) Executions.createComponents(editHospitalDifusion,
				null, parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listHospitalDifusion.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinHospitalDifusion", this);
			Window win = (Window) Executions.createComponents(
					editHospitalDifusion, null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listHospitalDifusion.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioHospitalDifusion.borrarHospitalDifusion(seleccion);
					hospitalDifusiones.remove(seleccion);
					listHospitalDifusion.setModel(new BindingListModelList(
							hospitalDifusiones, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		hospitalDifusiones = servicioHospitalDifusion.buscarTodos('A');
		listHospitalDifusion.setModel(new BindingListModelList(
				hospitalDifusiones, false));
		buscando = false;
		verTodos = true;
		txtEnlace.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtEnlace.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			hospitalDifusiones = servicioHospitalDifusion.buscarCoincidencias(
					txtEnlace.getValue(), 'A');
			if (hospitalDifusiones.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listHospitalDifusion.setModel(new BindingListModelList(
						hospitalDifusiones, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listHospitalDifusion() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtEnlace() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			hospitalDifusiones = servicioHospitalDifusion.buscarTodos('A');
		else if (buscando)
			hospitalDifusiones = servicioHospitalDifusion.buscarCoincidencias(
					txtEnlace.getValue(), 'A');
		else
			hospitalDifusiones.clear();

		listHospitalDifusion.setModel(new BindingListModelList(
				hospitalDifusiones, false));
	}

	public void apagarBotones() {
		txtEnlace.setFocus(true);
		listHospitalDifusion.clearSelection();
		listHospitalDifusion.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}
}
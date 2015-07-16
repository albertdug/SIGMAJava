/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Hospital;
import org.ucla.sigma.servicio.ServicioHospital;
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
 * @author rafael
 * 
 */
public class ctrlWinHospital extends GenericForwardComposer {

	private Window winHospital;
	private Listbox listHospital;
	private Button btnEditar;

	private String editHospital = "/sigma/vistas/maestros/hospital/editHospital.zul";
	private ServicioHospital servicioHospital;

	// ----------------------------------------------------------------------------------------------------

	private Hospital seleccion;
	private List<Hospital> hospitals = new ArrayList<Hospital>();

	public Window getWinHospital() {
		return winHospital;
	}

	public void setWinHospital(Window winHospital) {
		this.winHospital = winHospital;
	}

	public Listbox getListHospital() {
		return listHospital;
	}

	public void setListHospital(Listbox listHospital) {
		this.listHospital = listHospital;
	}

	public Button getBtnEditar() {
		return btnEditar;
	}

	public void setBtnEditar(Button btnEditar) {
		this.btnEditar = btnEditar;
	}

	public String getEditHospital() {
		return editHospital;
	}

	public void setEditHospital(String editHospital) {
		this.editHospital = editHospital;
	}

	public ServicioHospital getServicioHospital() {
		return servicioHospital;
	}

	public void setServicioHospital(ServicioHospital servicioHospital) {
		this.servicioHospital = servicioHospital;
	}

	public Hospital getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Hospital seleccion) {
		this.seleccion = seleccion;
	}

	public List<Hospital> getHospitals() {
		return hospitals;
	}

	public void setHospitals(List<Hospital> hospitals) {
		this.hospitals = hospitals;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winHospital.setAttribute(comp.getId() + "ctrl", this);
		servicioHospital = (ServicioHospital) SpringUtil
				.getBean("beanServicioHospital");
		seleccion = servicioHospital.buscarUnico();

		hospitals = new ArrayList<Hospital>();
		hospitals.add(seleccion);

	}

	public void onClick$btnEditar() {
		if (listHospital.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinHospital", this);
			Window win = (Window) Executions.createComponents(editHospital,
					null, parametros);
			win.doHighlighted();
		}

	}

	public void onSelect$listHospital() {
		btnEditar.setDisabled(false);
	}

	public void recargar() {
		seleccion = servicioHospital.buscarUnico();

		hospitals = new ArrayList<Hospital>();
		hospitals.add(seleccion);

		listHospital.setModel(new BindingListModelList(hospitals, false));
	}

}

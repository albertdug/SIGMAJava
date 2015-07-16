/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.List;
import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.modelo.Suscripcion;
import org.ucla.sigma.servicio.ServicioSuscripcion;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

/**
 * @author usuario
 * 
 */
public class ctrlWinSuscripcion extends GenericForwardComposer {

	private Window winSuscripcion;
	private Listbox listSuscripciones;
	private Button btnVer;
	private Button btnBuscar;
	private Datebox dboxFechaHasta;
	private Datebox dboxFechaDesde;

	private ServicioSuscripcion servicioSuscripcion;
	private boolean buscando = false;
	private boolean verTodos = false;

	private Suscripcion seleccion;
	private List<Suscripcion> suscripciones = new ArrayList<Suscripcion>();

	public List<Suscripcion> getSuscripciones() {
		return suscripciones;
	}

	public void setSuscripciones(List<Suscripcion> suscripciones) {
		this.suscripciones = suscripciones;
	}

	public Suscripcion getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Suscripcion seleccion) {
		this.seleccion = seleccion;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winSuscripcion.setAttribute(comp.getId() + "ctrl", this);
		servicioSuscripcion = (ServicioSuscripcion) SpringUtil
				.getBean("beanServicioSuscripcion");
		dboxFechaHasta.setDisabled(true);
	}

	public void onClick$btnBuscar() {
		// ('DATESELECT','Debe seleccionar un rango de fecha','VALIDACION'),

		if (dboxFechaDesde.getValue() == null
				|| dboxFechaHasta.getValue() == null) {
			MensajeEmergente.mostrar("DATESELECT");
		} else {

			suscripciones = servicioSuscripcion.buscarCoincidenciasFecha(
					dboxFechaDesde.getValue(), dboxFechaHasta.getValue(), 'A');

			if (suscripciones.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND");
				suscripciones.clear();
				listSuscripciones.setModel(new BindingListModelList(
						suscripciones, false));
			} else {
				listSuscripciones.setModel(new BindingListModelList(
						suscripciones, false));
				listSuscripciones.clearSelection();
				listSuscripciones.selectItem(null);
				buscando = true;
				verTodos = false;
			}
		}

	}

	public void recargar() {
		seleccion = null;

		listSuscripciones.clearSelection();
		listSuscripciones.selectItem(null);
		if (verTodos) {
			suscripciones = servicioSuscripcion.buscarTodos();
		} else if (buscando) {

			suscripciones = servicioSuscripcion.buscarCoincidenciasFecha(
					dboxFechaDesde.getValue(), dboxFechaHasta.getValue(), 'A');

		} else {
			suscripciones.clear();
		}

		listSuscripciones.setModel(new BindingListModelList(suscripciones,
				false));
	}

	public void onClick$btnVer() {
		apagarBotones();
		suscripciones = servicioSuscripcion.buscarTodos();
		listSuscripciones.setModel(new BindingListModelList(suscripciones,
				false));

		buscando = false;
		verTodos = true;
	}

	public void onChange$dboxFechaDesde() {
		dboxFechaHasta.setValue(null);
		dboxFechaHasta.setConstraint("between "
				+ HelperDate.format(dboxFechaDesde.getValue(), "yyyyMMdd")
				+ " and " + HelperDate.nowFormat("yyyyMMdd"));
		dboxFechaHasta.setDisabled(false);
	}

	public void apagarBotones() {
		dboxFechaDesde.setValue(null);
		dboxFechaHasta.setValue(null);
		dboxFechaHasta.setDisabled(true);
		listSuscripciones.clearSelection();
		listSuscripciones.selectItem(null);
	}

}

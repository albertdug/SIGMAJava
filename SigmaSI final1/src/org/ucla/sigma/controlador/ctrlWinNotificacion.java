/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.modelo.Notificacion;
import org.ucla.sigma.servicio.ServicioNotificacion;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

/**
 * @author JP
 * 
 */
public class ctrlWinNotificacion extends GenericForwardComposer {

	private Window winNotificacion;
	private Listbox listNotificaciones;
	private Button btnVer;
	private Button btnLeer;
	private Button btnBuscar;
	private Radiogroup rgTipo;
	private Datebox dboxFechaHasta;
	private Datebox dboxFechaDesde;
	private String responder = "/sigma/vistas/portal/notificacion/Responder.zul";

	private ServicioNotificacion servicioNotificacion;
	private boolean buscando = false;
	private boolean verTodos = false;

	private Notificacion seleccion;
	private List<Notificacion> notificaciones = new ArrayList<Notificacion>();

	public Button getBtnLeer() {
		return btnLeer;
	}

	public void setBtnLeer(Button btnLeer) {
		this.btnLeer = btnLeer;
	}

	public List<Notificacion> getNotificaciones() {
		return notificaciones;
	}

	public void setNotificaciones(List<Notificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}

	public Notificacion getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Notificacion seleccion) {
		this.seleccion = seleccion;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winNotificacion.setAttribute(comp.getId() + "ctrl", this);
		servicioNotificacion = (ServicioNotificacion) SpringUtil
				.getBean("beanServicioNotificacion");
		btnLeer.setDisabled(true);
		dboxFechaHasta.setDisabled(true);
	}

	public void onClick$btnBuscar() {
		// ('DATESELECT','Debe seleccionar un rango de fecha','VALIDACION'),
		
		if (dboxFechaDesde.getValue() == null
				|| dboxFechaHasta.getValue() == null) {
			MensajeEmergente.mostrar("DATESELECT");
		} else {
			if (rgTipo.getSelectedItem().getValue().equals("null")) {
				notificaciones = servicioNotificacion
						.buscarCoincidenciasFechas(dboxFechaDesde.getValue(),
								dboxFechaHasta.getValue());
			} else {
				notificaciones = servicioNotificacion
						.buscarCoincidenciasFechas(dboxFechaDesde.getValue(),
								dboxFechaHasta.getValue(), rgTipo
										.getSelectedItem().getValue().charAt(0));
			}

			if (notificaciones.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND");
				notificaciones.clear();
				listNotificaciones.setModel(new BindingListModelList(
						notificaciones, false));
			} else {
				listNotificaciones.setModel(new BindingListModelList(
						notificaciones, false));
				listNotificaciones.clearSelection();
				listNotificaciones.selectItem(null);
				btnLeer.setDisabled(true);
				buscando = true;
				verTodos = false;
			}
		}

	}

	public void recargar() {
		seleccion = null;

		listNotificaciones.clearSelection();
		listNotificaciones.selectItem(null);
		btnLeer.setDisabled(true);
		if (verTodos) {
			notificaciones = servicioNotificacion.buscarTodos();
		} else if (buscando) {
			if (rgTipo.getSelectedItem().getValue().equals("null")) {
				notificaciones = servicioNotificacion
						.buscarCoincidenciasFechas(dboxFechaDesde.getValue(),
								dboxFechaHasta.getValue());
			} else {
				notificaciones = servicioNotificacion
						.buscarCoincidenciasFechas(dboxFechaDesde.getValue(),
								dboxFechaHasta.getValue(), rgTipo
										.getSelectedItem().getValue().charAt(0));
			}
		} else {
			notificaciones.clear();
		}

		listNotificaciones.setModel(new BindingListModelList(notificaciones,
				false));
	}
	
	public void onClick$btnLeer() {
		if (listNotificaciones.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinNotificacion", this);
			Window win = (Window) Executions.createComponents(responder, null,
					parametros);
			win.doHighlighted();
		}
	}
	
	public void onSelect$listNotificaciones() {
		btnLeer.setDisabled(false);		
	}

	public void onClick$btnVer() {
		apagarBotones();
		notificaciones = servicioNotificacion.buscarTodos();
		listNotificaciones.setModel(new BindingListModelList(notificaciones,
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
		listNotificaciones.clearSelection();
		listNotificaciones.selectItem(null);
		btnLeer.setDisabled(true);
	}
	
	

}

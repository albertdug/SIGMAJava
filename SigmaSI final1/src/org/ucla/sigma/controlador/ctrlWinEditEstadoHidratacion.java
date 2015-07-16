/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.EstadoHidratacion;
import org.ucla.sigma.servicio.ServicioEstadoHidratacion;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author jhoan
 *
 */
public class ctrlWinEditEstadoHidratacion extends GenericForwardComposer {

	private Window winEditEstadoHidratacion;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioEstadoHidratacion servicioEstadoHidratacion;
	private ctrlWinEstadoHidratacion ctrlwinestadohidratacion;
	private EstadoHidratacion estadohidratacion;
	
	
	// ----------------------------------------------------------------------------------------------------
	
	public Window getWinEditEstadoHidratacion() {
		return winEditEstadoHidratacion;
	}

	public void setWinEditEstadoHidratacion(Window winEditEstadoHidratacion) {
		this.winEditEstadoHidratacion = winEditEstadoHidratacion;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioEstadoHidratacion getServicioEstadoHidratacion() {
		return servicioEstadoHidratacion;
	}

	public void setServicioEstadoHidratacion(
			ServicioEstadoHidratacion servicioEstadoHidratacion) {
		this.servicioEstadoHidratacion = servicioEstadoHidratacion;
	}

	public ctrlWinEstadoHidratacion getCtrlwinestadohidratacion() {
		return ctrlwinestadohidratacion;
	}

	public void setCtrlwinestadohidratacion(
			ctrlWinEstadoHidratacion ctrlwinestadohidratacion) {
		this.ctrlwinestadohidratacion = ctrlwinestadohidratacion;
	}

	public EstadoHidratacion getEstadohidratacion() {
		return estadohidratacion;
	}

	public void setEstadohidratacion(EstadoHidratacion estadohidratacion) {
		this.estadohidratacion = estadohidratacion;
	}
	
	// ----------------------------------------------------------------------------------------------------	

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditEstadoHidratacion.setAttribute(comp.getId() + "ctrl", this);
		servicioEstadoHidratacion = (ServicioEstadoHidratacion) SpringUtil
				.getBean("beanServicioEstadoHidratacion");
		estadohidratacion = new EstadoHidratacion();
		ctrlwinestadohidratacion = (ctrlWinEstadoHidratacion) arg.get("ctrlWinEstadoHidratacion");
		if (!(arg.get("objeto") == null)) {
			estadohidratacion = (EstadoHidratacion) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinestadohidratacion.recargar();
		ctrlwinestadohidratacion.apagarBotones();
		this.winEditEstadoHidratacion.detach();
	}

	public void onClick$btnGuardar() {

		if (txtNombre.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nNombre",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtNombre.setFocus(true);
						}
					});
		} else {
			EstadoHidratacion estadohidratacionTemp = servicioEstadoHidratacion.buscarUno(estadohidratacion.getNombre());
			if (estadohidratacionTemp != null) {
				estadohidratacion.setId(estadohidratacionTemp.getId());
			}

			try {
				servicioEstadoHidratacion.guardarEstadoHidratacion(estadohidratacion);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinestadohidratacion.recargar();
						ctrlwinestadohidratacion.apagarBotones();
						winEditEstadoHidratacion.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditEstadoHidratacion() {
		ctrlwinestadohidratacion.apagarBotones();
		this.winEditEstadoHidratacion.detach();
	}



}

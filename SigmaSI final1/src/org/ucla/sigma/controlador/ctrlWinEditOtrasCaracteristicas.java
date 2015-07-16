/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.OtrasCaracteristicas;
import org.ucla.sigma.servicio.ServicioOtrasCaracteristicas;
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
public class ctrlWinEditOtrasCaracteristicas extends GenericForwardComposer {

	private Window winEditOtrasCaracteristicas;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioOtrasCaracteristicas servicioOtrasCaracteristicas;
	private ctrlWinOtrasCaracteristicas ctrlwinotrascaracteristicas;

	// ----------------------------------------------------------------------------------------------------

	private OtrasCaracteristicas otrascaracteristicas;

	

	// ----------------------------------------------------------------------------------------------------
	
	public Window getWinEditOtrasCaracteristicas() {
		return winEditOtrasCaracteristicas;
	}

	public void setWinEditOtrasCaracteristicas(Window winEditOtrasCaracteristicas) {
		this.winEditOtrasCaracteristicas = winEditOtrasCaracteristicas;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioOtrasCaracteristicas getServicioOtrasCaracteristicas() {
		return servicioOtrasCaracteristicas;
	}

	public void setServicioOtrasCaracteristicas(
			ServicioOtrasCaracteristicas servicioOtrasCaracteristicas) {
		this.servicioOtrasCaracteristicas = servicioOtrasCaracteristicas;
	}

	public ctrlWinOtrasCaracteristicas getCtrlwinotrascaracteristicas() {
		return ctrlwinotrascaracteristicas;
	}

	public void setCtrlwinotrascaracteristicas(
			ctrlWinOtrasCaracteristicas ctrlwinotrascaracteristicas) {
		this.ctrlwinotrascaracteristicas = ctrlwinotrascaracteristicas;
	}

	public OtrasCaracteristicas getOtrascaracteristicas() {
		return otrascaracteristicas;
	}

	public void setOtrascaracteristicas(OtrasCaracteristicas otrascaracteristicas) {
		this.otrascaracteristicas = otrascaracteristicas;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditOtrasCaracteristicas.setAttribute(comp.getId() + "ctrl", this);
		servicioOtrasCaracteristicas = (ServicioOtrasCaracteristicas) SpringUtil
				.getBean("beanServicioOtrasCaracteristicas");
		otrascaracteristicas = new OtrasCaracteristicas();
		ctrlwinotrascaracteristicas = (ctrlWinOtrasCaracteristicas) arg.get("ctrlWinOtrasCaracteristicas");
		if (!(arg.get("objeto") == null)) {
			otrascaracteristicas = (OtrasCaracteristicas) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinotrascaracteristicas.recargar();
		ctrlwinotrascaracteristicas.apagarBotones();
		this.winEditOtrasCaracteristicas.detach();
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
			OtrasCaracteristicas otrascaracteristicasTemp = servicioOtrasCaracteristicas.buscarUno(otrascaracteristicas.getNombre());
			if (otrascaracteristicasTemp != null) {
				otrascaracteristicas.setId(otrascaracteristicasTemp.getId());
			}

			try {
				servicioOtrasCaracteristicas.guardarOtrasCaracteristicas(otrascaracteristicas);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinotrascaracteristicas.recargar();
						ctrlwinotrascaracteristicas.apagarBotones();
						winEditOtrasCaracteristicas.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditOtrasCaracteristicas() {
		ctrlwinotrascaracteristicas.apagarBotones();
		this.winEditOtrasCaracteristicas.detach();
	}



}

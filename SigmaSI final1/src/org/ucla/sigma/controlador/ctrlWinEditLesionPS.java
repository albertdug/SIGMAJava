/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.LesionPS;
import org.ucla.sigma.servicio.ServicioLesionPS;
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
public class ctrlWinEditLesionPS extends GenericForwardComposer {

	private Window winEditLesionPS;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioLesionPS servicioLesionPS;
	private ctrlWinLesionPS ctrlwinlesionps;
	private LesionPS lesionps;
	

	// ----------------------------------------------------------------------------------------------------
	
	public Window getWinEditLesionPS() {
		return winEditLesionPS;
	}

	public void setWinEditLesionPS(Window winEditLesionPS) {
		this.winEditLesionPS = winEditLesionPS;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioLesionPS getServicioLesionPS() {
		return servicioLesionPS;
	}

	public void setServicioLesionPS(ServicioLesionPS servicioLesionPS) {
		this.servicioLesionPS = servicioLesionPS;
	}

	public ctrlWinLesionPS getCtrlwinlesionps() {
		return ctrlwinlesionps;
	}

	public void setCtrlwinlesionps(ctrlWinLesionPS ctrlwinlesionps) {
		this.ctrlwinlesionps = ctrlwinlesionps;
	}

	public LesionPS getLesionps() {
		return lesionps;
	}

	public void setLesionps(LesionPS lesionps) {
		this.lesionps = lesionps;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditLesionPS.setAttribute(comp.getId() + "ctrl", this);
		servicioLesionPS = (ServicioLesionPS) SpringUtil
				.getBean("beanServicioLesionPS");
		lesionps = new LesionPS();
		ctrlwinlesionps = (ctrlWinLesionPS) arg.get("ctrlWinLesionPS");
		if (!(arg.get("objeto") == null)) {
			lesionps = (LesionPS) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinlesionps.recargar();
		ctrlwinlesionps.apagarBotones();
		this.winEditLesionPS.detach();
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
			LesionPS lesionpsTemp = servicioLesionPS.buscarUno(lesionps.getNombre());
			if (lesionpsTemp != null) {
				lesionps.setId(lesionpsTemp.getId());
			}

			try {
				servicioLesionPS.guardarLesionPS(lesionps);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinlesionps.recargar();
						ctrlwinlesionps.apagarBotones();
						winEditLesionPS.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditLesionPS() {
		ctrlwinlesionps.apagarBotones();
		this.winEditLesionPS.detach();
	}



}

/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Profundidad;
import org.ucla.sigma.servicio.ServicioProfundidad;
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
public class ctrlWinEditProfundidad extends GenericForwardComposer {

	private Window winEditProfundidad;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioProfundidad servicioProfundidad;
	private ctrlWinProfundidad ctrlwinprofundidad;
	private Profundidad profundidad;



	// ----------------------------------------------------------------------------------------------------
	
	public Window getWinEditProfundidad() {
		return winEditProfundidad;
	}

	public void setWinEditProfundidad(Window winEditProfundidad) {
		this.winEditProfundidad = winEditProfundidad;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioProfundidad getServicioProfundidad() {
		return servicioProfundidad;
	}

	public void setServicioProfundidad(ServicioProfundidad servicioProfundidad) {
		this.servicioProfundidad = servicioProfundidad;
	}

	public ctrlWinProfundidad getCtrlwinprofundidad() {
		return ctrlwinprofundidad;
	}

	public void setCtrlwinprofundidad(ctrlWinProfundidad ctrlwinprofundidad) {
		this.ctrlwinprofundidad = ctrlwinprofundidad;
	}

	public Profundidad getProfundidad() {
		return profundidad;
	}

	public void setProfundidad(Profundidad profundidad) {
		this.profundidad = profundidad;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditProfundidad.setAttribute(comp.getId() + "ctrl", this);
		servicioProfundidad = (ServicioProfundidad) SpringUtil
				.getBean("beanServicioProfundidad");
		profundidad = new Profundidad();
		ctrlwinprofundidad = (ctrlWinProfundidad) arg.get("ctrlWinProfundidad");
		if (!(arg.get("objeto") == null)) {
			profundidad = (Profundidad) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinprofundidad.recargar();
		ctrlwinprofundidad.apagarBotones();
		this.winEditProfundidad.detach();
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
			Profundidad profundidadTemp = servicioProfundidad.buscarUno(profundidad.getNombre());
			if (profundidadTemp != null) {
				profundidad.setId(profundidadTemp.getId());
			}

			try {
				servicioProfundidad.guardarProfundidad(profundidad);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinprofundidad.recargar();
						ctrlwinprofundidad.apagarBotones();
						winEditProfundidad.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditProfundidad() {
		ctrlwinprofundidad.apagarBotones();
		this.winEditProfundidad.detach();
	}



}

/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.AvanceEnfermedad;
import org.ucla.sigma.servicio.ServicioAvanceEnfermedad;
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
public class ctrlWinEditAvanceEnfermedad extends GenericForwardComposer {

	private Window winEditAvanceEnfermedad;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioAvanceEnfermedad servicioAvanceEnfermedad;
	private ctrlWinAvanceEnfermedad ctrlwinavanceenfermedad;
	private AvanceEnfermedad avanceenfermedad;

	// ----------------------------------------------------------------------------------------------------
	
	public Window getWinEditAvanceEnfermedad() {
		return winEditAvanceEnfermedad;
	}

	public void setWinEditAvanceEnfermedad(Window winEditAvanceEnfermedad) {
		this.winEditAvanceEnfermedad = winEditAvanceEnfermedad;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioAvanceEnfermedad getServicioAvanceEnfermedad() {
		return servicioAvanceEnfermedad;
	}

	public void setServicioAvanceEnfermedad(
			ServicioAvanceEnfermedad servicioAvanceEnfermedad) {
		this.servicioAvanceEnfermedad = servicioAvanceEnfermedad;
	}

	public ctrlWinAvanceEnfermedad getCtrlwinavanceenfermedad() {
		return ctrlwinavanceenfermedad;
	}

	public void setCtrlwinavanceenfermedad(
			ctrlWinAvanceEnfermedad ctrlwinavanceenfermedad) {
		this.ctrlwinavanceenfermedad = ctrlwinavanceenfermedad;
	}

	public AvanceEnfermedad getAvanceenfermedad() {
		return avanceenfermedad;
	}

	public void setAvanceenfermedad(AvanceEnfermedad avanceenfermedad) {
		this.avanceenfermedad = avanceenfermedad;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditAvanceEnfermedad.setAttribute(comp.getId() + "ctrl", this);
		servicioAvanceEnfermedad = (ServicioAvanceEnfermedad) SpringUtil
				.getBean("beanServicioAvanceEnfermedad");
		avanceenfermedad = new AvanceEnfermedad();
		ctrlwinavanceenfermedad = (ctrlWinAvanceEnfermedad) arg.get("ctrlWinAvanceEnfermedad");
		if (!(arg.get("objeto") == null)) {
			avanceenfermedad = (AvanceEnfermedad) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinavanceenfermedad.recargar();
		ctrlwinavanceenfermedad.apagarBotones();
		this.winEditAvanceEnfermedad.detach();
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
			AvanceEnfermedad avanceenfermedadTemp = servicioAvanceEnfermedad.buscarUno(avanceenfermedad.getNombre());
			if (avanceenfermedadTemp != null) {
				avanceenfermedad.setId(avanceenfermedadTemp.getId());
			}

			try {
				servicioAvanceEnfermedad.guardarAvanceEnfermedad(avanceenfermedad);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinavanceenfermedad.recargar();
						ctrlwinavanceenfermedad.apagarBotones();
						winEditAvanceEnfermedad.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditAvanceEnfermedad() {
		ctrlwinavanceenfermedad.apagarBotones();
		this.winEditAvanceEnfermedad.detach();
	}



}

/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.LesionSecundaria;
import org.ucla.sigma.servicio.ServicioLesionSecundaria;
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
public class ctrlWinEditLesionSecundaria extends GenericForwardComposer {

	private Window winEditLesionSecundaria;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioLesionSecundaria servicioLesionSecundaria;
	private ctrlWinLesionSecundaria ctrlwinlesionsecundaria;
	private LesionSecundaria lesionsecundaria;

	// ----------------------------------------------------------------------------------------------------
	
	public Window getWinEditLesionSecundaria() {
		return winEditLesionSecundaria;
	}

	public void setWinEditLesionSecundaria(Window winEditLesionSecundaria) {
		this.winEditLesionSecundaria = winEditLesionSecundaria;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioLesionSecundaria getServicioLesionSecundaria() {
		return servicioLesionSecundaria;
	}

	public void setServicioLesionSecundaria(
			ServicioLesionSecundaria servicioLesionSecundaria) {
		this.servicioLesionSecundaria = servicioLesionSecundaria;
	}

	public ctrlWinLesionSecundaria getCtrlwinlesionsecundaria() {
		return ctrlwinlesionsecundaria;
	}

	public void setCtrlwinlesionsecundaria(
			ctrlWinLesionSecundaria ctrlwinlesionsecundaria) {
		this.ctrlwinlesionsecundaria = ctrlwinlesionsecundaria;
	}

	public LesionSecundaria getLesionsecundaria() {
		return lesionsecundaria;
	}

	public void setLesionsecundaria(LesionSecundaria lesionsecundaria) {
		this.lesionsecundaria = lesionsecundaria;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditLesionSecundaria.setAttribute(comp.getId() + "ctrl", this);
		servicioLesionSecundaria = (ServicioLesionSecundaria) SpringUtil
				.getBean("beanServicioLesionSecundaria");
		lesionsecundaria = new LesionSecundaria();
		ctrlwinlesionsecundaria = (ctrlWinLesionSecundaria) arg.get("ctrlWinLesionSecundaria");
		if (!(arg.get("objeto") == null)) {
			lesionsecundaria = (LesionSecundaria) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinlesionsecundaria.recargar();
		ctrlwinlesionsecundaria.apagarBotones();
		this.winEditLesionSecundaria.detach();
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
			LesionSecundaria lesionsecundariaTemp = servicioLesionSecundaria.buscarUno(lesionsecundaria.getNombre());
			if (lesionsecundariaTemp != null) {
				lesionsecundaria.setId(lesionsecundariaTemp.getId());
			}

			try {
				servicioLesionSecundaria.guardarLesionSecundaria(lesionsecundaria);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinlesionsecundaria.recargar();
						ctrlwinlesionsecundaria.apagarBotones();
						winEditLesionSecundaria.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditLesionSecundaria() {
		ctrlwinlesionsecundaria.apagarBotones();
		this.winEditLesionSecundaria.detach();
	}



}

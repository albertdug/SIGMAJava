/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.LesionPrimaria;
import org.ucla.sigma.servicio.ServicioLesionPrimaria;
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
public class ctrlWinEditLesionPrimaria extends GenericForwardComposer {

	private Window winEditLesionPrimaria;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioLesionPrimaria servicioLesionPrimaria;
	private ctrlWinLesionPrimaria ctrlwinlesionprimaria;
	private LesionPrimaria lesionprimaria;

	// ----------------------------------------------------------------------------------------------------

	
	public Window getWinEditLesionPrimaria() {
		return winEditLesionPrimaria;
	}

	public void setWinEditLesionPrimaria(Window winEditLesionPrimaria) {
		this.winEditLesionPrimaria = winEditLesionPrimaria;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioLesionPrimaria getServicioLesionPrimaria() {
		return servicioLesionPrimaria;
	}

	public void setServicioLesionPrimaria(
			ServicioLesionPrimaria servicioLesionPrimaria) {
		this.servicioLesionPrimaria = servicioLesionPrimaria;
	}

	public ctrlWinLesionPrimaria getCtrlwinlesionprimaria() {
		return ctrlwinlesionprimaria;
	}

	public void setCtrlwinlesionprimaria(ctrlWinLesionPrimaria ctrlwinlesionprimaria) {
		this.ctrlwinlesionprimaria = ctrlwinlesionprimaria;
	}

	public LesionPrimaria getLesionprimaria() {
		return lesionprimaria;
	}

	public void setLesionprimaria(LesionPrimaria lesionprimaria) {
		this.lesionprimaria = lesionprimaria;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditLesionPrimaria.setAttribute(comp.getId() + "ctrl", this);
		servicioLesionPrimaria = (ServicioLesionPrimaria) SpringUtil
				.getBean("beanServicioLesionPrimaria");
		lesionprimaria = new LesionPrimaria();
		ctrlwinlesionprimaria = (ctrlWinLesionPrimaria) arg.get("ctrlWinLesionPrimaria");
		if (!(arg.get("objeto") == null)) {
			lesionprimaria = (LesionPrimaria) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinlesionprimaria.recargar();
		ctrlwinlesionprimaria.apagarBotones();
		this.winEditLesionPrimaria.detach();
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
			LesionPrimaria lesionprimariaTemp = servicioLesionPrimaria.buscarUno(lesionprimaria.getNombre());
			if (lesionprimariaTemp != null) {
				lesionprimaria.setId(lesionprimariaTemp.getId());
			}

			try {
				servicioLesionPrimaria.guardarLesionPrimaria(lesionprimaria);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinlesionprimaria.recargar();
						ctrlwinlesionprimaria.apagarBotones();
						winEditLesionPrimaria.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditLesionPrimaria() {
		ctrlwinlesionprimaria.apagarBotones();
		this.winEditLesionPrimaria.detach();
	}



}

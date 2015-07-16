/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Aspecto;
import org.ucla.sigma.servicio.ServicioAspecto;
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
public class ctrlWinEditAspecto extends GenericForwardComposer {

	private Window winEditAspecto;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioAspecto servicioAspecto;
	private ctrlWinAspecto ctrlwinaspecto;
	private Aspecto aspecto;	

	// ----------------------------------------------------------------------------------------------------
	
	public Window getWinEditAspecto() {
		return winEditAspecto;
	}

	public void setWinEditAspecto(Window winEditAspecto) {
		this.winEditAspecto = winEditAspecto;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioAspecto getServicioAspecto() {
		return servicioAspecto;
	}

	public void setServicioAspecto(ServicioAspecto servicioAspecto) {
		this.servicioAspecto = servicioAspecto;
	}

	public ctrlWinAspecto getCtrlwinaspecto() {
		return ctrlwinaspecto;
	}

	public void setCtrlwinaspecto(ctrlWinAspecto ctrlwinaspecto) {
		this.ctrlwinaspecto = ctrlwinaspecto;
	}

	public Aspecto getAspecto() {
		return aspecto;
	}

	public void setAspecto(Aspecto aspecto) {
		this.aspecto = aspecto;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditAspecto.setAttribute(comp.getId() + "ctrl", this);
		servicioAspecto = (ServicioAspecto) SpringUtil
				.getBean("beanServicioAspecto");
		aspecto = new Aspecto();
		ctrlwinaspecto = (ctrlWinAspecto) arg.get("ctrlWinAspecto");
		if (!(arg.get("objeto") == null)) {
			aspecto = (Aspecto) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinaspecto.recargar();
		ctrlwinaspecto.apagarBotones();
		this.winEditAspecto.detach();
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
			Aspecto aspectoTemp = servicioAspecto.buscarUno(aspecto.getNombre());
			if (aspectoTemp != null) {
				aspecto.setId(aspectoTemp.getId());
			}

			try {
				servicioAspecto.guardarAspecto(aspecto);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinaspecto.recargar();
						ctrlwinaspecto.apagarBotones();
						winEditAspecto.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditAspecto() {
		ctrlwinaspecto.apagarBotones();
		this.winEditAspecto.detach();
	}



}

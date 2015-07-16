/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Perfil;
import org.ucla.sigma.servicio.ServicioPerfil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author rafael
 *
 */
public class ctrlWinEditPerfil extends GenericForwardComposer {

	private Window winEditPerfil;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;
	private Textbox txtDescripcion;

	// ----------------------------------------------------------------------------------------------------

	private ServicioPerfil servicioPerfil;
	private ctrlWinPerfil ctrlwinperfil;

	// ----------------------------------------------------------------------------------------------------

	private Perfil perfil;

	// ----------------------------------------------------------------------------------------------------
	
	public Window getWinEditPerfil() {
		return winEditPerfil;
	}

	public Textbox getTxtDescripcion() {
		return txtDescripcion;
	}

	public void setTxtDescripcion(Textbox txtDescripcion) {
		this.txtDescripcion = txtDescripcion;
	}

	public void setWinEditPerfil(Window winEditPerfil) {
		this.winEditPerfil = winEditPerfil;
	}

	public Button getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(Button btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public Button getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(Button btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioPerfil getServicioPerfil() {
		return servicioPerfil;
	}

	public void setServicioPerfil(ServicioPerfil servicioPerfil) {
		this.servicioPerfil = servicioPerfil;
	}

	public ctrlWinPerfil getCtrlwinperfil() {
		return ctrlwinperfil;
	}

	public void setCtrlwinperfil(ctrlWinPerfil ctrlwinperfil) {
		this.ctrlwinperfil = ctrlwinperfil;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditPerfil.setAttribute(comp.getId() + "ctrl", this);
		servicioPerfil = (ServicioPerfil) SpringUtil
				.getBean("beanServicioPerfil");
		perfil = new Perfil();
		ctrlwinperfil = (ctrlWinPerfil) arg.get("ctrlWinPerfil");
		if (!(arg.get("objeto") == null)) {
			perfil = (Perfil) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinperfil.recargar();
		ctrlwinperfil.apagarBotones();
		this.winEditPerfil.detach();
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
			Perfil perfilTemp = servicioPerfil.buscarUno(perfil.getNombre());
			if (perfilTemp != null) {
				perfil.setId(perfilTemp.getId());
			}

			try {
				servicioPerfil.guardarPerfil(perfil);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinperfil.recargar();
						ctrlwinperfil.apagarBotones();
						winEditPerfil.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditPerfil() {
		ctrlwinperfil.apagarBotones();
		this.winEditPerfil.detach();
	}

}

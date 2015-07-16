package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Membrana;
import org.ucla.sigma.servicio.ServicioMembrana;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author Albert
 * 
 */
public class ctrlWinEditMembrana extends GenericForwardComposer {

	private Window winEditMembrana;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioMembrana servicioMembrana;
	private ctrlWinMembrana ctrlwinmembrana;

	// ----------------------------------------------------------------------------------------------------

	private Membrana membrana;

	// ----------------------------------------------------------------------------------------------------

	public ServicioMembrana getServicioMembrana() {
		return servicioMembrana;
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

	public void setServicioMembrana(ServicioMembrana servicioMembrana) {
		this.servicioMembrana = servicioMembrana;
	}

	public ctrlWinMembrana getCtrlwinmembrana() {
		return ctrlwinmembrana;
	}

	public void setCtrlwinmembrana(ctrlWinMembrana ctrlwinmembrana) {
		this.ctrlwinmembrana = ctrlwinmembrana;
	}

	public Window getWinEditMembrana() {
		return winEditMembrana;
	}

	public void setWinEditMembrana(Window winEditMembrana) {
		this.winEditMembrana = winEditMembrana;
	}

	public Membrana getMembrana() {
		return membrana;
	}

	public void setMembrana(Membrana membrana) {
		this.membrana = membrana;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditMembrana.setAttribute(comp.getId() + "ctrl", this);
		servicioMembrana = (ServicioMembrana) SpringUtil
				.getBean("beanServicioMembrana");
		membrana = new Membrana();
		ctrlwinmembrana = (ctrlWinMembrana) arg.get("ctrlWinMembrana");
		if (!(arg.get("objeto") == null)) {
			membrana = (Membrana) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinmembrana.apagarBotones();
		ctrlwinmembrana.recargar();
		this.winEditMembrana.detach();
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
			Membrana membranaTemp = servicioMembrana.buscarUno(membrana
					.getNombre());
			if (membranaTemp != null) {
				membrana.setId(membranaTemp.getId());
			}

			try {
				servicioMembrana.guardarMembrana(membrana);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinmembrana.recargar();
						ctrlwinmembrana.apagarBotones();
						winEditMembrana.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditEspecie() {
		ctrlwinmembrana.apagarBotones();
		this.winEditMembrana.detach();
	}

}

/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.ParteCuerpo;
import org.ucla.sigma.servicio.ServicioParteCuerpo;
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
public class ctrlWinEditParteCuerpo extends GenericForwardComposer {

	private Window winEditParteCuerpo;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioParteCuerpo servicioParteCuerpo;
	private ctrlWinParteCuerpo ctrlwinpartecuerpo;
	private ParteCuerpo partecuerpo;
	
	// ----------------------------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------------------------
	
	public Window getWinEditParteCuerpo() {
		return winEditParteCuerpo;
	}

	public void setWinEditParteCuerpo(Window winEditParteCuerpo) {
		this.winEditParteCuerpo = winEditParteCuerpo;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioParteCuerpo getServicioParteCuerpo() {
		return servicioParteCuerpo;
	}

	public void setServicioParteCuerpo(ServicioParteCuerpo servicioParteCuerpo) {
		this.servicioParteCuerpo = servicioParteCuerpo;
	}

	public ctrlWinParteCuerpo getCtrlwinpartecuerpo() {
		return ctrlwinpartecuerpo;
	}

	public void setCtrlwinpartecuerpo(ctrlWinParteCuerpo ctrlwinpartecuerpo) {
		this.ctrlwinpartecuerpo = ctrlwinpartecuerpo;
	}

	public ParteCuerpo getPartecuerpo() {
		return partecuerpo;
	}

	public void setPartecuerpo(ParteCuerpo partecuerpo) {
		this.partecuerpo = partecuerpo;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditParteCuerpo.setAttribute(comp.getId() + "ctrl", this);
		servicioParteCuerpo = (ServicioParteCuerpo) SpringUtil
				.getBean("beanServicioParteCuerpo");
		partecuerpo = new ParteCuerpo();
		ctrlwinpartecuerpo = (ctrlWinParteCuerpo) arg.get("ctrlWinParteCuerpo");
		if (!(arg.get("objeto") == null)) {
			partecuerpo = (ParteCuerpo) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinpartecuerpo.recargar();
		ctrlwinpartecuerpo.apagarBotones();
		this.winEditParteCuerpo.detach();
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
			ParteCuerpo partecuerpoTemp = servicioParteCuerpo.buscarUno(partecuerpo.getNombre());
			if (partecuerpoTemp != null) {
				partecuerpo.setId(partecuerpoTemp.getId());
			}

			try {
				servicioParteCuerpo.guardarParteCuerpo(partecuerpo);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinpartecuerpo.recargar();
						ctrlwinpartecuerpo.apagarBotones();
						winEditParteCuerpo.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditParteCuerpo() {
		ctrlwinpartecuerpo.apagarBotones();
		this.winEditParteCuerpo.detach();
	}



}

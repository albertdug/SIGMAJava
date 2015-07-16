/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.MembranaMucosa;
import org.ucla.sigma.servicio.ServicioMembranaMucosa;
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
public class ctrlWinEditMembranaMucosa extends GenericForwardComposer {

	private Window winEditMembranaMucosa;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioMembranaMucosa servicioMembranaMucosa;
	private ctrlWinMembranaMucosa ctrlwinmembranamucosa;
	private MembranaMucosa membranamucosa;
	

	// ----------------------------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------------------------
	
	public Window getWinEditMembranaMucosa() {
		return winEditMembranaMucosa;
	}

	public void setWinEditMembranaMucosa(Window winEditMembranaMucosa) {
		this.winEditMembranaMucosa = winEditMembranaMucosa;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioMembranaMucosa getServicioMembranaMucosa() {
		return servicioMembranaMucosa;
	}

	public void setServicioMembranaMucosa(
			ServicioMembranaMucosa servicioMembranaMucosa) {
		this.servicioMembranaMucosa = servicioMembranaMucosa;
	}

	public ctrlWinMembranaMucosa getCtrlwinmembranamucosa() {
		return ctrlwinmembranamucosa;
	}

	public void setCtrlwinmembranamucosa(ctrlWinMembranaMucosa ctrlwinmembranamucosa) {
		this.ctrlwinmembranamucosa = ctrlwinmembranamucosa;
	}

	public MembranaMucosa getMembranamucosa() {
		return membranamucosa;
	}

	public void setMembranamucosa(MembranaMucosa membranamucosa) {
		this.membranamucosa = membranamucosa;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditMembranaMucosa.setAttribute(comp.getId() + "ctrl", this);
		servicioMembranaMucosa = (ServicioMembranaMucosa) SpringUtil
				.getBean("beanServicioMembranaMucosa");
		membranamucosa = new MembranaMucosa();
		ctrlwinmembranamucosa = (ctrlWinMembranaMucosa) arg.get("ctrlWinMembranaMucosa");
		if (!(arg.get("objeto") == null)) {
			membranamucosa = (MembranaMucosa) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinmembranamucosa.recargar();
		ctrlwinmembranamucosa.apagarBotones();
		this.winEditMembranaMucosa.detach();
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
			MembranaMucosa membranamucosaTemp = servicioMembranaMucosa.buscarUno(membranamucosa.getNombre());
			if (membranamucosaTemp != null) {
				membranamucosa.setId(membranamucosaTemp.getId());
			}

			try {
				servicioMembranaMucosa.guardarMembranaMucosa(membranamucosa);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinmembranamucosa.recargar();
						ctrlwinmembranamucosa.apagarBotones();
						winEditMembranaMucosa.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditMembranaMucosa() {
		ctrlwinmembranamucosa.apagarBotones();
		this.winEditMembranaMucosa.detach();
	}



}

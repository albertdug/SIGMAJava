/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.AreaEnfermedad;
import org.ucla.sigma.servicio.ServicioAreaEnfermedad;
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
public class ctrlWinEditAreaEnfermedad extends GenericForwardComposer {

	private Window winEditAreaEnfermedad;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioAreaEnfermedad servicioAreaEnfermedad;
	private ctrlWinAreaEnfermedad ctrlwinareaenfermedad;
	private AreaEnfermedad areaenfermedad;	

	// ----------------------------------------------------------------------------------------------------
	
	public Window getWinEditAreaEnfermedad() {
		return winEditAreaEnfermedad;
	}

	public void setWinEditAreaEnfermedad(Window winEditAreaEnfermedad) {
		this.winEditAreaEnfermedad = winEditAreaEnfermedad;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioAreaEnfermedad getServicioAreaEnfermedad() {
		return servicioAreaEnfermedad;
	}

	public void setServicioAreaEnfermedad(
			ServicioAreaEnfermedad servicioAreaEnfermedad) {
		this.servicioAreaEnfermedad = servicioAreaEnfermedad;
	}

	public ctrlWinAreaEnfermedad getCtrlwinareaenfermedad() {
		return ctrlwinareaenfermedad;
	}

	public void setCtrlwinareaenfermedad(ctrlWinAreaEnfermedad ctrlwinareaenfermedad) {
		this.ctrlwinareaenfermedad = ctrlwinareaenfermedad;
	}

	public AreaEnfermedad getAreaenfermedad() {
		return areaenfermedad;
	}

	public void setAreaenfermedad(AreaEnfermedad areaenfermedad) {
		this.areaenfermedad = areaenfermedad;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditAreaEnfermedad.setAttribute(comp.getId() + "ctrl", this);
		servicioAreaEnfermedad = (ServicioAreaEnfermedad) SpringUtil
				.getBean("beanServicioAreaEnfermedad");
		areaenfermedad = new AreaEnfermedad();
		ctrlwinareaenfermedad = (ctrlWinAreaEnfermedad) arg.get("ctrlWinAreaEnfermedad");
		if (!(arg.get("objeto") == null)) {
			areaenfermedad = (AreaEnfermedad) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinareaenfermedad.recargar();
		ctrlwinareaenfermedad.apagarBotones();
		this.winEditAreaEnfermedad.detach();
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
			AreaEnfermedad areaenfermedadTemp = servicioAreaEnfermedad.buscarUno(areaenfermedad.getNombre());
			if (areaenfermedadTemp != null) {
				areaenfermedad.setId(areaenfermedadTemp.getId());
			}

			try {
				servicioAreaEnfermedad.guardarAreaEnfermedad(areaenfermedad);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinareaenfermedad.recargar();
						ctrlwinareaenfermedad.apagarBotones();
						winEditAreaEnfermedad.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditAreaEnfermedad() {
		ctrlwinareaenfermedad.apagarBotones();
		this.winEditAreaEnfermedad.detach();
	}



}

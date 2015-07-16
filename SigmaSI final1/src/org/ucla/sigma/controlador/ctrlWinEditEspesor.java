/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Espesor;
import org.ucla.sigma.servicio.ServicioEspesor;
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
public class ctrlWinEditEspesor extends GenericForwardComposer {

	private Window winEditEspesor;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioEspesor servicioEspesor;
	private ctrlWinEspesor ctrlwinespesor;
	private Espesor espesor;
	
	// ----------------------------------------------------------------------------------------------------
	
	public Window getWinEditEspesor() {
		return winEditEspesor;
	}

	public void setWinEditEspesor(Window winEditEspesor) {
		this.winEditEspesor = winEditEspesor;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioEspesor getServicioEspesor() {
		return servicioEspesor;
	}

	public void setServicioEspesor(ServicioEspesor servicioEspesor) {
		this.servicioEspesor = servicioEspesor;
	}

	public ctrlWinEspesor getCtrlwinespesor() {
		return ctrlwinespesor;
	}

	public void setCtrlwinespesor(ctrlWinEspesor ctrlwinespesor) {
		this.ctrlwinespesor = ctrlwinespesor;
	}

	public Espesor getEspesor() {
		return espesor;
	}

	public void setEspesor(Espesor espesor) {
		this.espesor = espesor;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditEspesor.setAttribute(comp.getId() + "ctrl", this);
		servicioEspesor = (ServicioEspesor) SpringUtil
				.getBean("beanServicioEspesor");
		espesor = new Espesor();
		ctrlwinespesor = (ctrlWinEspesor) arg.get("ctrlWinEspesor");
		if (!(arg.get("objeto") == null)) {
			espesor = (Espesor) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinespesor.recargar();
		ctrlwinespesor.apagarBotones();
		this.winEditEspesor.detach();
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
			Espesor espesorTemp = servicioEspesor.buscarUno(espesor.getNombre());
			if (espesorTemp != null) {
				espesor.setId(espesorTemp.getId());
			}

			try {
				servicioEspesor.guardarEspesor(espesor);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinespesor.recargar();
						ctrlwinespesor.apagarBotones();
						winEditEspesor.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditEspesor() {
		ctrlwinespesor.apagarBotones();
		this.winEditEspesor.detach();
	}



}



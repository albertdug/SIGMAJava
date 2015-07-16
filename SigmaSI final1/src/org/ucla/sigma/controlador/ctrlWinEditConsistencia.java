/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Consistencia;
import org.ucla.sigma.servicio.ServicioConsistencia;
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
public class ctrlWinEditConsistencia extends GenericForwardComposer {

	private Window winEditConsistencia;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioConsistencia servicioConsistencia;
	private ctrlWinConsistencia ctrlwinconsistencia;
	private Consistencia consistencia;
	
	
	// ----------------------------------------------------------------------------------------------------

	
	public Window getWinEditConsistencia() {
		return winEditConsistencia;
	}

	public void setWinEditConsistencia(Window winEditConsistencia) {
		this.winEditConsistencia = winEditConsistencia;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioConsistencia getServicioConsistencia() {
		return servicioConsistencia;
	}

	public void setServicioConsistencia(ServicioConsistencia servicioConsistencia) {
		this.servicioConsistencia = servicioConsistencia;
	}

	public ctrlWinConsistencia getCtrlwinconsistencia() {
		return ctrlwinconsistencia;
	}

	public void setCtrlwinconsistencia(ctrlWinConsistencia ctrlwinconsistencia) {
		this.ctrlwinconsistencia = ctrlwinconsistencia;
	}

	public Consistencia getConsistencia() {
		return consistencia;
	}

	public void setConsistencia(Consistencia consistencia) {
		this.consistencia = consistencia;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditConsistencia.setAttribute(comp.getId() + "ctrl", this);
		servicioConsistencia = (ServicioConsistencia) SpringUtil
				.getBean("beanServicioConsistencia");
		consistencia = new Consistencia();
		ctrlwinconsistencia = (ctrlWinConsistencia) arg.get("ctrlWinConsistencia");
		if (!(arg.get("objeto") == null)) {
			consistencia = (Consistencia) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinconsistencia.recargar();
		ctrlwinconsistencia.apagarBotones();
		this.winEditConsistencia.detach();
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
			Consistencia consistenciaTemp = servicioConsistencia.buscarUno(consistencia.getNombre());
			if (consistenciaTemp != null) {
				consistencia.setId(consistenciaTemp.getId());
			}

			try {
				servicioConsistencia.guardarConsistencia(consistencia);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinconsistencia.recargar();
						ctrlwinconsistencia.apagarBotones();
						winEditConsistencia.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditConsistencia() {
		ctrlwinconsistencia.apagarBotones();
		this.winEditConsistencia.detach();
	}



}

/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Elasticidad;
import org.ucla.sigma.servicio.ServicioElasticidad;
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
public class ctrlWinEditElasticidad extends GenericForwardComposer {

	private Window winEditElasticidad;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioElasticidad servicioElasticidad;
	private ctrlWinElasticidad ctrlwinelasticidad;
	private Elasticidad elasticidad;
	
	// ----------------------------------------------------------------------------------------------------
	
	public Window getWinEditElasticidad() {
		return winEditElasticidad;
	}

	public void setWinEditElasticidad(Window winEditElasticidad) {
		this.winEditElasticidad = winEditElasticidad;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioElasticidad getServicioElasticidad() {
		return servicioElasticidad;
	}

	public void setServicioElasticidad(ServicioElasticidad servicioElasticidad) {
		this.servicioElasticidad = servicioElasticidad;
	}

	public ctrlWinElasticidad getCtrlwinelasticidad() {
		return ctrlwinelasticidad;
	}

	public void setCtrlwinelasticidad(ctrlWinElasticidad ctrlwinelasticidad) {
		this.ctrlwinelasticidad = ctrlwinelasticidad;
	}

	public Elasticidad getElasticidad() {
		return elasticidad;
	}

	public void setElasticidad(Elasticidad elasticidad) {
		this.elasticidad = elasticidad;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditElasticidad.setAttribute(comp.getId() + "ctrl", this);
		servicioElasticidad = (ServicioElasticidad) SpringUtil
				.getBean("beanServicioElasticidad");
		elasticidad = new Elasticidad();
		ctrlwinelasticidad = (ctrlWinElasticidad) arg.get("ctrlWinElasticidad");
		if (!(arg.get("objeto") == null)) {
			elasticidad = (Elasticidad) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinelasticidad.recargar();
		ctrlwinelasticidad.apagarBotones();
		this.winEditElasticidad.detach();
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
			Elasticidad elasticidadTemp = servicioElasticidad.buscarUno(elasticidad.getNombre());
			if (elasticidadTemp != null) {
				elasticidad.setId(elasticidadTemp.getId());
			}

			try {
				servicioElasticidad.guardarElasticidad(elasticidad);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinelasticidad.recargar();
						ctrlwinelasticidad.apagarBotones();
						winEditElasticidad.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditElasticidad() {
		ctrlwinelasticidad.apagarBotones();
		this.winEditElasticidad.detach();
	}



}



/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.TipoPatologia;
import org.ucla.sigma.servicio.ServicioTipoPatologia;
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
public class ctrlWinEditTipoPatologia extends GenericForwardComposer {

	private Window winEditTipoPatologia;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;
	
	// ----------------------------------------------------------------------------------------------------

	private ServicioTipoPatologia servicioTipoPatologia;
	private ctrlWinTipoPatologia ctrlwintipoPatologia;

	// ----------------------------------------------------------------------------------------------------

	private TipoPatologia tipoPatologia;

	public Window getWinEditTipoPatologia() {
		return winEditTipoPatologia;
	}

	public void setWinEditTipoPatologia(Window winEditTipoPatologia) {
		this.winEditTipoPatologia = winEditTipoPatologia;
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

	public ServicioTipoPatologia getServicioTipoPatologia() {
		return servicioTipoPatologia;
	}

	public void setServicioTipoPatologia(ServicioTipoPatologia servicioTipoPatologia) {
		this.servicioTipoPatologia = servicioTipoPatologia;
	}

	public ctrlWinTipoPatologia getCtrlwintipoPatologia() {
		return ctrlwintipoPatologia;
	}

	public void setCtrlwintipoPatologia(ctrlWinTipoPatologia ctrlwintipoPatologia) {
		this.ctrlwintipoPatologia = ctrlwintipoPatologia;
	}
	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public TipoPatologia getTipoPatologia() {
		return tipoPatologia;
	}

	public void setTipoPatologia(TipoPatologia tipoPatologia) {
		this.tipoPatologia = tipoPatologia;
	}	

	// ----------------------------------------------------------------------------------------------------

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditTipoPatologia.setAttribute(comp.getId() + "ctrl", this);
		servicioTipoPatologia = (ServicioTipoPatologia) SpringUtil
				.getBean("beanServicioTipoPatologia");
		tipoPatologia = new TipoPatologia();
		ctrlwintipoPatologia = (ctrlWinTipoPatologia) arg.get("ctrlWinTipoPatologia");
		if (!(arg.get("objeto") == null)) {
			tipoPatologia = (TipoPatologia) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwintipoPatologia.recargar();
		ctrlwintipoPatologia.apagarBotones();
		this.winEditTipoPatologia.detach();
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
			TipoPatologia tipoPatologiaTemp = servicioTipoPatologia
					.buscarUno(tipoPatologia.getNombre());
			if (tipoPatologiaTemp != null) {
				tipoPatologia.setId(tipoPatologiaTemp.getId());
			}

			try {
				servicioTipoPatologia.guardarTipoPatologia(tipoPatologia);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwintipoPatologia.recargar();
						ctrlwintipoPatologia.apagarBotones();
						winEditTipoPatologia.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditTipoPatologia() {
		ctrlwintipoPatologia.recargar();
		ctrlwintipoPatologia.apagarBotones();
		this.winEditTipoPatologia.detach();
	}

}

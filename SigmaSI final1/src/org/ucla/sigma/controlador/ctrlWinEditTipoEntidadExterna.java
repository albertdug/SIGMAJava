/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.TipoEntidadExterna;
import org.ucla.sigma.servicio.ServicioTipoEntidadExterna;
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
public class ctrlWinEditTipoEntidadExterna extends GenericForwardComposer {

	private Window winEditTipoEntidadExterna;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;
	
	// ----------------------------------------------------------------------------------------------------

	private ServicioTipoEntidadExterna servicioTipoEntidadExterna;
	private ctrlWinTipoEntidadExterna ctrlwintipoEntidadExterna;

	// ----------------------------------------------------------------------------------------------------

	private TipoEntidadExterna tipoEntidadExterna;

	// ----------------------------------------------------------------------------------------------------

	public Window getWinEditTipoEntidadExterna() {
		return winEditTipoEntidadExterna;
	}

	public void setWinEditTipoEntidadExterna(Window winEditTipoEntidadExterna) {
		this.winEditTipoEntidadExterna = winEditTipoEntidadExterna;
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

	public ServicioTipoEntidadExterna getServicioTipoEntidadExterna() {
		return servicioTipoEntidadExterna;
	}

	public void setServicioTipoEntidadExterna(
			ServicioTipoEntidadExterna servicioTipoEntidadExterna) {
		this.servicioTipoEntidadExterna = servicioTipoEntidadExterna;
	}

	public ctrlWinTipoEntidadExterna getCtrlwintipoEntidadExterna() {
		return ctrlwintipoEntidadExterna;
	}

	public void setCtrlwintipoEntidadExterna(
			ctrlWinTipoEntidadExterna ctrlwintipoEntidadExterna) {
		this.ctrlwintipoEntidadExterna = ctrlwintipoEntidadExterna;
	}

	public TipoEntidadExterna getTipoEntidadExterna() {
		return tipoEntidadExterna;
	}

	public void setTipoEntidadExterna(TipoEntidadExterna tipoEntidadExterna) {
		this.tipoEntidadExterna = tipoEntidadExterna;
	}

	/**
	 *
	 *
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		// TODO Auto-generated method stub
		winEditTipoEntidadExterna.setAttribute(comp.getId() + "ctrl", this);
		servicioTipoEntidadExterna = (ServicioTipoEntidadExterna) SpringUtil
				.getBean("beanServicioTipoEntidadExterna");
		tipoEntidadExterna = new TipoEntidadExterna();
		ctrlwintipoEntidadExterna = (ctrlWinTipoEntidadExterna) arg.get("ctrlWinTipoEntidadExterna");
		if (!(arg.get("objeto") == null)) {
			tipoEntidadExterna = (TipoEntidadExterna) arg.get("objeto");
		}

	}

	public void onClick$btnCancelar() {
		ctrlwintipoEntidadExterna.recargar();
		ctrlwintipoEntidadExterna.apagarBotones();
		this.winEditTipoEntidadExterna.detach();
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
			TipoEntidadExterna tipoEntidadExternaTemp = servicioTipoEntidadExterna.buscarUno(tipoEntidadExterna.getNombre());
			if (tipoEntidadExternaTemp != null) {
				tipoEntidadExterna.setId(tipoEntidadExternaTemp.getId());
			}

			try {
				servicioTipoEntidadExterna.guardarTipoEntidadExterna(tipoEntidadExterna);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwintipoEntidadExterna.recargar();
						ctrlwintipoEntidadExterna.apagarBotones();
						winEditTipoEntidadExterna.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}
		}

	}

	public void onClose$winEditTipoEntidadExterna() {
		ctrlwintipoEntidadExterna.apagarBotones();
		this.winEditTipoEntidadExterna.detach();
	}

}

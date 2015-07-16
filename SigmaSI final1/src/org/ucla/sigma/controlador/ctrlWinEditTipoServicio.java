/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.TipoServicio;
import org.ucla.sigma.modelo.TipoServicio;
import org.ucla.sigma.servicio.ServicioTipoServicio;
import org.ucla.sigma.servicio.ServicioTipoServicio;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author promo49
 * 
 */
public class ctrlWinEditTipoServicio extends GenericForwardComposer {

	private Window winEditTipoServicio;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;
	private Textbox txtAbreviatura;

	// ----------------------------------------------------------------------------------------------------

	private ServicioTipoServicio servicioTipoServicio;
	private ctrlWinTipoServicio ctrlwintipoServicio;

	// ----------------------------------------------------------------------------------------------------

	private TipoServicio tipoServicio;

	public Window getWinEditTipoServicio() {
		return winEditTipoServicio;
	}

	public void setWinEditTipoServicio(Window winEditTipoServicio) {
		this.winEditTipoServicio = winEditTipoServicio;
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

	public Textbox getTxtAbreviatura() {
		return txtAbreviatura;
	}

	public void setTxtAbreviatura(Textbox txtAbreviatura) {
		this.txtAbreviatura = txtAbreviatura;
	}

	public ServicioTipoServicio getServicioTipoServicio() {
		return servicioTipoServicio;
	}

	public void setServicioTipoServicio(ServicioTipoServicio servicioTipoServicio) {
		this.servicioTipoServicio = servicioTipoServicio;
	}

	public ctrlWinTipoServicio getCtrlwintipoServicio() {
		return ctrlwintipoServicio;
	}

	public void setCtrlwintipoServicio(ctrlWinTipoServicio ctrlwintipoServicio) {
		this.ctrlwintipoServicio = ctrlwintipoServicio;
	}

	public TipoServicio getTipoServicio() {
		return tipoServicio;
	}

	public void setTipoServicio(TipoServicio tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	// ----------------------------------------------------------------------------------------------------
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditTipoServicio.setAttribute(comp.getId() + "ctrl", this);
		servicioTipoServicio = (ServicioTipoServicio) SpringUtil
				.getBean("beanServicioTipoServicio");
		tipoServicio = new TipoServicio();
		ctrlwintipoServicio = (ctrlWinTipoServicio) arg.get("ctrlWinTipoServicio");
		if (!(arg.get("objeto") == null)) {
			tipoServicio = (TipoServicio) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwintipoServicio.recargar();
		ctrlwintipoServicio.apagarBotones();
		this.winEditTipoServicio.detach();
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
			TipoServicio tipoServicioTemp = servicioTipoServicio
					.buscarUno(tipoServicio.getNombre());
			if (tipoServicioTemp != null) {
				tipoServicio.setId(tipoServicioTemp.getId());
			}

			try {
				servicioTipoServicio.guardarTipoServicio(tipoServicio);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwintipoServicio.recargar();
						ctrlwintipoServicio.apagarBotones();
						winEditTipoServicio.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditTipoServicio() {
		ctrlwintipoServicio.apagarBotones();
		this.winEditTipoServicio.detach();
	}

}

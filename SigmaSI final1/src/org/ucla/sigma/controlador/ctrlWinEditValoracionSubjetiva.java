package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.modelo.ValoracionSubjetiva;
import org.ucla.sigma.servicio.ServicioValoracionSubjetiva;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEditValoracionSubjetiva extends GenericForwardComposer {

	private Window winEditValoracionSubjetiva;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioValoracionSubjetiva servicioValoracionSubjetiva;
	private ctrlWinValoracionSubjetiva ctrlwinvaloracionSubjetiva;

	// ----------------------------------------------------------------------------------------------------

	private ValoracionSubjetiva valoracionSubjetiva;

	// ----------------------------------------------------------------------------------------------------
	
	public ServicioValoracionSubjetiva getServicioValoracionSubjetiva() {
		return servicioValoracionSubjetiva;
	}

	public void setServicioValoracionSubjetiva(
			ServicioValoracionSubjetiva servicioValoracionSubjetiva) {
		this.servicioValoracionSubjetiva = servicioValoracionSubjetiva;
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
	
	public ctrlWinValoracionSubjetiva getCtrlwinvaloracionSubjetiva() {
		return ctrlwinvaloracionSubjetiva;
	}

	public void setCtrlwinvaloracionSubjetiva(
			ctrlWinValoracionSubjetiva ctrlwinvaloracionSubjetiva) {
		this.ctrlwinvaloracionSubjetiva = ctrlwinvaloracionSubjetiva;
	}
	
	public Window getWinEditValoracionSubjetiva() {
		return winEditValoracionSubjetiva;
	}

	public void setWinEditValoracionSubjetiva(Window winEditValoracionSubjetiva) {
		this.winEditValoracionSubjetiva = winEditValoracionSubjetiva;
	}
	
	public ValoracionSubjetiva getValoracionSubjetiva() {
		return valoracionSubjetiva;
	}

	public void setValoracionSubjetiva(ValoracionSubjetiva valoracionSubjetiva) {
		this.valoracionSubjetiva = valoracionSubjetiva;
	}
	

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditValoracionSubjetiva.setAttribute(comp.getId() + "ctrl", this);
		servicioValoracionSubjetiva = (ServicioValoracionSubjetiva) SpringUtil
				.getBean("beanServicioValoracionSubjetiva");
		valoracionSubjetiva = new ValoracionSubjetiva();
		ctrlwinvaloracionSubjetiva = (ctrlWinValoracionSubjetiva) arg.get("ctrlWinValoracionSubjetiva");
		if (!(arg.get("objeto") == null)) {
			valoracionSubjetiva = (ValoracionSubjetiva) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinvaloracionSubjetiva.apagarBotones();
		this.winEditValoracionSubjetiva.detach();
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
			ValoracionSubjetiva valoracionSubjetivaTemp = servicioValoracionSubjetiva
					.buscarUno(valoracionSubjetiva.getNombre());
			if (valoracionSubjetivaTemp != null) {
				valoracionSubjetiva.setId(valoracionSubjetivaTemp.getId());
			}

			try {
				servicioValoracionSubjetiva.guardarValoracionSubjetiva(valoracionSubjetiva);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinvaloracionSubjetiva.recargar();
						ctrlwinvaloracionSubjetiva.apagarBotones();
						winEditValoracionSubjetiva.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditValoracionSubjetiva() {
		ctrlwinvaloracionSubjetiva.apagarBotones();
		this.winEditValoracionSubjetiva.detach();
	}
}

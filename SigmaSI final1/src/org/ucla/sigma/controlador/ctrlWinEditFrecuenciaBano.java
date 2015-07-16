package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.FrecuenciaBano;
import org.ucla.sigma.servicio.ServicioFrecuenciaBano;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


public class ctrlWinEditFrecuenciaBano extends GenericForwardComposer {

	private Window winEditFrecuenciaBano;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;
	
	// ----------------------------------------------------------------------------------------------------

	private ServicioFrecuenciaBano servicioFrecuenciaBano;
	private ctrlWinFrecuenciaBano ctrlwinfrecuenciaBano;

	// ----------------------------------------------------------------------------------------------------

	private FrecuenciaBano frecuenciaBano;

	public Window getWinEditFrecuenciaBano() {
		return winEditFrecuenciaBano;
	}

	public void setWinEditFrecuenciaBano(Window winEditFrecuenciaBano) {
		this.winEditFrecuenciaBano = winEditFrecuenciaBano;
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

	public ServicioFrecuenciaBano getServicioFrecuenciaBano() {
		return servicioFrecuenciaBano;
	}

	public void setServicioFrecuenciaBano(ServicioFrecuenciaBano servicioFrecuenciaBano) {
		this.servicioFrecuenciaBano = servicioFrecuenciaBano;
	}

	public ctrlWinFrecuenciaBano getCtrlwinfrecuenciaBano() {
		return ctrlwinfrecuenciaBano;
	}

	public void setCtrlwinfrecuenciaBano(ctrlWinFrecuenciaBano ctrlwinfrecuenciaBano) {
		this.ctrlwinfrecuenciaBano = ctrlwinfrecuenciaBano;
	}
	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public FrecuenciaBano getFrecuenciaBano() {
		return frecuenciaBano;
	}

	public void setFrecuenciaBano(FrecuenciaBano frecuenciaBano) {
		this.frecuenciaBano = frecuenciaBano;
	}	

	// ----------------------------------------------------------------------------------------------------

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditFrecuenciaBano.setAttribute(comp.getId() + "ctrl", this);
		servicioFrecuenciaBano = (ServicioFrecuenciaBano) SpringUtil
				.getBean("beanServicioFrecuenciaBano");
		frecuenciaBano = new FrecuenciaBano();
		ctrlwinfrecuenciaBano = (ctrlWinFrecuenciaBano) arg.get("ctrlWinFrecuenciaBano");
		if (!(arg.get("objeto") == null)) {
			frecuenciaBano = (FrecuenciaBano) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinfrecuenciaBano.recargar();
		ctrlwinfrecuenciaBano.apagarBotones();
		this.winEditFrecuenciaBano.detach();
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
			System.out.println(frecuenciaBano.getNombre());
			FrecuenciaBano frecuenciaBanoTemp = servicioFrecuenciaBano
					.buscarUno(frecuenciaBano.getNombre());
			if (frecuenciaBanoTemp != null) {
				frecuenciaBano.setId(frecuenciaBanoTemp.getId());
			}

			try {
				servicioFrecuenciaBano.guardarFrecuenciaBano(frecuenciaBano);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinfrecuenciaBano.recargar();
						ctrlwinfrecuenciaBano.apagarBotones();
						winEditFrecuenciaBano.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditTipoPatologia() {
		ctrlwinfrecuenciaBano.recargar();
		ctrlwinfrecuenciaBano.apagarBotones();
		this.winEditFrecuenciaBano.detach();
	}

}

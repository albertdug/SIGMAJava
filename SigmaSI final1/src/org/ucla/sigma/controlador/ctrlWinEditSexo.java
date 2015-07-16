/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Sexo;
import org.ucla.sigma.servicio.ServicioSexo;
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
public class ctrlWinEditSexo extends GenericForwardComposer {

	private Window winEditSexo;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioSexo servicioSexo;
	private ctrlWinSexo ctrlwinsexo;

	// ----------------------------------------------------------------------------------------------------

	private Sexo sexo;

	public Window getWinEditSexo() {
		return winEditSexo;
	}

	public void setWinEditSexo(Window winEditSexo) {
		this.winEditSexo = winEditSexo;
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

	public ServicioSexo getServicioSexo() {
		return servicioSexo;
	}

	public void setServicioSexo(ServicioSexo servicioSexo) {
		this.servicioSexo = servicioSexo;
	}

	public ctrlWinSexo getCtrlwinsexo() {
		return ctrlwinsexo;
	}

	public void setCtrlwinsexo(ctrlWinSexo ctrlwinsexo) {
		this.ctrlwinsexo = ctrlwinsexo;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	// ----------------------------------------------------------------------------------------------------
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditSexo.setAttribute(comp.getId() + "ctrl", this);
		servicioSexo = (ServicioSexo) SpringUtil
				.getBean("beanServicioSexo");
		sexo = new Sexo();
		ctrlwinsexo = (ctrlWinSexo) arg.get("ctrlWinSexo");
		if (!(arg.get("objeto") == null)) {
			sexo = (Sexo) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinsexo.recargar();
		ctrlwinsexo.apagarBotones();
		this.winEditSexo.detach();
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
			Sexo sexoTemp = servicioSexo.buscarUno(sexo.getNombre());
			if (sexoTemp != null) {
				sexo.setId(sexoTemp.getId());
			}

			try {
				servicioSexo.guardarSexo(sexo);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinsexo.recargar();
						ctrlwinsexo.apagarBotones();
						winEditSexo.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditSexo() {
		ctrlwinsexo.apagarBotones();
		this.winEditSexo.detach();
	}



}

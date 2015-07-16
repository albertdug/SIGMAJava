/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.PalpacionAbdominal;
import org.ucla.sigma.servicio.ServicioPalpacionAbdominal;
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
public class ctrlWinEditPalpacionAbdominal extends GenericForwardComposer {

	private Window winEditPalpacionAbdominal;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioPalpacionAbdominal servicioPalpacionAbdominal;
	private ctrlWinPalpacionAbdominal ctrlwinpalpacionabdominal;
	private PalpacionAbdominal palpacionabdominal;

	// ----------------------------------------------------------------------------------------------------

	public Window getWinEditPalpacionAbdominal() {
		return winEditPalpacionAbdominal;
	}

	public void setWinEditPalpacionAbdominal(Window winEditPalpacionAbdominal) {
		this.winEditPalpacionAbdominal = winEditPalpacionAbdominal;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioPalpacionAbdominal getServicioPalpacionAbdominal() {
		return servicioPalpacionAbdominal;
	}

	public void setServicioPalpacionAbdominal(
			ServicioPalpacionAbdominal servicioPalpacionAbdominal) {
		this.servicioPalpacionAbdominal = servicioPalpacionAbdominal;
	}

	public ctrlWinPalpacionAbdominal getCtrlwinpalpacionabdominal() {
		return ctrlwinpalpacionabdominal;
	}

	public void setCtrlwinpalpacionabdominal(
			ctrlWinPalpacionAbdominal ctrlwinpalpacionabdominal) {
		this.ctrlwinpalpacionabdominal = ctrlwinpalpacionabdominal;
	}

	public PalpacionAbdominal getPalpacionabdominal() {
		return palpacionabdominal;
	}

	public void setPalpacionabdominal(PalpacionAbdominal palpacionabdominal) {
		this.palpacionabdominal = palpacionabdominal;
	}

	// ----------------------------------------------------------------------------------------------------

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditPalpacionAbdominal.setAttribute(comp.getId() + "ctrl", this);
		servicioPalpacionAbdominal = (ServicioPalpacionAbdominal) SpringUtil
				.getBean("beanServicioPalpacionAbdominal");
		palpacionabdominal = new PalpacionAbdominal();
		ctrlwinpalpacionabdominal = (ctrlWinPalpacionAbdominal) arg
				.get("ctrlWinPalpacionAbdominal");
		if (!(arg.get("objeto") == null)) {
			palpacionabdominal = (PalpacionAbdominal) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinpalpacionabdominal.recargar();
		ctrlwinpalpacionabdominal.apagarBotones();
		this.winEditPalpacionAbdominal.detach();
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
			PalpacionAbdominal palpacionabdominalTemp = servicioPalpacionAbdominal
					.buscarUno(palpacionabdominal.getNombre());
			if (palpacionabdominalTemp != null) {
				palpacionabdominal.setId(palpacionabdominalTemp.getId());
			}

			try {
				servicioPalpacionAbdominal
						.guardarPalpacionAbdominal(palpacionabdominal);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinpalpacionabdominal.recargar();
						ctrlwinpalpacionabdominal.apagarBotones();
						winEditPalpacionAbdominal.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditPalpacionAbdominal() {
		ctrlwinpalpacionabdominal.apagarBotones();
		this.winEditPalpacionAbdominal.detach();
	}

}

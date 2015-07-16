/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Dosis;
import org.ucla.sigma.servicio.ServicioDosis;
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
public class ctrlWinEditDosis extends GenericForwardComposer {

	private Window winEditDosis;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioDosis servicioDosis;
	private ctrlWinDosis ctrlwindosis;
	private Dosis dosis;
	
	// ----------------------------------------------------------------------------------------------------
	
	public Window getWinEditDosis() {
		return winEditDosis;
	}

	public void setWinEditDosis(Window winEditDosis) {
		this.winEditDosis = winEditDosis;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioDosis getServicioDosis() {
		return servicioDosis;
	}

	public void setServicioDosis(ServicioDosis servicioDosis) {
		this.servicioDosis = servicioDosis;
	}

	public ctrlWinDosis getCtrlwindosis() {
		return ctrlwindosis;
	}

	public void setCtrlwindosis(ctrlWinDosis ctrlwindosis) {
		this.ctrlwindosis = ctrlwindosis;
	}

	public Dosis getDosis() {
		return dosis;
	}

	public void setDosis(Dosis dosis) {
		this.dosis = dosis;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditDosis.setAttribute(comp.getId() + "ctrl", this);
		servicioDosis = (ServicioDosis) SpringUtil
				.getBean("beanServicioDosis");
		dosis = new Dosis();
		ctrlwindosis = (ctrlWinDosis) arg.get("ctrlWinDosis");
		if (!(arg.get("objeto") == null)) {
			dosis = (Dosis) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwindosis.recargar();
		ctrlwindosis.apagarBotones();
		this.winEditDosis.detach();
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
			Dosis dosisTemp = servicioDosis.buscarUno(dosis.getNombre());
			if (dosisTemp != null) {
				dosis.setId(dosisTemp.getId());
			}

			try {
				servicioDosis.guardarDosis(dosis);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwindosis.recargar();
						ctrlwindosis.apagarBotones();
						winEditDosis.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditDosis() {
		ctrlwindosis.apagarBotones();
		this.winEditDosis.detach();
	}



}



/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Patron;
import org.ucla.sigma.servicio.ServicioPatron;
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
public class ctrlWinEditPatron extends GenericForwardComposer {

	private Window winEditPatron;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioPatron servicioPatron;
	private ctrlWinPatron ctrlwinpatron;

	// ----------------------------------------------------------------------------------------------------

	private Patron patron;

	public Window getWinEditPatron() {
		return winEditPatron;
	}

	public void setWinEditPatron(Window winEditPatron) {
		this.winEditPatron = winEditPatron;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioPatron getServicioPatron() {
		return servicioPatron;
	}

	public void setServicioPatron(ServicioPatron servicioPatron) {
		this.servicioPatron = servicioPatron;
	}

	public ctrlWinPatron getCtrlwinpatron() {
		return ctrlwinpatron;
	}

	public void setCtrlwinpatron(ctrlWinPatron ctrlwinpatron) {
		this.ctrlwinpatron = ctrlwinpatron;
	}

	public Patron getPatron() {
		return patron;
	}

	public void setPatron(Patron patron) {
		this.patron = patron;
	}

	// ----------------------------------------------------------------------------------------------------

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditPatron.setAttribute(comp.getId() + "ctrl", this);
		servicioPatron = (ServicioPatron) SpringUtil
				.getBean("beanServicioPatron");
		patron = new Patron();
		ctrlwinpatron = (ctrlWinPatron) arg.get("ctrlWinPatron");
		if (!(arg.get("objeto") == null)) {
			patron = (Patron) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinpatron.recargar();
		ctrlwinpatron.apagarBotones();
		this.winEditPatron.detach();
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
			Patron patronTemp = servicioPatron.buscarUno(patron.getNombre());
			if (patronTemp != null) {
				patron.setId(patronTemp.getId());
			}

			try {
				servicioPatron.guardarPatron(patron);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinpatron.recargar();
						ctrlwinpatron.apagarBotones();
						winEditPatron.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditPatron() {
		ctrlwinpatron.apagarBotones();
		this.winEditPatron.detach();
	}

}

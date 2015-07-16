/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.PatronDistribucion;
import org.ucla.sigma.servicio.ServicioPatronDistribucion;
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
public class ctrlWinEditPatronDistribucion extends GenericForwardComposer {

	private Window winEditPatronDistribucion;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioPatronDistribucion servicioPatronDistribucion;
	private ctrlWinPatronDistribucion ctrlwinpatrondistribucion;

	// ----------------------------------------------------------------------------------------------------

	private PatronDistribucion patrondistribucion;

	public Window getWinEditPatronDistribucion() {
		return winEditPatronDistribucion;
	}

	public void setWinEditPatronDistribucion(Window winEditPatronDistribucion) {
		this.winEditPatronDistribucion = winEditPatronDistribucion;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioPatronDistribucion getServicioPatronDistribucion() {
		return servicioPatronDistribucion;
	}

	public void setServicioPatronDistribucion(
			ServicioPatronDistribucion servicioPatronDistribucion) {
		this.servicioPatronDistribucion = servicioPatronDistribucion;
	}

	public ctrlWinPatronDistribucion getCtrlwinpatrondistribucion() {
		return ctrlwinpatrondistribucion;
	}

	public void setCtrlwinpatrondistribucion(
			ctrlWinPatronDistribucion ctrlwinpatrondistribucion) {
		this.ctrlwinpatrondistribucion = ctrlwinpatrondistribucion;
	}

	public PatronDistribucion getPatrondistribucion() {
		return patrondistribucion;
	}

	public void setPatrondistribucion(PatronDistribucion patrondistribucion) {
		this.patrondistribucion = patrondistribucion;
	}

	

	// ----------------------------------------------------------------------------------------------------
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditPatronDistribucion.setAttribute(comp.getId() + "ctrl", this);
		servicioPatronDistribucion = (ServicioPatronDistribucion) SpringUtil
				.getBean("beanServicioPatronDistribucion");
		patrondistribucion = new PatronDistribucion();
		ctrlwinpatrondistribucion = (ctrlWinPatronDistribucion) arg.get("ctrlWinPatronDistribucion");
		if (!(arg.get("objeto") == null)) {
			patrondistribucion = (PatronDistribucion) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinpatrondistribucion.recargar();
		ctrlwinpatrondistribucion.apagarBotones();
		this.winEditPatronDistribucion.detach();
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
			PatronDistribucion patrondistribucionTemp = servicioPatronDistribucion.buscarUno(patrondistribucion.getNombre());
			if (patrondistribucionTemp != null) {
				patrondistribucion.setId(patrondistribucionTemp.getId());
			}

			try {
				servicioPatronDistribucion.guardarPatronDistribucion(patrondistribucion);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinpatrondistribucion.recargar();
						ctrlwinpatrondistribucion.apagarBotones();
						winEditPatronDistribucion.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditPatronDistribucion() {
		ctrlwinpatrondistribucion.apagarBotones();
		this.winEditPatronDistribucion.detach();
	}



}
/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.TipoExamen;
import org.ucla.sigma.servicio.ServicioTipoExamen;
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
public class ctrlWinEditTipoExamen extends GenericForwardComposer {

	private Window winEditTipoExamen;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;
	
	// ----------------------------------------------------------------------------------------------------

	private ServicioTipoExamen servicioTipoExamen;
	private ctrlWinTipoExamen ctrlwintipoExamen;

	// ----------------------------------------------------------------------------------------------------

	private TipoExamen tipoExamen;

	public Window getWinEditTipoExamen() {
		return winEditTipoExamen;
	}

	public void setWinEditTipoExamen(Window winEditTipoExamen) {
		this.winEditTipoExamen = winEditTipoExamen;
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

	public ServicioTipoExamen getServicioTipoExamen() {
		return servicioTipoExamen;
	}

	public void setServicioTipoExamen(ServicioTipoExamen servicioTipoExamen) {
		this.servicioTipoExamen = servicioTipoExamen;
	}

	public ctrlWinTipoExamen getCtrlwintipoExamen() {
		return ctrlwintipoExamen;
	}

	public void setCtrlwintipoExamen(ctrlWinTipoExamen ctrlwintipoExamen) {
		this.ctrlwintipoExamen = ctrlwintipoExamen;
	}
	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public TipoExamen getTipoExamen() {
		return tipoExamen;
	}

	public void setTipoExamen(TipoExamen tipoExamen) {
		this.tipoExamen = tipoExamen;
	}	

	// ----------------------------------------------------------------------------------------------------

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditTipoExamen.setAttribute(comp.getId() + "ctrl", this);
		servicioTipoExamen = (ServicioTipoExamen) SpringUtil
				.getBean("beanServicioTipoExamen");
		tipoExamen = new TipoExamen();
		ctrlwintipoExamen = (ctrlWinTipoExamen) arg.get("ctrlWinTipoExamen");
		if (!(arg.get("objeto") == null)) {
			tipoExamen = (TipoExamen) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwintipoExamen.recargar();
		ctrlwintipoExamen.apagarBotones();
		this.winEditTipoExamen.detach();
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
			System.out.println(tipoExamen.getNombre());
			TipoExamen tipoExamenTemp = servicioTipoExamen
					.buscarUno(tipoExamen.getNombre());
			if (tipoExamenTemp != null) {
				tipoExamen.setId(tipoExamenTemp.getId());
			}

			try {
				servicioTipoExamen.guardarTipoExamen(tipoExamen);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwintipoExamen.recargar();
						ctrlwintipoExamen.apagarBotones();
						winEditTipoExamen.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditTipoPatologia() {
		ctrlwintipoExamen.recargar();
		ctrlwintipoExamen.apagarBotones();
		this.winEditTipoExamen.detach();
	}

}

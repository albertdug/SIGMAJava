/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.TipoDefuncion;
import org.ucla.sigma.servicio.ServicioTipoDefuncion;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author lis
 *
 */
public class ctrlWinEditTipoDefuncion extends GenericForwardComposer {

	private Window winEditTipoDefuncion;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioTipoDefuncion servicioTipoDefuncion;
	private ctrlWinTipoDefuncion ctrlwintipodefuncion;

	// ----------------------------------------------------------------------------------------------------

	private TipoDefuncion tipodefuncion;

	// ----------------------------------------------------------------------------------------------------

	public Window getWinEditTipoDefuncion() {
		return winEditTipoDefuncion;
	}

	public void setWinEditTipoDefuncion(Window winEditTipoDefuncion) {
		this.winEditTipoDefuncion = winEditTipoDefuncion;
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

	public ServicioTipoDefuncion getServicioTipoDefuncion() {
		return servicioTipoDefuncion;
	}

	public void setServicioTipoDefuncion(ServicioTipoDefuncion servicioTipoDefuncion) {
		this.servicioTipoDefuncion = servicioTipoDefuncion;
	}

	public ctrlWinTipoDefuncion getCtrlwintipodefuncion() {
		return ctrlwintipodefuncion;
	}

	public void setCtrlwintipodefuncion(ctrlWinTipoDefuncion ctrlwintipodefuncion) {
		this.ctrlwintipodefuncion = ctrlwintipodefuncion;
	}

	public TipoDefuncion getTipodefuncion() {
		return tipodefuncion;
	}

	public void setTipodefuncion(TipoDefuncion tipodefuncion) {
		this.tipodefuncion = tipodefuncion;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditTipoDefuncion.setAttribute(comp.getId() + "ctrl", this);
		servicioTipoDefuncion = (ServicioTipoDefuncion) SpringUtil
				.getBean("beanServicioTipoDefuncion");
		tipodefuncion = new TipoDefuncion();
		ctrlwintipodefuncion = (ctrlWinTipoDefuncion) arg.get("ctrlWinTipoDefuncion");
		if (!(arg.get("objeto") == null)) {
			tipodefuncion = (TipoDefuncion) arg.get("objeto");
		}
	}

	
	public void onClick$btnCancelar() {
		ctrlwintipodefuncion.recargar();
		ctrlwintipodefuncion.apagarBotones();
		this.winEditTipoDefuncion.detach();
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
			TipoDefuncion tipodefuncionTemp = servicioTipoDefuncion
					.buscarUno(tipodefuncion.getNombre());
			if (tipodefuncionTemp!= null) {
				tipodefuncion.setId(tipodefuncionTemp.getId());
			}

			try {
				servicioTipoDefuncion.guardarTipoDefuncion(tipodefuncion);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwintipodefuncion.recargar();
						ctrlwintipodefuncion.apagarBotones();
						winEditTipoDefuncion.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditTipoDefuncion() {
		ctrlwintipodefuncion.apagarBotones();
		this.winEditTipoDefuncion.detach();
	}


}

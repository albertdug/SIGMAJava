package org.ucla.sigma.controlador;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Estado;
import org.ucla.sigma.modelo.TipoAlimentacion;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioTipoAlimentacion;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * 
 */

/**
 * @author promo49
 *
 */
public class ctrlWinEditTipoAlimentacion extends GenericForwardComposer {

	private Window winEditTipoAlimentacion;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtDescripcion;
	private Textbox txtNombre;

	/**
	 *
	 *
	 */
	// ----------------------------------------------------------------------------------------------------

	private ServicioTipoAlimentacion servicioTipoAlimentacion;
	private ctrlWinTipoAlimentacion ctrlwintipoalimentacion;

	// ----------------------------------------------------------------------------------------------------
	private TipoAlimentacion tipoalimentacion;

	// ----------------------------------------------------------------------------------------------------

	
	

	public Window getWinEditTipoAlimentacion() {
		return winEditTipoAlimentacion;
	}

	public void setWinEditTipoAlimentacion(Window winEditTipoAlimentacion) {
		this.winEditTipoAlimentacion = winEditTipoAlimentacion;
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

	public Textbox getTxtDescripcion() {
		return txtDescripcion;
	}

	public void setTxtDescripcion(Textbox txtDescripcion) {
		this.txtDescripcion = txtDescripcion;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioTipoAlimentacion getServicioTipoAlimentacion() {
		return servicioTipoAlimentacion;
	}

	public void setServicioTipoAlimentacion(
			ServicioTipoAlimentacion servicioTipoAlimentacion) {
		this.servicioTipoAlimentacion = servicioTipoAlimentacion;
	}

	public ctrlWinTipoAlimentacion getCtrlwintipoalimentacion() {
		return ctrlwintipoalimentacion;
	}

	public void setCtrlwintipoalimentacion(
			ctrlWinTipoAlimentacion ctrlwintipoalimentacion) {
		this.ctrlwintipoalimentacion = ctrlwintipoalimentacion;
	}

	public TipoAlimentacion getTipoalimentacion() {
		return tipoalimentacion;
	}

	public void setTipoalimentacion(TipoAlimentacion tipoalimentacion) {
		this.tipoalimentacion = tipoalimentacion;
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditTipoAlimentacion.setAttribute(comp.getId() + "ctrl", this);
		servicioTipoAlimentacion = (ServicioTipoAlimentacion) SpringUtil
				.getBean("beanServicioTipoAlimentacion");
		tipoalimentacion = new TipoAlimentacion();
		ctrlwintipoalimentacion = (ctrlWinTipoAlimentacion) arg.get("ctrlWinTipoAlimentacion");
		if (!(arg.get("objeto") == null)) {
			tipoalimentacion = (TipoAlimentacion) arg.get("objeto");
		}
	}
	
	public void onClick$btnCancelar() {
		ctrlwintipoalimentacion.recargar();
		ctrlwintipoalimentacion.apagarBotones();
		this.winEditTipoAlimentacion.detach();
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
		} else if (txtDescripcion.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nDescripcion",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtDescripcion.setFocus(true);
						}
					});
		} else {
			TipoAlimentacion tipoalimentacionTemp = servicioTipoAlimentacion.buscarUno(tipoalimentacion.getNombre());
			if (tipoalimentacionTemp != null) {
				tipoalimentacion.setId(tipoalimentacionTemp.getId());
			}

			try {
				servicioTipoAlimentacion.guardarTipoAlimentacion(tipoalimentacion);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						System.out.println("Hola");
						ctrlwintipoalimentacion.recargar();
						ctrlwintipoalimentacion.apagarBotones();
						winEditTipoAlimentacion.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}
	public void onClose$winEditEstado() {
		ctrlwintipoalimentacion.apagarBotones();
		this.winEditTipoAlimentacion.detach();
	}
}

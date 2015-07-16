/**
 * 
 */
package org.ucla.sigma.controlador;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import org.ucla.sigma.components.HelperString;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.TipoPublicacion;
import org.ucla.sigma.servicio.ServicioTipoPublicacion;
import org.zkoss.zkplus.spring.SpringUtil;

/**
 * @author usuario
 *
 */
public class ctrlWinEditTipoPublicacion extends GenericForwardComposer {

	private Window winEditTipoPublicacion;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;
	private Textbox txtDescripcion;
	

	// ----------------------------------------------------------------------------------------------------

		private ServicioTipoPublicacion servicioTipoPublicacion;
		private ctrlWinTipoPublicacion ctrlwintipoPublicacion;

		// ----------------------------------------------------------------------------------------------------

		private TipoPublicacion tipoPublicacion;

		// ----------------------------------------------------------------------------------------------------

		public ServicioTipoPublicacion getServicioTipoPublicacion() {
			return servicioTipoPublicacion;
		}

		public Textbox getTxtDescripcion() {
			return txtDescripcion;
		}

		public void setTxtDescripcion(Textbox txtDescripcion) {
			this.txtDescripcion = txtDescripcion;
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

		public void setServicioTipoPublicacion(ServicioTipoPublicacion servicioTipoPublicacion) {
			this.servicioTipoPublicacion = servicioTipoPublicacion;
		}

		public ctrlWinTipoPublicacion getCtrlwintipoPublicacion() {
			return ctrlwintipoPublicacion;
		}

		public void setCtrlwintipoPublicacion(ctrlWinTipoPublicacion ctrlwintipoPublicacion) {
			this.ctrlwintipoPublicacion = ctrlwintipoPublicacion;
		}

		public Window getWinEditTipoPublicacion() {
			return winEditTipoPublicacion;
		}

		public void setWinEditTipoPublicacion(Window winEditTipoPublicacion) {
			this.winEditTipoPublicacion = winEditTipoPublicacion;
		}

		public TipoPublicacion getTipoPublicacion() {
			return tipoPublicacion;
		}

		public void setTipoPublicacion(TipoPublicacion tipoPublicacion) {
			this.tipoPublicacion = tipoPublicacion;
		}

		@Override
		public void doAfterCompose(Component comp) throws Exception {
			super.doAfterCompose(comp);
			winEditTipoPublicacion.setAttribute(comp.getId() + "ctrl", this);
			servicioTipoPublicacion = (ServicioTipoPublicacion) SpringUtil
					.getBean("beanServicioTipoPublicacion");
			tipoPublicacion = new TipoPublicacion();
			ctrlwintipoPublicacion = (ctrlWinTipoPublicacion) arg.get("ctrlWinTipoPublicacion");
			if (!(arg.get("objeto") == null)) {
				tipoPublicacion = (TipoPublicacion) arg.get("objeto");
			}
		}

		public void onClick$btnCancelar() {
			ctrlwintipoPublicacion.recargar();
			ctrlwintipoPublicacion.apagarBotones();
			this.winEditTipoPublicacion.detach();
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
			}
			else if (txtDescripcion.getValue().trim().equalsIgnoreCase("")) {
				MensajeEmergente.mostrar("NOEMPTY", "\nDescripcion",
						new MensajeListener() {
							@Override
							public void alDestruir() {
								txtDescripcion.setFocus(true);
							}
						});
			}
			else {
				TipoPublicacion tipoPublicacionTemp = servicioTipoPublicacion
						.buscarUno(tipoPublicacion.getNombre());
				TipoPublicacion tipoPublicacionTemp1 = servicioTipoPublicacion
						.buscarUno(tipoPublicacion.getDescripcion());
				if ((tipoPublicacionTemp != null) && (tipoPublicacionTemp1 != null)) {
					tipoPublicacion.setId(tipoPublicacionTemp.getId());
					tipoPublicacion.setId(tipoPublicacionTemp1.getId());
				}

				try {
					tipoPublicacion.setUri(HelperString.urlTitle(tipoPublicacion.getNombre()));
					servicioTipoPublicacion.guardarTipoPublicacion(tipoPublicacion);
					MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
						@Override
						public void alAceptar() {
							ctrlwintipoPublicacion.recargar();
							ctrlwintipoPublicacion.apagarBotones();
							winEditTipoPublicacion.detach();
						}
					});
				} catch (Exception e) {
					MensajeEmergente.mostrar("ERRDB");
					e.printStackTrace();
				}

			}

		}

		public void onClose$winEditTipoPublicacion() {
			ctrlwintipoPublicacion.apagarBotones();
			this.winEditTipoPublicacion.detach();
		}
}

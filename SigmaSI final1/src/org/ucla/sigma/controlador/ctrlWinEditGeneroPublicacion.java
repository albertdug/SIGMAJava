/**
 * 
 */
package org.ucla.sigma.controlador;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.GeneroPublicacion;
import org.ucla.sigma.servicio.ServicioGeneroPublicacion;
import org.zkoss.zkplus.spring.SpringUtil;

/**
 * @author usuario
 *
 */
public class ctrlWinEditGeneroPublicacion extends GenericForwardComposer {

	private Window winEditGeneroPublicacion;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;
	private Textbox txtDescripcion;
	

	// ----------------------------------------------------------------------------------------------------

		private ServicioGeneroPublicacion servicioGeneroPublicacion;
		private ctrlWinGeneroPublicacion ctrlwingeneroPublicacion;

		// ----------------------------------------------------------------------------------------------------

		private GeneroPublicacion generoPublicacion;

		// ----------------------------------------------------------------------------------------------------

		public ServicioGeneroPublicacion getServicioGeneroPublicacion() {
			return servicioGeneroPublicacion;
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

		public void setServicioGeneroPublicacion(ServicioGeneroPublicacion servicioGeneroPublicacion) {
			this.servicioGeneroPublicacion = servicioGeneroPublicacion;
		}

		public ctrlWinGeneroPublicacion getCtrlwingeneroPublicacion() {
			return ctrlwingeneroPublicacion;
		}

		public void setCtrlwingeneroPublicacion(ctrlWinGeneroPublicacion ctrlwingeneroPublicacion) {
			this.ctrlwingeneroPublicacion = ctrlwingeneroPublicacion;
		}

		public Window getWinEditGeneroPublicacion() {
			return winEditGeneroPublicacion;
		}

		public void setWinEditGeneroPublicacion(Window winEditGeneroPublicacion) {
			this.winEditGeneroPublicacion = winEditGeneroPublicacion;
		}

		public GeneroPublicacion getGeneroPublicacion() {
			return generoPublicacion;
		}

		public void setGeneroPublicacion(GeneroPublicacion generoPublicacion) {
			this.generoPublicacion = generoPublicacion;
		}

		@Override
		public void doAfterCompose(Component comp) throws Exception {
			super.doAfterCompose(comp);
			winEditGeneroPublicacion.setAttribute(comp.getId() + "ctrl", this);
			servicioGeneroPublicacion = (ServicioGeneroPublicacion) SpringUtil
					.getBean("beanServicioGeneroPublicacion");
			generoPublicacion = new GeneroPublicacion();
			ctrlwingeneroPublicacion = (ctrlWinGeneroPublicacion) arg.get("ctrlWinGeneroPublicacion");
			if (!(arg.get("objeto") == null)) {
				generoPublicacion = (GeneroPublicacion) arg.get("objeto");
			}
		}

		public void onClick$btnCancelar() {
			ctrlwingeneroPublicacion.recargar();
			ctrlwingeneroPublicacion.apagarBotones();
			this.winEditGeneroPublicacion.detach();
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
				GeneroPublicacion generoPublicacionTemp = servicioGeneroPublicacion
						.buscarUno(generoPublicacion.getNombre());
				GeneroPublicacion generoPublicacionTemp1 = servicioGeneroPublicacion
						.buscarUno(generoPublicacion.getDescripcion());
				if ((generoPublicacionTemp != null) && (generoPublicacionTemp1 != null)) {
					generoPublicacion.setId(generoPublicacionTemp.getId());
					generoPublicacion.setId(generoPublicacionTemp1.getId());
				}

				try {
					servicioGeneroPublicacion.guardarGeneroPublicacion(generoPublicacion);
					MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
						@Override
						public void alAceptar() {
							ctrlwingeneroPublicacion.recargar();
							ctrlwingeneroPublicacion.apagarBotones();
							winEditGeneroPublicacion.detach();
						}
					});
				} catch (Exception e) {
					MensajeEmergente.mostrar("ERRDB");
					e.printStackTrace();
				}

			}

		}

		public void onClose$winEditTipoPublicacion() {
			ctrlwingeneroPublicacion.apagarBotones();
			this.winEditGeneroPublicacion.detach();
		}
}

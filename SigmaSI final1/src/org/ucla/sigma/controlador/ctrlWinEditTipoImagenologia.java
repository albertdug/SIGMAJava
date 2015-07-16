/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.TipoImagenologia;
import org.ucla.sigma.servicio.ServicioTipoImagenologia;
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
public class ctrlWinEditTipoImagenologia extends GenericForwardComposer {

	private Window winEditTipoImagenologia;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;
	private Textbox txtDescripcion;

	
	// ----------------------------------------------------------------------------------------------------

		private ServicioTipoImagenologia servicioTipoImagenologia;
		private ctrlWinTipoImagenologia ctrlWinTipoImagenologia;

		// ----------------------------------------------------------------------------------------------------

		private TipoImagenologia tipoImagenologia;
		
		
		public Window getWinEditTipoImagenologia() {
			return winEditTipoImagenologia;
		}

		public void setWinEditTipoImagenologia(Window winEditTipoImagenologia) {
			this.winEditTipoImagenologia = winEditTipoImagenologia;
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

		
		public Textbox getTxtDescripcion() {
			return txtDescripcion;
		}

		public void setTxtDescripcion(Textbox txtDescripcion) {
			this.txtDescripcion = txtDescripcion;
		}

		public void setTxtNombre(Textbox txtNombre) {
			this.txtNombre = txtNombre;
		}

		public ServicioTipoImagenologia getServicioTipoImagenologia() {
			return servicioTipoImagenologia;
		}

		public void setServicioTipoImagenologia(
				ServicioTipoImagenologia servicioTipoImagenologia) {
			this.servicioTipoImagenologia = servicioTipoImagenologia;
		}


		public ctrlWinTipoImagenologia getCtrlWinTipoImagenologia() {
			return ctrlWinTipoImagenologia;
		}

		public void setCtrlWinTipoImagenologia(
				ctrlWinTipoImagenologia ctrlWinTipoImagenologia) {
			this.ctrlWinTipoImagenologia = ctrlWinTipoImagenologia;
		}

		public TipoImagenologia getTipoImagenologia() {
			return tipoImagenologia;
		}

		public void setTipoImagenologia(TipoImagenologia tipoImagenologia) {
			this.tipoImagenologia = tipoImagenologia;
		}

		@Override
		public void doAfterCompose(Component comp) throws Exception {
			super.doAfterCompose(comp);
			winEditTipoImagenologia.setAttribute(comp.getId() + "ctrl", this);
			servicioTipoImagenologia = (ServicioTipoImagenologia) SpringUtil
					.getBean("beanServicioTipoImagenologia");
			tipoImagenologia = new TipoImagenologia();
			ctrlWinTipoImagenologia = (ctrlWinTipoImagenologia) arg.get("ctrlWinTipoImagenologia");
			if (!(arg.get("objeto") == null)) {
				tipoImagenologia = (TipoImagenologia) arg.get("objeto");
			}
		}

		public void onClick$btnCancelar() {
			ctrlWinTipoImagenologia.recargar();
			ctrlWinTipoImagenologia.apagarBotones();
			this.winEditTipoImagenologia.detach();
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
				TipoImagenologia tipoImagTemp = servicioTipoImagenologia
						.buscarUno(tipoImagenologia.getNombre());
				if (tipoImagTemp != null) {
					tipoImagenologia.setId(tipoImagTemp.getId());
				}

				try {
					servicioTipoImagenologia.guardarTipoImagenologia(tipoImagenologia);
					MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
						@Override
						public void alAceptar() {
							ctrlWinTipoImagenologia.recargar();
							ctrlWinTipoImagenologia.apagarBotones();
							winEditTipoImagenologia.detach();
						}
					});
				} catch (Exception e) {
					MensajeEmergente.mostrar("ERRDB");
					e.printStackTrace();
				}

			}

		}

		public void onClose$winEditTipoImagenologia() {
			ctrlWinTipoImagenologia.apagarBotones();
			this.winEditTipoImagenologia.detach();
		}		
		
		
}

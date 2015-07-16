/**
 * 
 */
package org.ucla.sigma.controlador;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.modelo.CursoClinico;
import org.ucla.sigma.servicio.ServicioCursoClinico;
import org.zkoss.zkplus.spring.SpringUtil;

/**
 * @author usuario
 *
 */
public class ctrlWinEditCursoClinico extends GenericForwardComposer {

	private Window winEditCursoClinico;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

		private ServicioCursoClinico servicioCursoClinico;
		private ctrlWinCursoClinico ctrlwincursoClinico;

		// ----------------------------------------------------------------------------------------------------

		private CursoClinico cursoClinico;

		// ----------------------------------------------------------------------------------------------------

		public ServicioCursoClinico getServicioCursoClinico() {
			return servicioCursoClinico;
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

		public void setServicioCursoClinico(ServicioCursoClinico servicioCursoClinico) {
			this.servicioCursoClinico = servicioCursoClinico;
		}

		public ctrlWinCursoClinico getCtrlwincursoClinico() {
			return ctrlwincursoClinico;
		}

		public void setCtrlwincursoClinico(ctrlWinCursoClinico ctrlwincursoClinico) {
			this.ctrlwincursoClinico = ctrlwincursoClinico;
		}

		public Window getWinEditCursoClinico() {
			return winEditCursoClinico;
		}

		public void setWinEditCursoClinico(Window winEditCursoClinico) {
			this.winEditCursoClinico = winEditCursoClinico;
		}

		public CursoClinico getCursoClinico() {
			return cursoClinico;
		}

		public void setCursoClinico(CursoClinico cursoClinico) {
			this.cursoClinico = cursoClinico;
		}

		@Override
		public void doAfterCompose(Component comp) throws Exception {
			super.doAfterCompose(comp);
			winEditCursoClinico.setAttribute(comp.getId() + "ctrl", this);
			servicioCursoClinico = (ServicioCursoClinico) SpringUtil
					.getBean("beanServicioCursoClinico");
			cursoClinico = new CursoClinico();
			ctrlwincursoClinico = (ctrlWinCursoClinico) arg.get("ctrlWinCursoClinico");
			if (!(arg.get("objeto") == null)) {
				cursoClinico = (CursoClinico) arg.get("objeto");
			}
		}

		public void onClick$btnCancelar() {
			ctrlwincursoClinico.apagarBotones();
			this.winEditCursoClinico.detach();
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
				CursoClinico cursoClinicoTemp = servicioCursoClinico
						.buscarUno(cursoClinico.getNombre());
				if (cursoClinicoTemp != null) {
					cursoClinico.setId(cursoClinicoTemp.getId());
				}

				try {
					servicioCursoClinico.guardarCursoClinico(cursoClinico);
					MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
						@Override
						public void alAceptar() {
							ctrlwincursoClinico.recargar();
							ctrlwincursoClinico.apagarBotones();
							winEditCursoClinico.detach();
						}
					});
				} catch (Exception e) {
					MensajeEmergente.mostrar("ERRDB");
					e.printStackTrace();
				}

			}

		}

		public void onClose$winEditCursoClinico() {
			ctrlwincursoClinico.apagarBotones();
			this.winEditCursoClinico.detach();
		}
}

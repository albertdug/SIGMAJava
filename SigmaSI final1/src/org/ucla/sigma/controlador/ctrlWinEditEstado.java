package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Estado;
import org.ucla.sigma.servicio.ServicioEstado;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEditEstado extends GenericForwardComposer {

	private Window winEditEstado;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;
	private Textbox txtAbreviatura;

	// ----------------------------------------------------------------------------------------------------

	private ServicioEstado servicioEstado;
	private ctrlWinEstado ctrlwinestado;

	// ----------------------------------------------------------------------------------------------------

	private Estado estado;

	// ----------------------------------------------------------------------------------------------------

	public ServicioEstado getServicioEstado() {
		return servicioEstado;
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

	public Textbox getTxtAbreviatura() {
		return txtAbreviatura;
	}

	public void setTxtAbreviatura(Textbox txtAbreviatura) {
		this.txtAbreviatura = txtAbreviatura;
	}

	public void setServicioEstado(ServicioEstado servicioEstado) {
		this.servicioEstado = servicioEstado;
	}

	public ctrlWinEstado getCtrlwinestado() {
		return ctrlwinestado;
	}

	public void setCtrlwinestado(ctrlWinEstado ctrlwinestado) {
		this.ctrlwinestado = ctrlwinestado;
	}

	public Window getWinEditEstado() {
		return winEditEstado;
	}

	public void setWinEditEstado(Window winEditEstado) {
		this.winEditEstado = winEditEstado;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditEstado.setAttribute(comp.getId() + "ctrl", this);
		servicioEstado = (ServicioEstado) SpringUtil
				.getBean("beanServicioEstado");
		estado = new Estado();
		ctrlwinestado = (ctrlWinEstado) arg.get("ctrlWinEstado");
		if (!(arg.get("objeto") == null)) {
			estado = (Estado) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinestado.recargar();
		ctrlwinestado.apagarBotones();
		this.winEditEstado.detach();
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
		} else if (txtAbreviatura.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nAbreviatura",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtAbreviatura.setFocus(true);
						}
					});
		} else {
			Estado estadoTemp = servicioEstado.buscarUno(estado.getNombre());
			if (estadoTemp != null) {
				estado.setId(estadoTemp.getId());
			}

			try {
				servicioEstado.guardarEstado(estado);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinestado.recargar();
						ctrlwinestado.apagarBotones();
						winEditEstado.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditEstado() {
		ctrlwinestado.apagarBotones();
		this.winEditEstado.detach();
	}
}

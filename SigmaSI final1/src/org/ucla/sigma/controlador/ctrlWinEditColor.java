/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Color;
import org.ucla.sigma.servicio.ServicioColor;
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
public class ctrlWinEditColor extends GenericForwardComposer {

	private Window winEditColor;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioColor servicioColor;
	private ctrlWinColor ctrlwincolor;
	private Color color;
	
	// ----------------------------------------------------------------------------------------------------
	
	public Window getWinEditColor() {
		return winEditColor;
	}

	public void setWinEditColor(Window winEditColor) {
		this.winEditColor = winEditColor;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioColor getServicioColor() {
		return servicioColor;
	}

	public void setServicioColor(ServicioColor servicioColor) {
		this.servicioColor = servicioColor;
	}

	public ctrlWinColor getCtrlwincolor() {
		return ctrlwincolor;
	}

	public void setCtrlwincolor(ctrlWinColor ctrlwincolor) {
		this.ctrlwincolor = ctrlwincolor;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditColor.setAttribute(comp.getId() + "ctrl", this);
		servicioColor = (ServicioColor) SpringUtil
				.getBean("beanServicioColor");
		color = new Color();
		ctrlwincolor = (ctrlWinColor) arg.get("ctrlWinColor");
		if (!(arg.get("objeto") == null)) {
			color = (Color) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwincolor.recargar();
		ctrlwincolor.apagarBotones();
		this.winEditColor.detach();
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
			Color colorTemp = servicioColor.buscarUno(color.getNombre());
			if (colorTemp != null) {
				color.setId(colorTemp.getId());
			}

			try {
				servicioColor.guardarColor(color);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwincolor.recargar();
						ctrlwincolor.apagarBotones();
						winEditColor.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditColor() {
		ctrlwincolor.apagarBotones();
		this.winEditColor.detach();
	}



}

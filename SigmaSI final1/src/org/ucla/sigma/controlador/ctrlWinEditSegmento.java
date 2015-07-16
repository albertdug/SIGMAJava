package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Segmento;
import org.ucla.sigma.servicio.ServicioSegmento;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author Albert
 * 
 */
public class ctrlWinEditSegmento extends GenericForwardComposer {

	private Window winEditSegmento;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioSegmento servicioSegmento;
	private ctrlWinSegmento ctrlwinsegmento;

	// ----------------------------------------------------------------------------------------------------

	private Segmento segmento;

	// ----------------------------------------------------------------------------------------------------

	public ServicioSegmento getServicioSegmento() {
		return servicioSegmento;
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

	public void setServicioSegmento(ServicioSegmento servicioSegmento) {
		this.servicioSegmento = servicioSegmento;
	}

	public ctrlWinSegmento getCtrlwinsegmento() {
		return ctrlwinsegmento;
	}

	public void setCtrlwinsegmento(ctrlWinSegmento ctrlwinsegmento) {
		this.ctrlwinsegmento = ctrlwinsegmento;
	}

	public Window getWinEditSegmento() {
		return winEditSegmento;
	}

	public void setWinEditSegmento(Window winEditSegmento) {
		this.winEditSegmento = winEditSegmento;
	}

	public Segmento getSegmento() {
		return segmento;
	}

	public void setSegmento(Segmento segmento) {
		this.segmento = segmento;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		winEditSegmento.setAttribute(comp.getId() + "ctrl", this);
		servicioSegmento = (ServicioSegmento) SpringUtil
				.getBean("beanServicioSegmento");
		segmento = new Segmento();
		ctrlwinsegmento = (ctrlWinSegmento) arg.get("ctrlWinSegmento");
		if (!(arg.get("objeto") == null)) {
			segmento = (Segmento) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinsegmento.apagarBotones();
		ctrlwinsegmento.recargar();
		this.winEditSegmento.detach();
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
			Segmento segmentoTemp = servicioSegmento.buscarUno(segmento
					.getNombre());
			if (segmentoTemp != null) {
				segmento.setId(segmentoTemp.getId());
			}

			try {
				servicioSegmento.guardarSegmento(segmento);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinsegmento.recargar();
						ctrlwinsegmento.apagarBotones();
						winEditSegmento.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditEspecie() {
		ctrlwinsegmento.apagarBotones();
		this.winEditSegmento.detach();
	}

}

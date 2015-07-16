/**
 * 
 */
package org.ucla.sigma.controlador;

import org.hibernate.ejb.criteria.ParameterContainer.Helper;
import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.components.HelperFile;
import org.ucla.sigma.components.HelperString;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Adjunto;
import org.ucla.sigma.servicio.ServicioAdjunto;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author rafael
 * 
 */
public class ctrlWinEditAdjunto extends GenericForwardComposer {

	private Window winEditAdjunto;
	private Button btnCancelar;
	private Button btnGuardar;
	private Button upAdjunto;
	private Datebox dbCreacion;
	private Label lblAdj;
	// ----------------------------------------------------------------------------------------------------

	private ServicioAdjunto servicioAdjunto;
	private ctrlWinAdjunto ctrlwinadjunto;

	// ----------------------------------------------------------------------------------------------------

	private Adjunto adjunto;

	public Window getWinEditAdjunto() {
		return winEditAdjunto;
	}

	public void setWinEditAdjunto(Window winEditAdjunto) {
		this.winEditAdjunto = winEditAdjunto;
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

	public Button getUpAdjunto() {
		return upAdjunto;
	}

	public void setUpAdjunto(Button upAdjunto) {
		this.upAdjunto = upAdjunto;
	}

	public Datebox getDbCreacion() {
		return dbCreacion;
	}

	public void setDbCreacion(Datebox dbCreacion) {
		this.dbCreacion = dbCreacion;
	}

	public ServicioAdjunto getServicioAdjunto() {
		return servicioAdjunto;
	}

	public void setServicioAdjunto(ServicioAdjunto servicioAdjunto) {
		this.servicioAdjunto = servicioAdjunto;
	}

	public ctrlWinAdjunto getCtrlwinadjunto() {
		return ctrlwinadjunto;
	}

	public void setCtrlwinadjunto(ctrlWinAdjunto ctrlwinadjunto) {
		this.ctrlwinadjunto = ctrlwinadjunto;
	}

	public Adjunto getAdjunto() {
		return adjunto;
	}

	public void setAdjunto(Adjunto adjunto) {
		this.adjunto = adjunto;
	}

	// ----------------------------------------------------------------------------------------------------

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditAdjunto.setAttribute(comp.getId() + "ctrl", this);
		servicioAdjunto = (ServicioAdjunto) SpringUtil
				.getBean("beanServicioAdjunto");
		adjunto = new Adjunto();
		adjunto.setCreacion(HelperDate.now());
		ctrlwinadjunto = (ctrlWinAdjunto) arg.get("ctrlWinAdjunto");
		if (!(arg.get("objeto") == null)) {
			adjunto = (Adjunto) arg.get("objeto");
			 lblAdj.setValue(adjunto.getNombre());
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinadjunto.recargar();
		ctrlwinadjunto.apagarBotones();
		this.winEditAdjunto.detach();
	}

	public void onClick$btnGuardar() {

		if (adjunto.getBytes() == null) {
			MensajeEmergente.mostrar("NOEMPTY", "\nAdjunto");
		} else {
			Adjunto adjuntoTemp = servicioAdjunto
					.buscarUno(adjunto.getNombre());
			if (adjuntoTemp != null) {
				adjunto.setId(adjuntoTemp.getId());
			}

			try {
				adjunto.setCreacion(dbCreacion.getValue());
				servicioAdjunto.guardarAdjunto(adjunto);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinadjunto.recargar();
						ctrlwinadjunto.apagarBotones();
						winEditAdjunto.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditAdjunto() {
		ctrlwinadjunto.apagarBotones();
		this.winEditAdjunto.detach();
	}

	public void subir(Event event) {
		org.zkoss.util.media.Media media = ((org.zkoss.zk.ui.event.UploadEvent) event)
				.getMedia();
		if (HelperFile.isFileType(media.getFormat(), "odt", "odf", "odp",
				"pdf", "doc", "docx", "ppt", "pptx", "xls", "xlsx", "zip",
				"rar")) {
			lblAdj.setValue(HelperFile.getOnlyName(media.getName()));
			adjunto.setNombre(HelperFile.getOnlyName(media.getName()));
			adjunto.setBytes(media.getByteData());
			adjunto.setExtension(HelperFile.getExtension(media.getName()));

		} else {
			MensajeEmergente.mostrar("ERRFORMAT");
		}
	}

}

/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.List;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Area;
import org.ucla.sigma.modelo.Hospital;
import org.ucla.sigma.servicio.ServicioArea;
import org.ucla.sigma.servicio.ServicioHospital;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author rafael
 * 
 */
public class ctrlWinEditArea extends GenericForwardComposer {

	private Window winEditArea;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtDescripcion;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioHospital servicioHospital;
	private ServicioArea servicioArea;
	private ctrlWinArea ctrlwinarea;

	// ----------------------------------------------------------------------------------------------------

	private Hospital hospital;
	private Area area;

	// ----------------------------------------------------------------------------------------------------

	public Window getWinEditArea() {
		return winEditArea;
	}

	public void setWinEditArea(Window winEditArea) {
		this.winEditArea = winEditArea;
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

	public ServicioHospital getServicioHospital() {
		return servicioHospital;
	}

	public void setServicioHospital(ServicioHospital servicioHospital) {
		this.servicioHospital = servicioHospital;
	}

	public ServicioArea getServicioArea() {
		return servicioArea;
	}

	public void setServicioArea(ServicioArea servicioArea) {
		this.servicioArea = servicioArea;
	}

	public ctrlWinArea getCtrlwinarea() {
		return ctrlwinarea;
	}

	public void setCtrlwinarea(ctrlWinArea ctrlwinarea) {
		this.ctrlwinarea = ctrlwinarea;
	}

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	// ----------------------------------------------------------------------------------------------------

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditArea.setAttribute(comp.getId() + "ctrl", this);
		servicioArea = (ServicioArea) SpringUtil.getBean("beanServicioArea");
		servicioHospital = (ServicioHospital) SpringUtil
				.getBean("beanServicioHospital");
		area = new Area();
		hospital = servicioHospital.buscarUnico();
		ctrlwinarea = (ctrlWinArea) arg.get("ctrlWinArea");
		if (!(arg.get("objeto") == null)) {
			area = (Area) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinarea.recargar();
		ctrlwinarea.apagarBotones();
		this.winEditArea.detach();
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
			Area areaTemp = servicioArea.buscarUno(area.getNombre());
			if (areaTemp != null) {
				area.setId(areaTemp.getId());
			}
			area.setHospital(hospital);
			try {
				servicioArea.guardarArea(area);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinarea.recargar();
						ctrlwinarea.apagarBotones();
						winEditArea.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditArea() {
		ctrlwinarea.apagarBotones();
		this.winEditArea.detach();
	}

}

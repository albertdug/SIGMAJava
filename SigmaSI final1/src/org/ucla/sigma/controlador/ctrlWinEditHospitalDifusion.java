/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.List;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Difusion;
import org.ucla.sigma.modelo.Hospital;
import org.ucla.sigma.modelo.HospitalDifusion;
import org.ucla.sigma.modelo.Servicio;
import org.ucla.sigma.modelo.TipoServicio;
import org.ucla.sigma.servicio.ServicioDifusion;
import org.ucla.sigma.servicio.ServicioHospital;
import org.ucla.sigma.servicio.ServicioHospitalDifusion;
import org.ucla.sigma.servicio.ServicioServicio;
import org.ucla.sigma.servicio.ServicioTipoServicio;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author lis
 * 
 */
public class ctrlWinEditHospitalDifusion extends GenericForwardComposer {

	private Window winEditHospitalDifusion;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtEnlace;
	private Listbox listDifusion;

	// ----------------------------------------------------------------------------------------------------

	private ServicioHospitalDifusion servicioHospitalDifusion;
	private ServicioDifusion servicioDifusion;
	private ServicioHospital servicioHospital;
	private ctrlWinHospitalDifusion ctrlWinHospitalDifusion;
	private int indexDifusion = -1;

	// ----------------------------------------------------------------------------------------------------

	private HospitalDifusion hospitalDifusion;
	private List<Difusion> difusiones = new ArrayList<Difusion>();
	private Hospital hospital = new Hospital();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinEditHospitalDifusion() {
		return winEditHospitalDifusion;
	}

	public ServicioHospital getServicioHospital() {
		return servicioHospital;
	}

	public void setServicioHospital(ServicioHospital servicioHospital) {
		this.servicioHospital = servicioHospital;
	}

	public List<Difusion> getDifusiones() {
		return difusiones;
	}

	public void setDifusiones(List<Difusion> difusiones) {
		this.difusiones = difusiones;
	}

	public Listbox getListDifusion() {
		return listDifusion;
	}

	public void setListDifusion(Listbox listDifusion) {
		this.listDifusion = listDifusion;
	}

	public int getIndexDifusion() {
		return indexDifusion;
	}

	public void setIndexDifusion(int indexDifusion) {
		this.indexDifusion = indexDifusion;
	}

	public ServicioDifusion getServicioDifusion() {
		return servicioDifusion;
	}

	public void setServicioDifusion(ServicioDifusion servicioDifusion) {
		this.servicioDifusion = servicioDifusion;
	}

	public void setWinEditHospitalDifusion(Window winEditHospitalDifusion) {
		this.winEditHospitalDifusion = winEditHospitalDifusion;
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

	public Textbox getTxtEnlace() {
		return txtEnlace;
	}

	public void setTxtEnlace(Textbox txtEnlace) {
		this.txtEnlace = txtEnlace;
	}

	public ServicioHospitalDifusion getServicioHospitalDifusion() {
		return servicioHospitalDifusion;
	}

	public void setServicioHospitalDifusion(
			ServicioHospitalDifusion servicioHospitalDifusion) {
		this.servicioHospitalDifusion = servicioHospitalDifusion;
	}

	public ctrlWinHospitalDifusion getCtrlWinHospitalDifusion() {
		return ctrlWinHospitalDifusion;
	}

	public void setCtrlWinHospitalDifusion(
			ctrlWinHospitalDifusion ctrlWinHospitalDifusion) {
		this.ctrlWinHospitalDifusion = ctrlWinHospitalDifusion;
	}

	public HospitalDifusion getHospitalDifusion() {
		return hospitalDifusion;
	}

	public void setHospitalDifusion(HospitalDifusion hospitalDifusion) {
		this.hospitalDifusion = hospitalDifusion;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditHospitalDifusion.setAttribute(comp.getId() + "ctrl", this);
		servicioHospitalDifusion = (ServicioHospitalDifusion) SpringUtil
				.getBean("beanServicioHospitalDifusion");
		servicioDifusion = (ServicioDifusion) SpringUtil
				.getBean("beanServicioDifusion");
		servicioHospital = (ServicioHospital) SpringUtil
				.getBean("beanServicioHospital");
		hospitalDifusion = new HospitalDifusion();
		difusiones = servicioDifusion.buscarTodos();
		hospital = servicioHospital.buscarUnico();
		ctrlWinHospitalDifusion = (ctrlWinHospitalDifusion) arg
				.get("ctrlWinHospitalDifusion");
		if (!(arg.get("objeto") == null)) {
			hospitalDifusion = (HospitalDifusion) arg.get("objeto");
			indexDifusion = difusiones.indexOf(hospitalDifusion.getDifusion());
		}
	}

	public void onClick$btnCancelar() {
		ctrlWinHospitalDifusion.recargar();
		ctrlWinHospitalDifusion.apagarBotones();
		this.winEditHospitalDifusion.detach();
	}

	public void onClick$btnGuardar() {

		if (txtEnlace.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nNombre",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtEnlace.setFocus(true);
						}
					});
		} else if (listDifusion.getSelectedIndex() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nTipo difusión",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listDifusion.setFocus(true);
						}
					});
		} else {
			HospitalDifusion hospitalDifusionTemp = servicioHospitalDifusion
					.buscarUno(hospitalDifusion.getEnlace());
			if (hospitalDifusionTemp != null) {
				hospitalDifusion.setId(hospitalDifusionTemp.getId());
			}
			hospitalDifusion.setDifusion((Difusion) listDifusion
					.getSelectedItem().getValue());
			hospitalDifusion.setHospital(hospital);
			try {
				servicioHospitalDifusion
						.guardarHospitalDifusion(hospitalDifusion);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlWinHospitalDifusion.recargar();
						ctrlWinHospitalDifusion.apagarBotones();
						winEditHospitalDifusion.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onAfterRender$listDifusion() {
		listDifusion.setSelectedIndex(indexDifusion);
	}

	public void onClose$winEditHospitalDifusion() {
		ctrlWinHospitalDifusion.apagarBotones();
		this.winEditHospitalDifusion.detach();
	}
}

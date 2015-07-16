package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Medicamento;
import org.ucla.sigma.servicio.ServicioMedicamento;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEditMedicamento extends GenericForwardComposer {

	private Window winEditMedicamento;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;
	

	// ----------------------------------------------------------------------------------------------------

	private ServicioMedicamento servicioMedicamento;
	private ctrlWinMedicamento ctrlwinmedicamento;

	// ----------------------------------------------------------------------------------------------------

	private Medicamento medicamento;

	// ----------------------------------------------------------------------------------------------------


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

	

	public void setCtrlwinmedicamento(ctrlWinMedicamento ctrlwinmedicamento) {
		this.ctrlwinmedicamento = ctrlwinmedicamento;
	}

	
	public void setWinEditMedicamento(Window winEditMedicamento) {
		this.winEditMedicamento = winEditMedicamento;
	}

	
	public ServicioMedicamento getServicioMedicamento() {
		return servicioMedicamento;
	}

	public void setServicioMedicamento(ServicioMedicamento servicioMedicamento) {
		this.servicioMedicamento = servicioMedicamento;
	}

	public Medicamento getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}

	public Window getWinEditMedicamento() {
		return winEditMedicamento;
	}

	public ctrlWinMedicamento getCtrlwinmedicamento() {
		return ctrlwinmedicamento;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditMedicamento.setAttribute(comp.getId() + "ctrl", this);
		servicioMedicamento = (ServicioMedicamento) SpringUtil
				.getBean("beanServicioMedicamento");
		medicamento = new Medicamento();
		ctrlwinmedicamento = (ctrlWinMedicamento) arg.get("ctrlWinMedicamento");
		if (!(arg.get("objeto") == null)) {
			medicamento = (Medicamento) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinmedicamento.recargar();
		ctrlwinmedicamento.apagarBotones();
		this.winEditMedicamento.detach();
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
		}  else {
			Medicamento medicamentoTemp = servicioMedicamento.buscarUno(medicamento.getNombre());
			if (medicamentoTemp != null) {
				medicamento.setId(medicamentoTemp.getId());
			}

			try {
				servicioMedicamento.guardarMedicamento(medicamento);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinmedicamento.recargar();
						ctrlwinmedicamento.apagarBotones();
						winEditMedicamento.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditMedicamento() {
		ctrlwinmedicamento.apagarBotones();
		this.winEditMedicamento.detach();
	}
}

package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.List;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Frecuencia;
import org.ucla.sigma.modelo.FrecuenciaTipo;
import org.ucla.sigma.modelo.Ganglio;
import org.ucla.sigma.modelo.TipoAlimentacion;
import org.ucla.sigma.modelo.TipoEntidadExterna;
import org.ucla.sigma.servicio.ServicioFrecuencia;
import org.ucla.sigma.servicio.ServicioFrecuenciaTipo;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEditFrecuencia extends GenericForwardComposer {

	private Window winEditFrecuencia;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtDescripcion;
	private Listbox listTipoFrecuencia;
	// ----------------------------------------------------------------------------------------------------

	private ServicioFrecuencia servicioFrecuencia;
	private ServicioFrecuenciaTipo servicioFrecuenciaTipo;
	private ctrlWinFrecuencia ctrlwinfrecuencia;
	private List<FrecuenciaTipo> tipoFrecuencias = new ArrayList<FrecuenciaTipo>();

	// ----------------------------------------------------------------------------------------------------

	private Frecuencia frecuencia;
	private int indexTipoFrecuencia = -1;

	// ----------------------------------------------------------------------------------------------------
	
	
	public ServicioFrecuencia getServicioFrecuencia() {
		return servicioFrecuencia;
	}

	public Listbox getListTipoFrecuencia() {
		return listTipoFrecuencia;
	}

	public void setListTipoFrecuencia(Listbox listTipoFrecuencia) {
		this.listTipoFrecuencia = listTipoFrecuencia;
	}

	public ServicioFrecuenciaTipo getServicioFrecuenciaTipo() {
		return servicioFrecuenciaTipo;
	}

	public void setServicioFrecuenciaTipo(
			ServicioFrecuenciaTipo servicioFrecuenciaTipo) {
		this.servicioFrecuenciaTipo = servicioFrecuenciaTipo;
	}

	public List<FrecuenciaTipo> getTipoFrecuencias() {
		return tipoFrecuencias;
	}

	public void setTipoFrecuencias(List<FrecuenciaTipo> tipoFrecuencias) {
		this.tipoFrecuencias = tipoFrecuencias;
	}

	public void setServicioFrecuencia(ServicioFrecuencia servicioFrecuencia) {
		this.servicioFrecuencia = servicioFrecuencia;
	}

	public void setCtrlwinfrecuencia(ctrlWinFrecuencia ctrlwinfrecuencia) {
		this.ctrlwinfrecuencia = ctrlwinfrecuencia;
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

	public void setServicioEstado(ServicioFrecuencia servicioFrecuencia) {
		this.servicioFrecuencia = servicioFrecuencia;
	}

	public ctrlWinFrecuencia getCtrlwinfrecuencia() {
		return ctrlwinfrecuencia;
	}

	public void setCtrlwinestado(ctrlWinFrecuencia ctrlwinfrecuencia) {
		this.ctrlwinfrecuencia = ctrlwinfrecuencia;
	}

	public Window getWinEditFrecuencia() {
		return winEditFrecuencia;
	}

	public void setWinEditFrecuencia(Window winEditFrecuencia) {
		this.winEditFrecuencia = winEditFrecuencia;
	}

	public Frecuencia getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(Frecuencia frecuencia) {
		this.frecuencia = frecuencia;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditFrecuencia.setAttribute(comp.getId() + "ctrl", this);
		servicioFrecuencia = (ServicioFrecuencia) SpringUtil
				.getBean("beanServicioFrecuencia");
		servicioFrecuenciaTipo = (ServicioFrecuenciaTipo) SpringUtil
				.getBean("beanServicioFrecuenciaTipo");
		frecuencia = new Frecuencia();
		tipoFrecuencias = servicioFrecuenciaTipo.buscarTodos();
		ctrlwinfrecuencia = (ctrlWinFrecuencia) arg.get("ctrlWinFrecuencia");
		if (!(arg.get("objeto") == null)) {
			frecuencia = (Frecuencia) arg.get("objeto");
			indexTipoFrecuencia = tipoFrecuencias.indexOf(frecuencia.getFrecuenciaTipo());
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinfrecuencia.recargar();
		ctrlwinfrecuencia.apagarBotones();
		this.winEditFrecuencia.detach();
	}

	public void onClick$btnGuardar() {

		if (txtDescripcion.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nDescripcion",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtDescripcion.setFocus(true);
						}
					});
		} else
		if (listTipoFrecuencia.getSelectedIndex() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nTipo de Frecuencia",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listTipoFrecuencia.setFocus(true);
						}
					});
		} else {
			Frecuencia frecuenciaTemp = servicioFrecuencia.buscarUno(frecuencia
					.getNombre());
			if (frecuenciaTemp != null) {
				frecuencia.setId(frecuenciaTemp.getId());
			}
			frecuencia.setFrecuenciaTipo((FrecuenciaTipo)listTipoFrecuencia.getSelectedItem().getValue());
			try {
				servicioFrecuencia.guardarFrecuencia(frecuencia);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinfrecuencia.recargar();
						ctrlwinfrecuencia.apagarBotones();
						winEditFrecuencia.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}
		}

	}

	public void onClose$winEditFrecuencia() {
		ctrlwinfrecuencia.apagarBotones();
		this.winEditFrecuencia.detach();
	}
	
	public void onAfterRender$listTipoFrecuencia() {
		listTipoFrecuencia.setSelectedIndex(indexTipoFrecuencia);		
	}
}

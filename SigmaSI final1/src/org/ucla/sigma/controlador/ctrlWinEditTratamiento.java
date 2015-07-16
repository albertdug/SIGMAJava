/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.List;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.TipoTratamiento;
import org.ucla.sigma.modelo.Tratamiento;
import org.ucla.sigma.servicio.ServicioTipoTratamiento;
import org.ucla.sigma.servicio.ServicioTratamiento;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author promo49
 * 
 */
public class ctrlWinEditTratamiento extends GenericForwardComposer {

	private Window winEditTratamiento;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;
	private Listbox listTipotratamiento;
	
	private ServicioTratamiento servicioTratamiento;
	private ServicioTipoTratamiento servicioTipoTratamiento;
	private ctrlWinTratamiento ctrlwintratamiento;
	private int indexTipoTratamiento = -1;

	// ----------------------------------------------------------------------------------------------------

	private List<TipoTratamiento> tipoTratamientos = new ArrayList<TipoTratamiento>();
	private Tratamiento tratamiento;
	
	public Listbox getListTipotratamiento() {
		return listTipotratamiento;
	}
	public void setListTipotratamiento(Listbox listTipotratamiento) {
		this.listTipotratamiento = listTipotratamiento;
	}
	public Window getWinEditTratamiento() {
		return winEditTratamiento;
	}
	public void setWinEditTratamiento(Window winEditTratamiento) {
		this.winEditTratamiento = winEditTratamiento;
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
	public ServicioTratamiento getServicioTratamiento() {
		return servicioTratamiento;
	}
	public void setServicioTratamiento(ServicioTratamiento servicioTratamiento) {
		this.servicioTratamiento = servicioTratamiento;
	}
	public ServicioTipoTratamiento getServicioTipoTratamiento() {
		return servicioTipoTratamiento;
	}
	public void setServicioTipoTratamiento(
			ServicioTipoTratamiento servicioTipoTratamiento) {
		this.servicioTipoTratamiento = servicioTipoTratamiento;
	}
	public ctrlWinTratamiento getCtrlwintratamiento() {
		return ctrlwintratamiento;
	}
	public void setCtrlwintratamiento(ctrlWinTratamiento ctrlwintratamiento) {
		this.ctrlwintratamiento = ctrlwintratamiento;
	}
	public int getIndexTipoTratamiento() {
		return indexTipoTratamiento;
	}
	public void setIndexTipoTratamiento(int indexTipoTratamiento) {
		this.indexTipoTratamiento = indexTipoTratamiento;
	}
	public List<TipoTratamiento> getTipoTratamientos() {
		return tipoTratamientos;
	}
	public void setTipoTratamientos(List<TipoTratamiento> tipoTratamientos) {
		this.tipoTratamientos = tipoTratamientos;
	}
	public Tratamiento getTratamiento() {
		return tratamiento;
	}
	public void setTratamiento(Tratamiento tratamiento) {
		this.tratamiento = tratamiento;
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditTratamiento.setAttribute(comp.getId() + "ctrl", this);
		servicioTratamiento = (ServicioTratamiento) SpringUtil
				.getBean("beanServicioTratamiento");
		servicioTipoTratamiento = (ServicioTipoTratamiento) SpringUtil
				.getBean("beanServicioTipoTratamiento");
		tratamiento = new Tratamiento();
		tipoTratamientos = servicioTipoTratamiento.buscarTodos();
		ctrlwintratamiento = (ctrlWinTratamiento) arg.get("ctrlWinTratamiento");
		if (!(arg.get("objeto") == null)) {
			tratamiento = (Tratamiento) arg.get("objeto");
			indexTipoTratamiento = tipoTratamientos.indexOf(tratamiento.getTipoTratamiento());
		}
	}

	public void onClick$btnCancelar() {
		ctrlwintratamiento.apagarBotones();
		ctrlwintratamiento.recargar();
		this.winEditTratamiento.detach();
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
		} else if (listTipotratamiento.getSelectedIndex() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nTipoTratamiento",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listTipotratamiento.setFocus(true);
						}
					});
		} else {
			Tratamiento tratamientoTemp = servicioTratamiento.buscarUno(tratamiento.getNombre());
			if (tratamientoTemp != null) {
				tratamiento.setId(tratamientoTemp.getId());
			}
			tratamiento.setTipoTratamiento((TipoTratamiento) listTipotratamiento.getSelectedItem().getValue());
			try {
				servicioTratamiento.guardarTratamiento(tratamiento);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwintratamiento.recargar();
						ctrlwintratamiento.apagarBotones();
						winEditTratamiento.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditTratamiento() {
		ctrlwintratamiento.apagarBotones();
		this.winEditTratamiento.detach();
	}

	public void onAfterRender$listTipoTratamiento() {
		listTipotratamiento.setSelectedIndex(indexTipoTratamiento);
	}

}

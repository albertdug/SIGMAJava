/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.List;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Servicio;
import org.ucla.sigma.modelo.TipoServicio;
import org.ucla.sigma.servicio.ServicioServicio;
import org.ucla.sigma.servicio.ServicioTipoServicio;
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
public class ctrlWinEditServicio extends GenericForwardComposer {

	private Window winEditServicio;
	private Button btnCancelar;
	private Button btnGuardar;
	private Listbox listTipoServicio;
	private Textbox txtDescripcion;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioServicio servicioServicio;
	private ServicioTipoServicio servicioTipoServicio;
	private ctrlWinServicio ctrlWinServicio;
	private int indexTipoServicio = -1;

	// ----------------------------------------------------------------------------------------------------

	private List<TipoServicio> tipoServicios = new ArrayList<TipoServicio>();
	private Servicio servicio;

	// ----------------------------------------------------------------------------------------------------

	public Window getWinEditServicio() {
		return winEditServicio;
	}

	public void setWinEditServicio(Window winEditServicio) {
		this.winEditServicio = winEditServicio;
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

	public Listbox getListTipoServicio() {
		return listTipoServicio;
	}

	public void setListTipoServicio(Listbox listTipoServicio) {
		this.listTipoServicio = listTipoServicio;
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

	
	
	public ServicioServicio getServicioServicio() {
		return servicioServicio;
	}

	public void setServicioServicio(ServicioServicio servicioServicio) {
		this.servicioServicio = servicioServicio;
	}

	public ServicioTipoServicio getServicioTipoServicio() {
		return servicioTipoServicio;
	}

	public void setServicioTipoServicio(ServicioTipoServicio servicioTipoServicio) {
		this.servicioTipoServicio = servicioTipoServicio;
	}

	public ctrlWinServicio getCtrlWinServicio() {
		return ctrlWinServicio;
	}

	public void setCtrlWinServicio(ctrlWinServicio ctrlWinServicio) {
		this.ctrlWinServicio = ctrlWinServicio;
	}

	public int getIndexTipoServicio() {
		return indexTipoServicio;
	}

	public void setIndexTipoServicio(int indexTipoServicio) {
		this.indexTipoServicio = indexTipoServicio;
	}

	public List<TipoServicio> getTipoServicios() {
		return tipoServicios;
	}

	public void setTipoServicios(List<TipoServicio> tipoServicios) {
		this.tipoServicios = tipoServicios;
	}

	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditServicio.setAttribute(comp.getId() + "ctrl", this);
		servicioServicio = (ServicioServicio) SpringUtil.getBean("beanServicioServicio");
		servicioTipoServicio = (ServicioTipoServicio) SpringUtil
				.getBean("beanServicioTipoServicio");
		servicio = new Servicio();
		tipoServicios = servicioTipoServicio.buscarTodos();
		ctrlWinServicio = (ctrlWinServicio) arg.get("ctrlWinServicio");
		if (!(arg.get("objeto") == null)) {
			servicio = (Servicio) arg.get("objeto");
			indexTipoServicio = tipoServicios.indexOf(servicio.getTipoServicio());
		}
	}

	public void onClick$btnCancelar() {
		ctrlWinServicio.apagarBotones();
		this.winEditServicio.detach();
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
		} else if (listTipoServicio.getSelectedIndex() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nTipo Servicio",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listTipoServicio.setFocus(true);
						}
					});
		} else {
			Servicio servicioTemp = servicioServicio.buscarUno(servicio.getNombre());
			if (servicioTemp != null) {
				servicio.setId(servicioTemp.getId());
			}
			servicio.setTipoServicio((TipoServicio) listTipoServicio.getSelectedItem().getValue());
			try {
				servicioServicio.guardarServicio(servicio);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlWinServicio.recargar();
						ctrlWinServicio.apagarBotones();
						winEditServicio.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditServicio() {
		ctrlWinServicio.apagarBotones();
		this.winEditServicio.detach();
	}

	public void onAfterRender$listTipoServicio() {
		listTipoServicio.setSelectedIndex(indexTipoServicio);
	}
}

/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.List;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.TipoPatologia;
import org.ucla.sigma.modelo.Patologia;
import org.ucla.sigma.modelo.Patologia;
import org.ucla.sigma.modelo.TipoPatologia;
import org.ucla.sigma.servicio.ServicioTipoPatologia;
import org.ucla.sigma.servicio.ServicioPatologia;
import org.ucla.sigma.servicio.ServicioPatologia;
import org.ucla.sigma.servicio.ServicioTipoPatologia;
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
public class ctrlWinEditPatologia extends GenericForwardComposer {

	private Window winEditPatologia;
	private Button btnCancelar;
	private Button btnGuardar;
	private Listbox listTipoPatologia;
	private Textbox txtNombrePatologia;

	// ----------------------------------------------------------------------------------------------------

	private ServicioPatologia servicioPatologia;
	private ctrlWinPatologia ctrlwinpatologia;
	private ServicioTipoPatologia servicioTipoPatologia;
	private int indexTipoPatologia = -1;

	// ----------------------------------------------------------------------------------------------------

	private List<TipoPatologia> tipoPatologias = new ArrayList<TipoPatologia>();
	private Patologia patologia;

	public Window getWinEditPatologia() {
		return winEditPatologia;
	}

	public void setWinEditPatologia(Window winEditPatologia) {
		this.winEditPatologia = winEditPatologia;
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

	public Listbox getListTipoPatologia() {
		return listTipoPatologia;
	}

	public void setListTipoPatologia(Listbox listTipoPatologia) {
		this.listTipoPatologia = listTipoPatologia;
	}

	public Textbox getTxtNombrePatologia() {
		return txtNombrePatologia;
	}

	public void setTxtNombrePatologia(Textbox txtNombrePatologia) {
		this.txtNombrePatologia = txtNombrePatologia;
	}

	public ServicioPatologia getServicioPatologia() {
		return servicioPatologia;
	}

	public void setServicioPatologia(ServicioPatologia servicioPatologia) {
		this.servicioPatologia = servicioPatologia;
	}

	public ctrlWinPatologia getCtrlwinpatologia() {
		return ctrlwinpatologia;
	}

	public void setCtrlwinpatologia(ctrlWinPatologia ctrlwinpatologia) {
		this.ctrlwinpatologia = ctrlwinpatologia;
	}

	public ServicioTipoPatologia getServicioTipoPatologia() {
		return servicioTipoPatologia;
	}

	public void setServicioTipoPatologia(
			ServicioTipoPatologia servicioTipoPatologia) {
		this.servicioTipoPatologia = servicioTipoPatologia;
	}

	public int getIndexTipoPatologia() {
		return indexTipoPatologia;
	}

	public void setIndexTipoPatologia(int indexTipoPatologia) {
		this.indexTipoPatologia = indexTipoPatologia;
	}

	public List<TipoPatologia> getTipoPatologias() {
		return tipoPatologias;
	}

	public void setTipoPatologias(List<TipoPatologia> tipoPatologias) {
		this.tipoPatologias = tipoPatologias;
	}

	public Patologia getPatologia() {
		return patologia;
	}

	public void setPatologia(Patologia patologia) {
		this.patologia = patologia;
	}

	// ----------------------------------------------------------------------------------------------------

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditPatologia.setAttribute(comp.getId() + "ctrl", this);
		servicioPatologia = (ServicioPatologia) SpringUtil
				.getBean("beanServicioPatologia");
		servicioTipoPatologia = (ServicioTipoPatologia) SpringUtil
				.getBean("beanServicioTipoPatologia");
		patologia = new Patologia();
		tipoPatologias = servicioTipoPatologia.buscarTodos();
		ctrlwinpatologia = (ctrlWinPatologia) arg.get("ctrlWinPatologia");
		if (!(arg.get("objeto") == null)) {
			patologia = (Patologia) arg.get("objeto");
			indexTipoPatologia = tipoPatologias.indexOf(patologia
					.getTipoPatologia());
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinpatologia.recargar();
		ctrlwinpatologia.apagarBotones();
		winEditPatologia.detach();
	}

	public void onClick$btnGuardar() {

		if (txtNombrePatologia.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nNombre",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtNombrePatologia.setFocus(true);
						}
					});
		} else if (listTipoPatologia.getSelectedIndex() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nTipoPatologia",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listTipoPatologia.setFocus(true);
						}
					});
		} else {
			TipoPatologia especieTemp = servicioTipoPatologia
					.buscarUno(patologia.getNombre());
			if (especieTemp != null) {
				patologia.setId(especieTemp.getId());
			}
			patologia.setTipoPatologia((TipoPatologia) listTipoPatologia
					.getSelectedItem().getValue());
			try {
				servicioPatologia.guardarPatologia(patologia);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinpatologia.recargar();
						ctrlwinpatologia.apagarBotones();
						winEditPatologia.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditPatologia() {
		ctrlwinpatologia.recargar();
		ctrlwinpatologia.apagarBotones();
		winEditPatologia.detach();
	}

	public void onAfterRender$listTipoPatologia() {
		listTipoPatologia.setSelectedIndex(indexTipoPatologia);
	}

}

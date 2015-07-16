/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.List;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Examen;
import org.ucla.sigma.modelo.TipoExamen;
import org.ucla.sigma.servicio.ServicioExamen;
import org.ucla.sigma.servicio.ServicioTipoExamen;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author Albert
 * 
 */
public class ctrlWinEditExamen extends GenericForwardComposer {

	private Window winEditExamen;
	private Button btnCancelar;
	private Button btnGuardar;
	private Listbox listTipoExamen;
	private Textbox txtNombreExamen;

	// ----------------------------------------------------------------------------------------------------

	private ServicioExamen servicioExamen;
	private ctrlWinExamen ctrlwinexamen;
	private ServicioTipoExamen servicioTipoExamen;
	private int indexTipoExamen = -1;

	// ----------------------------------------------------------------------------------------------------

	private List<TipoExamen> tipoExamenes = new ArrayList<TipoExamen>();
	private Examen examen;

	public Window getWinEditExamen() {
		return winEditExamen;
	}

	public void setWinEditExamen(Window winEditExamen) {
		this.winEditExamen = winEditExamen;
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

	public Listbox getListTipoExamen() {
		return listTipoExamen;
	}

	public void setListTipoExamen(Listbox listTipoExamen) {
		this.listTipoExamen = listTipoExamen;
	}

	public Textbox getTxtNombreExamen() {
		return txtNombreExamen;
	}

	public void setTxtNombreExamen(Textbox txtNombreExamen) {
		this.txtNombreExamen = txtNombreExamen;
	}

	public ServicioExamen getServicioExamen() {
		return servicioExamen;
	}

	public void setServicioExamen(ServicioExamen servicioExamen) {
		this.servicioExamen = servicioExamen;
	}

	public ctrlWinExamen getCtrlwinexamen() {
		return ctrlwinexamen;
	}

	public void setCtrlwinexamen(ctrlWinExamen ctrlwinexamen) {
		this.ctrlwinexamen = ctrlwinexamen;
	}

	public ServicioTipoExamen getServicioTipoExamen() {
		return servicioTipoExamen;
	}

	public void setServicioTipoExamen(ServicioTipoExamen servicioTipoExamen) {
		this.servicioTipoExamen = servicioTipoExamen;
	}

	public int getIndexTipoExamen() {
		return indexTipoExamen;
	}

	public void setIndexTipoExamen(int indexTipoExamen) {
		this.indexTipoExamen = indexTipoExamen;
	}

	public List<TipoExamen> getTipoExamenes() {
		return tipoExamenes;
	}

	public void setTipoExamenes(List<TipoExamen> tipoExamenes) {
		this.tipoExamenes = tipoExamenes;
	}

	public Examen getExamen() {
		return examen;
	}

	public void setExamen(Examen examen) {
		this.examen = examen;
	}

	// ----------------------------------------------------------------------------------------------------

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditExamen.setAttribute(comp.getId() + "ctrl", this);
		servicioExamen = (ServicioExamen) SpringUtil
				.getBean("beanServicioExamen");
		servicioTipoExamen = (ServicioTipoExamen) SpringUtil
				.getBean("beanServicioTipoExamen");
		examen = new Examen();
		tipoExamenes = servicioTipoExamen.buscarTodos();
		ctrlwinexamen = (ctrlWinExamen) arg.get("ctrlWinExamen");
		if (!(arg.get("objeto") == null)) {
			examen = (Examen) arg.get("objeto");
			indexTipoExamen = tipoExamenes.indexOf(examen.getTipoExamen());
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinexamen.recargar();
		ctrlwinexamen.apagarBotones();
		winEditExamen.detach();
	}

	public void onClick$btnGuardar() {

		if (txtNombreExamen.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nNombre",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtNombreExamen.setFocus(true);
						}
					});
		} else if (listTipoExamen.getSelectedIndex() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nTipoExamen",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listTipoExamen.setFocus(true);
						}
					});
		} else {
			TipoExamen especieTemp = servicioTipoExamen.buscarUno(examen
					.getNombre());
			if (especieTemp != null) {
				examen.setId(especieTemp.getId());
			}
			examen.setTipoExamen((TipoExamen) listTipoExamen.getSelectedItem()
					.getValue());
			try {
				servicioExamen.guardarExamen(examen);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinexamen.recargar();
						ctrlwinexamen.apagarBotones();
						winEditExamen.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditExamen() {
		ctrlwinexamen.recargar();
		ctrlwinexamen.apagarBotones();
		winEditExamen.detach();
	}

	public void onAfterRender$listTipoExamen() {
		listTipoExamen.setSelectedIndex(indexTipoExamen);
	}

}
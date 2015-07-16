package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.List;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Raza;
import org.ucla.sigma.modelo.Especie;
import org.ucla.sigma.servicio.ServicioRaza;
import org.ucla.sigma.servicio.ServicioEspecie;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEditRaza extends GenericForwardComposer {

	private Window winEditRaza;
	private Button btnCancelar;
	private Button btnGuardar;
	private Listbox listEspecies;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioRaza servicioRaza;
	private ServicioEspecie servicioEspecie;
	private ctrlWinRaza ctrlwinraza;
	private int indexEspecie = -1;

	// ----------------------------------------------------------------------------------------------------

	private List<Especie> especies = new ArrayList<Especie>();
	private Raza raza;

	// ----------------------------------------------------------------------------------------------------

	public Window getWinEditRaza() {
		return winEditRaza;
	}

	public void setWinEditRaza(Window winEditRaza) {
		this.winEditRaza = winEditRaza;
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

	public Listbox getListEspecies() {
		return listEspecies;
	}

	public void setListEspecies(Listbox listEspecies) {
		this.listEspecies = listEspecies;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public List<Especie> getEspecies() {
		return especies;
	}

	public void setEspecies(List<Especie> especies) {
		this.especies = especies;
	}

	public Raza getRaza() {
		return raza;
	}

	public void setRaza(Raza raza) {
		this.raza = raza;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditRaza.setAttribute(comp.getId() + "ctrl", this);
		servicioRaza = (ServicioRaza) SpringUtil.getBean("beanServicioRaza");
		servicioEspecie = (ServicioEspecie) SpringUtil
				.getBean("beanServicioEspecie");
		raza = new Raza();
		especies = servicioEspecie.buscarTodos();
		ctrlwinraza = (ctrlWinRaza) arg.get("ctrlWinRaza");
		if (!(arg.get("objeto") == null)) {
			raza = (Raza) arg.get("objeto");
			indexEspecie = especies.indexOf(raza.getEspecie());
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinraza.recargar();
		ctrlwinraza.apagarBotones();
		this.winEditRaza.detach();
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
		} else if (listEspecies.getSelectedIndex() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nEspecie",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listEspecies.setFocus(true);
						}
					});
		} else {
			Especie especieTemp = servicioEspecie.buscarUno(raza.getNombre());
			if (especieTemp != null) {
				raza.setId(especieTemp.getId());
			}
			raza.setEspecie((Especie) listEspecies.getSelectedItem().getValue());
			try {
				servicioRaza.guardarRaza(raza);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinraza.recargar();
						ctrlwinraza.apagarBotones();
						winEditRaza.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditRaza() {
		ctrlwinraza.apagarBotones();
		this.winEditRaza.detach();
	}

	public void onAfterRender$listEspecies() {
		listEspecies.setSelectedIndex(indexEspecie);
	}

}

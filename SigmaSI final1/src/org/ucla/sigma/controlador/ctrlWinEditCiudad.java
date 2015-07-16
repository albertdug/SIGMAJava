package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.List;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.Estado;
import org.ucla.sigma.servicio.ServicioCiudad;
import org.ucla.sigma.servicio.ServicioEstado;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEditCiudad extends GenericForwardComposer {

	private Window winEditCiudad;
	private Button btnCancelar;
	private Button btnGuardar;
	private Listbox listEstados;
	private Intbox intCodigo;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioCiudad servicioCiudad;
	private ServicioEstado servicioEstado;
	private ctrlWinCiudad ctrlwinciudad;
	private int indexEstado = -1;

	// ----------------------------------------------------------------------------------------------------

	private List<Estado> estados = new ArrayList<Estado>();
	private Ciudad ciudad;

	// ----------------------------------------------------------------------------------------------------

	public Window getWinEditCiudad() {
		return winEditCiudad;
	}

	public void setWinEditCiudad(Window winEditCiudad) {
		this.winEditCiudad = winEditCiudad;
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

	public Listbox getListEstados() {
		return listEstados;
	}

	public void setListEstados(Listbox listEstados) {
		this.listEstados = listEstados;
	}

	public Intbox getIntCodigo() {
		return intCodigo;
	}

	public void setIntCodigo(Intbox intCodigo) {
		this.intCodigo = intCodigo;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditCiudad.setAttribute(comp.getId() + "ctrl", this);
		servicioCiudad = (ServicioCiudad) SpringUtil
				.getBean("beanServicioCiudad");
		servicioEstado = (ServicioEstado) SpringUtil
				.getBean("beanServicioEstado");
		ciudad = new Ciudad();
		estados = servicioEstado.buscarTodos();
		ctrlwinciudad = (ctrlWinCiudad) arg.get("ctrlWinCiudad");
		if (!(arg.get("objeto") == null)) {
			ciudad = (Ciudad) arg.get("objeto");
			indexEstado = estados.indexOf(ciudad.getEstado());
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinciudad.recargar();
		ctrlwinciudad.apagarBotones();
		this.winEditCiudad.detach();
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
		} else if (listEstados.getSelectedIndex() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nEstado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listEstados.setFocus(true);
						}
					});
		} else {
			Ciudad ciudadTemp = servicioCiudad.buscarUno(ciudad.getNombre());
			if (ciudadTemp != null) {
				ciudad.setId(ciudadTemp.getId());
			}
			ciudad.setEstado((Estado) listEstados.getSelectedItem().getValue());
			try {
				servicioCiudad.guardarCiudad(ciudad);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinciudad.recargar();
						ctrlwinciudad.apagarBotones();
						winEditCiudad.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditCiudad() {
		ctrlwinciudad.apagarBotones();
		this.winEditCiudad.detach();
	}

	public void onAfterRender$listEstados() {
		listEstados.setSelectedIndex(indexEstado);
	}

}

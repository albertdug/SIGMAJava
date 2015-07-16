/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.List;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.Estado;
import org.ucla.sigma.modelo.Persona;
import org.ucla.sigma.modelo.Veterinario;
import org.ucla.sigma.modelo.Veterinario;
import org.ucla.sigma.modelo.Recepcionista;
import org.ucla.sigma.modelo.Responsable;
import org.ucla.sigma.modelo.Veterinario;
import org.ucla.sigma.servicio.ServicioCiudad;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioPersona;
import org.ucla.sigma.servicio.ServicioVeterinario;
import org.ucla.sigma.servicio.ServicioVeterinario;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author rafael
 *
 */
public class ctrlWinEditVeterinario extends GenericForwardComposer {

	private Window winEditVeterinario;
	private Button btnCancelar;
	private Button btnGuardar;
	private Button btnBuscar;
	private Textbox txtRif;
	private Textbox txtColegio;
	private Textbox txtSanidad;
	private Textbox txtCorreo;
	private Textbox txtTelefono;
	private Textbox txtDireccion;
	private Listbox listCiudads;
	private Listbox listEstados;
	private Textbox txtApellido;
	private Textbox txtNombre;
	private Intbox txtCedula;
	private AnnotateDataBinder binder;

	// ----------------------------------------------------------------------------------------------------

	private ServicioCiudad servicioCiudad;
	private ServicioEstado servicioEstado;
	private ServicioVeterinario servicioVeterinario;
	private ServicioPersona servicioPersona;
	private List<Ciudad> ciudads = new ArrayList<Ciudad>();
	private List<Estado> estados = new ArrayList<Estado>();
	private Persona persona;
	private Veterinario veterinario;
	private ctrlWinVeterinario ctrlwinVeterinario;
	private int indexCiudad = -1;
	private int indexEstado = -1;

	
	// ----------------------------------------------------------------------------------------------------

	public Window getWinEditVeterinario() {
		return winEditVeterinario;
	}

	public void setWinEditVeterinario(Window winEditVeterinario) {
		this.winEditVeterinario = winEditVeterinario;
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

	public Textbox getTxtRif() {
		return txtRif;
	}

	public void setTxtRif(Textbox txtRif) {
		this.txtRif = txtRif;
	}

	public Textbox getTxtColegio() {
		return txtColegio;
	}

	public void setTxtColegio(Textbox txtColegio) {
		this.txtColegio = txtColegio;
	}

	public Textbox getTxtSanidad() {
		return txtSanidad;
	}

	public void setTxtSanidad(Textbox txtSanidad) {
		this.txtSanidad = txtSanidad;
	}	

	public Textbox getTxtCorreo() {
		return txtCorreo;
	}

	public void setTxtCorreo(Textbox txtCorreo) {
		this.txtCorreo = txtCorreo;
	}

	public Textbox getTxtTelefono() {
		return txtTelefono;
	}

	public void setTxtTelefono(Textbox txtTelefono) {
		this.txtTelefono = txtTelefono;
	}

	public Textbox getTxtDireccion() {
		return txtDireccion;
	}

	public void setTxtDireccion(Textbox txtDireccion) {
		this.txtDireccion = txtDireccion;
	}

	public Listbox getListCiudads() {
		return listCiudads;
	}

	public void setListCiudads(Listbox listCiudads) {
		this.listCiudads = listCiudads;
	}

	public Listbox getListEstados() {
		return listEstados;
	}

	public void setListEstados(Listbox listEstados) {
		this.listEstados = listEstados;
	}

	public Textbox getTxtApellido() {
		return txtApellido;
	}

	public void setTxtApellido(Textbox txtApellido) {
		this.txtApellido = txtApellido;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}
	
	public ServicioCiudad getServicioCiudad() {
		return servicioCiudad;
	}

	public void setServicioCiudad(ServicioCiudad servicioCiudad) {
		this.servicioCiudad = servicioCiudad;
	}

	public ServicioEstado getServicioEstado() {
		return servicioEstado;
	}

	public void setServicioEstado(ServicioEstado servicioEstado) {
		this.servicioEstado = servicioEstado;
	}

	public ServicioVeterinario getServicioVeterinario() {
		return servicioVeterinario;
	}

	public void setServicioVeterinario(ServicioVeterinario servicioVeterinario) {
		this.servicioVeterinario = servicioVeterinario;
	}

	public List<Ciudad> getCiudads() {
		return ciudads;
	}

	public void setCiudads(List<Ciudad> ciudads) {
		this.ciudads = ciudads;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Veterinario getVeterinario() {
		return veterinario;
	}

	public void setVeterinario(Veterinario veterinario) {
		this.veterinario = veterinario;
	}

	public ctrlWinVeterinario getCtrlwinVeterinario() {
		return ctrlwinVeterinario;
	}

	public void setCtrlwinVeterinario(ctrlWinVeterinario ctrlwinVeterinario) {
		this.ctrlwinVeterinario = ctrlwinVeterinario;
	}

	public int getIndexCiudad() {
		return indexCiudad;
	}

	public void setIndexCiudad(int indexCiudad) {
		this.indexCiudad = indexCiudad;
	}

	public int getIndexEstado() {
		return indexEstado;
	}

	public void setIndexEstado(int indexEstado) {
		this.indexEstado = indexEstado;
	}
	
	public ServicioPersona getServicioPersona() {
		return servicioPersona;
	}

	public void setServicioPersona(ServicioPersona servicioPersona) {
		this.servicioPersona = servicioPersona;
	}

	/**
	 *
	 *
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditVeterinario.setAttribute(comp.getId() + "ctrl", this);
		servicioVeterinario = (ServicioVeterinario) SpringUtil
				.getBean("beanServicioVeterinario");
		servicioPersona = (ServicioPersona) SpringUtil
				.getBean("beanServicioPersona");
		servicioCiudad = (ServicioCiudad) SpringUtil
				.getBean("beanServicioCiudad");
		servicioEstado = (ServicioEstado) SpringUtil
				.getBean("beanServicioEstado");
		persona = new Persona();
		veterinario = new Veterinario();
		estados = servicioEstado.buscarTodos();
		ciudads = servicioCiudad.buscarTodos();
		listCiudads.setDisabled(true);
		ctrlwinVeterinario = (ctrlWinVeterinario) arg.get("ctrlWinVeterinario");
		if (!(arg.get("objeto") == null)) {
			veterinario = (Veterinario) arg.get("objeto");
			persona= veterinario.getPersona();
			indexCiudad = ciudads.indexOf(veterinario.getPersona().getCiudad());
			indexEstado = estados.indexOf(veterinario.getPersona().getCiudad().getEstado());
			txtCedula.setValue(persona.getCedula());
			txtCedula.setReadonly(true);
			activarCampos();
			btnBuscar.setVisible(false);
			
		}

	}

	public void onClick$btnCancelar() {
		ctrlwinVeterinario.apagarBotones();
		ctrlwinVeterinario.recargar();
		this.winEditVeterinario.detach();
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
		} else if (txtCedula.getValue() == null) {
			MensajeEmergente.mostrar("NOEMPTY", "\nCedula",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtCedula.setFocus(true);
						}
					});
		} else if (txtApellido.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nApellido",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtApellido.setFocus(true);
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
		} else if (listCiudads.getSelectedIndex() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nCiudad",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listCiudads.setFocus(true);
						}
					});
		} else if (txtDireccion.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nDireccion",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtDireccion.setFocus(true);
						}
					});
		} else if (txtTelefono.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nTelefono",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtTelefono.setFocus(true);
						}
					});
		} else if (txtCorreo.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nCorreo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtCorreo.setFocus(true);
						}
					});
		} else if (txtColegio.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nColegio",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtColegio.setFocus(true);
						}
					});
		} else if (txtSanidad.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nSanidad",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtSanidad.setFocus(true);
						}
					});
		} else if (txtRif.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nRif",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtRif.setFocus(true);
						}
					});
		} else {
			try {
				persona.setCedula(txtCedula.getValue());
				persona.setCiudad((Ciudad) listCiudads.getSelectedItem()
						.getValue());
				veterinario.setPersona(persona);
				servicioPersona.guardarPersona(persona);
				servicioVeterinario.guardarVeterinario(veterinario);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinVeterinario.recargar();
						ctrlwinVeterinario.apagarBotones();
						winEditVeterinario.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}
		}
	}

	public void onClose$winEditVeterinario() {
		ctrlwinVeterinario.apagarBotones();
		this.winEditVeterinario.detach();
	}

	public void onAfterRender$listEstados() {
		listEstados.setSelectedIndex(indexEstado);
		listCiudads.setSelectedIndex(indexCiudad);
	}

	public void onSelect$listEstados() {
		ciudads = servicioCiudad.buscarEstadosAsociados((Estado) listEstados
				.getSelectedItem().getValue(), 'A');
		listCiudads.setModel(new BindingListModelList(ciudads, false));
		listCiudads.setDisabled(false);
	}
	
	public void activarCampos() {
		txtApellido.setReadonly(false);
		txtColegio.setReadonly(false);
		txtCorreo.setReadonly(false);
		txtDireccion.setReadonly(false);
		txtNombre.setReadonly(false);
		txtRif.setReadonly(false);
		txtSanidad.setReadonly(false);
		txtTelefono.setReadonly(false);
		listCiudads.setDisabled(false);
		listEstados.setDisabled(false);
	}
	
	public void onClick$btnBuscar() {
		if (txtCedula.getValue() == null) {
			MensajeEmergente.mostrar("NOEMPTY", "\nCedula",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtCedula.setFocus(true);
						}
					});
		} else {
			persona = servicioPersona.buscarUno(txtCedula.getValue(), 'A');
			if (persona != null) {
				if (persona.getVeterinario() != null) {
					veterinario = persona.getVeterinario();
				}
				binder.loadAll();
				indexCiudad = ciudads.indexOf(persona.getCiudad());
				indexEstado = estados.indexOf(persona.getCiudad().getEstado());
				listEstados.setSelectedIndex(indexEstado);
				listCiudads.setSelectedIndex(indexCiudad);
				activarCampos();
			} else {
				MensajeEmergente.mostrar("NOTREGIS", new MensajeListener() {
					@Override
					public void alAceptar(){
						persona = new Persona();
						txtCedula.setReadonly(true);
						btnBuscar.setDisabled(true);
						activarCampos();
					}
					@Override
					public void alDestruir() {
						txtNombre.setFocus(true);
					}
				});
			}
		}
	}
	
}

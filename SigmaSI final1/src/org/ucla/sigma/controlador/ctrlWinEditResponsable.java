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
import org.ucla.sigma.modelo.Responsable;
import org.ucla.sigma.servicio.ServicioCiudad;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioPersona;
import org.ucla.sigma.servicio.ServicioResponsable;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author rafael
 * 
 */
public class ctrlWinEditResponsable extends GenericForwardComposer {

	private Window winEditResponsable;
	private Button btnCancelar;
	private Button btnGuardar;
	private Button btnBuscar;
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
	private ServicioResponsable servicioResponsable;
	private ServicioPersona servicioPersona;
	private List<Ciudad> ciudads = new ArrayList<Ciudad>();
	private List<Estado> estados = new ArrayList<Estado>();
	private Persona persona;
	private Responsable responsable;
	private ctrlWinResponsable ctrlwinResponsable;
	private ctrlWinFichaBasica ctrlwinFichaBasica;
	private int indexCiudad = -1;
	private int indexEstado = -1;

	// ----------------------------------------------------------------------------------------------------

	public Window getWinEditResponsable() {
		return winEditResponsable;
	}

	public void setWinEditResponsable(Window winEditResponsable) {
		this.winEditResponsable = winEditResponsable;
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

	public Intbox getTxtCedula() {
		return txtCedula;
	}

	public void setTxtCedula(Intbox txtCedula) {
		this.txtCedula = txtCedula;
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

	public ServicioResponsable getServicioResponsable() {
		return servicioResponsable;
	}

	public void setServicioResponsable(ServicioResponsable servicioResponsable) {
		this.servicioResponsable = servicioResponsable;
	}

	public ServicioPersona getServicioPersona() {
		return servicioPersona;
	}

	public void setServicioPersona(ServicioPersona servicioPersona) {
		this.servicioPersona = servicioPersona;
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

	public Responsable getResponsable() {
		return responsable;
	}

	public void setResponsable(Responsable responsable) {
		this.responsable = responsable;
	}

	public ctrlWinResponsable getCtrlwinResponsable() {
		return ctrlwinResponsable;
	}

	public void setCtrlwinResponsable(ctrlWinResponsable ctrlwinResponsable) {
		this.ctrlwinResponsable = ctrlwinResponsable;
	}

	public ctrlWinFichaBasica getCtrlwinFichaBasica() {
		return ctrlwinFichaBasica;
	}

	public void setCtrlwinFichaBasica(ctrlWinFichaBasica ctrlwinFichaBasica) {
		this.ctrlwinFichaBasica = ctrlwinFichaBasica;
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

	/**
	 *
	 *
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditResponsable.setAttribute(comp.getId() + "ctrl", this);
		servicioResponsable = (ServicioResponsable) SpringUtil
				.getBean("beanServicioResponsable");
		servicioPersona = (ServicioPersona) SpringUtil
				.getBean("beanServicioPersona");
		servicioCiudad = (ServicioCiudad) SpringUtil
				.getBean("beanServicioCiudad");
		servicioEstado = (ServicioEstado) SpringUtil
				.getBean("beanServicioEstado");
		persona = new Persona();
		responsable = new Responsable();
		estados = servicioEstado.buscarTodos();
		ciudads = servicioCiudad.buscarTodos();
		listCiudads.setDisabled(true);

		if (!(arg.get("ctrlWinFichaBasica") == null)) {
			ctrlwinFichaBasica = (ctrlWinFichaBasica) arg
					.get("ctrlWinFichaBasica");
			persona.setCedula(ctrlwinFichaBasica.getTxtCedulaResponsable()
					.getValue());
			txtCedula.setReadonly(true);
			txtCedula.setValue(ctrlwinFichaBasica.getTxtCedulaResponsable().getValue());
			activarCampos();
			btnBuscar.setVisible(false);
		}

		if (!(arg.get("ctrlWinResponsable") == null)) {
			ctrlwinResponsable = (ctrlWinResponsable) arg
					.get("ctrlWinResponsable");
			if (!(arg.get("objeto") == null)) {
				responsable = (Responsable) arg.get("objeto");
				persona = responsable.getPersona();
				indexCiudad = ciudads.indexOf(responsable.getPersona()
						.getCiudad());
				indexEstado = estados.indexOf(responsable.getPersona()
						.getCiudad().getEstado());
				txtCedula.setValue(persona.getCedula());
				btnBuscar.setDisabled(true);
				txtCedula.setReadonly(true);
			}

		}

	}

	public void onClick$btnCancelar() {
		if (!(arg.get("ctrlWinResponsable") == null)) {
			ctrlwinResponsable.apagarBotones();
			ctrlwinResponsable.recargar();
		}
		this.winEditResponsable.detach();
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
		} else {
			try {
				persona.setCedula(txtCedula.getValue());
				persona.setCiudad((Ciudad) listCiudads.getSelectedItem()
						.getValue());
				responsable.setPersona(persona);
				servicioPersona.guardarPersona(persona);
				servicioResponsable.guardarResponsable(responsable);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						if (!(arg.get("ctrlWinResponsable") == null)) {
							ctrlwinResponsable.recargar();
							ctrlwinResponsable.apagarBotones();
						}else if (!(arg.get("ctrlWinFichaBasica") == null)) {
							ctrlwinFichaBasica.cargarResponsable(responsable);
							ctrlwinFichaBasica.deshabilitarCampos();
							ctrlwinFichaBasica.activar();
						}
						
						winEditResponsable.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}
		}
	}

	public void onClose$winEditResponsable() {
		if (!(arg.get("ctrlWinResponsable") == null)) {
			ctrlwinResponsable.apagarBotones();
		}
		this.winEditResponsable.detach();
	}

	public void onAfterRender$listEstados() {
		if (!(arg.get("ctrlWinResponsable") == null)) {
			listEstados.setSelectedIndex(indexEstado);
			listCiudads.setSelectedIndex(indexCiudad);
		}
	}

	public void onSelect$listEstados() {
		ciudads = servicioCiudad.buscarEstadosAsociados((Estado) listEstados
				.getSelectedItem().getValue(), 'A');
		listCiudads.setModel(new BindingListModelList(ciudads, false));
		listCiudads.setDisabled(false);
	}
	
	public void activarCampos() {
		txtApellido.setReadonly(false);
		txtCorreo.setReadonly(false);
		txtDireccion.setReadonly(false);
		txtNombre.setReadonly(false);
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
				if (persona.getResponsable() != null) {
					responsable = persona.getResponsable();
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

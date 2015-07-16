/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.Especie;
import org.ucla.sigma.modelo.Estado;
import org.ucla.sigma.modelo.Paciente;
import org.ucla.sigma.modelo.Raza;
import org.ucla.sigma.modelo.Responsable;
import org.ucla.sigma.modelo.Sexo;
import org.ucla.sigma.servicio.ServicioCiudad;
import org.ucla.sigma.servicio.ServicioEspecie;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioPaciente;
import org.ucla.sigma.servicio.ServicioRaza;
import org.ucla.sigma.servicio.ServicioResponsable;
import org.ucla.sigma.servicio.ServicioSexo;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author rafael
 * 
 */
public class ctrlWinEditPaciente extends GenericForwardComposer {

	private Window winEditPaciente;
	private Button btnCancelar;
	private Button btnGuardar;
	private Listbox listRaza;
	private Listbox listEspecie;
	private Listbox listSexo;
	private Datebox dbFechaNac;
	private Textbox txtNombre;
	private Textbox txtHistorial;
	private Textbox txtNombreResponsable;
	private Textbox txtApellidoResponsable;
	private Button btnBuscarResponsable;
	private Intbox txtCedulaResponsable;

	// ----------------------------------------------------------------------------------------------------

	private ServicioSexo servicioSexo;
	private ServicioEspecie servicioEspecie;
	private ServicioRaza servicioRaza;
	private ServicioPaciente servicioPaciente;
	private ServicioResponsable servicioResponsable;
	private List<Sexo> sexos = new ArrayList<Sexo>();
	private List<Especie> especies = new ArrayList<Especie>();
	private List<Raza> razas = new ArrayList<Raza>();
	private Paciente paciente;
	private Responsable responsable;
	private ctrlWinPaciente ctrlwinPaciente;
	private ctrlWinFichaBasica ctrlwinFichaBasica;
	private int indexRaza = -1;
	private int indexEspecie = -1;
	private int indexSexo = -1;

	// ----------------------------------------------------------------------------------------------------

	public Responsable getResponsable() {
		return responsable;
	}

	public Textbox getTxtApellidoResponsable() {
		return txtApellidoResponsable;
	}

	public void setTxtApellidoResponsable(Textbox txtApellidoResponsable) {
		this.txtApellidoResponsable = txtApellidoResponsable;
	}

	public void setResponsable(Responsable responsable) {
		this.responsable = responsable;
	}

	public Window getWinEditPaciente() {
		return winEditPaciente;
	}

	public void setWinEditPaciente(Window winEditPaciente) {
		this.winEditPaciente = winEditPaciente;
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

	public Listbox getListRaza() {
		return listRaza;
	}

	public void setListRaza(Listbox listRaza) {
		this.listRaza = listRaza;
	}

	public Listbox getListEspecie() {
		return listEspecie;
	}

	public void setListEspecie(Listbox listEspecie) {
		this.listEspecie = listEspecie;
	}

	public Listbox getListSexo() {
		return listSexo;
	}

	public void setListSexo(Listbox listSexo) {
		this.listSexo = listSexo;
	}

	public Datebox getDbFechaNac() {
		return dbFechaNac;
	}

	public void setDbFechaNac(Datebox dbFechaNac) {
		this.dbFechaNac = dbFechaNac;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public Textbox getTxtHistorial() {
		return txtHistorial;
	}

	public void setTxtHistorial(Textbox txtHistorial) {
		this.txtHistorial = txtHistorial;
	}

	public Textbox getTxtNombreResponsable() {
		return txtNombreResponsable;
	}

	public void setTxtNombreResponsable(Textbox txtNombreResponsable) {
		this.txtNombreResponsable = txtNombreResponsable;
	}

	public Button getBtnBuscarResponsable() {
		return btnBuscarResponsable;
	}

	public void setBtnBuscarResponsable(Button btnBuscarResponsable) {
		this.btnBuscarResponsable = btnBuscarResponsable;
	}

	public Intbox getTxtCedulaResponsable() {
		return txtCedulaResponsable;
	}

	public void setTxtCedulaResponsable(Intbox txtCedulaResponsable) {
		this.txtCedulaResponsable = txtCedulaResponsable;
	}

	public ServicioPaciente getServicioPaciente() {
		return servicioPaciente;
	}

	public void setServicioPaciente(ServicioPaciente servicioPaciente) {
		this.servicioPaciente = servicioPaciente;
	}

	public ServicioResponsable getServicioResponsable() {
		return servicioResponsable;
	}

	public void setServicioResponsable(ServicioResponsable servicioResponsable) {
		this.servicioResponsable = servicioResponsable;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public ctrlWinPaciente getCtrlwinPaciente() {
		return ctrlwinPaciente;
	}

	public void setCtrlwinPaciente(ctrlWinPaciente ctrlwinPaciente) {
		this.ctrlwinPaciente = ctrlwinPaciente;
	}

	public ServicioSexo getServicioSexo() {
		return servicioSexo;
	}

	public void setServicioSexo(ServicioSexo servicioSexo) {
		this.servicioSexo = servicioSexo;
	}

	public ServicioEspecie getServicioEspecie() {
		return servicioEspecie;
	}

	public void setServicioEspecie(ServicioEspecie servicioEspecie) {
		this.servicioEspecie = servicioEspecie;
	}

	public ServicioRaza getServicioRaza() {
		return servicioRaza;
	}

	public void setServicioRaza(ServicioRaza servicioRaza) {
		this.servicioRaza = servicioRaza;
	}

	public List<Sexo> getSexos() {
		return sexos;
	}

	public void setSexos(List<Sexo> sexos) {
		this.sexos = sexos;
	}

	public List<Especie> getEspecies() {
		return especies;
	}

	public void setEspecies(List<Especie> especies) {
		this.especies = especies;
	}

	public List<Raza> getRazas() {
		return razas;
	}

	public void setRazas(List<Raza> razas) {
		this.razas = razas;
	}

	public int getIndexRaza() {
		return indexRaza;
	}

	public void setIndexRaza(int indexRaza) {
		this.indexRaza = indexRaza;
	}

	public int getIndexEspecie() {
		return indexEspecie;
	}

	public void setIndexEspecie(int indexEspecie) {
		this.indexEspecie = indexEspecie;
	}

	public int getIndexSexo() {
		return indexSexo;
	}

	public void setIndexSexo(int indexSexo) {
		this.indexSexo = indexSexo;
	}

	public ctrlWinFichaBasica getCtrlwinFichaBasica() {
		return ctrlwinFichaBasica;
	}

	public void setCtrlwinFichaBasica(ctrlWinFichaBasica ctrlwinFichaBasica) {
		this.ctrlwinFichaBasica = ctrlwinFichaBasica;
	}

	/**
	 *
	 *
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditPaciente.setAttribute(comp.getId() + "ctrl", this);
		servicioPaciente = (ServicioPaciente) SpringUtil
				.getBean("beanServicioPaciente");
		servicioEspecie = (ServicioEspecie) SpringUtil
				.getBean("beanServicioEspecie");
		servicioSexo = (ServicioSexo) SpringUtil.getBean("beanServicioSexo");
		servicioRaza = (ServicioRaza) SpringUtil.getBean("beanServicioRaza");
		servicioResponsable = (ServicioResponsable) SpringUtil
				.getBean("beanServicioResponsable");
		paciente = new Paciente();
		responsable = new Responsable();
		razas = servicioRaza.buscarTodos('A');
		especies = servicioEspecie.buscarTodos('A');
		sexos = servicioSexo.buscarTodos('A');
		listRaza.setDisabled(true);

		// HISTORIA MEDICA
		Date creacion = HelperDate.now();
		paciente.setCreacion(creacion);
		// NO SINCRONIZADO
		int numero = servicioPaciente.countEnMes(HelperDate.format(creacion, "M"),HelperDate.format(creacion, "yyyy"));
		paciente.setHistoriaMedica("T-" + String.format("%03d", numero+1)	+ HelperDate.format(creacion, "-MM-yy"));
		// HISTORIA MEDICA

		if (!(arg.get("ctrlWinFichaBasica") == null)) {
			ctrlwinFichaBasica = (ctrlWinFichaBasica) arg
					.get("ctrlWinFichaBasica");
			responsable = ctrlwinFichaBasica.getResponsable();
			btnBuscarResponsable.setVisible(false);
			txtNombre.setFocus(true);
			if (!(arg.get("objeto") == null)) {
				paciente = (Paciente) arg.get("objeto");
				responsable = paciente.getResponsable();
				indexSexo = sexos.indexOf(paciente.getSexo());
				razas = servicioRaza.buscarEspeciesAsociados(paciente.getRaza()
						.getEspecie(), 'A');
				listRaza.setDisabled(false);
				indexRaza = razas.indexOf(paciente.getRaza());
				indexEspecie = especies
						.indexOf(paciente.getRaza().getEspecie());

			}
		}

		if (!(arg.get("ctrlWinPaciente") == null)) {
			ctrlwinPaciente = (ctrlWinPaciente) arg.get("ctrlWinPaciente");
			if (!(arg.get("objeto") == null)) {
				paciente = (Paciente) arg.get("objeto");
				responsable = paciente.getResponsable();
				indexSexo = sexos.indexOf(paciente.getSexo());
				razas = servicioRaza.buscarEspeciesAsociados(paciente.getRaza()
						.getEspecie(), 'A');
				listRaza.setDisabled(false);
				indexRaza = razas.indexOf(paciente.getRaza());
				indexEspecie = especies
						.indexOf(paciente.getRaza().getEspecie());
			}
		}
	}

	public void onClick$btnCancelar() {
		if (!(arg.get("ctrlWinPaciente") == null)) {
			ctrlwinPaciente.apagarBotones();
			ctrlwinPaciente.recargar();
		}
		this.winEditPaciente.detach();
	}

	public void onClick$btnGuardar() {

		if (txtNombreResponsable.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nNombre Responsable",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtNombreResponsable.setFocus(true);
						}
					});
		} else if (txtApellidoResponsable.getValue() == null) {
			MensajeEmergente.mostrar("NOEMPTY", "\nApellido Responsable",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtApellidoResponsable.setFocus(true);
						}
					});
		} else if (txtCedulaResponsable.getValue() == null) {
			MensajeEmergente.mostrar("NOEMPTY", "\nCedula Responsable",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtCedulaResponsable.setFocus(true);
						}
					});
		} else if (txtNombre.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nNombre",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtNombre.setFocus(true);
						}
					});
		} else if (dbFechaNac.getValue() == null) {
			MensajeEmergente.mostrar("NOEMPTY", "\ndbFechaNac",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							dbFechaNac.setFocus(true);
						}
					});
		} else if (dbFechaNac.getValue() == null) {
			MensajeEmergente.mostrar("NOEMPTY", "\ndbFechaNac",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							dbFechaNac.setFocus(true);
						}
					});
		} else if (listSexo.getSelectedIndex() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nSexo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listSexo.setFocus(true);
						}
					});
		} else if (listEspecie.getSelectedIndex() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nEspecie",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listEspecie.setFocus(true);
						}
					});
		} else if (listRaza.getSelectedIndex() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nRaza",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listRaza.setFocus(true);
						}
					});
		} else {
			Paciente pacienteTemp = servicioPaciente.buscarUno(paciente
					.getHistoriaMedica());
			if (pacienteTemp != null) {
				paciente.setHistoriaMedica(pacienteTemp.getHistoriaMedica());
			}
			try {
				paciente.setResponsable(responsable);
				paciente.setRaza((Raza) listRaza.getSelectedItem().getValue());
				paciente.setSexo((Sexo) listSexo.getSelectedItem().getValue());
				servicioPaciente.guardarPaciente(paciente);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						if (!(arg.get("ctrlWinPaciente") == null)) {
							ctrlwinPaciente.recargar();
							ctrlwinPaciente.apagarBotones();
						} else if (!(arg.get("ctrlWinFichaBasica") == null)) {
							ctrlwinFichaBasica.recargarLista();
							ctrlwinFichaBasica.getTxtCedulaResponsable()
									.setReadonly(true);
						}
						winEditPaciente.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}
		}
	}

	public void onClose$winEditPaciente() {
		if (!(arg.get("ctrlWinPaciente") == null)) {
			ctrlwinPaciente.apagarBotones();
		}
		this.winEditPaciente.detach();
	}

	public void onClick$btnBuscarResponsable() {
		if (txtCedulaResponsable.getValue() != null) {
			responsable = servicioResponsable.buscarUno(
					txtCedulaResponsable.getValue(), 'A');
			if (responsable != null) {
				txtNombreResponsable.setValue(responsable.getPersona()
						.getNombre());
				txtApellidoResponsable.setValue(responsable.getPersona()
						.getApellido());
			} else {
				MensajeEmergente.mostrar("RESNOFOUND", "\nCedula Responsable",
						new MensajeListener() {
							@Override
							public void alDestruir() {
								txtCedulaResponsable.setFocus(true);
							}
						});
				txtCedulaResponsable.setValue(null);
				txtNombreResponsable.setValue("");
			}
		} else {
			MensajeEmergente.mostrar("NOEMPTY", "\nCedula Responsable",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtCedulaResponsable.setFocus(true);
						}
					});
		}
	}

	public void onAfterRender$listSexo() {
		listSexo.setSelectedIndex(indexSexo);
	}

	public void onAfterRender$listEspecie() {
		listEspecie.setSelectedIndex(indexEspecie);
		listRaza.setSelectedIndex(indexRaza);
	}

	public void onSelect$listEspecie() {
		razas = servicioRaza.buscarEspeciesAsociados((Especie) listEspecie
				.getSelectedItem().getValue(), 'A');
		listRaza.setModel(new BindingListModelList(razas, false));
		// NUEVA LINEA
		indexRaza = -1;
		// NUEVA LINEA
		listRaza.setDisabled(false);
	}

}

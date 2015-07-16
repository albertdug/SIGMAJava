package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.ConsultaGeneral;
import org.ucla.sigma.modelo.Frecuencia;
import org.ucla.sigma.modelo.Ganglio;
import org.ucla.sigma.modelo.Paciente;
import org.ucla.sigma.modelo.Patologia;
import org.ucla.sigma.modelo.Servicio;
import org.ucla.sigma.modelo.Sintoma;
import org.ucla.sigma.modelo.TipoAlimentacion;
import org.ucla.sigma.modelo.TipoPatologia;
import org.ucla.sigma.modelo.TipoTratamiento;
import org.ucla.sigma.modelo.Tratamiento;
import org.ucla.sigma.modelo.Veterinario;
import org.ucla.sigma.servicio.ServicioConsultaGeneral;
import org.ucla.sigma.servicio.ServicioFrecuencia;
import org.ucla.sigma.servicio.ServicioGanglio;
import org.ucla.sigma.servicio.ServicioPatologia;
import org.ucla.sigma.servicio.ServicioServicio;
import org.ucla.sigma.servicio.ServicioSintoma;
import org.ucla.sigma.servicio.ServicioTipoAlimentacion;
import org.ucla.sigma.servicio.ServicioTipoPatologia;
import org.ucla.sigma.servicio.ServicioTipoTratamiento;
import org.ucla.sigma.servicio.ServicioTratamiento;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinConsultaGeneral extends GenericForwardComposer {

	// Variables -> Zul
	// ---------------------------------------------------------------------------

	private Window WinConsultaGeneral;
	private Textbox txtObservaciones;
	private Textbox txtTratamientoEnviado;
	private Textbox txtTratamientoAplicado;
	private Textbox txtTratamientoIndicaciones;
	private Textbox txtDiagnosticoDefinitivo;
	private Textbox txtDiagnosticoDiferencial;
	private Textbox txtDiagnosticoTentativo;
	private Textbox txtDiagnosticoProcedimiento;
	private Textbox txtPatologiaComentario;
	private Textbox txtServicioDescripcion;
	private Textbox txtCaracter;
	private Textbox txtPrepucio;
	private Textbox txtTesticulos;
	private Textbox txtSecrecionVaginal;
	private Textbox txtVejiga;
	private Textbox txtOrina;
	private Textbox txtTacto;
	private Textbox txtOlfato;
	private Textbox txtGusto;
	private Textbox txtOido;
	private Textbox txtVista;
	private Textbox txtRinnon;
	private Textbox txtHigado;
	private Textbox txtBazo;
	private Textbox txtMotivoIngreso;
	private Textbox txtLibido;
	private Textbox txtContactoAnimal;
	private Textbox txtPelajeEstado;
	private Listbox listListadoTratamiento;
	private Listbox listTratamiento;
	private Listbox listTratamientoTipo;
	private Listbox listPatologiaTipo;
	private Listbox listListadoPatologias;
	private Listbox listPatoligias;
	private Listbox listListadoSintomas1;
	private Listbox listListadoSintomas2;
	private Listbox listSintomas;
	private Listbox listListadoServicio1;
	private Listbox listListadoServicio2;
	private Listbox listApetitoFrecuencia;
	private Listbox listGanglio;
	private Listbox listTipoAlimentacion;
	private Button btnMenosTratamiento;
	private Button btnMasTratamiento;
	private Button btnMenosPatologia;
	private Button btnMasPatologia;
	private Datebox dbUltimoAlimento;
	private Datebox dbInicioEnfermedad;
	private Datebox dbCastracion;
	private Datebox dbUltimoCelo;
	private Combobox cbApetito;
	private Combobox cbEctoparasitos;
	private Combobox cbRuidoAdventicios;
	private Combobox cbMucosa;
	private Spinner spPulsoYugular;
	private Spinner spPulsoArterial;
	private Spinner spPulso;
	private Spinner spFrecuenciaCardiaca;
	private Spinner spRespiracion;
	private Spinner spTpc;
	private Spinner spTemperatura;
	private Spinner spPartos;
	private Spinner spAltura;
	private Spinner spPeso;
	private Tabbox tbConsulta;
	private Caption cpArrow;
	private Groupbox gbSintoma;

	// Variables -> Zul Propias
	// -------------------------------------------------------------------

	// Variables y Servicios -> Modelo
	// ------------------------------------------------------------

	private ServicioConsultaGeneral servicioConsultaGeneral;
	private ServicioGanglio servicioGanglio;
	private ServicioFrecuencia servicioFrecuencia;
	private ServicioTipoAlimentacion servicioTipoAlimentacion;
	private ServicioServicio servicioServicio;
	private ServicioSintoma servicioSintoma;
	private ServicioTipoPatologia servicioTipoPatoligia;
	private ServicioPatologia servicioPatologia;
	private ServicioTipoTratamiento servicioTipoTratamiento;
	private ServicioTratamiento servicioTratamiento;
	private ConsultaGeneral consultaGeneral;
	private Paciente paciente;
	private Veterinario veterinario;
	private List<Ganglio> ganglios = new ArrayList<Ganglio>();
	private List<Frecuencia> frecuencias = new ArrayList<Frecuencia>();
	private List<TipoAlimentacion> tipoAlimentaciones = new ArrayList<TipoAlimentacion>();
	private List<TipoPatologia> tipoPatologiaCombo = new ArrayList<TipoPatologia>();
	private List<Patologia> patologiaCombo = new ArrayList<Patologia>();
	private List<TipoTratamiento> tipoTratamientoCombo = new ArrayList<TipoTratamiento>();
	private List<Tratamiento> tratamientoCombo = new ArrayList<Tratamiento>();

	// Variables -> Entorno
	// -----------------------------------------------------------------------

	private ctrlWinServicioConsultaGeneral ctrlwinservicioconsultageneral;
	private List<Sintoma> sintomas1 = new ArrayList<Sintoma>();
	private List<Sintoma> sintomas2 = new ArrayList<Sintoma>();
	private List<Servicio> servicios1 = new ArrayList<Servicio>();
	private List<Servicio> servicios2 = new ArrayList<Servicio>();
	private List<Patologia> patologias = new ArrayList<Patologia>();
	private List<Tratamiento> tratamientos = new ArrayList<Tratamiento>();
	private boolean castrado;
	private int indexTipoAlimentacion = -1;

	// Codigo -> Carga Inicial
	// --------------------------------------------------------------------

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		WinConsultaGeneral.setAttribute(comp.getId() + "ctrl", this);
		servicioConsultaGeneral = (ServicioConsultaGeneral) SpringUtil
				.getBean("beanServicioConsultaGeneral");
		servicioGanglio = (ServicioGanglio) SpringUtil
				.getBean("beanServicioGanglio");
		servicioFrecuencia = (ServicioFrecuencia) SpringUtil
				.getBean("beanServicioFrecuencia");
		servicioTipoAlimentacion = (ServicioTipoAlimentacion) SpringUtil
				.getBean("beanServicioTipoAlimentacion");
		servicioServicio = (ServicioServicio) SpringUtil
				.getBean("beanServicioServicio");
		servicioSintoma = (ServicioSintoma) SpringUtil
				.getBean("beanServicioSintoma");
		servicioTipoPatoligia = (ServicioTipoPatologia) SpringUtil
				.getBean("beanServicioTipoPatologia");
		servicioPatologia = (ServicioPatologia) SpringUtil
				.getBean("beanServicioPatologia");
		servicioTipoTratamiento = (ServicioTipoTratamiento) SpringUtil
				.getBean("beanServicioTipoTratamiento");
		servicioTratamiento = (ServicioTratamiento) SpringUtil
				.getBean("beanServicioTratamiento");
		consultaGeneral = new ConsultaGeneral();
		paciente = new Paciente();
		veterinario = new Veterinario();
		ctrlwinservicioconsultageneral = (ctrlWinServicioConsultaGeneral) arg
				.get("ctrlWinServicioConsultaGeneral");
		ctrlwinservicioconsultageneral.setCtrlwinconsultageneral(this);
		paciente = ctrlwinservicioconsultageneral.getPaciente();
		veterinario = ctrlwinservicioconsultageneral.getVeterinario();
		ganglios = servicioGanglio.buscarTodos();
		frecuencias = servicioFrecuencia.buscarFrecuenciaAlimento();
		tipoAlimentaciones = servicioTipoAlimentacion.buscarTodos();
		servicios2 = servicioServicio.buscarTodos();
		cargarList(servicios1, servicios2);
		sintomas2 = servicioSintoma.buscarTodos();
		cargarList(sintomas1, sintomas2);
		tipoPatologiaCombo = servicioTipoPatoligia.buscarTodos();
		tipoTratamientoCombo = servicioTipoTratamiento.buscarTodos();
		castrado = false;
		listTratamiento.setDisabled(true);
		listPatoligias.setDisabled(true);
		cargar();
	}

	public void cargar() {
		if (paciente.getSexo().getNombre().equals("Hembra")){
			txtTesticulos.setDisabled(true);
			txtPrepucio.setDisabled(true);
		} else
			if (paciente.getSexo().getNombre().equals("Macho")){
				dbUltimoCelo.setDisabled(true);
				spPartos.setDisabled(true);
				txtSecrecionVaginal.setDisabled(true);
			}
		ConsultaGeneral auxConsultaGeneral = servicioConsultaGeneral
				.buscarUltimaConsultaGPaciente(paciente, 'A');
		if (auxConsultaGeneral != null) {
			consultaGeneral.setPeso(auxConsultaGeneral.getPeso());
			consultaGeneral.setAltura(auxConsultaGeneral.getAltura());
			consultaGeneral.setPelajeEstado(auxConsultaGeneral
					.getPelajeEstado());
			indexTipoAlimentacion = tipoAlimentaciones
					.indexOf(auxConsultaGeneral.getTipoAlimentacion());
			consultaGeneral.setContactoAnimal(auxConsultaGeneral
					.getContactoAnimal());
			consultaGeneral.setLibido(auxConsultaGeneral.getLibido());
			consultaGeneral.setUltimoCelo(auxConsultaGeneral.getUltimoCelo());
			consultaGeneral.setPartos(auxConsultaGeneral.getPartos());
			auxConsultaGeneral = servicioConsultaGeneral
					.buscarCastracionConsultaGPaciente(paciente, 'A');
			if (auxConsultaGeneral != null) {
				consultaGeneral.setCastracion(auxConsultaGeneral
						.getCastracion());
				castrado = true;
				dbCastracion.setDisabled(true);
			}
		}
	}

	public void cargarList(List primeraList, List segundaList) {
		int mitad;
		mitad = segundaList.size() / 2;
		for (int x = 0; x < mitad; x++) {
			primeraList.add(segundaList.remove(0));
		}
	}

	// Codigo -> Botones y Secciones
	// --------------------------------------------------------------

	public void onClick$btnMenosTratamiento() {
		quitarItemListbox(listListadoTratamiento, tratamientos);
	}

	public void onClick$btnMasTratamiento() {
		agregarItemListbox(listListadoTratamiento, listTratamiento,
				tratamientos, tratamientoCombo, "Tratamientos");
	}

	public void onClick$btnMenosPatologia() {
		quitarItemListbox(listListadoPatologias, patologias);
	}

	public void onClick$btnMasPatologia() {
		agregarItemListbox(listListadoPatologias, listPatoligias, patologias,
				patologiaCombo, "Patologias");
	}

	// Codigo -> Entorno
	// --------------------------------------------------------------------------

	public void guardar() {
		List<?> lists;
		Set<?> sets;
		Set<Ganglio> auxGanglios = new HashSet<Ganglio>();
		Set<Servicio> auxServicio = new HashSet<Servicio>();
		Set<Patologia> auxPatologias = new HashSet<Patologia>();
		Set<Sintoma> auxSintomas = new HashSet<Sintoma>();
		Set<Tratamiento> auxTratamientos = new HashSet<Tratamiento>();

		if (validar()) {
			consultaGeneral.setPaciente(paciente);
			consultaGeneral.setVeterinario(veterinario);
			consultaGeneral.setFecha(ctrlwinservicioconsultageneral
					.getDbFechaActual().getValue());
			consultaGeneral.setHora(ctrlwinservicioconsultageneral
					.getDbFechaActual().getValue());
			consultaGeneral
					.setTipoAlimentacion((TipoAlimentacion) listTipoAlimentacion
							.getSelectedItem().getValue());
			consultaGeneral.setFrecuenciaAlimento((Frecuencia) listApetitoFrecuencia
					.getSelectedItem().getValue());
			if (!listGanglio.getSelectedItems().isEmpty()) {
				sets = listGanglio.getSelectedItems();
				for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxGanglios.add((Ganglio) item.getValue());
				}
				consultaGeneral.setGanglios(auxGanglios);
			}
			if (!listListadoServicio1.getItems().isEmpty()) {
				sets = listListadoServicio1.getSelectedItems();
				for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxServicio.add((Servicio) item.getValue());
				}
				consultaGeneral.setServicios(auxServicio);
			}
			if (!listListadoServicio2.getItems().isEmpty()) {
				sets = listListadoServicio2.getSelectedItems();
				for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxServicio.add((Servicio) item.getValue());
				}
				consultaGeneral.setServicios(auxServicio);
			}
			if (!listListadoSintomas1.getItems().isEmpty()) {
				sets = listListadoSintomas1.getSelectedItems();
				for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxSintomas.add((Sintoma) item.getValue());
				}
				consultaGeneral.setSintomas(auxSintomas);
			}
			if (!listListadoSintomas2.getItems().isEmpty()) {
				sets = listListadoSintomas2.getSelectedItems();
				for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxSintomas.add((Sintoma) item.getValue());
				}
				consultaGeneral.setSintomas(auxSintomas);
			}
			if (!listListadoPatologias.getItems().isEmpty()) {
				lists = listListadoPatologias.getItems();
				for (Iterator iterator = lists.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxPatologias.add((Patologia) item.getValue());
				}
				consultaGeneral.setPatologias(auxPatologias);
			}
			if (!listListadoTratamiento.getItems().isEmpty()) {
				lists = listListadoTratamiento.getItems();
				for (Iterator iterator = lists.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxTratamientos.add((Tratamiento) item.getValue());
				}
				consultaGeneral.setTratamientos(auxTratamientos);
			}
			if (castrado) {
				consultaGeneral.setCastracion(null);
			} else {
				if (!(dbCastracion.getValue() == null)) {
					consultaGeneral.setCastracion(dbCastracion.getValue());
				}
			}
			if (cbApetito.getSelectedIndex() == 0) {
				consultaGeneral.setApetito(true);
			} else {
				consultaGeneral.setApetito(false);
			}
			if (cbEctoparasitos.getSelectedIndex() == 0) {
				consultaGeneral.setEctoparasitos(true);
			} else {
				consultaGeneral.setEctoparasitos(false);
			}
			if (cbMucosa.getSelectedIndex() == 0) {
				consultaGeneral.setMucosa(true);
			} else {
				consultaGeneral.setMucosa(false);
			}
			if (cbRuidoAdventicios.getSelectedIndex() == 0) {
				consultaGeneral.setRuidoAdventicios(true);
			} else {
				consultaGeneral.setRuidoAdventicios(false);
			}
			try {
				servicioConsultaGeneral.guardarConsultaG(consultaGeneral);
				System.out.println("terminado!!");
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinservicioconsultageneral.actividadBotones(true,
								false, true, false, false, false);
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}
		}
	}

	public boolean validar() {
		boolean valido = false;
		if (spPeso.getValue() <= 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nPeso",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spPeso.setFocus(true);
						}
					});
		} else

		if (spAltura.getValue() <= 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nAltura",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spAltura.setFocus(true);
						}
					});
		} else

		if (txtPelajeEstado.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nEstado del Pelaje",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtPelajeEstado.setFocus(true);
						}
					});
		} else

		if (listTipoAlimentacion.getSelectedIndex() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nTipo de Alimentacion",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listTipoAlimentacion.setFocus(true);
						}
					});
		} else

		if (txtContactoAnimal.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\nContacto con otros Animales", new MensajeListener() {
						@Override
						public void alDestruir() {
							txtContactoAnimal.setFocus(true);
						}
					});
		} else

		if (txtMotivoIngreso.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nMotivo de Ingreso",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtMotivoIngreso.setFocus(true);
						}
					});
		} else

		if (dbInicioEnfermedad.getValue() == null) {
			MensajeEmergente.mostrar("NOEMPTY", "\nInicio Enfermedad",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							dbInicioEnfermedad.setFocus(true);
						}
					});
		} else

		if (spTemperatura.getValue() <= 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nTemperatura",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spTemperatura.setFocus(true);
						}
					});
		} else

		if (spTpc.getValue() <= 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nT:PC",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spTpc.setFocus(true);
						}
					});
		} else

		if (spRespiracion.getValue() <= 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nRespiracion;",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spRespiracion.setFocus(true);
						}
					});
		} else

		if (spFrecuenciaCardiaca.getValue() <= 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nFrecuencia Cardiaca",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spFrecuenciaCardiaca.setFocus(true);
						}
					});
		} else

		if (spPulso.getValue() <= 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nPulso",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spPulso.setFocus(true);
						}
					});
		} else

		if (spPulsoArterial.getValue() <= 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nPulso Arterial",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spPulsoArterial.setFocus(true);
						}
					});
		} else

		if (spPulsoYugular.getValue() <= 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nPulso Yugular",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spPulsoYugular.setFocus(true);
						}
					});
		} else

		if (cbMucosa.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nMucosas Visibles",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							cbMucosa.setFocus(true);
						}
					});
		} else

		if (cbRuidoAdventicios.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nRuido Adventicios",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							cbRuidoAdventicios.setFocus(true);
						}
					});
		} else

		if (cbEctoparasitos.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nEctoparasitos",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							cbEctoparasitos.setFocus(true);
						}
					});
		} else

		if (cbApetito.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nApetito",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							cbApetito.setFocus(true);
						}
					});
		} else

		if (listApetitoFrecuencia.getSelectedIndex() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nFrecuencia",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listApetitoFrecuencia.setFocus(true);
						}
					});
		} else

		if (dbUltimoAlimento.getValue() == null) {
			MensajeEmergente.mostrar("NOEMPTY", "\nUltimo Alimento",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							dbUltimoAlimento.setFocus(true);
						}
					});
		} else

		if (txtVista.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nVista",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtVista.setFocus(true);
						}
					});
		} else

		if (txtOido.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nOido",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtOido.setFocus(true);
						}
					});
		} else

		if (txtGusto.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nGusto",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtGusto.setFocus(true);
						}
					});
		} else

		if (txtOlfato.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nOlfato",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtOlfato.setFocus(true);
						}
					});
		} else

		if (txtTacto.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nTacto",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtTacto.setFocus(true);
						}
					});
		} else

		if (txtCaracter.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nCambio de Caracter",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtCaracter.setFocus(true);
						}
					});
		} else

		if (txtDiagnosticoDefinitivo.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nDiagnostico Definitivo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtDiagnosticoDefinitivo.setFocus(true);
						}
					});
		} else

		if (txtTratamientoEnviado.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nTratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtTratamientoEnviado.setFocus(true);
						}
					});
		} else {
			valido = true;
		}
		return valido;
	}

	public void agregarItemListbox(Listbox listado, Listbox combo,
			List listListado, List listCombo, String nombreComboListB) {
		if (combo.getSelectedIndex() >= 0) {
			boolean encontrado = false;
			if (listListado.indexOf(combo.getSelectedItem().getValue()) != -1){
				encontrado = true;
			}
			if (!encontrado) {
				listListado.add(combo.getSelectedItem().getValue());
				listado.setModel(new BindingListModelList(listListado, false));
			} else {
				MensajeEmergente.mostrar("ELEEXIST", "\n" + nombreComboListB);
			}
		} else {
			MensajeEmergente.mostrar("SELECTELE", "\n" + nombreComboListB);
			combo.setFocus(true);
		}
	}

	public void quitarItemListbox(Listbox listado, List listListado) {
		int indice = listado.getSelectedIndex();
		if (indice >= 0) {
			listListado.remove(indice);
			listado.setModel(new BindingListModelList(listListado, false));
		} else
			MensajeEmergente.mostrar("SELECTELE");
	}

	public void onAfterRender$listTipoAlimentacion() {
		listTipoAlimentacion.setSelectedIndex(indexTipoAlimentacion);
	}

	public void onSelect$listTratamientoTipo() {
		tratamientoCombo = servicioTratamiento.buscarCoincidenciasTipo(
				(TipoTratamiento) listTratamientoTipo.getSelectedItem()
						.getValue(), 'A');
		listTratamiento.setModel(new BindingListModelList(tratamientoCombo,
				false));
		listTratamiento.setDisabled(false);

	}

	public void onSelect$listPatologiaTipo() {
		patologiaCombo = servicioPatologia.buscarCoincidenciasTipo(
				(TipoPatologia) listPatologiaTipo.getSelectedItem().getValue(),
				'A');
		listPatoligias
				.setModel(new BindingListModelList(patologiaCombo, false));
		listPatoligias.setDisabled(false);
	}

	public void onOpen$gbSintoma() {
		if (gbSintoma.isOpen()) {
			cpArrow.setImage("/sigma/imagenes/botones-basicos/quitar.png");
		} else {
			cpArrow.setImage("/sigma/imagenes/botones-basicos/agregar.png");
		}
	}

	// Codigo -> Getters y Setters
	// ----------------------------------------------------------------

	public Window getWinConsultaGeneral() {
		return WinConsultaGeneral;
	}

	public void setWinConsultaGeneral(Window winConsultaGeneral) {
		WinConsultaGeneral = winConsultaGeneral;
	}

	public Textbox getTxtObservaciones() {
		return txtObservaciones;
	}

	public void setTxtObservaciones(Textbox txtObservaciones) {
		this.txtObservaciones = txtObservaciones;
	}

	public Textbox getTxtTratamientoEnviado() {
		return txtTratamientoEnviado;
	}

	public void setTxtTratamientoEnviado(Textbox txtTratamientoEnviado) {
		this.txtTratamientoEnviado = txtTratamientoEnviado;
	}

	public Textbox getTxtTratamientoAplicado() {
		return txtTratamientoAplicado;
	}

	public void setTxtTratamientoAplicado(Textbox txtTratamientoAplicado) {
		this.txtTratamientoAplicado = txtTratamientoAplicado;
	}

	public Textbox getTxtTratamientoIndicaciones() {
		return txtTratamientoIndicaciones;
	}

	public void setTxtTratamientoIndicaciones(Textbox txtTratamientoIndicaciones) {
		this.txtTratamientoIndicaciones = txtTratamientoIndicaciones;
	}

	public Textbox getTxtDiagnosticoDefinitivo() {
		return txtDiagnosticoDefinitivo;
	}

	public void setTxtDiagnosticoDefinitivo(Textbox txtDiagnosticoDefinitivo) {
		this.txtDiagnosticoDefinitivo = txtDiagnosticoDefinitivo;
	}

	public Textbox getTxtDiagnosticoDiferencial() {
		return txtDiagnosticoDiferencial;
	}

	public void setTxtDiagnosticoDiferencial(Textbox txtDiagnosticoDiferencial) {
		this.txtDiagnosticoDiferencial = txtDiagnosticoDiferencial;
	}

	public Textbox getTxtDiagnosticoTentativo() {
		return txtDiagnosticoTentativo;
	}

	public void setTxtDiagnosticoTentativo(Textbox txtDiagnosticoTentativo) {
		this.txtDiagnosticoTentativo = txtDiagnosticoTentativo;
	}

	public Textbox getTxtDiagnosticoProcedimiento() {
		return txtDiagnosticoProcedimiento;
	}

	public void setTxtDiagnosticoProcedimiento(
			Textbox txtDiagnosticoProcedimiento) {
		this.txtDiagnosticoProcedimiento = txtDiagnosticoProcedimiento;
	}

	public Textbox getTxtPatologiaComentario() {
		return txtPatologiaComentario;
	}

	public void setTxtPatologiaComentario(Textbox txtPatologiaComentario) {
		this.txtPatologiaComentario = txtPatologiaComentario;
	}

	public Textbox getTxtCaracter() {
		return txtCaracter;
	}

	public void setTxtCaracter(Textbox txtCaracter) {
		this.txtCaracter = txtCaracter;
	}

	public Textbox getTxtPrepucio() {
		return txtPrepucio;
	}

	public void setTxtPrepucio(Textbox txtPrepucio) {
		this.txtPrepucio = txtPrepucio;
	}

	public Textbox getTxtTesticulos() {
		return txtTesticulos;
	}

	public void setTxtTesticulos(Textbox txtTesticulos) {
		this.txtTesticulos = txtTesticulos;
	}

	public Textbox getTxtSecrecionVaginal() {
		return txtSecrecionVaginal;
	}

	public void setTxtSecrecionVaginal(Textbox txtSecrecionVaginal) {
		this.txtSecrecionVaginal = txtSecrecionVaginal;
	}

	public Textbox getTxtVejiga() {
		return txtVejiga;
	}

	public void setTxtVejiga(Textbox txtVejiga) {
		this.txtVejiga = txtVejiga;
	}

	public Textbox getTxtOrina() {
		return txtOrina;
	}

	public void setTxtOrina(Textbox txtOrina) {
		this.txtOrina = txtOrina;
	}

	public Textbox getTxtTacto() {
		return txtTacto;
	}

	public void setTxtTacto(Textbox txtTacto) {
		this.txtTacto = txtTacto;
	}

	public Textbox getTxtOlfato() {
		return txtOlfato;
	}

	public void setTxtOlfato(Textbox txtOlfato) {
		this.txtOlfato = txtOlfato;
	}

	public Textbox getTxtGusto() {
		return txtGusto;
	}

	public void setTxtGusto(Textbox txtGusto) {
		this.txtGusto = txtGusto;
	}

	public Textbox getTxtOido() {
		return txtOido;
	}

	public void setTxtOido(Textbox txtOido) {
		this.txtOido = txtOido;
	}

	public Textbox getTxtVista() {
		return txtVista;
	}

	public void setTxtVista(Textbox txtVista) {
		this.txtVista = txtVista;
	}

	public Textbox getTxtRinnon() {
		return txtRinnon;
	}

	public void setTxtRinnon(Textbox txtRinnon) {
		this.txtRinnon = txtRinnon;
	}

	public Caption getCpArrow() {
		return cpArrow;
	}

	public void setCpArrow(Caption cpArrow) {
		this.cpArrow = cpArrow;
	}

	public Groupbox getGbSintoma() {
		return gbSintoma;
	}

	public void setGbSintoma(Groupbox gbSintoma) {
		this.gbSintoma = gbSintoma;
	}

	public Textbox getTxtHigado() {
		return txtHigado;
	}

	public void setTxtHigado(Textbox txtHigado) {
		this.txtHigado = txtHigado;
	}

	public Textbox getTxtBazo() {
		return txtBazo;
	}

	public void setTxtBazo(Textbox txtBazo) {
		this.txtBazo = txtBazo;
	}

	public Textbox getTxtMotivoIngreso() {
		return txtMotivoIngreso;
	}

	public void setTxtMotivoIngreso(Textbox txtMotivoIngreso) {
		this.txtMotivoIngreso = txtMotivoIngreso;
	}

	public Textbox getTxtLibido() {
		return txtLibido;
	}

	public void setTxtLibido(Textbox txtLibido) {
		this.txtLibido = txtLibido;
	}

	public Textbox getTxtContactoAnimal() {
		return txtContactoAnimal;
	}

	public void setTxtContactoAnimal(Textbox txtContactoAnimal) {
		this.txtContactoAnimal = txtContactoAnimal;
	}

	public Textbox getTxtPelajeEstado() {
		return txtPelajeEstado;
	}

	public void setTxtPelajeEstado(Textbox txtPelajeEstado) {
		this.txtPelajeEstado = txtPelajeEstado;
	}

	public Listbox getListListadoTratamiento() {
		return listListadoTratamiento;
	}

	public void setListListadoTratamiento(Listbox listListadoTratamiento) {
		this.listListadoTratamiento = listListadoTratamiento;
	}

	public Listbox getListTratamiento() {
		return listTratamiento;
	}

	public void setListTratamiento(Listbox listTratamiento) {
		this.listTratamiento = listTratamiento;
	}

	public Listbox getListTratamientoTipo() {
		return listTratamientoTipo;
	}

	public void setListTratamientoTipo(Listbox listTratamientoTipo) {
		this.listTratamientoTipo = listTratamientoTipo;
	}

	public Listbox getListPatologiaTipo() {
		return listPatologiaTipo;
	}

	public void setListPatologiaTipo(Listbox listPatologiaTipo) {
		this.listPatologiaTipo = listPatologiaTipo;
	}

	public Listbox getListListadoPatologias() {
		return listListadoPatologias;
	}

	public void setListListadoPatologias(Listbox listListadoPatologias) {
		this.listListadoPatologias = listListadoPatologias;
	}

	public Listbox getListPatoligias() {
		return listPatoligias;
	}

	public void setListPatoligias(Listbox listPatoligias) {
		this.listPatoligias = listPatoligias;
	}

	public Listbox getListSintomas() {
		return listSintomas;
	}

	public void setListSintomas(Listbox listSintomas) {
		this.listSintomas = listSintomas;
	}

	public Listbox getListApetitoFrecuencia() {
		return listApetitoFrecuencia;
	}

	public void setListApetitoFrecuencia(Listbox listApetitoFrecuencia) {
		this.listApetitoFrecuencia = listApetitoFrecuencia;
	}

	public Listbox getListGanglio() {
		return listGanglio;
	}

	public void setListGanglio(Listbox listGanglio) {
		this.listGanglio = listGanglio;
	}

	public Listbox getListTipoAlimentacion() {
		return listTipoAlimentacion;
	}

	public void setListTipoAlimentacion(Listbox listTipoAlimentacion) {
		this.listTipoAlimentacion = listTipoAlimentacion;
	}

	public Datebox getDbUltimoAlimento() {
		return dbUltimoAlimento;
	}

	public void setDbUltimoAlimento(Datebox dbUltimoAlimento) {
		this.dbUltimoAlimento = dbUltimoAlimento;
	}

	public Datebox getDbInicioEnfermedad() {
		return dbInicioEnfermedad;
	}

	public void setDbInicioEnfermedad(Datebox dbInicioEnfermedad) {
		this.dbInicioEnfermedad = dbInicioEnfermedad;
	}

	public Datebox getDbCastracion() {
		return dbCastracion;
	}

	public void setDbCastracion(Datebox dbCastracion) {
		this.dbCastracion = dbCastracion;
	}

	public Datebox getDbUltimoCelo() {
		return dbUltimoCelo;
	}

	public void setDbUltimoCelo(Datebox dbUltimoCelo) {
		this.dbUltimoCelo = dbUltimoCelo;
	}

	public Combobox getCbApetito() {
		return cbApetito;
	}

	public void setCbApetito(Combobox cbApetito) {
		this.cbApetito = cbApetito;
	}

	public Combobox getCbEctoparasitos() {
		return cbEctoparasitos;
	}

	public void setCbEctoparasitos(Combobox cbEctoparasitos) {
		this.cbEctoparasitos = cbEctoparasitos;
	}

	public Combobox getCbRuidoAdventicios() {
		return cbRuidoAdventicios;
	}

	public void setCbRuidoAdventicios(Combobox cbRuidoAdventicios) {
		this.cbRuidoAdventicios = cbRuidoAdventicios;
	}

	public Combobox getCbMucosa() {
		return cbMucosa;
	}

	public void setCbMucosa(Combobox cbMucosa) {
		this.cbMucosa = cbMucosa;
	}

	public Spinner getSpPulsoYugular() {
		return spPulsoYugular;
	}

	public void setSpPulsoYugular(Spinner spPulsoYugular) {
		this.spPulsoYugular = spPulsoYugular;
	}

	public Spinner getSpPulsoArterial() {
		return spPulsoArterial;
	}

	public void setSpPulsoArterial(Spinner spPulsoArterial) {
		this.spPulsoArterial = spPulsoArterial;
	}

	public Spinner getSpPulso() {
		return spPulso;
	}

	public void setSpPulso(Spinner spPulso) {
		this.spPulso = spPulso;
	}

	public Spinner getSpFrecuenciaCardiaca() {
		return spFrecuenciaCardiaca;
	}

	public void setSpFrecuenciaCardiaca(Spinner spFrecuenciaCardiaca) {
		this.spFrecuenciaCardiaca = spFrecuenciaCardiaca;
	}

	public Spinner getSpRespiracion() {
		return spRespiracion;
	}

	public void setSpRespiracion(Spinner spRespiracion) {
		this.spRespiracion = spRespiracion;
	}

	public Spinner getSpTpc() {
		return spTpc;
	}

	public void setSpTpc(Spinner spTpc) {
		this.spTpc = spTpc;
	}

	public Spinner getSpTemperatura() {
		return spTemperatura;
	}

	public void setSpTemperatura(Spinner spTemperatura) {
		this.spTemperatura = spTemperatura;
	}

	public Spinner getSpPartos() {
		return spPartos;
	}

	public void setSpPartos(Spinner spPartos) {
		this.spPartos = spPartos;
	}

	public Spinner getSpAltura() {
		return spAltura;
	}

	public void setSpAltura(Spinner spAltura) {
		this.spAltura = spAltura;
	}

	public Spinner getSpPeso() {
		return spPeso;
	}

	public void setSpPeso(Spinner spPeso) {
		this.spPeso = spPeso;
	}

	public ConsultaGeneral getConsultaGeneral() {
		return consultaGeneral;
	}

	public void setConsultaGeneral(ConsultaGeneral consultaGeneral) {
		this.consultaGeneral = consultaGeneral;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Veterinario getVeterinario() {
		return veterinario;
	}

	public void setVeterinario(Veterinario veterinario) {
		this.veterinario = veterinario;
	}

	public List<Ganglio> getGanglios() {
		return ganglios;
	}

	public void setGanglios(List<Ganglio> ganglios) {
		this.ganglios = ganglios;
	}

	public List<Frecuencia> getFrecuencias() {
		return frecuencias;
	}

	public void setFrecuencias(List<Frecuencia> frecuencias) {
		this.frecuencias = frecuencias;
	}

	public List<TipoAlimentacion> getTipoAlimentaciones() {
		return tipoAlimentaciones;
	}

	public void setTipoAlimentaciones(List<TipoAlimentacion> tipoAlimentaciones) {
		this.tipoAlimentaciones = tipoAlimentaciones;
	}

	public List<TipoPatologia> getTipoPatologiaCombo() {
		return tipoPatologiaCombo;
	}

	public void setTipoPatologiaCombo(List<TipoPatologia> tipoPatologiaCombo) {
		this.tipoPatologiaCombo = tipoPatologiaCombo;
	}

	public List<Patologia> getPatologiaCombo() {
		return patologiaCombo;
	}

	public void setPatologiaCombo(List<Patologia> patologiaCombo) {
		this.patologiaCombo = patologiaCombo;
	}

	public List<TipoTratamiento> getTipoTratamientoCombo() {
		return tipoTratamientoCombo;
	}

	public void setTipoTratamientoCombo(
			List<TipoTratamiento> tipoTratamientoCombo) {
		this.tipoTratamientoCombo = tipoTratamientoCombo;
	}

	public List<Tratamiento> getTratamientoCombo() {
		return tratamientoCombo;
	}

	public void setTratamientoCombo(List<Tratamiento> tratamientoCombo) {
		this.tratamientoCombo = tratamientoCombo;
	}

	public ctrlWinServicioConsultaGeneral getCtrlwinservicioconsultageneral() {
		return ctrlwinservicioconsultageneral;
	}

	public void setCtrlwinservicioconsultageneral(
			ctrlWinServicioConsultaGeneral ctrlwinservicioconsultageneral) {
		this.ctrlwinservicioconsultageneral = ctrlwinservicioconsultageneral;
	}

	public List<Patologia> getPatologias() {
		return patologias;
	}

	public void setPatologias(List<Patologia> patologias) {
		this.patologias = patologias;
	}

	public List<Tratamiento> getTratamientos() {
		return tratamientos;
	}

	public void setTratamientos(List<Tratamiento> tratamientos) {
		this.tratamientos = tratamientos;
	}

	public boolean isCastrado() {
		return castrado;
	}

	public void setCastrado(boolean castrado) {
		this.castrado = castrado;
	}

	public Listbox getListListadoSintomas1() {
		return listListadoSintomas1;
	}

	public void setListListadoSintomas1(Listbox listListadoSintomas1) {
		this.listListadoSintomas1 = listListadoSintomas1;
	}

	public Listbox getListListadoSintomas2() {
		return listListadoSintomas2;
	}

	public void setListListadoSintomas2(Listbox listListadoSintomas2) {
		this.listListadoSintomas2 = listListadoSintomas2;
	}

	public List<Sintoma> getSintomas1() {
		return sintomas1;
	}

	public void setSintomas1(List<Sintoma> sintomas1) {
		this.sintomas1 = sintomas1;
	}

	public List<Sintoma> getSintomas2() {
		return sintomas2;
	}

	public void setSintomas2(List<Sintoma> sintomas2) {
		this.sintomas2 = sintomas2;
	}

	public Button getBtnMenosTratamiento() {
		return btnMenosTratamiento;
	}

	public void setBtnMenosTratamiento(Button btnMenosTratamiento) {
		this.btnMenosTratamiento = btnMenosTratamiento;
	}

	public Button getBtnMasTratamiento() {
		return btnMasTratamiento;
	}

	public void setBtnMasTratamiento(Button btnMasTratamiento) {
		this.btnMasTratamiento = btnMasTratamiento;
	}

	public Button getBtnMenosPatologia() {
		return btnMenosPatologia;
	}

	public void setBtnMenosPatologia(Button btnMenosPatologia) {
		this.btnMenosPatologia = btnMenosPatologia;
	}

	public Button getBtnMasPatologia() {
		return btnMasPatologia;
	}

	public void setBtnMasPatologia(Button btnMasPatologia) {
		this.btnMasPatologia = btnMasPatologia;
	}

	public Tabbox getTbConsulta() {
		return tbConsulta;
	}

	public void setTbConsulta(Tabbox tbConsulta) {
		this.tbConsulta = tbConsulta;
	}

	public ServicioConsultaGeneral getServicioConsultaGeneral() {
		return servicioConsultaGeneral;
	}

	public void setServicioConsultaGeneral(
			ServicioConsultaGeneral servicioConsultaGeneral) {
		this.servicioConsultaGeneral = servicioConsultaGeneral;
	}

	public ServicioGanglio getServicioGanglio() {
		return servicioGanglio;
	}

	public void setServicioGanglio(ServicioGanglio servicioGanglio) {
		this.servicioGanglio = servicioGanglio;
	}

	public ServicioFrecuencia getServicioFrecuencia() {
		return servicioFrecuencia;
	}

	public void setServicioFrecuencia(ServicioFrecuencia servicioFrecuencia) {
		this.servicioFrecuencia = servicioFrecuencia;
	}

	public ServicioTipoAlimentacion getServicioTipoAlimentacion() {
		return servicioTipoAlimentacion;
	}

	public void setServicioTipoAlimentacion(
			ServicioTipoAlimentacion servicioTipoAlimentacion) {
		this.servicioTipoAlimentacion = servicioTipoAlimentacion;
	}

	public ServicioSintoma getServicioSintoma() {
		return servicioSintoma;
	}

	public void setServicioSintoma(ServicioSintoma servicioSintoma) {
		this.servicioSintoma = servicioSintoma;
	}

	public ServicioTipoPatologia getServicioTipoPatoligia() {
		return servicioTipoPatoligia;
	}

	public void setServicioTipoPatoligia(
			ServicioTipoPatologia servicioTipoPatoligia) {
		this.servicioTipoPatoligia = servicioTipoPatoligia;
	}

	public ServicioPatologia getServicioPatologia() {
		return servicioPatologia;
	}

	public void setServicioPatologia(ServicioPatologia servicioPatologia) {
		this.servicioPatologia = servicioPatologia;
	}

	public ServicioTipoTratamiento getServicioTipoTratamiento() {
		return servicioTipoTratamiento;
	}

	public void setServicioTipoTratamiento(
			ServicioTipoTratamiento servicioTipoTratamiento) {
		this.servicioTipoTratamiento = servicioTipoTratamiento;
	}

	public ServicioTratamiento getServicioTratamiento() {
		return servicioTratamiento;
	}

	public void setServicioTratamiento(ServicioTratamiento servicioTratamiento) {
		this.servicioTratamiento = servicioTratamiento;
	}	
	
	public Textbox getTxtServicioDescripcion() {
		return txtServicioDescripcion;
	}

	public void setTxtServicioDescripcion(Textbox txtServicioDescripcion) {
		this.txtServicioDescripcion = txtServicioDescripcion;
	}

	public Listbox getListListadoServicio1() {
		return listListadoServicio1;
	}

	public void setListListadoServicio1(Listbox listListadoServicio1) {
		this.listListadoServicio1 = listListadoServicio1;
	}

	public Listbox getListListadoServicio2() {
		return listListadoServicio2;
	}

	public void setListListadoServicio2(Listbox listListadoServicio2) {
		this.listListadoServicio2 = listListadoServicio2;
	}

	public ServicioServicio getServicioServicio() {
		return servicioServicio;
	}

	public void setServicioServicio(ServicioServicio servicioServicio) {
		this.servicioServicio = servicioServicio;
	}

	public List<Servicio> getServicios1() {
		return servicios1;
	}

	public void setServicios1(List<Servicio> servicios1) {
		this.servicios1 = servicios1;
	}

	public List<Servicio> getServicios2() {
		return servicios2;
	}

	public void setServicios2(List<Servicio> servicios2) {
		this.servicios2 = servicios2;
	}

	public int getIndexTipoAlimentacion() {
		return indexTipoAlimentacion;
	}

	public void setIndexTipoAlimentacion(int indexTipoAlimentacion) {
		this.indexTipoAlimentacion = indexTipoAlimentacion;
	}

}

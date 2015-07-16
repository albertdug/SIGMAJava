package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.components.HelperDateAge;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.componentszk.SessionAdministrator;
import org.ucla.sigma.modelo.EspImagenologia;
import org.ucla.sigma.modelo.Frecuencia;
import org.ucla.sigma.modelo.Ganglio;
import org.ucla.sigma.modelo.Imagenologia;
import org.ucla.sigma.modelo.Paciente;
import org.ucla.sigma.modelo.Patologia;
import org.ucla.sigma.modelo.Referencia;
import org.ucla.sigma.modelo.Sintoma;
import org.ucla.sigma.modelo.TipoAlimentacion;
import org.ucla.sigma.modelo.TipoImagenologia;
import org.ucla.sigma.modelo.TipoPatologia;
import org.ucla.sigma.modelo.TipoTratamiento;
import org.ucla.sigma.modelo.Tratamiento;
import org.ucla.sigma.modelo.Usuario;
import org.ucla.sigma.modelo.Veterinario;
import org.ucla.sigma.servicio.ServicioConsultaGeneral;
import org.ucla.sigma.servicio.ServicioEspImagenologia;
import org.ucla.sigma.servicio.ServicioFichaMedica;
import org.ucla.sigma.servicio.ServicioImagenologia;
import org.ucla.sigma.servicio.ServicioPaciente;
import org.ucla.sigma.servicio.ServicioPatologia;
import org.ucla.sigma.servicio.ServicioSintoma;
import org.ucla.sigma.servicio.ServicioTipoImagenologia;
import org.ucla.sigma.servicio.ServicioTipoPatologia;
import org.ucla.sigma.servicio.ServicioTipoTratamiento;
import org.ucla.sigma.servicio.ServicioTratamiento;
import org.ucla.sigma.servicio.ServicioVeterinario;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Center;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.North;
import org.zkoss.zul.South;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinServicioImagenologia extends GenericForwardComposer {

	/*
	 * Deben cambiar donde diga "XxxxNombreDelServicioXxxx", aqui y en el .zul
	 * Se ha de dejar el codigo q ya este, es decir NO borren nada, solo
	 * agreguen de acuerdo a lo nesesario, si no hay otra opcion, antes de
	 * borrar consulten, q luego es mas duro hallar cualquier error, para evitar
	 * problemas con el orden de algunos metodos ya existentes, porfavor agregar
	 * el codigo extra donde aparesca " //AGREGAR AQUI " PD1: Una vez terminado
	 * y funcionando el controlador, borrar los comentarios, que son solo para
	 * guiarce, es decir los q estan entre " /* "
	 */
	// Variables -> Zul
	// ---------------------------------------------------------------------------

	private Window winServicioImagenologia;
	private Button btnBuscar;
	private Button btnDefuncion;
	private Button btnReferenciaMedica;
	private Button btnHistorial;
	private Button btnCancelarPrincipal;
	private Button btnGuardarPrincipal;
	private Button btnMenosTratamiento;
	private Button btnMasTratamiento;
	private Button btnMenosPatologia;
	private Button btnMasPatologia;
	private Button btnMasImagen;
	private Button btnMenosImagen;
	private Datebox dbFechaActual;
	private Textbox txtHalazgos;
	private Textbox txtConclusion;
	private Textbox txtHm;
	private Textbox txtNombrePaciente;
	private Textbox txtRaza;
	private Textbox txtEspecie;
	private Textbox txtSexo;
	private Textbox txtEdad;
	private Textbox txtNombreResponsable;
	private Textbox txtCi;
	private Textbox txtObservaciones;
	private Textbox txtTratamientoEnviado;
	private Textbox txtTratamientoAplicado;
	private Textbox txtTratamientoIndicaciones;
	private Textbox txtPatologiaComentario;
	private Textbox txtDiagnosticoDefinitivo;
	private Textbox txtDiagnosticoDiferencial;
	private Textbox txtDiagnosticoTentativo;
	private Textbox txtDiagnosticoProcedimiento;
	private Listbox listListadoTratamiento;
	private Listbox listTratamiento;
	private Listbox listTratamientoTipo;
	private Listbox listListadoPatologias;
	private Listbox listPatoligias;
	private Listbox listPatologiaTipo;
	private Listbox listListadoSintomas1;
	private Listbox listListadoSintomas2;
	private Listbox listListadoImagenologia;
	private Listbox listTiposImagenologia;
	private Listbox listEspecificacionImagenologia;
	private North blNorte;
	private Center blCentro;
	private South blSur;
	private Div contCentro;
	private Caption cpArrow;
	private Groupbox gbSintoma;

	// Variables -> Zul Propias
	// -------------------------------------------------------------------

	/*
	 * Aqui han de ir las variables distintas del Default(Cabezera)
	 */

	// Variables y Servicios -> Modelo
	// ------------------------------------------------------------

	/*
	 * Aqui han de ir las variables para los servicios y todas aquellas que
	 * dependan de los mismo, asi como las q tiene un modelo(Para Hibernate) PD:
	 * Una vez precionado el boton buscar y que este aya tenido exito, las
	 * variables tanto Paciente como Veterinario, estaran cargadas y listas para
	 * usar, asi que hacer uso de ellas a gusto
	 */

	private ServicioImagenologia servicioImagenologia;
	private ServicioTipoImagenologia servicioTipoImagenologia;
	private ServicioEspImagenologia servicioEspImagenologia;
	private ServicioPaciente servicioPaciente;
	private ServicioSintoma servicioSintoma;
	private ServicioTipoPatologia servicioTipoPatoligia;
	private ServicioPatologia servicioPatologia;
	private ServicioTipoTratamiento servicioTipoTratamiento;
	private ServicioTratamiento servicioTratamiento;
	private Paciente paciente;
	private Usuario usuario;
	private Veterinario veterinario;
	private Imagenologia imagenologia;
	// AGREGAR AQUI

	// Variables -> Entorno
	// -----------------------------------------------------------------------

	/*
	 * Aqui han de ir todas aquellas variables q se consideran necesarias para
	 * el funcionamiento de controlador, esto depende de cada controlador y de
	 * quien lo haga, esto no incluye a las variables locales de algun metodo
	 * PD: Si es necesario usar o llamar alguna ventana(.zul) tratar en lo
	 * posible de crear un variable para el url, Ejemplo: private String
	 * xxxXxxxString = "/sigma/vistas/servicios/Servicios/xxxXxxx.zul";
	 */
	private List<TipoTratamiento> tipoTratamientoCombo = new ArrayList<TipoTratamiento>();
	private List<Tratamiento> tratamientoCombo = new ArrayList<Tratamiento>();
	private List<Tratamiento> tratamientos = new ArrayList<Tratamiento>();
	private List<TipoPatologia> tipoPatologiaCombo = new ArrayList<TipoPatologia>();
	private List<Patologia> patologiaCombo = new ArrayList<Patologia>();
	private List<Patologia> patologias = new ArrayList<Patologia>();
	private List<Sintoma> sintomas1 = new ArrayList<Sintoma>();
	private List<Sintoma> sintomas2 = new ArrayList<Sintoma>();

	private List<TipoImagenologia> tipoImganeologias = new ArrayList<TipoImagenologia>();
	private List<EspImagenologia> espImagenologias = new ArrayList<EspImagenologia>();
	private List<EspImagenologia> espImagenologiaCombo = new ArrayList<EspImagenologia>();

	// AGREGAR AQUI
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtHm.setFocus(true);
		};
	};

	// Codigo -> Carga Inicial
	// --------------------------------------------------------------------

	/*
	 * Aqui ira el codigo q sea necesario para inicializar el servicio, de mas
	 * notar que el doAfterCompose sera el basico e invariable
	 */

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winServicioImagenologia.setAttribute(comp.getId() + "ctrl", this);
		servicioPaciente = (ServicioPaciente) SpringUtil
				.getBean("beanServicioPaciente");
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
		servicioImagenologia = (ServicioImagenologia) SpringUtil
				.getBean("beanServicioImagenologia");
		servicioTipoImagenologia = (ServicioTipoImagenologia) SpringUtil
				.getBean("beanServicioTipoImagenologia");
		servicioEspImagenologia = (ServicioEspImagenologia) SpringUtil
				.getBean("beanServicioEspImagenologia");

		tipoImganeologias = servicioTipoImagenologia.buscarTodos('A');
		espImagenologiaCombo = servicioEspImagenologia.buscarTodos('A');

		paciente = new Paciente();
		imagenologia = new Imagenologia();
		usuario = SessionAdministrator.getLoggedUsuario();
		veterinario = usuario.getPersona().getVeterinario();
		dbFechaActual.setValue(HelperDate.now());
		
		sintomas2 = servicioSintoma.buscarTodos();
		cargarList(sintomas1, sintomas2);
		tipoPatologiaCombo = servicioTipoPatoligia.buscarTodos();
		tipoTratamientoCombo = servicioTipoTratamiento.buscarTodos();
		listTratamiento.setDisabled(true);
		listPatoligias.setDisabled(true);
		listEspecificacionImagenologia.setDisabled(true);
		visibilidadSecciones(true, false, false);
		actividadBotones(true, true, false, true, true, true);
		// AGREGAR AQUI
		txtHm.setValue(((Referencia) arg.get("objeto")).getHistorial()
				.getPaciente().getHistoriaMedica());
		btnBuscar.setVisible(false);
		onClick$btnBuscar();
	}

	public void cargar() {
		/*
		 * Aqui se ha de colocar cualquier codigo q sea neceario para precargar
		 * el servicio, este metodo se ejecutara justo luego de precionar buscar
		 * si el paciente es encontrado, claro esta PD: Si decean cambiar el
		 * titulo de la ventana, usar la siguiente linea
		 * winServicioImagenologia.setTitle("xxx Lo que mas les guste xxx");
		 */
		// AGREGAR AQUI
	}

	public void postCargar() {
		/*
		 * Aqui se ha de colocar cualquier codigo q sea neceario para modificar,
		 * llamar o preparar el servicio luego de guardar, este metodo se
		 * ejecutara justo luego de precionar Aceptar una vez guardado
		 * exitosamente, claro esta
		 */
		// AGREGAR AQUI
	}

	public void cargarList(List<Sintoma> primeraList, List<Sintoma> segundaList) {
		int mitad;
		mitad = segundaList.size() / 2;
		for (int x = 0; x < mitad; x++) {
			primeraList.add(segundaList.remove(0));
		}
	}

	// Codigo -> Botones y Secciones
	// --------------------------------------------------------------

	/*
	 * En esta seccion estaran todas las acciones de los botones y secciones del
	 * .zul, asi como el control que sea necesario ejercer sobre ellos.
	 */

	public void actividadBotones(boolean guardar, boolean cancelar,
			boolean buscar, boolean defuncion, boolean referencia,
			boolean historial) {
		btnGuardarPrincipal.setDisabled(guardar);
		btnCancelarPrincipal.setDisabled(cancelar);
		btnBuscar.setDisabled(buscar);
		btnDefuncion.setDisabled(defuncion);
		btnReferenciaMedica.setDisabled(referencia);
		btnHistorial.setDisabled(historial);
	}

	public void visibilidadSecciones(boolean norte, boolean sur,
			boolean contcentro) {
		blNorte.setVisible(norte);
		blSur.setVisible(sur);
		contCentro.setVisible(contcentro);
	}

	public void onClick$btnGuardarPrincipal() {
		/*
		 * Aqui se hara el llamado al metodo Guardar, pero si se da el caso y
		 * alguno necesita ejecutar alguna accion justo antes de guardarpuede
		 * hacerlo aki, asi como aplicar alguna condicion extra a dicho evento
		 */

		// AGREGAR AQUI
		guardar();
	}

	public void onClick$btnCancelarPrincipal() {
		paciente = new Paciente();
		veterinario = new Veterinario();
		dbFechaActual.setValue(HelperDate.now());
		visibilidadSecciones(true, false, false); // Ocultar sur y contenido
		// centro
		actividadBotones(true, true, false, true, true, true); // Desactivar
		// todos menos
		// buscar
		txtEdad.setValue("");
		txtNombrePaciente.setValue("");
		txtRaza.setValue("");
		txtEspecie.setValue("");
		txtSexo.setValue("");
		txtNombreResponsable.setValue("");
		txtCi.setValue("");
		txtHm.setValue("");
		txtHm.setDisabled(false);

	}

	public void onClick$btnBuscar() {
		if (txtHm.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			paciente = servicioPaciente.buscarUno(txtHm.getValue(), 'A');
			veterinario = usuario.getPersona().getVeterinario();
			if (paciente == null) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				txtHm.setDisabled(true);
				txtNombrePaciente.setValue(paciente.getNombre());
				txtRaza.setValue(paciente.getRaza().getNombre());
				txtEspecie
						.setValue(paciente.getRaza().getEspecie().getNombre());
				txtSexo.setValue(paciente.getSexo().getNombre());
				txtNombreResponsable.setValue(paciente.getResponsable()
						.getPersona().getNombre()
						+ " "
						+ paciente.getResponsable().getPersona().getApellido());
				txtCi.setValue(String.valueOf(paciente.getResponsable()
						.getCedula()));
				txtEdad.setValue(HelperDateAge.age(paciente.getFechaNac(), ' '));
				visibilidadSecciones(true, true, true);
				cargar();
				actividadBotones(false, false, true, false, false, false);
			}
		}
	}

	public void onSelect$listTratamientoTipo() {
		tratamientoCombo = servicioTratamiento.buscarCoincidenciasTipo(
				(TipoTratamiento) listTratamientoTipo.getSelectedItem()
						.getValue(), 'A');
		listTratamiento.setModel(new BindingListModelList(tratamientoCombo,
				false));
		listTratamiento.setDisabled(false);
	}
	
	public void onSelect$listTiposImagenologia() {
		espImagenologiaCombo = servicioEspImagenologia.buscarCoincidenciasTipo(
				(TipoImagenologia) listTiposImagenologia.getSelectedItem()
						.getValue(), 'A');
		listEspecificacionImagenologia.setModel(new BindingListModelList(espImagenologiaCombo,
				false));
		listEspecificacionImagenologia.setDisabled(false);
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

	public void onClick$btnDefuncion(){
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("paciente", paciente);
		Window win = (Window) Executions.createComponents("/sigma/vistas/servicios/servicios/ServicioDefuncion.zul", null, parametros);
		win.doHighlighted();
	}

	public void onClick$btnReferenciaMedica() {
		/*
		 * En construccion
		 */

	}

	public void onClick$btnHistorial() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("paciente", paciente);
		Window win = (Window) Executions.createComponents("/sigma/vistas/servicios/servicios/CatalogoHistorial.zul", null, parametros);
		win.doHighlighted();

	}
	
	
	public void onClick$btnMenosImagen() {
		quitarItemListbox(listListadoImagenologia,espImagenologias );
	}

	public void onClick$btnMasImagen() {
		agregarItemListbox(listListadoImagenologia, listEspecificacionImagenologia,
				espImagenologias, espImagenologiaCombo, "Imagenologias");
	}

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

	/*
	 * Aqui ira el codigo para el correcto funcionamiento del servicio, o lo q
	 * es o mimso, todo lo que no tenga lugar en las otras secciones
	 */

	public void guardar() {
		/*
		 * Es aqui donde comienza la complicacion, aqui iran todos los llamados,
		 * algoritmos o artimañas necesarias para que guarde el servicio PD: No
		 * hay mallor detalle con respecto a como hacer con algunas situacines,
		 * asi que si tiene dudas, dirijance al controlador de consulta
		 * general(ctrlWinConsultaGeneral.java) para guiarce, o pregunten
		 */

		List<?> lists;
		Set<?> sets;
		Set<Patologia> auxPatologias = new HashSet<Patologia>();
		Set<Sintoma> auxSintomas = new HashSet<Sintoma>();
		Set<Tratamiento> auxTratamientos = new HashSet<Tratamiento>();
		Set<EspImagenologia> auxEspImagenologia = new HashSet<EspImagenologia>();
		if (validar()) {
			if (!listListadoSintomas1.getItems().isEmpty()) {
				sets = listListadoSintomas1.getSelectedItems();
				for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxSintomas.add((Sintoma) item.getValue());
				}
				imagenologia.setSintomas(auxSintomas);
			}
			if (!listListadoSintomas2.getItems().isEmpty()) {
				sets = listListadoSintomas2.getSelectedItems();
				for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxSintomas.add((Sintoma) item.getValue());
				}
				/*
				 * Justo aki bede ir una copia de la siguiente linea adaptada a
				 * su propio servicio, sea cual sea el caso
				 * XxxxElQueCorrespondaXxxx.setSintomas(auxSintomas);
				 */
				// AGREGAR AQUI
				imagenologia.setSintomas(auxSintomas);
			}
			if (!listListadoPatologias.getItems().isEmpty()) {
				lists = listListadoPatologias.getItems();
				for (Iterator iterator = lists.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxPatologias.add((Patologia) item.getValue());
				}
				/*
				 * Justo aki bede ir una copia de la siguiente linea adaptada a
				 * su propio servicio, sea cual sea el caso
				 * XxxxElQueCorrespondaXxxx.setPatologias(auxPatologias);
				 */
				// AGREGAR AQUI
				imagenologia.setPatologias(auxPatologias);
			}
			if (!listListadoTratamiento.getItems().isEmpty()) {
				lists = listListadoTratamiento.getItems();
				for (Iterator iterator = lists.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxTratamientos.add((Tratamiento) item.getValue());
				}
				/*
				 * Justo aki bede ir una copia de la siguiente linea adaptada a
				 * su propio servicio, sea cual sea el caso
				 * XxxxElQueCorrespondaXxxx.setTratamientos(auxTratamientos);
				 */
				// AGREGAR AQUI
				imagenologia.setTratamientos(auxTratamientos);
			}
			if (!listListadoImagenologia.getItems().isEmpty()) {
				lists = listListadoImagenologia.getItems();
				for (Iterator iterator = lists.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxEspImagenologia.add((EspImagenologia) item.getValue());
				}
				/*
				 * Justo aki bede ir una copia de la siguiente linea adaptada a
				 * su propio servicio, sea cual sea el caso
				 * XxxxElQueCorrespondaXxxx.setTratamientos(auxTratamientos);
				 */
				// AGREGAR AQUI
				imagenologia.setEspImagenologias(auxEspImagenologia);
			}
			// AGREGAR AQUI
			try {
				/*
				 * Como nota particular, justo aki bede ir exclusivamente(Y nada
				 * mas, por excepcion, de los servicios que por la estructura de
				 * la base de datos tengan mas de una estructura modelo(Para
				 * Hibernate) que necesiten guardar) una copia de la siguiente
				 * linea adaptada a su propio servicio, sea cual sea el caso
				 * servicioXxxxElQueCorresondaXxxx
				 * .guardarXxxx(XxxxElQueCorrespondaXxxx);
				 */
				// AGREGAR AQUI
				imagenologia.setPaciente(paciente);
				imagenologia.setVeterinario(veterinario);
				imagenologia.setFecha(dbFechaActual.getValue());
				imagenologia.setHora(dbFechaActual.getValue());
				servicioImagenologia.guardarImagenologia(imagenologia);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						actividadBotones(true, false, true, false, false, false);
						postCargar();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}
		}
	}

	public boolean validar() {
		/*
		 * Si en Guardar inicio la complicacion, es aqui donde continua, aqui se
		 * validara cada campo que sea nesesario, de acuerdo al criterio de cada
		 * quien, claro esta, que si el campo es notNull, se debe validar o
		 * saltara un error obviamente. Esto no es mas q un gran conjunto de If
		 * anidados, los cuales deben estar ordenados desendientemente, es decir
		 * de acuerdo al orden en que serian llenados, ademas deben seguir el
		 * siguente patron } else if (xxXxxxNombreDeLaVariableZulXxx y su
		 * validacion correspondiente) { MensajeEmergente.mostrar("NOEMPTY",
		 * "\n XxxxNombreDeLaVariableZulXxx o alguna frace aluciva a la mismas",
		 * new MensajeListener() {
		 * 
		 * @Override public void alDestruir() {
		 * xxXxxxNombreDeLaVariableZulXxx.setFocus(true); } });
		 */

		boolean valido = false;
		/*
		 * Agregar una copia del codigo de validacion ya mostrado, iniciando con
		 * el "} else" y termiando con "});" y asi susesivamente por cada campo
		 * a validar
		 */
		// AGREGAR AQUI

		if (txtDiagnosticoDefinitivo.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Diagnostico Definitivo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtDiagnosticoDefinitivo.setFocus(true);
						}
					});
		} else

		if (txtTratamientoEnviado.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtTratamientoEnviado.setFocus(true);
						}
					});
		} else

		if (txtConclusion.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Conclusion",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtConclusion.setFocus(true);
						}
					});
		} else

		if (txtHalazgos.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Halazgos",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtHalazgos.setFocus(true);
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

	// Codigo -> Getters y Setters
		// ----------------------------------------------------------------

		/*
		 * Esta mas que claro
		 */
	
	public Window getWinServicioImagenologia() {
		return winServicioImagenologia;
	}

	public void setWinServicioImagenologia(Window winServicioImagenologia) {
		this.winServicioImagenologia = winServicioImagenologia;
	}

	public Button getBtnBuscar() {
		return btnBuscar;
	}

	public void setBtnBuscar(Button btnBuscar) {
		this.btnBuscar = btnBuscar;
	}

	public Button getBtnDefuncion() {
		return btnDefuncion;
	}

	public void setBtnDefuncion(Button btnDefuncion) {
		this.btnDefuncion = btnDefuncion;
	}

	public Button getBtnReferenciaMedica() {
		return btnReferenciaMedica;
	}

	public void setBtnReferenciaMedica(Button btnReferenciaMedica) {
		this.btnReferenciaMedica = btnReferenciaMedica;
	}

	public Button getBtnHistorial() {
		return btnHistorial;
	}

	public void setBtnHistorial(Button btnHistorial) {
		this.btnHistorial = btnHistorial;
	}

	public Button getBtnCancelarPrincipal() {
		return btnCancelarPrincipal;
	}

	public void setBtnCancelarPrincipal(Button btnCancelarPrincipal) {
		this.btnCancelarPrincipal = btnCancelarPrincipal;
	}

	public Button getBtnGuardarPrincipal() {
		return btnGuardarPrincipal;
	}

	public void setBtnGuardarPrincipal(Button btnGuardarPrincipal) {
		this.btnGuardarPrincipal = btnGuardarPrincipal;
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

	public Button getBtnMasImagen() {
		return btnMasImagen;
	}

	public void setBtnMasImagen(Button btnMasImagen) {
		this.btnMasImagen = btnMasImagen;
	}

	public Button getBtnMenosImagen() {
		return btnMenosImagen;
	}

	public void setBtnMenosImagen(Button btnMenosImagen) {
		this.btnMenosImagen = btnMenosImagen;
	}

	public Datebox getDbFechaActual() {
		return dbFechaActual;
	}

	public void setDbFechaActual(Datebox dbFechaActual) {
		this.dbFechaActual = dbFechaActual;
	}

	public Textbox getTxtHalazgos() {
		return txtHalazgos;
	}

	public void setTxtHalazgos(Textbox txtHalazgos) {
		this.txtHalazgos = txtHalazgos;
	}

	public Textbox getTxtConclusion() {
		return txtConclusion;
	}

	public void setTxtConclusion(Textbox txtConclusion) {
		this.txtConclusion = txtConclusion;
	}

	public Textbox getTxtHm() {
		return txtHm;
	}

	public void setTxtHm(Textbox txtHm) {
		this.txtHm = txtHm;
	}

	public Textbox getTxtNombrePaciente() {
		return txtNombrePaciente;
	}

	public void setTxtNombrePaciente(Textbox txtNombrePaciente) {
		this.txtNombrePaciente = txtNombrePaciente;
	}

	public Textbox getTxtRaza() {
		return txtRaza;
	}

	public void setTxtRaza(Textbox txtRaza) {
		this.txtRaza = txtRaza;
	}

	public Textbox getTxtEspecie() {
		return txtEspecie;
	}

	public void setTxtEspecie(Textbox txtEspecie) {
		this.txtEspecie = txtEspecie;
	}

	public Textbox getTxtSexo() {
		return txtSexo;
	}

	public void setTxtSexo(Textbox txtSexo) {
		this.txtSexo = txtSexo;
	}

	public Textbox getTxtEdad() {
		return txtEdad;
	}

	public void setTxtEdad(Textbox txtEdad) {
		this.txtEdad = txtEdad;
	}

	public Textbox getTxtNombreResponsable() {
		return txtNombreResponsable;
	}

	public void setTxtNombreResponsable(Textbox txtNombreResponsable) {
		this.txtNombreResponsable = txtNombreResponsable;
	}

	public Textbox getTxtCi() {
		return txtCi;
	}

	public void setTxtCi(Textbox txtCi) {
		this.txtCi = txtCi;
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

	public Textbox getTxtPatologiaComentario() {
		return txtPatologiaComentario;
	}

	public void setTxtPatologiaComentario(Textbox txtPatologiaComentario) {
		this.txtPatologiaComentario = txtPatologiaComentario;
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

	public void setTxtDiagnosticoProcedimiento(Textbox txtDiagnosticoProcedimiento) {
		this.txtDiagnosticoProcedimiento = txtDiagnosticoProcedimiento;
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

	public Listbox getListPatologiaTipo() {
		return listPatologiaTipo;
	}

	public void setListPatologiaTipo(Listbox listPatologiaTipo) {
		this.listPatologiaTipo = listPatologiaTipo;
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

	public Listbox getListListadoImagenologia() {
		return listListadoImagenologia;
	}

	public void setListListadoImagenologia(Listbox listListadoImagenologia) {
		this.listListadoImagenologia = listListadoImagenologia;
	}

	public Listbox getListTiposImagenologia() {
		return listTiposImagenologia;
	}

	public void setListTiposImagenologia(Listbox listTiposImagenologia) {
		this.listTiposImagenologia = listTiposImagenologia;
	}

	public Listbox getListEspecificacionImagenologia() {
		return listEspecificacionImagenologia;
	}

	public void setListEspecificacionImagenologia(
			Listbox listEspecificacionImagenologia) {
		this.listEspecificacionImagenologia = listEspecificacionImagenologia;
	}

	public North getBlNorte() {
		return blNorte;
	}

	public void setBlNorte(North blNorte) {
		this.blNorte = blNorte;
	}

	public Center getBlCentro() {
		return blCentro;
	}

	public void setBlCentro(Center blCentro) {
		this.blCentro = blCentro;
	}

	public South getBlSur() {
		return blSur;
	}

	public void setBlSur(South blSur) {
		this.blSur = blSur;
	}

	public Div getContCentro() {
		return contCentro;
	}

	public void setContCentro(Div contCentro) {
		this.contCentro = contCentro;
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

	public ServicioImagenologia getServicioImagenologia() {
		return servicioImagenologia;
	}

	public void setServicioImagenologia(ServicioImagenologia servicioImagenologia) {
		this.servicioImagenologia = servicioImagenologia;
	}

	public ServicioTipoImagenologia getServicioTipoImagenologia() {
		return servicioTipoImagenologia;
	}

	public void setServicioTipoImagenologia(
			ServicioTipoImagenologia servicioTipoImagenologia) {
		this.servicioTipoImagenologia = servicioTipoImagenologia;
	}

	public ServicioEspImagenologia getServicioEspImagenologia() {
		return servicioEspImagenologia;
	}

	public void setServicioEspImagenologia(
			ServicioEspImagenologia servicioEspImagenologia) {
		this.servicioEspImagenologia = servicioEspImagenologia;
	}

	public ServicioPaciente getServicioPaciente() {
		return servicioPaciente;
	}

	public void setServicioPaciente(ServicioPaciente servicioPaciente) {
		this.servicioPaciente = servicioPaciente;
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

	public void setServicioTipoPatoligia(ServicioTipoPatologia servicioTipoPatoligia) {
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

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Veterinario getVeterinario() {
		return veterinario;
	}

	public void setVeterinario(Veterinario veterinario) {
		this.veterinario = veterinario;
	}

	public Imagenologia getImagenologia() {
		return imagenologia;
	}

	public void setImagenologia(Imagenologia imagenologia) {
		this.imagenologia = imagenologia;
	}

	public List<TipoTratamiento> getTipoTratamientoCombo() {
		return tipoTratamientoCombo;
	}

	public void setTipoTratamientoCombo(List<TipoTratamiento> tipoTratamientoCombo) {
		this.tipoTratamientoCombo = tipoTratamientoCombo;
	}

	public List<Tratamiento> getTratamientoCombo() {
		return tratamientoCombo;
	}

	public void setTratamientoCombo(List<Tratamiento> tratamientoCombo) {
		this.tratamientoCombo = tratamientoCombo;
	}

	public List<Tratamiento> getTratamientos() {
		return tratamientos;
	}

	public void setTratamientos(List<Tratamiento> tratamientos) {
		this.tratamientos = tratamientos;
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

	public List<Patologia> getPatologias() {
		return patologias;
	}

	public void setPatologias(List<Patologia> patologias) {
		this.patologias = patologias;
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

	public List<TipoImagenologia> getTipoImganeologias() {
		return tipoImganeologias;
	}

	public void setTipoImganeologias(List<TipoImagenologia> tipoImganeologias) {
		this.tipoImganeologias = tipoImganeologias;
	}

	public List<EspImagenologia> getEspImagenologias() {
		return espImagenologias;
	}

	public void setEspImagenologias(List<EspImagenologia> espImagenologias) {
		this.espImagenologias = espImagenologias;
	}

	public List<EspImagenologia> getEspImagenologiaCombo() {
		return espImagenologiaCombo;
	}

	public void setEspImagenologiaCombo(List<EspImagenologia> espImagenologiaCombo) {
		this.espImagenologiaCombo = espImagenologiaCombo;
	}

	public MensajeListener getAsignarFocusBuscar() {
		return asignarFocusBuscar;
	}

	public void setAsignarFocusBuscar(MensajeListener asignarFocusBuscar) {
		this.asignarFocusBuscar = asignarFocusBuscar;
	}


}

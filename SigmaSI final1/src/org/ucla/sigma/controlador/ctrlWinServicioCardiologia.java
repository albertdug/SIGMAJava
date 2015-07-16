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
import org.ucla.sigma.modelo.Auscultacion;
import org.ucla.sigma.modelo.Cardiaca;
import org.ucla.sigma.modelo.Cardiologia;
import org.ucla.sigma.modelo.Membrana;
import org.ucla.sigma.modelo.Paciente;
import org.ucla.sigma.modelo.PalpacionAbdominal;
import org.ucla.sigma.modelo.Patologia;
import org.ucla.sigma.modelo.Patron;
import org.ucla.sigma.modelo.Pulmonar;
import org.ucla.sigma.modelo.Referencia;
import org.ucla.sigma.modelo.Ritmo;
import org.ucla.sigma.modelo.Segmento;
import org.ucla.sigma.modelo.Silueta;
import org.ucla.sigma.modelo.Sintoma;
import org.ucla.sigma.modelo.TipoPatologia;
import org.ucla.sigma.modelo.TipoTratamiento;
import org.ucla.sigma.modelo.Tratamiento;
import org.ucla.sigma.modelo.Usuario;
import org.ucla.sigma.modelo.Veterinario;
import org.ucla.sigma.servicio.ServicioAuscultacion;
import org.ucla.sigma.servicio.ServicioCardiaca;
import org.ucla.sigma.servicio.ServicioCardiologia;
import org.ucla.sigma.servicio.ServicioMembrana;
import org.ucla.sigma.servicio.ServicioPaciente;
import org.ucla.sigma.servicio.ServicioPalpacionAbdominal;
import org.ucla.sigma.servicio.ServicioPatologia;
import org.ucla.sigma.servicio.ServicioPatron;
import org.ucla.sigma.servicio.ServicioPulmonar;
import org.ucla.sigma.servicio.ServicioRitmo;
import org.ucla.sigma.servicio.ServicioSegmento;
import org.ucla.sigma.servicio.ServicioSilueta;
import org.ucla.sigma.servicio.ServicioSintoma;
import org.ucla.sigma.servicio.ServicioTipoPatologia;
import org.ucla.sigma.servicio.ServicioTipoTratamiento;
import org.ucla.sigma.servicio.ServicioTratamiento;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Center;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.North;
import org.zkoss.zul.South;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinServicioCardiologia extends GenericForwardComposer {

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

	private Window winServicioCardiologia;
	private Textbox txtTratamientoCaridaca;
	private Textbox txtDiagnosticoCardiaca;
	private Textbox txtHallazgos;
	private Spinner spOndaSegMinV;
	private Spinner spOndaSeg;
	private Spinner spIntervaloPRSeg;
	private Spinner spIntervaloPR;
	private Spinner spOndaPSegMin;
	private Spinner spOndaPSeg;
	private Spinner spIntervaloSeg;
	private Spinner spComplejoQRSMinV;
	private Spinner spComplejoQRSSeg;
	private Decimalbox dbOndaT;
	private Decimalbox dbIntervaloQT;
	private Decimalbox dbComplejoQRS;
	private Decimalbox dbOndaP;
	private Decimalbox dbEjeElectricoM;
	private Decimalbox dbFrecFemoral;
	private Decimalbox dbFV;
	private Decimalbox dbFC;
	private Decimalbox dbFrecCardiaca;
	private Decimalbox dbICV;
	private Decimalbox dbPulsoFemoral;

	private Listbox listSilueta;
	private Listbox listPatron;
	private Listbox listPalpacionAbdominal;
	private Listbox listCardiaca;
	private Listbox listPulmonar;
	private Listbox listAuscultacion;
	private Listbox listMembrana;
	private Listbox listRitmo;
	private Listbox listSegmento;

	private Tabbox tb2;

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
	private Datebox dbFechaActual;
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

	private ServicioCardiologia servicioCardiologia;

	private List<Membrana> membranas = new ArrayList<Membrana>();
	private Membrana membrana;
	private ServicioMembrana servicioMembrana;

	private Pulmonar pulmonar;
	private ServicioPulmonar servicioPulmonar;
	private List<Pulmonar> pulmonars = new ArrayList<Pulmonar>();

	private List<Patron> patrons = new ArrayList<Patron>();
	private ServicioPatron servicioPatron;
	private Patron patron;

	private List<Silueta> siluetas = new ArrayList<Silueta>();
	private ServicioSilueta servicioSilueta;
	private Silueta silueta;

	private Cardiaca cardiaca;
	private List<Cardiaca> cardiacas = new ArrayList<Cardiaca>();
	private ServicioCardiaca servicioCardiaca;

	private PalpacionAbdominal palpacionabdominal;
	private List<PalpacionAbdominal> palpacionabdominals = new ArrayList<PalpacionAbdominal>();
	private ServicioPalpacionAbdominal servicioPalpacionAbdominal;

	private Auscultacion auscultacion;
	private List<Auscultacion> auscultacions = new ArrayList<Auscultacion>();
	private ServicioAuscultacion servicioAuscultacion;

	private Ritmo ritmo;
	private List<Ritmo> ritmos = new ArrayList<Ritmo>();
	private ServicioRitmo servicioRitmo;

	private Segmento segmento;
	private List<Segmento> segmentos = new ArrayList<Segmento>();
	private ServicioSegmento servicioSegmento;

	private ServicioPaciente servicioPaciente;
	private ServicioSintoma servicioSintoma;
	private ServicioTipoPatologia servicioTipoPatoligia;
	private ServicioPatologia servicioPatologia;
	private ServicioTipoTratamiento servicioTipoTratamiento;
	private ServicioTratamiento servicioTratamiento;
	private Paciente paciente;
	private Usuario usuario;
	private Veterinario veterinario;
	private Cardiologia cardiologia;
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
		winServicioCardiologia.setAttribute(comp.getId() + "ctrl", this);
		servicioCardiologia = (ServicioCardiologia) SpringUtil
				.getBean("beanServicioCardiologia");
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
		servicioMembrana = (ServicioMembrana) SpringUtil
				.getBean("beanServicioMembrana");
		servicioPulmonar = (ServicioPulmonar) SpringUtil
				.getBean("beanServicioPulmonar");
		servicioPatron = (ServicioPatron) SpringUtil
				.getBean("beanServicioPatron");
		servicioSilueta = (ServicioSilueta) SpringUtil
				.getBean("beanServicioSilueta");
		servicioCardiaca = (ServicioCardiaca) SpringUtil
				.getBean("beanServicioCardiaca");
		servicioPalpacionAbdominal = (ServicioPalpacionAbdominal) SpringUtil
				.getBean("beanServicioPalpacionAbdominal");
		servicioAuscultacion = (ServicioAuscultacion) SpringUtil
				.getBean("beanServicioAuscultacion");
		servicioSegmento = (ServicioSegmento) SpringUtil
				.getBean("beanServicioSegmento");
		servicioRitmo = (ServicioRitmo) SpringUtil.getBean("beanServicioRitmo");
		membranas = servicioMembrana.buscarTodos('A');
		pulmonars = servicioPulmonar.buscarTodos('A');
		patrons = servicioPatron.buscarTodos('A');
		siluetas = servicioSilueta.buscarTodos('A');
		cardiacas = servicioCardiaca.buscarTodos('A');
		palpacionabdominals = servicioPalpacionAbdominal.buscarTodos('A');
		auscultacions = servicioAuscultacion.buscarTodos('A');
		segmentos = servicioSegmento.buscarTodos('A');
		ritmos = servicioRitmo.buscarTodos('A');
		paciente = new Paciente();
		cardiologia = new Cardiologia();

		usuario = SessionAdministrator.getLoggedUsuario();
		veterinario = usuario.getPersona().getVeterinario();

		dbFechaActual.setValue(HelperDate.now());
		sintomas2 = servicioSintoma.buscarTodos('A');
		cargarList(sintomas1, sintomas2);
		tipoPatologiaCombo = servicioTipoPatoligia.buscarTodos('A');
		tipoTratamientoCombo = servicioTipoTratamiento.buscarTodos('A');
		listTratamiento.setDisabled(true);
		listPatoligias.setDisabled(true);
		visibilidadSecciones(true, false, false); // Ocultar sur y contenido
		// centro
		actividadBotones(true, true, false, true, true, true); // Desactivar
		// todos menos
		// buscar
		// AGREGAR AQUI
		txtHm.setValue(((Referencia) arg.get("objeto")).getHistorial()
				.getPaciente().getHistoriaMedica());
		btnBuscar.setVisible(false);
		onClick$btnBuscar();
		System.out.println(dbFrecFemoral.getValue());
	}

	public void cargar() {
		/*
		 * Aqui se ha de colocar cualquier codigo q sea neceario para precargar
		 * el servicio, este metodo se ejecutara justo luego de precionar buscar
		 * si el paciente es encontrado, claro esta PD: Si decean cambiar el
		 * titulo de la ventana, usar la siguiente linea
		 * winServicioCardiologia.setTitle("xxx Lo que mas les guste xxx");
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
				visibilidadSecciones(true, true, true); // Mostrar sur y
				// contenido centro
				cargar();
				actividadBotones(false, false, true, false, false, false); // Activar
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
		if (validar()) {
			cardiologia.setAuscultacion((Auscultacion) listAuscultacion
					.getSelectedItem().getValue());
			cardiologia.setCardiaca((Cardiaca) listCardiaca.getSelectedItem()
					.getValue());
			cardiologia.setSilueta((Silueta) listSilueta.getSelectedItem()
					.getValue());
			cardiologia.setPatron((Patron) listPatron.getSelectedItem()
					.getValue());
			cardiologia
					.setPalpacionAbdominal((PalpacionAbdominal) listPalpacionAbdominal
							.getSelectedItem().getValue());
			cardiologia.setPulmonar((Pulmonar) listPulmonar.getSelectedItem()
					.getValue());
			cardiologia.setSegmento((Segmento) listSegmento.getSelectedItem()
					.getValue());
			cardiologia
					.setRitmo((Ritmo) listRitmo.getSelectedItem().getValue());
			cardiologia.setMembrana((Membrana) listMembrana.getSelectedItem()
					.getValue());

			if (!listListadoSintomas1.getItems().isEmpty()) {
				sets = listListadoSintomas1.getSelectedItems();
				for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxSintomas.add((Sintoma) item.getValue());
				}
				cardiologia.setSintomas(auxSintomas);
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
				cardiologia.setSintomas(auxSintomas);
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
				cardiologia.setPatologias(auxPatologias);
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
				cardiologia.setTratamientos(auxTratamientos);
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
				cardiologia.setPaciente(paciente);
				cardiologia.setVeterinario(veterinario);
				cardiologia.setFecha(dbFechaActual.getValue());
				cardiologia.setHora(dbFechaActual.getValue());
				servicioCardiologia.guardarCardiologia(cardiologia);
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

		if (listSilueta.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listSilueta.setFocus(true);
						}
					});
		} else

		if (listSegmento.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listSegmento.setFocus(true);
						}
					});
		} else

		if (listRitmo.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listRitmo.setFocus(true);
						}
					});
		} else

		if (listMembrana.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listMembrana.setFocus(true);
						}
					});
		} else

		if (listAuscultacion.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listAuscultacion.setFocus(true);
						}
					});
		} else

		if (listPulmonar.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listPulmonar.setFocus(true);
						}
					});
		} else

		if (listPalpacionAbdominal.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listPalpacionAbdominal.setFocus(true);
						}
					});
		} else

		if (listPatron.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listPatron.setFocus(true);
						}
					});
		} else

		if (listCardiaca.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listCardiaca.setFocus(true);
						}
					});
		} else

		if (dbFC.getValue()== null) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							dbFC.setFocus(true);
						}
					});
		} else

		if (dbFV.getValue()== null) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							dbFV.setFocus(true);
						}
					});
		} else

		if (dbFrecFemoral.getValue()== null) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							dbFrecFemoral.setFocus(true);
						}
					});
		} else

		if (dbPulsoFemoral.getValue() == null) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							dbPulsoFemoral.setFocus(true);
						}
					});
		} else

		if (dbICV.getValue()== null) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							dbICV.setFocus(true);
						}
					});
		} else

		if (dbFrecCardiaca.getValue()== null) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							dbFrecCardiaca.setFocus(true);
						}
					});
		} else

		if (dbEjeElectricoM.getValue()== null) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							dbEjeElectricoM.setFocus(true);
						}
					});
		} else

		if (dbOndaP.getValue()== null) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							dbOndaP.setFocus(true);
						}
					});
		} else

		if (spOndaPSeg.getValue()== null) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spOndaPSeg.setFocus(true);
						}
					});
		} else

		if (spOndaPSegMin.getValue()== null) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spOndaPSegMin.setFocus(true);
						}
					});
		} else

		if (spIntervaloPR.getValue()== null) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spIntervaloPR.setFocus(true);
						}
					});
		} else

		if (spIntervaloPRSeg.getValue()== null) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spIntervaloPRSeg.setFocus(true);
						}
					});
		} else

		if (dbComplejoQRS.getValue()== null) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							dbComplejoQRS.setFocus(true);
						}
					});
		} else

		if (spComplejoQRSMinV.getValue()== null) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spComplejoQRSMinV.setFocus(true);
						}
					});
		} else

		if (spComplejoQRSSeg.getValue()== null) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spComplejoQRSSeg.setFocus(true);
						}
					});
		} else

		if (dbIntervaloQT.getValue()== null) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							dbIntervaloQT.setFocus(true);
						}
					});
		} else

		if (spIntervaloSeg.getValue()== null) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spIntervaloSeg.setFocus(true);
						}
					});
		} else

		if (dbOndaT.getValue()== null) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							dbOndaT.setFocus(true);
						}
					});
		} else

		if (spOndaSeg.getValue()== null) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spOndaSeg.setFocus(true);
						}
					});
		} else

		if (spOndaSegMinV.getValue()== null) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spOndaSegMinV.setFocus(true);
						}
					});
		} else

		if (txtHallazgos.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtHallazgos.setFocus(true);
						}
					});
		} else

		if (txtDiagnosticoCardiaca.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtDiagnosticoCardiaca.setFocus(true);
						}
					});
		} else

		if (txtTratamientoCaridaca.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtTratamientoCaridaca.setFocus(true);
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

	public Window getWinServicioConsultaGeneral() {
		return winServicioCardiologia;
	}

	public void setWinServicioConsultaGeneral(Window winServicioConsultaGeneral) {
		this.winServicioCardiologia = winServicioConsultaGeneral;
	}

	public Datebox getDbFechaActual() {
		return dbFechaActual;
	}

	public void setDbFechaActual(Datebox dbFechaActual) {
		this.dbFechaActual = dbFechaActual;
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

	public MensajeListener getAsignarFocusBuscar() {
		return asignarFocusBuscar;
	}

	public void setAsignarFocusBuscar(MensajeListener asignarFocusBuscar) {
		this.asignarFocusBuscar = asignarFocusBuscar;
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

	public void setTxtDiagnosticoProcedimiento(
			Textbox txtDiagnosticoProcedimiento) {
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

	public Window getWinServicioCardiologia() {
		return winServicioCardiologia;
	}

	public void setWinServicioCardiologia(Window winServicioCardiologia) {
		this.winServicioCardiologia = winServicioCardiologia;
	}

	public Textbox getTxtTratamientoCaridaca() {
		return txtTratamientoCaridaca;
	}

	public void setTxtTratamientoCaridaca(Textbox txtTratamientoCaridaca) {
		this.txtTratamientoCaridaca = txtTratamientoCaridaca;
	}

	public Textbox getTxtDiagnosticoCardiaca() {
		return txtDiagnosticoCardiaca;
	}

	public void setTxtDiagnosticoCardiaca(Textbox txtDiagnosticoCardiaca) {
		this.txtDiagnosticoCardiaca = txtDiagnosticoCardiaca;
	}

	public Textbox getTxtHallazgos() {
		return txtHallazgos;
	}

	public void setTxtHallazgos(Textbox txtHallazgos) {
		this.txtHallazgos = txtHallazgos;
	}

	public Listbox getListSegmento() {
		return listSegmento;
	}

	public void setListSegmento(Listbox listSegmento) {
		this.listSegmento = listSegmento;
	}

	public Spinner getSpOndaSegMinV() {
		return spOndaSegMinV;
	}

	public void setSpOndaSegMinV(Spinner spOndaSegMinV) {
		this.spOndaSegMinV = spOndaSegMinV;
	}

	public Spinner getSpOndaSeg() {
		return spOndaSeg;
	}

	public void setSpOndaSeg(Spinner spOndaSeg) {
		this.spOndaSeg = spOndaSeg;
	}

	public Decimalbox getDbOndaT() {
		return dbOndaT;
	}

	public void setDbOndaT(Decimalbox dbOndaT) {
		this.dbOndaT = dbOndaT;
	}

	public Spinner getSpIntervaloSeg() {
		return spIntervaloSeg;
	}

	public void setSpIntervaloSeg(Spinner spIntervaloSeg) {
		this.spIntervaloSeg = spIntervaloSeg;
	}

	public Decimalbox getDbIntervaloQT() {
		return dbIntervaloQT;
	}

	public void setDbIntervaloQT(Decimalbox dbIntervaloQT) {
		this.dbIntervaloQT = dbIntervaloQT;
	}

	public Spinner getSpComplejoQRSMinV() {
		return spComplejoQRSMinV;
	}

	public void setSpComplejoQRSMinV(Spinner spComplejoQRSMinV) {
		this.spComplejoQRSMinV = spComplejoQRSMinV;
	}

	public Spinner getSpComplejoQRSSeg() {
		return spComplejoQRSSeg;
	}

	public void setSpComplejoQRSSeg(Spinner spComplejoQRSSeg) {
		this.spComplejoQRSSeg = spComplejoQRSSeg;
	}

	public Decimalbox getDbComplejoQRS() {
		return dbComplejoQRS;
	}

	public void setDbComplejoQRS(Decimalbox dbComplejoQRS) {
		this.dbComplejoQRS = dbComplejoQRS;
	}

	public Spinner getSpIntervaloPRSeg() {
		return spIntervaloPRSeg;
	}

	public void setSpIntervaloPRSeg(Spinner spIntervaloPRSeg) {
		this.spIntervaloPRSeg = spIntervaloPRSeg;
	}

	public Spinner getSpIntervaloPR() {
		return spIntervaloPR;
	}

	public void setSpIntervaloPR(Spinner spIntervaloPR) {
		this.spIntervaloPR = spIntervaloPR;
	}

	public Spinner getSpOndaPSegMin() {
		return spOndaPSegMin;
	}

	public void setSpOndaPSegMin(Spinner spOndaPSegMin) {
		this.spOndaPSegMin = spOndaPSegMin;
	}

	public Spinner getSpOndaPSeg() {
		return spOndaPSeg;
	}

	public void setSpOndaPSeg(Spinner spOndaPSeg) {
		this.spOndaPSeg = spOndaPSeg;
	}

	public Decimalbox getDbOndaP() {
		return dbOndaP;
	}

	public void setDbOndaP(Decimalbox dbOndaP) {
		this.dbOndaP = dbOndaP;
	}

	public Decimalbox getDbEjeElectricoM() {
		return dbEjeElectricoM;
	}

	public void setDbEjeElectricoM(Decimalbox dbEjeElectricoM) {
		this.dbEjeElectricoM = dbEjeElectricoM;
	}

	public Listbox getListRitmo() {
		return listRitmo;
	}

	public void setListRitmo(Listbox listRitmo) {
		this.listRitmo = listRitmo;
	}

	public Decimalbox getDbFrecCardiaca() {
		return dbFrecCardiaca;
	}

	public void setDbFrecCardiaca(Decimalbox dbFrecCardiaca) {
		this.dbFrecCardiaca = dbFrecCardiaca;
	}

	public Decimalbox getDbICV() {
		return dbICV;
	}

	public void setDbICV(Decimalbox dbICV) {
		this.dbICV = dbICV;
	}

	public Listbox getListSilueta() {
		return listSilueta;
	}

	public void setListSilueta(Listbox listSilueta) {
		this.listSilueta = listSilueta;
	}

	public Listbox getListPatron() {
		return listPatron;
	}

	public void setListPatron(Listbox listPatron) {
		this.listPatron = listPatron;
	}

	public Listbox getListCardiaca() {
		return listCardiaca;
	}

	public void setListCardiaca(Listbox listCardiaca) {
		this.listCardiaca = listCardiaca;
	}

	public Listbox getListPulmonar() {
		return listPulmonar;
	}

	public void setListPulmonar(Listbox listPulmonar) {
		this.listPulmonar = listPulmonar;
	}

	public Listbox getListAuscultacion() {
		return listAuscultacion;
	}

	public void setListAuscultacion(Listbox listAuscultacion) {
		this.listAuscultacion = listAuscultacion;
	}

	public Decimalbox getDbPulsoFemoral() {
		return dbPulsoFemoral;
	}

	public void setDbPulsoFemoral(Decimalbox dbPulsoFemoral) {
		this.dbPulsoFemoral = dbPulsoFemoral;
	}

	public Listbox getListMembrana() {
		return listMembrana;
	}

	public void setListMembrana(Listbox listMembrana) {
		this.listMembrana = listMembrana;
	}

	public Decimalbox getDbFrecFemoral() {
		return dbFrecFemoral;
	}

	public void setDbFrecFemoral(Decimalbox dbFrecFemoral) {
		this.dbFrecFemoral = dbFrecFemoral;
	}

	public Decimalbox getDbFV() {
		return dbFV;
	}

	public void setDbFV(Decimalbox dbFV) {
		this.dbFV = dbFV;
	}

	public Decimalbox getDbFC() {
		return dbFC;
	}

	public void setDbFC(Decimalbox dbFC) {
		this.dbFC = dbFC;
	}

	public Tabbox getTb2() {
		return tb2;
	}

	public void setTb2(Tabbox tb2) {
		this.tb2 = tb2;
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

	public ServicioCardiologia getServicioCardiologia() {
		return servicioCardiologia;
	}

	public void setServicioCardiologia(ServicioCardiologia servicioCardiologia) {
		this.servicioCardiologia = servicioCardiologia;
	}

	public List<Membrana> getMembranas() {
		return membranas;
	}

	public void setMembranas(List<Membrana> membranas) {
		this.membranas = membranas;
	}

	public Membrana getMembrana() {
		return membrana;
	}

	public void setMembrana(Membrana membrana) {
		this.membrana = membrana;
	}

	public ServicioMembrana getServicioMembrana() {
		return servicioMembrana;
	}

	public void setServicioMembrana(ServicioMembrana servicioMembrana) {
		this.servicioMembrana = servicioMembrana;
	}

	public Pulmonar getPulmonar() {
		return pulmonar;
	}

	public void setPulmonar(Pulmonar pulmonar) {
		this.pulmonar = pulmonar;
	}

	public ServicioPulmonar getServicioPulmonar() {
		return servicioPulmonar;
	}

	public void setServicioPulmonar(ServicioPulmonar servicioPulmonar) {
		this.servicioPulmonar = servicioPulmonar;
	}

	public List<Pulmonar> getPulmonars() {
		return pulmonars;
	}

	public void setPulmonars(List<Pulmonar> pulmonars) {
		this.pulmonars = pulmonars;
	}

	public List<Patron> getPatrons() {
		return patrons;
	}

	public void setPatrons(List<Patron> patrons) {
		this.patrons = patrons;
	}

	public ServicioPatron getServicioPatron() {
		return servicioPatron;
	}

	public void setServicioPatron(ServicioPatron servicioPatron) {
		this.servicioPatron = servicioPatron;
	}

	public Patron getPatron() {
		return patron;
	}

	public void setPatron(Patron patron) {
		this.patron = patron;
	}

	public List<Silueta> getSiluetas() {
		return siluetas;
	}

	public void setSiluetas(List<Silueta> siluetas) {
		this.siluetas = siluetas;
	}

	public ServicioSilueta getServicioSilueta() {
		return servicioSilueta;
	}

	public void setServicioSilueta(ServicioSilueta servicioSilueta) {
		this.servicioSilueta = servicioSilueta;
	}

	public Silueta getSilueta() {
		return silueta;
	}

	public void setSilueta(Silueta silueta) {
		this.silueta = silueta;
	}

	public Cardiaca getCardiaca() {
		return cardiaca;
	}

	public void setCardiaca(Cardiaca cardiaca) {
		this.cardiaca = cardiaca;
	}

	public List<Cardiaca> getCardiacas() {
		return cardiacas;
	}

	public void setCardiacas(List<Cardiaca> cardiacas) {
		this.cardiacas = cardiacas;
	}

	public ServicioCardiaca getServicioCardiaca() {
		return servicioCardiaca;
	}

	public void setServicioCardiaca(ServicioCardiaca servicioCardiaca) {
		this.servicioCardiaca = servicioCardiaca;
	}

	public PalpacionAbdominal getPalpacionabdominal() {
		return palpacionabdominal;
	}

	public void setPalpacionabdominal(PalpacionAbdominal palpacionabdominal) {
		this.palpacionabdominal = palpacionabdominal;
	}

	public Listbox getListPalpacionAbdominal() {
		return listPalpacionAbdominal;
	}

	public void setListPalpacionAbdominal(Listbox listPalpacionAbdominal) {
		this.listPalpacionAbdominal = listPalpacionAbdominal;
	}

	public List<PalpacionAbdominal> getPalpacionabdominals() {
		return palpacionabdominals;
	}

	public void setPalpacionabdominals(
			List<PalpacionAbdominal> palpacionabdominals) {
		this.palpacionabdominals = palpacionabdominals;
	}

	public ServicioPalpacionAbdominal getServicioPalpacionAbdominal() {
		return servicioPalpacionAbdominal;
	}

	public void setServicioPalpacionAbdominal(
			ServicioPalpacionAbdominal servicioPalpacionAbdominal) {
		this.servicioPalpacionAbdominal = servicioPalpacionAbdominal;
	}

	public Auscultacion getAuscultacion() {
		return auscultacion;
	}

	public void setAuscultacion(Auscultacion auscultacion) {
		this.auscultacion = auscultacion;
	}

	public List<Auscultacion> getAuscultacions() {
		return auscultacions;
	}

	public void setAuscultacions(List<Auscultacion> auscultacions) {
		this.auscultacions = auscultacions;
	}

	public ServicioAuscultacion getServicioAuscultacion() {
		return servicioAuscultacion;
	}

	public void setServicioAuscultacion(
			ServicioAuscultacion servicioAuscultacion) {
		this.servicioAuscultacion = servicioAuscultacion;
	}

	public Ritmo getRitmo() {
		return ritmo;
	}

	public void setRitmo(Ritmo ritmo) {
		this.ritmo = ritmo;
	}

	public List<Ritmo> getRitmos() {
		return ritmos;
	}

	public void setRitmos(List<Ritmo> ritmos) {
		this.ritmos = ritmos;
	}

	public ServicioRitmo getServicioRitmo() {
		return servicioRitmo;
	}

	public void setServicioRitmo(ServicioRitmo servicioRitmo) {
		this.servicioRitmo = servicioRitmo;
	}

	public Segmento getSegmento() {
		return segmento;
	}

	public void setSegmento(Segmento segmento) {
		this.segmento = segmento;
	}

	public List<Segmento> getSegmentos() {
		return segmentos;
	}

	public void setSegmentos(List<Segmento> segmentos) {
		this.segmentos = segmentos;
	}

	public ServicioSegmento getServicioSegmento() {
		return servicioSegmento;
	}

	public void setServicioSegmento(ServicioSegmento servicioSegmento) {
		this.servicioSegmento = servicioSegmento;
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

	public Cardiologia getCardiologia() {
		return cardiologia;
	}

	public void setCardiologia(Cardiologia cardiologia) {
		this.cardiologia = cardiologia;
	}

}

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
import org.ucla.sigma.modelo.Color;
import org.ucla.sigma.modelo.Configuracion;
import org.ucla.sigma.modelo.Consistencia;
import org.ucla.sigma.modelo.Dermatologia;
import org.ucla.sigma.modelo.EstadoHidratacion;
import org.ucla.sigma.modelo.Frecuencia;
import org.ucla.sigma.modelo.LesionPS;
import org.ucla.sigma.modelo.LesionPrimaria;
import org.ucla.sigma.modelo.LesionSecundaria;
import org.ucla.sigma.modelo.Paciente;
import org.ucla.sigma.modelo.PalpacionAbdominal;
import org.ucla.sigma.modelo.Patologia;
import org.ucla.sigma.modelo.PatronDistribucion;
import org.ucla.sigma.modelo.Referencia;
import org.ucla.sigma.modelo.Sintoma;
import org.ucla.sigma.modelo.TexturaPilosa;
import org.ucla.sigma.modelo.TipoPatologia;
import org.ucla.sigma.modelo.TipoTratamiento;
import org.ucla.sigma.modelo.Tratamiento;
import org.ucla.sigma.modelo.Usuario;
import org.ucla.sigma.modelo.Veterinario;
import org.ucla.sigma.servicio.ServicioColor;
import org.ucla.sigma.servicio.ServicioConfiguracion;
import org.ucla.sigma.servicio.ServicioConsistencia;
import org.ucla.sigma.servicio.ServicioDermatologia;
import org.ucla.sigma.servicio.ServicioEstadoHidratacion;
import org.ucla.sigma.servicio.ServicioFrecuencia;
import org.ucla.sigma.servicio.ServicioLesionPS;
import org.ucla.sigma.servicio.ServicioLesionPrimaria;
import org.ucla.sigma.servicio.ServicioLesionSecundaria;
import org.ucla.sigma.servicio.ServicioPaciente;
import org.ucla.sigma.servicio.ServicioPalpacionAbdominal;
import org.ucla.sigma.servicio.ServicioPatologia;
import org.ucla.sigma.servicio.ServicioPatronDistribucion;
import org.ucla.sigma.servicio.ServicioSintoma;
import org.ucla.sigma.servicio.ServicioTexturaPilosa;
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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.North;
import org.zkoss.zul.South;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinDermatologia extends GenericForwardComposer {

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

	private Window winDermatologia;
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

	// AGREGAR AQUI
	private Textbox txtTiempoEvolucion;
	private Textbox txtEdadInicio;
	private Textbox txtDondeComenzo;
	private Textbox txtAparienciaInicial;
	private Textbox txtAvanceEnfermedad;
	private Textbox txtDescripcionAnimalesInfectados;
	private Textbox txtDescripcionPersonasInfetadas;
	private Textbox txtDescripcionPulgas;
	private Textbox txtDescripcionOlor;
	private Textbox txtDescripcionRasca;
	private Textbox txtProductoUtilizado;
	private Textbox txtEfectoProducto;
	private Textbox txtMedicamentoUtilizado;
	private Textbox txtDescripcionMedicamento;
	private Textbox txtPielElasticidadEspesor;
	private Textbox txtGeneroElectroparasitos;
	private Textbox txtCaracteristicasInfestacion;
	private Textbox txtDescripcionLesionPrimaria;
	private Textbox txtDescripcionLesionPS;
	private Textbox txtDescripcionLesionSecundaria;
	private Textbox txtDescripcionPatronDistribucion;
	private Textbox txtDescripcionConfiguracion;
	private Textbox txtDescripcionConsistencia;
	private Textbox txtPulpejos;
	private Textbox txtUnas;
	private Listbox listFrecuenciaRasca;
	private Listbox listFrecuenciaBano;
	private Listbox listPalpacionAbdominal;
	private Listbox listEstadoHidratacion;
	private Listbox listTexturaPilosa;
	private Listbox listLesionPrimaria;
	private Listbox listLesionPS;
	private Listbox listLesionSecundaria;
	private Listbox listPatronDistribucion;
	private Listbox listConfiguracion;
	private Listbox listConsistencia;
	private Listbox listColorPiel;
	private Combobox cbTodoAnno;
	private Combobox cbPresenciaAnimalesInfectados;
	private Combobox cbPresenciaPersonasInfetadas;
	private Combobox cbPresenciaPulgas;
	private Combobox cbPresenciaOlor;
	private Combobox cbPresenciaRasca;
	private Combobox cbPresenciaReflejoDeglutorio;
	private Combobox cbPresenciaReflejoTusigeno;
	private Combobox cbPresenciaElectroparasitos;
	private Spinner spTemperatura;
	private Spinner spPerfusionCapilar;
	private Spinner spNodulosLinfaticos;
	private Datebox dbInicioEnfermedad;

	// Variables y Servicios -> Modelo
	// ------------------------------------------------------------

	private ServicioPaciente servicioPaciente;
	private ServicioSintoma servicioSintoma;
	private ServicioTipoPatologia servicioTipoPatoligia;
	private ServicioPatologia servicioPatologia;
	private ServicioTipoTratamiento servicioTipoTratamiento;
	private ServicioTratamiento servicioTratamiento;
	private Paciente paciente;
	private Usuario usuario;
	private Veterinario veterinario;
	// AGREGAR AQUI
	private ServicioDermatologia servicioDermatologia;
	private ServicioFrecuencia servicioFrecuencia;
	private ServicioPalpacionAbdominal servicioPalpacionAbdominal;
	private ServicioEstadoHidratacion servicioEstadoHidratacion;
	private ServicioTexturaPilosa servicioTexturaPilosa;
	private ServicioLesionPrimaria servicioLesionPrimaria;
	private ServicioLesionPS servicioLesionPS;
	private ServicioLesionSecundaria servicioLesionSecundaria;
	private ServicioPatronDistribucion servicioPatronDistribucion;
	private ServicioConfiguracion servicioConfiguracion;
	private ServicioConsistencia servicioConsistencia;
	private ServicioColor servicioColorPiel;
	private Dermatologia dermatologia;

	// Variables -> Entorno
	// -----------------------------------------------------------------------

	private List<TipoTratamiento> tipoTratamientoCombo = new ArrayList<TipoTratamiento>();
	private List<Tratamiento> tratamientoCombo = new ArrayList<Tratamiento>();
	private List<Tratamiento> tratamientos = new ArrayList<Tratamiento>();
	private List<TipoPatologia> tipoPatologiaCombo = new ArrayList<TipoPatologia>();
	private List<Patologia> patologiaCombo = new ArrayList<Patologia>();
	private List<Patologia> patologias = new ArrayList<Patologia>();
	private List<Sintoma> sintomas1 = new ArrayList<Sintoma>();
	private List<Sintoma> sintomas2 = new ArrayList<Sintoma>();
	// AGREGAR AQUI
	private List<Frecuencia> frecuenciasRasca = new ArrayList<Frecuencia>();
	private List<Frecuencia> frecuenciaBanos = new ArrayList<Frecuencia>();
	private List<PalpacionAbdominal> palpacionAbdominales = new ArrayList<PalpacionAbdominal>();
	private List<EstadoHidratacion> estadoHidrataciones = new ArrayList<EstadoHidratacion>();
	private List<TexturaPilosa> texturaPilosas = new ArrayList<TexturaPilosa>();
	private List<LesionPrimaria> lesionesPrimaria = new ArrayList<LesionPrimaria>();
	private List<LesionPS> lesionesPS = new ArrayList<LesionPS>();
	private List<LesionSecundaria> lesionesSecundaria = new ArrayList<LesionSecundaria>();
	private List<PatronDistribucion> patronDistribuciones = new ArrayList<PatronDistribucion>();
	private List<Configuracion> configuraciones = new ArrayList<Configuracion>();
	private List<Consistencia> consistencias = new ArrayList<Consistencia>();
	private List<Color> colorPieles = new ArrayList<Color>();
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtHm.setFocus(true);
		};
	};

	// Codigo -> Carga Inicial
	// --------------------------------------------------------------------

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winDermatologia.setAttribute(comp.getId() + "ctrl", this);
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
		paciente = new Paciente();
		usuario = new Usuario();
		veterinario = new Veterinario();
		session = Executions.getCurrent().getSession();
		usuario = (Usuario) session.getAttribute("sigmaSession");
		dbFechaActual.setValue(HelperDate.now());
		sintomas2 = servicioSintoma.buscarTodos();
		cargarList(sintomas1, sintomas2);
		tipoPatologiaCombo = servicioTipoPatoligia.buscarTodos();
		tipoTratamientoCombo = servicioTipoTratamiento.buscarTodos();
		listTratamiento.setDisabled(true);
		listPatoligias.setDisabled(true);
		visibilidadSecciones(true, false, false); // Ocultar sur y contenido
													// centro
		actividadBotones(true, true, false, true, true, true); // Desactivar
																// todos menos
																// buscar
		// AGREGAR AQUI
		servicioDermatologia = (ServicioDermatologia) SpringUtil
				.getBean("beanServicioDermatologia");
		servicioFrecuencia = (ServicioFrecuencia) SpringUtil
				.getBean("beanServicioFrecuencia");
		servicioPalpacionAbdominal = (ServicioPalpacionAbdominal) SpringUtil
				.getBean("beanServicioPalpacionAbdominal");
		servicioEstadoHidratacion = (ServicioEstadoHidratacion) SpringUtil
				.getBean("beanServicioEstadoHidratacion");
		servicioTexturaPilosa = (ServicioTexturaPilosa) SpringUtil
				.getBean("beanServicioTexturaPilosa");
		servicioLesionPrimaria = (ServicioLesionPrimaria) SpringUtil
				.getBean("beanServicioLesionPrimaria");
		servicioLesionPS = (ServicioLesionPS) SpringUtil
				.getBean("beanServicioLesionPS");
		servicioLesionSecundaria = (ServicioLesionSecundaria) SpringUtil
				.getBean("beanServicioLesionSecundaria");
		servicioPatronDistribucion = (ServicioPatronDistribucion) SpringUtil
				.getBean("beanServicioPatronDistribucion");
		servicioConfiguracion = (ServicioConfiguracion) SpringUtil
				.getBean("beanServicioConfiguracion");
		servicioConsistencia = (ServicioConsistencia) SpringUtil
				.getBean("beanServicioConsistencia");
		servicioColorPiel = (ServicioColor) SpringUtil
				.getBean("beanServicioColor");
		dermatologia = new Dermatologia();
		frecuenciasRasca = servicioFrecuencia.buscarFrecuenciaRasca();
		frecuenciaBanos = servicioFrecuencia.buscarFrecuenciaBano();
		palpacionAbdominales = servicioPalpacionAbdominal.buscarTodos();
		estadoHidrataciones = servicioEstadoHidratacion.buscarTodos();
		texturaPilosas = servicioTexturaPilosa.buscarTodos();
		lesionesPrimaria = servicioLesionPrimaria.buscarTodos();
		lesionesPS = servicioLesionPS.buscarTodos();
		lesionesSecundaria = servicioLesionSecundaria.buscarTodos();
		patronDistribuciones = servicioPatronDistribucion.buscarTodos();
		configuraciones = servicioConfiguracion.buscarTodos();
		consistencias = servicioConsistencia.buscarTodos();
		colorPieles = servicioColorPiel.buscarTodos();
		/*
		 * Para: Rafa Como no me pasan los datos del paciente desde la cita,
		 * dado que es un pantalla de mentira, solo puedo hacer lo siguiente
		 * para precargar la data, queda de ustedes colocar el id del paciente q
		 * mas les guste
		 */
		txtHm.setValue(((Referencia) arg.get("objeto")).getHistorial()
				.getPaciente().getHistoriaMedica());
		btnBuscar.setVisible(false);
		onClick$btnBuscar();

	}

	public void cargar() {
		// AGREGAR AQUI
		txtGeneroElectroparasitos.setDisabled(true);
		txtCaracteristicasInfestacion.setDisabled(true);
		txtDescripcionAnimalesInfectados.setDisabled(true);
		txtDescripcionPersonasInfetadas.setDisabled(true);
		txtDescripcionPulgas.setDisabled(true);
		txtDescripcionOlor.setDisabled(true);
		listFrecuenciaRasca.setDisabled(true);
		txtDescripcionRasca.setDisabled(true);
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
				txtHm.setReadonly(true);
				txtNombrePaciente.setValue(paciente.getNombre());
				txtRaza.setValue(paciente.getRaza().getNombre());
				txtEspecie.setValue(paciente.getRaza().getEspecie().getNombre());
				txtSexo.setValue(paciente.getSexo().getNombre());
				txtNombreResponsable.setValue(paciente.getResponsable().getPersona().getNombre() + " " + paciente.getResponsable().getPersona().getApellido());
				txtCi.setValue(String.valueOf(paciente.getResponsable().getCedula()));
				txtEdad.setValue(HelperDateAge.age(paciente.getFechaNac(), ' '));
				visibilidadSecciones(true, true, true); // Mostrar sur y
														// contenido centro
				cargar();
				actividadBotones(false, false, true, false, false, false); // Activar
																			// todos
																			// menos
																			// buscar
			}
		}
	}
	
	public void onSelect$cbPresenciaAnimalesInfectados(){
		if (cbPresenciaAnimalesInfectados.getSelectedIndex() == 0) {
			txtDescripcionAnimalesInfectados.setDisabled(false);
		} else
		if (cbPresenciaAnimalesInfectados.getSelectedIndex() == 1){
			txtDescripcionAnimalesInfectados.setDisabled(true);
		}
	}
	
	public void onSelect$cbPresenciaPersonasInfetadas(){
		if (cbPresenciaPersonasInfetadas.getSelectedIndex() == 0) {
			txtDescripcionPersonasInfetadas.setDisabled(false);
		} else
		if (cbPresenciaPersonasInfetadas.getSelectedIndex() == 1){
			txtDescripcionPersonasInfetadas.setDisabled(true);
		}
	}
	
	public void onSelect$cbPresenciaPulgas(){
		if (cbPresenciaPulgas.getSelectedIndex() == 0) {
			txtDescripcionPulgas.setDisabled(false);
		} else
		if (cbPresenciaPulgas.getSelectedIndex() == 1){
			txtDescripcionPulgas.setDisabled(true);
		}
	}
	
	public void onSelect$cbPresenciaOlor(){
		if (cbPresenciaOlor.getSelectedIndex() == 0) {
			txtDescripcionOlor.setDisabled(false);
		} else
		if (cbPresenciaOlor.getSelectedIndex() == 1){
			txtDescripcionOlor.setDisabled(true);
		}
	}
	
	public void onSelect$cbPresenciaRasca(){
		if (cbPresenciaRasca.getSelectedIndex() == 0) {
			listFrecuenciaRasca.setDisabled(false);
			txtDescripcionRasca.setDisabled(false);
		} else
		if (cbPresenciaRasca.getSelectedIndex() == 1){
			listFrecuenciaRasca.setDisabled(true);
			txtDescripcionRasca.setDisabled(true);
		}
	}

	public void onSelect$cbPresenciaElectroparasitos(){
		if (cbPresenciaElectroparasitos.getSelectedIndex() == 0) {
			txtGeneroElectroparasitos.setDisabled(false);
			txtCaracteristicasInfestacion.setDisabled(false);
		} else
		if (cbPresenciaElectroparasitos.getSelectedIndex() == 1){
			txtGeneroElectroparasitos.setDisabled(true);
			txtCaracteristicasInfestacion.setDisabled(true);
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

	public void onChange$dbInicioEnfermedad() {
		txtTiempoEvolucion.setValue(HelperDateAge.age(
				dbInicioEnfermedad.getValue(), HelperDateAge.SPA));
		txtEdadInicio.setValue(HelperDateAge.age(paciente.getFechaNac(),
				dbInicioEnfermedad.getValue(), HelperDateAge.SPA));
	}

	public void onClick$btnDefuncion() {
		/*
		 * En construccion
		 */

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

	public void guardar() {
		List<?> lists;
		Set<?> sets;
		Set<LesionPrimaria> auxLesionPrimaria = new HashSet<LesionPrimaria>();
		Set<LesionPS> auxLesionPS = new HashSet<LesionPS>(); 
		Set<LesionSecundaria> auxLesionSecundaria = new HashSet<LesionSecundaria>();
		Set<PatronDistribucion> auxPatronDistribucion = new HashSet<PatronDistribucion>();
		Set<Configuracion> auxConfiguracion = new HashSet<Configuracion>();
		Set<Consistencia> auxConsistencia = new HashSet<Consistencia>();	
		Set<Patologia> auxPatologias = new HashSet<Patologia>();
		Set<Sintoma> auxSintomas = new HashSet<Sintoma>();
		Set<Tratamiento> auxTratamientos = new HashSet<Tratamiento>();
		if (validar()) {
			dermatologia.setPaciente(paciente);
			dermatologia.setVeterinario(veterinario);
			dermatologia.setFecha(getDbFechaActual().getValue());
			dermatologia.setHora(getDbFechaActual().getValue());
			if (listFrecuenciaRasca.getSelectedIndex() >= 0) {
				dermatologia.setFrecuenciaRasca((Frecuencia)listFrecuenciaRasca.getSelectedItem().getValue());
			}
			dermatologia.setFrecuenciaBano((Frecuencia)listFrecuenciaBano.getSelectedItem().getValue());
			dermatologia.setPalpacionAbdominal((PalpacionAbdominal)listPalpacionAbdominal.getSelectedItem().getValue());
			dermatologia.setEstadoHidratacion((EstadoHidratacion)listEstadoHidratacion.getSelectedItem().getValue());
			dermatologia.setTexturaPilosa((TexturaPilosa)listTexturaPilosa.getSelectedItem().getValue());
			dermatologia.setColor((Color)listColorPiel.getSelectedItem().getValue());			
			if (!listLesionPrimaria.getItems().isEmpty()) {
				sets = listLesionPrimaria.getSelectedItems();
				for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxLesionPrimaria.add((LesionPrimaria) item.getValue());
				}
				// AGREGAR AQUI
				dermatologia.setLesionPrimaria(auxLesionPrimaria);
			}			
			if (!listLesionPS.getItems().isEmpty()) {
				sets = listLesionPS.getSelectedItems();
				for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxLesionPS.add((LesionPS) item.getValue());
				}
				// AGREGAR AQUI
				dermatologia.setLesionPS(auxLesionPS);
			}
			if (!listLesionSecundaria.getItems().isEmpty()) {
				sets = listLesionSecundaria.getSelectedItems();
				for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxLesionSecundaria.add((LesionSecundaria) item.getValue());
				}
				// AGREGAR AQUI
				dermatologia.setLesionSecundaria(auxLesionSecundaria);
			}
			if (!listPatronDistribucion.getItems().isEmpty()) {
				sets = listPatronDistribucion.getSelectedItems();
				for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxPatronDistribucion.add((PatronDistribucion) item.getValue());
				}
				// AGREGAR AQUI
				dermatologia.setPatronDistribucion(auxPatronDistribucion);
			}
			if (!listConfiguracion.getItems().isEmpty()) {
				sets = listConfiguracion.getSelectedItems();
				for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxConfiguracion.add((Configuracion) item.getValue());
				}
				// AGREGAR AQUI
				dermatologia.setConfiguracion(auxConfiguracion);
			}
			if (!listConsistencia.getItems().isEmpty()) {
				sets = listConsistencia.getSelectedItems();
				for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxConsistencia.add((Consistencia) item.getValue());
				}
				// AGREGAR AQUI
				dermatologia.setConsistencia(auxConsistencia);
			}			
			if (!listListadoSintomas1.getItems().isEmpty()) {
				sets = listListadoSintomas1.getSelectedItems();
				for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxSintomas.add((Sintoma) item.getValue());
				}
				dermatologia.setSintomas(auxSintomas);
			}
			if (!listListadoSintomas2.getItems().isEmpty()) {
				sets = listListadoSintomas2.getSelectedItems();
				for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxSintomas.add((Sintoma) item.getValue());
				}
				// AGREGAR AQUI
				dermatologia.setSintomas(auxSintomas);
			}
			if (!listListadoPatologias.getItems().isEmpty()) {
				lists = listListadoPatologias.getItems();
				for (Iterator iterator = lists.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxPatologias.add((Patologia) item.getValue());
				}
				// AGREGAR AQUI
				dermatologia.setPatologias(auxPatologias);
			}
			if (!listListadoTratamiento.getItems().isEmpty()) {
				lists = listListadoTratamiento.getItems();
				for (Iterator iterator = lists.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxTratamientos.add((Tratamiento) item.getValue());
				}
				// AGREGAR AQUI
				 dermatologia.setTratamientos(auxTratamientos);
			}
			// AGREGAR AQUI
			if (cbTodoAnno.getSelectedIndex() == 0) {
				dermatologia.setEsTodoAnno(true);
			} else {
				dermatologia.setEsTodoAnno(false);
			}
			if (cbPresenciaAnimalesInfectados.getSelectedIndex() == 0) {
				dermatologia.setPresenciaAnimalesInfectados(true);
			} else {
				dermatologia.setPresenciaAnimalesInfectados(false);
			}
			if (cbPresenciaPersonasInfetadas.getSelectedIndex() == 0) {
				dermatologia.setPresenciaPersonasInfetadas(true);
			} else {
				dermatologia.setPresenciaPersonasInfetadas(false);
			}
			if (cbPresenciaPulgas.getSelectedIndex() == 0) {
				dermatologia.setPresenciaPulgas(true);
			} else {
				dermatologia.setPresenciaPulgas(false);
			}
			if (cbPresenciaOlor.getSelectedIndex() == 0) {
				dermatologia.setPresenciaOlor(true);
			} else {
				dermatologia.setPresenciaOlor(false);
			}
			if (cbPresenciaRasca.getSelectedIndex() == 0) {
				dermatologia.setPresenciaRasca(true);
			} else {
				dermatologia.setPresenciaRasca(false);
			}
			if (cbPresenciaReflejoDeglutorio.getSelectedIndex() == 0) {
				dermatologia.setPresenciaReflejoDeglutorio(true);
			} else {
				dermatologia.setPresenciaReflejoDeglutorio(false);
			}
			if (cbPresenciaReflejoTusigeno.getSelectedIndex() == 0) {
				dermatologia.setPresenciaReflejoTusigeno(true);
			} else {
				dermatologia.setPresenciaReflejoTusigeno(false);
			}
			if (cbPresenciaElectroparasitos.getSelectedIndex() == 0) {
				dermatologia.setPresenciaElectroParasitos(true);
			} else {
				dermatologia.setPresenciaElectroParasitos(false);
			}
			try {
				// AGREGAR AQUI
				servicioDermatologia.guardarDermatologia(dermatologia);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						actividadBotones(true, false, true, false, false, false); // Activar
						// todos
						// menos
						// guardar
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
		boolean valido = false;
		// AGREGAR AQUI
		if (dbInicioEnfermedad.getValue() == null) {
			MensajeEmergente.mostrar("NOEMPTY", "\nInicio de la Enfermedad",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							dbInicioEnfermedad.setFocus(true);
						}
					});
		} else

		if (cbTodoAnno.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nTodo el Año",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							cbTodoAnno.setFocus(true);
						}
					});					
		} else

		if (txtDondeComenzo.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nDonde Comenzo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtDondeComenzo.setFocus(true);
						}
					});
		} else

		if (txtAvanceEnfermedad.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nAvance de la Enfermedad",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtAvanceEnfermedad.setFocus(true);
						}
					});	
		} else
			
		if (cbPresenciaAnimalesInfectados.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nOtros Animales Infectados",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							cbPresenciaAnimalesInfectados.setFocus(true);
						}
					});					
	
		} else
			
		if (cbPresenciaPersonasInfetadas.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nPresencia de Personas Infetadas",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							cbPresenciaPersonasInfetadas.setFocus(true);
						}
					});					
		} else
			
		if (cbPresenciaPulgas.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nPresencia de Pulgas",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							cbPresenciaPulgas.setFocus(true);
						}
					});	
		} else
			
		if (cbPresenciaOlor.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nPresencia de mal Olor",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							cbPresenciaOlor.setFocus(true);
						}
					});	
		} else
			
		if (cbPresenciaRasca.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nRascado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							cbPresenciaRasca.setFocus(true);
						}
					});		
		} else

		if (listFrecuenciaBano.getSelectedIndex() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nFrecuencia de Baño",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listFrecuenciaBano.setFocus(true);
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
	
		if (spPerfusionCapilar.getValue() <= 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nPerfusion Capilar",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spPerfusionCapilar.setFocus(true);
						}
					});	
		} else
			
		if (spNodulosLinfaticos.getValue() <= 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nNodulos Linfaticos",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spNodulosLinfaticos.setFocus(true);
						}
					});	
		} else
			
		if (cbPresenciaReflejoDeglutorio.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nPresencia de Reflejo Deglutorio",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							cbPresenciaReflejoDeglutorio.setFocus(true);
						}
					});				
		} else

		if (listPalpacionAbdominal.getSelectedIndex() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nPalpacion Abdominal",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listPalpacionAbdominal.setFocus(true);
						}
					});	
		} else
				
		if (cbPresenciaReflejoTusigeno.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nPresencia de Reflejo Tusigeno",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							cbPresenciaReflejoTusigeno.setFocus(true);
						}
					});		
		} else

		if (listEstadoHidratacion.getSelectedIndex() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nEstado de Hidratacion de la piel",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listEstadoHidratacion.setFocus(true);
						}
					});		
		} else

		if (listTexturaPilosa.getSelectedIndex() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nTextura Pilosa",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listTexturaPilosa.setFocus(true);
						}
					});		
		} else

		if (txtPielElasticidadEspesor.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nPiel,Elasticidad y Espesor",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtPielElasticidadEspesor.setFocus(true);
						}
					});			
		} else
			
		if (cbPresenciaElectroparasitos.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nPresencia de Electroparasitos",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							cbPresenciaElectroparasitos.setFocus(true);
						}
					});							
		} else

		if (listColorPiel.getSelectedIndex() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nColor de Piel",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listColorPiel.setFocus(true);
						}
					});		
		} else

		if (txtPulpejos.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nPulpejos",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtPulpejos.setFocus(true);
						}
					});	
			
		} else

		if (txtUnas.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nUñas",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtUnas.setFocus(true);
						}
					});						
		} else

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

	public Window getWinDermatologia() {
		return winDermatologia;
	}

	public void setWinDermatologia(Window winDermatologia) {
		this.winDermatologia = winDermatologia;
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

	public Div getContCentro() {
		return contCentro;
	}

	public void setContCentro(Div contCentro) {
		this.contCentro = contCentro;
	}

	public Textbox getTxtTiempoEvolucion() {
		return txtTiempoEvolucion;
	}

	public void setTxtTiempoEvolucion(Textbox txtTiempoEvolucion) {
		this.txtTiempoEvolucion = txtTiempoEvolucion;
	}

	public Textbox getTxtEdadInicio() {
		return txtEdadInicio;
	}

	public void setTxtEdadInicio(Textbox txtEdadInicio) {
		this.txtEdadInicio = txtEdadInicio;
	}

	public Textbox getTxtDondeComenzo() {
		return txtDondeComenzo;
	}

	public void setTxtDondeComenzo(Textbox txtDondeComenzo) {
		this.txtDondeComenzo = txtDondeComenzo;
	}

	public Textbox getTxtAparienciaInicial() {
		return txtAparienciaInicial;
	}

	public void setTxtAparienciaInicial(Textbox txtAparienciaInicial) {
		this.txtAparienciaInicial = txtAparienciaInicial;
	}

	public Textbox getTxtAvanceEnfermedad() {
		return txtAvanceEnfermedad;
	}

	public void setTxtAvanceEnfermedad(Textbox txtAvanceEnfermedad) {
		this.txtAvanceEnfermedad = txtAvanceEnfermedad;
	}

	public Textbox getTxtDescripcionAnimalesInfectados() {
		return txtDescripcionAnimalesInfectados;
	}

	public void setTxtDescripcionAnimalesInfectados(
			Textbox txtDescripcionAnimalesInfectados) {
		this.txtDescripcionAnimalesInfectados = txtDescripcionAnimalesInfectados;
	}

	public Textbox getTxtDescripcionPersonasInfetadas() {
		return txtDescripcionPersonasInfetadas;
	}

	public void setTxtDescripcionPersonasInfetadas(
			Textbox txtDescripcionPersonasInfetadas) {
		this.txtDescripcionPersonasInfetadas = txtDescripcionPersonasInfetadas;
	}

	public Textbox getTxtDescripcionPulgas() {
		return txtDescripcionPulgas;
	}

	public void setTxtDescripcionPulgas(Textbox txtDescripcionPulgas) {
		this.txtDescripcionPulgas = txtDescripcionPulgas;
	}

	public Textbox getTxtDescripcionOlor() {
		return txtDescripcionOlor;
	}

	public void setTxtDescripcionOlor(Textbox txtDescripcionOlor) {
		this.txtDescripcionOlor = txtDescripcionOlor;
	}

	public Textbox getTxtDescripcionRasca() {
		return txtDescripcionRasca;
	}

	public void setTxtDescripcionRasca(Textbox txtDescripcionRasca) {
		this.txtDescripcionRasca = txtDescripcionRasca;
	}

	public Textbox getTxtProductoUtilizado() {
		return txtProductoUtilizado;
	}

	public void setTxtProductoUtilizado(Textbox txtProductoUtilizado) {
		this.txtProductoUtilizado = txtProductoUtilizado;
	}

	public Textbox getTxtEfectoProducto() {
		return txtEfectoProducto;
	}

	public void setTxtEfectoProducto(Textbox txtEfectoProducto) {
		this.txtEfectoProducto = txtEfectoProducto;
	}

	public Textbox getTxtMedicamentoUtilizado() {
		return txtMedicamentoUtilizado;
	}

	public void setTxtMedicamentoUtilizado(Textbox txtMedicamentoUtilizado) {
		this.txtMedicamentoUtilizado = txtMedicamentoUtilizado;
	}

	public Textbox getTxtDescripcionMedicamento() {
		return txtDescripcionMedicamento;
	}

	public void setTxtDescripcionMedicamento(Textbox txtDescripcionMedicamento) {
		this.txtDescripcionMedicamento = txtDescripcionMedicamento;
	}

	public Textbox getTxtPielElasticidadEspesor() {
		return txtPielElasticidadEspesor;
	}

	public void setTxtPielElasticidadEspesor(Textbox txtPielElasticidadEspesor) {
		this.txtPielElasticidadEspesor = txtPielElasticidadEspesor;
	}

	public Textbox getTxtGeneroElectroparasitos() {
		return txtGeneroElectroparasitos;
	}

	public void setTxtGeneroElectroparasitos(Textbox txtGeneroElectroparasitos) {
		this.txtGeneroElectroparasitos = txtGeneroElectroparasitos;
	}

	public Textbox getTxtCaracteristicasInfestacion() {
		return txtCaracteristicasInfestacion;
	}

	public void setTxtCaracteristicasInfestacion(
			Textbox txtCaracteristicasInfestacion) {
		this.txtCaracteristicasInfestacion = txtCaracteristicasInfestacion;
	}

	public Textbox getTxtDescripcionLesionPrimaria() {
		return txtDescripcionLesionPrimaria;
	}

	public void setTxtDescripcionLesionPrimaria(
			Textbox txtDescripcionLesionPrimaria) {
		this.txtDescripcionLesionPrimaria = txtDescripcionLesionPrimaria;
	}

	public Textbox getTxtDescripcionLesionPS() {
		return txtDescripcionLesionPS;
	}

	public void setTxtDescripcionLesionPS(Textbox txtDescripcionLesionPS) {
		this.txtDescripcionLesionPS = txtDescripcionLesionPS;
	}

	public Textbox getTxtDescripcionLesionSecundaria() {
		return txtDescripcionLesionSecundaria;
	}

	public void setTxtDescripcionLesionSecundaria(
			Textbox txtDescripcionLesionSecundaria) {
		this.txtDescripcionLesionSecundaria = txtDescripcionLesionSecundaria;
	}

	public Textbox getTxtDescripcionPatronDistribucion() {
		return txtDescripcionPatronDistribucion;
	}

	public void setTxtDescripcionPatronDistribucion(
			Textbox txtDescripcionPatronDistribucion) {
		this.txtDescripcionPatronDistribucion = txtDescripcionPatronDistribucion;
	}

	public Textbox getTxtDescripcionConfiguracion() {
		return txtDescripcionConfiguracion;
	}

	public void setTxtDescripcionConfiguracion(
			Textbox txtDescripcionConfiguracion) {
		this.txtDescripcionConfiguracion = txtDescripcionConfiguracion;
	}

	public Textbox getTxtDescripcionConsistencia() {
		return txtDescripcionConsistencia;
	}

	public void setTxtDescripcionConsistencia(Textbox txtDescripcionConsistencia) {
		this.txtDescripcionConsistencia = txtDescripcionConsistencia;
	}

	public Textbox getTxtPulpejos() {
		return txtPulpejos;
	}

	public void setTxtPulpejos(Textbox txtPulpejos) {
		this.txtPulpejos = txtPulpejos;
	}

	public Textbox getTxtUnas() {
		return txtUnas;
	}

	public void setTxtUnas(Textbox txtUnas) {
		this.txtUnas = txtUnas;
	}

	public Listbox getListFrecuenciaRasca() {
		return listFrecuenciaRasca;
	}

	public void setListFrecuenciaRasca(Listbox listFrecuenciaRasca) {
		this.listFrecuenciaRasca = listFrecuenciaRasca;
	}

	public Listbox getListFrecuenciaBano() {
		return listFrecuenciaBano;
	}

	public void setListFrecuenciaBano(Listbox listFrecuenciaBano) {
		this.listFrecuenciaBano = listFrecuenciaBano;
	}

	public Listbox getListPalpacionAbdominal() {
		return listPalpacionAbdominal;
	}

	public void setListPalpacionAbdominal(Listbox listPalpacionAbdominal) {
		this.listPalpacionAbdominal = listPalpacionAbdominal;
	}

	public Listbox getListEstadoHidratacion() {
		return listEstadoHidratacion;
	}

	public void setListEstadoHidratacion(Listbox listEstadoHidratacion) {
		this.listEstadoHidratacion = listEstadoHidratacion;
	}

	public Listbox getListTexturaPilosa() {
		return listTexturaPilosa;
	}

	public void setListTexturaPilosa(Listbox listTexturaPilosa) {
		this.listTexturaPilosa = listTexturaPilosa;
	}

	public Listbox getListLesionPrimaria() {
		return listLesionPrimaria;
	}

	public void setListLesionPrimaria(Listbox listLesionPrimaria) {
		this.listLesionPrimaria = listLesionPrimaria;
	}

	public Listbox getListLesionPS() {
		return listLesionPS;
	}

	public void setListLesionPS(Listbox listLesionPS) {
		this.listLesionPS = listLesionPS;
	}

	public Listbox getListLesionSecundaria() {
		return listLesionSecundaria;
	}

	public void setListLesionSecundaria(Listbox listLesionSecundaria) {
		this.listLesionSecundaria = listLesionSecundaria;
	}

	public Listbox getListPatronDistribucion() {
		return listPatronDistribucion;
	}

	public void setListPatronDistribucion(Listbox listPatronDistribucion) {
		this.listPatronDistribucion = listPatronDistribucion;
	}

	public Listbox getListConfiguracion() {
		return listConfiguracion;
	}

	public void setListConfiguracion(Listbox listConfiguracion) {
		this.listConfiguracion = listConfiguracion;
	}

	public Listbox getListConsistencia() {
		return listConsistencia;
	}

	public void setListConsistencia(Listbox listConsistencia) {
		this.listConsistencia = listConsistencia;
	}

	public Listbox getListColorPiel() {
		return listColorPiel;
	}

	public void setListColorPiel(Listbox listColorPiel) {
		this.listColorPiel = listColorPiel;
	}

	public Combobox getCbTodoAnno() {
		return cbTodoAnno;
	}

	public void setCbTodoAnno(Combobox cbTodoAnno) {
		this.cbTodoAnno = cbTodoAnno;
	}

	public Combobox getCbPresenciaAnimalesInfectados() {
		return cbPresenciaAnimalesInfectados;
	}

	public void setCbPresenciaAnimalesInfectados(
			Combobox cbPresenciaAnimalesInfectados) {
		this.cbPresenciaAnimalesInfectados = cbPresenciaAnimalesInfectados;
	}

	public Combobox getCbPresenciaPersonasInfetadas() {
		return cbPresenciaPersonasInfetadas;
	}

	public void setCbPresenciaPersonasInfetadas(
			Combobox cbPresenciaPersonasInfetadas) {
		this.cbPresenciaPersonasInfetadas = cbPresenciaPersonasInfetadas;
	}

	public Combobox getCbPresenciaPulgas() {
		return cbPresenciaPulgas;
	}

	public void setCbPresenciaPulgas(Combobox cbPresenciaPulgas) {
		this.cbPresenciaPulgas = cbPresenciaPulgas;
	}

	public Combobox getCbPresenciaOlor() {
		return cbPresenciaOlor;
	}

	public void setCbPresenciaOlor(Combobox cbPresenciaOlor) {
		this.cbPresenciaOlor = cbPresenciaOlor;
	}

	public Combobox getCbPresenciaRasca() {
		return cbPresenciaRasca;
	}

	public void setCbPresenciaRasca(Combobox cbPresenciaRasca) {
		this.cbPresenciaRasca = cbPresenciaRasca;
	}

	public Combobox getCbPresenciaReflejoDeglutorio() {
		return cbPresenciaReflejoDeglutorio;
	}

	public void setCbPresenciaReflejoDeglutorio(
			Combobox cbPresenciaReflejoDeglutorio) {
		this.cbPresenciaReflejoDeglutorio = cbPresenciaReflejoDeglutorio;
	}

	public Combobox getCbPresenciaReflejoTusigeno() {
		return cbPresenciaReflejoTusigeno;
	}

	public void setCbPresenciaReflejoTusigeno(
			Combobox cbPresenciaReflejoTusigeno) {
		this.cbPresenciaReflejoTusigeno = cbPresenciaReflejoTusigeno;
	}

	public Combobox getCbPresenciaElectroparasitos() {
		return cbPresenciaElectroparasitos;
	}

	public void setCbPresenciaElectroparasitos(
			Combobox cbPresenciaElectroparasitos) {
		this.cbPresenciaElectroparasitos = cbPresenciaElectroparasitos;
	}

	public Spinner getSpTemperatura() {
		return spTemperatura;
	}

	public void setSpTemperatura(Spinner spTemperatura) {
		this.spTemperatura = spTemperatura;
	}

	public Spinner getSpPerfusionCapilar() {
		return spPerfusionCapilar;
	}

	public void setSpPerfusionCapilar(Spinner spPerfusionCapilar) {
		this.spPerfusionCapilar = spPerfusionCapilar;
	}

	public Spinner getSpNodulosLinfaticos() {
		return spNodulosLinfaticos;
	}

	public void setSpNodulosLinfaticos(Spinner spNodulosLinfaticos) {
		this.spNodulosLinfaticos = spNodulosLinfaticos;
	}

	public Datebox getDbInicioEnfermedad() {
		return dbInicioEnfermedad;
	}

	public void setDbInicioEnfermedad(Datebox dbInicioEnfermedad) {
		this.dbInicioEnfermedad = dbInicioEnfermedad;
	}

	public Dermatologia getDermatologia() {
		return dermatologia;
	}

	public void setDermatologia(Dermatologia dermatologia) {
		this.dermatologia = dermatologia;
	}

	public List<Frecuencia> getFrecuenciasRasca() {
		return frecuenciasRasca;
	}

	public void setFrecuenciasRasca(List<Frecuencia> frecuenciasRasca) {
		this.frecuenciasRasca = frecuenciasRasca;
	}

	public List<Frecuencia> getFrecuenciaBanos() {
		return frecuenciaBanos;
	}

	public void setFrecuenciaBanos(List<Frecuencia> frecuenciaBanos) {
		this.frecuenciaBanos = frecuenciaBanos;
	}

	public List<PalpacionAbdominal> getPalpacionAbdominales() {
		return palpacionAbdominales;
	}

	public void setPalpacionAbdominales(
			List<PalpacionAbdominal> palpacionAbdominales) {
		this.palpacionAbdominales = palpacionAbdominales;
	}

	public List<EstadoHidratacion> getEstadoHidrataciones() {
		return estadoHidrataciones;
	}

	public void setEstadoHidrataciones(
			List<EstadoHidratacion> estadoHidrataciones) {
		this.estadoHidrataciones = estadoHidrataciones;
	}

	public List<TexturaPilosa> getTexturaPilosas() {
		return texturaPilosas;
	}

	public void setTexturaPilosas(List<TexturaPilosa> texturaPilosas) {
		this.texturaPilosas = texturaPilosas;
	}

	public List<LesionPrimaria> getLesionesPrimaria() {
		return lesionesPrimaria;
	}

	public void setLesionesPrimaria(List<LesionPrimaria> lesionesPrimaria) {
		this.lesionesPrimaria = lesionesPrimaria;
	}

	public List<LesionPS> getLesionesPS() {
		return lesionesPS;
	}

	public void setLesionesPS(List<LesionPS> lesionesPS) {
		this.lesionesPS = lesionesPS;
	}

	public List<LesionSecundaria> getLesionesSecundaria() {
		return lesionesSecundaria;
	}

	public void setLesionesSecundaria(List<LesionSecundaria> lesionesSecundaria) {
		this.lesionesSecundaria = lesionesSecundaria;
	}

	public List<PatronDistribucion> getPatronDistribuciones() {
		return patronDistribuciones;
	}

	public void setPatronDistribuciones(
			List<PatronDistribucion> patronDistribuciones) {
		this.patronDistribuciones = patronDistribuciones;
	}

	public List<Configuracion> getConfiguraciones() {
		return configuraciones;
	}

	public void setConfiguraciones(List<Configuracion> configuraciones) {
		this.configuraciones = configuraciones;
	}

	public List<Consistencia> getConsistencias() {
		return consistencias;
	}

	public void setConsistencias(List<Consistencia> consistencias) {
		this.consistencias = consistencias;
	}

	public List<Color> getColorPieles() {
		return colorPieles;
	}

	public void setColorPieles(List<Color> colorPieles) {
		this.colorPieles = colorPieles;
	}
	
	

}

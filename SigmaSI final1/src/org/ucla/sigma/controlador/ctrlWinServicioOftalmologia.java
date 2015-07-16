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
import org.ucla.sigma.modelo.Frecuencia;
import org.ucla.sigma.modelo.Ganglio;
import org.ucla.sigma.modelo.Oftalmologia;
import org.ucla.sigma.modelo.Paciente;
import org.ucla.sigma.modelo.Patologia;
import org.ucla.sigma.modelo.Referencia;
import org.ucla.sigma.modelo.Reflejo;
import org.ucla.sigma.modelo.Sintoma;
import org.ucla.sigma.modelo.TipoAlimentacion;
import org.ucla.sigma.modelo.TipoPatologia;
import org.ucla.sigma.modelo.TipoTratamiento;
import org.ucla.sigma.modelo.Tratamiento;
import org.ucla.sigma.modelo.Usuario;
import org.ucla.sigma.modelo.Veterinario;
import org.ucla.sigma.servicio.ServicioConsultaGeneral;
import org.ucla.sigma.servicio.ServicioFichaMedica;
import org.ucla.sigma.servicio.ServicioOftalmologia;
import org.ucla.sigma.servicio.ServicioPaciente;
import org.ucla.sigma.servicio.ServicioPatologia;
import org.ucla.sigma.servicio.ServicioReflejo;
import org.ucla.sigma.servicio.ServicioSintoma;
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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.North;
import org.zkoss.zul.South;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinServicioOftalmologia extends GenericForwardComposer {
	
	/*
	 * Deben cambiar donde diga "XxxxNombreDelServicioXxxx", aqui y en el .zul
	 * Se ha de dejar el codigo q ya este, es decir NO borren nada, solo agreguen de acuerdo a lo nesesario, si no hay otra opcion, antes 
	 * de borrar consulten, q luego es mas duro hallar cualquier error, para evitar problemas con el orden de algunos metodos ya existentes, 
	 * porfavor agregar el codigo extra donde aparesca " //AGREGAR AQUI "
	 * PD1: Una vez terminado y funcionando el controlador, borrar los comentarios, que son solo para guiarce, es decir los q estan entre " /* " 
	 */
	// Variables -> Zul ---------------------------------------------------------------------------
	
	private Window winServicioOftalmologia;
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
	
	// Variables -> Zul Propias -------------------------------------------------------------------
	
	/*
	 * Aqui han de ir las variables distintas del Default(Cabezera)
	 */
	
	private Listbox listReflejosIzq;
	private Checkbox chkAmenazaIzq;
	private Checkbox chkFundicoIzq;
	private Checkbox chkFluorescenciaIzq;
	private Checkbox chkBengalaIzq;
	private Decimalbox dcPIOIzq;
	private Decimalbox dcShirmerIzq;
	
	private Listbox listReflejosDer;
	private Checkbox chkAmenazaDer;
	private Checkbox chkFundicoDer;
	private Checkbox chkFluorescenciaDer;
	private Checkbox chkBengalaDer;
	private Decimalbox dcPIODer;
	private Decimalbox dcShirmerDer;
	
	
	// Variables y Servicios -> Modelo ------------------------------------------------------------
	
	/*
	 * Aqui han de ir las variables para los servicios y todas aquellas que dependan de los mismo, asi como las q tiene un modelo(Para Hibernate)
	 * PD: Una vez precionado el boton buscar y que este aya tenido exito, las variables tanto Paciente como Veterinario, estaran cargadas y listas para
	 * usar, asi que hacer uso de ellas a gusto
	 */
	
	private ServicioOftalmologia servicioOftalmologia;
	private ServicioReflejo servicioReflejo;
	private ServicioPaciente servicioPaciente;
	private ServicioSintoma servicioSintoma;
	private ServicioTipoPatologia servicioTipoPatoligia;
	private ServicioPatologia servicioPatologia;
	private ServicioTipoTratamiento servicioTipoTratamiento;
	private ServicioTratamiento servicioTratamiento;
	private Paciente paciente;
	private Usuario usuario;
	private Veterinario veterinario; 
	//AGREGAR AQUI

	// Variables -> Entorno -----------------------------------------------------------------------
	
	private Oftalmologia oftalmologia;
	
	private List<Reflejo> reflejosDer = new ArrayList<Reflejo>();
	private List<Reflejo> reflejosIzq = new ArrayList<Reflejo>();
	
	/*
	 * Aqui han de ir todas aquellas variables q se consideran necesarias para el funcionamiento de controlador, esto depende de cada controlador
	 * y de quien lo haga, esto no incluye a las variables locales de algun metodo
	 * PD: Si es necesario usar o llamar alguna ventana(.zul) tratar en lo posible de crear un variable para el url, 
	 * Ejemplo:
	 * private String xxxXxxxString = "/sigma/vistas/servicios/Servicios/xxxXxxx.zul";
	 */
	private List<TipoTratamiento> tipoTratamientoCombo = new ArrayList<TipoTratamiento>();
	private List<Tratamiento> tratamientoCombo = new ArrayList<Tratamiento>();
	private List<Tratamiento> tratamientos = new ArrayList<Tratamiento>();
	private List<TipoPatologia> tipoPatologiaCombo = new ArrayList<TipoPatologia>();
	private List<Patologia> patologiaCombo = new ArrayList<Patologia>();
	private List<Patologia> patologias = new ArrayList<Patologia>();
	private List<Sintoma> sintomas1 = new ArrayList<Sintoma>();
	private List<Sintoma> sintomas2 = new ArrayList<Sintoma>();
	//AGREGAR AQUI
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtHm.setFocus(true);
		};
	};

	// Codigo -> Carga Inicial --------------------------------------------------------------------
	
	/*
	 * Aqui ira el codigo q sea necesario para inicializar el servicio, de mas notar que el doAfterCompose sera el basico e invariable
	 */
	
	@Override
	public void doAfterCompose(Component comp)throws Exception{
		super.doAfterCompose(comp);
		winServicioOftalmologia.setAttribute(comp.getId() + "ctrl", this);
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
		servicioReflejo = (ServicioReflejo) SpringUtil
				.getBean("beanServicioReflejo");
		servicioOftalmologia = (ServicioOftalmologia) SpringUtil
				.getBean("beanServicioOftalmologia");
		paciente = new Paciente();
		usuario = new Usuario();
		veterinario = new Veterinario();
		oftalmologia = new Oftalmologia();
		
		usuario = SessionAdministrator.getLoggedUsuario();
		veterinario = usuario.getPersona().getVeterinario();
		dbFechaActual.setValue(HelperDate.now());
		
		sintomas2 = servicioSintoma.buscarTodos();
		cargarList(sintomas1, sintomas2);
		tipoPatologiaCombo = servicioTipoPatoligia.buscarTodos();
		tipoTratamientoCombo = servicioTipoTratamiento.buscarTodos();
		reflejosDer = servicioReflejo.buscarTodos('A');
		reflejosIzq = servicioReflejo.buscarTodos('A');
		listTratamiento.setDisabled(true);
		listPatoligias.setDisabled(true);
		visibilidadSecciones(true,false,false); //Ocultar sur y contenido centro
		actividadBotones(true,true,false,true,true,true); //Desactivar todos menos buscar
		//AGREGAR AQUI
		txtHm.setValue(((Referencia) arg.get("objeto")).getHistorial()
				.getPaciente().getHistoriaMedica());
		btnBuscar.setVisible(false);
		onClick$btnBuscar();
	}
	
	public void cargar() {
		/*
		 * Aqui se ha de colocar cualquier codigo q sea neceario para precargar el servicio, este metodo se ejecutara justo luego de 
		 * precionar buscar si el paciente es encontrado, claro esta
		 * PD: Si desean cambiar el titulo de la ventana, usar la siguiente linea
		 * winServicioOftalmologia.setTitle("xxx Lo que mas les guste xxx");
		 */
		//AGREGAR AQUI
	}
	
	public void postCargar() {
		/*
		 * Aqui se ha de colocar cualquier codigo q sea neceario para modificar, llamar o preparar el servicio luego de guardar, este 
		 * metodo se ejecutara justo luego de precionar Aceptar una vez guardado exitosamente, claro esta
		 */
		//AGREGAR AQUI
	}
	
	public void cargarList(List<Sintoma> primeraList, List<Sintoma> segundaList) {
		int mitad;
		mitad = segundaList.size() / 2;
		for (int x = 0; x < mitad; x++) {
			primeraList.add(segundaList.remove(0));
		}
	}
	
	// Codigo -> Botones y Secciones --------------------------------------------------------------
	
	/*
	 * En esta seccion estaran todas las acciones de los botones y secciones del .zul, asi como el control que sea necesario ejercer 
	 * sobre ellos.
	 */
	
	public void actividadBotones(boolean guardar, boolean cancelar, boolean buscar, boolean defuncion, boolean referencia, boolean historial) {
		btnGuardarPrincipal.setDisabled(guardar);
		btnCancelarPrincipal.setDisabled(cancelar);
		btnBuscar.setDisabled(buscar);
		btnDefuncion.setDisabled(defuncion);
		btnReferenciaMedica.setDisabled(referencia);
		btnHistorial.setDisabled(historial);
	}
	
	public void visibilidadSecciones(boolean norte, boolean sur, boolean contcentro) {
		blNorte.setVisible(norte);
		blSur.setVisible(sur);
		contCentro.setVisible(contcentro);
	}
	
	public void onClick$btnGuardarPrincipal(){
		/*
		 *Aqui se hara el llamado al metodo Guardar, pero si se da el caso y alguno necesita ejecutar alguna accion justo antes de guardar
		 *puede hacerlo aki, asi como aplicar alguna condicion extra a dicho evento
		 */
		
		//AGREGAR AQUI
		guardar();
	}

	public void onClick$btnCancelarPrincipal(){
		paciente = new Paciente();
		veterinario = new Veterinario();
		dbFechaActual.setValue(HelperDate.now());
		visibilidadSecciones(true,false,false); //Ocultar sur y contenido centro
		actividadBotones(true,true,false,true,true,true); //Desactivar todos menos buscar
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

	public void onClick$btnBuscar(){
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
				txtEspecie.setValue(paciente.getRaza().getEspecie().getNombre());
				txtSexo.setValue(paciente.getSexo().getNombre());
				txtNombreResponsable.setValue(paciente.getResponsable().getPersona().getNombre()+ " " +paciente.getResponsable().getPersona().getApellido());
				txtCi.setValue(String.valueOf(paciente.getResponsable().getCedula()));
				txtEdad.setValue(HelperDateAge.age(paciente.getFechaNac(), ' '));
				visibilidadSecciones(true,true,true); //Mostrar sur y contenido centro
				cargar();	
				actividadBotones(false,false,true,false,false,false); //Activar todos menos buscar
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
	
	public void onClick$btnReferenciaMedica(){
		/*
		 * En construccion
		 */
		
	}
	
	public void onClick$btnHistorial(){
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
	
	// Codigo -> Entorno --------------------------------------------------------------------------
	
	/*
	 * Aqui ira el codigo para el correcto funcionamiento del servicio, o lo q es o mimso, todo lo que no tenga lugar en las 
	 * otras secciones
	 */
	
	public void guardar() {	
		/*
		 * Es aqui donde comienza la complicacion, aqui iran todos los llamados, algoritmos o artimañas necesarias para
		 * que guarde el servicio
		 * PD: No hay mayor detalle con respecto a como hacer con algunas situacines, asi que si tiene dudas, dirijance al
		 * controlador de consulta general(ctrlWinConsultaGeneral.java) para guiarce, o pregunten 
		 */
		System.out.println("estoy en el guardar");
		List<?> lists;
		Set<?> sets;
		Set<Patologia> auxPatologias = new HashSet<Patologia>();
		Set<Sintoma> auxSintomas = new HashSet<Sintoma>();
		Set<Tratamiento> auxTratamientos = new HashSet<Tratamiento>();
		if (validar()) {
			System.out.println("pase el validar");
			oftalmologia.setVeterinario(veterinario);
			oftalmologia.setPaciente(paciente);
			oftalmologia.setFecha(dbFechaActual.getValue());
			oftalmologia.setHora(dbFechaActual.getValue());
			oftalmologia.setDcPIODer(dcPIODer.getValue().doubleValue());
			oftalmologia.setDcPIOIzq(dcPIOIzq.getValue().doubleValue());
			oftalmologia.setDcShirmerDer(dcShirmerDer.getValue().doubleValue());
			oftalmologia.setDcShirmerIzq(dcShirmerIzq.getValue().doubleValue());
			oftalmologia.setReflejoDer((Reflejo) listReflejosDer.getSelectedItem().getValue());
			oftalmologia.setReflejoIzq((Reflejo) listReflejosIzq.getSelectedItem().getValue());
			
			if (!listListadoSintomas1.getItems().isEmpty()) {
				sets = listListadoSintomas1.getSelectedItems();
				for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxSintomas.add((Sintoma) item.getValue());
				}
				oftalmologia.setSintomas(auxSintomas);
			}
			if (!listListadoSintomas2.getItems().isEmpty()) {
				sets = listListadoSintomas2.getSelectedItems();
				for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxSintomas.add((Sintoma) item.getValue());
				}
				/*
				 * Justo aki bede ir una copia de la siguiente linea adaptada a su propio servicio, sea cual sea el caso
				 * XxxxElQueCorrespondaXxxx.setSintomas(auxSintomas);
				 */
				//AGREGAR AQUI
				oftalmologia.setSintomas(auxSintomas);
			}
			if (!listListadoPatologias.getItems().isEmpty()) {
				lists = listListadoPatologias.getItems();
				for (Iterator iterator = lists.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxPatologias.add((Patologia) item.getValue());
				}
				/*
				 * Justo aki bede ir una copia de la siguiente linea adaptada a su propio servicio, sea cual sea el caso
				 * XxxxElQueCorrespondaXxxx.setPatologias(auxPatologias);
				 */
				//AGREGAR AQUI
				oftalmologia.setPatologias(auxPatologias);
			}
			if (!listListadoTratamiento.getItems().isEmpty()) {
				lists = listListadoTratamiento.getItems();
				for (Iterator iterator = lists.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxTratamientos.add((Tratamiento) item.getValue());
				}
				/*
				 * Justo aki bede ir una copia de la siguiente linea adaptada a su propio servicio, sea cual sea el caso
				 * XxxxElQueCorrespondaXxxx.setTratamientos(auxTratamientos);
				 */
				//AGREGAR AQUI
				oftalmologia.setTratamientos(auxTratamientos);
			}
			//AGREGAR AQUI
			try {
				/*
				 * Como nota particular, justo aki bede ir exclusivamente(Y nada mas, por excepcion, de los servicios que por la 
				 * estructura de la base de datos tengan mas de una estructura modelo(Para Hibernate) que necesiten guardar) 
				 * una copia de la siguiente linea adaptada a su propio servicio, sea cual sea el caso
				 * servicioXxxxElQueCorresondaXxxx.guardarXxxx(XxxxElQueCorrespondaXxxx);
				 */
				//AGREGAR AQUI
				if (chkAmenazaDer.isChecked()) {
					oftalmologia.setAmenazaDer(true);
				}else {
					oftalmologia.setAmenazaDer(false);
				}
				
				if (chkAmenazaIzq.isChecked()) {
					oftalmologia.setAmenazaIzq(true);
				}else {
					oftalmologia.setAmenazaDer(false);
				}
				
				if (chkBengalaDer.isChecked()) {
					oftalmologia.setBengalaDer(true);
				}else {
					oftalmologia.setBengalaDer(false);
				}
				
				if (chkBengalaIzq.isChecked()) {
					oftalmologia.setBengalaIzq(true);
				}else {
					oftalmologia.setBengalaIzq(false);
				}
				
				if (chkFluorescenciaDer.isChecked()) {
					oftalmologia.setFluorescenciaDer(true);
				}else {
					oftalmologia.setFluorescenciaDer(false);
				}
				
				if (chkFluorescenciaIzq.isChecked()) {
					oftalmologia.setFluorescenciaIzq(true);
				}else {
					oftalmologia.setFluorescenciaIzq(false);
				}
				
				if (chkFundicoDer.isChecked()) {
					oftalmologia.setFundicoDer(true);
				}else {
					oftalmologia.setFundicoDer(false);
				}
				
				if (chkFundicoIzq.isChecked()) {
					oftalmologia.setFundicoIzq(true);
				}else {
					oftalmologia.setFundicoIzq(false);
				}
				servicioOftalmologia.guardarOftalmologia(oftalmologia);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						actividadBotones(true,false, true, false, false, false); // Activar todos menos guardar
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
		 * Si en Guardar inicio la complicacion, es aqui donde continua, aqui se validara cada campo que sea nesesario,
		 * de acuerdo al criterio de cada quien, claro esta, que si el campo es notNull, se debe validar o saltara un error obviamente.
		 * Esto no es mas q un gran conjunto de If anidados, los cuales deben estar ordenados desendientemente, es decir de acuerdo al 
		 * orden en que serian llenados, ademas deben seguir el siguente patron 
		  	} else
		 	if (xxXxxxNombreDeLaVariableZulXxx y su validacion correspondiente) {
			MensajeEmergente.mostrar("NOEMPTY", "\n XxxxNombreDeLaVariableZulXxx o alguna frace aluciva a la mismas",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							xxXxxxNombreDeLaVariableZulXxx.setFocus(true);
						}
					});
		 */
		
		boolean valido = false;
			/*
			 * Agregar una copia del codigo de validacion ya mostrado, iniciando con el "} else" y termiando con "});"
			 * y asi susesivamente por cada campo a validar
			 */
			//AGREGAR AQUI

			if (dcPIODer.getValue() == null) {
				MensajeEmergente.mostrar("NOEMPTY", "\n PIO Derecho",
						new MensajeListener() {
							@Override
							public void alDestruir() {
								dcPIODer.setFocus(true);
							}
						});
			}else if (dcPIOIzq.getValue() == null) {
				MensajeEmergente.mostrar("NOEMPTY", "\n PIO Izquierdo",
						new MensajeListener() {
							@Override
							public void alDestruir() {
								dcPIOIzq.setFocus(true);
							}
						});
			}else if (dcShirmerDer.getValue() == null) {
				MensajeEmergente.mostrar("NOEMPTY", "\n Shirmer Derecho",
						new MensajeListener() {
							@Override
							public void alDestruir() {
								dcShirmerDer.setFocus(true);
							}
						});
				
			}else if (dcShirmerIzq.getValue() == null) {
				MensajeEmergente.mostrar("NOEMPTY", "\n shirmer Izquierdo",
						new MensajeListener() {
							@Override
							public void alDestruir() {
								dcShirmerIzq.setFocus(true);
							}
						});
			}else if (listReflejosDer.getSelectedItem() == null) {
				MensajeEmergente.mostrar("NOEMPTY", "\n Reflejo Derecho",
						new MensajeListener() {
							@Override
							public void alDestruir() {
								listReflejosDer.setFocus(true);
							}
						});
			}else if (listReflejosIzq.getSelectedItem() == null) {
				MensajeEmergente.mostrar("NOEMPTY", "\n Reflejo Izquierdo",
						new MensajeListener() {
							@Override
							public void alDestruir() {
								listReflejosIzq.setFocus(true);
							}
						});
		} else
			
		if (txtDiagnosticoDefinitivo.getValue().trim().equalsIgnoreCase("")) {
			System.out.println("1");
			MensajeEmergente.mostrar("NOEMPTY", "\n Diagnostico Definitivo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtDiagnosticoDefinitivo.setFocus(true);
						}
					});
		} else

		if (txtTratamientoEnviado.getValue().trim().equalsIgnoreCase("")) {
			System.out.println("2");
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
	
	public void agregarItemListbox(Listbox listado, Listbox combo, List listListado, List listCombo, String nombreComboListB) {
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
		
	// Codigo -> Getters y Setters ----------------------------------------------------------------
	
	/*
	 * Esta mas que claro
	 */
	
	public Window getWinServicioConsultaGeneral() {
		return winServicioOftalmologia;
	}

	public void setWinServicioConsultaGeneral(Window winServicioConsultaGeneral) {
		this.winServicioOftalmologia = winServicioConsultaGeneral;
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

	public Window getwinServicioOftalmologia() {
		return winServicioOftalmologia;
	}

	public void setwinServicioOftalmologia(Window winServicioOftalmologia) {
		this.winServicioOftalmologia = winServicioOftalmologia;
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
	
	public Window getWinServicioOftalmologia() {
		return winServicioOftalmologia;
	}

	public void setWinServicioOftalmologia(Window winServicioOftalmologia) {
		this.winServicioOftalmologia = winServicioOftalmologia;
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

	public Listbox getListReflejosIzq() {
		return listReflejosIzq;
	}

	public void setListReflejosIzq(Listbox listReflejosIzq) {
		this.listReflejosIzq = listReflejosIzq;
	}

	public Listbox getListReflejosDer() {
		return listReflejosDer;
	}

	public void setListReflejosDer(Listbox listReflejosDer) {
		this.listReflejosDer = listReflejosDer;
	}

	public Oftalmologia getOftalmologia() {
		return oftalmologia;
	}

	public void setOftalmologia(Oftalmologia oftalmologia) {
		this.oftalmologia = oftalmologia;
	}

	public Checkbox getChkAmenazaIzq() {
		return chkAmenazaIzq;
	}

	public void setChkAmenazaIzq(Checkbox chkAmenazaIzq) {
		this.chkAmenazaIzq = chkAmenazaIzq;
	}

	public Checkbox getChkFundicoIzq() {
		return chkFundicoIzq;
	}

	public void setChkFundicoIzq(Checkbox chkFundicoIzq) {
		this.chkFundicoIzq = chkFundicoIzq;
	}

	public Checkbox getChkFluorescenciaIzq() {
		return chkFluorescenciaIzq;
	}

	public void setChkFluorescenciaIzq(Checkbox chkFluorescenciaIzq) {
		this.chkFluorescenciaIzq = chkFluorescenciaIzq;
	}

	public Checkbox getChkBengalaIzq() {
		return chkBengalaIzq;
	}

	public void setChkBengalaIzq(Checkbox chkBengalaIzq) {
		this.chkBengalaIzq = chkBengalaIzq;
	}

	public Decimalbox getDcPIOIzq() {
		return dcPIOIzq;
	}

	public void setDcPIOIzq(Decimalbox dcPIOIzq) {
		this.dcPIOIzq = dcPIOIzq;
	}

	public Decimalbox getDcShirmerIzq() {
		return dcShirmerIzq;
	}

	public void setDcShirmerIzq(Decimalbox dcShirmerIzq) {
		this.dcShirmerIzq = dcShirmerIzq;
	}

	public ServicioReflejo getServicioReflejo() {
		return servicioReflejo;
	}

	public void setServicioReflejo(ServicioReflejo servicioReflejo) {
		this.servicioReflejo = servicioReflejo;
	}

	public Checkbox getChkAmenazaDer() {
		return chkAmenazaDer;
	}

	public void setChkAmenazaDer(Checkbox chkAmenazaDer) {
		this.chkAmenazaDer = chkAmenazaDer;
	}

	public Checkbox getChkFundicoDer() {
		return chkFundicoDer;
	}

	public void setChkFundicoDer(Checkbox chkFundicoDer) {
		this.chkFundicoDer = chkFundicoDer;
	}

	public Checkbox getChkFluorescenciaDer() {
		return chkFluorescenciaDer;
	}

	public void setChkFluorescenciaDer(Checkbox chkFluorescenciaDer) {
		this.chkFluorescenciaDer = chkFluorescenciaDer;
	}

	public Checkbox getChkBengalaDer() {
		return chkBengalaDer;
	}

	public void setChkBengalaDer(Checkbox chkBengalaDer) {
		this.chkBengalaDer = chkBengalaDer;
	}

	public Decimalbox getDcPIODer() {
		return dcPIODer;
	}

	public void setDcPIODer(Decimalbox dcPIODer) {
		this.dcPIODer = dcPIODer;
	}

	public Decimalbox getDcShirmerDer() {
		return dcShirmerDer;
	}

	public void setDcShirmerDer(Decimalbox dcShirmerDer) {
		this.dcShirmerDer = dcShirmerDer;
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

	public List<Reflejo> getReflejosDer() {
		return reflejosDer;
	}

	public void setReflejosDer(List<Reflejo> reflejosDer) {
		this.reflejosDer = reflejosDer;
	}

	public List<Reflejo> getReflejosIzq() {
		return reflejosIzq;
	}

	public void setReflejosIzq(List<Reflejo> reflejosIzq) {
		this.reflejosIzq = reflejosIzq;
	}
	
	
	
}

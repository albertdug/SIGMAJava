package org.ucla.sigma.controlador;

import java.util.HashMap;
import java.util.Map;

import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.components.HelperDateAge;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.componentszk.SessionAdministrator;
import org.ucla.sigma.modelo.Historial;
import org.ucla.sigma.modelo.Paciente;
import org.ucla.sigma.modelo.Referencia;
import org.ucla.sigma.modelo.Usuario;
import org.ucla.sigma.modelo.Veterinario;
import org.ucla.sigma.servicio.ServicioConsultaGeneral;
import org.ucla.sigma.servicio.ServicioFichaMedica;
import org.ucla.sigma.servicio.ServicioHistorial;
import org.ucla.sigma.servicio.ServicioPaciente;
import org.ucla.sigma.servicio.ServicioVeterinario;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.North;
import org.zkoss.zul.South;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinServicioConsultaGeneral extends GenericForwardComposer {
	
	// Variables -> Zul ---------------------------------------------------------------------------
	
	private Window winServicioConsultaGeneral;
	private Button btnBuscar;
	private Button btnDefuncion;
	private Button btnReferenciaMedica;
	private Button btnHistorial;
	private Button btnCancelarPrincipal;
	private Button btnGuardarPrincipal;
	private Datebox dbFechaActual;
	private Textbox txtHm;
	private Textbox txtNombrePaciente;
	private Textbox txtRaza;
	private Textbox txtEspecie;
	private Textbox txtSexo;
	private Textbox txtEdad;
	private Textbox txtNombreResponsable;
	private Textbox txtCi;
	private North blNorte;
	private Center blCentro;
	private South blSur;
	private Div contCentro;
	
	// Variables -> Zul Propias -------------------------------------------------------------------
	
	private Window windowCentro;
	
	// Variables y Servicios -> Modelo ------------------------------------------------------------

	private ServicioFichaMedica servicioFichaMedica;
	private ServicioPaciente servicioPaciente;
	private Paciente paciente;
	private Usuario usuario;
	private Veterinario veterinario; 
	private ServicioHistorial servicioHistorial;
	private Historial historial;

	// Variables -> Entorno -----------------------------------------------------------------------

	private String consultaGeneralString = "/sigma/vistas/servicios/servicios/ConsultaGeneral.zul";
	private String selecReferencia = "/sigma/vistas/servicios/referencias/selecReferencia.zul";	
	private String fichaMedicaString = "/sigma/vistas/servicios/servicios/FichaMedica.zul";
	private String catalogoPaciente = "/sigma/vistas/servicios/servicios/CatalogoPacientes.zul";	
	private ctrlWinConsultaGeneral ctrlwinconsultageneral;
	private ctrlWinFichaMedica ctrlwinfichamedica;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtHm.setFocus(true);
		};
	};
	private MensajeListener llamarCatalogo = new MensajeListener() {
		public void alAceptar() {
			cargarCatalogo();
		};
	};
	private boolean primeraVez;

	// Codigo -> Carga Inicial --------------------------------------------------------------------
	
	@Override
	public void doAfterCompose(Component comp)throws Exception{
		super.doAfterCompose(comp);
		winServicioConsultaGeneral.setAttribute(comp.getId() + "ctrl", this);
		servicioPaciente = (ServicioPaciente) SpringUtil
				.getBean("beanServicioPaciente");
		servicioFichaMedica = (ServicioFichaMedica) SpringUtil
				.getBean("beanServicioFichaMedica");
		servicioHistorial = (ServicioHistorial) SpringUtil
				.getBean("beanServicioHistorial");
		paciente = new Paciente();
		usuario = new Usuario();
		veterinario = new Veterinario();
		usuario = SessionAdministrator.getLoggedUsuario();
		dbFechaActual.setValue(HelperDate.now());
		primeraVez = false;
		visibilidadSecciones(true,false,false); //Ocultar sur y contenido centro
		actividadBotones(true,true,false,true,true,true); //Desactivar todos menos buscar
	}
	
	public void cargar() {
		if (servicioFichaMedica.buscarFichaMedicaPaciente(paciente ,'A').isEmpty()){
			primeraVez = true;
			MensajeEmergente.mostrar("1ERAVEZ");
			cargarFichaMedica();
		} else {
			cargarConsultaGeneral();
		}
	}
	
	public void cargarFichaMedica() {
		winServicioConsultaGeneral.setTitle("Consulta General - Primera Visita");
		contCentro.getChildren().clear();
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinServicioConsultaGeneral", this);
		windowCentro = (Window) execution.createComponents(fichaMedicaString, contCentro,
				parametros); 
		actividadBotones(false,false,true,true,true,true); //Activar todos menos buscar, defuncion , referencia
	}
	
	public void cargarConsultaGeneral() {
		winServicioConsultaGeneral.setTitle("Consulta General - Recurrente");
		contCentro.getChildren().clear();
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinServicioConsultaGeneral", this);
		windowCentro = (Window) execution.createComponents(consultaGeneralString, contCentro,
				parametros);
		actividadBotones(false,false,true,true,true,false); //Activar todos menos buscar
	}
	
	public void cargarCatalogo(){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinServicioConsultaGeneral", this);
		Window win = (Window) Executions.createComponents(catalogoPaciente, null,
				parametros);
		win.doHighlighted();		
	}
	
	public void recargar(String historia){
		txtHm.setValue(historia);
		onClick$btnBuscar();		
	}
	
	// Codigo -> Botones y Secciones --------------------------------------------------------------
	
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
		if (primeraVez){
			this.ctrlwinfichamedica.guardar();
		} else {
			this.ctrlwinconsultageneral.guardar();
		}	
	}

	public void onClick$btnCancelarPrincipal(){
		paciente = new Paciente();
		veterinario = new Veterinario();
		dbFechaActual.setValue(HelperDate.now());
		primeraVez = false;
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
		windowCentro.detach();
	}

	public void onClick$btnBuscar(){
		if (txtHm.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDCAT", llamarCatalogo);
		} else {
			paciente = servicioPaciente.buscarUno(txtHm.getValue(), 'A');
			veterinario = usuario.getPersona().getVeterinario();
			if (paciente == null) {
				MensajeEmergente.mostrar("NOFOUNCAT", llamarCatalogo);
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
			}	
		}	
	}
	
	public void onClick$btnDefuncion(){
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("paciente", paciente);
		Window win = (Window) Executions.createComponents("/sigma/vistas/servicios/servicios/ServicioDefuncion.zul", null, parametros);
		win.doHighlighted();
	}
	
	public void onClick$btnReferenciaMedica(){
		historial = servicioHistorial.buscarUno(ctrlwinconsultageneral.getConsultaGeneral().getId(), 'A');
		//System.out.println(historial.getId());
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("paciente", paciente);
		parametros.put("historial", historial);
		Window win = (Window) Executions.createComponents(selecReferencia,
				null, parametros);
		win.doHighlighted();
		
	}
	
	public void onClick$btnHistorial(){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("paciente", paciente);
		Window win = (Window) Executions.createComponents("/sigma/vistas/servicios/servicios/CatalogoHistorial.zul", null, parametros);
		win.doHighlighted();
		
	}
	
	// Codigo -> Getters y Setters ----------------------------------------------------------------
	
	public Window getWinServicioConsultaGeneral() {
		return winServicioConsultaGeneral;
	}

	public void setWinServicioConsultaGeneral(Window winServicioConsultaGeneral) {
		this.winServicioConsultaGeneral = winServicioConsultaGeneral;
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

	public Window getWindowCentro() {
		return windowCentro;
	}

	public void setWindowCentro(Window windowCentro) {
		this.windowCentro = windowCentro;
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

	public ctrlWinConsultaGeneral getCtrlwinconsultageneral() {
		return ctrlwinconsultageneral;
	}

	public void setCtrlwinconsultageneral(
			ctrlWinConsultaGeneral ctrlwinconsultageneral) {
		this.ctrlwinconsultageneral = ctrlwinconsultageneral;
	}

	public ctrlWinFichaMedica getCtrlwinfichamedica() {
		return ctrlwinfichamedica;
	}

	public void setCtrlwinfichamedica(ctrlWinFichaMedica ctrlwinfichamedica) {
		this.ctrlwinfichamedica = ctrlwinfichamedica;
	}

	public MensajeListener getAsignarFocusBuscar() {
		return asignarFocusBuscar;
	}

	public void setAsignarFocusBuscar(MensajeListener asignarFocusBuscar) {
		this.asignarFocusBuscar = asignarFocusBuscar;
	}

	public boolean isPrimeraVez() {
		return primeraVez;
	}

	public void setPrimeraVez(boolean primeraVez) {
		this.primeraVez = primeraVez;
	}

}

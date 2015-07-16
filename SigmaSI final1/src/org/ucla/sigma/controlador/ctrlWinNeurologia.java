/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.annotations.Check;
import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.components.HelperDateAge;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.componentszk.SessionAdministrator;
import org.ucla.sigma.modelo.Area;
import org.ucla.sigma.modelo.Ataxia;
import org.ucla.sigma.modelo.Cojera;
import org.ucla.sigma.modelo.CursoClinico;
import org.ucla.sigma.modelo.Dolor;
import org.ucla.sigma.modelo.Neurologia;
import org.ucla.sigma.modelo.ParesiaPlejia;
import org.ucla.sigma.modelo.Patologia;
import org.ucla.sigma.modelo.Postura;
import org.ucla.sigma.modelo.ReaccionPostular;
import org.ucla.sigma.modelo.Referencia;
import org.ucla.sigma.modelo.ReflejoEspinal;
import org.ucla.sigma.modelo.Sintoma;
import org.ucla.sigma.modelo.ValoracionSubjetiva;
import org.ucla.sigma.modelo.Paciente;
import org.ucla.sigma.modelo.TipoPatologia;
import org.ucla.sigma.modelo.TipoTratamiento;
import org.ucla.sigma.modelo.Tratamiento;
import org.ucla.sigma.modelo.Usuario;
import org.ucla.sigma.modelo.Veterinario;

import org.ucla.sigma.servicio.ServicioConsultaGeneral;
import org.ucla.sigma.servicio.ServicioFichaMedica;
import org.ucla.sigma.servicio.ServicioPaciente;
import org.ucla.sigma.servicio.ServicioPatologia;
import org.ucla.sigma.servicio.ServicioSintoma;
import org.ucla.sigma.servicio.ServicioTipoPatologia;
import org.ucla.sigma.servicio.ServicioTipoTratamiento;
import org.ucla.sigma.servicio.ServicioTratamiento;
import org.ucla.sigma.servicio.ServicioVeterinario;
import org.ucla.sigma.servicio.ServicioAtaxia;
import org.ucla.sigma.servicio.ServicioCojera;
import org.ucla.sigma.servicio.ServicioDolor;
import org.ucla.sigma.servicio.ServicioNeurologia;
import org.ucla.sigma.servicio.ServicioParesiaPlejia;
import org.ucla.sigma.servicio.ServicioPostura;
import org.ucla.sigma.servicio.ServicioReaccionPostular;
import org.ucla.sigma.servicio.ServicioReflejoEspinal;
import org.ucla.sigma.servicio.ServicioCursoClinico;
import org.ucla.sigma.servicio.ServicioValoracionSubjetiva;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Center;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.North;
import org.zkoss.zul.South;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;

public class ctrlWinNeurologia extends GenericForwardComposer {

	private Window winNeurologia;
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
	private Listbox listSintomas;
	private Listbox listSintoma;
	private Listbox listPatologias;
	private Listbox listPatologia;

	private Listbox listCursoClinico;
	private List<CursoClinico> cursoClinicos = new ArrayList<CursoClinico>();
	private ServicioCursoClinico servicioCursoClinico;
	private CursoClinico cursoClinico;

	private Listbox listValoracionSubjetiva;
	private List<ValoracionSubjetiva> valoracionSubjetivas = new ArrayList<ValoracionSubjetiva>();
	private ServicioValoracionSubjetiva servicioValoracionSubjetiva;
	private ValoracionSubjetiva valoracionSubjetiva;

	private Listbox listPostura;
	private List<Postura> posturas = new ArrayList<Postura>();
	private ServicioPostura servicioPostura;
	private Postura postura;

	private Textbox txtOtras;

	private Listbox listAtaxia;
	private List<Ataxia> ataxias = new ArrayList<Ataxia>();
	private ServicioAtaxia servicioAtaxia;
	private Ataxia ataxia;

	private Listbox listParesia;
	private List<ParesiaPlejia> paresias = new ArrayList<ParesiaPlejia>();
	private ServicioParesiaPlejia servicioParesiaPlejia;
	private ParesiaPlejia paresia;

	private Listbox listDolor;
	private List<Dolor> dolores = new ArrayList<Dolor>();
	private ServicioDolor servicioDolor;
	private Dolor dolor;

	private Listbox listParesia1;
	private List<ParesiaPlejia> paresias1 = new ArrayList<ParesiaPlejia>();

	private Listbox listDolor1;
	private List<Dolor> dolores1 = new ArrayList<Dolor>();

	private Listbox listCojera;
	private List<Cojera> cojeras = new ArrayList<Cojera>();
	private ServicioCojera servicioCojera;
	private Cojera cojera;

	private Listbox listReaccionPostular;
	private List<ReaccionPostular> reaccionPostulares = new ArrayList<ReaccionPostular>();
	private ServicioReaccionPostular servicioReaccionPostular;
	private ReaccionPostular reaccionPostular;

	private Listbox listReaccionPostular1;
	private List<ReaccionPostular> reaccionPostulares1 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular2;
	private List<ReaccionPostular> reaccionPostulares2 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular3;
	private List<ReaccionPostular> reaccionPostulares3 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular4;
	private List<ReaccionPostular> reaccionPostulares4 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular5;
	private List<ReaccionPostular> reaccionPostulares5 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular6;
	private List<ReaccionPostular> reaccionPostulares6 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular7;
	private List<ReaccionPostular> reaccionPostulares7 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular8;
	private List<ReaccionPostular> reaccionPostulares8 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular9;
	private List<ReaccionPostular> reaccionPostulares9 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular10;
	private List<ReaccionPostular> reaccionPostulares10 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular11;
	private List<ReaccionPostular> reaccionPostulares11 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular12;
	private List<ReaccionPostular> reaccionPostulares12 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular13;
	private List<ReaccionPostular> reaccionPostulares13 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular14;
	private List<ReaccionPostular> reaccionPostulares14 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular15;
	private List<ReaccionPostular> reaccionPostulares15 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular16;
	private List<ReaccionPostular> reaccionPostulares16 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular17;
	private List<ReaccionPostular> reaccionPostulares17 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular18;
	private List<ReaccionPostular> reaccionPostulares18 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular19;
	private List<ReaccionPostular> reaccionPostulares19 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular20;
	private List<ReaccionPostular> reaccionPostulares20 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular21;
	private List<ReaccionPostular> reaccionPostulares21 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular22;
	private List<ReaccionPostular> reaccionPostulares22 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular23;
	private List<ReaccionPostular> reaccionPostulares23 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular24;
	private List<ReaccionPostular> reaccionPostulares24 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular25;
	private List<ReaccionPostular> reaccionPostulares25 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular26;
	private List<ReaccionPostular> reaccionPostulares26 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular27;
	private List<ReaccionPostular> reaccionPostulares27 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular28;
	private List<ReaccionPostular> reaccionPostulares28 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular29;
	private List<ReaccionPostular> reaccionPostulares29 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular30;
	private List<ReaccionPostular> reaccionPostulares30 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular31;
	private List<ReaccionPostular> reaccionPostulares31 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular32;
	private List<ReaccionPostular> reaccionPostulares32 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular33;
	private List<ReaccionPostular> reaccionPostulares33 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular34;
	private List<ReaccionPostular> reaccionPostulares34 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular35;
	private List<ReaccionPostular> reaccionPostulares35 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular36;
	private List<ReaccionPostular> reaccionPostulares36 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular37;
	private List<ReaccionPostular> reaccionPostulares37 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular38;
	private List<ReaccionPostular> reaccionPostulares38 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular39;
	private List<ReaccionPostular> reaccionPostulares39 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular40;
	private List<ReaccionPostular> reaccionPostulares40 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular41;
	private List<ReaccionPostular> reaccionPostulares41 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular42;
	private List<ReaccionPostular> reaccionPostulares42 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular43;
	private List<ReaccionPostular> reaccionPostulares43 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular44;
	private List<ReaccionPostular> reaccionPostulares44 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular45;
	private List<ReaccionPostular> reaccionPostulares45 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular46;
	private List<ReaccionPostular> reaccionPostulares46 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular47;
	private List<ReaccionPostular> reaccionPostulares47 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular48;
	private List<ReaccionPostular> reaccionPostulares48 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular49;
	private List<ReaccionPostular> reaccionPostulares49 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular50;
	private List<ReaccionPostular> reaccionPostulares50 = new ArrayList<ReaccionPostular>();
	private Listbox listReaccionPostular51;
	private List<ReaccionPostular> reaccionPostulares51 = new ArrayList<ReaccionPostular>();

	private Listbox listReflejoEspinal;
	private List<ReflejoEspinal> reflejoEspinales = new ArrayList<ReflejoEspinal>();
	private ServicioReflejoEspinal servicioReflejoEspinal;
	private ReflejoEspinal reflejoEspinal;

	private List<ReflejoEspinal> reflejoEspinales1 = new ArrayList<ReflejoEspinal>();
	private List<ReflejoEspinal> reflejoEspinales2 = new ArrayList<ReflejoEspinal>();
	private List<ReflejoEspinal> reflejoEspinales3 = new ArrayList<ReflejoEspinal>();
	private List<ReflejoEspinal> reflejoEspinales4 = new ArrayList<ReflejoEspinal>();
	private List<ReflejoEspinal> reflejoEspinales5 = new ArrayList<ReflejoEspinal>();
	private List<ReflejoEspinal> reflejoEspinales6 = new ArrayList<ReflejoEspinal>();
	private List<ReflejoEspinal> reflejoEspinales7 = new ArrayList<ReflejoEspinal>();
	private List<ReflejoEspinal> reflejoEspinales8 = new ArrayList<ReflejoEspinal>();
	private List<ReflejoEspinal> reflejoEspinales9 = new ArrayList<ReflejoEspinal>();
	private List<ReflejoEspinal> reflejoEspinales10 = new ArrayList<ReflejoEspinal>();
	private List<ReflejoEspinal> reflejoEspinales11 = new ArrayList<ReflejoEspinal>();
	private List<ReflejoEspinal> reflejoEspinales12 = new ArrayList<ReflejoEspinal>();
	private List<ReflejoEspinal> reflejoEspinales13 = new ArrayList<ReflejoEspinal>();
	private List<ReflejoEspinal> reflejoEspinales14 = new ArrayList<ReflejoEspinal>();
	private List<ReflejoEspinal> reflejoEspinales15 = new ArrayList<ReflejoEspinal>();
	private List<ReflejoEspinal> reflejoEspinales16 = new ArrayList<ReflejoEspinal>();
	private List<ReflejoEspinal> reflejoEspinales17 = new ArrayList<ReflejoEspinal>();
	private Listbox listReflejoEspinal1;
	private Listbox listReflejoEspinal2;
	private Listbox listReflejoEspinal3;
	private Listbox listReflejoEspinal4;
	private Listbox listReflejoEspinal5;
	private Listbox listReflejoEspinal6;
	private Listbox listReflejoEspinal7;
	private Listbox listReflejoEspinal8;
	private Listbox listReflejoEspinal9;
	private Listbox listReflejoEspinal10;
	private Listbox listReflejoEspinal11;
	private Listbox listReflejoEspinal12;
	private Listbox listReflejoEspinal13;
	private Listbox listReflejoEspinal14;
	private Listbox listReflejoEspinal15;
	private Listbox listReflejoEspinal16;
	private Listbox listReflejoEspinal17;

	private List<Dolor> dolores2 = new ArrayList<Dolor>();
	private List<Dolor> dolores3 = new ArrayList<Dolor>();
	private Listbox listDolor2;
	private Listbox listDolor3;

	private Checkbox cervical;
	private Checkbox toracica;
	private Checkbox toracolumbar;
	private Checkbox lumbar;
	private Checkbox lumbosacra;
	private Checkbox caudal;
	private Checkbox ninguna;
	private Checkbox otra;

	private Combobox cutaneaTroncoNormal;
	// ----------------------------------------------------------------------------------------------------

	private String editArea = "/sigma/vistas/maestros/area/editArea.zul";

	private ServicioNeurologia servicioNeurologia;
	private ServicioPaciente servicioPaciente;
	private ServicioSintoma servicioSintoma;
	private ServicioTipoPatologia servicioTipoPatoligia;
	private ServicioPatologia servicioPatologia;
	private ServicioTipoTratamiento servicioTipoTratamiento;
	private ServicioTratamiento servicioTratamiento;
	private Paciente paciente;
	private Usuario usuario;
	private Veterinario veterinario;

	private ctrlWinNeurologia ctrlwinneurologia;
	private int indexSintoma = -1;
	private Combobox cmbPatologia;
	private Listbox listDerecho;
	private Listbox listIzquierdo;
	private boolean buscando = false;
	private boolean verTodos = false;

	private List<TipoTratamiento> tipoTratamientoCombo = new ArrayList<TipoTratamiento>();
	private List<Tratamiento> tratamientoCombo = new ArrayList<Tratamiento>();
	private List<Tratamiento> tratamientos = new ArrayList<Tratamiento>();
	private List<TipoPatologia> tipoPatologiaCombo = new ArrayList<TipoPatologia>();
	private List<Patologia> patologiaCombo = new ArrayList<Patologia>();
	private List<Patologia> patologias = new ArrayList<Patologia>();
	private List<Sintoma> sintomas1 = new ArrayList<Sintoma>();
	private List<Sintoma> sintomas2 = new ArrayList<Sintoma>();

	private Area seleccion;
	private Neurologia neurologia;

	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtHm.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	public Window getWinNeurologia() {
		return winNeurologia;
	}

	public void setWinNeurologia(Window winNeurologia) {
		this.winNeurologia = winNeurologia;
	}

	public void setListCursoClinico(Listbox listCursoClinico) {
		this.listCursoClinico = listCursoClinico;
	}

	public Listbox getListCursoClinico() {
		return listCursoClinico;
	}

	public void setCursoClinicos(List<CursoClinico> cursoClinicos) {
		this.cursoClinicos = cursoClinicos;
	}

	public List<CursoClinico> getCursoClinicos() {
		return cursoClinicos;
	}

	public void setServicioCursoClinico(
			ServicioCursoClinico servicioCursoClinico) {
		this.servicioCursoClinico = servicioCursoClinico;
	}

	public ServicioCursoClinico getServicioCursoClinico() {
		return servicioCursoClinico;
	}

	public void setCursoClinico(CursoClinico cursoClinico) {
		this.cursoClinico = cursoClinico;
	}

	public CursoClinico getCursoClinico() {
		return cursoClinico;
	}

	public void setListValoracionSubjetiva(Listbox listValoracionSubjetiva) {
		this.listValoracionSubjetiva = listValoracionSubjetiva;
	}

	public Listbox getListValoracionSubjetiva() {
		return listValoracionSubjetiva;
	}

	public void setValoracionSubjetivas(
			List<ValoracionSubjetiva> valoracionSubjetivas) {
		this.valoracionSubjetivas = valoracionSubjetivas;
	}

	public List<ValoracionSubjetiva> getValoracionSubjetivas() {
		return valoracionSubjetivas;
	}

	public void setServicioValoracionSubjetiva(
			ServicioValoracionSubjetiva servicioValoracionSubjetiva) {
		this.servicioValoracionSubjetiva = servicioValoracionSubjetiva;
	}

	public ServicioValoracionSubjetiva getServicioValoracionSubjetiva() {
		return servicioValoracionSubjetiva;
	}

	public void setValoracionSubjetiva(ValoracionSubjetiva valoracionSubjetiva) {
		this.valoracionSubjetiva = valoracionSubjetiva;
	}

	public ValoracionSubjetiva getValoracionSubjetiva() {
		return valoracionSubjetiva;
	}

	public void setListPostura(Listbox listPostura) {
		this.listPostura = listPostura;
	}

	public Listbox getListPostura() {
		return listPostura;
	}

	public void setPosturas(List<Postura> posturas) {
		this.posturas = posturas;
	}

	public List<Postura> getPosturas() {
		return posturas;
	}

	public void setServicioPostura(ServicioPostura servicioPostura) {
		this.servicioPostura = servicioPostura;
	}

	public ServicioPostura getServicioPostura() {
		return servicioPostura;
	}

	public void setPostura(Postura postura) {
		this.postura = postura;
	}

	public Postura getPostura() {
		return postura;
	}

	public void setTxtOtras(Textbox txtOtras) {
		this.txtOtras = txtOtras;
	}

	public Textbox getTxtOtras() {
		return txtOtras;
	}

	public Listbox getListAtaxia() {
		return listAtaxia;
	}

	public void setListAtaxia(Listbox listAtaxia) {
		this.listAtaxia = listAtaxia;
	}

	public List<Ataxia> getAtaxias() {
		return ataxias;
	}

	public void setAtaxias(List<Ataxia> ataxias) {
		this.ataxias = ataxias;
	}

	public ServicioAtaxia getServicioAtaxia() {
		return servicioAtaxia;
	}

	public void setServicioAtaxia(ServicioAtaxia servicioAtaxia) {
		this.servicioAtaxia = servicioAtaxia;
	}

	public Ataxia getAtaxia() {
		return ataxia;
	}

	public void setAtaxia(Ataxia ataxia) {
		this.ataxia = ataxia;
	}

	public Listbox getListParesia() {
		return listParesia;
	}

	public void setListParesia(Listbox listParesia) {
		this.listParesia = listParesia;
	}

	public List<ParesiaPlejia> getParesias() {
		return paresias;
	}

	public void setParesias(List<ParesiaPlejia> paresias) {
		this.paresias = paresias;
	}

	public ServicioParesiaPlejia getServicioParesiaPlejia() {
		return servicioParesiaPlejia;
	}

	public void setServicioParesiaPlejia(
			ServicioParesiaPlejia servicioParesiaPlejia) {
		this.servicioParesiaPlejia = servicioParesiaPlejia;
	}

	public ParesiaPlejia getParesia() {
		return paresia;
	}

	public void setParesia(ParesiaPlejia paresia) {
		this.paresia = paresia;
	}

	public Listbox getListDolor() {
		return listDolor;
	}

	public void setListDolor(Listbox listDolor) {
		this.listDolor = listDolor;
	}

	public List<Dolor> getDolores() {
		return dolores;
	}

	public void setDolores(List<Dolor> dolores) {
		this.dolores = dolores;
	}

	public ServicioDolor getServicioDolor() {
		return servicioDolor;
	}

	public void setServicioDolor(ServicioDolor servicioDolor) {
		this.servicioDolor = servicioDolor;
	}

	public Dolor getDolor() {
		return dolor;
	}

	public void setDolor(Dolor dolor) {
		this.dolor = dolor;
	}

	public Listbox getListParesia1() {
		return listParesia1;
	}

	public void setListParesia1(Listbox listParesia1) {
		this.listParesia1 = listParesia1;
	}

	public List<ParesiaPlejia> getParesias1() {
		return paresias1;
	}

	public void setParesias1(List<ParesiaPlejia> paresias1) {
		this.paresias1 = paresias1;
	}

	public Listbox getListDolor1() {
		return listDolor1;
	}

	public void setListDolor1(Listbox listDolor1) {
		this.listDolor1 = listDolor1;
	}

	public List<Dolor> getDolores1() {
		return dolores1;
	}

	public void setDolores1(List<Dolor> dolores1) {
		this.dolores1 = dolores1;
	}

	public Listbox getListCojera() {
		return listCojera;
	}

	public void setListCojera(Listbox listCojera) {
		this.listCojera = listCojera;
	}

	public List<Cojera> getCojeras() {
		return cojeras;
	}

	public void setCojeras(List<Cojera> cojeras) {
		this.cojeras = cojeras;
	}

	public ServicioCojera getServicioCojera() {
		return servicioCojera;
	}

	public void setServicioCojera(ServicioCojera servicioCojera) {
		this.servicioCojera = servicioCojera;
	}

	public Cojera getCojera() {
		return cojera;
	}

	public void setCojera(Cojera cojera) {
		this.cojera = cojera;
	}

	public Listbox getListReaccionPostular() {
		return listReaccionPostular;
	}

	public void setListReaccionPostular(Listbox listReaccionPostular) {
		this.listReaccionPostular = listReaccionPostular;
	}

	public List<ReaccionPostular> getReaccionPostulares() {
		return reaccionPostulares;
	}

	public void setReaccionPostulares(List<ReaccionPostular> reaccionPostulares) {
		this.reaccionPostulares = reaccionPostulares;
	}

	public ServicioReaccionPostular getServicioReaccionPostular() {
		return servicioReaccionPostular;
	}

	public void setServicioReaccionPostular(
			ServicioReaccionPostular servicioReaccionPostular) {
		this.servicioReaccionPostular = servicioReaccionPostular;
	}

	public ReaccionPostular getReaccionPostular() {
		return reaccionPostular;
	}

	public void setReaccionPostular(ReaccionPostular reaccionPostular) {
		this.reaccionPostular = reaccionPostular;
	}

	public List<ReaccionPostular> getReaccionPostulares1() {
		return reaccionPostulares1;
	}

	public void setReaccionPostulares1(
			List<ReaccionPostular> reaccionPostulares1) {
		this.reaccionPostulares1 = reaccionPostulares1;
	}

	public List<ReaccionPostular> getReaccionPostulares2() {
		return reaccionPostulares2;
	}

	public void setReaccionPostulares2(
			List<ReaccionPostular> reaccionPostulares2) {
		this.reaccionPostulares2 = reaccionPostulares2;
	}

	public List<ReaccionPostular> getReaccionPostulares3() {
		return reaccionPostulares3;
	}

	public void setReaccionPostulares3(
			List<ReaccionPostular> reaccionPostulares3) {
		this.reaccionPostulares3 = reaccionPostulares3;
	}

	public List<ReaccionPostular> getReaccionPostulares4() {
		return reaccionPostulares4;
	}

	public void setReaccionPostulares4(
			List<ReaccionPostular> reaccionPostulares4) {
		this.reaccionPostulares4 = reaccionPostulares4;
	}

	public List<ReaccionPostular> getReaccionPostulares5() {
		return reaccionPostulares5;
	}

	public void setReaccionPostulares5(
			List<ReaccionPostular> reaccionPostulares5) {
		this.reaccionPostulares5 = reaccionPostulares5;
	}

	public List<ReaccionPostular> getReaccionPostulares6() {
		return reaccionPostulares6;
	}

	public void setReaccionPostulares6(
			List<ReaccionPostular> reaccionPostulares6) {
		this.reaccionPostulares6 = reaccionPostulares6;
	}

	public List<ReaccionPostular> getReaccionPostulares7() {
		return reaccionPostulares7;
	}

	public void setReaccionPostulares7(
			List<ReaccionPostular> reaccionPostulares7) {
		this.reaccionPostulares7 = reaccionPostulares7;
	}

	public List<ReaccionPostular> getReaccionPostulares8() {
		return reaccionPostulares8;
	}

	public void setReaccionPostulares8(
			List<ReaccionPostular> reaccionPostulares8) {
		this.reaccionPostulares8 = reaccionPostulares8;
	}

	public List<ReaccionPostular> getReaccionPostulares9() {
		return reaccionPostulares9;
	}

	public void setReaccionPostulares9(
			List<ReaccionPostular> reaccionPostulares9) {
		this.reaccionPostulares9 = reaccionPostulares9;
	}

	public List<ReaccionPostular> getReaccionPostulares10() {
		return reaccionPostulares10;
	}

	public void setReaccionPostulares10(
			List<ReaccionPostular> reaccionPostulares10) {
		this.reaccionPostulares10 = reaccionPostulares10;
	}

	public List<ReaccionPostular> getReaccionPostulares11() {
		return reaccionPostulares11;
	}

	public void setReaccionPostulares11(
			List<ReaccionPostular> reaccionPostulares11) {
		this.reaccionPostulares11 = reaccionPostulares11;
	}

	public List<ReaccionPostular> getReaccionPostulares12() {
		return reaccionPostulares12;
	}

	public void setReaccionPostulares12(
			List<ReaccionPostular> reaccionPostulares12) {
		this.reaccionPostulares12 = reaccionPostulares12;
	}

	public List<ReaccionPostular> getReaccionPostulares13() {
		return reaccionPostulares13;
	}

	public void setReaccionPostulares13(
			List<ReaccionPostular> reaccionPostulares13) {
		this.reaccionPostulares13 = reaccionPostulares13;
	}

	public List<ReaccionPostular> getReaccionPostulares14() {
		return reaccionPostulares14;
	}

	public void setReaccionPostulares14(
			List<ReaccionPostular> reaccionPostulares14) {
		this.reaccionPostulares14 = reaccionPostulares14;
	}

	public List<ReaccionPostular> getReaccionPostulares15() {
		return reaccionPostulares15;
	}

	public void setReaccionPostulares15(
			List<ReaccionPostular> reaccionPostulares15) {
		this.reaccionPostulares15 = reaccionPostulares15;
	}

	public List<ReaccionPostular> getReaccionPostulares16() {
		return reaccionPostulares16;
	}

	public void setReaccionPostulares16(
			List<ReaccionPostular> reaccionPostulares16) {
		this.reaccionPostulares16 = reaccionPostulares16;
	}

	public List<ReaccionPostular> getReaccionPostulares17() {
		return reaccionPostulares17;
	}

	public void setReaccionPostulares17(
			List<ReaccionPostular> reaccionPostulares17) {
		this.reaccionPostulares17 = reaccionPostulares17;
	}

	public List<ReaccionPostular> getReaccionPostulares18() {
		return reaccionPostulares18;
	}

	public void setReaccionPostulares18(
			List<ReaccionPostular> reaccionPostulares18) {
		this.reaccionPostulares18 = reaccionPostulares18;
	}

	public List<ReaccionPostular> getReaccionPostulares19() {
		return reaccionPostulares19;
	}

	public void setReaccionPostulares19(
			List<ReaccionPostular> reaccionPostulares19) {
		this.reaccionPostulares19 = reaccionPostulares19;
	}

	public List<ReaccionPostular> getReaccionPostulares20() {
		return reaccionPostulares20;
	}

	public void setReaccionPostulares20(
			List<ReaccionPostular> reaccionPostulares20) {
		this.reaccionPostulares20 = reaccionPostulares20;
	}

	public List<ReaccionPostular> getReaccionPostulares21() {
		return reaccionPostulares21;
	}

	public void setReaccionPostulares21(
			List<ReaccionPostular> reaccionPostulares21) {
		this.reaccionPostulares21 = reaccionPostulares21;
	}

	public List<ReaccionPostular> getReaccionPostulares22() {
		return reaccionPostulares22;
	}

	public void setReaccionPostulares22(
			List<ReaccionPostular> reaccionPostulares22) {
		this.reaccionPostulares22 = reaccionPostulares22;
	}

	public List<ReaccionPostular> getReaccionPostulares23() {
		return reaccionPostulares23;
	}

	public void setReaccionPostulares23(
			List<ReaccionPostular> reaccionPostulares23) {
		this.reaccionPostulares23 = reaccionPostulares23;
	}

	public List<ReaccionPostular> getReaccionPostulares24() {
		return reaccionPostulares24;
	}

	public void setReaccionPostulares24(
			List<ReaccionPostular> reaccionPostulares24) {
		this.reaccionPostulares24 = reaccionPostulares24;
	}

	public List<ReaccionPostular> getReaccionPostulares25() {
		return reaccionPostulares25;
	}

	public void setReaccionPostulares25(
			List<ReaccionPostular> reaccionPostulares25) {
		this.reaccionPostulares25 = reaccionPostulares25;
	}

	public List<ReaccionPostular> getReaccionPostulares26() {
		return reaccionPostulares26;
	}

	public void setReaccionPostulares26(
			List<ReaccionPostular> reaccionPostulares26) {
		this.reaccionPostulares26 = reaccionPostulares26;
	}

	public List<ReaccionPostular> getReaccionPostulares27() {
		return reaccionPostulares27;
	}

	public void setReaccionPostulares27(
			List<ReaccionPostular> reaccionPostulares27) {
		this.reaccionPostulares27 = reaccionPostulares27;
	}

	public List<ReaccionPostular> getReaccionPostulares28() {
		return reaccionPostulares28;
	}

	public void setReaccionPostulares28(
			List<ReaccionPostular> reaccionPostulares28) {
		this.reaccionPostulares28 = reaccionPostulares28;
	}

	public List<ReaccionPostular> getReaccionPostulares29() {
		return reaccionPostulares29;
	}

	public void setReaccionPostulares29(
			List<ReaccionPostular> reaccionPostulares29) {
		this.reaccionPostulares29 = reaccionPostulares29;
	}

	public List<ReaccionPostular> getReaccionPostulares30() {
		return reaccionPostulares30;
	}

	public void setReaccionPostulares30(
			List<ReaccionPostular> reaccionPostulares30) {
		this.reaccionPostulares30 = reaccionPostulares30;
	}

	public List<ReaccionPostular> getReaccionPostulares31() {
		return reaccionPostulares31;
	}

	public void setReaccionPostulares31(
			List<ReaccionPostular> reaccionPostulares31) {
		this.reaccionPostulares31 = reaccionPostulares31;
	}

	public List<ReaccionPostular> getReaccionPostulares32() {
		return reaccionPostulares32;
	}

	public void setReaccionPostulares32(
			List<ReaccionPostular> reaccionPostulares32) {
		this.reaccionPostulares32 = reaccionPostulares32;
	}

	public List<ReaccionPostular> getReaccionPostulares33() {
		return reaccionPostulares33;
	}

	public void setReaccionPostulares33(
			List<ReaccionPostular> reaccionPostulares33) {
		this.reaccionPostulares33 = reaccionPostulares33;
	}

	public List<ReaccionPostular> getReaccionPostulares34() {
		return reaccionPostulares34;
	}

	public void setReaccionPostulares34(
			List<ReaccionPostular> reaccionPostulares34) {
		this.reaccionPostulares34 = reaccionPostulares34;
	}

	public List<ReaccionPostular> getReaccionPostulares35() {
		return reaccionPostulares35;
	}

	public void setReaccionPostulares35(
			List<ReaccionPostular> reaccionPostulares35) {
		this.reaccionPostulares35 = reaccionPostulares35;
	}

	public List<ReaccionPostular> getReaccionPostulares36() {
		return reaccionPostulares36;
	}

	public void setReaccionPostulares36(
			List<ReaccionPostular> reaccionPostulares36) {
		this.reaccionPostulares36 = reaccionPostulares36;
	}

	public List<ReaccionPostular> getReaccionPostulares37() {
		return reaccionPostulares37;
	}

	public void setReaccionPostulares37(
			List<ReaccionPostular> reaccionPostulares37) {
		this.reaccionPostulares37 = reaccionPostulares37;
	}

	public List<ReaccionPostular> getReaccionPostulares38() {
		return reaccionPostulares38;
	}

	public void setReaccionPostulares38(
			List<ReaccionPostular> reaccionPostulares38) {
		this.reaccionPostulares38 = reaccionPostulares38;
	}

	public List<ReaccionPostular> getReaccionPostulares39() {
		return reaccionPostulares39;
	}

	public void setReaccionPostulares39(
			List<ReaccionPostular> reaccionPostulares39) {
		this.reaccionPostulares39 = reaccionPostulares39;
	}

	public List<ReaccionPostular> getReaccionPostulares40() {
		return reaccionPostulares40;
	}

	public void setReaccionPostulares40(
			List<ReaccionPostular> reaccionPostulares40) {
		this.reaccionPostulares40 = reaccionPostulares40;
	}

	public List<ReaccionPostular> getReaccionPostulares41() {
		return reaccionPostulares41;
	}

	public void setReaccionPostulares41(
			List<ReaccionPostular> reaccionPostulares41) {
		this.reaccionPostulares41 = reaccionPostulares41;
	}

	public List<ReaccionPostular> getReaccionPostulares42() {
		return reaccionPostulares42;
	}

	public void setReaccionPostulares42(
			List<ReaccionPostular> reaccionPostulares42) {
		this.reaccionPostulares42 = reaccionPostulares42;
	}

	public List<ReaccionPostular> getReaccionPostulares43() {
		return reaccionPostulares43;
	}

	public void setReaccionPostulares43(
			List<ReaccionPostular> reaccionPostulares43) {
		this.reaccionPostulares43 = reaccionPostulares43;
	}

	public List<ReaccionPostular> getReaccionPostulares44() {
		return reaccionPostulares44;
	}

	public void setReaccionPostulares44(
			List<ReaccionPostular> reaccionPostulares44) {
		this.reaccionPostulares44 = reaccionPostulares44;
	}

	public List<ReaccionPostular> getReaccionPostulares45() {
		return reaccionPostulares45;
	}

	public void setReaccionPostulares45(
			List<ReaccionPostular> reaccionPostulares45) {
		this.reaccionPostulares45 = reaccionPostulares45;
	}

	public List<ReaccionPostular> getReaccionPostulares46() {
		return reaccionPostulares46;
	}

	public void setReaccionPostulares46(
			List<ReaccionPostular> reaccionPostulares46) {
		this.reaccionPostulares46 = reaccionPostulares46;
	}

	public List<ReaccionPostular> getReaccionPostulares47() {
		return reaccionPostulares47;
	}

	public void setReaccionPostulares47(
			List<ReaccionPostular> reaccionPostulares47) {
		this.reaccionPostulares47 = reaccionPostulares47;
	}

	public List<ReaccionPostular> getReaccionPostulares48() {
		return reaccionPostulares48;
	}

	public void setReaccionPostulares48(
			List<ReaccionPostular> reaccionPostulares48) {
		this.reaccionPostulares48 = reaccionPostulares48;
	}

	public List<ReaccionPostular> getReaccionPostulares49() {
		return reaccionPostulares49;
	}

	public void setReaccionPostulares49(
			List<ReaccionPostular> reaccionPostulares49) {
		this.reaccionPostulares49 = reaccionPostulares49;
	}

	public List<ReaccionPostular> getReaccionPostulares50() {
		return reaccionPostulares50;
	}

	public void setReaccionPostulares50(
			List<ReaccionPostular> reaccionPostulares50) {
		this.reaccionPostulares50 = reaccionPostulares50;
	}

	public List<ReaccionPostular> getReaccionPostulares51() {
		return reaccionPostulares51;
	}

	public void setReaccionPostulares51(
			List<ReaccionPostular> reaccionPostulares51) {
		this.reaccionPostulares51 = reaccionPostulares51;
	}

	public Listbox getListReaccionPostular1() {
		return listReaccionPostular1;
	}

	public void setListReaccionPostular1(Listbox listReaccionPostular1) {
		this.listReaccionPostular1 = listReaccionPostular1;
	}

	public Listbox getListReaccionPostular2() {
		return listReaccionPostular2;
	}

	public void setListReaccionPostular2(Listbox listReaccionPostular2) {
		this.listReaccionPostular2 = listReaccionPostular2;
	}

	public Listbox getListReaccionPostular3() {
		return listReaccionPostular3;
	}

	public void setListReaccionPostular3(Listbox listReaccionPostular3) {
		this.listReaccionPostular3 = listReaccionPostular3;
	}

	public Listbox getListReaccionPostular4() {
		return listReaccionPostular4;
	}

	public void setListReaccionPostular4(Listbox listReaccionPostular4) {
		this.listReaccionPostular4 = listReaccionPostular4;
	}

	public Listbox getListReaccionPostular5() {
		return listReaccionPostular5;
	}

	public void setListReaccionPostular5(Listbox listReaccionPostular5) {
		this.listReaccionPostular5 = listReaccionPostular5;
	}

	public Listbox getListReaccionPostular6() {
		return listReaccionPostular6;
	}

	public void setListReaccionPostular6(Listbox listReaccionPostular6) {
		this.listReaccionPostular6 = listReaccionPostular6;
	}

	public Listbox getListReaccionPostular7() {
		return listReaccionPostular7;
	}

	public void setListReaccionPostular7(Listbox listReaccionPostular7) {
		this.listReaccionPostular7 = listReaccionPostular7;
	}

	public Listbox getListReaccionPostular8() {
		return listReaccionPostular8;
	}

	public void setListReaccionPostular8(Listbox listReaccionPostular8) {
		this.listReaccionPostular8 = listReaccionPostular8;
	}

	public Listbox getListReaccionPostular9() {
		return listReaccionPostular9;
	}

	public void setListReaccionPostular9(Listbox listReaccionPostular9) {
		this.listReaccionPostular9 = listReaccionPostular9;
	}

	public Listbox getListReaccionPostular10() {
		return listReaccionPostular10;
	}

	public void setListReaccionPostular10(Listbox listReaccionPostular10) {
		this.listReaccionPostular10 = listReaccionPostular10;
	}

	public Listbox getListReaccionPostular11() {
		return listReaccionPostular11;
	}

	public void setListReaccionPostular11(Listbox listReaccionPostular11) {
		this.listReaccionPostular11 = listReaccionPostular11;
	}

	public Listbox getListReaccionPostular12() {
		return listReaccionPostular12;
	}

	public void setListReaccionPostular12(Listbox listReaccionPostular12) {
		this.listReaccionPostular12 = listReaccionPostular12;
	}

	public Listbox getListReaccionPostular13() {
		return listReaccionPostular13;
	}

	public void setListReaccionPostular13(Listbox listReaccionPostular13) {
		this.listReaccionPostular13 = listReaccionPostular13;
	}

	public Listbox getListReaccionPostular14() {
		return listReaccionPostular14;
	}

	public void setListReaccionPostular14(Listbox listReaccionPostular14) {
		this.listReaccionPostular14 = listReaccionPostular14;
	}

	public Listbox getListReaccionPostular15() {
		return listReaccionPostular15;
	}

	public void setListReaccionPostular15(Listbox listReaccionPostular15) {
		this.listReaccionPostular15 = listReaccionPostular15;
	}

	public Listbox getListReaccionPostular16() {
		return listReaccionPostular16;
	}

	public void setListReaccionPostular16(Listbox listReaccionPostular16) {
		this.listReaccionPostular16 = listReaccionPostular16;
	}

	public Listbox getListReaccionPostular17() {
		return listReaccionPostular17;
	}

	public void setListReaccionPostular17(Listbox listReaccionPostular17) {
		this.listReaccionPostular17 = listReaccionPostular17;
	}

	public Listbox getListReaccionPostular18() {
		return listReaccionPostular18;
	}

	public void setListReaccionPostular18(Listbox listReaccionPostular18) {
		this.listReaccionPostular18 = listReaccionPostular18;
	}

	public Listbox getListReaccionPostular19() {
		return listReaccionPostular19;
	}

	public void setListReaccionPostular19(Listbox listReaccionPostular19) {
		this.listReaccionPostular19 = listReaccionPostular19;
	}

	public Listbox getListReaccionPostular20() {
		return listReaccionPostular20;
	}

	public void setListReaccionPostular20(Listbox listReaccionPostular20) {
		this.listReaccionPostular20 = listReaccionPostular20;
	}

	public Listbox getListReaccionPostular21() {
		return listReaccionPostular21;
	}

	public void setListReaccionPostular21(Listbox listReaccionPostular21) {
		this.listReaccionPostular21 = listReaccionPostular21;
	}

	public Listbox getListReaccionPostular22() {
		return listReaccionPostular22;
	}

	public void setListReaccionPostular22(Listbox listReaccionPostular22) {
		this.listReaccionPostular22 = listReaccionPostular22;
	}

	public Listbox getListReaccionPostular23() {
		return listReaccionPostular23;
	}

	public void setListReaccionPostular23(Listbox listReaccionPostular23) {
		this.listReaccionPostular23 = listReaccionPostular23;
	}

	public Listbox getListReaccionPostular24() {
		return listReaccionPostular24;
	}

	public void setListReaccionPostular24(Listbox listReaccionPostular24) {
		this.listReaccionPostular24 = listReaccionPostular24;
	}

	public Listbox getListReaccionPostular25() {
		return listReaccionPostular25;
	}

	public void setListReaccionPostular25(Listbox listReaccionPostular25) {
		this.listReaccionPostular25 = listReaccionPostular25;
	}

	public Listbox getListReaccionPostular26() {
		return listReaccionPostular26;
	}

	public void setListReaccionPostular26(Listbox listReaccionPostular26) {
		this.listReaccionPostular26 = listReaccionPostular26;
	}

	public Listbox getListReaccionPostular27() {
		return listReaccionPostular27;
	}

	public void setListReaccionPostular27(Listbox listReaccionPostular27) {
		this.listReaccionPostular27 = listReaccionPostular27;
	}

	public Listbox getListReaccionPostular28() {
		return listReaccionPostular28;
	}

	public void setListReaccionPostular28(Listbox listReaccionPostular28) {
		this.listReaccionPostular28 = listReaccionPostular28;
	}

	public Listbox getListReaccionPostular29() {
		return listReaccionPostular29;
	}

	public void setListReaccionPostular29(Listbox listReaccionPostular29) {
		this.listReaccionPostular29 = listReaccionPostular29;
	}

	public Listbox getListReaccionPostular30() {
		return listReaccionPostular30;
	}

	public void setListReaccionPostular30(Listbox listReaccionPostular30) {
		this.listReaccionPostular30 = listReaccionPostular30;
	}

	public Listbox getListReaccionPostular31() {
		return listReaccionPostular31;
	}

	public void setListReaccionPostular31(Listbox listReaccionPostular31) {
		this.listReaccionPostular31 = listReaccionPostular31;
	}

	public Listbox getListReaccionPostular32() {
		return listReaccionPostular32;
	}

	public void setListReaccionPostular32(Listbox listReaccionPostular32) {
		this.listReaccionPostular32 = listReaccionPostular32;
	}

	public Listbox getListReaccionPostular33() {
		return listReaccionPostular33;
	}

	public void setListReaccionPostular33(Listbox listReaccionPostular33) {
		this.listReaccionPostular33 = listReaccionPostular33;
	}

	public Listbox getListReaccionPostular34() {
		return listReaccionPostular34;
	}

	public void setListReaccionPostular34(Listbox listReaccionPostular34) {
		this.listReaccionPostular34 = listReaccionPostular34;
	}

	public Listbox getListReaccionPostular35() {
		return listReaccionPostular35;
	}

	public void setListReaccionPostular35(Listbox listReaccionPostular35) {
		this.listReaccionPostular35 = listReaccionPostular35;
	}

	public Listbox getListReaccionPostular36() {
		return listReaccionPostular36;
	}

	public void setListReaccionPostular36(Listbox listReaccionPostular36) {
		this.listReaccionPostular36 = listReaccionPostular36;
	}

	public Listbox getListReaccionPostular37() {
		return listReaccionPostular37;
	}

	public void setListReaccionPostular37(Listbox listReaccionPostular37) {
		this.listReaccionPostular37 = listReaccionPostular37;
	}

	public Listbox getListReaccionPostular38() {
		return listReaccionPostular38;
	}

	public void setListReaccionPostular38(Listbox listReaccionPostular38) {
		this.listReaccionPostular38 = listReaccionPostular38;
	}

	public Listbox getListReaccionPostular39() {
		return listReaccionPostular39;
	}

	public void setListReaccionPostular39(Listbox listReaccionPostular39) {
		this.listReaccionPostular39 = listReaccionPostular39;
	}

	public Listbox getListReaccionPostular40() {
		return listReaccionPostular40;
	}

	public void setListReaccionPostular40(Listbox listReaccionPostular40) {
		this.listReaccionPostular40 = listReaccionPostular40;
	}

	public Listbox getListReaccionPostular41() {
		return listReaccionPostular41;
	}

	public void setListReaccionPostular41(Listbox listReaccionPostular41) {
		this.listReaccionPostular41 = listReaccionPostular41;
	}

	public Listbox getListReaccionPostular42() {
		return listReaccionPostular42;
	}

	public void setListReaccionPostular42(Listbox listReaccionPostular42) {
		this.listReaccionPostular42 = listReaccionPostular42;
	}

	public Listbox getListReaccionPostular43() {
		return listReaccionPostular43;
	}

	public void setListReaccionPostular43(Listbox listReaccionPostular43) {
		this.listReaccionPostular43 = listReaccionPostular43;
	}

	public Listbox getListReaccionPostular44() {
		return listReaccionPostular44;
	}

	public void setListReaccionPostular44(Listbox listReaccionPostular44) {
		this.listReaccionPostular44 = listReaccionPostular44;
	}

	public Listbox getListReaccionPostular45() {
		return listReaccionPostular45;
	}

	public void setListReaccionPostular45(Listbox listReaccionPostular45) {
		this.listReaccionPostular45 = listReaccionPostular45;
	}

	public Listbox getListReaccionPostular46() {
		return listReaccionPostular46;
	}

	public void setListReaccionPostular46(Listbox listReaccionPostular46) {
		this.listReaccionPostular46 = listReaccionPostular46;
	}

	public Listbox getListReaccionPostular47() {
		return listReaccionPostular47;
	}

	public void setListReaccionPostular47(Listbox listReaccionPostular47) {
		this.listReaccionPostular47 = listReaccionPostular47;
	}

	public Listbox getListReaccionPostular48() {
		return listReaccionPostular48;
	}

	public void setListReaccionPostular48(Listbox listReaccionPostular48) {
		this.listReaccionPostular48 = listReaccionPostular48;
	}

	public Listbox getListReaccionPostular49() {
		return listReaccionPostular49;
	}

	public void setListReaccionPostular49(Listbox listReaccionPostular49) {
		this.listReaccionPostular49 = listReaccionPostular49;
	}

	public Listbox getListReaccionPostular50() {
		return listReaccionPostular50;
	}

	public void setListReaccionPostular50(Listbox listReaccionPostular50) {
		this.listReaccionPostular50 = listReaccionPostular50;
	}

	public Listbox getListReaccionPostular51() {
		return listReaccionPostular51;
	}

	public void setListReaccionPostular51(Listbox listReaccionPostular51) {
		this.listReaccionPostular51 = listReaccionPostular51;
	}

	public Listbox getListReflejoEspinal() {
		return listReflejoEspinal;
	}

	public void setListReflejoEspinal(Listbox listReflejoEspinal) {
		this.listReflejoEspinal = listReflejoEspinal;
	}

	public List<ReflejoEspinal> getReflejoEspinales() {
		return reflejoEspinales;
	}

	public void setReflejoEspinales(List<ReflejoEspinal> reflejoEspinales) {
		this.reflejoEspinales = reflejoEspinales;
	}

	public ServicioReflejoEspinal getServicioReflejoEspinal() {
		return servicioReflejoEspinal;
	}

	public void setServicioReflejoEspinal(
			ServicioReflejoEspinal servicioReflejoEspinal) {
		this.servicioReflejoEspinal = servicioReflejoEspinal;
	}

	public ReflejoEspinal getReflejoEspinal() {
		return reflejoEspinal;
	}

	public void setReflejoEspinal(ReflejoEspinal reflejoEspinal) {
		this.reflejoEspinal = reflejoEspinal;
	}

	public List<ReflejoEspinal> getReflejoEspinales1() {
		return reflejoEspinales1;
	}

	public void setReflejoEspinales1(List<ReflejoEspinal> reflejoEspinales1) {
		this.reflejoEspinales1 = reflejoEspinales1;
	}

	public List<ReflejoEspinal> getReflejoEspinales2() {
		return reflejoEspinales2;
	}

	public void setReflejoEspinales2(List<ReflejoEspinal> reflejoEspinales2) {
		this.reflejoEspinales2 = reflejoEspinales2;
	}

	public List<ReflejoEspinal> getReflejoEspinales3() {
		return reflejoEspinales3;
	}

	public void setReflejoEspinales3(List<ReflejoEspinal> reflejoEspinales3) {
		this.reflejoEspinales3 = reflejoEspinales3;
	}

	public List<ReflejoEspinal> getReflejoEspinales4() {
		return reflejoEspinales4;
	}

	public void setReflejoEspinales4(List<ReflejoEspinal> reflejoEspinales4) {
		this.reflejoEspinales4 = reflejoEspinales4;
	}

	public List<ReflejoEspinal> getReflejoEspinales5() {
		return reflejoEspinales5;
	}

	public void setReflejoEspinales5(List<ReflejoEspinal> reflejoEspinales5) {
		this.reflejoEspinales5 = reflejoEspinales5;
	}

	public List<ReflejoEspinal> getReflejoEspinales6() {
		return reflejoEspinales6;
	}

	public void setReflejoEspinales6(List<ReflejoEspinal> reflejoEspinales6) {
		this.reflejoEspinales6 = reflejoEspinales6;
	}

	public List<ReflejoEspinal> getReflejoEspinales7() {
		return reflejoEspinales7;
	}

	public void setReflejoEspinales7(List<ReflejoEspinal> reflejoEspinales7) {
		this.reflejoEspinales7 = reflejoEspinales7;
	}

	public List<ReflejoEspinal> getReflejoEspinales8() {
		return reflejoEspinales8;
	}

	public void setReflejoEspinales8(List<ReflejoEspinal> reflejoEspinales8) {
		this.reflejoEspinales8 = reflejoEspinales8;
	}

	public List<ReflejoEspinal> getReflejoEspinales9() {
		return reflejoEspinales9;
	}

	public void setReflejoEspinales9(List<ReflejoEspinal> reflejoEspinales9) {
		this.reflejoEspinales9 = reflejoEspinales9;
	}

	public List<ReflejoEspinal> getReflejoEspinales10() {
		return reflejoEspinales10;
	}

	public void setReflejoEspinales10(List<ReflejoEspinal> reflejoEspinales10) {
		this.reflejoEspinales10 = reflejoEspinales10;
	}

	public List<ReflejoEspinal> getReflejoEspinales11() {
		return reflejoEspinales11;
	}

	public void setReflejoEspinales11(List<ReflejoEspinal> reflejoEspinales11) {
		this.reflejoEspinales11 = reflejoEspinales11;
	}

	public List<ReflejoEspinal> getReflejoEspinales12() {
		return reflejoEspinales12;
	}

	public void setReflejoEspinales12(List<ReflejoEspinal> reflejoEspinales12) {
		this.reflejoEspinales12 = reflejoEspinales12;
	}

	public List<ReflejoEspinal> getReflejoEspinales13() {
		return reflejoEspinales13;
	}

	public void setReflejoEspinales13(List<ReflejoEspinal> reflejoEspinales13) {
		this.reflejoEspinales13 = reflejoEspinales13;
	}

	public List<ReflejoEspinal> getReflejoEspinales14() {
		return reflejoEspinales14;
	}

	public void setReflejoEspinales14(List<ReflejoEspinal> reflejoEspinales14) {
		this.reflejoEspinales14 = reflejoEspinales14;
	}

	public List<ReflejoEspinal> getReflejoEspinales15() {
		return reflejoEspinales15;
	}

	public void setReflejoEspinales15(List<ReflejoEspinal> reflejoEspinales15) {
		this.reflejoEspinales15 = reflejoEspinales15;
	}

	public List<ReflejoEspinal> getReflejoEspinales16() {
		return reflejoEspinales16;
	}

	public void setReflejoEspinales16(List<ReflejoEspinal> reflejoEspinales16) {
		this.reflejoEspinales16 = reflejoEspinales16;
	}

	public List<ReflejoEspinal> getReflejoEspinales17() {
		return reflejoEspinales17;
	}

	public void setReflejoEspinales17(List<ReflejoEspinal> reflejoEspinales17) {
		this.reflejoEspinales17 = reflejoEspinales17;
	}

	public Listbox getListReflejoEspinal1() {
		return listReflejoEspinal1;
	}

	public void setListReflejoEspinal1(Listbox listReflejoEspinal1) {
		this.listReflejoEspinal1 = listReflejoEspinal1;
	}

	public Listbox getListReflejoEspinal2() {
		return listReflejoEspinal2;
	}

	public void setListReflejoEspinal2(Listbox listReflejoEspinal2) {
		this.listReflejoEspinal2 = listReflejoEspinal2;
	}

	public Listbox getListReflejoEspinal3() {
		return listReflejoEspinal3;
	}

	public void setListReflejoEspinal3(Listbox listReflejoEspinal3) {
		this.listReflejoEspinal3 = listReflejoEspinal3;
	}

	public Listbox getListReflejoEspinal4() {
		return listReflejoEspinal4;
	}

	public void setListReflejoEspinal4(Listbox listReflejoEspinal4) {
		this.listReflejoEspinal4 = listReflejoEspinal4;
	}

	public Listbox getListReflejoEspinal5() {
		return listReflejoEspinal5;
	}

	public void setListReflejoEspinal5(Listbox listReflejoEspinal5) {
		this.listReflejoEspinal5 = listReflejoEspinal5;
	}

	public Listbox getListReflejoEspinal6() {
		return listReflejoEspinal6;
	}

	public void setListReflejoEspinal6(Listbox listReflejoEspinal6) {
		this.listReflejoEspinal6 = listReflejoEspinal6;
	}

	public Listbox getListReflejoEspinal7() {
		return listReflejoEspinal7;
	}

	public void setListReflejoEspinal7(Listbox listReflejoEspinal7) {
		this.listReflejoEspinal7 = listReflejoEspinal7;
	}

	public Listbox getListReflejoEspinal8() {
		return listReflejoEspinal8;
	}

	public void setListReflejoEspinal8(Listbox listReflejoEspinal8) {
		this.listReflejoEspinal8 = listReflejoEspinal8;
	}

	public Listbox getListReflejoEspinal9() {
		return listReflejoEspinal9;
	}

	public void setListReflejoEspinal9(Listbox listReflejoEspinal9) {
		this.listReflejoEspinal9 = listReflejoEspinal9;
	}

	public Listbox getListReflejoEspinal10() {
		return listReflejoEspinal10;
	}

	public void setListReflejoEspinal10(Listbox listReflejoEspinal10) {
		this.listReflejoEspinal10 = listReflejoEspinal10;
	}

	public Listbox getListReflejoEspinal11() {
		return listReflejoEspinal11;
	}

	public void setListReflejoEspinal11(Listbox listReflejoEspinal11) {
		this.listReflejoEspinal11 = listReflejoEspinal11;
	}

	public Listbox getListReflejoEspinal12() {
		return listReflejoEspinal12;
	}

	public void setListReflejoEspinal12(Listbox listReflejoEspinal12) {
		this.listReflejoEspinal12 = listReflejoEspinal12;
	}

	public Listbox getListReflejoEspinal13() {
		return listReflejoEspinal13;
	}

	public void setListReflejoEspinal13(Listbox listReflejoEspinal13) {
		this.listReflejoEspinal13 = listReflejoEspinal13;
	}

	public Listbox getListReflejoEspinal14() {
		return listReflejoEspinal14;
	}

	public void setListReflejoEspinal14(Listbox listReflejoEspinal14) {
		this.listReflejoEspinal14 = listReflejoEspinal14;
	}

	public Listbox getListReflejoEspinal15() {
		return listReflejoEspinal15;
	}

	public void setListReflejoEspinal15(Listbox listReflejoEspinal15) {
		this.listReflejoEspinal15 = listReflejoEspinal15;
	}

	public Listbox getListReflejoEspinal16() {
		return listReflejoEspinal16;
	}

	public void setListReflejoEspinal16(Listbox listReflejoEspinal16) {
		this.listReflejoEspinal16 = listReflejoEspinal16;
	}

	public Listbox getListReflejoEspinal17() {
		return listReflejoEspinal17;
	}

	public void setListReflejoEspinal17(Listbox listReflejoEspinal17) {
		this.listReflejoEspinal17 = listReflejoEspinal17;
	}

	public List<Dolor> getDolores2() {
		return dolores2;
	}

	public void setDolores2(List<Dolor> dolores2) {
		this.dolores2 = dolores2;
	}

	public List<Dolor> getDolores3() {
		return dolores3;
	}

	public void setDolores3(List<Dolor> dolores3) {
		this.dolores3 = dolores3;
	}

	public Listbox getListDolor2() {
		return listDolor2;
	}

	public void setListDolor2(Listbox listDolor2) {
		this.listDolor2 = listDolor2;
	}

	public Listbox getListDolor3() {
		return listDolor3;
	}

	public void setListDolor3(Listbox listDolor3) {
		this.listDolor3 = listDolor3;
	}

	public Checkbox getCervical() {
		return cervical;
	}

	public void setCervical(Checkbox cervical) {
		this.cervical = cervical;
	}

	public Checkbox getToracica() {
		return toracica;
	}

	public void setToracica(Checkbox toracica) {
		this.toracica = toracica;
	}

	public Checkbox getToracolumbar() {
		return toracolumbar;
	}

	public void setToracolumbar(Checkbox toracolumbar) {
		this.toracolumbar = toracolumbar;
	}

	public Checkbox getLumbar() {
		return lumbar;
	}

	public void setLumbar(Checkbox lumbar) {
		this.lumbar = lumbar;
	}

	public Checkbox getLumbosacra() {
		return lumbosacra;
	}

	public void setLumbosacra(Checkbox lumbosacra) {
		this.lumbosacra = lumbosacra;
	}

	public Checkbox getCaudal() {
		return caudal;
	}

	public void setCaudal(Checkbox caudal) {
		this.caudal = caudal;
	}

	public Checkbox getNinguna() {
		return ninguna;
	}

	public void setNinguna(Checkbox ninguna) {
		this.ninguna = ninguna;
	}

	public Checkbox getOtra() {
		return otra;
	}

	public void setOtra(Checkbox otra) {
		this.otra = otra;
	}

	public Combobox getCutaneaTroncoNormal() {
		return cutaneaTroncoNormal;
	}

	public void setCutaneaTroncoNormal(Combobox cutaneaTroncoNormal) {
		this.cutaneaTroncoNormal = cutaneaTroncoNormal;
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

	public ServicioPaciente getServicioPaciente() {
		return servicioPaciente;
	}

	public void setServicioPaciente(ServicioPaciente servicioPaciente) {
		this.servicioPaciente = servicioPaciente;
	}

	public ServicioTipoPatologia getServicioTipoPatoligia() {
		return servicioTipoPatoligia;
	}

	public void setServicioTipoPatoligia(
			ServicioTipoPatologia servicioTipoPatoligia) {
		this.servicioTipoPatoligia = servicioTipoPatoligia;
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

	public Listbox getListPatologias() {
		return listPatologias;
	}

	public void setListPatologias(Listbox listPatologias) {
		this.listPatologias = listPatologias;
	}

	public Listbox getListSintoma() {
		return listSintoma;
	}

	public void setListSintoma(Listbox listSintoma) {
		this.listSintoma = listSintoma;
	}

	public ServicioPatologia getServicioPatologia() {
		return servicioPatologia;
	}

	public void setServicioPatologia(ServicioPatologia servicioPatologia) {
		this.servicioPatologia = servicioPatologia;
	}

	public Combobox getCmbPatologia() {
		return cmbPatologia;
	}

	public void setCmbPatologia(Combobox cmbPatologia) {
		this.cmbPatologia = cmbPatologia;
	}

	public Listbox getListPatologia() {
		return listPatologia;
	}

	public void setListPatologia(Listbox listPatologia) {
		this.listPatologia = listPatologia;
	}

	public Listbox getListDerecho() {
		return listDerecho;
	}

	public void setListDerecho(Listbox listDerecho) {
		this.listDerecho = listDerecho;
	}

	public Listbox getListIzquierdo() {
		return listIzquierdo;
	}

	public void setListIzquierdo(Listbox listIzquierdo) {
		this.listIzquierdo = listIzquierdo;
	}

	public List<Patologia> getPatologias() {
		return patologias;
	}

	public void setPatologias(List<Patologia> patologias) {
		this.patologias = patologias;
	}

	public Listbox getListSintomas() {
		return listSintomas;
	}

	public void setListSintomas(Listbox listSintomas) {
		this.listSintomas = listSintomas;
	}

	private List<Area> areas = new ArrayList<Area>();

	public Neurologia getNeurologia() {
		return neurologia;
	}

	public void setNeurologia(Neurologia neurologia) {
		this.neurologia = neurologia;
	}

	public ServicioSintoma getServicioSintoma() {
		return servicioSintoma;
	}

	public void setServicioSintoma(ServicioSintoma servicioSintoma) {
		this.servicioSintoma = servicioSintoma;
	}

	public ctrlWinNeurologia getCtrlwinneurologia() {
		return ctrlwinneurologia;
	}

	public void setCtrlwinneurologia(ctrlWinNeurologia ctrlwinneurologia) {
		this.ctrlwinneurologia = ctrlwinneurologia;
	}

	public String getEditArea() {
		return editArea;
	}

	public void setEditArea(String editArea) {
		this.editArea = editArea;
	}

	public ServicioNeurologia getServicioNeurologia() {
		return servicioNeurologia;
	}

	public void setServicioNeurologia(ServicioNeurologia servicioNeurologia) {
		this.servicioNeurologia = servicioNeurologia;
	}

	public boolean isBuscando() {
		return buscando;
	}

	public void setBuscando(boolean buscando) {
		this.buscando = buscando;
	}

	public boolean isVerTodos() {
		return verTodos;
	}

	public void setVerTodos(boolean verTodos) {
		this.verTodos = verTodos;
	}

	public MensajeListener getAsignarFocusBuscar() {
		return asignarFocusBuscar;
	}

	public void setAsignarFocusBuscar(MensajeListener asignarFocusBuscar) {
		this.asignarFocusBuscar = asignarFocusBuscar;
	}

	public Area getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Area seleccion) {
		this.seleccion = seleccion;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public int getIndexSintoma() {
		return indexSintoma;
	}

	public void setIndexSintoma(int indexSintoma) {
		this.indexSintoma = indexSintoma;
	}

	// ----------------------------------------------------------------------------------------------------

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */

	public void agregarItemListbox(Listbox lst, Listbox combo) {
		if (combo.getSelectedIndex() != -1) {
			String auxcombo = combo.getSelectedItem().getLabel();
			boolean repetir = false;
			for (int i = 0; i < lst.getItemCount(); i++) {

				Listcell des = (Listcell) lst.getItemAtIndexApi(i)
						.getChildren().get(0);
				if (des.getLabel().equalsIgnoreCase(auxcombo))
					repetir = true;
			}
			if (!repetir) {

				if (combo.getSelectedItem().getLabel().equals("")) {
					alert("Debe Seleccionar un elemento para poder agregarlo a la lista ");
					if (combo.getSelectedItem().getLabel().equals(""))
						combo.setFocus(true);

				} else {
					Listcell celdpan = new Listcell();
					celdpan.setLabel(combo.getSelectedItem().getLabel());
					Listitem item = new Listitem();
					item.appendChild(celdpan);
					lst.appendChild(item);
					combo.setSelectedIndex(-1);
				}
			} else {
				alert("El valor ya se encuentra en la lista");
			}
		} else
			alert("Debe Seleccionar un elemento para poder agregarlo a la lista");
	}

	public void quitarItemListbox(Listbox lst) throws InterruptedException {
		int indice = lst.getSelectedIndex();
		if (indice != -1) // se verifica que se ha seleccionado algun valor de
			lst.removeItemAt(indice);
		else
			alert("Debe seleccionar algun Valor para acceder a esta opcion");
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		winNeurologia.setAttribute(comp.getId() + "ctrl", this);
		servicioPaciente = (ServicioPaciente) SpringUtil
				.getBean("beanServicioPaciente");
		servicioTipoPatoligia = (ServicioTipoPatologia) SpringUtil
				.getBean("beanServicioTipoPatologia");
		servicioTipoTratamiento = (ServicioTipoTratamiento) SpringUtil
				.getBean("beanServicioTipoTratamiento");
		servicioTratamiento = (ServicioTratamiento) SpringUtil
				.getBean("beanServicioTratamiento");
		servicioNeurologia = (ServicioNeurologia) SpringUtil
				.getBean("beanServicioNeurologia");
		servicioSintoma = (ServicioSintoma) SpringUtil
				.getBean("beanServicioSintoma");
		servicioPatologia = (ServicioPatologia) SpringUtil
				.getBean("beanServicioPatologia");

		servicioCursoClinico = (ServicioCursoClinico) SpringUtil
				.getBean("beanServicioCursoClinico");
		servicioValoracionSubjetiva = (ServicioValoracionSubjetiva) SpringUtil
				.getBean("beanServicioValoracionSubjetiva");
		servicioPostura = (ServicioPostura) SpringUtil
				.getBean("beanServicioPostura");
		servicioAtaxia = (ServicioAtaxia) SpringUtil
				.getBean("beanServicioAtaxia");
		servicioParesiaPlejia = (ServicioParesiaPlejia) SpringUtil
				.getBean("beanServicioParesiaPlejia");
		servicioDolor = (ServicioDolor) SpringUtil.getBean("beanServicioDolor");
		servicioCojera = (ServicioCojera) SpringUtil
				.getBean("beanServicioCojera");
		servicioReflejoEspinal = (ServicioReflejoEspinal) SpringUtil
				.getBean("beanServicioReflejoEspinal");
		servicioReaccionPostular = (ServicioReaccionPostular) SpringUtil
				.getBean("beanServicioReaccionPostular");

		paciente = new Paciente();
		usuario = SessionAdministrator.getLoggedUsuario();
		veterinario = usuario.getPersona().getVeterinario();
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

		neurologia = new Neurologia();
		// patologias = servicioPatologia.buscarTodos('A');
		cursoClinicos = servicioCursoClinico.buscarTodos('A');
		valoracionSubjetivas = servicioValoracionSubjetiva.buscarTodos('A');
		posturas = servicioPostura.buscarTodos('A');
		ataxias = servicioAtaxia.buscarTodos('A');
		paresias = servicioParesiaPlejia.buscarTodos('A');
		paresias1 = servicioParesiaPlejia.buscarTodos('A');
		dolores = servicioDolor.buscarTodos('A');
		dolores1 = servicioDolor.buscarTodos('A');
		dolores2 = servicioDolor.buscarTodos('A');
		dolores3 = servicioDolor.buscarTodos('A');
		cojeras = servicioCojera.buscarTodos('A');
		reflejoEspinales = servicioReflejoEspinal.buscarTodos('A');
		reflejoEspinales1 = servicioReflejoEspinal.buscarTodos('A');
		reflejoEspinales2 = servicioReflejoEspinal.buscarTodos('A');
		reflejoEspinales3 = servicioReflejoEspinal.buscarTodos('A');
		reflejoEspinales4 = servicioReflejoEspinal.buscarTodos('A');
		reflejoEspinales5 = servicioReflejoEspinal.buscarTodos('A');
		reflejoEspinales6 = servicioReflejoEspinal.buscarTodos('A');
		reflejoEspinales7 = servicioReflejoEspinal.buscarTodos('A');
		reflejoEspinales8 = servicioReflejoEspinal.buscarTodos('A');
		reflejoEspinales9 = servicioReflejoEspinal.buscarTodos('A');
		reflejoEspinales10 = servicioReflejoEspinal.buscarTodos('A');
		reflejoEspinales11 = servicioReflejoEspinal.buscarTodos('A');
		reflejoEspinales12 = servicioReflejoEspinal.buscarTodos('A');
		reflejoEspinales13 = servicioReflejoEspinal.buscarTodos('A');
		reflejoEspinales14 = servicioReflejoEspinal.buscarTodos('A');
		reflejoEspinales15 = servicioReflejoEspinal.buscarTodos('A');
		reflejoEspinales16 = servicioReflejoEspinal.buscarTodos('A');
		reflejoEspinales17 = servicioReflejoEspinal.buscarTodos('A');

		reaccionPostulares = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares1 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares2 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares3 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares4 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares5 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares6 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares7 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares8 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares9 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares10 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares11 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares12 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares13 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares14 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares15 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares16 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares17 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares18 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares19 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares20 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares21 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares22 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares23 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares24 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares25 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares26 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares27 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares28 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares29 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares30 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares31 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares32 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares33 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares34 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares35 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares36 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares37 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares38 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares39 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares40 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares41 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares42 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares43 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares44 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares45 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares46 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares47 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares48 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares49 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares50 = servicioReaccionPostular.buscarTodos('A');
		reaccionPostulares51 = servicioReaccionPostular.buscarTodos('A');

		ctrlwinneurologia = (ctrlWinNeurologia) arg.get("ctrlWinNeurologia");

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
		 * winXxxxNombreDelServicioXxxx
		 * .setTitle("xxx Lo que mas les guste xxx");
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
		// onClick$btnCancelarPrincipal();

		/*
		 * setCervical(null); setToracica(null); setToracolumbar(null);
		 * setLumbar(null); setLumbosacra(null); setCaudal(null);
		 * setNinguna(null); setOtra(null);
		 */
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

		listCursoClinico.clearSelection();
		listValoracionSubjetiva.clearSelection();
		listPostura.clearSelection();
		txtOtras.setValue("");
		listAtaxia.clearSelection();
		listParesia.clearSelection();
		listDolor.clearSelection();
		listParesia1.clearSelection();
		listDolor1.clearSelection();
		listCojera.clearSelection();
		listReaccionPostular.clearSelection();
		listReaccionPostular1.clearSelection();
		listReaccionPostular2.clearSelection();
		listReaccionPostular3.clearSelection();
		listReaccionPostular4.clearSelection();
		listReaccionPostular5.clearSelection();
		listReaccionPostular6.clearSelection();
		listReaccionPostular7.clearSelection();
		listReaccionPostular8.clearSelection();
		listReaccionPostular9.clearSelection();
		listReaccionPostular10.clearSelection();
		listReaccionPostular11.clearSelection();
		listReaccionPostular12.clearSelection();
		listReaccionPostular13.clearSelection();
		listReaccionPostular14.clearSelection();
		listReaccionPostular15.clearSelection();
		listReaccionPostular16.clearSelection();
		listReaccionPostular17.clearSelection();
		listReaccionPostular18.clearSelection();
		listReaccionPostular19.clearSelection();
		listReaccionPostular20.clearSelection();
		listReaccionPostular21.clearSelection();
		listReaccionPostular22.clearSelection();
		listReaccionPostular23.clearSelection();
		listReaccionPostular24.clearSelection();
		listReaccionPostular25.clearSelection();
		listReaccionPostular26.clearSelection();
		listReaccionPostular27.clearSelection();
		listReaccionPostular28.clearSelection();
		listReaccionPostular29.clearSelection();
		listReaccionPostular30.clearSelection();
		listReaccionPostular31.clearSelection();
		listReaccionPostular32.clearSelection();
		listReaccionPostular33.clearSelection();
		listReaccionPostular34.clearSelection();
		listReaccionPostular35.clearSelection();
		listReaccionPostular36.clearSelection();
		listReaccionPostular37.clearSelection();
		listReaccionPostular38.clearSelection();
		listReaccionPostular39.clearSelection();
		listReaccionPostular40.clearSelection();
		listReaccionPostular41.clearSelection();
		listReaccionPostular42.clearSelection();
		listReaccionPostular43.clearSelection();
		listReaccionPostular44.clearSelection();
		listReaccionPostular45.clearSelection();
		listReaccionPostular46.clearSelection();
		listReaccionPostular47.clearSelection();
		listReaccionPostular48.clearSelection();
		listReaccionPostular49.clearSelection();
		listReaccionPostular50.clearSelection();
		listReaccionPostular51.clearSelection();
		listReflejoEspinal.clearSelection();
		listReflejoEspinal1.clearSelection();
		listReflejoEspinal2.clearSelection();
		listReflejoEspinal3.clearSelection();
		listReflejoEspinal4.clearSelection();
		listReflejoEspinal5.clearSelection();
		listReflejoEspinal6.clearSelection();
		listReflejoEspinal7.clearSelection();
		listReflejoEspinal8.clearSelection();
		listReflejoEspinal9.clearSelection();
		listReflejoEspinal10.clearSelection();
		listReflejoEspinal11.clearSelection();
		listReflejoEspinal12.clearSelection();
		listReflejoEspinal13.clearSelection();
		listReflejoEspinal14.clearSelection();
		listReflejoEspinal15.clearSelection();
		listReflejoEspinal16.clearSelection();
		listReflejoEspinal17.clearSelection();
		listDolor2.clearSelection();
		listDolor3.clearSelection();
		cutaneaTroncoNormal.setValue("");
		listListadoSintomas1.clearSelection();
		listListadoSintomas2.clearSelection();
		txtDiagnosticoProcedimiento.setValue("");
		txtDiagnosticoTentativo.setValue("");
		txtDiagnosticoDiferencial.setValue("");
		txtDiagnosticoDefinitivo.setValue("");
		listPatologiaTipo.clearSelection();
		listPatoligias.clearSelection();
		listListadoPatologias.clearSelection();
		txtPatologiaComentario.setValue("");
		listTratamientoTipo.clearSelection();
		listTratamiento.clearSelection();
		listListadoTratamiento.clearSelection();
		txtTratamientoIndicaciones.setValue("");
		txtTratamientoAplicado.setValue("");
		txtTratamientoEnviado.setValue("");
		txtObservaciones.setValue("");

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
				// todos
				// menos
				// buscar
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

	public void guardar() {
		/*
		 * Es aqui donde comienza la complicacion, aqui iran todos los llamados,
		 * algoritmos o artima�as necesarias para que guarde el servicio PD: No
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
			neurologia.setPaciente(paciente);
			neurologia.setVeterinario(veterinario);
			neurologia.setFecha(dbFechaActual.getValue());
			neurologia.setHora(dbFechaActual.getValue());

			if (!listListadoSintomas1.getItems().isEmpty()) {
				sets = listListadoSintomas1.getSelectedItems();
				for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxSintomas.add((Sintoma) item.getValue());
				}
				neurologia.setSintomas(auxSintomas);
			}

			if (!listListadoSintomas2.getItems().isEmpty()) {
				sets = listListadoSintomas2.getSelectedItems();
				for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxSintomas.add((Sintoma) item.getValue());
				}
				neurologia.setSintomas(auxSintomas);
			}

			if (!listListadoPatologias.getItems().isEmpty()) {
				lists = listListadoPatologias.getItems();
				for (Iterator iterator = lists.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxPatologias.add((Patologia) item.getValue());
				}
				neurologia.setPatologias(auxPatologias);
			}

			if (!listListadoTratamiento.getItems().isEmpty()) {
				lists = listListadoTratamiento.getItems();
				for (Iterator iterator = lists.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxTratamientos.add((Tratamiento) item.getValue());
				}
				neurologia.setTratamientos(auxTratamientos);
			}

			// if (listCursoClinico.getSelectedItems().isEmpty()) {
			// MensajeEmergente.mostrar("NOEMPTY", "\n Curso Clinico",
			// new MensajeListener() {
			// @Override
			// public void alDestruir() {
			// listCursoClinico.setFocus(true);
			// }
			// });
			// }
			// else if (listValoracionSubjetiva.getSelectedItems().isEmpty()) {
			// MensajeEmergente.mostrar("NOEMPTY", "\n Valoraci�n Subjetiva",
			// new MensajeListener() {
			// @Override
			// public void alDestruir() {
			// listValoracionSubjetiva.setFocus(true);
			// }
			// });
			// }
			// else if (listPostura.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Postura",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listPostura.setFocus(true);
			// }
			// });
			// }
			// else if (txtOtras.getValue().trim().equalsIgnoreCase("")) {
			// MensajeEmergente.mostrar("NOEMPTY", "\n Otras",
			// new MensajeListener() {
			// @Override
			// public void alDestruir() {
			// txtOtras.setFocus(true);
			// }
			// });
			// }
			// // else if (listAtaxia.getSelectedItems().isEmpty()) {
			// // MensajeEmergente.mostrar("NOEMPTY", "\n Ataxia",
			// // new MensajeListener() {
			// // @Override
			// // public void alDestruir() {
			// // listAtaxia.setFocus(true);
			// // }
			// // });
			// // }
			// else if (listParesia.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Paresia",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listParesia.setFocus(true);
			// }
			// });
			// }
			// else if (listDolor.getSelectedItems().isEmpty()) {
			// MensajeEmergente.mostrar("NOEMPTY", "\n Paso Corto/Rigidez",
			// new MensajeListener() {
			// @Override
			// public void alDestruir() {
			// listDolor.setFocus(true);
			// }
			// });
			// }
			// else if (listParesia1.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Plejia",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listParesia1.setFocus(true);
			// }
			// });
			// }
			// else if (listDolor1.getSelectedItems().isEmpty()) {
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Incapaz de Mantenerse en Pie",
			// new MensajeListener() {
			// @Override
			// public void alDestruir() {
			// listDolor1.setFocus(true);
			// }
			// });
			// }
			// else if (listCojera.getSelectedItems().isEmpty()) {
			// MensajeEmergente.mostrar("NOEMPTY", "\n Cojera",
			// new MensajeListener() {
			// @Override
			// public void alDestruir() {
			// listCojera.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Propiocepci�n tor�cica izquierda",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular1.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Propiocepci�n tor�cica derecha",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular1.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular2.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Propiocepci�n p�lvica izquierda",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular2.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular3.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Propiocepci�n p�lvica derecha",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular3.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular4.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Salto tor�cica izquierda",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular4.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular5.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Salto tor�cica derecha",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular5.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular6.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Salto p�lvica izquierda",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular6.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular7.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Salto p�lvica derecha",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular7.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular8.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Carretilla tor�cica izquierda",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular8.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular9.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Carretilla tor�cica derecha",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular9.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular10.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Carretilla p�lvica izquierda",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular10.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular11.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Carretilla p�lvica derecha",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular11.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular12.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Heliestaci�n marcha tor�cica izquierda",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular12.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular13.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Heliestaci�n marcha tor�cica derecha",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular13.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular14.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Heliestaci�n marcha p�lvica izquierda",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular14.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular15.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Heliestaci�n marcha p�lvica derecha",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular15.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular16.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Colocaci�n marcha tor�cica izquierda",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular16.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular17.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Colocaci�n marcha tor�cica derecha",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular17.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular18.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Colocaci�n marcha p�lvica izquierda",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular18.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular19.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Colocaci�n marcha p�lvica derecha",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular19.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular20.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Extensor postular tor�cica izquierda",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular20.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular21.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Extensor postular tor�cica derecha",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular21.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular22.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Extensor postular p�lvica izquierda",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular22.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular23.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Extensor postular p�lvica derecha",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular23.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular24.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Olfato izquierdo",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular24.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular25.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Olfato derecho",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular25.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular26.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Respuesta amenaza izquierda",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular26.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular27.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Respuesta amenaza derecha",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular27.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular28.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Tama�o pupilar izquierdo",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular28.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular29.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Tama�o pupilar derecho",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular29.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular30.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Respuesta pupilar a la luz izquierdo",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular30.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular31.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Respuesta pupilar a la luz derecho",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular31.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular32.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Estrabismo en reposo izquierdo",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular32.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular33.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Estrabismo en reposo derecho",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular33.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular34.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Estrabismo posicional izquierdo",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular34.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular35.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Estrabismo posicional derecho",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular35.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular36.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Nistagmo izquierdo",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular36.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular37.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Nistagmo derecho",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular37.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular38.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Oculovestibular izquierdo",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular38.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular39.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Oculovestibular derecho",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular39.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular40.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Sensibilidad facial izquierda",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular40.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular41.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Sensibilidad facial derecha",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular41.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular42.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Masticaci�n izquierda",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular42.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular43.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Masticaci�n derecha",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular43.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular44.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Sensibilidad ocular izquierdo",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular44.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular45.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Sensibilidad ocular derecho",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular45.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular46.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Palpebral izquierdo",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular46.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular47.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Palpebral derecho",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular47.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular48.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Degluci�n izquierdo",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular48.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular49.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Degluci�n derecho",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular49.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular50.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Lengua izquierda",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular50.setFocus(true);
			// }
			// });
			// }
			// else if (listReaccionPostular51.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Lengua derecha",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReaccionPostular51.setFocus(true);
			// }
			// });
			// }
			// else if (listReflejoEspinal.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Biceps izquierdo",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReflejoEspinal.setFocus(true);
			// }
			// });
			// }
			// else if (listReflejoEspinal1.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Biceps derecho",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReflejoEspinal1.setFocus(true);
			// }
			// });
			// }
			// else if (listReflejoEspinal2.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Triceps izquierdo",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReflejoEspinal2.setFocus(true);
			// }
			// });
			// }
			// else if (listReflejoEspinal3.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Triceps derecho",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReflejoEspinal3.setFocus(true);
			// }
			// });
			// }
			// else if (listReflejoEspinal4.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Extensor carpo radial izquierdo",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReflejoEspinal4.setFocus(true);
			// }
			// });
			// }
			// else if (listReflejoEspinal5.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Extensor carpo radial derecho",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReflejoEspinal5.setFocus(true);
			// }
			// });
			// }
			// else if (listReflejoEspinal6.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Flexor tor�cico izquierdo",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReflejoEspinal6.setFocus(true);
			// }
			// });
			// }
			// else if (listReflejoEspinal7.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Flexor tor�cico derecho",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReflejoEspinal7.setFocus(true);
			// }
			// });
			// }
			// else if (listReflejoEspinal8.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Patelar izquierdo",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReflejoEspinal8.setFocus(true);
			// }
			// });
			// }
			// else if (listReflejoEspinal9.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Patelar derecho",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReflejoEspinal9.setFocus(true);
			// }
			// });
			// }
			// else if (listReflejoEspinal10.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Tibial craneal izquierdo",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReflejoEspinal10.setFocus(true);
			// }
			// });
			// }
			// else if (listReflejoEspinal11.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Tibial craneal derecho",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReflejoEspinal11.setFocus(true);
			// }
			// });
			// }
			// else if (listReflejoEspinal12.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Gastrocnemio izquierdo",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReflejoEspinal12.setFocus(true);
			// }
			// });
			// }
			// else if (listReflejoEspinal13.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Gastrocnemio derecho",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReflejoEspinal13.setFocus(true);
			// }
			// });
			// }
			// else if (listReflejoEspinal14.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Flexor p�lvico izquierdo",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReflejoEspinal14.setFocus(true);
			// }
			// });
			// }
			// else if (listReflejoEspinal15.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Flexor p�lvico derecho",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReflejoEspinal15.setFocus(true);
			// }
			// });
			// }
			// else if (listReflejoEspinal16.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Perineal izquierdo",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReflejoEspinal16.setFocus(true);
			// }
			// });
			// }
			// else if (listReflejoEspinal17.getSelectedItems().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY", "\n Perineal derecho",
			// new MensajeListener(){
			// @Override
			// public void alDestruir(){
			// listReflejoEspinal17.setFocus(true);
			// }
			// });
			// }
			// else if (listDolor2.getSelectedItems().isEmpty()) {
			// MensajeEmergente.mostrar("NOEMPTY", "\n Atrofia",
			// new MensajeListener() {
			// @Override
			// public void alDestruir() {
			// listDolor2.setFocus(true);
			// }
			// });
			// }
			// else if (listDolor3.getSelectedItems().isEmpty()) {
			// MensajeEmergente.mostrar("NOEMPTY", "\n Dolor profundo",
			// new MensajeListener() {
			// @Override
			// public void alDestruir() {
			// listDolor3.setFocus(true);
			// }
			// });
			// }
			// else if (cutaneaTroncoNormal.getValue().isEmpty()){
			// MensajeEmergente.mostrar("NOEMPTY",
			// "\n Cutanea del tronco normal",
			// new MensajeListener() {
			// @Override
			// public void alDestruir() {
			// cutaneaTroncoNormal.setFocus(true);
			// }
			// });
			// }

			neurologia.setCursoClinico((CursoClinico) listCursoClinico
					.getSelectedItem().getValue());
			neurologia
					.setValoracionSubjetiva((ValoracionSubjetiva) listValoracionSubjetiva
							.getSelectedItem().getValue());
			neurologia.setPostura((Postura) listPostura.getSelectedItem()
					.getValue());
			neurologia.setOtras(txtOtras.getValue());
			neurologia.setAtaxia((Ataxia) listAtaxia.getSelectedItem()
					.getValue());
			neurologia.setParesiaPlejia((ParesiaPlejia) listParesia
					.getSelectedItem().getValue());
			neurologia.setDolor((Dolor) listDolor.getSelectedItem().getValue());
			neurologia.setParesiaPlejia1((ParesiaPlejia) listParesia1
					.getSelectedItem().getValue());
			neurologia.setDolor1((Dolor) listDolor1.getSelectedItem()
					.getValue());
			neurologia.setCojera((Cojera) listCojera.getSelectedItem()
					.getValue());
			neurologia
					.setReaccionPostular((ReaccionPostular) listReaccionPostular
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular1((ReaccionPostular) listReaccionPostular1
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular2((ReaccionPostular) listReaccionPostular2
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular3((ReaccionPostular) listReaccionPostular3
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular4((ReaccionPostular) listReaccionPostular4
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular5((ReaccionPostular) listReaccionPostular5
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular6((ReaccionPostular) listReaccionPostular6
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular7((ReaccionPostular) listReaccionPostular7
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular8((ReaccionPostular) listReaccionPostular8
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular9((ReaccionPostular) listReaccionPostular9
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular10((ReaccionPostular) listReaccionPostular10
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular11((ReaccionPostular) listReaccionPostular11
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular12((ReaccionPostular) listReaccionPostular12
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular13((ReaccionPostular) listReaccionPostular13
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular14((ReaccionPostular) listReaccionPostular14
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular15((ReaccionPostular) listReaccionPostular15
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular16((ReaccionPostular) listReaccionPostular16
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular17((ReaccionPostular) listReaccionPostular17
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular18((ReaccionPostular) listReaccionPostular18
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular19((ReaccionPostular) listReaccionPostular19
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular20((ReaccionPostular) listReaccionPostular20
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular21((ReaccionPostular) listReaccionPostular21
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular22((ReaccionPostular) listReaccionPostular22
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular23((ReaccionPostular) listReaccionPostular23
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular24((ReaccionPostular) listReaccionPostular24
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular25((ReaccionPostular) listReaccionPostular25
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular26((ReaccionPostular) listReaccionPostular26
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular27((ReaccionPostular) listReaccionPostular27
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular28((ReaccionPostular) listReaccionPostular28
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular29((ReaccionPostular) listReaccionPostular29
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular30((ReaccionPostular) listReaccionPostular30
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular31((ReaccionPostular) listReaccionPostular31
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular32((ReaccionPostular) listReaccionPostular32
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular33((ReaccionPostular) listReaccionPostular33
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular34((ReaccionPostular) listReaccionPostular34
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular35((ReaccionPostular) listReaccionPostular35
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular36((ReaccionPostular) listReaccionPostular36
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular37((ReaccionPostular) listReaccionPostular37
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular38((ReaccionPostular) listReaccionPostular38
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular39((ReaccionPostular) listReaccionPostular39
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular40((ReaccionPostular) listReaccionPostular40
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular41((ReaccionPostular) listReaccionPostular41
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular42((ReaccionPostular) listReaccionPostular42
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular43((ReaccionPostular) listReaccionPostular43
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular44((ReaccionPostular) listReaccionPostular44
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular45((ReaccionPostular) listReaccionPostular45
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular46((ReaccionPostular) listReaccionPostular46
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular47((ReaccionPostular) listReaccionPostular47
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular48((ReaccionPostular) listReaccionPostular48
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular49((ReaccionPostular) listReaccionPostular49
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular50((ReaccionPostular) listReaccionPostular50
							.getSelectedItem().getValue());
			neurologia
					.setReaccionPostular51((ReaccionPostular) listReaccionPostular51
							.getSelectedItem().getValue());
			neurologia.setReflejoEspinal((ReflejoEspinal) listReflejoEspinal
					.getSelectedItem().getValue());
			neurologia.setReflejoEspinal1((ReflejoEspinal) listReflejoEspinal1
					.getSelectedItem().getValue());
			neurologia.setReflejoEspinal2((ReflejoEspinal) listReflejoEspinal2
					.getSelectedItem().getValue());
			neurologia.setReflejoEspinal3((ReflejoEspinal) listReflejoEspinal3
					.getSelectedItem().getValue());
			neurologia.setReflejoEspinal4((ReflejoEspinal) listReflejoEspinal4
					.getSelectedItem().getValue());
			neurologia.setReflejoEspinal5((ReflejoEspinal) listReflejoEspinal5
					.getSelectedItem().getValue());
			neurologia.setReflejoEspinal6((ReflejoEspinal) listReflejoEspinal6
					.getSelectedItem().getValue());
			neurologia.setReflejoEspinal7((ReflejoEspinal) listReflejoEspinal7
					.getSelectedItem().getValue());
			neurologia.setReflejoEspinal8((ReflejoEspinal) listReflejoEspinal8
					.getSelectedItem().getValue());
			neurologia.setReflejoEspinal9((ReflejoEspinal) listReflejoEspinal9
					.getSelectedItem().getValue());
			neurologia
					.setReflejoEspinal10((ReflejoEspinal) listReflejoEspinal10
							.getSelectedItem().getValue());
			neurologia
					.setReflejoEspinal11((ReflejoEspinal) listReflejoEspinal11
							.getSelectedItem().getValue());
			neurologia
					.setReflejoEspinal12((ReflejoEspinal) listReflejoEspinal12
							.getSelectedItem().getValue());
			neurologia
					.setReflejoEspinal13((ReflejoEspinal) listReflejoEspinal13
							.getSelectedItem().getValue());
			neurologia
					.setReflejoEspinal14((ReflejoEspinal) listReflejoEspinal14
							.getSelectedItem().getValue());
			neurologia
					.setReflejoEspinal15((ReflejoEspinal) listReflejoEspinal15
							.getSelectedItem().getValue());
			neurologia
					.setReflejoEspinal16((ReflejoEspinal) listReflejoEspinal16
							.getSelectedItem().getValue());
			neurologia
					.setReflejoEspinal17((ReflejoEspinal) listReflejoEspinal17
							.getSelectedItem().getValue());
			neurologia.setDolor2((Dolor) listDolor2.getSelectedItem()
					.getValue());
			neurologia.setDolor3((Dolor) listDolor3.getSelectedItem()
					.getValue());
			neurologia.setCutaneaTroncoNormal(cutaneaTroncoNormal.getValue());

			neurologia.setCervical(cervical.isChecked());
			neurologia.setToracica(toracica.isChecked());
			neurologia.setToracolumbar(toracolumbar.isChecked());
			neurologia.setLumbar(lumbar.isChecked());
			neurologia.setLumbosacra(lumbosacra.isChecked());
			neurologia.setCaudal(caudal.isChecked());
			neurologia.setNinguna(ninguna.isChecked());
			neurologia.setOtra(otra.isChecked());

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
				servicioNeurologia.guardarNeurologia(neurologia);
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
		} else if (txtTratamientoEnviado.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n TratamientoEnviado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtTratamientoEnviado.setFocus(true);
						}
					});
		} else if (listCursoClinico.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Curso Clinico",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listCursoClinico.setFocus(true);
						}
					});
		} else if (listValoracionSubjetiva.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Valoraci�n Subjetiva",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listValoracionSubjetiva.setFocus(true);
						}
					});
		} else if (listPostura.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Postura",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listPostura.setFocus(true);
						}
					});
		} else if (txtOtras.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Otras",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtOtras.setFocus(true);
						}
					});
		} else if (listAtaxia.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Ataxia",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listAtaxia.setFocus(true);
						}
					});
		} else if (listParesia.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Paresia",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listParesia.setFocus(true);
						}
					});
		} else if (listDolor.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Paso Corto/Rigidez",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listDolor.setFocus(true);
						}
					});
		} else if (listParesia1.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Plejia",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listParesia1.setFocus(true);
						}
					});
		} else if (listDolor1.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Incapaz de Mantenerse en Pie", new MensajeListener() {
						@Override
						public void alDestruir() {
							listDolor1.setFocus(true);
						}
					});
		} else if (listCojera.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Cojera",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listCojera.setFocus(true);
						}
					});
		} else if (listReaccionPostular.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Propiocepci�n tor�cica izquierda",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular.setFocus(true);
						}
					});
		} else if (listReaccionPostular1.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Propiocepci�n tor�cica derecha", new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular1.setFocus(true);
						}
					});
		} else if (listReaccionPostular2.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Propiocepci�n p�lvica izquierda",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular2.setFocus(true);
						}
					});
		} else if (listReaccionPostular3.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Propiocepci�n p�lvica derecha", new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular3.setFocus(true);
						}
					});
		} else if (listReaccionPostular4.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Salto tor�cica izquierda",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular4.setFocus(true);
						}
					});
		} else if (listReaccionPostular5.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Salto tor�cica derecha",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular5.setFocus(true);
						}
					});
		} else if (listReaccionPostular6.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Salto p�lvica izquierda",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular6.setFocus(true);
						}
					});
		} else if (listReaccionPostular7.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Salto p�lvica derecha",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular7.setFocus(true);
						}
					});
		} else if (listReaccionPostular8.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Carretilla tor�cica izquierda", new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular8.setFocus(true);
						}
					});
		} else if (listReaccionPostular9.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Carretilla tor�cica derecha", new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular9.setFocus(true);
						}
					});
		} else if (listReaccionPostular10.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Carretilla p�lvica izquierda", new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular10.setFocus(true);
						}
					});
		} else if (listReaccionPostular11.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Carretilla p�lvica derecha", new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular11.setFocus(true);
						}
					});
		} else if (listReaccionPostular12.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Heliestaci�n marcha tor�cica izquierda",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular12.setFocus(true);
						}
					});
		} else if (listReaccionPostular13.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Heliestaci�n marcha tor�cica derecha",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular13.setFocus(true);
						}
					});
		} else if (listReaccionPostular14.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Heliestaci�n marcha p�lvica izquierda",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular14.setFocus(true);
						}
					});
		} else if (listReaccionPostular15.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Heliestaci�n marcha p�lvica derecha",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular15.setFocus(true);
						}
					});
		} else if (listReaccionPostular16.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Colocaci�n marcha tor�cica izquierda",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular16.setFocus(true);
						}
					});
		} else if (listReaccionPostular17.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Colocaci�n marcha tor�cica derecha",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular17.setFocus(true);
						}
					});
		} else if (listReaccionPostular18.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Colocaci�n marcha p�lvica izquierda",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular18.setFocus(true);
						}
					});
		} else if (listReaccionPostular19.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Colocaci�n marcha p�lvica derecha",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular19.setFocus(true);
						}
					});
		} else if (listReaccionPostular20.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Extensor postular tor�cica izquierda",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular20.setFocus(true);
						}
					});
		} else if (listReaccionPostular21.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Extensor postular tor�cica derecha",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular21.setFocus(true);
						}
					});
		} else if (listReaccionPostular22.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Extensor postular p�lvica izquierda",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular22.setFocus(true);
						}
					});
		} else if (listReaccionPostular23.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Extensor postular p�lvica derecha",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular23.setFocus(true);
						}
					});
		} else if (listReaccionPostular24.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Olfato izquierdo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular24.setFocus(true);
						}
					});
		} else if (listReaccionPostular25.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Olfato derecho",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular25.setFocus(true);
						}
					});
		} else if (listReaccionPostular26.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Respuesta amenaza izquierda", new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular26.setFocus(true);
						}
					});
		} else if (listReaccionPostular27.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Respuesta amenaza derecha",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular27.setFocus(true);
						}
					});
		} else if (listReaccionPostular28.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Tama�o pupilar izquierdo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular28.setFocus(true);
						}
					});
		} else if (listReaccionPostular29.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Tama�o pupilar derecho",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular29.setFocus(true);
						}
					});
		} else if (listReaccionPostular30.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Respuesta pupilar a la luz izquierdo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular30.setFocus(true);
						}
					});
		} else if (listReaccionPostular31.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Respuesta pupilar a la luz derecho",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular31.setFocus(true);
						}
					});
		} else if (listReaccionPostular32.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Estrabismo en reposo izquierdo", new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular32.setFocus(true);
						}
					});
		} else if (listReaccionPostular33.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Estrabismo en reposo derecho", new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular33.setFocus(true);
						}
					});
		} else if (listReaccionPostular34.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Estrabismo posicional izquierdo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular34.setFocus(true);
						}
					});
		} else if (listReaccionPostular35.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Estrabismo posicional derecho", new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular35.setFocus(true);
						}
					});
		} else if (listReaccionPostular36.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Nistagmo izquierdo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular36.setFocus(true);
						}
					});
		} else if (listReaccionPostular37.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Nistagmo derecho",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular37.setFocus(true);
						}
					});
		} else if (listReaccionPostular38.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Oculovestibular izquierdo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular38.setFocus(true);
						}
					});
		} else if (listReaccionPostular39.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Oculovestibular derecho",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular39.setFocus(true);
						}
					});
		} else if (listReaccionPostular40.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Sensibilidad facial izquierda", new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular40.setFocus(true);
						}
					});
		} else if (listReaccionPostular41.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Sensibilidad facial derecha", new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular41.setFocus(true);
						}
					});
		} else if (listReaccionPostular42.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Masticaci�n izquierda",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular42.setFocus(true);
						}
					});
		} else if (listReaccionPostular43.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Masticaci�n derecha",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular43.setFocus(true);
						}
					});
		} else if (listReaccionPostular44.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Sensibilidad ocular izquierdo", new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular44.setFocus(true);
						}
					});
		} else if (listReaccionPostular45.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Sensibilidad ocular derecho", new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular45.setFocus(true);
						}
					});
		} else if (listReaccionPostular46.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Palpebral izquierdo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular46.setFocus(true);
						}
					});
		} else if (listReaccionPostular47.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Palpebral derecho",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular47.setFocus(true);
						}
					});
		} else if (listReaccionPostular48.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Degluci�n izquierdo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular48.setFocus(true);
						}
					});
		} else if (listReaccionPostular49.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Degluci�n derecho",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular49.setFocus(true);
						}
					});
		} else if (listReaccionPostular50.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Lengua izquierda",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular50.setFocus(true);
						}
					});
		} else if (listReaccionPostular51.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Lengua derecha",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReaccionPostular51.setFocus(true);
						}
					});
		} else if (listReflejoEspinal.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Biceps izquierdo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReflejoEspinal.setFocus(true);
						}
					});
		} else if (listReflejoEspinal1.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Biceps derecho",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReflejoEspinal1.setFocus(true);
						}
					});
		} else if (listReflejoEspinal2.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Triceps izquierdo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReflejoEspinal2.setFocus(true);
						}
					});
		} else if (listReflejoEspinal3.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Triceps derecho",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReflejoEspinal3.setFocus(true);
						}
					});
		} else if (listReflejoEspinal4.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Extensor carpo radial izquierdo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReflejoEspinal4.setFocus(true);
						}
					});
		} else if (listReflejoEspinal5.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\n Extensor carpo radial derecho", new MensajeListener() {
						@Override
						public void alDestruir() {
							listReflejoEspinal5.setFocus(true);
						}
					});
		} else if (listReflejoEspinal6.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Flexor tor�cico izquierdo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReflejoEspinal6.setFocus(true);
						}
					});
		} else if (listReflejoEspinal7.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Flexor tor�cico derecho",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReflejoEspinal7.setFocus(true);
						}
					});
		} else if (listReflejoEspinal8.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Patelar izquierdo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReflejoEspinal8.setFocus(true);
						}
					});
		} else if (listReflejoEspinal9.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Patelar derecho",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReflejoEspinal9.setFocus(true);
						}
					});
		} else if (listReflejoEspinal10.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Tibial craneal izquierdo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReflejoEspinal10.setFocus(true);
						}
					});
		} else if (listReflejoEspinal11.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Tibial craneal derecho",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReflejoEspinal11.setFocus(true);
						}
					});
		} else if (listReflejoEspinal12.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Gastrocnemio izquierdo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReflejoEspinal12.setFocus(true);
						}
					});
		} else if (listReflejoEspinal13.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Gastrocnemio derecho",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReflejoEspinal13.setFocus(true);
						}
					});
		} else if (listReflejoEspinal14.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Flexor p�lvico izquierdo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReflejoEspinal14.setFocus(true);
						}
					});
		} else if (listReflejoEspinal15.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Flexor p�lvico derecho",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReflejoEspinal15.setFocus(true);
						}
					});
		} else if (listReflejoEspinal16.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Perineal izquierdo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReflejoEspinal16.setFocus(true);
						}
					});
		} else if (listReflejoEspinal17.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Perineal derecho",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listReflejoEspinal17.setFocus(true);
						}
					});
		} else if (listDolor2.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Atrofia",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listDolor2.setFocus(true);
						}
					});
		} else if (listDolor3.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Dolor profundo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listDolor3.setFocus(true);
						}
					});
		} else if (cutaneaTroncoNormal.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Cutanea del tronco normal",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							cutaneaTroncoNormal.setFocus(true);
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

}

/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.HashMap;
import java.util.Map;

import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.components.HelperDateAge;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.componentszk.SessionAdministrator;
import org.ucla.sigma.modelo.CirugiaPreOperatoria;
import org.ucla.sigma.modelo.CirugiaTransquirurgico;
import org.ucla.sigma.modelo.Paciente;
import org.ucla.sigma.modelo.Referencia;
import org.ucla.sigma.modelo.Usuario;
import org.ucla.sigma.modelo.Veterinario;
import org.ucla.sigma.servicio.ServicioCirugiaPreOperatoria;
import org.ucla.sigma.servicio.ServicioCirugiaTransquirurgico;
import org.ucla.sigma.servicio.ServicioPaciente;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.North;
import org.zkoss.zul.South;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author rafael
 *
 */
public class ctrlWinServicioCirugiaTransquirurgico extends
		GenericForwardComposer {

	private Window winServicioCirugiaTransquirurgico;
	private Button btnCancelarPrincipal;
	private Button btnGuardarPrincipal;
	private South blSur;
	
	private Textbox txtComentarios;
	private Textbox txtOtroInstrumental;
	private Checkbox ckOftalmologia;
	private Checkbox ckTraumatologia;
	private Checkbox ckGeneral;
	private Spinner spOtro;
	private Textbox txtOtroCalibre;
	private Textbox txtOtroMaterial;
	private Spinner spPDS;
	private Textbox txtPDSCalibre;
	private Spinner spMonocryl;
	private Textbox txtMonocrylCalibre;
	private Spinner spProlene;
	private Textbox txtProleneCalibre;
	private Spinner spNylon;
	private Textbox txtNylonCalibre;
	private Spinner spSafil;
	private Textbox txtSafilCalibre;
	private Spinner spVicryl;
	private Textbox txtVicrylCalibre;
	private Textbox txtTecnica;
	
	private Div contCentro;
	private Center blCentro;
	private Button btnDefuncion;
	private Button btnReferenciaMedica;
	private Button btnHistorial;
	private Datebox dbFechaActual;
	private Textbox txtNombreResponsable;
	private Textbox txtCi;
	private Textbox txtRaza;
	private Textbox txtEspecie;
	private Textbox txtSexo;
	private Textbox txtEdad;
	private Textbox txtNombrePaciente;
	private Button btnBuscar;
	private Textbox txtHm;
	private North blNorte;
	
	private ServicioCirugiaTransquirurgico servicioCirugiaTransquirurgico;
	private CirugiaTransquirurgico cirugiaTransquirurgico;
	private Usuario usuario;
	private Veterinario veterinario;
	private Paciente paciente;
	private ServicioPaciente servicioPaciente;
	
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtHm.setFocus(true);
		};
	};

	/**
	 *
	 *
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winServicioCirugiaTransquirurgico.setAttribute(comp.getId()+"ctrl", this);
		servicioPaciente = (ServicioPaciente) SpringUtil
				.getBean("beanServicioPaciente");
		servicioCirugiaTransquirurgico = (ServicioCirugiaTransquirurgico) SpringUtil
				.getBean("beanServicioCirugiaTransquirurgico");
		
		cirugiaTransquirurgico = new CirugiaTransquirurgico();
		
		usuario = SessionAdministrator.getLoggedUsuario();
		veterinario = usuario.getPersona().getVeterinario();
		dbFechaActual.setValue(HelperDate.now());
		
		visibilidadSecciones(true, false, false);
		actividadBotones(true, true, false, true, true, true);
		
		txtHm.setValue(((Referencia) arg.get("objeto")).getHistorial()
				.getPaciente().getHistoriaMedica());
		btnBuscar.setVisible(false);
		onClick$btnBuscar();

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
	
	public void guardar() {
		/*
		 * Es aqui donde comienza la complicacion, aqui iran todos los llamados,
		 * algoritmos o artimañas necesarias para que guarde el servicio PD: No
		 * hay mallor detalle con respecto a como hacer con algunas situacines,
		 * asi que si tiene dudas, dirijance al controlador de consulta
		 * general(ctrlWinConsultaGeneral.java) para guiarce, o pregunten
		 */

		if (validar()) {
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
				cirugiaTransquirurgico.setPaciente(paciente);
				cirugiaTransquirurgico.setVeterinario(veterinario);
				cirugiaTransquirurgico.setFecha(dbFechaActual.getValue());
				cirugiaTransquirurgico.setHora(dbFechaActual.getValue());
				cirugiaTransquirurgico.setInstrumentalGeneral(ckGeneral.isChecked());
				cirugiaTransquirurgico.setInstrumentalOftalmologia(ckOftalmologia.isChecked());
				cirugiaTransquirurgico.setInstrumentalTraumatologia(ckTraumatologia.isChecked());
				servicioCirugiaTransquirurgico.guardarCirugiaTransquirurgico(cirugiaTransquirurgico);
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

		
		if (txtTecnica.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Tecnica",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtTecnica.setFocus(true);
						}
					});
		} else if (txtVicrylCalibre.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Vicry",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtVicrylCalibre.setFocus(true);
						}
					});
		} else if (spVicryl.getValue() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Cantidad Vicry",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spVicryl.setFocus(true);
						}
					});
		} else if (txtSafilCalibre.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Safil",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtSafilCalibre.setFocus(true);
						}
					});
		} else if (spSafil.getValue() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Cantidad Safil",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spSafil.setFocus(true);
						}
					});
		} else if (txtNylonCalibre.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Nylon",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtNylonCalibre.setFocus(true);
						}
					});
		} else if (spNylon.getValue() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Nylon Cantidad",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spNylon.setFocus(true);
						}
					});
		} else if (txtProleneCalibre.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Prolene",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtProleneCalibre.setFocus(true);
						}
					});
		} else if (spProlene.getValue() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Cantidad Prolene",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtVicrylCalibre.setFocus(true);
						}
					});
		} else if (txtMonocrylCalibre.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Monocry",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtMonocrylCalibre.setFocus(true);
						}
					});
		} else if (spMonocryl.getValue() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Monocry",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spMonocryl.setFocus(true);
						}
					});
		} else if (txtPDSCalibre.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n PDS",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtPDSCalibre.setFocus(true);
						}
					});
		} else if (spPDS.getValue() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Cantidad PDS",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spPDS.setFocus(true);
						}
					});
		} else if (txtOtroMaterial.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Otro Material",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtOtroMaterial.setFocus(true);
						}
					});
		} else if (txtOtroCalibre.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Otro Calibre",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtOtroCalibre.setFocus(true);
						}
					});
		} else if (spOtro.getValue() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Cantidad Otro",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spOtro.setFocus(true);
						}
					});
		} else if (txtComentarios.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Comentario",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							spOtro.setFocus(true);
						}
					});
		} else {
			valido = true;
		}
		return valido;
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

	public Window getWinServicioCirugiaTransquirurgico() {
		return winServicioCirugiaTransquirurgico;
	}

	public void setWinServicioCirugiaTransquirurgico(
			Window winServicioCirugiaTransquirurgico) {
		this.winServicioCirugiaTransquirurgico = winServicioCirugiaTransquirurgico;
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

	public South getBlSur() {
		return blSur;
	}

	public void setBlSur(South blSur) {
		this.blSur = blSur;
	}

	public Textbox getTxtComentarios() {
		return txtComentarios;
	}

	public void setTxtComentarios(Textbox txtComentarios) {
		this.txtComentarios = txtComentarios;
	}

	public Textbox getTxtOtroInstrumental() {
		return txtOtroInstrumental;
	}

	public void setTxtOtroInstrumental(Textbox txtOtroInstrumental) {
		this.txtOtroInstrumental = txtOtroInstrumental;
	}

	public Checkbox getCkOftalmologia() {
		return ckOftalmologia;
	}

	public void setCkOftalmologia(Checkbox ckOftalmologia) {
		this.ckOftalmologia = ckOftalmologia;
	}

	public Checkbox getCkTraumatologia() {
		return ckTraumatologia;
	}

	public void setCkTraumatologia(Checkbox ckTraumatologia) {
		this.ckTraumatologia = ckTraumatologia;
	}

	public Checkbox getCkGeneral() {
		return ckGeneral;
	}

	public void setCkGeneral(Checkbox ckGeneral) {
		this.ckGeneral = ckGeneral;
	}

	public Spinner getSpOtro() {
		return spOtro;
	}

	public void setSpOtro(Spinner spOtro) {
		this.spOtro = spOtro;
	}

	public Textbox getTxtOtroCalibre() {
		return txtOtroCalibre;
	}

	public void setTxtOtroCalibre(Textbox txtOtroCalibre) {
		this.txtOtroCalibre = txtOtroCalibre;
	}

	public Textbox getTxtOtroMaterial() {
		return txtOtroMaterial;
	}

	public void setTxtOtroMaterial(Textbox txtOtroMaterial) {
		this.txtOtroMaterial = txtOtroMaterial;
	}

	public Spinner getSpPDS() {
		return spPDS;
	}

	public void setSpPDS(Spinner spPDS) {
		this.spPDS = spPDS;
	}

	public Textbox getTxtPDSCalibre() {
		return txtPDSCalibre;
	}

	public void setTxtPDSCalibre(Textbox txtPDSCalibre) {
		this.txtPDSCalibre = txtPDSCalibre;
	}

	public Spinner getSpMonocryl() {
		return spMonocryl;
	}

	public void setSpMonocryl(Spinner spMonocryl) {
		this.spMonocryl = spMonocryl;
	}

	public Textbox getTxtMonocrylCalibre() {
		return txtMonocrylCalibre;
	}

	public void setTxtMonocrylCalibre(Textbox txtMonocrylCalibre) {
		this.txtMonocrylCalibre = txtMonocrylCalibre;
	}

	public Spinner getSpProlene() {
		return spProlene;
	}

	public void setSpProlene(Spinner spProlene) {
		this.spProlene = spProlene;
	}

	public Textbox getTxtProleneCalibre() {
		return txtProleneCalibre;
	}

	public void setTxtProleneCalibre(Textbox txtProleneCalibre) {
		this.txtProleneCalibre = txtProleneCalibre;
	}

	public Spinner getSpNylon() {
		return spNylon;
	}

	public void setSpNylon(Spinner spNylon) {
		this.spNylon = spNylon;
	}

	public Textbox getTxtNylonCalibre() {
		return txtNylonCalibre;
	}

	public void setTxtNylonCalibre(Textbox txtNylonCalibre) {
		this.txtNylonCalibre = txtNylonCalibre;
	}

	public Spinner getSpSafil() {
		return spSafil;
	}

	public void setSpSafil(Spinner spSafil) {
		this.spSafil = spSafil;
	}

	public Textbox getTxtSafilCalibre() {
		return txtSafilCalibre;
	}

	public void setTxtSafilCalibre(Textbox txtSafilCalibre) {
		this.txtSafilCalibre = txtSafilCalibre;
	}

	public Spinner getSpVicryl() {
		return spVicryl;
	}

	public void setSpVicryl(Spinner spVicryl) {
		this.spVicryl = spVicryl;
	}

	public Textbox getTxtVicrylCalibre() {
		return txtVicrylCalibre;
	}

	public void setTxtVicrylCalibre(Textbox txtVicrylCalibre) {
		this.txtVicrylCalibre = txtVicrylCalibre;
	}

	public Textbox getTxtTecnica() {
		return txtTecnica;
	}

	public void setTxtTecnica(Textbox txtTecnica) {
		this.txtTecnica = txtTecnica;
	}

	public Div getContCentro() {
		return contCentro;
	}

	public void setContCentro(Div contCentro) {
		this.contCentro = contCentro;
	}

	public Center getBlCentro() {
		return blCentro;
	}

	public void setBlCentro(Center blCentro) {
		this.blCentro = blCentro;
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

	public Datebox getDbFechaActual() {
		return dbFechaActual;
	}

	public void setDbFechaActual(Datebox dbFechaActual) {
		this.dbFechaActual = dbFechaActual;
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

	public Textbox getTxtNombrePaciente() {
		return txtNombrePaciente;
	}

	public void setTxtNombrePaciente(Textbox txtNombrePaciente) {
		this.txtNombrePaciente = txtNombrePaciente;
	}

	public Button getBtnBuscar() {
		return btnBuscar;
	}

	public void setBtnBuscar(Button btnBuscar) {
		this.btnBuscar = btnBuscar;
	}

	public Textbox getTxtHm() {
		return txtHm;
	}

	public void setTxtHm(Textbox txtHm) {
		this.txtHm = txtHm;
	}

	public North getBlNorte() {
		return blNorte;
	}

	public void setBlNorte(North blNorte) {
		this.blNorte = blNorte;
	}

	public ServicioCirugiaTransquirurgico getServicioCirugiaTransquirurgico() {
		return servicioCirugiaTransquirurgico;
	}

	public void setServicioCirugiaTransquirurgico(
			ServicioCirugiaTransquirurgico servicioCirugiaTransquirurgico) {
		this.servicioCirugiaTransquirurgico = servicioCirugiaTransquirurgico;
	}

	public CirugiaTransquirurgico getCirugiaTransquirurgico() {
		return cirugiaTransquirurgico;
	}

	public void setCirugiaTransquirurgico(
			CirugiaTransquirurgico cirugiaTransquirurgico) {
		this.cirugiaTransquirurgico = cirugiaTransquirurgico;
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

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public ServicioPaciente getServicioPaciente() {
		return servicioPaciente;
	}

	public void setServicioPaciente(ServicioPaciente servicioPaciente) {
		this.servicioPaciente = servicioPaciente;
	}

	public MensajeListener getAsignarFocusBuscar() {
		return asignarFocusBuscar;
	}

	public void setAsignarFocusBuscar(MensajeListener asignarFocusBuscar) {
		this.asignarFocusBuscar = asignarFocusBuscar;
	}
	
	

}

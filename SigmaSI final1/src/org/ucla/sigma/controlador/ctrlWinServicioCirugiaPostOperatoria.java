/**
 * 
 */
package org.ucla.sigma.controlador;

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
import org.ucla.sigma.modelo.CirugiaPostOperatoria;
import org.ucla.sigma.modelo.EspImagenologia;
import org.ucla.sigma.modelo.Paciente;
import org.ucla.sigma.modelo.Patologia;
import org.ucla.sigma.modelo.Referencia;
import org.ucla.sigma.modelo.Sintoma;
import org.ucla.sigma.modelo.Tratamiento;
import org.ucla.sigma.modelo.Usuario;
import org.ucla.sigma.modelo.Veterinario;
import org.ucla.sigma.servicio.ServicioCirugiaPostOperatoria;
import org.ucla.sigma.servicio.ServicioPaciente;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.North;
import org.zkoss.zul.South;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Window;

/**
 * @author lis
 * 
 */
public class ctrlWinServicioCirugiaPostOperatoria extends
		GenericForwardComposer {

	private Window winServicioCirugiaPostOperatoria;
	private Button btnCancelarPrincipal;
	private Button btnGuardarPrincipal;
	private South blSur;

	private Textbox txtComentarios;
	private Textbox txtOtros;
	private Textbox txtDosis4;
	private Textbox txtAnalgesico2;
	private Textbox txtDosis3;
	private Textbox txtAnalgesico1;
	private Textbox txtDosis2;
	private Textbox txtAntibiotico2;
	private Textbox txtDosis1;
	private Textbox txtAntibiotico1;
	private Timebox tbRecuperacion;

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

	private CirugiaPostOperatoria cirugiaPostOperatoria;
	private ServicioCirugiaPostOperatoria servicioCirugiaPostOperatoria;
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
		winServicioCirugiaPostOperatoria.setAttribute(comp.getId() + "ctrl",
				this);
		cirugiaPostOperatoria = new CirugiaPostOperatoria();
		usuario = SessionAdministrator.getLoggedUsuario();
		veterinario = usuario.getPersona().getVeterinario();
		dbFechaActual.setValue(HelperDate.now());
		servicioPaciente = (ServicioPaciente) SpringUtil
				.getBean("beanServicioPaciente");
		servicioCirugiaPostOperatoria = (ServicioCirugiaPostOperatoria) SpringUtil
				.getBean("beanServicioCirugiaPostOperatoria");

		txtHm.setValue(((Referencia) arg.get("objeto")).getHistorial()
				.getPaciente().getHistoriaMedica());
		visibilidadSecciones(true, false, false);
		actividadBotones(true, true, false, true, true, true);
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
				cirugiaPostOperatoria.setPaciente(paciente);
				cirugiaPostOperatoria.setVeterinario(veterinario);
				cirugiaPostOperatoria.setFecha(dbFechaActual.getValue());
				cirugiaPostOperatoria.setHora(dbFechaActual.getValue());
				servicioCirugiaPostOperatoria
						.guardarCirugiaPostOperatoria(cirugiaPostOperatoria);
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

		if (tbRecuperacion.getValue() == null) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Recuperacion",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							tbRecuperacion.setFocus(true);
						}
					});

		} else if (txtAntibiotico1.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Antibiotico",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtAntibiotico1.setFocus(true);
						}
					});

		} else if (txtDosis1.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Dosis",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtDosis1.setFocus(true);
						}
					});

		} else if (txtAntibiotico2.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Antibiotico",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtAntibiotico2.setFocus(true);
						}
					});

		} else if (txtDosis2.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Dosis",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtDosis2.setFocus(true);
						}
					});

		} else if (txtAnalgesico1.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Analgesico",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtAnalgesico1.setFocus(true);
						}
					});

		} else if (txtDosis3.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Dosis",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtDosis3.setFocus(true);
						}
					});

		} else if (txtAnalgesico2.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Analgesico",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtAnalgesico2.setFocus(true);
						}
					});

		} else if (txtDosis4.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Dosis",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtDosis4.setFocus(true);
						}
					});

		} else if (txtOtros.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Otros",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtOtros.setFocus(true);
						}
					});

		} else if (txtComentarios.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\n Comentario",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtComentarios.setFocus(true);
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

	public Window getWinServicioCirugiaPostOperatoria() {
		return winServicioCirugiaPostOperatoria;
	}

	public void setWinServicioCirugiaPostOperatoria(
			Window winServicioCirugiaPostOperatoria) {
		this.winServicioCirugiaPostOperatoria = winServicioCirugiaPostOperatoria;
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

	public Textbox getTxtOtros() {
		return txtOtros;
	}

	public void setTxtOtros(Textbox txtOtros) {
		this.txtOtros = txtOtros;
	}

	public Textbox getTxtDosis4() {
		return txtDosis4;
	}

	public void setTxtDosis4(Textbox txtDosis4) {
		this.txtDosis4 = txtDosis4;
	}

	public Textbox getTxtAnalgesico2() {
		return txtAnalgesico2;
	}

	public void setTxtAnalgesico2(Textbox txtAnalgesico2) {
		this.txtAnalgesico2 = txtAnalgesico2;
	}

	public Textbox getTxtDosis3() {
		return txtDosis3;
	}

	public void setTxtDosis3(Textbox txtDosis3) {
		this.txtDosis3 = txtDosis3;
	}

	public Textbox getTxtAnalgesico1() {
		return txtAnalgesico1;
	}

	public void setTxtAnalgesico1(Textbox txtAnalgesico1) {
		this.txtAnalgesico1 = txtAnalgesico1;
	}

	public Textbox getTxtDosis2() {
		return txtDosis2;
	}

	public void setTxtDosis2(Textbox txtDosis2) {
		this.txtDosis2 = txtDosis2;
	}

	public Textbox getTxtAntibiotico2() {
		return txtAntibiotico2;
	}

	public void setTxtAntibiotico2(Textbox txtAntibiotico2) {
		this.txtAntibiotico2 = txtAntibiotico2;
	}

	public Textbox getTxtDosis1() {
		return txtDosis1;
	}

	public void setTxtDosis1(Textbox txtDosis1) {
		this.txtDosis1 = txtDosis1;
	}

	public Textbox getTxtAntibiotico1() {
		return txtAntibiotico1;
	}

	public void setTxtAntibiotico1(Textbox txtAntibiotico1) {
		this.txtAntibiotico1 = txtAntibiotico1;
	}

	public Timebox getTbRecuperacion() {
		return tbRecuperacion;
	}

	public void setTbRecuperacion(Timebox tbRecuperacion) {
		this.tbRecuperacion = tbRecuperacion;
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

	public CirugiaPostOperatoria getCirugiaPostOperatoria() {
		return cirugiaPostOperatoria;
	}

	public void setCirugiaPostOperatoria(
			CirugiaPostOperatoria cirugiaPostOperatoria) {
		this.cirugiaPostOperatoria = cirugiaPostOperatoria;
	}

	public ServicioCirugiaPostOperatoria getServicioCirugiaPostOperatoria() {
		return servicioCirugiaPostOperatoria;
	}

	public void setServicioCirugiaPostOperatoria(
			ServicioCirugiaPostOperatoria servicioCirugiaPostOperatoria) {
		this.servicioCirugiaPostOperatoria = servicioCirugiaPostOperatoria;
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

package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.ConsultaGeneral;
import org.ucla.sigma.modelo.FichaMedica;
import org.ucla.sigma.modelo.Paciente;
import org.ucla.sigma.modelo.Veterinario;
import org.ucla.sigma.servicio.ServicioConsultaGeneral;
import org.ucla.sigma.servicio.ServicioFichaMedica;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinFichaMedica extends GenericForwardComposer {

	// Variables -> Zul ---------------------------------------------------------------------------
	
	private Window winFichaMedica;
	private Textbox txtObservaciones;
	private Textbox txtAlergias;
	private Textbox txtCaracteristicas;
	private Tabbox tbFichaMedica;
	
	// Variables -> Zul Propias -------------------------------------------------------------------
	
	// Variables y Servicios -> Modelo ------------------------------------------------------------
	
	private ServicioFichaMedica servicioFichaMedica;
	private FichaMedica fichaMedica;
	private Paciente paciente;
	private Veterinario veterinario;
	
	// Variables -> Entorno -----------------------------------------------------------------------
	
	private ctrlWinServicioConsultaGeneral ctrlwinservicioconsultageneral;
	
	// Codigo -> Carga Inicial --------------------------------------------------------------------
			
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		 winFichaMedica.setAttribute(comp.getId() + "ctrl", this);
		 servicioFichaMedica = (ServicioFichaMedica) SpringUtil
				 .getBean("beanServicioFichaMedica");
		fichaMedica = new FichaMedica();
		paciente = new Paciente();
		veterinario = new Veterinario();
 		ctrlwinservicioconsultageneral = (ctrlWinServicioConsultaGeneral) arg.get("ctrlWinServicioConsultaGeneral");
 		ctrlwinservicioconsultageneral.setCtrlwinfichamedica(this);
 		paciente = ctrlwinservicioconsultageneral.getPaciente();
 		veterinario = ctrlwinservicioconsultageneral.getVeterinario();
	}
	
	// Codigo -> Botones y Secciones --------------------------------------------------------------

	// Codigo -> Entorno --------------------------------------------------------------------------
	
	public void guardar(){
		if (validar()) {
			fichaMedica.setPaciente(paciente);
			fichaMedica.setVeterinario(veterinario);
			fichaMedica.setFecha(ctrlwinservicioconsultageneral.getDbFechaActual().getValue()); 
			fichaMedica.setHora(ctrlwinservicioconsultageneral.getDbFechaActual().getValue()); 
			try {
				servicioFichaMedica.guardarFichaMedica(fichaMedica);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinservicioconsultageneral.setPrimeraVez(false);
						ctrlwinservicioconsultageneral.cargar();
						winFichaMedica.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}
		}
	}
	
	public boolean validar(){
		boolean valido = false;
			if (txtCaracteristicas.getValue().trim().equalsIgnoreCase("")) {
				MensajeEmergente.mostrar("NOEMPTY", "\nSeñales Caracteristicas",
						new MensajeListener() {
							@Override
							public void alDestruir() {
								txtCaracteristicas.setFocus(true);
							}
						});
		} else	
			if (txtAlergias.getValue().trim().equalsIgnoreCase("")) {
				MensajeEmergente.mostrar("NOEMPTY", "\nAlergias",
						new MensajeListener() {
							@Override
							public void alDestruir() {
								txtAlergias.setFocus(true);
							}
						});
		} else
			if (txtObservaciones.getValue().trim().equalsIgnoreCase("")) {
				MensajeEmergente.mostrar("NOEMPTY", "\nObservaciones Medicas",
						new MensajeListener() {
							@Override
							public void alDestruir() {
								txtObservaciones.setFocus(true);
							}
						});
		} else {
			valido = true;
		}
		return valido;	
	}
	
	// Codigo -> Getters y Setters ----------------------------------------------------------------
	
	public Window getWinFichaMedica() {
		return winFichaMedica;
	}

	public void setWinFichaMedica(Window winFichaMedica) {
		this.winFichaMedica = winFichaMedica;
	}

	public Textbox getTxtObservaciones() {
		return txtObservaciones;
	}

	public void setTxtObservaciones(Textbox txtObservaciones) {
		this.txtObservaciones = txtObservaciones;
	}

	public Textbox getTxtAlergias() {
		return txtAlergias;
	}

	public void setTxtAlergias(Textbox txtAlergias) {
		this.txtAlergias = txtAlergias;
	}

	public Textbox getTxtCaracteristicas() {
		return txtCaracteristicas;
	}

	public void setTxtCaracteristicas(Textbox txtCaracteristicas) {
		this.txtCaracteristicas = txtCaracteristicas;
	}

	public FichaMedica getFichaMedica() {
		return fichaMedica;
	}

	public void setFichaMedica(FichaMedica fichaMedica) {
		this.fichaMedica = fichaMedica;
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

	public ctrlWinServicioConsultaGeneral getCtrlwinservicioconsultageneral() {
		return ctrlwinservicioconsultageneral;
	}

	public void setCtrlwinservicioconsultageneral(
			ctrlWinServicioConsultaGeneral ctrlwinservicioconsultageneral) {
		this.ctrlwinservicioconsultageneral = ctrlwinservicioconsultageneral;
	}	
	
}

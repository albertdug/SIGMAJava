/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.componentsjr.HelperJR;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Carnet;
import org.ucla.sigma.modelo.Hospital;
import org.ucla.sigma.modelo.Paciente;
import org.ucla.sigma.modelo.Responsable;
import org.ucla.sigma.servicio.ServicioCarnet;
import org.ucla.sigma.servicio.ServicioHospital;
import org.ucla.sigma.servicio.ServicioPaciente;
import org.ucla.sigma.servicio.ServicioResponsable;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author rafael
 * 
 */
public class ctrlWinFichaBasica extends GenericForwardComposer {

	private Window winFichaBasica;
	private Button btnGenerarCarnet;
	private Listbox listPaciente;
	private Button btnEditarPac;
	private Button btnNuevoPac;
	private Button btnCancelar;
	private Textbox txtCiudadResponsable;
	private Textbox txtDirecionResponsable;
	private Textbox txtCorreoResponsable;
	private Textbox txtTelefonoResponsable;
	private Textbox txtApellidoResponsable;
	private Textbox txtNombreResponsable;
	private Button btnBuscar;
	private Intbox txtCedulaResponsable;
	private Groupbox gbPacientes;

	private String editResponsable = "/sigma/vistas/maestros/responsable/editResponsable.zul";
	private String editPaciente = "/sigma/vistas/maestros/paciente/editPaciente.zul";
	private String rutaReporte = "/sigma/vistas/reportes/reportesJR/viewReport.zul";
	private String rutaImagen = "/sigma/vistas/reportes/reportesJR/carnet/carnetsigmablank.png";
	private String rutaJrxml = "/sigma/vistas/reportes/reportesJR/carnet/carnet.jrxml";

	private ServicioResponsable servicioResponsable;
	private ServicioPaciente servicioPaciente;
	private ServicioHospital servicioHospital;
	private ServicioCarnet servicioCarnet;
	private Paciente seleccion;
	private Responsable responsable;
	private Carnet carnet;
	private Hospital hospital;
	private AnnotateDataBinder binder;
	private Map<String, Object> parametros;
	private Map<String, Object> parametrosJasper;

	private List<Paciente> pacientes = new ArrayList<Paciente>();

	public ServicioHospital getServicioHospital() {
		return servicioHospital;
	}

	public void setServicioHospital(ServicioHospital servicioHospital) {
		this.servicioHospital = servicioHospital;
	}

	public ServicioCarnet getServicioCarnet() {
		return servicioCarnet;
	}

	public void setServicioCarnet(ServicioCarnet servicioCarnet) {
		this.servicioCarnet = servicioCarnet;
	}

	public Map<String, Object> getparametros() {
		return parametros;
	}

	public void setparametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

	public Groupbox getGbPacientes() {
		return gbPacientes;
	}

	public void setGbPacientes(Groupbox gbPacientes) {
		this.gbPacientes = gbPacientes;
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public Window getWinFichaBasica() {
		return winFichaBasica;
	}

	public void setWinFichaBasica(Window winFichaBasica) {
		this.winFichaBasica = winFichaBasica;
	}

	public Button getBtnGenerarCarnet() {
		return btnGenerarCarnet;
	}

	public void setBtnGenerarCarnet(Button btnGenerarCarnet) {
		this.btnGenerarCarnet = btnGenerarCarnet;
	}

	public Listbox getListPaciente() {
		return listPaciente;
	}

	public void setListPaciente(Listbox listPaciente) {
		this.listPaciente = listPaciente;
	}

	public Button getBtnEditarPac() {
		return btnEditarPac;
	}

	public void setBtnEditarPac(Button btnEditarPac) {
		this.btnEditarPac = btnEditarPac;
	}

	public Button getBtnNuevoPac() {
		return btnNuevoPac;
	}

	public void setBtnNuevoPac(Button btnNuevoPac) {
		this.btnNuevoPac = btnNuevoPac;
	}

	public Textbox getTxtCiudadResponsable() {
		return txtCiudadResponsable;
	}

	public void setTxtCiudadResponsable(Textbox txtCiudadResponsable) {
		this.txtCiudadResponsable = txtCiudadResponsable;
	}

	public Textbox getTxtDirecionResponsable() {
		return txtDirecionResponsable;
	}

	public void setTxtDirecionResponsable(Textbox txtDirecionResponsable) {
		this.txtDirecionResponsable = txtDirecionResponsable;
	}

	public Textbox getTxtCorreoResponsable() {
		return txtCorreoResponsable;
	}

	public void setTxtCorreoResponsable(Textbox txtCorreoResponsable) {
		this.txtCorreoResponsable = txtCorreoResponsable;
	}

	public Textbox getTxtTelefonoResponsable() {
		return txtTelefonoResponsable;
	}

	public void setTxtTelefonoResponsable(Textbox txtTelefonoResponsable) {
		this.txtTelefonoResponsable = txtTelefonoResponsable;
	}

	public Textbox getTxtApellidoResponsable() {
		return txtApellidoResponsable;
	}

	public void setTxtApellidoResponsable(Textbox txtApellidoResponsable) {
		this.txtApellidoResponsable = txtApellidoResponsable;
	}

	public Textbox getTxtNombreResponsable() {
		return txtNombreResponsable;
	}

	public void setTxtNombreResponsable(Textbox txtNombreResponsable) {
		this.txtNombreResponsable = txtNombreResponsable;
	}

	public Button getBtnBuscar() {
		return btnBuscar;
	}

	public void setBtnBuscar(Button btnBuscar) {
		this.btnBuscar = btnBuscar;
	}

	public Intbox getTxtCedulaResponsable() {
		return txtCedulaResponsable;
	}

	public void setTxtCedulaResponsable(Intbox txtCedulaResponsable) {
		this.txtCedulaResponsable = txtCedulaResponsable;
	}

	public String getEditResponsable() {
		return editResponsable;
	}

	public void setEditResponsable(String editResponsable) {
		this.editResponsable = editResponsable;
	}

	public String getEditPaciente() {
		return editPaciente;
	}

	public void setEditPaciente(String editPaciente) {
		this.editPaciente = editPaciente;
	}

	public ServicioResponsable getServicioResponsable() {
		return servicioResponsable;
	}

	public void setServicioResponsable(ServicioResponsable servicioResponsable) {
		this.servicioResponsable = servicioResponsable;
	}

	public ServicioPaciente getServicioPaciente() {
		return servicioPaciente;
	}

	public void setServicioPaciente(ServicioPaciente servicioPaciente) {
		this.servicioPaciente = servicioPaciente;
	}

	public Paciente getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Paciente seleccion) {
		this.seleccion = seleccion;
	}

	public Responsable getResponsable() {
		return responsable;
	}

	public void setResponsable(Responsable responsable) {
		this.responsable = responsable;
	}

	public List<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

	/**
	 *
	 *
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winFichaBasica.setAttribute(comp.getId() + "ctrl", this);
		servicioPaciente = (ServicioPaciente) SpringUtil
				.getBean("beanServicioPaciente");
		servicioResponsable = (ServicioResponsable) SpringUtil
				.getBean("beanServicioResponsable");
		servicioHospital = (ServicioHospital) SpringUtil
				.getBean("beanServicioHospital");
		servicioCarnet = (ServicioCarnet) SpringUtil
				.getBean("beanServicioCarnet");
		txtCedulaResponsable.setFocus(true);
	}

	public void onClick$btnBuscar() {
		if (txtCedulaResponsable.getValue() != null) {
			responsable = servicioResponsable.buscarUno(
					txtCedulaResponsable.getValue(), 'A');
			if (responsable != null) {
				binder.loadAll();
				activar();
				recargarLista();
				txtCedulaResponsable.setReadonly(true);
			} else {
				MensajeEmergente.mostrar("REGRESP", new MensajeListener() {
					@Override
					public void alAceptar() {
						cargarWinResponsable();
					}

					@Override
					public void alDestruir() {
						txtCedulaResponsable.setFocus(true);
					}
				});
			}
		} else {
			MensajeEmergente.mostrar("NOEMPTY", "\nCedula",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtCedulaResponsable.setFocus(true);
						}
					});
		}
	}

	public void activar() {
		btnBuscar.setDisabled(true);
		gbPacientes.setVisible(true);
		btnGenerarCarnet.setVisible(true);
		btnGenerarCarnet.setDisabled(true);
	}
	
	public void desactivar() {
		btnBuscar.setDisabled(false);
		gbPacientes.setVisible(false);
		btnGenerarCarnet.setVisible(false);
		btnGenerarCarnet.setDisabled(false);
	}

	public void cargarWinResponsable() {
		parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinFichaBasica", this);
		Window win = (Window) execution.createComponents(editResponsable, null,
				parametros);
		win.doHighlighted();
	}

	public void cargarResponsable(Responsable responsable) {
		this.responsable = responsable;
		binder.loadAll();
		gbPacientes.setOpen(true);
	}

	public void deshabilitarCampos() {
		txtCedulaResponsable.setReadonly(true);
	}
	
	public void onClick$btnNuevoPac() {
		parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinFichaBasica", this);
		Window win = (Window) execution.createComponents(editPaciente, null,
				parametros);
		win.doHighlighted();
	}

	public void onClick$btnEditarPac() {
		parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinFichaBasica", this);
		parametros.put("objeto", seleccion);
		Window win = (Window) execution.createComponents(editPaciente, null,
				parametros);
		win.doHighlighted();
	}

	public void recargarLista() {
		pacientes = servicioPaciente.buscarPorResponsable(responsable, 'A');
		listPaciente.setModel(new BindingListModelList(pacientes, false));
	}

	public void onClick$btnGenerarCarnet() {
		carnet = new Carnet();
		carnet.setPaciente(seleccion);
		carnet.setExpedicion(HelperDate.now());
		carnet.setVencimiento(HelperDate.future(HelperDate.ANNO, 2));
		hospital = servicioHospital.buscarUnico();
		String rutaAbosultaImagen = Sessions.getCurrent().getWebApp()
				.getRealPath(rutaImagen);
		String rutaAbosultaJrxml = Sessions.getCurrent().getWebApp()
				.getRealPath(rutaJrxml);
		parametros = new HashMap<String, Object>();
		
		parametrosJasper = new HashMap<String, Object>();
		parametros.put("titulo", "Carnet");
		parametrosJasper.put("paciente", seleccion);
		parametrosJasper.put("hospital", hospital);
		parametrosJasper.put("carnet", carnet);
		parametrosJasper.put("rutaimagen", rutaAbosultaImagen);
		parametros.put("rutajrxml", rutaAbosultaJrxml);
		parametros.put("tipo", HelperJR.EMPTY);
		parametros.put("parametrosJasper", parametrosJasper);
		Window win = (Window) Executions.createComponents(rutaReporte, null,
				parametros);
		win.doHighlighted();
	}

	public void onSelect$listPaciente() {
		btnGenerarCarnet.setDisabled(false);
		btnEditarPac.setDisabled(false);
	}
	
	public void onClick$btnCancelar() {
		seleccion = null;
		responsable = null;
		binder.loadAll();
		btnGenerarCarnet.setVisible(false);
		gbPacientes.setVisible(false);
		txtCedulaResponsable.setReadonly(false);
		txtCedulaResponsable.setValue(null);
		btnEditarPac.setDisabled(true);
		desactivar();
		
	}

}

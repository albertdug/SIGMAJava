/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.Severity;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Especie;
import org.ucla.sigma.modelo.Paciente;
import org.ucla.sigma.modelo.Raza;
import org.ucla.sigma.modelo.Sexo;
import org.ucla.sigma.modelo.TipoTratamiento;
import org.ucla.sigma.servicio.ServicioEspecie;
import org.ucla.sigma.servicio.ServicioPaciente;
import org.ucla.sigma.servicio.ServicioRaza;
import org.ucla.sigma.servicio.ServicioSexo;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author leonardo
 *
 */
public class ctrlWinCatalogoPaciente extends GenericForwardComposer {

	// Variables -> Zul
	// ---------------------------------------------------------------------------
	
	private Window winCatalogoPaciente;
	private Textbox txtCi;
	private Textbox txtNombreResponsable;
	private Textbox txtApellidoResponsable;
	private Textbox txtNombrePaciente;
	private Listbox listSexo;
	private Listbox listEspecie;
	private Listbox listRaza;
	private Listbox listPaciente;
	private Button btnBuscar;
	private Button btnLimpiar;
	private Button btnSeleccionar;
	private Button btnCancelar;

	// Variables -> Zul Propias
	// ---------------------------------------------------------------------------
	
	
	// Variables y Servicios -> Modelo
	// ---------------------------------------------------------------------------
	
	private ServicioPaciente servicioPaciente;
	private ServicioSexo servicioSexo;
	private ServicioEspecie servicioEspecie;
	private ServicioRaza servicioRaza;
	
	// Variables -> Entorno
	// ---------------------------------------------------------------------------
	
	private ctrlWinServicioConsultaGeneral ctrlwinservicioconsultageneral;
	private List<Paciente> pacientes = new ArrayList<Paciente>();
	private List<Sexo> sexos = new ArrayList<Sexo>();
	private List<Especie> especies = new ArrayList<Especie>();
	private List<Raza> razas = new ArrayList<Raza>();
	private Paciente seleccionPaciente;
	private MensajeListener asignarFocusCedula = new MensajeListener() {
		public void alDestruir() {
			txtCi.setFocus(true);
		};
	};
	
	// Codigo -> Carga Inicial
	// ---------------------------------------------------------------------------

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winCatalogoPaciente.setAttribute(comp.getId() + "ctrl", this);
		servicioPaciente = (ServicioPaciente) SpringUtil
				.getBean("beanServicioPaciente");
		servicioSexo = (ServicioSexo) SpringUtil
				.getBean("beanServicioSexo");
		servicioEspecie = (ServicioEspecie) SpringUtil
				.getBean("beanServicioEspecie");
		servicioRaza = (ServicioRaza) SpringUtil
				.getBean("beanServicioRaza");
		seleccionPaciente = new Paciente();
		sexos = servicioSexo.buscarTodos();
		especies = servicioEspecie.buscarTodos();
		razas = servicioRaza.buscarTodos();
		listRaza.setDisabled(true);
		ctrlwinservicioconsultageneral = (ctrlWinServicioConsultaGeneral) arg
				.get("ctrlWinServicioConsultaGeneral");
	}

	// Codigo -> Botones y Secciones
	// ---------------------------------------------------------------------------
	
	public void onClick$btnBuscar(){
		boolean vacio = true;
		boolean confirmar = true;
		String auxHql = "";
		
		if (!txtCi.getValue().trim().equalsIgnoreCase("")) {
			try {
				Integer.parseInt(txtCi.getValue());
			} catch (Exception e) {
				confirmar = false;
			}			
			if (confirmar) {
				auxHql =  auxHql+" r.cedula = "+txtCi.getValue();
				vacio = false;
			} else {
				MensajeEmergente.mostrar("UNVALIDATA", "\nDebe ingresar un valor numerico.",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtCi.setFocus(true);
						}
					});
			}
		}
		if (!txtNombreResponsable.getValue().trim().equalsIgnoreCase("")) {
			if (!vacio){
				auxHql =  auxHql+" and";
			}
			auxHql =  auxHql+" upper(n.nombre) like upper('%%"+txtNombreResponsable.getValue()+"%%')";
			vacio = false;
		}
		if (!txtApellidoResponsable.getValue().trim().equalsIgnoreCase("")) {
			if (!vacio){
				auxHql =  auxHql+" and";
			}
			auxHql =  auxHql+" upper(n.apellido) like upper('%%"+txtApellidoResponsable.getValue()+"%%')";
			vacio = false;
		}
		if (!txtNombrePaciente.getValue().trim().equalsIgnoreCase("")) {
			if (!vacio){
				auxHql =  auxHql+" and";
			}
			auxHql =  auxHql+" upper(p.nombre) like upper('%%"+txtNombrePaciente.getValue()+"%%')";
			vacio = false;
		}
		if (listSexo.getSelectedIndex() >= 0) {	
			if (!vacio){
				auxHql =  auxHql+" and";
			}
			Sexo auxSexo = (Sexo)listSexo.getSelectedItem().getValue();
			auxHql =  auxHql+" s.id = "+auxSexo.getId();
			vacio = false;
		}
		if (listEspecie.getSelectedIndex() >= 0) {
			if (!vacio){
				auxHql =  auxHql+" and";
			}
			Especie auxEspecie = (Especie)listEspecie.getSelectedItem().getValue();
			auxHql =  auxHql+" e.id = "+auxEspecie.getId();
			vacio = false;	
		}
		if (listRaza.getSelectedIndex() >= 0) {
			if (!vacio){
				auxHql =  auxHql+" and";
			}
			Raza auxRaza = (Raza)listRaza.getSelectedItem().getValue();
			auxHql =  auxHql+" z.id = "+auxRaza.getId();
			vacio = false;	
		}
		if (vacio){
			if (confirmar) {
				MensajeEmergente.mostrar("NOFINDED", asignarFocusCedula);
			}
		} else {
			try {
				String hql =  "select distinct p from Paciente p join p.responsable r join r.persona n join p.sexo s join p.raza z join z.especie e where"+auxHql;
				pacientes = servicioPaciente.buscarPacientesHQL(hql);	
				if (!pacientes.isEmpty()){
					listPaciente.setModel(new BindingListModelList(pacientes, false));
				} else {
					MensajeEmergente.mostrar("NOTFOUND", asignarFocusCedula);
					pacientes.clear();
					listPaciente.setModel(new BindingListModelList(pacientes, false));
				}
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}
		}
	}
	
	public void onClick$btnLimpiar(){
		txtCi.setValue("");
		txtNombreResponsable.setValue("");
		txtApellidoResponsable.setValue("");
		txtNombrePaciente.setValue("");
		listSexo.setSelectedIndex(-1);
		listEspecie.setSelectedIndex(-1);
		listRaza.setSelectedIndex(-1);
		listRaza.setDisabled(true);
		pacientes.clear();
		listPaciente.setModel(new BindingListModelList(pacientes, false));
	}
	
	public void onClick$btnSeleccionar(){
		if (listPaciente.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			ctrlwinservicioconsultageneral.recargar(seleccionPaciente.getHistoriaMedica());
			this.winCatalogoPaciente.detach();
		}
	}
	
	public void onClick$btnCancelar(){
		this.winCatalogoPaciente.detach();
	}
	
	public void onSelect$listSexo(){
		
	}
	
	public void onSelect$listEspecie(){
		razas = servicioRaza.buscarEspeciesAsociados((Especie)listEspecie.getSelectedItem().getValue(), 'A');
		listRaza.setModel(new BindingListModelList(razas, false));
		listRaza.setDisabled(false);
	}
	
	public void onSelect$listRaza(){
		
	}
	
	// Codigo -> Entorno
	// ---------------------------------------------------------------------------
	
	
	// Codigo -> Getters y Setters
	// ---------------------------------------------------------------------------

	public Window getWinCatalogoPaciente() {
		return winCatalogoPaciente;
	}

	public void setWinCatalogoPaciente(Window winCatalogoPaciente) {
		this.winCatalogoPaciente = winCatalogoPaciente;
	}

	public Textbox getTxtCi() {
		return txtCi;
	}

	public void setTxtCi(Textbox txtCi) {
		this.txtCi = txtCi;
	}

	public Textbox getTxtNombreResponsable() {
		return txtNombreResponsable;
	}

	public void setTxtNombreResponsable(Textbox txtNombreResponsable) {
		this.txtNombreResponsable = txtNombreResponsable;
	}

	public Textbox getTxtApellidoResponsable() {
		return txtApellidoResponsable;
	}

	public void setTxtApellidoResponsable(Textbox txtApellidoResponsable) {
		this.txtApellidoResponsable = txtApellidoResponsable;
	}

	public Textbox getTxtNombrePaciente() {
		return txtNombrePaciente;
	}

	public void setTxtNombrePaciente(Textbox txtNombrePaciente) {
		this.txtNombrePaciente = txtNombrePaciente;
	}

	public Listbox getListSexo() {
		return listSexo;
	}

	public void setListSexo(Listbox listSexo) {
		this.listSexo = listSexo;
	}

	public Listbox getListEspecie() {
		return listEspecie;
	}

	public void setListEspecie(Listbox listEspecie) {
		this.listEspecie = listEspecie;
	}

	public Listbox getListRaza() {
		return listRaza;
	}

	public void setListRaza(Listbox listRaza) {
		this.listRaza = listRaza;
	}

	public Listbox getListPaciente() {
		return listPaciente;
	}

	public void setListPaciente(Listbox listPaciente) {
		this.listPaciente = listPaciente;
	}

	public List<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

	public List<Sexo> getSexos() {
		return sexos;
	}

	public void setSexos(List<Sexo> sexos) {
		this.sexos = sexos;
	}

	public List<Especie> getEspecies() {
		return especies;
	}

	public void setEspecies(List<Especie> especies) {
		this.especies = especies;
	}

	public List<Raza> getRazas() {
		return razas;
	}

	public void setRazas(List<Raza> razas) {
		this.razas = razas;
	}

	public Paciente getSeleccionPaciente() {
		return seleccionPaciente;
	}

	public void setSeleccionPaciente(Paciente seleccionPaciente) {
		this.seleccionPaciente = seleccionPaciente;
	}
	
}

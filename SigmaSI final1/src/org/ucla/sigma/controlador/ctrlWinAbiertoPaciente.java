/**
 * 
 */
package org.ucla.sigma.controlador;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JViewport;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.view.JasperViewer;

import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.componentsjr.HelperJR;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.icontrolador.IUsarCatalogoReportes;
import org.ucla.sigma.modelo.Especie;
import org.ucla.sigma.modelo.Historial;
import org.ucla.sigma.modelo.Patologia;
import org.ucla.sigma.modelo.Raza;
import org.ucla.sigma.modelo.Servicio;
import org.ucla.sigma.modelo.Sexo;
import org.ucla.sigma.modelo.TipoPatologia;
import org.ucla.sigma.modelo.TipoServicio;
import org.ucla.sigma.servicio.ServicioHistorial;
import org.ucla.sigma.servicio.ServicioNoModel;
import org.ucla.sigma.servicio.ServicioSexo;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

/**
 * @author rafael
 * 
 */
public class ctrlWinAbiertoPaciente extends GenericForwardComposer implements
		IUsarCatalogoReportes {

	private Window winAbiertoPaciente;
	private Button btnCancelar;
	private Button btnGenerar;
	private Listbox listSexo;
	private Listbox listColumna1;
	private Listbox listColumna2;
	private Listitem listItemEspecie;
	private Listitem listItemRaza;
	private Listitem listItemSexo;
	private Listitem listItemEspeciales;
	private Listitem listItemTipoServicio;
	private Listitem listItemTipoPatologia;
	private Listitem listItemPatologia;
	private Button btnRaza;
	private Button btnEspecie;
	private Button btnTipoServicio;
	private Button btnServicio;
	private Button btnTipoPatologia;
	private Button btnPatologia;
	private Datebox dbFinNacimiento;
	private Datebox dbInicioNacimiento;
	private Datebox dbFinAtencion;
	private Datebox dbInicioAtencion;

	private String rutaReporte = "/sigma/vistas/reportes/reportesJR/viewReport.zul";
	
	private String auxEspecie="No Seleccionado ";
	private String auxRaza="No Seleccionado ";
	private String auxSexo="No Seleccionado ";
	private String auxTipoServicio="No Seleccionado ";
	private String auxServicio="No Seleccionado ";
	private String auxTipoPatologia="No Seleccionado ";
	private String auxPatologia="No Seleccionado ";
	
	private String catalogoPatologia = "/sigma/vistas/reportes/catalogos/catalogoPatologia.zul";
	private String catalogoTipoPatologia = "/sigma/vistas/reportes/catalogos/catalogoTipoPatologia.zul";
	private String catalogoEspecie = "/sigma/vistas/reportes/catalogos/catalogoEspecie.zul";
	private String catalogoServicio = "/sigma/vistas/reportes/catalogos/catalogoServicio.zul";
	private String catalogoRaza = "/sigma/vistas/reportes/catalogos/catalogoRaza.zul";
	private String catalogoTipoServicio = "/sigma/vistas/reportes/catalogos/catalogoTipoServicio.zul";
	private ServicioSexo servicioSexo;
	private ServicioHistorial servicioHistorial;
	private List<Sexo> sexos = new ArrayList<Sexo>();

	private Set<TipoPatologia> tipoPatologias = new HashSet<TipoPatologia>();
	private Set<Patologia> patologias = new HashSet<Patologia>();
	private Set<Servicio> servicios = new HashSet<Servicio>();
	private Set<TipoServicio> tipoServicios = new HashSet<TipoServicio>();
	private Set<Especie> especies = new HashSet<Especie>();
	private Set<Raza> razas = new HashSet<Raza>();
	private List<Historial> historials = new ArrayList<Historial>();

	private Map<String, Object> parametros = new HashMap<String, Object>();

	// ---------------------------------------------------------------------------------------------------------------------------------------

	public Button getBtnCancelar() {
		return btnCancelar;
	}

	public Window getWinAbiertoPaciente() {
		return winAbiertoPaciente;
	}

	public void setWinAbiertoPaciente(Window winAbiertoPaciente) {
		this.winAbiertoPaciente = winAbiertoPaciente;
	}

	public void setBtnCancelar(Button btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public Button getBtnTipoServicio() {
		return btnTipoServicio;
	}

	public void setBtnTipoServicio(Button btnTipoServicio) {
		this.btnTipoServicio = btnTipoServicio;
	}

	public Button getBtnGenerar() {
		return btnGenerar;
	}

	public void setBtnGenerar(Button btnGenerar) {
		this.btnGenerar = btnGenerar;
	}

	public Listbox getListSexo() {
		return listSexo;
	}

	public void setListSexo(Listbox listSexo) {
		this.listSexo = listSexo;
	}

	public Button getBtnRaza() {
		return btnRaza;
	}

	public void setBtnRaza(Button btnRaza) {
		this.btnRaza = btnRaza;
	}

	public Button getBtnEspecie() {
		return btnEspecie;
	}

	public void setBtnEspecie(Button btnEspecie) {
		this.btnEspecie = btnEspecie;
	}

	public Button getBtnServicio() {
		return btnServicio;
	}

	public void setBtnServicio(Button btnServicio) {
		this.btnServicio = btnServicio;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winAbiertoPaciente.setAttribute(comp.getId() + "ctrl", this);
		servicioSexo = (ServicioSexo) SpringUtil.getBean("beanServicioSexo");
		servicioHistorial = (ServicioHistorial) SpringUtil
				.getBean("beanServicioHistorial");
		sexos = servicioSexo.buscarTodos('A');
	}

	public void onSelect$listColumna2() {

		listItemRaza.setDisabled(false);
		listItemSexo.setDisabled(false);
		listItemEspecie.setDisabled(false);
		btnRaza.setDisabled(true);

		razas = new HashSet<Raza>();

		if (listItemEspecie.isSelected()) {
			listItemRaza.setDisabled(listItemEspecie.isSelected());
			listItemSexo.setDisabled(listItemEspecie.isSelected());
		} else if (listItemRaza.isSelected()) {
			listItemEspecie.setDisabled(listItemRaza.isSelected());
			listItemSexo.setDisabled(listItemRaza.isSelected());
			btnRaza.setDisabled(false);

		} else if (listItemSexo.isSelected()) {
			listItemEspecie.setDisabled(listItemSexo.isSelected());
			listItemRaza.setDisabled(listItemSexo.isSelected());
		}

	}

	public void onClick$btnEspecie() {

		parametros.put("controladorEstadistico", this);
		Window win = (Window) Executions.createComponents(catalogoEspecie,
				null, parametros);
		win.doHighlighted();

	}

	public void onClick$btnServicio() {

		parametros.put("controladorEstadistico", this);
		Window win = (Window) Executions.createComponents(catalogoServicio,
				null, parametros);
		win.doHighlighted();

	}

	public void onClick$btnTipoServicio() {

		parametros.put("controladorEstadistico", this);
		Window win = (Window) Executions.createComponents(catalogoTipoServicio,
				null, parametros);
		win.doHighlighted();

	}

	public void onClick$btnRaza() {

		parametros.put("controladorEstadistico", this);
		Window win = (Window) Executions.createComponents(catalogoRaza, null,
				parametros);
		win.doHighlighted();

	}

	public void onClick$btnPatologia() {

		parametros.put("controladorEstadistico", this);
		Window win = (Window) Executions.createComponents(catalogoPatologia,
				null, parametros);
		win.doHighlighted();

	}

	public void onClick$btnTipoPatologia() {

		parametros.put("controladorEstadistico", this);
		Window win = (Window) Executions.createComponents(
				catalogoTipoPatologia, null, parametros);
		win.doHighlighted();

	}

	public String getCatalogoEspecie() {
		return catalogoEspecie;
	}

	public void setCatalogoEspecie(String catalogoEspecie) {
		this.catalogoEspecie = catalogoEspecie;
	}

	public String getCatalogoServicio() {
		return catalogoServicio;
	}

	public void setCatalogoServicio(String catalogoServicio) {
		this.catalogoServicio = catalogoServicio;
	}

	public String getCatalogoRaza() {
		return catalogoRaza;
	}

	public void setCatalogoRaza(String catalogoRaza) {
		this.catalogoRaza = catalogoRaza;
	}

	public String getCatalogoTipoServicio() {
		return catalogoTipoServicio;
	}

	public void setCatalogoTipoServicio(String catalogoTipoServicio) {
		this.catalogoTipoServicio = catalogoTipoServicio;
	}

	public ServicioSexo getServicioSexo() {
		return servicioSexo;
	}

	public void setServicioSexo(ServicioSexo servicioSexo) {
		this.servicioSexo = servicioSexo;
	}

	public List<Sexo> getSexos() {
		return sexos;
	}

	public void setSexos(List<Sexo> sexos) {
		this.sexos = sexos;
	}

	public Set<TipoServicio> getTipoServicios() {
		return tipoServicios;
	}

	public void setTipoServicios(Set<TipoServicio> tipoServicios) {
		this.tipoServicios = tipoServicios;
	}

	public Set<Especie> getEspecies() {
		return especies;
	}

	public void setEspecies(Set<Especie> especies) {
		this.especies = especies;
	}

	public Set<Raza> getRazas() {
		return razas;
	}

	public void setRazas(Set<Raza> razas) {
		this.razas = razas;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

	@Override
	public Set<Especie> InterfazgetEspecies() {
		return this.especies;

	}

	@Override
	public void InterfazsetEspecies(Set<Especie> especies) {
		this.especies = especies;

	}

	@Override
	public Set<TipoServicio> InterfazgetTipoServicios() {
		return this.tipoServicios;
	}

	@Override
	public void InterfazsetTipoServicios(Set<TipoServicio> tipoServicio) {
		this.tipoServicios = tipoServicio;
	}

	@Override
	public Set<Servicio> InterfazgetServicios() {
		return this.servicios;
	}

	@Override
	public void InterfazsetServicios(Set<Servicio> servicios) {
		this.servicios = servicios;
	}

	@Override
	public Set<Raza> InterfazgetRazas() {
		return this.razas;
	}

	@Override
	public void InterfazsetRazas(Set<Raza> razas) {
		this.razas = razas;

	}

	@Override
	public Set<TipoPatologia> interfazgetTipoPatologias() {
		return this.tipoPatologias;
	}

	@Override
	public void InterfazsetTipoPatologias(Set<TipoPatologia> tipoPatologias) {
		this.tipoPatologias = tipoPatologias;
	}

	@Override
	public Set<Patologia> interfazgetPatologias() {
		return this.patologias;
	}

	@Override
	public void InterfazsetPatologias(Set<Patologia> patologias) {
		this.patologias = patologias;
	}

	public Listbox getListColumna1() {
		return listColumna1;
	}

	public void setListColumna1(Listbox listColumna1) {
		this.listColumna1 = listColumna1;
	}

	public Listbox getListColumna2() {
		return listColumna2;
	}

	public void setListColumna2(Listbox listColumna2) {
		this.listColumna2 = listColumna2;
	}

	public Listitem getListItemEspecie() {
		return listItemEspecie;
	}

	public void setListItemEspecie(Listitem listItemEspecie) {
		this.listItemEspecie = listItemEspecie;
	}

	public Listitem getListItemRaza() {
		return listItemRaza;
	}

	public void setListItemRaza(Listitem listItemRaza) {
		this.listItemRaza = listItemRaza;
	}

	public Listitem getListItemSexo() {
		return listItemSexo;
	}

	public void setListItemSexo(Listitem listItemSexo) {
		this.listItemSexo = listItemSexo;
	}

	public Listitem getListItemEspeciales() {
		return listItemEspeciales;
	}

	public void setListItemEspeciales(Listitem listItemEspeciales) {
		this.listItemEspeciales = listItemEspeciales;
	}

	public Listitem getListItemTipoServicio() {
		return listItemTipoServicio;
	}

	public void setListItemTipoServicio(Listitem listItemTipoServicio) {
		this.listItemTipoServicio = listItemTipoServicio;
	}

	public Listitem getListItemTipoPatologia() {
		return listItemTipoPatologia;
	}

	public void setListItemTipoPatologia(Listitem listItemTipoPatologia) {
		this.listItemTipoPatologia = listItemTipoPatologia;
	}

	public Listitem getListItemPatologia() {
		return listItemPatologia;
	}

	public void setListItemPatologia(Listitem listItemPatologia) {
		this.listItemPatologia = listItemPatologia;
	}

	public Button getBtnTipoPatologia() {
		return btnTipoPatologia;
	}

	public void setBtnTipoPatologia(Button btnTipoPatologia) {
		this.btnTipoPatologia = btnTipoPatologia;
	}

	public Button getBtnPatologia() {
		return btnPatologia;
	}

	public void setBtnPatologia(Button btnPatologia) {
		this.btnPatologia = btnPatologia;
	}

	public Datebox getDbFinNacimiento() {
		return dbFinNacimiento;
	}

	public ServicioHistorial getServicioHistorial() {
		return servicioHistorial;
	}

	public void setServicioHistorial(ServicioHistorial servicioHistorial) {
		this.servicioHistorial = servicioHistorial;
	}

	public List<Historial> getHistorials() {
		return historials;
	}

	public void setHistorials(List<Historial> historials) {
		this.historials = historials;
	}

	public void setDbFinNacimiento(Datebox dbFinNacimiento) {
		this.dbFinNacimiento = dbFinNacimiento;
	}

	public Datebox getDbInicioNacimiento() {
		return dbInicioNacimiento;
	}

	public void setDbInicioNacimiento(Datebox dbInicioNacimiento) {
		this.dbInicioNacimiento = dbInicioNacimiento;
	}

	public Datebox getDbFinAtencion() {
		return dbFinAtencion;
	}

	public void setDbFinAtencion(Datebox dbFinAtencion) {
		this.dbFinAtencion = dbFinAtencion;
	}

	public Datebox getDbInicioAtencion() {
		return dbInicioAtencion;
	}

	public void setDbInicioAtencion(Datebox dbInicioAtencion) {
		this.dbInicioAtencion = dbInicioAtencion;
	}

	public String getCatalogoPatologia() {
		return catalogoPatologia;
	}

	public void setCatalogoPatologia(String catalogoPatologia) {
		this.catalogoPatologia = catalogoPatologia;
	}

	public String getCatalogoTipoPatologia() {
		return catalogoTipoPatologia;
	}

	public void setCatalogoTipoPatologia(String catalogoTipoPatologia) {
		this.catalogoTipoPatologia = catalogoTipoPatologia;
	}

	public Set<TipoPatologia> getTipoPatologias() {
		return tipoPatologias;
	}

	public void setTipoPatologias(Set<TipoPatologia> tipoPatologias) {
		this.tipoPatologias = tipoPatologias;
	}

	public Set<Patologia> getPatologias() {
		return patologias;
	}

	public void setPatologias(Set<Patologia> patologias) {
		this.patologias = patologias;
	}

	public Set<Servicio> getServicios() {
		return servicios;
	}

	public void setServicios(Set<Servicio> servicios) {
		this.servicios = servicios;
	}

	public void onClick$btnGenerar() {
		/*
		 * hql="select distinct h from Historial h join h.patologias pt join
		 * pt.tipoPatologia tpt join h.tipoServicio ts join h.paciente p join p.sexo sx join p.raza r join
		 * r.especie es join ts.servicios ser join h.patologias pat join pat.tipoPatologia tpat where
		 *  <------- es.nombre in 
		 *  and 
		 *  <--------- r.nombre in
		 * and <--------- sx.nombre in
		 * and
		 * tpt.nombre in
		 */

				
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		Map<String, Object> parametrosJasper = new HashMap<String, Object>();
		
		String condicion = "";

		String espcieHQL = "";
		if (!especies.isEmpty()) {
			condicion = " where ";
			String condicionEspecie = " es.nombre in ";
			auxEspecie = "(";
			Set sets = especies;
			for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
				Especie esp = (Especie) iterator.next();
				auxEspecie = auxEspecie + "'" + esp.getNombre() + "'";
				if (iterator.hasNext()) {
					auxEspecie = auxEspecie + ",";
				}
			}
			auxEspecie = auxEspecie + ")";
			espcieHQL = condicionEspecie + auxEspecie;
		}

		String razaHQL = "";
		if (!razas.isEmpty()) {
			condicion = " where ";
			String condicionRaza = "";
			if (espcieHQL.trim().equalsIgnoreCase("")) {
				condicionRaza = " r.nombre in ";
			} else {
				condicionRaza = " and r.nombre in ";
			}
			auxRaza = "(";
			Set sets = razas;
			for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
				Raza raza = (Raza) iterator.next();
				auxRaza = auxRaza + "'" + raza.getNombre() + "'";
				if (iterator.hasNext()) {
					auxRaza = auxRaza + ",";
				}
			}
			auxRaza = auxRaza + ")";
			razaHQL = condicionRaza + auxRaza;

		}

		String sexoHQL = "";
		if (!listSexo.getSelectedItems().isEmpty()) {
			condicion = " where ";
			String condicionSexo = "";
			if (espcieHQL.trim().equalsIgnoreCase("")
					&& razaHQL.trim().equalsIgnoreCase("")) {
				condicionSexo = " sx.nombre in ";
			} else {
				condicionSexo = " and sx.nombre in ";
			}

			auxSexo = "(";
			Set sets = listSexo.getSelectedItems();
			for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
				Listitem item = (Listitem) iterator.next();
				auxSexo = auxSexo + "'" + ((Sexo) item.getValue()).getNombre()
						+ "'";
				if (iterator.hasNext()) {
					auxSexo = auxSexo + ",";
				}
			}
			auxSexo = auxSexo + ")";
			sexoHQL = condicionSexo + auxSexo;

		}

		String fechaHQL = "";
		if (dbInicioNacimiento.getValue() != null
				&& dbFinNacimiento.getValue() != null) {
			condicion = " where ";
			String condicionFecha = "";
			
			if (espcieHQL.trim().equalsIgnoreCase("")
					&& razaHQL.trim().equalsIgnoreCase("")
					&& sexoHQL.trim().equalsIgnoreCase("")) {
				
				condicionFecha = " p.fechaNac between "
						+ "'"+HelperDate.format(dbInicioNacimiento.getValue(),HelperDate.FORMATO_POSTGRES_DATE)+"'" + " and "
						+ "'"+HelperDate.format(dbFinNacimiento.getValue(),HelperDate.FORMATO_POSTGRES_DATE)+"'" + " ";
			} else {
				
				condicionFecha = " and p.fechaNac between "
						+ "'"+HelperDate.format(dbInicioNacimiento.getValue(),HelperDate.FORMATO_POSTGRES_DATE)+"'" + " and "
						+ "'"+HelperDate.format(dbFinNacimiento.getValue(),HelperDate.FORMATO_POSTGRES_DATE)+"'" + " " ;
			}
			parametrosJasper.put("fechaNacimiento", HelperDate.format(dbInicioNacimiento.getValue(),HelperDate.FORMATO_POSTGRES_DATE)+ " hasta " +HelperDate.format(dbFinNacimiento.getValue(),HelperDate.FORMATO_POSTGRES_DATE));
			fechaHQL=condicionFecha;

		}
		
		String tipoServicioHQL = "";
		if (!tipoServicios.isEmpty()) {
			condicion = " where ";
			String condicionTipoServicio= "";
			if (espcieHQL.trim().equalsIgnoreCase("")
					&& razaHQL.trim().equalsIgnoreCase("")
					&& sexoHQL.trim().equalsIgnoreCase("")
					&& fechaHQL.trim().equalsIgnoreCase("")) {
				
				condicionTipoServicio= " ts.nombre in ";
			} else {
				condicionTipoServicio = " and ts.nombre in ";
			}

			auxTipoServicio = "(";
			Set sets = tipoServicios;
			for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
				TipoServicio tipoServicio= (TipoServicio) iterator.next();
				auxTipoServicio = auxTipoServicio + "'" + tipoServicio.getNombre() + "'";
				if (iterator.hasNext()) {
					auxTipoServicio = auxTipoServicio + ",";
				}
			}
			auxTipoServicio = auxTipoServicio + ")";
			tipoServicioHQL = condicionTipoServicio + auxTipoServicio;

		}
		
		String servicioHQL = "";
		if (!servicios.isEmpty()) {
			condicion = " where ";
			String condicionServicio= "";
			if (espcieHQL.trim().equalsIgnoreCase("")
					&& razaHQL.trim().equalsIgnoreCase("")
					&& sexoHQL.trim().equalsIgnoreCase("")
					&& fechaHQL.trim().equalsIgnoreCase("")
					&& tipoServicioHQL.trim().equalsIgnoreCase("")) {
				
				condicionServicio= " ser.nombre in ";
			} else {
				condicionServicio = " and ser.nombre in ";
			}

			auxServicio = "(";
			Set sets = servicios;
			for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
				Servicio servicio= (Servicio) iterator.next();
				auxServicio = auxServicio + "'" + servicio.getNombre() + "'";
				if (iterator.hasNext()) {
					auxServicio = auxServicio + ",";
				}
			}
			auxServicio = auxServicio + ")";
			servicioHQL = condicionServicio + auxServicio;

		}
		
		String tipoPatologiaHQL = "";
		if (!tipoPatologias.isEmpty()) {
			condicion = " where ";
			String condicionTipoPatologia= "";
			if (espcieHQL.trim().equalsIgnoreCase("")
					&& razaHQL.trim().equalsIgnoreCase("")
					&& sexoHQL.trim().equalsIgnoreCase("")
					&& fechaHQL.trim().equalsIgnoreCase("")
					&& tipoServicioHQL.trim().equalsIgnoreCase("")
					&& servicioHQL.trim().equalsIgnoreCase("")) {
				
				condicionTipoPatologia= " tpat.nombre in ";
			} else {
				condicionTipoPatologia= " and tpat.nombre in ";
			}

			auxTipoPatologia = "(";
			Set sets = tipoPatologias;
			for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
				TipoPatologia tipoPatologia= (TipoPatologia) iterator.next();
				auxTipoPatologia = auxTipoPatologia + "'" + tipoPatologia.getNombre() + "'";
				if (iterator.hasNext()) {
					auxTipoPatologia = auxTipoPatologia+ ",";
				}
			}
			auxTipoPatologia = auxTipoPatologia + ")";
			tipoPatologiaHQL = condicionTipoPatologia + auxTipoPatologia;

		}
		
		String patologiaHQL = "";
		if (!patologias.isEmpty()) {
			condicion = " where ";
			String condicionPatologia= "";
			if (espcieHQL.trim().equalsIgnoreCase("")
					&& razaHQL.trim().equalsIgnoreCase("")
					&& sexoHQL.trim().equalsIgnoreCase("")
					&& fechaHQL.trim().equalsIgnoreCase("")
					&& tipoServicioHQL.trim().equalsIgnoreCase("")
					&& servicioHQL.trim().equalsIgnoreCase("")
					&& tipoPatologiaHQL.trim().equalsIgnoreCase("")) {
				
				condicionPatologia= " pat.nombre in ";
			} else {
				condicionPatologia= " and pat.nombre in ";
			}

			auxPatologia = "(";
			Set sets = patologias;
			for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
				Patologia patologia= (Patologia) iterator.next();
				auxPatologia = auxPatologia + "'" + patologia.getNombre() + "'";
				if (iterator.hasNext()) {
					auxPatologia = auxPatologia+ ",";
				}
			}
			auxPatologia = auxPatologia + ")";
			patologiaHQL = condicionPatologia + auxPatologia;

		}
		
		String fechaAHQL = "";
		if (dbInicioAtencion.getValue() != null
				&& dbFinAtencion.getValue() != null) {
			condicion = " where ";
			String condicionFechaA = "";
			
			if (espcieHQL.trim().equalsIgnoreCase("")
					&& razaHQL.trim().equalsIgnoreCase("")
					&& sexoHQL.trim().equalsIgnoreCase("")
					&& fechaHQL.trim().equalsIgnoreCase("")
					&& tipoServicioHQL.trim().equalsIgnoreCase("")
					&& servicioHQL.trim().equalsIgnoreCase("")
					&& tipoPatologiaHQL.trim().equalsIgnoreCase("")
					&& patologiaHQL.trim().equalsIgnoreCase("")) {
				
				condicionFechaA = " h.fecha between "
						+ "'"+HelperDate.format(dbInicioAtencion.getValue(),HelperDate.FORMATO_POSTGRES_DATE)+"'" + " and "
						+ "'"+HelperDate.format(dbFinAtencion.getValue(),HelperDate.FORMATO_POSTGRES_DATE)+"'" + " ";
			} else {
				
				condicionFechaA = " and h.fecha between "
						+ "'"+HelperDate.format(dbInicioAtencion.getValue(),HelperDate.FORMATO_POSTGRES_DATE)+"'" + " and "
						+ "'"+HelperDate.format(dbFinAtencion.getValue(),HelperDate.FORMATO_POSTGRES_DATE)+"'" + " " ;
			}
			parametrosJasper.put("fechaAtencion", HelperDate.format(dbInicioAtencion.getValue(),HelperDate.FORMATO_POSTGRES_DATE)+" hasta "+HelperDate.format(dbFinAtencion.getValue(),HelperDate.FORMATO_POSTGRES_DATE));
			fechaAHQL=condicionFechaA;

		}

		condicion = condicion + espcieHQL + razaHQL + sexoHQL + fechaHQL + tipoServicioHQL + servicioHQL + tipoPatologiaHQL + patologiaHQL + fechaAHQL;

		historials = servicioHistorial.buscarHistorialHQL(condicion);

		if (historials.size() > 0) {
			String rutaAbsolutaJrxml=Sessions.getCurrent().getWebApp()
					.getRealPath("/sigma/vistas/reportes/reportesJR/abiertos/reporteAbiertoPaciente.jrxml");
					
			
					parametrosJasper.put("especie", auxEspecie);
					parametrosJasper.put("raza", auxRaza);
					parametrosJasper.put("sexo", auxSexo);
					parametrosJasper.put("tipoServicio", auxTipoServicio);
					parametrosJasper.put("servicio", auxServicio);
					parametrosJasper.put("tipoPatologia", auxTipoPatologia);
					parametrosJasper.put("patologia", auxPatologia);

					
					
					
					parametros.put("rutajrxml", rutaAbsolutaJrxml);
					parametros.put("tipo", HelperJR.DATASOURCE);
					parametros.put("dataSource", historials);
					parametros.put("titulo", "Reporte abierto de Pacientes");
					parametros.put("parametrosJasper", parametrosJasper);
					Window win = (Window) Executions.createComponents(rutaReporte, null,
							parametros);
					win.doHighlighted();
			
		}
		else {
			MensajeEmergente.mostrar("NOREPORT");
		}
	}

}

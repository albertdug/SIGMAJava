package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.componentsjr.HelperJR;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.icontrolador.IUsarCatalogoReportes;
import org.ucla.sigma.modelo.Especie;
import org.ucla.sigma.modelo.Patologia;
import org.ucla.sigma.modelo.Raza;
import org.ucla.sigma.modelo.Referencia;
import org.ucla.sigma.modelo.Servicio;
import org.ucla.sigma.modelo.TipoPatologia;
import org.ucla.sigma.modelo.TipoServicio;
import org.ucla.sigma.servicio.ServicioReferencia;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

/**
 * @author Albert
 * 
 */
public class ctrlWinAbiertoReferencia extends GenericForwardComposer implements
		IUsarCatalogoReportes {

	private Window winAbiertoReferencia;
	private Button btnCancelar;
	private Button btnGenerar;
	private Listbox listColumna1;
	private Button btnTipoServicio;
	private Datebox dbFinEmision;
	private Datebox dbInicioEmision;
	
	private String rutaReporte = "/sigma/vistas/reportes/reportesJR/viewReport.zul";

	private String catalogoTipoServicio = "/sigma/vistas/reportes/catalogos/catalogoTipoServicio.zul";
	private String catalogoServicio = "/sigma/vistas/reportes/catalogos/catalogoServicio.zul";
	private ServicioReferencia servicioReferencia;

	private Set<Servicio> servicios = new HashSet<Servicio>();
	private List<Referencia> referencias = new ArrayList<Referencia>();
	private Set<TipoServicio> tipoServicios = new HashSet<TipoServicio>();
	private Map<String, Object> parametros = new HashMap<String, Object>();
	private String auxTipoServicio ="No Seleccionado";

	// ---------------------------------------------------------------------------------------------------------------------------------------

	public Button getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(Button btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public Button getBtnGenerar() {
		return btnGenerar;
	}

	public void setBtnGenerar(Button btnGenerar) {
		this.btnGenerar = btnGenerar;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winAbiertoReferencia.setAttribute(comp.getId() + "ctrl", this);
		servicioReferencia = (ServicioReferencia) SpringUtil
				.getBean("beanServicioReferencia");

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

	public String getCatalogoServicio() {
		return catalogoServicio;
	}

	public void setCatalogoServicio(String catalogoServicio) {
		this.catalogoServicio = catalogoServicio;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

	@Override
	public Set<Servicio> InterfazgetServicios() {
		return this.servicios;
	}

	@Override
	public void InterfazsetServicios(Set<Servicio> servicios) {
		this.servicios = servicios;
	}

	public Listbox getListColumna1() {
		return listColumna1;
	}

	public void setListColumna1(Listbox listColumna1) {
		this.listColumna1 = listColumna1;
	}

	public Datebox getDbFinEmision() {
		return dbFinEmision;
	}

	public ServicioReferencia getServicioReferencia() {
		return servicioReferencia;
	}

	public void setServicioReferencia(ServicioReferencia servicioReferencia) {
		this.servicioReferencia = servicioReferencia;
	}

	public List<Referencia> getReferencias() {
		return referencias;
	}

	public void setReferencias(List<Referencia> referencias) {
		this.referencias = referencias;
	}

	public void setDbFinEmision(Datebox dbFinEmision) {
		this.dbFinEmision = dbFinEmision;
	}

	public Datebox getDbInicioEmision() {
		return dbInicioEmision;
	}

	public void setDbInicioEmision(Datebox dbInicioEmision) {
		this.dbInicioEmision = dbInicioEmision;
	}

	public Set<Servicio> getServicios() {
		return servicios;
	}

	public void setServicios(Set<Servicio> servicios) {
		this.servicios = servicios;
	}

	@Override
	public Set<Especie> InterfazgetEspecies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void InterfazsetEspecies(Set<Especie> especies) {
		// TODO Auto-generated method stub

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
	public Set<Raza> InterfazgetRazas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void InterfazsetRazas(Set<Raza> razas) {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<TipoPatologia> interfazgetTipoPatologias() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void InterfazsetTipoPatologias(Set<TipoPatologia> tipoPatologias) {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<Patologia> interfazgetPatologias() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void InterfazsetPatologias(Set<Patologia> patologias) {
		// TODO Auto-generated method stub
		
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

		String tipoServicioHQL = "";
		if (!tipoServicios.isEmpty()) {
			condicion = " where r.estatus='R' and ";
			String condicionTipoServicio= "";
			condicionTipoServicio= " ts.nombre in ";
			auxTipoServicio  = "(";
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

		String fechaHQL = "";
		if (dbInicioEmision.getValue() != null
				&& dbFinEmision.getValue() != null) {
			condicion = " where r.estatus='R' and ";
			String condicionFecha = "";
			
			if (tipoServicioHQL.trim().equalsIgnoreCase("")) {
				
				condicionFecha = " r.fechaExpedicion between "
						+ "'"+HelperDate.format(dbInicioEmision.getValue(),HelperDate.FORMATO_POSTGRES_DATE)+"'" + " and "
						+ "'"+HelperDate.format(dbFinEmision.getValue(),HelperDate.FORMATO_POSTGRES_DATE)+"'" + " ";
			} else {
				
				condicionFecha = " and r.fechaExpedicion between "
						+ "'"+HelperDate.format(dbInicioEmision.getValue(),HelperDate.FORMATO_POSTGRES_DATE)+"'" + " and "
						+ "'"+HelperDate.format(dbFinEmision.getValue(),HelperDate.FORMATO_POSTGRES_DATE)+"'" + " " ;
			}
			parametrosJasper.put("fechaCita", HelperDate.format(dbInicioEmision.getValue(),HelperDate.FORMATO_POSTGRES_DATE)+ " hasta " +HelperDate.format(dbFinEmision.getValue(),HelperDate.FORMATO_POSTGRES_DATE));
			fechaHQL=condicionFecha;

		}

		condicion = condicion + tipoServicioHQL+ fechaHQL;

		referencias = servicioReferencia.buscarReferenciaHQL(condicion);

		if (referencias.size() > 0) {
			String rutaAbsolutaJrxml=Sessions.getCurrent().getWebApp()
					.getRealPath("/sigma/vistas/reportes/reportesJR/abiertos/reporteAbiertoReferencia.jrxml");
					
			
					parametrosJasper.put("tipoServicio", auxTipoServicio);
		
					
					
					parametros.put("rutajrxml", rutaAbsolutaJrxml);
					parametros.put("tipo", HelperJR.DATASOURCE);
					parametros.put("dataSource", referencias);
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

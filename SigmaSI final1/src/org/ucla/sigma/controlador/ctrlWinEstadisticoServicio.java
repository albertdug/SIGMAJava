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

import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.componentsjr.HelperJR;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.icontrolador.IUsarCatalogoReportes;
import org.ucla.sigma.modelo.Especie;
import org.ucla.sigma.modelo.Patologia;
import org.ucla.sigma.modelo.Raza;
import org.ucla.sigma.modelo.Servicio;
import org.ucla.sigma.modelo.Sexo;
import org.ucla.sigma.modelo.TipoPatologia;
import org.ucla.sigma.modelo.TipoServicio;
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
public class ctrlWinEstadisticoServicio extends GenericForwardComposer
		implements IUsarCatalogoReportes {

	private Window winEstadisticoServicio;
	private Button btnCancelar;
	private Button btnGenerar;
	private Listbox listSexo;
	private Listbox listColumna1;
	private Listbox listColumna2;
	private Listitem listItemTodas;
	private Listitem listItemEspeciales;
	private Listitem listItemEspecie;
	private Listitem listItemRaza;
	private Listitem listItemSexo;
	private Button btnRaza;
	private Button btnEspecie;
	private Datebox dbFin;
	private Datebox dbInicio;
	private Button btnServicio;
	private String catalogoEspecie = "/sigma/vistas/reportes/catalogos/catalogoEspecie.zul";
	private String catalogoServicio = "/sigma/vistas/reportes/catalogos/catalogoServicio.zul";
	private String catalogoRaza = "/sigma/vistas/reportes/catalogos/catalogoRaza.zul";
	private String catalogoTipoServicio = "/sigma/vistas/reportes/catalogos/catalogoTipoServicio.zul";
	private ServicioSexo servicioSexo;
	private List<Sexo> sexos = new ArrayList<Sexo>();
	private String rutaReporte = "/sigma/vistas/reportes/reportesJR/viewReport.zul";
	private String rutaJrxmlTodos = "/sigma/vistas/reportes/reportesJR/demandaServicios/demandaTipoServicio.jrxml";
	private String rutaJrxmlEspecie = "/sigma/vistas/reportes/reportesJR/demandaServicios/demandaTipoServicioEspecie.jrxml";
	private String rutaJrxmlSexo = "/sigma/vistas/reportes/reportesJR/demandaServicios/demandaTipoServicioSexo.jrxml";
	private String rutaJrxmlRaza = "/sigma/vistas/reportes/reportesJR/demandaServicios/demandaTipoServicioRaza.jrxml";
	private String rutaJrxmlServEspecifico = "/sigma/vistas/reportes/reportesJR/demandaServiciosEspecificos/demandaServicio.jrxml";
	private String rutaJrxmlEspecificoEspecie = "/sigma/vistas/reportes/reportesJR/demandaServiciosEspecificos/demandaServicioEspecie.jrxml";
	private String rutaJrxmlEspecificoSexo = "/sigma/vistas/reportes/reportesJR/demandaServiciosEspecificos/demandaServicioSexo.jrxml";
	private String rutaJrxmlEspecificoRaza = "/sigma/vistas/reportes/reportesJR/demandaServiciosEspecificos/demandaServicioRaza.jrxml";

	private Map<String, Object> parametrosJasper;
	private Set<TipoServicio> tipoServicios = new HashSet<TipoServicio>();
	private Set<Servicio> servicios = new HashSet<Servicio>();
	private Set<Especie> especies = new HashSet<Especie>();
	private Set<Raza> razas = new HashSet<Raza>();

	private Map<String, Object> parametros;
	private Map<String, Object> parametrosControladores = new HashMap<String, Object>();;

	// ---------------------------------------------------------------------------------------------------------------------------------------

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

	public Map<String, Object> getParametrosControladores() {
		return parametrosControladores;
	}

	public void setParametrosControladores(
			Map<String, Object> parametrosControladores) {
		this.parametrosControladores = parametrosControladores;
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

	public Set<Servicio> getServicios() {
		return servicios;
	}

	public void setServicios(Set<Servicio> servicios) {
		this.servicios = servicios;
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

	public Listitem getListItemTodas() {
		return listItemTodas;
	}

	public void setListItemTodas(Listitem listItemTodas) {
		this.listItemTodas = listItemTodas;
	}

	public Listitem getListItemEspeciales() {
		return listItemEspeciales;
	}

	public void setListItemEspeciales(Listitem listItemEspeciales) {
		this.listItemEspeciales = listItemEspeciales;
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

	public Window getWinEstadisticoServicio() {
		return winEstadisticoServicio;
	}

	public void setWinEstadisticoServicio(Window winEstadisticoServicio) {
		this.winEstadisticoServicio = winEstadisticoServicio;
	}

	public Button getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(Button btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public String getRutaReporte() {
		return rutaReporte;
	}

	public void setRutaReporte(String rutaReporte) {
		this.rutaReporte = rutaReporte;
	}

	public String getRutaJrxmlTodos() {
		return rutaJrxmlTodos;
	}

	public void setRutaJrxmlTodos(String rutaJrxmlTodos) {
		this.rutaJrxmlTodos = rutaJrxmlTodos;
	}

	public String getRutaJrxmlEspecie() {
		return rutaJrxmlEspecie;
	}

	public void setRutaJrxmlEspecie(String rutaJrxmlEspecie) {
		this.rutaJrxmlEspecie = rutaJrxmlEspecie;
	}

	public String getRutaJrxmlSexo() {
		return rutaJrxmlSexo;
	}

	public void setRutaJrxmlSexo(String rutaJrxmlSexo) {
		this.rutaJrxmlSexo = rutaJrxmlSexo;
	}

	public Map<String, Object> getParametrosJasper() {
		return parametrosJasper;
	}

	public void setParametrosJasper(Map<String, Object> parametrosJasper) {
		this.parametrosJasper = parametrosJasper;
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

	public Datebox getDbFin() {
		return dbFin;
	}

	public void setDbFin(Datebox dbFin) {
		this.dbFin = dbFin;
	}

	public Datebox getDbInicio() {
		return dbInicio;
	}

	public void setDbInicio(Datebox dbInicio) {
		this.dbInicio = dbInicio;
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
		winEstadisticoServicio.setAttribute(comp.getId() + "ctrl", this);
		servicioSexo = (ServicioSexo) SpringUtil.getBean("beanServicioSexo");
		sexos = servicioSexo.buscarTodos('A');
		apagarBotones();
	}

	public void onSelect$listColumna1() {

		listItemTodas.setDisabled(false);
		listItemEspeciales.setDisabled(false);
		btnServicio.setDisabled(true);

		tipoServicios = new HashSet<TipoServicio>();
		servicios = new HashSet<Servicio>();

		if (listItemTodas.isSelected()) {
			listItemEspeciales.setDisabled(listItemTodas.isSelected());
		} else if (listItemEspeciales.isSelected()) {
			listItemTodas.setDisabled(listItemEspeciales.isSelected());
			btnServicio.setDisabled(false);
		}

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

		parametrosControladores.put("controladorEstadistico", this);
		Window win = (Window) Executions.createComponents(catalogoEspecie,
				null, parametrosControladores);
		win.doHighlighted();

	}

	public void onClick$btnServicio() {
		parametrosControladores = new HashMap<String, Object>();

		parametrosControladores.put("controladorEstadistico", this);
		Window win = (Window) Executions.createComponents(catalogoServicio,
				null, parametrosControladores);
		win.doHighlighted();

	}

	public void onClick$btnTipoServicio() {

		parametrosControladores.put("controladorEstadistico", this);
		Window win = (Window) Executions.createComponents(catalogoTipoServicio,
				null, parametrosControladores);
		win.doHighlighted();

	}

	public void onClick$btnRaza() {

		parametrosControladores.put("controladorEstadistico", this);
		Window win = (Window) Executions.createComponents(catalogoRaza, null,
				parametrosControladores);
		win.doHighlighted();

	}

	public void onClick$btnCancelar() {
		dbInicio.setText(null);
		dbFin.setText(null);
		dbFin.setDisabled(true);
		btnGenerar.setDisabled(true);
	}

	public void onChange$dbInicio() {
		dbFin.setValue(null);
		dbFin.setConstraint("between "
				+ HelperDate.format(dbInicio.getValue(), "yyyyMMdd") + " and "
				+ HelperDate.nowFormat("yyyyMMdd"));
		dbFin.setDisabled(false);
	}

	public void onChange$dbFin() {
		btnGenerar.setDisabled(false);
	}

	public void apagarBotones() {
		btnGenerar.setDisabled(true);
		dbFin.setDisabled(true);
	}

	public void onClick$btnGenerar() {

		String rutaAlbosultaJrxmlTodos = Sessions.getCurrent().getWebApp()
				.getRealPath(rutaJrxmlTodos);
		String rutaAlbosultaJrxmlSexo = Sessions.getCurrent().getWebApp()
				.getRealPath(rutaJrxmlSexo);
		String rutaAlbosultaJrxmlEspecie = Sessions.getCurrent().getWebApp()
				.getRealPath(rutaJrxmlEspecie);
		String rutaAlbosultaJrxmlRaza = Sessions.getCurrent().getWebApp()
				.getRealPath(rutaJrxmlRaza);
		String rutaAlbosultaJrxmlServEspecifico = Sessions.getCurrent()
				.getWebApp().getRealPath(rutaJrxmlServEspecifico);
		String rutaAlbosultaJrxmlEspecificoEspecie = Sessions.getCurrent()
				.getWebApp().getRealPath(rutaJrxmlEspecificoEspecie);
		String rutaAlbosultaJrxmlEspecificoSexo = Sessions.getCurrent()
				.getWebApp().getRealPath(rutaJrxmlEspecificoSexo);
		String rutaAlbosultaJrxmlEspecificoRaza = Sessions.getCurrent()
				.getWebApp().getRealPath(rutaJrxmlEspecificoRaza);
		parametros = new HashMap<String, Object>();
		parametrosJasper = new HashMap<String, Object>();

		if (listItemTodas.isSelected()
				&& listColumna2.getSelectedItems().isEmpty()) {

			parametrosJasper.put("desde", dbInicio.getValue());
			parametrosJasper.put("hasta", dbFin.getValue());

			parametrosJasper.put("titulo",
					"Distribución Frecuencial de Consultas Atendidas");

			List sernotin = new ArrayList();

			sernotin.add("DEF");
			sernotin.add("FME");
			sernotin.add("PRO");
			sernotin.add("PTO");
			sernotin.add("TRQ");
			sernotin.add("LAB");

			parametrosJasper.put("sernotin", sernotin);
			parametros.put("rutajrxml", rutaAlbosultaJrxmlTodos);
			parametros.put("parametrosJasper", parametrosJasper);
			parametros.put("tipo", HelperJR.CONNECTION);
			parametros.put("titulo", "Reporte Servicios Prestados");

			Window win = (Window) Executions.createComponents(rutaReporte,
					null, parametros);

			win.doHighlighted();

		} else if (listItemTodas.isSelected() && listItemEspecie.isSelected()) {

			parametrosJasper.put("desde", dbInicio.getValue());
			parametrosJasper.put("hasta", dbFin.getValue());

			parametrosJasper
					.put("titulo",
							"Distribución Frecuencial de Consultas Atendidas por Especie");

			List sernotin = new ArrayList();

			sernotin.add("DEF");
			sernotin.add("FME");
			sernotin.add("PRO");
			sernotin.add("PTO");
			sernotin.add("TRQ");
			sernotin.add("LAB");

			parametrosJasper.put("sernotin", sernotin);
			parametros.put("rutajrxml", rutaAlbosultaJrxmlEspecie);
			parametros.put("parametrosJasper", parametrosJasper);
			parametros.put("tipo", HelperJR.CONNECTION);
			parametros.put("titulo", "Reporte Servicios Prestados");

			Window win = (Window) Executions.createComponents(rutaReporte,
					null, parametros);

			win.doHighlighted();

		} else if (listItemTodas.isSelected() && listItemSexo.isSelected()) {

			parametrosJasper.put("desde", dbInicio.getValue());
			parametrosJasper.put("hasta", dbFin.getValue());

			parametrosJasper.put("titulo",
					"Distribución Frecuencial de Consultas Atendidas por Sexo");

			List sernotin = new ArrayList();

			sernotin.add("DEF");
			sernotin.add("FME");
			sernotin.add("PRO");
			sernotin.add("PTO");
			sernotin.add("TRQ");
			sernotin.add("LAB");

			parametrosJasper.put("sernotin", sernotin);
			parametros.put("rutajrxml", rutaAlbosultaJrxmlSexo);
			parametros.put("parametrosJasper", parametrosJasper);
			parametros.put("tipo", HelperJR.CONNECTION);
			parametros.put("titulo", "Reporte Servicios Prestados");

			Window win = (Window) Executions.createComponents(rutaReporte,
					null, parametros);

			win.doHighlighted();

		} else if (listItemTodas.isSelected() && listItemRaza.isSelected()) {

			Set sets = razas;
			int i = 1;
			for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
				Raza rz = (Raza) iterator.next();
				parametrosJasper.put("razaid" + i, rz.getId());
				i++;
			}
			parametrosJasper.put("desde", dbInicio.getValue());
			parametrosJasper.put("hasta", dbFin.getValue());

			parametrosJasper
					.put("titulo",
							"Distribución Frecuencial de Consultas Atendidas por Razas");

			List sernotin = new ArrayList();

			sernotin.add("DEF");
			sernotin.add("FME");
			sernotin.add("PRO");
			sernotin.add("PTO");
			sernotin.add("TRQ");
			sernotin.add("LAB");

			parametrosJasper.put("sernotin", sernotin);
			parametros.put("rutajrxml", rutaAlbosultaJrxmlRaza);
			parametros.put("parametrosJasper", parametrosJasper);
			parametros.put("tipo", HelperJR.CONNECTION);
			parametros.put("titulo", "Reporte Servicios Prestados");

			Window win = (Window) Executions.createComponents(rutaReporte,
					null, parametros);

			win.doHighlighted();

		} else if (listItemEspeciales.isSelected()
				&& listColumna2.getSelectedItems().isEmpty()) {

			List serin = new ArrayList();
			Set sets = servicios;
			for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
				Servicio sv = (Servicio) iterator.next();
				serin.add(sv.getId());
			}
			parametrosJasper.put("desde", dbInicio.getValue());
			parametrosJasper.put("hasta", dbFin.getValue());

			parametrosJasper.put("titulo",
					"Distribución Frecuencial de Servicios Prestados");

			parametrosJasper.put("serin", serin);
			parametros.put("rutajrxml", rutaAlbosultaJrxmlServEspecifico);
			parametros.put("parametrosJasper", parametrosJasper);
			parametros.put("tipo", HelperJR.CONNECTION);
			parametros.put("titulo", "Reporte Servicios Prestados");

			Window win = (Window) Executions.createComponents(rutaReporte,
					null, parametros);

			win.doHighlighted();

		} else if (listItemEspeciales.isSelected()
				&& listItemEspecie.isSelected()) {

			List serin = new ArrayList();
			Set sets = servicios;
			for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
				Servicio sv = (Servicio) iterator.next();
				serin.add(sv.getId());
			}
			parametrosJasper.put("desde", dbInicio.getValue());
			parametrosJasper.put("hasta", dbFin.getValue());

			parametrosJasper
					.put("titulo",
							"Distribución Frecuencial de Servicios Prestados por Especie");

			parametrosJasper.put("serin", serin);
			parametros.put("rutajrxml", rutaAlbosultaJrxmlEspecificoEspecie);
			parametros.put("parametrosJasper", parametrosJasper);
			parametros.put("tipo", HelperJR.CONNECTION);
			parametros.put("titulo", "Reporte Servicios Prestados");

			Window win = (Window) Executions.createComponents(rutaReporte,
					null, parametros);

			win.doHighlighted();

		} else if (listItemEspeciales.isSelected() && listItemSexo.isSelected()) {

			List serin = new ArrayList();
			Set sets = servicios;
			for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
				Servicio sv = (Servicio) iterator.next();
				serin.add(sv.getId());
			}
			parametrosJasper.put("desde", dbInicio.getValue());
			parametrosJasper.put("hasta", dbFin.getValue());

			parametrosJasper.put("titulo",
					"Distribución Frecuencial de Servicios Prestados por Sexo");

			parametrosJasper.put("serin", serin);
			parametros.put("rutajrxml", rutaAlbosultaJrxmlEspecificoSexo);
			parametros.put("parametrosJasper", parametrosJasper);
			parametros.put("tipo", HelperJR.CONNECTION);
			parametros.put("titulo", "Reporte Servicios Prestados");

			Window win = (Window) Executions.createComponents(rutaReporte,
					null, parametros);

			win.doHighlighted();

		} else if (listItemEspeciales.isSelected() && listItemRaza.isSelected()) {

			Set sets = razas;
			int i = 1;
			for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
				Raza rz = (Raza) iterator.next();
				parametrosJasper.put("razaid" + i, rz.getId());
				i++;
			}

			List serin = new ArrayList();
			Set sets1 = servicios;
			for (Iterator iterator = sets1.iterator(); iterator.hasNext();) {
				Servicio sv = (Servicio) iterator.next();
				serin.add(sv.getId());
			}

			parametrosJasper.put("desde", dbInicio.getValue());
			parametrosJasper.put("hasta", dbFin.getValue());

			parametrosJasper
					.put("titulo",
							"Distribución Frecuencial de Servicios Prestados por Razas");

			parametrosJasper.put("serin", serin);
			parametros.put("rutajrxml", rutaAlbosultaJrxmlEspecificoRaza);
			parametros.put("parametrosJasper", parametrosJasper);
			parametros.put("tipo", HelperJR.CONNECTION);
			parametros.put("titulo", "Reporte Servicios Prestados");

			Window win = (Window) Executions.createComponents(rutaReporte,
					null, parametros);

			win.doHighlighted();

		}
	}
}
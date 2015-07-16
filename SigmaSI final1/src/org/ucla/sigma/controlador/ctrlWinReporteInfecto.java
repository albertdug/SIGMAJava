/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.HashMap;
import java.util.Map;

import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.components.HelperFileStream;
import org.ucla.sigma.componentsjr.HelperJR;
import org.ucla.sigma.servicio.ServicioNoModel;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

/**
 * @author rafael
 * 
 */
public class ctrlWinReporteInfecto extends GenericForwardComposer {

	// ----------------------------------------------------------------------------------------------------	
	
	private Window winReporteInfecto;
	private Button btnExportar;
	private Datebox dbInicio;
	private Datebox dbFin;
	private Checkbox cbFecha;
	private String sqlcount;
	private String rutaReporte = "/sigma/vistas/reportes/reportesJR/viewReport.zul";
	private String rutaImagen1 = "/sigma/vistas/reportes/reportesJR/imagenesComunes/sanidad.png";
	private String rutaImagen2 = "/sigma/vistas/reportes/reportesJR/imagenesComunes/ucla.png";
	private String rutaImagen3 = "/sigma/vistas/reportes/reportesJR/imagenesComunes/veterinaria.png";
	private String rutaJrxml = "/sigma/vistas/reportes/reportesJR/especialIncidenciasIC/especialIncidenciasIC.jrxml";
	private Map<String, Object> parametros;
	private Map<String, Object> parametrosJasper;
	private Button btnCancelar;
	private ServicioNoModel servicioNoModel;
	// ----------------------------------------------------------------------------------------------------	
	
	public Window getWinReporteInfecto() {
		return winReporteInfecto;
	}

	public void setWinReporteInfecto(Window winReporteInfecto) {
		this.winReporteInfecto = winReporteInfecto;
	}

	public Button getBtnExportar() {
		return btnExportar;
	}

	public void setBtnExportar(Button btnExportar) {
		this.btnExportar = btnExportar;
	}

	public Datebox getDbInicio() {
		return dbInicio;
	}

	public void setDbInicio(Datebox dbInicio) {
		this.dbInicio = dbInicio;
	}

	public Datebox getDbFin() {
		return dbFin;
	}

	public void setDbFin(Datebox dbFin) {
		this.dbFin = dbFin;
	}

	public Checkbox getCbFecha() {
		return cbFecha;
	}

	public void setCbFecha(Checkbox cbFecha) {
		this.cbFecha = cbFecha;
	}

	public String getRutaReporte() {
		return rutaReporte;
	}

	public void setRutaReporte(String rutaReporte) {
		this.rutaReporte = rutaReporte;
	}

	public String getRutaImagen1() {
		return rutaImagen1;
	}

	public void setRutaImagen1(String rutaImagen1) {
		this.rutaImagen1 = rutaImagen1;
	}

	public String getRutaImagen2() {
		return rutaImagen2;
	}

	public void setRutaImagen2(String rutaImagen2) {
		this.rutaImagen2 = rutaImagen2;
	}

	public String getRutaImagen3() {
		return rutaImagen3;
	}

	public void setRutaImagen3(String rutaImagen3) {
		this.rutaImagen3 = rutaImagen3;
	}

	public String getRutaJrxml() {
		return rutaJrxml;
	}

	public void setRutaJrxml(String rutaJrxml) {
		this.rutaJrxml = rutaJrxml;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

	public Button getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(Button btnCancelar) {
		this.btnCancelar = btnCancelar;
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		servicioNoModel = (ServicioNoModel) SpringUtil.getBean("beanServicioNoModel");
		apagarBotones();
	}

	public void onClick$btnCancelar(){
		dbInicio.setText(null);
		dbFin.setText(null);
		dbFin.setDisabled(true);
		btnExportar.setDisabled(true);
	}
	
	public void onChange$dbInicio() {
		dbFin.setValue(null);
		//dbFin.setConstraint("after"
		//		+ HelperDate.format(dbInicio.getValue(), "yyyyMMdd"));
		dbFin.setConstraint("between "
				+ HelperDate.format(dbInicio.getValue(), "yyyyMMdd")
				+ " and " + HelperDate.nowFormat("yyyyMMdd"));
		dbFin.setDisabled(false);
	}
	
	public void onChange$dbFin() {
		btnExportar.setDisabled(false);
	}
	
	public void apagarBotones() {
		btnExportar.setDisabled(true);
		dbFin.setDisabled(true);
	}
	
	public void onClick$btnExportar() {

		String rutaAlbosultaImagen1 = Sessions.getCurrent().getWebApp()
				.getRealPath(rutaImagen1);
		String rutaAlbosultaImagen2 = Sessions.getCurrent().getWebApp()
				.getRealPath(rutaImagen2);
		String rutaAlbosultaImagen3 = Sessions.getCurrent().getWebApp()
				.getRealPath(rutaImagen3);
		String rutaAlbosultaJrxml = Sessions.getCurrent().getWebApp()
				.getRealPath(rutaJrxml);
		sqlcount = "select count(distinct paciente) from patologia, historial, historial_patologia, paciente, raza, especie where patologia.tipo_patologiaid = %d and  especie.id = %d and historial.id = historial_patologia.historialid and historial_patologia.patologiaid= patologia.id and raza.especieid = especie.id and paciente.razaid = raza.id and paciente.historia_medica = historial.pacientehistoria_medica and historial.fecha between '"+HelperDate.format(dbInicio.getValue(), "yyyy-MM-dd")+"' and '"+HelperDate.format(dbFin.getValue(), "yyyy-MM-dd")+"'";
		
		int tpid = 2;
		int caninoid = 1;
		int felinoid = 2;
		int canttcaninos = servicioNoModel.count(String.format(sqlcount,tpid,caninoid));
		int canttfelinos = servicioNoModel.count(String.format(sqlcount,tpid,felinoid));
		
		parametros = new HashMap<String, Object>();
		parametrosJasper = new HashMap<String, Object>();
		
		parametrosJasper.put("desde", dbInicio.getValue());
		parametrosJasper.put("hasta", dbFin.getValue());
		parametrosJasper.put("canttcaninos", canttcaninos);
		parametrosJasper.put("canttfelinos", canttfelinos);
		parametrosJasper.put("tpid", tpid);
		parametrosJasper.put("caninoid", caninoid);
		parametrosJasper.put("felinoid", felinoid);
		parametrosJasper.put("logohospital", rutaAlbosultaImagen3);
		parametrosJasper.put("logosanidad", rutaAlbosultaImagen1);

		parametros.put("parametrosJasper", parametrosJasper);
		parametros.put("rutajrxml", rutaAlbosultaJrxml);
		parametros.put("tipo", HelperJR.CONNECTION);
		parametros.put("titulo", "Reporte Incidencias Infecto Contagiosas");
		
		Window win = (Window) Executions.createComponents(rutaReporte, null,
				parametros);
		
		win.doHighlighted();
	}

}

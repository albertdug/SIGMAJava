/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.componentsjr.HelperJR;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Window;

/**
 * @author rafael
 * 
 */
public class ctrlWinReporteGestion extends GenericForwardComposer {

	// ----------------------------------------------------------------------------------------------------

	private Window winReporteGestion;
	private Button btnExportar;
	private Datebox dbInicio;
	private Datebox dbFin;
	private String rutaReporte = "/sigma/vistas/reportes/reportesJR/viewReport.zul";
	private String rutaImagen1 = "/sigma/vistas/reportes/reportesJR/imagenesComunes/sanidad.png";
	private String rutaImagen2 = "/sigma/vistas/reportes/reportesJR/imagenesComunes/ucla.png";
	private String rutaImagen3 = "/sigma/vistas/reportes/reportesJR/imagenesComunes/veterinaria.png";
	private String rutaJrxml = "/sigma/vistas/reportes/reportesJR/gestion/gestion.jrxml";
	private String subdef = "/sigma/vistas/reportes/reportesJR/gestion/gestion_subreportdefuncion.jasper";
	private String subdefdet = "/sigma/vistas/reportes/reportesJR/gestion/gestion_subreportdetalledef.jasper";
	private String subpat = "/sigma/vistas/reportes/reportesJR/gestion/gestion_subreportpatologia.jasper";
	private String subcons= "/sigma/vistas/reportes/reportesJR/gestion/gestion_subreportconsulta.jasper";
	private String subser= "/sigma/vistas/reportes/reportesJR/gestion/gestion_subreportservicio.jasper";
	private String subref= "/sigma/vistas/reportes/reportesJR/gestion/gestion_subreportreferencia.jasper";
	private String subcit= "/sigma/vistas/reportes/reportesJR/gestion/gestion_subreportcita.jasper";
	
	private Map<String, Object> parametros;
	private Map<String, Object> parametrosJasper;
	private Button btnCancelar;
	
	// ----------------------------------------------------------------------------------------------------

	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		apagarBotones();
	}

	public void onClick$btnCancelar() {
		dbInicio.setText(null);
		dbFin.setText(null);
		dbFin.setDisabled(true);
		btnExportar.setDisabled(true);
	}

	public void onChange$dbInicio() {
		dbFin.setValue(null);
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
		String abssubdef = Sessions.getCurrent().getWebApp()
				.getRealPath(subdef);
		String abssubpat = Sessions.getCurrent().getWebApp()
				.getRealPath(subpat);
		String abssubcons = Sessions.getCurrent().getWebApp()
				.getRealPath(subcons);
		String abssubser = Sessions.getCurrent().getWebApp()
				.getRealPath(subser);
		String abssubref = Sessions.getCurrent().getWebApp()
				.getRealPath(subref);
		String abssubcit = Sessions.getCurrent().getWebApp()
				.getRealPath(subcit);
		String abssubdefdet = Sessions.getCurrent().getWebApp()
				.getRealPath(subdefdet);

		parametros = new HashMap<String, Object>();
		parametrosJasper = new HashMap<String, Object>();

		parametrosJasper.put("desde", dbInicio.getValue());
		parametrosJasper.put("hasta", dbFin.getValue());
		parametrosJasper.put("logohospital", rutaAlbosultaImagen3);
		parametrosJasper.put("logosanidad", rutaAlbosultaImagen1);
		parametrosJasper.put("logoucla", rutaAlbosultaImagen2);
		parametrosJasper.put("rutasubdef", abssubdef);
		parametrosJasper.put("rutasubpat", abssubpat);
		parametrosJasper.put("rutasubcons", abssubcons);
		parametrosJasper.put("rutasubser", abssubser);
		parametrosJasper.put("rutasubref", abssubref);
		parametrosJasper.put("rutasubcit", abssubcit);
		parametrosJasper.put("rutasubdefdet", abssubdefdet);
		
		List sernotin = new ArrayList();		
		sernotin.add("DEF");
		sernotin.add("FME");
		//sernotin.add("PRO");
		//sernotin.add("PTO");
		sernotin.add("TRQ");
		sernotin.add("LAB");		
		parametrosJasper.put("sernotin", sernotin);
		
		List sernotincitas = new ArrayList();		
		sernotincitas.add("DEF");
		sernotincitas.add("FME");
		sernotincitas.add("TRQ");
		sernotincitas.add("LAB");
		sernotincitas.add("CGL");
		parametrosJasper.put("sernotincitas", sernotincitas);
		
		parametros.put("parametrosJasper", parametrosJasper);
		parametros.put("rutajrxml", rutaAlbosultaJrxml);
		parametros.put("tipo", HelperJR.CONNECTION);
		parametros.put("titulo", "Informe de Gestión");

		Window win = (Window) Executions.createComponents(rutaReporte, null,
				parametros);

		win.doHighlighted();
	}

}

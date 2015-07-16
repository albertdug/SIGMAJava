/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class ctrlWinReporteEstadisticoReferencia extends GenericForwardComposer {

	// ----------------------------------------------------------------------------------------------------

	private Window winReporteEstadisticoReferencia;
	private Button btnExportar;
	private Datebox dbInicio;
	private Datebox dbFin;
	private String rutaReporte = "/sigma/vistas/reportes/reportesJR/viewReport.zul";
	private String rutaJrxml = "/sigma/vistas/reportes/reportesJR/estadisticoReferencia/estadisticoReferencia.jrxml";
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
		String rutaAlbosultaJrxml = Sessions.getCurrent().getWebApp()
				.getRealPath(rutaJrxml);

		parametros = new HashMap<String, Object>();
		parametrosJasper = new HashMap<String, Object>();

		parametrosJasper.put("desde", dbInicio.getValue());
		parametrosJasper.put("hasta", dbFin.getValue());
		parametrosJasper.put("titulo", "Frecuencia de Referencias Asignadas por Servicios");
		
		List sernotin = new ArrayList();
		sernotin.add("DEF");
		sernotin.add("FME");
		sernotin.add("TRQ");
		sernotin.add("LAB");
		sernotin.add("CGL");
		parametrosJasper.put("sernotin", sernotin);
		
		parametros.put("parametrosJasper", parametrosJasper);
		parametros.put("rutajrxml", rutaAlbosultaJrxml);
		parametros.put("tipo", HelperJR.CONNECTION);
		parametros.put("titulo", "Frecuencia de Referencias Asignadas por Servicios");

		Window win = (Window) Executions.createComponents(rutaReporte, null,
				parametros);

		win.doHighlighted();
	}

}

/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.HashMap;
import java.util.Map;

import org.ucla.sigma.components.HelperFileStream;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

/**
 * @author rafael
 * 
 */
public class ctrlWinReporteEstadistico extends GenericForwardComposer {

	private Window winReporteEstadistico;
	private Button btnGenerar;
	private Listbox listSexo;
	private String rutaReporte = "/sigma/vistas/reportes/reportesJR/viewReport.zul";
	private boolean var = true;
	private byte[] file;

	/**
	 *
	 *
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		// TODO Auto-generated method stub

	}

	public void onClick$btnGenerar() {
		try {
			if (var) {
				file = HelperFileStream
						.readFile(Sessions
								.getCurrent()
								.getWebApp()
								.getRealPath(
										"/sigma/vistas/reportes/estadisticos/graficas.pdf"));
				var = false;

			} else {
				file = HelperFileStream
						.readFile(Sessions
								.getCurrent()
								.getWebApp()
								.getRealPath(
										"/sigma/vistas/reportes/estadisticos/servicioRaza.pdf"));
				var = true;
			}

			java.io.InputStream mediais = new java.io.ByteArrayInputStream(file);
			org.zkoss.util.media.AMedia amedia = new org.zkoss.util.media.AMedia(
					"prueba" + ".pdf", "pdf", "application/pdf", mediais);
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("titulo", "Informe Estadistico");
			parametros.put("pdf", amedia);
			Window win = (Window) Executions.createComponents(rutaReporte,
					null, parametros);
			win.doHighlighted();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

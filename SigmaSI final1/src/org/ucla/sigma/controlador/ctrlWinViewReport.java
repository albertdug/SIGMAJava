package org.ucla.sigma.controlador;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.ucla.sigma.componentsjr.HelperJR;
import org.ucla.sigma.modelo.Carnet;
import org.ucla.sigma.modelo.Hospital;
import org.ucla.sigma.modelo.Paciente;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Window;

/**
 * @author rafael
 * 
 */

public class ctrlWinViewReport extends GenericForwardComposer {

	private Window winViewReport;
	private Iframe report;
	private Hospital hospital;
	private Carnet carnet;
	private Paciente paciente;
	private Connection con;
	private String rutajrxml;
	private String titulo;
	private int tipo;

	public Window getwinViewReport() {
		return winViewReport;
	}

	public void setwinViewReport(Window winViewReport) {
		this.winViewReport = winViewReport;
	}

	public Iframe getreport() {
		return report;
	}

	public void setreport(Iframe report) {
		this.report = report;
	}

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public Carnet getCarnet() {
		return carnet;
	}

	public void setCarnet(Carnet carnet) {
		this.carnet = carnet;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	/**
	 *
	 *
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winViewReport.setAttribute(comp.getId() + "ctrl", this);
		Map<String, Object> parametros = new HashMap<String, Object>();

		parametros = (Map<String, Object>) arg.get("parametrosJasper");
		titulo = (String) arg.get("titulo");
		rutajrxml = (String) arg.get("rutajrxml");
		tipo = (Integer)  arg.get("tipo");

		winViewReport.setTitle(titulo);

		try {
			ByteArrayOutputStream bytesOutputStream = new ByteArrayOutputStream();
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
					bytesOutputStream);
			
			JasperReport reporte = JasperCompileManager
					.compileReport(rutajrxml);

			JasperPrint print = null;

			switch (tipo) {
			case HelperJR.CONNECTION:
				print = JasperFillManager.fillReport(reporte, parametros,
						((DataSource) SpringUtil.getBean("dataSource"))
								.getConnection());
				break;
			case HelperJR.DATASOURCE:
				print = JasperFillManager.fillReport(
						reporte,
						parametros,
						new JRBeanCollectionDataSource((List) arg
								.get("dataSource")));
				break;
			case HelperJR.EMPTY:
			default:
				print = JasperFillManager.fillReport(reporte, parametros,
						new JREmptyDataSource());
				break;
			}

			JRExporter exporter = new JRPdfExporter();

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					bufferedOutputStream);
			exporter.exportReport();

			InputStream mediais = new ByteArrayInputStream(
					bytesOutputStream.toByteArray());
			bytesOutputStream.close();

			AMedia amedia = new AMedia(titulo + ".pdf", "pdf",
					"application/pdf", mediais);

			report.setContent(amedia);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	public void onClose$winViewReport() {
		this.winViewReport.detach();
	}

}

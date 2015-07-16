package org.ucla.sigma.componentsjr;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartCustomizer;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.*;


/**
 * Se utiliza en la propiedad "Customizar Class" para las graficas, 
 * esta clase agraga mas margen superior a l grafico, asi poder ver las "Labels"
 * Ejemplo en xml:
 * ...<chart customizerClass="org.ucla.sigma.componentsjr.TopBarLabels">...</chart>...
 */
public class TopBarLabels extends JRAbstractChartCustomizer implements
		JRChartCustomizer {

	@Override
	public void customize(JFreeChart chart, JRChart jasperChart) {
		
		Plot plot = chart.getPlot();
		if (plot instanceof CategoryPlot) {
			CategoryPlot categoryPlot = (CategoryPlot)plot;
			ValueAxis valueAxis = categoryPlot.getRangeAxis();		
			valueAxis.setUpperMargin(0.40);
		}

	}

}

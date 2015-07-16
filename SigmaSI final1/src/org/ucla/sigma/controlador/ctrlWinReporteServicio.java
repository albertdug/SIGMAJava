/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.modelo.Sexo;
import org.ucla.sigma.servicio.ServicioEspecie;
import org.ucla.sigma.servicio.ServicioPaciente;
import org.ucla.sigma.servicio.ServicioSexo;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

/**
 * @author lis
 *
 */
public class ctrlWinReporteServicio extends GenericForwardComposer {

	private Window winReporteServicio;
	private Button btnCancelar;
	private Button btnGenerar;
	private Listbox listSexo;
	private Button btnRaza;
	private Checkbox cbRaza;
	private Button btnEspecie;
	private Checkbox cbEspecie;
	private Datebox dbFin;
	private Datebox dbInicio;
	private Button btnServicio;
	private Checkbox cbServicio;
	private Checkbox cbTipoServicio;
	private Button btnTipoServicio;
	
	// ----------------------------------------------------------------------------------------------------
	
	private ServicioSexo servicioSexo;
	private List<Sexo> sexos = new ArrayList<Sexo>();
	private String catServicio = "/sigma/vistas/reportes/catalogos/catalogoServicio.zul";
	private String catTipoServicio = "/sigma/vistas/reportes/catalogos/catalogoTipoServicio.zul";
	private String catEspecie = "/sigma/vistas/reportes/catalogos/catalogoEspecie.zul";
	private String catRaza = "/sigma/vistas/reportes/catalogos/catalogoRaza.zul";
	
	// ----------------------------------------------------------------------------------------------------
	
	
	public Window getWinReporteServicio() {
		return winReporteServicio;
	}

	public String getCatServicio() {
		return catServicio;
	}

	public void setCatServicio(String catServicio) {
		this.catServicio = catServicio;
	}

	public String getCatTipoServicio() {
		return catTipoServicio;
	}

	public void setCatTipoServicio(String catTipoServicio) {
		this.catTipoServicio = catTipoServicio;
	}

	public String getCatEspecie() {
		return catEspecie;
	}

	public void setCatEspecie(String catEspecie) {
		this.catEspecie = catEspecie;
	}

	public String getCatRaza() {
		return catRaza;
	}

	public void setCatRaza(String catRaza) {
		this.catRaza = catRaza;
	}

	public void setWinReporteServicio(Window winReporteServicio) {
		this.winReporteServicio = winReporteServicio;
	}

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

	public Checkbox getCbTipoServicio() {
		return cbTipoServicio;
	}

	public void setCbTipoServicio(Checkbox cbTipoServicio) {
		this.cbTipoServicio = cbTipoServicio;
	}

	public Button getBtnTipoServicio() {
		return btnTipoServicio;
	}

	public void setBtnTipoServicio(Button btnTipoServicio) {
		this.btnTipoServicio = btnTipoServicio;
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

	public Checkbox getCbRaza() {
		return cbRaza;
	}

	public void setCbRaza(Checkbox cbRaza) {
		this.cbRaza = cbRaza;
	}

	public Button getBtnEspecie() {
		return btnEspecie;
	}

	public void setBtnEspecie(Button btnEspecie) {
		this.btnEspecie = btnEspecie;
	}

	public Checkbox getCbEspecie() {
		return cbEspecie;
	}

	public void setCbEspecie(Checkbox cbEspecie) {
		this.cbEspecie = cbEspecie;
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

	public Checkbox getCbServicio() {
		return cbServicio;
	}

	public void setCbServicio(Checkbox cbServicio) {
		this.cbServicio = cbServicio;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winReporteServicio.setAttribute(comp.getId() + "ctrl", this);
		servicioSexo = (ServicioSexo) SpringUtil.getBean("beanServicioSexo");
		sexos = servicioSexo.buscarTodos('A');
		btnGenerar.setDisabled(true);
		apagarBotones();
	}

	public void onClick$btnEspecie(){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinReporteServicio", this);
		Window win = (Window) Executions.createComponents(catEspecie, null,
				parametros);
		win.doHighlighted();
	}
	
	public void onClick$btnRaza(){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinReporteServicio", this);
		Window win = (Window) Executions.createComponents(catRaza, null,
				parametros);
		win.doHighlighted();
	}
	
	public void onClick$btnTipoServicio(){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinReporteServicio", this);
		Window win = (Window) Executions.createComponents(catTipoServicio, null,
				parametros);
		win.doHighlighted();
	}
	
	public void onClick$btnServicio(){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinReporteServicio", this);
		Window win = (Window) Executions.createComponents(catServicio, null,
				parametros);
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
		//dbFin.setConstraint("after"
		//		+ HelperDate.format(dbInicio.getValue(), "yyyyMMdd"));
		dbFin.setConstraint("between "
				+ HelperDate.format(dbInicio.getValue(), "yyyyMMdd")
				+ " and " + HelperDate.nowFormat("yyyyMMdd"));
		dbFin.setDisabled(false);
	}

	public void onChange$dbFin() {
		btnGenerar.setDisabled(false);
	}

	public void apagarBotones() {
		btnGenerar.setDisabled(true);
		dbFin.setDisabled(true);
	}

}

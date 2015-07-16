/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

/**
 * @author rafael
 * 
 */
public class ctrlWinEstadisticoPatologia extends GenericForwardComposer
		implements IUsarCatalogoReportes {

	private Window winEstadisticoPatologia;
	private Button btnCancelar;
	private Button btnGenerar;
	private Listbox listSexo;
	private Listbox listColumna1;
	private Listbox listColumna2;
	private Listitem listItemEspecie;
	private Listitem listItemRaza;
	private Listitem listItemSexo;
	private Button btnRaza;
	private Button btnEspecie;
	private Button btnTipoServicio;
	private Datebox dbFin;
	private Datebox dbInicio;
	private Button btnServicio;
	private String catalogoEspecie = "/sigma/vistas/reportes/catalogos/catalogoEspecie.zul";
	private String catalogoPatologia = "/sigma/vistas/reportes/catalogos/catalogoPatologia.zul";
	private String catalogoRaza = "/sigma/vistas/reportes/catalogos/catalogoRaza.zul";
	private String catalogoTipoPatologia = "/sigma/vistas/reportes/catalogos/catalogoTipoPatologia.zul";
	private ServicioSexo servicioSexo;
	private List<Sexo> sexos = new ArrayList<Sexo>();

	private Set<TipoPatologia> tipoPatologias = new HashSet<TipoPatologia>();
	private Set<Patologia> patologias = new HashSet<Patologia>();
	private Set<Especie> especies = new HashSet<Especie>();
	private Set<Raza> razas = new HashSet<Raza>();

	private Map<String, Object> parametros = new HashMap<String, Object>();

	/**
	 *
	 *
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEstadisticoPatologia.setAttribute(comp.getId() + "ctrl", this);
		servicioSexo = (ServicioSexo) SpringUtil.getBean("beanServicioSexo");
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

	public void onClick$btnRaza() {

		parametros.put("controladorEstadistico", this);
		Window win = (Window) Executions.createComponents(catalogoRaza, null,
				parametros);
		win.doHighlighted();

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void InterfazsetTipoServicios(Set<TipoServicio> tipoServicios) {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<Servicio> InterfazgetServicios() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void InterfazsetServicios(Set<Servicio> servicios) {
		// TODO Auto-generated method stub

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

	public Window getWinEstadisticoPatologia() {
		return winEstadisticoPatologia;
	}

	public void setWinEstadisticoPatologia(Window winEstadisticoPatologia) {
		this.winEstadisticoPatologia = winEstadisticoPatologia;
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

	public Button getBtnTipoServicio() {
		return btnTipoServicio;
	}

	public void setBtnTipoServicio(Button btnTipoServicio) {
		this.btnTipoServicio = btnTipoServicio;
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

	public String getCatalogoEspecie() {
		return catalogoEspecie;
	}

	public void setCatalogoEspecie(String catalogoEspecie) {
		this.catalogoEspecie = catalogoEspecie;
	}

	public String getCatalogoPatologia() {
		return catalogoPatologia;
	}

	public void setCatalogoPatologia(String catalogoPatologia) {
		this.catalogoPatologia = catalogoPatologia;
	}

	public String getCatalogoRaza() {
		return catalogoRaza;
	}

	public void setCatalogoRaza(String catalogoRaza) {
		this.catalogoRaza = catalogoRaza;
	}

	public String getCatalogoTipoPatologia() {
		return catalogoTipoPatologia;
	}

	public void setCatalogoTipoPatologia(String catalogoTipoPatologia) {
		this.catalogoTipoPatologia = catalogoTipoPatologia;
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
	
	

}

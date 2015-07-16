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

import org.ucla.sigma.icontrolador.IUsarCatalogoReportes;
import org.ucla.sigma.modelo.Especie;
import org.ucla.sigma.modelo.Funcion;
import org.ucla.sigma.servicio.ServicioEspecie;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

/**
 * @author lis
 * 
 */
public class ctrlWinCatalogoEspecie extends GenericForwardComposer {

	private Window winCatalogoEspecie;
	private Button btnCancelar;
	private Button btnAceptar;
	private Listbox listEspecie;
	private List<Especie> especies = new ArrayList<Especie>();
	private ServicioEspecie servicioEspecie;
	
	private IUsarCatalogoReportes controlador;
	private Set<Especie> sets = new HashSet<Especie>();
	private Set<Especie> auxEspecies = new HashSet<Especie>();
	private List<Especie> lists = new ArrayList<Especie>();

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public Window getWinCatalogoEspecie() {
		return winCatalogoEspecie;
	}

	public void setWinCatalogoEspecie(Window winCatalogoEspecie) {
		this.winCatalogoEspecie = winCatalogoEspecie;
	}

	public Button getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(Button btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public Button getBtnAceptar() {
		return btnAceptar;
	}

	public void setBtnAceptar(Button btnAceptar) {
		this.btnAceptar = btnAceptar;
	}

	public Listbox getListEspecie() {
		return listEspecie;
	}

	public void setListEspecie(Listbox listEspecie) {
		this.listEspecie = listEspecie;
	}

	public List<Especie> getEspecies() {
		return especies;
	}

	public void setEspecies(List<Especie> especies) {
		this.especies = especies;
	}

	public ServicioEspecie getServicioEspecie() {
		return servicioEspecie;
	}

	public void setServicioEspecie(ServicioEspecie servicioEspecie) {
		this.servicioEspecie = servicioEspecie;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winCatalogoEspecie.setAttribute(comp.getId() + "ctrl", this);
		servicioEspecie = (ServicioEspecie) SpringUtil
				.getBean("beanServicioEspecie");
		especies = servicioEspecie.buscarTodos('A');
		
		if (arg.get("controladorEstadistico") != null) {
			controlador = (IUsarCatalogoReportes) arg
					.get("controladorEstadistico");
			sets = controlador.InterfazgetEspecies();
		}

	}
	
	public void onCreate$listEspecie(){
			
		listEspecie.renderAll();
			lists = listEspecie.getItems();
			
			for (Iterator iterator = lists.iterator(); iterator.hasNext();) {
				Listitem item = (Listitem) iterator.next();
				for (Iterator iterator1 = sets.iterator(); iterator1.hasNext();) {
					Especie especie= (Especie) iterator1.next();
					if (especie.equals((((Especie) item.getValue())))) {
						listEspecie.addItemToSelection(item);
					}
				}

			}
	}

	public void onClick$btnAceptar() {
		if (listEspecie.getSelectedCount() != 0) {
			
		Set aux = listEspecie.getSelectedItems();
		auxEspecies = new HashSet<Especie>();
		controlador.InterfazsetEspecies(auxEspecies);
		for (Iterator iterator = aux.iterator(); iterator.hasNext();) {
			Listitem item = (Listitem) iterator.next();
			controlador.InterfazgetEspecies().add(
					(Especie) item.getValue());
		}
		}else {
			controlador.InterfazsetEspecies(sets = new HashSet<Especie>());
		}
		this.winCatalogoEspecie.detach();
	}

	public void onClose$winCatalogoEspecie() {
		this.winCatalogoEspecie.detach();
	}

	public void onClick$btnCancelar() {
		this.winCatalogoEspecie.detach();
	}
}

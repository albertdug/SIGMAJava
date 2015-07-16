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

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.icontrolador.IUsarCatalogoReportes;
import org.ucla.sigma.modelo.Especie;
import org.ucla.sigma.modelo.Raza;
import org.ucla.sigma.modelo.Raza;
import org.ucla.sigma.modelo.TipoPatologia;
import org.ucla.sigma.servicio.ServicioRaza;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

/**
 * @author lis
 * 
 */
public class ctrlWinCatalogoRaza extends GenericForwardComposer {

	private Window winCatalogoRaza;
	private Button btnCancelar;
	private Button btnAceptar;
	private Listbox listRaza;
	private List<Raza> razas = new ArrayList<Raza>();
	private ServicioRaza servicioRaza;

	private IUsarCatalogoReportes controlador;
	private Set<Especie> setsEspecie = new HashSet<Especie>();
	private Set<Raza> sets = new HashSet<Raza>();
	private Set<Raza> auxRazas = new HashSet<Raza>();
	private List<Raza> lists = new ArrayList<Raza>();

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public Window getWinCatalogoRaza() {
		return winCatalogoRaza;
	}

	public void setWinCatalogoRaza(Window winCatalogoRaza) {
		this.winCatalogoRaza = winCatalogoRaza;
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

	public Listbox getListRaza() {
		return listRaza;
	}

	public void setListRaza(Listbox listRaza) {
		this.listRaza = listRaza;
	}

	public List<Raza> getRazas() {
		return razas;
	}

	public void setRazas(List<Raza> razas) {
		this.razas = razas;
	}

	public ServicioRaza getServicioRaza() {
		return servicioRaza;
	}

	public void setServicioRaza(ServicioRaza servicioRaza) {
		this.servicioRaza = servicioRaza;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winCatalogoRaza.setAttribute(comp.getId() + "ctrl", this);
		servicioRaza = (ServicioRaza) SpringUtil.getBean("beanServicioRaza");
		razas = servicioRaza.buscarTodos('A');
		
		if (arg.get("controladorEstadistico") != null) {
			controlador = (IUsarCatalogoReportes) arg
					.get("controladorEstadistico");
			sets = controlador.InterfazgetRazas();
		}

		if (!controlador.InterfazgetEspecies().isEmpty()) {
			setsEspecie = controlador.InterfazgetEspecies();
			razas = servicioRaza.buscarCoincidenciasEspeciesVarias(
					setsEspecie, 'A');
		} else {
			razas = servicioRaza.buscarTodos('A');
		}
	}

	public void onCreate$listRaza() {

		listRaza.renderAll();
		lists = listRaza.getItems();

		for (Iterator iterator = lists.iterator(); iterator.hasNext();) {
			Listitem item = (Listitem) iterator.next();
			for (Iterator iterator1 = sets.iterator(); iterator1.hasNext();) {
				Raza raza = (Raza) iterator1.next();
				if (raza.equals((((Raza) item.getValue())))) {
					listRaza.addItemToSelection(item);
				}
			}
		}
	}

	public void onClick$btnAceptar() {
		if (listRaza.getSelectedCount() == 4) {
		if (listRaza.getSelectedCount() > 0) {

			Set aux = listRaza.getSelectedItems();
			auxRazas = new HashSet<Raza>();
			controlador.InterfazsetRazas(auxRazas);
			for (Iterator iterator = aux.iterator(); iterator.hasNext();) {
				Listitem item = (Listitem) iterator.next();
				controlador.InterfazgetRazas().add((Raza) item.getValue());
			}
		} else {
			controlador.InterfazsetRazas(sets = new HashSet<Raza>());
		}
		this.winCatalogoRaza.detach();
		}else {
			MensajeEmergente.mostrar("SELECT4");
		}
	}

	public void onClose$winCatalogoRaza() {
		this.winCatalogoRaza.detach();
	}

	public void onClick$btnCancelar() {
		this.winCatalogoRaza.detach();
	}
}

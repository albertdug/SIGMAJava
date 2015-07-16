/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.ucla.sigma.icontrolador.IUsarCatalogoReportes;
import org.ucla.sigma.modelo.Patologia;
import org.ucla.sigma.modelo.Patologia;
import org.ucla.sigma.modelo.TipoPatologia;
import org.ucla.sigma.servicio.ServicioPatologia;
import org.ucla.sigma.servicio.ServicioTipoServicio;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

/**
 * @author rafael
 * 
 */
public class ctrlWinCatalogoPatologia extends GenericForwardComposer {

	private Window winCatalogoPatologia;
	private Button btnCancelar;
	private Button btnAceptar;
	private Listbox listPatologia;
	private List<Patologia> patologias = new ArrayList<Patologia>();
	private ServicioPatologia servicioPatologia;

	private IUsarCatalogoReportes controlador;
	private Set<TipoPatologia> setsTipoPatologia = new HashSet<TipoPatologia>();
	private Set<Patologia> sets = new HashSet<Patologia>();
	private Set<Patologia> auxPatologias = new HashSet<Patologia>();
	private List<Patologia> lists = new ArrayList<Patologia>();

	// ------------------------------------------------------------------------------------------

	public Window getWinCatalogoPatologia() {
		return winCatalogoPatologia;
	}

	public void setWinCatalogoPatologia(Window winCatalogoPatologia) {
		this.winCatalogoPatologia = winCatalogoPatologia;
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

	public List<Patologia> getPatologias() {
		return patologias;
	}

	public void setPatologias(List<Patologia> patologias) {
		this.patologias = patologias;
	}

	public ServicioPatologia getServicioPatologia() {
		return servicioPatologia;
	}

	public void setServicioPatologia(ServicioPatologia servicioPatologia) {
		this.servicioPatologia = servicioPatologia;
	}

	public Listbox getListPatologia() {
		return listPatologia;
	}

	public void setListPatologia(Listbox listPatologia) {
		this.listPatologia = listPatologia;
	}

	public IUsarCatalogoReportes getControlador() {
		return controlador;
	}

	public void setControlador(IUsarCatalogoReportes controlador) {
		this.controlador = controlador;
	}

	public Set<Patologia> getSets() {
		return sets;
	}

	public void setSets(Set<Patologia> sets) {
		this.sets = sets;
	}

	public Set<Patologia> getAuxPatologias() {
		return auxPatologias;
	}

	public void setAuxPatologias(Set<Patologia> auxPatologias) {
		this.auxPatologias = auxPatologias;
	}

	public List<Patologia> getLists() {
		return lists;
	}

	public void setLists(List<Patologia> lists) {
		this.lists = lists;
	}

	/**
	 *
	 *
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winCatalogoPatologia.setAttribute(comp.getId() + "ctrl", this);
		servicioPatologia = (ServicioPatologia) SpringUtil
				.getBean("beanServicioPatologia");

		if (arg.get("controladorEstadistico") != null) {
			controlador = (IUsarCatalogoReportes) arg
					.get("controladorEstadistico");
			sets = controlador.interfazgetPatologias();
		}
		if (!controlador.interfazgetTipoPatologias().isEmpty()) {
			setsTipoPatologia = controlador.interfazgetTipoPatologias();
			patologias = servicioPatologia.buscarCoincidenciasTipoVarias(
					setsTipoPatologia, 'A');
		} else {
			patologias = servicioPatologia.buscarTodos('A');
		}

	}

	public void onCreate$listPatologia() {
		listPatologia.renderAll();
		lists = listPatologia.getItems();
		for (Iterator iterator = lists.iterator(); iterator.hasNext();) {
			Listitem item = (Listitem) iterator.next();
			for (Iterator iterator1 = sets.iterator(); iterator1.hasNext();) {
				Patologia patologia = (Patologia) iterator1.next();
				if (patologia.equals((((Patologia) item.getValue())))) {
					listPatologia.addItemToSelection(item);
				}
			}
		}
	}

	public void onClick$btnAceptar() {
		if (listPatologia.getSelectedCount() != 0) {

			Set aux = listPatologia.getSelectedItems();
			auxPatologias = new HashSet<Patologia>();
			for (Iterator iterator = aux.iterator(); iterator.hasNext();) {
				Listitem item = (Listitem) iterator.next();
				auxPatologias.add((Patologia) item.getValue());
			}
			controlador.InterfazsetPatologias(auxPatologias);
		} else {
			controlador.InterfazsetPatologias(sets = new HashSet<Patologia>());
		}
		System.out.println(auxPatologias);
		this.winCatalogoPatologia.detach();
	}

	public void onClose$winCatalogoPatologia() {
		this.winCatalogoPatologia.detach();
	}

	public void onClick$btnCancelar() {
		this.winCatalogoPatologia.detach();
	}

}

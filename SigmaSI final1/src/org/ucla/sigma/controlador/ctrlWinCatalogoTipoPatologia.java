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
import org.ucla.sigma.modelo.TipoPatologia;
import org.ucla.sigma.modelo.TipoPatologia;
import org.ucla.sigma.servicio.ServicioTipoPatologia;
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
public class ctrlWinCatalogoTipoPatologia extends GenericForwardComposer {

	private Window winCatalogoTipoPatologia;
	private Button btnCancelar;
	private Button btnAceptar;
	private Listbox listTipoPatologia;
	private List<TipoPatologia> tipoPatologias = new ArrayList<TipoPatologia>();
	private ServicioTipoPatologia servicioTipoPatologia;

	private IUsarCatalogoReportes controlador;
	private Set<TipoPatologia> sets = new HashSet<TipoPatologia>();
	private Set<TipoPatologia> auxTipoPatologias = new HashSet<TipoPatologia>();
	private List<TipoPatologia> lists = new ArrayList<TipoPatologia>();

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public Window getWinCatalogoTipoPatologia() {
		return winCatalogoTipoPatologia;
	}

	public void setWinCatalogoTipoPatologia(Window winCatalogoTipoPatologia) {
		this.winCatalogoTipoPatologia = winCatalogoTipoPatologia;
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

	public Listbox getListTipoPatologia() {
		return listTipoPatologia;
	}

	public void setListTipoPatologia(Listbox listTipoPatologia) {
		this.listTipoPatologia = listTipoPatologia;
	}

	public List<TipoPatologia> getTipoPatologias() {
		return tipoPatologias;
	}

	public void setTipoPatologias(List<TipoPatologia> tipoPatologias) {
		this.tipoPatologias = tipoPatologias;
	}

	public ServicioTipoPatologia getServicioTipoPatologia() {
		return servicioTipoPatologia;
	}

	public void setServicioTipoPatologia(
			ServicioTipoPatologia servicioTipoPatologia) {
		this.servicioTipoPatologia = servicioTipoPatologia;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winCatalogoTipoPatologia.setAttribute(comp.getId() + "ctrl", this);
		servicioTipoPatologia = (ServicioTipoPatologia) SpringUtil
				.getBean("beanServicioTipoPatologia");
		tipoPatologias = servicioTipoPatologia.buscarTodos('A');

		if (arg.get("controladorEstadistico") != null) {
			controlador = (IUsarCatalogoReportes) arg
					.get("controladorEstadistico");
			sets = controlador.interfazgetTipoPatologias();
		}
	}

	public void onCreate$listTipoPatologia() {

		listTipoPatologia.renderAll();
		lists = listTipoPatologia.getItems();

		for (Iterator iterator = lists.iterator(); iterator.hasNext();) {
			Listitem item = (Listitem) iterator.next();
			for (Iterator iterator1 = sets.iterator(); iterator1.hasNext();) {
				TipoPatologia tipoPatologia = (TipoPatologia) iterator1.next();
				if (tipoPatologia.equals((((TipoPatologia) item.getValue())))) {
					listTipoPatologia.addItemToSelection(item);
				}
			}

		}
	}

	public void onClick$btnAceptar() {
		if (listTipoPatologia.getSelectedCount() != 0) {

			Set aux = listTipoPatologia.getSelectedItems();
			auxTipoPatologias = new HashSet<TipoPatologia>();
			controlador.InterfazsetTipoPatologias(auxTipoPatologias);
			for (Iterator iterator = aux.iterator(); iterator.hasNext();) {
				Listitem item = (Listitem) iterator.next();
				controlador.interfazgetTipoPatologias().add(
						(TipoPatologia) item.getValue());
			}
		} else {
			controlador
					.InterfazsetTipoPatologias(sets = new HashSet<TipoPatologia>());
		}
		this.winCatalogoTipoPatologia.detach();
	}

	public void onClose$winCatalogoTipoPatologia() {
		this.winCatalogoTipoPatologia.detach();
	}

	public void onClick$btnCancelar() {
		this.winCatalogoTipoPatologia.detach();
	}
}

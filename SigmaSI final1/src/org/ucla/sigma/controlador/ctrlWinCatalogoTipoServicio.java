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
import org.ucla.sigma.modelo.TipoServicio;
import org.ucla.sigma.modelo.TipoServicio;
import org.ucla.sigma.servicio.ServicioTipoServicio;
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
public class ctrlWinCatalogoTipoServicio extends GenericForwardComposer {

	private Window winCatalogoTipoServicio;
	private Button btnCancelar;
	private Button btnAceptar;
	private Listbox listTipoServicio;
	private List<TipoServicio> tipoServicios = new ArrayList<TipoServicio>();
	private ServicioTipoServicio servicioTipoServicio;

	private IUsarCatalogoReportes controlador;
	private Set<TipoServicio> sets = new HashSet<TipoServicio>();
	private Set<TipoServicio> auxTipoServicios = new HashSet<TipoServicio>();
	private List<TipoServicio> lists = new ArrayList<TipoServicio>();

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public Window getWinCatalogoTipoServicio() {
		return winCatalogoTipoServicio;
	}

	public void setWinCatalogoTipoServicio(Window winCatalogoTipoServicio) {
		this.winCatalogoTipoServicio = winCatalogoTipoServicio;
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

	public Listbox getListTipoServicio() {
		return listTipoServicio;
	}

	public void setListTipoServicio(Listbox listTipoServicio) {
		this.listTipoServicio = listTipoServicio;
	}

	public List<TipoServicio> getTipoServicios() {
		return tipoServicios;
	}

	public void setTipoServicios(List<TipoServicio> tipoServicios) {
		this.tipoServicios = tipoServicios;
	}

	public ServicioTipoServicio getServicioTipoServicio() {
		return servicioTipoServicio;
	}

	public void setServicioTipoServicio(
			ServicioTipoServicio servicioTipoServicio) {
		this.servicioTipoServicio = servicioTipoServicio;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winCatalogoTipoServicio.setAttribute(comp.getId() + "ctrl", this);
		servicioTipoServicio = (ServicioTipoServicio) SpringUtil
				.getBean("beanServicioTipoServicio");
		tipoServicios = servicioTipoServicio.buscarTodos('A');

		if (arg.get("controladorEstadistico") != null) {
			controlador = (IUsarCatalogoReportes) arg
					.get("controladorEstadistico");
			sets = controlador.InterfazgetTipoServicios();
		}

	}

	public void onCreate$listTipoServicio() {

		listTipoServicio.renderAll();
		lists = listTipoServicio.getItems();
		for (Iterator iterator = lists.iterator(); iterator.hasNext();) {
			Listitem item = (Listitem) iterator.next();
			for (Iterator iterator1 = sets.iterator(); iterator1.hasNext();) {
				TipoServicio tipoServicio = (TipoServicio) iterator1.next();
				if (tipoServicio.equals((((TipoServicio) item.getValue())))) {
					listTipoServicio.addItemToSelection(item);
				}
			}

		}
	}

	public void onClick$btnAceptar() {
		if (listTipoServicio.getSelectedCount() != 0) {

			Set aux = listTipoServicio.getSelectedItems();
			auxTipoServicios = new HashSet<TipoServicio>();
			controlador.InterfazsetTipoServicios(auxTipoServicios);
			for (Iterator iterator = aux.iterator(); iterator.hasNext();) {
				Listitem item = (Listitem) iterator.next();
				controlador.InterfazgetTipoServicios().add(
						(TipoServicio) item.getValue());
			}
		} else {
			controlador
					.InterfazsetTipoServicios(sets = new HashSet<TipoServicio>());
		}
		this.winCatalogoTipoServicio.detach();
	}

	public void onClose$winCatalogoTipoServicio() {
		this.winCatalogoTipoServicio.detach();
	}

	public void onClick$btnCancelar() {
		this.winCatalogoTipoServicio.detach();
	}
}

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
import org.ucla.sigma.modelo.Servicio;
import org.ucla.sigma.modelo.Servicio;
import org.ucla.sigma.modelo.TipoPatologia;
import org.ucla.sigma.modelo.TipoServicio;
import org.ucla.sigma.servicio.ServicioServicio;
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
public class ctrlWinCatalogoServicio extends GenericForwardComposer {

	private Window winCatalogoServicio;
	private Button btnCancelar;
	private Button btnAceptar;
	private Listbox listServicio;
	private List<Servicio> servicios = new ArrayList<Servicio>();
	private ServicioServicio servicioServicio;

	private IUsarCatalogoReportes controlador;
	private Set<TipoServicio> setsTipoServicio = new HashSet<TipoServicio>();
	private Set<Servicio> sets = new HashSet<Servicio>();
	private Set<Servicio> auxServicios = new HashSet<Servicio>();
	private List<Servicio> lists = new ArrayList<Servicio>();
	private TipoServicio tipoServicio;

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public Window getWinCatalogoServicio() {
		return winCatalogoServicio;
	}

	public void setWinCatalogoServicio(Window winCatalogoServicio) {
		this.winCatalogoServicio = winCatalogoServicio;
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

	public Listbox getListServicio() {
		return listServicio;
	}

	public void setListServicio(Listbox listServicio) {
		this.listServicio = listServicio;
	}

	public List<Servicio> getServicios() {
		return servicios;
	}

	public void setServicios(List<Servicio> servicios) {
		this.servicios = servicios;
	}

	public ServicioServicio getServicioServicio() {
		return servicioServicio;
	}

	public void setServicioServicio(ServicioServicio servicioServicio) {
		this.servicioServicio = servicioServicio;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winCatalogoServicio.setAttribute(comp.getId() + "ctrl", this);
		servicioServicio = (ServicioServicio) SpringUtil
				.getBean("beanServicioServicio");
		servicios = servicioServicio.buscarTodos('A');

		if (arg.get("controladorEstadistico") != null) {
			controlador = (IUsarCatalogoReportes) arg
					.get("controladorEstadistico");
			sets = controlador.InterfazgetServicios();
		}

		if (!controlador.InterfazgetTipoServicios().isEmpty()) {
			setsTipoServicio = controlador.InterfazgetTipoServicios();
		}
		tipoServicio = new TipoServicio("CGL");
		servicios = servicioServicio
				.buscarCoincidenciasTipoServiciosVarias(tipoServicio,
						'A');
	}

	public void onCreate$listServicio() {

		listServicio.renderAll();
		lists = listServicio.getItems();

		for (Iterator iterator = lists.iterator(); iterator.hasNext();) {
			Listitem item = (Listitem) iterator.next();
			for (Iterator iterator1 = sets.iterator(); iterator1.hasNext();) {
				Servicio servicio = (Servicio) iterator1.next();
				if (servicio.equals((((Servicio) item.getValue())))) {
					listServicio.addItemToSelection(item);
				}
			}

		}
	}

	public void onClick$btnAceptar() {
		if (listServicio.getSelectedCount() <= 4) {
			if (listServicio.getSelectedCount() > 0) {

				Set aux = listServicio.getSelectedItems();
				auxServicios = new HashSet<Servicio>();
				controlador.InterfazsetServicios(auxServicios);
				for (Iterator iterator = aux.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					controlador.InterfazgetServicios().add(
							(Servicio) item.getValue());
				}
			} else {
				controlador
						.InterfazsetServicios(sets = new HashSet<Servicio>());
			}
			this.winCatalogoServicio.detach();
		} else {
			MensajeEmergente.mostrar("SELECT4");
		}
	}

	public void onClose$winCatalogoServicio() {
		this.winCatalogoServicio.detach();
	}

	public void onClick$btnCancelar() {
		this.winCatalogoServicio.detach();
	}

}

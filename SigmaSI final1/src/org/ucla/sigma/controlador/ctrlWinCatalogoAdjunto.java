/**
 * 
 */
package org.ucla.sigma.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Adjunto;
import org.ucla.sigma.servicio.ServicioAdjunto;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author rafael
 * 
 */
public class ctrlWinCatalogoAdjunto extends GenericForwardComposer {

	private Window winCatalogoAdjunto;
	private Listbox listAdjunto;
	private Button btnCancelar;
	private Button btnSeleccionar;
	private Button btnBuscar;
	private Button btnVerTodos;
	private boolean buscando = false;
	private boolean verTodos = false;
	private Textbox txtNombreAdjunto;
	private ServicioAdjunto servicioAdjunto;
	private List<Adjunto> adjuntos = new ArrayList<Adjunto>();
	private ctrlWinEditPublicacion ctrlwinEditPublicacion;
	private Set<Adjunto> sets;
	private List<Adjunto> lists;
	private List<Adjunto> lists2;

	public ServicioAdjunto getServicioAdjunto() {
		return servicioAdjunto;
	}

	public void setServicioAdjunto(ServicioAdjunto servicioAdjunto) {
		this.servicioAdjunto = servicioAdjunto;
	}

	public List<Adjunto> getAdjuntos() {
		return adjuntos;
	}

	public void setAdjuntos(List<Adjunto> adjuntos) {
		this.adjuntos = adjuntos;
	}

	public ctrlWinEditPublicacion getCtrlwinEditPublicacion() {
		return ctrlwinEditPublicacion;
	}

	public void setCtrlwinEditPublicacion(
			ctrlWinEditPublicacion ctrlwinEditPublicacion) {
		this.ctrlwinEditPublicacion = ctrlwinEditPublicacion;
	}

	public MensajeListener getAsignarFocusBuscar() {
		return asignarFocusBuscar;
	}

	public void setAsignarFocusBuscar(MensajeListener asignarFocusBuscar) {
		this.asignarFocusBuscar = asignarFocusBuscar;
	}

	public Window getWinCatalogoAdjunto() {
		return winCatalogoAdjunto;
	}

	public void setWinCatalogoAdjunto(Window winCatalogoAdjunto) {
		this.winCatalogoAdjunto = winCatalogoAdjunto;
	}

	public Listbox getListAdjunto() {
		return listAdjunto;
	}

	public void setListAdjunto(Listbox listAdjunto) {
		this.listAdjunto = listAdjunto;
	}

	public Button getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(Button btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public Button getBtnSeleccionar() {
		return btnSeleccionar;
	}

	public void setBtnSeleccionar(Button btnSeleccionar) {
		this.btnSeleccionar = btnSeleccionar;
	}

	public Button getBtnBuscar() {
		return btnBuscar;
	}

	public void setBtnBuscar(Button btnBuscar) {
		this.btnBuscar = btnBuscar;
	}

	public Button getBtnVerTodos() {
		return btnVerTodos;
	}

	public void setBtnVerTodos(Button btnVerTodos) {
		this.btnVerTodos = btnVerTodos;
	}

	public boolean isBuscando() {
		return buscando;
	}

	public void setBuscando(boolean buscando) {
		this.buscando = buscando;
	}

	public boolean isVerTodos() {
		return verTodos;
	}

	public void setVerTodos(boolean verTodos) {
		this.verTodos = verTodos;
	}

	public Textbox getTxtNombreAdjunto() {
		return txtNombreAdjunto;
	}

	public void setTxtNombreAdjunto(Textbox txtNombreAdjunto) {
		this.txtNombreAdjunto = txtNombreAdjunto;
	}

	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreAdjunto.setFocus(true);
		};
	};

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winCatalogoAdjunto.setAttribute(comp.getId() + "ctrl", this);
		servicioAdjunto = (ServicioAdjunto) SpringUtil
				.getBean("beanServicioAdjunto");
		adjuntos = servicioAdjunto.buscarTodos('A');
		btnSeleccionar.setDisabled(true);
		ctrlwinEditPublicacion = (ctrlWinEditPublicacion) arg
				.get("ctrlWinEditPublicacion");

	}

	public void onClick$btnSeleccionar() {
		ctrlwinEditPublicacion.limpiarLista();
		ctrlwinEditPublicacion.getPublicacion().setAdjuntos(sets = new HashSet<Adjunto>());
		if (!this.listAdjunto.getSelectedItems().isEmpty()) {
			if (this.listAdjunto.getSelectedItems().size()<=3) {
				sets = this.listAdjunto.getSelectedItems();
				for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					ctrlwinEditPublicacion.getAdjuntos().add((Adjunto) item.getValue());
				}
				ctrlwinEditPublicacion.recargar();
				this.winCatalogoAdjunto.detach();
			}else {
				MensajeEmergente.mostrar("ARCHADJ");
			}
		}else {
			ctrlwinEditPublicacion.recargar();
			this.winCatalogoAdjunto.detach();
		}
	}

	public void onAfterRender$listAdjunto() {
		listAdjunto.renderAll();
		lists = ctrlwinEditPublicacion.getListAdjuntos().getItems();
		lists2 = this.listAdjunto.getItems();
		for (Iterator iterator = lists.iterator(); iterator.hasNext();) {
			Listitem item = (Listitem) iterator.next();
			for (Iterator iterator1 = lists2.iterator(); iterator1.hasNext();) {
				Listitem item1 = (Listitem) iterator1.next();
				if (((Adjunto) item.getValue()).equals(((Adjunto) item1
						.getValue()))) {
					this.listAdjunto.addItemToSelection(item1);
				}
			}
		}
	}

	public void onSelect$listAdjunto() {
		btnSeleccionar.setDisabled(false);
	}

	public void onClick$btnCancelar() {
		this.winCatalogoAdjunto.detach();
	}

	public void apagarBotones() {
		txtNombreAdjunto.setFocus(true);
		listAdjunto.clearSelection();
		listAdjunto.selectItem(null);
		btnSeleccionar.setDisabled(true);
	}

	public void recargar() {
		if (verTodos)
			adjuntos = servicioAdjunto.buscarTodos('A');
		else if (buscando)
			adjuntos = servicioAdjunto.buscarCoincidencias(
					txtNombreAdjunto.getValue(), 'A');
		else
			adjuntos.clear();

		listAdjunto.setModel(new BindingListModelList(adjuntos, false));
	}

	public void onClick$btnVerTodos() {
		adjuntos = servicioAdjunto.buscarTodos('A');
		listAdjunto.setModel(new BindingListModelList(adjuntos, false));
		buscando = false;
		verTodos = true;
		txtNombreAdjunto.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreAdjunto.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			adjuntos = servicioAdjunto.buscarCoincidencias(
					txtNombreAdjunto.getValue(), 'A');
			if (adjuntos.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listAdjunto.setModel(new BindingListModelList(adjuntos, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

}

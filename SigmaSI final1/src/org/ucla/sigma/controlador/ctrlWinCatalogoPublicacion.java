package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Publicacion;
import org.ucla.sigma.servicio.ServicioPublicacion;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinCatalogoPublicacion extends GenericForwardComposer {
	
	private Window winCatalogoPublicacion;
	private Listbox listPublicacion;
	private Button btnCancelar;
	private Button btnSeleccionar;
	private Button btnBuscar;
	private Button btnVerTodos;
	private boolean buscando = false;
	private boolean verTodos = false;
	private Textbox txtTituloPublicacion;
	private ServicioPublicacion servicioPublicacion;
	private List<Publicacion> publicaciones = new ArrayList<Publicacion>();
	private ctrlWinEditBoletinInformativo ctrlwinEditBoletinInformativo;
	private Set<Publicacion> sets;
	private List<Publicacion> lists;
	private List<Publicacion> lists2;

	public ServicioPublicacion getServicioPublicacion() {
		return servicioPublicacion;
	}

	public void setServicioPublicacion(ServicioPublicacion servicioPublicacion) {
		this.servicioPublicacion = servicioPublicacion;
	}

	public List<Publicacion> getPublicaciones() {
		return publicaciones;
	}

	public void setPublicaciones(List<Publicacion> publicaciones) {
		this.publicaciones = publicaciones;
	}

	public ctrlWinEditBoletinInformativo getCtrlwinEditBoletinInformativo() {
		return ctrlwinEditBoletinInformativo;
	}

	public void setCtrlwinEditBoletinInformativo(
			ctrlWinEditBoletinInformativo ctrlwinEditBoletinInformativo) {
		this.ctrlwinEditBoletinInformativo = ctrlwinEditBoletinInformativo;
	}

	public MensajeListener getAsignarFocusBuscar() {
		return asignarFocusBuscar;
	}

	public void setAsignarFocusBuscar(MensajeListener asignarFocusBuscar) {
		this.asignarFocusBuscar = asignarFocusBuscar;
	}

	public Window getWinCatalogoPublicacion() {
		return winCatalogoPublicacion;
	}

	public void setWinCatalogoPublicacion(Window winCatalogoPublicacion) {
		this.winCatalogoPublicacion = winCatalogoPublicacion;
	}

	public Listbox getListPublicacion() {
		return listPublicacion;
	}

	public void setListPublicacion(Listbox listPublicacion) {
		this.listPublicacion = listPublicacion;
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

	public Textbox getTxtTituloPublicacion() {
		return txtTituloPublicacion;
	}

	public void setTxtTituloPublicacion(Textbox txtTituloPublicacion) {
		this.txtTituloPublicacion = txtTituloPublicacion;
	}

	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtTituloPublicacion.setFocus(true);
		};
	};

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winCatalogoPublicacion.setAttribute(comp.getId() + "ctrl", this);
		servicioPublicacion = (ServicioPublicacion) SpringUtil
				.getBean("beanServicioPublicacion");
		publicaciones = servicioPublicacion.buscarTodos('A');
		btnSeleccionar.setDisabled(true);
		ctrlwinEditBoletinInformativo = (ctrlWinEditBoletinInformativo) arg
				.get("ctrlWinEditBoletinInformativo");

	}

	public void onClick$btnSeleccionar() {
		ctrlwinEditBoletinInformativo.limpiarLista();
		ctrlwinEditBoletinInformativo.getBoletinInformativo().setPublicacions(sets = new HashSet<Publicacion>());
		if (!this.listPublicacion.getSelectedItems().isEmpty()) {
			if (this.listPublicacion.getSelectedItems().size()<=3) {
				sets = this.listPublicacion.getSelectedItems();
				for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					ctrlwinEditBoletinInformativo.getPublicaciones().add((Publicacion) item.getValue());
				}
				ctrlwinEditBoletinInformativo.recargar();
				this.winCatalogoPublicacion.detach();
			}else {
				MensajeEmergente.mostrar("ARCHADJ");
			}
		}else {
			ctrlwinEditBoletinInformativo.recargar();
			this.winCatalogoPublicacion.detach();
		}
	}

	public void onAfterRender$listPublicacion() {
		listPublicacion.renderAll();
		lists = ctrlwinEditBoletinInformativo.getListPublicaciones().getItems();
		lists2 = this.listPublicacion.getItems();
		for (Iterator iterator = lists.iterator(); iterator.hasNext();) {
			Listitem item = (Listitem) iterator.next();
			for (Iterator iterator1 = lists2.iterator(); iterator1.hasNext();) {
				Listitem item1 = (Listitem) iterator1.next();
				if (((Publicacion) item.getValue()).equals(((Publicacion) item1
						.getValue()))) {
					this.listPublicacion.addItemToSelection(item1);
				}
			}
		}
	}

	public void onSelect$listPublicacion() {
		btnSeleccionar.setDisabled(false);
	}

	public void onClick$btnCancelar() {
		this.winCatalogoPublicacion.detach();
	}

	public void apagarBotones() {
		txtTituloPublicacion.setFocus(true);
		listPublicacion.clearSelection();
		listPublicacion.selectItem(null);
		btnSeleccionar.setDisabled(true);
	}

	public void recargar() {
		if (verTodos)
			publicaciones = servicioPublicacion.buscarTodos('A');
		else if (buscando)
			publicaciones = servicioPublicacion.buscarCoincidencias(
					txtTituloPublicacion.getValue(), 'A');
		else
			publicaciones.clear();

		listPublicacion.setModel(new BindingListModelList(publicaciones, false));
	}

	public void onClick$btnVerTodos() {
		publicaciones = servicioPublicacion.buscarTodos('A');
		listPublicacion.setModel(new BindingListModelList(publicaciones, false));
		buscando = false;
		verTodos = true;
		txtTituloPublicacion.setValue(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtTituloPublicacion.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			publicaciones = servicioPublicacion.buscarCoincidencias(
					txtTituloPublicacion.getValue(), 'A');
			if (publicaciones.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listPublicacion.setModel(new BindingListModelList(publicaciones, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}


}

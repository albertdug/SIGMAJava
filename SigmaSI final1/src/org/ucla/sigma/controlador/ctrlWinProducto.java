package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Producto;
import org.ucla.sigma.servicio.ServicioProducto;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinProducto extends GenericForwardComposer {

	private Window winProducto;
	private Listbox listProducto;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombreProducto;

	// ----------------------------------------------------------------------------------------------------

	private String editProducto = "/sigma/vistas/maestros/producto/editProducto.zul";
	private ServicioProducto servicioProducto;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreProducto.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Producto seleccion;
	private List<Producto> productos = new ArrayList<Producto>();

	// ----------------------------------------------------------------------------------------------------

	
	
	public Window getWinProducto() {
		return winProducto;
	}

	public void setWinProducto(Window winProducto) {
		this.winProducto = winProducto;
	}

	

	public Listbox getListProducto() {
		return listProducto;
	}

	public void setListProducto(Listbox listProducto) {
		this.listProducto = listProducto;
	}

	public Button getBtnBuscar() {
		return btnBuscar;
	}

	public void setBtnBuscar(Button btnBuscar) {
		this.btnBuscar = btnBuscar;
	}

	public Button getBtnNuevo() {
		return btnNuevo;
	}

	public void setBtnNuevo(Button btnNuevo) {
		this.btnNuevo = btnNuevo;
	}

	public Button getBtnEditar() {
		return btnEditar;
	}

	public void setBtnEditar(Button btnEditar) {
		this.btnEditar = btnEditar;
	}

	public Button getBtnEliminar() {
		return btnEliminar;
	}

	public void setBtnEliminar(Button btnEliminar) {
		this.btnEliminar = btnEliminar;
	}

	public Button getBtnVerTodos() {
		return btnVerTodos;
	}

	public void setBtnVerTodos(Button btnVerTodos) {
		this.btnVerTodos = btnVerTodos;
	}

	public ServicioProducto getServicioProducto() {
		return servicioProducto;
	}

	public void setServicioProducto(ServicioProducto servicioProducto) {
		this.servicioProducto = servicioProducto;
	}

	public Producto getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Producto seleccion) {
		this.seleccion = seleccion;
	}

	public Textbox getTxtNombreProducto() {
		return txtNombreProducto;
	}

	public void setTxtNombreProducto(Textbox txtNombreProducto) {
		this.txtNombreProducto = txtNombreProducto;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winProducto.setAttribute(comp.getId() + "ctrl", this);
		servicioProducto = (ServicioProducto) SpringUtil
				.getBean("beanServicioProducto");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinProducto", this);
		Window win = (Window) Executions.createComponents(editProducto, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listProducto.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinProducto", this);
			Window win = (Window) Executions.createComponents(editProducto, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listProducto.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioProducto.borrarProducto(seleccion);
					productos.remove(seleccion);
					listProducto
							.setModel(new BindingListModelList(productos, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		productos = servicioProducto.buscarTodos('A');
		listProducto.setModel(new BindingListModelList(productos, false));
		buscando = false;
		verTodos = true;
		txtNombreProducto.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreProducto.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			productos = servicioProducto.buscarCoincidencias(
					txtNombreProducto.getValue(), 'A');
			if (productos.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listProducto.setModel(new BindingListModelList(productos, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listProducto() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreProducto() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			productos = servicioProducto.buscarTodos('A');
		else if (buscando)
			productos = servicioProducto.buscarCoincidencias(
					txtNombreProducto.getValue(), 'A');
		else
     productos.clear();

		listProducto.setModel(new BindingListModelList(productos, false));
	}

	public void apagarBotones() {
		txtNombreProducto.setFocus(true);
		listProducto.clearSelection();
		listProducto.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

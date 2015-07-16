package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Silueta;
import org.ucla.sigma.servicio.ServicioSilueta;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * 
 */

/**
 * @author Albert
 * 
 */
public class ctrlWinSilueta extends GenericForwardComposer {

	private Window winSilueta;
	private Listbox listSilueta;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombreSilueta;

	// ----------------------------------------------------------------------------------------------------

	private String editSilueta = "/sigma/vistas/maestros/silueta/editSilueta.zul";
	private ServicioSilueta servicioSilueta;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreSilueta.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Silueta seleccion;
	private List<Silueta> siluetas = new ArrayList<Silueta>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinSilueta() {
		return winSilueta;
	}

	public void setWinSilueta(Window winSilueta) {
		this.winSilueta = winSilueta;
	}

	public Listbox getListSilueta() {
		return listSilueta;
	}

	public void setListSilueta(Listbox listSilueta) {
		this.listSilueta = listSilueta;
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

	public ServicioSilueta getServicioSilueta() {
		return servicioSilueta;
	}

	public void setServicioSilueta(ServicioSilueta servicioSilueta) {
		this.servicioSilueta = servicioSilueta;
	}

	public Silueta getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Silueta seleccion) {
		this.seleccion = seleccion;
	}

	public Textbox getTxtNombreSilueta() {
		return txtNombreSilueta;
	}

	public void setTxtNombreSilueta(Textbox txtNombreSilueta) {
		this.txtNombreSilueta = txtNombreSilueta;
	}

	public List<Silueta> getSiluetas() {
		return siluetas;
	}

	public void setSiluetas(List<Silueta> siluetas) {
		this.siluetas = siluetas;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winSilueta.setAttribute(comp.getId() + "ctrl", this);
		servicioSilueta = (ServicioSilueta) SpringUtil
				.getBean("beanServicioSilueta");
		apagarBotones();

	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinSilueta", this);
		Window win = (Window) Executions.createComponents(editSilueta, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listSilueta.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinSilueta", this);
			Window win = (Window) Executions.createComponents(editSilueta,
					null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listSilueta.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioSilueta.borrarSilueta(seleccion);
					siluetas.remove(seleccion);
					listSilueta.setModel(new BindingListModelList(siluetas,
							false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		siluetas = servicioSilueta.buscarTodos('A');
		listSilueta.setModel(new BindingListModelList(siluetas, false));
		buscando = false;
		verTodos = true;
		txtNombreSilueta.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreSilueta.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			siluetas = servicioSilueta.buscarCoincidencias(
					txtNombreSilueta.getValue(), 'A');
			if (siluetas.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listSilueta.setModel(new BindingListModelList(siluetas, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();

	}

	public void onSelect$listSilueta() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreSilueta() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			siluetas = servicioSilueta.buscarTodos('A');
		else if (buscando)
			siluetas = servicioSilueta.buscarCoincidencias(
					txtNombreSilueta.getValue(), 'A');
		else
			siluetas.clear();

		listSilueta.setModel(new BindingListModelList(siluetas, false));
	}

	public void apagarBotones() {
		txtNombreSilueta.setFocus(true);
		listSilueta.clearSelection();
		listSilueta.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

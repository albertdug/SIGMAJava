package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Segmento;
import org.ucla.sigma.servicio.ServicioSegmento;
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
public class ctrlWinSegmento extends GenericForwardComposer {

	private Window winSegmento;
	private Listbox listSegmento;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombreSegmento;

	// ----------------------------------------------------------------------------------------------------

	private String editSegmento = "/sigma/vistas/maestros/segmento/editSegmento.zul";
	private ServicioSegmento servicioSegmento;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreSegmento.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Segmento seleccion;
	private List<Segmento> segmentos = new ArrayList<Segmento>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinSegmento() {
		return winSegmento;
	}

	public void setWinSegmento(Window winSegmento) {
		this.winSegmento = winSegmento;
	}

	public Listbox getListSegmento() {
		return listSegmento;
	}

	public void setListSegmento(Listbox listSegmento) {
		this.listSegmento = listSegmento;
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

	public ServicioSegmento getServicioSegmento() {
		return servicioSegmento;
	}

	public void setServicioSegmento(ServicioSegmento servicioSegmento) {
		this.servicioSegmento = servicioSegmento;
	}

	public Segmento getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Segmento seleccion) {
		this.seleccion = seleccion;
	}

	public Textbox getTxtNombreSegmento() {
		return txtNombreSegmento;
	}

	public void setTxtNombreSegmento(Textbox txtNombreSegmento) {
		this.txtNombreSegmento = txtNombreSegmento;
	}

	public List<Segmento> getSegmentos() {
		return segmentos;
	}

	public void setSegmentos(List<Segmento> segmentos) {
		this.segmentos = segmentos;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winSegmento.setAttribute(comp.getId() + "ctrl", this);
		servicioSegmento = (ServicioSegmento) SpringUtil
				.getBean("beanServicioSegmento");
		apagarBotones();

	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinSegmento", this);
		Window win = (Window) Executions.createComponents(editSegmento, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listSegmento.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinSegmento", this);
			Window win = (Window) Executions.createComponents(editSegmento,
					null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listSegmento.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioSegmento.borrarSegmento(seleccion);
					segmentos.remove(seleccion);
					listSegmento.setModel(new BindingListModelList(segmentos,
							false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		segmentos = servicioSegmento.buscarTodos('A');
		listSegmento.setModel(new BindingListModelList(segmentos, false));
		buscando = false;
		verTodos = true;
		txtNombreSegmento.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreSegmento.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			segmentos = servicioSegmento.buscarCoincidencias(
					txtNombreSegmento.getValue(), 'A');
			if (segmentos.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listSegmento
						.setModel(new BindingListModelList(segmentos, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();

	}

	public void onSelect$listSegmento() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreSegmento() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			segmentos = servicioSegmento.buscarTodos('A');
		else if (buscando)
			segmentos = servicioSegmento.buscarCoincidencias(
					txtNombreSegmento.getValue(), 'A');
		else
			segmentos.clear();

		listSegmento.setModel(new BindingListModelList(segmentos, false));
	}

	public void apagarBotones() {
		txtNombreSegmento.setFocus(true);
		listSegmento.clearSelection();
		listSegmento.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

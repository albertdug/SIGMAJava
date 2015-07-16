package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Sintoma;
import org.ucla.sigma.servicio.ServicioSintoma;
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
public class ctrlWinSintoma extends GenericForwardComposer {

	private Window winSintoma;
	private Listbox listSintoma;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombreSintoma;

	// ----------------------------------------------------------------------------------------------------

	private String editSintoma = "/sigma/vistas/maestros/sintoma/editSintoma.zul";
	private ServicioSintoma servicioSintoma;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreSintoma.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private Sintoma seleccion;
	private List<Sintoma> sintomas = new ArrayList<Sintoma>();

	// ----------------------------------------------------------------------------------------------------

	public Window getWinSintoma() {
		return winSintoma;
	}

	public void setWinSintoma(Window winSintoma) {
		this.winSintoma = winSintoma;
	}

	public Listbox getListSintoma() {
		return listSintoma;
	}

	public void setListSintoma(Listbox listSintoma) {
		this.listSintoma = listSintoma;
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

	public ServicioSintoma getServicioSintoma() {
		return servicioSintoma;
	}

	public void setServicioSintoma(ServicioSintoma servicioSintoma) {
		this.servicioSintoma = servicioSintoma;
	}

	public Sintoma getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Sintoma seleccion) {
		this.seleccion = seleccion;
	}

	public Textbox getTxtNombreSintoma() {
		return txtNombreSintoma;
	}

	public void setTxtNombreSintoma(Textbox txtNombreSintoma) {
		this.txtNombreSintoma = txtNombreSintoma;
	}

	public List<Sintoma> getSintomas() {
		return sintomas;
	}

	public void setSintomas(List<Sintoma> sintomas) {
		this.sintomas = sintomas;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winSintoma.setAttribute(comp.getId() + "ctrl", this);
		servicioSintoma = (ServicioSintoma) SpringUtil
				.getBean("beanServicioSintoma");
		apagarBotones();

	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinSintoma", this);
		Window win = (Window) Executions.createComponents(editSintoma, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listSintoma.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinSintoma", this);
			Window win = (Window) Executions.createComponents(editSintoma,
					null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listSintoma.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioSintoma.borrarSintoma(seleccion);
					sintomas.remove(seleccion);
					listSintoma.setModel(new BindingListModelList(sintomas,
							false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		sintomas = servicioSintoma.buscarTodos('A');
		listSintoma.setModel(new BindingListModelList(sintomas, false));
		buscando = false;
		verTodos = true;
		txtNombreSintoma.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreSintoma.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			sintomas = servicioSintoma.buscarCoincidencias(
					txtNombreSintoma.getValue(), 'A');
			if (sintomas.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listSintoma.setModel(new BindingListModelList(sintomas, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();

	}

	public void onSelect$listSintoma() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreSintoma() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			sintomas = servicioSintoma.buscarTodos('A');
		else if (buscando)
			sintomas = servicioSintoma.buscarCoincidencias(
					txtNombreSintoma.getValue(), 'A');
		else
			sintomas.clear();

		listSintoma.setModel(new BindingListModelList(sintomas, false));
	}

	public void apagarBotones() {
		txtNombreSintoma.setFocus(true);
		listSintoma.clearSelection();
		listSintoma.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

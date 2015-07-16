/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.EspImagenologia;
import org.ucla.sigma.servicio.ServicioEspImagenologia;
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
 * @author lis
 *
 */
public class ctrlWinEspecificacionImagenologia extends GenericForwardComposer {

	private Window winEspecificacionImagenologia;
	private Listbox listEspImagenologia;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private String editEspImagenologia = "/sigma/vistas/maestros/especificacionImagenologia/editEspecificacionImagenologia.zul";
	private ServicioEspImagenologia servicioEspImagenologia;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombre.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private EspImagenologia seleccion;
	private List<EspImagenologia> espImagenologias = new ArrayList<EspImagenologia>();
	
	
	// ----------------------------------------------------------------------------------------------------
	
	public Window getWinEspecificacionImagenologia() {
		return winEspecificacionImagenologia;
	}
	public void setWinEspecificacionImagenologia(
			Window winEspecificacionImagenologia) {
		this.winEspecificacionImagenologia = winEspecificacionImagenologia;
	}
	public Listbox getListEspImagenologia() {
		return listEspImagenologia;
	}
	public void setListEspImagenologia(Listbox listEspImagenologia) {
		this.listEspImagenologia = listEspImagenologia;
	}
	public Button getBtnVerTodos() {
		return btnVerTodos;
	}
	public void setBtnVerTodos(Button btnVerTodos) {
		this.btnVerTodos = btnVerTodos;
	}
	public Button getBtnEliminar() {
		return btnEliminar;
	}
	public void setBtnEliminar(Button btnEliminar) {
		this.btnEliminar = btnEliminar;
	}
	public Button getBtnEditar() {
		return btnEditar;
	}
	public void setBtnEditar(Button btnEditar) {
		this.btnEditar = btnEditar;
	}
	public Button getBtnNuevo() {
		return btnNuevo;
	}
	public void setBtnNuevo(Button btnNuevo) {
		this.btnNuevo = btnNuevo;
	}
	public Button getBtnBuscar() {
		return btnBuscar;
	}
	public void setBtnBuscar(Button btnBuscar) {
		this.btnBuscar = btnBuscar;
	}
	public Textbox getTxtNombre() {
		return txtNombre;
	}
	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}
	public String getEditEspImagenologia() {
		return editEspImagenologia;
	}
	public void setEditEspImagenologia(String editEspImagenologia) {
		this.editEspImagenologia = editEspImagenologia;
	}
	public ServicioEspImagenologia getServicioEspImagenologia() {
		return servicioEspImagenologia;
	}
	public void setServicioEspImagenologia(
			ServicioEspImagenologia servicioEspImagenologia) {
		this.servicioEspImagenologia = servicioEspImagenologia;
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
	public MensajeListener getAsignarFocusBuscar() {
		return asignarFocusBuscar;
	}
	public void setAsignarFocusBuscar(MensajeListener asignarFocusBuscar) {
		this.asignarFocusBuscar = asignarFocusBuscar;
	}
	public EspImagenologia getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(EspImagenologia seleccion) {
		this.seleccion = seleccion;
	}
	public List<EspImagenologia> getEspImagenologias() {
		return espImagenologias;
	}
	public void setEspImagenologias(List<EspImagenologia> espImagenologias) {
		this.espImagenologias = espImagenologias;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEspecificacionImagenologia.setAttribute(comp.getId() + "ctrl", this);
		servicioEspImagenologia = (ServicioEspImagenologia) SpringUtil
				.getBean("beanServicioEspImagenologia");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinEspImagenologia", this);
		Window win = (Window) Executions.createComponents(editEspImagenologia, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listEspImagenologia.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			System.out.println(seleccion.getNombre());
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinEspImagenologia", this);
			Window win = (Window) Executions.createComponents(editEspImagenologia, null,
					parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listEspImagenologia.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioEspImagenologia.borrarEspImagenologia(seleccion);
					espImagenologias.remove(seleccion);
					listEspImagenologia
							.setModel(new BindingListModelList(espImagenologias, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		espImagenologias = servicioEspImagenologia.buscarTodos('A');
		listEspImagenologia.setModel(new BindingListModelList(espImagenologias, false));
		buscando = false;
		verTodos = true;
		txtNombre.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombre.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			espImagenologias = servicioEspImagenologia.buscarCoincidencias(
					txtNombre.getValue(), 'A');
			if (espImagenologias.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listEspImagenologia.setModel(new BindingListModelList(espImagenologias, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listEspImagenologia() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombre() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			espImagenologias = servicioEspImagenologia.buscarTodos('A');
		else if (buscando)
			espImagenologias = servicioEspImagenologia.buscarCoincidencias(
					txtNombre.getValue(), 'A');
		else
			espImagenologias.clear();

		listEspImagenologia.setModel(new BindingListModelList(espImagenologias, false));
	}

	public void apagarBotones() {
		txtNombre.setFocus(true);
		listEspImagenologia.clearSelection();
		listEspImagenologia.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}


	
	
	
	
}

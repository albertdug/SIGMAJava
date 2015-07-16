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
import org.ucla.sigma.modelo.Patologia;
import org.ucla.sigma.modelo.Patologia;
import org.ucla.sigma.servicio.ServicioPatologia;
import org.ucla.sigma.servicio.ServicioPatologia;
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
 * @author rafael
 * 
 */
public class ctrlWinPatologia extends GenericForwardComposer {

	private Window winPatologia;
	private Listbox listPatologia;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtNombrePatologia;

	private String editPatologia = "/sigma/vistas/maestros/patologia/editPatologia.zul";
	private ServicioPatologia servicioPatologia;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombrePatologia.setFocus(true);
		};
	};

	private Patologia seleccion;
	private List<Patologia> patologias = new ArrayList<Patologia>();

	public Window getWinPatologia() {
		return winPatologia;
	}

	public void setWinPatologia(Window winPatologia) {
		this.winPatologia = winPatologia;
	}

	public Listbox getListPatologia() {
		return listPatologia;
	}

	public void setListPatologia(Listbox listPatologia) {
		this.listPatologia = listPatologia;
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

	public Textbox getTxtNombrePatologia() {
		return txtNombrePatologia;
	}

	public void setTxtNombrePatologia(Textbox txtNombrePatologia) {
		this.txtNombrePatologia = txtNombrePatologia;
	}

	public String getEditPatologia() {
		return editPatologia;
	}

	public void setEditPatologia(String editPatologia) {
		this.editPatologia = editPatologia;
	}

	public ServicioPatologia getServicioPatologia() {
		return servicioPatologia;
	}

	public void setServicioPatologia(ServicioPatologia servicioPatologia) {
		this.servicioPatologia = servicioPatologia;
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

	public Patologia getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Patologia seleccion) {
		this.seleccion = seleccion;
	}

	public List<Patologia> getPatologias() {
		return patologias;
	}

	public void setPatologias(List<Patologia> patologias) {
		this.patologias = patologias;
	}

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winPatologia.setAttribute(comp.getId() + "ctrl", this);
		servicioPatologia = (ServicioPatologia) SpringUtil
				.getBean("beanServicioPatologia");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinPatologia", this);
		Window win = (Window) Executions.createComponents(editPatologia, null,
				parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listPatologia.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinPatologia", this);
			Window win = (Window) Executions.createComponents(editPatologia,
					null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listPatologia.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioPatologia.borrarPatologia(seleccion);
					patologias.remove(seleccion);
					listPatologia.setModel(new BindingListModelList(patologias,
							false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		patologias = servicioPatologia.buscarTodos('A');
		listPatologia.setModel(new BindingListModelList(patologias, false));
		buscando = false;
		verTodos = true;
		txtNombrePatologia.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombrePatologia.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			patologias = servicioPatologia.buscarCoincidencias(
					txtNombrePatologia.getValue(), 'A');
			if (patologias.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listPatologia.setModel(new BindingListModelList(patologias,
						false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listPatologia() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombrePatologia() {
		apagarBotones();
	}

	public void recargar() {

		seleccion = null;

		if (verTodos)
			patologias = servicioPatologia.buscarTodos('A');
		else if (buscando)
			patologias = servicioPatologia.buscarCoincidencias(
					txtNombrePatologia.getValue(), 'A');
		else
			patologias.clear();

		listPatologia.setModel(new BindingListModelList(patologias, false));
	}

	public void apagarBotones() {
		txtNombrePatologia.setFocus(true);
		listPatologia.clearSelection();
		listPatologia.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

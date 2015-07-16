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
import org.ucla.sigma.modelo.Estado;
import org.ucla.sigma.modelo.PalpacionAbdominal;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioPalpacionAbdominal;
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
 * @author jhoan
 * 
 */
public class ctrlWinPalpacionAbdominal extends GenericForwardComposer {

	private Window winPalpacionAbdominal;
	private Listbox listPalpacionAbdominal;
	private Button btnBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Button btnEliminar;
	private Button btnVerTodos;
	private Textbox txtNombrePalpacionAbdominal;

	// ----------------------------------------------------------------------------------------------------

	private String editPalpacionAbdominal = "/sigma/vistas/maestros/palpacionAbdominal/editPalpacionAbdominal.zul";
	private ServicioPalpacionAbdominal servicioPalpacionAbdominal;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombrePalpacionAbdominal.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private PalpacionAbdominal seleccion;
	private List<PalpacionAbdominal> palpacionabdominals = new ArrayList<PalpacionAbdominal>();

	public Window getWinPalpacionAbdominal() {
		return winPalpacionAbdominal;
	}

	public void setWinPalpacionAbdominal(Window winPalpacionAbdominal) {
		this.winPalpacionAbdominal = winPalpacionAbdominal;
	}

	public List<PalpacionAbdominal> getPalpacionabdominals() {
		return palpacionabdominals;
	}

	public void setPalpacionabdominals(
			List<PalpacionAbdominal> palpacionabdominals) {
		this.palpacionabdominals = palpacionabdominals;
	}

	public Listbox getListPalpacionAbdominal() {
		return listPalpacionAbdominal;
	}

	public void setListPalpacionAbdominal(Listbox listPalpacionAbdominal) {
		this.listPalpacionAbdominal = listPalpacionAbdominal;
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

	public Textbox getTxtNombrePalpacionAbdominal() {
		return txtNombrePalpacionAbdominal;
	}

	public void setTxtNombrePalpacionAbdominal(
			Textbox txtNombrePalpacionAbdominal) {
		this.txtNombrePalpacionAbdominal = txtNombrePalpacionAbdominal;
	}

	public String getEditPalpacionAbdominal() {
		return editPalpacionAbdominal;
	}

	public void setEditPalpacionAbdominal(String editPalpacionAbdominal) {
		this.editPalpacionAbdominal = editPalpacionAbdominal;
	}

	public ServicioPalpacionAbdominal getServicioPalpacionAbdominal() {
		return servicioPalpacionAbdominal;
	}

	public void setServicioPalpacionAbdominal(
			ServicioPalpacionAbdominal servicioPalpacionAbdominal) {
		this.servicioPalpacionAbdominal = servicioPalpacionAbdominal;
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

	public PalpacionAbdominal getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(PalpacionAbdominal seleccion) {
		this.seleccion = seleccion;
	}

	public List<PalpacionAbdominal> getPalpacionAbdominals() {
		return palpacionabdominals;
	}

	public void setPalpacionAbdominals(
			List<PalpacionAbdominal> palpacionabdominals) {
		this.palpacionabdominals = palpacionabdominals;
	}

	// ----------------------------------------------------------------------------------------------------

	/**
	 * Agrega el controlador a la vista, Asigna el servicio a usar
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winPalpacionAbdominal.setAttribute(comp.getId() + "ctrl", this);
		servicioPalpacionAbdominal = (ServicioPalpacionAbdominal) SpringUtil
				.getBean("beanServicioPalpacionAbdominal");
		apagarBotones();
	}

	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinPalpacionAbdominal", this);
		Window win = (Window) Executions.createComponents(
				editPalpacionAbdominal, null, parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listPalpacionAbdominal.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinPalpacionAbdominal", this);
			Window win = (Window) Executions.createComponents(
					editPalpacionAbdominal, null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listPalpacionAbdominal.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioPalpacionAbdominal
							.borrarPalpacionAbdominal(seleccion);
					palpacionabdominals.remove(seleccion);
					listPalpacionAbdominal.setModel(new BindingListModelList(
							palpacionabdominals, false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		palpacionabdominals = servicioPalpacionAbdominal.buscarTodos('A');
		listPalpacionAbdominal.setModel(new BindingListModelList(
				palpacionabdominals, false));
		buscando = false;
		verTodos = true;
		txtNombrePalpacionAbdominal.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombrePalpacionAbdominal.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			palpacionabdominals = servicioPalpacionAbdominal
					.buscarCoincidencias(
							txtNombrePalpacionAbdominal.getValue(), 'A');
			if (palpacionabdominals.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listPalpacionAbdominal.setModel(new BindingListModelList(
						palpacionabdominals, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listPalpacionAbdominal() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtNombreEstado() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			palpacionabdominals = servicioPalpacionAbdominal.buscarTodos('A');
		else if (buscando)
			palpacionabdominals = servicioPalpacionAbdominal
					.buscarCoincidencias(
							txtNombrePalpacionAbdominal.getValue(), 'A');
		else
			palpacionabdominals.clear();

		listPalpacionAbdominal.setModel(new BindingListModelList(
				palpacionabdominals, false));
	}

	public void apagarBotones() {
		txtNombrePalpacionAbdominal.setFocus(true);
		listPalpacionAbdominal.clearSelection();
		listPalpacionAbdominal.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

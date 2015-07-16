/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.modelo.Referencia;
import org.ucla.sigma.servicio.ServicioReferencia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Window;

/**
 * @author lis
 * 
 */
public class ctrlWinCitasMedico extends GenericForwardComposer {

	private Window winCitasMedico;
	private Button btnAtender;
	private Listbox listCitasMedico;

	private String pag;

	// ----------------------------------------------------------------------------------------------------

	private Referencia seleccion;
	private List<Referencia> referencias = new ArrayList<Referencia>();
	private String editCitas = "/sigma/vistas/servicios/citas/editCitas.zul";
	private ServicioReferencia servicioReferencia;
	private boolean buscando = false;
	private boolean verTodos = false;

	// ----------------------------------------------------------------------------------------------------

	public Button getBtnAtender() {
		return btnAtender;
	}

	public void setBtnAtender(Button btnAtender) {
		this.btnAtender = btnAtender;
	}

	public Window getWinCitasMedico() {
		return winCitasMedico;
	}

	public void setWinCitasMedico(Window winCitasMedico) {
		this.winCitasMedico = winCitasMedico;
	}

	public Listbox getlistCitasMedico() {
		return listCitasMedico;
	}

	public void setlistCitasMedico(Listbox listCitasMedico) {
		this.listCitasMedico = listCitasMedico;
	}

	public Referencia getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Referencia seleccion) {
		this.seleccion = seleccion;
	}

	public List<Referencia> getReferencias() {
		return referencias;
	}

	public void setReferencias(List<Referencia> referencias) {
		this.referencias = referencias;
	}

	public String getEditCitas() {
		return editCitas;
	}

	public void setEditCitas(String editCitas) {
		this.editCitas = editCitas;
	}

	public ServicioReferencia getServicioReferencia() {
		return servicioReferencia;
	}

	public void setServicioReferencia(ServicioReferencia servicioReferencia) {
		this.servicioReferencia = servicioReferencia;
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

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winCitasMedico.setAttribute(comp.getId() + "ctrl", this);
		seleccion = new Referencia();
		servicioReferencia = (ServicioReferencia) SpringUtil
				.getBean("beanServicioReferencia");
		apagarBotones();
		referencias = servicioReferencia.buscarHoy("fechaCita", 'C');
		if (referencias.isEmpty()) {
			MensajeEmergente.mostrar("NODATE");
		}
	}

	public void apagarBotones() {
		btnAtender.setDisabled(true);
	}

	public void onSelect$listCitasMedico() {
		btnAtender.setDisabled(false);
	}

	public void onClick$btnAtender() {

		if (!listCitasMedico.getSelectedItems().isEmpty()) {
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			pag =seleccion.getTipoServicio().getVista();
			execution.createComponents(pag, winCitasMedico.getParent(),
					parametros);
			winCitasMedico.detach();
			
		} else {
			MensajeEmergente.mostrar("SELECTREG");
		}
	}
}
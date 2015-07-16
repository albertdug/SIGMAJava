/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.soap.Text;

import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.components.HelperDateAge;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.ConsultaGeneral;
import org.ucla.sigma.modelo.EspImagenologia;
import org.ucla.sigma.modelo.Estado;
import org.ucla.sigma.modelo.Historial;
import org.ucla.sigma.modelo.Paciente;
import org.ucla.sigma.modelo.ReferenciaConsultaEspecializada;
import org.ucla.sigma.modelo.ReferenciaImagenologia;
import org.ucla.sigma.modelo.TipoImagenologia;
import org.ucla.sigma.modelo.TipoReferencia;
import org.ucla.sigma.modelo.TipoServicio;
import org.ucla.sigma.servicio.ServicioEspImagenologia;
import org.ucla.sigma.servicio.ServicioReferenciaConsultaEspecializada;
import org.ucla.sigma.servicio.ServicioReferenciaImagenologia;
import org.ucla.sigma.servicio.ServicioTipoImagenologia;
import org.ucla.sigma.servicio.ServicioTipoReferencia;
import org.ucla.sigma.servicio.ServicioTipoServicio;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author lis
 *
 */
public class ctrlWinReferenciaImagenologia extends GenericForwardComposer {

	private Window winReferenciaImagenologia;
	private Button btnCancelar;
	private Button btnGuardar;
	private Button btnMas;
	private Button btnMenos;
	private Listbox listEspecificacion;
	private Listbox listListadoImagenologia;
	private Listbox listEstudio;
	private Textbox txtEdad;
	private Datebox dbFechaActual;
	private List auxList;
	private Set<EspImagenologia> auxSet =  new HashSet<EspImagenologia>();
	

	// ----------------------------------------------------------------------------------------------------

	private ServicioTipoImagenologia servicioTipoImagenologia;
	private ServicioEspImagenologia servicioEspImagenologia;
	private ServicioReferenciaImagenologia servicioReferenciaImagenologia;
	private TipoReferencia tipoReferencia;
	private Paciente paciente;
	private Historial historial;

	// ----------------------------------------------------------------------------------------------------

	private List<EspImagenologia> espImagenologiaCombo = new ArrayList<EspImagenologia>();
	private List<EspImagenologia> espImagenologias = new ArrayList<EspImagenologia>();
	private List<TipoImagenologia> tipoImagenologias = new ArrayList<TipoImagenologia>();
	private ReferenciaImagenologia referenciaImagenologia;
	private TipoServicio tipoServicio;
	private ServicioTipoServicio servicioTipoServicio;

	
	public Set getAuxSet() {
		return auxSet;
	}
	public void setAuxSet(Set auxSet) {
		this.auxSet = auxSet;
	}
	public Listbox getListListadoImagenologia() {
		return listListadoImagenologia;
	}
	public void setListListadoImagenologia(Listbox listListadoImagenologia) {
		this.listListadoImagenologia = listListadoImagenologia;
	}
	public Datebox getDbFechaActual() {
		return dbFechaActual;
	}
	public void setDbFechaActual(Datebox dbFechaActual) {
		this.dbFechaActual = dbFechaActual;
	}

	public Listbox getListEspecificacion() {
		return listEspecificacion;
	}
	public void setListEspecificacion(Listbox listEspecificacion) {
		this.listEspecificacion = listEspecificacion;
	}
	public TipoReferencia getTipoReferencia() {
		return tipoReferencia;
	}
	public void setTipoReferencia(TipoReferencia tipoReferencia) {
		this.tipoReferencia = tipoReferencia;
	}

	public List<EspImagenologia> getEspImagenologiaCombo() {
		return espImagenologiaCombo;
	}
	public void setEspImagenologiaCombo(List<EspImagenologia> espImagenologiaCombo) {
		this.espImagenologiaCombo = espImagenologiaCombo;
	}
	public Button getBtnMas() {
		return btnMas;
	}
	public void setBtnMas(Button btnMas) {
		this.btnMas = btnMas;
	}
	public Button getBtnMenos() {
		return btnMenos;
	}
	public void setBtnMenos(Button btnMenos) {
		this.btnMenos = btnMenos;
	}
	public TipoServicio getTipoServicio() {
		return tipoServicio;
	}
	public void setTipoServicio(TipoServicio tipoServicio) {
		this.tipoServicio = tipoServicio;
	}
	public ServicioTipoServicio getServicioTipoServicio() {
		return servicioTipoServicio;
	}
	public void setServicioTipoServicio(ServicioTipoServicio servicioTipoServicio) {
		this.servicioTipoServicio = servicioTipoServicio;
	}
	public Historial getHistorial() {
		return historial;
	}
	public void setHistorial(Historial historial) {
		this.historial = historial;
	}
	public Textbox getTxtEdad() {
		return txtEdad;
	}
	public void setTxtEdad(Textbox txtEdad) {
		this.txtEdad = txtEdad;
	}
	public List<EspImagenologia> getEspImagenologias() {
		return espImagenologias;
	}
	public void setEspImagenologias(List<EspImagenologia> espImagenologias) {
		this.espImagenologias = espImagenologias;
	}
	public List<TipoImagenologia> getTipoImagenologias() {
		return tipoImagenologias;
	}
	public void setTipoImagenologias(List<TipoImagenologia> tipoImagenologias) {
		this.tipoImagenologias = tipoImagenologias;
	}
	public Window getWinReferenciaImagenologia() {
		return winReferenciaImagenologia;
	}

	public void setWinReferenciaImagenologia(Window winReferenciaImagenologia) {
		this.winReferenciaImagenologia = winReferenciaImagenologia;
	}

	public Listbox getListEstudio() {
		return listEstudio;
	}
	public void setListEstudio(Listbox listEstudio) {
		this.listEstudio = listEstudio;
	}

	public ServicioTipoImagenologia getServicioTipoImagenologia() {
		return servicioTipoImagenologia;
	}
	public void setServicioTipoImagenologia(
			ServicioTipoImagenologia servicioTipoImagenologia) {
		this.servicioTipoImagenologia = servicioTipoImagenologia;
	}
	public ServicioReferenciaImagenologia getServicioReferenciaImagenologia() {
		return servicioReferenciaImagenologia;
	}
	public void setServicioReferenciaImagenologia(
			ServicioReferenciaImagenologia servicioReferenciaImagenologia) {
		this.servicioReferenciaImagenologia = servicioReferenciaImagenologia;
	}
	public ServicioEspImagenologia getServicioEspImagenologia() {
		return servicioEspImagenologia;
	}
	public void setServicioEspImagenologia(
			ServicioEspImagenologia servicioEspImagenologia) {
		this.servicioEspImagenologia = servicioEspImagenologia;
	}
	public ReferenciaImagenologia getReferenciaImagenologia() {
		return referenciaImagenologia;
	}
	public void setReferenciaImagenologia(
			ReferenciaImagenologia referenciaImagenologia) {
		this.referenciaImagenologia = referenciaImagenologia;
	}
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	public Button getBtnCancelar() {
		return btnCancelar;
	}
	public void setBtnCancelar(Button btnCancelar) {
		this.btnCancelar = btnCancelar;
	}
	public Button getBtnGuardar() {
		return btnGuardar;
	}
	public void setBtnGuardar(Button btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		paciente = (Paciente) arg.get("paciente");
		historial = (Historial) arg.get("historial");
		winReferenciaImagenologia.setAttribute(comp.getId() + "ctrl", this);
		servicioEspImagenologia = (ServicioEspImagenologia) SpringUtil
				.getBean("beanServicioEspImagenologia");
		servicioTipoImagenologia = (ServicioTipoImagenologia) SpringUtil
				.getBean("beanServicioTipoImagenologia");
		servicioTipoServicio= (ServicioTipoServicio) SpringUtil
				.getBean("beanServicioTipoServicio");
		servicioReferenciaImagenologia = (ServicioReferenciaImagenologia) SpringUtil
				.getBean("beanServicioReferenciaImagenologia");
		referenciaImagenologia = new ReferenciaImagenologia();
		tipoServicio = servicioTipoServicio.buscarTipoServicio("IMG",'A');
		txtEdad.setValue(HelperDateAge.age(paciente.getFechaNac(), ' '));
		dbFechaActual.setValue(HelperDate.now());
		espImagenologiaCombo = servicioEspImagenologia.buscarTodos('A'); 
		tipoImagenologias = servicioTipoImagenologia.buscarTodos('A');

	}

	public void onClick$btnCancelar() {
		this.winReferenciaImagenologia.detach();
	}

	public void onClick$btnGuardar(){
		if (listEstudio.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\nTipo de estudio",
					new MensajeListener() {
				@Override
				public void alDestruir() {
					listEstudio.setFocus(true);
				}
			});
		}else if (listEspecificacion.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("NOEMPTY", "\nEspecificación",
					new MensajeListener() {
				@Override
				public void alDestruir() {
					listEspecificacion.setFocus(true);
				}
			});
		}else {
			referenciaImagenologia.setFechaExpedicion(dbFechaActual.getValue());
			referenciaImagenologia.setHistorial(historial);
			referenciaImagenologia.setTipoServicio(tipoServicio);
			 
			auxList = listListadoImagenologia.getItems();
			for (Iterator iterator = auxList.iterator(); iterator.hasNext();) {
				Listitem temp = (Listitem) iterator.next();
				auxSet.add((EspImagenologia) temp.getValue());
				
			}
			referenciaImagenologia.setEspImagenologias(auxSet);
			
			servicioReferenciaImagenologia.guardarReferenciaImagenologia(referenciaImagenologia);
			MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
				@Override
				public void alAceptar() {
					winReferenciaImagenologia.detach();
				}
			});

		}
	}

	public void onSelect$listEstudio() {

		espImagenologiaCombo = servicioEspImagenologia.buscarEstudiosAsociados((TipoImagenologia)listEstudio.getSelectedItem().getValue(), 'A');
		listEspecificacion.setModel(new BindingListModelList(espImagenologiaCombo, false));
		listEspecificacion.setDisabled(false);
	}

	public void onClick$btnMenos() {
		quitarItemListbox(listListadoImagenologia,espImagenologias );
	}

	public void onClick$btnMas() {
		agregarItemListbox(listListadoImagenologia, listEspecificacion,
				espImagenologias, espImagenologiaCombo, "Imagenologias");
	}

	public void agregarItemListbox(Listbox listado, Listbox combo,
			List listListado, List listCombo, String nombreComboListB) {
		if (combo.getSelectedIndex() >= 0) {
			boolean encontrado = false;
			for (int i = 0; i < listListado.size(); i++) {
				if (listListado.get(i) == combo.getSelectedItem().getValue()) {
					encontrado = true;
				}
			}
			if (!encontrado) {
				listListado.add(combo.getSelectedItem().getValue());
				listado.setModel(new BindingListModelList(listListado, false));
			} else {
				MensajeEmergente.mostrar("ELEEXIST", "\n" + nombreComboListB);
			}
		} else {
			MensajeEmergente.mostrar("SELECTELE", "\n" + nombreComboListB);
			combo.setFocus(true);
		}
	}

	public void quitarItemListbox(Listbox listado, List listListado) {
		int indice = listado.getSelectedIndex();
		if (indice >= 0) {
			listListado.remove(indice);
			listado.setModel(new BindingListModelList(listListado, false));
		} else
			MensajeEmergente.mostrar("SELECTELE");
	}

}

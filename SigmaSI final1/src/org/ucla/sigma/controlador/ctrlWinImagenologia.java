/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.List;

import org.ucla.sigma.modelo.Ecocardiograma;
import org.ucla.sigma.modelo.Ecosonograma;
import org.ucla.sigma.modelo.Endoscopia;
import org.ucla.sigma.modelo.Radiografia;
import org.ucla.sigma.modelo.Imagenologia;
import org.ucla.sigma.servicio.ServicioEcocardiograma;
import org.ucla.sigma.servicio.ServicioEcosonograma;
import org.ucla.sigma.servicio.ServicioEndoscopia;
import org.ucla.sigma.servicio.ServicioImagenologia;
import org.ucla.sigma.servicio.ServicioRadiografia;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author promo49
 * 
 */
public class ctrlWinImagenologia extends GenericForwardComposer {

	private Window winImagenologia;
	private Button btnCancelar;
	private Button btnEliminar;
	private Button btnGuardar;
	private ServicioImagenologia servicioImagenologia;
	private ServicioRadiografia servicioRadiografia;
	private ServicioEndoscopia servicioEndoscopia;
	private ServicioEcosonograma servicioEcosonograma;
	private ServicioEcocardiograma servicioEcocardiograma;
	private List<Imagenologia> all_imagenologia = new ArrayList<Imagenologia>();
	private List<Radiografia> radiografias = new ArrayList<Radiografia>();
	private List<Endoscopia> all_endoscopia = new ArrayList<Endoscopia>();
	private List<Ecosonograma> all_ecosonograma = new ArrayList<Ecosonograma>();
	private List<Ecocardiograma> all_ecocardiograma = new ArrayList<Ecocardiograma>();
	private Imagenologia imagenologia;
	private Radiografia radiografia;
	private Endoscopia endoscopia;
	private Ecosonograma ecosonograma;
	private Ecocardiograma ecocardiograma;
	private Listbox listRadiografia;
	private Listbox listEcosonograma;
	private Listbox listEcocardiograma;
	private int indexRadiografia = -1;

	public ctrlWinImagenologia(Window winImagenologia, Button btnCancelar,
			Button btnEliminar, Button btnGuardar,
			ServicioImagenologia servicioImagenologia,
			ServicioRadiografia servicioRadiografia,
			ServicioEndoscopia servicioEndoscopia,
			ServicioEcosonograma servicioEcosonograma,
			ServicioEcocardiograma servicioEcocardiograma,
			List<Imagenologia> all_imagenologia,
			List<Radiografia> radiografias, List<Endoscopia> all_endoscopia,
			List<Ecosonograma> all_ecosonograma,
			List<Ecocardiograma> all_ecocardiograma, Imagenologia imagenologia,
			Radiografia radiografia, Endoscopia endoscopia,
			Ecosonograma ecosonograma, Ecocardiograma ecocardiograma,
			Listbox listRadiografia, Listbox listEcosonograma,
			Listbox listEcocardiograma) {
		super();
		this.winImagenologia = winImagenologia;
		this.btnCancelar = btnCancelar;
		this.btnEliminar = btnEliminar;
		this.btnGuardar = btnGuardar;
		this.servicioImagenologia = servicioImagenologia;
		this.servicioRadiografia = servicioRadiografia;
		this.servicioEndoscopia = servicioEndoscopia;
		this.servicioEcosonograma = servicioEcosonograma;
		this.servicioEcocardiograma = servicioEcocardiograma;
		this.all_imagenologia = all_imagenologia;
		this.radiografias = radiografias;
		this.all_endoscopia = all_endoscopia;
		this.all_ecosonograma = all_ecosonograma;
		this.all_ecocardiograma = all_ecocardiograma;
		this.imagenologia = imagenologia;
		this.radiografia = radiografia;
		this.endoscopia = endoscopia;
		this.ecosonograma = ecosonograma;
		this.ecocardiograma = ecocardiograma;
		this.listRadiografia = listRadiografia;
		this.listEcosonograma = listEcosonograma;
		this.listEcocardiograma = listEcocardiograma;
	}

	public ctrlWinImagenologia() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ctrlWinImagenologia(char separator, boolean ignoreZScript,
			boolean ignoreXel) {
		super(separator, ignoreZScript, ignoreXel);
		// TODO Auto-generated constructor stub
	}

	public ctrlWinImagenologia(char separator) {
		super(separator);
		// TODO Auto-generated constructor stub
	}

	public Window getWinImagenologia() {
		return winImagenologia;
	}

	public void setWinImagenologia(Window winImagenologia) {
		this.winImagenologia = winImagenologia;
	}

	public Button getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(Button btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public Button getBtnEliminar() {
		return btnEliminar;
	}

	public void setBtnEliminar(Button btnEliminar) {
		this.btnEliminar = btnEliminar;
	}

	public Button getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(Button btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public ServicioImagenologia getServicioImagenologia() {
		return servicioImagenologia;
	}

	public void setServicioImagenologia(
			ServicioImagenologia servicioImagenologia) {
		this.servicioImagenologia = servicioImagenologia;
	}

	public ServicioRadiografia getServicioRadiografia() {
		return servicioRadiografia;
	}

	public void setServicioRadiografia(ServicioRadiografia servicioRadiografia) {
		this.servicioRadiografia = servicioRadiografia;
	}

	public ServicioEndoscopia getServicioEndoscopia() {
		return servicioEndoscopia;
	}

	public void setServicioEndoscopia(ServicioEndoscopia servicioEndoscopia) {
		this.servicioEndoscopia = servicioEndoscopia;
	}

	public ServicioEcosonograma getServicioEcosonograma() {
		return servicioEcosonograma;
	}

	public void setServicioEcosonograma(
			ServicioEcosonograma servicioEcosonograma) {
		this.servicioEcosonograma = servicioEcosonograma;
	}

	public ServicioEcocardiograma getServicioEcocardiograma() {
		return servicioEcocardiograma;
	}

	public void setServicioEcocardiograma(
			ServicioEcocardiograma servicioEcocardiograma) {
		this.servicioEcocardiograma = servicioEcocardiograma;
	}

	public List<Imagenologia> getAll_imagenologia() {
		return all_imagenologia;
	}

	public void setAll_imagenologia(List<Imagenologia> all_imagenologia) {
		this.all_imagenologia = all_imagenologia;
	}

	public List<Radiografia> getRadiografias() {
		return radiografias;
	}

	public void setRadiografias(List<Radiografia> radiografias) {
		this.radiografias = radiografias;
	}

	public List<Endoscopia> getAll_endoscopia() {
		return all_endoscopia;
	}

	public void setAll_endoscopia(List<Endoscopia> all_endoscopia) {
		this.all_endoscopia = all_endoscopia;
	}

	public List<Ecosonograma> getAll_ecosonograma() {
		return all_ecosonograma;
	}

	public void setAll_ecosonograma(List<Ecosonograma> all_ecosonograma) {
		this.all_ecosonograma = all_ecosonograma;
	}

	public List<Ecocardiograma> getAll_ecocardiograma() {
		return all_ecocardiograma;
	}

	public void setAll_ecocardiograma(List<Ecocardiograma> all_ecocardiograma) {
		this.all_ecocardiograma = all_ecocardiograma;
	}

	public Imagenologia getImagenologia() {
		return imagenologia;
	}

	public void setImagenologia(Imagenologia imagenologia) {
		this.imagenologia = imagenologia;
	}

	public Radiografia getRadiografia() {
		return radiografia;
	}

	public void setRadiografia(Radiografia radiografia) {
		this.radiografia = radiografia;
	}

	public Endoscopia getEndoscopia() {
		return endoscopia;
	}

	public void setEndoscopia(Endoscopia endoscopia) {
		this.endoscopia = endoscopia;
	}

	public Ecosonograma getEcosonograma() {
		return ecosonograma;
	}

	public void setEcosonograma(Ecosonograma ecosonograma) {
		this.ecosonograma = ecosonograma;
	}

	public Ecocardiograma getEcocardiograma() {
		return ecocardiograma;
	}

	public void setEcocardiograma(Ecocardiograma ecocardiograma) {
		this.ecocardiograma = ecocardiograma;
	}

	public Listbox getListRadiografia() {
		return listRadiografia;
	}

	public void setListRadiografia(Listbox listRadiografia) {
		this.listRadiografia = listRadiografia;
	}

	public Listbox getListEcosonograma() {
		return listEcosonograma;
	}

	public void setListEcosonograma(Listbox listEcosonograma) {
		this.listEcosonograma = listEcosonograma;
	}

	public Listbox getListEcocardiograma() {
		return listEcocardiograma;
	}

	public void setListEcocardiograma(Listbox listEcocardiograma) {
		this.listEcocardiograma = listEcocardiograma;
	}

	/**
	 *
	 *
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		// TODO Auto-generated method stub
		winImagenologia.setAttribute(comp.getId() + "ctrl", this);
		imagenologia = new Imagenologia();
		servicioImagenologia = (ServicioImagenologia) SpringUtil
				.getBean("beanServicioImagenologia");
		servicioRadiografia = (ServicioRadiografia) SpringUtil
				.getBean("beanServicioRadiografia");
		radiografias = servicioRadiografia.buscarTodos();
		// servicioEndoscopia = (ServicioEndoscopia)
		// SpringUtil.getBean("beanServicioEndoscopia");
		// all_endoscopia = servicioEndoscopia.buscarTodos();
		// servicioEcosonograma = (ServicioEcosonograma)
		// SpringUtil.getBean("beanServicioEcosonograma");
		// all_ecosonograma = servicioEcosonograma.buscarTodos();
		// servicioEcocardiograma = (ServicioEcocardiograma)
		// SpringUtil.getBean("beanServicioEcocardiograma");
		// all_ecocardiograma = servicioEcocardiograma.buscarTodos();
		// if (!(arg.get("objeto") == null)) {
		// indexRadiografia =
		// radiografia.indexOf(imagenologia.getRadiografia());
		// }
		// servicioImagenologia = (ServicioImagenologia)
		// SpringUtil.getBean("beanServicioImagenologia");
		// servicioRadiografia = (ServicioRadiografia)
		// SpringUtil.getBean("beanServicioRadiografia");
	}

	public void onClick$btnCancelar() {
		winImagenologia.setAttribute("recargar", "B");
		this.winImagenologia.detach();

	}

	// public void onClick$btnGuardar() {
	// if (!servicioImagenologia.validacion(imagenologia.getNombre())) {
	// alert("El registro esta eliminado, sera reanudado");
	// servicioImagenologia.guardarImagenologia(imagenologia);
	// //raza.setRadio((Radio) listRadio.getSelectedItem().getValue());
	// winImagenologia.setAttribute("recargar", "A");
	// this.winImagenologia.detach();
	// } else {
	// imagenologia.setRadiografia((Radiografia)
	// listRadiografia.getSelectedItem().getValue());
	// alert("El registro se agrego satisfactoriamente");
	// servicioImagenologia.guardarImagenologia(imagenologia);
	// winImagenologia.setAttribute("recargar", "A");
	// this.winImagenologia.detach();
	// }
	// }

	public void onClose$winImagenologia() {
		winImagenologia.setAttribute("recargar", "B");
		this.winImagenologia.detach();
	}

}

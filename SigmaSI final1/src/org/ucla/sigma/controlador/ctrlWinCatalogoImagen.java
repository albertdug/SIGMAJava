/**
 * 
 */
package org.ucla.sigma.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.icontrolador.IUsarCatalogoImagen;
import org.ucla.sigma.modelo.Imagen;
import org.ucla.sigma.servicio.ServicioImagen;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
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
public class ctrlWinCatalogoImagen extends GenericForwardComposer {

	private Window winCatalogoImagen;
	private Listbox listImagen;
	private Button btnCancelar;
	private Button btnSeleccionar;
	private Imagen seleccion;
	private AImage tempImg;
	private Button btnBuscar;
	private Button btnVerTodos;
	private boolean buscando = false;
	private boolean verTodos = false;
	private Textbox txtNombreImagen;
	private ServicioImagen servicioImagen;
	private List<Imagen> imagens = new ArrayList<Imagen>();

	private IUsarCatalogoImagen iWinUseImage;


	public Imagen getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Imagen seleccion) {
		this.seleccion = seleccion;
	}

	public AImage getTempImg() {
		return tempImg;
	}

	public void setTempImg(AImage tempImg) {
		this.tempImg = tempImg;
	}

	public Window getWinCatalogoImagen() {
		return winCatalogoImagen;
	}

	public void setWinCatalogoImagen(Window winCatalogoImagen) {
		this.winCatalogoImagen = winCatalogoImagen;
	}

	public Listbox getListImagen() {
		return listImagen;
	}

	public void setListImagen(Listbox listImagen) {
		this.listImagen = listImagen;
	}

	public Button getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(Button btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public Button getBtnSeleccionar() {
		return btnSeleccionar;
	}

	public void setBtnSeleccionar(Button btnSeleccionar) {
		this.btnSeleccionar = btnSeleccionar;
	}

	public ServicioImagen getServicioImagen() {
		return servicioImagen;
	}

	public void setServicioImagen(ServicioImagen servicioImagen) {
		this.servicioImagen = servicioImagen;
	}

	public List<Imagen> getImagens() {
		return imagens;
	}

	public void setImagens(List<Imagen> imagens) {
		this.imagens = imagens;
	}

	public IUsarCatalogoImagen getiWinUseImage() {
		return iWinUseImage;
	}

	public void setiWinUseImage(IUsarCatalogoImagen iWinUseImage) {
		this.iWinUseImage = iWinUseImage;
	}
	
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombreImagen.setFocus(true);
		};
	};

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winCatalogoImagen.setAttribute(comp.getId() + "ctrl", this);
		servicioImagen = (ServicioImagen) SpringUtil
				.getBean("beanServicioImagen");
		seleccion = new Imagen();
		imagens = servicioImagen.buscarTodos('A');
		btnSeleccionar.setDisabled(true);
		iWinUseImage = (IUsarCatalogoImagen) arg.get("beforeCtrl");

	}

	public void onClick$btnSeleccionar() {
		try {
			tempImg = new AImage(null, seleccion.getBytes());
		} catch (IOException e) {
			MensajeEmergente.mostrar("ERRIMAGE");
			e.printStackTrace();
		}
		iWinUseImage.setImagenToModel(seleccion);
		iWinUseImage.setAImageToImageContent(tempImg);
		this.winCatalogoImagen.detach();
	}

	public void onSelect$listImagen() {
		btnSeleccionar.setDisabled(false);
	}

	public void onClick$btnCancelar() {
		this.winCatalogoImagen.detach();
	}
	
	public void apagarBotones() {
		txtNombreImagen.setFocus(true);
		listImagen.clearSelection();
		listImagen.selectItem(null);
		btnSeleccionar.setDisabled(true);
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			imagens = servicioImagen.buscarTodos('A');
		else if (buscando)
			imagens = servicioImagen.buscarCoincidencias(
					txtNombreImagen.getValue(), 'A');
		else
			imagens.clear();

		listImagen.setModel(new BindingListModelList(imagens, false));
	}
	
	public void onClick$btnVerTodos() {
		imagens = servicioImagen.buscarTodos('A');
		listImagen.setModel(new BindingListModelList(imagens, false));
		buscando = false;
		verTodos = true;
		txtNombreImagen.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtNombreImagen.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			imagens = servicioImagen.buscarCoincidencias(
					txtNombreImagen.getValue(), 'A');
			if (imagens.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listImagen.setModel(new BindingListModelList(imagens, false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}
}

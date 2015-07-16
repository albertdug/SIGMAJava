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
import org.ucla.sigma.icontrolador.IUsarCatalogoImagen;
import org.ucla.sigma.modelo.Difusion;
import org.ucla.sigma.modelo.EntidadExterna;
import org.ucla.sigma.modelo.Especie;
import org.ucla.sigma.modelo.Imagen;
import org.ucla.sigma.servicio.ServicioDifusion;
import org.ucla.sigma.servicio.ServicioEntidadExterna;
import org.ucla.sigma.servicio.ServicioEspecie;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author promo49
 * 
 */
public class ctrlWinEditDifusion extends GenericForwardComposer implements IUsarCatalogoImagen {

	private Window winEditDifusion;
	private Button btnCancelar;
	private Button btnGuardar;
	private Image image;
	private Button btnImg;
	private Textbox txtEnlace;
	private Textbox txtNombre;
	private Imagen imagen;
	private AImage aImage;

	// ----------------------------------------------------------------------------------------------------

	private String catalogoImagen = "/sigma/vistas/portal/imagen/catalogoImagen.zul";
	private String editDifusion = "/sigma/vistas/maestros/difusion/editDifusion.zul";
	private ServicioDifusion servicioDifusion;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtNombre.setFocus(true);
		};
	};

	// ----------------------------------------------------------------------------------------------------

	private ctrlWinDifusion ctrlWinDifusion;
	private Difusion difusion;

	// ----------------------------------------------------------------------------------------------------

	
	
	public Window getWinEditDifusion() {
		return winEditDifusion;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}

	public AImage getaImage() {
		return aImage;
	}

	public void setaImage(AImage aImage) {
		this.aImage = aImage;
	}

	public String getCatalogoImagen() {
		return catalogoImagen;
	}

	public void setCatalogoImagen(String catalogoImagen) {
		this.catalogoImagen = catalogoImagen;
	}

	public void setWinEditDifusion(Window winEditDifusion) {
		this.winEditDifusion = winEditDifusion;
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

	public Button getBtnImg() {
		return btnImg;
	}

	public void setBtnImg(Button btnImg) {
		this.btnImg = btnImg;
	}

	public Textbox getTxtEnlace() {
		return txtEnlace;
	}

	public void setTxtEnlace(Textbox txtEnlace) {
		this.txtEnlace = txtEnlace;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	// ----------------------------------------------------------------------------------------------------

	public String getEditDifusion() {
		return editDifusion;
	}

	public void setEditDifusion(String editDifusion) {
		this.editDifusion = editDifusion;
	}

	public ServicioDifusion getServicioDifusion() {
		return servicioDifusion;
	}

	public void setServicioDifusion(ServicioDifusion servicioDifusion) {
		this.servicioDifusion = servicioDifusion;
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

	public ctrlWinDifusion getCtrlWinDifusion() {
		return ctrlWinDifusion;
	}

	public void setCtrlWinDifusion(ctrlWinDifusion ctrlWinDifusion) {
		this.ctrlWinDifusion = ctrlWinDifusion;
	}

	public Difusion getDifusion() {
		return difusion;
	}

	public void setDifusion(Difusion difusion) {
		this.difusion = difusion;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditDifusion.setAttribute(comp.getId() + "ctrl", this);
		servicioDifusion = (ServicioDifusion) SpringUtil
				.getBean("beanServicioDifusion");
		difusion = new Difusion();
		ctrlWinDifusion = (ctrlWinDifusion) arg.get("ctrlWinDifusion");
		if (!(arg.get("objeto") == null)) {
			difusion = (Difusion) arg.get("objeto");
			if (difusion.getImagen() != null) {
				aImage = new AImage(null, difusion.getImagen().getBytes());
				image.setContent(aImage);
			}
		}
	}

	public void onClick$btnCancelar() {
		ctrlWinDifusion.recargar();
		ctrlWinDifusion.apagarBotones();
		this.winEditDifusion.detach();
	}

	public void onClick$btnGuardar() {

		if (txtNombre.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nNombre",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtNombre.setFocus(true);
						}
					});
		} else {
			Difusion difusionTemp = servicioDifusion.buscarUno(difusion
					.getNombre());
			if (difusionTemp != null) {
				difusion.setId(difusionTemp.getId());
			}

			try {
				servicioDifusion.guardarDifusion(difusion);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlWinDifusion.recargar();
						ctrlWinDifusion.apagarBotones();
						winEditDifusion.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}
		}
	}

	public void onClose$winEditDifusion() {
		ctrlWinDifusion.apagarBotones();
		this.winEditDifusion.detach();
	}

	public void onClick$btnImg() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("beforeCtrl", this);
		Window win = (Window) Executions.createComponents(catalogoImagen, null,
				parametros);
		win.doHighlighted();
	}
	
	@Override
	public void setImagenToModel(Imagen imagen) {
		difusion.setImagen(imagen);		
	}

	@Override
	public void setAImageToImageContent(AImage aImage) {
		image.setContent(aImage);
	}
	
	@Override
	public Image getTagImage() {
		return image;
	}


}

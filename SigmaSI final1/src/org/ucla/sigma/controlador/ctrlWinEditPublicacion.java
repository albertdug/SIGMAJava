/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.components.HelperString;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.componentszk.SessionAdministrator;
import org.ucla.sigma.icontrolador.IUsarCatalogoImagen;
import org.ucla.sigma.modelo.Adjunto;
import org.ucla.sigma.modelo.GeneroPublicacion;
import org.ucla.sigma.modelo.Imagen;
import org.ucla.sigma.modelo.Publicacion;
import org.ucla.sigma.modelo.TipoPublicacion;
import org.ucla.sigma.servicio.ServicioGeneroPublicacion;
import org.ucla.sigma.servicio.ServicioPublicacion;
import org.ucla.sigma.servicio.ServicioTipoPublicacion;
import org.zkoss.image.AImage;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author Albert
 * 
 */
public class ctrlWinEditPublicacion extends GenericForwardComposer implements
		IUsarCatalogoImagen {

	private Window winEditPublicacion;
	private Button btnCancelar;
	private Button btnGuardar;
	private Image imgImagen;
	private Imagen imagen;
	private AImage tmpImg;
	private Button btnImg;
	private Listbox listTipos;
	private Listbox listGeneros;
	private Listbox listAdjuntos;
	private Textbox txtTexto;
	private Textbox txtTitulo;

	// ----------------------------------------------------------------------------------------------------

	private String catalogoImagen = "/sigma/vistas/portal/imagen/catalogoImagen.zul";
	private String catalogoAdjunto = "/sigma/vistas/portal/adjunto/catalogoAdjunto.zul";
	private ServicioGeneroPublicacion servicioGeneroPublicacion;
	private ServicioTipoPublicacion servicioTipoPublicacion;
	private ServicioPublicacion servicioPublicacion;
	private ctrlWinPublicacion ctrlwinPublicacion;
	private int indexPublicaciones = -1;
	private int indexGeneroPublicacion = -1;
	private int indexTipoPublicacion = -1;

	// ----------------------------------------------------------------------------------------------------

	private List<GeneroPublicacion> generos = new ArrayList<GeneroPublicacion>();
	private List<Adjunto> adjuntos = new ArrayList<Adjunto>();
	private List<TipoPublicacion> tipos = new ArrayList<TipoPublicacion>();;
	private Publicacion publicacion;
	private List<Adjunto> lists;
	private Set<Adjunto> sets;

	
	public Listbox getListAdjuntos() {
		return listAdjuntos;
	}

	public void setListAdjuntos(Listbox listAdjuntos) {
		this.listAdjuntos = listAdjuntos;
	}

	public List<Adjunto> getAdjuntos() {
		return adjuntos;
	}

	public void setAdjuntos(List<Adjunto> adjuntos) {
		this.adjuntos = adjuntos;
	}

	public Window getWinEditPublicacion() {
		return winEditPublicacion;
	}

	public void setWinEditPublicacion(Window winEditPublicacion) {
		this.winEditPublicacion = winEditPublicacion;
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

	public Image getImgImagen() {
		return imgImagen;
	}

	public void setImgImagen(Image imgImagen) {
		this.imgImagen = imgImagen;
	}

	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}

	public AImage getTmpImg() {
		return tmpImg;
	}

	public void setTmpImg(AImage tmpImg) {
		this.tmpImg = tmpImg;
	}

	public Button getBtnImg() {
		return btnImg;
	}

	public void setBtnImg(Button btnImg) {
		this.btnImg = btnImg;
	}

	public Listbox getListTipos() {
		return listTipos;
	}

	public void setListTipos(Listbox listTipos) {
		this.listTipos = listTipos;
	}

	public Listbox getListGeneros() {
		return listGeneros;
	}

	public void setListGeneros(Listbox listGeneros) {
		this.listGeneros = listGeneros;
	}

	public Textbox getTxtTexto() {
		return txtTexto;
	}

	public void setTxtTexto(Textbox txtTexto) {
		this.txtTexto = txtTexto;
	}

	public Textbox getTxtTitulo() {
		return txtTitulo;
	}

	public void setTxtTitulo(Textbox txtTitulo) {
		this.txtTitulo = txtTitulo;
	}

	public String getCatalogoImagen() {
		return catalogoImagen;
	}

	public void setCatalogoImagen(String catalogoImagen) {
		this.catalogoImagen = catalogoImagen;
	}

	public ServicioGeneroPublicacion getServicioGeneroPublicacion() {
		return servicioGeneroPublicacion;
	}

	public void setServicioGeneroPublicacion(
			ServicioGeneroPublicacion servicioGeneroPublicacion) {
		this.servicioGeneroPublicacion = servicioGeneroPublicacion;
	}

	public ServicioTipoPublicacion getServicioTipoPublicacion() {
		return servicioTipoPublicacion;
	}

	public void setServicioTipoPublicacion(
			ServicioTipoPublicacion servicioTipoPublicacion) {
		this.servicioTipoPublicacion = servicioTipoPublicacion;
	}

	public ServicioPublicacion getServicioPublicacion() {
		return servicioPublicacion;
	}

	public void setServicioPublicacion(ServicioPublicacion servicioPublicacion) {
		this.servicioPublicacion = servicioPublicacion;
	}

	public ctrlWinPublicacion getCtrlwinPublicacion() {
		return ctrlwinPublicacion;
	}

	public void setCtrlwinPublicacion(ctrlWinPublicacion ctrlwinPublicacion) {
		this.ctrlwinPublicacion = ctrlwinPublicacion;
	}

	public int getIndexPublicaciones() {
		return indexPublicaciones;
	}

	public void setIndexPublicaciones(int indexPublicaciones) {
		this.indexPublicaciones = indexPublicaciones;
	}

	public int getIndexGeneroPublicacion() {
		return indexGeneroPublicacion;
	}

	public void setIndexGeneroPublicacion(int indexGeneroPublicacion) {
		this.indexGeneroPublicacion = indexGeneroPublicacion;
	}

	public int getIndexTipoPublicacion() {
		return indexTipoPublicacion;
	}

	public void setIndexTipoPublicacion(int indexTipoPublicacion) {
		this.indexTipoPublicacion = indexTipoPublicacion;
	}

	public List<GeneroPublicacion> getGeneros() {
		return generos;
	}

	public void setGeneros(List<GeneroPublicacion> generos) {
		this.generos = generos;
	}

	public List<TipoPublicacion> getTipos() {
		return tipos;
	}

	public void setTipos(List<TipoPublicacion> tipos) {
		this.tipos = tipos;
	}

	public Publicacion getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
	}

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditPublicacion.setAttribute(comp.getId() + "ctrl", this);
		servicioPublicacion = (ServicioPublicacion) SpringUtil
				.getBean("beanServicioPublicacion");
		servicioTipoPublicacion = (ServicioTipoPublicacion) SpringUtil
				.getBean("beanServicioTipoPublicacion");
		servicioGeneroPublicacion = (ServicioGeneroPublicacion) SpringUtil
				.getBean("beanServicioGeneroPublicacion");

		publicacion = new Publicacion();
		imagen = new Imagen();
		tipos = servicioTipoPublicacion.buscarTodos();
		generos = servicioGeneroPublicacion.buscarTodos();
		ctrlwinPublicacion = (ctrlWinPublicacion) arg.get("ctrlWinPublicacion");
		if (!(arg.get("objeto") == null)) {
			publicacion = (Publicacion) arg.get("objeto");
			indexGeneroPublicacion = generos.indexOf(publicacion
					.getGeneroPublicacion());
			indexTipoPublicacion = tipos.indexOf(publicacion
					.getTipoPublicacion());
			sets = publicacion.getAdjuntos();
			for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
				Adjunto adj = (Adjunto) iterator.next();
				adjuntos.add(adj);
			}

			// NUEVA LINEA
			if (publicacion.getImagen() != null) {
				tmpImg = new AImage(null, publicacion.getImagen().getBytes());
				imgImagen.setContent(tmpImg);
			}
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinPublicacion.recargar();
		ctrlwinPublicacion.apagarBotones();
		this.winEditPublicacion.detach();
	}

	public void onClick$btnGuardar() {
		// ORDEN MODIFICADO
		
		Publicacion tempp = servicioPublicacion.buscarUnoPorUri(HelperString
				.urlTitle(HelperString.urlTitle(publicacion.getTitulo())), 'A');

		if (txtTitulo.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nTitulo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtTitulo.setFocus(true);
						}
					});
		} else if (tempp != null && tempp.getId()!=publicacion.getId()) {

			MensajeEmergente.mostrar("TITLEEXIST", new MensajeListener() {
				@Override
				public void alDestruir() {
					txtTitulo.setFocus(true);
				}
			});

		} else if (txtTexto.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nTexto",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtTexto.setFocus(true);
						}
					});

		} else if (listGeneros.getSelectedIndex() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nGenero Publicacion",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listGeneros.setFocus(true);
						}
					});
		} else if (listTipos.getSelectedIndex() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nTipo Publicacion",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listTipos.setFocus(true);
						}
					});
		} else {
			
			if (listAdjuntos.getItems() != null) {
				publicacion.getAdjuntos().addAll(adjuntos);
			}

			publicacion.setGeneroPublicacion((GeneroPublicacion) listGeneros
					.getSelectedItem().getValue());
			publicacion.setUri(HelperString.urlTitle(publicacion.getTitulo()));
			publicacion.setCreacion(HelperDate.now());
			if(publicacion.getUsuario()==null){
				publicacion.setUsuario(SessionAdministrator.getLoggedUsuario());
			}
			
			try {
				publicacion.setTipoPublicacion((TipoPublicacion) listTipos
						.getSelectedItem().getValue());
				servicioPublicacion.guardarPublicacion(publicacion);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinPublicacion.recargar();
						ctrlwinPublicacion.apagarBotones();
						winEditPublicacion.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}
		}
	}

	public void onClose$winEditPublicacion() {
		ctrlwinPublicacion.apagarBotones();
		this.winEditPublicacion.detach();
	}

	public void onAfterRender$listGeneros() {
		listGeneros.setSelectedIndex(indexGeneroPublicacion);
	}

	public void onAfterRender$listTipos() {
		listTipos.setSelectedIndex(indexTipoPublicacion);
	}

	public void onClick$btnImg() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("beforeCtrl", this);
		Window win = (Window) Executions.createComponents(catalogoImagen, null,
				parametros);
		win.doHighlighted();
	}
	
	public void onClick$btnAdj() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinEditPublicacion", this);
		Window win = (Window) Executions.createComponents(catalogoAdjunto, null,
				parametros);
		win.doHighlighted();
	}
	
	public void recargar() {
		listAdjuntos.setModel(new BindingListModelList(adjuntos, false));
	}
	
	public void limpiarLista() {
		adjuntos = new ArrayList<Adjunto>();
	}

	@Override
	public void setImagenToModel(Imagen imagen) {
		publicacion.setImagen(imagen);
	}

	@Override
	public void setAImageToImageContent(AImage aImage) {
		imgImagen.setContent(aImage);
	}

	@Override
	public Image getTagImage() {
		return imgImagen;
	}

}

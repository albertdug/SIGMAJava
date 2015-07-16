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
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.EntidadExterna;
import org.ucla.sigma.modelo.Estado;
import org.ucla.sigma.modelo.Imagen;
import org.ucla.sigma.modelo.TipoEntidadExterna;
import org.ucla.sigma.servicio.ServicioCiudad;
import org.ucla.sigma.servicio.ServicioEntidadExterna;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioTipoEntidadExterna;
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
 * @author rafael
 * 
 */
public class ctrlWinEditEntidadExterna extends GenericForwardComposer implements
		IUsarCatalogoImagen {

	private Window winEditEntidadExterna;
	private Button btnCancelar;
	private Button btnGuardar;
	private Image image;
	private Imagen imagen;
	private AImage aImage;
	private Button btnImg;
	private Textbox txtNit;
	private Textbox txtRif;
	private Textbox txtCorreo;
	private Textbox txtWeb;
	private Textbox txtFax;
	private Textbox txtTelefono;
	private Textbox txtDireccion;
	private Listbox listCiudads;
	private Listbox listEstados;
	private Textbox txtDescripcion;
	private Listbox listEntidadExs;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private String catalogoImagen = "/sigma/vistas/portal/imagen/catalogoImagen.zul";
	private ServicioCiudad servicioCiudad;
	private ServicioEstado servicioEstado;
	private ServicioTipoEntidadExterna servicioTipoEntidadExterna;
	private ServicioEntidadExterna servicioEntidadExterna;
	private ctrlWinEntidadExterna ctrlwinEntidadExterna;
	private int indexEntidadExterna = -1;
	private int indexCiudad = -1;
	private int indexEstado = -1;

	// ----------------------------------------------------------------------------------------------------

	private List<Ciudad> ciudads = new ArrayList<Ciudad>();
	private List<Estado> estados = new ArrayList<Estado>();
	private List<TipoEntidadExterna> tipoEntidadExs = new ArrayList<TipoEntidadExterna>();
	private EntidadExterna entidadExterna;

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

	public int getIndexCiudad() {
		return indexCiudad;
	}

	public void setIndexCiudad(int indexCiudad) {
		this.indexCiudad = indexCiudad;
	}

	public int getIndexEstado() {
		return indexEstado;
	}

	public void setIndexEstado(int indexEstado) {
		this.indexEstado = indexEstado;
	}

	public Textbox getTxtFax() {
		return txtFax;
	}

	public void setTxtFax(Textbox txtFax) {
		this.txtFax = txtFax;
	}

	public Window getWinEditEntidadExterna() {
		return winEditEntidadExterna;
	}

	public void setWinEditEntidadExterna(Window winEditEntidadExterna) {
		this.winEditEntidadExterna = winEditEntidadExterna;
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

	public Textbox getTxtNit() {
		return txtNit;
	}

	public void setTxtNit(Textbox txtNit) {
		this.txtNit = txtNit;
	}

	public Textbox getTxtRif() {
		return txtRif;
	}

	public void setTxtRif(Textbox txtRif) {
		this.txtRif = txtRif;
	}

	public Textbox getTxtCorreo() {
		return txtCorreo;
	}

	public void setTxtCorreo(Textbox txtCorreo) {
		this.txtCorreo = txtCorreo;
	}

	public Textbox getTxtWeb() {
		return txtWeb;
	}

	public void setTxtWeb(Textbox txtWeb) {
		this.txtWeb = txtWeb;
	}

	public Textbox getTxtTelefono() {
		return txtTelefono;
	}

	public void setTxtTelefono(Textbox txtTelefono) {
		this.txtTelefono = txtTelefono;
	}

	public Textbox getTxtDireccion() {
		return txtDireccion;
	}

	public void setTxtDireccion(Textbox txtDireccion) {
		this.txtDireccion = txtDireccion;
	}

	public Listbox getListCiudads() {
		return listCiudads;
	}

	public void setListCiudads(Listbox listCiudads) {
		this.listCiudads = listCiudads;
	}

	public Listbox getListEstados() {
		return listEstados;
	}

	public void setListEstados(Listbox listEstados) {
		this.listEstados = listEstados;
	}

	public Textbox getTxtDescripcion() {
		return txtDescripcion;
	}

	public void setTxtDescripcion(Textbox txtDescripcion) {
		this.txtDescripcion = txtDescripcion;
	}

	public Listbox getListEntidadExs() {
		return listEntidadExs;
	}

	public void setListEntidadExs(Listbox listEntidadExs) {
		this.listEntidadExs = listEntidadExs;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioCiudad getServicioCiudad() {
		return servicioCiudad;
	}

	public void setServicioCiudad(ServicioCiudad servicioCiudad) {
		this.servicioCiudad = servicioCiudad;
	}

	public ServicioEstado getServicioEstado() {
		return servicioEstado;
	}

	public void setServicioEstado(ServicioEstado servicioEstado) {
		this.servicioEstado = servicioEstado;
	}

	public ServicioTipoEntidadExterna getServicioTipoEntidadExterna() {
		return servicioTipoEntidadExterna;
	}

	public void setServicioTipoEntidadExterna(
			ServicioTipoEntidadExterna servicioTipoEntidadExterna) {
		this.servicioTipoEntidadExterna = servicioTipoEntidadExterna;
	}

	public ServicioEntidadExterna getServicioEntidadExterna() {
		return servicioEntidadExterna;
	}

	public void setServicioEntidadExterna(
			ServicioEntidadExterna servicioEntidadExterna) {
		this.servicioEntidadExterna = servicioEntidadExterna;
	}

	public ctrlWinEntidadExterna getCtrlwinEntidadExterna() {
		return ctrlwinEntidadExterna;
	}

	public void setCtrlwinEntidadExterna(
			ctrlWinEntidadExterna ctrlwinEntidadExterna) {
		this.ctrlwinEntidadExterna = ctrlwinEntidadExterna;
	}

	public int getIndexEntidadExterna() {
		return indexEntidadExterna;
	}

	public void setIndexEntidadExterna(int indexEntidadExterna) {
		this.indexEntidadExterna = indexEntidadExterna;
	}

	public List<Ciudad> getCiudads() {
		return ciudads;
	}

	public void setCiudads(List<Ciudad> ciudads) {
		this.ciudads = ciudads;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public List<TipoEntidadExterna> getTipoEntidadExs() {
		return tipoEntidadExs;
	}

	public void setTipoEntidadExs(List<TipoEntidadExterna> tipoEntidadExs) {
		this.tipoEntidadExs = tipoEntidadExs;
	}

	public EntidadExterna getEntidadExterna() {
		return entidadExterna;
	}

	public void setEntidadExterna(EntidadExterna entidadExterna) {
		this.entidadExterna = entidadExterna;
	}

	// ----------------------------------------------------------------------------------------------------

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditEntidadExterna.setAttribute(comp.getId() + "ctrl", this);
		servicioEntidadExterna = (ServicioEntidadExterna) SpringUtil
				.getBean("beanServicioEntidadExterna");
		servicioTipoEntidadExterna = (ServicioTipoEntidadExterna) SpringUtil
				.getBean("beanServicioTipoEntidadExterna");
		servicioCiudad = (ServicioCiudad) SpringUtil
				.getBean("beanServicioCiudad");
		servicioEstado = (ServicioEstado) SpringUtil
				.getBean("beanServicioEstado");
		entidadExterna = new EntidadExterna();
		imagen = new Imagen();
		tipoEntidadExs = servicioTipoEntidadExterna.buscarTodos();
		estados = servicioEstado.buscarTodos();
		ciudads = servicioCiudad.buscarTodos();
		listCiudads.setDisabled(true);
		ctrlwinEntidadExterna = (ctrlWinEntidadExterna) arg
				.get("ctrlWinEntidadExterna");
		if (!(arg.get("objeto") == null)) {
			entidadExterna = (EntidadExterna) arg.get("objeto");
			indexEntidadExterna = tipoEntidadExs.indexOf(entidadExterna
					.getTipoEntidadExterna());

			indexEstado = estados.indexOf(entidadExterna.getCiudad()
					.getEstado());
			// NUEVA LINEA
			ciudads = servicioCiudad.buscarEstadosAsociados(entidadExterna
					.getCiudad().getEstado(), 'A');
			listCiudads.setDisabled(false);
			indexCiudad = ciudads.indexOf(entidadExterna.getCiudad());
			// NUEVA LINEA
			if (entidadExterna.getImagen() != null) {
				aImage = new AImage(null, entidadExterna.getImagen().getBytes());
				image.setContent(aImage);
			}
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinEntidadExterna.recargar();
		ctrlwinEntidadExterna.apagarBotones();
		this.winEditEntidadExterna.detach();
	}

	public void onClick$btnGuardar() {
		// ORDEN MODIFICADO
		if (txtNombre.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nNombre",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtNombre.setFocus(true);
						}
					});
		} else if (listEntidadExs.getSelectedIndex() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nTipo Entidad Externa",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listEntidadExs.setFocus(true);
						}
					});
		} else if (txtDescripcion.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nDescripcion",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtDescripcion.setFocus(true);
						}
					});
		} else if (txtDireccion.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nDireccion",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtDireccion.setFocus(true);
						}
					});
		} else if (listEstados.getSelectedIndex() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nEstado",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listEstados.setFocus(true);
						}
					});
		} else if (listCiudads.getSelectedIndex() < 0) {
			MensajeEmergente.mostrar("NOEMPTY", "\nCiudad",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							listCiudads.setFocus(true);
						}
					});
		} else if (txtTelefono.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nTelefono",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtTelefono.setFocus(true);
						}
					});
		} /*else if (txtFax.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nFax", new MensajeListener() {
				@Override
				public void alDestruir() {
					txtFax.setFocus(true);
				}
			});
		} else if (txtWeb.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nWeb", new MensajeListener() {
				@Override
				public void alDestruir() {
					txtWeb.setFocus(true);
				}
			});
		} else if (txtCorreo.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nCorreo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtCorreo.setFocus(true);
						}
					});
		}*/ else if (txtRif.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nRif", new MensajeListener() {
				@Override
				public void alDestruir() {
					txtRif.setFocus(true);
				}
			});
		} /*else if (txtNit.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nNit", new MensajeListener() {
				@Override
				public void alDestruir() {
					txtNit.setFocus(true);
				}
			});
		}*/ else {
			EntidadExterna entidadExternaTemp = servicioEntidadExterna
					.buscarUno(entidadExterna.getNombre());
			if (entidadExternaTemp != null) {
				entidadExterna.setId(entidadExternaTemp.getId());
			}
			entidadExterna
					.setTipoEntidadExterna((TipoEntidadExterna) listEntidadExs
							.getSelectedItem().getValue());
			try {
				entidadExterna.setCiudad((Ciudad) listCiudads.getSelectedItem()
						.getValue());
				servicioEntidadExterna.guardarEntidadExterna(entidadExterna);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinEntidadExterna.recargar();
						ctrlwinEntidadExterna.apagarBotones();
						winEditEntidadExterna.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}
		}
	}

	public void onClose$winEditEntidadExterna() {
		ctrlwinEntidadExterna.apagarBotones();
		this.winEditEntidadExterna.detach();
	}

	public void onAfterRender$listEntidadExs() {
		listEntidadExs.setSelectedIndex(indexEntidadExterna);
	}

	public void onAfterRender$listEstados() {
		listEstados.setSelectedIndex(indexEstado);
		listCiudads.setSelectedIndex(indexCiudad);
	}

	public void onSelect$listEstados() {
		ciudads = servicioCiudad.buscarEstadosAsociados((Estado) listEstados
				.getSelectedItem().getValue(), 'A');
		listCiudads.setModel(new BindingListModelList(ciudads, false));
		// NUEVA LINEA
		indexCiudad = -1;
		// NUEVA LINEA
		listCiudads.setDisabled(false);
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
		entidadExterna.setImagen(imagen);
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

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
import org.ucla.sigma.modelo.Estado;
import org.ucla.sigma.modelo.Hospital;
import org.ucla.sigma.modelo.Imagen;
import org.ucla.sigma.servicio.ServicioCiudad;
import org.ucla.sigma.servicio.ServicioEstado;
import org.ucla.sigma.servicio.ServicioHospital;
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
public class ctrlWinEditHospital extends GenericForwardComposer implements
		IUsarCatalogoImagen {

	private Window winEditHospital;
	private Button btnCancelar;
	private Button btnGuardar;
	private Imagen imagen;
	private AImage aImage;
	private Image image;
	private Button btnImg;
	private Textbox txtSlogan;
	private Textbox txtObjetivo;
	private Textbox txtVision;
	private Textbox txtMision;
	private Textbox txtHorario;
	private Textbox txtRif;
	private Textbox txtNit;
	private Textbox txtFaxB;
	private Textbox txtFaxA;
	private Textbox txtCorreoB;
	private Textbox txtCorreoA;
	private Textbox txtTelefonoB;
	private Textbox txtTelefonoA;
	private Textbox txtDireccion;
	private Listbox listCiudads;
	private Listbox listEstados;
	private Textbox txtDescripcion;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private String catalogoImagen = "/sigma/vistas/portal/imagen/catalogoImagen.zul";
	private ServicioCiudad servicioCiudad;
	private ServicioEstado servicioEstado;
	private ServicioHospital servicioHospital;
	private ctrlWinHospital ctrlwinHospital;
	private int indexCiudad = -1;
	private int indexEstado = -1;

	// ----------------------------------------------------------------------------------------------------

	private List<Ciudad> ciudads = new ArrayList<Ciudad>();
	private List<Estado> estados = new ArrayList<Estado>();
	private Hospital hospital;

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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Window getWinEditHospital() {
		return winEditHospital;
	}

	public void setWinEditHospital(Window winEditHospital) {
		this.winEditHospital = winEditHospital;
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

	public Textbox getTxtSlogan() {
		return txtSlogan;
	}

	public void setTxtSlogan(Textbox txtSlogan) {
		this.txtSlogan = txtSlogan;
	}

	public Textbox getTxtObjetivo() {
		return txtObjetivo;
	}

	public void setTxtObjetivo(Textbox txtObjetivo) {
		this.txtObjetivo = txtObjetivo;
	}

	public Textbox getTxtVision() {
		return txtVision;
	}

	public void setTxtVision(Textbox txtVision) {
		this.txtVision = txtVision;
	}

	public Textbox getTxtMision() {
		return txtMision;
	}

	public void setTxtMision(Textbox txtMision) {
		this.txtMision = txtMision;
	}

	public Textbox getTxtHorario() {
		return txtHorario;
	}

	public void setTxtHorario(Textbox txtHorario) {
		this.txtHorario = txtHorario;
	}

	public Textbox getTxtRif() {
		return txtRif;
	}

	public void setTxtRif(Textbox txtRif) {
		this.txtRif = txtRif;
	}

	public Textbox getTxtNit() {
		return txtNit;
	}

	public void setTxtNit(Textbox txtNit) {
		this.txtNit = txtNit;
	}

	public Textbox getTxtFaxB() {
		return txtFaxB;
	}

	public void setTxtFaxB(Textbox txtFaxB) {
		this.txtFaxB = txtFaxB;
	}

	public Textbox getTxtFaxA() {
		return txtFaxA;
	}

	public void setTxtFaxA(Textbox txtFaxA) {
		this.txtFaxA = txtFaxA;
	}

	public Textbox getTxtCorreoB() {
		return txtCorreoB;
	}

	public void setTxtCorreoB(Textbox txtCorreoB) {
		this.txtCorreoB = txtCorreoB;
	}

	public Textbox getTxtCorreoA() {
		return txtCorreoA;
	}

	public void setTxtCorreoA(Textbox txtCorreoA) {
		this.txtCorreoA = txtCorreoA;
	}

	public Textbox getTxtTelefonoB() {
		return txtTelefonoB;
	}

	public void setTxtTelefonoB(Textbox txtTelefonoB) {
		this.txtTelefonoB = txtTelefonoB;
	}

	public Textbox getTxtTelefonoA() {
		return txtTelefonoA;
	}

	public void setTxtTelefonoA(Textbox txtTelefonoA) {
		this.txtTelefonoA = txtTelefonoA;
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

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public String getCatalogoImagen() {
		return catalogoImagen;
	}

	public void setCatalogoImagen(String catalogoImagen) {
		this.catalogoImagen = catalogoImagen;
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

	public ServicioHospital getServicioHospital() {
		return servicioHospital;
	}

	public void setServicioHospital(ServicioHospital servicioHospital) {
		this.servicioHospital = servicioHospital;
	}

	public ctrlWinHospital getCtrlwinHospital() {
		return ctrlwinHospital;
	}

	public void setCtrlwinHospital(ctrlWinHospital ctrlwinHospital) {
		this.ctrlwinHospital = ctrlwinHospital;
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

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	// ----------------------------------------------------------------------------------------------------

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditHospital.setAttribute(comp.getId() + "ctrl", this);
		servicioHospital = (ServicioHospital) SpringUtil
				.getBean("beanServicioHospital");
		servicioCiudad = (ServicioCiudad) SpringUtil
				.getBean("beanServicioCiudad");
		servicioEstado = (ServicioEstado) SpringUtil
				.getBean("beanServicioEstado");
		hospital = new Hospital();
		imagen = new Imagen();
		estados = servicioEstado.buscarTodos();
		ciudads = servicioCiudad.buscarTodos();
		listCiudads.setDisabled(true);
		ctrlwinHospital = (ctrlWinHospital) arg.get("ctrlWinHospital");
		if (!(arg.get("objeto") == null)) {
			hospital = (Hospital) arg.get("objeto");
			indexCiudad = ciudads.indexOf(hospital.getCiudad());
			indexEstado = estados.indexOf(hospital.getCiudad().getEstado());
			// NUEVA LINEA
			ciudads = servicioCiudad.buscarEstadosAsociados(hospital
					.getCiudad().getEstado(), 'A');
			listCiudads.setDisabled(false);
			indexCiudad = ciudads.indexOf(hospital.getCiudad());
			// NUEVA LINEA
			if (hospital.getImagen() != null) {
				aImage = new AImage(null, hospital.getImagen().getBytes());
				image.setContent(aImage);
			}
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinHospital.recargar();
		this.winEditHospital.detach();
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
		} else if (txtDescripcion.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nDescripcion",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtDescripcion.setFocus(true);
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
		} else if (txtDireccion.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nDireccion",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtDireccion.setFocus(true);
						}
					});
		} else if (txtTelefonoA.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nTelefono",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtTelefonoA.setFocus(true);
						}
					});
		} /*
		 * else if (txtTelefonoB.getValue().trim().equalsIgnoreCase("")) {
		 * MensajeEmergente.mostrar("NOEMPTY", "\nTelefono", new
		 * MensajeListener() {
		 * 
		 * @Override public void alDestruir() { txtTelefonoB.setFocus(true); }
		 * }); }
		 */else if (txtCorreoA.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nCorreo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtCorreoA.setFocus(true);
						}
					});
		} /*
		 * else if (txtCorreoB.getValue().trim().equalsIgnoreCase("")) {
		 * MensajeEmergente.mostrar("NOEMPTY", "\nCorreo", new MensajeListener()
		 * {
		 * 
		 * @Override public void alDestruir() { txtCorreoB.setFocus(true); } });
		 * } else if (txtFaxA.getValue().trim().equalsIgnoreCase("")) {
		 * MensajeEmergente.mostrar("NOEMPTY", "\nFax", new MensajeListener() {
		 * 
		 * @Override public void alDestruir() { txtFaxA.setFocus(true); } }); }
		 * else if (txtFaxB.getValue().trim().equalsIgnoreCase("")) {
		 * MensajeEmergente.mostrar("NOEMPTY", "\nFax", new MensajeListener() {
		 * 
		 * @Override public void alDestruir() { txtFaxB.setFocus(true); } }); }
		 * else if (txtRif.getValue().trim().equalsIgnoreCase("")) {
		 * MensajeEmergente.mostrar("NOEMPTY", "\nWeb", new MensajeListener() {
		 * 
		 * @Override public void alDestruir() { txtRif.setFocus(true); } }); }
		 * else if (txtNit.getValue().trim().equalsIgnoreCase("")) {
		 * MensajeEmergente.mostrar("NOEMPTY", "\nRif", new MensajeListener() {
		 * 
		 * @Override public void alDestruir() { txtNit.setFocus(true); } }); }
		 */else if (txtMision.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nNit", new MensajeListener() {
				@Override
				public void alDestruir() {
					txtMision.setFocus(true);
				}
			});
		} else if (txtVision.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nNit", new MensajeListener() {
				@Override
				public void alDestruir() {
					txtVision.setFocus(true);
				}
			});
		} else if (txtObjetivo.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nNit", new MensajeListener() {
				@Override
				public void alDestruir() {
					txtObjetivo.setFocus(true);
				}
			});
		} else if (txtSlogan.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nNit", new MensajeListener() {
				@Override
				public void alDestruir() {
					txtSlogan.setFocus(true);
				}
			});
		} else if (txtHorario.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nNit", new MensajeListener() {
				@Override
				public void alDestruir() {
					txtHorario.setFocus(true);
				}
			});
		} else {

			try {
				hospital.setCiudad((Ciudad) listCiudads.getSelectedItem()
						.getValue());
				servicioHospital.guardarHospital(hospital);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinHospital.recargar();
						winEditHospital.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}
		}
	}

	public void onClose$winEditHospital() {
		this.winEditHospital.detach();
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
		hospital.setImagen(imagen);
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

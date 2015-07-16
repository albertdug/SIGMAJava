package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Producto;
import org.ucla.sigma.servicio.ServicioProducto;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEditProducto extends GenericForwardComposer {

	private Window winEditProducto;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;
	

	// ----------------------------------------------------------------------------------------------------

	private ServicioProducto servicioProducto;
	private ctrlWinProducto ctrlwinproducto;

	// ----------------------------------------------------------------------------------------------------

	private Producto producto;

	// ----------------------------------------------------------------------------------------------------


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

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	

	public void setCtrlwinproducto(ctrlWinProducto ctrlwinproducto) {
		this.ctrlwinproducto = ctrlwinproducto;
	}

	
	public void setWinEditProducto(Window winEditProducto) {
		this.winEditProducto = winEditProducto;
	}

	
	public ServicioProducto getServicioProducto() {
		return servicioProducto;
	}

	public void setServicioProducto(ServicioProducto servicioProducto) {
		this.servicioProducto = servicioProducto;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Window getWinEditProducto() {
		return winEditProducto;
	}

	public ctrlWinProducto getCtrlwinproducto() {
		return ctrlwinproducto;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditProducto.setAttribute(comp.getId() + "ctrl", this);
		servicioProducto = (ServicioProducto) SpringUtil
				.getBean("beanServicioProducto");
		producto = new Producto();
		ctrlwinproducto = (ctrlWinProducto) arg.get("ctrlWinProducto");
		if (!(arg.get("objeto") == null)) {
			producto = (Producto) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinproducto.recargar();
		ctrlwinproducto.apagarBotones();
		this.winEditProducto.detach();
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
		}  else {
			Producto productoTemp = servicioProducto.buscarUno(producto.getNombre());
			if (productoTemp != null) {
				producto.setId(productoTemp.getId());
			}

			try {
				servicioProducto.guardarProducto(producto);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinproducto.recargar();
						ctrlwinproducto.apagarBotones();
						winEditProducto.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditProducto() {
		ctrlwinproducto.apagarBotones();
		this.winEditProducto.detach();
	}
}

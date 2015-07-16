/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.components.HelperString;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Adjunto;
import org.ucla.sigma.modelo.BoletinInformativo;
import org.ucla.sigma.modelo.Imagen;
import org.ucla.sigma.modelo.Publicacion;
import org.ucla.sigma.modelo.Publicidad;
import org.ucla.sigma.servicio.ServicioBoletinInformativo;
import org.ucla.sigma.servicio.ServicioPublicidad;
import org.zkoss.image.AImage;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author Albert
 *
 */
public class ctrlWinEditBoletinInformativo extends GenericForwardComposer {

	private Window winEditBoletinInformativo;
	private Button btnCancelar;
	private Button btnGuardar;
	private Listbox listPublicaciones;
	private Button btnAdj;
	private Checkbox cbEnviado;
	private Datebox dbEnvio;
	private Textbox txtTexto;
	private Textbox txtTitulo;
	private Datebox dbfehca;
	
	private String catalogoPublicacion = "/sigma/vistas/portal/publicacion/catalogoPublicacion.zul";
	private BoletinInformativo boletinInformativo;
	
	private ServicioBoletinInformativo servicioBoletinInformativo;
	private ctrlWinBoletinInformativo ctrlwinBoletinInformativo;
	private List<Publicacion> publicaciones = new ArrayList<Publicacion>();
	private List<Publicacion> lists;
	private Set<Publicacion> sets;
	
	
	public Listbox getListPublicaciones() {
		return listPublicaciones;
	}

	public void setListPublicaciones(Listbox listPublicaciones) {
		this.listPublicaciones = listPublicaciones;
	}

	public List<Publicacion> getPublicaciones() {
		return publicaciones;
	}

	public void setPublicaciones(List<Publicacion> publicaciones) {
		this.publicaciones = publicaciones;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditBoletinInformativo.setAttribute(comp.getId() + "ctrl", this);
		servicioBoletinInformativo = (ServicioBoletinInformativo) SpringUtil
				.getBean("beanServicioBoletinInformativo");

		boletinInformativo = new BoletinInformativo();
		boletinInformativo.setCreacion(HelperDate.now());
		dbfehca.setValue(HelperDate.now());
		ctrlwinBoletinInformativo = (ctrlWinBoletinInformativo) arg.get("ctrlWinBoletinInformativo");

		if (!(arg.get("objeto") == null)) {
			boletinInformativo = (BoletinInformativo) arg.get("objeto");
			//cbEnviado.setChecked(boletinInformativo.isEnviado());
			sets = boletinInformativo.getPublicacions();
			for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
				Publicacion adj = (Publicacion) iterator.next();
				publicaciones.add(adj);
			}
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinBoletinInformativo.recargar();
		ctrlwinBoletinInformativo.apagarBotones();
		this.winEditBoletinInformativo.detach();
	}

	public void onClose$winEditBoletinInformativo() {
		ctrlwinBoletinInformativo.apagarBotones();
		this.winEditBoletinInformativo.detach();
	}

	public BoletinInformativo getBoletinInformativo() {
		return boletinInformativo;
	}

	public void setBoletinInformativo(BoletinInformativo boletinInformativo) {
		this.boletinInformativo = boletinInformativo;
	}
	
	public void recargar() {
		listPublicaciones.setModel(new BindingListModelList(publicaciones, false));
	}
	
	public void limpiarLista() {
		publicaciones = new ArrayList<Publicacion>();
	}
	
	public void onClick$btnAdj() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinEditBoletinInformativo", this);
		Window win = (Window) Executions.createComponents(catalogoPublicacion, null,
				parametros);
		win.doHighlighted();
	}

	public void onClick$btnGuardar() {

		if (HelperString.isEmpty(txtTitulo.getValue())) {
			MensajeEmergente.mostrar("NOEMPTY", "\nTitulo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtTitulo.setFocus(true);
						}
					});
		} else if (HelperString.isEmpty(txtTexto.getValue())) {
			MensajeEmergente.mostrar("NOEMPTY", "\nTexto",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtTexto.setFocus(true);
						}
					});
		} else if (dbEnvio.getValue()==null) {
			MensajeEmergente.mostrar("NOEMPTY", "\nFecha de envio",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							dbEnvio.setFocus(true);
						}
					});
		} else {
			boletinInformativo.setCreacion(dbfehca.getValue());
			boletinInformativo.setEnvio(dbEnvio.getValue());
			if (listPublicaciones.getItems() != null) {
				boletinInformativo.getPublicacions().addAll(publicaciones);
			}
			try {
				BoletinInformativo temp = servicioBoletinInformativo.buscarUno(boletinInformativo
						.getTitulo());
				if (temp != null) {
					boletinInformativo.setId(temp.getId());
				}
				boletinInformativo.setEnviado(true);
				servicioBoletinInformativo.guardarBoletinInformativo(boletinInformativo);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinBoletinInformativo.recargar();
						ctrlwinBoletinInformativo.apagarBotones();
						winEditBoletinInformativo.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}
	}

}

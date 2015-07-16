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
import org.ucla.sigma.modelo.BoletinInformativo;
import org.ucla.sigma.servicio.ServicioBoletinInformativo;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author Albert
 *
 */
public class ctrlWinBoletinInformativo extends GenericForwardComposer {

	private Window winBoletinInformativo;
	private Listbox listBoletinInformativo;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtTitulo;

	public BoletinInformativo getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(BoletinInformativo seleccion) {
		this.seleccion = seleccion;
	}

	public List<BoletinInformativo> getBoletinInformativos() {
		return boletinInformativos;
	}

	public void setBoletinInformativos(List<BoletinInformativo> boletinInformativos) {
		this.boletinInformativos = boletinInformativos;
	}

	private String editBoletinInformativo = "/sigma/vistas/portal/boletinInformativo/editBoletinInformativo.zul";
	private ServicioBoletinInformativo servicioBoletinInformativo;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtTitulo.setFocus(true);
		};
	};	

	private BoletinInformativo seleccion;
	private List<BoletinInformativo> boletinInformativos = new ArrayList<BoletinInformativo>();	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);		
		winBoletinInformativo.setAttribute(comp.getId() + "ctrl", this);
		servicioBoletinInformativo = (ServicioBoletinInformativo) SpringUtil
				.getBean("beanServicioBoletinInformativo");
		apagarBotones();
	}
	
	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinBoletinInformativo", this);
		Window win = (Window) Executions.createComponents(editBoletinInformativo,
				null, parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listBoletinInformativo.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinBoletinInformativo", this);
			Window win = (Window) Executions.createComponents(
					editBoletinInformativo, null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listBoletinInformativo.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioBoletinInformativo.borrarBoletinInformativo(seleccion);
					boletinInformativos.remove(seleccion);
					listBoletinInformativo.setModel(new BindingListModelList(boletinInformativos,
							false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		boletinInformativos = servicioBoletinInformativo.buscarTodos('A');
		listBoletinInformativo.setModel(new BindingListModelList(boletinInformativos, false));
		buscando = false;
		verTodos = true;
		txtTitulo.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtTitulo.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			boletinInformativos = servicioBoletinInformativo.buscarCoincidencias(
					txtTitulo.getValue(), 'A');
			if (boletinInformativos.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listBoletinInformativo.setModel(new BindingListModelList(boletinInformativos,
						false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listBoletinInformativo() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtTitulo() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			boletinInformativos = servicioBoletinInformativo.buscarTodos('A');
		else if (buscando)
			boletinInformativos = servicioBoletinInformativo.buscarCoincidencias(
					txtTitulo.getValue(), 'A');
		else
			boletinInformativos.clear();

		listBoletinInformativo.setModel(new BindingListModelList(boletinInformativos, false));
	}

	public void apagarBotones() {
		txtTitulo.setFocus(true);
		listBoletinInformativo.clearSelection();
		listBoletinInformativo.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

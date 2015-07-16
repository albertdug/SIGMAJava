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
import org.ucla.sigma.modelo.Publicidad;
import org.ucla.sigma.servicio.ServicioPublicidad;
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
 * @author JP
 *
 */
public class ctrlWinPublicidad extends GenericForwardComposer {

	private Window winPublicidad;
	private Listbox listPublicidad;
	private Button btnVerTodos;
	private Button btnEliminar;
	private Button btnEditar;
	private Button btnNuevo;
	private Button btnBuscar;
	private Textbox txtTitulo;

	public Publicidad getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Publicidad seleccion) {
		this.seleccion = seleccion;
	}

	public List<Publicidad> getPublicidades() {
		return publicidades;
	}

	public void setPublicidades(List<Publicidad> publicidades) {
		this.publicidades = publicidades;
	}

	private String editPublicidad = "/sigma/vistas/portal/publicidad/editPublicidad.zul";
	private ServicioPublicidad servicioPublicidad;
	private boolean buscando = false;
	private boolean verTodos = false;
	private MensajeListener asignarFocusBuscar = new MensajeListener() {
		public void alDestruir() {
			txtTitulo.setFocus(true);
		};
	};	

	private Publicidad seleccion;
	private List<Publicidad> publicidades = new ArrayList<Publicidad>();	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);		
		winPublicidad.setAttribute(comp.getId() + "ctrl", this);
		servicioPublicidad = (ServicioPublicidad) SpringUtil
				.getBean("beanServicioPublicidad");
		apagarBotones();
	}
	
	public void onClick$btnNuevo() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ctrlWinPublicidad", this);
		Window win = (Window) Executions.createComponents(editPublicidad,
				null, parametros);
		win.doHighlighted();
		apagarBotones();
	}

	public void onClick$btnEditar() {
		if (listPublicidad.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("objeto", seleccion);
			parametros.put("ctrlWinPublicidad", this);
			Window win = (Window) Executions.createComponents(
					editPublicidad, null, parametros);
			win.doHighlighted();
			apagarBotones();
		}

	}

	public void onClick$btnEliminar() {
		if (listPublicidad.getSelectedItems().isEmpty()) {
			MensajeEmergente.mostrar("SELECTREG");
		} else {
			MensajeEmergente.mostrar("CONFDELETE", new MensajeListener() {
				@Override
				public void alAceptar() {
					servicioPublicidad.borrarPublicidad(seleccion);
					publicidades.remove(seleccion);
					listPublicidad.setModel(new BindingListModelList(publicidades,
							false));
					MensajeEmergente.mostrar("REGDELETE", asignarFocusBuscar);
				}
			});
			apagarBotones();
		}

	}

	public void onClick$btnVerTodos() {
		publicidades = servicioPublicidad.buscarTodos('A');
		listPublicidad.setModel(new BindingListModelList(publicidades, false));
		buscando = false;
		verTodos = true;
		txtTitulo.setText(null);
		apagarBotones();
	}

	public void onClick$btnBuscar() {

		if (txtTitulo.getValue().isEmpty()) {
			MensajeEmergente.mostrar("NOFINDED", asignarFocusBuscar);
		} else {
			publicidades = servicioPublicidad.buscarCoincidencias(
					txtTitulo.getValue(), 'A');
			if (publicidades.isEmpty()) {
				MensajeEmergente.mostrar("NOTFOUND", asignarFocusBuscar);
			} else {
				listPublicidad.setModel(new BindingListModelList(publicidades,
						false));
				buscando = true;
				verTodos = false;
			}
		}
		apagarBotones();
	}

	public void onSelect$listPublicidad() {
		btnEditar.setDisabled(false);
		btnEliminar.setDisabled(false);
	}

	public void onFocus$txtTitulo() {
		apagarBotones();
	}

	public void recargar() {
		seleccion = null;
		if (verTodos)
			publicidades = servicioPublicidad.buscarTodos('A');
		else if (buscando)
			publicidades = servicioPublicidad.buscarCoincidencias(
					txtTitulo.getValue(), 'A');
		else
			publicidades.clear();

		listPublicidad.setModel(new BindingListModelList(publicidades, false));
	}

	public void apagarBotones() {
		txtTitulo.setFocus(true);
		listPublicidad.clearSelection();
		listPublicidad.selectItem(null);
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

}

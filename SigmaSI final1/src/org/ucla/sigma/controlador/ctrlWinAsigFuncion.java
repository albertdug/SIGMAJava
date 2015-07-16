/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.modelo.Funcion;
import org.ucla.sigma.modelo.Perfil;
import org.ucla.sigma.modelo.Usuario;
import org.ucla.sigma.servicio.ServicioFuncion;
import org.ucla.sigma.servicio.ServicioPerfil;
import org.ucla.sigma.servicio.ServicioUsuario;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

/**
 * @author rafael
 * 
 */
public class ctrlWinAsigFuncion extends GenericForwardComposer {

	private Window winAsigFuncion;
	private Listbox listFuncion;
	private Listbox listPerfil;
	private Button btnAsignar;
	private Checkbox checkTodos;

	private ServicioPerfil servicioPerfil;
	private ServicioFuncion servicioFuncion;

	private Perfil seleccionPerfil;
	private Funcion seleccionFuncion;

	private List<?> lists;
	private List<Funcion> funcions = new ArrayList<Funcion>();
	private List<Perfil> perfils = new ArrayList<Perfil>();

	private Set<?> sets;
	private Set<Funcion> auxFuncions = new HashSet<Funcion>();

	public Window getWinAsigFuncion() {
		return winAsigFuncion;
	}

	public void setWinAsigFuncion(Window winAsigFuncion) {
		this.winAsigFuncion = winAsigFuncion;
	}

	public Listbox getListFuncion() {
		return listFuncion;
	}

	public void setListFuncion(Listbox listFuncion) {
		this.listFuncion = listFuncion;
	}

	public Listbox getListPerfil() {
		return listPerfil;
	}

	public void setListPerfil(Listbox listPerfil) {
		this.listPerfil = listPerfil;
	}

	public Button getBtnAsignar() {
		return btnAsignar;
	}

	public void setBtnAsignar(Button btnAsignar) {
		this.btnAsignar = btnAsignar;
	}

	public Checkbox getCheckTodos() {
		return checkTodos;
	}

	public void setCheckTodos(Checkbox checkTodos) {
		this.checkTodos = checkTodos;
	}

	public ServicioPerfil getServicioPerfil() {
		return servicioPerfil;
	}

	public void setServicioPerfil(ServicioPerfil servicioPerfil) {
		this.servicioPerfil = servicioPerfil;
	}

	public ServicioFuncion getServicioFuncion() {
		return servicioFuncion;
	}

	public void setServicioFuncion(ServicioFuncion servicioFuncion) {
		this.servicioFuncion = servicioFuncion;
	}

	public Perfil getSeleccionPerfil() {
		return seleccionPerfil;
	}

	public void setSeleccionPerfil(Perfil seleccionPerfil) {
		this.seleccionPerfil = seleccionPerfil;
	}

	public Funcion getSeleccionFuncion() {
		return seleccionFuncion;
	}

	public void setSeleccionFuncion(Funcion seleccionFuncion) {
		this.seleccionFuncion = seleccionFuncion;
	}

	public List<?> getLists() {
		return lists;
	}

	public void setLists(List<?> lists) {
		this.lists = lists;
	}

	public List<Funcion> getFuncions() {
		return funcions;
	}

	public void setFuncions(List<Funcion> funcions) {
		this.funcions = funcions;
	}

	public List<Perfil> getPerfils() {
		return perfils;
	}

	public void setPerfils(List<Perfil> perfils) {
		this.perfils = perfils;
	}

	public Set<?> getSets() {
		return sets;
	}

	public void setSets(Set<?> sets) {
		this.sets = sets;
	}

	public Set<Funcion> getAuxFuncions() {
		return auxFuncions;
	}

	public void setAuxFuncions(Set<Funcion> auxFuncions) {
		this.auxFuncions = auxFuncions;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winAsigFuncion.setAttribute(comp.getId() + "ctrl", this);
		servicioFuncion = (ServicioFuncion) SpringUtil
				.getBean("beanServicioFuncion");
		servicioPerfil = (ServicioPerfil) SpringUtil
				.getBean("beanServicioPerfil");
		funcions = servicioFuncion.buscarFunciones();
		perfils = servicioPerfil.buscarTodos('A');

	}

	public void onCheck$checkTodos() {
		if (checkTodos.isChecked()) {
			listFuncion.selectAll();
		} else {
			listFuncion.clearSelection();
		}
	}

	public void onSelect$listFuncion() {

		if (listFuncion.getItemCount() == listFuncion.getSelectedCount()) {
			checkTodos.setChecked(true);
		} else {
			checkTodos.setChecked(false);
		}
	}

	public void onSelect$listPerfil() {

		if (seleccionPerfil.getFuncions().size() == listFuncion.getItemCount()) {
			checkTodos.setChecked(true);
		} else {
			checkTodos.setChecked(false);
		}
		listFuncion.setVisible(true);
		btnAsignar.setDisabled(false);
		listFuncion.renderAll();
		listFuncion.clearSelection();
		sets = seleccionPerfil.getFuncions();
		lists = listFuncion.getItems();
		for (Iterator iterator = lists.iterator(); iterator.hasNext();) {
			Listitem item = (Listitem) iterator.next();
			for (Iterator iterator1 = sets.iterator(); iterator1.hasNext();) {
				Funcion funcion = (Funcion) iterator1.next();
				if (funcion.equals((((Funcion) item.getValue())))) {
					listFuncion.addItemToSelection(item);
				}
			}

		}
	}

	public void onClick$btnAsignar() {

		if (seleccionPerfil != null) {
			if (!listFuncion.getSelectedItems().isEmpty()) {
				sets = listFuncion.getSelectedItems();
				auxFuncions = new HashSet<Funcion>();
				for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxFuncions.add((Funcion) item.getValue());
				}
				seleccionPerfil.setFuncions(auxFuncions);
			} else {
				seleccionPerfil
						.setFuncions(auxFuncions = new HashSet<Funcion>());
			}
			servicioPerfil.guardarPerfil(seleccionPerfil);
			MensajeEmergente.mostrar("REGINSERT");
		}

	}

}

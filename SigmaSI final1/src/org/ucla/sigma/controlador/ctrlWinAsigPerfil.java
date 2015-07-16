/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jruby.RubyProcess.Sys;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.modelo.Perfil;
import org.ucla.sigma.modelo.Usuario;
import org.ucla.sigma.servicio.ServicioPerfil;
import org.ucla.sigma.servicio.ServicioUsuario;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import sun.misc.Perf;

/**
 * @author rafael
 * 
 */
public class ctrlWinAsigPerfil extends GenericForwardComposer {

	private Window winAsigPerfil;
	private Listbox listPerfil;
	private Listbox listUsuarios;
	private Button btnAsignar;

	private ServicioUsuario servicioUsuario;
	private ServicioPerfil servicioPerfil;

	private Usuario seleccionUsuario;
	private Perfil seleccionPerfil;

	private List<?> lists;
	private List<Usuario> usuarios = new ArrayList<Usuario>();
	private List<Perfil> perfils = new ArrayList<Perfil>();

	private Set<?> sets;
	private Set<Perfil> auxPerfils = new HashSet<Perfil>();

	public Window getWinAsigPerfil() {
		return winAsigPerfil;
	}

	public void setWinAsigPerfil(Window winAsigPerfil) {
		this.winAsigPerfil = winAsigPerfil;
	}

	public Listbox getListPerfil() {
		return listPerfil;
	}

	public void setListPerfil(Listbox listPerfil) {
		this.listPerfil = listPerfil;
	}

	public Listbox getListUsuarios() {
		return listUsuarios;
	}

	public void setListUsuarios(Listbox listUsuarios) {
		this.listUsuarios = listUsuarios;
	}

	public Button getBtnAsignar() {
		return btnAsignar;
	}

	public void setBtnAsignar(Button btnAsignar) {
		this.btnAsignar = btnAsignar;
	}

	public ServicioUsuario getServicioUsuario() {
		return servicioUsuario;
	}

	public void setServicioUsuario(ServicioUsuario servicioUsuario) {
		this.servicioUsuario = servicioUsuario;
	}

	public ServicioPerfil getServicioPerfil() {
		return servicioPerfil;
	}

	public void setServicioPerfil(ServicioPerfil servicioPerfil) {
		this.servicioPerfil = servicioPerfil;
	}

	public Usuario getSeleccionUsuario() {
		return seleccionUsuario;
	}

	public void setSeleccionUsuario(Usuario seleccionUsuario) {
		this.seleccionUsuario = seleccionUsuario;
	}

	public Perfil getSeleccionPerfil() {
		return seleccionPerfil;
	}

	public void setSeleccionPerfil(Perfil seleccionPerfil) {
		this.seleccionPerfil = seleccionPerfil;
	}

	public List<?> getLists() {
		return lists;
	}

	public void setLists(List<?> lists) {
		this.lists = lists;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
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

	public Set<Perfil> getAuxPerfils() {
		return auxPerfils;
	}

	public void setAuxPerfils(Set<Perfil> auxPerfils) {
		this.auxPerfils = auxPerfils;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winAsigPerfil.setAttribute(comp.getId() + "ctrl", this);
		servicioUsuario = (ServicioUsuario) SpringUtil
				.getBean("beanServicioUsuario");
		servicioPerfil = (ServicioPerfil) SpringUtil
				.getBean("beanServicioPerfil");
		usuarios = servicioUsuario.buscarTodos('A');
		perfils = servicioPerfil.buscarTodos('A');

		btnAsignar.setDisabled(true);
	}

	public void onSelect$listUsuarios() {
		btnAsignar.setDisabled(false);
		listPerfil.renderAll();
		listPerfil.clearSelection();
		lists = listPerfil.getItems();
		for (Iterator iterator = lists.iterator(); iterator.hasNext();) {
			Listitem listadoPerfil = (Listitem) iterator.next();
			Set sets = seleccionUsuario.getPerfils();
			for (Iterator iterator1 = sets.iterator(); iterator1.hasNext();) {
				Perfil usuarioPerfil = (Perfil) iterator1.next();
				if (usuarioPerfil.equals((Perfil) listadoPerfil.getValue())) {
					listPerfil.addItemToSelection(listadoPerfil); // esta es
				}
			}
		}
	}

	public void onClick$btnAsignar() {

		if (seleccionUsuario != null) {
			if (!listPerfil.getSelectedItems().isEmpty()) {
				sets = listPerfil.getSelectedItems();
				auxPerfils = new HashSet<Perfil>();
				for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
					Listitem item = (Listitem) iterator.next();
					auxPerfils.add((Perfil) item.getValue());
				}
				seleccionUsuario.setPerfils(auxPerfils);
			} else {
				seleccionUsuario.setPerfils(auxPerfils = new HashSet<Perfil>());
			}
			servicioUsuario.guardarUsuario(seleccionUsuario);
			MensajeEmergente.mostrar("REGINSERT");
		}
	}
}

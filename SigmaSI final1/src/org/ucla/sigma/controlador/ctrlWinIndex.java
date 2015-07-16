/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.ucla.sigma.componentszk.SessionAdministrator;
import org.ucla.sigma.modelo.Funcion;
import org.ucla.sigma.modelo.Perfil;
import org.ucla.sigma.modelo.Usuario;
import org.ucla.sigma.modelo.Veterinario;
import org.ucla.sigma.servicio.ServicioFuncion;
import org.ucla.sigma.servicio.ServicioPerfil;
import org.ucla.sigma.servicio.ServicioUsuario;
import org.ucla.sigma.servicio.ServicioVeterinario;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.A;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.West;
import org.zkoss.zul.Window;

/**
 * @author JP
 * 
 */
public class ctrlWinIndex extends GenericForwardComposer {

	private Window winIndex;
	private Div contenido;
	private Tabpanels ctnTabsP;
	private Tabs ctnTabs;
	private Tabbox tb;
	private West contMenu;
	private Borderlayout borLayout;
	private Label nombreUsuario;
	private Boolean isVeterinario;

	private ServicioFuncion serFuncion;
	private List<Funcion> funcionalidades;
	private Set<Perfil> perfiles;
	private List<Funcion> funcionalidadesPerfil;
	private ServicioPerfil serPerfil;
	private ServicioUsuario serUsuario;
	private Perfil perfil;
	private Usuario usuario;
	private A cerrar;
	private ServicioVeterinario serVeterinario;

	public Label getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(Label nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public Window getWinIndex() {
		return winIndex;
	}

	public void setWinIndex(Window winIndex) {
		this.winIndex = winIndex;
	}

	public Div getContenido() {
		return contenido;
	}

	public void setContenido(Div contenido) {
		this.contenido = contenido;
	}

	public Tabpanels getCtnTabsP() {
		return ctnTabsP;
	}

	public void setCtnTabsP(Tabpanels ctnTabsP) {
		this.ctnTabsP = ctnTabsP;
	}

	public Tabs getCtnTabs() {
		return ctnTabs;
	}

	public void setCtnTabs(Tabs ctnTabs) {
		this.ctnTabs = ctnTabs;
	}

	public Tabbox getTb() {
		return tb;
	}

	public void setTb(Tabbox tb) {
		this.tb = tb;
	}

	public West getContMenu() {
		return contMenu;
	}

	public void setContMenu(West contMenu) {
		this.contMenu = contMenu;
	}

	public Borderlayout getBorLayout() {
		return borLayout;
	}

	public void setBorLayout(Borderlayout borLayout) {
		this.borLayout = borLayout;
	}

	public ServicioFuncion getSerFuncion() {
		return serFuncion;
	}

	public void setSerFuncion(ServicioFuncion serFuncion) {
		this.serFuncion = serFuncion;
	}

	public List<Funcion> getFunciones() {
		return funcionalidades;
	}

	public void setFunciones(List<Funcion> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}

	public ServicioPerfil getSerPerfil() {
		return serPerfil;
	}

	public void setSerPerfil(ServicioPerfil serPerfil) {
		this.serPerfil = serPerfil;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	@Override
	public ComponentInfo doBeforeCompose(Page page, Component parent,
			ComponentInfo compInfo) {

		usuario = SessionAdministrator.getLoggedUsuario();

		if (usuario == null) {
			SessionAdministrator.sessionDestroy();
			Executions.sendRedirect("/sigma/login.zul");
			return null;
		}

		return super.doBeforeCompose(page, parent, compInfo);
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		winIndex.setAttribute(comp.getId() + "ctrl", this);
		serFuncion = (ServicioFuncion) SpringUtil
				.getBean("beanServicioFuncion");
		serPerfil = (ServicioPerfil) SpringUtil.getBean("beanServicioPerfil");
		serVeterinario = (ServicioVeterinario) SpringUtil
				.getBean("beanServicioVeterinario");

		funcionalidades = serFuncion.buscarPadres();
		perfiles = usuario.getPerfils();
		nombreUsuario.setValue("Usuario: " + usuario.getPersona().getNombre()
				+ " " + usuario.getPersona().getApellido() + " - ");

		Veterinario vet = serVeterinario.buscarUno(usuario.getCedula());
		isVeterinario = vet != null;

		// CAMBIAR CONTRASEÑA

		// ('F_CCT','Cambiar
		// Contraseña',5,'/sigma/vistas/administracion/cambiarContrasena/cambiarContrasena.zul','A','M_ADM',false)

		funcionalidadesPerfil = new ArrayList<Funcion>();
		
		int t = 0;
		for (Perfil perfil : usuario.getPerfils()) {
			t += perfil.getFuncions().size();
		}

		if (t > 0) {
			funcionalidadesPerfil = serPerfil
					.getFunciones(new ArrayList<Perfil>(perfiles));
		}

		Funcion cambiarpass = serFuncion.buscarUno("F_CCT");
		funcionalidadesPerfil.add(cambiarpass);
		
		// CAMBIAR CONTRASEÑA

		generarMenuPerfiles();
	}

	public void ir(String pagina) {
		contenido.getChildren().clear();
		execution.createComponents(pagina, contenido, null);
	}

	public void onClick$cerrar() {
		Executions.sendRedirect("/sigma/login.zul");
		SessionAdministrator.sessionDestroy();
	}

	private void generarMenuPerfiles() {

		for (Funcion menu : funcionalidades) {
			boolean menuexist = false;
			Tree tempTree = new Tree();
			tempTree.setRows(8);
			tempTree.setWidth("auto");
			tempTree.setHeight("auto");

			Treechildren contItem = new Treechildren();

			for (Iterator iterator_menu = menu.getFuncions().iterator(); iterator_menu
					.hasNext();) {
				Funcion padre = (Funcion) iterator_menu.next();

				// ES FUNCIONALIDAD UN PADRE
				for (Funcion per_fun : funcionalidadesPerfil) {
					if (padre.equals(per_fun)) {
						Treeitem temptreeitem = new Treeitem(padre.getNombre());
						temptreeitem.setOpen(false);
						final String url = padre.getUrl();
						temptreeitem.addEventListener("onClick",
								new EventListener() {

									@Override
									public void onEvent(Event e)
											throws Exception {
										if (e.getName().equalsIgnoreCase(
												"onClick")) {
											ir(url);
										}

									}
								});

						// VETERINARIO
						if (padre.getNeedVeterinario()) {
							if (isVeterinario) {
								System.out.println("\tA " + padre.getNombre());
								contItem.appendChild(temptreeitem);
								menuexist = true;
								break;
							}
						} else {
							System.out.println("\tA " + padre.getNombre());
							contItem.appendChild(temptreeitem);
							menuexist = true;
							break;
						}
						// VETERINARIO

					}
				}
				// ES FUNCIONALIDAD UN PADRE

				// ES FUNCION
				boolean funcionexist = false;
				Treeitem temptreeitem = new Treeitem(padre.getNombre());
				temptreeitem.setOpen(false);
				Treechildren contItem2 = new Treechildren();
				temptreeitem.appendChild(contItem2);
				for (Iterator iterator_padre = padre.getFuncions().iterator(); iterator_padre
						.hasNext();) {

					Funcion funcion = (Funcion) iterator_padre.next();
					for (Funcion per_fun : funcionalidadesPerfil) {
						if (funcion.equals(per_fun)) {
							Treeitem temptreeitemfun = new Treeitem(
									funcion.getNombre());
							final String url = funcion.getUrl();
							temptreeitemfun.addEventListener("onClick",
									new EventListener() {

										@Override
										public void onEvent(Event e)
												throws Exception {
											if (e.getName().equalsIgnoreCase(
													"onClick")) {
												ir(url);
											}

										}
									});

							// VETERINARIO
							if (funcion.getNeedVeterinario()) {
								if (isVeterinario) {
									contItem2.appendChild(temptreeitemfun);
									System.out.println("\t\tB "
											+ funcion.getNombre());
									funcionexist = true;
									menuexist = true;
									break;
								}
							} else {
								contItem2.appendChild(temptreeitemfun);
								System.out.println("\t\tB "
										+ funcion.getNombre());
								funcionexist = true;
								menuexist = true;
								break;
							}
							// VETERINARIO

						}
					}

				}
				if (funcionexist) {

					contItem.appendChild(temptreeitem);
					System.out.println("\tC " + padre.getNombre());
				}
				// ES FUNCION

			}
			if (menuexist) {
				Tab tabmenu = new Tab(menu.getNombre());
				tabmenu.setSelected(false);
				ctnTabs.appendChild(tabmenu);

				Tabpanel tapp = new Tabpanel();
				tempTree.appendChild(contItem);
				tapp.appendChild(tempTree);

				ctnTabsP.appendChild(tapp);

				System.out.println("D " + menu.getNombre());
			}

		}
	}

}

/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.SessionAdministrator;
import org.ucla.sigma.modelo.Usuario;
import org.ucla.sigma.servicio.ServicioUsuario;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author JP
 * 
 */
public class ctrlWinLogin extends GenericForwardComposer {

	private Window winLogin;
	private Button btnLogin;
	private Textbox txtPassword;
	private Textbox txtLogin;
	private Usuario usuario;
	private ServicioUsuario servicioUsuario;

	public Window getWinLogin() {
		return winLogin;
	}

	public void setWinLogin(Window winLogin) {
		this.winLogin = winLogin;
	}

	public Button getBtnLogin() {
		return btnLogin;
	}

	public void setBtnLogin(Button btnLogin) {
		this.btnLogin = btnLogin;
	}

	public Textbox getTxtPassword() {
		return txtPassword;
	}

	public void setTxtPassword(Textbox txtPassword) {
		this.txtPassword = txtPassword;
	}

	public Textbox getTxtLogin() {
		return txtLogin;
	}

	public void setTxtLogin(Textbox txtLogin) {
		this.txtLogin = txtLogin;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public ServicioUsuario getServicioUsuario() {
		return servicioUsuario;
	}

	public void setServicioUsuario(ServicioUsuario servicioUsuario) {
		this.servicioUsuario = servicioUsuario;
	}

	@Override
	public ComponentInfo doBeforeCompose(Page page, Component parent,
			ComponentInfo compInfo) {

		usuario = SessionAdministrator.getLoggedUsuario();

		if (usuario != null) {
			Executions.sendRedirect("/sigma/");
			return null;
		}

		return super.doBeforeCompose(page, parent, compInfo);
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winLogin.setAttribute(comp.getId() + "ctrl", this);
		servicioUsuario = (ServicioUsuario) SpringUtil
				.getBean("beanServicioUsuario");
	}

	public void onClick$btnLogin() {
		usuario = servicioUsuario.Credenciales(txtLogin.getValue(),
				txtPassword.getValue(), 'A');
		if (usuario != null) {
			SessionAdministrator.setSessionUsuario(usuario);
			Executions.sendRedirect("/sigma/");
		} else {
			MensajeEmergente.mostrar("ERRLOGIN");
		}
	}

}

/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.ArrayList;
import java.util.List;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.EspImagenologia;
import org.ucla.sigma.modelo.Especie;
import org.ucla.sigma.modelo.Raza;
import org.ucla.sigma.modelo.TipoImagenologia;
import org.ucla.sigma.servicio.ServicioEspImagenologia;
import org.ucla.sigma.servicio.ServicioEspecie;
import org.ucla.sigma.servicio.ServicioRaza;
import org.ucla.sigma.servicio.ServicioTipoImagenologia;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author lis
 *
 */
public class ctrlWinEditEspecificacionImagenologia extends
		GenericForwardComposer {

	private Window winEditEspecificacionImagenologia;
	private Button btnCancelar;
	private Button btnGuardar;
	private Listbox listTipo;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

		private ServicioEspImagenologia servicioEspImagenologia;
		private ServicioTipoImagenologia servicioTipoImagenologia;
		private ctrlWinEspecificacionImagenologia ctrlwinEspecificacionImagenologia;
		private int indexTipoImagenologia = -1;

		// ----------------------------------------------------------------------------------------------------

		private List<TipoImagenologia> tipos = new ArrayList<TipoImagenologia>();
		private EspImagenologia espImagenologia;
		
		// ----------------------------------------------------------------------------------------------------
				
		
		public Window getWinEditEspecificacionImagenologia() {
			return winEditEspecificacionImagenologia;
		}
		public void setWinEditEspecificacionImagenologia(
				Window winEditEspecificacionImagenologia) {
			this.winEditEspecificacionImagenologia = winEditEspecificacionImagenologia;
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
		public Listbox getListTipo() {
			return listTipo;
		}
		public void setListTipo(Listbox listTipo) {
			this.listTipo = listTipo;
		}
		public Textbox getTxtNombre() {
			return txtNombre;
		}
		public void setTxtNombre(Textbox txtNombre) {
			this.txtNombre = txtNombre;
		}
		public ServicioEspImagenologia getServicioEspImagenologia() {
			return servicioEspImagenologia;
		}
		public void setServicioEspImagenologia(
				ServicioEspImagenologia servicioEspImagenologia) {
			this.servicioEspImagenologia = servicioEspImagenologia;
		}
		public ServicioTipoImagenologia getServicioTipoImagenologia() {
			return servicioTipoImagenologia;
		}
		public void setServicioTipoImagenologia(
				ServicioTipoImagenologia servicioTipoImagenologia) {
			this.servicioTipoImagenologia = servicioTipoImagenologia;
		}
		public ctrlWinEspecificacionImagenologia getCtrlwinEspecificacionImagenologia() {
			return ctrlwinEspecificacionImagenologia;
		}
		public void setCtrlwinEspecificacionImagenologia(
				ctrlWinEspecificacionImagenologia ctrlwinEspecificacionImagenologia) {
			this.ctrlwinEspecificacionImagenologia = ctrlwinEspecificacionImagenologia;
		}
		
		
		public int getIndexTipoImagenologia() {
			return indexTipoImagenologia;
		}
		public void setIndexTipoImagenologia(int indexTipoImagenologia) {
			this.indexTipoImagenologia = indexTipoImagenologia;
		}

		public List<TipoImagenologia> getTipos() {
			return tipos;
		}
		public void setTipos(List<TipoImagenologia> tipos) {
			this.tipos = tipos;
		}
		public EspImagenologia getEspImagenologia() {
			return espImagenologia;
		}
		public void setEspImagenologia(EspImagenologia espImagenologia) {
			this.espImagenologia = espImagenologia;
		}

		@Override
		public void doAfterCompose(Component comp) throws Exception {
			super.doAfterCompose(comp);
			winEditEspecificacionImagenologia.setAttribute(comp.getId() + "ctrl", this);
			servicioEspImagenologia = (ServicioEspImagenologia) SpringUtil.getBean("beanServicioEspImagenologia");
			servicioTipoImagenologia = (ServicioTipoImagenologia) SpringUtil
					.getBean("beanServicioTipoImagenologia");
			espImagenologia = new EspImagenologia();
			tipos = servicioTipoImagenologia.buscarTodos();
			ctrlwinEspecificacionImagenologia = (ctrlWinEspecificacionImagenologia) arg.get("ctrlWinEspImagenologia");
			System.out.println("justo antes");
			if (!(arg.get("objeto") == null)) {
				espImagenologia = (EspImagenologia) arg.get("objeto");
				System.out.println(espImagenologia.getNombre());
				indexTipoImagenologia = tipos.indexOf(espImagenologia.getTipoImagenologia());
			}
		}

		public void onClick$btnCancelar() {
			ctrlwinEspecificacionImagenologia.recargar();
			ctrlwinEspecificacionImagenologia.apagarBotones();
			this.winEditEspecificacionImagenologia.detach();
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
			} else if (listTipo.getSelectedIndex() < 0) {
				MensajeEmergente.mostrar("NOEMPTY", "\nTipo imagenología" +
						"",
						new MensajeListener() {
							@Override
							public void alDestruir() {
								listTipo.setFocus(true);
							}
						});
			} else {
				TipoImagenologia tipoTemp = servicioTipoImagenologia.buscarUno(espImagenologia.getNombre());
				if (tipoTemp != null) {
					espImagenologia.setId(tipoTemp.getId());
				}
				espImagenologia.setTipoImagenologia((TipoImagenologia) listTipo.getSelectedItem().getValue());
				try {
					servicioEspImagenologia.guardarEspImagenologia(espImagenologia);
					MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
						@Override
						public void alAceptar() {
							ctrlwinEspecificacionImagenologia.recargar();
							ctrlwinEspecificacionImagenologia.apagarBotones();
							winEditEspecificacionImagenologia.detach();
						}
					});
				} catch (Exception e) {
					MensajeEmergente.mostrar("ERRDB");
					e.printStackTrace();
				}

			}

		}

		public void onClose$winEditEspecificacionImagenologia() {
			ctrlwinEspecificacionImagenologia.apagarBotones();
			this.winEditEspecificacionImagenologia.detach();
		}

		public void onAfterRender$listTipo() {
			listTipo.setSelectedIndex(indexTipoImagenologia);
		}
		
}

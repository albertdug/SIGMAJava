package org.ucla.sigma.controladorweb;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.modelo.Notificacion;
import org.ucla.sigma.servicio.ServicioHospital;
import org.ucla.sigma.servicio.ServicioNotificacion;
import org.ucla.sigma.servicio.ServicioPublicidad;
import org.ucla.sigma.servicio.ServicioTipoPublicacion;
import org.ucla.sigma.servicio.ServicioTipoServicioWeb;

@Controller
@RequestMapping("/contacto")
public class ContactoController {

	@Autowired
	private ServicioHospital servicioHospital;

	@Autowired
	private ServicioTipoServicioWeb servicioTipoServicioWeb;

	@Autowired
	private ServicioTipoPublicacion servicioTipoPublicacion;

	@Autowired
	private ServicioPublicidad servicioPublicidad;

	@Autowired
	private ServicioNotificacion servicioNotificacion;
	
	@RequestMapping(method = RequestMethod.GET)
	public String printContacto(ModelMap model, HttpServletRequest request) {
		cargarElementos(model,request);
		return "Contacto";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(ModelMap model, HttpServletRequest request) {
		cargarElementos(model,request);
		Notificacion notificacion = new Notificacion();

		notificacion.setNombre(request.getParameter("nombre"));
		notificacion.setApellido(request.getParameter("apellido"));
		notificacion.setCorreo(request.getParameter("correo"));
		notificacion.setTelefono(request.getParameter("telefono"));
		notificacion.setTexto(request.getParameter("observacion"));
		notificacion.setCreacion(HelperDate.now());

		servicioNotificacion.guardarNotificacion(notificacion);
		
		model.addAttribute("mensaje", "El mensaje ha sido guardado satisfactoriamente");
		
		return "Contacto";

	}
	
	private void cargarElementos(ModelMap model, HttpServletRequest request){
		model.addAttribute("urlRaiz", request.getContextPath());
		model.addAttribute("hospital", servicioHospital.buscarUnico());

		model.addAttribute("servicios",
				servicioTipoServicioWeb.buscarTodos('A'));

		model.addAttribute("tipoPublicaciones",
				servicioTipoPublicacion.buscarTodos('A'));

		model.addAttribute("publicidad", servicioPublicidad.getRand('A'));
	}

}
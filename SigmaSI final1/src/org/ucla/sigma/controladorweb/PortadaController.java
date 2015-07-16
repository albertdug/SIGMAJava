package org.ucla.sigma.controladorweb;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.ucla.sigma.servicio.ServicioHospital;
import org.ucla.sigma.servicio.ServicioPublicacion;
import org.ucla.sigma.servicio.ServicioPublicidad;
import org.ucla.sigma.servicio.ServicioTipoPublicacion;
import org.ucla.sigma.servicio.ServicioTipoServicioWeb;

@Controller
@RequestMapping("/")
public class PortadaController {

	@Autowired
	private ServicioHospital servicioHospital;

	@Autowired
	private ServicioTipoServicioWeb servicioTipoServicioWeb;

	@Autowired
	private ServicioTipoPublicacion servicioTipoPublicacion;

	@Autowired
	private ServicioPublicidad servicioPublicidad;

	@Autowired
	private ServicioPublicacion servicioPublicacion;

	@RequestMapping(method = RequestMethod.GET)
	public String printPortada(ModelMap model, HttpServletRequest request){
		model.addAttribute("urlRaiz", request.getContextPath());

		model.addAttribute("hospital", servicioHospital.buscarUnico());

		model.addAttribute("servicios",
				servicioTipoServicioWeb.buscarTodos('A'));

		model.addAttribute("tipoPublicaciones",
				servicioTipoPublicacion.buscarTodos('A'));

		model.addAttribute("publicidad", servicioPublicidad.getRand('A'));

		model.addAttribute("lastPublicaciones",
				servicioPublicacion.last(0, 3, 'A'));

		return "Portada";

	}

}
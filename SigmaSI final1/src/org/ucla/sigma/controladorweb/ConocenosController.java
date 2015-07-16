package org.ucla.sigma.controladorweb;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.ucla.sigma.servicio.ServicioHospital;
import org.ucla.sigma.servicio.ServicioPublicidad;
import org.ucla.sigma.servicio.ServicioTipoPublicacion;
import org.ucla.sigma.servicio.ServicioTipoServicioWeb;

@Controller
@RequestMapping("/conocenos")
public class ConocenosController {

	@Autowired
	private ServicioHospital servicioHospital;

	@Autowired
	private ServicioTipoServicioWeb servicioTipoServicioWeb;

	@Autowired
	private ServicioTipoPublicacion servicioTipoPublicacion;

	@Autowired
	private ServicioPublicidad servicioPublicidad;

	@RequestMapping(method = RequestMethod.GET)
	public String printConocenos(ModelMap model, HttpServletRequest request) {
		model.addAttribute("urlRaiz", request.getContextPath());
		model.addAttribute("hospital", servicioHospital.buscarUnico());

		model.addAttribute("servicios",
				servicioTipoServicioWeb.buscarTodos('A'));

		model.addAttribute("tipoPublicaciones",
				servicioTipoPublicacion.buscarTodos('A'));

		model.addAttribute("publicidad", servicioPublicidad.getRand('A'));

		return "Conocenos";
	}

}
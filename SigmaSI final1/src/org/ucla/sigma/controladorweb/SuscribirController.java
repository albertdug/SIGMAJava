package org.ucla.sigma.controladorweb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.modelo.Suscripcion;
import org.ucla.sigma.modelo.TipoPublicacion;
import org.ucla.sigma.servicio.ServicioHospital;
import org.ucla.sigma.servicio.ServicioPublicidad;
import org.ucla.sigma.servicio.ServicioSuscripcion;
import org.ucla.sigma.servicio.ServicioTipoPublicacion;
import org.ucla.sigma.servicio.ServicioTipoServicioWeb;

@Controller
@RequestMapping("/suscribir")
public class SuscribirController {

	@Autowired
	private ServicioHospital servicioHospital;

	@Autowired
	private ServicioTipoServicioWeb servicioTipoServicioWeb;

	@Autowired
	private ServicioTipoPublicacion servicioTipoPublicacion;

	@Autowired
	private ServicioPublicidad servicioPublicidad;

	@Autowired
	private ServicioSuscripcion servicioSuscripcion;

	@RequestMapping(method = RequestMethod.POST)
	public String printSuscribir(ModelMap model, HttpServletRequest request) {
		cargarElementos(model, request);

		model.addAttribute("nombre", request.getParameter("newsletter-nombre"));

		model.addAttribute("correo", request.getParameter("newsletter-correo"));

		return "Suscribir";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String processSubmit(ModelMap model, HttpServletRequest request) {
		cargarElementos(model, request);

		Suscripcion suscripcion = servicioSuscripcion.buscarUnoCorreo(request
				.getParameter("correo"));

		if (suscripcion == null) {
			suscripcion = new Suscripcion();
		}

		suscripcion.setCorreo(request.getParameter("correo"));
		suscripcion.setNombre(request.getParameter("nombre"));
		suscripcion.setCreacion(HelperDate.now());

		String[] idtp = request.getParameterValues("tipoP");

		List<Integer> idtpub = new ArrayList<Integer>();
		if (idtp != null) {
			for (int i = 0; i < idtp.length; i++) {
				idtpub.add(Integer.parseInt(idtp[i]));
			}
		}

		List tiposDeP = servicioTipoPublicacion.buscarEn(idtpub, 'A');

		suscripcion.setTipoPublicacions(new HashSet<TipoPublicacion>(tiposDeP));
		servicioSuscripcion.guardarSuscripcion(suscripcion);

		model.addAttribute("nombre", request.getParameter("nombre"));

		model.addAttribute("correo", request.getParameter("correo"));

		return "SuscribirExitoso";

	}

	private void cargarElementos(ModelMap model, HttpServletRequest request) {
		model.addAttribute("urlRaiz", request.getContextPath());
		model.addAttribute("hospital", servicioHospital.buscarUnico());

		model.addAttribute("servicios",
				servicioTipoServicioWeb.buscarTodos('A'));

		model.addAttribute("tipoPublicaciones",
				servicioTipoPublicacion.buscarTodos('A'));

		model.addAttribute("publicidad", servicioPublicidad.getRand('A'));
	}

}

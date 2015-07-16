package org.ucla.sigma.controladorweb;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.ucla.sigma.modelo.Publicacion;
import org.ucla.sigma.modelo.TipoPublicacion;
import org.ucla.sigma.servicio.ServicioHospital;
import org.ucla.sigma.servicio.ServicioPublicacion;
import org.ucla.sigma.servicio.ServicioPublicidad;
import org.ucla.sigma.servicio.ServicioTipoPublicacion;
import org.ucla.sigma.servicio.ServicioTipoServicioWeb;

@Controller
@RequestMapping("/publicacion")
public class PublicacionController {

	private int limit = 5;

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

	private String anteriorOffset(int limit, int offset, int cantidad) {
		if (offset > 0)
			return "/offset/" + (offset - limit < 0 ? 0 : offset - limit);
		else
			return "";
	}

	private String siguienteOffset(int limit, int offset, int cantidad) {
		int siguiente = offset + limit;
		if (cantidad > siguiente)
			return "/offset/" + siguiente;
		else
			return "";
	}

	private String ultimoOffset(int limit, int offset, int cantidad) {
		return "";
	}

	@RequestMapping(value = "/{tipouri}/offset/{offsetn}", method = RequestMethod.GET)
	public String printPublicacionesOffset(
			@PathVariable("tipouri") String tipoUri,
			@PathVariable("offsetn") int offsetn, ModelMap model,
			HttpServletRequest request) {
		cargarDatos(model, request);

		TipoPublicacion tipoPublicacionActual = servicioTipoPublicacion
				.buscarUnoPorUri(tipoUri, 'A');

		model.addAttribute("tipoPublicacionActual", tipoPublicacionActual);

		int offset = offsetn;
		int cantidad = servicioPublicacion.countPorTipo(tipoPublicacionActual,
				'A');

		model.addAttribute("anterior", anteriorOffset(limit, offset, cantidad));
		model.addAttribute("proximo", siguienteOffset(limit, offset, cantidad));

		List<Publicacion> publicaciones = servicioPublicacion.last(offset,
				limit, tipoPublicacionActual, 'A');
		model.addAttribute("publicaciones", publicaciones);

		return "PublicacionesPorTipo";
	}

	@RequestMapping(value = "/{tipouri}", method = RequestMethod.GET)
	public String printPublicaciones(@PathVariable("tipouri") String tipoUri,
			ModelMap model, HttpServletRequest request) {
		cargarDatos(model, request);

		TipoPublicacion tipoPublicacionActual = servicioTipoPublicacion
				.buscarUnoPorUri(tipoUri, 'A');

		model.addAttribute("tipoPublicacionActual", tipoPublicacionActual);

		int offset = 0;
		int cantidad = servicioPublicacion.countPorTipo(tipoPublicacionActual,
				'A');

		model.addAttribute("anterior", anteriorOffset(limit, offset, cantidad));
		model.addAttribute("proximo", siguienteOffset(limit, offset, cantidad));

		List<Publicacion> publicaciones = servicioPublicacion.last(offset,
				limit, tipoPublicacionActual, 'A');
		model.addAttribute("publicaciones", publicaciones);

		return "PublicacionesPorTipo";
	}

	@RequestMapping(value = "/{tipouri}/{publicacionuri}", method = RequestMethod.GET)
	public String printPublicacion(@PathVariable("tipouri") String tipoUri,
			@PathVariable("publicacionuri") String publicacionUri,
			ModelMap model, HttpServletRequest request) {
		cargarDatos(model, request);

		TipoPublicacion tipoPublicacionActual = servicioTipoPublicacion
				.buscarUnoPorUri(tipoUri, 'A');

		model.addAttribute("tipoPublicacionActual", tipoPublicacionActual);

		Publicacion publicacionActual = servicioPublicacion.buscarUnoPorUri(
				publicacionUri, 'A');

		model.addAttribute("publicacion", publicacionActual);


		model.addAttribute("anterior", servicioPublicacion.anterior(
				publicacionActual, 'A'));
		model.addAttribute("proximo", servicioPublicacion.siguiente(
				publicacionActual, 'A'));

		return "Publicacion";
	}

	private void cargarDatos(ModelMap model, HttpServletRequest request) {
		model.addAttribute("urlRaiz", request.getContextPath());
		model.addAttribute("hospital", servicioHospital.buscarUnico());

		model.addAttribute("servicios",
				servicioTipoServicioWeb.buscarTodos('A'));

		model.addAttribute("tipoPublicaciones",
				servicioTipoPublicacion.buscarTodos('A'));

		model.addAttribute("publicidad", servicioPublicidad.getRand('A'));

	}
}

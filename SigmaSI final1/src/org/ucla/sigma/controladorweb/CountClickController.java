package org.ucla.sigma.controladorweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.ucla.sigma.modelo.Publicidad;
import org.ucla.sigma.servicio.ServicioPublicidad;

@Controller
@RequestMapping("/countclick")
public class CountClickController {

	@Autowired
	private ServicioPublicidad servicioPublicidad;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String count(@PathVariable("id") int id) {
		Publicidad publicidad = servicioPublicidad.buscarUnoPorId(id, 'A');

		publicidad.setClicks(publicidad.getClicks() + 1);
		
		servicioPublicidad.guardarPublicidad(publicidad);
		
		return "redirect:"+publicidad.getEnlace();

	}
}

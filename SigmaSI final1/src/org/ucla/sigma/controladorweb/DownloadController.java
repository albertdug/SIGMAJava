package org.ucla.sigma.controladorweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.ucla.sigma.components.HelperMimeType;
import org.ucla.sigma.modelo.Adjunto;
import org.ucla.sigma.servicio.ServicioAdjunto;

@Controller
@RequestMapping("/download")
public class DownloadController {
	
	@Autowired
	private ServicioAdjunto servicioAdjunto;

	public ServicioAdjunto getServicioAdjunto() {
		return servicioAdjunto;
	}

	public void setServicioAdjunto(ServicioAdjunto servicioAdjunto) {
		this.servicioAdjunto = servicioAdjunto;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> download(@PathVariable("id") int id) {
		Adjunto adjunto = servicioAdjunto.buscarUnoPorId(id, 'A');
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(MediaType.parseMediaType(HelperMimeType.getMimeType(adjunto.getExtension())));
        headers.add("Content-disposition", "attachment; filename="+adjunto.getNombre()+"."+adjunto.getExtension());
		
		return new ResponseEntity<byte[]>(adjunto.getBytes(), headers,
				HttpStatus.CREATED);
	}

}

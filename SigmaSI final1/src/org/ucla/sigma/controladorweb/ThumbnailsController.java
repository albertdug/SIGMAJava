package org.ucla.sigma.controladorweb;

import java.awt.image.BufferedImage;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.ucla.sigma.components.HelperImage;
import org.ucla.sigma.components.HelperMimeType;
import org.ucla.sigma.modelo.Imagen;
import org.ucla.sigma.servicio.ServicioImagen;

@Controller
@RequestMapping("/thumbnails")
public class ThumbnailsController {

	@Autowired
	private ServicioImagen servicioImagen;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> printThumbnails(@PathVariable("id") int id) {
		Imagen imagen = servicioImagen.buscarUnoPorId(id, 'A');
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.parseMediaType(HelperMimeType
				.getMimeType(imagen.getExtension())));

		return new ResponseEntity<byte[]>(imagen.getBytes(), headers,
				HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}/{resize}/{size}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> printThumbnailsResize(
			@PathVariable("id") int id, @PathVariable("resize") String resize,
			@PathVariable("size") Integer size) throws IOException {

		Imagen imagen = servicioImagen.buscarUnoPorId(id, 'A');
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.parseMediaType(HelperMimeType
				.getMimeType(imagen.getExtension())));

		byte[] tempImageBytes = imagen.getBytes();

		BufferedImage tempImage = HelperImage
				.byteArrayToBufferedImage(tempImageBytes);

		if (size > 0) {
			if (resize.equalsIgnoreCase("percent")) {
				tempImage = HelperImage.resizePercent(tempImage, size);
			} else if (resize.equalsIgnoreCase("maxHeight")
					&& tempImage.getHeight() > size) {
				tempImage = HelperImage.resizeMaxHeight(tempImage, size);
			} else if (resize.equalsIgnoreCase("maxWidth")
					&& tempImage.getWidth() > size) {
				tempImage = HelperImage.resizeMaxWidth(tempImage, size);
			}
			tempImageBytes = HelperImage.bufferedImageToByteArray(tempImage,
					imagen.getExtension());

		}

		return new ResponseEntity<byte[]>(tempImageBytes, headers,
				HttpStatus.CREATED);
	}
}

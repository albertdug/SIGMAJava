package org.ucla.sigma.controladorweb;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.ucla.sigma.modelo.Hospital;
import org.ucla.sigma.modelo.Publicacion;
import org.ucla.sigma.servicio.ServicioHospital;
import org.ucla.sigma.servicio.ServicioPublicacion;

@Controller
@RequestMapping("/rss")
public class RssController {

	@Autowired
	private ServicioHospital servicioHospital;

	@Autowired
	private ServicioPublicacion servicioPublicacion;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> printRss(HttpServletRequest request) {

		String baseUrl = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath();

		Hospital hospital = servicioHospital.buscarUnico();
		List<Publicacion> publicaciones = servicioPublicacion.last(0, 10, 'A');
		HttpHeaders headers = new HttpHeaders();

		headers.add("Content-Type", "application/rss+xml; charset=utf-8");

		StringBuffer rss = new StringBuffer();
		rss.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		rss.append("<rss version=\"2.0\">");
		rss.append("<channel>");

		rss.append("<title>");
		rss.append(hospital.getNombre());
		rss.append("</title>");
		rss.append("<link>");
		rss.append(baseUrl);
		rss.append("</link>");
		rss.append("<description>");
		rss.append(hospital.getDescripcion());
		rss.append("</description>");

		rss.append("<image>");
		rss.append("<title>" + hospital.getNombre() + "</title>");
		rss.append("<link>" + baseUrl + "</link>");
		rss.append("<url>" + baseUrl + "/thumbnails/"
				+ hospital.getImagen().getId() + "</url>");
		rss.append("</image>");

		for (int i = 0; i < publicaciones.size(); i++) {
			Publicacion tempPub = publicaciones.get(i);
			rss.append("<item>");
			rss.append("<title>");
			rss.append(tempPub.getTitulo());
			rss.append("</title>");
			rss.append("<link>");
			rss.append(baseUrl + "/publicacion/"
					+ tempPub.getTipoPublicacion().getUri() + "/"
					+ tempPub.getUri());
			rss.append("</link>");
			rss.append("<pubDate>");
			rss.append(tempPub.getCreacion());
			rss.append("</pubDate>");
			rss.append("<description>");
			rss.append("<![CDATA[");

			if (tempPub.getImagen() != null) {
				rss.append("<img src=\"" + baseUrl + "/thumbnails/"
						+ tempPub.getImagen().getId()
						+ "/maxwidth/250\"  align=\"left\" />");
			}

			rss.append(tempPub.getTexto());
			rss.append("]]>");
			rss.append("</description>");
			rss.append("</item>");
		}

		rss.append("</channel>");
		rss.append("</rss>");

		return new ResponseEntity<String>(rss.toString(), headers,
				HttpStatus.CREATED);
	}

}

package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.PublicacionDAO;
import org.ucla.sigma.interfazservicio.IServicioPublicacion;
import org.ucla.sigma.modelo.Area;
import org.ucla.sigma.modelo.Publicacion;
import org.ucla.sigma.modelo.TipoPublicacion;

public class ServicioPublicacion implements IServicioPublicacion, Serializable {

	private PublicacionDAO publicacionDAO;

	public PublicacionDAO getPublicacionDAO() {
		return publicacionDAO;
	}

	public void setPublicacionDAO(PublicacionDAO publicacionDAO) {
		this.publicacionDAO = publicacionDAO;
	}

	@Override
	public void guardarPublicacion(Publicacion obj) {
		obj.setEstatus('A');
		publicacionDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarPublicacion(Publicacion obj) {
		obj.setEstatus('E');
		publicacionDAO.saveOrUpdate(obj);
	}
	
	@Override
	public List<Publicacion> last(int offset, int limit, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.desc("creacion"));
		orden.add(Order.asc("titulo"));
		return publicacionDAO.findByCriterions(Publicacion.class,
				restricciones, orden,offset, limit);
	}

	@Override
	public Publicacion buscarUnoPorUri(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("uri", valor));
		List busqueda = publicacionDAO.findByCriterions(Publicacion.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Publicacion) busqueda.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Publicacion> last(int offset, int limit,
			TipoPublicacion tipoPublicacion, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("tipoPublicacion", tipoPublicacion));
		List orden = new ArrayList();
		orden.add(Order.desc("creacion"));
		orden.add(Order.asc("titulo"));
		return publicacionDAO.findByCriterions(Publicacion.class,
				restricciones, orden, offset, limit);
	}

	@Override
	public List<Publicacion> buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();

		orden.add(Order.desc("creacion"));
		orden.add(Order.asc("titulo"));
		return publicacionDAO.findByCriterions(Publicacion.class,
				restricciones, orden);
	}
	
	@Override
	public List<Publicacion> buscarTodos(TipoPublicacion tipoPublicacion, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("tipoPublicacion", tipoPublicacion));
		List orden = new ArrayList();

		orden.add(Order.desc("creacion"));
		orden.add(Order.asc("titulo"));
		return publicacionDAO.findByCriterions(Publicacion.class,
				restricciones, orden);
	}

	@Override
	public Publicacion siguiente(Publicacion publicacion, char... estatus) {

		List<Publicacion> publicaciones = buscarTodos(publicacion.getTipoPublicacion(), estatus);

		int index = publicaciones.indexOf(publicacion);

		if (index + 1 >= publicaciones.size())
			return null;
		else
			return publicaciones.get(index + 1);
	}

	@Override
	public Publicacion anterior(Publicacion publicacion, char... estatus) {
		List<Publicacion> publicaciones = buscarTodos(publicacion.getTipoPublicacion(), estatus);

		int index = publicaciones.indexOf(publicacion);

		if (index == 0)
			return null;
		else
			return publicaciones.get(index - 1);
	}

	@Override
	public int count(char... estatus) {
		String sql = "select count(*) from publicacion";
		if (estatus.length > 0) {
			sql = "select count(*) from publicacion where estatus = '"
					+ estatus[0] + "'";
		}
		return Integer.parseInt(publicacionDAO.findBySQLQuery(sql).get(0)
				.toString());
	}

	@Override
	public int countPorTipo(TipoPublicacion tipoPublicacionActual,
			char... estatus) {
		String sql = "select count(*) from publicacion where tipo_publicacionid="
				+ tipoPublicacionActual.getId();
		if (estatus.length > 0) {
			sql = "select count(*) from publicacion where tipo_publicacionid="
					+ tipoPublicacionActual.getId() + "and estatus = '"
					+ estatus[0] + "'";
		}
		return Integer.parseInt(publicacionDAO.findBySQLQuery(sql).get(0)
				.toString());
	}
	
	@Override
	public List buscarCoincidencias(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("titulo", valor,
				MatchMode.ANYWHERE));
		List orden = new ArrayList();
		orden.add(Order.asc("titulo"));
		return publicacionDAO.findByCriterions(Publicacion.class, restricciones, orden);
	}

	@Override
	public Publicacion buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("titulo", valor));
		List busqueda = publicacionDAO.findByCriterions(Publicacion.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Publicacion) busqueda.get(0);
		} else {
			return null;
		}
	}

}

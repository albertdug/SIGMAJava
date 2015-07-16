<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<nav>
	<ul>
		<li class="izquierda"><a href="${urlRaiz}">Inicio</a></li>
		<li><a href="${urlRaiz}/conocenos">Conócenos</a></li>
		<li><a>Servicios</a>
			<ul>
				<c:forEach var="servicio" items="${servicios}">
					<li><a href="${urlRaiz}/servicio/${servicio.uri}">${servicio.tipoServicio.nombre}</a></li>
				</c:forEach>
			</ul></li>
		<li><a>Publicaciones</a>
			<ul>
				<c:forEach var="tipoPublicacion" items="${tipoPublicaciones}">
					<li><a href="${urlRaiz}/publicacion/${tipoPublicacion.uri}">${tipoPublicacion.nombre}</a></li>
				</c:forEach>
			</ul></li>
		<li class="float-right derecha"><a href="${urlRaiz}/contacto">Contacto</a></li>
		<li class="limpiar"></li>
	</ul>
</nav>

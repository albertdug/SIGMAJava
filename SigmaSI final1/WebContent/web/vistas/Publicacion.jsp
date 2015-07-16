<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="helperDate" uri="/WEB-INF/jstl/helperDate.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="/web/vistas/porciones/comun.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/web/vistas/porciones/Header.jsp" />
	<jsp:include page="/web/vistas/porciones/barraLateral.jsp" />
	<jsp:include page="/web/vistas/porciones/Menu.jsp" />
	<section>
		<h1 class="float-left">
			<span>${publicacion.generoPublicacion.nombre}</span>
		</h1>
		<a class="azul float-right"
			href="${urlRaiz}/publicacion/${tipoPublicacionActual.uri}">Ir a
			${tipoPublicacionActual.nombre}</a>
		<div class="limpiar"></div>
		<article>
			<h2>
				${publicacion.titulo} <span class="sub-1">Autor: ${publicacion.usuario.persona.nombre}
					${publicacion.usuario.persona.apellido}</span> <span class="sub"> Fecha de publicación:
					${helperDate:formatDate(publicacion.creacion,"dd 'de' MMMM 'del'
					yyyy")}</span>
			</h2>
			<c:if test="${publicacion.imagen != null}">
				<img class="float-left imagen-borde"
					src="${urlRaiz}/thumbnails/${publicacion.imagen.id}/maxwidth/200" />
			</c:if>
			<p>${publicacion.texto}</p>

			<c:if test="${fn:length(publicacion.adjuntos) > 0}">
				<br />
				<h3>Descargas:</h3>
				<ul class="list-2">
					<c:forEach var="adjunto" items="${publicacion.adjuntos}">
						<li><a href="${urlRaiz}/download/${adjunto.id}" class="azul">${adjunto.nombre}</a></li>
					</c:forEach>
				</ul>
			</c:if>
			<div class="limpiar"></div>
			<c:if test="${anterior != null || proximo != null}">
			<br/>
				<div class="offset">
					<c:if test="${anterior != null}">
						<a href="${urlRaiz}/publicacion/${tipoPublicacionActual.uri}/${anterior.uri}"
							class="botonp float-left">&lsaquo;&lsaquo; anterior</a>
					</c:if>
					<c:if test="${proximo != null}">
						<a href="${urlRaiz}/publicacion/${tipoPublicacionActual.uri}/${proximo.uri}"
							class="botonp float-right">siguiente &rsaquo;&rsaquo;</a>
					</c:if>
					<div class="limpiar"></div>
				</div>
			</c:if>
			<div class="limpiar"></div>
		</article>
	</section>
	<jsp:include page="/web/vistas/porciones/piePagina.jsp" />
</body>
</html>
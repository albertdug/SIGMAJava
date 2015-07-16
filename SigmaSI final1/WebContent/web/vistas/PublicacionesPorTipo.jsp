<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="helperDate" uri="/WEB-INF/jstl/helperDate.tld"%>
<%@ taglib prefix="helperString" uri="/WEB-INF/jstl/helperString.tld"%>
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
		<h1>${tipoPublicacionActual.nombre}</h1>

		<c:choose>
			<c:when test="${fn:length(publicaciones) > 0}">
				<article>
					<ul class="list-1">
						<c:forEach var="publicacion" items="${publicaciones}">
							<li><a class="verde"
								href="${urlRaiz}/publicacion/${tipoPublicacionActual.uri}/${publicacion.uri}">${publicacion.titulo}<span>Autor: ${publicacion.usuario.persona.nombre}
										${publicacion.usuario.persona.apellido}</span><span class="gris">Fecha de publicación: ${helperDate:formatDate(publicacion.creacion,"dd
										'de' MMMM 'del' yyyy")}</span></a> <c:if
									test="${publicacion.imagen != null}">
									<img class="float-left imagen-borde"
										src="${urlRaiz}/thumbnails/${publicacion.imagen.id}/maxwidth/100" />
								</c:if>${helperString:wordsLimiter(publicacion.texto,60)}
								<div class="limpiar"></div> <a class="verde alingt-right"
								href="${urlRaiz}/publicacion/${tipoPublicacionActual.uri}/${publicacion.uri}">(Leer
									más)</a></li>

						</c:forEach>
					</ul>
				</article>
				<c:if test="${anterior != '' || proximo != ''}">
					<br />
					<div class="offset">
						<c:if test="${anterior != ''}">
							<a
								href="${urlRaiz}/publicacion/${tipoPublicacionActual.uri}${anterior}"
								class="botonp float-left">&lsaquo;&lsaquo; anterior</a>
						</c:if>
						<c:if test="${proximo != ''}">
							<a
								href="${urlRaiz}/publicacion/${tipoPublicacionActual.uri}${proximo}"
								class="botonp float-right">siguiente &rsaquo;&rsaquo;</a>
						</c:if>
						<div class="limpiar"></div>
					</div>
				</c:if>
			</c:when>
			<c:otherwise>
           		No se encontraron publicaciones
        	</c:otherwise>
		</c:choose>

	</section>
	<jsp:include page="/web/vistas/porciones/piePagina.jsp" />
</body>
</html>
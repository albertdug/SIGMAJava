<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<article>
			<h1>${servicioWeb.tipoServicio.nombre}</h1>

			<p>${servicioWeb.descripcion}</p>
			<c:if test="${fn:length(servicioWeb.tipoServicio.servicios) > 0}">
				<h2>Ofrecemos nuestros servicios en:</h2>
				<ul class="list-2">
					<c:forEach var="servicio"
						items="${servicioWeb.tipoServicio.servicios}">
						<li>${servicio.nombre}</li>
					</c:forEach>
				</ul>
			</c:if>
			<div class="limpiar"></div>
		</article>
	</section>
	<jsp:include page="/web/vistas/porciones/piePagina.jsp" />
</body>
</html>
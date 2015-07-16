<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="/web/vistas/porciones/comun.jsp"></jsp:include>
<script type="text/javascript">
	$(document).ready(function() {
		$("#msj").hide();
		$("#enviar").click(function() {
			var noselect = true;			
			if (!$(".chktp").is(":checked")) {
				$("#msj").slideDown(300);
				$("#msj").delay(10000).slideUp(300);
			} else {
				$("#sus-save").submit();
			}

		});
	});
</script>
</head>
<body>
	<jsp:include page="/web/vistas/porciones/Header.jsp" />
	<jsp:include page="/web/vistas/porciones/barraLateral.jsp" />
	<jsp:include page="/web/vistas/porciones/Menu.jsp" />
	<section>
		<article>
				
			<div class="msj-error" id="msj">Debe seleccionar al menos una categoría</div>
			<h1>
				SUSCRIBIR <span>A NOTICIAS</span>
			</h1>
			<form action="suscribir/save" method="post" id="sus-save">
				<input class="caja-texto" value="${nombre}" readonly="readonly"
					name="nombre" /> <input class="caja-texto" value="${correo}"
					readonly="readonly" name="correo" />

				<h2>Categorías</h2>
				<ul class="list-2">
				<c:forEach var="tipop" items="${tipoPublicaciones}">

					<li><input name="tipoP" type="checkbox" value="${tipop.id}"
						style="margin-right: 10px;" class="chktp">
					<span style="margin-right: 10px;">${tipop.nombre}</span></li>

				</c:forEach>
				</ul>
				<br /> <input class="boton" type="button" name="enviar"
					value="ENVIAR" id="enviar" />

			</form>
		</article>
	</section>
	<jsp:include page="/web/vistas/porciones/piePagina.jsp" />
</body>
</html>
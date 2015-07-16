<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
			<h1>EL HOSPITAL</h1>
			<p>${hospital.descripcion}</p>
		</article>
		<article>
			<h1>MISIÓN</h1>
			<p>${hospital.mision}</p>
		</article>
		<article>
			<h1>VISIÓN</h1>
			<p>${hospital.vision}</p>
		</article>
		
		<img alt="" class="imagen-borde" src="web/imagenes/banner-conocenos.png">
		
	</section>
	<jsp:include page="/web/vistas/porciones/piePagina.jsp" />
</body>
</html>
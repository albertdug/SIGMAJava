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
			<h1>SUSCRIBIR <span>A NOTICIAS</span></h1>
			<strong>${nombre}</strong>, tus datos han sido guardados exitosamente, se enviarán correos a:<strong> ${correo}</strong>
		</article>		
	</section>
	<jsp:include page="/web/vistas/porciones/piePagina.jsp" />
</body>
</html>
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
		var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
		var number = /[0-9]+/;
		$("#enviar").click(function() {
			if ($.trim($("#nombre").val()) == "") {
				$("#lblnombre").text("EL NOMBRE ES REQUERIDO");
				$("#lblnombre").addClass("label-caja-texto-error");
				$("#nombre").val(null);
				$("#nombre").focus();
				$("#enviar").focus();
				$("#nombre").focus();
			} else if (number.test($.trim($("#nombre").val()))) {
				$("#lblnombre").text("EL NOMBRE NO PUEDE CONTENER NUMEROS");
				$("#lblnombre").addClass("label-caja-texto-error");
				$("#nombre").val(null);
				$("#nombre").focus();
				$("#enviar").focus();
				$("#nombre").focus();
			} else if ($.trim($("#apellido").val()) == "") {
				$("#lblapellido").text("EL APELLIDO ES REQUERIDO");
				$("#lblapellido").addClass("label-caja-texto-error");
				$("#apellido").val(null);
				$("#apellido").focus();
				$("#enviar").focus();
				$("#apellido").focus();
			} else if (number.test($.trim($("#apellido").val()))) {
				$("#lblapellido").text("EL APELLIDO NO PUEDE CONTENER NUMEROS");
				$("#lblapellido").addClass("label-caja-texto-error");
				$("#apellido").val(null);
				$("#apellido").focus();
				$("#enviar").focus();
				$("#apellido").focus();
			} else if ($.trim($("#correo").val()) == "") {
				$("#lblcorreo").text("EL CORREO ES REQUERIDO");
				$("#lblcorreo").addClass("label-caja-texto-error");
				$("#correo").val(null);
				$("#correo").focus();
				$("#enviar").focus();
				$("#correo").focus();
			} else if (!emailReg.test(($("#correo").val()))) {
				$("#lblcorreo").text("EL CORREO NO ES VÁLIDO");
				$("#lblcorreo").addClass("label-caja-texto-error");
				$("#correo").val(null);
				$("#correo").focus();
				$("#enviar").focus();
				$("#correo").focus();
			} else if ($.trim($("#telefono").val()) != "" && !$.isNumeric($.trim($("#telefono").val()))) {
				$("#lbltelefono").text("EL TELÉFONO DEBE SER NUMÉRICO");
				$("#lbltelefono").addClass("label-caja-texto-error");
				$("#telefono").val(null);
				$("#telefono").focus();
				$("#enviar").focus();
				$("#telefono").focus();	
			} else if ($.trim($("#observacion").val()) == "") {
				$("#lblobservacion").text("¿CUAL ES TU CONSULTA?");
				$("#lblobservacion").addClass("label-caja-texto-error");
				$("#observacion").val(null);
				$("#observacion").focus();
				$("#enviar").focus();
				$("#observacion").focus();
			}  else {
				$("#contacto").submit();
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

		<c:if test="${mensaje != null}">
			<script type="text/javascript">
				$(document).ready(function() {
					$("#msj").delay(10000).slideUp(300);
				});
			</script>
			<div class="msj" id="msj">${mensaje}</div>
		</c:if>

		<h1>CONTACTO</h1>
		<article>Escríbenos a <strong>${hospital.correoA}</strong>, o a través
			del siguiente formulario.</article>
		<article>
			<form action="" method="post" id="contacto">
			 <label class="label-caja-texto"
					for="nombre" id="lblnombre">NOMBRE</label> <input
					class="caja-texto" type="text" name="nombre" id="nombre" /> <label
					class="label-caja-texto" for="apellido" id="lblapellido">APELLIDO</label>
				<input class="caja-texto" type="text" name="apellido" id="apellido" />

				<label class="label-caja-texto" for="correo" id="lblcorreo">CORREO
					ELECTRÓNICO</label> <input class="caja-texto" type="text" name="correo"
					id="correo" /> <label class="label-caja-texto" for="telefono"
					id="lbltelefono">TELÉFONO</label> <input class="caja-texto"
					type="text" name="telefono" id="telefono" /> <label
					class="label-caja-texto" for="observacion" id="lblobservacion">INQUIETUD
					O CONSULTA</label>
				<textarea class="caja-texto" id="observacion" name="observacion"
					rows="8"></textarea>

				<input class="boton" type="button" name="enviar" value="ENVIAR"
					id="enviar" />
			</form>
		</article>
	</section>
	<jsp:include page="/web/vistas/porciones/piePagina.jsp" />
</body>
</html>
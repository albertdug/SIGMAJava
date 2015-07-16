<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<aside>
	<h1>
		LOGIN <span>USUARIOS</span>
	</h1>
	<input class="boton" type="button" name="some_name" value="ENTRAR"
		id="aside-entrar" />
		 <a href="${urlRaiz}/countclick/${publicidad.id}"	class="imagen-enlace"> 
		 <img	src="${urlRaiz}/thumbnails/${publicidad.imagen.id}" alt="${publicidad.titulo}" width="250px" height="250px" />
	</a>
	<h1>HORARIO</h1>
	<p>${hospital.horario}</p>
	<h1>DIRECCIÓN</h1>
	<p>${hospital.direccion}</p>

	<h1>
		SUSCRÍBETE <span>A NOTICIAS</span>
	</h1>
	<p>Recibe nuestras publicaciones en tu correo electrónico. Suscríbete o actualiza tu suscripción</p>
	
	<script type="text/javascript">
	$(document).ready(function() {
		var nlemailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
		var nlnumber = /[0-9]+/;
		$("#newsletter-suscribir").click(function() {
			if ($.trim($("#newsletter-nombre").val()) == "") {
				$("#lblnewsletter-nombre").text("EL NOMBRE ES REQUERIDO");
				$("#lblnewsletter-nombre").addClass("label-caja-texto-error");
				$("#newsletter-nombre").val(null);
				$("#newsletter-nombre").focus();
				$("#newsletter-suscribir").focus();
				$("#newsletter-nombre").focus();
			} else if (nlnumber.test($.trim($("#newsletter-nombre").val()))) {
				$("#lblnewsletter-nombre").text("EL NOMBRE ES INVALIDO");
				$("#lblnewsletter-nombre").addClass("label-caja-texto-error");
				$("#newsletter-nombre").val(null);
				$("#newsletter-nombre").focus();
				$("#newsletter-suscribir").focus();
				$("#newsletter-nombre").focus();
			}  else if ($.trim($("#newsletter-correo").val()) == "") {
				$("#lblnewsletter-correo").text("EL CORREO ES REQUERIDO");
				$("#lblnewsletter-correo").addClass("label-caja-texto-error");
				$("#newsletter-correo").val(null);
				$("#newsletter-correo").focus();
				$("#newsletter-suscribir").focus();
				$("#newsletter-correo").focus();
			} else if (!nlemailReg.test(($("#newsletter-correo").val()))) {
				$("#lblnewsletter-correo").text("EL CORREO NO ES VÁLIDO");
				$("#lblnewsletter-correo").addClass("label-caja-texto-error");
				$("#newsletter-correo").val(null);
				$("#newsletter-correo").focus();
				$("#newsletter-suscribir").focus();
				$("#newsletter-correo").focus();
			} else {
				$("#form-nl").submit();
			}

		});

	});
</script>
	
	<form action="${urlRaiz}/suscribir" method="post" id="form-nl">
		<label class="label-caja-texto" for="newsletter-nombre" id="lblnewsletter-nombre">NOMBRE</label>
		<input class="caja-texto" type="text" name="newsletter-nombre"
			value="" id="newsletter-nombre" /> <label class="label-caja-texto"
			for="newsletter-correo" id="lblnewsletter-correo">CORREO</label> <input class="caja-texto"
			type="text" name="newsletter-correo" value="" id="newsletter-correo" />

		<input class="boton" type="button" name="newsletter-suscribir"
			value="SUSCRIBIR" id="newsletter-suscribir" />
	</form>


	<script charset="utf-8" src="http://widgets.twimg.com/j/2/widget.js"></script>
	<script>
		new TWTR.Widget({
			version : 2,
			type : 'profile',
			rpp : 4,
			interval : 30000,
			width : 'auto',
			height : 200,
			theme : {
				shell : {
					background : '#70bbec',
					color : '#ffffff'
				},
				tweets : {
					background : '#ffffff',
					color : '#000000',
					links : '#70bbec'
				}
			},
			features : {
				scrollbar : true,
				loop : false,
				live : true,
				behavior : 'default'
			}
		}).render().setUser('${hospital.twitter}').start();
	</script>
	<br />
</aside>

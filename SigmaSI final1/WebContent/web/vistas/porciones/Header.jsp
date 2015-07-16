<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
	<div id="top">
		<img class="float-left" src="${urlRaiz}/thumbnails/${hospital.imagen.id}"
			alt="Hospital Veterinario" height="70px" /> <img class="float-right"
			src="${urlRaiz}/web/imagenes/logo-ucla.png" alt="UCLA" height="70px" />
		<div id="nombre-hosp">${hospital.nombre}</div>
		<div id="redes-sociales">

			<a href="${urlRaiz}/rss"> <img src="${urlRaiz}/web/imagenes/rss_32.png" alt="RSS" />
			</a>
			<c:forEach var="difusion" items="${hospital.hospitalDifusions}">

				<c:if test="${difusion.estatus == 'A'}">
					<a href="${difusion.enlace}"> <img
						src="${urlRaiz}/thumbnails/${difusion.difusion.imagen.id}"
						alt="${difusion.difusion.nombre}" width="32px" height="32px" />
					</a>
				</c:if>

			</c:forEach>
		</div>
		<div class="limpiar"></div>
	</div>
	<div id="top-banner">
		<img id="imagen-1" src="${urlRaiz}/web/imagenes/imagen-1.png" alt="" />
		<div id="texto-1">
			<img id="imagen-1" src="${urlRaiz}/web/imagenes/linea-banner.png" alt="" />
			<h2>${hospital.slogan}</h2>
		</div>
		<div id="texto-2">
			<h1>
				¡Bienvenido <span>La mejor atención a su mascota!</span>
			</h1>
			<p>Nuestro objetivo: ${hospital.objetivo}</p>
		</div>
		<img id="imagen-2" src="${urlRaiz}/web/imagenes/imagen-2.png" alt="" />
	</div>
</header>

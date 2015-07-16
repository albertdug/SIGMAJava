<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<title>${hospital.nombre}</title>
<link  href="rss" rel="alternate" type="application/rss+xml" title="${hospital.nombre}" />
<link rel="shortcut icon" type="image/vnd.microsoft.icon" href="${urlRaiz}/web/imagenes/favicon.ico">
<link rel="stylesheet" type="text/css" href="${urlRaiz}/web/css/estilo.css" />
<script type="text/javascript" src="${urlRaiz}/web/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="${urlRaiz}/web/js/jquery.infieldlabel.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("label").inFieldLabels();
		$("#aside-entrar").click(function() {
			window.location = "${urlRaiz}/sigma/login.zul";
		});
	});
</script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es-ES">
<%@include file="fragments/head.html"%>
<body>
	<div id="page">
<jsp:include page="fragments/banner.jsp"/>
		<div id="main">
			<div id="errorheader">Error en la aplicacion:</div>
			<table id="errorinfo">
				<tr>
					<td>Error:</td><td>${error}</td>
				</tr>
				<tr>
					<td>Mensaje:</td><td>${message}</td>
				</tr>
				<tr>
					<td>Fecha:</td><td>${timestamp}</td>
				</tr>
			</table>
			<div id="buttons">
				<button type='button' onclick='javascript:history.back()'>Volver</button>
			</div>
		</div>
	</div>
</body>
</html>

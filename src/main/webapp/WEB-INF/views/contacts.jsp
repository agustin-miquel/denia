<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es-ES">
<%@include file="fragments/head.html"%>
<body>
	<div id="page">
<jsp:include page="fragments/banner.jsp"/>
		<div id="main">
			<div class="title">Lista de Contactos</div>
			<table class='list'>
				<tr>
					<th>Fecha</th>
					<th>Hora</th>
					<th>Nombre</th>
					<th>Email</th>
					<th>Teléfono</th>
					<th>Asunto</th>
					<th>Resultado</th>
				</tr>
				<c:forEach items="${contactList}" var="contact">
					<tr>
						<td><fmt:formatDate type="date" value="${contact.contactDate}" /></td>
						<td><fmt:formatDate type="time" value="${contact.contactDate}" /></td>
						<td>${contact.name}</td>
						<td>${contact.email}</td>
						<td>${contact.phone}</td>
						<td>${contact.description}</td>
						<td>${contact.result}</td>
					</tr>
				</c:forEach>
			</table>
			<div id="buttons">
				<button type="button" onclick="location.href='/denia/admin/menu'">Volver</button>
				<button type="button" onclick="location.href='/denia/${pageName}'">Salir</button>
			</div>
		</div>
<jsp:include page="fragments/footer.jsp"/>
	</div>
</body>
</html>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es-ES">
<%@include file="fragments/head.html"%>
<body>
	<div id="page">
<jsp:include page="fragments/banner.jsp"/>
		<div id="main">
			<div class="title">Datos del Cliente</div>
			<form role="form" action="/denia/admin/updateClient" method="post">
				<table>
					<tr>
						<td><span class='label'>Actividad</span></td>
						<td><input type="text" name="Page.description" value="${page.description}" size='30' onchange="changed();"></td>
					</tr>
					<tr>
						<td><span class='label'>Email</span></td>
						<td><input type="text" name="Page.email" value="${page.email}" size='30' onchange="changed();"></td>
					</tr>
					<tr>
						<td><span class='sectionlabel'>Dirección:</span></td>
					<tr>
					<tr>
						<td><span class='label'>Nombre</span></td>
						<td><input type="text" name="Address.name" value="${page.address.name}" size='30' onchange="changed();"></td>
					</tr>
					<tr>
						<td><span class='label'>Calle</span></td>
						<td><input type="text" name="Address.street" value="${page.address.street}" size='30' onchange="changed();"></td>
					</tr>
					<tr>
						<td><span class='label'>Ciudad</span></td>
						<td><input type="text" name="Address.town" value="${page.address.town}" size='30' onchange="changed();"></td>
					</tr>
					<tr>
						<td><span class='label'>Cod.Postal</span></td>
						<td><input type="text" name="Address.postalCode" value="${page.address.postalCode}" size='8' onchange="changed();"></td>
					</tr>
					<tr>
						<td><span class='label'>Teléfono</span></td>
						<td><input type="text" name="Address.phone" value="${page.address.phone}" size='16' onchange="changed();"></td>
					</tr>
				</table>

				<input type="hidden" id="changes" name="changes" value="0">

				<div id="buttons">
					<button type="submit" class="ok">Actualizar</button>
					<button type="button" class="cancel" onclick="if( checkChanges() ) location.href='/denia/admin/content';">Volver</button>
				</div>
			</form>
			<br/>
			<div id="message" class="message">${message}</div>
			<div id="error" class="error">${error}</div>
		</div>
<jsp:include page="fragments/footer.jsp"/>
	</div>
</body>
</html>


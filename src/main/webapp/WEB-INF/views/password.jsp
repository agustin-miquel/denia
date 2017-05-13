<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es-ES">
<%@include file="fragments/head.html"%>
<body>
	<div id="page">
<jsp:include page="fragments/banner.jsp"/>
		<div id="main">
			<div class="title">Cambiar contraseña:</div>
			<form action="<c:url value='/admin/changePassword' />" method='POST'>
				<table>
					<tr><td>
					Contraseña actual:
					</td><td> 
					<input type="password" id="currentPassword" name="currentPassword" autofocus="autofocus">
					</td></tr>

					<tr><td>
					Nueva contraseña:
					</td><td> 
					<input type="password" id="newPassword" name="newPassword">
					</td></tr>

					<tr><td>
					Confirmar nueva contraseña:
					</td><td> 
					<input type="password" id="repeatPassword" name="repeatPassword">
					</td><td> 
					<a href="#" onclick="toggle_password();" id="showhide">Ver contraseña</a>
					</td></tr>
				</table>
				<div id="buttons">
					<button type="submit" class="ok">Cambiar</button>
					<button type="button" onclick="location.href='/denia/admin/menu'">Volver</button>
					<button type="button" onclick="location.href='/denia/${pageName}'">Salir</button>
				</div>
			</form>
			<br/>
			<div id="error" class="error">${error}</div>
		</div>
<jsp:include page="fragments/footer.jsp"/>
	</div>
</body>
</html>


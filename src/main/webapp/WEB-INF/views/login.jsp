<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es-ES">
<%@include file="fragments/head.html"%>
<body>
	<div id="page">
<jsp:include page="fragments/banner.jsp"/>
		<div id="main">
			<div id="login">
				<div class='info'>Iniciar sesión:</div>
				<form action="<c:url value='/login' />" method='POST'>
					<table>
						<tr>
							<td><b>Usuario:</b></td>
							<td><input type="text" id="username" name="username" value="${pageName}" readonly="readonly"></td>
						</tr>
						<tr>
							<td><b>Contraseña:</b></td>
							<td><input type="password" id="password" name="password" autofocus="autofocus"></td>
						</tr>
					</table>
					<button type="submit" class="ok" style="float:left;">Acceder</button>
					<button type="button" class="loginexit" onclick="javascript:location='/denia/${pageName}'">Salir</button>
				</form>

				<div id="login_messages">				
					<c:if test="${param.error}">
						<div class="error">Usuario/contraseña no válidos</div>
					</c:if>
					<c:if test="${param.logout}">
						<div class="login_msg">Desconectado</div>
					</c:if>
					<c:if test="${passwordChanged}">
						<div class="login_msg">Se ha cambiado la contraseña</div>
					</c:if>
				</div>
			</div>
		</div>
<jsp:include page="fragments/footer.jsp"/>
	</div>
</body>
</html>
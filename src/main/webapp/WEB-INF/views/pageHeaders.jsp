<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es-ES">
<%@include file="fragments/head.html"%>
<body>
	<div id="page">
<jsp:include page="fragments/banner.jsp"/>
		<div id="main">
			<div class="title">Cabeceras de la Página</div>
			<form role="form" action="/denia/admin/updateHeaders" method="post">
				<table>
					<tr>
						<td><span class="label">title</span></td>
						<td><input type="text" name="Head.title" value="${page.head.title}" size="50" onchange="changed();"></td>
					</tr>
					<tr>
						<td><span class="label">description</span></td
						><td><input type="text" name="Head.description" value="${page.head.description}" size="50" onchange="changed();"></td>
					</tr>
					<tr>
						<td><span class="label">facebook</span></td>
						<td>
							<input type="checkbox" name="Head.facebook" value="true" <c:if test="${page.head.facebook == true}">checked</c:if> onchange="changed();"> 
	  		   				<input type="hidden" name="Head.facebook" value="false">
						</td>
					</tr>
					<tr>
						<td><span class="label">twitter</span></td>
						<td>
							<input type="checkbox" name="Head.twitter" value="true" <c:if test="${page.head.twitter == true}">checked</c:if> onchange="changed();"> 
	  		   				<input type="hidden" name="Head.twitter" value="false">
						</td>
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


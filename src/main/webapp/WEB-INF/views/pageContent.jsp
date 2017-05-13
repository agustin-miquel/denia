<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es-ES">
<%@include file="fragments/head.html"%>
<body>
	<div id="page">
<jsp:include page="fragments/banner.jsp"/>
		<div id="main">
			<div class="title">Contenido de la Página</div>
			<form role="form" action="/denia/admin/updateContent" method="post" enctype="multipart/form-data">
				<table>
					<tr>
						<td><span class='label'>Cabecera 1</span></td>
						<td colspan='2'><input type="text" name="Body.h1" value="${page.body.h1}" size='50' onchange="changed();"></td>
					</tr>
					<tr>
						<td><span class='label'>Cabecera 2</span></td>
						<td colspan='2'><input type="text" name="Body.h2" value="${page.body.h2}" size='50' onchange="changed();"></td>
					</tr>
					<tr>
						<td><span class='label'>Secciones</span></td>
						<td>
							<input type="checkbox" name="Body.showSections" value="true" <c:if test="${page.body.showSections == true}">checked</c:if> onchange="changed();"> 
	  		   				<input type="hidden" name="Body.showSections" value="false">
						</td>
						<td>
							<a href='sectionsConfig' class='ok'>Configurar Secciones</a>
						</td>
					</tr>
					<tr>
						<td><span class='label'>Contacto</span></td>
						<td>
							<input type="checkbox" name="Body.showContact" value="true" <c:if test="${page.body.showContact == true}">checked</c:if> onchange="changed();"> 
	  		   				<input type="hidden" name="Body.showContact" value="false">
						</td>
					</tr>
					<tr>
						<td><span class='label'>Mapa</span></td>
						<td>
							<input type="checkbox" name="Body.showMap" value="true" <c:if test="${page.body.showMap == true}">checked</c:if> onchange="changed();"> 
	  		   				<input type="hidden" name="Body.showMap" value="false">
						</td>
					</tr>
					<tr>
						<td><span class='label'>Imagen logo:</span></td>
						<td colspan='2'><input id='logoFile' type='file' name='logo' size='35' accept='image/*' onchange="changed();"></td>
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


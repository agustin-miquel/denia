<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es-ES">
<%@include file="fragments/head.html"%>
<body>
	<div id="page">
<jsp:include page="fragments/banner.jsp"/>
		<div id="main">
			<div class="title">Secciones</div>
			<table id="sections">
				<tr>
					<th>Id.</th>
					<th>Título</th>
					<th>Mostrar Sección&nbsp;&nbsp;</th>
					<th>Mostrar en Menú&nbsp;&nbsp;</th>
					<th colspan="2">
						<button type="button" class='new' onclick="javascript:location='newSection'">Nueva Sección</button>
					</th>
				</tr>
				<c:forEach items="${sectionList}" var="section">
					<tr>
						<td>${section.id}</td>
						<td>${section.title}</td>

						<td><input type="checkbox" <c:if test="${section.showSection == true}">checked="checked"</c:if> 
									onchange="showSection(${section.id})"/></td>
						<td><input type="checkbox" <c:if test="${section.showInMenu == true}">checked="checked"</c:if>
									onchange="showInMenu(${section.id})"/></td>

						<td><button type="button" class='ok' onclick="javascript:location='editSection?section=${section.id}'">Editar</button>
						<td><button type="button" class='del' onclick="removeSection(${section.id},this)">Eliminar</button>
					</tr>
				</c:forEach>
			</table>
			<br/>
			<div id='uploader'>
				<form method='POST' enctype='multipart/form-data' action='/denia/admin/upload'>
					<div style="overflow-x:auto;">
					<table>
						<tr>
							<td><div class='label'>Subir imágenes:</div></td>
						</tr>
						<tr>	
							<td><input type='file' name='file' size='35' accept='image/*'></td>
						</tr>
						<tr>
							<td><button type='submit' class='ok'>Subir</button></td>
						</tr>
					</table>
					</div>
				</form>
			</div>
			<div id="buttons">
				<button type="button" onclick="location.href='/denia/admin/pageContent'">Volver</button>	
				<button type="button" onclick="location.href='/denia/${pageName}'">Salir</button>
			</div>
			<br/>
			<div id="message" class="message">${message}</div>
			<div id="error" class="error">${error}</div>
		</div>
<jsp:include page="fragments/footer.jsp"/>
	</div>
</body>
</html>


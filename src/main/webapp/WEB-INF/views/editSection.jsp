<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es-ES">
<%@include file="fragments/head.html"%>
<body>
	<div id="page">
<jsp:include page="fragments/banner.jsp"/>
		<div id="main">
			<div class="title">Editar Sección</div>
			<form action="updateSection" method="POST">
				<input type="hidden" name="id" value="${section.id}"/>

				<span class='label'>Título: &nbsp;</span>
				<input type="text" name="title" value="${section.title}">
				<br/>
				<span class='label'>Contenido:</span>
				<textarea name='content' id='content' rows='10' cols='80'>${section.content}</textarea>
				<script>
					CKEDITOR.replace( 'content', {
						language: 'es',
						extraPlugins: 'imagebrowser',
						imageBrowser_listUrl: '${listOfFiles}'
					});
				</script>
				<div id="buttons">
					<button type="submit" class="ok">Grabar cambios</button>
					<button type="button" onclick="location.href='/denia/admin/sectionsConfig'">Cancelar</button>
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


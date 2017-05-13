<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang='es-ES'>
<%@include file="fragments/head.html"%>
<body>
	<div id="page">
<jsp:include page="fragments/banner.jsp"/>
		<div id="main">
			<div class="title">${menuTitle}</div>
			<ul>
			<c:forEach var="menuItem" items="${menuItems}">
				<li>
					<a href="${menuItem[0]}"><span class="menuitem">${menuItem[1]}</span></a>
				</li>
			 </c:forEach>
			 </ul>
			<div id="buttons">
				<c:if test="${not menuGeneral}">
					<button type="button" onclick="location.href='/denia/admin/menu'">Volver</button>
				</c:if>
				<button type="button" onclick="location.href='/denia/${pageName}'">Salir</button>
			</div>
			<noscript>
			    <div class="error">
			    You don't have javascript enabled. Some functions will not work.
			    </div>
			</noscript>
		</div>
<jsp:include page="fragments/footer.jsp"/>
	</div>
</body>
</html>


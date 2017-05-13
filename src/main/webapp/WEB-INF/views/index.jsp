<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang='es-ES'>
<head>
	<meta charset='UTF-8'/>
	<meta name='viewport' content='width=device-width,initial-scale=1'/>
	<link rel='shortcut icon' href='favicon.ico'/>
	<title>Denia Content Manager</title>
	<meta name='description' content='Gestor de contenidos para empresas de servicios en Denia'/>
	<link rel='stylesheet' href='resources/common/pages.css' type='text/css' media='all'/>
	<link rel='stylesheet' href='resources/common/index.css' type='text/css' media='all'/>
	<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Roboto:400,400i,700'/>
</head>
<body>
	<div id='page'>
		<header id='banner1' role='banner'>
			<img id='mainlogo' src='/denia/resources/common/images/logo.png' alt='logo'>
			<div id='logotext'>
				<div id='logotext1'>Dénia</div>
				<div id='logotext2'>Content Manager</div>
			</div>
		</header>
		<section id='main' role='main'>
			<div id='lista'>
				<h2>Directorio de Servicios:</h2>
				<ul>
				<c:forEach var="page" items="${pages}">
					<li>
					    <a href="${page.pageName}" >
					        <span id="pagename">${page.id}. ${page.pageName}</span> <span id="pagedesc"> ${page.description}</span>
					    </a>
				    </li>
				</c:forEach>
				</ul>
			</div>
			<div id="mapa" class="pconly" style="float:right; margin-top:60px; margin-right: 20px; width:400px;">
				<iframe width="400" height="275" style="border:0;" src="https://www.google.com/maps/embed/v1/place?key=AIzaSyC5oZfGoLNpLggdTCyCQrrDDE-B2fYNmrs &q=Denia"></iframe>
			</div>		
	
			<div class="mobileonly" style="width:100%; display:flex;">
				<div class="mobileonly" style="text-align:center; margin:auto; width:300px;">
					<iframe width="300" height="225" style="border:0;" src="https://www.google.com/maps/embed/v1/place?key=AIzaSyC5oZfGoLNpLggdTCyCQrrDDE-B2fYNmrs &q=Denia"></iframe>
				</div>
			</div>		
		</section>
		<section id='indexfooter'>
	<jsp:include page="fragments/footer.jsp"/>
		</section>
	</div>
</body>
</html>


<%@tag import="ip.vigilante.service.UserService"%>
<%@tag import="ip.vigilante.model.User"%>
<%@ tag description="Generic page template" language="java" pageEncoding="UTF-8"%>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="msapplication-TileColor" content="#ffffff">
	<meta name="msapplication-TileImage" content="/ms-icon-144x144.png">
	<meta name="theme-color" content="#ffffff">
	
	<link rel="apple-touch-icon" sizes="57x57" href="resources/apple-icon-57x57.png">
	<link rel="apple-touch-icon" sizes="60x60" href="resources/apple-icon-60x60.png">
	<link rel="apple-touch-icon" sizes="72x72" href="resources/apple-icon-72x72.png">
	<link rel="apple-touch-icon" sizes="76x76" href="resources/apple-icon-76x76.png">
	<link rel="apple-touch-icon" sizes="114x114" href="resources/apple-icon-114x114.png">
	<link rel="apple-touch-icon" sizes="120x120" href="resources/apple-icon-120x120.png">
	<link rel="apple-touch-icon" sizes="144x144" href="resources/apple-icon-144x144.png">
	<link rel="apple-touch-icon" sizes="152x152" href="resources/apple-icon-152x152.png">
	<link rel="apple-touch-icon" sizes="180x180" href="resources/apple-icon-180x180.png">
	<link rel="icon" type="image/png" sizes="192x192"  href="resources/android-icon-192x192.png">
	<link rel="icon" type="image/png" sizes="32x32" href="resources/favicon-32x32.png">
	<link rel="icon" type="image/png" sizes="96x96" href="resources/favicon-96x96.png">
	<link rel="icon" type="image/png" sizes="16x16" href="resources/favicon-16x16.png">
	<link rel="manifest" href="/manifest.json">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="/EmergencyApp/style/style.css" type="text/css">
	<link rel="canonical" href="https://getbootstrap.com/docs/4.0/examples/navbars/">
</head>

<%
	User user = null;
	Object userIdObj = session.getAttribute("userId");
	
	if(userIdObj != null) {
		int id = (int)userIdObj;
		user = UserService.getInstance().getUserById(id);
	}
	
	session.setAttribute("user", user);
%>

<html>
	<div id="page-header">
		<nav class="navbar navbar-expand-lg navbar-dark bg-navbar-violet">
			<div class="container">
				<a class="navbar-brand" href="/EmergencyApp/home">Home</a>
				<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#menu-links" aria-controls="menu-links" aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>

				<div class="collapse navbar-collapse" id="menu-links">
					<ul class="navbar-nav ml-auto">
						<c:if test = "${user != null && user.isApproved()}">
							<li class="nav-item">
								<a class="nav-link" href="/EmergencyApp/profile">Profile</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="/EmergencyApp/logout">Sign out</a>
							</li>
						</c:if>
						<c:if test = "${user == null || !user.isApproved()}">
							<li class="nav-item">
								<a class="nav-link" href="/EmergencyApp/register">Register</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="/EmergencyApp/login">Sign in</a>
							</li>
						</c:if>
					</ul>
				</div>
			</div>
		</nav>
	</div>
	
	<div id="page-body">
		<div class="container body-content">
			<jsp:doBody/>
			<hr/>
			<footer class="mt-5 mb-3 text-muted">© 2020</footer>
		</div>
	</div>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<title>Vigilante - Login</title>

<t:layout>
	<jsp:body>
		<div class="text-center">
			<br/>
			<form class="form-signin" method="post" action="login">
				<div class="text-center mb-4">
					<img class="mb-4" src="resources/logo.png" alt="" width="72" height="72">
					<h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
				</div>
				<p id="errorMessage" class="error text-danger">${message}</p>
				<hr/>
				<div class="form-label-group row align-items-center">
					<div class="col-md-4 form-label-left">
						<i class="fa fa-user ml-2 mr-2 w-10"></i>
						<label for="usernameInput">Username</label>
					</div>
					<div class="col-md-8">
						<input type="text" id="usernameInput" name="username" class="form-control" placeholder="User name" value="${username}" required="required" autofocus="autofocus">
					</div>
				</div>
				<br/>
				<div class="form-label-group row align-items-center">
					<div class="col-md-4 form-label-left">
						<i class="fa fa-lock ml-2 mr-2 w-10"></i>
						<label for="passwordInput">Password</label>
					</div>
					<div class="col-md-8">
						<input type="password" id="passwordInput" name="password" class="form-control" placeholder="Password" required="required">
					</div>
				</div>
				<hr/>
				<button class="btn btn-lg btn-outline-primary signin-btn" type="submit">Sign in</button>
				<br/>
			</form>
			<br />
			<p>
				Don't have an account? Sign up <a href="register.jsp">here</a>.
			</p>
		</div>
	</jsp:body>
</t:layout>
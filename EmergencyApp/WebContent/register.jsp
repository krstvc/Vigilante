<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<title>Vigilante - Register</title>

<t:layout>
	<jsp:body>
		<div class="text-center">
			<br/>
			<div class="row">
				<div class="col-md-5 d-flex align-items-center justify-content-center">
					<div class="text-center mb-4">
						<img class="mb-4" src="resources/logo.png" alt="" width="200" height="200">
						<br/>
						<p>
							Already have an account? <a href="login.jsp">Sign in</a> instead.
						</p>
					</div>
				</div>

				<div class="col-md-7 d-flex align-items-center justify-content-end">
					<form class="form-signup" method="post" action="register">
						<h1 class="h3 mb-3 font-weight-normal">Please sign up</h1>
						<p id="errorMessage" class="error text-danger">${message}</p>
						<hr/>
						<div class="form-label-group row align-items-center">
							<div class="col-md-4 form-label-left">
								<i class="fa fa-info-circle ml-2 mr-2 w-10"></i>
								<label for="nameInput">First Name</label>
							</div>
							<div class="col-md-8">
								<input type="text" id="nameInput" name="name" class="form-control" placeholder="First name" required="required" autofocus="autofocus" value="${name}">
							</div>
						</div>
						<br/>
						<div class="form-label-group row align-items-center">
							<div class="col-md-4 form-label-left">
								<i class="fa fa-info-circle ml-2 mr-2 w-10"></i>
								<label for="surnameInput">Last Name</label>
							</div>
							<div class="col-md-8">
								<input type="text" id="surnameInput" name="surname" class="form-control" placeholder="Last name" required="required" value="${surname}">
							</div>
						</div>
						<br/>
						<div class="form-label-group row align-items-center">
							<div class="col-md-4 form-label-left">
								<i class="fa fa-user ml-2 mr-2 w-10"></i>
								<label for="usernameInput">Username</label>
							</div>
							<div class="col-md-8">
								<input type="text" id="usernameInput" name="username" class="form-control" placeholder="Username" required="required" value="${username}">
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
						<br/>
						<div class="form-label-group row align-items-center">
							<div class="col-md-4 form-label-left">
								<i class="fa fa-lock ml-2 mr-2 w-10"></i>
								<label for="confirmPasswordInput">Confirm Password</label>
							</div>
							<div class="col-md-8">
								<input type="password" id="confirmPasswordInput" name="passwordConfirm" class="form-control" placeholder="Confirm password" required="required">
							</div>
						</div>
						<br/>
						<div class="form-label-group row align-items-center">
							<div class="col-md-4 form-label-left">
								<i class="fa fa-envelope ml-2 mr-2 w-10"></i>
								<label for="emailInput">Email</label>
							</div>
							<div class="col-md-8">
								<input type="email" id="emailInput" name="email" class="form-control" placeholder="Email address" required="required" value="${email}">
							</div>
						</div>
						<hr/>
						
						<button id="signUpBtn" class="btn btn-lg btn-outline-primary signup-btn" type="submit">Sign up</button>
						<br/>
					</form>
				</div>
			</div>
		</div>
	</jsp:body>
</t:layout>

<script type="text/javascript">
	$(function(){
		$("#signUpBtn").click(function(event){
			var pass = $("#passwordInput").val();
			var conf = $("#confirmPasswordInput").val();
			if(pass != conf){
				event.preventDefault();
				$("#errorMessage").text("Passwords do not match");
			}
		});
	})
</script>
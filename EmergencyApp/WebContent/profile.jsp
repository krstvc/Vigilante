<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@page import="ip.vigilante.emergency.util.UrlManager" %>

<title>Vigilante - Profile</title>

<t:layout>
	<jsp:body>
		<br />
		<form method="post" action="profile" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${user.id}" />
			<div class="row">
				<div class="col-md-4 d-flex justify-content-center align-items-center">
					<div class="picture-container">
						<div class="picture align-items-center">
							<img id="profilePic" src="${user.hasImage() ? user.getImageURI() : UrlManager.DEFAULT_AVATAR_SRC}" alt="Profile picture" class="picture-src mx-auto d-block" data-manual="false" />
						</div>
						<button id="showPicBtn" type="button" data-toggle="modal" data-target="#modalImage" hidden>Show</button>
						<div class="custom-file mt-1">
							<input type="file" name="image" accept="image/*" class="custom-file-input" id="choosePicBtn" value="${user.imageURI}">
    						<label class="custom-file-label" for="choosePicBtn">Choose file</label>
						</div>
					</div>
					<div id="modalImage" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-body">
									<img id="modalProfilePic" src="${user.hasImage() ? user.getImageURI() : UrlManager.DEFAULT_AVATAR_SRC}" class="img-responsive img-center">
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-8 p-1">
					<div class="form-label-group row align-items-center p-1">
						<div class="col-md-3 form-label-left">
							<i class="fa fa-info-circle ml-2 mr-2 w-10"></i>
							<label for="nameInput">First Name</label>
						</div>
						<div class="col-md-9">
							<input id="nameInput" type="text" name="name" class="form-control" required value="${user.name}" placeholder="First name" />
						</div>
					</div>
					<div class="form-label-group row align-items-center p-1">
						<div class="col-md-3 form-label-left">
							<i class="fa fa-info-circle ml-2 mr-2 w-10"></i>
							<label for="surnameInput">Last Name</label>
						</div>
						<div class="col-md-9">
							<input id="surnameInput" type="text" name="surname" class="form-control" required value="${user.surname}" placeholder="Last name" />
						</div>
					</div>
					<div class="form-label-group row align-items-center p-1">
						<div class="col-md-3 form-label-left">
							<i class="fa fa-user ml-2 mr-2 w-10"></i>
							<label for="usernameInput">Username</label>
						</div>
						<div class="col-md-9">
							<input id="usernameInput" type="text" name="username" class="form-control" required value="${user.username}" placeholder="Username" />
						</div>
					</div>
					<div class="form-label-group row align-items-center p-1">
						<div class="col-md-3 form-label-left">
							<i class="fa fa-envelope ml-2 mr-2 w-10"></i>
							<label for="emailInput">E-mail</label>
						</div>
						<div class="col-md-9">
							<input id="emailInput" type="email" name="email" class="form-control" required value="${user.email}" placeholder="E-mail address" />
						</div>
					</div>
				</div>
			</div>
			<br />
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<i class="fa fa-globe"></i>
						<strong>Country</strong>
						<select id="countryDropdown" name="country" class="form-control" required>
							<option value="" selected disabled hidden>Select</option>
						</select>
						<input type="hidden" name="countryFlag" id="selectedCountryFlag" value="${user.imageURI}" />
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<i class="fa fa-location-arrow"></i>
						<strong>Region</strong>
						<select id="regionDropdown" name="region" class="form-control">
							<option value="" selected disabled hidden>Select</option>
						</select>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<i class="fa fa-thumb-tack"></i>
						<strong>City</strong>
						<select id="cityDropdown" name="city" class="form-control">
							<option value="" selected disabled hidden>Select</option>
						</select>
					</div>	
				</div>
			</div>
			<br />
			<div class="form-group">
				<strong>Subscribed to notifications:</strong>
				<div class="ml-4">
					<div class="form-check-inline">
						<strong class="w-40">App:</strong>
						<label class="form-check-label ml-2">
							<input type="radio" class="form-check-input" name="appSubscription" value="true" ${user.isSubscribedToAppNotifications() == true ? "checked" : ""} />Yes
						</label>
						<label class="form-check-label ml-2">
							<input type="radio" class="form-check-input" name="appSubscription" value="false" ${user.isSubscribedToAppNotifications() != true ? "checked" : ""} />No
						</label>
					</div>
				</div>
				<div class="ml-4">
					<div class="form-check-inline">
						<strong class="w-40">Mail:</strong>
						<label class="form-check-label ml-2">
							<input type="radio" class="form-check-input" name="mailSubscription" value="true" ${user.isSubscribedToMailNotifications() == true ? "checked" : ""} />Yes
						</label>
						<label class="form-check-label ml-2">
							<input type="radio" class="form-check-input" name="mailSubscription" value="false" ${user.isSubscribedToMailNotifications() != true ? "checked" : ""} />No
						</label>
					</div>
				</div>
			</div>
			<hr />
			<div class="form-group d-flex justify-content-center">
				<input type="submit" value="Save" class="btn btn-outline-primary submit-btn" />
			</div>
		</form>
	</jsp:body>
</t:layout>

<script type="text/javascript">
	$(function(){
		resetCountries();
		resetRegions();
		resetCities();
		fillCountries();
		
		$("#countryDropdown").change(function(e){
			var test = "${pageContext.request.contextPath}";
			console.log('testttt: ' + test);
			var pic = $("#profilePic");
			var current = pic.attr("src");
			var modal = $("#modalProfilePic");
			var manual = pic.attr("data-manual");
			var selected = $("option:selected", this);
			var flag = selected.attr("data-flag");
			var code = selected.val();
			var flagInput = $("#selectedCountryFlag");
			
			flagInput.val(flag);
			
			if(manual == "false"){	
				pic.attr("src", flag);
				modal.attr("src", flag);
			}
			
			fillRegions(code);
		});
		
		$("#regionDropdown").change(function(e){
			var selected = $("option:selected", this);
			var country = selected.attr("data-country");
			var region = selected.val();
			
			fillCities(country, region);
		});
		
		$("#profilePic").click(function(event){
			var src = $(this).attr("src");
			
			if(src != "") {
				$("#showPicBtn").click();
			}
		});
		
		$("#choosePicBtn").on("change", function(event){
			var btn = $(this);
			var files = btn[0].files;
			var file = files[0];
			
			if(files && file){
				var reader = new FileReader();
				
				reader.onload = function(e){
					$("#profilePic").attr("src", e.target.result).attr("data-manual", "true");
					$("#modalProfilePic").attr("src", e.target.result);
					btn.val(e.target.result);
				}
				
				reader.readAsDataURL(file);
			}
		});
		
		$(".custom-file-input").on("change", function() {
			var fileName = $(this).val().split("\\").pop();
			$(this).siblings(".custom-file-label").addClass("selected").html(fileName);
		});
	});
	
	function fillCountries(){
		var url = "${UrlManager.COUNTRY_SERVICE_BASE_URL}";
		$.ajax({
			url: url,
			type: "get",
			async: false,
			success: function(resp){
				var result = "";
				var userCountry = "${user.countryCode}";
				for(var i = 0; i < resp.length; ++i){
					var country = resp[i];
					if(country.alpha2Code == userCountry){
						result += "<option value='" + country.alpha2Code + "' data-flag='" + country.flag + "' selected>" + country.name + "</option>";
						fillRegions(userCountry);
					} else {
						result += "<option value='" + country.alpha2Code + "' data-flag='" + country.flag + "'>" + country.name + "</option>";
					}
				}
				resetCountries();
				resetRegions();
				resetCities();
				
				$(result).appendTo($("#countryDropdown"));
			},
			error: function(){
				alert("Error occurred while loading countries list");
			}
		});
	}
	
	function fillRegions(alpha2code){
		var base = "${UrlManager.BATTUTA_BASE_URL}";
		var key = "${UrlManager.BATTUTA_KEY}";
		var url = base + "region/" + alpha2code + "/all/?key=" + key + "&callback=cb";
		
		var el = document.createElement("script");
		el.src = url;
		document.body.appendChild(el);
		
		window["cb"] = function(data){
			var result = "";
			var userRegion = "${user.region}";
			for(var i = 0; i < data.length; ++i){
				var region = data[i];
				if(region.region == userRegion){
					result += "<option value='" + region.region + "' data-country='" + alpha2code + "' selected>" + region.region + "</option>";
					fillCities(alpha2code, userRegion);
				} else {
					result += "<option value='" + region.region + "' data-country='" + alpha2code + "'>" + region.region + "</option>";
				}
			}
			resetRegions();
			resetCities();
			
			$(result).appendTo($("#regionDropdown"));
		}
	}
	
	function fillCities(country, region){
		var base = "${UrlManager.BATTUTA_BASE_URL}";
		var key = "${UrlManager.BATTUTA_KEY}";
		var url = base + "city/" + country + "/search/?region=" + region + "&key=" + key + "&callback=cb";
			
		var el = document.createElement("script");
		el.src = url;
		document.body.appendChild(el);
		
		window["cb"] = function(data){
			var result = "";
			var userCity = "${user.city}";
			for(var i = 0; i < data.length; ++i){
				var city = data[i];
				if(city.city == userCity){
					result += "<option value='" + city.city + "' data-country='" + country + "' data-region='" + region + "' selected>" + city.city + "</option>";
				} else {
					result += "<option value='" + city.city + "' data-country='" + country + "' data-region='" + region + "'>" + city.city + "</option>";
				}
			}
			resetCities();
			
			$(result).appendTo($("#cityDropdown"));
		}
	}
	
	function resetCountries(){
		var option = '<option value="" selected disabled hidden>Select</option>';
		$("#countryDropdown").html(option);
	}
	
	function resetRegions(){
		var option = '<option value="" selected disabled hidden>Select</option>';
		$("#regionDropdown").html(option);
	}
	
	function resetCities(){
		var option = '<option value="" selected disabled hidden>Select</option>';
		$("#cityDropdown").html(option);
	}
</script>

<%@page import="ip.vigilante.emergency.services.PostCommentService"%>
<%@page import="ip.vigilante.emergency.model.User"%>
<%@page import="ip.vigilante.emergency.services.UserService"%>
<%@page import="ip.vigilante.emergency.model.Post"%>
<%@page import="ip.vigilante.emergency.services.PostService"%>
<%@page import="ip.vigilante.emergency.util.UrlManager" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="https://unpkg.com/leaflet@1.6.0/dist/leaflet.css"
   integrity="sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ=="
   crossorigin=""/>

<script src="https://unpkg.com/leaflet@1.6.0/dist/leaflet.js"
   integrity="sha512-gZwIG9x3wUXg2hdXF6+rVkLF/0Vi9U8D2Ntg4Ga5I5BZpVkVxlJWbSQtXPSiUTtC0TjtGOmxa1AJPuV0CPthew=="
   crossorigin=""></script>

<title>Vigilante - Post</title>

<%
	String idStr = request.getParameter("id");
	int id = Integer.parseInt(idStr);
	
	PostService postSvc = PostService.getInstance();
	UserService userSvc = UserService.getInstance();
	PostCommentService commSvc = PostCommentService.getInstance();
	
	Post post = postSvc.getPostById(id);
	User user = userSvc.getUserById(post.getUserId());
	
	session.setAttribute("post", post);
	session.setAttribute("postUser", user);
	session.setAttribute("commSvc", commSvc);
%>

<t:layout>
	<jsp:body>
		<div class="m-2">
			<c:set var="itemId" value="${post.id}" />
			<c:set var="userId" value="${post.userId}" />
			<c:set var="postUser" value="${userSvc.getUserById(userId)}" />
			<div id="notification${post.id}" class="post-bg p-2 mb-4 ml-3 mr-3">
				<div class="post-header row p-2 m-2 align-items-center ${post.isEmergencyAlert() ? 'emergency-alert' : ''}">
					<div class="col-md-1">
						<div class="picture-container">
							<div class="picture-xs align-items-center">
								<img id="profilePic" src="${postUser.hasImage() ? postUser.getImageURI() : UrlManager.DEFAULT_AVATAR_SRC}" alt="Profile picture" class="picture-src mx-auto d-block" />
							</div>
						</div>
					</div>
					<div class="col-md-11 row align-items-center">
						<span class="col-md-8 fs-14"><strong>${postUser.name} ${postUser.surname}</strong></span>
						<span class="col-md-4 fs-12">${post.getTimeFormatted()}</span>
					</div>
				</div>
				<div class="post-body p-2 m-2">
					<strong class="fs-14 w-100" title="${post.title}">${post.title}</strong>
				</div> 
				<div class="post-body p-2 m-2">
					<p class="fs-12">${post.content}</p>
					<c:if test="${post.link != null && post.link.length() > 0}">
						<hr />
						<a href="${post.link}" class="badge badge-info ellipsis-text">${post.link}</a>
					</c:if>
					<c:set var="images" value="${imgSvc.getImagesForPost(post.id)}" />
					<c:if test="${images != null && images.size() > 0}">
						<hr />
						<div class="post-images mt-1">
							<div id="imagesCarousel" class="carousel slide" data-ride="carousel">
								<div class="carousel-inner">
									<c:set var="index" value="active" />
									<c:forEach var="image" items="${imgSvc.getImagesForPost(post.id)}">
										<c:set var="img" value="${image}" />
										<c:if test="${index.equals(\"active\")}" >
											<div class="carousel-item active">
												<img class="d-block w-100" src="${img.imageURI}" alt="Image" />
											</div>
										</c:if>
										<c:if test="${!index.equals(\"active\")}" >
											<div class="carousel-item">
												<img class="d-block w-100" src="${img.imageURI}" alt="Image" />
											</div>
										</c:if>
										<c:set var="index" value="inactive" />
									</c:forEach>
								</div>
								<a class="carousel-control-prev" href="#imagesCarousel" role="button" data-slide="prev">
									<span class="carousel-control-prev-icon" aria-hidden="true"></span>
									<span class="sr-only">Previous</span>
								</a>
								<a class="carousel-control-next" href="#imagesCarousel" role="button" data-slide="next">
									<span class="carousel-control-next-icon" aria-hidden="true"></span>
									<span class="sr-only">Next</span>
								</a>
							</div>
						</div>
					</c:if>
					<c:if test="${post.videoURI != null && post.videoURI.length() > 0}">
						<hr />
						<div class="embed-responsive embed-responsive-16by9">
							<iframe class="embed-responsive-item" src="${post.videoURI}" allowfullscreen></iframe>
						</div>
					</c:if>
					<c:if test="${post.location != null && post.location.length() > 0}">
						<hr />
						<div id="mapid"></div>
					</c:if>
				</div>
				<div class="post-comments pl-3 pr-3 pt-2 pb-2 m-2">
					<c:set var="comments" value="${commSvc.getCommentsForPost(post.id)}" />
					<strong class="fs-14 ellipsis p-2 mt-2 mb-2">${comments.size()} comments</strong>
					<c:forEach var="comment" items="${comments}">
						<c:set var="commentUser" value="${userSvc.getUserById(comment.userId)}" />
						<div class="comment pt-2 pl-4 pr-4 mb-1 mt-2">
							<div class="row align-items-center">
								<div class="picture-container col-md-1">
									<div class="picture-xs-comment align-items-center">
										<img id="profilePic" src="${commentUser.hasImage() ? commentUser.getImageURI() : UrlManager.DEFAULT_AVATAR_SRC}" alt="Profile picture" class="picture-src mx-auto d-block" />
									</div>
								</div>
								<div class="col-md-11 row align-items-center">
									<span class="col-md-8 fs-14"><strong>${commentUser.name} ${commentUser.surname}</strong></span>
									<span class="col-md-4 fs-12">${comment.getTimeFormatted()}</span>
								</div>
							</div>
							<div class="comment-content p-1 mt-2 mb-2">
								<span class="fs-12 p-2">${comment.content}</span>
							</div>
							<c:if test="${comment.imageURI != null && comment.imageURI.length() > 0}">
								<div class="picture-container mb-2">
									<div class="align-items-center">
										<img src="${comment.imageURI}" alt="Comment picture" class="comment-picture-src mx-auto d-block mh-400" />
									</div>
								</div>
							</c:if>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</jsp:body>
</t:layout>

<script>
	$(function(){
		var latlng = "${post.location}";
		if(latlng != "") {
			var lat = parseFloat(latlng.split(", ")[0]);
			var lng = parseFloat(latlng.split(", ")[1]);
			var mymap = L.map('mapid').setView([lat, lng], 13);
			L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
			    maxZoom: 18,
			    id: 'mapbox/streets-v11',
			    tileSize: 512,
			    zoomOffset: -1,
			    accessToken: "${UrlManager.LEAFLET_TOKEN}"
			}).addTo(mymap);
			var marker = L.marker([lat, lng]).addTo(mymap);
			marker.bindPopup("<b>Location</b><br>This is the location related to the post.").openPopup();
		}
	})
	
</script>
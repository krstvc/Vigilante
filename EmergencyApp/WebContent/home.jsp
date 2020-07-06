<%@page import="ip.vigilante.emergency.services.PostCommentService"%>
<%@page import="ip.vigilante.emergency.model.EmergencyCategory"%>
<%@page import="ip.vigilante.emergency.services.EmergencyCategoryService"%>
<%@page import="ip.vigilante.emergency.services.ImageService"%>
<%@page import="ip.vigilante.emergency.model.Image"%>
<%@page import="ip.vigilante.emergency.model.Post"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ip.vigilante.emergency.services.PostService"%>
<%@page import="ip.vigilante.emergency.services.UserService"%>
<%@page import="ip.vigilante.emergency.model.User"%>
<%@page import="ip.vigilante.emergency.util.UrlManager" %>
<%@page import="ip.vigilante.emergency.util.PropertiesManager" %>
<%@page import="ip.vigilante.emergency.util.RSSManager" %>
<%@page import="java.net.URLEncoder" %>
<%@page import="java.nio.charset.StandardCharsets" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="https://unpkg.com/leaflet@1.6.0/dist/leaflet.css"
   integrity="sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ=="
   crossorigin=""/>

<script src="https://unpkg.com/leaflet@1.6.0/dist/leaflet.js"
   integrity="sha512-gZwIG9x3wUXg2hdXF6+rVkLF/0Vi9U8D2Ntg4Ga5I5BZpVkVxlJWbSQtXPSiUTtC0TjtGOmxa1AJPuV0CPthew=="
   crossorigin=""></script>

<title>Vigilante - Home</title>

<% 
	PostService postSvc = PostService.getInstance();
	ImageService imgSvc = ImageService.getInstance();
	UserService userSvc = UserService.getInstance();
	EmergencyCategoryService catSvc = EmergencyCategoryService.getInstance();
	PostCommentService commSvc = PostCommentService.getInstance();
	PropertiesManager props = PropertiesManager.getInstance();
	
	ArrayList<Post> posts = postSvc.getAllPosts();
	ArrayList<Post> emergencyPosts = postSvc.getAllEmergencyAlertPosts();
	ArrayList<EmergencyCategory> categories = catSvc.getCategories();
	
	session.setAttribute("posts", posts);
	session.setAttribute("emergencyPosts", emergencyPosts);
	session.setAttribute("imgSvc", imgSvc);
	session.setAttribute("userSvc", userSvc);
	session.setAttribute("postSvc", postSvc);
	session.setAttribute("commSvc", commSvc);
	session.setAttribute("categories", categories);
	session.setAttribute("props", props);
%>


<t:layout>
	<jsp:body>
		<div id="fb-root"></div>
		<script async defer crossorigin="anonymous" src="https://connect.facebook.net/en_US/sdk.js#xfbml=1&version=v7.0" nonce="3ZXVxRyi"></script>
		
		<c:if test = "${user != null && user.isApproved()}">
			<div class="row">
				<div class="col-md-2 mt-2">
					<div class="jumbotron jumbotron-fluid jumbotron-custom">
						<div class="text-center m-2">
							<div class="card">
								<div>
									<img id="profilePic" class="card-img-top clickable square" src="${user.hasImage() ? user.getImageURI() : UrlManager.DEFAULT_AVATAR_SRC}" alt="Profile picture">
									<button id="showPicBtn" type="button" data-toggle="modal" data-target="#modalImage" hidden></button>
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
								<div class="card-body pad-5">
									<strong class="ellipsis text-dark-violet" title="${user.getFullName()}">${user.getFullName()}</strong>
									<div class="text-violet fs-10" title="${user.getLoginCount()}">Logged in: ${user.getLoginCount()} times</div>
								</div>
							</div>
						</div>
						
						<c:if test = "${user.isSubscribedToAppNotifications()}">
							<hr />
							<div class="m-2">
								<strong class="notice-header">Notifications &nbsp;<i class="fa fa-bullhorn"></i></strong>
								<div class="mt-1">
									<c:forEach var="notification" items="${emergencyPosts}">
										<div class="notice notice-warning notice-sm click-scroll" data-target="${notification.id}">
											<span>${notification.title}</span>
										</div>
									</c:forEach>
								</div>
							</div>
						</c:if>
					</div>
				</div>
				<div class="col-md-8 mt-2 jumbotron jumbotron-homepad">
					<div class="text-center m-2">
						<button type="button" id="postToggleBtn" class="btn btn-secondary home-toggle-btn" disabled="disabled">Posts &nbsp;<i class="fa fa-newspaper-o"></i></button>
						<button type="button" id="rssToggleBtn" class="btn btn-secondary home-toggle-btn">RSS &nbsp;<i class="fa fa-rss"></i></button>
					</div>
					<hr />
					
					<div id="posts-feed">
						<div class="post-bg p-2 mb-4 ml-3 mr-3">
							<strong class="fs-14 btn btn-outline-primary btn-block" id="createToggleBtn">Create new post</strong>
							<form id="createPostForm" action="post" method="POST" class="no-display">
								<hr />
								<div class="form-group mb-2">
									<label for="titleInput" class="fs-14"><strong>Post title</strong></label>
									<input type="text" id="titleInput" name="title" placeholder="Post title" class="form-control p-1" />
								</div>
								<hr />
								<div class="form-group mb-2">
									<label for="contentInput" class="fs-14"><strong>Post content</strong></label>
									<textarea id="contentInput" name="content" class="form-control p-1" placeholder="Enter text description" required></textarea>
								</div>
								<div class="form-group mb-2 ellipsis-container">
									<input type="url" id="linkInput" name="link" placeholder="External link" class="form-control p-1 ellipsis-text" />
								</div>
								<div class="form-group custom-file rounded mb-2 ellipsis-container">
									<input type="file" name="image" accept="image/*" class="custom-file-input ellipsis-text" id="choosePicBtn" multiple />
		    						<label class="custom-file-label" for="choosePicBtn">Choose images</label>
								</div>
								<hr />
								<div class="form-group mb-2 text-center">
									<button type="button" id="uploadToggleBtn" class="btn btn-secondary home-toggle-btn" disabled="disabled">Upload video</button>
									<button type="button" id="urlToggleBtn" class="btn btn-secondary home-toggle-btn">Enter video URL</button>
								</div>
								<div id="videoUploadDiv" class="form-group custom-file rounded mb-2 ellipsis-container">
									<div id="uploadDiv">
										<input type="file" name="video" accept="video/*" class="custom-file-input ellipsis-text" id="chooseVideoInput" />
			    						<label class="custom-file-label" for="chooseVideoBtn">Choose video</label>
			    					</div>
			    					<div id="urlDiv" class="no-display">
			    						<input type="url" name="videoUrl" class="form-control" placeholder="Enter video URL" id="urlVideoInput" />
			    					</div>
								</div>
								<hr />
								<div class="form-group mb-2">
									<label for="locationInput" class="fs-14"><strong>Location</strong></label>
									<div id="mapid"></div>
									<div class="row mt-2">
										<div class="col-md-6">
											<label for="latInput" class="fs-12">Latitude</label>
											<input type="text" name="latitude" id="latInput" class="form-control" pattern="^\d*(\.\d+)?$" title="Decimal number" />
										</div>
										<div class="col-md-6">
											<label for="lngInput" class="fs-12">Longitude</label>
											<input type="text" name="longitude" id="lngInput" class="form-control" pattern="^\d*(\.\d+)?$" title="Decimal number" />
										</div>
									</div>
								</div>
								<hr />
								<div class="form-group mb-2">
									<label class="fs-14"><strong>Categories</strong></label>
									<div class="scrollable-checkbox">
										<c:forEach var="category" items="${categories}">
											<div class="ml-4 checkbox">
												<label><input type="checkbox" class="mr-2" name="${category.category}" value="${category.category}" />${category.category}</label>
											</div>
										</c:forEach>
									</div>
								</div>
								<hr />
								<div class="form-group emergency-alert p-2">
									<label class="fs-14"><strong>Emergency</strong></label>
									<div class="ml-4 checkbox">
										<label><input type="checkbox" class="mr-2" name="alert" value="alert" />This is an emergency</label>
									</div>
								</div>
								<hr />
								<div class="form-group text-center">
									<button type="submit" class="btn btn-outline-primary btn-block">Submit</button>
								</div>
							</form>
						</div>
						<hr />
						
						<c:forEach var="item" items="${posts}">
							<c:set var="itemId" value="${item.id}" />
							<c:set var="userId" value="${item.userId}" />
							<c:set var="postUser" value="${userSvc.getUserById(userId)}" />
							
							<div id="post-${itemId}" class="card ml-2 mr-2 mt-4 mb-4 tab-card">
								<div class="m-2 row">
									<div class="col-md-1 col-xs-1">
										<div class="picture-container">
											<div class="picture-xs align-items-center">
												<img id="profilePic" src="${postUser.hasImage() ? postUser.getImageURI() : UrlManager.DEFAULT_AVATAR_SRC}" alt="Profile picture" class="picture-src mx-auto d-block" />
											</div>
										</div>
									</div>
									<div class="col-md-11 col-xs-11 row align-items-center">
										<span class="col-xs-8 col-md-8 fs-16"><strong>${postUser.name} ${postUser.surname}</strong></span>
										<span class="col-xs-3 col-md-3 fs-12">${item.getTimeFormatted()}</span>
										<span class="col-xs-1 col-md-1"><a href="/EmergencyApp/postDetails.jsp?id=${item.id}"><i class="fa fa-external-link" aria-hidden="true"></i></a></span>
									</div>
								</div>
								<div class="card-header tab-card-header">
									<ul class="nav nav-tabs card-header-tabs post-nav" id="post-tab-${itemId}" role="tablist">
										<li class="nav-item">
											<a class="nav-link active" id="content-tab-${itemId}" data-toggle="tab" href="#content-${itemId}" role="tab" aria-controls="content-${itemId}" aria-selected="true">Content</a>
										</li>
										
										<c:set var="images" value="${imgSvc.getImagesForPost(item.id)}" />
										<c:if test="${images != null && images.size() > 0}">
											<li class="nav-item">
												<a class="nav-link" id="images-tab-${itemId}" data-toggle="tab" href="#images-${itemId}" role="tab" aria-controls="images-${itemId}" aria-selected="false">Images</a>
											</li>
										</c:if>
										
										<c:if test="${item.videoURI != null && item.videoURI.length() > 0}">
											<li class="nav-item">
												<a class="nav-link" id="video-tab-${itemId}" data-toggle="tab" href="#video-${itemId}" role="tab" aria-controls="video-${itemId}" aria-selected="false">Video</a>
											</li>
										</c:if>
										
										<c:if test="${item.location != null && item.location.length() > 0}">
											<li class="nav-item">
												<a class="nav-link" id="location-tab-${itemId}" data-toggle="tab" href="#location-${itemId}" role="tab" aria-controls="location-${itemId}" aria-selected="false">Location</a>
											</li>
										</c:if>
										
										<li class="nav-item">
											<a class="nav-link" id="categories-tab-${itemId}" data-toggle="tab" href="#categories-${itemId}" role="tab" aria-controls="categories-${itemId}" aria-selected="false">Categories</a>
										</li>
										
										<li class="nav-item">
											<a class="nav-link" id="comments-tab-${itemId}" data-toggle="tab" href="#comments-${itemId}" role="tab" aria-controls="comments-${itemId}" aria-selected="false">Comments</a>
										</li>
									</ul>
        						</div>

								<div class="card-content">
									<div class="tab-content" id="post-tab-${itemId}-content">
										<div class="tab-pane fade show active p-3" id="content-${itemId}" role="tabpanel" aria-labelledby="content-tab-${itemId}">
											<h5 class="card-title" title="${item.title}">${item.title}</h5>
											<hr />
											<p class="card-text">${item.content}</p>
											<c:if test="${item.link != null && item.link.length() > 0}">
												<hr />
												<a href="${item.link}" class="badge badge-primary ellipsis w-100" title="${item.link}">${item.link}</a>
											</c:if>
										</div>
										
										<c:if test="${images != null && images.size() > 0}">
											<div class="tab-pane fade p-3" id="images-${itemId}" role="tabpanel" aria-labelledby="images-tab-${itemId}">
												<div class="post-images mt-1">
													<div id="imagesCarousel" class="carousel slide" data-ride="carousel">
														<div class="carousel-inner">
															<c:set var="index" value="active" />
															<c:forEach var="image" items="${imgSvc.getImagesForPost(item.id)}">
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
											</div>
										</c:if>	
										
										<c:if test="${item.videoURI != null && item.videoURI.length() > 0}">
											<div class="tab-pane fade p-3" id="video-${itemId}" role="tabpanel" aria-labelledby="video-tab-${itemId}">
												<div class="embed-responsive embed-responsive-16by9">
													<iframe class="embed-responsive-item" src="${item.videoURI}" allowfullscreen></iframe>
												</div>          
											</div>
										</c:if>
										
										<c:if test="${item.location != null && item.location.length() > 0}">
											<div class="tab-pane fade p-3" id="location-${itemId}" role="tabpanel" aria-labelledby="location-tab-${itemId}">
												<div id="mapid-${itemId}"></div>
												<span id="coord-${itemId}" hidden>${item.location}</span>
											</div>
										</c:if>
										
										<div class="tab-pane fade p-3" id="categories-${itemId}" role="tabpanel" aria-labelledby="categories-tab-${itemId}">
											<c:forEach var="category" items="${postSvc.getAllEmergencyCategoriesForPost(itemId)}">
												<div class="m-1">
													<span class="card-text"><i class="fa fa-tag"></i>&nbsp; ${category.category}</span>
												</div>
											</c:forEach>    
											<c:if test="${item.isEmergencyAlert()}">
												<hr />
												<div class="m-1">
													<span class="card-text"><i class="fa fa-exclamation-triangle" aria-hidden="true"></i>&nbsp; This is an emergency!</span>
												</div>
											</c:if>  
										</div>
										
										<div class="tab-pane fade p-3" id="comments-${itemId}" role="tabpanel" aria-labelledby="comments-tab-${itemId}">
											<c:set var="comments" value="${commSvc.getCommentsForPost(item.id)}" />
											
											
											<!-- 
											<div class="post-comments pl-3 pr-3 pt-2 pb-2 m-2">
												<c:set var="comments" value="${commSvc.getCommentsForPost(item.id)}" />
												<button type="button" id="commentsbtn-${item.id}" class="btn btn-outline-primary btn-block comment-toggler mb-2 mt-2" ${comments.size() > 0 ? '' : 'disabled'}>${comments.size()} comments</button>
												<div id="commentsdiv-${item.id}" class="no-display">
													<c:forEach var="comment" items="${commSvc.getCommentsForPost(item.id)}">
														<c:set var="commentUser" value="${userSvc.getUserById(comment.userId)}" />
														<div class="comment pt-1 pl-4 pr-4 mb-1">
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
																		<img src="${comment.imageURI}" alt="Comment picture" class="comment-picture-src mx-auto d-block mh-200" />
																	</div>
																</div>
															</c:if>
														</div>
													</c:forEach>
												</div>
											</div>
											 -->
										</div>
									</div>
								</div>
								
								<div class="card-footer">
									<span class="fb-share-button" data-href="${props.getProperty('share_target')}" data-layout="button" data-size="small">
										<a target="_blank" 
											href="https://www.facebook.com/sharer/sharer.php?u=${props.getEncodedProperty('share_target')}&amp;src=sdkpreparse" 
											class="fb-xfbml-parse-ignore">
											<i class="fa fa-facebook-square fa-2x"></i>
										</a>
									</span>
									<span class="twitter-share-button">
										<a href="https://twitter.com/intent/tweet?text=${props.getProperty('share_target')}" target="_blank">
											<i class="fa fa-twitter-square fa-2x"></i>
										</a>
									</span>
								</div>
							</div>
							
							<!--  
							<div id="notification${item.id}" class="post-bg p-2 mb-4 ml-3 mr-3">
								<div class="post-header row p-2 m-2 align-items-center ${item.isEmergencyAlert() ? 'emergency-alert' : ''}">
									<div class="col-md-1 col-xs-1">
										<div class="picture-container">
											<div class="picture-xs align-items-center">
												<img id="profilePic" src="${postUser.hasImage() ? postUser.getImageURI() : UrlManager.DEFAULT_AVATAR_SRC}" alt="Profile picture" class="picture-src mx-auto d-block" />
											</div>
										</div>
									</div>
									<div class="col-md-11 col-xs-11 row align-items-center">
										<span class="col-xs-8 col-md-8 fs-14"><strong>${postUser.name} ${postUser.surname}</strong></span>
										<span class="col-xs-4 col-sm-4 fs-12">${item.getTimeFormatted()}</span>
									</div>
								</div>
								<div class="post-body p-2 m-2 text-center">
									<a href="/EmergencyApp/postDetails.jsp?id=${item.id}" class="inline-block fs-14 ellipsis w-100" title="${item.title}">${item.title}</a>
								</div> 
								<div class="post-body p-2 m-2">
									<p class="fs-12">${item.content}</p>
									<c:if test="${item.link != null && item.link.length() > 0}">
										<hr />
										<a href="${item.link}" class="badge badge-info ellipsis w-100" title="${item.link}">${item.link}</a>
									</c:if>
									<c:set var="images" value="${imgSvc.getImagesForPost(item.id)}" />
									<c:if test="${images != null && images.size() > 0}">
										<hr />
										<div class="post-images mt-1">
											<div id="imagesCarousel" class="carousel slide" data-ride="carousel">
												<div class="carousel-inner">
													<c:set var="index" value="active" />
													<c:forEach var="image" items="${imgSvc.getImagesForPost(item.id)}">
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
									<c:if test="${item.videoURI != null && item.videoURI.length() > 0}">
										<hr />
										<div class="embed-responsive embed-responsive-16by9">
											<iframe class="embed-responsive-item" src="${item.videoURI}" allowfullscreen></iframe>
										</div>
									</c:if>
								</div>
								<div class="post-comments pl-3 pr-3 pt-2 pb-2 m-2">
									<c:set var="comments" value="${commSvc.getCommentsForPost(item.id)}" />
									<button type="button" id="commentsbtn-${item.id}" class="btn btn-outline-primary btn-block comment-toggler mb-2 mt-2" ${comments.size() > 0 ? '' : 'disabled'}>${comments.size()} comments</button>
									<div id="commentsdiv-${item.id}" class="no-display">
										<c:forEach var="comment" items="${commSvc.getCommentsForPost(item.id)}">
											<c:set var="commentUser" value="${userSvc.getUserById(comment.userId)}" />
											<div class="comment pt-1 pl-4 pr-4 mb-1">
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
															<img src="${comment.imageURI}" alt="Comment picture" class="comment-picture-src mx-auto d-block mh-200" />
														</div>
													</div>
												</c:if>
											</div>
										</c:forEach>
									</div>
								</div>
							</div>-->
						</c:forEach>
					</div>
					
					<div id="rss-feed" class="jumbotron jumbotron-homepad no-display">
						<c:forEach var="entry" items="${RSSManager.getFeed()}">
							<div class="card ml-2 mr-2 mb-4">
								<div class="card-header">
									<h5 class="card-title">${entry.getTitle()}</h5>
								</div>
								<div class="card-body">
									<p class="card-text">${entry.getDescription().getValue()}</p>
								</div>
								<div class="card-footer">
									<a href="${entry.getLink()}" class="badge badge-primary ellipsis w-100" title="${entry.getLink()}">${entry.getLink()}</a>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
				<div class="col-md-2 mt-2">
					<div class="jumbotron jumbotron-fluid jumbotron-custom align-items-center justify-content-center text-center">
						<div id="city1" class="m-1">
							<div>
								<span class="city-name badge badge-light ellipsis mb-1"></span>
							</div>
							<img class="weather-img" hidden />
							<div>
								<span class="desc"></span>
								<span class="badge badge-info mt-2 temp"></span>
							</div>
							<div class="desc-det fs-10"></div>
						</div>
						<br />
						<div id="city2" class="m-1">
							<div>
								<span class="city-name badge badge-light ellipsis mb-1"></span>
							</div>
							<img class="weather-img" hidden />
							<div>
								<span class="desc"></span>
								<span class="badge badge-info mt-2 temp"></span>
							</div>
							<div class="desc-det fs-10"></div>
						</div>
						<br />
						<div id="city3" class="m-1">
							<div>
								<span class="city-name badge badge-light ellipsis mb-1"></span>
							</div>
							<img class="weather-img" hidden />
							<div>
								<span class="desc"></span>
								<span class="badge badge-info mt- temp"></span>
							</div>
							<div class="desc-det fs-10"></div>
						</div>
					</div>
				</div>
			</div>
		</c:if>
		<c:if test = "${user == null}">
			<div class="text-center jumbotron">
				<h3>Please log in or register to view this content</h3>
				<br />
				<p>You can log in by clicking <a href="/EmergencyApp/login.jsp">here</a>.</p>
				<p>You can register by clicking <a href="/EmergencyApp/register.jsp">here</a>.</p>
			</div>
		</c:if>
		<c:if test = "${user != null && !user.isApproved()}">
			<div class="text-center jumbotron">
				<h3>Please wait for account approval</h3>
				<p>An administrator needs to review your registration request before you can log in.</p>
			</div>
		</c:if>
	</jsp:body>
</t:layout>

<script>
	$("#profilePic").click(function(event){
		var src = $(this).attr("src");
		
		if(src != "") {
			$("#showPicBtn").click();
		}
	});
	
	$(function(){
		var country = "${user.getCountryCode()}";
		var regions = [];
		var cities = [];
		
		var base = "${UrlManager.BATTUTA_BASE_URL}";
		var key = "${UrlManager.BATTUTA_KEY}";
		var regionUrl = base + "region/" + country + "/all/?key=" + key + "&callback=regioncb";
		
		var regionEl = document.createElement("script");
		regionEl.src = regionUrl;
		document.body.appendChild(regionEl);
		
		window["regioncb"] = function(data){
			var regionCount = data.length;
			
			for(var i = 0; i < data.length; ++i){
				var region = data[i].region;
				regions.push(region);
			}
			
			regions.forEach(function(region){
				
				var cityUrl = base + "city/" + country + "/search/?region=" + region + "&key=" + key + "&callback=citycb";
				
				var cityEl = document.createElement("script");
				cityEl.src = cityUrl;
				document.body.appendChild(cityEl);
				
				window["citycb"] = function(data){
					for(var i = 0; i < data.length; ++i){
						var city = data[i].city;
						cities.push(city);
					}
					
					--regionCount;
					if(regionCount == 0){
						fillWeatherData(cities);
					}
				}
			});
		}
		
		var mymap = L.map('mapid').setView([44.766, 17.187], 13);
		L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
			attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
		    maxZoom: 18,
		    id: 'mapbox/streets-v11',
		    tileSize: 512,
		    zoomOffset: -1,
		    accessToken: "${UrlManager.LEAFLET_TOKEN}"
		}).addTo(mymap);
		mymap.on("click", mapClick);
		
		var mapids = $("div[id^='mapid-']");
		for(var i = 0; i < mapids.length; ++i){
			var elemId = mapids[i].id.replace("mapid", "coord");
			var latlng = $("#" + elemId).text();
			var lat = parseFloat(latlng.split(", ")[0]);
			var lng = parseFloat(latlng.split(", ")[1]);
			
			var mapI = L.map(mapids[i].id).setView([lat, lng], 13);
			L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
				attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
			    maxZoom: 18,
			    id: 'mapbox/streets-v11',
			    tileSize: 512,
			    zoomOffset: -1,
			    accessToken: "${UrlManager.LEAFLET_TOKEN}"
			}).addTo(mapI);
			var marker = L.marker([lat, lng]).addTo(mapI);
			marker.bindPopup("<b>Location</b><br>This is the location related to the post.").openPopup();
		}
	});
	
	function mapClick(e) {
		$("#latInput").val(Math.round(e.latlng.lat * 10000) / 10000);
		$("#lngInput").val(Math.round(e.latlng.lng * 10000) / 10000);
	}
	
	$("#uploadToggleBtn").click(function(e){
		$("#urlDiv").hide();
		$("#urlVideoInput").attr("disabled", "disabled")
		$("#uploadDiv").show();
		$("#uploadVideoInput").removeAttr("disabled");
		$("#urlToggleBtn").removeAttr("disabled");
		$(this).attr("disabled", "disabled");
	});
	
	$("#urlToggleBtn").click(function(e){
		$("#uploadDiv").hide();
		$("#uploadVideoInput").attr("disabled", "disabled");
		$("#urlDiv").show();
		$("#urlVideoInput").removeAttr("disabled");
		$("#uploadToggleBtn").removeAttr("disabled");
		$(this).attr("disabled", "disabled");
	});
	
	$("#postToggleBtn").click(function(e){
		$("#rss-feed").hide();
		$("#posts-feed").show();
		$("#rssToggleBtn").removeAttr("disabled");
		$(this).attr("disabled", "disabled");
	});
	
	$("#rssToggleBtn").click(function(e){
		$("#posts-feed").hide();
		$("#rss-feed").show();
		$("#postToggleBtn").removeAttr("disabled");
		$(this).attr("disabled", "disabled");
	});
	
	$(".click-scroll").click(function(e) {
		var targetName = $(this).attr("data-target");
		var target = $("#post-" + targetName);
		var body = $("html, body");
	    body.animate({
	    	scrollTop: target.offset().top
	    }, 1000);
	});
	
	$(".comment-toggler").click(function(e){
		var id = $(this).attr("id");
		var divId = id.replace("btn", "div");
		$("#" + divId).slideToggle("slow");
	});
	
	$(".custom-file-input").on("change", function() {
		var files = $(this)[0].files;
		var text = "";
		
		if(files.length > 1) {
			var count = files.length;
			$(this).siblings(".custom-file-label").addClass("selected").html(count + " files selected");
		}
		else if(files.length == 1){
			var name = files[0].name.split("\\").pop();
			$(this).siblings(".custom-file-label").addClass("selected").html(name);
		}
	});
	
	$("#createToggleBtn").on("click", function(e){
		$("#createPostForm").slideToggle("slow");
	});
	
	function fillWeatherData(cities){
		for(var i = 0; i < 3; ++i){
			getCityWeather(cities, i, 0);
		}
	}
	
	function getBareName(city){
		return city.replace("Opstina", "").trim();
	}
	
	function getCityWeather(cities, i, errorCount){
		var index = Math.floor(Math.random() * cities.length);
		var city = getBareName(cities[index]);
		cities.splice(index, 1);
		
		var base = "${UrlManager.OPENWEATHER_BASE_URL}";
		var key = "${UrlManager.WEATHER_KEY}";
		
		var url = base + "?q=" + encodeURIComponent(city) + "&appid=" + key;
		console.log(url);
		
		$.ajax({
			type: 'GET',
			url: url,
			async: false,
			success: function(data){
				console.log("SUCCESS " + city);
				var content = data.list[0];
				if(content != null){
					var id = "city" + (i + 1);
					var temp = content.main.temp - 272.15;
					temp = temp.toPrecision(2) + " °C";
					var desc = content.weather[0].main;
					var detailed = content.weather[0].description;
					var img = "http://openweathermap.org/img/wn/" + content.weather[0].icon + "@2x.png";
					
					$("#" + id + " div .city-name").text(city).attr("title", city);
					$("#" + id + " img").attr("src", img).removeAttr("hidden");
					$("#" + id + " div .desc").text(desc);
					$("#" + id + " div .temp").text(temp);
					$("#" + id + " .desc-det").text(detailed);
					
					$("#" + id + " img").show();
				}
			},
			error: function(data){
				console.log("ERROR " + errorCount + " " + city);
				if(errorCount > 10){
					alert("Error occurred while obtaining weather forecast.");
				} else {
					getCityWeather(cities, i, errorCount + 1);
				}
			}
		});
	}
</script>
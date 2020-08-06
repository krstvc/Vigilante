function showBlockAlert(data) {
	var status = data.status;
	
	if(status == "success") {
		alert("User blocked successfully");
	}
}

function showResetAlert(data) {
	var status = data.status;
	
	if(status == "success") {
		alert("Password changed successfully");
	}
}

function showApproveAlert(data) {
	var status = data.status;
	
	if(status == "success") {
		alert("User approved successfully");
	}
}

function initChart(labels, data) {
	var canvas = document.getElementById("activity-canvas");
	var context = canvas.getContext("2d");
	
	var chart = new Chart(context, {
		type: "line",
		data: {
			labels: labels,
			datasets: [{
				label: "Last 24 hours activity",
				data: data,
				backgroundColor: [
					'rgba(56, 147, 245, 0.2)',
					'rgba(54, 162, 235, 0.2)',
					'rgba(255, 206, 86, 0.2)',
					'rgba(75, 192, 192, 0.2)',
					'rgba(153, 102, 255, 0.2)',
					'rgba(255, 159, 64, 0.2)'
				],
				borderColor: [
					'rgba(4, 53, 143, 1)',
					'rgba(54, 162, 235, 1)',
					'rgba(255, 206, 86, 1)',
					'rgba(75, 192, 192, 1)',
					'rgba(153, 102, 255, 1)',
					'rgba(255, 159, 64, 1)'
				],
				borderWidth: 1
			}]
		},
		options: {
			responsive: true,
			scales: {
				yAxes: [{
					ticks: {
						beginAtZero: true
					}
				}]
			}
		}
	});
}

function getLabels() {
	ret = [];
	
	var h = new Date().getHours();
	
	for(var i = 1; i <= 24; ++i) {
		var val = (h + i) % 24;
		ret.push(val);
	}
	
	return ret;
}

function refreshChart() {
	var labels = getLabels();
	data = [];
	
	$.ajax({
		url: "api/activity",
		type: "GET",
		success: function(json) {
			for(var i = 0; i < 24; ++i) {
				var hour = labels[i].toString();
				data.push(json[hour]);
			}
			
			initChart(labels, data);
		},
		error: function(err) {
			alert("Error occurred while trying to draw a graph");
		}
	});
	
	initChart(labels, data);
}

function initHomePage() {
	refreshChart();
}

function drawMap(id, latlng) {
	var lat = parseFloat(latlng.split(", ")[0]);
	var lng = parseFloat(latlng.split(", ")[1]);
	
	var mapI = L.map(id).setView([lat, lng], 13);
	L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
		attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
	    maxZoom: 18,
	    id: 'mapbox/streets-v11',
	    tileSize: 512,
	    zoomOffset: -1,
	    accessToken: "pk.eyJ1Ijoia3JzdHZjIiwiYSI6ImNrYnNsbzU5NTAwbXoyc2xpdmx1cnF2ZXAifQ.ksKrgxHuPiBKlvDH-AHb0g"
	}).addTo(mapI);
	var marker = L.marker([lat, lng]).addTo(mapI);
	marker.bindPopup("<b>Location</b><br>This is the location related to the post.").openPopup();
}


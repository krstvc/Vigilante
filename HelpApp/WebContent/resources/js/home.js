function init() {
	var mymap = L.map('mapid').setView([44.766, 17.187], 13);
	L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
		attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
	    maxZoom: 18,
	    id: 'mapbox/streets-v11',
	    tileSize: 512,
	    zoomOffset: -1,
	    accessToken: "pk.eyJ1Ijoia3JzdHZjIiwiYSI6ImNrYnNsbzU5NTAwbXoyc2xpdmx1cnF2ZXAifQ.ksKrgxHuPiBKlvDH-AHb0g"
	}).addTo(mymap);
	
	mymap.on("click", function(e) {
		var lat = Math.round(e.latlng.lat * 10000) / 10000;
		var lng = Math.round(e.latlng.lng * 10000) / 10000;
		var latlng = lat + ", " + lng;
		
		$("input[name='add-call-form:location']").val(latlng);
		$("input[name='add-call-form:readonly']").val(latlng);
	});
}

function showSuccess(data) {
	var status = data.status;
	
	if(status == "success") {
		alert("Posted successfully");
	}
}
function zobrazSkore() {
	var index = document.getElementById("games");
	var game = index.options[index.selectedIndex].value;

	$.ajax({
		type : "GET",
		url : "http://localhost:8080/rest/score/" + game,
		dataType : "json"

	})
			.done(
					function(receivedData) {
						$("#scoreTable").html(
								Mustache.render($("#tmplScores").html(),
										receivedData));
					}).fail(function() {
				window.alert("Nebola vybrata hra");
			}

			);
}

$("#btSaveScore").click(function() {
	var index = document.getElementById("games");
	var game = index.options[index.selectedIndex].value;
	var score = parseInt($("#newScore").val().trim());
	var username = $("#newUsername").val();
	if (isNaN(score)) {
		window.alert("Bad Input!");
		return;
	}
	var data2send = {
		username : username,
		game : game,
		value : score
	}

	$.ajax({

		type : "POST",
		url : "http://localhost:8080/rest/score",
		contentType : "application/json",
		data : JSON.stringify(data2send)

	}).done(function(receivedData) {
		zobrazSkore();
		document.getElementById("newScore").value = "";
		document.getElementById("newUsername").value = "";
	}).fail(function() {
		window.alert("Unable to send data");
	}

	);

});

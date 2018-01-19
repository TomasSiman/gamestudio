drawScoreTable();

function drawScoreTable(){
	$.ajax({

		type : "GET",
		url : "http://localhost:8080/rest/score/mines",
		dataType : "json"

	}).done(
			function(receivedData) {

				$("#scoreTable").html(
						Mustache.render($("#tmplScores").html(), receivedData));
			}).fail(function() {
		$("#scoreTable").html("<p>SORRY.</p>");
	}

	);
}



$("#btSaveScore").click(function() {
	var score = parseInt($("#inNewScore").val().trim());

	if (isNaN(score)) {
		window.alert("Bad Input!");
		return;
	}
	var data2send = {
			username:"Jakobo",
			game: "mines",
			value: score
	}
	
	$.ajax({

		type : "POST",
		url : "http://localhost:8080/rest/score",
		contentType : "application/json",
		data: JSON.stringify(data2send)

	}).done(
			function(receivedData) {
				drawScoreTable();
				
			}).fail(function() {
				window.alert("Unable to send data");
	}

	);
}


);

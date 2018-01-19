var staticTasks = '[{"id":"1","task":"Write Europass CV as web page","isDone":true},{"id":"2","task":"Implement TODO list","isDone":false},{"id":"3","task":"Do final assignment","isDone":false}]';

var tasks = JSON.parse(staticTasks);

var taskTemplate = $("#tmplTask").html();

var storage = localStorage.getItem('newTasks7');

 tasks = JSON.parse(storage);


function addTaskToHtml(task) {

	if (task) {
		$element = $(Mustache.render(taskTemplate, task));

		$("#frmTasks").append($element);

		if (task.isDone) {
			$element.removeClass("activeTask");
			$element.addClass("completedTask");
		}

		$element.click(function() {
			$(this).toggleClass("activeTask");
			$(this).toggleClass("completedTask");
			for (var i = 0, len = tasks.length; i < len; i++) {
				if ($(this).attr('data-id') == tasks[i].id) {
					tasks[i].isDone = !tasks[i].isDone;
				}
			}
		});

	}
}

// --------------------------------------
// 1.nacitanie uloh
for (var i = 0, len = tasks.length; i < len; i++) {
	addTaskToHtml(tasks[i]);
	
	
}


/*
 * //Alternatíva predch. cyklu pre EcmaScript 6 for(var task of tasks){
 * addTaskToHtml(task); }
 */
$("#btAddTask").click(function() {

	var text = $("#inNewTask").val().trim();
	if (text == "")
		return;

	var newTask = {
		id : Date.now(),
		task : text,
		isDone : false

	};
	
	
	tasks.push(newTask);
	addTaskToHtml(newTask);
	var stringTask = JSON.stringify(tasks);
console.log(stringTask);
	localStorage.setItem('newTasks7', stringTask);


});

$("#btRemCmpl").click(function() {

	/*
	 * 1. vymazanie z pola tasks pomocou cyklu
	 *  // for(var i=0 ; i<tasks.length; i++){ // if(tasks[i].isDone){ //
	 * tasks.splice(i, 1); // i--; // } // }
	 */
/*
 * vymazanie z pola tasks pomocou filtra
 */
	tasks = tasks.filter(function(task) {
		return !task.isDone;
	});
	
	/*
	 * vymazanie z pola tasks pomocou filtra s lambda 
	 */
	//tasks = tasks.filter(function(task => !task.isDone;
	
	

	$("#frmTasks").empty();
	for (var i = 0, len = tasks.length; i < len; i++) {
		addTaskToHtml(tasks[i]);
	}
}

);

$("#buttonCancel").on("click", function() {
	window.location = "/TerminPlaner";
});


function showModalDialog(message){
	$("#modalTitle").text("Warning");
	$("#modalBody").text(message);
	$("#modalDialog").modal();
}


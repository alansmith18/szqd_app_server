function displayNextRow(row) {
	if (event.srcElement.tagName == "INPUT") return;
	var id = row.rowIndex;
	var rows = document.getElementById("content_t").rows;
	if (typeof(rows.length) == "undefined" || rows.length <= id) return;
	var nextrow = rows[id + 1];
	if (nextrow.style.display == "none") {
		nextrow.style.display = "table-row";
		row.className = "grid_row_current";
	} else {
		nextrow.style.display = "none";
		row.className = "";
	}
}

function changeCheck() {
	var checked = event.srcElement.checked;
	var checks = document.getElementsByName("checkbox");
	if (typeof(checks.length) == "undefined" || checks.length == 1) return;
	for (var i = 0; i < checks.length; i++) {
		checks[i].checked = checked;
	}
}

var currentRow;
function changeCurrentRow(row) {
//		if (currentRow) {
//			currentRow.className = "";
//		}
//		currentRow = row;
//		if (row) {
//			row.className = "grid_row_current";
//		}
}

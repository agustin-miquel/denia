/*
 * Show/hide password
 */
function toggle_password(target){
    var pwd1 = document.getElementById("newPassword");
    var pwd2 = document.getElementById("repeatPassword");
    var tag = document.getElementById("showhide");

    if (tag.innerHTML == 'Ver contraseña'){
        pwd1.setAttribute('type', 'text');   
        pwd2.setAttribute('type', 'text');   
        tag.innerHTML = 'Ocultar';

    } else {
        pwd1.setAttribute('type', 'password');   
        pwd2.setAttribute('type', 'password');   
        tag.innerHTML = 'Ver contraseña';
    }
}

/*
 * Validations
 */
function validatePageContent() {
	var ok = true;
	
	var name = document.getElementById("logoFile").name;
	var value = document.getElementById("logoFile").value;

	// 1. Validate the selected file extension:
	if (value != null & value.trim().length > 0) {
		var pos = name.indexOf('.');
		if (pos == -1) {
			ok = false;
		} else {
			var ext1 = name.substring(pos + 1);
		
			pos = value.indexOf('.');
			if (pos == -1) {
				ok = false;
			} else {
				var ext2 = value.substring(pos + 1);
			}
		}
	
		if (!ok || ext1 != ext2) {
			alert("El fichero del logotipo debe ser una imagen " + ext2);
			return false;
		}
	}
	return true;
}

/*
 * Confirm and remove a section from DB and table 
 */
function removeSection(id, element) {
	
	if (!confirm("Eliminar sección?")) return;
	
	var xhttp = new XMLHttpRequest();
	var url = "/denia/admin/removeSection";
	var params = "id=" + id;
	
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			if (this.responseText == "ok") {
				var row = element.parentNode.parentNode;
				row.parentNode.removeChild(row);

				document.getElementById("message").innerHTML = "La sección ha sido eliminada";
				document.getElementById("error").innerHTML = "";
			} else {
				document.getElementById("error").innerHTML = this.responseText;
				document.getElementById("message").innerHTML = "";
			}
		}
	};
	xhttp.open("POST", url, true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send(params);
}

/*
 * Show/hide a section
 */
function showSection(id) {
	var xhttp = new XMLHttpRequest();
	var url = "/denia/admin/toggleShowSection";
	var params = "id=" + id;

	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			if (this.responseText == "ok") {
				document.getElementById("message").innerHTML = "Valor modificado";
			} else {
				document.getElementById("error").innerHTML = this.responseText;
			}
		}
	};

	xhttp.open("POST", url, true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send(params);
}

/*
 * Add/remove the section menu option 
 */
function showInMenu(id) {
	var xhttp = new XMLHttpRequest();
	var url = "/denia/admin/toggleShowInMenu?id=" + id;
	var params = "id=" + id;
	
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			if (this.responseText == "ok") {
				document.getElementById("message").innerHTML = "Valor modificado";
			} else {
				document.getElementById("error").innerHTML = this.responseText;
			}
		}
	};

	xhttp.open("POST", url, true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send(params);
}

/*
 * Control of changes inf data forms
 */
function changed() {
	document.getElementById("changes").value = "1";
	
	document.getElementById("message").innerHTML = "";
	document.getElementById("error").innerHTML = "";
}

/*
 * Return true if there are no changes or confirmation message accepted
 */
function checkChanges() {
	if (document.getElementById("changes").value == "1") {
		if (confirm("Salir sin grabar cambios?")) {
			return true;
		}
		else return false;
	}
	return true;
}

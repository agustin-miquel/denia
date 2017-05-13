/*  
 * Mobile: toggle between adding and removing the "responsive" class to topnav when the user clicks on the icon 
 */
function openMenu() {
    var x = document.getElementById("menu");
    if (x.className === "topnav") {
        x.className += " responsive";
    } else {
        x.className = "topnav";
    }
}

/*
 * Validate contact form
 */
function validateContactForm() {
    if (contactForm.email.value == '' && contactForm.phone.value == '') {
        alert('Se necesita el email o el telefono');
        return false;
    } else {
    	return true;
    }
}

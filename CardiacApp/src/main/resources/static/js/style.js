function myFunction() {
	  document.getElementById("encargados").classList.toggle("show");
	}

	function enableInputs() {
	  $("#fieldset").prop('disabled', false);
	  $("#start").focus();
	}

	function showNav() {
	  var nav = document.getElementById("list");
	  if (nav.className === "list-nav") {
	    nav.className += " responsive";
	  } else {
	    nav.className += "list-nav";
	  }
	}

	// Close the dropdown if the user clicks outside of it
	window.onclick = function (e) {
	  if (!e.target.matches('.dropbtn')) {
	    var myDropdown = document.getElementById("encargados");
	    if (myDropdown.classList.contains('show')) {
	      myDropdown.classList.remove('show');
	    }
	  }
	}

	

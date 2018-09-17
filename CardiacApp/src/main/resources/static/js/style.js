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

	const OXIGENCHART = document.getElementById("oxigenationChart");

	

	let oxigenationChart = new Chart(OXIGENCHART, {
	  type: 'bar',
	  data: data = {
	    labels: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"],
	    datasets: [
	      {
	        label: "Oxigenación",
	        backgroundColor: "rgba(200,25,58,0.2)",
	        borderColor: "#C8193A",
	        borderCapStyle: "butt",
	        borderJoinStyle: 'miter',
	        BorderWidth: 5,
	        data: [10, 20, 55, 30, 80, 60, 20, 40, 50, 65]
	      }
	    ]
	  },
	  options: {
	    scales: {
	      yAxes: [{
	        ticks: {
	          beginAtZero: true,
	          max: 100
	        }
	      }]
	    }
	  }
	})

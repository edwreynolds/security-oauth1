<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
	<!-- Required meta tags -->
	<meta charset="utf-8">
	<!-- 
	Force IE to use the latest rendering engine to render this website.  
	This will prevent our website from breaking as older rendering engines do not 
	support all properties of CSS. 
	-->
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<!-- Tell the browser to scale our application to the size of window space available. -->
	<meta name="viewport" content="width=device-width,?initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <meta name="description" content="OAuth2 App">
	
	<!-- Bootstrap CSS -->
	<!--<link rel='stylesheet' href="webjars/font-awsome/4.7.0/css/font-awesome.min.css"> -->	
	<link rel='stylesheet' href='webjars/bootstrap/3.3.7/css/bootstrap.min.css'>
	<link rel='stylesheet' href='webjars/jquery-ui/1.12.1/jquery-ui.min.css'> 
	<!--<link rel='stylesheet' href='webjars/free-jqgrid/4.15.3/css/ui.jqgrid.min.css'> -->

	<!-- Custom styles for this template -->
    <link rel="stylesheet" type="text/css"  href="css/sticky-footer-navbar.css">

	<title>OAuth using GitHub App</title>
	<link rel="icon" href="images/favicon.ico" type="image/x-icon">

	
	<!-- Optional JavaScript: jQuery first then Bootstrap JS -->
	<script src="webjars/jquery/1.12.1/jquery.min.js"></script>
	<script src="webjars/jquery-ui/1.12.1/jquery-ui.min.js"></script>
	<!-- <script src="webjars/free-jqgrid/4.15.3/js/jquery.jqgrid.min.js"></script> -->
	<!-- <script src="webjars/free-jqgrid/4.15.3/js/i18n/grid.locale-en.js"></script> -->
	<script src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

	<style>
		/* 
		* Override to set background white instead of verylight grey.  Also make border a DLA blue that's '
		* a couple pixels wide.  Made bkgnd white because the dlaLogo JPEG has a white background.
		* See: bootstrap.css
		*/
		.navbar-default {
		  background-color: #ffffff;
		  border-top: 2px solid #182991;	/* DLA blue */
		  border-bottom: 2px solid #182991;
		}

		/* When mouse hovers over an item in navbar highlight it */
		.navbar-default .navbar-nav > li > a:focus, .navbar-default .navbar-nav > li > a:hover {
			color: #182991;
			font-style:italic;
			font-weight: bold;	
		}	

		.jumbotron {
			padding-top: 8px;
			padding-bottom: 8px;
		}
	</style>	

	<script>
	"use strict";
	
	
	$( document ).ready(function() {
		try {
			console.log("Index is ready...");

			//$("#statusContentArea").hide();
			
			if( typeof jQuery != 'undefined' ) {  
			    // jQuery is loaded => print the version
			    console.log("jQuery version is: "+jQuery.fn.jquery);
			} else {
			    console.warn("jQuery not defined!");
			}		
			
			$("#appTitle").text("Index page, no login required");
			
			// Add function to handle user clicks on dropdown list shown in navigation bar
			$(".dropdown-menu > li > a.trigger").on("click",function(e){
				var current=$(this).next();
				var grandparent=$(this).parent().parent();
				if($(this).hasClass('left-caret')||$(this).hasClass('right-caret'))
					$(this).toggleClass('right-caret left-caret');
				grandparent.find('.left-caret').not(this).toggleClass('right-caret left-caret');
				grandparent.find(".sub-menu:visible").not(current).hide();
				current.toggle();
				e.stopPropagation();
			});
			$(".dropdown-menu > li > a:not(.trigger)").on("click",function(){
				var root=$(this).closest('.dropdown');
				root.find('.left-caret').toggleClass('right-caret left-caret');
				root.find('.sub-menu:visible').hide();
			});
			
			$("#logOut").click( function() {
				alert("TODO:  Logout current user");
			});
			$("#logOut").hide();
			
			$("#userId").click( function() {
				alert("TODO:  Show user details");
			});
			$("#userId").hide();
			
			//$("#logIn").click( function() {
				//$.get("/home");				
				//alert("TODO:  Login");
			//});
			
			$("#settingsId").click( function() {
				alert("TODO:  Settings");
			});

			$("#helpImgId").click( function(/*event*/) { 
				alert("TODO: Display help content");
			});

		} catch(ex) {
			console.log("ready() caught an exception! "+ex);
		}
	});
	</script>
</head>

<body>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <!-- Brand shown at left side of nav bar -->
                <a href="#"><img src="images/dlaLogo.jpg" alt="DataLogic" style="width: 228px; height: 50px; "> </a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav "></ul>

                <ul class="nav navbar-nav navbar-right">
                    <li><a href="./home" id="logIn" title="Log In"><span class="glyphicon glyphicon-log-in"></span> LogIn</a></li>
                    <li><a href="#" id="userId"><span class="glyphicon glyphicon-user"></span>  Role: Admin | John Doe</a></li>
                    <li><a href="#" id="logOut" title="Log Off"><span class="glyphicon glyphicon-off"></span> LogOut</a></li>
					<li class="dropdown">
					  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-menu-hamburger"></span> Menu</a>
					   <ul class="dropdown-menu">
					     <li><a id="reportsId"><span class="glyphicon glyphicon-paperclip"></span>  Reports</a></li>
					     <li><a id="statsId"><span class="glyphicon glyphicon-stats"></span>  Stats</a></li>
					     <li><a id="chartsId"><span class="glyphicon glyphicon glyphicon-pencil"></span>  Charts</a></li>
					   </ul>
					</li>
                    <li><a href="#" id="settingsId"><span class="glyphicon glyphicon-cog"></span>  Settings</a></li>
                    <li><a href="#" id="helpImgId"><span class="glyphicon glyphicon-question-sign"></span>  Help</a></li>
                </ul>
                    
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>

    <h2 id="appTitle" style="text-align:center;"></h2>

    <div class="container">
    </div>

    <footer class="footer">
        <div class="container-fluid">
            <p id="copyright" class="text-muted"><b>&copy; 2019 Datalogic Industrial Automation</b></p>
        </div>
    </footer>

</body>
</html>

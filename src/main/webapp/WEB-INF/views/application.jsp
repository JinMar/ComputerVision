<%@page contentType="text/html" pageEncoding="windows-1250" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="cs">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="author" content="Bc. Marek Jindrák">
    <meta name="description" content="Diplomova prace">
    <spring:url value="/img/favicon.ico" var="favicon"/>
    <link rel="icon" href="${favicon}">

    <title>Diplomová práce</title>
    <spring:url value="/css/bootstrap.min.css" var="bootstrap"/>
    <link href="${bootstrap}" rel="stylesheet">
    <spring:url value="/css/navbar-fixed-top.css" var="bootstrapTop"/>
    <link href="${bootstrapTop}" rel="stylesheet">
    <spring:url value="/css/lightbox.css" var="ligbox"/>
    <link href="${ligbox}" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <spring:url value="/js/bootstrap.min.js" var="bootstrapJs"/>
    <script src="${bootstrapJs}"></script>
    <spring:url value="/img/logo-fm.png" var="logo"/>
    <spring:url value="/img/test.png" var="tst"/>
    <spring:url value="/img/wait.gif" var="waitingBar"/>


    <spring:url value="/js/jquery.js" var="coreJs"/>

    <spring:url value="/js/attributeSetup.js" var="attributeSetup"/>
    <spring:url value="/js/lightbox.js" var="lightbox"/>
    <script src="${coreJs}"></script>
    <script src="${coreJSUI}"></script>
    <script src="${attributeSetup}"></script>
    <script src="${lightbox}"></script>


</head>

<body>

<!-- Navbar -->
<nav class="navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Navigace</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">DevTUL</a>
        </div>
        <%@include file="menu.jsp" %>
        <!--/.nav-->
    </div>
</nav>

<div class="jumbotron">
    <div class="container">

        <div class="img_Border">
            <img id="logoTUL" src=${logo} alt="logo"/>
        </div>
    </div>
</div>

<div class="container">
    <h2>Systém pro analýzu obrazu</h2>
    <div class="message">
    </div>
    <div class="cnt">
        <div class="row">


            <ul id="sortable" class="col-xs-12 col-sm-12 col-md-12">
                <li id="init_img" class="col-xs-12 col-sm-12 col-md-12 methodItem">
                    <img src="/img/noImg.jpg" class="img-thumbnail previewOrig" alt="Cinque Terre" width="152">
                    <div class="methodName">
                        <p>ZÁKALDNÍ OBRÁZEK</p>
                    </div>
                    <form class="input-group" id="img2b64">
                        <input id="inputFileToLoad" type="file"/>
                    </form>
                </li>
                <li class="add">
                    <input class="adding methodItem" style="font-family:Helvetica" type="button" value="+">
                </li>
                <!-- Here will be new methods -->

            </ul>
        </div>

    </div>
</div>
<div class="clr"><img class="hide" id="hiddenImg"></div>
<footer class="footer">

    <p>&copy; Diplomová práce 2017. All rights reserved. Design by Marek Jindrák.</p>

</footer>

</body>
</html>

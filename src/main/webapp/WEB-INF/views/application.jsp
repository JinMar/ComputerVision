<%@page contentType="text/html" pageEncoding="windows-1250" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="cs">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="author" content="Bc. Marek Jindr�k">
    <meta name="description" content="Diplomova prace">
    <spring:url value="/img/favicon.ico" var="favicon"/>
    <link rel="icon" href="${favicon}">

    <title>Diplomov� pr�ce</title>
    <spring:url value="/css/bootstrap.min.css" var="bootstrap"/>
    <link href="${bootstrap}" rel="stylesheet">
    <spring:url value="/css/navbar-fixed-top.css" var="bootstrapTop"/>
    <link href="${bootstrapTop}" rel="stylesheet">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <spring:url value="/js/bootstrap.min.js" var="bootstrapJs"/>
    <script src="${bootstrapJs}"></script>
    <spring:url value="/img/logo-fm.png" var="logo"/>
    <spring:url value="/img/test.png" var="tst"/>

    <spring:url value="/js/jquery.js" var="coreJs"/>
    <spring:url value="/js/jquery-ui.js" var="coreJSUI"/>
    <spring:url value="/js/attributeSetup.js" var="attributeSetup"/>
    <script src="${coreJs}"></script>
    <script src="${coreJSUI}"></script>
    <script src="${attributeSetup}"></script>


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
        <div id="navbar" class="navbar-collapse collapse">

            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Home</a></li>
                <li><a href="news">Novinky</a></li>
                <li><a href="task">Zad�n�</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button">Dokumentace</a>
                    <ul class="dropdown-menu">
                        <li><a href="userdocs">U�ivatelsk� dokumentace</a></li>
                        <li><a href="devdoc">V�voj��sk� dokumentace</a></li>
                    </ul>
                </li>
                <li><a href="function">Funkce</a></li>

            </ul>
        </div>
        <!--/.nav-->
    </div>
</nav>


<!-- Main component for a primary marketing message or call to action -->
<div class="jumbotron">
    <div class="container">

        <div class="img_Border">
            <img id="logoTUL" src=${logo} alt="logo"/>
        </div>
    </div>
</div>
<div class="container">
    <div class="row">
        <h2>V�tejte na str�nk�ch diplomov� pr�ce</h2>
        <input class="adding" style="font-family:Helvetica" type="submit" value="P�idat ">
        <input class="send" style="font-family:Helvetica" type="submit" value="Odeslat ">

        <div id="init_img" class="col-xs-12 col-sm-12 col-md-12 methodItem">
            <img src="/img/test.png" class="img-thumbnail preview" alt="Cinque Terre" width="152">
            <div class="methodName">
                <p>Z�KALDN� OBR�ZEK</p>
            </div>
            <form class="input-group" id="img2b64">
                <input id="inputFileToLoad" type="file"/>
            </form>
        </div>
        <div class="col-xs-12 col-sm-12 col-md-12">
            <!-- Here will be new methods -->
            <div class="contentBody">
                <ul id="sortable"></ul>
            </div>

        </div>
    </div>

</div>
<div class="clr"></div>
<footer class="footer">

    <p>&copy; Semestr�ln� projekt 2016. All rights reserved. Design by Marek Jindr�k.</p>

</footer>

</body>
</html>

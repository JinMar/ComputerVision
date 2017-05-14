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

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <spring:url value="/js/bootstrap.min.js" var="bootstrapJs"/>
    <script src="${bootstrapJs}"></script>
    <spring:url value="/img/logo-fm.png" var="logo"/>


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
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-12">

            <h2>Zadání diplomové práce</h2>
            <h3>(PROJEKTU, UMÌLECKÉHO DÍLA, UMÌLECKÉHO VÝKONU</h3>
            <table class="about">
                <tr>
                    <td>Jméno a pøíjmení:</td>
                    <td><b>Bc. Marek Jindrák</b></td>
                </tr>
                <tr>
                    <td>Osobní èíslo:</td>
                    <td><b>M15000241</b></td>
                </tr>
                <tr>
                    <td>Studijní program:</td>
                    <td><b>N2612 Elektrotechnika a informatika</b></td>
                </tr>
                <tr>
                    <td>Studijní obor:</td>
                    <td><b>Informaèní technologie</b></td>
                </tr>
                <tr>
                    <td>Název tématu:</td>
                    <td><b>Webový systmém pro analýzu obrazu</b></td>
                </tr>
                <tr>
                    <td>Zadávající katedra:</td>
                    <td><b>Ústav informaèních technologií a elektroniky</b></td>
                </tr>
            </table>
            <h3>Zásady pro vypracování</h3>
            <ol>
                <li>Seznamte se s problematikou zpracování a rozpoznání obrazu.</li>
                <li>Navrhnìte a realizujte komplexní webový systém pro poloautomatické zpracování a rozpoznání obrazu.
                </li>
                <li>Tento system by mìl obsahovat graficky pøívìtivé prostøedí, ve kterém budou použity jednotlivé
                    algoritmy pro zpracování a rozpoznání obrazu z knihovny OpenCV
                </li>
                <li>Jednotlivé funkce budou prezentovány jako moduly, které by mìl uživatel snadno pøidávat a editovat,
                    aby bylo možné provádìt i složitìjší analýzu obrazu.
                </li>
                <li>Celý systém by mìl být navržen tak, aby byl „otevøený“,tj. aby se v budoucnu daly do programu
                    pøidávat nové moduly.
                </li>
            </ol>
            <table class="about">
                <tr>
                    <td>Rozsah grafických prací:</td>
                    <td><b>Dle potøeby dokumentace</b></td>
                </tr>
                <tr>
                    <td>Rozsah pracovní zprávy:</td>
                    <td><b>cca 40-50 stran</b></td>
                </tr>
                <tr>
                    <td>Forma zpracování diplomové práce:</td>
                    <td><b>tištìná / elektronická</b></td>
                </tr>
                <tr>
                    <td>Seznam odborné literatury:</td>
                    <td></td>
                </tr>


            </table>
            <p class="dl"><b>[1] DAVIES, E. R. 2005. Machine vision: theory, algorithms, practicalities. 3rd ed. Boston:
                Elsevier.
                ISBN 0122060938.</b></p>
            <p class="dl"><b>[2] ŠONKA, Milan, Václav HLAVÁÈ a Roger BOYLE. 2008. Image processing, analysis, and
                machine vision.
                3rd ed. Toronto: Thomson. ISBN 9780495082521.</b></p>
            <p class="dl"><b>[2] HLAVÁÈ, Václav. a Miloš. SEDLÁÈEK. 2007. Zpracování signálù a obrazù. 2. pøeprac. vyd.
                Praha:
                ÈVUT. ISBN 9788001031100.</b></p>
            <table class="about">
                <tr>
                    <td>Vedoucí diplomové práce</td>
                    <td><b>doc. Ing. Josef Chaloupka, Ph.D.</b></td>
                </tr>
                <tr>
                    <td></td>
                    <td>Ústav informaèních technologii a elektroniky</td>
                </tr>
                <tr>
                    <td>Konzultant diplomové práce</td>
                    <td><b></b></td>
                </tr>
                <tr>
                    <td></td>
                    <td>Ústav informaèních technologii a elektroniky</td>
                </tr>
                <tr>
                    <td>Datum zadání diplomové práce:</td>
                    <td><b>12. záøí 2016</b></td>
                </tr>
                <tr>
                    <td>Termín odevzdání diplomové práce:</td>
                    <td><b>15. kvìtna 2017</b></td>
                </tr>

            </table>
        </div>
    </div>

</div>
<footer class="footer">

    <p>&copy; Diplomová práce 2017. All rights reserved. Design by Marek Jindrák.</p>

</footer>

</body>
</html>

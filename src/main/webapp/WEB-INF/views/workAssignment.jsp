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

            <h2>Zad�n� diplomov� pr�ce</h2>
            <h3>(PROJEKTU, UM�LECK�HO D�LA, UM�LECK�HO V�KONU</h3>
            <table class="about">
                <tr>
                    <td>Jm�no a p��jmen�:</td>
                    <td><b>Bc. Marek Jindr�k</b></td>
                </tr>
                <tr>
                    <td>Osobn� ��slo:</td>
                    <td><b>M15000241</b></td>
                </tr>
                <tr>
                    <td>Studijn� program:</td>
                    <td><b>N2612 Elektrotechnika a informatika</b></td>
                </tr>
                <tr>
                    <td>Studijn� obor:</td>
                    <td><b>Informa�n� technologie</b></td>
                </tr>
                <tr>
                    <td>N�zev t�matu:</td>
                    <td><b>Webov� systm�m pro anal�zu obrazu</b></td>
                </tr>
                <tr>
                    <td>Zad�vaj�c� katedra:</td>
                    <td><b>�stav informa�n�ch technologi� a elektroniky</b></td>
                </tr>
            </table>
            <h3>Z�sady pro vypracov�n�</h3>
            <ol>
                <li>Seznamte se s problematikou zpracov�n� a rozpozn�n� obrazu.</li>
                <li>Navrhn�te a realizujte komplexn� webov� syst�m pro poloautomatick� zpracov�n� a rozpozn�n� obrazu.
                </li>
                <li>Tento system by m�l obsahovat graficky p��v�tiv� prost�ed�, ve kter�m budou pou�ity jednotliv�
                    algoritmy pro zpracov�n� a rozpozn�n� obrazu z knihovny OpenCV
                </li>
                <li>Jednotliv� funkce budou prezentov�ny jako moduly, kter� by m�l u�ivatel snadno p�id�vat a editovat,
                    aby bylo mo�n� prov�d�t i slo�it�j�� anal�zu obrazu.
                </li>
                <li>Cel� syst�m by m�l b�t navr�en tak, aby byl �otev�en��,tj. aby se v budoucnu daly do programu
                    p�id�vat nov� moduly.
                </li>
            </ol>
            <table class="about">
                <tr>
                    <td>Rozsah grafick�ch prac�:</td>
                    <td><b>Dle pot�eby dokumentace</b></td>
                </tr>
                <tr>
                    <td>Rozsah pracovn� zpr�vy:</td>
                    <td><b>cca 40-50 stran</b></td>
                </tr>
                <tr>
                    <td>Forma zpracov�n� diplomov� pr�ce:</td>
                    <td><b>ti�t�n� / elektronick�</b></td>
                </tr>
                <tr>
                    <td>Seznam odborn� literatury:</td>
                    <td></td>
                </tr>


            </table>
            <p class="dl"><b>[1] DAVIES, E. R. 2005. Machine vision: theory, algorithms, practicalities. 3rd ed. Boston:
                Elsevier.
                ISBN 0122060938.</b></p>
            <p class="dl"><b>[2] �ONKA, Milan, V�clav HLAV�� a Roger BOYLE. 2008. Image processing, analysis, and
                machine vision.
                3rd ed. Toronto: Thomson. ISBN 9780495082521.</b></p>
            <p class="dl"><b>[2] HLAV��, V�clav. a Milo�. SEDL��EK. 2007. Zpracov�n� sign�l� a obraz�. 2. p�eprac. vyd.
                Praha:
                �VUT. ISBN 9788001031100.</b></p>
            <table class="about">
                <tr>
                    <td>Vedouc� diplomov� pr�ce</td>
                    <td><b>doc. Ing. Josef Chaloupka, Ph.D.</b></td>
                </tr>
                <tr>
                    <td></td>
                    <td>�stav informa�n�ch technologii a elektroniky</td>
                </tr>
                <tr>
                    <td>Konzultant diplomov� pr�ce</td>
                    <td><b></b></td>
                </tr>
                <tr>
                    <td></td>
                    <td>�stav informa�n�ch technologii a elektroniky</td>
                </tr>
                <tr>
                    <td>Datum zad�n� diplomov� pr�ce:</td>
                    <td><b>12. z��� 2016</b></td>
                </tr>
                <tr>
                    <td>Term�n odevzd�n� diplomov� pr�ce:</td>
                    <td><b>15. kv�tna 2017</b></td>
                </tr>

            </table>
        </div>
    </div>

</div>
<footer class="footer">

    <p>&copy; Diplomov� pr�ce 2017. All rights reserved. Design by Marek Jindr�k.</p>

</footer>

</body>
</html>

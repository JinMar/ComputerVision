/**
 * Created by Marek on 02.10.2016.
 */

$(document).ready(function () {

    var count = 1;
    var index = 1;
    var maxHeigh = 200;
    var interval;
    var testData;
    var arraz1 = [];
    var allmethod = [];
    var minHeigh = 200;
    var firstItem = {
        "position": 0,
        "methodId": "",
        "attributes": []
    };

    var itemIMG;
    var itemIMG2;
    var imageMap = [];
    window.onload = getFunction1s();


    function encodeImageFileAsURL(cb) {
        return function () {
            console.log("ukládama data ");
            var file = this.files[0];
            var reader = new FileReader();
            reader.onloadend = function () {
                cb(reader.result);
            };
            reader.onload = function (e) {
                $('.previewOrig')
                    .attr('src', e.target.result);
                itemIMG = e.target.result;

            };
            reader.readAsDataURL(file);
        }
    }

    $('#inputFileToLoad').change(encodeImageFileAsURL(function (base64Img) {

        console.log("ukládama data 2 ");
        var inputData = [];
        var inputDataItem;

        inputDataItem = {
            "value": base64Img,
            "methodAttributeId": ""
        };
        inputData.push(inputDataItem);
        firstItem = {
            "position": 0,
            "methodId": "",
            "attributes": inputData
        };

    }));


    function sendAjax(dataToSend) {
        // alert(JSON.stringify(dataToSend));


        $.ajax({
            url: "/rest/createChain",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(dataToSend),
            contentType: 'application/json',
            mimeType: 'application/json',
            success: function (data) {
                testData = data;

                if (data.state) {

                    $(".message").append("<div class='alert alert-success'>" +
                        "<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>" +
                        " <strong>Saved!</strong> " + data.message + "</div>");
                    setTimeout(loadChain, 1000);
                } else {

                    $(".message").append("<div class='alert alert-danger'>" +
                        "<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>" +
                        " <strong>FAILED!</strong> " + data.message + "</div>");

                }
            },
            error: function (data, status, er) {
                alert("error: " + data + " status: " + status + " er:" + er);
                console.log(JSON.stringify(data));
                console.log(JSON.stringify(status));
                console.log(JSON.stringify(er));
            }
        });
    }
    function loadChain() {

        var dataToSend = [];
        var newItem = {
            'chainId': testData.chainId,
            'message': testData.message
        };

        dataToSend.push(newItem);


        console.log(JSON.stringify(dataToSend));
        $.ajax({
            url: "/rest/isChainReady",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(dataToSend),
            contentType: 'application/json',
            mimeType: 'application/json',
            cache: false,
            success: function (data) {

                if (data.ready == true) {


                    clearInterval(interval);
                    reDraw(data);


                } else {
                    reDraw(data);
                    setTimeout(loadChain, 2000);
                }

                //  ;
            },
            error: function (data, status, er) {
                alert("error: " + data + " status: " + status + " er:" + er);
                clearInterval(interval);
            }
        });
    }
    function reDraw(data) {
        $('#sortable li').each(function () {
            var number = this.id.split("-")[1];
            removeFCE(number)
        });
        count = 0;
        imageMap = [];
        for (var key in data.parts) {
            var recieveData = data.parts[key];
            var tempURL = "";
            count += 1;

            if (recieveData.url == null) {
                tempURL = '/img/wait.gif';
            } else {
                tempURL = recieveData.url;
            }
            if (recieveData.mURL == null || recieveData.hURL == null) {
                $(".add").before($("<li id='attachment-" + count + "'class='col-xs-12 col-sm-12 col-md-12 methodItem'>" +
                    "<a href='" + tempURL + "' data-lightbox='image-" + count + "' data-title='My caption'>" +
                    "<img src='" + tempURL + "' class='img-thumbnail preview' id='img-" + count + "' alt='Cinque Terre' width='152'/></a>" +
                    "<div class='methodName'>" +
                    "<table id ='tab-" + count + "'cellspacing='0'>" +
                    "<tr id='tblRow-0' >" +
                    "<td width='50%'>" +

                    "</td></tr>" +
                    " <tr id='tblRow-1' >" +
                    "<td width='90%'>" +
                    "<select class='function' id='fce-" + count + "' name='comp'>" +
                    " <option disabled selected value> -- Function -- </option>" +
                    "</select>" +
                    "</td>" +
                    "<td width='10%'>" +
                    "    <button onclick='removeFCE(" + count + ")' class='close'>" + '&times' +
                    "</td>" +
                    "</tr>" +
                    " <tr id='tblRow-2' >" +
                    "<td width='90%'>" +
                    "<select class='metoda' id='method-" + count + "' name='comp'>" +
                    " <option disabled selected value> -- Method -- </option>" +
                    "</select>" +
                    "</td>" +
                    "<td width='10%'>" +
                    "</td>" +
                    "</tr>" +
                    " <tr id='tblRow-3' >" +
                    "<td width='90%'>" +
                    "<select class='operation' id='op-" + count + "' name='comp'>" +
                    " <option disabled selected value> -- Operation -- </option>" +
                    "</select>" +
                    "</td>" +
                    "<td width='10%'>" +

                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "</button></div>" +
                    "<table id='tbl-" + count + "' cellspacing='0' width='100%' ></table>" +
                    "</li>"));
            }
            else {
                $(".add").before($("<li id='attachment-" + count + "'class='col-xs-12 col-sm-12 col-md-12 methodItem'>" +
                    "<a href='" + tempURL + "' data-lightbox='image-" + count + "' data-title='My caption'>" +
                    "<img src='" + tempURL + "' class='img-thumbnail preview' id='img-" + count + "' alt='Cinque Terre' width='152'/></a>" +
                    "<div class='methodName'>" +
                    "<table id ='tab-" + count + "'cellspacing='0'>" +
                    " <tr id='tblRow-0' >" +
                    "<td width='50%'>" +
                    "<a href='" + recieveData.mURL + "' data-lightbox='image-" + count + "' data-title='My caption'>" +
                    "<img src='" + '/img/mag.jpg' + "' class='img-thumbnail additional-info'  id='img-" + count + "' alt='Cinque Terre' width='50'/></a>" +
                    "<a href='" + recieveData.hURL + "' data-lightbox='image-" + count + "' data-title='My caption'>" +
                    "<img src='" + '/img/frek.jpg' + "' class='img-thumbnail additional-info'  id='img-" + count + "' alt='Cinque Terre' width='50'/></a>" +
                    "</td>" +
                    " <tr id='tblRow-1' >" +
                    "<td width='90%'>" +
                    "<select class='function' id='fce-" + count + "' name='comp'>" +
                    " <option disabled selected value> -- Function -- </option>" +
                    "</select>" +
                    "</td>" +
                    "<td width='10%'>" +
                    "    <button onclick='removeFCE(" + count + ")' class='close'>" + '&times' +
                    "</td>" +
                    "</tr>" +
                    " <tr id='tblRow-2' >" +
                    "<td width='90%'>" +
                    "<select class='metoda' id='method-" + count + "' name='comp'>" +
                    " <option disabled selected value> -- Method -- </option>" +
                    "</select>" +
                    "</td>" +
                    "<td width='10%'>" +
                    "</td>" +
                    "</tr>" +
                    " <tr id='tblRow-3' >" +
                    "<td width='90%'>" +
                    "<select class='operation' id='op-" + count + "' name='comp'>" +
                    " <option disabled selected value> -- Operation -- </option>" +
                    "</select>" +
                    "</td>" +
                    "<td width='10%'>" +

                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "</button></div>" +
                    "<table id='tbl-" + count + "' cellspacing='0' width='100%' ></table>" +
                    "</li>"));
            }
            $.each(allmethod, function (key, value) {
                if (value.idMethod == recieveData.functionId) {
                    $('#fce-' + count)
                        .append($("<option selected></option>")
                            .attr("value", value.idMethod)
                            .text(value.name));
                } else {
                    $('#fce-' + count)
                        .append($("<option></option>")
                            .attr("value", value.idMethod)
                            .text(value.name));
                }
            });
            $.each(recieveData.methods, function (key, value) {
                if (value.idMethod == recieveData.methodId) {
                    $('#method-' + count)
                        .append($("<option selected></option>")
                            .attr("value", value.idMethod)
                            .text(value.name));
                } else {
                    $('#method-' + count)
                        .append($("<option></option>")
                            .attr("value", value.idMethod)
                            .text(value.name));
                }
            });
            $.each(recieveData.operations, function (key, value) {
                if (value.idMethod == recieveData.operationId) {
                    $('#op-' + count)
                        .append($("<option selected></option>")
                            .attr("value", value.idMethod)
                            .text(value.name));
                } else {
                    $('#op-' + count)
                        .append($("<option></option>")
                            .attr("value", value.idMethod)
                            .text(value.name));
                }
            });
            for (var key in recieveData.partValueList) {
                var stored = recieveData.partValueList[key];
                if (stored.type.toLowerCase() == "select".toLowerCase()) {
                    $("#tbl-" + count).append(
                        $(
                            "<tr class='tabRow-" + count + "'>" +
                            "<td width='45%'>" + stored.name + "</td>" +
                            "<td width='55%'><select   id='param-" + key + "-" + count + "'   onchange='updateSendButtonPosition(" + count + ")' name='comp'>" +
                            " <option disabled selected value> -- selection -- </option>" +
                            "</select></tr>"));

                    for (var keyOption in stored.options) {
                        if (keyOption == stored.value) {
                            $('#param-' + key + "-" + count)
                                .append($("<option selected></option>")
                                    .attr("value", keyOption)
                                    .text(stored.options[keyOption]));
                        } else {
                            $('#param-' + key + "-" + count)
                                .append($("<option></option>")
                                    .attr("value", keyOption)
                                    .text(stored.options[keyOption]));
                        }


                    }

                }
                if (stored.type.toLowerCase() == "number".toLowerCase()) {
                    $("#tbl-" + count)
                        .append($(
                            "<tr class='tabRow-" + count + "'>" +
                            "<td>" + stored.name + "</td>" +
                            "<td><input  id='param-" + key + "-" + count + "' onchange='updateSendButtonPosition(" + count + ")'" +
                            " type='" + stored.type.toLowerCase() + "'  value='" + stored.value + "'></td>" +
                            "</tr>"
                        ));
                }
                if (stored.type.toLowerCase() == "image".toLowerCase()) {

                    $("#tbl-" + count)
                        .append($(
                            "<tr class='tabRow-" + key + "'>" +
                            "<td width='45%'>" + stored.name + "</td>" +
                            "<td width='55%'> " +

                            "<input id='param-" + key + "-" + count + "' class='fileToload' type='file' onchange='updateSendButtonPosition(" + count + ")'/>" +

                            "</tr>"
                        ));
                    $("#param-" + key + "-" + count).attr("data-dataimg");
                    $("#param-" + key + "-" + count).data("dataimg", stored.value);


                }

                $('#param-' + key + "-" + count).attr("data-key");
                $('#param-' + key + "-" + count).data("key", stored.operationAttributesId);


            }

        }


        resize2(minHeigh);
    }

    function removeAtribute(par) {
        $("#" + par).remove();
    }

    function resize() {
        var first = true;
        var currentLoop = 0;
        var countLoop = $('#sortable li').size();
        maxHeigh = 200;
        $('#sortable li').each(function () {
            currentLoop += 1;
            if (!first && currentLoop != countLoop) {
                var number = this.id.split("-")[1];
                var table = document.getElementById('tbl-' + number);

                var heigh = parseInt($("#" + this.id).height()) + 15;


                console.log(this.id + " : " + heigh);
                if (maxHeigh < heigh) {
                    maxHeigh = heigh;
                }

                console.log(maxHeigh);
            } else {
                first = false
            }
        });
        $(".methodItem").css("min-height", maxHeigh + "px");
    }

    function resize2(num) {
        $(".methodItem").css("min-height", num + "px");
    }


    function getFunction1s() {
        $.ajax({
            url: "/rest/getFunctions",
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            success: function (data) {
                allmethod = data;

            },
            error: function (data, status, er) {
                alert("error: " + data + " status: " + status + " er:" + er);
            }
        });
    }

    $(document).on('click', '.adding', function () {


        count = count + 1;
        $(".add").before($("<li id='attachment-" + count + "'class='col-xs-12 col-sm-12 col-md-12 methodItem'>" +
            "<img class='img-thumbnail preview' id='img-" + count + "' alt='Cinque Terre' width='152'/>" +
            "<div class='methodName'>" +
            "<table id ='tab-" + count + "'cellspacing='0'>" +
            " <tr id='tblRow-1' >" +
            "<td width='90%'>" +
            "<select class='function' id='fce-" + count + "' name='comp'>" +
            " <option disabled selected value> -- Function -- </option>" +
            "</select>" +
            "</td>" +
            "<td width='10%'>" +
            "    <button onclick='removeFCE(" + count + ")' class='close'>" + '&times' +
            "</td>" +
            "</tr>" +
            " <tr id='tblRow-2' >" +
            "<td width='90%'>" +
            "<select class='metoda' id='method-" + count + "' name='comp'>" +
            " <option disabled selected value> -- Method -- </option>" +
            "</select>" +
            "</td>" +
            "<td width='10%'>" +
            "</td>" +
            "</tr>" +
            " <tr id='tblRow-3' >" +
            "<td width='90%'>" +
            "<select class='operation' id='op-" + count + "' name='comp'>" +
            " <option disabled selected value> -- Operation -- </option>" +
            "</select>" +
            "</td>" +
            "<td width='10%'>" +

            "</td>" +
            "</tr>" +
            "</table>" +
            "</button></div>" +
            "<table id='tbl-" + count + "' cellspacing='0' width='100%' ></table>" +
            "</li>"));

        if (itemIMG == null) {
            $('#img-' + count)
                .attr('src', '/img/test.png');
        } else {
            $('#img-' + count)
                .attr('src', itemIMG)
        }


        $.each(allmethod, function (key, value) {
            $('#fce-' + count)
                .append($("<option></option>")
                    .attr("value", value.idMethod)
                    .text(value.name));
        });

        resize();

    });
    $(document).on('click', '.send', function () {

        $(".alert").remove();
        $('.preview').attr('src', '/img/wait.gif');
        arraz1.push(firstItem);
        var countLoop = $('#sortable li').size();
        var currentLoop = 1;
        //alert("count loop: " + countLoop);
        var first = true;

        $('#sortable li').each(function () {
            if (!first) {
                if (currentLoop < countLoop) {
                    first = false;
                    var number = this.id.split("-")[1];


                    var inputData = [];

                    var table = document.getElementById('tbl-' + number);

                    var rowLength = table.rows.length;

                    for (var i = 0; i < rowLength; i++) {
                        var inputDataItem = [];
                        var tag = $("#param-" + i + "-" + number).prop("tagName");
                        var type = $("#param-" + i + "-" + number).attr("type");
                        inputDataItem = {
                            "value": $("#param-" + i + "-" + number).val(),
                            "operationAttributeId": $("#param-" + i + "-" + number).data('key')
                        };
                        if (tag.toLowerCase() == "input" && type == "file") {

                            var myData = $("#param-" + i + "-" + number).data('dataimg');
                            console.log("moje načtená data");
                            if (myData != null) {
                                if (0 < myData.length) {


                                    inputDataItem = {
                                        "value": myData,
                                        "operationAttributeId": $("#param-" + i + "-" + number).data('key')
                                    };
                                }
                            }


                        }
                        else {

                            inputDataItem = {
                                "value": $("#param-" + i + "-" + number).val(),
                                "operationAttributeId": $("#param-" + i + "-" + number).data('key')
                            };

                        }
                        console.log(inputDataItem.toString());
                        inputData.push(inputDataItem);
                    }

                    var finalItem = {
                        "position": index,
                        "operationId": $("#op-" + number).val(),
                        "methodId": $("#method-" + number).val(),
                        "functionId": $("#fce-" + number).val(),
                        "attributes": inputData
                    };
                    arraz1.push(finalItem);
                    index = index + 1;
                    currentLoop = currentLoop + 1;
                    if (currentLoop == countLoop) {
                        sendAjax(arraz1);
                        arraz1 = [];
                        index = 1
                    }


                }
            }
            else {
                first = false;
                currentLoop++;
            }
        });


    });
    $(document).on('change', '.function', function () {
        var dataToSend = [];
        var item = {
            "pageAttributeId": this.id,
            "objectId": $("#" + this.id).val()
        };

        var temp = this.id.split("-");
        var currentPositionElement = temp[1];
        updateSendButtonPosition(currentPositionElement);
        $('#method-' + currentPositionElement).empty().append('<option disabled selected value> -- Method -- </option>"');
        $('#op-' + currentPositionElement).empty().append('<option disabled selected value> -- Operation -- </option>"');
        $("#tbl-" + currentPositionElement).empty();
        dataToSend.push(item);
        $.ajax({
            url: "/rest/getMethod",
            type: 'POST',
            data: JSON.stringify(dataToSend),
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            success: function (data) {
                $.each(data, function (key, value) {
                    $('#method-' + currentPositionElement)
                        .append($("<option></option>")
                            .attr("value", value.idMethod)
                            .text(value.name));
                });

            },
            error: function (data, status, er) {
                alert("error: " + data + " status: " + status + " er:" + er);
            }
        });

    });
    $(document).on('change', '.metoda', function () {

        var temp = this.id.split("-");
        var currentPositionElement = temp[1];
        $('#op-' + currentPositionElement).empty().append('<option disabled selected value> -- Operation -- </option>"');
        $("#tbl-" + currentPositionElement).empty();
        var dataToSend = [];
        var item = {
            "pageAttributeId": this.id,
            "objectId": $("#" + this.id).val(),

        };
        updateSendButtonPosition(currentPositionElement);

        dataToSend.push(item);
        $.ajax({
            url: "/rest/getOperation",
            type: 'POST',
            data: JSON.stringify(dataToSend),
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            success: function (data) {
                $.each(data, function (key, value) {
                    $('#op-' + currentPositionElement)
                        .append($("<option></option>")
                            .attr("value", value.idMethod)
                            .text(value.name));
                });

            },
            error: function (data, status, er) {
                alert("error: " + data + " status: " + status + " er:" + er);
            }
        });

    });
    $(document).on('change', '.operation', function () {

        var temp = this.id.split("-");
        var currentPositionElement = temp[1];

        var dataToSend = [];
        var item = {
            "pageAttributeId": this.id,
            "objectId": $("#" + this.id).val(),

        };
        $("#tbl-" + currentPositionElement).empty();
        updateSendButtonPosition(currentPositionElement);
        dataToSend.push(item);
        $.ajax({
            url: "/rest/getAttributes",
            type: 'POST',
            data: JSON.stringify(dataToSend),
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            success: function (data) {
                $(".tbl-" + currentPositionElement).empty();

                for (var key in data.attributesDTOs) {
                    var recieveData = data.attributesDTOs[key];
                    console.log(recieveData.attributeType.toLowerCase());
                    if (recieveData.name.length != 0) {
                        if (recieveData.attributeType.toLowerCase() == "select".toLowerCase()) {

                            $("#tbl-" + currentPositionElement).append(
                                $(
                                    "<tr class='tabRow-" + key + "'>" +
                                    "<td width='45%'>" + recieveData.name + "</td>" +
                                    "<td width='55%'><select class='sel' id='param-" + key + "-" + currentPositionElement + "' onchange='updateSendButtonPosition(" + currentPositionElement + ")' name='comp'>" +
                                    " <option disabled selected value> -- selection -- </option>" +
                                    "</select></tr>"));
                            for (var keyOption in recieveData.options) {

                                if (keyOption == recieveData.defaultValues) {
                                    $('#param-' + key + "-" + currentPositionElement)
                                        .append($("<option selected></option>")
                                            .attr("value", keyOption)
                                            .text(recieveData.options[keyOption]));
                                } else {
                                    $('#param-' + key + "-" + currentPositionElement)
                                        .append($("<option></option>")
                                            .attr("value", keyOption)
                                            .text(recieveData.options[keyOption]));
                                }


                            }
                        }
                        if (recieveData.attributeType.toLowerCase() == "number".toLowerCase()) {

                            $("#tbl-" + currentPositionElement)
                                .append($(
                                    "<tr class='tabRow-" + key + "'>" +
                                    "<td width='45%'>" + recieveData.name + "</td>" +
                                    "<td width='55%'> <input  id='param-" + key + "-" + currentPositionElement + "'  onchange='updateSendButtonPosition(" + currentPositionElement + ")' type='" +
                                    recieveData.attributeType.toLowerCase() + "'  value='" + recieveData.defaultValues + "'" +
                                    "min='" + recieveData.minValue + "' max='" + recieveData.maxValue + "'/></td>" +
                                    "</tr>"
                                ));
                        }

                        if (recieveData.attributeType.toLowerCase() == "image".toLowerCase()) {

                            $("#tbl-" + currentPositionElement)
                                .append($(
                                    "<tr class='tabRow-" + key + "'>" +
                                    "<td width='45%'>" + recieveData.name + "</td>" +
                                    "<td width='55%'> " +

                                    "<input id='param-" + key + "-" + currentPositionElement + "' class='fileToload' onchange='updateSendButtonPosition(" + currentPositionElement + ")' type='file' />" +

                                    "</tr>"
                                ));

                        }
                        $('#param-' + key + "-" + currentPositionElement).attr("data-key");
                        $('#param-' + key + "-" + currentPositionElement).data("key", recieveData.operationAttributesId);

                    }
                }
                var minHeighnew = 200;
                if (data.attributesDTOs.length > 0) {
                    minHeighnew += 40 * data.attributesDTOs.length;
                    if (minHeigh < minHeighnew) {
                        minHeigh = minHeighnew;
                    }

                    resize2(minHeigh);
                }

            },
            error: function (data, status, er) {
                alert("error: " + data + " status: " + status + " er:" + er);
            }
        });


    });
    $(document).on('change', '.fileToload', encodeImageFileAsURL2(function (base64Img) {


        var targetElement = base64Img[0];
        console.log("ukládama data do mapy " + targetElement);
        $("#" + targetElement).attr("data-dataimg");
        $("#" + targetElement).data("dataimg", base64Img[1]);


    }));
    function encodeImageFileAsURL2(cb) {
        return function () {

            var idElement = event.target.id;
            var file = this.files[0];
            var reader = new FileReader();
            reader.onloadend = function () {
                var map = {};

                map[0] = idElement;
                map[1] = reader.result;
                cb(map);
            };
            reader.onload = function (e) {

                itemIMG2 = e.target.result;
                alert(itemIMG2);
            };
            reader.readAsDataURL(file);
        }
    }
})
;
function removeFCE(par) {

    $("#attachment-" + par).remove();
}


function updateSendButtonPosition(val) {
    $("#tblRow-" + 4).remove();
    var myTR = document.createElement('tr');
    myTR.setAttribute("id", "tblRow-" + 4);

    var myTD1 = document.createElement('td');
    myTD1.setAttribute("width", "90%");

    var myTD2 = document.createElement('td');
    myTD2.setAttribute("width", "10%");

    var myButton = document.createElement('input');
    myButton.setAttribute("class", "send");
    myButton.setAttribute("type", "button");
    myButton.setAttribute("value", "Odeslat");

    myTD1.appendChild(myButton);
    myTR.appendChild(myTD1);
    myTR.appendChild(myTD1);
    $("#tab-" + val).append(myTR);
    $(document).resize();
}


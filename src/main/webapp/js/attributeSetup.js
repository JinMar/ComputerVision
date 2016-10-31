/**
 * Created by Marek on 02.10.2016.
 */

$(document).ready(function () {

    var count = 1;
    var index = 1;
    var interval;
    var testData;
    var arraz1 = [];
    var allmethod = [];
    var firstItem = {
        "position": 0,
        "methodId": "",
        "attributes": []
    };

    var itemIMG;
    window.onload = getMethods();
    function encodeImageFileAsURL(cb) {
        return function () {
            var file = this.files[0];
            var reader = new FileReader();
            reader.onloadend = function () {
                cb(reader.result);
            };
            reader.onload = function (e) {
                $('.preview')
                    .attr('src', e.target.result);
                itemIMG = e.target.result;
            };
            reader.readAsDataURL(file);
        }
    }

    ////////////////////////////
    $("#sortable").sortable();
    $("#sortable").disableSelection();
    $(".adding").click(function () {

        count = count + 1;
        $("<li id='attachment-" + count + "'class='col-xs-12 col-sm-12 col-md-12 methodItem'>" +
            "<img class='img-thumbnail preview' id='img-" + count + "' alt='Cinque Terre' width='152'/>" +
            "<div class='methodName'>" +
            "<table cellspacing='0'>" +
            " <tr>" +
            "<td width='90%'>" +
            "<select  id='method-" + count + "' name='comp'>" +
            " <option disabled selected value> -- selection -- </option>" +
            "</select>" +
            "</td>" +
            "<td width='10%'>" +
            "    <button onclick='removeFCE(" + count + ")' class='close'>" + '&times' +
            "</td>" +
            "</tr>" +
            "</table>" +
            "</button></div>" +
            "<table id='tbl-" + count + "' cellspacing='0' width='100%' ></table>" +
            "</li>").appendTo("#sortable");
        if (itemIMG == null) {
            $('.preview')
                .attr('src', '/img/test.png');
        } else {
            $('.preview')
                .attr('src', itemIMG)
        }


        $.each(allmethod, function (key, value) {
            $('#method-' + count)
                .append($("<option></option>")
                    .attr("value", value.idMethod)
                    .text(value.name));
        });

        $('select').on('change', function () {
            var dataToSend = [];
            var item = {
                "pageAttributeId": this.id,
                "idMethod": this.value
            };
            var temp = this.id.split("-");

            var currentPositionElement = temp[1];
            dataToSend.push(item);
            $.ajax({
                url: "/rest/getAttributes",
                type: 'POST',
                data: JSON.stringify(dataToSend),
                dataType: 'json',
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {

                    if (data.pageAttributeId == item.pageAttributeId) {
                        $(".tabRow-" + currentPositionElement).remove();
                        for (var key in data.attributesDTOs) {
                            var recieveData = data.attributesDTOs[key];
                            if (recieveData.name.length != 0) {
                                if (recieveData.attributeType.toLowerCase() == "select".toLowerCase()) {

                                    $("#tbl-" + currentPositionElement).append(
                                        $(
                                            "<tr class='tabRow-" + currentPositionElement + "'>" +
                                            "<td>" + recieveData.name + "</td>" +
                                            "<td><select class='sel' id='param-" + key + "-" + currentPositionElement + "' name='comp'>" +
                                            " <option disabled selected value> -- selection -- </option>" +
                                            "</select></tr>"));

                                    for (var keyOption in recieveData.options) {

                                        $('#param-' + key + "-" + currentPositionElement)
                                            .append($("<option></option>")
                                                .attr("value", keyOption)
                                                .text(recieveData.options[keyOption]));

                                    }
                                }
                                else {

                                    $("#tbl-" + currentPositionElement)
                                        .append($(
                                            "<tr class='tabRow-" + currentPositionElement + "'>" +
                                            "<td>" + recieveData.name + "</td>" +
                                            "<td><input  id='param-" + key + "-" + currentPositionElement + "' type='" + recieveData.attributeType.toLowerCase() + "'  value='61'></td>" +
                                            "</tr>"
                                        ));
                                }

                            }
                        }
                    }

                },
                error: function (data, status, er) {
                    alert("error: " + data + " status: " + status + " er:" + er);
                }
            });

        })
        ;
    })
    ;

    $(".send").click(function () {

        arraz1.push(firstItem);
        var countLoop = $('#sortable li').size();
        var currentLoop = 0;
        //alert("count loop: " + countLoop);
        $('#sortable li').each(function () {
            var number = this.id.split("-")[1];

            var dataToSend = [];
            var item = {
                "pageAttributeId": "data",
                "idMethod": $("#method-" + number).val()
            };
            var finalItem = [];
            dataToSend.push(item);
            var inputData = [];
            var inputDataItem = {
                "value": "",
                "methodAttributeId": ""
            };

            $.ajax({
                url: "/rest/getAttributes",
                type: 'POST',
                data: JSON.stringify(dataToSend),
                dataType: 'json',
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    for (var key in data.attributesDTOs) {
                        var recieveData = data.attributesDTOs[key];
                        inputDataItem = {
                            "value": $("#param-" + key + "-" + number).val(),
                            "methodAttributeId": recieveData.methodAttributesId
                        };
                        inputData.push(inputDataItem);
                    }
                    finalItem = {
                        "position": index,
                        "methodId": $("#method-" + number).val(),
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
                },
                error: function (data, status, er) {
                    alert("error: " + data + " status: " + status + " er:" + er);
                }
            });


        });


    });
    function sendAjax(dataToSend) {
        // alert(JSON.stringify(dataToSend));
        console.log("data to send:   " + JSON.stringify(dataToSend));
        $("#wait").css("display", "block");
        $.ajax({
            url: "/rest/createChain",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(dataToSend),
            contentType: 'application/json',
            mimeType: 'application/json',
            success: function (data) {
                testData = data;
                console.log(data);
                if (data.state) {

                    $(".message").append("<div class='alert alert-success'>" +
                        "<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>" +
                        " <strong>Saved!</strong> " + data.message + "</div>");
                    setTimeout(loadChain, 1000);
                } else {

                    $(".message").append("<div class='alert alert-danger'>" +
                        "<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>" +
                        " <strong>FAILED!</strong> " + data.message + "</div>");
                    $("#wait").css("display", "none");
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
                    console.log("je to: " + data.ready);
                    console.log(JSON.stringify(data));
                    $("#wait").css("display", "none");
                    reDraw(data);
                    clearInterval(interval);

                } else {
                    setTimeout(loadChain, 5000);
                }

                //  ;
            },
            error: function (data, status, er) {
                alert("error: " + data + " status: " + status + " er:" + er);
            }
        });
    }

    function reDraw(data) {
        $('#sortable li').each(function () {
            var number = this.id.split("-")[1];
            removeFCE(number)
        });
        count = 1;

        for (var key in data.parts) {
            var recieveData = data.parts[key];

            count += 1;
            $("<li id='attachment-" + count + "'class='col-xs-12 col-sm-12 col-md-12 methodItem'>" +
                "<img src='" + recieveData.url + "' class='img-thumbnail preview' id='img-" + count + "' alt='Cinque Terre' width='152'/>" +
                "<div class='methodName'>" +
                "<table cellspacing='0'>" +
                " <tr>" +
                "<td width='90%'>" +
                "<select class='sel' id='method-" + count + "' name='comp'>" +

                "</select>" +
                "</td>" +
                "<td width='10%'>" +
                "    <button onclick='removeFCE(" + count + ")' class='close'>" + '&times' +
                "</td>" +
                "</tr>" +
                "</table>" +
                "</button></div>" +
                "<table id='tbl-" + count + "' cellspacing='0' width='100%' ></table>" +
                "</li>").appendTo("#sortable");
            $.each(allmethod, function (key, value) {
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
            for (var key in recieveData.partValueList) {
                var stored = recieveData.partValueList[key];
                if (stored.type.toLowerCase() == "select".toLowerCase()) {
                    $("#tbl-" + count).append(
                        $(
                            "<tr class='tabRow-" + count + "'>" +
                            "<td>" + stored.name + "</td>" +
                            "<td><select   id='param-" + key + "-" + count + "' name='comp'>" +
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
                else {
                    $("#tbl-" + count)
                        .append($(
                            "<tr class='tabRow-" + count + "'>" +
                            "<td>" + stored.name + "</td>" +
                            "<td><input  id='param-" + key + "-" + count + "' type='" + stored.type.toLowerCase() + "'  value='" + stored.value + "'></td>" +
                            "</tr>"
                        ));
                }

            }
        }
        $('.sel').on('change', function () {
            var dataToSend = [];
            var item = {
                "pageAttributeId": this.id,
                "idMethod": this.value
            };
            var temp = this.id.split("-");
            alert("t");
            var currentPositionElement = temp[1];
            dataToSend.push(item);
            $.ajax({
                url: "/rest/getAttributes",
                type: 'POST',
                data: JSON.stringify(dataToSend),
                dataType: 'json',
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {

                    if (data.pageAttributeId == item.pageAttributeId) {
                        $(".tabRow-" + currentPositionElement).remove();
                        for (var key in data.attributesDTOs) {
                            var recieveData = data.attributesDTOs[key];
                            if (recieveData.name.length != 0) {
                                if (recieveData.attributeType.toLowerCase() == "select".toLowerCase()) {

                                    $("#tbl-" + currentPositionElement).append(
                                        $(
                                            "<tr class='tabRow-" + currentPositionElement + "'>" +
                                            "<td>" + recieveData.name + "</td>" +
                                            "<td><select  id='param-" + key + "-" + currentPositionElement + "' name='comp'>" +
                                            " <option disabled selected value> -- selection -- </option>" +
                                            "</select></tr>"));

                                    for (var keyOption in recieveData.options) {

                                        $('#param-' + key + "-" + currentPositionElement)
                                            .append($("<option></option>")
                                                .attr("value", keyOption)
                                                .text(recieveData.options[keyOption]));

                                    }
                                }
                                else {

                                    $("#tbl-" + currentPositionElement)
                                        .append($(
                                            "<tr class='tabRow-" + currentPositionElement + "'>" +
                                            "<td>" + recieveData.name + "</td>" +
                                            "<td><input  id='param-" + key + "-" + currentPositionElement + "' type='" + recieveData.attributeType.toLowerCase() + "'  value='61'></td>" +
                                            "</tr>"
                                        ));
                                }

                            }
                        }
                    }

                },
                error: function (data, status, er) {
                    alert("error: " + data + " status: " + status + " er:" + er);
                }
            });

        })
        ;
        function removeFCE(par) {

            $("#attachment-" + par).remove();
        }
    }

    function removeAtribute(par) {
        $("#" + par).remove();
    }


    $('#inputFileToLoad').change(encodeImageFileAsURL(function (base64Img) {
        var initialIMG = "original";
        var initialMethod;
        var initialMethodAtr;
        var inputData = [];
        var inputDataItem;
        for (var key in allmethod) {

            if (allmethod[key].name.toLowerCase() == initialIMG) {
                initialMethod = allmethod[key].idMethod;
                //alert(allmethod[key].name);
            }
        }
        var dataToSend = [];

        var item = {
            "pageAttributeId": "inputImg",
            "idMethod": initialMethod
        };
        dataToSend.push(item);

        $.ajax({
            url: "/rest/getAttributes",
            type: 'POST',
            data: JSON.stringify(dataToSend),
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            success: function (data) {
                for (var key in data.attributesDTOs) {
                    var recieveData = data.attributesDTOs[key];
                    initialMethodAtr = recieveData.methodAttributesId;
                }
                inputDataItem = {
                    "value": base64Img,
                    "methodAttributeId": initialMethodAtr
                };
                inputData.push(inputDataItem);
                firstItem = {
                    "position": 0,
                    "methodId": initialMethod,
                    "attributes": inputData
                };

                //alert(JSON.stringify(firstItem));
            },
            error: function (data, status, er) {
                alert("error: " + data + " status: " + status + " er:" + er);
            }
        });


    }));

    function getMethods() {
        $.ajax({
            url: "/rest/getMethods",
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
})
;
function removeFCE(par) {

    $("#attachment-" + par).remove();
}



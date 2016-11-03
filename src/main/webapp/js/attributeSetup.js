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
    window.onload = getFunctions();
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
            "</li>").appendTo("#sortable");
        if (itemIMG == null) {
            $('.preview')
                .attr('src', '/img/test.png');
        } else {
            $('.preview')
                .attr('src', itemIMG)
        }


        $.each(allmethod, function (key, value) {
            $('#fce-' + count)
                .append($("<option></option>")
                    .attr("value", value.idMethod)
                    .text(value.name));
        });

        $('.function').on('change', function () {
            var dataToSend = [];
            var item = {
                "pageAttributeId": this.id,
                "objectId": $("#" + this.id).val()
            };

            var temp = this.id.split("-");
            var currentPositionElement = temp[1];

            $('#method-' + currentPositionElement).empty().append('<option disabled selected value> -- Method -- </option>"');
            $('#op-' + currentPositionElement).empty().append('<option disabled selected value> -- Operation -- </option>"');
            $(".tabRow-" + currentPositionElement).remove();
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
        $('.metoda').on('change', function () {

            var temp = this.id.split("-");
            var currentPositionElement = temp[1];
            $('#op-' + currentPositionElement).empty().append('<option disabled selected value> -- Operation -- </option>"');
            $(".tabRow-" + currentPositionElement).remove();
            var dataToSend = [];
            var item = {
                "pageAttributeId": this.id,
                "objectId": $("#" + this.id).val(),

            };


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
        $('.operation').on('change', function () {

            var temp = this.id.split("-");
            var currentPositionElement = temp[1];

            var dataToSend = [];
            var item = {
                "pageAttributeId": this.id,
                "objectId": $("#" + this.id).val(),

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
                    $(".tabRow-" + currentPositionElement).remove();
                    console.log(JSON.stringify(data));
                    for (var key in data.attributesDTOs) {
                        var recieveData = data.attributesDTOs[key];
                        if (recieveData.name.length != 0) {
                            if (recieveData.attributeType.toLowerCase() == "select".toLowerCase()) {

                                $("#tbl-" + currentPositionElement).append(
                                    $(
                                        "<tr class='tabRow-" + key + "'>" +
                                        "<td>" + recieveData.name + "</td>" +
                                        "<td><select class='sel' id='param-" + key + "-" + currentPositionElement + "' name='comp'>" +
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
                            else {

                                $("#tbl-" + currentPositionElement)
                                    .append($(
                                        "<tr class='tabRow-" + key + "'>" +
                                        "<td>" + recieveData.name + "</td>" +
                                        "<td><input  id='param-" + key + "-" + currentPositionElement + "' type='" +
                                        recieveData.attributeType.toLowerCase() + "'  value='" + recieveData.defaultValues + "'" +
                                        "min='" + recieveData.minValue + "' max='" + recieveData.maxValue + "'></td>" +
                                        "</tr>"
                                    ));
                            }
                            $('#param-' + key + "-" + currentPositionElement).attr("data-key");
                            $('#param-' + key + "-" + currentPositionElement).data("key", recieveData.operationAttributesId);
                            console.log($("#param-" + key + "-" + currentPositionElement).data('key'))
                        }
                    }

                },
                error: function (data, status, er) {
                    alert("error: " + data + " status: " + status + " er:" + er);
                }
            });

        });

    })
    ;

    $(".send").click(function () {

        arraz1.push(firstItem);
        var countLoop = $('#sortable li').size();
        var currentLoop = 0;
        //alert("count loop: " + countLoop);
        $('#sortable li').each(function () {
            var number = this.id.split("-")[1];
            var inputData = [];

            var table = document.getElementById('tbl-' + number);

            var rowLength = table.rows.length;
            for (var i = 0; i < rowLength; i += 1) {
                var inputDataItem = {
                    "value": $("#param-" + i + "-" + number).val(),
                    "operationAttributeId": $("#param-" + i + "-" + number).data('key')
                };
                inputData.push(inputDataItem)
            }
            console.log("$(op- + number).val(): " + $("#op-" + number).val());
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


        });


    });
    function sendAjax(dataToSend) {
        // alert(JSON.stringify(dataToSend));

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

                    $("#wait").css("display", "none");
                    clearInterval(interval);
                    reDraw(data);


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
        count = 0;

        for (var key in data.parts) {
            var recieveData = data.parts[key];

            count += 1;
            $("<li id='attachment-" + count + "'class='col-xs-12 col-sm-12 col-md-12 methodItem'>" +
                "<img src='" + recieveData.url + "' class='img-thumbnail preview' id='img-" + count + "' alt='Cinque Terre' width='152'/>" +
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
                "</li>").appendTo("#sortable");
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
                $('#param-' + key + "-" + count).attr("data-key");
                $('#param-' + key + "-" + count).data("key", stored.operationAttributesId);

                console.log($("#param-" + key + "-" + count).data('key'));

            }
        }

        $('.function').on('change', function () {
            var dataToSend = [];
            var item = {
                "pageAttributeId": this.id,
                "objectId": $("#" + this.id).val()
            };

            var temp = this.id.split("-");
            var currentPositionElement = temp[1];

            $('#method-' + currentPositionElement).empty().append('<option disabled selected value> -- Method -- </option>"');
            $('#op-' + currentPositionElement).empty().append('<option disabled selected value> -- Operation -- </option>"');
            $(".tabRow-" + currentPositionElement).remove();
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
        $('.metoda').on('change', function () {

            var temp = this.id.split("-");
            var currentPositionElement = temp[1];
            $('#op-' + currentPositionElement).empty().append('<option disabled selected value> -- Operation -- </option>"');
            $(".tabRow-" + currentPositionElement).remove();
            var dataToSend = [];
            var item = {
                "pageAttributeId": this.id,
                "objectId": $("#" + this.id).val(),

            };


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
        $('.operation').on('change', function () {

            var temp = this.id.split("-");
            var currentPositionElement = temp[1];

            var dataToSend = [];
            var item = {
                "pageAttributeId": this.id,
                "objectId": $("#" + this.id).val(),

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
                    $(".tabRow-" + currentPositionElement).remove();
                    console.log(JSON.stringify(data));
                    for (var key in data.attributesDTOs) {
                        var recieveData = data.attributesDTOs[key];
                        if (recieveData.name.length != 0) {
                            if (recieveData.attributeType.toLowerCase() == "select".toLowerCase()) {

                                $("#tbl-" + currentPositionElement).append(
                                    $(
                                        "<tr class='tabRow-" + key + "'>" +
                                        "<td>" + recieveData.name + "</td>" +
                                        "<td><select class='sel' id='param-" + key + "-" + currentPositionElement + "' name='comp'>" +
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
                            else {

                                $("#tbl-" + currentPositionElement)
                                    .append($(
                                        "<tr class='tabRow-" + key + "'>" +
                                        "<td>" + recieveData.name + "</td>" +
                                        "<td><input  id='param-" + key + "-" + currentPositionElement + "' type='" +
                                        recieveData.attributeType.toLowerCase() + "'  value='" + recieveData.defaultValues + "'" +
                                        "min='" + recieveData.minValue + "' max='" + recieveData.maxValue + "'></td>" +
                                        "</tr>"
                                    ));
                            }
                            $('#param-' + key + "-" + currentPositionElement).attr("data-key");
                            $('#param-' + key + "-" + currentPositionElement).data("key", recieveData.operationAttributesId);
                            console.log($("#param-" + key + "-" + currentPositionElement).data('key'))
                        }
                    }

                },
                error: function (data, status, er) {
                    alert("error: " + data + " status: " + status + " er:" + er);
                }
            });

        });
        function removeFCE(par) {

            $("#attachment-" + par).remove();
        }
    }

    function removeAtribute(par) {
        $("#" + par).remove();
    }


    $('#inputFileToLoad').change(encodeImageFileAsURL(function (base64Img) {


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

    function getFunctions() {
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
})
;
function removeFCE(par) {

    $("#attachment-" + par).remove();
}



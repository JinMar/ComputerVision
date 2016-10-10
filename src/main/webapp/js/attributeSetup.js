/**
 * Created by Marek on 02.10.2016.
 */
$(document).ready(function () {

    var count = 1;
    var index = 2;
    var arraz1 = [];
    var allmethod = [];
    var firstItem = {
        "position": "",
        "body": "",
        "method": "",
        "value": "",
        "url": "",
        "atr": this.id
    };
    window.onload = getMethods();
    $(function () {


        ////////////////////////////
        $("#sortable").sortable();
        $("#sortable").disableSelection();
        $(".adding").click(function () {

            count = count + 1;
            $("<li id='attachment-" + count + "'class='col-xs-12 col-sm-12 col-md-12 methodItem'>" +
                "<img src='/img/test.png'  class='img-thumbnail preview' alt='Cinque Terre' width='152'/>" +

                "<div class='methodName'>" +
                "<table cellspacing='0'>" +
                " <tr>" +
                "<td width='90%'>" +
                "<select  id='param1-" + count + "' name='comp'>" +
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

            $.each(allmethod, function (key, value) {
                $('#param1-' + count)
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
                                                "<td><select  id='param-" + currentPositionElement + "' name='comp'>" +
                                                " <option disabled selected value> -- selection -- </option>" +
                                                "</select></tr>"));

                                        for (var key in recieveData.options) {

                                            $('#param-' + currentPositionElement)
                                                .append($("<option></option>")
                                                    .attr("value", key)
                                                    .text(recieveData.options[key]));

                                        }
                                    }
                                    else {

                                        $("#tbl-" + currentPositionElement)
                                            .append($(
                                                "<tr class='tabRow-" + currentPositionElement + "'>" +
                                                "<td>" + recieveData.name + "</td>" +
                                                "<td><input  id='param-" + count + "' type='number'  value='61'></td>" +
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
                })
                ;

            })
            ;
        })
        ;

        $(".send").click(function () {
            // var ids = $("#sortable li").map(function () {
            //     return this.id.split("-")[1]; }).get().join(",");
            //  alert(JSON.stringify(ids));
            arraz1 = [];
            arraz1.push(firstItem);

            $('#sortable li').each(function () {

                var number = this.id.split("-")[1];


                var item = {
                    "position": index,
                    "body": "",
                    "method": $("#param1-" + number).val(),
                    "value": $("#param2-" + number).val(),
                    "url": "",
                    "atr": this.id
                };
                arraz1.push(item);
                index = index + 1;
            });
            sendAjax()
        });
        function sendAjax() {

            $.ajax({
                url: "/rest/ajax",
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(arraz1),
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    index = 2;
                    count = 1;
                    var receivedJson = JSON.stringify(data);

                    var text = "";
                    var parsed = new Function('return ' + receivedJson)();
                    for (var i = 0; i < parsed.length; i++) {
                        count = count + 1;

                        if (i != 0) {
                            text += parsed[i].atr + " " + parsed[i].method + " " + parsed[i].value + " " + parsed[i].url + " " + parsed[i].body + "\n";
                            if (parsed[i].wrongtype != null) {

                            } else {
                                removeAtribute(parsed[i].atr);
                                if (parsed[i].histogramURL == null) {


                                    $("<li id='attachment-" + count + "'class='ui-state-default'><label   for='comp'>METHOD</label><select" +
                                        " id='param1-" + count + "' name='comp'>" +
                                        "<option value='red'>Red</option>" +
                                        "<option value='green'>Green</option>" +
                                        "<option value='blue'>Blue</option>" +
                                        "<option value='gray'>Gray</option>" +
                                        "<option value='gray'>Gray</option>" +
                                        "<option value='hchannel'>HChannel</option>" +
                                        "<option value='ychannel'>YChannel</option>" +
                                        "<option value='cbchannel'>CbChannel</option>" +
                                        "<option value='crchannel'>CrChannel</option>" +
                                        "<option value='erode'>Erode</option>" +
                                        "<option value='dilate'>Dilate</option>" +
                                        "<option value='open'>Open</option>" +
                                        "<option value='close'>Close</option>" +
                                        "<option value='tophat'>Tophat</option>" +
                                        "<option value='coloringmethod'>Coloring method</option>" +
                                        "<option value='simplyaverage'>Simply average</option>" +
                                        "<option value='median'>Median</option>" +
                                        "<option value='rotatingmask'>Rotating mask</option>" +
                                        "<option value='sobel'>Sobel</option>" +
                                        "<option value='laplacian'>Laplacian</option>" +
                                        "<option value='segmentation'>Segmentace</option>" +
                                        "</select><label for='quantity'>VALUE </label>" +
                                        "<input  type='number' id='param2-" + count + "' class='my' name='quantity' min='1' max='5'><button onclick='removeFCE(" + count + ")'>Remove " +
                                        "</button> " +
                                        "<a href='" + parsed[i].url + "' data-featherlight='image'>Result</a></br>" +

                                        " </li>").appendTo("#sortable");
                                }
                                else {

                                    $("<li id='attachment-" + count + "'class='ui-state-default'><label   for='comp'>METHOD</label><select" +
                                        " id='param1-" + count + "' name='comp'>" +
                                        "<option value='red'>Red</option>" +
                                        "<option value='green'>Green</option>" +
                                        "<option value='blue'>Blue</option>" +
                                        "<option value='gray'>Gray</option>" +
                                        "<option value='gray'>Gray</option>" +
                                        "<option value='hchannel'>HChannel</option>" +
                                        "<option value='ychannel'>YChannel</option>" +
                                        "<option value='cbchannel'>CbChannel</option>" +
                                        "<option value='crchannel'>CrChannel</option>" +
                                        "<option value='erode'>Erode</option>" +
                                        "<option value='dilate'>Dilate</option>" +
                                        "<option value='open'>Open</option>" +
                                        "<option value='close'>Close</option>" +
                                        "<option value='tophat'>Tophat</option>" +
                                        "<option value='coloringmethod'>Coloring method</option>" +
                                        "<option value='simplyaverage'>Simply average</option>" +
                                        "<option value='median'>Median</option>" +
                                        "<option value='rotatingmask'>Rotating mask</option>" +
                                        "<option value='sobel'>Sobel</option>" +
                                        "<option value='laplacian'>Laplacian</option>" +
                                        "<option value='segmentation'>Segmentace</option>" +
                                        "</select><label for='quantity'>VALUE </label>" +
                                        "<input  type='number' id='param2-" + count + "' class='my' name='quantity' min='1' max='5'><button onclick='removeFCE(" + count + ")'>Remove " +
                                        "</button> " +
                                        "<a href='" + parsed[i].url + "' data-featherlight='image'>Result</a></br>" +
                                        "<a href='" + parsed[i].histogramURL + "' data-featherlight='image'>Histogram</a></br>" +
                                        " </li>").appendTo("#sortable");
                                }
                                $("#param2-" + count).val(parsed[i].value);
                                $("#param1-" + count).val(parsed[i].method);
                            }
                        }
                    }

                },
                error: function (data, status, er) {
                    alert("error: " + data + " status: " + status + " er:" + er);
                }
            });
        }

        function encodeImageFileAsURL(cb) {
            return function () {
                var file = this.files[0];
                var reader = new FileReader();
                reader.onloadend = function () {
                    cb(reader.result);
                };
                reader.readAsDataURL(file);
            }
        }

        $('#inputFileToLoad').change(encodeImageFileAsURL(function (base64Img) {
            firstItem = {
                "position": 1,
                "body": base64Img,
                "method": "sourceImg",
                "value": 0,
                "url": "",
                "atr": ""
            };

        }));


    })
    ;


    function removeAtribute(par) {
        $("#" + par).remove();
    }

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

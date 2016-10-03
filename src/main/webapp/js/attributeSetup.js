/**
 * Created by Marek on 02.10.2016.
 */


var count = 1;
var index = 2;
var arraz1 = [];
var firstItem = {
    "position": "",
    "body": "",
    "method": "",
    "value": "",
    "url": "",
    "atr": this.id
};

$(function () {


    ////////////////////////////
    $("#sortable").sortable();
    $("#sortable").disableSelection();
    $(".adding").click(function () {
        getMethods();
        count = count + 1;
        $("<li id='attachment-" + count + "'class='ui-state-default'>" +
            "<label   for='comp'>METHOD</label><select" +
            " id='param1-" + count + "' name='comp'>" +
            "<option value='red'>Red</option>" +
            "<option value='green'>Green</option>" +
            "<option value='blue'>Blue</option>" +
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

            "<input  type='number' id='param2-" + count + "' class='my' name='quantity' min='1' max='5'>" +
            "<button onclick='removeFCE(" + count + ")'>Remove " +
            "</button></li>").appendTo("#sortable");


    });
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
                            alert(parsed[i].wrongtype);
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


});
function removeFCE(par) {
    $("#attachment-" + par).remove();
}
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


        },
        error: function (data, status, er) {
            alert("error: " + data + " status: " + status + " er:" + er);
        }
    });
}

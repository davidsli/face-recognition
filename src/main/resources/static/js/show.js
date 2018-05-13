var web_root = "http://127.0.0.1:8080";
$(document).ready(function () {
    $(".search-result").empty();
    var fd = new FormData();
    $.ajax({
        type: 'GET',
        url: '/person/show',
    }).success(function (res) {
        if (res.code === "400") {
            alert(res.data);
            return;
        }
        var data = res.data;
        var lost = data.lostChildren;
        var find = data.findChildren;
        var selector;
        var url;
        lost.forEach(function(value, index) {
            url = "/upload/lostfaceimages/" + value.photoName;
            switch ((index + 1) % 2) {
                case 1 : {
                    selector = $(".lost-result-1");
                    break;
                }
                case 0 : {
                    selector = $(".lost-result-2");
                    break;
                }
            }
            selector.append("<figure data-am-widget=\"figure\" class=\"am am-figure am-figure-default \"   data-am-figure=\"{  pureview: 'true' }\">" +
                "<img src=\"" + web_root + url + "\" data-rel=\"" + web_root + url + "\" alt=\"" +
                "姓名：" + value.personName + " | " +
                "联系电话：" + value.phone + " | " +
                "拍照时间：" + value.photoTime + " | " +
                "拍照地点：" + value.photoAddress + " | " +"\" />" +
                "<figcaption class=\"am-figure-capition-btm\">" +
                "姓名：" + value.personName + "<br>" +
                "联系电话：" + value.phone + "<br>" +
                "拍照时间：" + value.photoTime + "<br>" +
                "拍照地点：" + value.photoAddress + "<br>" +
                "</figcaption></figure>");
        });
        find.forEach(function(value, index) {
            url = "/upload/findfaceimages/" + value.photoName;
            switch ((index + 1) % 2) {
                case 1 : {
                    selector = $(".find-result-1");
                    break;
                }
                case 0 : {
                    selector = $(".find-result-2");
                    break;
                }
            }
            selector.append("<figure data-am-widget=\"figure\" class=\"am am-figure am-figure-default \"   data-am-figure=\"{  pureview: 'true' }\">" +
                "<img src=\"" + web_root + url + "\" data-rel=\"" + web_root + url + "\" alt=\"" +
                "姓名：" + value.personName + " | " +
                "联系电话：" + value.phone + " | " +
                "拍照时间：" + value.photoTime + " | " +
                "拍照地点：" + value.photoAddress + " | " +"\" />" +
                "<figcaption class=\"am-figure-capition-btm\">" +
                "姓名：" + value.personName + "<br>" +
                "联系电话：" + value.phone + "<br>" +
                "拍照时间：" + value.photoTime + "<br>" +
                "拍照地点：" + value.photoAddress + "<br>" +
                "</figcaption></figure>");
        });
        AMUI.figure.init();
    }).error(function (err) {
        console.error(err);
    });
});
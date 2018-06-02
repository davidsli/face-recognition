var web_root = "http://127.0.0.1:8080";

$(":file").on('change', preview);

var dataURL = null;

function preview(e) {
    $("#process").hide();
    if (e.target.files == null) {
        alert("please select picture");
        return
    }
    var file = e.target.files[0];
    var reader = new FileReader();

    reader.onloadend = function () {
        // 图片的 base64 格式, 可以直接当成 img 的 src 属性值
        dataURL = reader.result;
        console.log(dataURL)
        var img = new Image();
        img.src = dataURL;
        img.className = "am-img-responsive";
        $(".color-table-upload").empty();
        $(".search-result").empty();
        $(".color-table-upload").append(img);
    };

    reader.readAsDataURL(file); // 读出 base64

}

function dataURItoBlob(dataURI) {
    var byteString = atob(dataURI.split(',')[1]);
    var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
    var ab = new ArrayBuffer(byteString.length);
    var ia = new Uint8Array(ab);
    for (var i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
    }
    return new Blob([ab], {type: mimeString});
}

$(document).ready(function () {
    $("#process").hide();
});
var progress = 0;
var onprocess = true;
$("#submit").click(function () {

    progress = 0;
    onprocess = true
    $(".am-progress-bar-success").css("width", "0%");
    $(".am-progress-bar-success").text("0%");
    $(".search-result").empty();
    var fd = new FormData();
    var blob = dataURItoBlob(dataURL);
    fd.append('file', blob);
    fd.append("type", $("input[name='type']:checked").val());
    var type = $("input[name='type']:checked").val();
    $.ajax({
        type: 'POST',
        url: '/person/search',
        data: fd,
        processData: false, // 不会将 data 参数序列化字符串
        contentType: false, // 根据表单 input 提交的数据使用其默认的 contentType
    }).success(function (res) {
        onprocess = false;
        if (res.code === "400") {
            alert(res.data);
            return;
        }
        console.log(res);
        var times = 0;
        var selector;
        var url;
        res.data.forEach(function(value, index) {
            var person = value.person;
            console.log(person.type);
            console.log(person.type === 1)
            url = (person.type === 1 ? "/upload/lostfaceimages/" :  "/upload/findfaceimages/") + person.photoName;
            console.log(times)
            switch (times) {
                case 0 : {
                    selector = $(".search-result-1");
                    times++;
                    break;
                }
                case 1 : {
                    selector = $(".search-result-2");
                    times++;
                    break;
                }
                case 2 : {
                    selector = $(".search-result-3");
                    times++;
                    break;
                }
                case 3 : {
                    selector = $(".search-result-4");
                    times = 0;
                    break;
                }
            }
            //
            if (person.type === 1) {
                selector.append("<figure data-am-widget=\"figure\" class=\"am am-figure am-figure-default \"   data-am-figure=\"{  pureview: 'true' }\">" +
                    "<img src=\"" + web_root + url + "\" data-rel=\"" + web_root + url + "\" alt=\"" +
                    "匹配度:" + Math.round(value.compareValue * 100) + "%" + " | " +
                    "姓名：" + person.personName + " | " +
                    "年龄：" + person.age + " | " +
                    "爱心热线：" + person.phone + " | " +
                    "走失描述：" + person.note + " | " + "\" />" +
                    "<figcaption class=\"am-figure-capition-btm\">" +
                    "匹配度:" + Math.round(value.compareValue * 100) + "%" + "<br>" +
                    "姓名：" + person.personName + "<br>" +
                    "年龄：" + person.age + "<br>" +
                    "爱心热线：" + person.phone + "<br>" +
                    "走失描述：" + person.note + "<br>" +
                    "</figcaption></figure>");
            } else {
                selector.append("<figure data-am-widget=\"figure\" class=\"am am-figure am-figure-default \"   data-am-figure=\"{  pureview: 'true' }\">" +
                    "<img src=\"" + web_root + url + "\" data-rel=\"" + web_root + url + "\" alt=\"" +
                    "匹配度:" + Math.round(value.compareValue * 100) + "%" + " | " +
                    "爱心热线：" + person.phone + " | " +
                    "拍照时间：" + person.photoTime + " | " +
                    "拍照地点：" + person.photoAddress + " | " +
                    "其他信息：" + person.note + " | " + "\" />" +
                    "<figcaption class=\"am-figure-capition-btm\">" +
                    "匹配度:" + Math.round(value.compareValue * 100) + "%" + "<br>" +
                    "爱心热线：" + person.phone + "<br>" +
                    "拍照时间：" + person.photoTime + "<br>" +
                    "拍照地点：" + person.photoAddress + "<br>" +
                    "其他信息：" + person.note + "<br>" +
                    "</figcaption></figure>");
            }
        });
        AMUI.figure.init();
        $(".am-progress-bar-success").css("width", "100%");
        $(".am-progress-bar-success").text("100%");
        $("#process").hide();
    }).error(function (err) {
        console.error(err);
    });
    process();
});

function process() {
    progress = 0;
    $("#process").show();
    onprogress();
}

function onprogress () {
    // 随机时间
    var timeout = random(10, 30);

    if (onprocess) {
        setTimeout(function () {

            progress += random(6, 10);

            // 随机进度不能超过 98%，以免页面还没加载完毕，进度已经 100% 了

            if(progress > 98){
                progress = 98;
            }

            if ($(".am-progress-bar-success").text() === "100%") {
                progress = 100;
            }
            $(".am-progress-bar-success").css("width", progress + '%');
            $(".am-progress-bar-success").text(progress + '%');
            onprogress();
        }, timeout);
    }
};
var random = function(min, max){
    return Math.floor(Math.random() * (max - min + 1) + min);
};
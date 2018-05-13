var web_root = "http://127.0.0.1:8080";

$("input").on('change', preview);

var dataURL = null;

function preview(e) {
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

$("#submit").click(function () {
    $(".search-result").empty();
    var fd = new FormData();
    var blob = dataURItoBlob(dataURL);
    fd.append('file', blob);
    fd.append("type", $("input[name='type']:checked").val());
    var type = $("input[name='type']:checked").val()
    $.ajax({
        type: 'POST',
        url: '/person/search',
        data: fd,
        processData: false, // 不会将 data 参数序列化字符串
        contentType: false, // 根据表单 input 提交的数据使用其默认的 contentType
    }).success(function (res) {
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
            selector.append("<figure data-am-widget=\"figure\" class=\"am am-figure am-figure-default \"   data-am-figure=\"{  pureview: 'true' }\">" +
                "<img src=\"" + web_root + url + "\" data-rel=\"" + web_root + url + "\" alt=\"" +
                "匹配度:" + Math.round(value.compareValue * 100)  + "%" + " | " +
                "姓名：" + person.personName + " | " +
                "联系电话：" + person.phone + " | " +
                "拍照时间：" + person.photoTime + " | " +
                "拍照地点：" + person.photoAddress + " | " + "\" />" +
                "<figcaption class=\"am-figure-capition-btm\">" +
                "匹配度:" + Math.round(value.compareValue * 100)  + "%" + "<br>" +
                "姓名：" + person.personName + "<br>" +
                "联系电话：" + person.phone + "<br>" +
                "拍照时间：" + person.photoTime + "<br>" +
                "拍照地点：" + person.photoAddress + "<br>" +
                "</figcaption></figure>");
        });
        AMUI.figure.init();
    }).error(function (err) {
        console.error(err);
    });
});
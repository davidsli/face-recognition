$("#file").on('change', preview);

var dataURL = null;

function preview(e) {
    if (e.target.files == null) {
        alert("please select picture")
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
    var fd = new FormData();
    var blob = dataURItoBlob(dataURL);
    fd.append('file', blob);
    fd.append("username", $("#username").val());
    $.ajax({
        type: 'POST',
        url: '/picture/add',
        data: fd,
        processData: false, // 不会将 data 参数序列化字符串
        contentType: false, // 根据表单 input 提交的数据使用其默认的 contentType
    }).success(function (res) {
        console.log(res)
        alert(res.data)
    }).error(function (err) {
        console.error(err);
    });
});
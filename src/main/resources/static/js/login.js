var web_root = "http://127.0.0.1:8080";

$("#submit").click(function () {
    $.ajax({
        type: 'POST',
        url: '/user/login',
        data: {
            username : $(".account").val(),
            password : $(".pwd").val()
        },
    }).success(function (res) {
        if (res.code === "400") {
            alert(res.data);
            return;
        }
        if (res.code === "200") {
            window.location.href = "/page/show";
        }
    }).error(function (err) {
        console.error(err);
    });
});

$(document).ready(function() {
    $.ajax({
        type: 'GET',
        url: '/person/slider',
    }).success(function (res) {
        if (res.code === "400") {
            alert(res.data);
            return;
        }
        if (res.code === "200") {
            var slider = res.data;
            console.log(slider)
            var url;
            slider.forEach(function(value, index) {
                url = "/upload/lostfaceimages/" + value.photoName;
                $(".am-slides").append("<li>" +
                    "<img src=\"" + web_root + url + "\" >" +
                    "<div class=\"am-slider-desc\">" +
                    "姓名：" + value.personName + " | " +
                    "年龄：" + value.age + " | " +
                    "爱心热线：" + value.phone + " | " +
                    "走失描述：" + value.note
                    + "</div>");
            });
        }
        AMUI.slider.init();
    }).error(function (err) {
        console.error(err);
    });
})
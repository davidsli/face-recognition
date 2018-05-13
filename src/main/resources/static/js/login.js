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
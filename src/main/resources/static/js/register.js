$("#submit").click(function () {
    $.ajax({
        type: 'POST',
        url: '/user/register',
        data: {
            username : $(".account").val(),
            password : $(".pwd1").val(),
            repeatPassword : $(".pwd2").val()
        },
    }).success(function (res) {
        if (res.code === "400") {
            alert(res.data);
            return;
        }
        if (res.code === "200") {
            alert("success");
            window.location.href = "/page/login";
        }
    }).error(function (err) {
        console.error(err);
    });
});
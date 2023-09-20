/*회원수정*/
function update(userId, event) {
    event.preventDefault();
    let data = $("#profileUpdate").serialize();
    console.log(data);

    $.ajax({
        type       : "put",
        url        : `/api/user/${userId}`,
        data       : data,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType   : "json"
    }).done(resp => {
        console.log("회원정보 수정 성공", resp);
        alert("회원정보 수정 성공");
        location.href = "/"
    }).fail(error => {
        alert("회원정보 수정 실패 \n원인:" + JSON.stringify(error.JSON.stringify(error.responseJSON.data)));
    });
}
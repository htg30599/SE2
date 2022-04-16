function dropDown(event) {
    var value = $("#search").value();
    if (value.length > 1) {
        $.ajax({
            type: "GET",
            url: "/user/search?value=" + value,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 5000,
            success: function (data) {
                $('.dropdown-toggle').dropDown();
                $("#dropdown-container").empty();
                $("#dropdown-container").append(data);
            },
            error: function (e) {
                console.log("Error: ", e);
            }
        })
    }
}
function loadMoreProducts(categoryId) {
    var loadMoreBtn = document.getElementById("loadMoreBtn");
    loadMoreBtn.disabled = true;

    var amount = document.getElementsByClassName("product").length;
    var url = '/load';
    if (categoryId !== undefined) {
        url += '?cId=' + categoryId;
    }

    // Hiển thị URL ra console
    console.log("URL: " + url);

    $.ajax({
        url: url,
        type: 'GET',
        data: {
            exits: amount
        },
        success: function (data) {
            var row = document.getElementById("content");
            row.innerHTML += data;
        },
        error: function (xhr) {
            // Xử lý lỗi
        }
    });

    loadMoreBtn.disabled = false;
}

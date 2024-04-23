window.addEventListener('DOMContentLoaded', event => {
    // Simple-DataTables
    // https://github.com/fiduswriter/Simple-DataTables/wiki

    const datatablesSimple = document.getElementById('datatablesSimple');
    if (datatablesSimple) {
        new simpleDatatables.DataTable(datatablesSimple);
    }
});

window.addEventListener('DOMContentLoaded', event => {
    // Lấy danh sách tất cả các ô td trong bảng
    const tableCells = document.querySelectorAll('td');

    // Duyệt qua từng ô td và thêm lớp và thuộc tính CSS để căn giữa
    tableCells.forEach(cell => {
        // Thêm lớp align-middle để căn giữa theo chiều dọc
        cell.classList.add('align-middle');
        // Thêm lớp text-center để căn giữa theo chiều ngang
        // cell.classList.add('text-center');
    });
});

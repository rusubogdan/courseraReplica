/**
* Common functionality for all pages
*/

$(document).on('click', '#logoutHref', function (e) {
    e.preventDefault();
    $('#logoutForm').submit();
    return false;
});
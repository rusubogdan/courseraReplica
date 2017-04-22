$(document).ready(function() {
    var $courseDescription = $('#courseDescription');

    $('#updateCourse').on({
        'click': function (event) {
            event.preventDefault();

            var courseId = $('#courseId').html();
            var course = {
                    'id': courseId,
                    'name': $('#courseTitle').val(),
                    'description': $courseDescription.summernote('code')
            };

            $.ajax({
                type: 'POST',
                url: '/courses/'+ courseId + '/edit/do',
                data: JSON.stringify(course), // without stringify, the Jackson mapping is failing
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                success: function (response) {
                    if (response.success) {
                        noty({
                            text: 'Salvat!',
                            type: 'success',
                            animation: {
                                open: {height: 'toggle'},
                                close: {height: 'toggle'},
                                easing: 'swing',
                                speed: 500 // opening & closing animation speed
                            }
                        });
                    }
                },
                fail: function (error) {
                    console.log(error);
                }
            })
        }
    });

    // apply summernote on description
    $courseDescription.summernote({
        width: 750,
        height: 300
    });
//    $courseDescription.summernote('code', $courseDescription.html());

});

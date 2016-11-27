$(document).ready(function() {
    var $courseDescription = $('#courseDescription');

    $('#updateCourse').on({
        'click': function (event) {
            event.preventDefault();
            var course = {
                    'id': $('#courseId').html(),
                    'name': $('#courseTitle').val(),
                    'description': $courseDescription.summernote('code')
            };

            $.ajax({
                type: 'POST',
                url: '/courses/1/edit/do',
                data: JSON.stringify(course), // without stringify, the Jackson mapping is failing
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                success: function (response) {
                    console.log(response);
                },
                fail: function (error) {
                    console.log(error);
                }
            })
        }
    });

    // apply summernote on description
    $courseDescription.summernote();
    $courseDescription.summernote('code', $courseDescription.html());

});

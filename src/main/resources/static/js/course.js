/**
 * Common functionality for all pages
 */

$(document).ready(function() {
    $('#courseTitle').summernote({
        toolbar: [
            ['fontsize', ['fontsize']]
        ]
    });
    $('#courseDescription').summernote();

    $('#saveCourse').ready(function() {
        $('#doSaveCourse').on({
            'click': function() {
                var courseTitle = $('#courseTitle').summernote('code');
                var courseDescription = $('#courseDescription').summernote('code');

                $.post("/courses/ajax/saveCourse", {
                    "courseTitle": courseTitle,
                    "courseDescription": courseDescription
                }, function (response) {
                    console.log(response);
                })
                .fail(function (error) {
                    console.log(error)
                });
            }
        });
    }
    );
});



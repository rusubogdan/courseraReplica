/**
 * Common functionality for all pages
 */

$(document).ready(function() {
    $('#courseDescription').summernote();

    $('#saveCourse').ready(function() {
        $('#doSaveCourse').on({
            'click': function() {
                var courseTitle = $('#courseTitle').val();
                var courseDescription = $('#courseDescription').summernote('code');

                $.post("/courses/ajax/saveCourse", {
                    "courseTitle": courseTitle,
                    "courseDescription": courseDescription
                }, function (response) {
                    console.log(response);
                    window.location='/courses/' + response;
                })
                .fail(function (error) {
                    console.log(error)
                });
            }
        });
    }
    );
});



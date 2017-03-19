$(document).ready(function() {
    var course = {
        id: $('#courseId').html()
    };

    $('#acc-description').accordion({
        collapsible: true
    });

    $('#acc-chapters').accordion({
        collapsible: true
    });

    $('#chapter-description-0').summernote({
        height: 150
    });

    $('#new-chapter-save').on({
        'click': function (event) {
            event.preventDefault();

            console.log("save");

            var noOfChapters = $('#acc-chapters').children('h3').length;
            var chapter = {
                name: $('#chapter-title-0').val(),
                description: $('#chapter-description-0').summernote('code'),
                position: noOfChapters
            };

            if (chapter.name.trim() == '') {
                noty({
                    text: 'Empty name!',
                    animation: {
                        open: {height: 'toggle'},
                        close: {height: 'toggle'},
                        easing: 'swing',
                        speed: 500 // opening & closing animation speed
                    }
                });

                return;
            }

            $.ajax({
                type: 'POST',
                url: '/courses/' + course.id + '/ch/newChapter/do',
                data: JSON.stringify(chapter),
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                success: function (response) {
                    console.log(response);
                    var n = noty({
                        text: 'Saved!',
                        animation: {
                            open: {height: 'toggle'},
                            close: {height: 'toggle'},
                            easing: 'swing',
                            speed: 500 // opening & closing animation speed
                        }
                    });

                },
                fail: function (error) {
                    console.log(error);
                    noty({
                        text: 'Error!',
                        animation: {
                            open: {height: 'toggle'},
                            close: {height: 'toggle'},
                            easing: 'swing',
                            speed: 500 // opening & closing animation speed
                        }
                    });
                }
            });
        }
    });

    $('#new-chapter-cancel').on({
        'click': function (event) {
            event.preventDefault();

            console.log("cancel");
        }
    });
});
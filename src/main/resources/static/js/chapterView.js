$(document).ready(function() {

    $('#new-paragraph').on({
        'click': function (event) {
            event.preventDefault();

            var courseId = $('#course-id').html();
            var chapterId = $('#chapter-id').html();

            var newParagraph = $('#add-paragraph-template').clone();
            var noOfParagraphs = $('#no-of-paragraphs').html();
            newParagraph
                .attr('id', 'paragraph-' + ++noOfParagraphs)
                .removeAttr('hidden');

            var newParHolder = $('#new-par-holder');
            newParHolder.append(newParagraph);
            var newParagraphText = newParagraph.find('.new-paragraph-text');
            newParagraphText.summernote({
                height: 200,
                focus: true
            });


            newParagraphText.trigger('focus');
            // at save

            var saveBtn = $('#save-para-null').clone();
            saveBtn.attr('id', 'save-para-' + ++noOfParagraphs);
            saveBtn.removeAttr('hidden');
            newParagraph.append(saveBtn);

            saveBtn.on({
                'click': function (event) {
                    event.preventDefault();

                    var paragraph = {
                        text: newParagraphText.summernote('code'),
                        position: noOfParagraphs
                    };

                    $.ajax({
                        type: 'POST',
                        url: '/courses/'+ courseId + '/ch/' + chapterId + '/newParagraph',
                        data: JSON.stringify(paragraph), // without stringify, the Jackson mapping is failing
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        success: function (response) {
                            console.log(response);

                            if (response.success) {
                                location.reload();
                            }
                        },
                        fail: function (error) {
                            console.log(error);
                        }
                    });
                }
            });
        }
    });

    $('.delete-href').each(function (index) {
       $(this).on({
           'click': function (event) {
               var a = $(event.target);

               if(confirm("Vrei sa stergi paragraful?")) {
                   console.log("da");

                   var courseId = $('#course-id').html();
                   var chapterId = $('#chapter-id').html();
                   var paragraphId = a.parents('.row.par-holder').children('.par-id-holder').html();

                   $.ajax({
                       type: 'POST',
                       url: '/courses/'+ courseId + '/ch/' + chapterId + '/par/' + paragraphId + '/delete',
                       dataType: 'json',
                       contentType: 'application/json; charset=utf-8',
                       success: function (response) {
                           console.log(response);

                           if (response.success) {
                               location.reload();
                           }
                       },
                       fail: function (error) {
                           console.log(error);
                       }
                   });
               }
           }
       });
    });

    $('.edit-href').each(function (index) {
        $(this).on({
            'click': function (event) {
                var a = $(event.target);
                a.attr('hidden', 'hidden');

                var saveBtn = $('#save-edit-para-null').clone();
                saveBtn.attr('id', 'save-edit-para-some')
                    .removeAttr('hidden');
                var cancelBtn = $('#cancel-edit-para-null').clone();
                cancelBtn.attr('id', 'cancel-edit-para-some')
                    .removeAttr('hidden');

                a.after(saveBtn)
                    .after(cancelBtn);

                saveBtn.parents('.row.par-holder').children('.col-md-12.course-content').summernote();

                saveBtn.on({
                    'click': function (event) {
                        event.preventDefault();

                        var courseId = $('#course-id').html();
                        var chapterId = $('#chapter-id').html();

                        var paragraphId = saveBtn.parents('.row.par-holder').children('.par-id-holder').html();
                        var paragraphText = saveBtn.parents('.row.par-holder').children('.col-md-12.course-content')
                            .summernote('code');
                        console.log(paragraphId + ' ' + paragraphText);
                        $.ajax({
                            type: 'POST',
                            url: '/courses/'+ courseId + '/ch/' + chapterId + '/par/' + paragraphId + '/edit',
                            data: paragraphText,
                            dataType: 'json',
                            contentType: 'application/json; charset=utf-8',
                            success: function (response) {
                                console.log(response);

                                if (response.success) {
                                    location.reload();
                                }
                            },
                            fail: function (error) {
                                console.log(error);
                            }
                        });
                    }
                })
            }
        });
    });

    $('.completed-checker.unchecked').waypoint(function (direction) {
        var par = $(this.element);

        // waypoint can't update it's controlled elements dynamically, yet
        if (par.attr('class') === 'completed-checker checked') {
            return;
        }

        var parId = par.parents('.par-holder').children('.par-id-holder').html();
        var chapterId = $('#chapter-id').html();

        var request = {
            paragraphId: parId,
            chapterId: chapterId
        };

        $.ajax({
            type: 'POST',
            url: '/v1/ajax/completeParagraph',
            data: JSON.stringify(request), // without stringify, the Jackson mapping is failing
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            success: function (response) {
                console.log(response);

                par.removeClass('unchecked')
                    .addClass('checked');

            },
            fail: function (error) {
                console.log(error);
            }
        });
    });
});

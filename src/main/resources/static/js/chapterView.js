$(document).ready(function() {

    $('#new-paragraph').on({
        'click': function (event) {
            event.preventDefault();

            var addNewParagraph = $(this);
            addNewParagraph.attr('hidden', 'hidden');

            var courseId = $('#course-id').html();
            var chapterId = $('#chapter-id').html();

            var newParagraph = $('#add-paragraph-template').clone();
            var noOfParagraphs = $('#no-of-paragraphs').html();
            noOfParagraphs++;

            newParagraph
                .attr('id', 'paragraph-' + noOfParagraphs)
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
            saveBtn.attr('id', 'save-para-' + noOfParagraphs);
            saveBtn.addClass('btn btn-success');
            saveBtn.removeAttr('hidden');
            newParagraph.append(saveBtn);

            var cancelBtn = $('#cancel-para-null').clone();
            cancelBtn.attr('id', 'cancel-para-' + noOfParagraphs);
            cancelBtn.addClass('btn btn-danger');
            cancelBtn.removeAttr('hidden');
            newParagraph.append(cancelBtn);

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

                            if (response.success) {
                                location.reload();
                            }

                            addNewParagraph.removeAttr('hidden');
                        },
                        fail: function (error) {
                            console.log(error);

                            newParagraph.remove();

                            addNewParagraph.removeAttr('hidden');

                        }
                    });
                }
            });

            cancelBtn.on({
                'click': function (event) {
                    event.preventDefault();

                    newParagraph.remove();

                    addNewParagraph.removeAttr('hidden');
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

    $('.delete-note-btn.delete-note').each(function () {
        $(this).on({
            'click': function (event) {
                var deleteBtn = $(event.target);

                var noteHolderDiv = deleteBtn.parents('.note-holder');
                var paragraphId = deleteBtn.parents('.note-holder').parents('.par-actions').children('.par-id-holder').html();

                noty({
                    text: 'Stergere nota?',
                    type: 'confirm',
                    dismissQueue: false,
                    layout: 'center',
                    theme: 'defaultTheme',
                    buttons: [
                        {
                            addClass: 'btn btn-primary',
                            text: 'Yes',
                            onClick: function ($noty) {
                                var requestData = {
                                    paragraphId: paragraphId
                                };
                                var deleteSuccess = false;

                                $.ajax({
                                    type: 'POST',
                                    url: '/v1/ajax/userNote/deleteUserNote',
                                    data: JSON.stringify(requestData),
                                    dataType: 'json',
                                    contentType: 'application/json; charset=utf-8',
                                    success: function (response) {
                                        if (!response.error) {
                                            deleteSuccess = true;

                                            $noty.close();

                                            noteHolderDiv.children('.tooltip').children('.hover-text').html('');
                                            noteHolderDiv.children('.tooltip').children('.tooltiptext').html('');
                                            noteHolderDiv.children('.textarea-content').val('');

                                            noty({text: 'Nota stearsa!', type: 'success', timeout: 1000});

                                            deleteBtn.attr('hidden', 'hidden');
                                        }
                                    },
                                    fail: function (error) {
                                        noty({text: 'Eroare steargere!', type: 'error', timeout: 2000});
                                        console.log(error);
                                    }
                                });

                                $noty.close();

                                noteHolderDiv.children('.tooltip').children('.hover-text').html('');
                                noteHolderDiv.children('.tooltip').children('.tooltiptext').html('');
                                noteHolderDiv.children('.textarea-content').val('');
                            }
                        },
                        {
                            addClass: 'btn btn-danger',
                            text: 'No',
                            onClick: function ($noty) {
                                $noty.close();
//                                noty({text: 'Cancel note removal', type: 'error', timeout: 1000});
                            }
                        }
                    ]
                });
            }
        });
    });

    $('.add-note-btn.add-note').each(function () {
       $(this).on({
           'click': function (event) {

               // TODO trigger cancel to all others

               // hide current elem
               var targetDiv = $(event.target);
               targetDiv.attr('hidden', 'hidden');

               // hide delete note element
               var deleteNoteBtn = targetDiv.parents('.note-holder').children('.delete-note-btn');
               deleteNoteBtn.attr('hidden', 'hidden');

               var saveBtn = $('#save-edit-para-null').clone();
               saveBtn.attr('id', 'save-edit-para-some')
                   .removeAttr('hidden');
               saveBtn.addClass('btn btn-success');

               var cancelBtn = $('#cancel-edit-para-null').clone();
               cancelBtn.attr('id', 'cancel-edit-para-some')
                   .removeAttr('hidden');
               cancelBtn.addClass('btn btn-danger');

               targetDiv.after(saveBtn)
                        .after(cancelBtn);

               // make the textarea visible
               var textarea = targetDiv.parents('.note-holder').children('.textarea-content');
               textarea.removeAttr('hidden')
                       .attr('contenteditable', 'true');

               var spanHoverText = textarea.parents('.note-holder').children('.tooltip').children('.hover-text');
               var spanHoverTextExtended = textarea.parents('.note-holder').children('.tooltip').children('.tooltiptext');
               textarea.text(spanHoverTextExtended.text());

               saveBtn.on({
                    'click': function (event) {
                        var content = textarea.val();

                        if (content.length < 10) {
                            noty({
                                text: 'Minim 10 caractere!',
                                type: 'warning',
                                animation: {
                                    open: {height: 'toggle'},
                                    close: {height: 'toggle'},
                                    easing: 'swing',
                                    speed: 500 // opening & closing animation speed
                                },
                                timeout: 500
                            });

                            return;
                        }

                        // hide delete note button
                        deleteNoteBtn.attr('hidden', 'hidden');

                        var paragraphHolder = targetDiv.parents('.row.par-holder').children('.par-id-holder');
                        var requestData = {
                            paragraphId: paragraphHolder.html(),
                            note: content
                        };

                        var saveSuccess = false;

                        $.ajax({
                            type: 'POST',
                            url: '/v1/ajax/userNote/saveUserNote',
                            data: JSON.stringify(requestData),
                            dataType: 'json',
                            contentType: 'application/json; charset=utf-8',
                            success: function (response) {
                                if (!response.error) {
                                    saveSuccess = true;

                                    noty({text: 'Nota adaugata', type: 'success', timeout: 1000});

                                    spanHoverText.html(content.substring(0, 8) + '...');

                                    var spanContent = textarea.parents('.note-holder').children('.tooltip').children('.tooltiptext');
                                    spanContent.html(content);
                                    spanContent.parents('.tooltip').removeAttr('hidden');

                                    // show delete note button
                                    deleteNoteBtn.removeAttr('hidden');
                                }
                            },
                            fail: function (error) {
                                console.log(error);
                            }
                        });

                        textarea.attr('hidden', 'hidden');

                        saveBtn.remove();
                        cancelBtn.remove();

                        // show the add note image
                        targetDiv.removeAttr('hidden');
                    }
               });

               cancelBtn.on({
                   'click': function (event) {
                       saveBtn.remove();
                       cancelBtn.remove();

                       // hide textarea
                       textarea.attr('hidden', 'hidden');

                       // show the add note image
                       targetDiv.removeAttr('hidden');

                       // show delete note - only if it has a note to delete
                       if (spanHoverText.html() != "") {
                           deleteNoteBtn.removeAttr('hidden');
                       }

                   }
               });
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
                saveBtn.addClass('btn btn-success');

                var cancelBtn = $('#cancel-edit-para-null').clone();
                cancelBtn.attr('id', 'cancel-edit-para-some')
                    .removeAttr('hidden');
                cancelBtn.addClass('btn btn-danger');

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

                        $.ajax({
                            type: 'POST',
                            url: '/courses/'+ courseId + '/ch/' + chapterId + '/par/' + paragraphId + '/edit',
                            data: paragraphText,
                            dataType: 'json',
                            contentType: 'application/json; charset=utf-8',
                            success: function (response) {

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
        var courseId = $('#course-id').html();

        var request = {
            courseId: courseId,
            chapterId: chapterId,
            paragraphId: parId
        };

        $.ajax({
            type: 'POST',
            url: '/v1/ajax/completeParagraph',
            data: JSON.stringify(request), // without stringify, the Jackson mapping is failing
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            success: function (response) {
                par.removeClass('unchecked')
                    .addClass('checked');

                noty({text: 'Paragraf citit', type: 'success', timeout: 1000});
            },
            fail: function (error) {
                console.log(error);
            }
        });
    });
});

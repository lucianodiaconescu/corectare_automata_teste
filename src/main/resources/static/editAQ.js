$(document).ready(function() {
    $('.edit-question-button').click(function() {
        var questionText = $(this).siblings('.question-text').text();
        var editForm = $(this).siblings('.edit-question-form');
        editForm.find('.edit-question-input').val(questionText);
        editForm.toggle();
    });
});

$(document).ready(function() {
    $('.edit-answer-button').click(function() {
        var answerText = $(this).siblings('.answer-text').text();
        var editFormAnswer = $(this).siblings('.edit-answer-form');
        editFormAnswer.find('.edit-answer-input').val(answerText);
        editFormAnswer.toggle();
    });
});

$(document).ready(function() {
    $('.questions-list').hide();
    $('.toggle-button').click(function() {
        var questionsList = $(this).next('.questions-list');
        questionsList.toggle();
    });
});

$(document).ready(function() {
    $('.answers-list').hide();
    $('.toggle-button-show-answers').click(function() {
        var answersList = $(this).next('.answers-list');
        answersList.toggle();
    });
});
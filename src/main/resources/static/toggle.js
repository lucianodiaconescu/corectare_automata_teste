$(document).ready(function() {
    $('.questions-list').hide();
    $('.toggle-button').click(function() {
        var questionsList = $(this).next('.questions-list');
        questionsList.toggle();
    });
});
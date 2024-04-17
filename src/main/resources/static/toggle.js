$(document).ready(function() {
    $('.toggle-data').click(function() {
        var $toggleButton = $(this);
        var $dataContainer = $toggleButton.siblings('.data-container');
        $dataContainer.slideToggle();
    });

    $('.test-id').each(function(index) {
        var $testId = $(this).text();
        var $testName = $(this).siblings('.test-name').text();
        var $email = $(this).closest('tr').find('.email_user').text();
        $(this).closest('tr').find('.data-id').text($testId);
        $(this).closest('tr').find('.data-test-name').text($testName);
        $(this).closest('tr').find('.data-email').text($email);
    });

    $('.delete-test-form').submit(function(event) {
        event.preventDefault();
        var form = $(this);
        var url = form.attr('action');
        var formData = form.serialize();

        $.post(url, formData)
            .done(function(response) {
                console.log(response);
                window.location.href = '/api/users/viewtests';
            })
            .fail(function(error) {
                console.log(error);
            });
    });
});
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

    $('.delete-form').submit(function(event) {
        event.preventDefault(); // Prevent the form from submitting normally
        var form = $(this);
        var url = form.attr('action');
        var formData = form.serialize();

        $.post(url, formData)
            .done(function(response) {
                // Handle success response here, if needed
                console.log(response);
            })
            .fail(function(error) {
                // Handle error response here, if needed
                console.log(error);
            });
    });
});
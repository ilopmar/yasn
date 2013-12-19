$(document).ready(function() {

    $("#publish-form").on("submit", function(e) {
        e.preventDefault();

        var text = $('#text-content').val();
        var url = $('#publish-form').attr('action');
        var params = {'content': text};

        $.ajax({
            type: 'POST',
            url: url,
            data: params,
            success: function(data) {
                $('#timeline-wrapper').prepend(data);
                $('#timeline-wrapper .ribbitWrapper:first-child').slideDown();
            }
        });
    });
});

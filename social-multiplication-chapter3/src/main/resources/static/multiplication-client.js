function updateMultiplication() {
    $.ajax({
        url: "http://localhost:8080/multiplications/random"
    }).then((data) => {
        $("#attempt-form").find("input[name='result-attempt']").val("");
        $("#attempt-form").find("input[name='user-alias']").val("");
        $('.multiplication-a').empty().append(data.factorA);
        $('.multiplication-b').empty().append(data.factorB);
    });
}

$(document).ready(() => {
    updateMultiplication();

    $("#attempt-form").submit((event) => {

        event.preventDefault();

        var a = $('.multiplication-a').text();
        var b = $('.multiplication-b').text();
        var $form = $(this),
            attempt = $form.find("input[name='result-attempt']").val(),
            userAlias = $form.find("input[name='user-alias']").val();
        var data = {
            user: {
                alias: userAlias
            },
            multiplication: {
                factorA: a,
                factorB: b
            },
            resultAttempt: attempt
        };

        $.ajax({
            url: '/results',
            type: 'POST',
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: (result) => {
                if(result.correct) {
                    $('.result-message').empty().append("The result is correct! Congratulations!");
                } else {
                    $('.result-message').empty().append("Oops that's not correct! But keep trying!");
                }
            }
        });

        updateMultiplication();
    });
});

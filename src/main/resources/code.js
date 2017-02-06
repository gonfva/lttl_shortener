$('#shortenButton').click(function(){
    waiting();
    $.ajax({
        type: "POST",
        url: 'http://localhost:9000/',
        data: '{ "url": "'+ $('#inputURL').val()+ '" }',
        contentType:"application/json",
        success: function (response) {
            $('#inputURL').attr("type","search");
            console.log("Success");
            console.log(response);
            $('#inputURL').val(response);
            copyStatus();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log("Failure");
            console.log(xhr.status);
            alert(xhr.responseText);
            reset();
        }
    });

});
$('#clearButton').click(function(){
    $('#inputURL').val("");
    reset();
});
$('#copyButton').click(function(){
    $('#inputURL').select();
    try {
        var successful = document.execCommand('copy');
        var msg = successful ? 'successful' : 'unsuccessful';
        console.log('Copy email command was ' + msg);
    } catch(err) {
        console.log('Oops, unable to copy');
    }
});
function waiting(){
    $('#shortenButton').text("Waiting");
}
function copyStatus(){
    $('#shortenButton').css('display','none');
    $('#copyButton').css('display','inline');
    $('#clearButton').css('display','inline');
    $('#inputURL').prop('readonly', true);
}
function reset(){
    $('#shortenButton').text("Shorten");
    $('#shortenButton').css('display','inline');
    $('#copyButton').css('display','none');
    $('#clearButton').css('display','none');
    $('#inputURL').prop('readonly', false);

}
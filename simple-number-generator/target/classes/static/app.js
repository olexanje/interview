var stompClient = null;
var isTimeToAutoGenerate = false;
var interval = null;
var timeout = null;

function sendRequest(count, auto) {
    stompClient.send("/app/request-to-generate", {}, JSON.stringify({ 'count': count, 'auto': auto}));
}

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#generated-table").show();
    }
    else {
        $("#generated-table").hide();
    }
    clearTable();

}

function connect() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        stompClient.subscribe('/topic/response-with-generated', function (message) {
            showTableAndButtonAutoControl(JSON.parse(message.body))
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    intervalStop();
}

function generate() {
    let count = ( $("#count").val() == null ? 10 : $("#count").val() )
    intervalStop();
    sendRequest( count, false );
}

function generateAuto() {

        let auto = true;
        if (isTimeToAutoGenerate) {
            sendRequest(null, auto)
            clearInterval(interval);
        } else {
            isTimeToAutoGenerate = true;
            sendRequest(10, auto);
            interval =  setInterval(() => {
            sendRequest(10, auto);
            }, 10000 );
        }

}

function showTable(message) {

    clearTable();
    let columnIndex = 0;
    let rowIndex = 0;
    $('#generated-head').append('<tr>');
    for (index in message) {
        $('#generated-head').append('<th scope="col">' + index + '</th>')
    }
    $('#generated-head').append('</tr>');
    for (rowIndex; rowIndex < message[columnIndex].numbers.length;) {
        if (rowIndex > 5) {
            rowIndex++;
            continue;
        }
        $('#generated-body').append("<tr>");
        for (columnIndex; columnIndex < message.length; )  {
            $("#generated-body").append("<td>" + message[columnIndex].numbers[rowIndex] + "</td>");
            columnIndex++;
        }
    $('#generated-body').append("</tr>")
    rowIndex++;
    columnIndex = 0;
    }
}

function clearTable() {
    $('#generated-body').html("");
    $('#generated-head').html("");
}

function showTableAndButtonAutoControl(message) {
    isTimeToAutoGenerate = message.auto;
    if (message.generated == null) {
        $("#auto").prop("class", "btn btn-primary");
        intervalStop()
    } else if (!message.auto) {
        $("#auto").prop("class", "btn btn-primary");
        showTable(message.generated);
        intervalStop()
    } else if (message.auto && isTimeToAutoGenerate) {
        clearTimeout(timeout);
        $("#auto").prop("class", "btn btn-success");
        showTable(message.generated);
        isTimeToAutoGenerate = true;
        timeout = setTimeout(() => {
        $("#auto").prop("class", "btn btn-primary");
        }, 10000);
    } else {
        $("#auto").prop("class", "btn btn-primary");
        intervalStop()
    }
}

function intervalStop() {
    clearInterval(interval);
    isTimeToAutoGenerate = false;
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { generate(); });
    $( "#auto" ).click(function() { generateAuto(); })
});
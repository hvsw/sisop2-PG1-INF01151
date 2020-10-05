var area = document.getElementById("area");
var msg = document.getElementById("msg");

//Definição do path utilizado pelo ServerEndpoint
var ws = new WebSocket("ws://localhost:8080/chat");

ws.onopen = function(event) {
    console.log(event);
};

ws.onclose = function(event) {
    console.log(event);
};

ws.onmessage = function(event) {
    console.log(event);
    var entry = document.createElement("li");
    entry.textContent = event.data;
    area.appendChild(entry);
}
 
ws.onerror = function(event) {
    console.log(event);
}

document.getElementById("echo").onclick = function() {
    ws.send(msg.value);
};

document.getElementById("close").onclick = function() {
    ws.close();
};


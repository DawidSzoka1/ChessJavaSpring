import CustomWebSocket from "./connectionWithWebSocket.js";
addEventListener("DOMContentLoaded", () => {
    const socket = new CustomWebSocket("", "ws://localhost:8080/ws/play")
    socket.connect()
    const info = document.getElementById("optionDiv");
    const find = document.getElementById("findGame")
    find.addEventListener("click", (e) => {
        e.preventDefault()
        socket.infoObject = info;
        console.log("Evenet findGame")
        info.innerHTML = "Szukanie gry..."
        socket.send({
            message: 'queue',
            messageType: 'queue',
            gameId: '0',
        });
    })
})
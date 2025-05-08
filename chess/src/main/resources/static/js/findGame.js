import CustomWebSocket from "./connectionWithWebSocket.js";
addEventListener("DOMContentLoaded", () => {
    let searchInterval;
    let secondsElapsed = 0;
    const socket = new CustomWebSocket("", "ws://localhost:8080/ws/play")
    socket.connect()
    const lookForGame = document.querySelectorAll('.lobby-game-mode')
    lookForGame.forEach(div => {
        div.addEventListener('click', function(e){
            document.getElementById('searchingGame').style.display = 'block';
            secondsElapsed = 0;
            searchInterval = setInterval(() => {
                secondsElapsed++;
                document.getElementById('searchTimer').textContent = secondsElapsed;
            }, 1000);
            socket.interval = searchInterval;
            socket.send({
                message: e.target.id,
                messageType: 'queue',
                gameId: '0',
            })
        })
    })
    document.getElementById('cancel').addEventListener('click', function (e)  {
        e.preventDefault();
        clearInterval(searchInterval);
        document.getElementById('searchingGame').style.display = 'none';
        secondsElapsed = 0;
        socket.send({
            message: 'cancel',
            messageType: 'cancel',
            gameId: '0'
        })
    })
})
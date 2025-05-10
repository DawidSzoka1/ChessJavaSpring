import CustomWebSocket from "./connectionWithWebSocket.js";
addEventListener("DOMContentLoaded", () => {
    let searchInterval;
    let secondsElapsed = 0;
    let gameType;
    const socket = new CustomWebSocket("", "ws://localhost:8080/ws/play")
    socket.connect()
    const lookForGame = document.querySelectorAll('.lobby-game-mode')
    console.log(lookForGame)
    lookForGame.forEach(div => {
        div.addEventListener('click', function(e){
            document.getElementById('searchingGame').style.display = 'block';
            gameType = this.id;
            secondsElapsed = 0;
            searchInterval = setInterval(() => {
                secondsElapsed++;
                document.getElementById('searchTimer').textContent = secondsElapsed;
            }, 1000);
            socket.interval = searchInterval;

            socket.send({
                message: gameType,
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
            message: gameType,
            messageType: 'cancel',
            gameId: '0'
        })
        gameType = null;
    })
})
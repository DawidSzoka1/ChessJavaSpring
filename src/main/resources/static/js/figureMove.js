import CustomWebSocket from "./connectionWithWebSocket.js";

document.addEventListener("DOMContentLoaded", () => {
    const game = document.querySelector("#game")
    const gameId = game.getAttribute('data-id')
    let userName = document.querySelector("#user").getAttribute('data-username')
    const pieces = document.querySelectorAll(".piece")
    const squares = document.querySelectorAll(".square-content")
    const drawPropose = document.getElementById("offer-draw-btn")
    let selectedPiece;
    if (userName == null) {
        userName = '';
    }
    const ws = new CustomWebSocket(gameId, "ws://localhost:8080/ws/chess");
    ws.connect();
    ws.socket.onopen = () => {
        ws.send({
            message: 'start',
            messageType: 'start',
            gameId: gameId,
        });
    }
    document.getElementById("surrender-btn")
        .addEventListener('click', (e) => {
            e.preventDefault();
            ws.send({
                message: 'surrender',
                messageType: 'surrender',
                gameId: gameId,
                userName: userName
            })
        })
    drawPropose.addEventListener("click", (e) => {
        e.preventDefault()
        drawPropose.style.display = 'none'
        document.getElementById("draw-request-sent").style.display = 'block'
        ws.send({
            message: 'Propozycja remisu',
            messageType: 'offer_draw',
            gameId: gameId,
            userName: userName
        })
    })
    document.getElementById("accept-draw-btn")
        .addEventListener('click', (e) => {
            e.preventDefault();
            ws.send({
                message: 'Akceptacja remisu',
                messageType: 'accept_draw',
                gameId: gameId,
                userName: userName
            })
            document.getElementById("buttons-area").style.display = "none";
        })
    document.getElementById("reject-draw-btn")
        .addEventListener('click', (e) => {
            e.preventDefault();
            ws.send({
                message: 'Odrzucono remis',
                messageType: 'reject_draw',
                gameId: gameId,
                userName: userName
            })
            drawPropose.style.display = 'block'
            document.getElementById("draw-request-container").style.display = "none";
        })

    pieces.forEach(piece => {
        piece.addEventListener("drag", dragging)
        piece.addEventListener("dragstart", dragStart)
        piece.addEventListener("click", (e) => {
            ws.send({
                message: e.target.id,
                messageType: "getMoves",
                gameId: gameId
            })
        })
    })

    function dragStart(e) {
        selectedPiece = e.target
        console.log("dragging start")
    }

    function dragging(e) {

    }

    squares.forEach(square => {
        square.addEventListener("dragover", dragOver)
        square.addEventListener("dragenter", dragEnter)
        square.addEventListener("dragleave", dragLeave)
        square.addEventListener("drop", dragDrop)
        square.addEventListener("dragend", dragEnd)
    })

    function dragOver(e) {
        e.preventDefault()
    }

    function dragEnter(e) {
    }

    function dragLeave(e) {
    }

    /**
     * Handler for the "drop" event. Sends the move to the WebSocket server and updates the board state.
     * @param {DragEvent} e - The drag event.
     */
    function dragDrop(e) {
        ws.eventForMove = e; // Set the event for the move
        ws.setPieceToMove(selectedPiece); // Set the piece being moved
        // Send the move message to the server
        ws.send({
            message: `${selectedPiece.id}-${e.target.id.split('-')[0]}`,
            messageType: e.target.tagName === 'DIV' ? 'move' : 'take',
            gameId: gameId,
            userName: userName
        });

        e.preventDefault();
    }

    function dragEnd(e) {
    }


});

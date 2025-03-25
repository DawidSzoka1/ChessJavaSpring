import CustomWebSocket from "./connectionWithWebSocket.js";

document.addEventListener("DOMContentLoaded", () => {
    const game = document.querySelector("#game")
    const gameId = game.getAttribute('data-id')
    const pieces = document.querySelectorAll(".piece")
    const squares = document.querySelectorAll(".square-content")
    let selectedPiece;

    const ws = new CustomWebSocket(gameId, "ws://localhost:8080/ws/chess");
    ws.connect();

    pieces.forEach(piece => {
        piece.addEventListener("drag", dragging)
        piece.addEventListener("dragstart", dragStart)
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
        });

        e.preventDefault();
    }

    function dragEnd(e) {
    }
});

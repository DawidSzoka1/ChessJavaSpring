import CustomWebSocket from "./connectionWithWebSocket.js";

document.addEventListener("DOMContentLoaded", () => {
    const game = document.querySelector("#game")
    const gameId = game.getAttribute('data-id')
    let gameBoard = game.getAttribute("data-board")
    const pieces = document.querySelectorAll(".piece")
    const squares = document.querySelectorAll(".square-content")
    let selectedPiece;
    let successfulMove;

    const ws = new CustomWebSocket(gameId, "ws://localhost:8080/chess");
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

    function dragDrop(e) {
        ws.eventForMove = e;
        ws.setPieceToMove(selectedPiece)
        ws.send(
            {
                message: `${selectedPiece.id}-${e.target.id}`,
                messageType: "move",
                gameId: gameId,
                board: JSON.parse(gameBoard)
            })
        if(ws.board != null){
            gameBoard = ws.board;
            game.setAttribute("data-board", gameBoard);
        }
        e.preventDefault()
    }

    function dragEnd(e) {
    }
});

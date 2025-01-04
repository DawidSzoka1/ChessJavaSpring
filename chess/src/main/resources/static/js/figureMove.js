import {customWebSocket} from "./connectionWithWebSocket.js";
document.addEventListener("DOMContentLoaded", () => {
    let socket = customWebSocket()
    let errorClose = true
    socket.onclose = () => {
        if(errorClose){
            socket = customWebSocket()
        }
    }

    const gameId = document.querySelector("#game").getAttribute('data-id')
    let gameBoard = document.querySelector("#game").getAttribute("data-board")
    const pieces = document.querySelectorAll(".piece")
    const squares = document.querySelectorAll(".square-content")
    let selectedPiece;
    let successfuleMove;
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

        socket.send(JSON.stringify(
            {
                message:`${selectedPiece.id}-${e.target.id}`,
                messageType: "move",
                gameId: gameId,
                board: JSON.parse(gameBoard)
                }))
        socket.onmessage = (event) => {
            // TODO message from move
            console.log("Z dragDrop ")
            console.log(event)
            console.log("Po dragDropie")
        }
        if(e.target.tagName === "DIV" && successfuleMove){
            e.target.appendChild(selectedPiece)
        }else if(e.target.className === "piece"){
            console.log("bicie")
        }
        e.preventDefault()
    }

    function dragEnd(e) {
    }
});

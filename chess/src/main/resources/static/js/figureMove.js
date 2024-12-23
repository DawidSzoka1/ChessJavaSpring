import {customWebSocket} from "./connectionWithWebSocket.js";
document.addEventListener("DOMContentLoaded", () => {
    let socket = customWebSocket()
    let errorClose = true
    socket.onclose = () => {
        if(errorClose){
            socket = customWebSocket()
        }
    }

    const gameObj = document.querySelector("#game").getAttribute('data')
    const pieces = document.querySelectorAll(".piece")
    const squares = document.querySelectorAll(".square-content")
    let selectedPiece;
    console.log(gameObj)
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
                game: gameObj,
                }))
        // socket.onmessage = (message) => {
        //     console.log("In drago Drop onmessage")
        //     console.log(message)
        // }
        if(e.target.tagName === "DIV"){
            e.target.appendChild(selectedPiece)
        }else if(e.target.className === "piece"){
            console.log("bicie")
        }
        e.preventDefault()
    }

    function dragEnd(e) {
    }
});

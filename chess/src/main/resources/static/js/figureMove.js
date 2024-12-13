document.addEventListener("DOMContentLoaded", () => {
    const socket = new WebSocket("ws://localhost:8080/game/classic")

    socket.onopen = () => {
        console.log("Connected to websocket")
    }

    socket.onmessage = (e) => {
        console.log(JSON.parse(e.data))
    }

    function sendMove(){
        socket.send(JSON.stringify({"from": "e2", "to" : "e4"}))
    }
    sendMove()

    const pieces = document.querySelectorAll(".piece")
    const squares = document.querySelectorAll(".square-content")
    let selectedPiece;
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

        console.log(e.target)
        console.log(selectedPiece)
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

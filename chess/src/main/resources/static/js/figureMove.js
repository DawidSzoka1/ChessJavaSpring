document.addEventListener("DOMContentLoaded", () => {
    const socket = new WebSocket("ws://localhost:8080/chess")

    let isPongReceived = true;
    socket.onopen = () => {
        console.log("Connected to WebSocket")
        setInterval(() => {
            if(socket.readyState === WebSocket.OPEN){
                if(!isPongReceived){
                    console.log("Some error with pong")
                    socket.close();
                }else{
                    isPongReceived = false
                    console.log("Sending Ping")
                    socket.send("PING")
                }
            }
        }, 30000)
    }

    socket.onmessage = (e) => {
        if(e.data === "PONG"){
            console.log("Pong received from server")
            isPongReceived = true
        }else{
            console.log(e)
        }

    }
    socket.onerror = (error) => {
        console.log(error)
    }
    socket.onclose = (e) => {
        console.log("Connection closed")

    }

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
        socket.send("ruch zostal zrobiony")
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

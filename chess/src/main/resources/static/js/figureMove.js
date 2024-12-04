document.addEventListener("DOMContentLoaded", () => {
    const pieces = document.querySelectorAll(".piece")
    const squares = document.querySelectorAll(".square-content")
    let selectedPiece;
    pieces.forEach(piece => {
        piece.addEventListener("drag", dragging)
        piece.addEventListener("dragstart", dragStart)
    })

    function dragStart(e){
        selectedPiece = e.target
        console.log("dragging start")
    }

    function dragging(e){
        console.log(e.target)
    }
    squares.forEach(square => {
        square.addEventListener("dragover", dragOver)
        square.addEventListener("dragenter", dragEnter)
        square.addEventListener("dragleave", dragLeave)
        square.addEventListener("drop", dragDrop)
        square.addEventListener("dragend", dragEnd)
    })
    function dragOver(e){
        e.preventDefault()
        console.log("now on this shit " + e.target)
    }
    function dragEnter(e){

        console.log("now on this shit " + e.target)
    }
    function dragLeave(){}
    function dragDrop(e){
        e.preventDefault()
        e.target.appendChild(selectedPiece)
    }
    function dragEnd(){}
});

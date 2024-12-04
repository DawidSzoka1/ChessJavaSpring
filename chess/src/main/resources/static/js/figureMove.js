document.addEventListener("DOMContentLoaded", () => {
    const squares = document.querySelectorAll(".square-content");
    const pieces = document.querySelectorAll(".piece");
    const container = document.querySelector(".container")
    const containerRect = container.getBoundingClientRect()

    let selectedPiece
    pieces.forEach(piece => {
        piece.addEventListener("mousedown", (e) => {
            selectedPiece = piece;
        })
    })


    container.addEventListener("mousemove", (e) => {
        if(selectedPiece){
            const mouseX = e.clientX - containerRect.x;
            const mouseY = e.clientY - containerRect.y;
            selectedPiece.style.left = mouseX + 'px'
            selectedPiece.style.top = mouseY + 'px'
        }
    })
    container.addEventListener("mouseup", (e) =>{
        e.preventDefault()
        if (selectedPiece){
            selectedPiece = null;
        }
    })

})
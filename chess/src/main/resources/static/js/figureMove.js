document.addEventListener("DOMContentLoaded", () => {
    const squares = document.querySelectorAll(".square-content");

    squares.forEach(square => {
        square.addEventListener("click", event => {
            console.log(`Kliknieto pole: ${event.target.id}`)
        })
    })
})
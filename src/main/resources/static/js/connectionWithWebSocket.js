/**
 * A custom WebSocket wrapper for managing game-related WebSocket communication.
 */
export default class CustomWebSocket {
    /**
     * @private
     * @type {string}
     * The unique game ID associated with this WebSocket connection.
     */
    #gameId = "";
    /**
     * @private
     * @type {Event|null}
     * The event that triggers a move action.
     */
    #eventForMove = null;

    /**
     * @type {WebSocket|null}
     * The underlying WebSocket connection.
     */
    socket = null;

    /**
     * @type {boolean}
     * Tracks whether the last "PONG" message was received.
     */
    isPongRecived = true;

    /**
     * @type {HTMLElement|null}
     * The HTML element representing the piece to move on the game board.
     */
    pieceToMove = null;

    interval = null;
    /**
     * @type {HTMLElement|null}
     */
    infoObject = null

    /**
     * Creates an instance of CustomWebSocket.
     * @param {string} gameId - The unique identifier for the game.
     * @param {string} url - The WebSocket server URL.
     */
    constructor(gameId, url) {
        this.#gameId = gameId;
        this.url = url;
    }

    /**
     * Sets the piece to move on the game board.
     * @param {HTMLElement} pieceToMove - The piece to move.
     */
    setPieceToMove(pieceToMove) {
        this.pieceToMove = pieceToMove;
    }

    /**
     * Sets the event that triggers a move action.
     * @param {Event} event - The event for the move.
     */
    set eventForMove(event) {
        this.#eventForMove = event;
    }

    /**
     * Gets the event that triggers a move action.
     * @returns {Event|null} The move event.
     */
    get eventForMove() {
        return this.#eventForMove;
    }

    /**
     * Sets the unique game ID.
     * @param {string} gameId - The new game ID.
     */
    set gameId(gameId) {
        this.#gameId = gameId;
    }

    /**
     * Gets the unique game ID.
     * @returns {string} The game ID.
     */
    get gameId() {
        return this.#gameId;
    }

    /**
     * Establishes a WebSocket connection and sets up event listeners.
     */
    connect() {
        this.socket = new WebSocket(this.url);
        console.log(`socekt: ${this.socket}`)
        this.open();

        this.messageListener();

        this.socket.onerror = (error) => {
            console.error(error);
            // setTimeout(() => this.connect(), 1000);
        };

        this.socket.onclose = () => {
            console.log("Connection closed");
        };
    }

    /**
     * Sets up a listener for incoming WebSocket messages.
     */
    messageListener() {
        this.socket.onmessage = (e) => {
            const data = JSON.parse(e.data);
            switch (data.type) {
                case "MOVE":
                    this.messageMove(data);
                    break;
                case "TAKE":
                    this.messageTake(data);
                    break;
                case "ERROR":
                    this.messageError(data);
                    break;
                case "QUEUE":
                    this.messageQueue(data);
                    break;
                case "FOUND":
                    this.messageFound(data);
                    break;
                case "ENEMY":
                    this.messageEnemy(data);
                    break;
                case "MOVES":
                    this.messageMoves(data);
                    break;
                case "DRAWOFFER":
                    this.messageDrawRequest(data);
                    break;
                case "DRAWACCEPT":
                    this.messageDrawAccept(data);
                    break;
                case "DRAWREJECT":
                    this.messageDrawReject(data);
                    break;
                case "SURRENDER":
                    this.messageSurrender(data);
                    break;
                default:
                    this.messagePong();
            }
        };
    }

    messageSurrender(data){
        document.getElementById("buttons-area").style.display = 'none';
    }

    messageDrawRequest(data) {
        document.getElementById("offer-draw-btn").style.display = 'none';
        document.getElementById("draw-request-container").style.display = 'block';
    }

    messageDrawAccept(data) {
        document.getElementById("buttons-area").style.display = 'none';

    }

    messageDrawReject(data) {
        document.getElementById("draw-request-container").style.display = 'none';
        document.getElementById("offer-draw-btn").style.display = 'block';
    }

    messageMoves(data) {
        this.clearHighlights()
        const allMoves = data.content.split(',');
        allMoves.forEach(targetId => {
            if (targetId === "") return;
            const square = document.querySelector(`#${targetId}-`);
            if (!square) return;

            const hasPiece = square.querySelector("img") !== null;
            square.classList.add("highlight");
            // if (hasPiece) {
            //     square.classList.add("highlight-take");
            // } else {
            //     square.classList.add("highlight-move");
            // }

        })


    }

    clearHighlights() {
        document.querySelectorAll('.highlight').forEach(el => {
            el.classList.remove('highlight');
        });
    }

    messageEnemy(data) {
        const move = data.content.split('-')
        const pieceToMove = document.getElementById(move[0])
        const squareToMove = document.getElementById(move[1] + '-')
        const test = document.getElementById(move[1])
        if (test == null) {
            squareToMove.appendChild(pieceToMove);
        } else {
            squareToMove.removeChild(test)
            squareToMove.appendChild(pieceToMove)
        }
        pieceToMove.id = move[1]
        this.addMoveToList(move[0], move[1], true)
    }

    messageFound(data) {
        console.log(`Found message ${data}`)
        clearInterval(this.interval)
        window.location.href = `/play/${data.content}`
    }

    messageQueue(data) {
        console.log(`Queue message ${data}`)
    }

    messageError(data) {
        console.log(data)
    }

    /**
     * Handles "PONG" messages to keep the WebSocket connection alive.
     * @returns {string} A confirmation string indicating receipt of the "PONG".
     */
    messagePong() {
        this.isPongReceived = true;
        return "received";
    }

    validData(data) {
        if (!this.#eventForMove) {
            console.warn("No event for move.");
            return false;
        }
        if (!data.valid) {
            console.log("Invalid move");
            console.log(data)
            return false;
        }
        return true;
    }

    /**
     * Handles "MOVE" messages to update the piece's position on the game board.
     * @param {Object} data - The data related to the move.
     * @param {boolean} data.valid - Whether the move is valid.
     * @param {string} data.content - The new position for the piece.
     */
    messageMove(data) {
        if (!this.validData(data)) {
            console.log("move isnt valid")
            console.log(data)
            return null;
        }
        const from = this.pieceToMove.id
        this.pieceToMove.id = data.content;
        this.#eventForMove.target.appendChild(this.pieceToMove);
        this.clearHighlights()
        this.addMoveToList(from, data.content, true)
    }

    addMoveToList(from, to, isWhite) {
        const moveList = document.getElementById("move-list");

        const moveText = `${from}-${to}`;
        const span = document.createElement("span");
        span.className = isWhite ? "white-move" : "black-move";
        span.textContent = moveText;
        const moveCount = moveList.lastElementChild !== null ? parseInt(moveList.lastElementChild.id) : 0;
        const li = document.createElement("li");
        li.id = String(moveCount + 1);
        li.textContent = `${moveCount + 1}. `;
        li.appendChild(span);
        moveList.appendChild(li);

    }

    messageTake(data) {
        if (!this.validData(data)) {
            console.log("move isnt valid")
            console.log(data)
            return null;
        }
        const from = this.pieceToMove.id
        const pieceToRemove = document.getElementById(data.content);
        const contentSquare = document.getElementById(data.content + '-')
        this.pieceToMove.id = data.content;
        contentSquare.removeChild(pieceToRemove);
        contentSquare.appendChild(this.pieceToMove);
        console.log("successful take")
        this.clearHighlights()
        this.addMoveToList(from, data.content, true)
    }

    /**
     * Sends a message to the WebSocket server.
     * @param {Object} message - The message to send.
     */
    send(message) {
        if (this.socket && this.socket.readyState === WebSocket.OPEN) {
            this.socket.send(JSON.stringify(message));
        } else {
            console.warn("WebSocket is not open.");
        }
    }

    /**
     * Handles the WebSocket "open" event and sends periodic "PING" messages.
     */
    open() {
        this.socket.onopen = () => {
            console.log("Connected to WebSocket");
            setInterval(() => {
                if (this.socket.readyState === WebSocket.OPEN) {
                    if (!this.isPongReceived) {
                        console.log("Some error with pong");
                        this.socket.close();
                    } else {
                        this.isPongReceived = false;
                        console.log("Sending Ping");
                        this.socket.send(JSON.stringify({
                            message: "Sending ping",
                            messageType: "PING",
                            gameId: "",
                        }));
                    }
                }
            }, 30000);
        };
    }

    /**
     * Closes the WebSocket connection.
     */
    close() {
        if (this.socket) {
            this.socket.close();
        }
    }
}

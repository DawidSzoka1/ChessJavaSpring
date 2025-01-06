/**
 * A custom WebSocket wrapper for managing game-related WebSocket communication.
 */
export default class CustomWebSocket {
    /**
     * @private
     * @type {number}
     * The unique game ID associated with this WebSocket connection.
     */
    #gameId = 0;

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

    /**
     * @private
     * @type {Object|null}
     * The current state of the game board.
     */
    #board = null;

    /**
     * Creates an instance of CustomWebSocket.
     * @param {number} gameId - The unique identifier for the game.
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
     * Sets the current state of the game board.
     * @param {Object} board - The updated game board.
     */
    set board(board) {
        this.#board = board;
    }

    /**
     * Gets the current state of the game board.
     * @returns {Object|null} The game board state.
     */
    get board() {
        return this.#board;
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
     * @param {number} gameId - The new game ID.
     */
    set gameId(gameId) {
        this.#gameId = gameId;
    }

    /**
     * Gets the unique game ID.
     * @returns {number} The game ID.
     */
    get gameId() {
        return this.#gameId;
    }

    /**
     * Establishes a WebSocket connection and sets up event listeners.
     */
    connect() {
        this.socket = new WebSocket(this.url);
        this.open();
        this.messageListener();

        this.socket.onerror = (error) => {
            console.error(error);
            setTimeout(() => this.connect(), 1000);
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
                    this.#board = data.gameBoard;
                    break;
                default:
                    this.messagePong();
            }
        };
    }

    /**
     * Handles "PONG" messages to keep the WebSocket connection alive.
     * @returns {string} A confirmation string indicating receipt of the "PONG".
     */
    messagePong() {
        this.isPongReceived = true;
        return "received";
    }

    /**
     * Handles "MOVE" messages to update the piece's position on the game board.
     * @param {Object} data - The data related to the move.
     * @param {boolean} data.valid - Whether the move is valid.
     * @param {string} data.content - The new position for the piece.
     */
    messageMove(data) {
        if (!this.#eventForMove) {
            console.warn("No event for move.");
            return null;
        }
        if (!data.valid) {
            console.log("Invalid move");
            return null;
        }
        const color = this.pieceToMove.id[0];
        this.pieceToMove.id = color + data.content;
        console.log(this.pieceToMove.id);
        this.#eventForMove.target.appendChild(this.pieceToMove);
        console.log("Successful take");
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
                            board: ""
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

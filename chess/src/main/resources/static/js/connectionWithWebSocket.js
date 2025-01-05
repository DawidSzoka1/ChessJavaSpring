export default class CustomWebSocket {
    #gameId = 0;
    #eventForMove = null;
    socket = null;
    isPongRecived = true;
    pieceToMove = null;

    constructor(gameId, url) {
        this.#gameId = gameId;
        this.url = url;
    }

    setPieceToMove(pieceToMove) {
        this.pieceToMove = pieceToMove;
    }

    set eventForMove(event) {
        this.#eventForMove = event
    }

    get eventForMove() {
        return this.#eventForMove
    }

    set gameId(gameId) {
        this.#gameId = gameId
    }

    get gameId() {
        return this.#gameId
    }

    connect() {
        this.socket = new WebSocket(this.url);

        this.open()
        this.messageListener()

        this.socket.onerror = (error) => {
            console.log(error)
            setTimeout(() => this.connect(), 1000)
        }
        this.socket.onclose = (e) => {
            console.log("Connection closed")

        }
    }

    messageListener() {
        this.socket.onmessage = (e) => {
            const data = JSON.parse(e.data);
            switch (data.type) {
                case "MOVE":
                    this.messageMove(data)
                    break;
                default:
                    console.log("its good")
                    this.messagePong()
            }
        }
    }

    messagePong() {
        this.isPongReceived = true;
        return "received"
    }

    messageMove(data) {
        if (!this.#eventForMove) {
            console.warn("no event for move.")
            return null;
        }
        if (!data.valid) {
            console.log("invalid move")
            return null;
        }
        this.#eventForMove.target.appendChild(this.pieceToMove)
        console.log("Successful take")
    }

    send(message) {
        if (this.socket && this.socket.readyState === WebSocket.OPEN) {
            this.socket.send(JSON.stringify(message))
        } else {
            console.warn("web socket is not open.")
        }
    }

    open() {
        this.socket.onopen = () => {
            console.log("Connected to WebSocket")
            setInterval(() => {
                if (this.socket.readyState === WebSocket.OPEN) {
                    if (!this.isPongReceived) {
                        console.log("Some error with pong")
                        this.socket.close();
                    } else {
                        this.isPongReceived = false
                        console.log("Sending Ping")
                        this.socket.send(JSON.stringify({
                            message: "Sending ping",
                            messageType: "PING",
                            gameId: "",
                            board: ""
                        }))
                    }
                }
            }, 30000)
        }
    }

    close() {
        if (this.socket) {
            this.socket.close()
        }
    }
}


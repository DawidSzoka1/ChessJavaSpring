export function customWebSocket() {
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
}

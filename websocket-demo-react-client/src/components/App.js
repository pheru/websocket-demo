import React from 'react'
import SockJS from 'sockjs-client';
import {Stomp} from '@stomp/stompjs';
import {Button, FormControl} from "react-bootstrap";

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            destination: "",
            payload: "",
            outputs: ""
        };
        this.handleDestinationChange = this.handleDestinationChange.bind(this);
        this.handlePayloadChange = this.handlePayloadChange.bind(this);
        this.send = this.send.bind(this);
        this.subscribe = this.subscribe.bind(this);
        this.onMessageReceived = this.onMessageReceived.bind(this);
    }

    componentDidMount() {
        // let socket = new SockJS('http://localhost:8080/websocket-demo');
        // let stompClient = Stomp.over(new SockJS('http://localhost:8080/websocket-demo'));
        let stompClient = Stomp.over(function () {
            return new SockJS('http://localhost:8080/websocket-demo')
        });
        // Disable debug logging
        stompClient.debug = function (str) {
        };
        stompClient.reconnect_delay = 5000;
        stompClient.connect({},
            () => {
                this.setState({connectedStompClient: stompClient});
            },
            () => console.log("error?"));
    }

    handleDestinationChange(value) {
        this.setState({destination: value});
    }

    handlePayloadChange(value) {
        this.setState({payload: value});
    }

    onMessageReceived(payload) {
        let payloadBody = payload.body;
        this.setState({outputs: "Received: " + payloadBody + "\n" + this.state.outputs});
    }

    subscribe() {
        this.setState({outputs: "Subscribe to " + this.state.destination + "\n" + this.state.outputs});
        this.state.connectedStompClient.subscribe(this.state.destination, this.onMessageReceived);
    }

    send() {
        this.setState({outputs: "Send: " + this.state.payload + " to " + this.state.destination + "\n" + this.state.outputs});
        this.state.connectedStompClient.send("/app" + this.state.destination,
            {},
            this.state.payload
        );
    }

    render() {
        return <div>
            <div style={{display: "flex"}}>
                <div style={{marginRight: 20}}>
                    <p>Messages</p>
                    <p>/simple/messagetopic</p>
                    <p>/simple/messagequeue</p>
                    <p>/forid/messagetopic/-id-</p>
                    <p>/forid/messagequeue/-id-</p>
                    <p>/setattr</p>
                    <p>/convertandsend/simple</p>
                </div>
                <div>
                    <p>Topics / Queues</p>
                    <p>/topic/simple</p>
                    <p>/queue/simple</p>
                    <p>/topic/forid/-id-</p>
                    <p>/queue/forid/-id-</p>
                </div>
            </div>
            <FormControl type="text" value={this.state.destination} placeholder="Destination"
                         onChange={(e) => this.handleDestinationChange(e.target.value)}/>
            <FormControl type="text" value={this.state.payload} placeholder="Payload"
                         onChange={(e) => this.handlePayloadChange(e.target.value)}/>
            <Button bsStyle="primary" onClick={this.subscribe}>Subscribe</Button>
            <Button bsStyle="primary" onClick={this.send}>Send</Button>
            <div>Outputs:</div>
            <p style={{whiteSpace: "pre"}}>{this.state.outputs}</p>
        </div>
    }
}

App.propTypes = {};

export default App;
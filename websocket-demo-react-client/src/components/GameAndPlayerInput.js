import React from 'react'
import {Button, FormControl} from "react-bootstrap";
import PropTypes from 'prop-types';


class GameAndPlayerInput extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            gameId: "",
            playerName: ""
        };
        this.handleGameIdChange = this.handleGameIdChange.bind(this);
        this.handlePlayerNameChange = this.handlePlayerNameChange.bind(this);
    }

    handleGameIdChange(value) {
        this.setState({gameId: value});
    }

    handlePlayerNameChange(value) {
        this.setState({playerName: value});
    }

    render() {
        return <div style={{display: "flex", flexDirection: "column", justifyContent: "center", margin: 25}}>
            <FormControl type="text" value={this.state.gameId}
                         style={{margin: 5}}
                         onChange={(e) => this.handleGameIdChange(e.target.value)}
                         placeholder="Game-ID"/>
            <FormControl type="text" value={this.state.playerName}
                         style={{margin: 5}}
                         onChange={(e) => this.handlePlayerNameChange(e.target.value)}
                         placeholder="Spielername"/>
            <Button bsStyle="primary" style={{margin: 5, width: "100%"}}
                onClick={() => {this.props.onSubmit(this.state.gameId, this.state.playerName)}}>
                {this.props.buttonText}
            </Button>
        </div>;
    }
}

GameAndPlayerInput.propTypes = {
    onSubmit: PropTypes.func.isRequired,
    buttonText: PropTypes.string.isRequired
};

export default GameAndPlayerInput;

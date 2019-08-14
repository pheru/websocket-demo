import React from 'react'
import PropTypes from 'prop-types';
import GameAndPlayerInput from "./GameAndPlayerInput";


class CreateOrJoin extends React.Component {

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
        this.setState({name: value});
    }

    handlePlayerNameChange(value) {
        this.setState({password: value});
    }

    render() {
        return <div style={{display: "flex", flexGrow: 1, justifyContent: "center"}}>
            <GameAndPlayerInput onSubmit={this.props.onCreate} buttonText="Spiel erstellen"/>
            <GameAndPlayerInput onSubmit={this.props.onJoin} buttonText="Spiel beitreten"/>
        </div>;
    }
}

CreateOrJoin.propTypes = {
    onCreate: PropTypes.func.isRequired,
    onJoin: PropTypes.func.isRequired
};

export default CreateOrJoin;

import React from 'react'
import PropTypes from 'prop-types';
import GameAndPlayerInput from "./GameAndPlayerInput";


class Game extends React.Component {

    render() {
        return <div>
            {this.props.gameState.players.map(
                (player) =>
                    <div key={player.name + "fickdick"}>{player.name} - {player.connected ? "Verbunden" : "Nicht verbunden"}</div>
            )}
        </div>;
    }
}

Game.propTypes = {
    gameState: PropTypes.object.isRequired
};

export default Game;

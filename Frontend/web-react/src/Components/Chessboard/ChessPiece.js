import React from 'react'


// Black Pieces
import BlackKing from './ChessPieces/Black/King.svg';
import BlackQueen from './ChessPieces/Black/Queen.svg';
import BlackRook from './ChessPieces/Black/Rook.svg';
import BlackBishop from './ChessPieces/Black/Bishop.svg';
import BlackKnight from './ChessPieces/Black/Knight.svg';
import BlackPawn from './ChessPieces/Black/Pawn.svg';
// White Pieces
import WhiteKing from './ChessPieces/White/King.svg';
import WhiteQueen from './ChessPieces/White/Queen.svg';
import WhiteRook from './ChessPieces/White/Rook.svg';
import WhiteBishop from './ChessPieces/White/Bishop.svg';
import WhiteKnight from './ChessPieces/White/Knight.svg';
import WhitePawn from './ChessPieces/White/Pawn.svg';

const PieceImages = {
    0: BlackKing,
    1: BlackQueen,
    2: BlackRook,
    3: BlackBishop,
    4: BlackKnight,
    5: BlackPawn,
    6: WhiteKing,
    7: WhiteQueen,
    8: WhiteRook,
    9: WhiteBishop,
    10: WhiteKnight,
    11: WhitePawn
};

const PieceColors = {
    0: "Black",
    1: "White"
}

const PieceTypes = {
    0: "King",
    1: "Queen",
    2: "Rook",
    3: "Bishop",
    4: "Knight",
    5: "Pawn"
}


function ChessPiece({Type, Color}) {
    /*
    Type: {
        0: King,
        1: Queen,
        2: Rook,
        3: Bishop,
        4: Knight,
        5: Pawn
    }

    Color: {
        0: Black,
        1: White
    }
*/
  return (
    <>
    <div draggable={true} style={{position:"absolute"}}> 
        <img 
        alt={`${Color === 0 ? "Black" : "White"} ${PieceTypes[Type]}`}
        src={PieceImages[Color === 0 ? (Type) : (6 + Type)]} />
    </div>
    </>
  )
}

export default ChessPiece
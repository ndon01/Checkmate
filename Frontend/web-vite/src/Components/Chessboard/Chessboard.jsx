import React, { useState } from 'react';
import { Paper, Typography, Grid, Button } from "@mui/material";
import Draggable from 'react-draggable';

const ChessBoard = () => {
  const [dragging, setDragging] = useState(false);
  const [currentSquare, setCurrentSquare] = useState(null);

  const handleDrag = (e, data) => {
    const tileSize = 500 / 8;
    const startX = e.clientX;
    const startY = e.clientY
    const deltaX = data.X;
    const deltaY = data.Y;

    const x = Math.floor((startX + deltaX) / tileSize);
    const y = Math.floor((startY + deltaY) / tileSize);
    setCurrentSquare({ x, y });
  };

  const handleDragStop = () => {
    setDragging(false);
    setCurrentSquare(null);
  };

  const board = [
    ['r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'],
    ['p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'],
    [null, null, null, null, null, null, null, null],
    [null, null, null, null, null, null, null, null],
    [null, null, null, null, null, null, null, null],
    [null, null, null, null, null, null, null, null],
    ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'],
    ['R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'],
  ];

  const pieces = {
    'r': '♜',
    'n': '♞',
    'b': '♝',
    'q': '♛',
    'k': '♚',
    'p': '♟︎',
    'R': '♖',
    'N': '♘',
    'B': '♗',
    'Q': '♕',
    'K': '♔',
    'P': '♙'
  };

  return (
    <Paper
      style={{
        width: "max-content",
        height: "max-content",
      }}
    >
      <Grid
        container
        direction="column"
        justifyContent="space-between"
        style={{ height: "100%" }}
      >
        {/* Top Player */}
        <Grid container item justifyContent="space-between" alignItems="center">
          <Typography variant="h6">User2</Typography>
          <Button variant="outlined">Clock</Button>
        </Grid>

        {/* Chess Board */}
        <Grid item container>
          <div style={{ position: "absolute", width: "500px", height: "500px", zIndex: 2 }}>
            {board.map((row, rowIndex) => (
              <Grid container key={rowIndex} style={{ height: "12.5%", width: "500px" }}>
                {row.map((piece, colIndex) => (
                  piece && (
                    <Draggable
                      key={colIndex}
                      onStart={() => setDragging(true)}
                      onDrag={handleDrag}
                      onStop={handleDragStop}
                    >
                      <div style={{
                        width: "12.5%",
                        height: "100%",
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "center",
                        fontSize: "24px",
                      
                      }}>
                        {pieces[piece]}
                      </div>
                    </Draggable>
                  )
                ))}
              </Grid>
            ))}
          </div>
          <Grid item container justifyContent="center" style={{ width: "100%" }}>
            <Paper style={{ width: "500px", height: "500px", background: "none" }}>
              {[...Array(8)].map((_, rowIndex) => (
                <Grid container key={rowIndex} style={{ height: "12.5%" }}>
                  {[...Array(8)].map((__, colIndex) => (
                    <Paper
                      key={colIndex}
                      style={{
                        width: "12.5%",
                        height: "100%",
                        backgroundColor:
                          (rowIndex + colIndex) % 2 === 0
                            ? "rgb(233, 194, 150)"
                            : "rgb(125, 85, 52)",
                        boxShadow:
                          (rowIndex + colIndex) % 2 !== 0
                            ? "2px 2px 5px rgba(0, 0, 0, 0.2)"
                            : "none",
                        zIndex: (rowIndex + colIndex) % 2 !== 0 ? 1 : 0,
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "center",
                        fontSize: "24px",
                        borderRadius: "0px",
                        transform: currentSquare && currentSquare.x === colIndex && currentSquare.y === rowIndex ? "scale(1.1)" : "none"
                      }}
                    >
                      {/* Empty space for now */}
                    </Paper>
                  ))}
                </Grid>
              ))}
            </Paper>
          </Grid>
        </Grid>

        {/* Bottom Player */}
        <Grid container item justifyContent="space-between" alignItems="center">
          <Typography variant="h6">Player</Typography>
          <Button variant="outlined">Clock</Button>
        </Grid>
      </Grid>
    </Paper>
  );
};

export default ChessBoard;

import React, { useState } from "react";
import { Paper, Typography, Grid, Button } from "@mui/material";


const ChessBoard = () => {
  

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
        justify="space-between"
        style={{ height: "100%" }}
      >
        {/* Top Player */}
        <Grid container item justify="space-between" alignItems="center">
          <Typography variant="h6">User2</Typography>
          <Button variant="outlined">Clock</Button>
        </Grid>

        {/* Chess Board */}
        <Grid item container>
          <Grid item container justify="center" style={{ width: "100%" }}>
            <Paper
              style={{ width: "500px", height: "500px", background: "none" }}
            >
              {board.map((row, rowIndex) => (
                <Grid container key={rowIndex} style={{ height: "12.5%" }}>
                  {row.map((piece, colIndex) => (
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
                      }}
                      onDrop={(e) => handleDrop(e, rowIndex, colIndex)}
                      onDragOver={handleDragOver}
                    >
                      <span
                        draggable="true"
                        onDragStart={(e) => handleDragStart(e, piece)}
                      >
                        {pieces[piece]}
                      </span>
                    </Paper>
                  ))}
                </Grid>
              ))}
            </Paper>
          </Grid>
        </Grid>

        {/* Bottom Player */}
        <Grid container item justify="space-between" alignItems="center">
          <Typography variant="h6">Player</Typography>
          <Button variant="outlined">Clock</Button>
        </Grid>
      </Grid>
    </Paper>
  );
};

export default ChessBoard;

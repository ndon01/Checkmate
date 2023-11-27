import NavigationBar from "@/Components/NavigationBar/NavigationBar.jsx";
import {FooterArea} from "@/Components/General/FooterArea/index.jsx";
import {Box, Fab} from "@mui/material";
import {Chat, Circle, CircleOutlined, Mail} from "@mui/icons-material";
import {MainArea} from "@/Components/General/MainArea.jsx";
import {useNavigate, useParams} from "react-router-dom";
import React, {useEffect} from "react";
import axios from "axios";
import { Chessboard } from "react-chessboard";

import styles from './match.module.css';

export const Match = () => {

    const navigate = useNavigate();

    const params = useParams();
    const { matchId} = params;

    const [matchMetadata, setMatchMetadata] = React.useState({
        id: "adfa",
        whiteUserId: 1,
        blackUserId: 2,
        winnerUserId: null,

        wasDraw: false,
        wasAbandoned: false,
        wasForfeited: false,

        createdAt: null,
        startedAt: null,
        finishedAt: null,
    })

    const [matchState, setMatchState] = React.useState({
        status: "notStarted", // notStarted, started, completed
        boardState: "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR",
        matchMoves: [],
        currentMove: "w",

        whiteSecondsLeft: 300,
        blackSecondsLeft: 300,
    })



    const [chessBoardSize, setChessBoardSize] = React.useState(600);

    useEffect(() => {
        // Set up the interval only when the match starts
        let timer;
        if (matchState.status === 'started') {
            timer = setInterval(() => {
                setMatchState(prevState => {
                    // Calculate new time based on whose turn it is
                    const newTime = prevState.currentMove === 'w'
                        ? prevState.whiteSecondsLeft - 1
                        : prevState.blackSecondsLeft - 1;

                    return {
                        ...prevState,
                        whiteSecondsLeft: prevState.currentMove === 'w' ? newTime : prevState.whiteSecondsLeft,
                        blackSecondsLeft: prevState.currentMove === 'b' ? newTime : prevState.blackSecondsLeft,
                    };
                });
            }, 1000);
        }

        // Clear the interval when component unmounts or when match status changes
        return () => clearInterval(timer);
    }, [matchState.status, matchState.currentMove]);

    function secondsToMinutesSeconds(seconds) {
        const minutes = Math.floor(seconds / 60);
        const remainingSeconds = seconds % 60;

        // Adding leading zeros if minutes or seconds are less than 10
        const formattedMinutes = minutes.toString().padStart(2, '0');
        const formattedSeconds = remainingSeconds.toString().padStart(2, '0');

        return `${formattedMinutes}:${formattedSeconds}`;
    }

    function isDraggablePiece({piece}) {
        console.log(piece)
        return piece.startsWith("w");
    }

    return (
        <>
            <NavigationBar/>

            <MainArea>
                <div className={styles.pageContainer}>
                    <div className={styles.pageHeader}>
                        <span className={styles.pageTitle}>Nick vs Jack</span>
                    </div>
                    <div className={styles.mainContent}>
                        <div className={styles.gameContainer}>
                            <div className={styles.playerContainer} style={{
                                width: `${chessBoardSize}px`
                            }}>
                                <div className={styles.playerUsernameContainer}>
                                    <Circle/><span style={
                                    {
                                        fontSize: '16px',
                                        fontFamily: 'Inter',
                                        fontWeight: 'bold',
                                        marginLeft: '10px'
                                    }
                                }>Opponent <span style={{fontWeight: 'normal'}}>(1200)</span>
                                    </span>
                                </div>
                                <div className={styles.timerContainer}>
                                    <span style={
                                        {
                                            fontSize: '16px',
                                            fontFamily: 'Inter',
                                            fontWeight: 'bold'
                                        }
                                    }>
                                        {secondsToMinutesSeconds(matchState.blackSecondsLeft)}
                                    </span>
                                </div>
                            </div>
                            <div className={styles.boardContainer}>
                                <Chessboard

                                    boardWidth={chessBoardSize}
                                    boardOrientation={"white"}
                                    isDraggablePiece={isDraggablePiece}
                                    showBoardNotation={true}
                                    position={matchState.boardState}
                                />
                            </div>
                            <div className={styles.playerContainer} style={{
                                width: `${chessBoardSize}px`
                            }}>
                                <div className={styles.playerUsernameContainer}>
                                    <CircleOutlined/><span style={
                                        {
                                            fontSize: '16px',
                                            fontFamily: 'Inter',
                                            fontWeight: 'bold',
                                            marginLeft: '10px'
                                        }
                                    }>You <span style={{fontWeight: 'normal'}}>(1200)</span>
                                    </span>
                                </div>
                                <div className={styles.timerContainer}>
                                    <span style={
                                        {
                                            fontSize: '16px',
                                            fontFamily: 'Inter',
                                            fontWeight: 'bold'
                                        }
                                    }>
                                        {secondsToMinutesSeconds(matchState.whiteSecondsLeft)}
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </MainArea>

        </>
    )
}

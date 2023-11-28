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
import {useUser} from "@/Contexts/UserContext.jsx";

export const Match = () => {

    const navigate = useNavigate();

    const params = useParams();
    const { matchId} = params;

    const {currentUser, checkContextValidity, setCurrentUser} = useUser();

    if (currentUser === null) {
        setCurrentUser(JSON.parse(localStorage.getItem("context")));
    }

    const [matchData, setMatchData] = React.useState({
        "matchId": 2,
        "matchStatus": "PENDING",
        "drawRequested": false,
        "drawRequesterId": null,
        "matchMoves": "",
        "currentBoard": "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR",
        "matchType": "BLITZ",
        "whiteUserId": 2,
        "blackUserId": 1,
        "lastWhitePing": 1701163343,
        "lastBlackPing": null,
        "winnerUserId": null,
        "lastMoveTime": null,
        "whiteTimeLeft": 300,
        "blackTimeLeft": 300,
        "abandoned": false,
        "forfeited": false,
        "whiteTurn": true,
        "draw": false,
        "rated": true
    });


    function fetchMatchData() {
        fetch(`http://localhost:8080/api/matches/pingMatch?matchId=${matchId}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("access_token")
            },
        }).then(response => {
            if (response.ok) {
                response.json().then(data => {
                    setMatchData(data);
                })
            } else {
                console.log(response)
            }
        }).catch(error => {
            console.log(error)
        })
    }

    useEffect(() => {
        // fetch match data interval
        const interval = setInterval(() => {
            fetchMatchData();
        }, 5000)

        return () => clearInterval(interval);
    }, [])


    const [chessBoardSize, setChessBoardSize] = React.useState(600);

    // timer countdown

    React.useEffect(() => {
        const interval = setInterval(() => {
            if (matchData.whiteTurn) {
                if (matchData.whiteTimeLeft <= 0) {
                    setMatchData(prevState => ({
                        ...prevState,
                        winnerUserId: matchData.blackUserId,
                        matchStatus: "FINISHED"
                    }))
                } else {
                    setMatchData(prevState => ({
                        ...prevState,
                        whiteTimeLeft: prevState.whiteTimeLeft - 1
                    }))
                }
            } else {
                if (matchData.blackTimeLeft <= 0) {
                    setMatchData(prevState => ({
                        ...prevState,
                        winnerUserId: matchData.whiteUserId,
                        matchStatus: "FINISHED"
                    }))
                } else {
                    setMatchData(prevState => ({
                        ...prevState,
                        blackTimeLeft: prevState.blackTimeLeft - 1
                    }))
                }
            }
        }, 1000)

        return () => clearInterval(interval);
    }, [matchData])

    function secondsToMinutesSeconds(seconds) {
        const minutes = Math.floor(seconds / 60);
        const remainingSeconds = seconds % 60;

        // Adding leading zeros if minutes or seconds are less than 10
        const formattedMinutes = minutes.toString().padStart(2, '0');
        const formattedSeconds = remainingSeconds.toString().padStart(2, '0');

        return `${formattedMinutes}:${formattedSeconds}`;
    }

    function isDraggablePiece({piece}) {
        let pieceColor = piece.startsWith("w") ? "white" : "black";

        if (pieceColor === "white" && matchData.whiteUserId === currentUser.userId && matchData.whiteTurn) {
            return true;
        } else return pieceColor === "black" && matchData.blackUserId === currentUser.userId && !matchData.whiteTurn;
    }

    console.log(matchData)
    console.log(currentUser)

    return (
        <>
            <NavigationBar/>

            <MainArea>
                {JSON.stringify(currentUser)}
                {JSON.stringify(matchData)}
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
                                    {(matchData.whiteTurn && matchData.whiteUserId != currentUser.userId) ? <Circle/> : <CircleOutlined/>}<span style={
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
                                        {secondsToMinutesSeconds(matchData.blackUserId !== currentUser.userId ? matchData.blackTimeLeft : matchData.whiteTimeLeft)}
                                    </span>
                                </div>
                            </div>
                            <div className={styles.boardContainer}>
                                <Chessboard

                                    boardWidth={chessBoardSize}
                                    boardOrientation={matchData.whiteUserId === currentUser.userId ? 'white' : 'black'}
                                    isDraggablePiece={isDraggablePiece}
                                    showBoardNotation={true}
                                    position={matchData.currentBoard}

                                />
                            </div>
                            <div className={styles.playerContainer} style={{
                                width: `${chessBoardSize}px`
                            }}>
                                <div className={styles.playerUsernameContainer}>
                                    {(matchData.whiteTurn && matchData.whiteUserId === currentUser.userId) ? <Circle/> : <CircleOutlined/>}<span style={
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
                                        {secondsToMinutesSeconds(currentUser.userId === matchData.blackUserId ? matchData.blackTimeLeft : matchData.whiteTimeLeft)}
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>

                    {JSON.stringify(matchData)}
                </div>
            </MainArea>

        </>
    )
}

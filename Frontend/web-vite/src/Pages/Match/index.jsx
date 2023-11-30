import NavigationBar from "@/Components/NavigationBar/NavigationBar.jsx";
import {FooterArea} from "@/Components/General/FooterArea/index.jsx";
import {Box, Fab} from "@mui/material";
import {Chat, Circle, CircleOutlined, Mail} from "@mui/icons-material";
import {MainArea} from "@/Components/General/MainArea.jsx";
import {useNavigate, useParams} from "react-router-dom";
import React, {useEffect} from "react";
import axios from "axios";
import {Chessboard} from "react-chessboard";

import styles from './match.module.css';
import {useUser} from "@/Contexts/UserContext.jsx";

export const Match = () => {

    const navigate = useNavigate();

    const params = useParams();
    const {matchId} = params;

    const {currentUser, checkContextValidity, setCurrentUser} = useUser();

    if (currentUser === null) {
        setCurrentUser(JSON.parse(localStorage.getItem("context")));
    }

    const [matchData, setMatchData] = React.useState({
        matchId: "",
        whiteUserId: "",
        blackUserId: "",
        whiteTimeLeft: 0,
        blackTimeLeft: 0,
        currentBoard: "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR",
        isWhiteTurn: false,
        matchStatus: ""
    });

    const [whiteTimeLeft, setWhiteTimeLeft] = React.useState(0);
    const [blackTimeLeft, setBlackTimeLeft] = React.useState(0);
    const [currentBoard, setCurrentBoard] = React.useState("");
    const [whiteTurn, setWhiteTurn] = React.useState(false);
    const [whiteUserId, setWhiteUserId] = React.useState(0);
    const [blackUserId, setBlackUserId] = React.useState(0);

    const [whiteUser, setWhiteUser] = React.useState({});
    const [blackUser, setBlackUser] = React.useState({});


    useEffect(() => {
        // Fetch match data interval
        const interval = setInterval(() => {
            fetchMatchData();
        }, 1000); // Polling every 1 seconds

        return () => clearInterval(interval);
    }, []); // Empty dependency array to run only on mount and unmount


    // Fetch Player Data

    useEffect(() => {

        fetch(`http://localhost:8080/api/users/getUserProfile?userId=${whiteUserId}`, {
            method: "GET",
            headers: {
                Authorization: "Bearer " + localStorage.getItem("access_token")
            }
        }).then(response => {
            if (response.ok) {
                response.json().then(data => {
                    setWhiteUser(data)
                })
            } else {
                setWhiteUser({
                    username: "Anonymous"
                })
                console.log(response);
            }
        }).catch(error => {
            console.log(error);
        });

    }, [whiteUserId])

    useEffect(() => {

        fetch(`http://localhost:8080/api/users/getUserProfile?userId=${blackUserId}`, {
            method: "GET",
            headers: {
                Authorization: "Bearer " + localStorage.getItem("access_token")
            }
        }).then(response => {
            if (response.ok) {
                response.json().then(data => {
                    setBlackUser(data);
                })
            } else {
                setBlackUser({
                    username: "Anonymous"
                })
                console.log(response);
            }
        }).catch(error => {
            console.log(error);
        });

    }, [blackUserId])

    function fetchMatchData() {
        fetch(`http://localhost:8080/api/matches/pingMatch?matchId=${matchId}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("access_token")
            },
        }).then(response => {
            if (response.ok) {
                response.json().then(newData => {
                    // Update state only if the turn or match status has changed
                    setMatchData(prevState => {
                        // Create a copy of the previous state
                        let updatedState = {...prevState};

                        // Iterate over the keys of the new data
                        for (let key in newData) {
                            // Update only if the value has changed
                            if (newData[key] !== prevState[key]) {

                                if (key === "whiteTurn") {
                                    setWhiteTurn(newData[key]);
                                }

                                if (key === "whiteTimeRemaining") {
                                    setWhiteTimeLeft(newData[key]);
                                }

                                if (key === "blackTimeRemaining") {
                                    setBlackTimeLeft(newData[key]);
                                }

                                if (key === "currentBoard") {
                                    setCurrentBoard(newData[key]);
                                }

                                if (key === "whiteUserId") {
                                    setWhiteUserId(newData[key]);
                                }

                                if (key === "blackUserId") {
                                    setBlackUserId(newData[key]);
                                }

                                updatedState[key] = newData[key];
                            }
                        }

                        return updatedState;
                    });
                })
            } else {
                console.log(response)
            }
        }).catch(error => {
            console.log(error)
        })
    }

    const [chessBoardSize, setChessBoardSize] = React.useState(600);

    // timer countdown
    React.useEffect(() => {
        const interval = setInterval(() => {
            if (whiteTimeLeft <= 0 || blackTimeLeft <= 0) {
                clearInterval(interval);
                return;
            }

            if (matchData.matchStatus === "FINISHED" || matchData.finished === true) {
                clearInterval(interval);
            }

            if (matchData.matchStatus === "PENDING") {
                return;
            }

            console.log("in progress")
            console.log("white turn: " + matchData.isWhiteTurn)
            if (matchData.isWhiteTurn === true) {
                setWhiteTimeLeft(prevState => prevState - 1);
            } else {
                setBlackTimeLeft(prevState => prevState - 1);
            }
        }, 1000)

        console.log(whiteTurn, whiteTimeLeft, blackTimeLeft)

        return () => clearInterval(interval);
    }, [whiteTimeLeft, blackTimeLeft])

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

        if (pieceColor === "white" && matchData.whiteUserId === currentUser?.userId && whiteTurn) {
            return true;
        } else return pieceColor === "black" && matchData.blackUserId === currentUser?.userId && !whiteTurn;
    }

    function formatMatchData(data) {
        return (
            <div className={styles.matchDataContainer}>
                {Object.entries(data).map(([key, value]) => (
                    <div key={key} className={styles.matchDataItem}>
                        <strong>{key}:</strong> {JSON.stringify(value)}
                    </div>
                ))}
            </div>
        );
    }


    function dropPiece(sourceSquare, targetSquare) {
        console.log(sourceSquare, targetSquare);
        let success = false;
        fetch(`http://localhost:8080/api/matches/makeMove?matchId=${matchData.matchId}&move=${sourceSquare}${targetSquare}`, {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("access_token"),
            }
        }).then(response => {
            if (response.ok) {
                console.log("Piece moved successfully!");
                success = true;
            } else {
                console.log(response);
                success = false;
            }
        })

        return success;
    }


    return (
        <>
            <NavigationBar/>

            <MainArea>
                {currentUser && formatMatchData(currentUser) || null}
                {formatMatchData(matchData)}
                {formatMatchData(whiteUser)}
                {formatMatchData(blackUser)}
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

                                    {/* Current user exists and is white user and it is his turn or he is black user and it is his turn else default to white*/}

                                    {blackUserId === currentUser?.userId && matchData.isWhiteTurn ? <Circle style={{
                                        marginRight: '10px'
                                    }}/>: null}

                                    {blackUserId === currentUser?.userId && !matchData.isWhiteTurn ? <CircleOutlined style={{
                                        marginRight: '10px'
                                    }}/> : null}

                                    {blackUserId !== currentUser?.userId && !matchData.isWhiteTurn ? <Circle style={{
                                        marginRight: '10px'
                                    }}/>: null}

                                    {blackUserId !== currentUser?.userId && matchData.isWhiteTurn ? <CircleOutlined style={{
                                        marginRight: '10px'
                                    }}/>: null}


                                    <span style={{
                                        fontSize: '16px',
                                        fontFamily: 'Inter',
                                        fontWeight: 'bold',
                                    }}>
                                        {blackUserId === currentUser?.userId ? whiteUser.username : blackUser.username}

                                        <span style={{fontWeight: 'normal', marginLeft: '10px'}}>(1200)</span>
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
                                       {secondsToMinutesSeconds( currentUser?.userId === matchData.blackUserId ? whiteTimeLeft : blackTimeLeft)}
                                    </span>
                                </div>
                            </div>
                            <div className={styles.boardContainer}>
                                <Chessboard

                                    boardWidth={chessBoardSize}
                                    boardOrientation={matchData.blackUserId === currentUser?.userId ? 'black' : 'white'}
                                    isDraggablePiece={isDraggablePiece}
                                    onPieceDrop={dropPiece}
                                    showBoardNotation={true}
                                    position={matchData.currentBoard}

                                />
                            </div>
                            <div className={styles.playerContainer} style={{
                                width: `${chessBoardSize}px`
                            }}>
                                <div className={styles.playerUsernameContainer}>

                                    {/* Current user exists and is white user and it is his turn or he is black user and it is his turn else default to white*/}

                                    {(blackUserId === currentUser?.userId && !matchData.isWhiteTurn) && <Circle style={{
                                        marginRight: '10px'
                                    }}/>}

                                    {blackUserId === currentUser?.userId && matchData.isWhiteTurn && <CircleOutlined style={{
                                        marginRight: '10px'
                                    }}/>}

                                    {blackUserId !== currentUser?.userId && matchData.isWhiteTurn && <Circle style={{
                                        marginRight: '10px'
                                    }}/>}

                                    {blackUserId !== currentUser?.userId && !matchData.isWhiteTurn && <CircleOutlined style={{
                                        marginRight: '10px'
                                    }}/>}

                                    <span style={{
                                        fontSize: '16px',
                                        fontFamily: 'Inter',
                                        fontWeight: 'bold',
                                    }}>
                                        {whiteUserId === currentUser?.userId && "You"}
                                        {blackUserId === currentUser?.userId && "You"}
                                        {!currentUser && whiteUser.username}

                                        <span style={{fontWeight: 'normal', marginLeft: '10px'}}>(1200)</span>
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
                                        {secondsToMinutesSeconds(currentUser?.userId === matchData.blackUserId ? blackTimeLeft : whiteTimeLeft)}
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

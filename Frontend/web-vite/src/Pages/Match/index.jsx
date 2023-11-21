import NavigationBar from "@/Components/NavigationBar/NavigationBar.jsx";
import {FooterArea} from "@/Components/General/FooterArea/index.jsx";
import ChessBoard from "@/Components/Chessboard/Chessboard.jsx";
import {Box, Fab} from "@mui/material";
import {Chat, Mail} from "@mui/icons-material";
import {MainArea} from "@/Components/General/MainArea.jsx";
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";
import axios from "axios";



export const Match = () => {

    const navigate = useNavigate();

    const queryParams = new URLSearchParams(location.search);
    const matchId = queryParams.get('matchId');

    useEffect(() => {
        // get match url

        // attempt to connect to match
        var socket = new WebSocket()

    }, []);

    return (
        <>
            <NavigationBar/>

            <MainArea>
                <Box style={{
                    width: '100%',
                    height: '100%'
                }}>



                </Box>
            </MainArea>

        </>
    )
}

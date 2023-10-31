import NavigationBar from "@/Components/General/NavigationBar/NavigationBar.jsx";
import {FooterArea} from "@/Components/General/FooterArea/index.jsx";
import ChessBoard from "@/Components/Chessboard/Chessboard.jsx";
import {Box, Fab} from "@mui/material";
import {Chat, Mail} from "@mui/icons-material";

export const Match = () => {
    return (
        <>
            <NavigationBar/>

            <Box style={{
                minWidth: "100%",
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                marginBlock: "20px"
            }}>
            <ChessBoard/>
            </Box>

            <Fab
                color="white"
                aria-label="message"
                style={{
                    position: 'fixed',
                    bottom: '20px',
                    right: '20px'
                }}
            >
                <Mail />
            </Fab>

            <FooterArea/>

        </>
    )
}

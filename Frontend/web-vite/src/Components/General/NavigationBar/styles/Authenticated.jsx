import React, { useState } from 'react';
import {
    AppBar,
    Toolbar,
    Button,
    IconButton,
    Box,
    Menu,
    MenuItem
} from "@mui/material";
import { Link } from "react-router-dom";
import imgUrl from '@/Assets/CheckmateLogo.png';
import {useUser} from "@/Contexts/UserContext.jsx";

function Authenticated() {
    const [anchorEl, setAnchorEl] = useState(null);

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const {
        currentUser,
        isAuthenticated,
        loginUser,
        logoutUser,
        sendRequest
    } = useUser();

    return (
        <>
            <AppBar position="static">
                <Toolbar>
                    <div style={{ textAlign: 'center' }}>
                        <img
                            src={imgUrl}
                            style={{ width: "50px", height: "50px" }}
                            alt="logo"
                        />
                    </div>

                    <div style={{position: "relative", left: "15px"}}>
                        <Button color="inherit" component={Link} to="/dashboard">
                            Dashboard
                        </Button>
                        <Button color="inherit" component={Link} to="/play">
                            Play
                        </Button>
                        <Button color="inherit" component={Link} to="/connect">
                            Connect
                        </Button>
                    </div>


                    <div style={{ position: 'absolute', right: '25px' }}>
                        <IconButton onClick={handleClick}>
                            <Box
                                style={{
                                    width: "32px", height: "32px", backgroundColor: "white",
                                    borderRadius: "100%"
                                }}
                            >
                                <span style={{
                                    color: "black",
                                    position: "relative",
                                    top: "1px",
                                    left: ".5px",
                                    fontSize: "18px"
                                }}>N</span>
                            </Box>
                        </IconButton>
                        <Menu
                            anchorEl={anchorEl}
                            open={Boolean(anchorEl)}
                            onClose={handleClose}
                        >
                            <MenuItem onClick={handleClose}>Settings</MenuItem>
                            <MenuItem onClick={logoutUser}>Logout</MenuItem>
                        </Menu>
                    </div>
                </Toolbar>
            </AppBar>
        </>
    );
}

export default Authenticated;

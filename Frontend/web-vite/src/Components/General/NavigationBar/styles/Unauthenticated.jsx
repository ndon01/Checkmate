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
                        <Button color="inherit" component={Link} to="/home">
                            Home
                        </Button>
                    </div>


                    <div style={{ position: 'absolute', right: '25px' }}>
                        <Button color="inherit" component={Link} to="/login">
                            Login
                        </Button>
                        <Button color="inherit" component={Link} to="/register">
                            Register
                        </Button>
                    </div>
                </Toolbar>
            </AppBar>
        </>
    );
}

export default Authenticated;

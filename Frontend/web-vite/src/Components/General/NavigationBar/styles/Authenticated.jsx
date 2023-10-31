import React from "react";
import {
    AppBar,
    Toolbar,
    Typography,
    Button,
    IconButton,
    Paper,
    Box, useColorScheme,
} from "@mui/material";
import HomeIcon from "@mui/icons-material/Home";
import SearchIcon from "@mui/icons-material/Search";
import NotificationsIcon from "@mui/icons-material/Notifications";
import SettingsIcon from "@mui/icons-material/Settings";
import { Link } from "react-router-dom";
import { useUser } from "@/Contexts/UserContext.jsx";
import imgUrl from '@/Assets/CheckmateLogo.png';
function Authenticated() {
    const isAuthenticated = true;
    return (
        <>
            <Box
                style={{
                    height: "100%",
                    width: "100%",
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    position: "absolute",
                }}
            >
                {/* Logo */}

                <img
                    src={imgUrl}
                    style={{ width: "50px", height: "50px", transition: "all .3s"}}
                    alt="logo"
                />

            </Box>
            <Toolbar style={{ display: "relative" }}>
                {/* Left Side */}
                <Box style={{ height: "100%", position: "relative", left: 0 }}>

                        <>
                            <Button color="inherit" component={Link} to="/dashboard">
                                Dashboard
                            </Button>
                            <Button color="inherit" component={Link} to="/play">
                                Play
                            </Button>
                            <Button color="inherit" component={Link} to="/connect">
                                Connect
                            </Button>
                        </>

                </Box>

                <Box
                    style={{
                        height: "100%",
                        width: "max-content",
                        position: "absolute",
                        right: "25px",
                        display: "flex",
                        justifyContent: "center",
                        alignItems: "center",
                    }}
                >
                    {/* Right Side */}
                        <>
                            <IconButton>
                                <Box
                                    style={{width:"32px", height: "32px", backgroundColor:"white",
                                    borderRadius:"100%"}}
                                >
                                    <span style={{
                                        color: "black",
                                        position: "relative",
                                        top:"1px",
                                        left:".5px",
                                        fontSize: "18px"
                                    }}>N</span>
                                    <div style={{
                                        height: "10px",
                                        width: "10px",
                                        borderRadius:"100%",
                                        backgroundColor: "limegreen",

                                        position: "relative",
                                        left:"22.5px",
                                        top:"-6px"
                                    }}></div>
                                </Box>
                            </IconButton>
                        </>

                </Box>
            </Toolbar>
        </>
    );
}

export default Authenticated;

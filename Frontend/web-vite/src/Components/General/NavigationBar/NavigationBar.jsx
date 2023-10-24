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
import { useUser } from "../../../Contexts/UserContext.jsx";
import imgUrl from './CheckmateLogo.png';
function NavigationBar() {
    const isAuthenticated = false;
  return (
    <AppBar position="sticky" >
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
          {isAuthenticated ? (
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
          ) : (
            <Button color="inherit" component={Link} to="/">
              Home
            </Button>
          )}
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
          {isAuthenticated ? (
            <>
              <IconButton
                color="inherit"
                style={{ width: "24px", height: "24px" }}
              >
                <SearchIcon />
              </IconButton>
              <IconButton
                color="inherit"
                style={{ width: "24px", height: "24px" }}
              >
                <NotificationsIcon />
              </IconButton>
              <IconButton
                color="inherit"
                style={{ width: "24px", height: "24px" }}
              >
                <SettingsIcon />
              </IconButton>
            </>
          ) : (
            <>
              <Button
                color="inherit"
                component={Link}
                to="/login"
              >
                Login
              </Button>
              <Button color="inherit" component={Link} to="/register">
                Register
              </Button>
            </>
          )}
        </Box>
      </Toolbar>
    </AppBar>
  );
}

export default NavigationBar;

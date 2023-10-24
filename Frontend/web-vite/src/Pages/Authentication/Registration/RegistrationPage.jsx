import React, { useState } from "react";
import { Button, TextField, Grid, Container, Typography } from "@mui/material";
import { AccountCircle, LockRounded, EmailRounded } from "@mui/icons-material";
import NavigationBar from "../../../Components/General/NavigationBar/NavigationBar.jsx";

import axios from "axios";
import {Navigate, useNavigate} from "react-router-dom";
import {useUser} from "../../../Contexts/UserContext.jsx";

const RegistrationPage = () => {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const navigate = useNavigate();
  const {
    currentUser,
    isAuthenticated,
    loginUser,
    logoutUser,
    registerUser,
    verifyUser
  } = useUser();
  const handleSubmit = (e) => {
    e.preventDefault();

    console.log(password, confirmPassword);

    registerUser({
      username: username,
      email: email,
      password: password
    })


  };

  return (
    <>
      <NavigationBar />
      <Container
        style={{ height: "100vh", display: "grid", placeItems: "center" }}
      >
        <form onSubmit={handleSubmit}>
          <Typography variant="h5" style={{ margin: "2rem 0", textAlign: "center"}}>
            Register
          </Typography>
          <Grid container spacing={1} alignItems="center" justifyContent="center" marginBottom={"15px"}>
            <Grid item>
              <AccountCircle />
            </Grid>
            <Grid item>
              <TextField
                label="Username"
                required
                value={username}
                onChange={(e) => setUsername(e.target.value)}
              />
            </Grid>
          </Grid>
          <Grid container spacing={1} alignItems="center" justifyContent="center" marginBottom={"15px"}>
            <Grid item>
              <EmailRounded />
            </Grid>
            <Grid item>
              <TextField
                label="Email"
                type="email"
                required
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </Grid>
          </Grid>
          <Grid container spacing={1} alignItems="center" justifyContent="center" marginBottom={"15px"}>
            <Grid item>
              <LockRounded />
            </Grid>
            <Grid item>
              <TextField
                label="Password"
                type="password"
                required
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </Grid>
          </Grid>
          <Grid container spacing={1} alignItems="center" justifyContent="center" marginBottom={"15px"}>
            <Grid item>
              <LockRounded />
            </Grid>
            <Grid item>
              <TextField
                label="Confirm Password"
                type="password"
                required
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
              />
            </Grid>
          </Grid>
          <Grid container justifyContent="center" alignItems="center" marginTop={"10px"}>
            <Button type="submit" variant="contained" color="primary">
              Register
            </Button>
          </Grid>
        </form>
      </Container>
    </>
  );
};

export default RegistrationPage;

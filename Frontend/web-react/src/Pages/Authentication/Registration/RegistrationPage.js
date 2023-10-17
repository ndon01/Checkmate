import React, { useState } from "react";
import { Button, TextField, Grid, Container, Typography } from "@mui/material";
import { AccountCircle, LockRounded, EmailRounded } from "@mui/icons-material";
import NavigationBar from "../../../Components/General/NavigationBar/NavigationBar";

import axios from "axios";

const RegistrationPage = () => {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();

    // Validation: Check if passwords match
    if (password !== confirmPassword) {
      alert("Passwords do not match!");
      return;
    }

    axios
      .post("/api/users/register", {
        Username: username,
        DisplayName: username,
        EmailAddress: email,
        DateOfBirth: "06-22-2003",
        Password: password,
      })
      .then((data) => {
        console.log(data);
      })
      .catch((err) => {
        console.log(err);
      });

    // Handle registration logic here (e.g., call an API)
    console.log("Username:", username, "Email:", email, "Password:", password);
  };

  return (
    <>
      <NavigationBar />
      <Container
        style={{ height: "100vh", display: "grid", placeItems: "center" }}
      >
        <form onSubmit={handleSubmit}>
          <Typography variant="h5" style={{ margin: "2rem 0" }}>
            Registers
          </Typography>
          <Grid container spacing={1} alignItems="center" marginBottom={"15px"}>
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
          <Grid container spacing={1} alignItems="center" marginBottom={"15px"}>
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
          <Grid container spacing={1} alignItems="center" marginBottom={"15px"}>
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
          <Grid container spacing={1} alignItems="center" marginBottom={"15px"}>
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
          <Grid container justifyContent="center" style={{ marginTop: "2rem" }}>
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

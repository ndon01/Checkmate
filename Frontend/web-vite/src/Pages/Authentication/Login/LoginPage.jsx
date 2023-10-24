import React, { useState } from "react";
import { Button, TextField, Grid, Container, Typography } from "@mui/material";
import { AccountCircle, LockRounded } from "@mui/icons-material";
import NavigationBar from "../../../Components/General/NavigationBar/NavigationBar.jsx";

const LoginPage = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    // Handle login logic here (e.g., call an API)
    console.log("Email:", email, "Password:", password);
  };

  return (
    <>
      <NavigationBar />
      <Container
        style={{ height: "100vh", display: "grid", placeItems: "center" }}
      >
        <form onSubmit={handleSubmit}>
          <Grid container spacing={1} alignItems="center" marginBottom={"15px"}>
            <Grid item>
              <AccountCircle />
            </Grid>
            <Grid item>
              <TextField
                label="Email / Username"
                type="email"
                required
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </Grid>
          </Grid>
          <Grid container spacing={1} alignItems="center">
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
          <Grid container justifyContent="center" style={{ marginTop: "2rem" }}>
            <Button type="submit" variant="contained" color="primary">
              Login
            </Button>
          </Grid>
        </form>
      </Container>
    </>
  );
};

export default LoginPage;

import React, {useState} from "react";
import {Button, TextField, Grid, Container, Typography} from "@mui/material";

export const WelcomeArea = () => {

    return (
        <>
            <Container
                style={{
                    height: "175px",
                    minWidth: "100%",

                    backgroundColor: "rgb(255,255,255)",

                    padding: 0

                }}>
                {/* Welcome */}
                <Container style={{
                    display: "flex",
                    flexDirection: "column",
                    justifyContent: "center",
                    alignItems: "center",
                    width: "50%",
                }}>
                    <h1 style={{
                        fontSize: "48px",
                        fontWeight: "100",
                        fontFamily: "Inter"

                    }}>Welcome Back</h1>
                    <h1 style={{
                        fontSize: "24px",
                        fontWeight: "bolder",
                        fontFamily: "Inter",
                        position: "relative",
                        top: "-40px"
                    }}>{"Nicholas"}</h1>
                </Container>

            </Container>

        </>
    );
};

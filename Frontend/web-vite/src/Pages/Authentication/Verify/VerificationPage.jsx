import React, {useEffect, useState} from "react";
import { Button, TextField, Grid, Container, Typography } from "@mui/material";
import NavigationBar from "../../../Components/General/NavigationBar/NavigationBar.jsx";
import { MailOutline } from "@mui/icons-material";
import {useNavigate, useParams} from "react-router-dom";
import {useUser} from "../../../Contexts/UserContext.jsx";

const VerificationPage = (props) => {
    const { token } = useParams();
    const {verified, setVerified} = useState(false);
    const {
        currentUser,
        isAuthenticated,
        loginUser,
        logoutUser,
        registerUser,
        verifyUser
    } = useUser();
    const navigate = useNavigate();
    useEffect(() => {
        fetch("http://localhost:8080/api/auth/verify", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                token: token
            })
        })
            .then(response => {
                if (!response.ok) {
                    setVerified(false);
                    alert("Code Denied")
                    throw new Error('Network response was not ok');
                }
                alert("Code Accepted")
                navigate("/")
                setVerified(true);
                return response.json();
            })
            .then(data => {
                console.log(data);
            })
            .catch(err => {
                alert("Code Denied")
                console.log(err);
            });
    }, []);


    return (
        <>
            <Container
                style={{ height: "100vh", display: "grid", placeItems: "center" }}
            >
                Token: {token}
                <br/>
                Verified: {verified}
            </Container>
        </>
    );
};

export default VerificationPage;

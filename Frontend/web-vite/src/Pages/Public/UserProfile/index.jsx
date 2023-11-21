import React, {useEffect} from 'react';
import NavigationBar from "@/Components/NavigationBar/NavigationBar.jsx";
import {FooterArea} from "@/Components/General/FooterArea/index.jsx";
import {MainArea} from "@/Components/General/MainArea.jsx";
import {Skeleton, Box, Typography, Grid, Button} from "@mui/material";
import {useParams} from "react-router-dom";

const UserProfile = () => {
    const params = useParams();
    const { userId } = params;

    useEffect(() => {
        // fetch user data
    }, [])

    return (
        <>
            <NavigationBar/>
            <MainArea>
                {/* First Section */}
                <Box style={{
                    width: '100vw',
                    height: '100vh',
                    padding: '20px',
                }}>

                    {userId}

                </Box>
            </MainArea>
            <FooterArea/>
        </>
    );
}

export default UserProfile;

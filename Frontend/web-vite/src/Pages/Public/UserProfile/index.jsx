import React from 'react';
import NavigationBar from "@/Components/General/NavigationBar/NavigationBar.jsx";
import {FooterArea} from "@/Components/General/FooterArea/index.jsx";
import {MainArea} from "@/Components/General/MainArea.jsx";
import {Skeleton, Box, Typography, Grid, Button} from "@mui/material";

const UserProfile = () => {
    return (
        <>
            <NavigationBar/>
            <MainArea>
                {/* First Section */}
                <Box style={{
                    width: '100vw',
                    padding: '20px',
                    height: 'max-content'
                }}>
                    
                </Box>
            </MainArea>
            <FooterArea/>
        </>
    );
}

export default UserProfile;

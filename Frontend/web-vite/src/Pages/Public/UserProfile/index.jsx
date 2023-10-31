import React from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Button from '@mui/material/Button';
import Avatar from '@mui/material/Avatar';
import Typography from '@mui/material/Typography';
import { Box } from '@mui/system';
import NavigationBar from "@/Components/General/NavigationBar/NavigationBar.jsx";
import {FooterArea} from "@/Components/General/FooterArea/index.jsx";

const UserProfile = () => {
    return (
        <>
        <NavigationBar/>
        <Box>

            {/* User Profile Section */}
            <Box display="flex" flexDirection="column" alignItems="center" mt={5}>
                <Avatar style={{ width: 100, height: 100 }} />
                <Typography variant="h4" style={{ marginTop: '1em' }}>TwentyCharactersOnly</Typography>
                <Box mt={2} display="flex" justifyContent="center">
                    <Box mx={2}>
                        <Typography variant="subtitle1">Friends</Typography>
                        <Typography variant="h6">0</Typography>
                    </Box>
                    <Box mx={2}>
                        <Typography variant="subtitle1">Following</Typography>
                        <Typography variant="h6">0</Typography>
                    </Box>
                    <Box mx={2}>
                        <Typography variant="subtitle1">Followers</Typography>
                        <Typography variant="h6">0</Typography>
                    </Box>
                </Box>
            </Box>

            {/* Personal Information Section */}
            <Box mt={5} display="flex" flexDirection="column" alignItems="center">
                <Typography variant="h5">Personal Information</Typography>
                {/* Add other personal information details here */}
            </Box>

            {/* Match History Section */}
            <Box mt={5} display="flex" flexDirection="column" alignItems="center">
                <Typography variant="h5">Match History</Typography>
                {/* Add match history details here */}
            </Box>
        </Box>
    <FooterArea/>
    </>
    );
}

export default UserProfile;

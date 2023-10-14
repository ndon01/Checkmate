import React from 'react';
import {Button} from '@mui/material';

import Container from '@mui/material/Container';
import Typography from '@mui/material/Typography';
import { Box } from '@mui/system';
import Styles from './LandingPage.module.css';
import NavigationBar from '../../../Components/General/NavigationBar/NavigationBar'
import { Link } from 'react-router-dom';

function LandingPage() {
  return (
    <>
    <NavigationBar />

    <Container maxWidth="lg" style={{height:"100vh", position:"relative", top: '64px'}}>
      <Box 
        display="flex" 
        flexDirection="column" 
        alignItems="center" 
        justifyContent="center" 
        height="100vh"
      >
        <Typography variant="h2" gutterBottom>
          Checkmate
        </Typography>
        <Box mb={2}>
          <Button to="/login" variant="outlined" fullWidth component={Link}>
            Sign in to an Existing Account
          </Button>
            <Button to="/register" variant="outlined" fullWidth component={Link}>
            Create an Account
          </Button>
        </Box>
      </Box>
    </Container>
    </>
  );
}

export default LandingPage;

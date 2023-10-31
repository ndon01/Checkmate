import React, { useState, useEffect } from 'react';
import { AppBar, Toolbar, Typography } from '@mui/material';
import {Box} from "@mui/system";
import Authenticated from "@/Components/General/NavigationBar/styles/Authenticated.jsx";
import Unauthenticated from "@/Components/General/NavigationBar/styles/Unauthenticated.jsx";

const NavigationBar = () => {
    const [showBar, setShowBar] = useState(true);
    const [lastScrollTop, setLastScrollTop] = useState(0);

    useEffect(() => {
        const handleScroll = () => {
            const currentScrollTop = window.pageYOffset;

            if (currentScrollTop <= 0) {
                setShowBar(true);
            } else if (currentScrollTop > lastScrollTop) {
                // Scrolled down
                setShowBar(false);
            } else {
                // Scrolled up
                setShowBar(true);
            }

            setLastScrollTop(currentScrollTop);
        };

        window.addEventListener('scroll', handleScroll);

        return () => {
            window.removeEventListener('scroll', handleScroll);
        };
    }, [lastScrollTop]);

    return (
        <>
        <AppBar position="fixed" style={{ transform: showBar ? 'translateY(0)' : 'translateY(-100%)', transition: 'transform 0.3s ease' }}>
            <Authenticated/>
        </AppBar>
        <Box style={{height: "64px"}} />
    </>
    );
};

export default NavigationBar;

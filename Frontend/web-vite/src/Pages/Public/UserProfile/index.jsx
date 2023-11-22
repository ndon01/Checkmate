import React, {useEffect} from 'react';
import NavigationBar from "@/Components/NavigationBar/NavigationBar.jsx";
import {FooterArea} from "@/Components/General/FooterArea/index.jsx";
import {MainArea} from "@/Components/General/MainArea.jsx";
import {Skeleton, Box, Typography, Grid, Button} from "@mui/material";
import {useParams} from "react-router-dom";


import styles from './UserProfile.module.css';

const UserProfile = () => {
    const params = useParams();
    const {userId} = params;

    const [userData, setUserData] = React.useState({
        username: 'nick',
        displayName: 'Nick',
        biography: '',

        friendCount: 0,
        followerCount: 0,
        followingCount: 0,

        isFriend: false,
        isFollowing: false,
        isFollower: false,
        isBlocked: false,
    });

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
                }}>

                    <div className={styles.LandingSection}>
                        <div className={styles.PFPnNames}>
                            <div className={styles.ProfilePictureContainer}>
                                <div className={styles.ProfilePictureContents}>
                                    <span style={{
                                        fontSize: '32px',
                                        fontFamily: 'Inter',
                                    }}>{userData.username[0].toUpperCase()}</span>
                                </div>
                            </div>
                            <div className={styles.NamesContainer}>
                               <span style={{
                                   fontSize: '100px',
                                   fontFamily: 'Inter',
                                   fontWeight: 'bold'
                               }}>{userData.displayName}</span>
                                <span style={{
                                    fontSize: '46px',
                                    fontFamily: 'Inter',
                                }}>@{userData.username}</span>
                            </div>
                        </div>

                        <div className={styles.StatsnBtns}>
                            <div>
                                stats
                            </div>
                            <div>
                                buttons
                            </div>
                        </div>

                    </div>


                </Box>
            </MainArea>
        </>
    );
}

export default UserProfile;

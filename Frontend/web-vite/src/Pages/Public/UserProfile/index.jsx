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
        displayName: 'Nicholas Donahue',
        biography: '',

        friendCount: 0,
        followingCount: 0,
        followerCount: 0,

        isFriend: false,
        isFriendPending: true,
        isFriendRequested: false,
        isFollowing: false, // user is following client
        isFollower: false, // client is following user

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

                    <div style={{
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                    }}>
                        <div style={{
                            marginTop: '25px'
                        }}>
                            <Skeleton variant="circular" width={150} height={150}/>
                        </div>
                        <div style={{
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                            marginTop: '25px'
                        }}>
                            <Typography variant="h4">
                                {userData.displayName}
                            </Typography>
                            <Typography variant="h6" style={{
                                color: 'gray',
                            }}>
                                @{userData.username}
                            </Typography>
                        </div>

                        <div style={{
                            display: 'flex',
                            flexDirection: 'row',
                            alignItems: 'center',
                            marginTop: '25px',
                            backgroundColor: 'lightgray',
                            borderRadius: '25px',
                            padding: '25px'
                        }}>
                            {/* Friends */}
                            <div style={{
                                display: 'flex',
                                flexDirection: 'column',
                                alignItems: 'center',
                                marginRight: '25px'
                            }}>
                                <div>
                                    <Typography variant="h6">
                                        {userData.friendCount}
                                    </Typography>
                                </div>
                                <div>
                                    <Typography variant="h5" fontWeight={"bolder"}>
                                        Friends
                                    </Typography>
                                </div>
                            </div>

                            {/* Following */}
                            <div style={{
                                display: 'flex',
                                flexDirection: 'column',
                                alignItems: 'center',
                            }}>
                                <div>
                                    <Typography variant="h6">
                                        {userData.friendCount}
                                    </Typography>
                                </div>
                                <div>
                                    <Typography variant="h5" fontWeight={"bolder"}>
                                        Following
                                    </Typography>
                                </div>
                            </div>

                            {/* Followers */}
                            <div style={{
                                display: 'flex',
                                flexDirection: 'column',
                                alignItems: 'center',
                                marginLeft: '25px'
                            }}>
                                <div>
                                    <Typography variant="h6">
                                        {userData.friendCount}
                                    </Typography>
                                </div>
                                <div>
                                    <Typography variant="h5" fontWeight={"bolder"}>
                                        Followers
                                    </Typography>
                                </div>
                            </div>
                        </div>

                        <div style={{
                            display: 'flex',
                            flexDirection: 'row',
                            alignItems: 'center',
                            marginTop: '25px',
                            backgroundColor: 'lightgray',
                            borderRadius: '25px',
                            padding: '25px'
                        }}>
                            {/* Friend Buttons */}
                            <div style={{
                                display: 'flex',
                                flexDirection: 'column',
                                alignItems: 'center',
                                marginRight: '25px'
                            }}>
                                { userData.isFriendPending && (
                                    <>
                                    <Button variant="contained" style={{
                                        backgroundColor: 'green',
                                    }}>
                                        Accept Friend Request
                                    </Button>

                                    <Button variant="contained" style={{
                                        backgroundColor: 'red',
                                        marginTop: '10px'
                                    }}>
                                        Deny Friend Request
                                    </Button>
                                    </>
                                )}

                                {userData.isFriendRequested && (
                                    <Button variant="contained" color="primary">
                                        Cancel Request
                                    </Button>
                                )}

                                {userData.isFriend && (
                                    <Button variant="contained" color="primary">
                                        Remove Friend
                                    </Button>
                                )}

                                {(!userData.isFriend && !userData.isFriendPending && !userData.isFriendRequested) && (
                                    <Button variant="contained" color="primary">
                                        Add Friend
                                    </Button>
                                )}

                            </div>

                            {/* Following Buttons */}
                            <div style={{
                                display: 'flex',
                                flexDirection: 'column',
                                alignItems: 'center',
                            }}>
                                {userData.isFollower ? (
                                    <Button variant="contained" color="primary">
                                        Unfollow
                                    </Button>
                                ) : (
                                    <Button variant="contained" color="primary">
                                        Follow
                                    </Button>
                                )}
                            </div>

                        </div>
                    </div>

                </Box>
            </MainArea>
        </>
    );
}

export default UserProfile;

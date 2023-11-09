import {Box} from "@mui/system";
import {Button, FormControl, Skeleton, TextField} from "@mui/material";
import React from "react";
import {Edit} from "@mui/icons-material";

export const ProfilePicture = () => {
    return (
        <>
            <Box style={{
                width: "100%",
                height: "100%",
                display: "flex",
                flexDirection: "column",
                justifyContent: "center",
                alignItems: "center"
            }}>

                <Box style={{
                    width: '100%',
                    height: 'max-content',
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'start',
                }}>

                    <Box style={{
                        display: "flex",
                        flexDirection: "column",
                        alignItems: "center",
                        justifyContent: "space-between",
                        width: '100%',
                    }}>

                        <Skeleton width={'100px'} height={'100px'} variant={'circular'}/>

                        <Button variant={'contained'} style={{
                            marginTop: '25px'
                        }}>Upload</Button>


                    </Box>

                </Box>


            </Box>
        </>
    )
}

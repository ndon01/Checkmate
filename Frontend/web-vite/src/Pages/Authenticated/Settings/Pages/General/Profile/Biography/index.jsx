import {Box} from "@mui/system";
import {Button, FormControl, Skeleton, TextField} from "@mui/material";
import React from "react";
import {Edit} from "@mui/icons-material";

export const Biography = () => {
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

                    <TextField style={{
                        width: '500px',
                    }} type={'text'} multiline fullWidth rows={5} value={'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec euismod, nisl eget aliquam ultricies, nunc nunc ultricies nunc, eget ultricies nunc nunc eget. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec euismod, nisl eget aliquam ultricies, nunc nunc ultricies nunc, eget ultricies nunc nunc eget.'} disabled/>

                </Box>


            </Box>
        </>
    )
}

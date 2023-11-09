import {Box} from "@mui/system";
import {FormControl, TextField} from "@mui/material";
import React from "react";
import {Edit} from "@mui/icons-material";

export const FullName = () => {
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
                        flexDirection: "row",
                        alignItems: "center",
                        justifyContent: "space-between",
                        width: '100%',
                    }}>



                        <FormControl fullWidth style={{marginBottom: '5px'}}>
                            <TextField
                                required
                                value={'Nicholas'}
                                disabled
                            />
                        </FormControl>


                        <Edit style={{
                            marginLeft: '15px'
                        }}/>

                    </Box>

                </Box>


            </Box>
        </>
    )
}

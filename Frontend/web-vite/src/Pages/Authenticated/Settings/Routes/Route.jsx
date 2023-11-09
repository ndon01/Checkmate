import {Typography} from "@mui/material";
import {Box} from "@mui/system";
import React from "react";
import {ArrowDownward, ArrowDownwardOutlined, ArrowRight, Circle, CircleOutlined} from "@mui/icons-material";

export const Route = ({ Title = '', element = null,  children = null}) => {
    const [open, setOpen] = React.useState(true);
    return (
        <>
            <Box style={{display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center'
            }}>
                <Box style={{
                    width: '100%',
                    height: 'max-content',
                    paddingBlock: '10px',
                    display: 'flex',
                    alignItems: 'start',
                    justifyContent: 'center',

                }}
                    onClick={() => setOpen(!open)  }
                >
                    {open ? <CircleOutlined/> : <Circle/>}
                    <Typography variant="h5" style={{
                        fontWeight: 'bold',
                        cursor: 'pointer',
                        color: open ? 'black' : 'gray',
                        width: '100%',
                        marginLeft: '10px',


                    }}>{Title}</Typography>
                </Box>
                <Box style={{
                    flexDirection: 'column',
                    alignItems: 'start',
                    justifyContent: 'center',
                    width: '100%',
                    paddingBlock: '10px',
                    display: open ? 'flex' : 'none',
                    paddingLeft: '40px',
                }}>
                    {children === null ? element : children}
                </Box>

            </Box>
        </>
    )
}

import {FooterArea} from "@/Components/General/FooterArea/index.jsx";
import React from "react";
import {Button} from "@mui/material";

export const MainArea = ({ children }) => {
    return (
        <>
            <div style={{top: '64px', position: 'relative', height: '100vh', width: '100vw'}} >

                {false ? (
                    <>
                        <div style={{
                            position: 'fixed',
                            width: "100vw",
                            height: "50px",
                            transition: 'transform 0.3s ease',
                            backgroundColor: 'rgb(225,225,225)',
                            zIndex: 98,
                            display: 'flex',
                            justifyContent: 'center',
                            alignItems: 'center'
                        }}>
                            Finish setting up your profile <Button style={{marginLeft: '5px'}} size='small' variant="contained">Here</Button>
                        </div>
                        <div style={{
                            width: "100vw",
                            height: "50px",
                            transition: 'transform 0.3s ease',
                            padding: '5px',
                            backgroundColor: 'white',
                            textAlign: "center",
                            zIndex: 98
                        }}/>
                    </>
                ) : null}


                {children}

                <FooterArea/>
            </div>
        </>
    )
}

import {MainArea} from "@/Components/General/MainArea.jsx";
import NavigationBar from "@/Components/General/NavigationBar/NavigationBar.jsx";
import {Box} from "@mui/system";
import {ArrowLeft, ArrowRight} from "@mui/icons-material";
import {PersonalInformation} from "@/Pages/Authenticated/Settings/Pages/General/PersonalInformation/index.jsx";
import {Profile} from "@/Pages/Authenticated/Settings/Pages/General/Profile/index.jsx";
import {Credentials} from "@/Pages/Authenticated/Settings/Pages/Authentication/Credentials/index.jsx";
import {TFA} from "@/Pages/Authenticated/Settings/Pages/Authentication/TFA/index.jsx";
import {Devices} from "@/Pages/Authenticated/Settings/Pages/Authentication/Devices/index.jsx";
import {Privacy} from "@/Pages/Authenticated/Settings/Pages/General/Privacy/index.jsx";
import {useState} from "react";
import {Routes} from "@/Pages/Authenticated/Settings/Routes/index.jsx";

export const Settings = () => {

    const [activePage, setActivePage] = useState(0)

    let settingsTree = {
        0: {
            title: 'General',
            content: {
                0: {
                    title: 'Personal Information',
                    component: PersonalInformation
                },
                1: {
                    title: 'Profile',
                    component: Profile
                },
                3: {
                    title: 'Privacy',
                    component: Privacy
                }
            }
        },
        1: {
            title: 'Authentication',
            content: {
                0: {
                    title: 'Credentials',
                    component: Credentials
                },
                1: {
                    title: 'Two Factor Authentication',
                    component: TFA
                },
                2: {
                    title: 'Devices',
                    component: Devices
                },

            }
        },
    }
    return (
        <>
            <NavigationBar/>
            <MainArea>

                <Box style={{
                    paddingBlock:'20px',
                    paddingRight: '20px'
                }}>
                <Box style={{
                    width: '100%',
                    height: 'max-content',

                }}>

                    <Box style={{
                        width: '100%',
                        minHeight: '500px',
                        borderRadius: '10px',
                        padding: '20px'

                    }}>
                        <Box style={{
                            width: '100%',
                            height: '50px',
                            borderTopLeftRadius: '10px',
                            borderTopRightRadius: '10px',
                            display: 'flex',
                            justifyContent: 'center',
                            alignItems: 'center',

                            fontFamily: 'Roboto',
                            fontWeight: 'light',
                        }}>
                            <h1>Settings</h1>
                        </Box>


                        <Routes/>

                    </Box>


                </Box>
                </Box>

            </MainArea>
        </>
    )
}


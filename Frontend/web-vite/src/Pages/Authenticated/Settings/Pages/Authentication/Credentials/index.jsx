import {Box} from "@mui/system";

export const Credentials = () => {
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
                        width: '100%',
                        height: 'max-content',
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'start',
                        paddingBlock: '10px',
                    }}>
                        <h2>Password</h2>

                    </Box>
                </Box>


            </Box>
        </>
    )
}
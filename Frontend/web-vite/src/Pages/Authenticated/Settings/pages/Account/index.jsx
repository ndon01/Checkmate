import {useUser} from "@/Contexts/UserContext.jsx";
import {Edit} from "@mui/icons-material";
import {IconButton} from "@mui/material";
import SettingsField from "@/Components/SettingsField.jsx";

export const Account = () => {

    const { currentUser } = useUser();

    return (
        <div style={{
            width: '100%',
        }}>
            {/* Title */}
            <div style={{
                width: '100%',
                display: 'flex',
                justifyContent: 'start'
            }}>
                <h2 style={{
                    fontSize: '36px',
                    fontWeight: 'bold',
                    marginBlock: '0px',
                    fontFamily: 'Inter',
                    padding: '25px',
                }}>Account Information</h2>
            </div>

            {/* Account Information */}

            <div style={{
                width: '100%',
                height: 'max-content',
            }}>
                <SettingsField label="Display Name" value={currentUser.displayName} />
                <SettingsField label="Username" value={currentUser.username} />
                <SettingsField label="Email Address" value={"*******@gmail.com"} />
                <SettingsField label="Password" value={"*************"} />
            </div>

            <div style={{
                width: '100%',
                display: 'flex',
                justifyContent: 'start'
            }}>
                <h2 style={{
                    fontSize: '36px',
                    fontWeight: 'bold',
                    marginBlock: '0px',
                    fontFamily: 'Inter',
                    padding: '25px',
                }}>Personal Information</h2>
            </div>

            <div style={{
                width: '100%',
                height: 'max-content',
                paddingBottom: '25px'
            }}>
                <SettingsField label="Birthday" value={"06-22-2003"} />
                <SettingsField label="Language" value={"English"} />
                <SettingsField label="Gender (Optional)" value={"Male"} />
                <SettingsField label="Region (Optional)" value={"NA"} />

            </div>

        </div>
    )
}

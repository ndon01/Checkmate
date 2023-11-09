import {PersonalInformation} from "@/Pages/Authenticated/Settings/Pages/General/PersonalInformation/index.jsx";
import {Profile} from "@/Pages/Authenticated/Settings/Pages/General/Profile/index.jsx";
import {Privacy} from "@/Pages/Authenticated/Settings/Pages/General/Privacy/index.jsx";
import {Credentials} from "@/Pages/Authenticated/Settings/Pages/Authentication/Credentials/index.jsx";
import {TFA} from "@/Pages/Authenticated/Settings/Pages/Authentication/TFA/index.jsx";
import {Devices} from "@/Pages/Authenticated/Settings/Pages/Authentication/Devices/index.jsx";
import {Route} from "@/Pages/Authenticated/Settings/Routes/Route.jsx";
import {Username} from "@/Pages/Authenticated/Settings/Pages/Authentication/Credentials/Username/index.jsx";
import {EmailAddress} from "@/Pages/Authenticated/Settings/Pages/Authentication/Credentials/EmailAddress/index.jsx";
import {Password} from "@/Pages/Authenticated/Settings/Pages/Authentication/Credentials/Password/index.jsx";
import {FullName} from "@/Pages/Authenticated/Settings/Pages/General/PersonalInformation/FullName/index.jsx";
import {DateOfBirth} from "@/Pages/Authenticated/Settings/Pages/General/PersonalInformation/DateOfBirth/index.jsx";
import {ProfilePicture} from "@/Pages/Authenticated/Settings/Pages/General/Profile/ProfilePicture/index.jsx";
import {Biography} from "@/Pages/Authenticated/Settings/Pages/General/Profile/Biography/index.jsx";

export const Routes = () => {

    return (
        <>
            <Route Title="General">
                <Route Title="Personal Information" >
                    <Route Title="Full Name" element={<FullName />} />
                    <Route Title="Date Of Birth" element={<DateOfBirth />} />

                </Route>
                <Route Title="Profile">
                    <Route Title="Profile Picture" element={<ProfilePicture />} />
                    <Route Title="Biography" element={<Biography />} />

                </Route>
                <Route Title="Privacy" element={<Privacy />} />
            </Route>

            <Route Title="Authentication">
                <Route Title="Credentials">
                    <Route Title="Username" element={<Username />} />
                    <Route Title="Email" element={<EmailAddress />} />
                    <Route Title="Password" element={<Password />} />

                </Route>
                <Route Title="Two Factor Authentication" element={<TFA />} />
                <Route Title="Devices" element={<Devices />} />
            </Route>

        </>
    )
}

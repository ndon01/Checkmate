import React from 'react'
import { Route, Routes } from 'react-router-dom'
import PageNotFound from '../Pages/Errors/PageNotFound.jsx'
import LandingPage from '../Pages/Public/LandingPage/LandingPage.jsx'
import LoginPage from '../Pages/Authentication/Login/LoginPage.jsx'
import RegistrationPage from '../Pages/Authentication/Registration/RegistrationPage.jsx'
import VerificationPage from "../Pages/Authentication/Verify/VerificationPage.jsx";
import {Dashboard} from "../Pages/Authenticated/Dashboard";
import {Match} from "@/Pages/Match/index.jsx";
import UserProfile from "@/Pages/Public/UserProfile/index.jsx";
import { useUser } from "@/Contexts/UserContext.jsx";
import loginPage from "../Pages/Authentication/Login/LoginPage.jsx";  // import the hook

function RouteComponent() {
    const { isAuthenticated } = useUser(); // get the authentication status

    return (
        <Routes basename="/">
            {/* Use conditional rendering for the root route */}
            {isAuthenticated ? (
                <>
                    <Route path='/' element={<Dashboard/>} />

                    <Route path='/dashboard' element={<Dashboard/>} />

                    <Route path='/login' element={<Dashboard />} />
                    <Route path='/register' element={<Dashboard />} />
                </>

            ) : (
                <>
                    <Route path='/' element={<LandingPage/>} />

                    <Route path='/dashboard' element={<LandingPage/>} />

                    <Route path='/login' element={<LoginPage />} />
                    <Route path='/register' element={<RegistrationPage />} />
                </>
            )}
            {/* Authentication */}
            <Route path='/verify/:token' element={<VerificationPage />} />

            {/* Authenticated */}


            {/* Match */}
            <Route path='/match' element={<Match />} />
            <Route path='/profile' element={<UserProfile />} />

            {/* Errors */}
            <Route path='*' element={<PageNotFound />} />
        </Routes>
    )
}

export default RouteComponent;

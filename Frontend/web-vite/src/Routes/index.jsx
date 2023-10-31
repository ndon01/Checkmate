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

function RouteComponent() {
  return (
    <Routes basename="/">
        <Route path='/' Component={LandingPage} />

        {/* Authentication */}
        <Route path='/login' Component={LoginPage} />
        <Route path='/register' Component={RegistrationPage} />
        <Route path='/verify/:token' Component={VerificationPage} />

        {/* Authenticated */}
        <Route path='/dashboard' Component={Dashboard} />

        {/* Match */}
        <Route path='/match' Component={Match} />
        <Route path='/profile' Component={UserProfile} />
        {/* Errors */}
        <Route path='*' Component={PageNotFound} />
    </Routes>
  )
}

export default RouteComponent
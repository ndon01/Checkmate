import React from 'react'
import { Route, Routes } from 'react-router-dom'
import PageNotFound from '../Pages/Errors/PageNotFound'
import LandingPage from '../Pages/Public/LandingPage/LandingPage'
import LoginPage from '../Pages/Authentication/Login/LoginPage'
import RegistrationPage from '../Pages/Authentication/Registration/RegistrationPage'

function RouteComponent() {
  return (
    <Routes basename="/">
        <Route path='/' Component={LandingPage} />

        {/* Authentication */}
        <Route path='/login' Component={LoginPage} />
        <Route path='/register' Component={RegistrationPage} />

        {/* Errors */}
        <Route path='*' Component={PageNotFound} />
    </Routes>
  )
}

export default RouteComponent
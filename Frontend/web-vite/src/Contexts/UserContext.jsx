import React, {createContext, useContext, useEffect, useState} from 'react';
import {useNavigate} from "react-router-dom";
import {useAlertContext} from "@/Contexts/AlertContext/AlertContext.jsx";

// Create the context
const UserContext = createContext();

export const useUser = () => {
    return useContext(UserContext);
};

export const UserProvider = ({children}) => {
    const [currentUser, setCurrentUser] = useState(null); // Store the current logged-in user
    const [isAuthenticated, setIsAuthenticated] = useState(false); // Store the authentication state
    const {createAlert} = useAlertContext();
    const navigate = useNavigate();

    const checkContextValidity = async () => {
        const storedRefreshToken = localStorage.getItem('refresh_token');
        const storedAccessToken = localStorage.getItem('access_token');

        const lastRefreshTime = localStorage.getItem("lastRefresh");
        const lastAccessTime = localStorage.getItem("lastAccess");

        let hasRefreshToken = storedRefreshToken != null;
        let hasRefreshTokenDate = lastRefreshTime != null;
        let useRefreshToken = false;

        let hasAccessToken = storedAccessToken != null;

        if (hasRefreshToken) {
            if (hasRefreshTokenDate) {
                let now = new Date();
                let lastRefresh = new Date(lastRefreshTime);
                let diff = now.getTime() - lastRefresh.getTime();
                let minutes = Math.floor(diff / 60000);
                let days = Math.floor(minutes / 1440);

                if (days >= 13 || !hasAccessToken) {
                    useRefreshToken = true;
                }
            } else {
                useRefreshToken = true;
            }
        }

        if (useRefreshToken) {
            const refreshResponse = await fetch("http://localhost:8080/api/auth/refresh", {
                method: "GET",
                headers: {
                    'Authorization': 'Refresh ' + storedRefreshToken
                }
            });

            if (!refreshResponse.ok) {
                logoutUser();
                hasAccessToken = false;
                hasRefreshToken = false;
            } else {
                const responseData = await refreshResponse.json();
                localStorage.setItem('refresh_token', responseData.refreshToken);
                localStorage.setItem('access_token', responseData.accessToken);
                localStorage.setItem("lastRefresh", new Date().getTime());
                localStorage.setItem("lastAccess", new Date().getTime());
            }
        }



        if (hasRefreshToken && hasAccessToken) {
            const storedUserContext = localStorage.getItem('context');
            const lastContextTime = localStorage.getItem("lastContext");
            let hasUserContext = storedUserContext != null;
            let getUserContext = false;

            if (hasUserContext) {
                if (lastContextTime == null) {
                    getUserContext = true;
                } else {
                    let now = new Date();
                    let lastContext = new Date(lastContextTime);
                    let diff = now.getTime() - lastContext.getTime();
                    let minutes = Math.floor(diff / 60000);

                    if (minutes >= 5) {
                        getUserContext = true;
                    }
                }

            } else {
                getUserContext = true;
            }

            if (getUserContext) {
                const contextResponse = await fetch("http://localhost:8080/api/users/getUserContext", {
                    method: "GET",
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem('access_token')
                    }
                });

                if (!contextResponse.ok) {
                    logoutUser();
                    createAlert("Session Error, Please Log In Again", "error");
                } else {
                    const responseData = await contextResponse.json();
                    localStorage.setItem('context', JSON.stringify(responseData));
                    localStorage.setItem("lastContext", new Date().getTime());
                    setCurrentUser(responseData);
                    setIsAuthenticated(true);
                }
            } else {
                setCurrentUser(JSON.parse(storedUserContext));
                setIsAuthenticated(true);
            }
        }
    };

    useEffect(() => {
        // Immediately invoke the async function
        checkContextValidity();
    }, []); // Empty dependency array ensures this useEffect runs once, similar to componentDidMount


    const loginUser = (refresh, access) => {
        localStorage.setItem("refresh_token", refresh)
        localStorage.setItem("access_token", access)
        localStorage.setItem("lastRefresh", new Date().getTime());
        localStorage.setItem("lastAccess", new Date().getTime());
        checkContextValidity();
    };

    const logoutUser = () => {
        localStorage.removeItem('refresh_token');
        localStorage.removeItem('access_token');
        localStorage.removeItem('context');
        setCurrentUser(null);
        setIsAuthenticated(false);
        createAlert("Successfully Logged Out")
    };

    const value = {
        currentUser,
        isAuthenticated,
        loginUser,
        logoutUser
    };

    return (
        <UserContext.Provider value={value}>
            {children}
        </UserContext.Provider>
    );
};

export default UserProvider;

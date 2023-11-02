import React, {createContext, useContext, useEffect, useState} from 'react';
import { useNavigate } from "react-router-dom";

// Create the context
const UserContext = createContext();

export const useUser = () => {
  return useContext(UserContext);
};

export const UserProvider = ({ children }) => {
  const [currentUser, setCurrentUser] = useState(null); // Store the current logged-in user
  const [isAuthenticated, setIsAuthenticated] = useState(false); // Store the authentication state

  const navigate = useNavigate();

  useEffect(() => {
    // Check localStorage for tokens and user context on initial load
    const storedRefreshToken = localStorage.getItem('refresh_token');
    const storedAccessToken = localStorage.getItem('access_token');
    const storedContext = localStorage.getItem('context');

    if (storedRefreshToken && storedAccessToken && storedContext) {
      setCurrentUser(storedContext);
      setIsAuthenticated(true);
    }
  }, []); // Empty dependency array ensures this useEffect runs once, similar to componentDidMount


  const sendRequest = async (url, method = 'GET', body = null, headers = {}) => {
    let tries = 2; // One try for the original request, another for after refreshing the token

    while (tries--) {
      const accessToken = localStorage.getItem('access_token');
      if (accessToken) {
        headers['Authorization'] = `Bearer ${accessToken}`;
      }

      const response = await fetch(url, {
        method,
        body,
        headers: {
          'Content-Type': 'application/json',
          ...headers
        }
      });

      if (response.status !== 401 || !tries) {
        return response;
      }

      const refreshToken = localStorage.getItem('refresh_token');
      if (!refreshToken) {
        return response;
      }

      const refreshResponse = await fetch("http://localhost:8080/api/auth/refresh", {
        method: "POST",
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ refresh_token: refreshToken })
      });

      if (!refreshResponse.ok) {
        localStorage.removeItem('refresh_token');
        localStorage.removeItem('access_token');
        setIsAuthenticated(false);
        setCurrentUser(null);
        return refreshResponse;
      }

      const responseData = await refreshResponse.json();
      localStorage.setItem('access_token', responseData.accessToken);
    }
  };

  const loginUser = (refresh, access, context) => {
    localStorage.setItem("refresh_token", refresh)
    localStorage.setItem("access_token", access)
    localStorage.setItem("context", context)

    setCurrentUser(context);
    setIsAuthenticated(true);
  };

  const logoutUser = () => {
    localStorage.removeItem('refresh_token');
    localStorage.removeItem('access_token');
    localStorage.removeItem('context');
    setCurrentUser(null);
    setIsAuthenticated(false);
  };

  const value = {
    currentUser,
    isAuthenticated,
    loginUser,
    logoutUser,
    sendRequest
  };

  return (
      <UserContext.Provider value={value}>
        {children}
      </UserContext.Provider>
  );
};

export default UserProvider;

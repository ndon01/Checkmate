import React, { createContext, useContext, useState } from 'react';
import {useNavigate} from "react-router-dom";

// Create the context
const UserContext = createContext();

export const useUser = () => {
  return useContext(UserContext);
};

export const UserProvider = ({ children }) => {
  const [currentUser, setCurrentUser] = useState(null); // Store the current logged-in user
  const [isAuthenticated, setIsAuthenticated] = useState(false); // Store the authentication state

  const navigate = useNavigate();

  const registerUser = async (userData) => {
    try {
      const response = await fetch("http://localhost:8080/api/auth/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(userData)
      });

      const data = await response.json();
      if (!response.ok) {
        throw new Error(data.message || 'Registration failed.');
      }

      navigate('/verify')

      return data;
    } catch (error) {
      console.error("Registration error:", error);
      throw error;
    }
  };

  const verifyUser = async (verificationToken) => {
    try {
      const response = await fetch("http://localhost:8080/api/auth/verify", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ token: verificationToken })
      });

      const data = await response.json();
      if (!response.ok) {
        throw new Error(data.message || 'Verification failed.');
      }

      // Update the user state if needed, based on the verification response
      // e.g., loginUser(data);

      return data;
    } catch (error) {
      console.error("Verification error:", error);
      throw error;
    }
  };

  const loginUser = (userData) => {
    setCurrentUser(userData);
    setIsAuthenticated(true);
  };

  
  const logoutUser = () => {
    setCurrentUser(null);
    setIsAuthenticated(false);
  };

  const value = {
    currentUser,
    isAuthenticated,
    loginUser,
    logoutUser,
    registerUser,
    verifyUser
  };

  return (
    <UserContext.Provider value={value}>
      {children}
    </UserContext.Provider>
  );
};

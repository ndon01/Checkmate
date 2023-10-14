import React, { createContext, useContext, useState } from 'react';

// Create the context
const UserContext = createContext();

export const useUser = () => {
  return useContext(UserContext);
};

export const UserProvider = ({ children }) => {
  const [currentUser, setCurrentUser] = useState(null); // Store the current logged-in user
  const [isAuthenticated, setIsAuthenticated] = useState(false); // Store the authentication state

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
    logoutUser
  };

  return (
    <UserContext.Provider value={value}>
      {children}
    </UserContext.Provider>
  );
};

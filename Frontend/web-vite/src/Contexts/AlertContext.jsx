import React, { createContext, useContext, useState, useEffect } from 'react';
import { Alert, IconButton } from "@mui/material";
import CloseIcon from '@mui/icons-material/Close';

// Create the context
const AlertContext = createContext();

export const useAlertContext = () => {
  return useContext(AlertContext);
};

export const AlertProvider = ({ children }) => {
  const [alerts, setAlerts] = useState([]);

  const removeAlert = (id) => {
    setAlerts((prevAlerts) => prevAlerts.filter(alert => alert.id !== id));
  }

  const createAlert = (message, severity = 'success', duration = 5000) => {
    const id = new Date().getTime(); // Simple unique id
    setAlerts((prevAlerts) => [...prevAlerts, { id, message, severity }]);

    // Automatically remove the alert after 'duration' milliseconds
    setTimeout(() => {
      removeAlert(id);
    }, duration);
  }

  const value = {
    createAlert
  };

  return (
      <AlertContext.Provider value={value}>
        <div style={{
          position: "absolute",
          width: "100vw",
          height: "100vh"
        }}>
          <div style={{
            position: "fixed",
            top: "64px",
            zIndex: 10,
            width: "100vw"
          }}>
            {alerts.map(alert => (
                <div key={alert.id} style={{ marginBottom: "10px" }}>
                  <Alert
                      variant="outlined"
                      severity={alert.severity}
                      style={{backgroundColor: "white"}}
                  >
                    {alert.message}
                  </Alert>
                </div>
            ))}
          </div>
        </div>
        <div>
        {children}
        </div>
      </AlertContext.Provider>
  );
};

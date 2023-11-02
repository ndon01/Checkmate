import React, {useEffect, useState} from 'react';
import {
  TextField,
  Box,
  LinearProgress,
  Button,
  FormControl,
  MenuItem,
  InputLabel,
  Select,
  FormHelperText
} from '@mui/material';
import NavigationBar from '../../../Components/General/NavigationBar/NavigationBar.jsx';
import { FooterArea } from '@/Components/General/FooterArea/index.jsx';
import {Link, useNavigate} from "react-router-dom";
import {useAlertContext} from "@/Contexts/AlertContext.jsx";
import {useUser} from "@/Contexts/UserContext.jsx";

const LoginPage = () => {

  const {createAlert} = useAlertContext();

  const {
      currentUser,
      isAuthenticated,
      loginUser,
      logoutUser,
      registerUser,
      verifyUser
  } = useUser();

  const navigate = useNavigate();


  const [formValues, setFormValues] = useState({
    identifier: '',
    password: '',

  });

  const [formErrors, setFormErrors] = useState({
    identifier: '',
    password: '',
  });

  const handleInputChange = (field) => (event) => {
    setFormValues(prevValues => ({
      ...prevValues,
      [field]: event.target.value,
    }));
  };

  const handleSubmit = async () => {
      console.log("Submitting login")
      // New form errors object
      const errors = {};

      let errored = false;
      // Basic validations
      for (const field in formValues) {
        console.log(field, formValues[field])
        if (formValues[field] === '') {
          errored = true;
          errors[field] = 'This field is required';
        }
      }

      // Updating errors
      setFormErrors(errors);

      // If there are no errors, you can send the form data to the server or further process
      if (!errored) {
        console.log("No errors, submitting the form!");
        try {
          const response = await fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            headers: {
              "Content-Type": "application/json"
            },
            body: JSON.stringify(formValues)
          });


          const responseData = await response.json();

          if (!response.ok) {
            if (responseData.failure) {
              let keys = Object.keys(responseData.fields)
              let values = Object.values(responseData.fields)

              for (let i = 0; i < keys.length; i++) {
                setInputError(keys[i], values[i])
              }
            }


          } else {
            navigate("/dashboard")
            createAlert("Login Successful")
            loginUser(responseData.refreshToken, responseData.accessToken, {})
          }

          // Handle the response data as needed
          // e.g., navigate the user to a dashboard or show a success message
          console.log(responseData);


        } catch (error) {
          console.error(error)
        }
      }
    }

    const showError = (field) => formErrors[field] && formErrors[field] !== '';

    return (
        <>
          <NavigationBar/>
          <Box
              style={{
                width: '100vw',
                height: 'max-content',
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                paddingBlock: '150px',
              }}
          >

            <Box
                style={{
                  width: '500px',
                  minHeight: 'max-content',
                  borderRadius: '10px',
                  boxShadow: '1px 1px 4px 0px black',
                  backgroundColor: 'white',
                  padding: '20px',
                  display: 'flex',
                  flexDirection: 'column',
                }}
            >

              <Box style={{
                width: "100%",
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                marginBottom: '20px'
              }}>
                <h1 style={{
                  fontSize: "24px",
                  fontWeight: "400",
                  fontFamily: "Inter"
                }}>Account Authentication</h1>
              </Box>

              <FormControl fullWidth style={{marginBottom: '5px'}}>
                <TextField
                    required
                    label="Username or Email"
                    value={formValues.identifier}
                    onChange={handleInputChange("identifier")}
                    helperText={formErrors.identifier}
                    error={showError("identifier")}
                />
              </FormControl>

              <FormControl fullWidth style={{marginBlock: '5px'}}>
                <TextField
                    required
                    label="Password"
                    type="password"
                    value={formValues.password}
                    onChange={handleInputChange("password")}
                    helperText={formErrors.password}
                    error={showError("password")}
                />
              </FormControl>

              <Button variant="contained" color="primary" onClick={handleSubmit} style={{marginTop: '20px'}}>
                Login
              </Button>

              <Button variant="outlined" color="primary" style={{marginTop: '20px'}} component={Link} to='/register'>
                Don't have an Account yet?
              </Button>
            </Box>
          </Box>
          <FooterArea/>
        </>
    );
  };

export default LoginPage;

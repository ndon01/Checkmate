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
import {Link} from "react-router-dom";

const LoginPage = () => {
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

  const handleSubmit = () => {
    // New form errors object
    const errors = {};

    // Basic validations
    for (const field in formValues) {
      if (formValues[field] === '') {
        errors[field] = 'This field is required';
      }
    }

    if (formValues.emailAddress !== '') {
      // Email format validation
      const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
      if (!emailPattern.test(formValues.emailAddress)) {
        errors.emailAddress = 'Invalid email format';
      }
    }

    // Date validation: Ensure age is at least 13 years old
    if (formValues.month && formValues.day && formValues.year) {
      const selectedDate = new Date(formValues.year, months.indexOf(formValues.month), formValues.day);
      const currentDate = new Date();
      let ageDiff = currentDate.getFullYear() - selectedDate.getFullYear();
      const monthDiff = currentDate.getMonth() - selectedDate.getMonth();
      const dayDiff = currentDate.getDate() - selectedDate.getDate();

      if (monthDiff < 0 || (monthDiff === 0 && dayDiff < 0)) {
        ageDiff--;
      }

      if (ageDiff < 13) {
        errors.year = 'You must be at least 13 years old to register';
      } else {
        errors.year = ''
      }
    }

    // Passwords matching validation
    if (formValues.password !== formValues.confirmPassword) {
      errors.confirmPassword = 'Passwords do not match';
    }

    // Updating errors
    setFormErrors(errors);

    // If there are no errors, you can send the form data to the server or further process
    if (Object.values(errors).every((val) => val === "")) {
      console.log("No errors, submitting the form!");

      // TODO: Send the formValues to the server or further processing
    }
  };


  const [daysInMonth, setDaysInMonth] = useState(31);

  const generateYears = (start, end) => {
    return Array.from({ length: end - start + 1 }, (_, idx) => start + idx);
  };

  const isLeapYear = (year) => {
    return (year % 4 === 0 && year % 100 !== 0) || year % 400 === 0;
  };

  const currentYear = new Date().getFullYear();
  const years = generateYears(1900, currentYear);

  useEffect(() => {
    if (formValues.month === "February") {
      setDaysInMonth(isLeapYear(formValues.year) ? 29 : 28);
    } else if (["April", "June", "September", "November"].includes(formValues.month)) {
      setDaysInMonth(30);
    } else {
      setDaysInMonth(31);
    }
  }, [formValues.month, formValues.year]);

  const showError = (field) => formErrors[field] && formErrors[field] !== '';

  const months = [
    "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
  ];


  return (
      <>
        <NavigationBar />
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
        <FooterArea />
      </>
  );
};

export default LoginPage;

// SearchComponent.js
import React, {useState} from 'react';
import {
    TextField,
    Button,
    Container,
    FormControl,
    InputLabel,
    Select,
    MenuItem,
    CircularProgress,
    List, ListItem
} from '@mui/material';
import {MainArea} from "@/Components/General/MainArea.jsx";
import NavigationBar from "@/Components/NavigationBar/NavigationBar.jsx";
import axios from "axios";
import {useAlertContext} from "@/Contexts/AlertContext/AlertContext.jsx";
import {Block, Fullscreen, PersonAdd} from "@mui/icons-material";

export default function SearchComponent() {
    const [specifier, setSpecifier] = useState('users');
    const [query, setQuery] = useState('');
    const [loading, setLoading] = useState(false);
    const [results, setResults] = useState([]);
    const [error, setError] = useState('');

    const {createAlert} = useAlertContext();
    const handleSpecifierChange = (event) => {
        setSpecifier(event.target.value);
    };

    const handleQueryChange = (event) => {
        setQuery(event.target.value);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        setLoading(true);
        setError('');
        try {
            // Replace '/your-api-endpoint' with the actual endpoint provided by your backend
            const response = await fetch(`http://localhost:8080/api/users/user-search?searchQuery=${encodeURIComponent(query)}`, {
                method: "GET"
            }).then(function(response) {
                // The response is a Response instance.
                // You parse the data into a useable format using `.json()`
                return response.json();
            }).then(function(data) {
                // `data` is the parsed version of the JSON returned from the above endpoint.
                console.log(data);  // { "userId": 1, "id": 1, "title": "...", "body": "..." }
                setResults(data)
            });
        } catch (err) {
            console.error('Failed to fetch search results:', err);
            setError('Failed to fetch search results');
            setResults([]); // Clear any previous results
        } finally {
            setLoading(false);
        }
    };

    return (
        <>
            <NavigationBar/>

            <MainArea>
                <div style={{minWidth: '100%', minHeight: '750px', display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
                        <form onSubmit={handleSubmit} style={{marginTop: '2rem'}}>
                            <FormControl fullWidth margin="normal">
                                <InputLabel id="specifier-label">Search In</InputLabel>
                                <Select
                                    labelId="specifier-label"
                                    id="specifier-select"
                                    value={specifier}
                                    label="Search In"
                                    onChange={handleSpecifierChange}
                                >
                                    <MenuItem value="users">Users</MenuItem>
                                    <MenuItem value="matches">Matches</MenuItem>
                                </Select>
                            </FormControl>
                            <TextField
                                fullWidth
                                id="search-query"
                                label="Search Query"
                                margin="normal"
                                value={query}
                                onChange={handleQueryChange}
                            />
                            <Button type="submit" variant="contained" color="primary" fullWidth disabled={loading}>
                                {loading ? <CircularProgress size={24} /> : 'Search'}
                            </Button>
                        </form>
                        {loading && (
                            <CircularProgress />
                        )}
                        {!loading && error && (
                            <p>Error: {error}</p>
                        )}
                        {!loading && results.length > 0 && (
                            <List>
                                {results.map((result, index) => (
                                    <ListItem key={index}>
                                        {result.username} (ID: {result.userId})
                                    </ListItem>
                                ))}
                            </List>
                        )}
                        <div style={{
                            padding: '0',
                            display: 'flex',
                            justifyContent: 'center',
                            minWidth: '100%',
                            marginTop: '25px'
                        }}>

                                    <div style={{
                                        width: '90%',
                                        minHeight: 'max-content',
                                        padding: '10px',

                                        borderRadius: '10px',
                                        backgroundColor: 'white',

                                        display: 'flex',
                                        justifyContent: 'start',
                                        alignItems: 'center',

                                        boxShadow: '0px 0px, 0px 0px 8px rgb(200,200,200)'
                                    }}>
                                        <div style={{
                                            width: '100%',
                                            display: 'flex',
                                            justifyContent: 'space-between'
                                        }}>
                                            <div style={{
                                                display: 'flex',
                                                flexDirection: 'row'
                                            }}>
                                                <div style={{
                                                    width: '80px',
                                                    height: '80px',
                                                    borderRadius: '100%',
                                                    backgroundColor: 'white',

                                                    display: 'flex',
                                                    justifyContent: 'center',
                                                    alignItems: 'center',
                                                    border: '1px solid black'

                                                }}>

                                                    <span style={{
                                                        fontSize: '32px'
                                                    }}>N</span>

                                                </div>
                                                <div style={{
                                                    display: 'flex',
                                                    flexDirection: 'column',
                                                    marginLeft: '10px',
                                                    justifyContent: 'space-between'
                                                }}>
                                                    <span style={{
                                                        fontSize: '40px',
                                                        fontWeight: 'bolder',
                                                        fontFamily: 'inter'
                                                    }}>Nicholas</span>

                                                    <span style={{
                                                        fontSize: '25px',
                                                        fontFamily: 'inter',
                                                        fontWeight: 'normal',
                                                    }}>Nick</span>
                                                </div>
                                            </div>
                                        </div>

                                        <div style={{
                                            minWidth: 'max-content'
                                        }}>
                                            <div style={{
                                                minWidth: '110px',
                                                padding: '7px',

                                                display: 'flex',
                                                justifyContent: 'center',
                                                alignItems: 'center',

                                                backgroundColor: 'white',
                                                borderRadius: '10px',
                                                border: '1px solid black'
                                            }}>
                                                <Fullscreen/>
                                                <span style={{marginLeft: '10px', fontFamily: 'inter'}}>View Profile</span>
                                            </div>
                                            <div style={{
                                                minWidth: '110px',
                                                padding: '7px',
                                                marginTop: '5px',

                                                display: 'flex',
                                                justifyContent: 'center',
                                                alignItems: 'center',

                                                backgroundColor: 'white',
                                                borderRadius: '10px',
                                                border: '1px solid black'
                                            }}>
                                                <PersonAdd/>
                                                <span style={{marginLeft: '10px', fontFamily: 'inter'}}>Add Friend</span>
                                            </div>

                                            <div style={{
                                                minWidth: '110px',
                                                padding: '7px',
                                                marginTop: '5px',

                                                display: 'flex',
                                                justifyContent: 'center',
                                                alignItems: 'center',

                                                backgroundColor: 'white',
                                                borderRadius: '10px',
                                                border: '1px solid black'
                                            }}>
                                                <Block/>
                                                <span style={{marginLeft: '10px', fontFamily: 'inter'}}>Block User</span>
                                            </div>
                                        </div>

                                    </div>

                    </div>
                </div>
            </MainArea>
        </>
    );
}

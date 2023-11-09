// SearchComponent.js
import React from 'react';
import {TextField, Button, Container, FormControl, InputLabel, Select, MenuItem} from '@mui/material';
import {MainArea} from "@/Components/General/MainArea.jsx";
import NavigationBar from "@/Components/General/NavigationBar/NavigationBar.jsx";

export default function SearchComponent() {
    const [specifier, setSpecifier] = React.useState('users');
    const [query, setQuery] = React.useState('');

    const handleSpecifierChange = (event) => {
        setSpecifier(event.target.value);
    };

    const handleQueryChange = (event) => {
        setQuery(event.target.value);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        // Here you would typically send the query to your backend service or search handler
        console.log(`Searching for ${specifier} with query: ${query}`);
    };

    return (
        <>
            <NavigationBar/>

            <MainArea>
                <div style={{minHeight: '750px', display: 'flex', justifyContent: 'center', alignItems: ''}}>
                    <Container maxWidth="sm">
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
                            <Button type="submit" variant="contained" color="primary" fullWidth>
                                Search
                            </Button>
                        </form>
                    </Container>
                </div>
            </MainArea>
        </>
    );
}

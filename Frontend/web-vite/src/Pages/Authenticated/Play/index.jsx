import React, { useState } from 'react';
import { Button, Radio, RadioGroup, FormControlLabel, FormControl, FormLabel } from '@mui/material';

function Play() {
    const [selectedType, setSelectedType] = useState('');

    const handleQueue = () => {
        if (selectedType) {
            // Logic to enter the queue
            console.log(`Entering the queue for ${selectedType} chess.`);
        } else {
            alert("Please select a matchmaking type first.");
        }
    };

    const handlePlay = () => {
        if (selectedType) {
            // Logic to play immediately
            console.log(`Playing immediately in ${selectedType} chess.`);
        } else {
            alert("Please select a matchmaking type first.");
        }
    };

    return (
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', padding: 20 }}>
            <h1>Chess Matchmaking</h1>
            <FormControl component="fieldset">
                <FormLabel component="legend">Match Type</FormLabel>
                <RadioGroup
                    aria-label="match-type"
                    name="match-type"
                    value={selectedType}
                    onChange={(e) => setSelectedType(e.target.value)}
                >
                    <FormControlLabel value="Standard" control={<Radio />} label="Standard" />
                    <FormControlLabel value="Blitz" control={<Radio />} label="Blitz" />
                    <FormControlLabel value="Bullet" control={<Radio />} label="Bullet" />
                </RadioGroup>
            </FormControl>
            <Button variant="contained" color="primary" style={{ margin: 10 }} onClick={handleQueue}>
                Enter Queue
            </Button>
            <Button variant="contained" color="secondary" style={{ margin: 10 }} onClick={handlePlay}>
                Play Now
            </Button>
        </div>
    );
}

export default Play;

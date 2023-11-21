import React from 'react';
import { IconButton } from "@mui/material";
import { Edit } from "@mui/icons-material";

const SettingsField = ({ label, value }) => {
    return (
        <div style={{
            paddingInline: '25px',
            display: 'flex',
            justifyContent: 'start',
            alignItems: 'center'
        }}>
            <span style={{ fontFamily: "Inter", fontSize: '24px' }}>
                {label}: <span style={{ fontWeight: 'bold' }}>{value}</span>
            </span>
            <IconButton style={{ marginLeft: '0px' }}>
                <Edit />
            </IconButton>
        </div>
    );
};

export default SettingsField;

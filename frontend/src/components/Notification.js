import React from "react";
import { Snackbar, Alert } from "@mui/material";

export default function Notification({ message, open, severity, onClose }) {
    if (!message) return null;
    return (
        <Snackbar open={open} autoHideDuration={3000} onClose={onClose} anchorOrigin={{ vertical: 'top', horizontal: 'center' }}>
            <Alert onClose={onClose} severity={severity} sx={{ width: '100%' }}>
                {message}
            </Alert>
        </Snackbar>
    );
}

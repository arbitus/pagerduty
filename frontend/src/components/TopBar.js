import React from "react";
import { AppBar, Toolbar, Typography, Button, Box } from "@mui/material";
import SyncIcon from '@mui/icons-material/Sync';

export default function TopBar({ onSync, loading }) {
    return (
        <AppBar position="fixed" sx={{ zIndex: 1201, bgcolor: "#00C853" }}>
            <Toolbar>
                <Typography variant="h6" noWrap component="div">
                    Integración PagerDuty
                </Typography>
                <Box sx={{ flexGrow: 1 }} />
                <Button
                    variant="contained"
                    color="inherit"
                    startIcon={<SyncIcon />}
                    onClick={onSync}
                    sx={{ bgcolor: "#00C853", color: "#fff", fontWeight: "bold", boxShadow: 2 }}
                    disabled={loading}
                >
                    Sincronizar
                </Button>
            </Toolbar>
        </AppBar>
    );
}

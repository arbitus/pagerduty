import { useEffect } from "react";
// Cargar servicios al iniciar la app

import * as React from "react";
import { useState } from "react";
import { TextField, IconButton } from "@mui/material";
import SearchIcon from '@mui/icons-material/Search';
import { AppBar, Toolbar, Typography, CssBaseline, Drawer, List, ListItem, ListItemIcon, ListItemText, Button, Box, Card, CardContent, CircularProgress } from "@mui/material";
import SyncIcon from '@mui/icons-material/Sync';
import ListAltIcon from '@mui/icons-material/ListAlt';
import GroupIcon from '@mui/icons-material/Group';
import PolicyIcon from '@mui/icons-material/Policy';
import { DataGrid } from '@mui/x-data-grid';

const drawerWidth = 220;
const menuItems = [
    { text: "Servicios", icon: <ListAltIcon />, endpoint: "services", view: "services" },
    { text: "Equipos", icon: <GroupIcon />, endpoint: "teams", view: "teams" },
    { text: "Políticas de Escalamiento", icon: <PolicyIcon />, endpoint: "escalation-policies", view: "escalation-policies" },
];

export default function App() {
    const [data, setData] = useState([]);
    const [view, setView] = useState("services");
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState("");
    const [searchId, setSearchId] = useState("");
    const [searchResult, setSearchResult] = useState(null);

    // Buscador por ID para equipos y políticas2
    const handleSearch = async () => {
        if (!searchId) return;
        setLoading(true);
        setSearchResult(null);
        let endpoint = "";
        if (view === "teams") endpoint = `/api/v1/teams/${searchId}`;
        if (view === "escalation-policies") endpoint = `/api/v1/escalation-policies/${searchId}`;
        if (!endpoint) return setLoading(false);
        try {
            const res = await fetch(endpoint);
            if (res.ok) {
                const json = await res.json();
                setSearchResult(json);
                setMessage("");
            } else {
                setSearchResult(null);
                setMessage("No se encontró el registro con ese ID");
            }
        } catch (err) {
            setSearchResult(null);
            setMessage("Error en la búsqueda: " + err.message);
        } finally {
            setLoading(false);
        }
    };

    React.useEffect(() => {
        fetchData("services", "services");
    }, []);

    const fetchData = async (endpoint, type) => {
        setLoading(true);
        setMessage("");
        try {
            const res = await fetch(`/api/v1/${endpoint}`);
            const json = await res.json();
            setData(json.services || json.teams || json.content || (Array.isArray(json) ? json : []));
            setView(type);
        } catch (err) {
            setMessage("Error al cargar datos: " + err.message);
        } finally {
            setLoading(false);
        }
    };

    const syncPagerDuty = async () => {
        setLoading(true);
        setMessage("");
        try {
            const res = await fetch("/sync/pagerduty", { method: "POST" });
            const json = await res.json();
            setMessage(json.message || "Sincronización completada");
            // Actualizar la vista actual automáticamente
            const currentMenu = menuItems.find((m) => m.view === view);
            if (currentMenu) {
                await fetchData(currentMenu.endpoint, currentMenu.view);
            }
        } catch (err) {
            setMessage("Error en la sincronización: " + err.message);
        } finally {
            setLoading(false);
        }
    };

    // Genera columnas dinámicas para DataGrid
    const getColumns = () => {
        if (!data || data.length === 0) return [];
        return Object.keys(data[0]).map((key) => ({
            field: key,
            headerName: key === "summary" && view === "teams" ? "Nombre" : key,
            flex: 1,
            minWidth: 120,
            valueGetter: (params) => params.row[key] || "",
        }));
    };

    return (
        <Box sx={{ display: "flex", bgcolor: "#f5f5f5", minHeight: "100vh" }}>
            <CssBaseline />
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
                        onClick={syncPagerDuty}
                        sx={{ bgcolor: "#00C853", color: "#fff", fontWeight: "bold", boxShadow: 2 }}
                        disabled={loading}
                    >
                        Sincronizar
                    </Button>
                </Toolbar>
            </AppBar>
            <Drawer
                variant="permanent"
                sx={{
                    width: drawerWidth,
                    flexShrink: 0,
                    [`& .MuiDrawer-paper`]: { width: drawerWidth, boxSizing: 'border-box', bgcolor: '#fff' },
                }}
            >
                <Toolbar />
                <Box sx={{ overflow: 'auto' }}>
                    <List>
                        {menuItems.map((item) => (
                            <ListItem button key={item.text} onClick={() => fetchData(item.endpoint, item.view)} selected={view === item.view}>
                                <ListItemIcon sx={{ color: view === item.view ? '#00C853' : '#1976D2' }}>{item.icon}</ListItemIcon>
                                <ListItemText primary={item.text} sx={{ color: view === item.view ? '#00C853' : '#1976D2', fontWeight: view === item.view ? 'bold' : 'normal' }} />
                            </ListItem>
                        ))}
                    </List>
                </Box>
            </Drawer>
            <Box component="main" sx={{ flexGrow: 1, p: 4, mt: 8 }}>
                <Card sx={{ mb: 2, boxShadow: 3 }}>
                    <CardContent>
                        <Typography variant="h5" sx={{ mb: 2, color: '#1976D2', fontWeight: 'bold' }}>
                            {menuItems.find((m) => m.view === view)?.text || "Datos"}
                        </Typography>
                        {(view === "teams" || view === "escalation-policies") && (
                            <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                                <TextField
                                    label={`Buscar por ID (${view === "teams" ? "Equipo" : "Política"})`}
                                    variant="outlined"
                                    size="small"
                                    value={searchId}
                                    onChange={e => setSearchId(e.target.value)}
                                    sx={{ mr: 1, width: 300 }}
                                    onKeyDown={e => { if (e.key === 'Enter') handleSearch(); }}
                                />
                                <IconButton color="primary" onClick={handleSearch} disabled={loading || !searchId}>
                                    <SearchIcon />
                                </IconButton>
                            </Box>
                        )}
                        {loading && <CircularProgress sx={{ color: '#00C853', mb: 2 }} />}
                        {message && <Typography sx={{ mb: 2, color: '#00C853' }}><strong>{message}</strong></Typography>}
                        {searchResult ? (
                            <Card sx={{ mb: 2, bgcolor: '#e8f5e9', boxShadow: 1 }}>
                                <CardContent>
                                    <Typography variant="subtitle1" sx={{ fontWeight: 'bold', color: '#1976D2' }}>Resultado búsqueda:</Typography>
                                    <pre style={{ fontSize: 14 }}>{JSON.stringify(searchResult, null, 2)}</pre>
                                </CardContent>
                            </Card>
                        ) : null}
                        {(!data || data.length === 0) ? (
                            <Typography>No hay datos disponibles</Typography>
                        ) : (
                            <Box sx={{ height: 480, width: '100%' }}>
                                <DataGrid
                                    rows={data.map((row, i) => ({ id: row.id || i, ...row }))}
                                    columns={getColumns()}
                                    pageSize={10}
                                    rowsPerPageOptions={[10, 25, 50]}
                                    sx={{ bgcolor: '#fff', borderRadius: 2, boxShadow: 1 }}
                                    disableSelectionOnClick
                                />
                            </Box>
                        )}
                    </CardContent>
                </Card>
            </Box>
        </Box>
    );
}

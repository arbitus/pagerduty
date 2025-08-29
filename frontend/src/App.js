import * as React from "react";
import { useState } from "react";
import { TextField, IconButton } from "@mui/material";
import SearchIcon from '@mui/icons-material/Search';
import { CssBaseline, Drawer, List, ListItem, ListItemIcon, ListItemText, Box, CircularProgress } from "@mui/material";
// SyncIcon import ya no es necesario
import ListAltIcon from '@mui/icons-material/ListAlt';
import GroupIcon from '@mui/icons-material/Group';
import PolicyIcon from '@mui/icons-material/Policy';
import Notification from "./components/Notification";
import SearchResult from "./components/SearchResult";
import SearchFilterBar from "./components/SearchFilterBar";
import MainCard from "./components/MainCard";
import SideMenu from "./components/SideMenu";
import TopBar from "./components/TopBar";
import DataTable from "./components/DataTable";
import { getColumns, filterData } from "./utils/dataUtils";
import { syncPagerDutyApi, fetchDataApi, searchByIdApi } from "./utils/api";

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
    const [filter, setFilter] = useState("");
    const [snackbarOpen, setSnackbarOpen] = useState(false);
    const [snackbarSeverity, setSnackbarSeverity] = useState("info");

    // Buscador por ID para equipos y políticas
    const handleSearch = async () => {
        if (!searchId) return;
        setLoading(true);
        setSearchResult(null);
        const { data, error } = await searchByIdApi(view, searchId);
        if (error) {
            setSearchResult(null);
            setMessage(error);
            setSnackbarSeverity("error");
            setSnackbarOpen(true);
        } else if (data) {
            setSearchResult(data);
            setMessage("");
            setSnackbarSeverity("success");
            setSnackbarOpen(true);
        } else {
            setSearchResult(null);
            setMessage("No se encontró el registro con ese ID");
            setSnackbarSeverity("warning");
            setSnackbarOpen(true);
        }
        setLoading(false);
    };

    React.useEffect(() => {
        fetchData("services", "services");
    }, []);

    const fetchData = async (endpoint, type) => {
        setLoading(true);
        setMessage("");
        const { data, error } = await fetchDataApi(endpoint);
        if (error) {
            setMessage(error);
            setSnackbarSeverity("error");
            setSnackbarOpen(true);
        } else {
            setData(data?.services || data?.teams || data?.content || (Array.isArray(data) ? data : []));
            setView(type);
            setSnackbarSeverity("info");
            setSnackbarOpen(true);
        }
        setLoading(false);
    };

    const syncPagerDuty = async () => {
        setLoading(true);
        setMessage("");
        const { data, error } = await syncPagerDutyApi();
        if (error) {
            setMessage(error);
            setSnackbarSeverity("error");
            setSnackbarOpen(true);
        } else {
            setMessage(data?.message || "Sincronización completada");
            setSnackbarSeverity("success");
            setSnackbarOpen(true);
            // Actualizar la vista actual automáticamente
            const currentMenu = menuItems.find((m) => m.view === view);
            if (currentMenu) {
                await fetchData(currentMenu.endpoint, currentMenu.view);
            }
        }
        setLoading(false);
    };

    // Utilidades de columnas y filtrado
    const columns = getColumns(data, view);
    const filteredData = filterData(data, filter);

    return (
        <Box sx={{ display: "flex", bgcolor: "#f5f5f5", minHeight: "100vh" }}>
            <CssBaseline />
            <TopBar onSync={syncPagerDuty} loading={loading} />
            <SideMenu menuItems={menuItems} view={view} onMenuClick={fetchData} />
            <Box component="main" sx={{ flexGrow: 1, p: 4, mt: 8 }}>
                <MainCard title={menuItems.find((m) => m.view === view)?.text || "Datos"}>
                    <SearchFilterBar
                        filter={filter}
                        setFilter={setFilter}
                        view={view}
                        searchId={searchId}
                        setSearchId={setSearchId}
                        onSearch={handleSearch}
                        loading={loading}
                    />
                    <Notification
                        message={message}
                        open={snackbarOpen}
                        severity={snackbarSeverity}
                        onClose={() => setSnackbarOpen(false)}
                    />
                    <SearchResult result={searchResult} />
                    <DataTable columns={columns} data={filteredData} loading={loading} />
                </MainCard>
            </Box>
        </Box>
    );
}
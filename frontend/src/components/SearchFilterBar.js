import React from "react";
import { Box, TextField, IconButton } from "@mui/material";
import SearchIcon from '@mui/icons-material/Search';

export default function SearchFilterBar({ filter, setFilter, view, searchId, setSearchId, onSearch, loading }) {
    return (
        <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
            <TextField
                label="Filtrar en tabla"
                variant="outlined"
                size="small"
                value={filter}
                onChange={e => setFilter(e.target.value)}
                sx={{ mr: 2, width: 300 }}
            />
            {(view === "teams" || view === "escalation-policies") && (
                <>
                    <TextField
                        label={`Buscar por ID (${view === "teams" ? "Equipo" : "Política"})`}
                        variant="outlined"
                        size="small"
                        value={searchId}
                        onChange={e => setSearchId(e.target.value)}
                        sx={{ mr: 1, width: 300 }}
                        onKeyDown={e => { if (e.key === 'Enter') onSearch(); }}
                    />
                    <IconButton color="primary" onClick={onSearch} disabled={loading || !searchId}>
                        <SearchIcon />
                    </IconButton>
                </>
            )}
        </Box>
    );
}

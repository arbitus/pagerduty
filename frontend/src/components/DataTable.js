import React from "react";
import { Box, Typography, CircularProgress } from "@mui/material";
import { DataGrid } from '@mui/x-data-grid';

export default function DataTable({ columns, data, loading }) {
    return (
        <>
            {loading && <CircularProgress sx={{ color: '#00C853', mb: 2 }} />}
            {(!data || data.length === 0) ? (
                <Typography>No hay datos disponibles</Typography>
            ) : (
                <Box sx={{ height: 480, width: '100%' }}>
                    <DataGrid
                        rows={data.map((row, i) => ({ id: row.id || i, ...row }))}
                        columns={columns}
                        pageSize={10}
                        rowsPerPageOptions={[10, 25, 50]}
                        sx={{ bgcolor: '#fff', borderRadius: 2, boxShadow: 1 }}
                        disableSelectionOnClick
                        loading={loading}
                    />
                </Box>
            )}
        </>
    );
}

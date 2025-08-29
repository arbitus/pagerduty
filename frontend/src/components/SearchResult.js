import React from "react";
import { Card, CardContent, Typography } from "@mui/material";

export default function SearchResult({ result }) {
    if (!result) return null;
    return (
        <Card sx={{ mb: 2, bgcolor: '#e8f5e9', boxShadow: 1 }}>
            <CardContent>
                <Typography variant="subtitle1" sx={{ fontWeight: 'bold', color: '#1976D2' }}>Resultado búsqueda:</Typography>
                <pre style={{ fontSize: 14 }}>{JSON.stringify(result, null, 2)}</pre>
            </CardContent>
        </Card>
    );
}

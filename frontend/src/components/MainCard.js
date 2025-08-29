import React from "react";
import { Card, CardContent, Typography } from "@mui/material";

export default function MainCard({ title, children }) {
    return (
        <Card sx={{ mb: 2, boxShadow: 3 }}>
            <CardContent>
                <Typography variant="h5" sx={{ mb: 2, color: '#1976D2', fontWeight: 'bold' }}>
                    {title}
                </Typography>
                {children}
            </CardContent>
        </Card>
    );
}

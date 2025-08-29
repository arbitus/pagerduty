import React from "react";
import { Drawer, Toolbar, List, ListItem, ListItemIcon, ListItemText, Box } from "@mui/material";

const drawerWidth = 220;

export default function SideMenu({ menuItems, view, onMenuClick }) {
    return (
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
                        <ListItem button key={item.text} onClick={() => onMenuClick(item.endpoint, item.view)} selected={view === item.view}>
                            <ListItemIcon sx={{ color: view === item.view ? '#00C853' : '#1976D2' }}>{item.icon}</ListItemIcon>
                            <ListItemText primary={item.text} sx={{ color: view === item.view ? '#00C853' : '#1976D2', fontWeight: view === item.view ? 'bold' : 'normal' }} />
                        </ListItem>
                    ))}
                </List>
            </Box>
        </Drawer>
    );
}

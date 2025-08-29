// Genera columnas dinámicas para DataGrid
export function getColumns(data, view) {
    if (!data || data.length === 0) return [];
    return Object.keys(data[0]).map((key) => ({
        field: key,
        headerName: key === "summary" && view === "teams" ? "Nombre" : key,
        flex: 1,
        minWidth: 120,
        valueGetter: (params) => params.row[key] || "",
    }));
}

// Filtrado general de datos
export function filterData(data, filter) {
    if (!filter) return data;
    return data.filter(row =>
        Object.values(row).some(val => val && val.toString().toLowerCase().includes(filter.toLowerCase()))
    );
}

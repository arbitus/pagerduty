export function getColumns(data, view) {
    if (!data || data.length === 0) return [];
    return Object.keys(data[0]).map((key) => {
        // Para teams (array de objetos)
        if (key === "teams") {
            return {
                field: key,
                headerName: "Teams",
                flex: 1,
                minWidth: 120,
                valueGetter: (params) => Array.isArray(params.row.teams)
                    ? params.row.teams.map(t => t.summary || t.name || t.id).join(', ')
                    : "",
            };
        }
        // Para escalation_policy (objeto)
        if (key === "escalation_policy") {
            return {
                field: key,
                headerName: "Escalation Policy",
                flex: 1,
                minWidth: 120,
                valueGetter: (params) => params.row.escalation_policy?.summary || params.row.escalation_policy?.id || "",
            };
        }
        return {
            field: key,
            headerName: key === "summary" && view === "teams" ? "Nombre" : key,
            flex: 1,
            minWidth: 120,
            valueGetter: (params) => params.row[key] || "",
        };
    });
}

export function filterData(data, filter) {
    if (!filter) return data;
    return data.filter(row =>
        Object.values(row).some(val => val && val.toString().toLowerCase().includes(filter.toLowerCase()))
    );
}

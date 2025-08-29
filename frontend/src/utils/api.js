// Funciones para llamadas a la API y manejo de errores

export async function fetchApi(endpoint, options = {}) {
    const res = await fetch(endpoint, options);
    let data = null;
    let error = null;
    if (!res.ok) {
        const text = await res.text();
        try {
            const errorJson = JSON.parse(text);
            // Traducción de mensaje de autorización
            if (errorJson.message === "Unauthorized to access PagerDuty API") {
                error = "No autorizado: revisa tu token de acceso";
            } else {
                error = errorJson.message || "Error en la sincronización";
            }
        } catch (jsonErr) {
            if (res.status === 401) {
                error = "No autorizado: revisa tu token de acceso";
            } else {
                error = "Error inesperado: la respuesta no es válida";
            }
        }
        return { data: null, error };
    }
    try {
        data = await res.json();
    } catch (err) {
        error = "Error al parsear la respuesta";
    }
    return { data, error };
}

export async function syncPagerDutyApi() {
    return await fetchApi("/sync/pagerduty", { method: "POST" });
}

export async function fetchDataApi(endpoint) {
    return await fetchApi(`/api/v1/${endpoint}`);
}

export async function searchByIdApi(view, id) {
    let endpoint = "";
    if (view === "teams") endpoint = `/api/v1/teams/${id}`;
    if (view === "escalation-policies") endpoint = `/api/v1/escalation-policies/${id}`;
    if (!endpoint) return { data: null, error: "Endpoint no válido" };
    return await fetchApi(endpoint);
}

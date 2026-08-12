// Alle Websites laden und anzeigen
async function loadWebsites() {
    const response = await fetch('/api/websites');
    const websites = await response.json();
    const list = document.getElementById('websiteList');
    list.innerHTML = '';
    websites.forEach(w => {
        const li = document.createElement('li');
        li.innerHTML = `${w.name} – <a href="${w.url}" target="_blank">Besuchen</a>`;
        list.appendChild(li);
    });
}

// Neue Website hinzufügen
document.getElementById('addWebsiteForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const name = document.getElementById('name').value;
    const url = document.getElementById('url').value;

    await fetch('/api/websites', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name, url, intervalMinutes: 30, recipientEmail: 'test@example.com' })
    });

    document.getElementById('name').value = '';
    document.getElementById('url').value = '';
    loadWebsites();
});

// Beim Laden der Seite
loadWebsites();
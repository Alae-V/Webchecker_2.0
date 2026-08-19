// ============================================
// 1. ALLE WEBSITES LADEN
// ============================================
async function loadWebsites() {
    const list = document.getElementById('websiteList');

    try {
        const response = await fetch('/api/websites');
        if (!response.ok) {
            throw new Error('Fehler beim Laden der Websites');
        }
        const websites = await response.json();

        list.innerHTML = '';

        if (websites.length === 0) {
            list.innerHTML = '<li>Keine Websites vorhanden.</li>';
            return;
        }

        websites.forEach(website => {
            const li = document.createElement('li');
            li.innerHTML = `
                <span>
                    <strong>${website.name}</strong>
                    <a href="${website.url}" target="_blank">Besuchen</a>
                </span>
                <button class="delete-btn" data-id="${website.id}">Löschen</button>
            `;
            list.appendChild(li);
        });

        document.querySelectorAll('.delete-btn').forEach(btn => {
            btn.addEventListener('click', deleteWebsite);
        });

    } catch (error) {
        console.error('Fehler:', error);
        list.innerHTML = '<li style="color:red;">Fehler beim Laden der Websites.</li>';
    }
}

// ============================================
// 2. WEBSITE LÖSCHEN
// ============================================
async function deleteWebsite(event) {
    const id = event.target.dataset.id;
    if (!confirm('Möchtest du diese Website wirklich löschen?')) return;

    try {
        const response = await fetch(`/api/websites/${id}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            throw new Error('Fehler beim Löschen');
        }

        loadWebsites();

    } catch (error) {
        console.error('Fehler:', error);
        alert('Löschen fehlgeschlagen.');
    }
}

// ============================================
// 3. NEUE WEBSITE HINZUFÜGEN
// ============================================
document.addEventListener('DOMContentLoaded', function() {
    // Wichtig: Das Formular wird erst GEFUNDEN, nachdem die Seite geladen ist
    const form = document.getElementById('addWebsiteForm');
    if (!form) {
        console.error('Formular mit ID "addWebsiteForm" nicht gefunden!');
        return;
    }

    form.addEventListener('submit', async (event) => {
        event.preventDefault();

        const nameInput = document.getElementById('nameInput');
        const urlInput = document.getElementById('urlInput');

        const name = nameInput.value.trim();
        const url = urlInput.value.trim();

        if (!name || !url) {
            alert('Bitte Name und URL ausfüllen.');
            return;
        }

        const newWebsite = {
            name: name,
            url: url,
            intervalMinutes: 30,
            recipientEmail: 'test@Email.com'
        };

        try {
            const response = await fetch('/api/websites', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(newWebsite)
            });

            if (!response.ok) {
                throw new Error('Fehler beim Hinzufügen');
            }

            const data = await response.json();
            console.log('Website hinzugefügt:', data);

            nameInput.value = '';
            urlInput.value = '';

            loadWebsites();

        } catch (error) {
            console.error('Fehler:', error);
            alert('Hinzufügen fehlgeschlagen.');
        }
    });

    loadWebsites();
});
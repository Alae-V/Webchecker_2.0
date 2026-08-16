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

        // Liste leeren
        list.innerHTML = '';

        if (websites.length === 0) {
            list.innerHTML = '<li>Keine Websites vorhanden.</li>';
            return;
        }

        // Für jede Website ein Listenelement erstellen
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

        // Alle Lösch-Buttons aktivieren
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

        // Liste neu laden
        loadWebsites();

    } catch (error) {
        console.error('Fehler:', error);
        alert('Löschen fehlgeschlagen.');
    }
}

// ============================================
// 3. NEUE WEBSITE HINZUFÜGEN
// ============================================
document.getElementById('addWebsiteForm').addEventListener('submit', async (event) => {
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
        recipientEmail: 'test@example.com'
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

        // Formular leeren
        nameInput.value = '';
        urlInput.value = '';

        // Liste neu laden
        loadWebsites();

    } catch (error) {
        console.error('Fehler:', error);
        alert('Hinzufügen fehlgeschlagen.');
    }
});

// ============================================
// 4. BEIM LADEN DER SEITE
// ============================================
loadWebsites();
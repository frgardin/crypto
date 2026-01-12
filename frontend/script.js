// API Configuration
const API_BASE_URL = 'http://localhost:8080';

// Utility functions
function showResult(elementId, content, type = 'success') {
    const resultDiv = document.getElementById(elementId);
    resultDiv.style.display = 'block';
    resultDiv.className = `result ${type}`;
    resultDiv.innerHTML = content;
}

function showLoading(elementId) {
    showResult(elementId, '🔄 Processing...', 'loading');
}

function hideResult(elementId) {
    const resultDiv = document.getElementById(elementId);
    resultDiv.style.display = 'none';
}

function copyToClipboard(text) {
    navigator.clipboard.writeText(text).then(() => {
        // Could show a toast notification here
        console.log('Copied to clipboard');
    });
}

function createCopyButton(text) {
    return `<button class="copy-btn" onclick="copyToClipboard('${text.replace(/'/g, "\\'")}')">Copy</button>`;
}

// API calls
async function callApi(endpoint, data, isFile = false) {
    try {
        const url = `${API_BASE_URL}${endpoint}`;

        if (isFile) {
            const formData = new FormData();
            formData.append('file', data);

            const response = await fetch(url, {
                method: 'POST',
                body: formData
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const blob = await response.blob();
            return blob;
        } else {
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const result = await response.json();
            return result;
        }
    } catch (error) {
        throw new Error(`API call failed: ${error.message}`);
    }
}

// Download file utility
function downloadBlob(blob, filename) {
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = filename;
    document.body.appendChild(a);
    a.click();
    window.URL.revokeObjectURL(url);
    document.body.removeChild(a);
}

// Text operations
async function encryptText() {
    const input = document.getElementById('encryptInput').value.trim();
    if (!input) {
        showResult('encryptResult', '❌ Please enter text to encrypt', 'error');
        return;
    }

    const button = document.getElementById('encryptBtn');
    const originalText = button.textContent;
    button.disabled = true;
    button.textContent = '🔄 Encrypting...';

    try {
        showLoading('encryptResult');
        const result = await callApi('/api/encrypt', { text: input });
        const encryptedText = result.text;
        showResult('encryptResult',
            `✅ Encrypted successfully:\n${encryptedText}${createCopyButton(encryptedText)}`);
    } catch (error) {
        showResult('encryptResult', `❌ ${error.message}`, 'error');
    } finally {
        button.disabled = false;
        button.textContent = originalText;
    }
}

async function decryptText() {
    const input = document.getElementById('decryptInput').value.trim();
    if (!input) {
        showResult('decryptResult', '❌ Please enter text to decrypt', 'error');
        return;
    }

    const button = document.getElementById('decryptBtn');
    const originalText = button.textContent;
    button.disabled = true;
    button.textContent = '🔄 Decrypting...';

    try {
        showLoading('decryptResult');
        const result = await callApi('/api/decrypt', { text: input });
        const decryptedText = result.text;
        showResult('decryptResult',
            `✅ Decrypted successfully:\n${decryptedText}${createCopyButton(decryptedText)}`);
    } catch (error) {
        showResult('decryptResult', `❌ ${error.message}`, 'error');
    } finally {
        button.disabled = false;
        button.textContent = originalText;
    }
}

async function hashText() {
    const input = document.getElementById('hashInput').value.trim();
    if (!input) {
        showResult('hashResult', '❌ Please enter text to hash', 'error');
        return;
    }

    const button = document.getElementById('hashBtn');
    const originalText = button.textContent;
    button.disabled = true;
    button.textContent = '🔄 Hashing...';

    try {
        showLoading('hashResult');
        const result = await callApi('/api/hash', { text: input });
        const hash = result.text;
        showResult('hashResult',
            `✅ SHA-256 Hash generated:\n${hash}${createCopyButton(hash)}`);
    } catch (error) {
        showResult('hashResult', `❌ ${error.message}`, 'error');
    } finally {
        button.disabled = false;
        button.textContent = originalText;
    }
}

// File operations
async function encryptFile() {
    const fileInput = document.getElementById('encryptFileInput');
    const file = fileInput.files[0];

    if (!file) {
        showResult('encryptFileResult', '❌ Please select a file to encrypt', 'error');
        return;
    }

    const button = document.getElementById('encryptFileBtn');
    const originalText = button.textContent;
    button.disabled = true;
    button.textContent = '🔄 Encrypting...';

    try {
        showLoading('encryptFileResult');
        const encryptedBlob = await callApi('/api/encrypt-file', file, true);
        const filename = `encrypted_${file.name}`;
        downloadBlob(encryptedBlob, filename);
        showResult('encryptFileResult', `✅ File encrypted successfully! Downloaded as: ${filename}`);
    } catch (error) {
        showResult('encryptFileResult', `❌ ${error.message}`, 'error');
    } finally {
        button.disabled = false;
        button.textContent = originalText;
    }
}

async function decryptFile() {
    const fileInput = document.getElementById('decryptFileInput');
    const file = fileInput.files[0];

    if (!file) {
        showResult('decryptFileResult', '❌ Please select a file to decrypt', 'error');
        return;
    }

    const button = document.getElementById('decryptFileBtn');
    const originalText = button.textContent;
    button.disabled = true;
    button.textContent = '🔄 Decrypting...';

    try {
        showLoading('decryptFileResult');
        const decryptedBlob = await callApi('/api/decrypt-file', file, true);
        const filename = `decrypted_${file.name}`;
        downloadBlob(decryptedBlob, filename);
        showResult('decryptFileResult', `✅ File decrypted successfully! Downloaded as: ${filename}`);
    } catch (error) {
        showResult('decryptFileResult', `❌ ${error.message}`, 'error');
    } finally {
        button.disabled = false;
        button.textContent = originalText;
    }
}

// File input handlers
function updateFileName(inputId, displayId) {
    const input = document.getElementById(inputId);
    const display = document.getElementById(displayId);

    input.addEventListener('change', (e) => {
        const file = e.target.files[0];
        if (file) {
            display.textContent = `📄 ${file.name} (${(file.size / 1024).toFixed(2)} KB)`;
        } else {
            display.textContent = '';
        }
    });
}

// Initialize event listeners
document.addEventListener('DOMContentLoaded', () => {
    // Text operation buttons
    document.getElementById('encryptBtn').addEventListener('click', encryptText);
    document.getElementById('decryptBtn').addEventListener('click', decryptText);
    document.getElementById('hashBtn').addEventListener('click', hashText);

    // File operation buttons
    document.getElementById('encryptFileBtn').addEventListener('click', encryptFile);
    document.getElementById('decryptFileBtn').addEventListener('click', decryptFile);

    // File input handlers
    updateFileName('encryptFileInput', 'encryptFileName');
    updateFileName('decryptFileInput', 'decryptFileName');

    // Enter key support for textareas
    document.querySelectorAll('.textarea').forEach(textarea => {
        textarea.addEventListener('keydown', (e) => {
            if (e.ctrlKey && e.key === 'Enter') {
                const card = textarea.closest('.card');
                const button = card.querySelector('.btn');
                if (button) {
                    button.click();
                }
            }
        });
    });

    // Add keyboard shortcuts info
    const shortcuts = document.createElement('div');
    shortcuts.style.cssText = `
        position: fixed;
        bottom: 20px;
        right: 20px;
        background: rgba(255, 255, 255, 0.9);
        padding: 10px 15px;
        border-radius: 8px;
        font-size: 0.8rem;
        color: #666;
        border: 1px solid #e1e5e9;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    `;
    shortcuts.innerHTML = '💡 Tip: Ctrl+Enter to process text';
    document.body.appendChild(shortcuts);

    // Hide shortcuts after 5 seconds
    setTimeout(() => {
        shortcuts.style.opacity = '0';
        setTimeout(() => document.body.removeChild(shortcuts), 300);
    }, 5000);
});

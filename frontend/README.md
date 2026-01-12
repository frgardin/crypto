# Crypto Tool Frontend

A modern, Notion-inspired frontend interface for the Crypto API backend.

## Features

- 🎨 Notion-style clean and modern UI design
- 🔐 Complete integration with all crypto APIs
- 📝 Text encryption, decryption, and hashing
- 📁 File encryption and decryption with automatic download
- ⚡ Real-time processing with loading states
- 📋 One-click copy to clipboard functionality
- ⌨️ Keyboard shortcuts (Ctrl+Enter)
- 📱 Responsive design for all devices

## Getting Started

### Prerequisites

1. **Backend Running**: Make sure the Spring Boot crypto backend is running on `http://localhost:8080`
2. **Modern Browser**: Chrome, Firefox, Safari, or Edge

### Usage

#### Option 1: Development Server (Recommended)
1. Run the development server: `python3 server.py`
2. Open `http://localhost:8000` in your browser
3. The interface will automatically connect to the backend API

#### Option 2: Direct File Opening
1. Open `index.html` directly in your browser
2. May encounter CORS issues in some browsers (see troubleshooting below)

### Text Operations

- **Encrypt**: Enter plain text and click "Encrypt" to get Base64 encrypted output
- **Decrypt**: Enter Base64 encrypted text and click "Decrypt" to get original text
- **Hash**: Enter any text and click "Generate Hash" to get SHA-256 hash

### File Operations

- **Encrypt File**: Select any file, click "Encrypt File" to download encrypted version
- **Decrypt File**: Select encrypted file, click "Decrypt File" to download decrypted version

## API Integration

The frontend consumes these backend endpoints:

```
POST /api/encrypt      - Text encryption
POST /api/decrypt      - Text decryption
POST /api/hash         - Text hashing
POST /api/encrypt-file - File encryption
POST /api/decrypt-file - File decryption
```

## Design Features

### Notion-Inspired Design
- Clean typography using system fonts
- Subtle shadows and rounded corners
- Light gray background with white cards
- Gradient header with modern colors
- Hover effects and smooth transitions

### User Experience
- Loading states during API calls
- Success/error feedback with color coding
- File size display for uploaded files
- Keyboard shortcuts for power users
- Responsive layout for mobile devices

### Accessibility
- Proper semantic HTML
- Keyboard navigation support
- Screen reader friendly
- High contrast color scheme

## File Structure

```
frontend/
├── index.html      # Main HTML file
├── style.css       # Notion-inspired styles
├── script.js       # API integration and interactions
└── README.md       # This file
```

## Browser Compatibility

- Chrome 90+
- Firefox 88+
- Safari 14+
- Edge 90+

## Troubleshooting

### Backend Connection Issues
- Ensure backend is running on `http://localhost:8080`
- Check browser console for CORS errors
- Verify all API endpoints are accessible

### CORS Issues
If you encounter CORS errors when opening `index.html` directly in the browser:
- **Firefox**: May block requests from file:// protocol to localhost
- **Solution 1**: Use a local web server to serve the frontend files
- **Solution 2**: Open browser in private/incognito mode
- **Solution 3**: Use browser flags to disable CORS (not recommended for production)
- **Solution 4**: Serve frontend from the same origin as backend (advanced setup)

**Quick Fix**: Run `python3 -m http.server 3000` in the frontend directory and open `http://localhost:3000`

### File Upload Issues
- Check file size limits (default Spring Boot limit applies)
- Ensure file is not corrupted
- Try smaller files for testing

### Copy to Clipboard
- Requires HTTPS or localhost for security
- Fallback shows text for manual copying

## Customization

### Changing API URL
Edit the `API_BASE_URL` constant in `script.js`:

```javascript
const API_BASE_URL = 'http://localhost:8080'; // Change this URL
```

### Styling Modifications
- Colors are defined as CSS custom properties
- Responsive breakpoints at 768px and 480px
- Font stack uses system fonts for performance

### Adding New Features
- Add new API calls in the `callApi` function
- Create new UI components following the card pattern
- Add event listeners in the DOMContentLoaded callback

## Development

To modify the frontend:

1. Edit HTML structure in `index.html`
2. Modify styles in `style.css`
3. Add functionality in `script.js`
4. Test with backend running
5. Refresh browser to see changes

## Security Notes

- All API calls are made over HTTP (localhost only)
- File uploads are handled securely by the backend
- No sensitive data is stored in browser
- Copy to clipboard requires user permission

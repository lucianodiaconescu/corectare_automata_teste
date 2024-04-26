function uploadAndScanImage() {
    const fileInput = document.getElementById('imageInput');
    const file = fileInput.files[0];

    if (!file) {
        alert('Please select an image.');
        return;
    }

    // Verificare dacă fișierul este în format JPEG, JPG sau PNG
    const allowedFormats = ['image/jpeg', 'image/jpg', 'image/png'];
    if (!allowedFormats.includes(file.type)) {
        alert('Please select an image in JPEG, JPG, or PNG format.');
        return;
    }

    const formData = new FormData();
    formData.append('image', file);

    fetch('/api/users/scantests/upload', {
        method: 'POST',
        body: formData
    })
        .then(response => response.text())
        .then(scannedText => {
            document.getElementById('scannedText').innerText = scannedText;
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error processing image.');
        });
}

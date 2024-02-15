document.addEventListener('DOMContentLoaded', function() {
    loadImages();
});

function loadImages() {
    var username = document.getElementById('username').textContent;

    fetch('/profile/images/' + username)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(imageUrls => {
            var imageCardDiv = document.getElementById('imageCard');
            imageCardDiv.innerHTML = '';
            imageUrls.forEach(url => {
                var img = document.createElement('img');
                img.src = url;
                img.id = 'uploadedImage';
                imageCardDiv.appendChild(img);
            });
        })
        .catch(error => console.error('Error loading images:', error));
}

function openImageUploadModal() {
    document.getElementById('imageUploadModal').style.display = 'block';
}

function closeImageUploadModal() {
    document.getElementById('imageUploadModal').style.display = 'none';
}

async function uploadImage() {
    var username = document.getElementById('username').textContent;
    var imageType = document.querySelector('#modalImageType').value;

    var formData = createFormData(username, imageType);

    try {
        const response = await fetch('/profile/imageUpload', {
            method: 'POST',
            body: formData,
        });

        if (!response.ok) {
            throw new Error('Image upload failed: ' + response.status);
        }

        const data = await response.json();
        console.log('Image uploaded successfully:', data);

        loadImages();
        closeImageUploadModal(); 
    } catch (error) {
        console.error('Error uploading image:', error);
    }
}

function previewFile() {
    const preview = document.querySelector('#preview');
    const file = document.querySelector('#modalImageUpload').files[0];
    const reader = new FileReader();

    reader.addEventListener("load", function () {
        preview.style.backgroundImage = 'url(' + reader.result + ')';
    }, false);

    if (file) {
        reader.readAsDataURL(file);
    }
}

function createFormData(username, imageType) {
    const formData = new FormData();
    const file = document.querySelector('#modalImageUpload').files[0];

    const uploadDTO = {
        username: username,
        imageType: imageType
    };

    formData.append('uploadDTO', new Blob([JSON.stringify(uploadDTO)], {type: 'application/json'}));
    formData.append('file', file);

    return formData;
}

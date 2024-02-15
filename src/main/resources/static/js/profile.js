async function uploadProfileImage() {
    var username = document.getElementById('username').textContent;
    var userType = document.getElementById('userType').value;

    // 프로필 이미지 가져오기
    var fileInput = document.getElementById('profileUpload');
    var file = fileInput.files[0];

    // 파일 선택 여부 검증
    if (!file) {
        alert('Please select a file to upload');
        return;
    }

    // formData 생성
    var formData = new FormData();
    formData.append('username', username);
    formData.append('userType', userType);
    formData.append('image', file);

    // 프로필 이미지 업데이트 요청 보내기
    try {
        var updateResponse = await fetch('/profile/upload', {
            method: 'POST',
            body: formData
        });

        if (!updateResponse.ok) {
            throw new Error('Network response was not ok');
        }
    
        var data = await updateResponse.json();
    
        console.log('Success:', data);
    
        var profileImage = document.getElementById('profileImage');
        profileImage.src = data.newImageUrl + '?t=' + new Date().getTime();
    } catch (error) {
        console.error('Error:', error);
        alert('Failed to upload profile image');
    }
}

async function updateBiography() {
    var username = document.getElementById('username').textContent;
    var biography = document.getElementById('biography').value;

    // 바이오그래피 업데이트 요청 보내기
    try {
        var updateResponse = await fetch('/profile/updateBiography', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: username,
                biography: biography
            })
        });
    
        if (!updateResponse.ok) {
            throw new Error('Network response was not ok');
        }
    
        console.log('Success:', await updateResponse.json());
    } catch (error) {
        console.error('Error:', error);
        alert('Failed to update biography.');
    }
}



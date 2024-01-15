// Function to handle login button click
function login() {
    var username = document.getElementById('username').value;
    if (username) {
        // Simulate backend API call (replace this with actual API call)
        var apiUrl = 'http://localhost:9000/login';
        var requestData = { name: username }; // Assuming 'name' is the parameter expected by the backend

        // Append the 'name' parameter to the URL
        var urlWithParams = `${apiUrl}?name=${encodeURIComponent(requestData.name)}`;

        fetch(urlWithParams, {
            method: 'POST',
            // Note: No need to set 'Content-Type' for form data
        })
        .then(response => response.json())
        .then(data => {
            // Check the result from the backend
            if (data.code === 200) {
                sessionStorage.setItem('ID', data.id);
           

                // Display welcome message
                alert(`Welcome, ${username}!`);

                // Redirect based on the role
                if (data.role === 1) {
                    window.location.href = 'admin.html';
                } else if (data.role === 2) {
                    window.location.href = 'home.html';
                } else if (data.role === 3) {
                    window.location.href = 'vendor.html';
                }
            } else {
                alert(data.msg);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred. Please try again later.');
        });
    } else {
        alert('Please fill in all required fields.');
    }
}

// Attach click event to the login button
document.getElementById('login-button').addEventListener('click', login);

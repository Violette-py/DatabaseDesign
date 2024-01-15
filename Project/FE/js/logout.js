// logout.js

function logout() {
    // Clear the user ID from the session storage
    sessionStorage.removeItem('ID');


    // Redirect to the index.html or another desired page
    window.location.href = 'index.html';
}

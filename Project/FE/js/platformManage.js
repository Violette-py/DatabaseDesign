var platformsData; // Variable to store platform data
var itemsPerPage = 6; // Number of platforms to display per page
var currentPage = 1;

window.onload = function () {
    loadAllPlatforms();
};

function loadAllPlatforms() {
    var apiUrl = 'http://localhost:9000/admin/displayAllPlatform';

    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            platformsData = data.data;
            loadPlatformPage(currentPage);
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred. Please try again later.');
        });
}

// Function to load platforms for a specific page
function loadPlatformPage(page) {
    var platformItemsContainer = document.getElementById('platform-items-container');
    platformItemsContainer.innerHTML = '';

    var startIndex = (page - 1) * itemsPerPage;
    var endIndex = startIndex + itemsPerPage;
    var platformsToDisplay = platformsData.slice(startIndex, endIndex);

    for (var i = 0; i < platformsToDisplay.length; i++) {
        var platformItem = document.createElement('div');
        platformItem.className = 'platform-item';
        platformItem.id = 'platform-item-' + platformsToDisplay[i].id; // Assign a unique ID to each platform item

        // Create input elements for platform information
        var platformNameInput = createInput('text', 'platformName', 'Platform Name:', platformsToDisplay[i].name, platformsToDisplay[i].id);

        // Create platform actions
        var platformActions = document.createElement('div');
        platformActions.className = 'platform-actions';
        platformActions.innerHTML = `<button onclick="modifyPlatform(${platformsToDisplay[i].id})">Modify</button>
                                   <button onclick="deletePlatform('${platformsToDisplay[i].id}')">Delete</button>`;

        platformItem.appendChild(platformNameInput);
        platformItem.appendChild(platformActions);
        platformItemsContainer.appendChild(platformItem);
    }

    currentPage = page;

    // Pagination logic
    var paginationContainer = document.getElementById('pagination-container');
    paginationContainer.innerHTML = '';

    var totalPages = Math.ceil(platformsData.length / itemsPerPage);

    // Previous button
    var prevButton = document.createElement('button');
    prevButton.innerText = 'Previous';
    prevButton.onclick = function () {
        if (currentPage > 1) {
            loadPlatformPage(currentPage - 1);
        }
    };
    paginationContainer.appendChild(prevButton);

    // Page buttons
    for (var i = 1; i <= totalPages; i++) {
        var pageButton = document.createElement('button');
        pageButton.innerText = i;
        pageButton.onclick = function () {
            loadPlatformPage(parseInt(this.innerText));
        };

        if (i === currentPage) {
            pageButton.className = 'active';
        }

        paginationContainer.appendChild(pageButton);
    }

    // Next button
    var nextButton = document.createElement('button');
    nextButton.innerText = 'Next';
    nextButton.onclick = function () {
        if (currentPage < totalPages) {
            loadPlatformPage(currentPage + 1);
        }
    };
    paginationContainer.appendChild(nextButton);
}

// Function to show the "Add Platform" form
function showAddPlatformForm() {
    var addPlatformForm = document.getElementById('add-platform-form');
    addPlatformForm.style.display = 'block';
}

// Helper function to create input elements
function createInput(type, id, label, value, platformId) {
    var container = document.createElement('div');
    container.className = 'platform-input-container';

    var labelElement = document.createElement('label');
    labelElement.htmlFor = id + '-' + platformId;
    labelElement.innerText = label;
    container.appendChild(labelElement);

    var input = document.createElement('input');
    input.type = type;
    input.id = id + '-' + platformId;
    input.value = value;
    input.disabled = true; // Disable input by default
    container.appendChild(input);

    return container;
}

// Function to enable input fields when Modify button is clicked
function modifyPlatform(platformId) {
    var platformNameInput = document.getElementById('platformName-' + platformId);

    platformNameInput.disabled = false;

    // Create a form for each platform item
    var updateForm = document.createElement('form');
    
    // Add a submit button with updatePlatform logic
    var submitButton = document.createElement('button');
    submitButton.type = 'button'; // Change type to 'button'
    submitButton.innerText = 'Submit';
    submitButton.onclick = function () {
        updatePlatform(platformId);
    };

    // Append the button to the form
    updateForm.appendChild(submitButton);

    // Append the form to platformActions
    var platformActions = document.getElementById('platform-item-' + platformId).getElementsByClassName('platform-actions')[0];
    platformActions.appendChild(updateForm);
}

function updatePlatform(platformId) {
    var updatedPlatformName = document.getElementById('platformName-' + platformId).value;

    var apiUrl = 'http://localhost:9000/admin/updatePlatform';

    fetch(apiUrl, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            id: platformId,
            name: updatedPlatformName,
        }),
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) {
            alert('平台更新成功');
            // You may choose to reload the page or update the UI as needed
            window.location.reload();
        } else {
            alert(data.msg);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('An error occurred. Please try again later.');
    });
}

function deletePlatform(platformId) {
    var isConfirmed = confirm("你确定删除此平台吗？");

    if (isConfirmed) {
        var apiUrl = 'http://localhost:9000/admin/removePlatform?id=' + platformId;

        fetch(apiUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
        })
        .then(response => response.json())
        .then(data => {
            if (data.code === 200) {
                alert('平台删除成功');
                // You may choose to reload the page or update the UI as needed
                window.location.reload();
            } else {
                alert(data.msg);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred. Please try again later.');
        });
    }
}

// Function to add a new platform
function addPlatform(event) {
    event.preventDefault(); // Prevent the form from submitting in the default way

    var platformName = document.getElementById('platformName').value;

    // Validate input if needed

    var apiUrl = 'http://localhost:9000/admin/addPlatform';

    fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name: platformName,
        }),
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) {
            alert('平台添加成功');
            // Reload the page or update the UI as needed
            window.location.reload();
        } else {
            alert(data.msg);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('An error occurred. Please try again later.');
    });
}

// Additional functions for pagination, showing/hiding forms, etc. can be added as needed

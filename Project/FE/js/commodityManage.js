var commoditiesData; // Variable to store commodity data
var commodityItemsPerPage = 5; // Number of commodities to display per page
var currentCommodityPage = 1;
window.onload = function () {
    initializeCommodityManage();
};

function initializeCommodityManage() {
    var apiUrl = 'http://localhost:9000/admin/displayAllCommodity';

    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            commoditiesData = data.data;
            displayCommodities(currentCommodityPage);
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred. Please try again later.');
        });
}

// Function to load commodities for a specific page
function displayCommodities(page) {
    var commodityItemsContainer = document.getElementById('commodity-items-container');
    commodityItemsContainer.innerHTML = '';

    var startIndex = (page - 1) * commodityItemsPerPage;
    var endIndex = startIndex + commodityItemsPerPage;
    var commoditiesToDisplay = commoditiesData.slice(startIndex, endIndex);

    for (var i = 0; i < commoditiesToDisplay.length; i++) {
        var commodityItem = document.createElement('div');
        commodityItem.className = 'commodity-item';
        commodityItem.id = 'commodity-item-' + commoditiesToDisplay[i].id; // Assign a unique ID to each commodity item

        // Create input elements for commodity information
        var nameInput = createInput('text', 'commodity-name', 'Name:', commoditiesToDisplay[i].name, commoditiesToDisplay[i].id);
        var categoryInput = createInput('text', 'commodity-category', 'Category:', commoditiesToDisplay[i].category, commoditiesToDisplay[i].id);

        // Create commodity actions
        var commodityActions = document.createElement('div');
        commodityActions.className = 'commodity-actions';
        commodityActions.innerHTML = `<button onclick="modifyCommodity(${commoditiesToDisplay[i].id})">Modify</button>
                                <button onclick="deleteCommodity('${commoditiesToDisplay[i].id}')">Delete</button>`;

        commodityItem.appendChild(nameInput);
        commodityItem.appendChild(categoryInput);
        commodityItem.appendChild(commodityActions);
        commodityItemsContainer.appendChild(commodityItem);
    }

    currentCommodityPage = page;

    // Pagination logic
    var paginationContainer = document.getElementById('pagination-container');
    paginationContainer.innerHTML = '';

    var totalCommodityPages = Math.ceil(commoditiesData.length / commodityItemsPerPage);

    // Previous button
    var prevButton = document.createElement('button');
    prevButton.innerText = 'Previous';
    prevButton.onclick = function () {
        if (currentCommodityPage > 1) {
            displayCommodities(currentCommodityPage - 1);
        }
    };
    paginationContainer.appendChild(prevButton);

    // Page buttons
    for (var i = 1; i <= totalCommodityPages; i++) {
        var pageButton = document.createElement('button');
        pageButton.innerText = i;
        pageButton.onclick = function () {
            displayCommodities(parseInt(this.innerText));
        };

        if (i === currentCommodityPage) {
            pageButton.className = 'active';
        }

        paginationContainer.appendChild(pageButton);
    }

    // Next button
    var nextButton = document.createElement('button');
    nextButton.innerText = 'Next';
    nextButton.onclick = function () {
        if (currentCommodityPage < totalCommodityPages) {
            displayCommodities(currentCommodityPage + 1);
        }
    };
    paginationContainer.appendChild(nextButton);
}

// Helper function to create input elements
function createInput(type, id, label, value, commodityId) {
    var container = document.createElement('div');
    container.className = 'commodity-input-container';

    var labelElement = document.createElement('label');
    labelElement.htmlFor = id + '-' + commodityId;
    labelElement.innerText = label;
    container.appendChild(labelElement);

    var input = document.createElement('input');
    input.type = type;
    input.id = id + '-' + commodityId;
    input.value = value;
    input.disabled = true; // Disable input by default
    container.appendChild(input);

    return container;
}

function modifyCommodity(commodityId) {
    // Similar to modifyPlatform function, modify commodity information here
    var commodityNameInput = document.getElementById('commodity-name-' + commodityId);
    var commodityCategoryInput = document.getElementById('commodity-category-' + commodityId);
    // Add more fields as needed

    commodityNameInput.disabled = false;
    commodityCategoryInput.disabled = false;
    // Disable more fields as needed

    // Create a form for each commodity item
    var updateForm = document.createElement('form');

    // Add a submit button with updateCommodity logic
    var submitButton = document.createElement('button');
    submitButton.type = 'button'; // Change type to 'button'
    submitButton.innerText = 'Submit';
    submitButton.onclick = function () {
        updateCommodity(commodityId);
    };

    // Append the button to the form
    updateForm.appendChild(submitButton);

    // Append the form to commodityActions
    var commodityActions = document.getElementById('commodity-item-' + commodityId).getElementsByClassName('commodity-actions')[0];
    commodityActions.appendChild(updateForm);
}

function updateCommodity(commodityId) {
    var updatedCommodityName = document.getElementById('commodity-name-' + commodityId).value;
    var updatedCommodityCategory = document.getElementById('commodity-category-' + commodityId).value;
    // Get more updated fields as needed

    var apiUrl = 'http://localhost:9000/admin/updateCommodity';

    fetch(apiUrl, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            id: commodityId,
            name: updatedCommodityName,
            category: updatedCommodityCategory,
            // Include more updated fields as needed
        }),
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) {
            alert('Commodity updated successfully.');
            // You may choose to reload the page or update the UI as needed
            window.location.reload();
        } else {
            alert('Failed to update commodity. Please try again.');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('An error occurred. Please try again later.');
    });
}

function deleteCommodity(commodityId) {
    var isConfirmed = confirm("Are you sure you want to delete this commodity?");

    if (isConfirmed) {
        var apiUrl = 'http://localhost:9000/admin/removeCommodity?id=' + commodityId;

        fetch(apiUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
        })
        .then(response => response.json())
        .then(data => {
            if (data.code === 200) {
                alert('Commodity deleted successfully.');
                // You may choose to reload the page or update the UI as needed
                window.location.reload();
            } else {
                alert('Failed to delete commodity. Please try again.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred. Please try again later.');
        });
    }
}

// ...

// Function to show add commodity form
function showCommodityForm() {
    var addCommodityForm = document.getElementById('add-commodity-form');
    addCommodityForm.style.display = 'block';
}

// Function to add commodity
// Function to add a new commodity
function addCommodity(event) {
    event.preventDefault(); // Prevent the default form submission behavior

    // Fetch input values from the form
    var name = document.getElementById('commodity-name').value;
    var category = document.getElementById('commodity-category').value;
    // Add more fields as needed

    // Perform validation on the input values as needed
    if (name.trim() === '' || category.trim() === '') {
        alert('Please enter valid commodity name and category.');
        return;
    }

    // Define the API endpoint for adding a commodity
    var apiUrl = 'http://localhost:9000/admin/addCommodity';

    // Make a POST request to the server to add the commodity
    fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name: name,
            category: category,
            // Include more fields as needed
        }),
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) {
            alert('Commodity added successfully.');
            // Reload the page to reflect the changes
            window.location.reload();
        } else {
            alert('Failed to add commodity. Please try again.');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('An error occurred. Please try again later.');
    });
}

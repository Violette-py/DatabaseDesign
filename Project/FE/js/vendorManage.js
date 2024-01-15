// vendorManage.js

var vendorsData; // Variable to store vendor data
var itemsPerPage = 6; // Number of vendors to display per page
var currentPage = 1;

window.onload = function () {
    loadAllVendors();
};

function loadAllVendors() {
    var apiUrl = 'http://localhost:9000/admin/displayAllVendor';

    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            vendorsData = data.data;
            loadVendorPage(currentPage);
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred. Please try again later.');
        });
}

// Function to load vendors for a specific page
function loadVendorPage(page) {
    var vendorItemsContainer = document.getElementById('vendor-items-container');
    vendorItemsContainer.innerHTML = '';

    var startIndex = (page - 1) * itemsPerPage;
    var endIndex = startIndex + itemsPerPage;
    var vendorsToDisplay = vendorsData.slice(startIndex, endIndex);

    for (var i = 0; i < vendorsToDisplay.length; i++) {
        var vendorItem = document.createElement('div');
        vendorItem.className = 'vendor-item';
        vendorItem.id = 'vendor-item-' + vendorsToDisplay[i].id; // Assign a unique ID to each vendor item

        // Create input elements for vendor information
        var vendorNameInput = createInput('text', 'vendorName', 'Vendor Name:', vendorsToDisplay[i].name, vendorsToDisplay[i].id);
        var vendorAddressInput = createInput('text', 'vendorAddress', 'Vendor Address:', vendorsToDisplay[i].address, vendorsToDisplay[i].id);

        // Create vendor actions
        var vendorActions = document.createElement('div');
        vendorActions.className = 'vendor-actions';
        vendorActions.innerHTML = `<button onclick="modifyVendor(${vendorsToDisplay[i].id})">Modify</button>
                                   <button onclick="deleteVendor('${vendorsToDisplay[i].id}')">Delete</button>`;

        vendorItem.appendChild(vendorNameInput);
        vendorItem.appendChild(vendorAddressInput);
        vendorItem.appendChild(vendorActions);
        vendorItemsContainer.appendChild(vendorItem);
    }

    currentPage = page;

    // Pagination logic
    var paginationContainer = document.getElementById('pagination-container');
    paginationContainer.innerHTML = '';

    var totalPages = Math.ceil(vendorsData.length / itemsPerPage);

    // Previous button
    var prevButton = document.createElement('button');
    prevButton.innerText = 'Previous';
    prevButton.onclick = function () {
        if (currentPage > 1) {
            loadVendorPage(currentPage - 1);
        }
    };
    paginationContainer.appendChild(prevButton);

    // Page buttons
    for (var i = 1; i <= totalPages; i++) {
        var pageButton = document.createElement('button');
        pageButton.innerText = i;
        pageButton.onclick = function () {
            loadVendorPage(parseInt(this.innerText));
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
            loadVendorPage(currentPage + 1);
        }
    };
    paginationContainer.appendChild(nextButton);
}

// Function to show the "Add Vendor" form
function showAddVendorForm() {
    var addVendorForm = document.getElementById('add-vendor-form');
    addVendorForm.style.display = 'block';
}

// Helper function to create input elements
function createInput(type, id, label, value, vendorId) {
    var container = document.createElement('div');
    container.className = 'vendor-input-container';

    var labelElement = document.createElement('label');
    labelElement.htmlFor = id + '-' + vendorId;
    labelElement.innerText = label;
    container.appendChild(labelElement);

    var input = document.createElement('input');
    input.type = type;
    input.id = id + '-' + vendorId;
    input.value = value;
    input.disabled = true; // Disable input by default
    container.appendChild(input);

    return container;
}

// Function to enable input fields when Modify button is clicked
function modifyVendor(vendorId) {
    var vendorNameInput = document.getElementById('vendorName-' + vendorId);
    var vendorAddressInput = document.getElementById('vendorAddress-' + vendorId);

    vendorNameInput.disabled = false;
    vendorAddressInput.disabled = false;

    // Create a form for each vendor item
    var updateForm = document.createElement('form');
    
    // Add a submit button with updateVendor logic
    var submitButton = document.createElement('button');
    submitButton.type = 'button'; // Change type to 'button'
    submitButton.innerText = 'Submit';
    submitButton.onclick = function () {
        updateVendor(vendorId);
    };

    // Append the button to the form
    updateForm.appendChild(submitButton);

    // Append the form to vendorActions
    var vendorActions = document.getElementById('vendor-item-' + vendorId).getElementsByClassName('vendor-actions')[0];
    vendorActions.appendChild(updateForm);
}

function updateVendor(vendorId) {
    var updatedVendorName = document.getElementById('vendorName-' + vendorId).value;
    var updatedVendorAddress = document.getElementById('vendorAddress-' + vendorId).value;

    var apiUrl = 'http://localhost:9000/admin/updateVendor';

    fetch(apiUrl, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            id: vendorId,
            name: updatedVendorName,
            address: updatedVendorAddress,
        }),
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) {
            alert('商家信息更新成功！');
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

function deleteVendor(vendorId) {
    var isConfirmed = confirm("你确定删除此商家吗？");

    if (isConfirmed) {
        var apiUrl = 'http://localhost:9000/admin/removeVendor?id=' + vendorId;

        fetch(apiUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
        })
        .then(response => response.json())
        .then(data => {
            if (data.code === 200) {
                alert('商家删除成功');
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

// Function to add a new vendor
function addVendor(event) {
    event.preventDefault(); // Prevent the form from submitting in the default way

    var vendorName = document.getElementById('vendorName').value;
    var vendorAddress = document.getElementById('vendorAddress').value;

    // Validate input if needed

    var apiUrl = 'http://localhost:9000/admin/addVendor';

    fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name: vendorName,
            address: vendorAddress,
        }),
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) {
            alert('商家添加成功');
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

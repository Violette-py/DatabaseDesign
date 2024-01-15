// Add this script in a separate .js file or within a <script> tag in your HTML

// function initializeSystem() {
//     var isConfirmed = confirm("Are you sure you want to initialize the system?");

//     if (isConfirmed) {
//         var apiUrl = 'http://localhost:9000/admin/init';

//         fetch(apiUrl, {
//             method: 'POST',
//         })
//         .then(response => response.json())
//         .then(data => {
//             if (data.code === 200) {
//                 alert('系统初始化成功');
//             } else {
//                 alert(data.msg);
//             }
//         })
//         .catch(error => {
//             console.error('Error:', error);
//             alert('An error occurred. Please try again later.');
//         });
//     }
// }

function showAddUserForm() {
    document.getElementById('add-user-form').style.display = 'block';
}

function addUser(event) {
    event.preventDefault(); // 阻止表单默认提交行为

    var username = document.getElementById('username').value;
    var age = document.getElementById('age').value;
    var gender = document.getElementById('gender').value;
    var phone = document.getElementById('phone').value;

    var apiUrl = 'http://localhost:9000/admin/addUser';

    fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name: username,
            age: age,
            gender: gender,
            phone: phone,
        }),
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) {
            alert('用户添加成功');
            // 重定向到当前页面
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

// admin.js
var usersData; // 用于存储后端返回的用户数据
var itemsPerPage = 6; // 每页显示的用户数量
var currentPage = 1;

window.onload = function () {
    loadAllUsers();
};

function loadAllUsers() {
    var apiUrl = 'http://localhost:9000/admin/displayAllUser';

    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            usersData = data.data;
            loadUserPage(currentPage);
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred. Please try again later.');
        });
}

function loadUserPage(page) {
    var userItemsContainer = document.getElementById('user-items-container');
    userItemsContainer.innerHTML = '';

    var startIndex = (page - 1) * itemsPerPage;
    var endIndex = startIndex + itemsPerPage;
    var usersToDisplay = usersData.slice(startIndex, endIndex);

    for (var i = 1; i < usersToDisplay.length; i++) {
        var userItem = document.createElement('div');
        userItem.className = 'user-item';
        userItem.id = 'user-item-' + usersToDisplay[i].id; // Assign a unique ID to each user item

        // Create input elements for user information
        var usernameInput = createInput('text', 'username', 'Username:', usersToDisplay[i].name, usersToDisplay[i].id);
        var ageInput = createInput('number', 'age', 'Age:', usersToDisplay[i].age, usersToDisplay[i].id);
        var genderInput = createSelect('gender', 'Gender:', ['male', 'female'], usersToDisplay[i].gender, usersToDisplay[i].id);
        var phoneInput = createInput('tel', 'phone', 'Phone:', usersToDisplay[i].phone, usersToDisplay[i].id);

        // Create user actions
        var userActions = document.createElement('div');
        userActions.className = 'user-actions';
        userActions.innerHTML = `<button onclick="modifyUser(${usersToDisplay[i].id})">Modify</button>
                                <button onclick="deleteUser('${usersToDisplay[i].id}')">Delete</button>`;

        userItem.appendChild(usernameInput);
        userItem.appendChild(ageInput);
        userItem.appendChild(genderInput);
        userItem.appendChild(phoneInput);
        userItem.appendChild(userActions);
        userItemsContainer.appendChild(userItem);
    }

    currentPage = page;

    // 在这里添加分页按钮的逻辑
    var paginationContainer = document.getElementById('pagination-container');
    paginationContainer.innerHTML = '';

    var totalPages = Math.ceil(usersData.length / itemsPerPage);

    // 添加总页数显示
    var totalPagesSpan = document.createElement('span');
    totalPagesSpan.innerHTML = 'Total Pages: ' + totalPages;
    paginationContainer.appendChild(totalPagesSpan);

    // 添加上一页按钮
    var prevButton = document.createElement('button');
    prevButton.innerText = 'Previous';
    prevButton.onclick = function () {
        if (currentPage > 1) {
            loadUserPage(currentPage - 1);
        }
    };
    paginationContainer.appendChild(prevButton);

    // 添加分页按钮
    for (var i = 1; i <= totalPages; i++) {
        var pageButton = document.createElement('button');
        pageButton.innerText = i;
        pageButton.onclick = function () {
            loadUserPage(parseInt(this.innerText));
        };

        if (i === currentPage) {
            pageButton.className = 'active';
        }

        paginationContainer.appendChild(pageButton);
    }

    // 添加下一页按钮
    var nextButton = document.createElement('button');
    nextButton.innerText = 'Next';
    nextButton.onclick = function () {
        if (currentPage < totalPages) {
            loadUserPage(currentPage + 1);
        }
    };
    paginationContainer.appendChild(nextButton);
}

// Helper function to create input elements
function createInput(type, id, label, value, userId) {
    var container = document.createElement('div');
    container.className = 'user-input-container';

    var labelElement = document.createElement('label');
    labelElement.htmlFor = id + '-' + userId;
    labelElement.innerText = label;
    container.appendChild(labelElement);

    var input = document.createElement('input');
    input.type = type;
    input.id = id + '-' + userId;
    input.value = value;
    input.disabled = true; // Disable input by default
    container.appendChild(input);

    return container;
}

// Helper function to create select elements
function createSelect(id, label, options, selectedValue, userId) {
    var container = document.createElement('div');
    container.className = 'user-input-container';

    var labelElement = document.createElement('label');
    labelElement.htmlFor = id + '-' + userId;
    labelElement.innerText = label;
    container.appendChild(labelElement);

    var select = document.createElement('select');
    select.id = id + '-' + userId;
    container.appendChild(select);

    for (var i = 0; i < options.length; i++) {
        var option = document.createElement('option');
        option.value = options[i];
        option.text = options[i];

        if (options[i] === selectedValue) {
            option.selected = true;
            option.disabled = true;
        }

        select.add(option);
    }
    select.disabled = true; // Disable select by default

    return container;
}

// Function to enable input fields when Modify button is clicked
function modifyUser(userId) {
    var usernameInput = document.getElementById('username-' + userId);
    var ageInput = document.getElementById('age-' + userId);
    var genderSelect = document.getElementById('gender-' + userId);
    var phoneInput = document.getElementById('phone-' + userId);

    usernameInput.disabled = false;
    ageInput.disabled = false;
    genderSelect.disabled = false;
    phoneInput.disabled = false;

    // Create a form for each user item
    var updateForm = document.createElement('form');
    
    // Add a submit button with updateUser logic
    var submitButton = document.createElement('button');
    submitButton.type = 'button'; // Change type to 'button'
    submitButton.innerText = 'Submit';
    submitButton.onclick = function () {
        updateUser(userId);
    };

    // Append the button to the form
    updateForm.appendChild(submitButton);

    // Append the form to userActions
    var userActions = document.getElementById('user-item-' + userId).getElementsByClassName('user-actions')[0];
    userActions.appendChild(updateForm);
}

function updateUser(userId) {
    var updatedUsername = document.getElementById('username-' + userId).value;
    var updatedAge = document.getElementById('age-' + userId).value;
    var updatedGender = document.getElementById('gender-' + userId).value;
    var updatedPhone = document.getElementById('phone-' + userId).value;

    var apiUrl = 'http://localhost:9000/admin/updateUser';

    fetch(apiUrl, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            id: userId,
            name: updatedUsername,
            age: updatedAge,
            gender: updatedGender,
            phone: updatedPhone,
        }),
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) {
            alert('用户信息更改成功');
            // You may choose to reload the page or update the UI as needed
            window.location.reload();
            // loadUserPage(currentPage); // Uncomment this if needed
        } else {
            alert(data.msg);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('An error occurred. Please try again later.');
    });
}

function deleteUser(userId) {
    var isConfirmed = confirm("你确定删除此用户吗？");

    if (isConfirmed) {
        var apiUrl = 'http://localhost:9000/admin/removeUser?id=' + userId;

        fetch(apiUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            // Note: No need to include body for a GET or POST request with parameters in the URL
        })
        .then(response => response.json())
        .then(data => {
            if (data.code === 200) {
                alert('用户删除成功');
                // You may choose to reload the page or update the UI as needed
                window.location.reload();
                // loadUserPage(currentPage); // Uncomment this if needed
            } else {
                alert('data.msg');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred. Please try again later.');
        });
    }
}

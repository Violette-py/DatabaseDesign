var saleCommoditiesData; // Variable to store saleCommodities data
var itemsPerPage = 4; // Number of saleCommodities to display per page
var currentPage = 1;

// Call the function when the page loads
window.onload = function () {
    displaySaleCommodities();
};

// Function to fetch saleCommodities data and display them
function displaySaleCommodities(page) {
    var saleCommodityItemsContainer = document.getElementById('saleCommodity-items-container');

    // Call the API to get saleCommodities data
    fetch(`http://localhost:9000/user/displayAllSaleCommodity?page=${page || 1}&pageSize=${itemsPerPage}`)
        .then(response => response.json())
        .then(data => {
            saleCommoditiesData = data.data;
            loadSaleCommodityPage(currentPage);
        })
        .catch(error => {
            console.error('Error fetching saleCommodities:', error);
            alert('An error occurred. Please try again later.');
        });
}

// Function to load saleCommodities for a specific page
function loadSaleCommodityPage(page) {
    var saleCommodityItemsContainer = document.getElementById('saleCommodity-items-container');
    saleCommodityItemsContainer.innerHTML = '';

    var startIndex = (page - 1) * itemsPerPage;
    var endIndex = startIndex + itemsPerPage;
    var saleCommoditiesToDisplay = saleCommoditiesData.slice(startIndex, endIndex);

    saleCommoditiesToDisplay.forEach(saleCommodity => {
        var saleCommodityItem = document.createElement('div');
        saleCommodityItem.className = 'saleCommodity-item';
        saleCommodityItem.id = 'saleCommodity-item-' + saleCommodity.id; // Assign a unique ID to each saleCommodity item

        // Create information rows
        createSaleCommodityRow(saleCommodityItem, 'Name', saleCommodity.name, true);
        createSaleCommodityRow(saleCommodityItem, 'Category', saleCommodity.category, true);
        createSaleCommodityRow(saleCommodityItem, 'Vendor', saleCommodity.vendorName, true);
        createSaleCommodityRow(saleCommodityItem, 'Platform', saleCommodity.platformName, true);
        createSaleCommodityRow(saleCommodityItem, 'Date', saleCommodity.date, true);
        createSaleCommodityRow(saleCommodityItem, 'Location', saleCommodity.place, true);
        createSaleCommodityRow(saleCommodityItem, 'Price', saleCommodity.price, true);
        createSaleCommodityRow(saleCommodityItem, 'Descript', saleCommodity.description, true);

        // Create buttons
        var buttonsContainer = document.createElement('div');
        buttonsContainer.className = 'saleCommodity-buttons';

        var modifyButton = document.createElement('button');
        modifyButton.className = 'modify-button';
        modifyButton.innerText = 'Modify';
        modifyButton.onclick = function () {
            // Call your modify function here
            modifySaleCommodity(saleCommodity.id); // Modify function needs to be defined
        };

        var modifyPriceButton = document.createElement('button');
        modifyPriceButton.className = 'modify-button';
        modifyPriceButton.innerText = 'Modify Price';
        modifyPriceButton.onclick = function () {
            // Call your modify price function here
            modifySaleCommodityPrice(saleCommodity.id); // Modify price function needs to be defined
        };

        var deleteButton = document.createElement('button');
        deleteButton.className = 'delete-button';
        deleteButton.innerText = 'Delete';
        deleteButton.onclick = function () {
            // Call your delete function here
            deleteSaleCommodity(saleCommodity.id); // Delete function needs to be defined
        };

        buttonsContainer.appendChild(modifyButton);
        buttonsContainer.appendChild(modifyPriceButton);
        buttonsContainer.appendChild(deleteButton);
        saleCommodityItem.appendChild(buttonsContainer);

        saleCommodityItemsContainer.appendChild(saleCommodityItem);
    });

    currentPage = page;

    // Pagination logic
    var paginationContainer = document.getElementById('pagination-container');
    paginationContainer.innerHTML = '';

    var totalPages = Math.ceil(saleCommoditiesData.length / itemsPerPage);

    // Previous button
    var prevButton = document.createElement('button');
    prevButton.innerText = 'Previous';
    prevButton.onclick = function () {
        if (currentPage > 1) {
            loadSaleCommodityPage(currentPage - 1);
        }
    };
    paginationContainer.appendChild(prevButton);

    // // Display current page and total pages
    // var pageInfoContainer = document.createElement('div');
    // pageInfoContainer.id = 'page-info-container'; // Make sure to set the id to 'page-info-container' in your HTML
    // pageInfoContainer.innerText = `${currentPage}/${totalPages} 页`;
    // paginationContainer.appendChild(pageInfoContainer);

    // Next button
    var nextButton = document.createElement('button');
    nextButton.innerText = 'Next';
    nextButton.onclick = function () {
        if (currentPage < totalPages) {
            loadSaleCommodityPage(currentPage + 1);
        }
    };
    paginationContainer.appendChild(nextButton);
    
}

function createSaleCommodityRow(container, label, value, isDisabled) {
    var row = document.createElement('div');
    row.className = 'saleCommodity-item-row';

    var labelElement = document.createElement('label');
    labelElement.className = 'row-label';
    labelElement.innerText = label + ': ';

    var valueElement = document.createElement('input'); // 仍然使用输入框展示信息
    valueElement.className = 'row-value';
    valueElement.value = value;
    valueElement.disabled = isDisabled; // 是否禁用
    valueElement.setAttribute('data-label', label);

    // 将 label 和 value 放在同一行
    row.appendChild(labelElement);
    row.appendChild(valueElement);

    container.appendChild(row);
}

// Function to modify the price of a saleCommodity
function modifySaleCommodityPrice(saleCommodityId) {
    var saleCommodityItem = document.getElementById('saleCommodity-item-' + saleCommodityId);
    if (!saleCommodityItem) {
        console.error('SaleCommodity item not found in the DOM.');
        return;
    }
    // Find the input element for price
    var priceInput = saleCommodityItem.querySelector('.row-value[data-label="Price"]');

    // Enable the price input for modification
    priceInput.disabled = false;

    // Create a submit button
    var submitButton = document.createElement('button');
    submitButton.innerText = 'Submit';
    submitButton.onclick = function () {
        // Call the function to update the saleCommodity price
        submitSaleCommodityPrice(saleCommodityId, priceInput.value);
    };

    // Append the submit button to the saleCommodity item
    saleCommodityItem.querySelector('.saleCommodity-buttons').appendChild(submitButton);
}

// Function to submit the modified saleCommodity price
function submitSaleCommodityPrice(saleCommodityId, newPrice) {
    // API endpoint for updating saleCommodity price
    var apiUrl = `http://localhost:9000/admin/updateSaleCommodityPrice?id=${saleCommodityId}&price=${newPrice}`;

    // Make a PUT request to update the saleCommodity price
    fetch(apiUrl, {
        method: 'PUT',
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) {
            alert('价格更新成功');
            // You may choose to reload the page or update the UI as needed
            window.location.reload();
        } else {
            alert(data.msg);
        
            window.location.reload();
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('An error occurred. Please try again later.');
    });
}

function modifySaleCommodity(saleCommodityId) {
    var saleCommodityItem = document.getElementById('saleCommodity-item-' + saleCommodityId);

    if (!saleCommodityItem) {
        console.error('SaleCommodity item not found in the DOM.');
        return;
    }

    // 启用所有输入元素进行修改，除了 '价格'
    var inputElements = saleCommodityItem.querySelectorAll('.row-value');
    inputElements.forEach(function (input) {
        var dataLabel = input.getAttribute('data-label');
        if (dataLabel && dataLabel.trim().toLowerCase() !== 'price'
        && dataLabel.trim().toLowerCase() !== 'vendor'
        && dataLabel.trim().toLowerCase() !== 'platform'
        && dataLabel.trim().toLowerCase() !== 'category') {
            input.disabled = false;
        } else {
            input.disabled = true;
        }
    });

    // Remove existing submit button if any
    var existingSubmitButton = saleCommodityItem.querySelector('.submit-button');
    if (existingSubmitButton) {
        existingSubmitButton.remove();
    }

    // Create a submit button
    var submitButton = document.createElement('button');
    submitButton.className = 'submit-button';
    submitButton.innerText = 'Submit';
    submitButton.onclick = function () {
        // Call the function to update the saleCommodity information
        submitSaleCommodityInfo(saleCommodityId);
    };

    // Append the submit button to the saleCommodity item
    saleCommodityItem.querySelector('.saleCommodity-buttons').appendChild(submitButton);
}

function submitSaleCommodityInfo(saleCommodityId) {
    var saleCommodityItem = document.getElementById('saleCommodity-item-' + saleCommodityId);

    if (!saleCommodityItem) {
        console.error('SaleCommodity item not found in the DOM.');
        return;
    }

    // Extract updated information from input elements
    var updatedInfo = {};

    // Enable all input elements for modification
    var inputElements = saleCommodityItem.querySelectorAll('.row-value');
    inputElements.forEach(function (input) {
        var dataLabel = input.getAttribute('data-label');
        console.log(dataLabel)
        //这里还未对应
        dataLabel = dataLabel.trim().toLowerCase()
        if(dataLabel== 'vendor'){
            dataLabel = 'vendorName';
        }
        if(dataLabel == 'platform'){
            dataLabel = 'platformName';
        }
        if(dataLabel == 'location'){
            dataLabel = 'place';
        }
        if(dataLabel == 'descript'){
            dataLabel = 'description';
        }

    
        updatedInfo[dataLabel] = input.value;
    });

    // Add the saleCommodityId to the updatedInfo object
    updatedInfo.id = saleCommodityId;
    
    // Prepare the updatedInfo object for the PUT request
    var requestBody = JSON.stringify(updatedInfo);
    // alert(requestBody)
    // Send a PUT request to update the saleCommodity information
    fetch('http://localhost:9000/vendor/updateBasicInfo', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: requestBody,
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) {
            alert('基本信息更新成功');
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

function deleteSaleCommodity(saleCommodityId) {
    var isConfirmed = confirm("确定要删除此商品吗？");

    if (isConfirmed) {
        var apiUrl = `http://localhost:9000/vendor/removeSaleCommodity?id=${saleCommodityId}`;

        fetch(apiUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
        })
        .then(response => response.json())
        .then(data => {
            if (data.code === 200) {
                alert('商品删除成功。');
                // 可以选择重新加载页面或根据需要更新UI
                window.location.reload();
            } else {
                alert('删除商品失败，请重试。');
            }
        })
        .catch(error => {
            console.error('错误：', error);
            alert('发生错误，请稍后再试。');
        });
    }
}


// saleCommodity.js

// ... 省略前面的代码 ...

// Function to display vendors and platforms as checkboxes
function showAddSaleCommodityForm() {
    var addSaleCommodityForm = document.getElementById('add-saleCommodity-form');
    addSaleCommodityForm.style.display = 'block';

    // Display commodities as options
    displayCommoditiesAsOptions();

    // Display vendors as checkboxes
    displayVendorsAsDropdown();

    // Display platforms as checkboxes
    displayPlatformsAsDropdown();
}

// Function to fetch commodities data and display them as options
function displayCommoditiesAsOptions() {
    return new Promise((resolve, reject) => {
        fetch('http://localhost:9000/admin/displayAllCommodity')
            .then(response => response.json())
            .then(data => {
                var commodityListContainer = document.getElementById('commodity-list');
                commodityListContainer.innerHTML = '';

                var select = document.createElement('select');
                select.id = 'selected-commodity';
                select.name = 'selected-commodity';

                // Create an object to store commodity information
                var commodityInfo = {};

                data.data.forEach(commodity => {
                    var option = document.createElement('option');
                    option.value = commodity.id; // Set the value to the commodity ID
                    option.innerText = commodity.name;

                    // Store commodity ID and category as properties in the commodityInfo object
                    commodityInfo[commodity.id] = {
                        id: commodity.id,
                        category: commodity.category,
                    };

                    select.appendChild(option);
                });

                // Attach commodityInfo object to the selected-commodity select element
                select.commodityInfo = commodityInfo;

                commodityListContainer.appendChild(select);

                resolve(); // Resolve the promise when options are displayed
            })
            .catch(error => {
                console.error('Error fetching commodities:', error);
                reject(error); // Reject the promise if there is an error
            });
    });
}

// Function to display vendors data and bind them to a dropdown
function displayVendorsAsDropdown() {
    return new Promise((resolve, reject) => {
        fetch('http://localhost:9000/admin/displayAllVendor')
            .then(response => response.json())
            .then(data => {
                var vendorDropdownContainer = document.getElementById('vendor-list');
                vendorDropdownContainer.innerHTML = '';

                var select = document.createElement('select');
                select.id = 'selected-vendor';
                select.name = 'selected-vendor';

                // Create an object to store vendor information
                var vendorInfo = {};

                data.data.forEach(vendor => {
                    var option = document.createElement('option');
                    option.value = vendor.name; // Set the value to the vendor ID
                    option.innerText = vendor.name;

                    // Store vendor ID as a property in the vendorInfo object
                    vendorInfo[vendor.id] = {
                        id: vendor.id,
                        // You can add more properties here if needed
                    };

                    select.appendChild(option);
                });

                // Attach vendorInfo object to the selected-vendor select element
                select.vendorInfo = vendorInfo;

                vendorDropdownContainer.appendChild(select);

                resolve(); // Resolve the promise when options are displayed
            })
            .catch(error => {
                console.error('Error fetching vendors:', error);
                reject(error); // Reject the promise if there is an error
            });
    });
}

// Function to display platforms data and bind them to a dropdown
function displayPlatformsAsDropdown() {
    return new Promise((resolve, reject) => {
        fetch('http://localhost:9000/admin/displayAllPlatform')
            .then(response => response.json())
            .then(data => {
                var platformDropdownContainer = document.getElementById('platform-list');
                platformDropdownContainer.innerHTML = '';

                var select = document.createElement('select');
                select.id = 'selected-platform';
                select.name = 'selected-platform';

                // Create an object to store platform information
                var platformInfo = {};

                data.data.forEach(platform => {
                    var option = document.createElement('option');
                    option.value = platform.name; // Set the value to the platform ID
                    option.innerText = platform.name;

                    // Store platform ID as a property in the platformInfo object
                    platformInfo[platform.id] = {
                        id: platform.id,
                        // You can add more properties here if needed
                    };

                    select.appendChild(option);
                });

                // Attach platformInfo object to the selected-platform select element
                select.platformInfo = platformInfo;

                platformDropdownContainer.appendChild(select);

                resolve(); // Resolve the promise when options are displayed
            })
            .catch(error => {
                console.error('Error fetching platforms:', error);
                reject(error); // Reject the promise if there is an error
            });
    });
}
function addSaleCommodity(event) {
    // 阻止表单的默认提交行为
    event.preventDefault();

    // 获取输入框中的值
    var name = document.getElementById('saleCommodityName').value;
    var date = document.getElementById('saleCommodityDate').value;
    var place = document.getElementById('saleCommodityPlace').value;
    var price = document.getElementById('saleCommodityPrice').value;
    var description = document.getElementById('saleCommodityDescription').value;

    // 获取选中的平台（platform）和供应商（vendor）
    var selectedPlatformId = document.getElementById('selected-platform').value;
    var selectedVendorId = document.getElementById('selected-vendor').value;

    // 获取选中的商品（commodity）的ID和类别
    var selectedCommodityId = document.getElementById('selected-commodity').value;
    var selectedCommodityCategory = document.getElementById('selected-commodity').commodityInfo[selectedCommodityId].category;

    // 创建 SaleCommodity 对象
    var saleCommodity = {
        name: name,
        date: date,
        place: place,
        price: price,
        description: description,
        platformName: selectedPlatformId,
        vendorName: selectedVendorId,
        commodityId: selectedCommodityId,
        category: selectedCommodityCategory
        // 还可以添加其他属性，根据 SaleCommodity 对象的定义
    };

    // 将 SaleCommodity 对象发送到后端
    submitSaleCommodity(saleCommodity);
}

// 提交 SaleCommodity 对象到后端
function submitSaleCommodity(saleCommodity) {
    // 准备 SaleCommodity 对象的 JSON 格式
    var requestBody = JSON.stringify(saleCommodity);
    // alert(requestBody)
    // 发送 POST 请求到后端
    fetch('http://localhost:9000/vendor/addSaleCommodity', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: requestBody,
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) {
            alert('SaleCommodity 添加成功。');
            // 可以选择重新加载页面或根据需要更新UI
            window.location.reload();
        } else {
            alert('添加 SaleCommodity 失败，请重试。');
        }
    })
    .catch(error => {
        console.error('错误：', error);
        alert('发生错误，请稍后再试。');
    });
}

var searchTerm
var myChart
var pricedifference

document.addEventListener('DOMContentLoaded', function () {
    // Retrieve search term from the URL parameter
    var searchParams = new URLSearchParams(window.location.search);
     searchTerm = searchParams.get('term');

    // Set the value of the search-term span
    document.getElementById('search-term').innerText = "为你找到"+searchTerm+"的上架商品数据啦";
    // Fetch and display search results
    fetchSearchResults(searchTerm);

    // Fetch and populate platform dropdown
    fetchPlatforms();

    // Populate time range dropdown
    populateTimeRangeDropdown();
});


function fetchSearchResults(searchTerm) {
    // Send a request to the backend with the search term
    // Example using fetch:
    fetch('http://localhost:9000/user/search?name=' + encodeURIComponent(searchTerm))
        .then(response => response.json())
        .then(data => displayProducts(data.data))
        .catch(error => console.error('Error fetching search results:', error));
}

function fetchPlatforms() {
    // Send a request to the backend to get all platforms
    // Example using fetch:
    fetch('http://localhost:9000/admin/displayAllPlatform')
        .then(response => response.json())
        .then(data => populatePlatformCheckboxes(data.data))
        .catch(error => console.error('Error fetching platforms:', error));
}

function populatePlatformCheckboxes(platforms) {
    // Populate the platform checkboxes with the fetched data
    var platformCheckboxes = document.getElementById('search-range');

    // Create checkboxes for each platform
    platforms.forEach(platform => {
        var checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.id = 'platform-' + platform.name;
        checkbox.value = platform.name;
        platformCheckboxes.appendChild(checkbox);

        var label = document.createElement('label');
        label.htmlFor = 'platform-' + platform.name;
        label.innerText = platform.name;
        platformCheckboxes.appendChild(label);
        // 添加事件监听器
        checkbox.addEventListener('change', handlePlatformCheckboxChange);
    });
}

var timeSpan = '1 week'
function populateTimeRangeDropdown() {
    // Populate the time range dropdown with predefined values
    var timeRangeDropdown = document.getElementById('time-range-dropdown');

    // Create a select element
    var select = document.createElement('select');
    select.id = 'time-range-select';
    select.name = 'time-range';

    // Create a default option with a prompt message
    var defaultOption = document.createElement('option');
    defaultOption.value = '';
    defaultOption.text = '选择时间跨度';
    select.appendChild(defaultOption);

    // Define predefined time ranges
    var timeRanges = ['近一周', '近一月', '近一年'];

    // Create and add an option for each time range
    timeRanges.forEach(range => {
        var option = document.createElement('option');
        option.value = range;
        option.text = range;
        select.appendChild(option);
    });

    // Replace the existing dropdown with the new select element
    timeRangeDropdown.parentNode.replaceChild(select, timeRangeDropdown);
}
document.addEventListener('DOMContentLoaded', function () {

    var timeRangeSelect = document.getElementById('time-range-select');
    timeRangeSelect.addEventListener('change', function () {
        // 获取用户选择的值
        var selectedRange = timeRangeSelect.value;

        // 转换为后端所需的格式
        timeSpan = convertRangeToTime(selectedRange);

        // 重新调用拿商品的函数
        fetchSearchResults(searchTerm)
    });

    // ... （其他初始化代码）
});

// Function to convert selectedRange to time duration
function convertRangeToTime(selectedRange) {
    switch (selectedRange) {
        case '近一周':
            return '1 week';
        case '近一月':
            return '1 month';
        case '近一年':
            return '1 year';
        default:
            return null;
    }
}


// Global variable to store price differences
var priceDifferences = {};

async function displayProducts(data) {
    // 清空product-display中的内容
    var productDisplay = document.getElementById('product-display');
    productDisplay.innerHTML = '';

    // 遍历后端返回的数据，创建product-item并添加到product-display中
    for (const product of data) {
        var productItem = document.createElement('div');
        productItem.className = 'product-item';
        productItem.setAttribute('data-product-id', product.id);


        // 创建 product-info 盒子
        var productInfoBox = document.createElement('div');
        productInfoBox.className = 'product-info';
        var productFullinfoBox = document.createElement('div');
        productFullinfoBox.className = 'product--full-info';

        // 创建 Name 元素
        var nameElement = document.createElement('div');
        nameElement.className = 'product-name';
        nameElement.innerText = 'Name: ' + product.name;
        productInfoBox.appendChild(nameElement);

        // 创建商家名元素
        var vendorElement = document.createElement('div');
        vendorElement.className = 'product-vendor';
        vendorElement.innerText = '商家名: ' + product.vendorName;
        productInfoBox.appendChild(vendorElement);

        // 创建平台名元素
        var platformElement = document.createElement('div');
        platformElement.className = 'product-platform';
        platformElement.innerText = '平台名: ' + product.platformName;
        productInfoBox.appendChild(platformElement);

        // 创建价格元素
        var priceElement = document.createElement('div');
        priceElement.className = 'product-price';
        priceElement.innerText = '价格: ' + product.price;
        productInfoBox.appendChild(priceElement);

        // 将 productInfoBox 添加到 product-item 中
        productItem.appendChild(productInfoBox);

        // 创建价格历史盒子
        var priceHistoryBox = document.createElement('div');
        priceHistoryBox.className = 'price-history-box';
        productFullinfoBox.appendChild(productInfoBox);
        productFullinfoBox.appendChild(priceHistoryBox);
        productItem.appendChild(productFullinfoBox);

        // 创建"Learn more"按钮
        var learnMoreButton = document.createElement('button');
        learnMoreButton.className = 'learn-more-button';
        learnMoreButton.innerText = 'Learn more';

        // Add a click event listener to the "Learn more" button
        learnMoreButton.addEventListener('click', async function () {
            // Get the product ID
            var productId = product.id;

            // Construct the URL with the product ID as a parameter
            var url = 'saleCommodity.html?id=' + encodeURIComponent(productId);

            // Redirect to the new page
            window.location.href = url;
        });

        // Append the "Learn more" button to the productItem
        productItem.appendChild(learnMoreButton);


        // 调用createPriceHistoryChart，并等待其完成
        const diff = await createPriceHistoryChart(
            priceHistoryBox,
            product.id
        );

        //不用存没有价格差的
        if(diff!=-1){
            priceDifferences[product.id] = diff;
        }
        

        // 将productItem添加到product-display中
        productDisplay.appendChild(productItem);
    }

    // Update the display of global minimum and maximum price differences in the corresponding product boxes
    updateProductPriceDifferences();
}

async function createPriceHistoryChart(container, saleCommodityId) {
    // Destroy previous chart instance
    if (myChart) {
        myChart.destroy();
    }

    try {
        // 调用后端API获取价格历史数据
        const response = await fetch(`http://localhost:9000/user/displayHistoryPrice?saleCommodityId=${saleCommodityId}&timeSpan=${timeSpan}`);
        const data = await response.json();
        console.log("我在这里")
        // 检查数据是否存在
        if (data && data.data) {
            // 提取时间戳和价格数据
            const dataPoints = data.data.map(entry => ({ time: entry.time, price: entry.price }));
            dataPoints.sort((a, b) => new Date(a.time) - new Date(b.time));

            const prices = dataPoints.map(entry => entry.price);

            // 清空container内容
            container.innerHTML = '';

            // 遍历每一条历史价格记录，并将其添加到container中
            dataPoints.forEach(entry => {
                const entryElement = document.createElement('p');
                entryElement.innerText = `${entry.time}: ￥${entry.price}`;
                container.appendChild(entryElement);
            });

            var priceDifferenceElement = document.createElement('p');
            priceDifferenceElement.id = 'pricediff' + saleCommodityId;
            const minPrice = Math.min(...prices);
            const maxPrice = Math.max(...prices);

            let diff;  // Declare diff outside the if block

            if (data.data.length > 1) {
                diff = (maxPrice - minPrice).toFixed(2);

                // 更新全局最小和最大价格差异
                priceDifferenceElement.innerText = '价格差: ' + diff;
            } else {
                diff = -1;
                priceDifferenceElement.innerText = '价格差: 无';
            }
            container.appendChild(priceDifferenceElement);

            // 返回价格差异
            return parseFloat(diff);
        } else {
            console.error('No historical price data found.');
            // 可以添加错误处理或显示消息
        }
    } catch (error) {
        console.error('Error fetching historical prices:', error);
        // 可以添加错误处理或显示消息
    }
}

function updateProductPriceDifferences() {
    // 遍历价格差异，更新对应产品框中的全局最小和最大价格差异
    let globalMinPriceDiff = Number.MAX_SAFE_INTEGER;
    let globalMaxPriceDiff = Number.MIN_SAFE_INTEGER;
    let globalMinPriceDiffProductId = null;
    let globalMaxPriceDiffProductId = null;

    for (const productId in priceDifferences) {
        const diff = priceDifferences[productId];

        if (diff < globalMinPriceDiff) {
            globalMinPriceDiff = diff;
            globalMinPriceDiffProductId = productId;
        }

        if (diff > globalMaxPriceDiff) {
            globalMaxPriceDiff = diff;
            globalMaxPriceDiffProductId = productId;
        }

        // // 更新对应产品框中的价格差异显示
        // updateProductPriceDifference(productId, diff);
    }

    // Update the display of global minimum and maximum price differences
    updateProductPriceDifference(globalMinPriceDiffProductId, "价格差最小");
    updateProductPriceDifference(globalMaxPriceDiffProductId, "价格差最大");
}

function updateProductPriceDifference(productId, diff) {
    // Update the product box with the corresponding product ID
    const productBox = document.querySelector(`[data-product-id="${productId}"]`);
    if (productBox) {
        // Create and append an element to display the price difference in the product box
        const priceDiffElement = document.createElement('p');
        priceDiffElement.innerText = diff;
        productBox.appendChild(priceDiffElement);
    }
}

// 监听每个 checkbox 变化的函数
function handlePlatformCheckboxChange() {
    // 获取所有选中的平台名
    var selectedPlatforms = getSelectedPlatforms();

    // 重新调用 fetchSearchPlatformResults 函数
    fetchSearchPlatformResults(selectedPlatforms);
}

// 获取选中的平台名数组
function getSelectedPlatforms() {
    var platformCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    var selectedPlatforms = Array.from(platformCheckboxes).map(function (checkbox) {
        return checkbox.value;
    });

    return selectedPlatforms.join(',');
}

function fetchSearchPlatformResults(selectedPlatforms) {
    priceDifferences = {}
    // 发送请求到后端，传递搜索词和选中的平台名
    fetch('http://localhost:9000/user/searchInPlatform?name=' + encodeURIComponent(searchTerm) + '&platformName=' + encodeURIComponent(selectedPlatforms))
        .then(response => response.json())
        .then(data => displayProducts(data.data))
        .catch(error => console.error('Error fetching search results:', error));
}

console.log(pricedifference)
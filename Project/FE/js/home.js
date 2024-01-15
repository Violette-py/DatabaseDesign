// home.js

// Function to fetch saleCommodity data and display them
function displaySaleCommodities() {
    // Call the API to get saleCommodities data
    fetch('http://localhost:9000/user/displayAllSaleCommodity')
        .then(response => response.json())
        .then(data => {
            // Check if data is present
            if (data && data.data) {
                // Get the product-display container
                var productDisplayContainer = document.getElementById('product-display');

                // Clear existing content
                productDisplayContainer.innerHTML = '';

                // 随机排序 saleCommodities 数据
                var shuffledSaleCommodities = shuffleArray(data.data);

                // Iterate over the first ten saleCommodities
                for (let i = 0; i < Math.min(10, shuffledSaleCommodities.length); i++) {
                    var saleCommodity = shuffledSaleCommodities[i];

                    // Create a product-item container
                    var productItem = document.createElement('div');
                    productItem.className = 'product-item';

                    // Create a product-info container
                    var productInfo = document.createElement('div');
                    productInfo.className = 'product-info';

                    // Populate product-info with saleCommodity details
                    productInfo.innerHTML = `
                        <p>Name: ${saleCommodity.name}</p>
                        <p>Vendor: ${saleCommodity.vendorName}</p>
                        <p>Platform: ${saleCommodity.platformName}</p>
                        <p>Price: ${saleCommodity.price}</p>
                        <!-- Add more details as needed -->
                    `;

                    // Append product-info to product-item
                    productItem.appendChild(productInfo);

                    // Create "Learn More" link
                    var learnMoreLink = document.createElement('a');
                    learnMoreLink.href = `saleCommodity.html?id=${saleCommodity.id}`; // Add appropriate query parameters
                    learnMoreLink.innerText = 'Learn More';

                    // Append Learn More link to product-item
                    productItem.appendChild(learnMoreLink);

                    // Append product-item to product-display
                    productDisplayContainer.appendChild(productItem);
                }
            } else {
                console.error('No saleCommodity data found');
            }
        })
        .catch(error => {
            console.error('Error fetching saleCommodities:', error);
            alert('An error occurred. Please try again later.');
        });
}

// 辅助函数：对数组进行随机排序
function shuffleArray(array) {
    var shuffledArray = array.slice(); // 创建一个副本以防修改原数组
    for (let i = shuffledArray.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [shuffledArray[i], shuffledArray[j]] = [shuffledArray[j], shuffledArray[i]];
    }
    return shuffledArray;
}

// Call the function to display saleCommodities when the page loads
window.onload = function () {
    displaySaleCommodities();
};
function loadNextBatch() {
    // 重新调用 displaySaleCommodities 函数以显示下一批 saleCommodities
    displaySaleCommodities();
}
function searchProducts() {
    var searchTerm = document.getElementById('search-input').value;

    // 通过 window.location.href 进行页面跳转，将搜索词作为参数传递给 search.html
    window.location.href = 'search.html?term=' + encodeURIComponent(searchTerm);
}

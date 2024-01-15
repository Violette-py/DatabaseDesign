
function showDistribution() {
    // Make an HTTP GET request to the backend endpoint
    fetch('http://localhost:9000/user/displayFavorDistributionByAgeRange')
        .then(response => {
            // Check if the request was successful (status code 200)
            return response.json();
        })
        .then(data => {
            clearHTML()
            updateDistributionPage(data.data);
        })
        .catch(error => {
            // Handle errors, e.g., network issues or backend errors
            console.error('Error fetching data:', error);
        });
}
function updateDistributionPage(data) {
    const distributionPage = document.getElementById('distribution-page');

    // Clear previous content
    distributionPage.innerHTML = '';

    // Create and append a table element
    const table = document.createElement('table');
    table.className = 'simple-table';

    // Create the table header
    const thead = document.createElement('thead');
    const headerRow = document.createElement('tr');
    headerRow.innerHTML = '<th>年龄段</th><th>最喜欢的商品</th><th>用户数量</th>';
    thead.appendChild(headerRow);
    table.appendChild(thead);

    // Create the table body and add rows
    const tbody = document.createElement('tbody');
    data.forEach(entry => {
        const row = document.createElement('tr');

        // Column: 年龄段
        const ageRangeCell = document.createElement('td');
        ageRangeCell.innerText = `${entry.ageStart}-${entry.ageEnd}`;
        row.appendChild(ageRangeCell);

        // Column: 最喜欢的商品
        const commodityCell = document.createElement('td');
        commodityCell.innerHTML = `${entry.saleCommodity.name} 
            <br>
            <a href="saleCommodity.html?id=${entry.saleCommodity.id}" target="_blank">Learn More</a>`;
        row.appendChild(commodityCell);

        // Column: 用户数量
        const userNumCell = document.createElement('td');
        userNumCell.innerText = entry.userNum;
        row.appendChild(userNumCell);

        tbody.appendChild(row);
    });
    table.appendChild(tbody);

    // Append the table to the distributionPage
    distributionPage.appendChild(table);
}
function clearHTML(){
    //清空内容

    var distributionPage= document.getElementById('distribution-page');
    distributionPage.innerHTML = '';
      
    var  saleCommoditiesPage = document.getElementById('saleCommodities-page');
    saleCommoditiesPage.innerHTML = '';
  

    var platformsPage =  document.getElementById('platforms-page');
    platformsPage.innerHTML = '';

}

function showTopSaleCommodities() {
    // Make an HTTP GET request to the backend endpoint
    fetch('http://localhost:9000/user/displayTopThreeFavorSaleCommodity')
        .then(response => {
            // Check if the request was successful (status code 200)
            return response.json();
        })
        .then(data => {
            clearHTML();
            // Assuming the response contains an array of top saleCommodities
            const topSaleCommodities = data.data;
            const salePageContainer = document.getElementById('saleCommodities-page');
           
            const productDisplayContainer = document.createElement('div');
            productDisplayContainer.id= 'product-display';
            // // Clear previous content in the product-display container
            

            // Iterate over the top saleCommodities
            for (let i = 0; i < Math.min(3, topSaleCommodities.length); i++) {
                const saleCommodity = topSaleCommodities[i];

                // Create a product-item container
                const productItem = document.createElement('div');
                productItem.className = 'product-item';

                // Create a product-info container
                const productInfo = document.createElement('div');
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
                const learnMoreLink = document.createElement('a');
                learnMoreLink.href = `saleCommodity.html?id=${saleCommodity.id}`; // Add appropriate query parameters
                learnMoreLink.innerText = 'Learn More';

                // Append Learn More link to product-item
                productItem.appendChild(learnMoreLink);

                // Append product-item to product-display
                productDisplayContainer.appendChild(productItem);
                salePageContainer.appendChild(productDisplayContainer);
            }
        })
        .catch(error => {
            // Handle errors, e.g., network issues or backend errors
            console.error('Error fetching top sale commodities:', error);
        });
}

function showTopPlatforms() {
    clearHTML();
    // Make an HTTP GET request to the backend endpoint
    fetch('http://localhost:9000/user/displayTopThreeFavorPlatform')
        .then(response => {
            // Check if the request was successful (status code 200)
            return response.json();
        })
        .then(data => {
            // Assuming the response contains an array of top platform names
            const topPlatforms = data.data;

            // Clear previous content in the platforms-page container
            const platformsPageContainer = document.getElementById('platforms-page');
            platformsPageContainer.innerHTML = '';

            // Iterate over the top platforms
            topPlatforms.forEach(platformName => {
                // Create a platform-item container
                const platformItem = document.createElement('div');
                platformItem.className = 'platform-item';

                // Populate platform-item with platform details
                platformItem.innerHTML = `
                    <p>Name: ${platformName}</p>
                    <!-- Add more details as needed -->
                `;

                // Append platform-item to platforms-page
                platformsPageContainer.appendChild(platformItem);
            });
        })
        .catch(error => {
            // Handle errors, e.g., network issues or backend errors
            console.error('Error fetching top platforms:', error);
        });
}

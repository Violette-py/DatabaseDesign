// saleCommodity.js

// Function to fetch saleCommodity details by ID
function displaySaleCommodityDetails() {
    // Get the saleCommodity ID from the query parameters
    const urlParams = new URLSearchParams(window.location.search);
    const saleCommodityId = urlParams.get('id');

    // Check if the ID is provided
    if (!saleCommodityId) {
        console.error('No saleCommodity ID provided.');
        // You can add error handling or redirect to an error page here
        return;
    }

    // Call the API to get saleCommodity details by ID
    fetch(`http://localhost:9000/user/displayOneSaleCommodity?id=${saleCommodityId}`)
        .then(response => response.json())
        .then(data => {
            // Check if data is present
            if (data && data.data) {
                // Get the saleCommodity-info container
                var saleCommodityInfoContainer = document.getElementById('saleCommodity-info');

                // Display saleCommodity details
                saleCommodityInfoContainer.innerHTML = `
                    <p><strong>Name:</strong> ${data.data.name}</p>
                    <p><strong>VendorName:</strong> ${data.data.vendorName}</p>
                    <p><strong>PlatformName:</strong> ${data.data.platformName}</p>
                    <p><strong>Category:</strong> ${data.data.category}</p>
                    <p><strong>Price:</strong> ${data.data.price}</p>
                    <p><strong>Place:</strong> ${data.data.place}</p>
                    <p><strong>Production Date:</strong> ${data.data.date}</p>
                    <p><strong>Description:</strong> ${data.data.description}</p>
                   
                    
                    <!-- Add to Favorites button -->
                    <button id="addToFavoritesBtn" onclick="addToFavorites(${data.data.id})">Add to Favorites</button>
                    <!-- Input for minimum price -->
                    <input id="minPriceInput" type="number" placeholder="请输入minPrice" style="display: none">
                    <button id="add" onclick="add(${data.data.id},${data.data.price})" style="display: none">add</button>
                    

                    <!-- Historical Prices -->
                    <h3>Historical Prices</h3>
                    <select id="historicalPriceRange">
                        <option value="week">Last Week</option>
                        <option value="month">Last Month</option>
                        <option value="year">Last Year</option>
                    </select>
                    <button onclick="viewHistoricalPrices(${data.data.id})">View Prices</button>
                    
    <!-- Chart container -->
            <div id="chartContainer">
                <canvas id="priceChart"></canvas>
             </div>
                    <!-- Add more details as needed -->
                `;

                // Check if saleCommodityId is in the user's favorites
                checkIfInFavorites(data.data.id);
            } else {
                console.error('No saleCommodity data found.');
                // You can add error handling or display a message here
            }
        })
        .catch(error => {
            console.error('Error fetching saleCommodity details:', error);
            // You can add error handling or display a message here
        });
}

// Function to check if saleCommodity is in user's favorites
function checkIfInFavorites(saleCommodityId) {
    console.log(saleCommodityId)
    // Get the user ID from sessionStorage (assuming it's stored there)
    const userId = sessionStorage.getItem('ID');

    // Check if userId is present
    if (!userId) {
        console.error('User ID not found.');
        // Handle the case where the user ID is not available
        return;
    }

    // Call the API to check if saleCommodity is in favorites
    fetch(`http://localhost:9000/user/displayFavor?userId=${userId}`)
        .then(response => response.json())
        .then(data => {
            console.log(data.data)
          // Get the "Add to Favorites" button
          var addToFavoritesBtn = document.getElementById('addToFavoritesBtn');

          // Check if saleCommodityId is in the user's messages
          const isInFavors = data && data.data && data.data.some(favor => favor.saleCommodity.id=== saleCommodityId);
            console.log(isInFavors)
          // Disable the button if it's already in messages
          if (isInFavors) {
              addToFavoritesBtn.disabled = true;
                // Change the button style to gray
              addToFavoritesBtn.style.backgroundColor = '#CCCCCC'; // Change to your desired gray color
             // Change the button text to "已添加"
             addToFavoritesBtn.innerText = '已添加';
          }
        })
        .catch(error => {
            console.error('Error checking if saleCommodity is in favorites:', error);
            // You can add error handling or display a message here
        });
}


// Function to add saleCommodity to favorites
function addToFavorites(saleCommodityId) {
    var minPriceInput = document.getElementById('minPriceInput');

    // Toggle the display property
    minPriceInput.style.display = 'block';

    var addButton = document.getElementById('add');
    addButton.style.display = 'block'
   
    
}
function add(saleCommodityId,saleCommodityPrice){
 // Get the minimum price from the input element
 const minPriceInput = document.getElementById('minPriceInput');
 const minPrice = minPriceInput.value;
 if(minPrice==null){
    alert('请输入');
 }
 if(minPrice <= 0){
    // Handle the case where the user did not enter a valid value
    alert('请输入正数');
}
else if(minPrice>=saleCommodityPrice){
    alert('请输入小于当前价格的数值');
 }
 // Check if the user entered a value
 else {
     // Call the addFavor function with saleCommodityId and minPrice
     addFavor(saleCommodityId, minPrice);
 } 
}
// Function to submit the saleCommodity to favorites with minimum price
function addFavor(saleCommodityId, minPrice) {
    // Get the user ID from sessionStorage (assuming it's stored there)
    const userId = sessionStorage.getItem('ID');

    // Check if userId is present
    if (!userId) {
        console.error('User ID not found.');
        // Handle the case where the user ID is not available
        return;
    }

    // Prepare the data for the POST request
    const requestData = {
        saleCommodityId: saleCommodityId,
        userId: userId,
        minPrice: minPrice
    };

    // Send a POST request to the backend
    fetch('http://localhost:9000/user/addFavor', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) {
            alert('商品已添加进收藏！');
            window.location.reload();
        } else {
            alert(data.msg);
        }
    })
    .catch(error => {
        console.error('Error adding SaleCommodity to favorites:', error);
        alert('An error occurred. Please try again later.');
    });
}
// Declare myChart variable in a broader scope
var myChart;
// Function to view historical prices
function viewHistoricalPrices(saleCommodityId) {
        // Destroy previous chart instance
        if (myChart) {
            myChart.destroy();
        }
    
    // Get the selected time range
    const selectedRange = document.getElementById('historicalPriceRange').value;

    // Convert selectedRange to the corresponding time duration
    const timeDuration = convertRangeToTime(selectedRange);

    // Check if timeDuration is valid
    if (!timeDuration) {
        console.error('Invalid time range selected.');
        // Handle the case where the time range is not valid
        return;
    }

    // Call the backend API to fetch historical prices
    fetch(`http://localhost:9000/user/displayHistoryPrice?saleCommodityId=${saleCommodityId}&timeSpan=${timeDuration}`)
        .then(response => response.json())
        .then(data => {
            // Check if data is present
            if (data && data.data) {
                console.log(data)

       // Extract timestamps and prices from the data
        const dataPoints = data.data.map(entry => ({ time: entry.time, price: entry.price }));

// Sort data points based on timestamps in ascending order
        dataPoints.sort((a, b) => new Date(a.time) - new Date(b.time));

// Extract sorted timestamps and prices
        const timestamps = dataPoints.map(entry => entry.time);
        const prices = dataPoints.map(entry => entry.price);

// Now 'timestamps' and 'prices' are sorted based on the timestamp values

       

                // Call a function to render the chart
                renderPriceChart(timestamps, prices);
            } else {
                console.error('No historical price data found.');
                // You can add error handling or display a message here
            }
        })
        .catch(error => {
            console.error('Error fetching historical prices:', error);
            // You can add error handling or display a message here
        });
}
 
// Function to convert selectedRange to time duration
function convertRangeToTime(selectedRange) {
    switch (selectedRange) {
        case 'week':
            return '1 week';
        case 'month':
            return '1 month';
        case 'year':
            return '1 year';
        default:
            return null;
    }
}


// Function to render the price chart
function renderPriceChart(timestamps, prices) {
    // Use a charting library like Chart.js to render the chart
    // You need to include Chart.js in your project for this to work

    // Find the min and max values in prices array
    var minPrice = Math.min(...prices);
    var maxPrice = Math.max(...prices);

    // Calculate y-axis range with some padding
    var yMin = minPrice - 10;  // Adjust the padding as needed
    var yMax = maxPrice + 10;  // Adjust the padding as needed

    // Find the index of the minimum price
    var minPriceIndex = prices.indexOf(minPrice);

    // Create an array to store background colors for each data point
    var backgroundColors = prices.map((price, index) => (index === minPriceIndex) ? 'red' : 'rgba(0, 0, 0, 0)');

    var ctx = document.getElementById('priceChart').getContext('2d');
    myChart = new Chart(ctx, {
        type: 'line',  // or other chart type
        data: {
            labels: timestamps,  // Set timestamps as x-axis labels
            datasets: [{
                label: 'Price',
                data: prices,  // Set prices as y-axis data
                backgroundColor: backgroundColors, // Set background colors for each data point
                borderColor: 'blue',  // Set border color
                borderWidth: 1,  // Set border width
                pointBackgroundColor: backgroundColors, // Set point background colors
                pointBorderColor: 'blue', // Set point border color
                pointRadius: 5, // Set point radius
                pointHoverRadius: 7, // Set point hover radius
            }]
        },
        options: {
            scales: {
                x: {
                    // type: 'time',  // or 'category', 'time', etc.
                    position: 'bottom',  // or 'top', 'right', 'left'
                    // other x-axis configurations as needed
                },
                y: {
                    min: yMin,
                    max: yMax,
                }
            },
            elements: {
                point: {
                    // Display y-axis values on each data point
                    radius: function (context) {
                        var index = context.dataIndex;
                        return prices[index] === minPrice ? 0 : 5; // Set radius to 0 for the minimum price point
                    },
                    hoverRadius: function (context) {
                        var index = context.dataIndex;
                        return prices[index] === minPrice ? 0 : 7; // Set hover radius to 0 for the minimum price point
                    },
                    backgroundColor: function (context) {
                        var index = context.dataIndex;
                        return prices[index] === minPrice ? 'red' : 'blue'; // Set background color to red for the minimum price point
                    },
                    borderColor: 'blue', // Set border color for all points
                    borderWidth: 1, // Set border width for all points
                }
            }
        }
    });
}


// Call the function to display saleCommodity details when the page loads
window.onload = function () {
    displaySaleCommodityDetails();
};

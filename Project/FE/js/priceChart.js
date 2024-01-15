// priceChart.js
document.addEventListener("DOMContentLoaded", function() {
    var ctx = document.getElementById("price-chart").getContext("2d");

    var priceHistory = [9.99, 10.49, 10.99, 11.29, 9.79, 10.89, 9.49];
    
    // Find the index of the lowest price
    var lowestPriceIndex = priceHistory.indexOf(Math.min(...priceHistory));

    var myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['Jan 1', 'Jan 5', 'Jan 10', 'Jan 15', 'Jan 20', 'Jan 25', 'Jan 30'],
            datasets: [{
                label: 'Price History',
                data: priceHistory,
                backgroundColor: function(context) {
                    // Highlight the bar with the lowest price in red
                    return context.dataIndex === lowestPriceIndex ? 'red' : 'blue';
                },
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
});

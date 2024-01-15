// user.js

// Function to show the account page
function showAccountPage() {
    // Get user ID from sessionStorage
    const userId = sessionStorage.getItem('ID');

    // Check if user ID exists
    if (!userId) {
        console.error('User ID not found in sessionStorage.');
        return;
    }

    // Call the backend API to fetch user information
    fetch(`http://localhost:9000/user/displayInfo?id=${userId}`)
    
        .then(response => response.json())
        .then(data => {
        
            console.log(data)
            // Check if data is present
            if (data && data.data) {
                // Extract user information
                const userInfo = data.data;
            
            

                // Display user information in the account page
                displayAccountInformation(userInfo);
            } else {
                console.error('No user information found.');
                // You can add error handling or display a message here
            }
        })
        .catch(error => {
            console.error('Error fetching user information:', error);
            // You can add error handling or display a message here
        });
}

// Function to display user information in the account page
function displayAccountInformation(userInfo) {
    clearHTML()
   
    const accountPage = document.getElementById('account-page');

   

    // Create HTML content to display user information
    const htmlContent = `
        <h2>User Information</h2>
        <p><strong>Name:</strong> ${userInfo.name}</p>
        <p><strong>Gender:</strong> ${userInfo.gender}</p>
        <p><strong>Age:</strong> ${userInfo.age}</p>
        <p><strong>Phone:</strong> ${userInfo.phone}</p>
    `;

    // Set the HTML content in the account page
    accountPage.innerHTML = htmlContent;
}
function showMessagesPage() {
    // 获取用户ID，假设用户ID存储在sessionStorage中
    var userId = sessionStorage.getItem('ID');

    // 调用后端API获取用户的消息
    fetch(`http://localhost:9000/user/displayMessage?userId=${userId}`)
        .then(response => response.json())
        .then(data => {
            clearHTML()
            // 获取消息容器
            var messagesContainer = document.getElementById('messages-page');

            // 检查是否有消息数据
            if (data && data.data && data.data.length > 0) {
                // 遍历消息数据并在页面上显示
                data.data.forEach(message => {
                    // 创建消息项容器
                    var messageItem = document.createElement('div');
                    messageItem.className = 'message-item';

                    // 在消息项容器中添加内容和时间
                    messageItem.innerHTML = `
                        <p>Content: ${message.content}</p>
                        <p>Time: ${message.time}</p>
                    `;

                    // 将消息项添加到消息容器
                    messagesContainer.appendChild(messageItem);
                });
            } else {
                // 在页面上显示提示信息
                var noMessagesMessage = document.createElement('p');
                noMessagesMessage.innerText = '您还未收到任何信息。';
                messagesContainer.appendChild(noMessagesMessage);
            }
        })
        .catch(error => {
            console.error('Error fetching messages:', error);
            alert('An error occurred. Please try again later.');
        });
}

function clearHTML(){
         // 获取消息容器
         var messagesContainer = document.getElementById('messages-page');

         // 清空现有内容
         messagesContainer.innerHTML = '';
         
         var  accountPage = document.getElementById('account-page');

     // 清空现有内容
     accountPage.innerHTML = '';
     

     var favoritesPage =  document.getElementById('favorites-page');
     favoritesPage.innerHTML = '';

     var pagination =  document.getElementById('pagination');
    pagination.innerHTML = '';
     

}

function showFavoritesPage(pageNumber = 1, pageSize = 4) {
    // 获取用户ID，假设用户ID存储在sessionStorage中
    var userId = sessionStorage.getItem('ID');
    // 调用后端API获取用户的收藏信息
    fetch(`http://localhost:9000/user/displayFavor?userId=${userId}`)
        .then(response => response.json())
        .then(data => {
            // 获取收藏容器
            clearHTML();
            var favoritesContainer = document.getElementById('favorites-page');

            // 检查是否有收藏数据
            if (data && data.data && data.data.length > 0) {
                // 计算总页数
                const totalPages = Math.ceil(data.data.length / pageSize);

                // 根据页码和每页数量计算起始和结束索引
                const startIndex = (pageNumber - 1) * pageSize;
                const endIndex = Math.min(startIndex + pageSize, data.data.length);

                // 遍历指定范围的收藏数据并在页面上显示
                for (let i = startIndex; i < endIndex; i++) {
                    const favorite = data.data[i];

                    // 创建收藏项容器
                    var favoriteItem = document.createElement('div');
                    favoriteItem.className = 'favorite-item';
                    favoriteItem.innerHTML = `
                        <p>Name: ${favorite.saleCommodity.name}</p>
                        <p>Price: ${favorite.saleCommodity.price}</p>
                        <p>Min Price: ${favorite.minPrice}</p> <!-- 显示 minPrice -->
                        <p>Vendor: ${favorite.saleCommodity.vendorName}</p>
                        <p>Platform: ${favorite.saleCommodity.platformName}</p>
                    `;

                    // 创建 "Learn More" 链接
                    var learnMoreLink = document.createElement('a');
                    learnMoreLink.href = `saleCommodity.html?id=${favorite.saleCommodity.id}`;
                    learnMoreLink.innerText = 'Learn More';

                    // 将 "Learn More" 链接添加到收藏项
                    favoriteItem.appendChild(learnMoreLink);

                    // 将收藏项添加到收藏容器
                    favoritesContainer.appendChild(favoriteItem);
                }

                // 显示分页导航
                showPagination(pageNumber, totalPages);
            } else {
                // 在页面上显示提示信息
                var noFavoritesMessage = document.createElement('p');
                noFavoritesMessage.innerText = '您还未收藏任何商品。';
                favoritesContainer.appendChild(noFavoritesMessage);
            }
        })
        .catch(error => {
            console.error('Error fetching favorites:', error);
            alert('An error occurred. Please try again later.');
        });
}

// Function to display pagination
function showPagination(currentPage, totalPages) {
    var paginationContainer = document.getElementById('pagination');
    paginationContainer.innerHTML = '';

    for (let i = 1; i <= totalPages; i++) {
        var pageButton = document.createElement('button');
        pageButton.innerText = i;
        pageButton.addEventListener('click', function () {
            showFavoritesPage(i);
        });

        // Highlight the current page
        if (i === currentPage) {
            pageButton.classList.add('active');
        }

        paginationContainer.appendChild(pageButton);
    }
}


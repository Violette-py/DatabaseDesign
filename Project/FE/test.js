// 使用Fetch API获取数据
fetch('http://127.0.0.1:9000/home/show')
    .then(response => response.json()) // 解析响应的JSON数据
    .then(data => {
        // 处理获取的数据
        document.getElementById('displayData').innerHTML = JSON.stringify(data, null, 2);
    })
    .catch(error => {
        console.error('Error fetching data:', error);
    });

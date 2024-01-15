// init.js
async function initVendor() {
    await handleInitialization('initVendor', '商家');
}

async function initPlatform() {
    await handleInitialization('initPlatform', '平台');
}

async function initCommodity() {
    await handleInitialization('initCommodity', '商品');
}

async function initSaleCommodity() {
    await handleInitialization('initSaleCommodity', '上架商品');
}

async function initHistoryPrice() {
    await handleInitialization('initHistoryPrice', '历史价格');
}

async function handleInitialization(apiEndpoint, dataType) {
    try {
        const apiUrl = `http://localhost:9000/admin/${apiEndpoint}`;
        const isConfirmed = confirm(`你想要导入${dataType}吗?`);

        if (isConfirmed) {
            const response = await fetch(apiUrl, {
                method: 'POST',
            });

            const data = await response.json();

            if (data.code !== 200) {
                throw new Error(data.msg);
            }

            alert(`成功导入${dataType}数据`);
        }
    } catch (error) {
        console.error('Error:', error);
        alert(`导入${dataType}数据时发生错误，请稍后再试。`);
    }
}

// Other shared functions if any...

// Rest of your init.js file

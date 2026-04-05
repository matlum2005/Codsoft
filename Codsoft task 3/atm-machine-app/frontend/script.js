const API_BASE = 'http://localhost:8080/api';

document.addEventListener('DOMContentLoaded', () => {
    const loginScreen = document.getElementById('loginScreen');
    const dashboardScreen = document.getElementById('dashboardScreen');
    const loginAccount = document.getElementById('loginAccount');
    const loginPin = document.getElementById('loginPin');
    const loginBtn = document.getElementById('loginBtn');
    const logoutBtn = document.getElementById('logoutBtn');
    const amountInput = document.getElementById('amount');
    const depositBtn = document.getElementById('depositBtn');
    const withdrawBtn = document.getElementById('withdrawBtn');
    const loginMessage = document.getElementById('loginMessage');
    const messageDiv = document.getElementById('message');
    const balanceDisplay = document.getElementById('balanceDisplay');
    const lastTxDisplay = document.getElementById('lastTxDisplay');

    let currentAccount = localStorage.getItem('atmAccount');

    const showScreen = (screen) => {
        loginScreen.classList.toggle('active', screen === 'login');
        dashboardScreen.classList.toggle('active', screen === 'dashboard');
    };

    const showMessage = (text, type = 'success', element = messageDiv) => {
        element.textContent = text;
        element.className = `message ${type}`;
        // Auto-clear after 5 seconds
        setTimeout(() => clearMessage(element), 5000);
    };

    const clearMessage = (element = messageDiv) => {
        element.textContent = '';
        element.className = 'message';
    };

    const setLoading = (btn, loading) => {
        btn.disabled = loading;
        if (loading) {
            btn.innerHTML = '⏳ Processing...';
            btn.classList.add('loading');
        } else {
            btn.innerHTML = btn.dataset.originalText || btn.innerHTML.replace('⏳ Processing...', btn.dataset.originalText);
            btn.classList.remove('loading');
            if (!btn.dataset.originalText) {
                btn.dataset.originalText = btn.innerHTML;
            }
        }
    };

    const updateDisplays = async () => {
        if (!currentAccount) return;
        try {
            console.log('Updating displays for account:', currentAccount);
            // Balance
            const balanceRes = await fetch(`${API_BASE}/atm/balance/${currentAccount}`);
            if (!balanceRes.ok) throw new Error('Balance fetch failed');
            const balance = await balanceRes.json();
            balanceDisplay.textContent = `Balance: $${balance.toFixed(2)}`;

            // Last Transaction
            const txRes = await fetch(`${API_BASE}/atm/lastTransaction/${currentAccount}`);
            const tx = await txRes.json();
            if (tx) {
                lastTxDisplay.textContent = `${tx.type}: $${tx.amount.toFixed(2)} at ${new Date(tx.timestamp).toLocaleString()}`;
            } else {
                lastTxDisplay.textContent = 'No transactions yet';
            }
        } catch (error) {
            console.error('Update displays error:', error);
            if (error.message.includes('fetch') || error.message.includes('Failed to fetch')) {
                showMessage('Backend server not running. Start with: cd atm-machine-app/backend && mvn spring-boot:run', 'error');
            } else {
                showMessage(`Data load failed: ${error.message}`, 'error');
            }
            balanceDisplay.textContent = 'Balance: --';
            lastTxDisplay.textContent = 'Server offline';
        }
    };

    // Login
    loginBtn.addEventListener('click', async () => {
        const account = loginAccount.value.trim();
        const pin = loginPin.value.trim();
        if (!account || !pin) {
            showMessage('Please enter account and PIN', 'error', loginMessage);
            return;
        }
        setLoading(loginBtn, true);
        try {
            const res = await fetch(`${API_BASE}/auth/login`, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                mode: 'cors',
                body: JSON.stringify({accountNumber: account, pin})
            });
            const data = await res.json();
            const isSuccess = data.success;
            if (isSuccess) {
                currentAccount = data.accountNumber;
                localStorage.setItem('atmAccount', currentAccount);
                loginAccount.value = '';
                loginPin.value = '';
                showScreen('dashboard');
                updateDisplays();
            } else {
                showMessage(data.message || 'Login failed', 'error', loginMessage);
            }
        } catch (error) {
            console.error('Login error:', error);
            let errorMsg = 'Login failed';
            if (error.message.includes('fetch') || error.message.includes('Failed to fetch')) {
                errorMsg += ': Backend server not running. Start with cd atm-machine-app/backend && mvn spring-boot:run';
            } else {
                errorMsg += ': ' + error.message;
            }
            showMessage(errorMsg, 'error', loginMessage);
        } finally {
            setLoading(loginBtn, false);
        }
    });

    // Logout
    logoutBtn.addEventListener('click', () => {
        localStorage.removeItem('atmAccount');
        currentAccount = null;
        showScreen('login');
        amountInput.value = '';
        clearMessage();
        balanceDisplay.textContent = 'Balance: $0.00';
        lastTxDisplay.textContent = 'No transactions';
    });

    // Deposit
    depositBtn.addEventListener('click', async () => {
        const amount = parseFloat(amountInput.value);
        console.log('Deposit:', currentAccount, amount);
        if (isNaN(amount) || amount <= 0) {
            showMessage('Enter positive amount', 'error');
            return;
        }
        if (!currentAccount) {
            showMessage('Please login first', 'error');
            return;
        }
        setLoading(depositBtn, true);
        try {
            const res = await fetch(`${API_BASE}/atm/deposit`, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                mode: 'cors',
                body: JSON.stringify({accountNumber: currentAccount, amount})
            });
            const data = await res.json();
            const isSuccess = data.status === 'success' || data.success;
            showMessage(data.message || (isSuccess ? 'Deposit successful' : 'Deposit failed'), isSuccess ? 'success' : 'error');
            if (isSuccess) {
                amountInput.value = '';
                updateDisplays();
            }
        } catch (error) {
            console.error('Deposit error:', error);
            showMessage(`Deposit failed: ${error.message}`, 'error');
        } finally {
            setLoading(depositBtn, false);
        }
    });

    // Withdraw
    withdrawBtn.addEventListener('click', async () => {
        const amount = parseFloat(amountInput.value);
        console.log('Withdraw:', currentAccount, amount);
        if (isNaN(amount) || amount <= 0) {
            showMessage('Enter positive amount', 'error');
            return;
        }
        if (!currentAccount) {
            showMessage('Please login first', 'error');
            return;
        }
        setLoading(withdrawBtn, true);
        try {
            const res = await fetch(`${API_BASE}/atm/withdraw`, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                mode: 'cors',
                body: JSON.stringify({accountNumber: currentAccount, amount})
            });
            const data = await res.json();
            const isSuccess = data.status === 'success' || data.success;
            showMessage(data.message || (isSuccess ? 'Withdraw successful' : 'Withdraw failed'), isSuccess ? 'success' : 'error');
            if (isSuccess) {
                amountInput.value = '';
                updateDisplays();
            }
        } catch (error) {
            console.error('Withdraw error:', error);
            showMessage(`Withdraw failed: ${error.message}`, 'error');
        } finally {
            setLoading(withdrawBtn, false);
        }
    });

    // Check if already logged in
    if (currentAccount) {
        showScreen('dashboard');
        updateDisplays();
    }
});


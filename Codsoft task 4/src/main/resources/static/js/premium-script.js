// Premium Currency Converter - Enhanced JS
class PremiumConverter {
    constructor() {
        this.currencies = new Map();
        this.history = JSON.parse(localStorage.getItem('conversionHistory')) || [];
        this.isDark = true;
        
        // DOM Elements
        this.elements = {
            amount: document.getElementById('amount'),
            baseCurrency: document.getElementById('baseCurrency'),
            targetCurrency: document.getElementById('targetCurrency'),
            fromTrigger: document.getElementById('fromTrigger'),
            toTrigger: document.getElementById('toTrigger'),
            fromList: document.getElementById('fromList'),
            toList: document.getElementById('toList'),
            fromSearch: document.getElementById('fromSearch'),
            toSearch: document.getElementById('toSearch'),
            swapBtn: document.getElementById('swapBtn'),
            convertBtn: document.getElementById('convertBtn'),
            btnText: document.getElementById('btnText'),
            loadingSpinner: document.getElementById('loadingSpinner'),
            resultSection: document.getElementById('resultSection'),
            convertedAmount: document.getElementById('convertedAmount'),
            exchangeRate: document.getElementById('exchangeRate'),
            liveRate: document.getElementById('liveRate'),
            liveRateValue: document.getElementById('liveRateValue'),
            errorMessage: document.getElementById('errorMessage'),
            historyBtn: document.getElementById('historyBtn'),
            historyModal: document.getElementById('historyModal'),
            historyList: document.getElementById('historyList'),
            closeHistory: document.getElementById('closeHistory'),
            themeToggle: document.getElementById('themeToggle'),
            form: document.getElementById('converterForm')
        };

        this.init();
    }

    async init() {
        await this.loadCurrencies();
        this.bindEvents();
        this.updateTheme();
        this.updateLiveRate();
        this.renderHistory();
    }

    bindEvents() {
        // Form submit
        this.elements.form.addEventListener('submit', (e) => this.handleConvert(e));
        
        // Swap
        this.elements.swapBtn.addEventListener('click', () => this.swapCurrencies());
        
        // Dropdowns
        this.elements.fromTrigger.addEventListener('click', () => this.toggleDropdown('from'));
        this.elements.toTrigger.addEventListener('click', () => this.toggleDropdown('to'));
        
        this.elements.fromSearch.addEventListener('input', (e) => this.filterDropdown('from', e.target.value));
        this.elements.toSearch.addEventListener('input', (e) => this.filterDropdown('to', e.target.value));
        
        // Close dropdowns on outside click
        document.addEventListener('click', (e) => this.handleOutsideClick(e));
        
        // History
        this.elements.historyBtn.addEventListener('click', () => this.toggleHistory());
        this.elements.closeHistory.addEventListener('click', () => this.toggleHistory());
        
        // Theme toggle
        this.elements.themeToggle.addEventListener('click', () => this.toggleTheme());
        
        // Live preview
        this.elements.baseCurrency.addEventListener('change', () => this.updateLiveRate());
        this.elements.targetCurrency.addEventListener('change', () => this.updateLiveRate());
        this.elements.amount.addEventListener('input', () => this.clearResults());
    }

    async loadCurrencies() {
        try {
            const response = await fetch('http://localhost:8080/api/rates?base=USD');
            const data = await response.json();
            
            const currencyList = Object.keys(data.rates).sort();
            
            // Static currency info with symbols/flags
            const currencyInfo = {
                'USD': { symbol: '$', flag: '🇺🇸', name: 'US Dollar' },
                'EUR': { symbol: '€', flag: '🇪🇺', name: 'Euro' },
                'GBP': { symbol: '£', flag: '🇬🇧', name: 'Pound Sterling' },
                'JPY': { symbol: '¥', flag: '🇯🇵', name: 'Japanese Yen' },
                'INR': { symbol: '₹', flag: '🇮🇳', name: 'Indian Rupee' },
                // Add more as needed
            };

            currencyList.forEach(code => {
                this.currencies.set(code, currencyInfo[code] || { symbol: code, flag: '🌍', name: code });
            });

            this.populateDropdowns();
            
        } catch (error) {
            console.error('Failed to load currencies:', error);
            this.showError('Using fallback currencies');
            this.loadFallbackCurrencies();
        }
    }

    populateDropdowns() {
        const fromList = this.elements.fromList.querySelectorAll('.dropdown-item:not(.dropdown-search)');
        const toList = this.elements.toList.querySelectorAll('.dropdown-item:not(.dropdown-search)');
        
        fromList.forEach(item => item.remove());
        toList.forEach(item => item.remove());

        this.currencies.forEach((info, code) => {
            const itemHTML = `
                <div class="dropdown-item" data-code="${code}">
                    <span class="currency-flag">${info.flag}</span>
                    <div>
                        <div class="currency-code">${code}</div>
                        <div class="currency-name">${info.name}</div>
                    </div>
                    <span class="currency-symbol">${info.symbol}</span>
                </div>
            `;
            
            this.elements.fromList.insertAdjacentHTML('beforeend', itemHTML);
            this.elements.toList.insertAdjacentHTML('beforeend', itemHTML);
        });

        // Bind item clicks
        this.elements.fromList.querySelectorAll('.dropdown-item').forEach(item => {
            item.addEventListener('click', (e) => this.selectCurrency('from', e.currentTarget.dataset.code));
        });
        this.elements.toList.querySelectorAll('.dropdown-item').forEach(item => {
            item.addEventListener('click', (e) => this.selectCurrency('to', e.currentTarget.dataset.code));
        });

        this.updateTriggers();
    }

    toggleDropdown(dropdown) {
        const list = document.getElementById(dropdown === 'from' ? 'fromList' : 'toList');
        const isOpen = list.classList.contains('show');
        
        // Close all
        document.querySelectorAll('.dropdown-list').forEach(l => l.classList.remove('show'));
        
        if (!isOpen) {
            list.classList.add('show');
        }
    }

    filterDropdown(dropdown, query) {
        const list = document.getElementById(dropdown === 'from' ? 'fromList' : 'toList');
        const items = list.querySelectorAll('.dropdown-item');
        
        items.forEach(item => {
            const code = item.dataset.code.toLowerCase();
            const name = item.textContent.toLowerCase();
            item.style.display = code.includes(query.toLowerCase()) || name.includes(query.toLowerCase()) ? 'flex' : 'none';
        });
    }

    selectCurrency(type, code) {
        if (type === 'from') {
            this.elements.baseCurrency.value = code;
            this.elements.fromTrigger.querySelector('.currency-symbol').textContent = this.currencies.get(code).symbol;
        } else {
            this.elements.targetCurrency.value = code;
            this.elements.toTrigger.querySelector('.currency-symbol').textContent = this.currencies.get(code).symbol;
        }
        
        document.querySelectorAll('.dropdown-list').forEach(l => l.classList.remove('show'));
        this.updateLiveRate();
        this.clearResults();
    }

    updateTriggers() {
        const baseInfo = this.currencies.get(this.elements.baseCurrency.value) || { symbol: '$' };
        const targetInfo = this.currencies.get(this.elements.targetCurrency.value) || { symbol: '€' };
        
        this.elements.fromTrigger.querySelector('.currency-symbol').textContent = baseInfo.symbol;
        this.elements.toTrigger.querySelector('.currency-symbol').textContent = targetInfo.symbol;
    }

    async updateLiveRate() {
        const base = this.elements.baseCurrency.value;
        const target = this.elements.targetCurrency.value;
        
        if (base && target && base !== target) {
            try {
                const response = await fetch(`http://localhost:8080/api/rates?base=${base}`);
                const data = await response.json();
                const rate = data.rates[target];
                
                this.elements.liveRateValue.textContent = rate.toFixed(4);
                this.elements.liveRate.classList.add('show');
            } catch (error) {
                console.error('Live rate failed:', error);
            }
        } else {
            this.elements.liveRate.classList.remove('show');
        }
    }

    async handleConvert(e) {
        e.preventDefault();
        
        const amount = parseFloat(this.elements.amount.value);
        const base = this.elements.baseCurrency.value;
        const target = this.elements.targetCurrency.value;

        if (!amount || amount <= 0 || !base || !target) {
            this.showError('Please enter valid amount and select different currencies');
            return;
        }

        this.setLoading(true);
        this.clearResults();

        try {
            const response = await fetch('http://localhost:8080/api/convert', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ baseCurrency: base, targetCurrency: target, amount })
            });

            if (!response.ok) throw new Error('Conversion failed');

            const result = await response.json();
            this.displayResult(result);
            this.addToHistory(amount, base, target, result);
        } catch (error) {
            this.showError(error.message);
        } finally {
            this.setLoading(false);
        }
    }

    swapCurrencies() {
        const base = this.elements.baseCurrency.value;
        const target = this.elements.targetCurrency.value;
        
        this.elements.baseCurrency.value = target;
        this.elements.targetCurrency.value = base;
        
        this.updateTriggers();
        this.updateLiveRate();
        this.clearResults();
    }

    displayResult(result) {
        this.elements.convertedAmount.textContent = `${result.target} ${result.convertedAmount.toLocaleString()}`;
        this.elements.exchangeRate.textContent = `1 ${result.base} = ${result.rate.toFixed(4)} ${result.target}`;
        this.elements.resultSection.classList.remove('hidden');
    }

    addToHistory(amount, base, target, result) {
        const entry = {
            timestamp: new Date().toLocaleString(),
            amount,
            base,
            target,
            convertedAmount: result.convertedAmount,
            rate: result.rate
        };
        
        this.history.unshift(entry);
        this.history = this.history.slice(0, 10); // Keep last 10
        localStorage.setItem('conversionHistory', JSON.stringify(this.history));
    }

    renderHistory() {
        if (this.history.length === 0) {
            this.elements.historyList.innerHTML = '<p style="text-align: center; color: var(--text-secondary);">No conversions yet...</p>';
            return;
        }
        
        this.elements.historyList.innerHTML = this.history.map(entry => `
            <div style="padding: 1rem; border-bottom: 1px solid rgba(255,255,255,0.1);">
                <div style="font-weight: 600; margin-bottom: 0.5rem;">
                    ${entry.amount.toLocaleString()} ${entry.base} → ${entry.convertedAmount.toLocaleString()} ${entry.target}
                </div>
                <div style="color: var(--text-secondary); font-size: 0.9rem;">
                    Rate: 1 ${entry.base} = ${entry.rate.toFixed(4)} ${entry.target} | ${entry.timestamp}
                </div>
            </div>
        `).join('');
    }

    toggleHistory() {
        this.elements.historyModal.classList.toggle('show');
        if (this.elements.historyModal.classList.contains('show')) {
            this.renderHistory();
        }
    }

    clearResults() {
        this.elements.resultSection.classList.add('hidden');
        this.hideError();
    }

    setLoading(loading) {
        this.elements.convertBtn.disabled = loading;
        this.elements.loadingSpinner.classList.toggle('hidden', !loading);
        this.elements.btnText.textContent = loading ? 'Converting...' : 'Convert Now';
    }

    showError(message) {
        this.elements.errorMessage.textContent = message;
        this.elements.errorMessage.classList.remove('hidden');
    }

    hideError() {
        this.elements.errorMessage.classList.add('hidden');
    }

    handleOutsideClick(e) {
        if (!e.target.closest('.custom-dropdown')) {
            document.querySelectorAll('.dropdown-list').forEach(l => l.classList.remove('show'));
        }
    }

    toggleTheme() {
        this.isDark = !this.isDark;
        document.body.classList.toggle('light-theme', !this.isDark);
        const icon = this.elements.themeToggle.querySelector('i');
        icon.className = this.isDark ? 'fas fa-moon' : 'fas fa-sun';
        localStorage.setItem('theme', this.isDark ? 'dark' : 'light');
    }

    updateTheme() {
        const saved = localStorage.getItem('theme');
        if (saved === 'light') {
            this.toggleTheme();
        }
    }

    loadFallbackCurrencies() {
        const fallback = ['USD', 'EUR', 'GBP', 'JPY', 'INR', 'AUD', 'CAD'];
        fallback.forEach(code => {
            this.currencies.set(code, { symbol: code, flag: '🌍', name: code });
        });
        this.populateDropdowns();
    }
}

// Initialize on DOM load
document.addEventListener('DOMContentLoaded', () => {
    new PremiumConverter();
});


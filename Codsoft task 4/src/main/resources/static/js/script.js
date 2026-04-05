// Currency Converter Frontend Logic
class CurrencyConverter {
    constructor() {
        this.baseCurrency = document.getElementById('baseCurrency');
        this.targetCurrency = document.getElementById('targetCurrency');
        this.amountInput = document.getElementById('amount');
        this.convertBtn = document.getElementById('convertBtn');
        this.swapBtn = document.getElementById('swapBtn');
        this.resultSection = document.getElementById('resultSection');
        this.errorMessage = document.getElementById('errorMessage');
        this.convertedAmount = document.getElementById('convertedAmount');
        this.exchangeRate = document.getElementById('exchangeRate');
        this.loadingSpinner = document.getElementById('loadingSpinner');
        this.btnText = document.getElementById('btnText');
        this.form = document.getElementById('converterForm');

        this.init();
    }

    async init() {
        await this.loadCurrencies();
        this.bindEvents();
    }

    bindEvents() {
        this.form.addEventListener('submit', (e) => this.handleConvert(e));
        this.swapBtn.addEventListener('click', () => this.swapCurrencies());
        this.amountInput.addEventListener('input', () => this.clearResults());
    }

    async loadCurrencies() {
        try {
            console.log('🔄 Fetching currencies from http://localhost:8080/api/rates?base=USD...');
            const response = await fetch('http://localhost:8080/api/rates?base=USD');
            
            if (!response.ok) {
                throw new Error(`HTTP ${response.status}: ${response.statusText}`);
            }
            
            const rates = await response.json();
            console.log('✅ API Response:', rates);
            
            if (!rates.rates || Object.keys(rates.rates).length === 0) {
                throw new Error('Empty rates response');
            }
            
            const currencies = Object.keys(rates.rates).sort();
            console.log(`📊 Found ${currencies.length} currencies`);
            
            // Clear existing options except placeholder
            this.baseCurrency.innerHTML = '<option value="">Select base currency</option>';
            this.targetCurrency.innerHTML = '<option value="">Select target currency</option>';
            
            currencies.forEach(currency => {
                const option1 = new Option(currency, currency);
                const option2 = new Option(currency, currency);
                this.baseCurrency.add(option1);
                this.targetCurrency.add(option2);
            });
            
            // Set defaults
            this.baseCurrency.value = 'USD';
            this.targetCurrency.value = 'EUR';
            
            console.log('✅ Dropdowns populated successfully');
            
        } catch (error) {
            console.error('❌ Failed to load currencies:', error);
            
            // Fallback static currencies
            console.log('🔄 Using fallback currency list...');
            const fallbackCurrencies = [
                'USD', 'EUR', 'GBP', 'JPY', 'AUD', 'CAD', 'CHF', 'CNY', 'SEK', 'NZD',
                'INR', 'BRL', 'RUB', 'MXN', 'ZAR', 'SGD', 'HKD', 'NOK', 'KRW', 'TRY',
                'PLN', 'THB', 'MYR', 'IDR', 'PHP', 'VND', 'EGP', 'ILS', 'AED', 'SAR'
            ].sort();
            
            this.baseCurrency.innerHTML = '<option value="">Select base currency</option>';
            this.targetCurrency.innerHTML = '<option value="">Select target currency</option>';
            
            fallbackCurrencies.forEach(currency => {
                const option1 = new Option(currency, currency);
                const option2 = new Option(currency, currency);
                this.baseCurrency.add(option1);
                this.targetCurrency.add(option2);
            });
            
            this.baseCurrency.value = 'USD';
            this.targetCurrency.value = 'EUR';
            
            this.showError('⚠️ API failed - using fallback currencies (' + fallbackCurrencies.length + ' available)');
            console.log('✅ Fallback dropdowns populated');
        }
    }

    async handleConvert(e) {
        e.preventDefault();
        
        const amount = parseFloat(this.amountInput.value);
        const base = this.baseCurrency.value;
        const target = this.targetCurrency.value;

        if (!amount || amount <= 0 || !base || !target) {
            this.showError('Please enter valid amount and select currencies');
            return;
        }

        this.setLoading(true);
        this.clearResults();

        try {
            const response = await fetch('http://localhost:8080/api/convert', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ 
                    baseCurrency: base, 
                    targetCurrency: target, 
                    amount 
                })
            });

            if (!response.ok) {
                const errorData = await response.text();
                console.error('Conversion API error:', response.status, errorData);
                let errorMsg = 'Conversion failed. Please try again.';
                try {
                    const errorJson = JSON.parse(errorData);
                    errorMsg = errorJson.error || `Server error: ${response.status}`;
                } catch (e) {
                    errorMsg = `Server error: ${response.status} - ${errorData.substring(0, 100)}`;
                }
                throw new Error(errorMsg);
            }

            const result = await response.json();
            this.displayResult(result);
        } catch (error) {
            console.error('Conversion error:', error);
            this.showError(error.message);
        } finally {
            this.setLoading(false);
        }
    }

    swapCurrencies() {
        const temp = this.baseCurrency.value;
        this.baseCurrency.value = this.targetCurrency.value;
        this.targetCurrency.value = temp;
        this.clearResults();
    }

    displayResult(result) {
        this.convertedAmount.textContent = 
            `${result.target} ${result.convertedAmount.toLocaleString('en-US', { 
                minimumFractionDigits: 2, 
                maximumFractionDigits: 2 
            })}`;
        
        this.exchangeRate.textContent = 
            `1 ${result.base} = ${result.rate.toLocaleString('en-US', { 
                minimumFractionDigits: 4, 
                maximumFractionDigits: 4 
            })} ${result.target}`;
        
        this.resultSection.classList.remove('hidden');
        this.amountInput.value = '';
    }

    clearResults() {
        this.resultSection.classList.add('hidden');
        this.hideError();
    }

    setLoading(loading) {
        this.convertBtn.disabled = loading;
        this.loadingSpinner.classList.toggle('hidden', !loading);
        this.btnText.textContent = loading ? '' : 'Convert';
    }

    showError(message) {
        this.errorMessage.textContent = message;
        this.errorMessage.classList.remove('hidden');
        this.resultSection.classList.add('hidden');
    }

    hideError() {
        this.errorMessage.classList.add('hidden');
    }
}

// Initialize app when DOM loaded
document.addEventListener('DOMContentLoaded', () => {
    new CurrencyConverter();
});


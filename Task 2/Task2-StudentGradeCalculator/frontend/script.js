const API_BASE = 'http://localhost:8080/api/student';
let subjectCount = 1;

document.addEventListener('DOMContentLoaded', () => {
    setupEventListeners();
    loadHistory();
});

function setupEventListeners() {
    document.getElementById('add-subject').addEventListener('click', addSubject);
    document.getElementById('calculate-btn').addEventListener('click', calculateGrades);
    document.getElementById('refresh-history').addEventListener('click', loadHistory);
}

function addSubject() {
    subjectCount++;
    const container = document.getElementById('subjects-container');
    const subjectRow = document.createElement('div');
    subjectRow.className = 'subject-row';
    subjectRow.innerHTML = `
        <label>Subject ${subjectCount}:</label>
        <input type="number" class="mark-input" min="0" max="100" placeholder="Enter marks">
        <button type="button" class="remove-btn">Remove</button>
    `;
    container.appendChild(subjectRow);
    
    subjectRow.querySelector('.remove-btn').addEventListener('click', () => {
        subjectRow.remove();
    });
}

async function calculateGrades() {
    const inputs = document.querySelectorAll('.mark-input');
    const marks = Array.from(inputs).map(input => parseFloat(input.value)).filter(mark => !isNaN(mark));
    
    if (marks.length === 0) {
        showMessage('Please enter at least one subject mark.', 'error');
        return;
    }
    
    if (marks.some(mark => mark < 0 || mark > 100)) {
        showMessage('Marks must be between 0 and 100.', 'error');
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE}/calculate`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ marks })
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const result = await response.json();
        displayResult(result);
        loadHistory();
        clearInputs();
        showMessage('Result calculated and saved successfully!', 'success');
    } catch (error) {
        console.error('Error:', error);
        showMessage('Error connecting to server. Make sure backend is running on port 8080.', 'error');
    }
}

function displayResult(result) {
    document.getElementById('total-marks').textContent = result.totalMarks;
    document.getElementById('percentage').textContent = result.percentage.toFixed(2);
    document.getElementById('grade').textContent = result.grade;
    document.getElementById('result-section').classList.remove('hidden');
}

function clearInputs() {
    document.querySelectorAll('.mark-input').forEach(input => input.value = '');
    document.getElementById('subjects-container').innerHTML = `
        <div class="subject-row">
            <label>Subject 1:</label>
            <input type="number" class="mark-input" min="0" max="100" placeholder="Enter marks">
            <button type="button" class="remove-btn" style="display: none;">Remove</button>
        </div>
    `;
    subjectCount = 1;
}

async function loadHistory() {
    try {
        const response = await fetch(`${API_BASE}/all`);
        const results = await response.json();
        displayHistory(results);
    } catch (error) {
        console.error('Error loading history:', error);
    }
}

function displayHistory(results) {
    const tbody = document.querySelector('#history-table tbody');
    tbody.innerHTML = '';
    
    results.forEach(result => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${result.id}</td>
            <td>${result.totalMarks}</td>
            <td>${result.percentage.toFixed(2)}%</td>
            <td>${result.grade}</td>
            <td>${new Date(result.createdAt).toLocaleString()}</td>
        `;
        tbody.appendChild(row);
    });
}

function showMessage(message, type) {
    const existing = document.querySelector('.error, .success');
    if (existing) existing.remove();
    
    const msg = document.createElement('div');
    msg.className = type;
    msg.textContent = message;
    document.querySelector('.input-section').appendChild(msg);
    setTimeout(() => msg.remove(), 5000);
}


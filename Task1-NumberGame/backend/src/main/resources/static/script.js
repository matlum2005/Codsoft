const API_BASE = 'http://localhost:8080/api/game';

let currentGame = {
    status: 'notStarted',
    attemptsLeft: 0
};

// DOM Elements
const guessInput = document.getElementById('guessInput');
const submitBtn = document.getElementById('submitBtn');
const newGameBtn = document.getElementById('newGameBtn');
const messageEl = document.getElementById('message');
const attemptsLeftEl = document.getElementById('attemptsLeft');
const statusEl = document.getElementById('status');
const scoresListEl = document.getElementById('scoresList');

// Initialize game
window.addEventListener('load', () => {
    loadTopScores();
    // Auto start new game
    startNewGame();
});

submitBtn.addEventListener('click', makeGuess);
guessInput.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') makeGuess();
});

newGameBtn.addEventListener('click', startNewGame);

async function startNewGame() {
    try {
        const url = `${API_BASE}/start`;
        console.log('Starting game, fetching:', url);
        const response = await fetch(url, { method: 'POST' });
        if (!response.ok) {
            throw new Error(`HTTP ${response.status}: ${response.statusText}`);
        }
        const data = await response.json();
        console.log('Game started:', data);
        
        currentGame.status = data.status;
        currentGame.attemptsLeft = data.attemptsLeft;
        
        updateUI(data);
        messageEl.textContent = data.message;
        messageEl.className = 'message';
        submitBtn.disabled = false;
        guessInput.disabled = false;
        guessInput.value = '';
        guessInput.focus();
    } catch (error) {
        console.error('Start game error:', error);
        let errorMsg = 'Failed to start game.';
        if (error.message.includes('Failed to fetch') || error.message.includes('network')) {
            errorMsg += ' Backend not running on http://localhost:8080. Run `mvn spring-boot:run` first.';
        } else if (error.message.startsWith('HTTP')) {
            errorMsg += ` Server error: ${error.message}`;
        }
        showError(errorMsg);
    }
}

async function makeGuess() {
    const guess = parseInt(guessInput.value);
    
    if (isNaN(guess) || guess < 1 || guess > 100) {
        showError('Please enter a number between 1-100');
        return;
    }

    try {
        const url = `${API_BASE}/guess?guessNum=${guess}`;
        console.log('Making guess:', guess, 'URL:', url);
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`HTTP ${response.status}: ${response.statusText}`);
        }
        const data = await response.json();
        console.log('Guess response:', data);
        
        currentGame.status = data.status;
        currentGame.attemptsLeft = data.attemptsLeft;
        
        updateUI(data);
        
        if (data.status === 'won' || data.status === 'lost') {
            submitBtn.disabled = true;
            guessInput.disabled = true;
            loadTopScores();
        }
    } catch (error) {
        console.error('Guess error:', error);
        showError('Guess failed. Check console.');
    }
}

function updateUI(data) {
    attemptsLeftEl.textContent = data.attemptsLeft || 0;
    statusEl.textContent = currentGame.status;
    statusEl.className = `status ${currentGame.status}`;
    
    if (data.message) {
        messageEl.textContent = data.message;
        messageEl.className = `message ${currentGame.status === 'won' ? 'success' : ''}`;
    }
}

async function loadTopScores() {
    try {
        const url = `${API_BASE}/scores`;
        console.log('Loading scores:', url);
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`HTTP ${response.status}`);
        }
        const scores = await response.json();
        console.log('Scores loaded:', scores);
        
        scoresListEl.innerHTML = '';
        
        if (scores.length === 0) {
            scoresListEl.innerHTML = '<div class="score-item">No scores yet!</div>';
            return;
        }
        
        scores.slice(0, 10).forEach((score, index) => {
            const scoreItem = document.createElement('div');
            scoreItem.className = 'score-item';
            scoreItem.innerHTML = `
                <span>#${index + 1} - Score: ${score.score}</span>
                <span>(${score.attemptsUsed} attempts)</span>
            `;
            scoresListEl.appendChild(scoreItem);
        });
    } catch (error) {
        console.error('Failed to load scores:', error);
    }
}

function showError(msg) {
    messageEl.textContent = msg;
    messageEl.className = 'message error';
}


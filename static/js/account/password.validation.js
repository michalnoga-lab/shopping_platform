const form = document.getElementById('form');
const password = document.getElementById('password');
const passwordConfirmation = document.getElementById('passwordConfirmation');

let isPasswordValid = false;
let isPasswordConfirmationValid = false;

form.addEventListener('input', () => {
    validateInputs();
});

form.addEventListener('submit', e => {
    if (!(isPasswordValid && isPasswordConfirmationValid)) {
        alert(passwordConfirmation.value);
        e.preventDefault();
    }
});

const setError = (element, message) => {
    const inputControl = element.parentElement;
    const errorDisplay = inputControl.querySelector('.error');

    errorDisplay.innerText = message;
    inputControl.classList.add('.error');
    inputControl.classList.remove('.success');
}

const setSuccess = element => {
    const inputControl = element.parentElement;
    const errorDisplay = inputControl.querySelector('.error');

    errorDisplay.innerText = '';
    inputControl.classList.add('.success');
    inputControl.classList.remove('.error');
}

const validateInputs = () => {
    const passwordValue = password.value;
    const passwordConfirmationValue = passwordConfirmation.value;

    if (passwordValue.length < 8) {
        setError(password, 'Minimalna długość hasła to 8 znaków');
        isPasswordValid = false;
    } else {
        setSuccess(password);
        isPasswordValid = true;
    }

    if (passwordConfirmationValue.length < 8) {
        setError(passwordConfirmation, 'Minimalna długość hasła to 8 znaków');
        isPasswordConfirmationValid = false;
    } else {
        setSuccess(passwordConfirmation);
        isPasswordConfirmationValid = true;
    }

    // TODO maksymalne długości hasła komunikat
    // TODO ogólna podsumowująca wszystkie walidacje metoda dla wszystkich JS
}
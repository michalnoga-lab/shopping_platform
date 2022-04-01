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
    } else {
        setSuccess(password)
    }

    if (passwordConfirmationValue.length < 8) {
        setError(passwordConfirmation, 'Minimalna długość hasła to 8 znaków');
    } else {
        setSuccess(passwordConfirmation)
    }

    if (passwordValue.length > 200) {
        setError(password, 'Maksymalna długość hasła to 200 znaków');
    }

    if (passwordConfirmationValue.length > 200) {
        setError(passwordConfirmation, 'Maksymalna długość hasła to 200 znaków');
    }

    if (passwordValue.length >= 8 && passwordConfirmationValue.length >= 8 && (passwordValue !== passwordConfirmationValue)) {
        setError(password, 'Hasło i potwierdzenie hasła nie są takie same');
        setError(passwordConfirmation, 'Hasło i potwierdzenie hasła nie są takie same');
    }

    if (passwordValue.length < 8 ||
        passwordValue.length > 200 ||
        passwordValue !== passwordConfirmationValue) {
        isPasswordValid = false;
    } else {
        isPasswordValid = true;
        setSuccess(password);
    }

    if (passwordConfirmationValue.length < 8 ||
        passwordConfirmationValue.length > 200 ||
        passwordValue !== passwordConfirmationValue) {
        isPasswordConfirmationValid = false;
    } else {
        isPasswordConfirmationValid = true;
        setSuccess(passwordConfirmation);
    }
}
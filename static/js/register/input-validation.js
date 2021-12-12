const form = document.getElementById('form');
const username = document.getElementById('username');
const email = document.getElementById('email');
const password = document.getElementById('password');
const passwordConfirmation = document.getElementById('password-confirmation');

let isUsernameValid = false;
let isEmailValid = false;
let isPasswordValid = false;
let isPasswordConfirmationValid = false;

form.addEventListener('input', e => {
    validateInputs();
});

form.addEventListener('submit', e => {
    if (!(isUsernameValid && isEmailValid && isPasswordValid && isPasswordConfirmationValid)) {
        e.preventDefault();
    }
});

const setError = (element, message) => {
    const inputControl = element.parentElement;
    const errorDisplay = inputControl.querySelector('.error');

    errorDisplay.innerText = message;
    inputControl.classList.add('error');
    inputControl.classList.remove('success');
}

const setSuccess = (element) => {
    const inputControl = element.parentElement;
    const errorDisplay = inputControl.querySelector('.error');

    errorDisplay.innerText = '';
    inputControl.classList.add('success');
    inputControl.classList.remove('error');
}

const validateInputs = () => {
    const usernameValue = username.value.trim();
    const emailValue = email.value.trim();
    const passwordValue = password.value;
    const passwordConfirmationValue = passwordConfirmation.value;

    if (usernameValue === '' || usernameValue.length < 1 || usernameValue.length > 200) {
        setError(username, 'Proszę wprowadzić nazwę użytkownika');
        isUsernameValid = false;
    } else {
        setSuccess(username);
        isUsernameValid = true;
    }

    // TODO walidacja email pod konkretną firmę
    if (!emailValue.match(/(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@[*[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+]*/) || emailValue.length > 200) {
        setError(email, 'Proszę wprowadzić prawidłowy adres email');
        isEmailValid = false;
    } else {
        setSuccess(email);
        isEmailValid = true;
    }

    if (passwordValue.length < 8 || passwordValue.length > 200) {
        setError(password, 'Minimalna długość hasła to 8 znaków');
        isPasswordValid = false;
    } else {
        setSuccess(password);
        isPasswordValid = true;
    }

    if (passwordConfirmationValue.length < 8 || passwordConfirmationValue.length > 200) {
        setError(passwordConfirmation, 'Minimalna długość hasła to 8 znaków');
        isPasswordConfirmationValid = false;
    } else {
        setSuccess(passwordConfirmation);
        isPasswordConfirmationValid = true;
    }

    if (passwordValue !== passwordConfirmationValue) {
        setError(passwordConfirmation, 'Wprowadzone hasła muszą być takie same');
        isPasswordConfirmationValid = false;
    } else {
        setSuccess(passwordConfirmation);
        isPasswordConfirmationValid = true;
    }
}
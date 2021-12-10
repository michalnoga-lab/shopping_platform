const form = document.getElementById('form');
const username = document.getElementById('username');
const email = document.getElementById('email');
const password = document.getElementById('password');
const passwordConfirmation = document.getElementById('password-confirmation');

let isUsernameValid = false;
let isEmailValid = false;
let isPasswordValid = false;
let isPasswordConfirmationValid = false;

form.addEventListener('input', () => {
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

const setSuccess = element => {
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
    const usernamePattern = /[a-zA-Z0-9\s-_]{0,200}/;
    const emailPattern = /(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/;

    if (!usernamePattern.test(usernameValue)) {
        setError(username, 'Dozwolone są tylko litery, cyfry i spacja');
    }

    if (usernameValue === '' || usernameValue.length < 1) {
        setError(username, 'Proszę wprowadzić nazwę użytkownika');
    }

    if (usernameValue.length > 200) {
        setError(username, 'Maksymalna długość nazwy użytkownika to 200 znaków');
    }

    if (!emailPattern.test(emailValue)) {
        setError(email, 'Podano nieprawidłowy email');
    }

    if (emailValue === '' || emailValue.length < 1) {
        setError(email, 'Proszę wprowadzić adres email');
    }

    if (emailValue.length > 200) {
        setError(email, 'Maksymalna długość adresu email to 200 znaków');
    }

    if (passwordValue.length < 8) {
        setError(password, 'Minimalna długość hasła to 8 znaków');
    }

    if (passwordValue.length > 200) {
        setError(password, 'Maksymalna długość hasła to 200 znaków');
    }

    if (passwordConfirmationValue.length < 8) {
        setError(passwordConfirmation, 'Minimalna długość hasła to 8 znaków');
    }

    if (passwordConfirmationValue.length > 200) {
        setError(passwordConfirmation, 'Maksymalna długość hasła to 200 znaków');
    }

    if (passwordValue.length > 8 && passwordConfirmationValue.length > 8 && (passwordValue !== passwordConfirmationValue)) {
        setError(password, 'Hasło i potwierdzenie hasła muszą być takie same');
        setError(passwordConfirmation, 'Hasło i potwierdzenie hasła muszą być takie same');
    }

    if (!usernamePattern.test(usernameValue) ||
        usernameValue === '' ||
        usernameValue.length < 1 ||
        usernameValue.length > 200) {
        isUsernameValid = false;
    } else {
        isUsernameValid = true;
        setSuccess(username);
    }

    if (!emailPattern.test(emailValue ||
        emailValue === '' ||
        emailValue.length < 1 ||
        emailValue.length > 200)) {
        isEmailValid = false;
    } else {
        isEmailValid = true;
        setSuccess(email);
    }

    if (passwordValue.length < 8 ||
        passwordValue.length > 200 ||
        passwordValue !== passwordConfirmationValue) {
        isPasswordValid = false;
        isPasswordConfirmationValid = false;
    } else {
        isPasswordValid = true;
        isPasswordConfirmationValid = true;
        setSuccess(password);
        setSuccess(passwordConfirmation);
    }

    if (passwordConfirmationValue.length < 8 ||
        passwordConfirmationValue.length > 200 ||
        passwordValue !== passwordConfirmationValue) {
        isPasswordValid = false;
        isPasswordConfirmationValid = false;
    } else {
        isPasswordValid = true;
        isPasswordConfirmationValid = true;
        setSuccess(password);
        setSuccess(passwordConfirmation);
    }
}
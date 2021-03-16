const form = document.getElementById('form');
const email = document.getElementById('email');
const password = document.getElementById('password');

let isEmailValid = false;
let isPasswordValid = false;

form.addEventListener('input', () => {
    validateInputs();
});

form.addEventListener('submit', e => {
    if (!(isEmailValid && isPasswordValid)) {
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

// TODO rozpisać walidację np. zbyt długi wprowadzony ciąg itd
const validateInputs = () => {
    const emailValue = email.value;
    const passwordValue = password.value;

    if (emailValue === '' || emailValue.length < 1 || emailValue.length > 200 ||
        !emailValue.match(/(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@[*[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+]*/)) {
        setError(email, 'Proszę wprowadzić adres email');
        isEmailValid = false;
    } else {
        setSuccess(email);
        isEmailValid = true;
    }

    if (passwordValue === '' || passwordValue.length < 1 || passwordValue.length > 200) {
        setError(password, 'Proszę wprowadzić hasło');
        isPasswordValid = false;
    } else {
        setSuccess(password);
        isPasswordValid = true;
    }
}
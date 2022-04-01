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

const validateInputs = () => {
    const emailValue = email.value;
    const passwordValue = password.value;
    const pattern = /(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/;

    if (!pattern.test(emailValue)) {
        setError('Podano nieprawidłowy adres email');
    }

    if (emailValue === '' || emailValue.length < 1) {
        setError(email, 'Proszę wprowadzić adres email');
    }

    if (emailValue.length > 200) {
        setError(email, 'Maksymalna długość adresu to 200 znaków');
    }

    if (passwordValue === '' || passwordValue.length < 1) {
        setError(password, 'Proszę wprowadzić hasło');
    }

    if (passwordValue.length > 200) {
        setError(password, 'Maksymalna długość hasła to 200 znaków');
    }

    if (!pattern.test(emailValue) ||
        emailValue === '' ||
        emailValue.length < 1 ||
        emailValue.length > 200
    ) {
        isEmailValid = false;
    } else {
        isEmailValid = true;
        setSuccess(email);
    }

    if (passwordValue === '' ||
        passwordValue.length < 1 ||
        passwordValue.length > 200) {
        isPasswordValid = false;
    } else {
        isPasswordValid = true;
        setSuccess(password);
    }
}
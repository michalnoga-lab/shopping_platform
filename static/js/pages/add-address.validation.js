const form = document.getElementById('form');
const street = document.getElementById('street');
const phone = document.getElementById('phone');

let isStreetValid = false;
let isPhoneValid = false;

form.addEventListener('input', () => {
    validateInputs();
})

form.addEventListener('submit', e => {
    if (!(isStreetValid && isPhoneValid)) {
        e.preventDefault();
    }
})

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
    const streetValue = street.value;
    const phoneValue = phone.value;

    if (streetValue === '' || streetValue.length < 1 || streetValue.length > 250) {
        setError(street, 'Proszę wprowadzić adres dostawy');
        isStreetValid = false;
    } else {
        setSuccess(street);
        isEmailValid = true;
    }

    if (phoneValue === '' || phoneValue.length < 1 || phoneValue.length > 250) {
        setError(phone, 'Proszę wprowadzić telefon kontaktowy');
        isPhoneValid = false
    } else {
        setSuccess(phone);
        isPhoneValid = true;
    }
}
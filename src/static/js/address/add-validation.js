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
    const streetValue = street.value;
    const phoneValue = phone.value;
    const pattern = /^[a-zA-Z0-9\s-_]{0,200}$/;

    if (!pattern.test(streetValue)) {
        setError(street, 'Dozwolone znaki do użycia to litery, cyfry i spacja');
    }

    if (streetValue === '' || streetValue.length < 1) {
        setError(street, 'Proszę wprowadzić adres dostawy');
    }

    if (streetValue.length > 200) {
        setError(street, 'Maksymalna długość adresu to 200 znaków');
    }

    if (!pattern.test(phoneValue)) {
        setError(phone, 'Dozwolone znaki do użycia to litery, cyfry i spacja');
    }

    if (phoneValue === '' || phoneValue.length < 1) {
        setError(phone, 'Proszę wprowadzić telefon kontaktowy');
    }

    if (phoneValue.length > 200) {
        setError(phone, 'Maksymalna długość telefonu kontaktowego to 200 znaków');
    }

    if (streetValue === '' ||
        streetValue.length < 1 ||
        streetValue.length > 200 ||
        !pattern.test(streetValue)) {
        isStreetValid = false;
    } else {
        isStreetValid = true;
        setSuccess(street);
    }

    if (phoneValue === '' ||
        phoneValue.length < 1 ||
        phoneValue.length > 250 ||
        !pattern.test(phoneValue)) {
        isPhoneValid = false
    } else {
        isPhoneValid = true;
        setSuccess(phone);
    }
}
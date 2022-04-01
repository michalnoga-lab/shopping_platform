const form = document.getElementById('form');
const name = document.getElementById('name');

let isNameValid = false;

form.addEventListener('input', () => {
    validateInputs();
});

form.addEventListener('submit', e => {
    if (!isNameValid) {
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
    const nameValue = name.value;
    const pattern = /^[a-zA-Z0-9\s-_]{0,200}$/;

    if (!pattern.test(nameValue)) {
        setError(name, 'Dozwolone znaki do użycia to litery, cyfry i spacja');
    }

    if (nameValue === '' || nameValue.length < 1) {
        setError(name, 'Proszę wprowadzić nazwę użytkownika');
    }

    if (nameValue.length > 200) {
        setError(name, 'Maksymalna długość nazwy użytkownika to 200 znaków');
    }

    if (!pattern.test(nameValue) ||
        nameValue === '' ||
        nameValue.length < 1 ||
        nameValue.length > 200) {
        isNameValid = false;
    } else {
        isNameValid = true;
        setSuccess(name);
    }
}

 // TODO wspólny plik CSS dla walidacji
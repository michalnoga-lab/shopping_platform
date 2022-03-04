const form = document.getElementById('form');
const quantity = document.getElementById('quantity');

let isQuantityValid = false;

form.addEventListener('input', () => {
    validateInputs();
});

form.addEventListener('submit', e => {
    if (!isQuantityValid) {
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
    const quantityValue = quantity.value;
    const pattern = /^\d{1,6}$/;

    if (!pattern.test(quantityValue)) {
        setError(quantity, 'Dozwolone są tylko cyfry');
    }

    if ( quantityValue === 0 || quantityValue === '0') {
        setError(quantity, 'Ilość musi być większa od zera');
    }

    if (quantityValue.length > 6) {
        setError(quantity, 'Maksymalna ilość cyfr to 6');
    }
    
    if (!pattern.test(quantityValue) ||
        quantityValue === '' ||
        quantityValue.length < 1 ||
        quantityValue < 1 ||
        quantityValue === 0 ||
        quantityValue === '0' ||
        quantityValue.length > 6) {
        isQuantityValid = false;
    } else {
        isQuantityValid = true;
        setSuccess(quantity);
    }
}
const streetRegex = new RegExp('abc', 'g');
const street = document.getElementById('street');

const checkStreet = () => {
    const streetFeedback = document.getElementById('streetFeedback');

    if (street.innerText === '') {
        streetFeedback.textContent = "KROTKI !!!!";
    }

    console.log('---------------------');   // todo cały czas value=""
                                            //todo <input id="street" class="form-control" type="text" name="street" value="">
    console.log(street.value);

    if (streetRegex.test(street.accessKey)) {
        const response = 'PODANY ADRES NIE JEST PRAWIDŁOWY 00000000000000000';
        response.fontcolor('red');
        streetFeedback.textContent = response;
    } else {
        streetFeedback.textContent = '';
    }
};

street.addEventListener('blur', checkStreet);
street.addEventListener('change', checkStreet);
street.addEventListener('input', checkStreet);
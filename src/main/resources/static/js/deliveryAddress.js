const streetRegex = new RegExp('[a-zA-z\\s-ąćęłńóśźżĄĆĘŁŃÓŚŹŻ0-9]+');
const street = document.getElementById('street');

const checkStreet = () => {
    const streetFeedback = document.getElementById('streetFeedback');

    if (street.innerText.length <= 0) {
        streetFeedback.textContent = "KROTKI !!!!";
    }

    if (!streetRegex.test(street.innerText)) {
        const response = 'PODANY ADRES NIE JEST PRAWIDŁOWY';
        streetFeedback.textContent = response;
    } else {
        streetFeedback.textContent = '';
    }
};

street.addEventListener('blur', checkStreet);
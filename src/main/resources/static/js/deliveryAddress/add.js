import {STRING_ONLY} from "../regex_pattern";


const streetRegex = new RegExp('^[a-zA-z\\s-ąćęłńóśźżĄĆĘŁŃÓŚŹŻ0-9]+$', 'g');
const phoneRegex = new RegExp('^[\\d\\s]+$', 'g');

const street = document.getElementById('street');
const phone = document.getElementById('phone');
const form = document.getElementById('form');











form.addEventListener('submit', checkFormData);
package com.app.Utilities;

public interface CustomRegex {

    String TEXT_ONLY_REGEX = "[a-zA-z\\s-ąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+";
    String TEXT_WITH_DIGITS_REGEX = "[a-zA-z\\s-ąćęłńóśźżĄĆĘŁŃÓŚŹŻ0-9]+";
    String TEXT_WITH_DIGITS_AND_SPECIALS_REGEX = "[a-zA-z\\s-ąćęłńóśźżĄĆĘŁŃÓŚŹŻ0-9-_\\.]+";
}
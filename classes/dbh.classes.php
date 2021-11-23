<?php
// error_reporting(0); TODO

class Dbh
{
    protected function connect()
        // TODO tworzenie bazy jeżeli nie istnieje i dodawanie admina
    {
        try {
            $username = "root";
            $password = "";
            return new PDO('mysql:host=localhost;dbname=platform', $username, $password);
        } catch (PDOException $e) {
            print "Error!: " . $e->getMessage() . "<br/>";
            die();
        }
    }
}
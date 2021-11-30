<?php
// error_reporting(0); TODO

class Dbh
{
    protected function connect()
        // TODO tworzenie bazy jeżeli nie istnieje
    {
        try {
            $username = "root";
            $password = "root";
            return new PDO('mysql:host=localhost;dbname=platform', $username, $password);
        } catch (PDOException $e) {
            print "Error!: " . $e->getMessage() . "<br/>";
            die();
        }
    }
}
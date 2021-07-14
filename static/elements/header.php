<?php
// error_reporting(0); TODO
session_start();
?>

<div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom box-shadow">
    <h5 class="my-0 mr-md-auto font-weight-normal"><a class="btn" href="/">START</a></h5>

    <?php if (isset($_SESSION['id'])) {
        ?>
        <nav class="my-2 my-md-0 mr-md-3">
            <a class="p-2 text-dark" href="/pages/products.php">Produkty</a>
            <a class="p-2 text-dark" href="/pages/addresses.php">Adresy</a>
            <a class="p-2 text-dark" href="/pages/account.php">Konto</a>
            <a class="p-2 text-dark" href="/pages/cart.php">Koszyk</a>
            <a class="p-2 text-dark" href="/pages/history.php">Historia</a>
        </nav>
        <a class="btn btn-outline-danger header-btn" href="/pages/logout.php">Wyloguj</a>
        <?php
    } else {
        ?>
        <a class="btn btn-outline-success header-btn" href="/pages/login.php">Logowanie</a>
        <a class="btn btn-outline-primary header-btn" href="/pages/register.php">Rejestracja</a>
        <?php
    }
    ?>

</div>
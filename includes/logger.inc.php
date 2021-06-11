<?php

class Logger
{
    public function systemEvent($event)
    {
        $event = date("Y m d H i s ") . ' ' . $event;
        $file = fopen('./logs/' . date('Ymd'), 'a') or die('Unable to open file');
        fwrite($file, $event . "\n");
        fclose($file);
    }
}
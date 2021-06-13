<?php

class Logger
{
    public function systemEvent($event)
    {
        $ip = $_SERVER['HTTP_X_FORWARDED_FOR'] ?: $_SERVER['HTTP_CLIENT_IP'] ?: $_SERVER['REMOTE_ADDR'];
        $agent = $_SERVER['HTTP_USER_AGENT'];
        $event = date("Y-m-d H:i:s") . ';' . $ip . ';' . $event . ';' . $agent;
        $file = fopen('../logs/' . date('Ymd'), 'a') or die('Unable to open file');
        fwrite($file, $event . "\n");
        fclose($file);
    }
}
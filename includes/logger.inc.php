<?php

class Logger
{
    public function saveEvent($event)
    {
        $event = date("Y.m.d.H.i.s") . ' ' . $event;
        //$name = date('Ymd');
        $name = 'filename';
        $path = '../logs/' . $name;
        $file = fopen($path, 'w+') or die('Unable to open file');
        fwrite($file, $event . '\n');
        fclose($file);
    }
}
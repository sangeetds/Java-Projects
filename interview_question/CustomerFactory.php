<?php


class CustomerFactory
{
    private static int $initial = 0;

    public static function createCustomer(): Customer
    {
        $id = self::$initial++;
        $name = substr(
            str_shuffle(
                str_repeat(
                    $x='0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ', ceil(24 / strlen($x))
                )
            ), 1, 24);

        return new Customer($id, $name);
    }
}
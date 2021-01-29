<?php


class SalesPersonFactory
{
    private static int $count = 0;

    public static function createSalesPerson(bool $active): SalesPerson
    {
        $id = self::$count++;
        $name = substr(
            str_shuffle(
                str_repeat(
                    $x='0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ', ceil(24 / strlen($x))
                )
            ), 1, 24);

        return new SalesPerson($id, $name, $active);
    }
}
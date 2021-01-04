<?php


class Driver
{
    public function main(): void
    {
        $shoppingMalls = new DoubleMall(12, 10);

        for ($i = 0; $i < 10; $i++) {
            $newCustomer = CustomerFactory::createCustomer();
            $shoppingMalls->allotSalesPerson($newCustomer);
        }
    }
}
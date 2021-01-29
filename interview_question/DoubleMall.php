<?php

class DoubleMall
{
    private ShoppingMall $firstShoppingMall;
    private ShoppingMall $secondShoppingMall;

    /**
     * DoubleMall constructor.
     * @param $firstMallCapacity
     * @param $secondMallCapacity
     */
    public function __construct($firstMallCapacity, $secondMallCapacity)
    {

        try {
            $randomFirstActiveNumber = random_int(0, $firstMallCapacity);
            $randomSecondActiveNumber = random_int(0, $secondMallCapacity);
        } catch (Exception $e) {
            echo "Error in getting a random value";
        }

        $this->firstShoppingMall = new ShoppingMall($firstMallCapacity, $randomFirstActiveNumber);
        $this->secondShoppingMall = new ShoppingMall($secondMallCapacity, $randomSecondActiveNumber);
    }

    public function allotSalesPerson(Customer $customer): void
    {
        $firstMallSalesPersonCount = $this->firstShoppingMall->getActiveSalesPersonCount();
        $secondMallSalesPersonCount = $this->secondShoppingMall->getActiveSalesPersonCount();

        echo "First Mall has " . $firstMallSalesPersonCount . " active sales persons.";
        echo "Second Mall has " . $secondMallSalesPersonCount . " active sales persons.";

        if ($firstMallSalesPersonCount > $secondMallSalesPersonCount) {
            echo "Customer " . $customer->getName() . "is proceeded towards first shopping mall";
        }
        else if ($firstMallSalesPersonCount < $secondMallSalesPersonCount) {
            echo "Customer " . $customer->getName() . "is proceeded towards second shopping mall";
        }
        else if ($this->firstShoppingMall->getCapacity() > $this->secondShoppingMall->getCapacity()) {
            echo "Customer " . $customer->getName() . "is proceeded towards first shopping mall";
        }
        else if ($this->firstShoppingMall->getCapacity() < $this->secondShoppingMall->getCapacity()) {
            echo "Customer " . $customer->getName() . "is proceeded towards second shopping mall";
        }
        else {
            echo "Customer " . $customer->getName() . "is proceeded towards first shopping mall";
        }
    }
}

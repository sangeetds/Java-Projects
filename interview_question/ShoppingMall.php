<?php

class ShoppingMall
{
    private int $capacity;
    private array $salesPersonsList;

    /**
     * ShoppingMall constructor.
     * @param $capacity
     * @param $active
     */
    public function __construct(int $capacity, int $active)
    {
        $this->capacity = $capacity;
        $this->salesPersonsList = array();

        for ($index = 0; $index < $capacity; $index++) {
            $this->salesPersonsList[] = ($index < $active) ?
                SalesPersonFactory::createSalesPerson(true) :
                SalesPersonFactory::createSalesPerson(false);
        }
    }

    /**
     * @return array
     */
    public function getSalesPersonsList(): array
    {
        return $this->salesPersonsList;
    }

    /**
     * @return int
     */
    public function getCapacity(): int
    {
        return $this->capacity;
    }

    public function getActiveSalesPersonCount(): int
    {
        $this->getSalesPersonActiveStatus();
        return count(array_filter($this->salesPersonsList, "SalesPerson::isSalesPersonActive"));
    }

    private function getSalesPersonActiveStatus(): void
    {
        foreach ($this->salesPersonsList as $salesPerson)
        {
            try {
                $active = random_int(0, 1) === 1;
                $salesPerson->setActive($active);
            } catch (Exception $e) {
                echo "Error in getting a random value";
            }
        }
    }
}
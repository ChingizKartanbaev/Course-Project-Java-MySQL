package CorseProject.dao;


import CorseProject.models.Basket;

import java.util.ArrayList;

public interface OrderRep {

    void createOrder(ArrayList<Basket>basket);
}

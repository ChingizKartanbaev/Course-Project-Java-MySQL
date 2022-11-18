package CorseProject.dao;

import CorseProject.models.Client;

import java.util.List;

public interface ClientRep {

    void createClient (Client client);
    List<Client> getAllClient ();
    Client getClientById (long id);
    void updateClient (int id);
    void deleteClient (long id);

}

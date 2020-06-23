package br.booksapi.books.google;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class GoogleClient implements AutoCloseable{

    private Client client = ClientBuilder.newClient();

    public GoogleClient(){
    }

    protected WebTarget toTarget(String host){
        return this.client.target(host);
    }

    @Override
    public void close() throws Exception {
        this.client.close();
    }

}

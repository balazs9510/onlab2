package hu.bme.aut.client.Service;

import hu.bme.aut.client.Model.Experiment;

public class ExperimentService {
    public Experiment getExperimnet(String id){
        return new Experiment(id,id + "Lol", "test", "Vége");
    }
}

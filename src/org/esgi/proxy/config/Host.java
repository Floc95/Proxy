package org.esgi.proxy.config;

import java.util.List;

public class Host {
    public String name;
    public String lb;
    public List<String> workers = null;
    public int incoming;
    public int outcoming;

    public Host(String _name, String _lb, List<String> _workers, int _in, int _out){
        this.name = _name;
        this.lb = _lb;
        this.workers = _workers;
        this.incoming = _in;
        this.outcoming = _out;
    }

    @Override
    public String toString() {
        return null;
    }

}
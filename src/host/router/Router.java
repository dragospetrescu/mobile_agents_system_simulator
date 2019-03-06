package host.router;

import host.Host;

import java.util.List;
import java.util.Map;

public class Router {

    private int id;
    private Host host;
    private Map<Router, Router> nextHopMap;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Router router = (Router) o;

        if (id != router.id) return false;
        return host.equals(router.host);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + host.hashCode();
        return result;
    }
}

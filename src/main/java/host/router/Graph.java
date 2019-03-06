package host.router;

import java.util.List;
import java.util.Map;

public class Graph {

    List<Router> routers;
    Map<Pair, Integer> connectionCost;



    class Pair {

        private Router source;
        private Router destination;

        public Pair(Router source, Router destination) {
            this.source = source;
            this.destination = destination;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair pair = (Pair) o;

            if (source != null ? !source.equals(pair.source) : pair.source != null) return false;
            return destination != null ? destination.equals(pair.destination) : pair.destination == null;
        }

        @Override
        public int hashCode() {
            int result = source != null ? source.hashCode() : 0;
            result = 31 * result + (destination != null ? destination.hashCode() : 0);
            return result;
        }
    }
}

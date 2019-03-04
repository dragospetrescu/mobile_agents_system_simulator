package router;

public class Connection {

    private int id;
    private Router from;
    private Router to;
    private int cost;

    public Connection(int id, Router from, Router to, int cost) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.cost = cost;
    }
}

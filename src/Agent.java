public class Agent {

    private int id;
    private Host host;
    private Host destination;

    private int workTime;
    private int migrateTime;
    private boolean isMigrating;

    public Agent(int id, Host host) {
        this.id = id;
        this.host = host;
    }

    public boolean isWorkDone() {
        return !isMigrating && workTime <= 0;
    }

    public void work() {
        workTime--;
    }

    public Host getDestination() {
        return destination;
    }

    public void arrived() {
        host = destination;
        isMigrating = false;
        assignWork();
        Logger.log(this + " arrived at " + destination + " and was assigned work for " + workTime);
    }

    public void assignWork() {
        workTime = TimeAssigner.assignWork();
    }

    public void startMigrating(Host destinationHost) {
        isMigrating = true;
        migrateTime = TimeAssigner.assignMigrateTime(host, destinationHost);
        this.destination = destinationHost;
        Logger.log(this + " migrates to " + destination + " for " + migrateTime);
    }

    public boolean isArrived() {
        return isMigrating && migrateTime == 0;
    }

    public void travel() {
        migrateTime--;
    }

    @Override
    public String toString() {
        return "Agent " + id;
    }
}

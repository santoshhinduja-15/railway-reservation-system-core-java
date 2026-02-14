public class Train {

    private String trainNo, name, source, destination;
    private int sleeperTotal;
    private int acTotal;

    public Train(String trainNo, String name, String source,
                 String destination, int sleeperTotal, int acTotal) {

        this.trainNo = trainNo;
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.sleeperTotal = sleeperTotal;
        this.acTotal = acTotal;
    }

    @Override
    public String toString() {
        return trainNo + "|" + name + "|" + source + "|" + destination + "|"
                + sleeperTotal + "|" + sleeperTotal + "|"
                + acTotal + "|" + acTotal;
    }
}
public class Ticket {

    private String ticketId, passengerId, trainNo;
    private String journeyDate, seatClass, status;
    private int fare;

    public Ticket(String ticketId, String passengerId,
                  String trainNo, String journeyDate,
                  String seatClass, String status, int fare) {

        this.ticketId = ticketId;
        this.passengerId = passengerId;
        this.trainNo = trainNo;
        this.journeyDate = journeyDate;
        this.seatClass = seatClass;
        this.status = status;
        this.fare = fare;
    }

    @Override
    public String toString() {
        return ticketId + "|" + passengerId + "|" + trainNo + "|"
                + journeyDate + "|" + seatClass + "|"
                + status + "|" + fare;
    }
}
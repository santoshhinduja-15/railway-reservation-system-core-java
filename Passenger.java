public class Passenger {

    private String passengerId, name, gender;
    private int age;

    public Passenger(String passengerId, String name, int age, String gender) {
        this.passengerId = passengerId;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return passengerId + "|" + name + "|" + age + "|" + gender;
    }
}

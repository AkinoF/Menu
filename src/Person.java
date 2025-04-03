public class Person {
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String postalCode;
    private String birthday;

    // Конструктор
    public Person(String firstName, String lastName, String street, String city, String postalCode, String birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.birthday = birthday;
    }

    // Геттеры
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getPostalCode() { return postalCode; }
    public String getBirthday() { return birthday; }
}
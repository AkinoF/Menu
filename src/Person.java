import java.io.Serializable;

public class Person implements Serializable {
    private static final long serialVersionUID = 1L; // Рекомендуется добавить это поле

    String firstName;
    String lastName;
    String street;
    String city;
    String postalCode;
    String birthday;

    public Person(String firstName, String lastName, String street, String city, String postalCode, String birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.birthday = birthday;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getPostalCode() { return postalCode; }
    public String getBirthday() { return birthday; }
}
package com.company;

public final class Employee {

    private String ID;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String rc;

    public Employee(String ID, String firstName, String lastName, String patronymic, String rc) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.rc = rc;
    }

    public String getFullName(){
        return lastName+" "+firstName+" "+patronymic;
    }

    public String getID() {
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getRc() {
        return rc;
    }

    @Override
    public String toString() {
        return getFullName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;

        Employee employee = (Employee) o;

        if (!getID().equals(employee.getID())) return false;
        if (!getFirstName().equals(employee.getFirstName())) return false;
        if (!getLastName().equals(employee.getLastName())) return false;
        if (getPatronymic() != null ? !getPatronymic().equals(employee.getPatronymic()) : employee.getPatronymic() != null)
            return false;
        return getRc().equals(employee.getRc());
    }

    @Override
    public int hashCode() {
        int result = getID().hashCode();
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        result = 31 * result + (getPatronymic() != null ? getPatronymic().hashCode() : 0);
        result = 31 * result + getRc().hashCode();
        return result;
    }
}

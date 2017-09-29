package com.company;

public class Course {

    private String name;
    private boolean state;
    private String rcName;
    private String userID;

    public Course(String name, String state, String rcName, String userID) {
        this.name = name;
        this.state = state.equals("Завершенo");
        this.rcName = rcName;
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public String getRcName() {
        return rcName;
    }

    public String getUserID() {
        return userID;
    }

    public boolean isSuccess() {
        return state;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;

        Course course = (Course) o;

        if (state != course.state) return false;
        if (!getName().equals(course.getName())) return false;
        if (!rcName.equals(course.rcName)) return false;
        return userID.equals(course.userID);
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + (state ? 1 : 0);
        result = 31 * result + rcName.hashCode();
        result = 31 * result + userID.hashCode();
        return result;
    }
}

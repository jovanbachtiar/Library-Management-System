package library;

public abstract class User{
  private String name;
  private String phoneNum;

  public User(String name, String phoneNum){
    this.name = name;
    this.phoneNum = phoneNum;
  }

  public String getName(){
    return name;
  }
  public String getPhoneNum(){
    return phoneNum;
  }

  public abstract void displayInfo();
}


class Student extends User{
  public Student(String name, String phoneNum){
    super(name, phoneNum);
  }

  @Override
  public void displayInfo(){
    System.out.println("Student Name: " + getName());
    System.out.println("Phone Numeber: " + getPhoneNum());
  }
}

class Faculty extends User{
  public Faculty(String name, String phoneNum){
    super(name, phoneNum);
  }

  @Override
  public void displayInfo(){
    System.out.println("Faculty name: "+ getName());
    System.out.println("Phone Number: "+ getPhoneNum());
  }
}

//Factory pattern
class UserFactory{
  public static User createUser(String type, String name, String phoneNum){
    switch(type.toLowerCase()){
      case "student":
        return new Student(name, phoneNum);

      case "faculty":
        return new Faculty(name, phoneNum);
        
      default:
        throw new IllegalArgumentException("Unknown user type.");
    }
  }
}

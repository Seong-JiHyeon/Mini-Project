import java.io.*;

public class StudentInfo implements Serializable{//binary file�� �����ϱ� ���� ����
	private String name;
	private char gender;
	private String id;
	private String department;
	private int age;//�ʵ� ����
	
	StudentInfo(){
		
	}//default constructor
	
	StudentInfo(String name, char gender, String id, String department, int age){
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.id = id;
		this.department = department;
	}//�ʵ� ���� constructor
	
	public String toString(){
		return name+"("+age+")     "+gender+"        "+id+"       "+department;
	}//��ü�� string���� ��ȯ
	
	//private�� ���� get �Լ�
	public String getName(){
		return name;
	}
	
	public String getID(){
		return id;
	}
	
	public String getDepartment(){
		return department;
	}
	
	public int getAge(){
		return age;
	}
	
	public char getGender(){
		return gender;
	}
	
	//private�� ���� set �Լ�
	public void setName(String name){
		this.name = name;
	}
	
	public void setGender(char gender){
		this.gender = gender;
	}
	
	public void setID(String id){
		this.id = id;
	}
	
	public void setAge(int age){
		this.age=age;
	}
	
	public void setDepartment(String department){
		this.department= department;
	}
}

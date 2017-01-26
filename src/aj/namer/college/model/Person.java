package aj.namer.college.model;

public class Person {
	
	private String personName;
	private int personRoll;
	
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public int getPersonRoll() {
		return personRoll;
	}
	public void setPersonRoll(int personRoll) {
		this.personRoll = personRoll;
	}
	@Override
	public String toString() {
		return "Person [personName=" + personName + ", personRoll="
				+ personRoll + "]";
	}
	
	
}

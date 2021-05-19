package assignment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import exception.DeadlineFormatException;

public abstract class Assignment implements AssignmentInput{

	protected AssignmentKind kind = AssignmentKind.University;
	protected String name;
	protected String professor;
	protected String classname;
	protected String deadline;
	protected String[] deadlineset;


	public Assignment() {
	}

	public Assignment(AssignmentKind kind) {
		this.kind = kind;
	}

	public Assignment(String name,
			String deadline) {
		this.name= name;
		this.deadline= deadline;
	}

	public Assignment(AssignmentKind kind,
			String name,
			String professor,
			String classname,
			String deadline) {
		this.kind= kind;
		this.name= name;
		this.professor= professor;
		this.classname= classname;
		this.deadline= deadline;


	}


	//getters and setters
	public AssignmentKind getKind() {
		return kind;
	}

	public void setKind(AssignmentKind kind) {
		this.kind = kind;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfessor() {
		return professor;
	}

	public void setProfessor(String professor) {
		this.professor = professor;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) throws DeadlineFormatException{
		SimpleDateFormat dateForm = new SimpleDateFormat("yyyy/MM/dd/HH/mm");
		
		try{
			dateForm.format(dateForm.parse(deadline));
		}
		catch(Exception e) {
			throw new DeadlineFormatException();
		}
		this.deadline = deadline;
	}

	public String[] getDeadlineset() {
		return deadlineset;
	}

	public void setDeadlineset(String[] deadlineset) {
		this.deadlineset = deadlineset;
	}


	public abstract void printInfo();


	public void setAssignmentName(Scanner input) {
		System.out.print("assignment name: ");
		String name = input.next();
		this.setName(name);
	}

	public void setAssignmentProfessor(Scanner input) {
		System.out.print("class professor: ");
		String professor = input.next();
		this.setProfessor(professor);
	}

	public void setAssignmentClassname(Scanner input) {

		System.out.print("class name: ");
		String classname = input.next();
		this.setClassname(classname);
	}

	public void setAssignmentDeadline(Scanner input) {
		String deadline="";
		//deadline이 지정된 형식인지 판단해야함
		//while조건문에서 판단하기 불가능하다고 판단하여 새로운 변수 추가
		boolean iscorrectformat=false;
		while(!iscorrectformat) { 
			System.out.print("deadline: ");
			deadline = input.next();
			try {
				this.setDeadline(deadline);
				//오류가 발생하지 않을때만 아래행이 실행됨
				iscorrectformat=true;
			}
			catch(DeadlineFormatException e) {
				System.out.println("Incorrect Deadline Format. put the Deadline that contains /");
				System.out.println("format 형식: YYYY/MM/DD/HH/MM");
				//e.printStackTrace();
			}
		}

	}

	public String getKindString() {
		String akind = "none";
		switch(this.kind) {
		case University:
			akind = "Univ.";
			break;
		case Personal:
			akind = "Personal";
			break;
		case Quick:
			akind = "Quick";
		default:
		}
		return akind;
	}


	//deadline으로부터 몇시간 남았는지 알리는 method
	public void printremainingtime() {
		deadlineset = this.deadline.split("/");

		Calendar Nowcal = Calendar.getInstance();
		Calendar Deadcal = new GregorianCalendar(
				Integer.parseInt(deadlineset[0]),
				Integer.parseInt(deadlineset[1])-1,
				Integer.parseInt(deadlineset[2]),
				Integer.parseInt(deadlineset[3]),
				Integer.parseInt(deadlineset[4]));
		//달 = 현재 달-1

		long diffSec = (Deadcal.getTimeInMillis()-Nowcal.getTimeInMillis())/1000;
		//System.out.println(diffSec);

		long d = diffSec / (24*60*60);
		diffSec = diffSec % (24*60*60);
		long h = diffSec / (60*60);
		diffSec = diffSec % (60*60);
		long m =diffSec / (60);
		diffSec = diffSec % (60);

		System.out.println("deadline까지 "+d+"일 "+h+"시간 "+m+"분 "+diffSec+"초 남았습니다.");
		if(d==0) System.out.println("Hurry up!!");

	}
}

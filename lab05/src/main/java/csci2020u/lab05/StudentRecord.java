package csci2020u.lab05;


public class StudentRecord {

	private String studentID;
	private float assignmentsMark;
	private float midtermMark;
	private float examMark;
	private float finalMark;
	private char letterGrade;


	public StudentRecord(String sid, float asmtsMark, float midtermMark, 
						 float examMark) {

		studentID = sid;
		assignmentsMark = asmtsMark;
		this.midtermMark = midtermMark;
		this.examMark = examMark;

		// Calculate final mark (asmts: 20%, midterm: 30%, final exam: 50%)
		finalMark = (float)(assignmentsMark*.2 + midtermMark*.3 + examMark*.5);
		
		// Determine student's letter grade based on final mark
		if (finalMark >= 80 && finalMark <= 100) {
			letterGrade = 'A';
		} else if (finalMark >= 70 && finalMark <= 79) {
			letterGrade = 'B';
		} else if (finalMark >= 60 && finalMark <= 69) {
			letterGrade = 'C';
		} else if (finalMark >= 50 && finalMark <= 59) {
			letterGrade = 'D';
		} else {
			letterGrade = 'F';
		}
	}

	public String getStudentID() {
		return studentID;
	}

	public float getAssignmentsMark() {
		return assignmentsMark;
	}

	public float getMidtermMark() {
		return midtermMark;
	}

	public float getExamMark() {
		return examMark;
	}

	public float getFinalMark() {
		return finalMark;
	}

	public char getLetterGrade() {
		return letterGrade;
	}

}

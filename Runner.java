import java.io.IOException;

public class Runner {
	public static void main(String[] args) throws IOException, InterruptedException {
		// LearningStatus ls = new LearningStatus("saved1.txt");
		LearningStatus ls = new LearningStatus();
		ls.student_id = "1987";

		Thread.sleep(2000);

		ls.lesson_statuses.put("1.2", "completed");
		ls.lesson_statuses.put("1.3", "started");

		ls.updateTimestamp();

		ls.exportToSaveFile("saved2.txt");
	}
}
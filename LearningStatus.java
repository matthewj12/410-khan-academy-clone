import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class LearningStatus {
	// seconds since ~2022-1-1
	private static long base_date = 1641017;
	// measures seconds since base_date
	public String timestamp;
	public HashMap<String, String> lesson_statuses;
	public String student_id;	

	public LearningStatus() {
		updateTimestamp();
		lesson_statuses = new HashMap<String, String>();
		lesson_statuses.put("1.1", "unstarted");
	}
	
	public LearningStatus(String file_path) throws IOException {
		loadFromSavedFile(file_path);
		
		updateTimestamp();
		lesson_statuses = new HashMap<String, String>();
	}

	public String toString() {
		String to_return = "";

		to_return += "student id:" + this.student_id + '\n';
		to_return += "timestamp:" + this.timestamp + '\n';

		for (String ls : this.lesson_statuses.keySet()) {
			to_return += ls + ":" + this.lesson_statuses.get(ls) + '\n';
		}

		return to_return;
	}

	public void updateTimestamp() {
		timestamp = String.valueOf(((new Date()).getTime() - base_date) / 1000);
	}

	public void loadFromSavedFile(String file_path) throws FileNotFoundException, IOException {
		File saved_file = new File(file_path);
		Scanner scnr = new Scanner(saved_file);

		while (scnr.hasNextLine()) {
			String line = scnr.nextLine();
			if (line.contains("timestamp")) {
				this.timestamp = line.split(":")[1];
			}
			else if (line.contains("student id")) {
				this.student_id = line.split(":")[1];
			}
			else if (line.contains("lesson statuses")) {
				scnr.nextLine();
				// lesson statuses always come at the end very end of the file
				while (scnr.hasNextLine()) {
					String[] split = scnr.nextLine().split(":");
					this.lesson_statuses.put(split[0].strip(), split[1]);
				}
			}
		}

		scnr.close();
	}
		
	public void exportToSaveFile(String file_path) throws IOException {
		FileWriter fw = new FileWriter(file_path);

		fw.write("timestamp:" + this.timestamp + '\n');
		fw.write("student id:" + this.student_id + '\n');
		fw.write("lesson statuses:\n");
		for (String ls : this.lesson_statuses.keySet()) {
			fw.write('\t' + ls + ": " + this.lesson_statuses.get(ls) + '\n');
		}
		
		fw.close();
	}
}
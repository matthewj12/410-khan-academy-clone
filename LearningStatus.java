import java.util.Date;
import java.util.HashMap;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

enum LessonStatus {
	UNSTARTED, STARTED, COMPLETED
}

class LearningStatus {
	// about 2022-1-1 (seconds since 1970-1-1)
	private static long base_date = 1641017;
	// measures seconds since base_date
	public long timestamp;
	public HashMap<String, LessonStatus> lesson_statuses;
	public short student_id;

	public LearningStatus() {
	}

	public short lessonStatusToByte(LessonStatus ls) {
		byte to_return;

		switch (ls) {
			case UNSTARTED:
				to_return = 0;
				break;

			case STARTED:
				to_return = 1;
				break;

			case COMPLETED:
				to_return = 2;
				break;
		}

		return to_return;
	}

	public byte[] serialize() {
		// one byte per lesson plus timestamp plus student_id
		ByteBuffer bb = ByteBuffer.allocate(Short.BYTES + Long.BYTES + lesson_statuses.size());

		int cur_index = 0;

		bb.order(ByteOrder.BIG_ENDIAN);

		bb.putLong(cur_index, timestamp);
		cur_index += Long.BYTES;

		bb.putShort(cur_index, student_id);
		cur_index += Short.BYTES;

		for (String key : lesson_statuses.keySet()) {
			bb.putShort(cur_index, lessonStatusToByte(lesson_statuses.get(key)));
			cur_index += Byte.BYTES;
		}

		return bb.array();
	}

	public void updateTimestamp() {
		timestamp = (long) (((new Date()).getTime() - base_date) / 1000);
	}

	public boolean areIdentical(LearningStatus to_compare) {
		if (to_compare.timestamp != timestamp) {
			return false;
		}

		if (!to_compare.lesson_statuses.keySet().equals(lesson_statuses.keySet()) {
			return false;
		}

		for (String key : to_compare.lesson_statuses.keySet()) {
			if (!to_compare.lesson_statuses.get(key).equals(lesson_statuses.get(key))) {
				return false;
			}
		}

		return true;
	}
}
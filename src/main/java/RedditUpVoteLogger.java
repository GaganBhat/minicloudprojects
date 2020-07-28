import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class RedditUpVoteLogger {

	private static final String REDDIT_LINK =
			"https://www.reddit.com/r/ProgrammerHumor/comments/hytwfl/github_notifications_in_a_nutshell/.json";

	public static final String USER_AGENT = "<java>:<app RedditUpVoteLogger>:<version 0.1> (by /u/<reddit SoProTheyGoWoah>)";

	public static void main(String[] args) throws IOException, InterruptedException {


		Timer timer = new Timer();
		timer.schedule(new RedditFileWriter(), 0, 30000);

	}

	static class RedditFileWriter extends TimerTask {

		PrintWriter writer;
		public RedditFileWriter() throws FileNotFoundException, UnsupportedEncodingException {
			writer = new PrintWriter("redditCounter.txt", "UTF-8");
		}

		public void run() {
			try {
				writer.println(getRedditCount() + ", " + java.time.LocalTime.now());
			} catch (Exception e) {
				e.printStackTrace();
			}
			writer.flush();
		}
	}

	public static String getRedditCount() throws InterruptedException, IOException {
		URLConnection redditURL = (new URL(REDDIT_LINK)).openConnection();
		Thread.sleep(2000);
		redditURL.setRequestProperty("User-Agent", USER_AGENT);

		String jsonString = IOUtils.toString(redditURL.getInputStream(), "UTF-8");

		JSONObject object = (JSONObject) new JSONArray(jsonString).get(0);

		return (((JSONObject) ((JSONObject) ((JSONArray) ((JSONObject) object.get("data")).get("children")).get(0)).get("data")).get("ups")).toString();
	}

}

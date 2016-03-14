import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Terminal {
	final static String FFMPEG_PATH = "/usr/local/bin/";
	static String inp = "";
	static String out = "";
	public static String execute(String command) {
		StringBuilder strBuild = new StringBuilder();
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = 
                            new BufferedReader(new InputStreamReader(p.getInputStream()));

                        String line = "";			
			while ((line = reader.readLine())!= null) {
				strBuild.append(line + System.lineSeparator());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		String outputJson = strBuild.toString().trim();
		return outputJson;
	}
	public static String executeFFMPEG(String command){
		return execute(FFMPEG_PATH + command);
	}
	public static String getVideoWithGreyStream(String input, String output){
		executeFFMPEG("ffmpeg -y -i "+ input + " -vf format=gray " + output);
		executeFFMPEG("ffmpeg -y -i "+ output + " -an  " + output);
		return executeFFMPEG("ffmpeg -y -i "+ input + " -i  " + output + " -c copy -map 0 -map 0:v " + output);
	}
	public static String getVideoStreams(String input ){
		return executeFFMPEG("ffprobe -show_streams "+ input );
	}
	public static void main(String[]args) throws IOException{
		inp = "video.mp4";
		out = "output.mp4";
		System.out.println(getVideoWithGreyStream(inp, out));
		System.out.println(getVideoStreams(out));
	}
}

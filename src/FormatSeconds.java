
public class FormatSeconds {
	public static String formatSeconds(int value, String delimiter)
	{
		String result = "";
	    int hours = value / 3600;
	    int secondsLeft = value - hours * 3600;
	    int minutes = secondsLeft / 60;
	    int seconds = secondsLeft - minutes * 60;
	    
	    if (hours < 10) result += "0";
	    result += hours + delimiter;

	    if (minutes < 10) result += "0";
	    result += minutes + delimiter;

	    if (seconds < 10) result += "0";
	    result += seconds;

	    return result;
	}
}

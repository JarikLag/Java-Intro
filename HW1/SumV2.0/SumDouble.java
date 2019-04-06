public class SumDouble {	
    public static void main(String[] args) {
        double summary = 0;
	    for (int i = 0; i < args.length; i++) {
		    for (int j = 0; j < args[i].length(); j++) {
				int begin = j, end = j;
                while (j < args[i].length() && !Character.isWhitespace(args[i].charAt(j))) {
                    end++;
                    j++;
                }
                if ((end - begin) != 0) {
                    summary += Double.parseDouble(args[i].substring(begin, end));
				}
            }
        }
        System.out.println(summary);
    }
}
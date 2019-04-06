public class Sum
{	
    public static void main(String[] args)
    {
        int summary = 0;
	    for(int i = 0; i < args.length; i++)
        {
		    for(int j = 0; j < args[i].length(); j++)
            {
                String currentNumber = "";
                while(j < args[i].length() && (args[i].charAt(j) <= '9' && args[i].charAt(j) >= '0' || args[i].charAt(j) == '-'))
                {
                    currentNumber += args[i].charAt(j);
                    j++;
                }
                int tempNumber = 0;
                if(currentNumber != "")
                    tempNumber = Integer.parseInt(currentNumber);
                summary += tempNumber;
            }
        }
        System.out.println(summary);
    }
}
package InWork.Operations;

public class NumberHandler {

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
    public static void ConvertPropertStringToDouble()
    {

    }
}

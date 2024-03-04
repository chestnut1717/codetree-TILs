import java.util.HashSet;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        HashSet<Integer> s = new HashSet<>();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        // sc.nextLine();
        for(int i = 0; i < n; i++) {
            String str = sc.next().trim();
            int a = sc.nextInt();

            if(str.equals("find")) {
                System.out.println(s.contains(a));
            }
            else if(str.equals("add")) {
                s.add(a);
            }
            else if(str.equals("remove")) {
                s.remove(a);
            }

        }




    }
}
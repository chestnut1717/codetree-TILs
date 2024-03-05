import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = null;

        TreeSet<Integer> s = new TreeSet<>();

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            String cmd = st.nextToken();
            if(cmd.equals("add")) {
                int a = Integer.parseInt(st.nextToken());
                s.add(a);
            }
            else if(cmd.equals("remove")) {
                int a = Integer.parseInt(st.nextToken());
                s.remove(a);
            }
            else if(cmd.equals("find")) {
                int a = Integer.parseInt(st.nextToken());
                if(s.contains(a)) System.out.println(true);
                else System.out.println(false);
            }
            else if(cmd.equals("lower_bound")) {
                int a = Integer.parseInt(st.nextToken());
                Integer result = s.ceiling(a);
                if(result==null) System.out.println("None");
                else System.out.println(result);
            }
            else if(cmd.equals("upper_bound")) {
                int a = Integer.parseInt(st.nextToken());
                Integer result = s.higher(a);
                if(result==null) System.out.println("None");
                else System.out.println(result);
            }
            else if(cmd.equals("largest")) {
                if(s.size() == 0) System.out.println("None");
                else System.out.println(s.last());
            }
            else if(cmd.equals("smallest")) {
                if(s.size() == 0) System.out.println("None");
                else System.out.println(s.first());
            }
        }

    }
}
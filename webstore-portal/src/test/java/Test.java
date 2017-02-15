import java.util.ArrayList;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		List<String> list =new ArrayList<String>();
		list.add("abc");
		tt(list);
		System.out.println(list.size());
		System.out.println(list.toString());
	}
	
	public static void tt(List<String> list){
		list.set(0, "aa");
		list.add("efg");
	}
}

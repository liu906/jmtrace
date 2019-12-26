package agent;

public class Print {
    public static void traceArrayRead(Object arr, int index, Object value) {
        String rw = "R";
        long treadNumber = Thread.currentThread().getId();
        long id = ((long)System.identityHashCode(arr) << 32) + index;
        String member = arr.getClass().getCanonicalName();
        member = member.substring(0, member.length() - 2) + "[" + index + "]";

        System.out.printf("%s %d %x %s\n", rw, treadNumber, id, member);
    }
    public static void traceArrayWrite(Object arr, int index, Object value) {
        String rw = "W";
        long treadNumber = Thread.currentThread().getId();
        long id = ((long)System.identityHashCode(arr) << 32) + index;
        String member = arr.getClass().getCanonicalName();
        member = member.substring(0, member.length() - 2) + "[" + index + "]";

        System.out.printf("%s %d %x %s\n", rw, treadNumber, id, member);

    }
    public static void traceFieldRead(Object obj, String field, Object value) {
        String rw = "R";
        long treadNumber = Thread.currentThread().getId();
        long id = ((long)System.identityHashCode(obj) << 32) + field.hashCode();
        String member = obj.getClass().getCanonicalName() + "." + field;

        System.out.printf("%s %d %x %s\n", rw, treadNumber, id, member);
    }
    public static void traceFieldWrite(Object obj, String field, Object value) {
        String rw = "W";
        long treadNumber = Thread.currentThread().getId();
        long id = ((long)System.identityHashCode(obj) << 32) + field.hashCode();
        String member = obj.getClass().getCanonicalName() + "." + field;

        System.out.printf("%s %d %x %s\n", rw, treadNumber, id, member);
    }
    public static void traceStaticRead(Class<?> cls, String field, Object value) {
        String rw = "R";
        long treadNumber = Thread.currentThread().getId();
        long id = ((long)System.identityHashCode(cls) << 32) + field.hashCode();
        String member = cls.getCanonicalName() + "." + field;
        System.out.printf("%s %d %x %s\n", rw, treadNumber, id, member);
    }
    public static void traceStaticWrite(Class<?> cls, String field, Object value) {
        String rw = "W";
        long treadNumber = Thread.currentThread().getId();
        long id = ((long)System.identityHashCode(cls) << 32) + field.hashCode();
        String member = cls.getCanonicalName() + "." + field;

        System.out.printf("%s %d %x %s\n", rw, treadNumber, id, member);
    }
}

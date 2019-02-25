import java.util.ArrayList;
<<<<<<< HEAD

public class SeatingAlg{
  
  public SeatingAlg() {
    int i = 0;
  }
  
  public ArrayList<Table> generateTables(ArrayList <Student> s, int m) {
    return null;
  }
}
  
=======
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class SeatingAlg {
    private int numStudents;
    private HashMap<String, Student> studentsById;
    ArrayList<Table> res;
    private static int maxRuntime = 2;
    private static boolean bidirectionalFriendship = true;
    private static boolean communist = false;

    public SeatingAlg() {
    }

    public ArrayList<Table> generateTables(ArrayList<Student> var1, int var2) {
        this.studentsById = this.createStudentsById(var1);
        long var3 = System.currentTimeMillis();
        this.numStudents = var1.size();
        if (bidirectionalFriendship) {
            var1 = this.makeFriendsBidirectional(var1);
        }

        var1 = this.sortStudentsByNumFriends(var1);
        this.res = new ArrayList();
        double var5 = 0.0D;
        double var7 = 0.0D;

        while(true) {
            ArrayList var9;
            double var23;
            double var24;
            label81:
            do {
                label63:
                while((new Date()).getTime() - var3 < (long)(maxRuntime * 1000)) {
                    var1 = this.shuffleStudents(var1);
                    var9 = new ArrayList();
                    HashMap var10 = new HashMap();
                    Iterator var11 = var1.iterator();

                    while(true) {
                        Student var12;
                        do {
                            if (!var11.hasNext()) {
                                var23 = this.calcSeatingScore(var9);
                                if (communist) {
                                    var24 = 0.0D;
                                    double var25 = 0.0D;
                                    double[] var17 = new double[this.numStudents];
                                    int var18 = 0;
                                    Iterator var19 = var9.iterator();

                                    while(var19.hasNext()) {
                                        Table var20 = (Table)var19.next();

                                        for(Iterator var21 = var20.getStudents().iterator(); var21.hasNext(); ++var18) {
                                            Student var22 = (Student)var21.next();
                                            var17[var18] = this.calcPersonScore(var22, this.getFriendsAtTable(var20, var22));
                                            var25 += var17[var18];
                                        }
                                    }

                                    var25 /= (double)this.numStudents;
                                    var24 = this.standardDeviation(var25, var17);
                                    continue label81;
                                }

                                if (var23 > var5) {
                                    var5 = var23;
                                    this.res = var9;
                                }
                                continue label63;
                            }

                            var12 = (Student)var11.next();
                        } while(var10.containsKey(var12.getStudentNumber()));

                        Table var13 = new Table(var2);
                        ArrayList var14 = var12.getFriendStudentNumbers();
                        Collections.shuffle(var14);
                        var10.put(var12.getStudentNumber(), var9.size());
                        var13.addStudent(var12);
                        Iterator var15 = var14.iterator();

                        while(var15.hasNext()) {
                            String var16 = (String)var15.next();
                            if (var13.getNumStudents() == var2) {
                                break;
                            }

                            if (!var10.containsKey(var16)) {
                                var10.put(var16, var9.size());
                                var13.addStudent((Student)this.studentsById.get(var16));
                            }
                        }

                        var9.add(var13);
                        new Table(var2);
                    }
                }

                this.res = this.groupTables(this.res, var2);
                return this.res;
            } while(((var24 - var7) / ((var24 + var7) / 2.0D) >= 0.05D || var23 <= var5) && var24 >= var7);

            var5 = var23;
            this.res = var9;
            var7 = var24;
        }
    }

    public void outputStatistics() {
        int var1 = 0;
        int var2 = 0;
        double var3 = 0.0D;
        double[] var5 = new double[this.numStudents];
        double var6 = 0.0D;
        double[] var8 = new double[this.numStudents];
        int var9 = 0;
        Iterator var10 = this.res.iterator();

        while(var10.hasNext()) {
            Table var11 = (Table)var10.next();
            int var12 = var11.getNumStudents();

            for(Iterator var13 = var11.getStudents().iterator(); var13.hasNext(); ++var9) {
                Student var14 = (Student)var13.next();
                int var15 = this.getFriendsAtTable(var11, var14);
                var2 += var15;
                var1 += var14.getFriendStudentNumbers().size();
                if (var14.getFriendStudentNumbers().size() > 0) {
                    var3 += (double)var15 * 1.0D / (double)var14.getFriendStudentNumbers().size();
                    var6 += this.calcPersonScore(var14, var15);
                    var5[var9] = (double)var15 * 1.0D / (double)var14.getFriendStudentNumbers().size();
                    var8[var9] = this.calcPersonScore(var14, var15);
                } else {
                    ++var3;
                    ++var6;
                    var5[var9] = 1.0D;
                    var8[var9] = 1.0D;
                }

                HashMap var16 = new HashMap();
                var16.put(var14.getStudentNumber(), this.calcPersonScore(var14, var15));
            }
        }

        var3 /= (double)this.numStudents;
        System.out.println((double)Math.round((double)var2 * 1000.0D / (double)var1) / 10.0D);
        System.out.println((double)Math.round(var3 * 1000.0D) / 10.0D);
        System.out.println((double)Math.round(var6 * 1000.0D) / 100.0D);
        System.out.println((double)Math.round(var5[this.numStudents / 2] * 1000.0D) / 10.0D);
        System.out.println((double)Math.round(var8[this.numStudents / 2] * 1000.0D) / 10.0D);
        System.out.println("");
        System.out.println((double)Math.round(this.standardDeviation(var3, var5) * 1000.0D) / 1000.0D);
        System.out.println((double)Math.round(this.standardDeviation(var6, var8) * 1000.0D) / 1000.0D);
        System.out.println("");
    }

    public void bidirectionalFriendships(boolean var1) {
        bidirectionalFriendship = var1;
    }

    public void communist(boolean var1) {
        communist = var1;
    }

    private ArrayList<Student> makeFriendsBidirectional(ArrayList<Student> var1) {
        Iterator var2 = var1.iterator();

        while(var2.hasNext()) {
            Student var3 = (Student)var2.next();
            ArrayList var4 = var3.getFriendStudentNumbers();
            Iterator var5 = var4.iterator();

            while(var5.hasNext()) {
                String var6 = (String)var5.next();

                for(int var7 = 0; var7 < var1.size(); ++var7) {
                    if (((Student)var1.get(var7)).getStudentNumber() == var6 && !((Student)var1.get(var7)).getFriendStudentNumbers().contains(var3.getStudentNumber())) {
                        ArrayList var8 = ((Student)var1.get(var7)).getFriendStudentNumbers();
                        var8.add(var3.getStudentNumber());
                        ((Student)var1.get(var7)).setFriendStudentNumbers(var8);
                    }
                }
            }
        }

        return var1;
    }

    private ArrayList<Student> shuffleStudents(ArrayList<Student> var1) {
        int var2 = ((Student)var1.get(0)).getFriendStudentNumbers().size();
        int var3 = 0;
        ArrayList var4 = new ArrayList();
        new ArrayList();

        ArrayList var5;
        for(int var6 = 0; var6 < this.numStudents; ++var6) {
            if (((Student)var1.get(var6)).getFriendStudentNumbers().size() != var2) {
                var5 = new ArrayList(var1.subList(var3, var6));
                var3 = var6;
                var2 = ((Student)var1.get(var6)).getFriendStudentNumbers().size();
                Collections.shuffle(var5);
                var4.addAll(var5);
            }
        }

        var5 = new ArrayList(var1.subList(var3, this.numStudents));
        Collections.shuffle(var5);
        var4.addAll(var5);
        return var4;
    }

    private ArrayList<Student> sortStudentsByNumFriends(ArrayList<Student> var1) {
        for(int var4 = 0; var4 < this.numStudents - 1; ++var4) {
            boolean var2 = false;

            for(int var5 = 0; var5 < this.numStudents - var4 - 1; ++var5) {
                if (((Student)var1.get(var5)).getFriendStudentNumbers().size() > ((Student)var1.get(var5 + 1)).getFriendStudentNumbers().size()) {
                    Student var3 = (Student)var1.get(var5);
                    var1.set(var5, (Student)var1.get(var5 + 1));
                    var1.set(var5 + 1, var3);
                    var2 = true;
                }
            }

            if (!var2) {
                break;
            }
        }

        return var1;
    }

    private HashMap<String, Student> createStudentsById(ArrayList<Student> var1) {
        HashMap var2 = new HashMap();
        Iterator var3 = var1.iterator();

        while(var3.hasNext()) {
            Student var4 = (Student)var3.next();
            var2.put(var4.getStudentNumber(), var4);
        }

        return var2;
    }

    private ArrayList<Table> groupTables(ArrayList<Table> var1, int var2) {
        ArrayList var3 = new ArrayList();
        Table var4 = new Table(var2);
        int var5 = 0;
        Iterator var6 = var1.iterator();

        while(var6.hasNext()) {
            Table var7 = (Table)var6.next();
            if (var5 + var7.getNumStudents() <= var2) {
                var4.addStudents(var7.getStudents());
                var5 += var7.getNumStudents();
            } else {
                var3.add(var4);
                var4 = new Table(var2);
                var4.addStudents(var7.getStudents());
                var5 = var7.getNumStudents();
            }
        }

        if (var4.getNumStudents() > 0) {
            var3.add(var4);
        }

        return var3;
    }

    private double calcSeatingScore(ArrayList<Table> var1) {
        double var2 = 0.0D;
        Iterator var4 = var1.iterator();

        while(var4.hasNext()) {
            Table var5 = (Table)var4.next();

            Student var7;
            for(Iterator var6 = var5.getStudents().iterator(); var6.hasNext(); var2 += this.calcPersonScore(var7, this.getFriendsAtTable(var5, var7))) {
                var7 = (Student)var6.next();
            }
        }

        return var2;
    }

    private int getFriendsAtTable(Table var1, Student var2) {
        int var3 = 0;
        Iterator var4 = var2.getFriendStudentNumbers().iterator();

        while(var4.hasNext()) {
            String var5 = (String)var4.next();
            if (var1.getStudents().contains(this.studentsById.get(var5))) {
                ++var3;
            }
        }

        return var3;
    }

    private double calcPersonScore(Student var1, int var2) {
        int var3 = var1.getFriendStudentNumbers().size();
        double var4 = -0.75D / (double)(2 * var2 + 1) + 0.75D;
        double var6 = 0.0D;
        if (var3 > 0) {
            var6 = -0.25D * ((double)var2 / ((double)var3 * 1.0D) - 1.0D) * ((double)var2 / ((double)var3 * 1.0D) - 1.0D) + 0.25D;
        } else {
            var6 = 1.0D;
        }

        return var4 + var6;
    }

    private double standardDeviation(double var1, double[] var3) {
        double var4 = 0.0D;

        for(int var6 = 0; var6 < this.numStudents; ++var6) {
            var4 += Math.pow(var3[var6] - var1, 2.0D);
        }

        return var4;
    }
}
>>>>>>> parent of 72e894b... Merge pull request #4 from feng-guo/feng-testing

package com.basic.leanring.java.calcite;



import java.sql.*;

/**
 * @author sunzihan
 * @version $Id: CalciteDemo.java V 0.1 3/21/17 22:19 sunzihan EXP $
 */
public class CalciteDemo {

    public static class HrSchema {
       // public final Employee[] emps = 0;
       // public final Department[] depts = 0;
    }

    public static void main(String[] args) {

    }



    public void test() throws ClassNotFoundException, SQLException {
//        Class.forName("org.apache.calcite.jdbc.Driver");
//        Properties info = new Properties();
//        info.setProperty("lex", "JAVA");
//        Connection connection = DriverManager.getConnection("jdbc:calcite:", info);
//        CalciteConnection calciteConnection =
//                connection.unwrap(CalciteConnection.class);
//        SchemaPlus rootSchema = calciteConnection.getRootSchema();
//
//        Schema schema = ReflectiveSchema.create(calciteConnection,
//                rootSchema, "hr", new HrSchema());
//        rootSchema.add("hr", schema);
//        Statement statement = calciteConnection.createStatement();
//        ResultSet resultSet = statement.executeQuery(
//                "select d.deptno, min(e.empid)\n"
//                        + "from hr.emps as e\n"
//                        + "join hr.depts as d\n"
//                        + "  on e.deptno = d.deptno\n"
//                        + "group by d.deptno\n"
//                        + "having count(*) > 1");
//        System.out.println(resultSet);
//        resultSet.close();
//        statement.close();
//        connection.close();
    }




}


package de.dbo.samples.springboot.data.elasticsearch.client.domain;

public final class EmployeePrint {
    
   public static StringBuilder print(Iterable<Employee> list) {
        final StringBuilder sb = new StringBuilder();
        list.forEach(employee -> sb.append("\n\t - " + print(employee)));
        return sb;
   }
   
   public static StringBuilder print(Employee employee) {
       final StringBuilder sb = new StringBuilder("Employee " + employee.getId()+":");
       sb.append("\n\t\t -- name    = " + employee.getName());
       sb.append("\n\t\t -- age     = " + employee.getAge());
       sb.append("\n\t\t -- skills  = " + employee.getSkills());
       sb.append("\n\t\t -- created = " + employee.getCreated());
       return sb;
  }

}

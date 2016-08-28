package de.dbo.samples.springboot.data.elasticsearch.client.domain2;

public final class CustomerPrint {
    
   public static StringBuilder print(Iterable<Customer> list) {
        final StringBuilder sb = new StringBuilder();
        list.forEach(customer -> sb.append("\n\t - " + print(customer)));
        return sb;
   }
   
   public static StringBuilder print(final Customer customer) {
       final StringBuilder sb = new StringBuilder("Customer " + customer.getId()+":");
       sb.append("\n\t\t -- name    = " + customer.getName());
       sb.append("\n\t\t -- age     = " + customer.getAge());
       sb.append("\n\t\t -- created = " + customer.getCreated());
       return sb;
  }

}

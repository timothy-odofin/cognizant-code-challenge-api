package cognizant.com.codechallenge.dto;

import lombok.Data;

@Data
public class CompilerResponse {
   private String output;
   private int statusCode;
   private Float memory;
   private Float cpuTime;
   private String error;
}

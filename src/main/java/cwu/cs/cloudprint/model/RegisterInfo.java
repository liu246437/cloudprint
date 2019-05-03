package cwu.cs.cloudprint.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterInfo implements Serializable {

    public String phoneNumber;

    public String verifyCode;

    public String password;

    public String signWord;
}

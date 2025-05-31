package FakeStoreUserAPI;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	private String password;
	private Address address;
	private String phone;
	private Name name;
	private String email;
	private String username;
}
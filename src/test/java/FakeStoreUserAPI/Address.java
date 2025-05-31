package FakeStoreUserAPI;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address{
	private String zipcode;
	private int number;
	private String city;
	private String street;
	private Geolocation geolocation;
}
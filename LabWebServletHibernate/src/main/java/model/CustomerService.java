package model;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
	@Autowired
	private CustomerDAO customerDao;

	public CustomerBean login(String username, String password) {
		CustomerBean bean = customerDao.select(username);
		if(bean!=null) {
			if(password!=null && password.length()!=0) {
				byte[] pass1 = password.getBytes();
				byte[] pass2 = bean.getPassword();
				if(Arrays.equals(pass1, pass2)) {
					return bean;
				}
			}
		}
		return null;
	}
	public boolean changePassword(String username, String oldPassword, String newPassword) {
		CustomerBean bean = this.login(username, oldPassword);
		if(bean!=null) {
			if(newPassword!=null && newPassword.length()!=0) {
				byte[] pass1 = newPassword.getBytes();	//�ϥΪ̿�J
				return customerDao.update(
						pass1, bean.getEmail(), bean.getBirth(), username);
			}
		}
		return false;
	}
}

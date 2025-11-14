package com.coder.springjwt.models;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(	name = "users",
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
		})
public class User extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

	@Size(max = 10)
	private String mobile;

	@Size(max = 10)
	private String sellerMobile;

	@Column(name = "customerMobileVerify", columnDefinition = "varchar(255) default 'N'")
	private String customerMobileVerify;

	@Column(name = "customerEmailVerify", columnDefinition = "varchar(255) default 'N'")
	private String customerEmailVerify;

	@Column(name = "sellerMobileVerify", columnDefinition = "varchar(255) default 'N'")
	private String sellerMobileVerify;

	@Column(name = "sellerEmailVerify", columnDefinition = "varchar(255) default 'N'")
	private String sellerEmailVerify;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@Size(max = 200)
	private String passKey;

	@Size(max = 200)
	private String custUsername;

	@Size(max = 20)
	private String customerMobileOtp;

	@Size(max = 20)
	@NotBlank
	private String projectRole;

	@Column(name = "customerRegisterComplete", columnDefinition = "varchar(255) default 'N'")
	private String customerRegisterComplete ;

	@Column(name = "sellerRegisterComplete", columnDefinition = "varchar(255) default 'N'")
	private String sellerRegisterComplete ;

	@Column(length = 50)
	private String customerForgotPasswordOtp ;

	@Column(length = 10)
	private String isCustomerForgotPassword;

	@Size(max = 100)
	@Email
	@Column(unique = true)
	private String customerEmail;

	@Size(max = 100)
	@Email
	@Column(unique = true)
	private String sellerEmail;

	@Size(max = 100)
	@Email
	@Column(unique = true)
	private String adminEmail;


	private String sellerStoreName;

	public User() {
	}

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public User(String username, String email, String password,String custUsername) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.custUsername = custUsername;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getPassKey() {
		return passKey;
	}

	public void setPassKey(String passKey) {
		this.passKey = passKey;
	}

	public String getCustUsername() {
		return custUsername;
	}

	public void setCustUsername(String custUsername) {
		this.custUsername = custUsername;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getCustomerEmailVerify() {
		return customerEmailVerify;
	}

	public void setCustomerEmailVerify(String customerEmailVerify) {
		this.customerEmailVerify = customerEmailVerify;
	}

	public String getCustomerMobileOtp() {
		return customerMobileOtp;
	}

	public void setCustomerMobileOtp(String customerMobileOtp) {
		this.customerMobileOtp = customerMobileOtp;
	}


	public String getCustomerRegisterComplete() {
		return customerRegisterComplete;
	}

	public void setCustomerRegisterComplete(String customerRegisterComplete) {
		this.customerRegisterComplete = customerRegisterComplete;
	}


	public String getProjectRole() {
		return projectRole;
	}

	public void setProjectRole(String projectRole) {
		this.projectRole = projectRole;
	}

	public String getCustomerForgotPasswordOtp() {
		return customerForgotPasswordOtp;
	}

	public void setCustomerForgotPasswordOtp(String customerForgotPasswordOtp) {
		this.customerForgotPasswordOtp = customerForgotPasswordOtp;
	}

	public String getIsCustomerForgotPassword() {
		return isCustomerForgotPassword;
	}

	public void setIsCustomerForgotPassword(String isCustomerForgotPassword) {
		this.isCustomerForgotPassword = isCustomerForgotPassword;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}


	public String getCustomerMobileVerify() {
		return customerMobileVerify;
	}

	public void setCustomerMobileVerify(String customerMobileVerify) {
		this.customerMobileVerify = customerMobileVerify;
	}

	public String getSellerMobileVerify() {
		return sellerMobileVerify;
	}

	public void setSellerMobileVerify(String sellerMobileVerify) {
		this.sellerMobileVerify = sellerMobileVerify;
	}

	public String getSellerEmailVerify() {
		return sellerEmailVerify;
	}

	public void setSellerEmailVerify(String sellerEmailVerify) {
		this.sellerEmailVerify = sellerEmailVerify;
	}


	public String getSellerRegisterComplete() {
		return sellerRegisterComplete;
	}

	public void setSellerRegisterComplete(String sellerRegisterComplete) {
		this.sellerRegisterComplete = sellerRegisterComplete;
	}

	public String getSellerMobile() {
		return sellerMobile;
	}

	public void setSellerMobile(String sellerMobile) {
		this.sellerMobile = sellerMobile;
	}

	public String getSellerStoreName() {
		return sellerStoreName;
	}

	public void setSellerStoreName(String sellerStoreName) {
		this.sellerStoreName = sellerStoreName;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", mobile='" + mobile + '\'' +
				", sellerMobile='" + sellerMobile + '\'' +
				", customerMobileVerify='" + customerMobileVerify + '\'' +
				", customerEmailVerify='" + customerEmailVerify + '\'' +
				", sellerMobileVerify='" + sellerMobileVerify + '\'' +
				", sellerEmailVerify='" + sellerEmailVerify + '\'' +
				", roles=" + roles +
				", passKey='" + passKey + '\'' +
				", custUsername='" + custUsername + '\'' +
				", customerMobileOtp='" + customerMobileOtp + '\'' +
				", projectRole='" + projectRole + '\'' +
				", customerRegisterComplete='" + customerRegisterComplete + '\'' +
				", sellerRegisterComplete='" + sellerRegisterComplete + '\'' +
				", customerForgotPasswordOtp='" + customerForgotPasswordOtp + '\'' +
				", isCustomerForgotPassword='" + isCustomerForgotPassword + '\'' +
				", customerEmail='" + customerEmail + '\'' +
				", sellerEmail='" + sellerEmail + '\'' +
				", adminEmail='" + adminEmail + '\'' +
				", sellerStoreName='" + sellerStoreName + '\'' +
				'}';
	}
}

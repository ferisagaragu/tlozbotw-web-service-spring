package org.neurobrain.tlozbotw.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String lastName;
	private String phoneNumber;
	private String imageUrl;
	private String userName;
	private String email;
	private String password;
	private String recoverCode;

	@Column(columnDefinition = "boolean default true")
	private Boolean firstSession;

	@Column(columnDefinition = "boolean default false")
	private Boolean locked;

	@Column(columnDefinition = "boolean default true")
	private Boolean enabled;

	@ManyToMany(fetch= FetchType.LAZY, cascade= CascadeType.ALL)
	@JoinTable(
		name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name="role_id"),
		uniqueConstraints = { @UniqueConstraint(columnNames = {"user_id", "role_id"}) }
	)
	private List<Role> roles;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<UserBow> userBows;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<LockReason> lockReasons;


	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Boolean getFirstSession() {
		return firstSession;
	}

	public void setFirstSession(Boolean firstSession) {
		this.firstSession = firstSession;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getRecoverCode() {
		return recoverCode;
	}

	public void setRecoverCode(String recoverCode) {
		this.recoverCode = recoverCode;
	}

	public List<UserBow> getUserBows() {
		return userBows;
	}

	public void setUserBows(List<UserBow> userBows) {
		this.userBows = userBows;
	}

	public List<LockReason> getLockReasons() {
		return lockReasons;
	}

	public void setLockReasons(List<LockReason> lockReasons) {
		this.lockReasons = lockReasons;
	}

	public boolean containsRole(String roleStg) {
		for (Role role: roles) {
			if (role.getName().equals("ROLE_" + roleStg)) {
				return true;
			}
		}
		return false;
	}

	public List<String> getFormatRoles() {
		List<String> rolesOut = new ArrayList<>();
		roles.forEach((Role role) -> {
			rolesOut.add(role.getName());
		});
		return rolesOut;
	}

}

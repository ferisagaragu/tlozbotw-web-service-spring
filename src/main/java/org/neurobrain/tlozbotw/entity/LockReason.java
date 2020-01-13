package org.neurobrain.tlozbotw.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Calendar;

@Entity
@Table(name = "lock_reason")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class LockReason implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String reason;
	private String lockerMail;
	private Long timesLocked;

	@Temporal(TemporalType.DATE)
	private Calendar lockDate;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;


	public LockReason() { }

	public LockReason(
		String reason,
		String lockerMail,
		Long timesLocked,
		Calendar lockDate,
		User user
	) {
		this.reason = reason;
		this.lockerMail = lockerMail;
		this.timesLocked = timesLocked;
		this.lockDate = lockDate;
		this.user = user;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getLockerMail() {
		return lockerMail;
	}

	public void setLockerMail(String lockerMail) {
		this.lockerMail = lockerMail;
	}

	public Long getTimesLocked() {
		return timesLocked;
	}

	public void setTimesLocked(Long timesLocked) {
		this.timesLocked = timesLocked;
	}

	public Calendar getLockDate() {
		return lockDate;
	}

	public void setLockDate(Calendar lockDate) {
		this.lockDate = lockDate;
	}

}

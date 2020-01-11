package org.neurobrain.tlozbotw.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.neurobrain.tlozbotw.util.Text;
import java.io.Serializable;


@Entity
@Table(name = "user_bow")
public class UserBow implements Serializable {

	@Id
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Id
	@ManyToOne
	@JoinColumn(name = "bow_id")
	private Bow bow;

	@Column(columnDefinition = "boolean default true")
	private Boolean see;


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Bow getBow() {
		return bow;
	}

	public void setBow(Bow bow) {
		this.bow = bow;
	}

	public Boolean getSee() {
		return see;
	}

	public void setSee(Boolean see) {
		this.see = see;
	}

	@Override
	public String toString() {
		return Text.toJSONString(this);
	}

}

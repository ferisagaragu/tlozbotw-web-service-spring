package org.neurobrain.tlozbotw.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "bow")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Bow implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private Long damage;
	private Long numberArrows;
	private String description;
	private String img;

	@Column(columnDefinition = "boolean default true")
	private Boolean available;

	@OneToMany(mappedBy = "bow", cascade = CascadeType.ALL)
	private List<UserBow> userBows;


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

	public Long getDamage() {
		return damage;
	}

	public void setDamage(Long damage) {
		this.damage = damage;
	}

	public Long getNumberArrows() {
		return numberArrows;
	}

	public void setNumberArrows(Long numberArrows) {
		this.numberArrows = numberArrows;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public List<UserBow> getUserBows() {
		return userBows;
	}

	public void setUserBows(List<UserBow> userBows) {
		this.userBows = userBows;
	}

	/*@Override
	public String toString() {
		return Text.toJSONString(this);
	}*/

}

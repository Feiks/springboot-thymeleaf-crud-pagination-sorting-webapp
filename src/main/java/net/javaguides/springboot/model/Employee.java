package net.javaguides.springboot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
public class Employee {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;

	@Column(name = "amount")
	private String amount;

	@Column(name = "currency")
	private String currency;

	@Column(name = "code")
	private String code;

	@Column(name = "state")
	private String state;

	@Column(name = "creation_date")
	private LocalDateTime date;

	@Column(name = "who_created")
	private String user;

	@Column(name = "who_changed")
	private String userChangeBy;

	@Column(name = "change_date")
	private LocalDateTime changedate;

	public String getUserChangeBy() {
		return userChangeBy;
	}

	public void setUserChangeBy(String userChangeBy) {
		this.userChangeBy = userChangeBy;
	}

	public LocalDateTime getChangedate() {
		return changedate;
	}

	public void setChangedate(LocalDateTime changedate) {
		this.changedate = changedate;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Employee(long id, String firstName, String lastName, String amount, String currency, String code, String state, LocalDateTime date, String user, String userChangeBy, LocalDateTime changedate) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.amount = amount;
		this.currency = currency;
		this.code = code;
		this.state = state;
		this.date = date;
		this.user = user;
		this.userChangeBy = userChangeBy;
		this.changedate = changedate;
	}

	public Employee(long id, String firstName, String lastName, String amount, String currency, String code, String state) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.amount = amount;
		this.currency = currency;
		this.code = code;
		this.state = state;
	}

	public Employee() {
	}
}

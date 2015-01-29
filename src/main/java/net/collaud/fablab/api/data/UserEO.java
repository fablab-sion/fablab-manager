package net.collaud.fablab.api.data;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Gaetan Collaud <gaetancollaud@gmail.com>
 */
@Entity
@Table(name = "t_user")
@Getter
@Setter
@ToString
public class UserEO extends AbstractDataEO<Integer> implements Serializable {

	public static final String FIND_ALL
			= " SELECT u "
			+ " FROM UserEO u "
			+ " LEFT JOIN FETCH u.membershipType AS mt ";

	public static final String FIND_BY_LOGIN
			= " SELECT u "
			+ " FROM UserEO u "
			+ " LEFT JOIN FETCH u.groups AS g "
			+ " LEFT JOIN FETCH g.roles AS r "
			+ " WHERE u.login = :" + UserEO.PARAM_LOGIN + " "
			+ " OR u.email=:" + UserEO.PARAM_LOGIN;

	public static final String FIND_BY_ID_AND_FETCH_ROLES
			= " SELECT u "
			+ " FROM UserEO u "
			+ " LEFT JOIN FETCH u.groups AS g "
			+ " LEFT JOIN FETCH g.roles AS r "
			+ " WHERE u.id = :" + UserEO.PARAM_ID;
	
	public static final String FIND_BY_ID_DETAIL
			= " SELECT u "
			+ " FROM UserEO u "
			+ " LEFT JOIN FETCH u.groups AS g "
			+ " LEFT JOIN FETCH u.membershipType AS mt "
			+ " WHERE u.id = :" + UserEO.PARAM_ID;

	public static final String PARAM_LOGIN = "login";
	public static final String PARAM_RFID = "rfid";
	public static final String PARAM_IDS = "ids";
	public static final String PARAM_ID = "id";

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false)
	private Integer id;

	@Column(name = "login", nullable = false, unique = true)
	private String login;

	@JsonIgnore
	@Column(name = "password", nullable = false)
	private String password;
	
	@JsonIgnore
	@Column(name = "password_salt", nullable = false)
	private String passwordSalt;
	
	@Transient
	@JsonProperty
	private String passwordNew;

	@Column(name = "firstname", nullable = false)
	private String firstname;

	@Column(name = "lastname", nullable = false)
	private String lastname;

	@Column(name = "email", nullable = true, unique = true)
	private String email;

	@Column(name = "date_inscr", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateInscr;

	@Column(name = "balance", nullable = false)
	private float balance;

	@Column(name = "rfid", nullable = true)
	private String rfid;

	@Column(name = "last_subscription_confirmation", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastSubscriptionConfirmation;

	@JsonIgnore
	@Column(name = "enabled", nullable = false)
	private boolean enabled;

	@JsonIgnore
	@Column(name = "auth_by_sql", nullable = false)
	private boolean authBySql;

	@Column(name = "phone", nullable = true)
	private String phone;

	@Column(name = "address", nullable = true)
	private String address;

	@JsonManagedReference("user-subscription")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<SubscriptionEO> subscriptions;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<PaymentEO> payments;

	@JoinTable(name = "r_group_user",
			joinColumns = {
				@JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, updatable = false)},
			inverseJoinColumns = {
				@JoinColumn(name = "group_id", referencedColumnName = "group_id", nullable = false, updatable = false)})
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<GroupEO> groups;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
	private Set<ReservationEO> reservations;

	@JoinColumn(name = "membership_type_id", referencedColumnName = "membership_type_id")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private MembershipTypeEO membershipType;

	public UserEO() {
	}

	public UserEO(Integer id) {
		this.id = id;
	}

	public UserEO(Integer id, boolean authBySql, String login, String password, String firstname, String lastname, Date dateInscr, float balance, String rfid) {
		this.id = id;
		this.authBySql = authBySql;
		this.login = login;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.dateInscr = dateInscr;
		this.balance = balance;
		this.rfid = rfid;
		this.enabled = true;
	}


	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	public Date getLastSubscriptionConfirmation() {
		return lastSubscriptionConfirmation;
	}

	public void setLastSubscriptionConfirmation(Date lastSubscriptionConfirmation) {
		this.lastSubscriptionConfirmation = lastSubscriptionConfirmation;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof UserEO)) {
			return false;
		}
		UserEO other = (UserEO) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "net.collaud.fablab.data.User[ userId=" + id + " ]";
	}

	@JsonIgnore
	public String getFirstLastName() {
		return firstname+" "+lastname;
	}
}

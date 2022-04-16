package SE2.user.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Length(min = 5, max = 30)
    private String userName;

    @Length(min = 8, max = 30)
    private String password;

    @Length(min = 5, max = 30)
    private String name;

    @Email
    private String email;
    
    private String address;

    private int gender;

    @Length(min = 9, max = 20)
    private String phoneNumber;

//    @ManyToMany
//    @JoinTable(name = "people_roles",
//        joinColumns = @JoinColumn(name = "User_id"),
//        inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Collection<Role> roles;

    @OneToOne
    private Role role;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
//
//    public Collection<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Collection<Role> roles) {
//        this.roles = roles;
//    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

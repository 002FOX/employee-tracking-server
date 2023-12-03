package fox.server.payload;

import fox.server.model.Role;

public class UserResponse {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private boolean enabled;
    private Role role;
    private String positionTitle;

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public UserResponse(Long id, String firstname, String lastname, String email, boolean enabled, Role role, String positionTitle) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.enabled = enabled;
        this.role = role;
        this.positionTitle = positionTitle;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}